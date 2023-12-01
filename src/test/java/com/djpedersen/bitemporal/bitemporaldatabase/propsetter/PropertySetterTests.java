package com.djpedersen.bitemporal.bitemporaldatabase.propsetter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.djpedersen.bitemporal.bitemporaldatabase.example.ExampleStruct;
import com.djpedersen.bitemporal.bitemporaldatabase.example.ExampleStruct.ExampleState;
import com.djpedersen.bitemporal.bitemporaldatabase.example.ExampleSubStruct;

class PropertySetterTests {

	@Test
	void setIntField_Simple() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final int initialValue = 33;
		final var objectToFix = ExampleStruct.builder().intValue(initialValue).build();

		Assertions.assertEquals(initialValue, objectToFix.getIntValue());

		final int newValue = 44;
		final String attributePath = "$.intValue";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		;
		Assertions.assertEquals(newValue, objectToFix.getIntValue(), "the wrong value was set");
	}

	@Test
	void setIntField_NullValueThrowsException() throws IllegalArgumentException, IllegalAccessException {
		final int initialValue = 33;
		final var objectToFix = ExampleStruct.builder().intValue(initialValue).build();

		final Integer newValue = null;
		final String attributePath = "$.intValue";

		Assertions.assertThrows(IllegalArgumentException.class, () -> PropertySetter.set(objectToFix, attributePath, newValue));
	}

	@Test
	void setUUIDField_Simple() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = UUID.randomUUID();
		final var objectToFix = ExampleStruct.builder().id(initialValue).build();

		Assertions.assertEquals(initialValue, objectToFix.getId());

		final var newValue = UUID.randomUUID();
		final String attributePath = "$.id";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getId(), "the wrong value was set");
	}

	@Test
	void setUUIDField_SetNull() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = UUID.randomUUID();
		final var objectToFix = ExampleStruct.builder().id(initialValue).build();

		Assertions.assertEquals(initialValue, objectToFix.getId());

		final UUID newValue = null;
		final String attributePath = "$.id";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getId(), "the wrong value was set");
	}

	@Test
	void setStateField_Simple() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = ExampleState.Working;
		final var objectToFix = ExampleStruct.builder().state(initialValue).build();

		Assertions.assertEquals(initialValue, objectToFix.getState());

		final var newValue = ExampleState.Closed;
		final String attributePath = "$.state";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getState(), "the wrong value was set");
	}

	@Test
	void setSubStateField_IntField_Simple()
			throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = 33;
		final var subStruct = ExampleSubStruct.builder().subIntValue(initialValue).build();
		final var objectToFix = ExampleStruct.builder().subStruct(subStruct).build();

		Assertions.assertEquals(initialValue, objectToFix.getSubStruct().getSubIntValue());

		final var newValue = 44;
		final String attributePath = "$.subStruct.subIntValue";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getSubStruct().getSubIntValue(), "the wrong value was set");
	}

	// TODO: PropertySetter setArrayField_Simple
	@Disabled("not fully implemented")
	@Test
	void setArrayField_Simple() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = "this is a test";
		final String[] array = { initialValue };
		final var objectToFix = ExampleStruct.builder().stringArray(array).build();

		Assertions.assertEquals(initialValue, objectToFix.getStringArray()[0]);

		final var newValue = "of the emergency broadcast system";
		final String attributePath = "$.stringArray[0]";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getStringArray()[0], "the wrong value was set");
	}

	// TODO: PropertySetter setListField_Simple
	@Disabled("not fully implemented")
	@Test
	void setListField_Simple() throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = "this is a test";
		final var list = new ArrayList<String>();
		list.add(initialValue);
		final var objectToFix = ExampleStruct.builder().stringList(list).build();

		Assertions.assertEquals(initialValue, objectToFix.getStringList().get(0));

		final var newValue = "of the emergency broadcast system";
		final String attributePath = "$.stringList[0]";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getStringList().get(0), "the wrong value was set");
	}

	@Test
	void setListObjectField_Simple()
			throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var initialValue = 33;
		final var subStruct = ExampleSubStruct.builder().subIntValue(initialValue).build();

		final var list = new ArrayList<ExampleSubStruct>();
		list.add(subStruct);
		final var objectToFix = ExampleStruct.builder().subStructList(list).build();

		Assertions.assertEquals(initialValue, objectToFix.getSubStructList().get(0).getSubIntValue());

		final var newValue = 44;
		final String attributePath = "$.subStructList[0].subIntValue";

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertTrue(wasSet, "value should have been set");
		Assertions.assertEquals(newValue, objectToFix.getSubStructList().get(0).getSubIntValue(), "the wrong value was set");
	}

	@Test
	void setSubObjectFieldAttribute_MissingSubObject()
			throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		final var objectToFix = ExampleStruct.builder().subStruct(null).build();

		final var newValue = 44;
		final String attributePath = "$.subStruct.subIntValue";

		final var originalObjectToFix = new ExampleStruct(objectToFix);

		final boolean wasSet = PropertySetter.set(objectToFix, attributePath, newValue);

		Assertions.assertFalse(wasSet, "value should NOT have been set");
		Assertions.assertEquals(originalObjectToFix, objectToFix, "object should not have been changed");
	}

}
