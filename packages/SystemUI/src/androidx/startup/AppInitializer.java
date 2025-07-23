package androidx.startup;

import android.content.Context;
import android.os.Bundle;
import androidx.tracing.Trace;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                try {
                    if (sInstance == null) {
                        sInstance = new AppInitializer(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public final void discoverAndInitialize(Bundle bundle) {
        String string = this.mContext.getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (Initializer.class.isAssignableFrom(cls)) {
                            ((HashSet) this.mDiscovered).add(cls);
                        }
                    }
                }
                Iterator it = ((HashSet) this.mDiscovered).iterator();
                while (it.hasNext()) {
                    doInitialize((Class) it.next(), hashSet);
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
                android.os.Trace.beginSection(cls.getSimpleName());
            } finally {
                android.os.Trace.endSection();
            }
        }
        HashSet hashSet = (HashSet) set;
        if (hashSet.contains(cls)) {
            throw new IllegalStateException("Cannot initialize " + cls.getName() + ". Cycle detected.");
        }
        if (((HashMap) this.mInitialized).containsKey(cls)) {
            obj = ((HashMap) this.mInitialized).get(cls);
        } else {
            hashSet.add(cls);
            try {
                Class[] clsArr = new Class[0];
                Initializer initializer = (Initializer) cls.getDeclaredConstructor(null).newInstance(null);
                List<Class> dependencies = initializer.dependencies();
                if (!dependencies.isEmpty()) {
                    for (Class cls2 : dependencies) {
                        if (!((HashMap) this.mInitialized).containsKey(cls2)) {
                            doInitialize(cls2, set);
                        }
                    }
                }
                obj = initializer.create(this.mContext);
                hashSet.remove(cls);
                ((HashMap) this.mInitialized).put(cls, obj);
            } catch (Throwable th) {
                throw new StartupException(th);
            }
        }
        return obj;
    }
}
