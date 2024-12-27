package android.hardware.authsecret;

import android.os.IInterface;

public interface IAuthSecret extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$authsecret$IAuthSecret".replace('$', '.');

    void setPrimaryUserCredential(byte[] bArr);
}
