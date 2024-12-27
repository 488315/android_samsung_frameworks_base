package com.android.server.net.watchlist;

import com.android.internal.util.HexDump;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class HarmfulCrcs {
    public final Set mCrcSet;

    public HarmfulCrcs(List list) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            byte[] bArr = (byte[]) arrayList.get(i);
            if (bArr.length <= 4) {
                int i2 = 0;
                for (byte b : bArr) {
                    i2 = (i2 << 8) | (b & 255);
                }
                hashSet.add(Integer.valueOf(i2));
            }
        }
        this.mCrcSet = Collections.unmodifiableSet(hashSet);
    }

    public final void dump(PrintWriter printWriter) {
        Iterator it = this.mCrcSet.iterator();
        while (it.hasNext()) {
            printWriter.println(HexDump.toHexString(((Integer) it.next()).intValue()));
        }
        printWriter.println("");
    }
}
