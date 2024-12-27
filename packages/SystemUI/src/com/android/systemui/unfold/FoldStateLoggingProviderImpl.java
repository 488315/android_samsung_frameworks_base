package com.android.systemui.unfold;

import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class FoldStateLoggingProviderImpl implements FoldStateLoggingProvider, FoldStateProvider.FoldUpdatesListener {
    public Long actionStartMillis;
    public final SystemClock clock;
    public final FoldStateProvider foldStateProvider;
    public Integer lastState;
    public final List outputListeners = new ArrayList();

    public FoldStateLoggingProviderImpl(FoldStateProvider foldStateProvider, SystemClock systemClock) {
        this.foldStateProvider = foldStateProvider;
        this.clock = systemClock;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.outputListeners).add((FoldStateLogger) obj);
    }

    public final void dispatchState(int i) {
        long elapsedRealtime = this.clock.elapsedRealtime();
        Integer num = this.lastState;
        Long l = this.actionStartMillis;
        if (num != null && num.intValue() != i && l != null) {
            FoldStateChange foldStateChange = new FoldStateChange(num.intValue(), i, elapsedRealtime - l.longValue());
            Iterator it = ((ArrayList) this.outputListeners).iterator();
            while (it.hasNext()) {
                ((FoldStateLogger) it.next()).getClass();
                FrameworkStatsLog.write(414, foldStateChange.previous, foldStateChange.current, foldStateChange.dtMillis);
            }
        }
        this.actionStartMillis = null;
        this.lastState = Integer.valueOf(i);
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onFoldUpdate(int i) {
        long elapsedRealtime = this.clock.elapsedRealtime();
        if (i == 0) {
            this.lastState = 2;
            this.actionStartMillis = Long.valueOf(elapsedRealtime);
            return;
        }
        if (i == 1) {
            this.actionStartMillis = Long.valueOf(elapsedRealtime);
            return;
        }
        if (i == 2) {
            dispatchState(3);
        } else if (i == 3) {
            dispatchState(1);
        } else {
            if (i != 4) {
                return;
            }
            dispatchState(2);
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
    public final void onUnfoldedScreenAvailable() {
        Log.d("FoldStateLoggingProviderImpl", "Unfolded screen available");
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.outputListeners).remove((FoldStateLogger) obj);
    }
}
