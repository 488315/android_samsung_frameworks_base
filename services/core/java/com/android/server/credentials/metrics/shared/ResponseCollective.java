package com.android.server.credentials.metrics.shared;

import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.credentials.metrics.EntryEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ResponseCollective {
    public final Map mEntryCounts;
    public final Map mResponseCounts;

    public ResponseCollective(Map map, Map map2) {
        this.mResponseCounts = map == null ? new LinkedHashMap() : new LinkedHashMap(map);
        this.mEntryCounts = map2 == null ? new LinkedHashMap() : new LinkedHashMap(map2);
    }

    public static void combineTypeCountMaps(Map map, Map map2) {
        for (Object obj : map2.keySet()) {
            map.put(
                    obj,
                    Integer.valueOf(
                            ((Integer) map2.get(obj)).intValue()
                                    + ((Integer) ((LinkedHashMap) map).getOrDefault(obj, 0))
                                            .intValue()));
        }
    }

    public final int getCountForEntry(EntryEnum entryEnum) {
        return ((Integer) this.mEntryCounts.getOrDefault(entryEnum, 0)).intValue();
    }

    public final int[] getUniqueEntries() {
        return this.mEntryCounts.keySet().stream()
                .mapToInt(new ResponseCollective$$ExternalSyntheticLambda0())
                .toArray();
    }

    public final int[] getUniqueEntryCounts() {
        return this.mEntryCounts.values().stream()
                .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                .toArray();
    }

    public final int[] getUniqueResponseCounts() {
        return this.mResponseCounts.values().stream()
                .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                .toArray();
    }

    public final String[] getUniqueResponseStrings() {
        String[] strArr = new String[this.mResponseCounts.keySet().size()];
        this.mResponseCounts.keySet().toArray(strArr);
        return strArr;
    }
}
