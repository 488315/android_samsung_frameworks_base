package com.android.systemui.statusbar.connectivity;

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
            MobileSignalController mobileSignalControllerValueAt = networkControllerImpl.mMobileSignalControllers.valueAt(i);
            ((MobileState) mobileSignalControllerValueAt.mCurrentState).userSetup = networkControllerImpl.mUserSetup;
            mobileSignalControllerValueAt.notifyListenersIfNecessary();
        }
    }
}
