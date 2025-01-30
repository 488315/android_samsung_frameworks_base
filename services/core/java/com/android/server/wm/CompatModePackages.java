package com.android.server.wm;

import android.app.AppGlobals;
import android.app.GameManagerInternal;
import android.app.compat.CompatChanges;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class CompatModePackages {
    public final AtomicFile mFile;
    public GameManagerInternal mGameManager;
    public final CompatHandler mHandler;
    public final HashMap mPackages = new HashMap();
    public final ActivityTaskManagerService mService;

    public final class CompatHandler extends Handler {
        public CompatHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 300) {
                return;
            }
            CompatModePackages.this.saveCompatModes();
        }
    }

    public CompatModePackages(ActivityTaskManagerService activityTaskManagerService, File file, Handler handler) {
        FileInputStream openRead;
        TypedXmlPullParser resolvePullParser;
        int eventType;
        String attributeValue;
        this.mService = activityTaskManagerService;
        AtomicFile atomicFile = new AtomicFile(new File(file, "packages-compat.xml"), "compat-mode");
        this.mFile = atomicFile;
        this.mHandler = new CompatHandler(handler.getLooper());
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    openRead = atomicFile.openRead();
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException e) {
                e = e;
            } catch (XmlPullParserException e2) {
                e = e2;
            }
            try {
                resolvePullParser = Xml.resolvePullParser(openRead);
                eventType = resolvePullParser.getEventType();
                while (eventType != 2 && eventType != 1) {
                    eventType = resolvePullParser.next();
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream = openRead;
                if (fileInputStream != null) {
                    Slog.w("ActivityTaskManager", "Error reading compat-packages", e);
                }
                if (fileInputStream == null) {
                    return;
                }
                fileInputStream.close();
            } catch (XmlPullParserException e4) {
                e = e4;
                fileInputStream = openRead;
                Slog.w("ActivityTaskManager", "Error reading compat-packages", e);
                if (fileInputStream == null) {
                    return;
                }
                fileInputStream.close();
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = openRead;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
            if (eventType == 1) {
                if (openRead != null) {
                    try {
                        openRead.close();
                        return;
                    } catch (IOException unused2) {
                        return;
                    }
                }
                return;
            }
            if ("compat-packages".equals(resolvePullParser.getName())) {
                int next = resolvePullParser.next();
                do {
                    if (next == 2) {
                        String name = resolvePullParser.getName();
                        if (resolvePullParser.getDepth() == 2 && "pkg".equals(name) && (attributeValue = resolvePullParser.getAttributeValue((String) null, "name")) != null) {
                            this.mPackages.put(attributeValue, Integer.valueOf(resolvePullParser.getAttributeInt((String) null, "mode", 0)));
                        }
                    }
                    next = resolvePullParser.next();
                } while (next != 1);
            }
            if (openRead != null) {
                openRead.close();
            }
        } catch (IOException unused3) {
        }
    }

    public HashMap getPackages() {
        return this.mPackages;
    }

    public final int getPackageFlags(String str) {
        Integer num = (Integer) this.mPackages.get(str);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public void handlePackageDataClearedLocked(String str) {
        removePackage(str);
    }

    public void handlePackageUninstalledLocked(String str) {
        removePackage(str);
    }

    public final void removePackage(String str) {
        if (this.mPackages.containsKey(str)) {
            this.mPackages.remove(str);
            scheduleWrite();
        }
    }

    public void handlePackageAddedLocked(String str, boolean z) {
        ApplicationInfo applicationInfo;
        boolean z2 = false;
        try {
            applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (RemoteException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return;
        }
        CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen() && !compatibilityInfoForPackageLocked.neverSupportsScreen()) {
            z2 = true;
        }
        if (z && !z2 && this.mPackages.containsKey(str)) {
            this.mPackages.remove(str);
            scheduleWrite();
        }
    }

    public final void scheduleWrite() {
        this.mHandler.removeMessages(300);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(300), 10000L);
    }

    public CompatibilityInfo compatibilityInfoForPackageLocked(ApplicationInfo applicationInfo) {
        boolean packageCompatModeEnabledLocked = getPackageCompatModeEnabledLocked(applicationInfo);
        float compatScale = getCompatScale(applicationInfo.packageName, applicationInfo.uid);
        Configuration globalConfiguration = this.mService.getGlobalConfiguration();
        return new CompatibilityInfo(applicationInfo, globalConfiguration.screenLayout, globalConfiguration.smallestScreenWidthDp, packageCompatModeEnabledLocked, compatScale);
    }

    public float getCompatScale(String str, int i) {
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        if (this.mGameManager == null) {
            this.mGameManager = (GameManagerInternal) LocalServices.getService(GameManagerInternal.class);
        }
        if (this.mGameManager != null) {
            float resolutionScalingFactor = this.mGameManager.getResolutionScalingFactor(str, userHandleForUid.getIdentifier());
            if (resolutionScalingFactor > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                return 1.0f / resolutionScalingFactor;
            }
        }
        boolean isChangeEnabled = CompatChanges.isChangeEnabled(168419799L, str, userHandleForUid);
        boolean isChangeEnabled2 = CompatChanges.isChangeEnabled(273564678L, str, userHandleForUid);
        if (isChangeEnabled || isChangeEnabled2) {
            float scalingFactor = getScalingFactor(str, userHandleForUid);
            if (scalingFactor != 1.0f) {
                return isChangeEnabled2 ? scalingFactor : 1.0f / scalingFactor;
            }
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mHasLeanbackFeature) {
            Configuration globalConfiguration = activityTaskManagerService.getGlobalConfiguration();
            int i2 = (int) ((globalConfiguration.smallestScreenWidthDp * (globalConfiguration.densityDpi / 160.0f)) + 0.5f);
            if (i2 > 1080 && !CompatChanges.isChangeEnabled(157629738L, str, userHandleForUid)) {
                return i2 / 1080.0f;
            }
        }
        return 1.0f;
    }

    public static float getScalingFactor(String str, UserHandle userHandle) {
        if (CompatChanges.isChangeEnabled(182811243L, str, userHandle)) {
            return 0.9f;
        }
        if (CompatChanges.isChangeEnabled(189969734L, str, userHandle)) {
            return 0.85f;
        }
        if (CompatChanges.isChangeEnabled(176926753L, str, userHandle)) {
            return 0.8f;
        }
        if (CompatChanges.isChangeEnabled(189969779L, str, userHandle)) {
            return 0.75f;
        }
        if (CompatChanges.isChangeEnabled(176926829L, str, userHandle)) {
            return 0.7f;
        }
        if (CompatChanges.isChangeEnabled(189969744L, str, userHandle)) {
            return 0.65f;
        }
        if (CompatChanges.isChangeEnabled(176926771L, str, userHandle)) {
            return 0.6f;
        }
        if (CompatChanges.isChangeEnabled(189970036L, str, userHandle)) {
            return 0.55f;
        }
        if (CompatChanges.isChangeEnabled(176926741L, str, userHandle)) {
            return 0.5f;
        }
        if (CompatChanges.isChangeEnabled(189969782L, str, userHandle)) {
            return 0.45f;
        }
        if (CompatChanges.isChangeEnabled(189970038L, str, userHandle)) {
            return 0.4f;
        }
        if (CompatChanges.isChangeEnabled(189969749L, str, userHandle)) {
            return 0.35f;
        }
        return CompatChanges.isChangeEnabled(189970040L, str, userHandle) ? 0.3f : 1.0f;
    }

    public int computeCompatModeLocked(ApplicationInfo applicationInfo) {
        CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
            return -2;
        }
        if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
            return -1;
        }
        return getPackageCompatModeEnabledLocked(applicationInfo) ? 1 : 0;
    }

    public boolean getPackageAskCompatModeLocked(String str) {
        return (getPackageFlags(str) & 1) == 0;
    }

    public void setPackageAskCompatModeLocked(String str, boolean z) {
        setPackageFlagLocked(str, 1, z);
    }

    public final boolean getPackageCompatModeEnabledLocked(ApplicationInfo applicationInfo) {
        return (getPackageFlags(applicationInfo.packageName) & 2) != 0;
    }

    public final void setPackageFlagLocked(String str, int i, boolean z) {
        int packageFlags = getPackageFlags(str);
        int i2 = z ? (~i) & packageFlags : i | packageFlags;
        if (packageFlags != i2) {
            if (i2 != 0) {
                this.mPackages.put(str, Integer.valueOf(i2));
            } else {
                this.mPackages.remove(str);
            }
            scheduleWrite();
        }
    }

    public int getPackageScreenCompatModeLocked(String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (RemoteException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return -3;
        }
        return computeCompatModeLocked(applicationInfo);
    }

    public void setPackageScreenCompatModeLocked(String str, int i) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
        } catch (RemoteException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            Slog.w("ActivityTaskManager", "setPackageScreenCompatMode failed: unknown package " + str);
            return;
        }
        setPackageScreenCompatModeLocked(applicationInfo, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002d, code lost:
    
        if ((r1 & 2) == 0) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setPackageScreenCompatModeLocked(ApplicationInfo applicationInfo, int i) {
        boolean z;
        int i2;
        CompatibilityInfo compatibilityInfoForPackageLocked;
        String str = applicationInfo.packageName;
        int packageFlags = getPackageFlags(str);
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    Slog.w("ActivityTaskManager", "Unknown screen compat mode req #" + i + "; ignoring");
                    return;
                }
            }
            z = true;
            i2 = !z ? packageFlags | 2 : packageFlags & (-3);
            compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
            if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
                Slog.w("ActivityTaskManager", "Ignoring compat mode change of " + str + "; compatibility never needed");
                i2 = 0;
            }
            if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                Slog.w("ActivityTaskManager", "Ignoring compat mode change of " + str + "; compatibility always needed");
                i2 = 0;
            }
            if (i2 == packageFlags) {
                if (i2 != 0) {
                    this.mPackages.put(str, Integer.valueOf(i2));
                } else {
                    this.mPackages.remove(str);
                }
                CompatibilityInfo compatibilityInfoForPackageLocked2 = compatibilityInfoForPackageLocked(applicationInfo);
                scheduleWrite();
                Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
                ActivityRecord restartPackage = topDisplayFocusedRootTask.restartPackage(str);
                SparseArray pidMap = this.mService.mProcessMap.getPidMap();
                for (int size = pidMap.size() - 1; size >= 0; size--) {
                    WindowProcessController windowProcessController = (WindowProcessController) pidMap.valueAt(size);
                    if (windowProcessController.containsPackage(str)) {
                        try {
                            if (windowProcessController.hasThread()) {
                                if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
                                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, 1337596507, 0, (String) null, new Object[]{String.valueOf(windowProcessController.mName), String.valueOf(compatibilityInfoForPackageLocked2)});
                                }
                                windowProcessController.getThread().updatePackageCompatibilityInfo(str, compatibilityInfoForPackageLocked2);
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
                if (restartPackage != null) {
                    restartPackage.ensureActivityConfiguration(0, false);
                    topDisplayFocusedRootTask.ensureActivitiesVisible(restartPackage, 0, false);
                    return;
                }
                return;
            }
            return;
        }
        z = false;
        if (!z) {
        }
        compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
        if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
        }
        if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
        }
        if (i2 == packageFlags) {
        }
    }

    public final void saveCompatModes() {
        HashMap hashMap;
        FileOutputStream fileOutputStream;
        IOException e;
        ApplicationInfo applicationInfo;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                hashMap = new HashMap(this.mPackages);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        try {
            fileOutputStream = this.mFile.startWrite();
        } catch (IOException e2) {
            fileOutputStream = null;
            e = e2;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "compat-packages");
            IPackageManager packageManager = AppGlobals.getPackageManager();
            for (Map.Entry entry : hashMap.entrySet()) {
                String str = (String) entry.getKey();
                int intValue = ((Integer) entry.getValue()).intValue();
                if (intValue != 0) {
                    try {
                        applicationInfo = packageManager.getApplicationInfo(str, 0L, 0);
                    } catch (RemoteException unused) {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        CompatibilityInfo compatibilityInfoForPackageLocked = compatibilityInfoForPackageLocked(applicationInfo);
                        if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen() && !compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                            resolveSerializer.startTag((String) null, "pkg");
                            resolveSerializer.attribute((String) null, "name", str);
                            resolveSerializer.attributeInt((String) null, "mode", intValue);
                            resolveSerializer.endTag((String) null, "pkg");
                        }
                    }
                }
            }
            resolveSerializer.endTag((String) null, "compat-packages");
            resolveSerializer.endDocument();
            this.mFile.finishWrite(fileOutputStream);
        } catch (IOException e3) {
            e = e3;
            Slog.w("ActivityTaskManager", "Error writing compat packages", e);
            if (fileOutputStream != null) {
                this.mFile.failWrite(fileOutputStream);
            }
        }
    }
}
