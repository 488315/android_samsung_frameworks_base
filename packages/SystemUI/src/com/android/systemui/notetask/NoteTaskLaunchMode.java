package com.android.systemui.notetask;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class NoteTaskLaunchMode {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Activity extends NoteTaskLaunchMode {
        public static final Activity INSTANCE = new Activity();

        private Activity() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
