package com.android.server.pm;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
