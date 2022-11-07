package com.infocz.igviewer.api.common;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

public class CamelMap extends HashMap {
  
  @SuppressWarnings("unchecked")
  @Override
  public Object put(Object key, Object value) {
    return super.put(JdbcUtils.convertUnderscoreNameToPropertyName((String) key), value);
  }
  
}
