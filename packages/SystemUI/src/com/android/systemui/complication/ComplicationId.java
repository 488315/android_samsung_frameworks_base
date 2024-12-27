package com.android.systemui.complication;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComplicationId {
    public final int mId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
