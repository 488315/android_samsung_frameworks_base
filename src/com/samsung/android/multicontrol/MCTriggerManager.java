package com.samsung.android.multicontrol;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.InputChannel;
import android.view.InputMonitor;

/* loaded from: classes6.dex */
public class MCTriggerManager {
    Context mContext;
    InputChannel mDexInputChannel;
    InputMonitor mDexInputMonitor;
    MCInputEventReceiver mDexInputReceiver;
    private DisplayManager mDisplayManager;
    InputChannel mInputChannel;
    InputMonitor mInputMonitor;
    MCInputEventReceiver mInputReceiver;
    Looper mLooper;
    public final String TAG_PREFIX = SemMultiControlManager.TAG_PREFIX;
    private final String TAG = "MultiControl@MCTriggerManager";
    boolean isEnabled = false;
    int DEX_DISPLAY = -1;
    private final int FLAG_EXTERNAL_DESKTOP_WINDOWING = 131072;
    private final String AUTHORITY = "com.samsung.android.inputshare.settings.provider";
    private final String KEY_MC_ACTION = "KEY_MC_ACTION";
    private final String MC_METHOD = "MC_DEX_CHECK";
    private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.samsung.android.multicontrol.MCTriggerManager.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            if (MCTriggerManager.this.DEX_DISPLAY == i) {
                MCTriggerManager.this.DEX_DISPLAY = -1;
                if (MCTriggerManager.this.isEnabled) {
                    MCTriggerManager.this.enable(false);
                    MCTriggerManager.this.enable(true);
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (MCTriggerManager.this.isDesktopModeEnabled(i)) {
                MCTriggerManager.this.DEX_DISPLAY = i;
                MCTriggerManager.this.mContext.getContentResolver().call("com.samsung.android.inputshare.settings.provider", "MC_DEX_CHECK", (String) null, new Bundle());
                if (MCTriggerManager.this.isEnabled) {
                    MCTriggerManager.this.enable(false);
                    MCTriggerManager.this.enable(true);
                }
            }
        }
    };

    public MCTriggerManager(Context context, Looper looper) {
        this.mContext = context;
        this.mLooper = looper;
        this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDesktopModeEnabled(int i) {
        Display display;
        return (i == 0 || i == -1 || (display = this.mDisplayManager.getDisplay(i)) == null || (display.getFlags() & 131072) == 0) ? false : true;
    }

    public void observeDesktopMode(boolean z) {
        try {
            if (z) {
                this.mDisplayManager.registerDisplayListener(this.mDisplayListener, null);
            } else {
                this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            }
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            Log.e(this.TAG, "[observeDesktopMode]", e);
        }
    }

    public void enable(boolean z) {
        if (z) {
            if (this.isEnabled) {
                return;
            }
            Log.i(this.TAG, "[enable] true");
            this.isEnabled = true;
            InputMonitor monitorGestureInput = ((InputManager) this.mContext.getSystemService("input")).monitorGestureInput("MultiControl_0", 0);
            this.mInputMonitor = monitorGestureInput;
            this.mInputChannel = monitorGestureInput.getInputChannel();
            this.mInputReceiver = new MCInputEventReceiver(this.mContext, 0, this.mInputMonitor, this.mInputChannel, this.mLooper);
            if (isDesktopModeEnabled(this.DEX_DISPLAY)) {
                InputMonitor monitorGestureInput2 = ((InputManager) this.mContext.createDisplayContext(((DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE)).getDisplay(this.DEX_DISPLAY)).getSystemService("input")).monitorGestureInput("MultiControl_2", this.DEX_DISPLAY);
                this.mDexInputMonitor = monitorGestureInput2;
                this.mDexInputChannel = monitorGestureInput2.getInputChannel();
                this.mDexInputReceiver = new MCInputEventReceiver(this.mContext, this.DEX_DISPLAY, this.mDexInputMonitor, this.mDexInputChannel, this.mLooper);
                return;
            }
            return;
        }
        if (this.isEnabled) {
            this.isEnabled = false;
            Log.i(this.TAG, "[enable] false");
            InputMonitor inputMonitor = this.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
            }
            MCInputEventReceiver mCInputEventReceiver = this.mInputReceiver;
            if (mCInputEventReceiver != null) {
                mCInputEventReceiver.dispose();
            }
            InputMonitor inputMonitor2 = this.mDexInputMonitor;
            if (inputMonitor2 != null) {
                inputMonitor2.dispose();
            }
            MCInputEventReceiver mCInputEventReceiver2 = this.mDexInputReceiver;
            if (mCInputEventReceiver2 != null) {
                mCInputEventReceiver2.dispose();
            }
            this.mDexInputReceiver = null;
            this.mInputReceiver = null;
            this.mInputMonitor = null;
            this.mDexInputMonitor = null;
        }
    }

    public void setTriggerThreshold(int i) {
        MCInputEventReceiver mCInputEventReceiver = this.mInputReceiver;
        if (mCInputEventReceiver != null) {
            mCInputEventReceiver.setTriggerThreshold(i);
        }
        MCInputEventReceiver mCInputEventReceiver2 = this.mDexInputReceiver;
        if (mCInputEventReceiver2 != null) {
            mCInputEventReceiver2.setTriggerThreshold(i);
        }
    }

    public void dump() {
        Log.d(this.TAG, "mInputChannel=" + this.mInputChannel);
        Log.d(this.TAG, "mInputReceiver=" + this.mInputReceiver);
        Log.d(this.TAG, "mDexInputChannel=" + this.mDexInputChannel);
        Log.d(this.TAG, "mDexInputReceiver=" + this.mDexInputReceiver);
    }
}
