package com.samsung.android.knox.zt.config;

/* loaded from: classes4.dex */
public interface ITrustEventListener {
    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();
}
