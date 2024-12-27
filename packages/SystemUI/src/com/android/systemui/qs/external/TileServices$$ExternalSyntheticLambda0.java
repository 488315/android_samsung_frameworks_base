package com.android.systemui.qs.external;

import android.content.ComponentName;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TileServices$$ExternalSyntheticLambda0(TileServices.AnonymousClass2 anonymousClass2, ComponentName componentName) {
        this.f$0 = anonymousClass2;
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
                TileServices.AnonymousClass2 anonymousClass2 = (TileServices.AnonymousClass2) this.f$0;
                TileServices.m2072$$Nest$mrequestListening(TileServices.this, (ComponentName) this.f$1);
                break;
        }
    }

    public /* synthetic */ TileServices$$ExternalSyntheticLambda0(TileServices tileServices, String str) {
        this.f$0 = tileServices;
        this.f$1 = str;
    }
}
