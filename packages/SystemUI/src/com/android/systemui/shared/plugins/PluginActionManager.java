package com.android.systemui.shared.plugins;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.VersionInfo;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PluginActionManager {
    public final String mAction;
    public final boolean mAllowMultiple;
    public final boolean mAllowMultipleUsers;
    public final Executor mBgExecutor;
    public final Context mContext;
    public final int mDisplayId;
    public final boolean mIsDebuggable;
    public final PluginListener mListener;
    public final Executor mMainExecutor;
    public final NotificationManager mNotificationManager;
    public final Class mPluginClass;
    public final PluginEnabler mPluginEnabler;
    public final PluginInstance.Factory mPluginInstanceFactory;
    private final ArrayList<PluginInstance> mPluginInstances;
    public final PackageManager mPm;
    public final ArraySet mPrivilegedPlugins;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final Executor mBgExecutor;
        public final Context mContext;
        public final Executor mMainExecutor;
        public final NotificationManager mNotificationManager;
        public final PackageManager mPackageManager;
        public final PluginEnabler mPluginEnabler;
        public final PluginInstance.Factory mPluginInstanceFactory;
        public final List mPrivilegedPlugins;

        public Factory(Context context, PackageManager packageManager, Executor executor, Executor executor2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory) {
            this.mContext = context;
            this.mPackageManager = packageManager;
            this.mMainExecutor = executor;
            this.mBgExecutor = executor2;
            this.mNotificationManager = notificationManager;
            this.mPluginEnabler = pluginEnabler;
            this.mPrivilegedPlugins = list;
            this.mPluginInstanceFactory = factory;
        }
    }

    /* renamed from: $r8$lambda$5F1fdvH66AX-KG-8BByIAWoLe8U, reason: not valid java name */
    public static void m2108$r8$lambda$5F1fdvH66AXKG8BByIAWoLe8U(PluginActionManager pluginActionManager) {
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("queryAll "), pluginActionManager.mAction, "PluginActionManager");
        for (int size = pluginActionManager.mPluginInstances.size() - 1; size >= 0; size--) {
            pluginActionManager.mMainExecutor.execute(new PluginActionManager$$ExternalSyntheticLambda2(pluginActionManager, pluginActionManager.mPluginInstances.get(size), 3));
        }
        pluginActionManager.mPluginInstances.clear();
        pluginActionManager.handleQueryPlugins(null);
    }

    /* renamed from: $r8$lambda$e2-SW2bOJhdGs27PogmcioXOXds, reason: not valid java name */
    public static void m2109$r8$lambda$e2SW2bOJhdGs27PogmcioXOXds(PluginActionManager pluginActionManager, String str) {
        pluginActionManager.removePkg(str);
        StringBuilder sb = new StringBuilder("queryPkg ");
        sb.append(str);
        sb.append(" ");
        String str2 = pluginActionManager.mAction;
        ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "PluginActionManager");
        if (pluginActionManager.mAllowMultiple || pluginActionManager.mPluginInstances.size() == 0) {
            pluginActionManager.handleQueryPlugins(str);
        } else {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Too many of ", str2, "PluginActionManager");
        }
    }

    public /* synthetic */ PluginActionManager(Context context, PackageManager packageManager, String str, PluginListener pluginListener, Class cls, boolean z, Executor executor, Executor executor2, boolean z2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List list, PluginInstance.Factory factory, boolean z3, int i, int i2) {
        this(context, packageManager, str, pluginListener, cls, z, executor, executor2, z2, notificationManager, pluginEnabler, list, factory, z3, i);
    }

    public static void onPluginDisconnected(PluginInstance pluginInstance) {
        Log.d("PluginActionManager", "onPluginDisconnected");
        synchronized (pluginInstance) {
            pluginInstance.log("onDestroy");
            pluginInstance.unloadPlugin();
            pluginInstance.mListener.onPluginDetached(pluginInstance);
        }
    }

    public final boolean checkAndDisable(String str) {
        Iterator it = new ArrayList(this.mPluginInstances).iterator();
        boolean z = false;
        while (it.hasNext()) {
            PluginInstance pluginInstance = (PluginInstance) it.next();
            if (str.startsWith(pluginInstance.mComponentName.getPackageName())) {
                z |= disable(pluginInstance, 3);
            }
        }
        return z;
    }

    public final boolean dependsOn(Plugin plugin, Class cls) {
        Iterator it = new ArrayList(this.mPluginInstances).iterator();
        while (it.hasNext()) {
            PluginInstance pluginInstance = (PluginInstance) it.next();
            if (pluginInstance.mComponentName.getClassName().equals(plugin.getClass().getName())) {
                Plugin plugin2 = pluginInstance.mPlugin;
                PluginInstance.PluginFactory pluginFactory = pluginInstance.mPluginFactory;
                return pluginFactory.checkVersion(plugin2) != null && pluginFactory.checkVersion(pluginInstance.mPlugin).mVersions.containsKey(cls);
            }
        }
        return false;
    }

    public final void destroy() {
        Log.d("PluginActionManager", "stopListening");
        Iterator it = new ArrayList(this.mPluginInstances).iterator();
        while (it.hasNext()) {
            this.mMainExecutor.execute(new PluginActionManager$$ExternalSyntheticLambda2(this, (PluginInstance) it.next(), 0));
        }
    }

    public final boolean disable(PluginInstance pluginInstance, int i) {
        ComponentName componentName = pluginInstance.mComponentName;
        if (isPluginPrivileged(componentName)) {
            return false;
        }
        Log.w("PluginActionManager", "Disabling plugin " + componentName.flattenToShortString());
        this.mPluginEnabler.setDisabled(componentName, i);
        return true;
    }

    public final boolean disableAll() {
        ArrayList arrayList = new ArrayList(this.mPluginInstances);
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            z |= disable((PluginInstance) arrayList.get(i), 4);
        }
        return z;
    }

    public final void handleQueryPlugins(String str) {
        String str2 = this.mAction;
        Intent intent = new Intent(str2);
        if (str != null) {
            intent.setPackage(str);
        }
        List<ResolveInfo> queryIntentServices = this.mPm.queryIntentServices(intent, 0);
        Log.d("PluginActionManager", "Found " + queryIntentServices.size() + " plugins");
        Iterator<ResolveInfo> it = queryIntentServices.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("  ", new ComponentName(serviceInfo.packageName, serviceInfo.name), "PluginActionManager");
        }
        if (queryIntentServices.size() > 1 && !this.mAllowMultiple) {
            MotionLayout$$ExternalSyntheticOutline0.m("Multiple plugins found for ", str2, "PluginActionManager");
            return;
        }
        Iterator<ResolveInfo> it2 = queryIntentServices.iterator();
        while (it2.hasNext()) {
            ServiceInfo serviceInfo2 = it2.next().serviceInfo;
            ComponentName componentName = new ComponentName(serviceInfo2.packageName, serviceInfo2.name);
            PluginInstance pluginInstance = null;
            if (!this.mIsDebuggable && !isPluginPrivileged(componentName)) {
                Log.w("PluginActionManager", "Plugin cannot be loaded on production build: " + componentName);
            } else if (this.mPluginEnabler.isEnabled(componentName)) {
                String packageName = componentName.getPackageName();
                try {
                    if (this.mPm.checkPermission("com.android.systemui.permission.PLUGIN", packageName) != 0) {
                        Log.d("PluginActionManager", "Plugin doesn't have permission: " + packageName);
                    } else {
                        ApplicationInfo applicationInfoAsUser = this.mAllowMultipleUsers ? this.mPm.getApplicationInfoAsUser(packageName, 0, ActivityManager.getCurrentUser()) : this.mPm.getApplicationInfo(packageName, 0);
                        Log.d("PluginActionManager", "createPlugin: " + componentName);
                        try {
                            pluginInstance = this.mPluginInstanceFactory.create(this.mContext, applicationInfoAsUser, componentName, this.mPluginClass, this.mListener, this.mDisplayId);
                        } catch (VersionInfo.InvalidVersionException e) {
                            reportInvalidVersion(componentName, componentName.getClassName(), e);
                        }
                    }
                } catch (Throwable th) {
                    Log.w("PluginActionManager", "Couldn't load plugin: " + componentName, th);
                }
            } else {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Plugin is not enabled, aborting load: ", componentName, "PluginActionManager");
            }
            if (pluginInstance != null) {
                this.mPluginInstances.add(pluginInstance);
                this.mMainExecutor.execute(new PluginActionManager$$ExternalSyntheticLambda2(this, pluginInstance, 1));
            }
        }
    }

    public final boolean isPluginPrivileged(ComponentName componentName) {
        Iterator it = this.mPrivilegedPlugins.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString == null) {
                if (str.equals(componentName.getPackageName())) {
                    return true;
                }
            } else if (unflattenFromString.equals(componentName)) {
                return true;
            }
        }
        return false;
    }

    public final void loadAll() {
        Log.d("PluginActionManager", "startListening");
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.shared.plugins.PluginActionManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PluginActionManager.m2108$r8$lambda$5F1fdvH66AXKG8BByIAWoLe8U(PluginActionManager.this);
            }
        });
    }

    public final void removePkg(String str) {
        for (int size = this.mPluginInstances.size() - 1; size >= 0; size--) {
            PluginInstance pluginInstance = this.mPluginInstances.get(size);
            if (pluginInstance.mComponentName.getPackageName().equals(str)) {
                this.mMainExecutor.execute(new PluginActionManager$$ExternalSyntheticLambda2(this, pluginInstance, 2));
                this.mPluginInstances.remove(size);
            }
        }
    }

    public final void reportInvalidVersion(ComponentName componentName, String str, VersionInfo.InvalidVersionException invalidVersionException) {
        Notification.Builder color = new Notification.Builder(this.mContext, "ALR").setStyle(new Notification.BigTextStyle()).setSmallIcon(Resources.getSystem().getIdentifier("stat_sys_warning", BriefViewController.URI_PATH_DRAWABLE, "android")).setWhen(0L).setShowWhen(false).setVisibility(1).setColor(this.mContext.getColor(Resources.getSystem().getIdentifier("system_notification_accent_color", "color", "android")));
        try {
            str = this.mPm.getServiceInfo(componentName, 0).loadLabel(this.mPm).toString();
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (invalidVersionException.isTooNew()) {
            Notification.Builder contentTitle = color.setContentTitle("Plugin \"" + str + "\" is too new");
            StringBuilder sb = new StringBuilder("Check to see if an OTA is available.\n");
            sb.append(invalidVersionException.getMessage());
            contentTitle.setContentText(sb.toString());
        } else {
            Notification.Builder contentTitle2 = color.setContentTitle("Plugin \"" + str + "\" is too old");
            StringBuilder sb2 = new StringBuilder("Contact plugin developer to get an updated version.\n");
            sb2.append(invalidVersionException.getMessage());
            contentTitle2.setContentText(sb2.toString());
        }
        color.addAction(new Notification.Action.Builder((Icon) null, "Disable plugin", PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.android.systemui.action.DISABLE_PLUGIN").setData(Uri.parse("package://" + componentName.flattenToString())), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)).build());
        this.mNotificationManager.notify(6, color.build());
        Log.w("PluginActionManager", "Error loading plugin; " + invalidVersionException.getMessage());
    }

    public final String toString() {
        return "PluginActionManager@" + hashCode() + " (action=" + this.mAction + ")";
    }

    private PluginActionManager(Context context, PackageManager packageManager, String str, PluginListener<Plugin> pluginListener, Class<Plugin> cls, boolean z, Executor executor, Executor executor2, boolean z2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory, boolean z3, int i) {
        ArraySet arraySet = new ArraySet();
        this.mPrivilegedPlugins = arraySet;
        this.mPluginInstances = new ArrayList<>();
        this.mPluginClass = cls;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mContext = context;
        this.mPm = packageManager;
        this.mAction = str;
        this.mListener = pluginListener;
        this.mAllowMultiple = z;
        this.mNotificationManager = notificationManager;
        this.mPluginEnabler = pluginEnabler;
        this.mPluginInstanceFactory = factory;
        arraySet.addAll(list);
        this.mIsDebuggable = z2;
        this.mAllowMultipleUsers = z3;
        this.mDisplayId = i;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PluginContextWrapper extends ContextWrapper {
        public final ClassLoader mClassLoader;
        public LayoutInflater mInflater;

        public PluginContextWrapper(Context context, ClassLoader classLoader) {
            super(context);
            this.mClassLoader = classLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public final ClassLoader getClassLoader() {
            return this.mClassLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public final Object getSystemService(String str) {
            if (!"layout_inflater".equals(str)) {
                return getBaseContext().getSystemService(str);
            }
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return this.mInflater;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public final Context getApplicationContext() {
            return this;
        }
    }
}
