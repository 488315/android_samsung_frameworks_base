package com.android.server.notification;

import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.function.QuintConsumer;

import java.util.ArrayList;

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
