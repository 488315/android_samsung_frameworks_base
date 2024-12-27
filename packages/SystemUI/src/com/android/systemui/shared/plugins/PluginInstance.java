package com.android.systemui.shared.plugins;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Display;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginFragment;
import com.android.systemui.plugins.PluginLifecycleManager;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginManagerImpl;
import com.android.systemui.shared.plugins.VersionInfo;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginInstance implements PluginLifecycleManager {
    public static final Map sClassLoaders = new ArrayMap();
    public final Context mAppContext;
    public final ComponentName mComponentName;
    public final PluginListener mListener;
    public BiConsumer mLogConsumer = null;
    public Plugin mPlugin;
    public PluginActionManager.PluginContextWrapper mPluginContext;
    public final PluginFactory mPluginFactory;
    public final String mTag;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final ClassLoader mBaseClassLoader;
        public final InstanceFactory mInstanceFactory;
        public final boolean mIsDebug;
        public final List mPrivilegedPlugins;
        public final VersionChecker mVersionChecker;

        public Factory(ClassLoader classLoader, InstanceFactory instanceFactory, VersionChecker versionChecker, List<String> list, boolean z) {
            this.mPrivilegedPlugins = list;
            this.mBaseClassLoader = classLoader;
            this.mInstanceFactory = instanceFactory;
            this.mVersionChecker = versionChecker;
            this.mIsDebug = z;
        }

        public final PluginInstance create(Context context, final ApplicationInfo applicationInfo, ComponentName componentName, Class cls, PluginListener pluginListener, int i) {
            return new PluginInstance(context, pluginListener, componentName, new PluginFactory(context, this.mInstanceFactory, applicationInfo, componentName, this.mVersionChecker, cls, new Supplier() { // from class: com.android.systemui.shared.plugins.PluginInstance$Factory$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    PluginInstance.Factory factory = PluginInstance.Factory.this;
                    ApplicationInfo applicationInfo2 = applicationInfo;
                    ClassLoader classLoader = factory.mBaseClassLoader;
                    if (!factory.mIsDebug) {
                        String str = applicationInfo2.packageName;
                        for (String str2 : factory.mPrivilegedPlugins) {
                            ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
                            if (unflattenFromString != null) {
                                if (unflattenFromString.getPackageName().equals(str)) {
                                }
                            } else if (str2.equals(str)) {
                            }
                        }
                        Log.w("PluginInstance", "Cannot get class loader for non-privileged plugin. Src:" + applicationInfo2.sourceDir + ", pkg: " + applicationInfo2.packageName);
                        return null;
                    }
                    ArrayMap arrayMap = (ArrayMap) PluginInstance.sClassLoaders;
                    if (arrayMap.containsKey(applicationInfo2.packageName)) {
                        return (ClassLoader) arrayMap.get(applicationInfo2.packageName);
                    }
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    LoadedApk.makePaths((ActivityThread) null, true, applicationInfo2, arrayList, arrayList2);
                    String str3 = File.pathSeparator;
                    PathClassLoader pathClassLoader = new PathClassLoader(TextUtils.join(str3, arrayList), TextUtils.join(str3, arrayList2), new PluginManagerImpl.ClassLoaderFilter(classLoader, "com.android.systemui.common", "com.android.systemui.log", "com.android.systemui.plugin", "androidx.customview.poolingcontainer"));
                    arrayMap.put(applicationInfo2.packageName, pathClassLoader);
                    return pathClassLoader;
                }
            }, i), null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class InstanceFactory {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PluginFactory {
        public final ApplicationInfo mAppInfo;
        public final Supplier mClassLoaderFactory;
        public final ComponentName mComponentName;
        public final Context mContext;
        public final int mDisplayId;
        public final InstanceFactory mInstanceFactory;
        public final Class mPluginClass;
        public final VersionChecker mVersionChecker;

        public PluginFactory(Context context, InstanceFactory instanceFactory, ApplicationInfo applicationInfo, ComponentName componentName, VersionChecker versionChecker, Class<Plugin> cls, Supplier<ClassLoader> supplier, int i) {
            this.mContext = context;
            this.mInstanceFactory = instanceFactory;
            this.mAppInfo = applicationInfo;
            this.mComponentName = componentName;
            this.mVersionChecker = versionChecker;
            this.mPluginClass = cls;
            this.mClassLoaderFactory = supplier;
            this.mDisplayId = i;
        }

        public final VersionInfo checkVersion(Plugin plugin) {
            if (plugin == null) {
                plugin = createPlugin();
            }
            Class<?> cls = plugin.getClass();
            Class cls2 = this.mPluginClass;
            ((VersionCheckerImpl) this.mVersionChecker).getClass();
            VersionInfo versionInfo = new VersionInfo();
            if (versionInfo.mDefault == null) {
                versionInfo.mDefault = cls2;
            }
            versionInfo.addClass(cls2, false);
            VersionInfo versionInfo2 = new VersionInfo();
            if (versionInfo2.mDefault == null) {
                versionInfo2.mDefault = cls;
            }
            versionInfo2.addClass(cls, false);
            if (!(!versionInfo2.mVersions.isEmpty())) {
                if (plugin.getVersion() == ((VersionInfo.Version) versionInfo.mVersions.get(versionInfo.mDefault)).mVersion) {
                    return null;
                }
                throw new VersionInfo.InvalidVersionException("Invalid legacy version", false);
            }
            ArrayMap arrayMap = new ArrayMap(versionInfo.mVersions);
            versionInfo2.mVersions.forEach(new BiConsumer() { // from class: com.android.systemui.shared.plugins.VersionInfo.1
                public final /* synthetic */ ArrayMap val$versions;

                public AnonymousClass1(ArrayMap arrayMap2) {
                    r2 = arrayMap2;
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    Class cls3 = (Class) obj;
                    Version version = (Version) obj2;
                    Version version2 = (Version) r2.remove(cls3);
                    if (version2 == null) {
                        VersionInfo.this.getClass();
                        ProvidesInterface providesInterface = (ProvidesInterface) cls3.getDeclaredAnnotation(ProvidesInterface.class);
                        version2 = providesInterface != null ? new Version(providesInterface.version(), false) : null;
                    }
                    if (version2 == null) {
                        throw new InvalidVersionException(cls3.getSimpleName().concat(" does not provide an interface"), false);
                    }
                    int i = version.mVersion;
                    int i2 = version2.mVersion;
                    if (i2 != i) {
                        int i3 = version.mVersion;
                        throw new InvalidVersionException(cls3, i2 < i3, i2, i3);
                    }
                }
            });
            arrayMap2.forEach(new BiConsumer(versionInfo) { // from class: com.android.systemui.shared.plugins.VersionInfo.2
                public AnonymousClass2(VersionInfo versionInfo3) {
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    Class cls3 = (Class) obj;
                    if (((Version) obj2).mRequired) {
                        throw new InvalidVersionException("Missing required dependency ".concat(cls3.getSimpleName()), false);
                    }
                }
            });
            return versionInfo2;
        }

        public final Plugin createPlugin() {
            try {
                Class<?> cls = Class.forName(this.mComponentName.getClassName(), true, (ClassLoader) this.mClassLoaderFactory.get());
                this.mInstanceFactory.getClass();
                Plugin plugin = (Plugin) cls.newInstance();
                Objects.toString(plugin);
                return plugin;
            } catch (ClassNotFoundException e) {
                Log.e("PluginInstance", "Failed to load plugin", e);
                return null;
            } catch (IllegalAccessException e2) {
                Log.e("PluginInstance", "Failed to load plugin", e2);
                return null;
            } catch (InstantiationException e3) {
                Log.e("PluginInstance", "Failed to load plugin", e3);
                return null;
            }
        }

        public final PluginActionManager.PluginContextWrapper createPluginContext() {
            try {
                ClassLoader classLoader = (ClassLoader) this.mClassLoaderFactory.get();
                int i = this.mDisplayId;
                if (i == 0) {
                    return new PluginActionManager.PluginContextWrapper(this.mContext.createApplicationContext(this.mAppInfo, 0), classLoader);
                }
                Context context = this.mContext;
                Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i);
                if (display != null) {
                    context = context.createDisplayContext(display);
                }
                return new PluginActionManager.PluginContextWrapper(context.createApplicationContext(this.mAppInfo, 0), classLoader);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PluginInstance", "Failed to create plugin context", e);
                return null;
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface VersionChecker {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class VersionCheckerImpl implements VersionChecker {
    }

    public PluginInstance(Context context, PluginListener<Plugin> pluginListener, ComponentName componentName, PluginFactory pluginFactory, Plugin plugin) {
        this.mAppContext = context;
        this.mListener = pluginListener;
        this.mComponentName = componentName;
        this.mPluginFactory = pluginFactory;
        this.mPlugin = plugin;
        this.mTag = "PluginInstance[" + componentName.getShortClassName() + "]@" + Integer.toHexString(hashCode());
        if (this.mPlugin != null) {
            this.mPluginContext = pluginFactory.createPluginContext();
        }
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final String getPackage() {
        return this.mComponentName.getPackageName();
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final Plugin getPlugin() {
        return this.mPlugin;
    }

    public Context getPluginContext() {
        return this.mPluginContext;
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final synchronized void loadPlugin() {
        if (this.mPlugin != null) {
            log("Load request when already loaded");
            return;
        }
        this.mPlugin = this.mPluginFactory.createPlugin();
        PluginActionManager.PluginContextWrapper createPluginContext = this.mPluginFactory.createPluginContext();
        this.mPluginContext = createPluginContext;
        if (this.mPlugin != null && createPluginContext != null) {
            log("Loaded plugin; running callbacks");
            this.mPluginFactory.checkVersion(this.mPlugin);
            Plugin plugin = this.mPlugin;
            if (!(plugin instanceof PluginFragment)) {
                plugin.onCreate(this.mAppContext, this.mPluginContext);
            }
            this.mListener.onPluginLoaded(this.mPlugin, this.mPluginContext, this);
            return;
        }
        Log.e(this.mTag, "Requested load, but failed");
    }

    public final void log(String str) {
        BiConsumer biConsumer = this.mLogConsumer;
        if (biConsumer != null) {
            biConsumer.accept(this.mTag, str);
        }
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final void setLogFunc(BiConsumer biConsumer) {
        this.mLogConsumer = biConsumer;
    }

    public final String toString() {
        return this.mTag;
    }

    @Override // com.android.systemui.plugins.PluginLifecycleManager
    public final synchronized void unloadPlugin() {
        if (this.mPlugin == null) {
            log("Unload request when already unloaded");
            return;
        }
        log("Unloading plugin, running callbacks");
        this.mListener.onPluginUnloaded(this.mPlugin, this);
        Plugin plugin = this.mPlugin;
        if (!(plugin instanceof PluginFragment)) {
            plugin.onDestroy();
        }
        this.mPlugin = null;
        this.mPluginContext = null;
    }
}
