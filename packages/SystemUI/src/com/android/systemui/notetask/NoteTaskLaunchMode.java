package com.android.systemui.notetask;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class NoteTaskLaunchMode {

    public final class Activity extends NoteTaskLaunchMode {
        public static final Activity INSTANCE = new Activity();

        private Activity() {
            super(null);
        }
    }

    public final class AppBubble extends NoteTaskLaunchMode {
        public static final AppBubble INSTANCE = new AppBubble();

        private AppBubble() {
            super(null);
        }
    }

    private NoteTaskLaunchMode() {
    }

    public /* synthetic */ NoteTaskLaunchMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
