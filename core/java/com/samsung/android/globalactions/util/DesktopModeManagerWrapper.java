package com.samsung.android.globalactions.util;

import android.content.Context;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

public class DesktopModeManagerWrapper {
    private static final String TAG = "DesktopModeManagerWrapper";
    private final Context mContext;
    SemDesktopModeManager mDesktopModeManager;
    SemDesktopModeManager.DesktopModeListener mListener;
    private final LogWrapper mLogWrapper;

    public DesktopModeManagerWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    public void registerModeChangedListener(final Runnable action) {
        try {
            this.mDesktopModeManager =
                    (SemDesktopModeManager)
                            this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        } catch (Exception exception) {
            this.mLogWrapper.e(TAG, "mContext.getSystemService() : exception = " + exception);
        }
        if (this.mDesktopModeManager == null) {
            this.mLogWrapper.e(TAG, "registerModeChangedListener() : mDesktopModeManager is NULL!");
            return;
        }
        if (this.mListener != null) {
            dispose();
        }
        this.mListener =
                new SemDesktopModeManager
                        .DesktopModeListener() { // from class:
                                                 // com.samsung.android.globalactions.util.DesktopModeManagerWrapper$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.desktopmode.SemDesktopModeManager.DesktopModeListener
                    public final void onDesktopModeStateChanged(
                            SemDesktopModeState semDesktopModeState) {
                        action.run();
                    }
                };
        this.mDesktopModeManager.registerListener(this.mListener);
    }

    public void dispose() {
        if (this.mDesktopModeManager != null) {
            this.mDesktopModeManager.unregisterListener(this.mListener);
            this.mListener = null;
        } else {
            this.mLogWrapper.e(TAG, "dispose() : mDesktopModeManager is NULL!");
        }
    }

    public boolean isDesktopModeStandalone() {
        if (this.mDesktopModeManager == null) {
            this.mLogWrapper.e(TAG, "isDesktopModeStandalone() : mDesktopModeManager is NULL!");
            return false;
        }
        SemDesktopModeState desktopModeState = this.mDesktopModeManager.getDesktopModeState();
        return desktopModeState != null
                && desktopModeState.enabled == 4
                && desktopModeState.getDisplayType() == 101;
    }

    public boolean isDesktopModeDualView() {
        if (this.mDesktopModeManager == null) {
            this.mLogWrapper.e(TAG, "isDesktopModeDualView() : mDesktopModeManager is NULL!");
            return false;
        }
        SemDesktopModeState desktopModeState = this.mDesktopModeManager.getDesktopModeState();
        return desktopModeState != null
                && desktopModeState.enabled == 4
                && desktopModeState.getDisplayType() == 102;
    }
}
