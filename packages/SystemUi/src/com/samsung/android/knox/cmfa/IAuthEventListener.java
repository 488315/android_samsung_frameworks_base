package com.samsung.android.knox.cmfa;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IAuthEventListener {
    void onFail(String str);

    void onStateUpdate(boolean z, String str);

    void onSuccess();
}
