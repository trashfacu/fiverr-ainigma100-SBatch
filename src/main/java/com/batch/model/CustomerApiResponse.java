package com.batch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerApiResponse {

    private List<CustomerErmDTO> data;

    @JsonProperty("first")
    private int first;
    @JsonProperty("prev")
    private Integer prev;
    @JsonProperty("next")
    private Integer next;
    @JsonProperty("last")
    private int last;
    @JsonProperty("pages")
    private int pages;
    @JsonProperty("items")
    private int items;

}
