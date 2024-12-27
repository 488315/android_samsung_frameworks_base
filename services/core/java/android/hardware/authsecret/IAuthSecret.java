package android.hardware.authsecret;

import android.os.IInterface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IAuthSecret extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$authsecret$IAuthSecret".replace('$', '.');

    void setPrimaryUserCredential(byte[] bArr);
}
