package vendor.samsung.hardware.biometrics.fingerprint;

import android.os.IInterface;

public interface ISehFingerprint extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$biometrics$fingerprint$ISehFingerprint".replace('$', '.');

    SehResult sehRequest(int i, int i2, int i3, byte[] bArr);
}
