package com.djpedersen.bitemporal.bitemporaldatabase.propsetter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.NonNull;

/**
 * Path Format:
 * 
 * Simple properties: $.attribute
 * 
 * Arrays/Sets/Lists: $.attribute[index] (does not yet work as the last node in a path)
 * 
 * Sub-object, simple property: $.subObject.subObjectAttribute
 * 
 * Maps: not yet implemented
 */
public class PropertySetter {

	/**
	 * Set a property on the provided object based on the attributePath to the provided new value. If any part of the attributePath
	 * does not match an attribute in the associated object then no change is affected.
	 * 
	 * N.B. one can extend an Array, Set or List by using the next logical index. (this is not yet implemented)
	 * 
	 * @param objectToFix   the object to traverse and set
	 * @param attributePath the path to the attribute to set, must start with "$."
	 * @param newValue      the new value (can be null, if allowed by the property)
	 * @throws IllegalArgumentException  if there is a problem
	 * @throws IllegalAccessException    if there is a problem
	 * @throws NoSuchMethodException     if there is a problem
	 * @throws SecurityException         if there is a problem
	 * @throws InvocationTargetException if there is a problem
	 */
	public static void set(final Object objectToFix, @NonNull final String attributePath, final Object newValue)
			throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {

		if (objectToFix == null) {
			return;
		} else if (!attributePath.startsWith("$.")) {
			throw new IllegalArgumentException("'attributePath' parameter must start with $.");
		}

		final var pathNames = attributePath.split("\\.");

		Field attribute = null;
		Object currentObject = objectToFix;

		for (int i = 1; pathNames != null && i < pathNames.length; i++) {
			var attributeName = pathNames[i];
//			System.out.println("attributeName:" + attributeName);

			final int arrayStart = attributeName.indexOf("[");
			final int arrayEnd = attributeName.indexOf("]");

			int arrayIndex = 0;
			boolean isArrayPath = false;

			if (arrayStart > 0) {
				final String arrayIndexStr = attributeName.substring(arrayStart + 1, arrayEnd);
				arrayIndex = Integer.parseInt(arrayIndexStr);
				attributeName = attributeName.substring(0, arrayStart);
				isArrayPath = true;
//				System.out.println("isArrayPath, index: " + arrayIndex);
			}

			attribute = getFieldByName(currentObject, attributeName);

			if (attribute == null) {
//				System.out.println("no attribute found named " + attributeName);
				break;
			}

			if (isArrayPath) {
				if (attribute.getDeclaringClass().isArray()) {
//					System.out.println("attribute is an Array");
					attribute.setAccessible(true);
					final var array = attribute.get(currentObject);
					currentObject = Array.get(array, arrayIndex);
				} else if (isListType(attribute) || isSetType(attribute)) {
//					System.out.println("attribute is a List or Set");
					attribute.setAccessible(true);
					final var list = attribute.get(currentObject);
					final Method method = list.getClass().getDeclaredMethod("get", int.class);
					currentObject = method.invoke(list, arrayIndex);
				} else {
//					System.out.println("attribute is not an Array, List or Set: " + attribute.getType());
				}
			} else if (i < pathNames.length - 1) {
				attribute.setAccessible(true);
				currentObject = attribute.get(currentObject);
			}
		}

		if (attribute != null) {
//			System.out.println("Setting attribute " + attribute.getName() + " to: " + newValue);
			attribute.setAccessible(true);
			attribute.set(currentObject, newValue);
		}
	}

	private static boolean isListType(Field field) {
		return List.class.isAssignableFrom(field.getType());
//		
//		if (List.class.isAssignableFrom(field.getType())) {
//			Type genericType = field.getGenericType();
//			if (genericType instanceof ParameterizedType) {
//				Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
//				// Check if the actual type argument is not empty
//				return actualTypeArguments.length > 0;
//			}
//		}
//		return false;
	}

	private static boolean isSetType(Field field) {
		return Set.class.isAssignableFrom(field.getType());
//
//		if (Set.class.isAssignableFrom(field.getType())) {
//			Type genericType = field.getGenericType();
//			if (genericType instanceof ParameterizedType) {
//				Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
//				// Check if the actual type argument is not empty
//				return actualTypeArguments.length > 0;
//			}
//		}
//		return false;
	}

	private static Field getFieldByName(final Object object, final String attributeName) {
		final Field[] fields = object.getClass().getDeclaredFields();

		if (fields == null) {
			return null;
		}

		return Arrays.stream(fields).filter(f -> f.getName().equals(attributeName)).findAny().orElse(null);
	}
}
