package com.micrometer.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.micrometer.model.request.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paging {

  @JsonProperty("page")
  private Long page;

  @JsonProperty("total_page")
  private Long totalPage;

  @JsonProperty("item_per_page")
  private Long itemPerPage;

  @JsonProperty("total_item")
  private Long totalItem;

  @JsonProperty("sort_by")
  private List<SortBy> sortBy;

}
