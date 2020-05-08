package com.pycogroup.pizza.product.api;


import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pycogroup.pizza.product.common.ErrorResponseBody;
import com.pycogroup.pizza.product.common.GenericResponse;
import com.pycogroup.pizza.product.common.GenericResponseCollection;
import com.pycogroup.pizza.product.common.GenericResponseError;
import com.pycogroup.pizza.product.common.LinkEntity;
import com.pycogroup.pizza.product.common.Links;
import com.pycogroup.pizza.product.common.Message;
import com.pycogroup.pizza.product.common.MetaForCollection;
import com.pycogroup.pizza.product.common.Reason;
import com.pycogroup.pizza.product.dto.ResponseCollectionDto;
import com.pycogroup.pizza.product.dto.ResponseDto;
import com.pycogroup.pizza.product.model.Category;
import com.pycogroup.pizza.product.model.Product;
import com.pycogroup.pizza.product.service.ProductService;

@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/pizza")
public class ProductApi {


  @Autowired
  ProductService productService;

  //User API
  @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> getProductById(
      @PathVariable("id") String id,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/pizza/products/" + id);
    headers.setLocation(locationUri);
    if (ObjectId.isValid(id)) {
      GenericResponse response = productService.getProductById(id);
      if (response.getData() instanceof ErrorResponseBody) {
        return new ResponseEntity<Object>(response,headers,
                                                  HttpStatus.valueOf(((ErrorResponseBody) response.getData()).getCode()));
      } else {
        // Create body for ResponseEntity: body = {serviceResposne,links}
        Links links = new Links();
        links.add(LinkEntity.builder().href(locationUri).relation("Self").method(RequestMethod.GET).build());
        return new ResponseEntity<Object>(ResponseDto.builder().code(HttpStatus.OK.value()).data(response.getData()).links(links.getLinks()).build()
            ,headers,HttpStatus.OK);
      }
    } else {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", id);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("id","Must be a valid ObjectId hexademical String");
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_FORMAT.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
     
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
  }
  
  @RequestMapping(
      value = "/productcards", 
      method = RequestMethod.GET, 
      params = {"category","size","page"})
  public ResponseEntity<Object> getProductCardByCategoryWithPagination(
      @RequestParam("category") String category,
      @RequestParam("size") String size,
      @RequestParam("page") String page,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + page);
    headers.setLocation(locationUri);
    if (Category.isCategory(category) && StringUtils.isNumeric(size) && StringUtils.isNumeric(page)) {
      GenericResponseCollection response = productService.getProductCardsByCategoryWithPagination(Category.valueOf(category), Integer.parseInt(size), Integer.parseInt(page));
      if (response.getData() instanceof ErrorResponseBody) {
        return new ResponseEntity<Object>(response,headers,
                                                  HttpStatus.valueOf(((ErrorResponseBody) response.getData()).getCode()));
      } else {
        // Create body for ResponseEntity: body = {serviceResposne,links}
        ArrayList<Object> body = new ArrayList<Object>();
        body.add(response);
        Links links = new Links();
        int currentPage = Integer.parseInt(page);
        int lastPage = ((MetaForCollection)(response).getMeta()).getPagination().getPageCount();
        URI nextPageUri = null;
        if (currentPage < lastPage) {
          nextPageUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + String.valueOf(((Integer.parseInt(page) + 1))));
        }
        URI previousPageUri = null;
        if (currentPage > 1 && currentPage <= lastPage) {
          previousPageUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + String.valueOf(((Integer.parseInt(page) - 1))));
        }
        URI  currentPageUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + page);
        URI  firstPageUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + "1");
        URI  lastPageUri = URI.create(baseUri + "/pizza/productcards?category=" + category + "&size=" + size + "&page=" + String.valueOf(lastPage));
        links.add(LinkEntity.builder().href(firstPageUri).relation("First page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(previousPageUri).relation("Previous page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(currentPageUri).relation("Current page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(nextPageUri).relation("Next page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(lastPageUri).relation("Last page").method(RequestMethod.GET).build());
        return new ResponseEntity<Object>(ResponseCollectionDto.builder().code(HttpStatus.OK.value()).data(response.getData()).meta(response.getMeta()).links(links).build(),
                                          headers,
                                          HttpStatus.OK);
      }
    } else {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("category", category);
      where.put("size", size);
      where.put("page", page);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("category","Must be one of the valid category [PIZZA, SIDE_DISH, DRINK, DESSERT]");
      when.put("size",Arrays.asList("Must be integer","Must consist only of numeric character [0,1,2,3,4,5,6,7,8,9]","Must be > 0 and < 100"));
      when.put("page",Arrays.asList("Must be integer","Must consist only of numeric character [0,1,2,3,4,5,6,7,8,9]","Must be > 0"));
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_FORMAT.getMessage())
                                              .reason(Reason.BAD_PARAMS.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
     
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(
      value = "/productcards", 
      method = RequestMethod.GET, 
      params = {"size","page"})
  public ResponseEntity<Object> getAllProductCardsWithPagination(
      @RequestParam("size") String size,
      @RequestParam("page") String page,
      UriComponentsBuilder ucb) {
    HttpHeaders headers = new HttpHeaders();
    String baseUri = ucb.build().toString();
    URI locationUri = URI.create(baseUri + "/pizza/productcards?" + "&size=" + size + "&page=" + page);
    headers.setLocation(locationUri);
    if (StringUtils.isNumeric(size) && StringUtils.isNumeric(page)) {
      GenericResponseCollection response = productService.getAllProductCardsWithPagination(Integer.parseInt(size), Integer.parseInt(page));
      if (response.getData() instanceof ErrorResponseBody) {
        return new ResponseEntity<Object>(response,headers,
                                                  HttpStatus.valueOf(((ErrorResponseBody) response.getData()).getCode()));
      } else {
        // Create body for ResponseEntity: body = {serviceResposne,links}
        ArrayList<Object> body = new ArrayList<Object>();
        body.add(response);
        Links links = new Links();
        int currentPage = Integer.parseInt(page);
        int lastPage = ((MetaForCollection)(response).getMeta()).getPagination().getPageCount();
        URI nextPageUri = null;
        if (currentPage < lastPage) {
          nextPageUri = URI.create(baseUri + "/pizza/productcards?" + "size=" + size + "&page=" + String.valueOf(((Integer.parseInt(page) + 1))));
        }
        URI previousPageUri = null;
        if (currentPage > 1 && currentPage <= lastPage) {
          previousPageUri = URI.create(baseUri + "/pizza/productcards?" + "size=" + size + "&page=" + String.valueOf(((Integer.parseInt(page) - 1))));
        }
        URI  currentPageUri = URI.create(baseUri + "/pizza/productcards?" + "size=" + size + "&page=" + page);
        URI  firstPageUri = URI.create(baseUri + "/pizza/productcards?" + "size=" + size + "&page=" + "1");
        URI  lastPageUri = URI.create(baseUri + "/pizza/productcards?" + "size=" + size + "&page=" + String.valueOf(lastPage));
        links.add(LinkEntity.builder().href(firstPageUri).relation("First page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(previousPageUri).relation("Next page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(currentPageUri).relation("Current page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(nextPageUri).relation("Next page").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(lastPageUri).relation("Last page").method(RequestMethod.GET).build());
        body.add(links);
        return new ResponseEntity<Object>(ResponseCollectionDto.builder().code(HttpStatus.OK.value()).data(response.getData()).meta(response.getMeta()).links(links).build(),
                                          headers,
                                          HttpStatus.OK);
      }
    } else {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("size", size);
      where.put("page", page);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("size",Arrays.asList("Must be integer","Must consist only of numeric character [0,1,2,3,4,5,6,7,8,9]","Must be > 0 and < 100"));
      when.put("page",Arrays.asList("Must be integer","Must consist only of numeric character [0,1,2,3,4,5,6,7,8,9]","Must be > 0"));
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_FORMAT.getMessage())
                                              .reason(Reason.BAD_PARAMS.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
     
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////
  //  Admin API                                                                                       //
  //////////////////////////////////////////////////////////////////////////////////////////////////////
 /* 
  @RequestMapping(
      value="", 
      method=RequestMethod.POST, 
      consumes="application/JSON")
  public ResponseEntity<Object> createProduct(
      @RequestParam("username") String username,
      @RequestParam("accessToken") String accessToken,
      @RequestBody Product product,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/products");
    headers.setLocation(locationUri);
    GenericResponse response = productService.createProduct(product);
    // Create body for ResponseEntity: body = {serviceResposne,links}
    ArrayList<Object> body = new ArrayList<Object>();
    body.add(response);
    Links links = new Links();
    links.add(LinkEntity.builder().href(locationUri).relation("Get product").method(RequestMethod.GET).build());
    links.add(LinkEntity.builder().href(locationUri).relation("Update product").method(RequestMethod.PUT).build());
    links.add(LinkEntity.builder().href(locationUri).relation("Delete product").method(RequestMethod.DELETE).build());
    body.add(links);
    return new ResponseEntity<Object>(
                                      body,
                                      headers,
                                      HttpStatus.CREATED);
  }
*/
  @RequestMapping(
      value="/products/createMany", 
      method=RequestMethod.POST, 
      consumes="application/JSON")
  public ResponseEntity<Object> createMany(
      @RequestBody ArrayList<Product> products,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/products/createMany");
    headers.setLocation(locationUri);
    GenericResponse response = productService.createBulk(products);
    return new ResponseEntity<Object>(
                                      response,
                                      headers,
                                      HttpStatus.CREATED);
  }
/*
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteProduct(
      @PathVariable("id") String id,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/products/" + id);
    headers.setLocation(locationUri);
    if (ObjectId.isValid(id)) {
      GenericResponse response = productService.deleteProduct(id);
      if (response.getData() instanceof ErrorResponseBody) {
        return new ResponseEntity<Object>(response,headers,
                                                  HttpStatus.valueOf(((ErrorResponseBody) response.getData()).getCode()));
      } else {
        return new ResponseEntity<Object>(response,headers,HttpStatus.NO_CONTENT);
      }
    } else {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", id);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("id","Must be a valid ObjectId hexademical String");
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_FORMAT.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
     
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
  }
  
  @RequestMapping(value = "/{id}", 
      method = RequestMethod.PUT)
  public ResponseEntity<Object> updateProduct(
      @PathVariable("id") String id,
      @RequestBody Product updateProduct,
      UriComponentsBuilder ucb) {
    String baseUri = ucb.build().toString();
    HttpHeaders headers = new HttpHeaders();
    URI locationUri = URI.create(baseUri + "/products/" + id);
    headers.setLocation(locationUri);
    if (ObjectId.isValid(id)) {
      GenericResponse response = productService.updateProduct(id,updateProduct);
      if (response.getData() instanceof ErrorResponseBody) {
        return new ResponseEntity<Object>(response,headers,
                                                  HttpStatus.valueOf(((ErrorResponseBody) response.getData()).getCode()));
      } else {
        // Create body for ResponseEntity: body = {serviceResposne,links}
        ArrayList<Object> body = new ArrayList<Object>();
        body.add(response);
        Links links = new Links();
        links.add(LinkEntity.builder().href(locationUri).relation("Get product").method(RequestMethod.GET).build());
        links.add(LinkEntity.builder().href(locationUri).relation("Update product").method(RequestMethod.PUT).build());
        links.add(LinkEntity.builder().href(locationUri).relation("Delete product").method(RequestMethod.DELETE).build());
        body.add(links);
        return new ResponseEntity<Object>(body,headers,HttpStatus.OK);
      }
    } else {
      LinkedHashMap<String , Object> where = new LinkedHashMap<String , Object>();
      where.put("id", id);
      LinkedHashMap<String , Object> when = new LinkedHashMap<String , Object>();
      when.put("id","Must be a valid ObjectId hexademical String");
      GenericResponseError response = new GenericResponseError(ErrorResponseBody.builder()
                                              .timestamp(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")))
                                              .status(HttpStatus.BAD_REQUEST)
                                              .code(HttpStatus.BAD_REQUEST.value())
                                              .message(Message.BAD_REQUEST_FORMAT.getMessage())
                                              .reason(Reason.BAD_PATH_VARIABLE.getReason())
                                              .where(where)
                                              .when(when)
                                              .build());
     
      return new ResponseEntity<Object>(response,headers,HttpStatus.BAD_REQUEST);
    }
  }
*/
}
