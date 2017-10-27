package com.antibry.recruitment.web.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class CommonObjectSerializer extends StdSerializer<Object> {

	public CommonObjectSerializer() {
		this(null);
	}
	
	public CommonObjectSerializer(Class<Object> t) {
		super(t);
	}

	@Override
	public void serialize(Object obj, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Map<String, Object>> fieldList = new ArrayList<>();
		Map<String, Object> temp = new HashMap<String, Object>();
		
		for (Field field: fields) {
			String fieldName = "";
			
			try {
				fieldName = field.getName().toString();
				System.out.println("get" + StringUtils.capitalize(fieldName));
				Method method = obj.getClass().getMethod("get" + StringUtils.capitalize(fieldName));

				temp.put(field.getName().toString(), method.invoke(obj));
				temp.put("class", field.getClass().toString());
			
				fieldList.add(temp);
				
				temp.clear();
			} catch (IllegalArgumentException | SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) { }
		}
		
		jgen.writeStartObject();
		
		if (!fieldList.isEmpty()) {
			for (Map<String, Object> map: fieldList) {
				String key = map.keySet()
								.stream()
								.filter(entry -> !entry.equals("class"))
								.findAny()
								.orElse("");
			
				if (key != null) {
					if (!(map.get(key) instanceof List<?>)) {
						jgen.writeStringField(key, map.get(key).toString());
					}
				}	
			}
		}
		jgen.writeEndObject();
	}
}
