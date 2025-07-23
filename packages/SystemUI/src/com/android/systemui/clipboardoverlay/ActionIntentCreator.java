package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.content.pm.PackageManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActionIntentCreator implements IntentCreator {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final PackageManager packageManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public ActionIntentCreator(Context context, PackageManager packageManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.packageManager = packageManager;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }
}
