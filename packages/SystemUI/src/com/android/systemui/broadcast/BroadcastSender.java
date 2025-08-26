package com.android.systemui.broadcast;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BroadcastSender {
    public final Executor bgExecutor;
    public final Context context;
    public final WakeLock.Builder wakeLockBuilder;

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
        final WakeLock wakeLockBuild = this.wakeLockBuilder.setTag("SysUI:BroadcastSender").setMaxTimeout(5000L).build();
        wakeLockBuild.acquire(str);
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.broadcast.BroadcastSender.sendInBackground.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    function0.invoke();
                } finally {
                    wakeLockBuild.release(str);
                }
            }
        });
    }
}
