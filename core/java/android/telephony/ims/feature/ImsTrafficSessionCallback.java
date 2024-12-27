package android.telephony.ims.feature;

public interface ImsTrafficSessionCallback {
    void onError(ConnectionFailureInfo connectionFailureInfo);

    void onReady();
}
