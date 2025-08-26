package com.android.systemui.media.mediaoutput.analytics;

import android.util.Log;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Arrays;
import java.util.LinkedHashMap;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* loaded from: classes2.dex */
public final class MoSaLogging {
    public static final MoSaLogging INSTANCE = new MoSaLogging();
    public static SaScreen currentScreen;

    private MoSaLogging() {
    }

    public static void send(SaEvent saEvent, String str, Long l) {
        String str2;
        SaScreen saScreen = currentScreen;
        Log.d("MoSaLogging", "send Event - " + saEvent);
        if (saScreen == null || (str2 = saScreen.id) == null) {
            return;
        }
        if (str != null) {
            if (l != null) {
                SystemUIAnalytics.sendEventLog(str2, saEvent.id, str, l.longValue());
                return;
            } else {
                SystemUIAnalytics.sendEventLog(str2, saEvent.id, str);
                return;
            }
        }
        if (l != null) {
            SystemUIAnalytics.sendEventLog(str2, saEvent.id, l.longValue());
        } else {
            SystemUIAnalytics.sendEventLog(str2, saEvent.id);
        }
    }

    public static /* synthetic */ void send$default(MoSaLogging moSaLogging, SaEvent saEvent) {
        moSaLogging.getClass();
        send(saEvent, (String) null, (Long) null);
    }

    public static void send(SaEvent saEvent, SaCustom... saCustomArr) {
        send(saEvent, currentScreen, (SaCustom[]) Arrays.copyOf(saCustomArr, saCustomArr.length));
    }

    public static void send(SaEvent saEvent, SaScreen saScreen, SaCustom... saCustomArr) {
        String str;
        Log.d("MoSaLogging", "send Event with Custom Dimension - " + saEvent + ", params = " + ArraysKt___ArraysKt.toList(saCustomArr));
        if (saScreen == null || (str = saScreen.id) == null) {
            return;
        }
        String str2 = saEvent.id;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(saCustomArr.length);
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        for (SaCustom saCustom : saCustomArr) {
            Pair pair = new Pair(saCustom.key, saCustom.value);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        SystemUIAnalytics.sendEventCDLog(str, str2, linkedHashMap);
    }
}
