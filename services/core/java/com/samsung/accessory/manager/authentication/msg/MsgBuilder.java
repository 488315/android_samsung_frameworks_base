package com.samsung.accessory.manager.authentication.msg;

import com.att.iqi.lib.metrics.hw.HwConstants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MsgBuilder {
    public byte[] randNum;

    public final byte[] getDataVerification() {
        byte[] bArr = (byte[]) this.randNum.clone();
        byte[] bArr2 = new byte[bArr.length + 5];
        byte[] bArr3 = {0, 118, 0, 0, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED};
        byte[] bArr4 = (byte[]) bArr.clone();
        System.arraycopy(bArr3, 0, bArr2, 0, bArr3.length);
        System.arraycopy(bArr4, 0, bArr2, bArr3.length, bArr4.length);
        return (byte[]) ((byte[]) bArr2.clone()).clone();
    }
}
