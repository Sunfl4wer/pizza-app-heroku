package com.pycogroup.pizza.product.common;

import lombok.Getter;
import lombok.Setter;

public class Pagination {

  @Getter
  @Setter
  private int size;

  @Getter
  @Setter
  private Integer currentPage;

  @Getter
  @Setter
  private long offset;

  @Getter
  @Setter
  private Long previousOffset;

  @Getter
  @Setter
  private Long nextOffset;

  @Getter
  @Setter
  private int pageCount;

  @Getter
  @Setter
  private long totalCount;

  public Pagination(int size, int page, int totalPage, long totalDocument) {
    this.offset = (long)((page-1)*size);
    this.size = size;
    if(page == 1) {
      this.previousOffset = null;
    } else {
      this.previousOffset = (long) ((page-2)*size);
    }
    if(page >= totalPage) {
      this.nextOffset = null;
    } else {
      this.nextOffset = (long) (page*size);
    }
    if (page > totalPage) {
      this.currentPage = null;
    } else {
      this.currentPage = page;
    }
    this.pageCount = totalPage;
    this.totalCount = totalDocument;
  }
}