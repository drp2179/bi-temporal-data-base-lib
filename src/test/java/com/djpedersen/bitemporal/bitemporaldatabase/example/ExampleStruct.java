/*
 * Copyright 2023 Daniel R. Pedersen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.djpedersen.bitemporal.bitemporaldatabase.example;

import java.util.List;
import java.util.UUID;

import com.djpedersen.bitemporal.bitemporaldatabase.TemporalStructureInterface;

import lombok.Builder;
import lombok.Data;

/**
 * @author Daniel R. Pedersen
 */
@Data
@Builder
public class ExampleStruct implements TemporalStructureInterface<UUID, ExampleStruct.ExampleState, ExampleStruct.ExampleEvent> {

	public static enum ExampleState {
		Working, Closed
	}

	public static enum ExampleEvent {
		Create, Close
	}

	private UUID id;
	private int intValue;

	private List<String> stringList;
	private String[] stringArray;
	private List<ExampleSubStruct> subStructList;

	private ExampleSubStruct subStruct;

	private ExampleState state;
	private ExampleEvent event;

	@Override
	public UUID getIdentifier() {
		return this.id;
	}

	@Override
	public ExampleState getState() {
		return this.state;
	}

	@Override
	public ExampleEvent getEvent() {
		return this.event;
	}

}
