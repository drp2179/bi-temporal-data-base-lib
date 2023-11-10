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
package com.djpedersen.bitemporal.bitemporaldatabase;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.djpedersen.bitemporal.bitemporaldatabase.example.ExampleSnapshot;
import com.djpedersen.bitemporal.bitemporaldatabase.example.ExampleStruct;

/**
 * @author Daniel R. Pedersen
 */
class TemporalSnapshotTests {

	@Test
	void getContextHandle() {
		final var id = UUID.randomUUID();
		final var struct = ExampleStruct.builder().id(id).build();
		final var context = new TemporalContext().createNextVersion().createNextRevision();
		final var snapshot = new ExampleSnapshot(context, struct);

		Assertions.assertEquals(id, snapshot.contextHandle.identifier, "identifier is wrong");
		Assertions.assertEquals(context.version, snapshot.contextHandle.version, "version is wrong");
		Assertions.assertEquals(context.revision, snapshot.contextHandle.revision, "revision is wrong");
	}
}
