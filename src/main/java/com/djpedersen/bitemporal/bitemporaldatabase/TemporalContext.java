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

import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * The defined context of a temporal structure.
 * 
 * @author Daniel R. Pedersen
 */
@ToString
@EqualsAndHashCode
public class TemporalContext {

	/**
	 * The present version of the snapshot, starting with 1.
	 */
	public final int version;

	/**
	 * The present revision of the snapshot, starting with 0.
	 */
	public final int revision;

	/**
	 * When the Snapshot is effective
	 */
	@NonNull
	public final Instant effectiveFrom;

	/**
	 * When the Snapshot was recorded
	 */
	@NonNull
	public final Instant recordedOn;

	/**
	 * An optional comment with why the Snapshot was created
	 */
	public final String comment;

	/**
	 * Primary set everything constructor
	 * 
	 * @param effectiveFrom when the context is effective
	 * @param version       the version of the context
	 * @param revision      the revision of the version of the context
	 * @param comment       (optional) a comment about the context
	 * @param recordedOn    when the context was recorded
	 */
	public TemporalContext(@NonNull final Instant effectiveFrom, final int version, final int revision, final String comment,
			@NonNull final Instant recordedOn) {
		this.effectiveFrom = effectiveFrom;
		this.version = version;
		this.revision = revision;
		this.comment = comment;
		this.recordedOn = recordedOn;
	}

	/**
	 * Simple constructor creates a context effective now, version 1, revision 0, no comment, recorded now
	 */
	public TemporalContext() {
		this(Instant.now(), 1, 0, null, Instant.now());
	}

	/**
	 * Simple constructor creates a context effective as specified, version 1, revision 0, no comment, recorded now
	 * 
	 * @param effectiveFrom when the context is effective
	 */
	public TemporalContext(@NonNull final Instant effectiveFrom) {
		this(effectiveFrom, 1, 0, null, Instant.now());
	}

	/**
	 * Simple constructor creates a context effective now, version 1, revision 0, comment as specified, recorded now
	 * 
	 * @param comment (optional) a comment about the context
	 */
	public TemporalContext(final String comment) {
		this(Instant.now(), 1, 0, comment, Instant.now());
	}

	/**
	 * Create context effective as specified, version 1, revision 0, no comment, recorded as specified
	 * 
	 * @param effectiveFrom when the context is effective
	 * @param recordedOn    when the context was recorded
	 */
	public TemporalContext(@NonNull final Instant effectiveFrom, @NonNull final Instant recordedOn) {
		this(effectiveFrom, 1, 0, null, recordedOn);
	}

	/**
	 * Create a context effective as specified, version 1, revision 0, comment as specified, recorded now
	 *
	 * @param effectiveFrom when the context is effective
	 * @param comment       (optional) a comment about the context
	 */
	public TemporalContext(@NonNull final Instant effectiveFrom, final String comment) {
		this(effectiveFrom, 1, 0, comment, Instant.now());
	}

	/**
	 * Create a context effective as specified, version 1, revision 0, comment as specified, recorded as specified
	 *
	 * @param effectiveFrom when the context is effective
	 * @param comment       (optional) a comment about the context
	 * @param recordedOn    when the context was recorded
	 */
	public TemporalContext(@NonNull final Instant effectiveFrom, final String comment, @NonNull final Instant recordedOn) {
		this(effectiveFrom, 1, 0, comment, recordedOn);
	}

	/**
	 * Copy the provided context
	 * 
	 * @param src the context to copy
	 */
	public TemporalContext(@NonNull final TemporalContext src) {
		this(src.effectiveFrom, src.version, src.revision, src.comment, src.recordedOn);
	}

	/**
	 * Create the next version of this TemporalContext.
	 * 
	 * @return the next version of this TemporalContext
	 */
	public TemporalContext createNextVersion() {
		return new TemporalContext(Instant.now(), this.version + 1, 0, null, Instant.now());
	}

	/**
	 * Create the next version of this TemporalContext with the provided effective date.
	 * 
	 * @param effectiveFrom when the version is effective.
	 * @return the next version of this TemporalContext
	 */
	public TemporalContext createNextVersion(@NonNull final Instant effectiveFrom) {
		return new TemporalContext(effectiveFrom, this.version + 1, 0, null, Instant.now());
	}

	/**
	 * Create the next version of this TemporalContext with the provided effective date and comment
	 * 
	 * @param effectiveFrom when the version is effective.
	 * @param comment       optional comment about the context
	 * @return the next version of this TemporalContext
	 */
	public TemporalContext createNextVersion(@NonNull final Instant effectiveFrom, final String comment) {
		return new TemporalContext(effectiveFrom, this.version + 1, 0, comment, Instant.now());
	}

	/**
	 * Create the next revision of the version of this TemporalContext.
	 * 
	 * @return the next revision of this TemporalContext
	 */
	public TemporalContext createNextRevision() {
		return new TemporalContext(this.effectiveFrom, this.version, this.revision + 1, null, Instant.now());
	}

	/**
	 * Create the next revision of the version of this TemporalContext with a comment.
	 * 
	 * @param comment optional comment about the context
	 * @return the next revision of this TemporalContext
	 */
	public TemporalContext createNextRevision(final String comment) {
		return new TemporalContext(this.effectiveFrom, this.version, this.revision + 1, comment, Instant.now());
	}

}
