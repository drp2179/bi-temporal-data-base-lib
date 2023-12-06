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

/**
 * A generic interface which defines the minimum temporal information of a temporal structure.
 * 
 * @author Daniel R. Pedersen
 * 
 * @param <IDTYPE> the type of the structure's identifier
 * @param <STATE>  the type of the structure's state enum
 * @param <EVENT>  the type of the structure's event enum
 */
public interface TemporalStructureInterface<IDTYPE, STATE extends Enum<?>, EVENT extends Enum<?>> {

	/**
	 * Get the identifier for this structure.
	 * 
	 * @return the identifier of this structure
	 */
	IDTYPE getIdentifier();

	/**
	 * Get the present state of this structure.
	 * 
	 * @return the current state
	 */
	STATE getState();

	/**
	 * Get the event that caused this structure
	 * 
	 * @return the event
	 */
	EVENT getEvent();

	/**
	 * What edition of this struct are we looking at?
	 * 
	 * @return 1 based edition number
	 */
	int getEdition();

}
