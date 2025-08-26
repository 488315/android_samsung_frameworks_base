package com.google.common.util.concurrent;

/* loaded from: classes4.dex */
public interface FutureCallback {
    void onFailure(Throwable th);

    void onSuccess(Object obj);
}
