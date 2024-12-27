package com.android.systemui.flags;

import android.util.Log;
import java.util.Set;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ConditionalRestarter implements Restarter {
    public final CoroutineScope applicationScope;
    public final CoroutineContext backgroundDispatcher;
    public final Set conditions;
    public String pendingReason = "";
    public final long restartDelaySec;
    public final SystemExitRestarter systemExitRestarter;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new ConditionalRestarter$scheduleRestart$1(this, null), 2);
    }
}
