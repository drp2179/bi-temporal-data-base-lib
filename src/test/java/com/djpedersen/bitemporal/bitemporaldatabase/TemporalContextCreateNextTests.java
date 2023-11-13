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
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Daniel R. Pedersen
 */
class TemporalContextCreateNextTests {

	@Test
	void createNextVersion() {
		final var temporalContext = new TemporalContext();

		final var nextVersionContext = temporalContext.createNextVersion();

		Assertions.assertEquals(2, nextVersionContext.version, "version is wrong");
		Assertions.assertEquals(0, nextVersionContext.revision, "revision is wrong");
		Assertions.assertNotNull(nextVersionContext.effectiveOn, "effectiveFrom is wrong");
		Assertions.assertNotNull(nextVersionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(nextVersionContext.comment, "comment is wrong");
	}

	@Test
	void createNextVersionEffective() {
		final var temporalContext = new TemporalContext();

		final var now = Instant.now().plus(1, ChronoUnit.MINUTES);

		final var nextVersionContext = temporalContext.createNextVersion(now);

		Assertions.assertEquals(2, nextVersionContext.version, "version is wrong");
		Assertions.assertEquals(0, nextVersionContext.revision, "revision is wrong");
		Assertions.assertEquals(now, nextVersionContext.effectiveOn, "effectiveFrom is wrong");
		Assertions.assertNotNull(nextVersionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(nextVersionContext.comment, "comment is wrong");
	}

	@Test
	void createNextVersionEffectiveComment() {
		final var temporalContext = new TemporalContext();

		final var now = Instant.now().plus(1, ChronoUnit.MINUTES);
		final var comment = "a comment";

		final var nextVersionContext = temporalContext.createNextVersion(now, comment);

		Assertions.assertEquals(2, nextVersionContext.version, "version is wrong");
		Assertions.assertEquals(0, nextVersionContext.revision, "revision is wrong");
		Assertions.assertEquals(now, nextVersionContext.effectiveOn, "effectiveFrom is wrong");
		Assertions.assertNotNull(nextVersionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertEquals(comment, nextVersionContext.comment, "comment is wrong");
	}

	@Test
	void createNextRevision() {
		final var temporalContext = new TemporalContext();

		final var nextRevisionContext = temporalContext.createNextRevision();

		Assertions.assertEquals(1, nextRevisionContext.version, "version is wrong");
		Assertions.assertEquals(1, nextRevisionContext.revision, "revision is wrong");
		Assertions.assertEquals(temporalContext.effectiveOn, nextRevisionContext.effectiveOn,
				"effectiveFrom is wrong");
		Assertions.assertNotNull(nextRevisionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(nextRevisionContext.comment, "comment is wrong");
	}

	@Test
	void createNextRevisionComment() {
		final var temporalContext = new TemporalContext();

		final var comment = "a comment";

		final var nextRevisionContext = temporalContext.createNextRevision(comment);

		Assertions.assertEquals(1, nextRevisionContext.version, "version is wrong");
		Assertions.assertEquals(1, nextRevisionContext.revision, "revision is wrong");
		Assertions.assertEquals(temporalContext.effectiveOn, nextRevisionContext.effectiveOn,
				"effectiveFrom is wrong");
		Assertions.assertNotNull(nextRevisionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertEquals(comment, nextRevisionContext.comment, "comment is wrong");
	}

	@Test
	void createNextRevisionOfNextVersion() {
		final var temporalContext = new TemporalContext();

		final var nextVersionContext = temporalContext.createNextVersion();

		final var nextVersionRevisionContext = nextVersionContext.createNextRevision();

		Assertions.assertEquals(2, nextVersionRevisionContext.version, "version is wrong");
		Assertions.assertEquals(1, nextVersionRevisionContext.revision, "revision is wrong");
		Assertions.assertEquals(nextVersionContext.effectiveOn, nextVersionRevisionContext.effectiveOn,
				"effectiveFrom is wrong");
		Assertions.assertNotNull(nextVersionRevisionContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(nextVersionRevisionContext.comment, "comment is wrong");
	}
}
