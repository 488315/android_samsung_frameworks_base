package com.android.wm.shell.compatui.impl;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class CompatUIEvents {
    public final int eventId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SizeCompatRestartButtonAppeared extends CompatUIEvents {
        public final int taskId;

        public SizeCompatRestartButtonAppeared(int i) {
            super(0, null);
            this.taskId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SizeCompatRestartButtonAppeared) && this.taskId == ((SizeCompatRestartButtonAppeared) obj).taskId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.taskId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, ")", new StringBuilder("SizeCompatRestartButtonAppeared(taskId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SizeCompatRestartButtonClicked extends CompatUIEvents {
        public final int taskId;

        public SizeCompatRestartButtonClicked(int i) {
            super(1, null);
            this.taskId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SizeCompatRestartButtonClicked) && this.taskId == ((SizeCompatRestartButtonClicked) obj).taskId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.taskId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, ")", new StringBuilder("SizeCompatRestartButtonClicked(taskId="));
        }
    }

    public /* synthetic */ CompatUIEvents(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private CompatUIEvents(int i) {
        this.eventId = i;
    }
}
