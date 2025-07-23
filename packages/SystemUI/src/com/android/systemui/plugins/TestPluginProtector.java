package com.android.systemui.plugins;

import android.content.Context;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TestPluginProtector implements TestPlugin, PluginWrapper<TestPlugin> {
    private static final String CLASS = "TestPlugin";
    private boolean mHasError = false;
    private TestPlugin mInstance;
    private ProtectedPluginListener mListener;

    private TestPluginProtector(TestPlugin testPlugin, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = testPlugin;
        this.mListener = protectedPluginListener;
    }

    public static TestPluginProtector protect(TestPlugin testPlugin, ProtectedPluginListener protectedPluginListener) {
        return testPlugin instanceof TestPluginProtector ? (TestPluginProtector) testPlugin : new TestPluginProtector(testPlugin, protectedPluginListener);
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

    @Override // com.android.systemui.plugins.TestPlugin
    public Object methodThrowsError() {
        if (this.mHasError) {
            return new Object();
        }
        try {
            return this.mInstance.methodThrowsError();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: methodThrowsError", e);
            this.mHasError = this.mListener.onFail(CLASS, "methodThrowsError", e);
            return new Object();
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: methodThrowsError", e2);
            this.mHasError = this.mListener.onFail(CLASS, "methodThrowsError", e2);
            return new Object();
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
    public TestPlugin getPlugin() {
        return this.mInstance;
    }
}
