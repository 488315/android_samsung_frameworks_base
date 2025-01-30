package androidx.startup;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppInitializer {
    public static volatile AppInitializer sInstance;
    public static final Object sLock = new Object();
    public final Context mContext;
    public final Set mDiscovered = new HashSet();
    public final Map mInitialized = new HashMap();

    public AppInitializer(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static AppInitializer getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    public final void discoverAndInitialize(Bundle bundle) {
        Set set;
        String string = this.mContext.getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet = new HashSet();
                Iterator<String> it = bundle.keySet().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    set = this.mDiscovered;
                    if (!hasNext) {
                        break;
                    }
                    String next = it.next();
                    if (string.equals(bundle.getString(next, null))) {
                        Class<?> cls = Class.forName(next);
                        if (Initializer.class.isAssignableFrom(cls)) {
                            ((HashSet) set).add(cls);
                        }
                    }
                }
                Iterator it2 = ((HashSet) set).iterator();
                while (it2.hasNext()) {
                    doInitialize((Class) it2.next(), hashSet);
                }
            } catch (ClassNotFoundException e) {
                throw new StartupException(e);
            }
        }
    }

    public final Object doInitialize(Class cls, Set set) {
        Object obj;
        if (Trace.isEnabled()) {
            try {
                Trace.beginSection(cls.getSimpleName());
            } finally {
                Trace.endSection();
            }
        }
        HashSet hashSet = (HashSet) set;
        if (hashSet.contains(cls)) {
            throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", cls.getName()));
        }
        Map map = this.mInitialized;
        if (((HashMap) map).containsKey(cls)) {
            obj = ((HashMap) map).get(cls);
        } else {
            hashSet.add(cls);
            try {
                Initializer initializer = (Initializer) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                List<Class> dependencies = initializer.dependencies();
                if (!dependencies.isEmpty()) {
                    for (Class cls2 : dependencies) {
                        if (!((HashMap) map).containsKey(cls2)) {
                            doInitialize(cls2, set);
                        }
                    }
                }
                obj = initializer.create(this.mContext);
                hashSet.remove(cls);
                ((HashMap) map).put(cls, obj);
            } catch (Throwable th) {
                throw new StartupException(th);
            }
        }
        return obj;
    }
}
