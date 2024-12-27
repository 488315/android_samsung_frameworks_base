package com.android.server.pm;

import java.util.List;

public final class QueryIntentActivitiesResult {
    public final boolean addInstant;
    public final List answer;
    public List result;
    public final boolean sortResult;

    public QueryIntentActivitiesResult(List list) {
        this.sortResult = false;
        this.addInstant = false;
        this.result = null;
        this.answer = list;
    }

    public QueryIntentActivitiesResult(List list, boolean z, boolean z2) {
        this.answer = null;
        this.sortResult = z;
        this.addInstant = z2;
        this.result = list;
    }
}
