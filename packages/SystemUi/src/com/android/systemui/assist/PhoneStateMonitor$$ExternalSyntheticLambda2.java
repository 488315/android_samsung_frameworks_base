package com.android.systemui.assist;

import com.android.systemui.assist.PhoneStateMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class PhoneStateMonitor$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PhoneStateMonitor$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PhoneStateMonitor phoneStateMonitor = (PhoneStateMonitor) this.f$0;
                phoneStateMonitor.getClass();
                phoneStateMonitor.mDefaultHome = PhoneStateMonitor.getCurrentDefaultHome();
                break;
            default:
                ((PhoneStateMonitor.C10431) this.f$0).this$0.mDefaultHome = PhoneStateMonitor.getCurrentDefaultHome();
                break;
        }
    }
}
