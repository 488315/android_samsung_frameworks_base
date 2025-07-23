package com.android.systemui.plugins.clocks;

import android.content.Context;
import android.util.Log;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ClockProviderPluginProtector implements ClockProviderPlugin, PluginWrapper<ClockProviderPlugin> {
    private static final String CLASS = "ClockProviderPlugin";
    private boolean mHasError = false;
    private ClockProviderPlugin mInstance;
    private ProtectedPluginListener mListener;

    private ClockProviderPluginProtector(ClockProviderPlugin clockProviderPlugin, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockProviderPlugin;
        this.mListener = protectedPluginListener;
    }

    public static ClockProviderPluginProtector protect(ClockProviderPlugin clockProviderPlugin, ProtectedPluginListener protectedPluginListener) {
        return clockProviderPlugin instanceof ClockProviderPluginProtector ? (ClockProviderPluginProtector) clockProviderPlugin : new ClockProviderPluginProtector(clockProviderPlugin, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public ClockController createClock(ClockSettings clockSettings) {
        if (this.mHasError) {
            return null;
        }
        try {
            return ClockControllerProtector.protect(this.mInstance.createClock(clockSettings), this.mListener);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: createClock", e);
            this.mHasError = this.mListener.onFail(CLASS, "createClock", e);
            return null;
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: createClock", e2);
            this.mHasError = this.mListener.onFail(CLASS, "createClock", e2);
            return null;
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public ClockPickerConfig getClockPickerConfig(ClockSettings clockSettings) {
        if (this.mHasError) {
            return new ClockPickerConfig("", "", "", null);
        }
        try {
            return this.mInstance.getClockPickerConfig(clockSettings);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: getClockPickerConfig", e);
            this.mHasError = this.mListener.onFail(CLASS, "getClockPickerConfig", e);
            return new ClockPickerConfig("", "", "", null);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: getClockPickerConfig", e2);
            this.mHasError = this.mListener.onFail(CLASS, "getClockPickerConfig", e2);
            return new ClockPickerConfig("", "", "", null);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public List<ClockMetadata> getClocks() {
        if (this.mHasError) {
            return new ArrayList();
        }
        try {
            return this.mInstance.getClocks();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: getClocks", e);
            this.mHasError = this.mListener.onFail(CLASS, "getClocks", e);
            return new ArrayList();
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: getClocks", e2);
            this.mHasError = this.mListener.onFail(CLASS, "getClocks", e2);
            return new ArrayList();
        }
    }

    @Override // com.android.systemui.plugins.Plugin
    public int getVersion() {
        if (this.mHasError) {
            return -1;
        }
        try {
            return this.mInstance.getVersion();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: getVersion", e);
            this.mHasError = this.mListener.onFail(CLASS, "getVersion", e);
            return -1;
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: getVersion", e2);
            this.mHasError = this.mListener.onFail(CLASS, "getVersion", e2);
            return -1;
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public void initialize(ClockMessageBuffers clockMessageBuffers) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.initialize(clockMessageBuffers);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: initialize", e);
            this.mHasError = this.mListener.onFail(CLASS, "initialize", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: initialize", e2);
            this.mHasError = this.mListener.onFail(CLASS, "initialize", e2);
        }
    }

    @Override // com.android.systemui.plugins.Plugin
    public void onCreate(Context context, Context context2) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onCreate(context, context2);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onCreate", e);
            this.mHasError = this.mListener.onFail(CLASS, "onCreate", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onCreate", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onCreate", e2);
        }
    }

    @Override // com.android.systemui.plugins.Plugin
    public void onDestroy() {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onDestroy();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onDestroy", e);
            this.mHasError = this.mListener.onFail(CLASS, "onDestroy", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onDestroy", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onDestroy", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockProviderPlugin getPlugin() {
        return this.mInstance;
    }
}
