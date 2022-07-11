package com.estudo.api.util;

import java.util.UUID;

public interface IdGenerator {

	public static String get() {
		return UUID.randomUUID().toString();
	}

}
