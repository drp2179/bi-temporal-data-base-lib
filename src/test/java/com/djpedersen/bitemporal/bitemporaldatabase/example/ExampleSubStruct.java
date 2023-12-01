package com.djpedersen.bitemporal.bitemporaldatabase.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleSubStruct {

	private int subIntValue;

	public ExampleSubStruct(@NonNull final ExampleSubStruct src) {
		this.subIntValue = src.subIntValue;
	}
}
