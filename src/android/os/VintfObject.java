package android.os;

import android.app.ActivityThread;
import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
public class VintfObject {
    private static final String LOG_TAG = "VintfObject";

    public static native String[] getHalNamesAndVersions();

    public static native String getPlatformSepolicyVersion();

    public static native String getSepolicyVersion();

    public static native Long getTargetFrameworkCompatibilityMatrixVersion();

    public static native Map<String, String[]> getVndkSnapshots();

    public static native String[] report();

    public static native int verifyBuildAtBoot();

    static {
        System.loadLibrary("vintf_jni");
    }

    private static String runShellCommand(String str) throws IOException {
        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(ActivityThread.currentActivityThread().getInstrumentation().getUiAutomation().executeShellCommand(str));
        try {
            String str2 = new String(autoCloseInputStream.readAllBytes());
            autoCloseInputStream.close();
            return str2;
        } catch (Throwable th) {
            try {
                autoCloseInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private VintfObject() {
    }
}
