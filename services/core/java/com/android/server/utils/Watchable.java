package com.android.server.utils;

import android.os.Build;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import java.lang.reflect.Field;

public interface Watchable {
    static void verifyWatchedAttributes(Object obj, Watcher watcher, boolean z) {
        if (Build.IS_ENG || Build.IS_USERDEBUG) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                Watched watched = (Watched) field.getAnnotation(Watched.class);
                if (watched != null) {
                    String str = obj.getClass().getName() + "." + field.getName();
                    try {
                        field.setAccessible(true);
                        Object obj2 = field.get(obj);
                        if (obj2 instanceof Watchable) {
                            Watchable watchable = (Watchable) obj2;
                            if (watchable != null && !watchable.isRegisteredObserver(watcher)) {
                                String str2 = "Watchable " + str + " missing an observer";
                                if (!z) {
                                    throw new RuntimeException(str2);
                                }
                                Log.e("Watchable", str2);
                            }
                        } else if (watched.manual()) {
                            continue;
                        } else {
                            String str3 =
                                    "@Watched annotated field "
                                            + str
                                            + " is not a watchable type and is not flagged for"
                                            + " manual watching.";
                            if (!z) {
                                throw new RuntimeException(str3);
                            }
                            Log.e("Watchable", str3);
                        }
                    } catch (IllegalAccessException unused) {
                        String m =
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "Watchable ", str, " not visible");
                        if (!z) {
                            throw new RuntimeException(m);
                        }
                        Log.e("Watchable", m);
                    }
                }
            }
        }
    }

    void dispatchChange(Watchable watchable);

    boolean isRegisteredObserver(Watcher watcher);

    void registerObserver(Watcher watcher);

    void unregisterObserver(Watcher watcher);
}
