package com.android.systemui.qs.tileimpl;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileImpl f$0;

    public /* synthetic */ QSTileImpl$$ExternalSyntheticLambda0(QSTileImpl qSTileImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        QSTileImpl qSTileImpl = this.f$0;
        switch (i) {
            case 0:
                qSTileImpl.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
            case 1:
                qSTileImpl.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                break;
            case 2:
                if (!qSTileImpl.mLifecycle.state.equals(Lifecycle.State.DESTROYED)) {
                    qSTileImpl.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
                    if (qSTileImpl.mReadyState == 0) {
                        qSTileImpl.mReadyState = 1;
                    }
                    qSTileImpl.refreshState(null);
                    break;
                }
                break;
            default:
                LifecycleRegistry lifecycleRegistry = qSTileImpl.mLifecycle;
                if (!lifecycleRegistry.state.equals(Lifecycle.State.DESTROYED)) {
                    lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
                    break;
                }
                break;
        }
    }
}
