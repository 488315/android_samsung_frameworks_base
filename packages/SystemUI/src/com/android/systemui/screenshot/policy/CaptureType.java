package com.android.systemui.screenshot.policy;

import android.graphics.Rect;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface CaptureType {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FullScreen implements CaptureType {
        public final int displayId;

        public FullScreen(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof FullScreen) && this.displayId == ((FullScreen) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("FullScreen(displayId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IsolatedTask implements CaptureType {
        public final Rect taskBounds;
        public final int taskId;

        public IsolatedTask(int i, Rect rect) {
            this.taskId = i;
            this.taskBounds = rect;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IsolatedTask)) {
                return false;
            }
            IsolatedTask isolatedTask = (IsolatedTask) obj;
            return this.taskId == isolatedTask.taskId && Intrinsics.areEqual(this.taskBounds, isolatedTask.taskBounds);
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.taskId) * 31;
            Rect rect = this.taskBounds;
            return hashCode + (rect == null ? 0 : rect.hashCode());
        }

        public final String toString() {
            return "IsolatedTask(taskId=" + this.taskId + ", taskBounds=" + this.taskBounds + ")";
        }
    }
}
