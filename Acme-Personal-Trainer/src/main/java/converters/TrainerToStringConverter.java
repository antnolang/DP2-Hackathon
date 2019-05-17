/*
 * BoxToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import javax.swing.Box;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TrainerToStringConverter implements Converter<Box, String> {

	@Override
	public String convert(final Box box) {
		String result;

		if (box == null)
			result = null;
		else
			result = String.valueOf(box.getId());

		return result;
	}

}