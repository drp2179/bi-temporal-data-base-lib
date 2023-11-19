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
package com.djpedersen.bitemporal.bitemporaldatabase.persistence;

import java.time.Instant;

import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Defines an exception for when a snapshot cannot be found by the persistence module
 * 
 * @author Daniel R. Pedersen
 */
@NoArgsConstructor
public class SnapshotNotFoundException extends TemporalPersistenceException {

	private static final long serialVersionUID = 9218708816016583451L;

	public SnapshotNotFoundException(@NonNull final String collectionName, @NonNull final String identifier) {

		super("No snapshot found for " + collectionName + " " + identifier);
	}

	public SnapshotNotFoundException(@NonNull final String collectionName, @NonNull final String identifier, final int version) {

		super("No snapshot found for " + collectionName + " " + identifier + " v" + version);
	}

	public SnapshotNotFoundException(@NonNull final String collectionName, @NonNull final String identifier, @NonNull final Instant effectiveFrom) {

		super("No snapshot found for " + collectionName + " " + identifier + " effective " + effectiveFrom);
	}

}
