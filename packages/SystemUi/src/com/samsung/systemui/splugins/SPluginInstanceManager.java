package com.samsung.systemui.splugins;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SVersionInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SPluginInstanceManager<T extends SPlugin> {
    private static final boolean DEBUG = false;
    public static final int PLUGIN_DISCONNECTED_REASON_CHANGE = 2;
    public static final int PLUGIN_DISCONNECTED_REASON_DELETED = 0;
    public static final int PLUGIN_DISCONNECTED_REASON_UPDATE = 1;
    public static final int PLUGIN_LOAD_FAILED_REASON_VERSION = 0;
    public static final String PLUGIN_PERMISSION = "com.samsung.systemui.permission.SPLUGIN";
    private static final String TAG = "PluginInstanceManager";
    private final boolean isDebuggable;
    private final String mAction;
    private final ActivityManagerProxy mActivityManagerProxy;
    private final boolean mAllowMultiple;
    private final boolean mAllowMultipleUsers;
    private final Context mContext;
    private boolean mIsPkgChanged;
    private final SPluginListener<T> mListener;
    final SPluginInstanceManager<T>.MainHandler mMainHandler;
    private final SPluginManagerImpl mManager;
    final SPluginInstanceManager<T>.PluginHandler mPluginHandler;
    private final PackageManager mPm;
    private final SPluginPolicyInteractor mPolicyInteractor;
    private final SVersionInfo mVersion;
    private final ArraySet<String> mWhitelistedPlugins;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class MainHandler extends Handler {
        private static final int PLUGIN_CONNECTED = 1;
        private static final int PLUGIN_DISCONNECTED = 2;
        private static final int PLUGIN_UPDATED = 4;

        public MainHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            StringBuilder sb;
            long currentTimeMillis = System.currentTimeMillis();
            try {
                PluginInfo pluginInfo = (PluginInfo) message.obj;
                int i = message.what;
                if (i == 1) {
                    Log.d(SPluginInstanceManager.TAG, "[" + SPluginInstanceManager.this.mAction + "], [PLUGIN_CONNECTED] " + pluginInfo.mClass);
                    SPluginPrefs.setHasPlugins(SPluginInstanceManager.this.mContext);
                    SPluginInstanceManager.this.mManager.handleWtfs();
                    if (!(message.obj instanceof SPluginFragment)) {
                        ((SPlugin) pluginInfo.mPlugin).onCreate(SPluginInstanceManager.this.mContext, pluginInfo.mPluginContext);
                    }
                    SPluginInstanceManager.this.mListener.onPluginConnected((SPlugin) pluginInfo.mPlugin, pluginInfo.mPluginContext);
                } else if (i == 2) {
                    Log.d(SPluginInstanceManager.TAG, "[" + SPluginInstanceManager.this.mAction + "], [PLUGIN_DISCONNECTED] " + pluginInfo.mClass + ", mIsPkgChanged =" + SPluginInstanceManager.this.mIsPkgChanged);
                    if (SPluginInstanceManager.this.mIsPkgChanged) {
                        SPluginInstanceManager.this.mListener.onPluginDisconnected((SPlugin) pluginInfo.mPlugin, 2);
                    } else {
                        SPluginInstanceManager.this.mListener.onPluginDisconnected((SPlugin) pluginInfo.mPlugin, 0);
                    }
                    T t = pluginInfo.mPlugin;
                    if (!(t instanceof SPluginFragment)) {
                        ((SPlugin) t).onDestroy();
                    }
                } else if (i != 4) {
                    super.handleMessage(message);
                } else {
                    Log.d(SPluginInstanceManager.TAG, "[" + SPluginInstanceManager.this.mAction + "], [PLUGIN_UPDATED], mPackage " + pluginInfo.mPackage);
                    SPluginInstanceManager.this.mListener.onPluginDisconnected((SPlugin) pluginInfo.mPlugin, 1);
                    T t2 = pluginInfo.mPlugin;
                    if (!(t2 instanceof SPluginFragment)) {
                        ((SPlugin) t2).onDestroy();
                    }
                }
                sb = new StringBuilder("[");
            } catch (Throwable th) {
                try {
                    Log.e(SPluginInstanceManager.TAG, "[" + SPluginInstanceManager.this.mAction + "] what=" + message.what, th);
                    sb = new StringBuilder("[");
                } catch (Throwable th2) {
                    Log.d(SPluginInstanceManager.TAG, "[" + SPluginInstanceManager.this.mAction + "], what=" + message.what + " elapsed=" + (System.currentTimeMillis() - currentTimeMillis) + "ms");
                    throw th2;
                }
            }
            sb.append(SPluginInstanceManager.this.mAction);
            sb.append("], what=");
            sb.append(message.what);
            sb.append(" elapsed=");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            sb.append("ms");
            Log.d(SPluginInstanceManager.TAG, sb.toString());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PluginHandler extends Handler {
        private static final int QUERY_ALL = 1;
        private static final int QUERY_PKG = 2;
        private static final int REMOVE_PKG = 3;
        private static final int UPDATE_PKG = 5;
        private final ArrayList<PluginInfo<T>> mPlugins;

        public PluginHandler(Looper looper) {
            super(looper);
            this.mPlugins = new ArrayList<>();
        }

        private SVersionInfo checkVersion(Class<?> cls, T t, SVersionInfo sVersionInfo) {
            SVersionInfo addClass = new SVersionInfo().addClass(cls);
            if (addClass.hasVersionInfo()) {
                sVersionInfo.checkVersion(addClass);
                return addClass;
            }
            if (t.getVersion() == sVersionInfo.getDefaultVersion()) {
                return null;
            }
            throw new SVersionInfo.InvalidVersionException("Invalid legacy version", false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleQueryPlugins(String str) {
            Intent intent = new Intent(SPluginInstanceManager.this.mAction);
            if (str != null) {
                if (!str.startsWith("com.samsung")) {
                    return;
                } else {
                    intent.setPackage(str);
                }
            }
            List queryIntentServicesAsUser = SPluginInstanceManager.this.mPm.queryIntentServicesAsUser(intent, 0, SPluginInstanceManager.this.mActivityManagerProxy.getCurrentUser());
            if (queryIntentServicesAsUser.size() > 1 && !SPluginInstanceManager.this.mAllowMultiple) {
                Log.w(SPluginInstanceManager.TAG, "Multiple plugins found for " + SPluginInstanceManager.this.mAction);
                return;
            }
            if (queryIntentServicesAsUser.size() == 0 && str != null) {
                SPluginInstanceManager.this.mPolicyInteractor.applyUrgentOSUpgradePolicy(str);
            }
            Iterator it = queryIntentServicesAsUser.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                PluginInfo<T> handleLoadPlugin = handleLoadPlugin(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                if (handleLoadPlugin != null) {
                    SPluginInstanceManager.this.mMainHandler.obtainMessage(1, handleLoadPlugin).sendToTarget();
                    this.mPlugins.add(handleLoadPlugin);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public PluginInfo<T> handleLoadPlugin(ComponentName componentName) {
            String packageName = componentName.getPackageName();
            String className = componentName.getClassName();
            try {
                ApplicationInfo applicationInfoAsUser = SPluginInstanceManager.this.mAllowMultipleUsers ? SPluginInstanceManager.this.mPm.getApplicationInfoAsUser(packageName, 0, SPluginInstanceManager.this.mActivityManagerProxy.getCurrentUser()) : SPluginInstanceManager.this.mPm.getApplicationInfo(packageName, 0);
                if (SPluginInstanceManager.this.mPm.checkPermission(SPluginInstanceManager.PLUGIN_PERMISSION, packageName) != 0) {
                    Log.d(SPluginInstanceManager.TAG, "Plugin doesn't have permission: " + packageName);
                    return null;
                }
                ClassLoader classLoader = SPluginInstanceManager.this.mManager.getClassLoader(applicationInfoAsUser);
                PluginContextWrapper pluginContextWrapper = new PluginContextWrapper(SPluginInstanceManager.this.mContext.createApplicationContext(applicationInfoAsUser, 0), classLoader);
                Class<?> cls = Class.forName(className, true, classLoader);
                SPlugin sPlugin = (SPlugin) cls.newInstance();
                try {
                    SVersionInfo checkVersion = checkVersion(cls, sPlugin, SPluginInstanceManager.this.mVersion);
                    SPluginInstanceManager.this.mPolicyInteractor.onPluginLoaded(packageName);
                    return new PluginInfo<>(packageName, className, sPlugin, pluginContextWrapper, checkVersion);
                } catch (SVersionInfo.InvalidVersionException unused) {
                    SPluginInstanceManager.this.mPolicyInteractor.onPluginLoadFailed(packageName);
                    SPluginInstanceManager.this.mListener.onPluginLoadFailed(0);
                    return null;
                }
            } catch (Throwable th) {
                Log.w(SPluginInstanceManager.TAG, "Couldn't load plugin: " + packageName, th);
                return null;
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String str = (String) message.obj;
            int i = message.what;
            if (i == 1) {
                for (int size = this.mPlugins.size() - 1; size >= 0; size--) {
                    SPluginInstanceManager.this.mMainHandler.obtainMessage(2, this.mPlugins.get(size)).sendToTarget();
                    this.mPlugins.remove(size);
                }
                handleQueryPlugins(null);
                return;
            }
            if (i == 2) {
                if (SPluginInstanceManager.this.mAllowMultiple || this.mPlugins.size() == 0) {
                    handleQueryPlugins(str);
                    return;
                }
                return;
            }
            if (i == 3) {
                for (int size2 = this.mPlugins.size() - 1; size2 >= 0; size2--) {
                    PluginInfo<T> pluginInfo = this.mPlugins.get(size2);
                    if (pluginInfo.mPackage.equals(str)) {
                        SPluginInstanceManager.this.mMainHandler.obtainMessage(2, pluginInfo).sendToTarget();
                        this.mPlugins.remove(size2);
                    }
                }
                return;
            }
            if (i != 5) {
                super.handleMessage(message);
                return;
            }
            for (int size3 = this.mPlugins.size() - 1; size3 >= 0; size3--) {
                PluginInfo<T> pluginInfo2 = this.mPlugins.get(size3);
                if (pluginInfo2.mPackage.equals(str)) {
                    SPluginInstanceManager.this.mMainHandler.obtainMessage(4, pluginInfo2).sendToTarget();
                    this.mPlugins.remove(size3);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PluginInfo<T> {
        private String mClass;
        String mPackage;
        T mPlugin;
        private final Context mPluginContext;
        private final SVersionInfo mVersion;

        public PluginInfo(String str, String str2, T t, Context context, SVersionInfo sVersionInfo) {
            this.mPlugin = t;
            this.mClass = str2;
            this.mPackage = str;
            this.mPluginContext = context;
            this.mVersion = sVersionInfo;
        }
    }

    public SPluginInstanceManager(Context context, String str, SPluginListener<T> sPluginListener, boolean z, boolean z2, Looper looper, SVersionInfo sVersionInfo, SPluginManagerImpl sPluginManagerImpl, SPluginPolicyInteractor sPluginPolicyInteractor) {
        this(context, context.getPackageManager(), str, sPluginListener, z, z2, looper, sVersionInfo, sPluginManagerImpl, Build.IS_DEBUGGABLE, sPluginManagerImpl.getAllowedPlugins(), sPluginPolicyInteractor);
    }

    private void disable(PluginInfo<T> pluginInfo, int i) {
        try {
            this.mListener.onPluginDisconnected(pluginInfo.mPlugin, 0);
            T t = pluginInfo.mPlugin;
            if (!(t instanceof SPluginFragment)) {
                t.onDestroy();
            }
        } catch (Throwable th) {
            Log.w(TAG, th.toString());
        }
        ComponentName componentName = new ComponentName(pluginInfo.mPackage, ((PluginInfo) pluginInfo).mClass);
        Log.w(TAG, "Disabling plugin " + componentName.flattenToShortString());
        this.mManager.getPluginEnabler().setDisabled(componentName, i);
    }

    public boolean checkAndDisable(String str) {
        Iterator it = new ArrayList(((PluginHandler) this.mPluginHandler).mPlugins).iterator();
        boolean z = false;
        while (it.hasNext()) {
            PluginInfo<T> pluginInfo = (PluginInfo) it.next();
            if (str.startsWith(pluginInfo.mPackage)) {
                disable(pluginInfo, 2);
                z = true;
            }
        }
        return z;
    }

    public <T> boolean dependsOn(SPlugin sPlugin, Class<T> cls) {
        Iterator it = new ArrayList(((PluginHandler) this.mPluginHandler).mPlugins).iterator();
        while (it.hasNext()) {
            PluginInfo pluginInfo = (PluginInfo) it.next();
            if (pluginInfo.mPlugin.getClass().getName().equals(sPlugin.getClass().getName())) {
                return pluginInfo.mVersion != null && pluginInfo.mVersion.hasClass(cls);
            }
        }
        return false;
    }

    public void destroy() {
        Iterator it = new ArrayList(((PluginHandler) this.mPluginHandler).mPlugins).iterator();
        while (it.hasNext()) {
            this.mMainHandler.obtainMessage(2, (PluginInfo) it.next()).sendToTarget();
        }
        this.mActivityManagerProxy.unregister();
    }

    public boolean disableAll(ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList(((PluginHandler) this.mPluginHandler).mPlugins);
        for (int i = 0; i < arrayList2.size(); i++) {
            PluginInfo<T> pluginInfo = (PluginInfo) arrayList2.get(i);
            if (arrayList == null || !arrayList.contains(pluginInfo.mPackage)) {
                disable(pluginInfo, 3);
            }
        }
        return arrayList2.size() != 0;
    }

    public PluginInfo<T> getPlugin() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Must be called from UI thread");
        }
        this.mPluginHandler.handleQueryPlugins(null);
        if (((PluginHandler) this.mPluginHandler).mPlugins.size() <= 0) {
            return null;
        }
        this.mMainHandler.removeMessages(1);
        PluginInfo<T> pluginInfo = (PluginInfo) ((PluginHandler) this.mPluginHandler).mPlugins.get(0);
        SPluginPrefs.setHasPlugins(this.mContext);
        pluginInfo.mPlugin.onCreate(this.mContext, ((PluginInfo) pluginInfo).mPluginContext);
        return pluginInfo;
    }

    public void loadAll() {
        this.mIsPkgChanged = true;
        this.mPluginHandler.sendEmptyMessage(1);
    }

    public void onPackageChange(String str) {
        this.mIsPkgChanged = true;
        this.mPluginHandler.obtainMessage(3, str).sendToTarget();
        this.mPluginHandler.obtainMessage(2, str).sendToTarget();
    }

    public void onPackageRemoved(String str) {
        this.mIsPkgChanged = false;
        this.mPluginHandler.obtainMessage(3, str).sendToTarget();
    }

    public void onPackageUpdated(String str) {
        this.mPluginHandler.obtainMessage(5, str).sendToTarget();
    }

    public String toString() {
        return String.format("%s@%s (action=%s)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this.mAction);
    }

    public SPluginInstanceManager(Context context, PackageManager packageManager, String str, SPluginListener<T> sPluginListener, boolean z, boolean z2, Looper looper, SVersionInfo sVersionInfo, SPluginManagerImpl sPluginManagerImpl, boolean z3, String[] strArr, SPluginPolicyInteractor sPluginPolicyInteractor) {
        ArraySet<String> arraySet = new ArraySet<>();
        this.mWhitelistedPlugins = arraySet;
        this.mIsPkgChanged = false;
        this.mMainHandler = new MainHandler(Looper.getMainLooper());
        this.mPluginHandler = new PluginHandler(looper);
        this.mManager = sPluginManagerImpl;
        this.mContext = context;
        this.mPm = packageManager;
        this.mAction = str;
        this.mListener = sPluginListener;
        this.mAllowMultiple = z;
        this.mAllowMultipleUsers = z2;
        this.mVersion = sVersionInfo;
        arraySet.addAll(Arrays.asList(strArr));
        this.isDebuggable = z3;
        this.mActivityManagerProxy = new ActivityManagerProxy();
        this.mPolicyInteractor = sPluginPolicyInteractor;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PluginContextWrapper extends ContextWrapper {
        private final ClassLoader mClassLoader;
        private LayoutInflater mInflater;

        public PluginContextWrapper(Context context, ClassLoader classLoader) {
            super(context);
            this.mClassLoader = classLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public ClassLoader getClassLoader() {
            return this.mClassLoader;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Object getSystemService(String str) {
            if (!"layout_inflater".equals(str)) {
                return getBaseContext().getSystemService(str);
            }
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return this.mInflater;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Context getApplicationContext() {
            return this;
        }
    }
}
