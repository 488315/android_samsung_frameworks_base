package android.os;

import android.app.ActivityThread;
import android.app.Instrumentation;
import android.app.UiAutomation;

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

    private static String runShellCommand(String command) throws IOException {
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        Instrumentation instrumentation = activityThread.getInstrumentation();
        UiAutomation automation = instrumentation.getUiAutomation();
        ParcelFileDescriptor pfd = automation.executeShellCommand(command);
        ParcelFileDescriptor.AutoCloseInputStream is =
                new ParcelFileDescriptor.AutoCloseInputStream(pfd);
        try {
            String str = new String(is.readAllBytes());
            is.close();
            return str;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private VintfObject() {}
}
