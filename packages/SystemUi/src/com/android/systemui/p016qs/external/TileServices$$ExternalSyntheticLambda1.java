package com.android.systemui.p016qs.external;

import android.content.ComponentName;
import com.android.systemui.p016qs.external.TileServices;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Comparable f$1;

    public /* synthetic */ TileServices$$ExternalSyntheticLambda1(Object obj, Comparable comparable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = comparable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TileServices tileServices = (TileServices) this.f$0;
                ((StatusBarIconControllerImpl) tileServices.mStatusBarIconController).removeAllIconsForSlot((String) this.f$1);
                break;
            default:
                TileServices.C21683 c21683 = (TileServices.C21683) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                c21683.getClass();
                boolean z = TileServices.DEBUG;
                TileServices.this.requestListening(componentName);
                break;
        }
    }
}
