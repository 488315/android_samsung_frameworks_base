package com.android.systemui.p016qs.tileimpl;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
                break;
            case 1:
                QSTileImpl qSTileImpl = this.f$0;
                if (!qSTileImpl.mLifecycle.mState.equals(Lifecycle.State.DESTROYED)) {
                    qSTileImpl.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
                    if (qSTileImpl.mReadyState == 0) {
                        qSTileImpl.mReadyState = 1;
                    }
                    qSTileImpl.refreshState(null);
                    break;
                }
                break;
            case 2:
                LifecycleRegistry lifecycleRegistry = this.f$0.mLifecycle;
                if (!lifecycleRegistry.mState.equals(Lifecycle.State.DESTROYED)) {
                    lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
                    break;
                }
                break;
            default:
                this.f$0.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
                break;
        }
    }
}
