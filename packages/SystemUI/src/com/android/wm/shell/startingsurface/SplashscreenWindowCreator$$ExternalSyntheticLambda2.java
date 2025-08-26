package com.android.wm.shell.startingsurface;

import android.window.SplashScreenView;
import com.android.wm.shell.startingsurface.SplashscreenWindowCreator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplashscreenWindowCreator$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplashscreenWindowCreator.SplashScreenViewSupplier f$0;

    public /* synthetic */ SplashscreenWindowCreator$$ExternalSyntheticLambda2(SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier, int i) {
        this.$r8$classId = i;
        this.f$0 = splashScreenViewSupplier;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier = this.f$0;
                SplashScreenView splashScreenView = (SplashScreenView) obj;
                synchronized (splashScreenViewSupplier) {
                    splashScreenViewSupplier.mView = splashScreenView;
                    splashScreenViewSupplier.mIsViewSet = true;
                    splashScreenViewSupplier.notify();
                }
                return;
            default:
                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier2 = this.f$0;
                Runnable runnable = (Runnable) obj;
                synchronized (splashScreenViewSupplier2) {
                    splashScreenViewSupplier2.mUiThreadInitTask = runnable;
                }
                return;
        }
    }
}
