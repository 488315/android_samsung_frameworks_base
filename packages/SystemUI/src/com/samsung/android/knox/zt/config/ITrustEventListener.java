package com.samsung.android.knox.zt.config;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ITrustEventListener {
    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();
}
