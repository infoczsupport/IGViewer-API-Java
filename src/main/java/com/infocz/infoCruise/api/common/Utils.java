package com.infocz.infoCruise.api.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Utils {
	/**
	 * uuid 생성
	 * @return string
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * uuid 생성
	 * @return string
	 */
	public static String getUuidDash() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 숫자 문자열 반환
	 * @param obj : Object
	 * @return string
	 */
	public static String getStrNum(Object obj) {
		if ((null == obj)) {
			return "";
		}
		return getString(obj.toString()).replaceAll("[^0-9.]", "");
	}

	/**
	 * 숫자 문자열 반환(음수포함)
	 * @param obj : Object
	 * @return string
	 */
	public static String getStrNegNum(Object obj) {
		if ((null == obj)) {
			return "";
		}
		return getString(obj.toString()).replaceAll("[^-0-9.]", "");
	}

	/**
	 * 문자열 반환
	 * @param obj : Object
	 * @return string
	 */
	public static String getString(Object obj) {
		if ((null == obj)) {
			return "";
		}

		return getString(obj.toString());
	}

	/**
	 * 문자열 반환
	 * @param str : String
	 * @return string
	 */
	public static String getString(String str) {
		if ((null == str) || "".equals(str)) {
			return "";
		} else if ((null == str.trim()) || "".equals(str.trim())) {
			return "";
		}

		return str.trim();
	}
	
	public static String getString(Object obj, String strDef) {
		if ((null == obj)) {
			return strDef;
		}

		return getString(obj.toString(), strDef);
	}

	public static String getString(String str, String strDef) {
		if ((null == str) || "".equals(str)) {
			return strDef;
		} else if ((null == str.trim()) || "".equals(str.trim())) {
			return strDef;
		}

		return str.trim();
	}

	@SuppressWarnings("unchecked")
	public static String[] getStringArray(Object obj) {
		if (obj == null) return null;

		if ( obj.getClass().getSimpleName().equals("ArrayList")) {
			ArrayList<String> arrList = (ArrayList<String>) obj;
			String[] result = arrList.toArray(new String[arrList.size()]);
			return result;
		}

		if ( ! obj.getClass().isArray() ) {
			String[] result = Arrays.asList(obj).toArray( new String[0] );
			return result;
		}

		if( ! obj.getClass().isArray() ) {
			String[] result = {""};
			result[0] = (String) obj; 
			return result;
		}

		return (String[]) obj;
	}

	public static Boolean isEmpty(Object value) {
		if ((null == value) || "".equals(value)) {
			return true;
		} else if ((null == value.toString().trim()) || "".equals(value.toString().trim())) {
			return true;
		}

		return false;
	}

	public static List<Map<String, Object>> convertDoubleQuotation(List<Map<String, Object>> list) {
		for (Map<String,Object> map : list) {
			for (String el : map.keySet()) {
				map.replace(el, map.get(el).toString().replace("\"", ""));
			}
		}
		return list;
	}
}	
