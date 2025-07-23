package com.samsung.android.knox.cmfa;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IAuthEventListener {
    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();
}
