package com.android.server.net.watchlist;

import com.android.internal.util.HexDump;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
