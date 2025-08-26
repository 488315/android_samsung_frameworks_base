package com.samsung.android.knox.ex.peripheral;

import android.os.Bundle;

/* loaded from: classes4.dex */
public interface PeripheralDataListener {
    void onFail(int i, String str);

    void onReceive(int i, Bundle bundle);

    void onSuccess();
}
