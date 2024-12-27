package com.android.systemui.broadcast;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BroadcastSender {
    public final Executor bgExecutor;
    public final Context context;
    public final WakeLock.Builder wakeLockBuilder;

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

    public BroadcastSender(Context context, WakeLock.Builder builder, Executor executor) {
        this.context = context;
        this.wakeLockBuilder = builder;
        this.bgExecutor = executor;
    }

    public final void closeSystemDialogs() {
        sendInBackground("closeSystemDialogs", new Function0() { // from class: com.android.systemui.broadcast.BroadcastSender$closeSystemDialogs$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                BroadcastSender.this.context.closeSystemDialogs();
                return Unit.INSTANCE;
            }
        });
    }

    public final void sendBroadcast(final Intent intent) {
        sendInBackground(String.valueOf(intent), new Function0() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcast$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                BroadcastSender.this.context.sendBroadcast(intent);
                return Unit.INSTANCE;
            }
        });
    }

    public final void sendBroadcast$1(final Intent intent) {
        final String str = "com.android.systemui.permission.SELF";
        sendInBackground(String.valueOf(intent), new Function0() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcast$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                BroadcastSender.this.context.sendBroadcast(intent, str);
                return Unit.INSTANCE;
            }
        });
    }

    public final void sendBroadcastAsUser(final Intent intent, final UserHandle userHandle) {
        sendInBackground(String.valueOf(intent), new Function0() { // from class: com.android.systemui.broadcast.BroadcastSender$sendBroadcastAsUser$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                BroadcastSender.this.context.sendBroadcastAsUser(intent, userHandle);
                return Unit.INSTANCE;
            }
        });
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
