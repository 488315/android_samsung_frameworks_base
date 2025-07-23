package android.filterfw.core;

import android.media.MediaMetrics;
import android.util.Log;
import dalvik.system.PathClassLoader;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class FilterFactory {
    private static FilterFactory mSharedFactory;
    private HashSet<String> mPackages = new HashSet<>();
    private static ClassLoader mCurrentClassLoader = Thread.currentThread().getContextClassLoader();
    private static HashSet<String> mLibraries = new HashSet<>();
    private static Object mClassLoaderGuard = new Object();
    private static final String TAG = "FilterFactory";
    private static boolean mLogVerbose = Log.isLoggable(TAG, 2);

    public static FilterFactory sharedFactory() {
        if (mSharedFactory == null) {
            mSharedFactory = new FilterFactory();
        }
        return mSharedFactory;
    }

    public static void addFilterLibrary(String str) {
        if (mLogVerbose) {
            Log.v(TAG, "Adding filter library " + str);
        }
        synchronized (mClassLoaderGuard) {
            if (mLibraries.contains(str)) {
                if (mLogVerbose) {
                    Log.v(TAG, "Library already added");
                }
            } else {
                mLibraries.add(str);
                mCurrentClassLoader = new PathClassLoader(str, mCurrentClassLoader);
            }
        }
    }

    public void addPackage(String str) {
        if (mLogVerbose) {
            Log.v(TAG, "Adding package " + str);
        }
        this.mPackages.add(str);
    }

    public Filter createFilterByClassName(String str, String str2) {
        if (mLogVerbose) {
            Log.v(TAG, "Looking up class " + str);
        }
        Iterator<String> it = this.mPackages.iterator();
        Class<?> cls = null;
        while (it.hasNext()) {
            String next = it.next();
            try {
                if (mLogVerbose) {
                    Log.v(TAG, "Trying " + next + MediaMetrics.SEPARATOR + str);
                }
                synchronized (mClassLoaderGuard) {
                    cls = mCurrentClassLoader.loadClass(next + MediaMetrics.SEPARATOR + str);
                }
            } catch (ClassNotFoundException unused) {
            }
            if (cls != null) {
                break;
            }
        }
        if (cls == null) {
            throw new IllegalArgumentException("Unknown filter class '" + str + "'!");
        }
        return createFilterByClass(cls, str2);
    }

    public Filter createFilterByClass(Class cls, String str) {
        Filter filter;
        if (!Filter.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Attempting to allocate class '" + cls + "' which is not a subclass of Filter!");
        }
        try {
            try {
                filter = (Filter) cls.getConstructor(String.class).newInstance(str);
            } catch (Throwable unused) {
                filter = null;
            }
            if (filter != null) {
                return filter;
            }
            throw new IllegalArgumentException("Could not construct the filter '" + str + "'!");
        } catch (NoSuchMethodException unused2) {
            throw new IllegalArgumentException("The filter class '" + cls + "' does not have a constructor of the form <init>(String name)!");
        }
    }
}
