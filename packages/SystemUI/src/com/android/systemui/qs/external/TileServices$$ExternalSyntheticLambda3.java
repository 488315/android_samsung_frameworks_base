package com.android.systemui.qs.external;

import android.content.ComponentName;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TileServices$$ExternalSyntheticLambda3(TileServices.AnonymousClass3 anonymousClass3, ComponentName componentName) {
        this.f$0 = anonymousClass3;
        this.f$1 = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TileServices tileServices = (TileServices) this.f$0;
                ((StatusBarIconControllerImpl) tileServices.mStatusBarIconController).removeAllIconsForSlot((String) this.f$1);
                break;
            default:
                TileServices.AnonymousClass3 anonymousClass3 = (TileServices.AnonymousClass3) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                anonymousClass3.getClass();
                boolean z = TileServices.DEBUG;
                anonymousClass3.this$0.requestListening(componentName);
                break;
        }
    }

    public /* synthetic */ TileServices$$ExternalSyntheticLambda3(TileServices tileServices, String str) {
        this.f$0 = tileServices;
        this.f$1 = str;
    }
}
