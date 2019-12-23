package dev.morphia.aggregation.experimental.stages;

import dev.morphia.query.Query;

public class Match implements Stage {
    private Query query;

    protected Match(final Query query) {
        this.query = query;
    }

    public static Match of(final Query<?> query) {
        return new Match(query);
    }
}