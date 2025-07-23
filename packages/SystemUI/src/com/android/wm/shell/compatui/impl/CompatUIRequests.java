package com.android.wm.shell.compatui.impl;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class CompatUIRequests {
    public final int requestId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DisplayCompatShowRestartDialog extends CompatUIRequests {
        public final int taskId;

        public DisplayCompatShowRestartDialog(int i) {
            super(0, null);
            this.taskId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DisplayCompatShowRestartDialog) && this.taskId == ((DisplayCompatShowRestartDialog) obj).taskId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.taskId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, ")", new StringBuilder("DisplayCompatShowRestartDialog(taskId="));
        }
    }

    public /* synthetic */ CompatUIRequests(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private CompatUIRequests(int i) {
        this.requestId = i;
    }
}
