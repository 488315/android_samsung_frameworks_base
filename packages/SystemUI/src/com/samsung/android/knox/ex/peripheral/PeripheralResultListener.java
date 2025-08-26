package com.samsung.android.knox.ex.peripheral;

import android.os.Bundle;

/* loaded from: classes4.dex */
public interface PeripheralResultListener {
    void onFail(int i, String str);

    void onSuccess(Bundle bundle);
}
