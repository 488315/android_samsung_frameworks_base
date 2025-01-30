package com.samsung.android.server.audio;

import com.att.iqi.lib.metrics.hw.HwConstants;

/* loaded from: classes2.dex */
public class MfData {
    public static int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = 8;
    public int mManufacturerType = 0;
    public byte[] mManufacturerRawData = null;
    public byte[] mDeviceId = new byte[2];

    public MfData(byte[] bArr) {
        setManufacturerRawData(bArr);
        setManufacturerType(bArr);
        setDeviceId(bArr);
    }

    public int getDeviceIdToInt() {
        byte[] bArr = this.mDeviceId;
        return ((bArr[0] & 255) << 8) + (bArr[1] & 255);
    }

    public final void setManufacturerRawData(byte[] bArr) {
        this.mManufacturerRawData = bArr;
    }

    public final void setManufacturerType(byte[] bArr) {
        if (bArr != null) {
            int i = 9;
            if (bArr.length >= 9) {
                byte b = bArr[5];
                if (b == 0 && bArr[6] == 2) {
                    this.mManufacturerType = 1;
                    return;
                }
                if (b == 9 && bArr[7] == 0) {
                    this.mManufacturerType = 2;
                    return;
                }
                if (b == 9 && bArr[7] == 2) {
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
                            i += bArr[i] + 1;
                        }
                    }
                    return;
                }
                this.mManufacturerType = 0;
                return;
            }
        }
        this.mManufacturerType = 0;
    }

    public final boolean isSupportFeature(byte b) {
        byte[] bArr;
        return this.mManufacturerType == 3 && (bArr = this.mManufacturerRawData) != null && (bArr[8] & b) == b;
    }

    public final void setDeviceId(byte[] bArr, int i) {
        System.arraycopy(bArr, i, this.mDeviceId, 0, 2);
    }

    public final void setDeviceId(byte[] bArr) {
        int i = this.mManufacturerType;
        if (i == 1) {
            setDeviceId(bArr, 7);
            return;
        }
        if (i != 2) {
            if (i == 3 && isSupportFeature(HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED)) {
                setDeviceId(bArr, MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + 1);
                return;
            }
            return;
        }
        int i2 = bArr[31] & 255;
        if (i2 <= 0 || bArr.length <= i2 + 31) {
            return;
        }
        setDeviceId(bArr, 32);
    }
}
