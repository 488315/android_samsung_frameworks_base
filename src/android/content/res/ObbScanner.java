package android.content.res;

import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class ObbScanner {
    private static native void getObbInfo_native(String str, ObbInfo obbInfo) throws IOException;

    private ObbScanner() {
    }

    public static ObbInfo getObbInfo(String str) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("file path cannot be null");
        }
        File file = new File(str);
        if (!file.exists()) {
            throw new IllegalArgumentException("OBB file does not exist: " + str);
        }
        String canonicalPath = file.getCanonicalPath();
        ObbInfo obbInfo = new ObbInfo();
        obbInfo.filename = canonicalPath;
        getObbInfo_native(canonicalPath, obbInfo);
        return obbInfo;
    }
}
