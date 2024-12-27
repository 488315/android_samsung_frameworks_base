package com.android.server.net.watchlist;

import com.android.internal.util.HexDump;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HarmfulDigests {
    public final Set mDigestSet;

    public HarmfulDigests(List list) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            hashSet.add(HexDump.toHexString((byte[]) arrayList.get(i)));
        }
        this.mDigestSet = Collections.unmodifiableSet(hashSet);
    }
}
