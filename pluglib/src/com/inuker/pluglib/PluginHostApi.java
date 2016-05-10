package com.inuker.pluglib;

public class PluginHostApi {
	
	protected static IPluginHostApi instance;
	
	public static IPluginHostApi getInstance() {
		return instance;
	}
}
