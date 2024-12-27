package com.android.server.timedetector;

import android.content.Context;
import android.provider.DeviceConfig;
import android.util.ArrayMap;

import com.android.server.timezonedetector.StateChangeListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ServerFlags {
    public static ServerFlags sInstance;
    public final ArrayMap mListeners = new ArrayMap();
    public static final Optional OPTIONAL_TRUE = Optional.of(Boolean.TRUE);
    public static final Optional OPTIONAL_FALSE = Optional.of(Boolean.FALSE);
    public static final Object SLOCK = new Object();

    public ServerFlags(Context context) {
        DeviceConfig.addOnPropertiesChangedListener(
                "system_time",
                context.getMainExecutor(),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.timedetector.ServerFlags$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        ArrayList arrayList;
                        ServerFlags serverFlags = ServerFlags.this;
                        synchronized (serverFlags.mListeners) {
                            try {
                                arrayList = new ArrayList(serverFlags.mListeners.size());
                                for (Map.Entry entry : serverFlags.mListeners.entrySet()) {
                                    HashSet hashSet = (HashSet) entry.getValue();
                                    Iterator it = properties.getKeyset().iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            if (hashSet.contains((String) it.next())) {
                                                arrayList.add((StateChangeListener) entry.getKey());
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            ((StateChangeListener) it2.next()).onChange();
                        }
                    }
                });
    }

    public static Duration getDurationFromMillis(String str, Duration duration) {
        long j = DeviceConfig.getLong("system_time", str, -1L);
        return j < 0 ? duration : Duration.ofMillis(j);
    }

    public static ServerFlags getInstance(Context context) {
        ServerFlags serverFlags;
        synchronized (SLOCK) {
            try {
                if (sInstance == null) {
                    sInstance = new ServerFlags(context);
                }
                serverFlags = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return serverFlags;
    }

    public final void addListener(StateChangeListener stateChangeListener, Set set) {
        Objects.requireNonNull(set);
        HashSet hashSet = new HashSet(set);
        synchronized (this.mListeners) {
            this.mListeners.put(stateChangeListener, hashSet);
        }
    }
}
