package com.infocz.igviewer.api.session;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConnectionInfo {
	private String database;
	private String graph;
	private LocalDateTime dateTime;
}	
