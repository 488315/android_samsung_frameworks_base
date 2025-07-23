package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes6.dex */
public class DataMap {
    public final int[] mIds;
    public final String[] mNames;
    public final byte[] mTypes;

    public DataMap(String[] strArr, byte[] bArr, int[] iArr) {
        this.mNames = strArr;
        this.mTypes = bArr;
        this.mIds = iArr;
    }

    public int getPos(String str) {
        int i = 0;
        while (true) {
            String[] strArr = this.mNames;
            if (i >= strArr.length) {
                return -1;
            }
            if (str.equals(strArr[i])) {
                return i;
            }
            i++;
        }
    }

    public byte getType(int i) {
        return this.mTypes[i];
    }

    public int getId(int i) {
        return this.mIds[i];
    }
}
