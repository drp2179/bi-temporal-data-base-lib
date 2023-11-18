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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A generic class which provides a convenience to identify a specific revision
 * of a version of a bi-temporal structure. One could store a context handle
 * instead of an identifier to better identify the construct which caused a
 * change in state.
 * 
 * @author Daniel R. Pedersen
 * 
 * @param <IDTYPE> the type of the identifier
 */
@ToString
@EqualsAndHashCode
@Builder
public class ContextHandle<IDTYPE> {

	/**
	 * The identifier of the handle
	 */
	public final IDTYPE identifier;

	/**
	 * The version of the handle
	 */
	public final Integer version;

	/**
	 * The revision of the handle
	 */
	public final Integer revision;

	/**
	 * Create a complete/full context handle
	 * 
	 * @param identifier the identifier of the handle
	 * @param version    the version of the handle
	 * @param revision   the revision of the handle
	 */
	public ContextHandle(final IDTYPE identifier, final int version, final int revision) {
		this.identifier = identifier;
		this.version = version;
		this.revision = revision;
	}

	/**
	 * Create an identifier only context handle
	 * 
	 * @param identifier the identifier of the handle
	 */
	protected ContextHandle(final IDTYPE identifier) {
		this.identifier = identifier;
		this.version = null;
		this.revision = null;
	}

	/**
	 * Create a versioned context handle. This is useful for referencing the most
	 * recent revision of a version of a bi-temporal record.
	 * 
	 * @param identifier the identifier of the handle
	 * @param version    the version of the handle
	 */
	protected ContextHandle(final IDTYPE identifier, final int version) {
		this.identifier = identifier;
		this.version = version;
		this.revision = null;
	}

	/**
	 * Create an identity only context handle from this context handle
	 * 
	 * @return an identity only context handle based on this context handle
	 */
	public ContextHandle<IDTYPE> createIndentityContextHandle() {
		return new ContextHandle<IDTYPE>(this.identifier);
	}

	/**
	 * Create a versioned context handle from this context handle. This will fail if
	 * converting an identity only handle.
	 * 
	 * @return a versioned context handle based on this context handle
	 */
	public ContextHandle<IDTYPE> createVersionedContextHandle() {
		if (this.version == null) {
			throw new IllegalStateException(
					"Cannot create a versioned context handle from an identity only context handle");
		}

		return new ContextHandle<IDTYPE>(this.identifier, this.version);
	}

}
