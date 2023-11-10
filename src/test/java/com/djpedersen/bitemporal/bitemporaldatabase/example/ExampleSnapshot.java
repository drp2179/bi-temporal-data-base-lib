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

import java.util.UUID;

import com.djpedersen.bitemporal.bitemporaldatabase.TemporalContext;
import com.djpedersen.bitemporal.bitemporaldatabase.TemporalSnapshot;

import lombok.NonNull;

/**
 * @author Daniel R. Pedersen
 */
public class ExampleSnapshot
		extends TemporalSnapshot<UUID, ExampleStruct.ExampleState, ExampleStruct.ExampleEvent, ExampleStruct> {

	public ExampleSnapshot(@NonNull ExampleStruct struct) {
		super(struct);
	}

	public ExampleSnapshot(@NonNull final TemporalContext temporalContext, @NonNull final ExampleStruct struct) {
		super(temporalContext, struct);
	}

}
