package com.android.wm.shell.compatui.letterbox;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxKey {
    public final int displayId;
    public final int taskId;

    public LetterboxKey(int i, int i2) {
        this.displayId = i;
        this.taskId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LetterboxKey)) {
            return false;
        }
        LetterboxKey letterboxKey = (LetterboxKey) obj;
        return this.displayId == letterboxKey.displayId && this.taskId == letterboxKey.taskId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.taskId) + (Integer.hashCode(this.displayId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LetterboxKey(displayId=");
        sb.append(this.displayId);
        sb.append(", taskId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, ")", sb);
    }
}
