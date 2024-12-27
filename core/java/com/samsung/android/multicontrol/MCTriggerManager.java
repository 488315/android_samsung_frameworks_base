package com.samsung.android.multicontrol;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputMonitor;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* loaded from: classes6.dex */
public class MCTriggerManager {
    Context mContext;
    InputChannel mDexInputChannel;
    InputMonitor mDexInputMonitor;
    MCInputEventReceiver mDexInputReceiver;
    InputChannel mInputChannel;
    InputMonitor mInputMonitor;
    MCInputEventReceiver mInputReceiver;
    Looper mLooper;
    SemDesktopModeManager semDesktopModeManager;
    public final String TAG_PREFIX = SemMultiControlManager.TAG_PREFIX;
    private final String TAG =
            SemMultiControlManager.TAG_PREFIX + MCTriggerManager.class.getSimpleName();
    boolean isEnabled = false;
    int DEX_DISPLAY = 2;
    private boolean isDexEnabled = false;
    private SemDesktopModeManager.DesktopModeListener desktopModeListener =
            new SemDesktopModeManager
                    .DesktopModeListener() { // from class:
                                             // com.samsung.android.multicontrol.MCTriggerManager.1
                @Override // com.samsung.android.desktopmode.SemDesktopModeManager.DesktopModeListener
                public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    if (semDesktopModeState.state == 4
                            && semDesktopModeState.getDisplayType() == 102) {
                        if (!MCTriggerManager.this.isDexEnabled) {
                            MCTriggerManager.this.enable(false);
                            MCTriggerManager.this.enable(true);
                        }
                        MCTriggerManager.this.isDexEnabled = true;
                        return;
                    }
                    if (MCTriggerManager.this.isDexEnabled) {
                        MCTriggerManager.this.enable(false);
                    }
                    MCTriggerManager.this.isDexEnabled = false;
                }
            };

    public MCTriggerManager(Context context, Looper looper) {
        this.mContext = context;
        this.mLooper = looper;
    }

    private void registerDesktopModeListener() {
        this.semDesktopModeManager =
                (SemDesktopModeManager)
                        this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (this.semDesktopModeManager == null) {
            return;
        }
        this.semDesktopModeManager.registerListener(this.desktopModeListener);
    }

    private void unregisterDesktopModeListener() {
        if (this.semDesktopModeManager != null) {
            this.semDesktopModeManager.unregisterListener(this.desktopModeListener);
        }
    }

    private boolean isDesktopModeEnabled() {
        if (this.semDesktopModeManager == null) {
            return false;
        }
        SemDesktopModeState semDesktopModeState = this.semDesktopModeManager.getDesktopModeState();
        return semDesktopModeState.state == 4 && semDesktopModeState.getDisplayType() == 102;
    }

    public void enable(boolean enable) {
        try {
            if (enable) {
                if (this.isEnabled) {
                    return;
                }
                Log.i(this.TAG, "[enable] true");
                this.isEnabled = true;
                InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
                this.mInputMonitor = inputManager.monitorGestureInput("MultiControl_0", 0);
                this.mInputChannel = this.mInputMonitor.getInputChannel();
                this.mInputReceiver =
                        new MCInputEventReceiver(
                                this.mContext,
                                0,
                                this.mInputMonitor,
                                this.mInputChannel,
                                this.mLooper);
                if (isDesktopModeEnabled()) {
                    DisplayManager dm =
                            (DisplayManager)
                                    this.mContext.getSystemService(Context.DISPLAY_SERVICE);
                    Context displayContext =
                            this.mContext.createDisplayContext(dm.getDisplay(this.DEX_DISPLAY));
                    InputManager inputManager2 =
                            (InputManager) displayContext.getSystemService("input");
                    this.mDexInputMonitor =
                            inputManager2.monitorGestureInput("MultiControl_2", this.DEX_DISPLAY);
                    this.mDexInputChannel = this.mDexInputMonitor.getInputChannel();
                    this.mDexInputReceiver =
                            new MCInputEventReceiver(
                                    this.mContext,
                                    this.DEX_DISPLAY,
                                    this.mDexInputMonitor,
                                    this.mDexInputChannel,
                                    this.mLooper);
                }
                registerDesktopModeListener();
                return;
            }
            if (!this.isEnabled) {
                return;
            }
            this.isEnabled = false;
            Log.i(this.TAG, "[enable] false");
            if (this.mInputMonitor != null) {
                this.mInputMonitor.dispose();
            }
            if (this.mInputReceiver != null) {
                this.mInputReceiver.dispose();
            }
            if (this.mDexInputMonitor != null) {
                this.mDexInputMonitor.dispose();
            }
            if (this.mDexInputReceiver != null) {
                this.mDexInputReceiver.dispose();
            }
            this.mDexInputReceiver = null;
            this.mInputReceiver = null;
            this.mInputMonitor = null;
            this.mDexInputMonitor = null;
            unregisterDesktopModeListener();
        } catch (Exception e) {
            Log.e(this.TAG, "[enableTriggerDetection]", e);
        }
    }

    public void setTriggerThreshold(int threshold) {
        try {
            if (this.mInputReceiver != null) {
                this.mInputReceiver.setTriggerThreshold(threshold);
            }
            if (this.mDexInputReceiver != null) {
                this.mDexInputReceiver.setTriggerThreshold(threshold);
            }
        } catch (Exception e) {
            Log.e(this.TAG, "[setTriggerThreshold]", e);
        }
    }

    public void dump() {
        Log.d(this.TAG, "mInputChannel=" + this.mInputChannel);
        Log.d(this.TAG, "mInputReceiver=" + this.mInputReceiver);
        Log.d(this.TAG, "mDexInputChannel=" + this.mDexInputChannel);
        Log.d(this.TAG, "mDexInputReceiver=" + this.mDexInputReceiver);
    }
}
