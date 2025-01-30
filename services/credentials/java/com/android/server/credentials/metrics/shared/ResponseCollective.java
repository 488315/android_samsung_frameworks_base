package com.android.server.credentials.metrics.shared;

import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.credentials.metrics.EntryEnum;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
public class ResponseCollective {
    public final Map mEntryCounts;
    public final Map mResponseCounts;

    public ResponseCollective(Map map, Map map2) {
        LinkedHashMap linkedHashMap;
        LinkedHashMap linkedHashMap2;
        if (map == null) {
            linkedHashMap = new LinkedHashMap();
        } else {
            linkedHashMap = new LinkedHashMap(map);
        }
        this.mResponseCounts = linkedHashMap;
        if (map2 == null) {
            linkedHashMap2 = new LinkedHashMap();
        } else {
            linkedHashMap2 = new LinkedHashMap(map2);
        }
        this.mEntryCounts = linkedHashMap2;
    }

    public String[] getUniqueResponseStrings() {
        String[] strArr = new String[this.mResponseCounts.keySet().size()];
        this.mResponseCounts.keySet().toArray(strArr);
        return strArr;
    }

    public Map getEntryCountsMap() {
        return Collections.unmodifiableMap(this.mEntryCounts);
    }

    public Map getResponseCountsMap() {
        return Collections.unmodifiableMap(this.mResponseCounts);
    }

    public int[] getUniqueResponseCounts() {
        return this.mResponseCounts.values().stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public int[] getUniqueEntries() {
        return this.mEntryCounts.keySet().stream().mapToInt(new ToIntFunction() { // from class: com.android.server.credentials.metrics.shared.ResponseCollective$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((EntryEnum) obj).ordinal();
            }
        }).toArray();
    }

    public int[] getUniqueEntryCounts() {
        return this.mEntryCounts.values().stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public int getCountForEntry(EntryEnum entryEnum) {
        return ((Integer) this.mEntryCounts.getOrDefault(entryEnum, 0)).intValue();
    }

    public int getNumEntriesTotal() {
        return this.mEntryCounts.values().stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).sum();
    }

    public static Map combineTypeCountMaps(Map map, Map map2) {
        for (Object obj : map2.keySet()) {
            map.put(obj, Integer.valueOf(((Integer) map.getOrDefault(obj, 0)).intValue() + ((Integer) map2.get(obj)).intValue()));
        }
        return map;
    }
}
