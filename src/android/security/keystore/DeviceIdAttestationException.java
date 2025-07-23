package android.security.keystore;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DeviceIdAttestationException extends Exception {
    public DeviceIdAttestationException(String str) {
        super(str);
    }

    public DeviceIdAttestationException(String str, Throwable th) {
        super(str, th);
    }
}
