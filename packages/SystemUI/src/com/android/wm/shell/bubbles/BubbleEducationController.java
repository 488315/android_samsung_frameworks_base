package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BubbleEducationController {
    public final Context context;
    public final SharedPreferences prefs;

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

    public BubbleEducationController(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(context.getPackageName(), 0);
    }
}
