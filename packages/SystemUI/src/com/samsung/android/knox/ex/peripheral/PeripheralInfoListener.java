package com.samsung.android.knox.ex.peripheral;

import android.os.Bundle;

/* loaded from: classes4.dex */
public interface PeripheralInfoListener {
    void onFail(int i, String str);

    void onReceive(Bundle bundle);

    void onSuccess();
}
