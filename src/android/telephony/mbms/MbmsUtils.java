package android.telephony.mbms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.telecom.Logging.Session;
import android.telephony.MbmsDownloadSession;
import android.telephony.MbmsGroupCallSession;
import android.telephony.MbmsStreamingSession;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* loaded from: classes4.dex */
public class MbmsUtils {
    private static final String LOG_TAG = "MbmsUtils";

    public static boolean isContainedIn(File file, File file2) {
        try {
            return file2.getCanonicalPath().startsWith(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to resolve canonical paths: " + e);
        }
    }

    public static ComponentName toComponentName(ComponentInfo componentInfo) {
        return new ComponentName(componentInfo.packageName, componentInfo.name);
    }

    public static ComponentName getOverrideServiceName(Context context, String str) {
        String str2;
        String string;
        str.hashCode();
        switch (str) {
            case "android.telephony.action.EmbmsStreaming":
                str2 = MbmsStreamingSession.MBMS_STREAMING_SERVICE_OVERRIDE_METADATA;
                break;
            case "android.telephony.action.EmbmsDownload":
                str2 = MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_OVERRIDE_METADATA;
                break;
            case "android.telephony.action.EmbmsGroupCall":
                str2 = MbmsGroupCallSession.MBMS_GROUP_CALL_SERVICE_OVERRIDE_METADATA;
                break;
            default:
                str2 = null;
                break;
        }
        if (str2 == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null || (string = applicationInfo.metaData.getString(str2)) == null) {
                return null;
            }
            return ComponentName.unflattenFromString(string);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static android.content.pm.ServiceInfo getMiddlewareServiceInfo(Context context, String str) {
        List<ResolveInfo> queryIntentServices;
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(str);
        ComponentName overrideServiceName = getOverrideServiceName(context, str);
        if (overrideServiceName == null) {
            queryIntentServices = packageManager.queryIntentServices(intent, 1048576);
        } else {
            intent.setComponent(overrideServiceName);
            queryIntentServices = packageManager.queryIntentServices(intent, 131072);
        }
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Log.w(LOG_TAG, "No MBMS services found, cannot get service info");
            return null;
        }
        if (queryIntentServices.size() > 1) {
            Log.w(LOG_TAG, "More than one MBMS service found, cannot get unique service");
            return null;
        }
        return queryIntentServices.get(0).serviceInfo;
    }

    public static int startBinding(Context context, String str, ServiceConnection serviceConnection) {
        Intent intent = new Intent();
        android.content.pm.ServiceInfo middlewareServiceInfo = getMiddlewareServiceInfo(context, str);
        if (middlewareServiceInfo == null) {
            return 1;
        }
        intent.setComponent(toComponentName(middlewareServiceInfo));
        context.bindService(intent, serviceConnection, 1);
        return 0;
    }

    public static File getEmbmsTempFileDirForService(Context context, String str) {
        return new File(MbmsTempFileProvider.getEmbmsTempFileDir(context), str.replaceAll("[^a-zA-Z0-9_]", Session.SESSION_SEPARATION_CHAR_CHILD));
    }
}
