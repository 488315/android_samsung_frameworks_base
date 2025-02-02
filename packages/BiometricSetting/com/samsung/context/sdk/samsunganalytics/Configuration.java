package com.samsung.context.sdk.samsunganalytics;

/* loaded from: classes.dex */
public final class Configuration {
    private String deviceId;
    private String trackingId;
    private UserAgreement userAgreement;
    private String version;
    private boolean enableAutoDeviceId = false;
    private int auidType = -1;

    public final void enableAutoDeviceId() {
        this.enableAutoDeviceId = true;
    }

    public final int getAuidType() {
        return this.auidType;
    }

    public final String getDeviceId() {
        return this.deviceId;
    }

    public final String getTrackingId() {
        return this.trackingId;
    }

    public final UserAgreement getUserAgreement() {
        return this.userAgreement;
    }

    public final String getVersion() {
        return this.version;
    }

    public final boolean isEnableAutoDeviceId() {
        return this.enableAutoDeviceId;
    }

    public final void setAuidType(int i) {
        this.auidType = i;
    }

    public final void setDeviceId(String str) {
        this.deviceId = str;
    }

    public final void setTrackingId() {
        this.trackingId = "759-399-5199102";
    }

    public final void setUserAgreement(UserAgreement userAgreement) {
        this.userAgreement = userAgreement;
    }

    public final void setVersion(String str) {
        this.version = str;
    }
}
