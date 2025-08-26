package com.android.systemui.complication;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public class ComplicationId {
    public final int mId;

    public class Factory {
        public int mNextId;
    }

    public /* synthetic */ ComplicationId(int i, int i2) {
        this(i);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mId, "}", new StringBuilder("ComplicationId{mId="));
    }

    private ComplicationId(int i) {
        this.mId = i;
    }
}
