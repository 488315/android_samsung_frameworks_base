package vendor.samsung.hardware.biometrics.fingerprint;

import android.os.IInterface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ISehFingerprint extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$biometrics$fingerprint$ISehFingerprint".replace('$', '.');

    SehResult sehRequest(int i, int i2, int i3, byte[] bArr);
}
