package com.android.systemui.wallpapers;

import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.IntegratedEngine.AnonymousClass2 f$0;

    public /* synthetic */ ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = this.f$0;
        switch (i) {
            case 0:
                anonymousClass2.mChoreographer.removeFrameCallback(anonymousClass2.mChoreographerFrameCallback);
                break;
            default:
                anonymousClass2.mChoreographer.postFrameCallback(anonymousClass2.mChoreographerFrameCallback);
                break;
        }
    }
}
