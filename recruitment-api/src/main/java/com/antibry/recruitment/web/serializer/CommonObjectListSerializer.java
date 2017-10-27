package com.antibry.recruitment.web.serializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class CommonObjectListSerializer extends StdSerializer<List<Object>> {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public CommonObjectListSerializer(Class<List<Object>> t) {
		super(t);
	}
	
	public CommonObjectListSerializer() {
		this(null);
	}

	@Override
	public void serialize(List<Object> objectList, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartArray();
		
		if (!objectList.isEmpty()) {
			Class<?> objectClass = objectList.get(0).getClass();
			Field[] fields = objectClass.getDeclaredFields();
			
			for (Object obj: objectList) {
				jgen.writeStartObject();
				
				for (Field field: fields) {
					try {
						Method method = objectClass.getMethod("get" + StringUtils.capitalize(field.getName()));
						Object value = method.invoke(obj);
						
							try {
								value.getClass().getDeclaredField("id");
								Method getId = value.getClass().getMethod("getId");
								
								jgen.writeNumberField(field.getName() + "Id", (Long) getId.invoke(value));
							} catch (NoSuchFieldException nex) { }
						
						
						String fieldClass = field.getType().toGenericString().toLowerCase();
						
						if (fieldClass.contains("string") || fieldClass.contains("char")) jgen.writeStringField(field.getName(), value.toString());
						else if (fieldClass.contains("boolean")) jgen.writeBooleanField(field.getName(), (boolean) value);
						else if (fieldClass.contains("int") || fieldClass.contains("integer")) jgen.writeNumberField(field.getName(), (int) value);
						else if (fieldClass.contains("double")) jgen.writeNumberField(field.getName(), (Double) value);
						else if (fieldClass.contains("float")) jgen.writeNumberField(field.getName(), (float) value);
						else if (fieldClass.contains("long")) jgen.writeNumberField(field.getName(), (Long) value);
						else if (fieldClass.contains("short")) jgen.writeNumberField(field.getName(), (short) value);
						else if (fieldClass.contains("byte")) jgen.writeNumberField(field.getName(), (byte) value);
						else if (fieldClass.contains("date")) jgen.writeStringField(field.getName(), format.format((Date) value));
						
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
							IllegalArgumentException | InvocationTargetException e) { } 
				}
				
				jgen.writeEndObject();
			}			
		}
		
		jgen.writeEndArray();
	}
	
}
