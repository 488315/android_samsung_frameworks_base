package com.android.systemui.notetask;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NoteTaskLaunchMode {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Activity extends NoteTaskLaunchMode {
        public static final Activity INSTANCE = new Activity();

        private Activity() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
