package com.android.server.notification;

import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.function.QuintConsumer;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PreferencesHelper$$ExternalSyntheticLambda0
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ArrayMap arrayMap = (ArrayMap) obj5;
        Boolean valueOf =
                Boolean.valueOf(
                        ((PreferencesHelper) obj)
                                .getOrCreatePackagePreferencesLocked(
                                        ((Integer) obj3).intValue(), (String) obj2)
                                .allowEdgeLighting);
        ((ArrayList) obj4).add(valueOf);
        Slog.d("NotificationPrefHelper", "isEdgeLightingAllowedWithLock result = " + valueOf);
        synchronized (arrayMap) {
            arrayMap.notify();
        }
    }
}
