package com.android.systemui.flags;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.Set;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ConditionalRestarter implements Restarter {
    public final CoroutineScope applicationScope;
    public final CoroutineContext backgroundDispatcher;
    public final Set conditions;
    public String pendingReason = "";
    public final long restartDelaySec;
    public final SystemExitRestarter systemExitRestarter;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Condition {
        Flow getCanRestartNow();
    }

    static {
        new Companion(null);
    }

    public ConditionalRestarter(SystemExitRestarter systemExitRestarter, Set<Condition> set, long j, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.systemExitRestarter = systemExitRestarter;
        this.conditions = set;
        this.restartDelaySec = j;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineContext;
    }

    @Override // com.android.systemui.flags.Restarter
    public final void restartSystemUI(String str) {
        Log.d("SysUIFlags", "SystemUI Restart requested. Restarting when idle.");
        if (str.length() == 0) {
            str = this.pendingReason;
        }
        this.pendingReason = str;
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new ConditionalRestarter$scheduleRestart$1(this, null), 5);
    }
}
