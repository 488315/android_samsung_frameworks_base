package com.samsung.android.server.util;

import android.content.Context;
import android.util.Slog;
import com.android.server.wm.ActivityTaskManagerService;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class SafetySystemService {

    public interface Callback {
        void onSystemReady(ActivityTaskManagerService activityTaskManagerService);
    }

    public abstract class LazyHolder {
        public static final Manager sSingleton = new Manager();
    }

    public static void onSystemReady(ActivityTaskManagerService activityTaskManagerService, Context context) {
        LazyHolder.sSingleton.onSystemReady(activityTaskManagerService, context);
    }

    public static void registerForSystemReady(Callback callback) {
        LazyHolder.sSingleton.registerCallback(callback);
    }

    public static Object getSystemService(Class cls) {
        return LazyHolder.sSingleton.getSystemService(cls);
    }

    public static Context getSystemContext() {
        return LazyHolder.sSingleton.mSystemContext;
    }

    public static void warning(String str, String str2) {
        if (str2 == null) {
            Slog.w(str, "Should be called after system ready.");
            return;
        }
        Slog.w(str, str2 + " should be called after system ready.");
    }

    public class Manager {
        public ActivityTaskManagerService mAtmService;
        public HashSet mCallbacks;
        public Context mSystemContext;
        public boolean mSystemReady;

        public Manager() {
        }

        public void onSystemReady(ActivityTaskManagerService activityTaskManagerService, Context context) {
            this.mAtmService = activityTaskManagerService;
            this.mSystemContext = context;
            synchronized (this) {
                this.mSystemReady = true;
            }
            HashSet hashSet = this.mCallbacks;
            if (hashSet != null) {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onSystemReady(this.mAtmService);
                }
                this.mCallbacks = null;
            }
        }

        public void registerCallback(Callback callback) {
            synchronized (this) {
                if (!this.mSystemReady) {
                    if (this.mCallbacks == null) {
                        this.mCallbacks = new HashSet();
                    }
                    this.mCallbacks.add(callback);
                    return;
                }
                callback.onSystemReady(this.mAtmService);
            }
        }

        public Object getSystemService(Class cls) {
            synchronized (this) {
                if (!this.mSystemReady) {
                    SafetySystemService.warning("SafetySystemService", cls.getSimpleName() + " service");
                    return null;
                }
                return this.mSystemContext.getSystemService(cls);
            }
        }
    }
}
