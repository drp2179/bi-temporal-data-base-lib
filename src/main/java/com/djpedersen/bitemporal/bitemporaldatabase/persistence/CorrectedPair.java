package com.djpedersen.bitemporal.bitemporaldatabase.persistence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CorrectedPair<SNAPSHOT> {

	public final SNAPSHOT originalSnapshot;
	public final SNAPSHOT correctedSnapshot;

	public static <SNAPSHOT> CorrectedPair<SNAPSHOT> of(@NonNull final SNAPSHOT originalSnapshot, @NonNull final SNAPSHOT correctedSnapshot) {
		return new CorrectedPair<>(originalSnapshot, correctedSnapshot);
	}
}
