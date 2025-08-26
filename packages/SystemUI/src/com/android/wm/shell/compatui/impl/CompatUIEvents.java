package com.android.wm.shell.compatui.impl;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class CompatUIEvents {
    public final int eventId;

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
