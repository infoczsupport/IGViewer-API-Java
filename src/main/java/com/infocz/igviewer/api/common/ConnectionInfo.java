package com.infocz.igviewer.api.common;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConnectionInfo {
	private String database;
	private String graph;
	private LocalDateTime dateTime;
}	
