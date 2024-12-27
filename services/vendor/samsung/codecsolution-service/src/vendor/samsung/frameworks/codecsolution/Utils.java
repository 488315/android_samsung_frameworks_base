package vendor.samsung.frameworks.codecsolution;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/* loaded from: classes.dex */
public class Utils {
    private static final String TAG = "CodecSolution_Utils";

    public enum QueryResult {
        SERVER_IS_NOT_AVAILABLE,
        NOT_FOUND,
        FOUND
    }

    public static int getNavigationBarHeight(Context context) {
        Log.d(TAG, "getNavigationBarHeight()");
        int i = 0;
        try {
            int identifier =
                    context.getResources()
                            .getIdentifier("navigation_bar_height", "dimen", "android");
            if (identifier > 0) {
                i = context.getResources().getDimensionPixelSize(identifier);
            }
        } catch (Exception unused) {
            Log.w(TAG, "Can't get navigation_bar_height");
        }
        Log.d(TAG, "navigation_bar_height : " + i);
        return i;
    }

    public static String getPkgName(Context context, int i) {
        String str;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                    runningAppProcesses) {
                if (runningAppProcessInfo.pid == i) {
                    str = runningAppProcessInfo.processName;
                    break;
                }
            }
        }
        str = null;
        if (str == null) {
            str = "";
        }
        Log.d(TAG, "pkg : ".concat(str));
        return str;
    }

    public static String getTopPackage(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks =
                ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks == null || runningTasks.size() == 0) {
            return "";
        }
        String packageName = runningTasks.get(0).topActivity.getPackageName();
        if (packageName != null) {
            return packageName;
        }
        Log.e(TAG, "getTopPackage : Failed to get top information");
        return "";
    }

    public static int versionCompare(String str, String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length && split[i].equals(split2[i])) {
            i++;
        }
        return (i >= split.length || i >= split2.length)
                ? Integer.signum(split.length - split2.length)
                : Integer.signum(Integer.valueOf(split[i]).compareTo(Integer.valueOf(split2[i])));
    }
}
