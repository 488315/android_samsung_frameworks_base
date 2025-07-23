package com.android.systemui.broadcast;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BroadcastSender {
    public final Executor bgExecutor;
    public final Context context;
    public final WakeLock.Builder wakeLockBuilder;

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

    public BroadcastSender(Context context, WakeLock.Builder builder, Executor executor) {
        this.context = context;
        this.wakeLockBuilder = builder;
        this.bgExecutor = executor;
    }

    public final void sendInBackground(final String str, final Function0 function0) {
        final WakeLock build = this.wakeLockBuilder.setTag("SysUI:BroadcastSender").setMaxTimeout(5000L).build();
        build.acquire(str);
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.BroadcastSender$sendInBackground$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Function0.this.invoke();
                } finally {
                    build.release(str);
                }
            }
        });
    }
}
