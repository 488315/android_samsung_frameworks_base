package com.android.systemui.plugins;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.android.systemui.R;
import com.android.systemui.dagger.PluginModule;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginManagerImpl;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PluginsModule {
    public static final String PLUGIN_DEBUG = "plugin_debug";
    public static final String PLUGIN_PRIVILEGED = "plugin_privileged";
    public static final String PLUGIN_THREAD = "plugin_thread";

    public static PluginActionManager.Factory providePluginInstanceManagerFactory(Context context, PackageManager packageManager, Executor executor, Executor executor2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory) {
        return new PluginActionManager.Factory(context, packageManager, executor, executor2, notificationManager, pluginEnabler, list, factory);
    }

    public static boolean providesPluginDebug() {
        return Build.IS_DEBUGGABLE;
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        return ((ThreadFactoryImpl) threadFactory).buildExecutorOnNewThread("plugin");
    }

    public static PluginInstance.Factory providesPluginInstanceFactory(List<String> list, boolean z) {
        return new PluginInstance.Factory(PluginModule.class.getClassLoader(), new PluginInstance.InstanceFactory(), new PluginInstance.VersionCheckerImpl(), list, z);
    }

    public static PluginManager providesPluginManager(Context context, PluginActionManager.Factory factory, boolean z, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list, PluginInstance.Factory factory2) {
        return new PluginManagerImpl(context, factory, z, uncaughtExceptionPreHandlerManager, pluginEnabler, pluginPrefs, list, factory2);
    }

    public static PluginPrefs providesPluginPrefs(Context context) {
        return new PluginPrefs(context);
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.config_pluginAllowlist));
    }

    public abstract PluginEnabler bindsPluginEnablerImpl(PluginEnablerImpl pluginEnablerImpl);
}
