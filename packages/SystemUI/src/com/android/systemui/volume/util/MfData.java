package com.android.systemui.volume.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MfData {
    public static int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH;
    public final byte[] mDeviceId = new byte[2];
    public final byte[] mManufacturerRawData;
    public final int mManufacturerType;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = 8;
    }

    public MfData(byte[] bArr) {
        byte[] bArr2;
        this.mManufacturerRawData = bArr;
        int i = 9;
        if (bArr.length < 9) {
            this.mManufacturerType = 0;
        } else {
            byte b = bArr[5];
            if (b == 0 && bArr[6] == 2) {
                this.mManufacturerType = 1;
            } else if (b == 9 && bArr[7] == 0) {
                this.mManufacturerType = 2;
            } else if (b == 9 && bArr[7] == 2) {
                this.mManufacturerType = 3;
                byte b2 = bArr[8];
                for (int i2 = 0; i2 < 5; i2++) {
                    byte b3 = (byte) (((byte) (1 << i2)) & b2);
                    if (b3 == 1) {
                        i++;
                    } else if (b3 == 2) {
                        i += 2;
                    } else if (b3 == 4) {
                        i += 6;
                    } else if (b3 == 8) {
                        i += 18;
                    } else if (b3 == 16) {
                        MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = i;
                        i = bArr[i] + 1 + i;
                    }
                }
            } else {
                this.mManufacturerType = 0;
            }
        }
        int i3 = this.mManufacturerType;
        if (i3 == 1) {
            System.arraycopy(bArr, 7, this.mDeviceId, 0, 2);
            return;
        }
        if (i3 == 2) {
            int i4 = bArr[31] & 255;
            if (i4 <= 0 || bArr.length <= i4 + 31) {
                return;
            }
            System.arraycopy(bArr, 32, this.mDeviceId, 0, 2);
            return;
        }
        if (i3 == 3 && i3 == 3 && (bArr2 = this.mManufacturerRawData) != null && (bArr2[8] & 16) == 16) {
            System.arraycopy(bArr, MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + 1, this.mDeviceId, 0, 2);
        }
    }
}
