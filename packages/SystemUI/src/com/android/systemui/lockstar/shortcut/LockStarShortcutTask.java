package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class LockStarShortcutTask {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LockStarShortcutTask(Context context) {
    }

    public abstract void execute();

    public abstract String getName();

    public abstract void init();

    public abstract void terminate();
}
