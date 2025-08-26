package com.android.systemui.pluginlock;

import com.android.systemui.pluginlock.PluginLockMediatorImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockMediatorImpl$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PluginLockMediatorImpl$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PluginLockMediatorImpl) obj).lambda$onBarStateChanged$6();
                break;
            default:
                ((PluginLockMediatorImpl.AnonymousClass2) obj).lambda$onStartedWakingUp$0();
                break;
        }
    }
}
