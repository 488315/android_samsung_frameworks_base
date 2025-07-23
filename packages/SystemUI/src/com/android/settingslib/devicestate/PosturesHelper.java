package com.android.settingslib.devicestate;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PosturesHelper {
    public final Map postures;

    public PosturesHelper(Context context, DeviceStateManager deviceStateManager) {
        if (deviceStateManager == null) {
            this.postures = MapsKt__MapsKt.mapOf(new Pair(0, ArraysKt___ArraysKt.toList(context.getResources().getIntArray(R.array.special_locale_codes))), new Pair(1, ArraysKt___ArraysKt.toList(context.getResources().getIntArray(R.array.vendor_disallowed_apps_managed_user))), new Pair(2, ArraysKt___ArraysKt.toList(context.getResources().getIntArray(17236290))), new Pair(3, ArraysKt___ArraysKt.toList(context.getResources().getIntArray(17236298))));
            return;
        }
        List supportedDeviceStates = deviceStateManager.getSupportedDeviceStates();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = supportedDeviceStates.iterator();
        while (true) {
            int i = -1;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            DeviceState deviceState = (DeviceState) next;
            deviceState.getClass();
            if (deviceState.hasProperty(16)) {
                i = 3;
            } else if (deviceState.hasProperty(11)) {
                i = 0;
            } else if (deviceState.hasProperties(new int[]{12, 2})) {
                i = 1;
            } else if (deviceState.hasProperty(12)) {
                i = 2;
            }
            Integer valueOf = Integer.valueOf(i);
            Object obj = linkedHashMap.get(valueOf);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(valueOf, obj);
            }
            ((List) obj).add(next);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((Number) entry.getKey()).intValue() != -1) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap2.size()));
        for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
            Object key = entry2.getKey();
            Iterable iterable = (Iterable) entry2.getValue();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator it2 = iterable.iterator();
            while (it2.hasNext()) {
                arrayList.add(Integer.valueOf(((DeviceState) it2.next()).getIdentifier()));
            }
            linkedHashMap3.put(key, arrayList);
        }
        this.postures = linkedHashMap3;
    }

    public final int deviceStateToPosture(int i) {
        Map map = this.postures;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (((List) entry.getValue()).contains(Integer.valueOf(i))) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(linkedHashMap.keySet());
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }
}
