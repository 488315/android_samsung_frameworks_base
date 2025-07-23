package com.android.systemui.wallpapers;

import android.graphics.RectF;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WakefulnessLifecycle.Observer f$0;

    public /* synthetic */ ImageWallpaper$$ExternalSyntheticLambda1(WakefulnessLifecycle.Observer observer, int i) {
        this.$r8$classId = i;
        this.f$0 = observer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WakefulnessLifecycle.Observer observer = this.f$0;
        switch (i) {
            case 0:
                RectF rectF = ImageWallpaper.LOCAL_COLOR_BOUNDS;
                ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).removeObserver(observer);
                break;
            default:
                RectF rectF2 = ImageWallpaper.LOCAL_COLOR_BOUNDS;
                ((WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class)).addObserver(observer);
                break;
        }
    }
}
