package com.samsung.android.knox.cmfa;

/* loaded from: classes4.dex */
public interface IAuthEventListener {
    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();
}
