package com.samsung.android.knox.zt.devicetrust.cert;

import android.os.Bundle;

/* loaded from: classes4.dex */
public interface ICertProvisionListener {
    void onError(int i, String str);

    void onStatusChange(String str, String str2);

    void onSuccess(Bundle bundle);
}
