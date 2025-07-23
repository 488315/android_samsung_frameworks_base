package com.android.systemui.notetask;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class NoteTaskLaunchMode {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Activity extends NoteTaskLaunchMode {
        public static final Activity INSTANCE = new Activity();

        private Activity() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppBubble extends NoteTaskLaunchMode {
        public final NoteTaskBubbleExpandBehavior bubbleExpandBehavior;

        public AppBubble(NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior) {
            super(null);
            this.bubbleExpandBehavior = noteTaskBubbleExpandBehavior;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof AppBubble) && this.bubbleExpandBehavior == ((AppBubble) obj).bubbleExpandBehavior;
        }

        public final int hashCode() {
            return this.bubbleExpandBehavior.hashCode();
        }

        public final String toString() {
            return "AppBubble(bubbleExpandBehavior=" + this.bubbleExpandBehavior + ")";
        }
    }

    public /* synthetic */ NoteTaskLaunchMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NoteTaskLaunchMode() {
    }
}
