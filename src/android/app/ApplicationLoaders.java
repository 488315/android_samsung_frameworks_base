package android.app;

import android.content.pm.SharedLibraryInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.GraphicsEnvironment;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.internal.os.ClassLoaderFactory;
import dalvik.system.PathClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ApplicationLoaders {
    private static final String TAG = "ApplicationLoaders";
    private static final ApplicationLoaders gApplicationLoaders = new ApplicationLoaders();
    private final ArrayMap<String, ClassLoader> mLoaders = new ArrayMap<>();
    private Map<String, CachedClassLoader> mSystemLibsCacheMap = null;

    public static ApplicationLoaders getDefault() {
        return gApplicationLoaders;
    }

    ClassLoader getClassLoader(String str, int i, boolean z, String str2, String str3, ClassLoader classLoader, String str4) {
        return getClassLoaderWithSharedLibraries(str, i, z, str2, str3, classLoader, str4, null, null, null);
    }

    ClassLoader getClassLoaderWithSharedLibraries(String str, int i, boolean z, String str2, String str3, ClassLoader classLoader, String str4, List<ClassLoader> list, List<String> list2, List<ClassLoader> list3) {
        return getClassLoader(str, i, z, str2, str3, classLoader, str, str4, list, list2, list3);
    }

    ClassLoader getSharedLibraryClassLoaderWithSharedLibraries(String str, int i, boolean z, String str2, String str3, ClassLoader classLoader, String str4, List<ClassLoader> list, List<ClassLoader> list2) {
        ClassLoader cachedNonBootclasspathSystemLib = getCachedNonBootclasspathSystemLib(str, classLoader, str4, list);
        if (cachedNonBootclasspathSystemLib != null) {
            return cachedNonBootclasspathSystemLib;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("ALL");
        return getClassLoaderWithSharedLibraries(str, i, z, str2, str3, classLoader, str4, list, arrayList, list2);
    }

    private ClassLoader getClassLoader(String str, int i, boolean z, String str2, String str3, ClassLoader classLoader, String str4, String str5, List<ClassLoader> list, List<String> list2, List<ClassLoader> list3) {
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        synchronized (this.mLoaders) {
            ClassLoader classLoader2 = classLoader == null ? parent : classLoader;
            if (classLoader2 == parent) {
                ClassLoader classLoader3 = this.mLoaders.get(str4);
                if (classLoader3 != null) {
                    return classLoader3;
                }
                Trace.traceBegin(64L, str);
                ClassLoader classLoaderCreateClassLoader = ClassLoaderFactory.createClassLoader(str, str2, str3, classLoader2, i, z, str5, list, list2, list3);
                Trace.traceEnd(64L);
                Trace.traceBegin(64L, "setLayerPaths");
                GraphicsEnvironment.getInstance().setLayerPaths(classLoaderCreateClassLoader, str2, str3);
                Trace.traceEnd(64L);
                if (str4 != null) {
                    this.mLoaders.put(str4, classLoaderCreateClassLoader);
                }
                return classLoaderCreateClassLoader;
            }
            Trace.traceBegin(64L, str);
            ClassLoader classLoaderCreateClassLoader2 = ClassLoaderFactory.createClassLoader(str, null, classLoader2, str5, list, null);
            Trace.traceEnd(64L);
            return classLoaderCreateClassLoader2;
        }
    }

    public void createAndCacheNonBootclasspathSystemClassLoaders(List<SharedLibraryInfo> list) {
        if (this.mSystemLibsCacheMap != null) {
            throw new IllegalStateException("Already cached.");
        }
        this.mSystemLibsCacheMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            createAndCacheNonBootclasspathSystemClassLoader(list.get(i));
        }
    }

    private void createAndCacheNonBootclasspathSystemClassLoader(SharedLibraryInfo sharedLibraryInfo) {
        ArrayList arrayList;
        String path = sharedLibraryInfo.getPath();
        List<SharedLibraryInfo> dependencies = sharedLibraryInfo.getDependencies();
        if (dependencies != null) {
            ArrayList arrayList2 = new ArrayList(dependencies.size());
            Iterator<SharedLibraryInfo> it = dependencies.iterator();
            while (it.hasNext()) {
                String path2 = it.next().getPath();
                CachedClassLoader cachedClassLoader = this.mSystemLibsCacheMap.get(path2);
                if (cachedClassLoader == null) {
                    throw new IllegalStateException("Failed to find dependency " + path2 + " of cachedlibrary " + path);
                }
                arrayList2.add(cachedClassLoader.loader);
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        ClassLoader classLoader = getClassLoader(path, Build.VERSION.SDK_INT, true, null, null, null, null, null, arrayList, null, null);
        if (classLoader == null) {
            throw new IllegalStateException("Failed to cache " + path);
        }
        CachedClassLoader cachedClassLoader2 = new CachedClassLoader();
        cachedClassLoader2.loader = classLoader;
        cachedClassLoader2.sharedLibraries = arrayList;
        Log.d(TAG, "Created zygote-cached class loader: " + path);
        this.mSystemLibsCacheMap.put(path, cachedClassLoader2);
    }

    private static boolean sharedLibrariesEquals(List<ClassLoader> list, List<ClassLoader> list2) {
        if (list == null) {
            return list2 == null;
        }
        return list.equals(list2);
    }

    public ClassLoader getCachedNonBootclasspathSystemLib(String str, ClassLoader classLoader, String str2, List<ClassLoader> list) {
        CachedClassLoader cachedClassLoader;
        Map<String, CachedClassLoader> map = this.mSystemLibsCacheMap;
        if (map == null || classLoader != null || str2 != null || (cachedClassLoader = map.get(str)) == null) {
            return null;
        }
        if (!sharedLibrariesEquals(list, cachedClassLoader.sharedLibraries)) {
            Log.w(TAG, "Unexpected environment loading cached library " + str + " (real|cached): (" + list + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + cachedClassLoader.sharedLibraries + NavigationBarInflaterView.KEY_CODE_END);
            return null;
        }
        Log.d(TAG, "Returning zygote-cached class loader: " + str);
        return cachedClassLoader.loader;
    }

    public ClassLoader createAndCacheWebViewClassLoader(String str, String str2, String str3) {
        return getClassLoader(str, Build.VERSION.SDK_INT, false, str2, null, null, str3, null, null, null, null);
    }

    void addPath(ClassLoader classLoader, String str) {
        if (!(classLoader instanceof PathClassLoader)) {
            throw new IllegalStateException("class loader is not a PathClassLoader");
        }
        ((PathClassLoader) classLoader).addDexPath(str);
    }

    void addNative(ClassLoader classLoader, Collection<String> collection) {
        if (!(classLoader instanceof PathClassLoader)) {
            throw new IllegalStateException("class loader is not a PathClassLoader");
        }
        ((PathClassLoader) classLoader).addNativePath(collection);
    }

    private static class CachedClassLoader {
        ClassLoader loader;
        List<ClassLoader> sharedLibraries;

        private CachedClassLoader() {
        }
    }
}
