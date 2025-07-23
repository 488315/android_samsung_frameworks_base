package android.telephony.ims.feature;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface ImsTrafficSessionCallback {
    void onError(ConnectionFailureInfo connectionFailureInfo);

    void onReady();
}
