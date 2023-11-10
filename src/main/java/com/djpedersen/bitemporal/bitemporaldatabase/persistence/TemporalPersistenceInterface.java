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
import java.util.List;
import java.util.Optional;

import com.djpedersen.bitemporal.bitemporaldatabase.ContextHandle;
import com.djpedersen.bitemporal.bitemporaldatabase.TemporalSnapshot;
import com.djpedersen.bitemporal.bitemporaldatabase.TemporalStructureInterface;

import lombok.NonNull;

/**
 * Defines the persistence operations required of any implementation of temporal
 * persistence.
 * 
 * @author Daniel R. Pedersen
 * 
 * @param <IDTYPE>   the type of the structure's identifier
 * @param <STATE>    the type of the structure's state enum
 * @param <EVENT>    the type of the structure's event enum
 * @param <STRUCT>   the type of the structure
 * @param <SNAPSHOT> the type of the structure's snapsho
 */
public interface TemporalPersistenceInterface<IDTYPE, STATE extends Enum<?>, EVENT extends Enum<?>, STRUCT extends TemporalStructureInterface<IDTYPE, STATE, EVENT>, SNAPSHOT extends TemporalSnapshot<IDTYPE, STATE, EVENT, STRUCT>> {

	/**
	 * 
	 * @return the last instant that's valid for the persistence system
	 */
	Instant getLastInstant();

	//
	// Create New
	//

	/**
	 * Create the first version and revision for the provided temporal structure
	 * effective now
	 * 
	 * @param struct the structure to save
	 * @return the snapshot created
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default SNAPSHOT createNew(@NonNull final STRUCT struct) throws TemporalPersistenceException {
		return createNew(struct, Instant.now(), null);
	}

	/**
	 * Create the first version and revision for the provided temporal structure
	 * effective now with the provided comment
	 * 
	 * @param struct  the structure to save
	 * @param comment the comment to place in the temporal context
	 * @return the snapshot created
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default SNAPSHOT createNew(@NonNull final STRUCT struct, final String comment) throws TemporalPersistenceException {
		return createNew(struct, Instant.now(), comment);
	}

	/**
	 * Create the first version and revision for the provided temporal structure
	 * effective the provided instant
	 * 
	 * @param struct        the structure to save
	 * @param effectiveFrom when the snapshot is effective
	 * @return the snapshot created
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default SNAPSHOT createNew(@NonNull final STRUCT struct, @NonNull final Instant effectiveFrom)
			throws TemporalPersistenceException {
		return createNew(struct, effectiveFrom, null);
	}

	/**
	 * Create the first version and revision for the provided temporal structure
	 * effective the provided instant with the provided comment
	 * 
	 * @param struct        the structure to save
	 * @param effectiveFrom when the snapshot is effective
	 * @param comment       the comment to place in the temporal context
	 * @return the snapshot created
	 * @throws TemporalPersistenceException if there is a problem
	 */
	SNAPSHOT createNew(@NonNull final STRUCT struct, @NonNull final Instant effectiveFrom, String comment)
			throws TemporalPersistenceException;

	//
	// Append Version
	//

	/**
	 * Create the next version of the provided temporal structure effective now
	 * 
	 * @param struct the structure to save
	 * @return the snapshot created
	 * @throws TemporalPersistenceException
	 */
	default SNAPSHOT appendVersion(@NonNull final STRUCT struct) throws TemporalPersistenceException {
		return appendVersion(struct, Instant.now(), null);
	}

	/**
	 * Create the next version of the provided temporal structure effective now with
	 * the provided comment
	 * 
	 * @param struct  the structure to save
	 * @param comment the comment to place in the temporal context
	 * @return the snapshot created
	 * @throws TemporalPersistenceException
	 */
	default SNAPSHOT appendVersion(@NonNull final STRUCT struct, final String comment)
			throws TemporalPersistenceException {
		return appendVersion(struct, Instant.now(), comment);
	}

	/**
	 * Create the next version of the provided temporal structure effective the
	 * provided instant
	 * 
	 * @param struct        the structure to save
	 * @param effectiveFrom when the new version is effective
	 * @return the snapshot created
	 * @throws TemporalPersistenceException
	 */
	default SNAPSHOT appendVersion(@NonNull STRUCT struct, @NonNull Instant effectiveFrom)
			throws TemporalPersistenceException {
		return appendVersion(struct, effectiveFrom, null);
	}

	/**
	 * Create the next version of the provided temporal structure effective the
	 * provided instant and comment
	 * 
	 * @param struct        the structure to save
	 * @param effectiveFrom when the new version is effective
	 * @param comment       the comment to place in the temporal context
	 * @return the snapshot created
	 * @throws TemporalPersistenceException
	 */
	SNAPSHOT appendVersion(@NonNull final STRUCT struct, @NonNull final Instant effectiveFrom, final String comment)
			throws TemporalPersistenceException;

	//
	// Query by Id
	//

