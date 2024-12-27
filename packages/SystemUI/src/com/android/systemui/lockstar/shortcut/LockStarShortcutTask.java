package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class LockStarShortcutTask {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
