package com.android.server.integrity.parser;

import android.content.integrity.AppInstallMetadata;
import com.android.server.integrity.model.BitInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class RuleIndexingController {
    public static LinkedHashMap sAppCertificateBasedIndexes;
    public static LinkedHashMap sPackageNameBasedIndexes;
    public static LinkedHashMap sUnindexedRuleIndexes;

    public RuleIndexingController(InputStream inputStream) {
        BitInputStream bitInputStream = new BitInputStream(inputStream);
        sPackageNameBasedIndexes = getNextIndexGroup(bitInputStream);
        sAppCertificateBasedIndexes = getNextIndexGroup(bitInputStream);
        sUnindexedRuleIndexes = getNextIndexGroup(bitInputStream);
    }

    public List identifyRulesToEvaluate(AppInstallMetadata appInstallMetadata) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(searchIndexingKeysRangeContainingKey(sPackageNameBasedIndexes, appInstallMetadata.getPackageName()));
        Iterator it = appInstallMetadata.getAppCertificates().iterator();
        while (it.hasNext()) {
            arrayList.add(searchIndexingKeysRangeContainingKey(sAppCertificateBasedIndexes, (String) it.next()));
        }
        arrayList.add(new RuleIndexRange(((Integer) sUnindexedRuleIndexes.get("START_KEY")).intValue(), ((Integer) sUnindexedRuleIndexes.get("END_KEY")).intValue()));
        return arrayList;
    }

    public final LinkedHashMap getNextIndexGroup(BitInputStream bitInputStream) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (bitInputStream.hasNext()) {
            String stringValue = BinaryFileOperations.getStringValue(bitInputStream);
            linkedHashMap.put(stringValue, Integer.valueOf(BinaryFileOperations.getIntValue(bitInputStream)));
            if (stringValue.matches("END_KEY")) {
                break;
            }
        }
        if (linkedHashMap.size() >= 2) {
            return linkedHashMap;
        }
        throw new IllegalStateException("Indexing file is corrupt.");
    }

    public static RuleIndexRange searchIndexingKeysRangeContainingKey(LinkedHashMap linkedHashMap, String str) {
        List list = (List) linkedHashMap.keySet().stream().collect(Collectors.toList());
        List searchKeysRangeContainingKey = searchKeysRangeContainingKey(list, str, 0, list.size() - 1);
        return new RuleIndexRange(((Integer) linkedHashMap.get(searchKeysRangeContainingKey.get(0))).intValue(), ((Integer) linkedHashMap.get(searchKeysRangeContainingKey.get(1))).intValue());
    }

    public static List searchKeysRangeContainingKey(List list, String str, int i, int i2) {
        if (i2 <= i) {
            throw new IllegalStateException("Indexing file is corrupt.");
        }
        int i3 = i2 - i;
        if (i3 == 1) {
            return Arrays.asList((String) list.get(i), (String) list.get(i2));
        }
        int i4 = (i3 / 2) + i;
        if (str.compareTo((String) list.get(i4)) >= 0) {
            return searchKeysRangeContainingKey(list, str, i4, i2);
        }
        return searchKeysRangeContainingKey(list, str, i, i4);
    }
}
