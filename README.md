# Bi-Temporal Data Base Library

This library provides the foundation elements of the broader bi-temporal persistence libraries.

## Class Structure

```mermaid
classDiagram
    
    class TemporalContext {
        int version
        int revision
        String comment
        Instant effectiveOn
        Instant recordedOn
    }

    class ContextHandle~IDTYPE~ {
        IDTYPE identifier
        Integer version
        Integer revision
    }

    class TemporalStructureInterface~IDTYPE, STATE_ENUM, EVENT_ENUM~ {
        <<interface>>
        getIdentifier() IDTYPE
        getState() STATE_ENUM
        getEvent() EVENT_ENUM
    }

    class Snapshot~IDTYPE, STATE_ENUM, EVENT_ENUM, STRUCT~ {
        context TemporalContext
        struct TemporalStructureInterface
        contextHandle ContextHandle
    }

    Snapshot --> ContextHandle
    Snapshot --> TemporalContext
    Snapshot --> TemporalStructureInterface

    class TemporalPersistenceInterface~IDTYPE, STATE_ENUM, EVENT_ENUM, STRUCT, SNAPSHOT~ {
        <<interface>>

        createNew(struct STRUCT) SNAPSHOT
        appendVersion(struct STRUCT) SNAPSHOT
        getByIdCurrent(id IDTYPE) Optional~SNAPSHOT~
        getByIdLast(id IDTYPE) Optional~SNAPSHOT~
        getByIdEffective(id IDTYPE, effectiveOn Instant) Optional~SNAPSHOT~
    }

    TemporalPersistenceInterface --> Snapshot

```