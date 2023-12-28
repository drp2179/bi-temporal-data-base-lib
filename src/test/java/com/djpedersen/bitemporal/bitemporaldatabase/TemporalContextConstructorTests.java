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
class TemporalContextConstructorTests {

	@Test
	void defaultConstructor() {
		final var temporalContext = new TemporalContext();

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertNotNull(temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertNotNull(temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(temporalContext.comment, "comment is wrong");
	}

	@Test
	void effectiveConstructor() {
		final var now = Instant.now();

		final var temporalContext = new TemporalContext(now);

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertEquals(now, temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertNotNull(temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(temporalContext.comment, "comment is wrong");
	}

	@Test
	void commentConstructor() {
		final var comment = "a comment";

		final var temporalContext = new TemporalContext(comment);

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertNotNull(temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertNotNull(temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertEquals(comment, temporalContext.comment, "comment is wrong");
	}

	@Test
	void effectiveCommentConstructor() {
		final var now = Instant.now();
		final var comment = "a comment";

		final var temporalContext = new TemporalContext(now, comment);

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertEquals(now, temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertNotNull(temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertEquals(comment, temporalContext.comment, "comment is wrong");
	}

	@Test
	void effectiveRecordedConstructor() {
		final var now = Instant.now();
		final var recorded = Instant.now().minus(3, ChronoUnit.MINUTES);

		final var temporalContext = new TemporalContext(now, recorded);

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertEquals(now, temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertEquals(recorded, temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertNull(temporalContext.comment, "comment is wrong");
	}

	@Test
	void effectiveCommentRecordedConstructor() {
		final var now = Instant.now();
		final var comment = "a comment";
		final var recorded = Instant.now().minus(3, ChronoUnit.MINUTES);

		final var temporalContext = new TemporalContext(now, comment, recorded);

		Assertions.assertEquals(1, temporalContext.version, "version is wrong");
		Assertions.assertEquals(0, temporalContext.revision, "revision is wrong");
		Assertions.assertEquals(now, temporalContext.effectiveFrom, "effectiveFrom is wrong");
		Assertions.assertEquals(recorded, temporalContext.recordedOn, "recordedOn is wrong");
		Assertions.assertEquals(comment, temporalContext.comment, "comment is wrong");
	}

}
