package com.ufes.ms_relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpsertResult<T> {

  private final T data;
  private final boolean created;
}
