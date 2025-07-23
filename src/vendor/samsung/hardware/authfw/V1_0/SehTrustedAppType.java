package vendor.samsung.hardware.authfw.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTrustedAppType {
    public static final int ASSET_PASS_AUTHENTICATOR = 10000;
    public static final int ASSET_PASS_ESE = 10001;
    public static final int PRELOAD_ASSET_DOWNLOADER = 3;
    public static final int PRELOAD_PASS_DEVICE_ROOT_KEY = 2;
    public static final int PRELOAD_PASS_FINGERPRINT = 1;

    public static final String toString(int i) {
        if (i == 1) {
            return "PRELOAD_PASS_FINGERPRINT";
        }
        if (i == 2) {
            return "PRELOAD_PASS_DEVICE_ROOT_KEY";
        }
        if (i == 3) {
            return "PRELOAD_ASSET_DOWNLOADER";
        }
        if (i == 10000) {
            return "ASSET_PASS_AUTHENTICATOR";
        }
        if (i == 10001) {
            return "ASSET_PASS_ESE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PRELOAD_PASS_FINGERPRINT");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("PRELOAD_PASS_DEVICE_ROOT_KEY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PRELOAD_ASSET_DOWNLOADER");
            i2 = 3;
        }
        if ((i & 10000) == 10000) {
            arrayList.add("ASSET_PASS_AUTHENTICATOR");
            i2 |= 10000;
        }
        if ((i & 10001) == 10001) {
            arrayList.add("ASSET_PASS_ESE");
            i2 |= 10001;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
