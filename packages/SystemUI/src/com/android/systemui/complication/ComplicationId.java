package com.android.systemui.complication;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

public final class ComplicationId {
    public final int mId;

    public final class Factory {
        public int mNextId;
    }

    public /* synthetic */ ComplicationId(int i, int i2) {
        this(i);
    }

    public final String toString() {
        return Anchor$$ExternalSyntheticOutline0.m(this.mId, "}", new StringBuilder("ComplicationId{mId="));
    }

    private ComplicationId(int i) {
        this.mId = i;
    }
}