	/**
	 * Get the latest revision of the version of the snapshot for the id effective
	 * "now"
	 * 
	 * @param id the id to search for
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default Optional<SNAPSHOT> getByIdCurrent(@NonNull final IDTYPE id) throws TemporalPersistenceException {
		return getByIdEffective(id, Instant.now());
	}

	/**
	 * Get the latest revision of the version of the snapshot for the id effective
	 * at the end of time.
	 * 
	 * @param id the id to search for
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default Optional<SNAPSHOT> getByIdLast(@NonNull final IDTYPE id) throws TemporalPersistenceException {
		return this.getByIdEffective(id, this.getLastInstant());
	}

	/**
	 * Get the snapshot for the provided id and effective date
	 * 
	 * @param id          the id to search for
	 * @param effectiveOn when the snapshot was effective
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	Optional<SNAPSHOT> getByIdEffective(@NonNull final IDTYPE id, @NonNull final Instant effectiveOn)
			throws TemporalPersistenceException;

	/**
	 * Get the most recent revision of the snapshot for the provided id and version
	 * 
	 * @param id      the id to search for
	 * @param version the version to search for
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	Optional<SNAPSHOT> getByIdAndVersion(@NonNull final IDTYPE id, final int version)
			throws TemporalPersistenceException;

	/**
	 * Get the specific revision of the version and id of the snapshot
	 * 
	 * @param id       the id to search for
	 * @param version  the version to search for
	 * @param revision the revision of the version to search for
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	Optional<SNAPSHOT> getByIdVersionAndRevision(@NonNull final IDTYPE id, final int version, final int revision)
			throws TemporalPersistenceException;

	/**
	 * If an identity only context handle, returns the current effective revision
	 * 
	 * If a version only context handle, returns the latest revision of the snapshot
	 * for the version.
	 * 
	 * If a fully specified context handle, returns the exact revision and version
	 * of the snapshot.
	 * 
	 * @param contextHandle the context handle to query for
	 * @return the snapshot if found
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default Optional<SNAPSHOT> getByContextHandle(@NonNull final ContextHandle<IDTYPE> contextHandle)
			throws TemporalPersistenceException {
		if (contextHandle.revision == null) {
			if (contextHandle.version == null) {
				return this.getByIdCurrent(contextHandle.identifier);
			}

			return this.getByIdAndVersion(contextHandle.identifier, contextHandle.version);
		}

		return this.getByIdVersionAndRevision(contextHandle.identifier, contextHandle.version, contextHandle.revision);
	}

	//
	// Version History
	//

	/**
	 * Return the list of all versions and revisions for the specified id. List is
	 * returned in reverse version and revision order.
	 * 
	 * @param id the id to search for
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default List<SNAPSHOT> getAllVersionsAndRevisions(@NonNull final IDTYPE id) throws TemporalPersistenceException {
		return this.getAllVersions(id, 0, Integer.MAX_VALUE);
	}

	/**
	 * Return the list of all versions and revisions between the specified effective
	 * times. List is returned in reverse version and revision order.
	 * 
	 * @param id             the id to search for
	 * @param effectiveFrom  optional inclusive starting timestamp, null implies the
	 *                       beginning of time
	 * @param effectiveUntil optional exclusive ending timestamp, null implies the
	 *                       end of time
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	List<SNAPSHOT> getAllVersionsAndRevisions(@NonNull final IDTYPE id, final Instant effectiveFrom,
			final Instant effectiveUntil) throws TemporalPersistenceException;

	/**
	 * Return the list of all versions and revisions between the specified versions.
	 * List is returned in reverse version and revision order.
	 * 
	 * @param id              the id to search for
	 * @param startingVersion the inclusive starting version
	 * @param endingVersion   the exclusive ending version
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	List<SNAPSHOT> getAllVersionsAndRevisions(@NonNull final IDTYPE id, final int startingVersion,
			final int endingVersion) throws TemporalPersistenceException;

	/**
	 * Return the list of the most recent revision of all versions for an id.
	 * 
	 * @param id the id to search for
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	default List<SNAPSHOT> getAllVersions(@NonNull final IDTYPE id) throws TemporalPersistenceException {
		return this.getAllVersions(id, 0, Integer.MAX_VALUE);
	}

	/**
	 * Return the list of the most recent revision of all versions for an id between
	 * the specified effective times.
	 * 
	 * @param id             the id to search for
	 * @param effectiveFrom  optional inclusive starting timestamp, null implies the
	 *                       beginning of time
	 * @param effectiveUntil optional exclusive ending timestamp, null implies the
	 *                       end of time
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	List<SNAPSHOT> getAllVersions(@NonNull final IDTYPE id, final Instant effectiveFrom, final Instant effectiveUntil)
			throws TemporalPersistenceException;

	/**
	 * Return the list of the most recent revision of all versions between the
	 * specified versions. List is returned in reverse version and revision order.
	 * 
	 * @param id              the id to search for
	 * @param startingVersion the inclusive starting version
	 * @param endingVersion   the exclusive ending version
	 * @return the list of matching snapshots, may be empty
	 * @throws TemporalPersistenceException if there is a problem
	 */
	List<SNAPSHOT> getAllVersions(@NonNull final IDTYPE id, final int startingVersion, final int endingVersion)
			throws TemporalPersistenceException;

}