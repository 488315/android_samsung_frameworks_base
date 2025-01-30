package com.samsung.android.lib.dexcontrol.uvdm.sender;

import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;

/* loaded from: classes2.dex */
public interface IUvdmSender {
    void destroy();

    void send(byte[] bArr);

    void send(byte[] bArr, int i);

    void setInMsgMinLength(int i);

    void setResponseListener(IResponseListener iResponseListener);
}
