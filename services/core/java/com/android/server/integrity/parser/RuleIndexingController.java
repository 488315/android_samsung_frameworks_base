package com.android.server.integrity.parser;

import android.content.integrity.AppInstallMetadata;
import com.android.server.integrity.model.BitInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RuleIndexingController {
    public static LinkedHashMap sAppCertificateBasedIndexes;
    public static LinkedHashMap sPackageNameBasedIndexes;
    public static LinkedHashMap sUnindexedRuleIndexes;

    public static LinkedHashMap getNextIndexGroup(BitInputStream bitInputStream) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (bitInputStream.mInputStream.available() > 0) {
            String stringValue = BinaryFileOperations.getStringValue(bitInputStream, bitInputStream.getNext(8), bitInputStream.getNext(1) == 1);
            linkedHashMap.put(stringValue, Integer.valueOf(bitInputStream.getNext(32)));
            if (stringValue.matches("END_KEY")) {
                break;
            }
        }
        if (linkedHashMap.size() >= 2) {
            return linkedHashMap;
        }
        throw new IllegalStateException("Indexing file is corrupt.");
    }

    public static List identifyRulesToEvaluate(AppInstallMetadata appInstallMetadata) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(searchIndexingKeysRangeContainingKey(sPackageNameBasedIndexes, appInstallMetadata.getPackageName()));
        Iterator it = appInstallMetadata.getAppCertificates().iterator();
        while (it.hasNext()) {
            arrayList.add(searchIndexingKeysRangeContainingKey(sAppCertificateBasedIndexes, (String) it.next()));
        }
        arrayList.add(new RuleIndexRange(((Integer) sUnindexedRuleIndexes.get("START_KEY")).intValue(), ((Integer) sUnindexedRuleIndexes.get("END_KEY")).intValue()));
        return arrayList;
    }

    public static RuleIndexRange searchIndexingKeysRangeContainingKey(LinkedHashMap linkedHashMap, String str) {
        List list = (List) linkedHashMap.keySet().stream().collect(Collectors.toList());
        List searchKeysRangeContainingKey = searchKeysRangeContainingKey(0, str, list.size() - 1, list);
        return new RuleIndexRange(((Integer) linkedHashMap.get(searchKeysRangeContainingKey.get(0))).intValue(), ((Integer) linkedHashMap.get(searchKeysRangeContainingKey.get(1))).intValue());
    }

    public static List searchKeysRangeContainingKey(int i, String str, int i2, List list) {
        if (i2 <= i) {
            throw new IllegalStateException("Indexing file is corrupt.");
        }
        int i3 = i2 - i;
        if (i3 == 1) {
            return Arrays.asList((String) list.get(i), (String) list.get(i2));
        }
        int i4 = (i3 / 2) + i;
        return str.compareTo((String) list.get(i4)) >= 0 ? searchKeysRangeContainingKey(i4, str, i2, list) : searchKeysRangeContainingKey(i, str, i4, list);
    }
}
