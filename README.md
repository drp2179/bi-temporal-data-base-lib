# Bi-Temporal Data Base Library

This library provides the foundation elements of the broader bi-temporal persistence libraries.

## Class Structure

```mermaid
classDiagram
    
    class TemporalContext {
        int version (final)
        int revision (final)
        String comment (final)
        Instant effectiveOn (final)
        Instant recordedOn (final)
    }

    class ContextHandle~IDTYPE~ {
        IDTYPE identifier (final)
        Integer version (final)
        Integer revision (final)
    }

    class TemporalStructureInterface~IDTYPE, STATE_ENUM, EVENT_ENUM~ {
        <<interface>>
        getIdentifier() IDTYPE
        getState() STATE_ENUM
        getEvent() EVENT_ENUM
        getEdition() int
    }

    class TemporalSnapshot~IDTYPE, STATE_ENUM, EVENT_ENUM, STRUCT~ {
        context TemporalContext (final)
        struct TemporalStructureInterface (final)
        contextHandle ContextHandle (final)
    }

    TemporalSnapshot --> ContextHandle
    TemporalSnapshot --> TemporalContext
    TemporalSnapshot --> TemporalStructureInterface
    
    class CorrectedPair~SNAPSHOT~ {
			originalSnapshot SNAPSHOT (final)
			correctedSnapshot SNAPSHOT (final)

			of(originalSnapshot SNAPSHOT, correctedSnapshot SNAPSHOT) CorrectedPair~SNAPSHOT~ $
    }
    
    CorrectedPair "2" --> TemporalSnapshot

    class TemporalPersistenceInterface~IDTYPE, STATE_ENUM, EVENT_ENUM, STRUCT, SNAPSHOT~ {
        <<interface>>

        createNew(struct STRUCT) SNAPSHOT
        createNew(struct STRUCT, comment String) SNAPSHOT
        createNew(struct STRUCT, effectiveOn Instant) SNAPSHOT
        createNew(struct STRUCT, effectiveOn Instant, comment String) SNAPSHOT
        
        appendVersion(struct STRUCT) SNAPSHOT
        appendVersion(struct STRUCT, comment String) SNAPSHOT
        appendVersion(struct STRUCT, effectiveOn Instant) SNAPSHOT
        appendVersion(struct STRUCT, effectiveOn Instant, comment String) SNAPSHOT
        
        correctStructByVersion(id IDTYPE, version int, structCorrectionPath String, newValue Object, reason String) CorrectedPair~SNAPSHOT~
        correctStructAllVersions(id IDTYPE, structCorrectionPath String, newValue Object, reason String) List~CorrectedPair~SNAPSHOT~~
        correctContextEffectiveOn(id IDTYPE, version int, newEffectiveOn Instant, reason String) List~CorrectedPair~SNAPSHOT~~
        
        getByIdCurrent(id IDTYPE) Optional~SNAPSHOT~
        getByIdLast(id IDTYPE) Optional~SNAPSHOT~
        getByIdEffective(id IDTYPE, effectiveOn Instant) Optional~SNAPSHOT~
        getByIdAndVersion(id IDTYPE, version int) Optional~SNAPSHOT~
        getByIdVersionAndRevision(id IDTYPE, version int, revision int) Optional~SNAPSHOT~
        getByContextHandle(contextHandle ContextHandle~IDTYPE~) Optional~SNAPSHOT~
        getAllVersions(id IDTYPE) List~SNAPSHOT~
        getAllVersions(id IDTYPE, effectiveFrom Instant, effectiveUntil Instant) List~SNAPSHOT~
        getAllVersions(id IDTYPE, startingVersion int, endingVersion int) List~SNAPSHOT~
        getAllVersionsAndRevisions(id IDTYPE) List~SNAPSHOT~
        getAllVersionsAndRevisions(id IDTYPE, effectiveFrom Instant, effectiveUntil Instant) List~SNAPSHOT~
        getAllVersionsAndRevisions(id IDTYPE, startingVersion int, endingVersion int) List~SNAPSHOT~
        
        getLastInstant() Instant
    }
    
    TemporalPersistenceInterface --> TemporalSnapshot
    TemporalPersistenceInterface --> CorrectedPair
    
    class TemporalPersistenceException {
    	<<Exception>>
    }

```