package com.android.server.pm;

import java.util.List;

/* loaded from: classes3.dex */
public final class QueryIntentActivitiesResult {
    public boolean addInstant;
    public List answer;
    public List result;
    public boolean sortResult;

    public QueryIntentActivitiesResult(List list) {
        this.sortResult = false;
        this.addInstant = false;
        this.result = null;
        this.answer = list;
    }

    public QueryIntentActivitiesResult(boolean z, boolean z2, List list) {
        this.answer = null;
        this.sortResult = z;
        this.addInstant = z2;
        this.result = list;
    }
}
