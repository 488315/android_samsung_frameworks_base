package com.android.systemui.wallpaper.engines.multipack;

import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.engines.multipack.MultipackEngine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MultipackEngine$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MultipackEngine$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((MultipackEngine) obj).updateWallpaper();
                break;
            case 1:
                ((MultipackEngine.AnonymousClass1) obj).this$0.updateWallpaper();
                break;
            default:
                MultipackEngine.AnonymousClass2 anonymousClass2 = (MultipackEngine.AnonymousClass2) obj;
                WallpaperUtils.sCachedSmartCroppedRect.put(anonymousClass2.this$0.getWhich(), null);
                anonymousClass2.this$0.updateWallpaper();
                break;
        }
    }
}
