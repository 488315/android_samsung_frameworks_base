package com.samsung.android.sume.core.service;

import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.ResponseHolder;

/* loaded from: classes4.dex */
public interface ServiceController {
    int createMediaFilterController();

    void releaseMediaFilterController(int i);

    ResponseHolder request(int i, Request request);
}
