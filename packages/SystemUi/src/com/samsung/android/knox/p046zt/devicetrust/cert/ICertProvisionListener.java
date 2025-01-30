package com.samsung.android.knox.p046zt.devicetrust.cert;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICertProvisionListener {
    void onError(int i, String str);

    void onStatusChange(String str, String str2);

    void onSuccess(Bundle bundle);
}
