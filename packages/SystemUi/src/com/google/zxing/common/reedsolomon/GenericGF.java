package com.google.zxing.common.reedsolomon;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GenericGF {
    public int[] expTable;
    public final int generatorBase;
    public boolean initialized = false;
    public int[] logTable;
    public final int primitive;
    public final int size;
    public GenericGFPoly zero;
    public static final GenericGF AZTEC_DATA_12 = new GenericGF(4201, 4096, 1);
    public static final GenericGF AZTEC_DATA_10 = new GenericGF(1033, 1024, 1);
    public static final GenericGF AZTEC_DATA_6 = new GenericGF(67, 64, 1);
    public static final GenericGF AZTEC_PARAM = new GenericGF(19, 16, 1);
    public static final GenericGF QR_CODE_FIELD_256 = new GenericGF(IKnoxCustomManager.Stub.TRANSACTION_startProKioskMode, 256, 0);
    public static final GenericGF AZTEC_DATA_8 = new GenericGF(301, 256, 1);

    public GenericGF(int i, int i2, int i3) {
        this.primitive = i;
        this.size = i2;
        this.generatorBase = i3;
        if (i2 <= 0) {
            initialize();
        }
    }

    public final void checkInit() {
        if (this.initialized) {
            return;
        }
        initialize();
    }

    public final void initialize() {
        int i = this.size;
        this.expTable = new int[i];
        this.logTable = new int[i];
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            this.expTable[i3] = i2;
            i2 <<= 1;
            if (i2 >= i) {
                i2 = (i2 ^ this.primitive) & (i - 1);
            }
        }
        for (int i4 = 0; i4 < i - 1; i4++) {
            this.logTable[this.expTable[i4]] = i4;
        }
        this.zero = new GenericGFPoly(this, new int[]{0});
        new GenericGFPoly(this, new int[]{1});
        this.initialized = true;
    }

    public final int multiply(int i, int i2) {
        checkInit();
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = this.expTable;
        int[] iArr2 = this.logTable;
        return iArr[(iArr2[i] + iArr2[i2]) % (this.size - 1)];
    }

    public final String toString() {
        return "GF(0x" + Integer.toHexString(this.primitive) + ',' + this.size + ')';
    }
}
