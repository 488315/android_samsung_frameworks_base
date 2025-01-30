package com.android.server.media;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.IMediaResourceMonitor;
import android.media.MediaMonitorDimension;
import android.media.MediaMonitorEvent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.SystemService;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaResourceMonitorService extends SystemService {
    public static final boolean DEBUG = Log.isLoggable("MediaResourceMonitor", 3);
    public static final boolean mServiceEnabled = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public final String CUSTOM_DIMENSION_PACKAGE_KEY;
    public final String CUSTOM_DIMENSION_UNSUPPORTED_CODEC_KEY;
    public final MediaResourceMonitorImpl mMediaResourceMonitorImpl;
    public PackageManager mPackageManager;

    public MediaResourceMonitorService(Context context) {
        super(context);
        this.CUSTOM_DIMENSION_PACKAGE_KEY = "1003";
        this.CUSTOM_DIMENSION_UNSUPPORTED_CODEC_KEY = "2001";
        this.mMediaResourceMonitorImpl = new MediaResourceMonitorImpl();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("media_resource_monitor", this.mMediaResourceMonitorImpl);
    }

    public class MediaResourceMonitorImpl extends IMediaResourceMonitor.Stub {
        public MediaResourceMonitorImpl() {
        }

        public void notifyResourceGranted(int i, int i2) {
            MediaResourceMonitorService mediaResourceMonitorService = MediaResourceMonitorService.this;
            mediaResourceMonitorService.mPackageManager = mediaResourceMonitorService.getContext().getPackageManager();
            if (MediaResourceMonitorService.this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                if (MediaResourceMonitorService.DEBUG) {
                    Log.d("MediaResourceMonitor", "notifyResourceGranted(pid=" + i + ", type=" + i2 + ")");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String[] packageNamesFromPid = getPackageNamesFromPid(i);
                    if (packageNamesFromPid == null) {
                        return;
                    }
                    List enabledProfiles = ((UserManager) MediaResourceMonitorService.this.getContext().createContextAsUser(UserHandle.of(ActivityManager.getCurrentUser()), 0).getSystemService(UserManager.class)).getEnabledProfiles();
                    if (enabledProfiles.isEmpty()) {
                        return;
                    }
                    Intent intent = new Intent("android.intent.action.MEDIA_RESOURCE_GRANTED");
                    intent.putExtra("android.intent.extra.PACKAGES", packageNamesFromPid);
                    intent.putExtra("android.intent.extra.MEDIA_RESOURCE_TYPE", i2);
                    Iterator it = enabledProfiles.iterator();
                    while (it.hasNext()) {
                        MediaResourceMonitorService.this.getContext().sendBroadcastAsUser(intent, (UserHandle) it.next(), "android.permission.RECEIVE_MEDIA_RESOURCE_USAGE");
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public void notifyMediaInfo(int i, MediaMonitorEvent mediaMonitorEvent) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    Intent broadcastIntent = getBroadcastIntent(i, mediaMonitorEvent);
                    if (MediaResourceMonitorService.mServiceEnabled) {
                        MediaResourceMonitorService.this.getContext().sendBroadcast(broadcastIntent);
                    }
                } catch (Exception unused) {
                    Log.i("MediaResourceMonitor", "sendBroadcast fail");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Intent getBroadcastIntent(int i, MediaMonitorEvent mediaMonitorEvent) {
            String[] packageNamesFromPid;
            String[] packageNamesFromPid2;
            Bundle bundle = new Bundle();
            bundle.putString("tracking_id", "4H9-399-505457");
            bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, mediaMonitorEvent.mName);
            if (120100 <= Build.VERSION.SEM_PLATFORM_INT) {
                HashMap hashMap = new HashMap();
                for (MediaMonitorDimension mediaMonitorDimension : mediaMonitorEvent.mCustomDimensions) {
                    int i2 = mediaMonitorDimension.mType;
                    String str = mediaMonitorDimension.mName;
                    if (i2 == 0) {
                        String text = mediaMonitorDimension.getText();
                        if (str.equals("1003")) {
                            text = text + "," + getPackageName(i);
                        } else if (str.equals("2001") && (packageNamesFromPid2 = getPackageNamesFromPid(i)) != null && packageNamesFromPid2.length > 0) {
                            text = text + ", " + packageNamesFromPid2[0];
                        }
                        hashMap.put(str, text);
                    } else if (i2 == 1) {
                        bundle.putLong(str, mediaMonitorDimension.getNumber());
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                bundle.putSerializable("dimension", hashMap);
            } else {
                for (MediaMonitorDimension mediaMonitorDimension2 : mediaMonitorEvent.mCustomDimensions) {
                    int i3 = mediaMonitorDimension2.mType;
                    String str2 = mediaMonitorDimension2.mName;
                    if (i3 == 0) {
                        String text2 = mediaMonitorDimension2.getText();
                        if (mediaMonitorEvent.mName.equals("MMIF_PACKAGE")) {
                            text2 = text2 + "," + getPackageName(i);
                        } else if (mediaMonitorEvent.mName.equals("MMER") && (packageNamesFromPid = getPackageNamesFromPid(i)) != null && packageNamesFromPid.length > 0) {
                            text2 = packageNamesFromPid[0] + ", " + text2;
                        }
                        bundle.putString(str2, text2);
                    } else if (i3 == 1) {
                        bundle.putLong(str2, mediaMonitorDimension2.getNumber());
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
            bundle.putString("type", "ev");
            bundle.putString("pkg_name", "com.samsung.android.mmfw");
            Intent intent = new Intent();
            intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
            intent.putExtras(bundle);
            intent.setPackage("com.sec.android.diagmonagent");
            return intent;
        }

        public final String getPackageName(int i) {
            String[] packageNamesFromPid;
            if (i == -1 || (packageNamesFromPid = getPackageNamesFromPid(i)) == null || packageNamesFromPid.length <= 0) {
                return "";
            }
            String str = packageNamesFromPid[0];
            return str.substring(str.lastIndexOf(".") + 1);
        }

        public final String[] getPackageNamesFromPid(int i) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) MediaResourceMonitorService.this.getContext().getSystemService(ActivityManager.class)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == i) {
                    return runningAppProcessInfo.pkgList;
                }
            }
            return null;
        }
    }
}
