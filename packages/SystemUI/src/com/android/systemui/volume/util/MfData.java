package com.android.systemui.volume.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MfData {
    public static int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH;
    public final byte[] mDeviceId = new byte[2];
    public final byte[] mManufacturerRawData;
    public final int mManufacturerType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = 8;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MfData(byte[] r14) {
        /*
            r13 = this;
            r13.<init>()
            r0 = 2
            byte[] r1 = new byte[r0]
            r13.mDeviceId = r1
            r13.mManufacturerRawData = r14
            r1 = 1
            r2 = 0
            r3 = 16
            r4 = 8
            r5 = 3
            r6 = 7
            if (r14 == 0) goto L66
            int r7 = r14.length
            r8 = 9
            if (r7 >= r8) goto L1a
            goto L66
        L1a:
            r7 = 5
            r9 = r14[r7]
            if (r9 != 0) goto L27
            r10 = 6
            r10 = r14[r10]
            if (r10 != r0) goto L27
            r13.mManufacturerType = r1
            goto L68
        L27:
            if (r9 != r8) goto L30
            r10 = r14[r6]
            if (r10 != 0) goto L30
            r13.mManufacturerType = r0
            goto L68
        L30:
            if (r9 != r8) goto L63
            r9 = r14[r6]
            if (r9 != r0) goto L63
            r13.mManufacturerType = r5
            r9 = r14[r4]
            r10 = r2
        L3b:
            if (r10 >= r7) goto L68
            int r11 = r1 << r10
            byte r11 = (byte) r11
            r11 = r11 & r9
            byte r11 = (byte) r11
            if (r11 != r1) goto L47
            int r8 = r8 + 1
            goto L60
        L47:
            if (r11 != r0) goto L4c
            int r8 = r8 + 2
            goto L60
        L4c:
            r12 = 4
            if (r11 != r12) goto L52
            int r8 = r8 + 6
            goto L60
        L52:
            if (r11 != r4) goto L57
            int r8 = r8 + 18
            goto L60
        L57:
            if (r11 != r3) goto L60
            com.android.systemui.volume.util.MfData.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = r8
            r11 = r14[r8]
            int r11 = r11 + r1
            int r11 = r11 + r8
            r8 = r11
        L60:
            int r10 = r10 + 1
            goto L3b
        L63:
            r13.mManufacturerType = r2
            goto L68
        L66:
            r13.mManufacturerType = r2
        L68:
            int r7 = r13.mManufacturerType
            if (r7 == r1) goto L9a
            if (r7 == r0) goto L86
            if (r7 == r5) goto L71
            goto L99
        L71:
            if (r7 == r5) goto L74
            goto L99
        L74:
            byte[] r5 = r13.mManufacturerRawData
            if (r5 == 0) goto L99
            r4 = r5[r4]
            r4 = r4 & r3
            if (r4 != r3) goto L99
            int r3 = com.android.systemui.volume.util.MfData.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH
            int r3 = r3 + r1
            byte[] r13 = r13.mDeviceId
            java.lang.System.arraycopy(r14, r3, r13, r2, r0)
            return
        L86:
            r1 = 31
            r3 = r14[r1]
            r3 = r3 & 255(0xff, float:3.57E-43)
            if (r3 <= 0) goto L99
            int r4 = r14.length
            int r3 = r3 + r1
            if (r4 <= r3) goto L99
            byte[] r13 = r13.mDeviceId
            r1 = 32
            java.lang.System.arraycopy(r14, r1, r13, r2, r0)
        L99:
            return
        L9a:
            byte[] r13 = r13.mDeviceId
            java.lang.System.arraycopy(r14, r6, r13, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.util.MfData.<init>(byte[]):void");
    }
}
