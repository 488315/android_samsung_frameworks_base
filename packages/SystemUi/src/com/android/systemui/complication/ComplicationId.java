package com.android.systemui.complication;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationId {
    public final int mId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public int mNextId;
    }

    public /* synthetic */ ComplicationId(int i, int i2) {
        this(i);
    }

    public final String toString() {
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("ComplicationId{mId="), this.mId, "}");
    }

    private ComplicationId(int i) {
        this.mId = i;
    }
}
