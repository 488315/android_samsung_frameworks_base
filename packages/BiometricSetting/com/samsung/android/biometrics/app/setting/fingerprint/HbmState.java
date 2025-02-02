package com.samsung.android.biometrics.app.setting.fingerprint;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.fingerprint.HbmController;

/* loaded from: classes.dex */
public abstract class HbmState {
    private HbmController.Provider mProvider;

    public final HbmController.Provider getProvider() {
        return this.mProvider;
    }

    abstract String getTag();

    public abstract void handleDisplayStateChanged(int i);

    protected final void setDisplayStateLimit(boolean z) {
        DisplayStateManager displayStateManager;
        displayStateManager = HbmController.this.mDisplayStateManager;
        displayStateManager.setDisplayStateLimit(z);
    }

    public final void setProvider(HbmController.Provider provider) {
        this.mProvider = provider;
    }

    protected final void setState(HbmState hbmState) {
        HbmController.this.setCurrentState(hbmState);
    }

    public abstract void turnOffHbm();

    public abstract void turnOnHbm();

    protected void stop() {
    }
}
