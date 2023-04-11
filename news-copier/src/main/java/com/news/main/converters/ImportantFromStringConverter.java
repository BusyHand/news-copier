package com.news.main.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.news.main.enums.Important;

/**
 * Converter for convert String to {@link com.news.main.enums.Important}
 */
@Component
public class ImportantFromStringConverter implements Converter<String, Important> {

	@Override
	public Important convert(String source) {
		for(Important important : Important.values()) {
			if(important.toString().equals(source)) {
				return important;
			}
		}
		return Important.ETC;
	}

	
	


	

}
