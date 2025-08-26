package com.samsung.systemui.splugins;

import android.content.Context;
import android.os.Looper;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: classes4.dex */
public class SPluginInitializerImpl implements SPluginInitializer {
    private static final boolean WTFS_SHOULD_CRASH = false;
    private boolean mWtfsSet;

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public String[] getAllowedPlugins(Context context) {
        return context.getResources().getStringArray(R.array.config_pluginAllowlist);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public Looper getBgLooper() {
        return (Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public SPluginEnabler getPluginEnabler(Context context) {
        return new SPluginEnablerImpl(context);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public void onPluginManagerInit() {
        ((SPluginDependencyProvider) Dependency.sDependency.getDependencyInner(SPluginDependencyProvider.class)).allowPluginDependency(ActivityStarter.class);
    }

    @Override // com.samsung.systemui.splugins.SPluginInitializer
    public void handleWtfs() {
    }
}
