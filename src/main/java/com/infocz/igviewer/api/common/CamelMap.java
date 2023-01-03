package com.infocz.igviewer.api.common;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CamelMap extends HashMap {  
  @Override
  public Object put(Object key, Object value) {
    return super.put(JdbcUtils.convertUnderscoreNameToPropertyName((String) key), value);
  }
  
}
