package com.samsung.android.knox.zt.devicetrust.cert;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ICertProvisionListener {
    void onError(int i, String str);

    void onStatusChange(String str, String str2);

    void onSuccess(Bundle bundle);
}
