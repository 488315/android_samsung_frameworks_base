package android.security;

import android.hardware.security.keymint.HardwareAuthToken;
import android.hardware.security.secureclock.Timestamp;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes3.dex */
public class AuthTokenUtils {
    private AuthTokenUtils() {
    }

    public static HardwareAuthToken toHardwareAuthToken(byte[] bArr) {
        HardwareAuthToken hardwareAuthToken = new HardwareAuthToken();
        hardwareAuthToken.challenge = ByteBuffer.wrap(bArr, 1, 8).order(ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.userId = ByteBuffer.wrap(bArr, 9, 8).order(ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.authenticatorId = ByteBuffer.wrap(bArr, 17, 8).order(ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.authenticatorType = ByteBuffer.wrap(bArr, 25, 4).order(ByteOrder.BIG_ENDIAN).getInt();
        Timestamp timestamp = new Timestamp();
        timestamp.milliSeconds = ByteBuffer.wrap(bArr, 29, 8).order(ByteOrder.BIG_ENDIAN).getLong();
        hardwareAuthToken.timestamp = timestamp;
        hardwareAuthToken.mac = new byte[32];
        System.arraycopy(bArr, 37, hardwareAuthToken.mac, 0, 32);
        return hardwareAuthToken;
    }
}
