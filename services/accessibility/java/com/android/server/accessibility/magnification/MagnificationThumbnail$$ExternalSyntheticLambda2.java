package com.android.server.accessibility.magnification;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationThumbnail$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ MagnificationThumbnail f$0;

    public /* synthetic */ MagnificationThumbnail$$ExternalSyntheticLambda2(MagnificationThumbnail magnificationThumbnail) {
        this.f$0 = magnificationThumbnail;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.hideThumbnailMainThread();
    }
}
