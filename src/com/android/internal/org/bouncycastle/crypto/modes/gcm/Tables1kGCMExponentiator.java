package com.android.internal.org.bouncycastle.crypto.modes.gcm;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class Tables1kGCMExponentiator implements GCMExponentiator {
    private List lookupPowX2;

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        long[] jArrAsLongs = GCMUtil.asLongs(bArr);
        List list = this.lookupPowX2;
        if (list == null || 0 == GCMUtil.areEqual(jArrAsLongs, (long[]) list.get(0))) {
            ArrayList arrayList = new ArrayList(8);
            this.lookupPowX2 = arrayList;
            arrayList.add(jArrAsLongs);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j, byte[] bArr) {
        long[] jArrOneAsLongs = GCMUtil.oneAsLongs();
        int i = 0;
        while (j > 0) {
            if ((1 & j) != 0) {
                GCMUtil.multiply(jArrOneAsLongs, getPowX2(i));
            }
            i++;
            j >>>= 1;
        }
        GCMUtil.asBytes(jArrOneAsLongs, bArr);
    }

    private long[] getPowX2(int i) {
        int size = this.lookupPowX2.size() - 1;
        if (size < i) {
            long[] jArr = (long[]) this.lookupPowX2.get(size);
            while (true) {
                long[] jArr2 = new long[2];
                GCMUtil.square(jArr, jArr2);
                this.lookupPowX2.add(jArr2);
                size++;
                if (size >= i) {
                    break;
                }
                jArr = jArr2;
            }
        }
        return (long[]) this.lookupPowX2.get(i);
    }
}
