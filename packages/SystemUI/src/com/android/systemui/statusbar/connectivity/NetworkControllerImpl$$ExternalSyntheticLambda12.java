package com.android.systemui.statusbar.connectivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ NetworkControllerImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda12(NetworkControllerImpl networkControllerImpl, boolean z) {
        this.f$0 = networkControllerImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NetworkControllerImpl networkControllerImpl = this.f$0;
        networkControllerImpl.mUserSetup = this.f$1;
        for (int i = 0; i < networkControllerImpl.mMobileSignalControllers.size(); i++) {
            MobileSignalController valueAt = networkControllerImpl.mMobileSignalControllers.valueAt(i);
            ((MobileState) valueAt.mCurrentState).userSetup = networkControllerImpl.mUserSetup;
            valueAt.notifyListenersIfNecessary();
        }
    }
}
