package com.samsung.android.knox.ex.peripheral;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface PeripheralStateListener {
    void onFail(int i, String str);

    void onStateChange(int i, Bundle bundle);

    void onSuccess();
}
