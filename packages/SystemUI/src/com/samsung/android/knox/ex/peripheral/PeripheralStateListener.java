package com.samsung.android.knox.ex.peripheral;

import android.os.Bundle;

/* loaded from: classes4.dex */
public interface PeripheralStateListener {
    void onFail(int i, String str);

    void onStateChange(int i, Bundle bundle);

    void onSuccess();
}
