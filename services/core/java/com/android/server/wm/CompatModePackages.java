package com.android.server.wm;

import android.app.AppGlobals;
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
import android.util.SparseBooleanArray;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.app.GameManagerService;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CompatModePackages {
    public final AtomicFile mFile;
    public final CompatHandler mHandler;
    public final ActivityTaskManagerService mService;
    public final HashMap mPackages = new HashMap();
    public final SparseBooleanArray mLegacyScreenCompatPackages = new SparseBooleanArray();
    public final SparseArray mProviders = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CompatHandler extends Handler {
        public CompatHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            HashMap hashMap;
            FileOutputStream fileOutputStream;
            IOException e;
            ApplicationInfo applicationInfo;
            if (message.what != 300) {
                return;
            }
            CompatModePackages compatModePackages = CompatModePackages.this;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    compatModePackages.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    hashMap = new HashMap(compatModePackages.mPackages);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            try {
                fileOutputStream = compatModePackages.mFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.setFeature(
                            "http://xmlpull.org/v1/doc/features.html#indent-output", true);
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
                                CompatibilityInfo compatibilityInfoForPackageLocked =
                                        compatModePackages.compatibilityInfoForPackageLocked(
                                                applicationInfo);
                                if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen()
                                        && !compatibilityInfoForPackageLocked
                                                .neverSupportsScreen()) {
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
                    compatModePackages.mFile.finishWrite(fileOutputStream);
                } catch (IOException e2) {
                    e = e2;
                    Slog.w("ActivityTaskManager", "Error writing compat packages", e);
                    if (fileOutputStream != null) {
                        compatModePackages.mFile.failWrite(fileOutputStream);
                    }
                }
            } catch (IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
        }
    }

    public CompatModePackages(
            Handler handler, ActivityTaskManagerService activityTaskManagerService, File file) {
        FileInputStream openRead;
        TypedXmlPullParser resolvePullParser;
        int eventType;
        String attributeValue;
        this.mService = activityTaskManagerService;
        AtomicFile atomicFile =
                new AtomicFile(new File(file, "packages-compat.xml"), "compat-mode");
        this.mFile = atomicFile;
        this.mHandler = new CompatHandler(handler.getLooper());
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    openRead = atomicFile.openRead();
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
                } catch (Throwable th) {
                    th = th;
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
                            if (resolvePullParser.getDepth() == 2
                                    && "pkg".equals(name)
                                    && (attributeValue =
                                                    resolvePullParser.getAttributeValue(
                                                            (String) null, "name"))
                                            != null) {
                                this.mPackages.put(
                                        attributeValue,
                                        Integer.valueOf(
                                                resolvePullParser.getAttributeInt(
                                                        (String) null, "mode", 0)));
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
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final CompatibilityInfo compatibilityInfoForPackageLocked(
            ApplicationInfo applicationInfo) {
        boolean z = (getPackageFlags(applicationInfo.packageName) & 2) != 0;
        CompatibilityInfo.CompatScale compatScaleFromProvider =
                getCompatScaleFromProvider(applicationInfo.uid, applicationInfo.packageName);
        float compatScale =
                compatScaleFromProvider != null
                        ? compatScaleFromProvider.mScaleFactor
                        : getCompatScale(applicationInfo.uid, applicationInfo.packageName, false);
        float f =
                compatScaleFromProvider != null
                        ? compatScaleFromProvider.mDensityScaleFactor
                        : compatScale;
        Configuration globalConfiguration = this.mService.getGlobalConfiguration();
        CompatibilityInfo compatibilityInfo =
                new CompatibilityInfo(
                        applicationInfo,
                        globalConfiguration.screenLayout,
                        globalConfiguration.smallestScreenWidthDp,
                        z,
                        compatScale,
                        f);
        if (applicationInfo.flags != 0 && applicationInfo.sourceDir != null) {
            if (!compatibilityInfo.supportsScreen()
                    && !"android".equals(applicationInfo.packageName)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Use legacy screen compat mode: "),
                        applicationInfo.packageName,
                        "ActivityTaskManager");
                this.mLegacyScreenCompatPackages.put(applicationInfo.packageName.hashCode(), true);
            } else if (this.mLegacyScreenCompatPackages.size() > 0) {
                this.mLegacyScreenCompatPackages.delete(applicationInfo.packageName.hashCode());
            }
        }
        return compatibilityInfo;
    }

    public final float getCompatScale(int i, String str, boolean z) {
        CompatibilityInfo.CompatScale compatScaleFromProvider;
        if (z && (compatScaleFromProvider = getCompatScaleFromProvider(i, str)) != null) {
            return compatScaleFromProvider.mScaleFactor;
        }
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        boolean isChangeEnabled = CompatChanges.isChangeEnabled(168419799L, str, userHandleForUid);
        boolean isChangeEnabled2 = CompatChanges.isChangeEnabled(273564678L, str, userHandleForUid);
        if (isChangeEnabled || isChangeEnabled2) {
            float f =
                    CompatChanges.isChangeEnabled(182811243L, str, userHandleForUid)
                            ? 0.9f
                            : CompatChanges.isChangeEnabled(189969734L, str, userHandleForUid)
                                    ? 0.85f
                                    : CompatChanges.isChangeEnabled(
                                                    176926753L, str, userHandleForUid)
                                            ? 0.8f
                                            : CompatChanges.isChangeEnabled(
                                                            189969779L, str, userHandleForUid)
                                                    ? 0.75f
                                                    : CompatChanges.isChangeEnabled(
                                                                    176926829L,
                                                                    str,
                                                                    userHandleForUid)
                                                            ? 0.7f
                                                            : CompatChanges.isChangeEnabled(
                                                                            189969744L,
                                                                            str,
                                                                            userHandleForUid)
                                                                    ? 0.65f
                                                                    : CompatChanges.isChangeEnabled(
                                                                                    176926771L,
                                                                                    str,
                                                                                    userHandleForUid)
                                                                            ? 0.6f
                                                                            : CompatChanges
                                                                                            .isChangeEnabled(
                                                                                                    189970036L,
                                                                                                    str,
                                                                                                    userHandleForUid)
                                                                                    ? 0.55f
                                                                                    : CompatChanges
                                                                                                    .isChangeEnabled(
                                                                                                            176926741L,
                                                                                                            str,
                                                                                                            userHandleForUid)
                                                                                            ? 0.5f
                                                                                            : CompatChanges
                                                                                                            .isChangeEnabled(
                                                                                                                    189969782L,
                                                                                                                    str,
                                                                                                                    userHandleForUid)
                                                                                                    ? 0.45f
                                                                                                    : CompatChanges
                                                                                                                    .isChangeEnabled(
                                                                                                                            189970038L,
                                                                                                                            str,
                                                                                                                            userHandleForUid)
                                                                                                            ? 0.4f
                                                                                                            : CompatChanges
                                                                                                                            .isChangeEnabled(
                                                                                                                                    189969749L,
                                                                                                                                    str,
                                                                                                                                    userHandleForUid)
                                                                                                                    ? 0.35f
                                                                                                                    : CompatChanges
                                                                                                                                    .isChangeEnabled(
                                                                                                                                            189970040L,
                                                                                                                                            str,
                                                                                                                                            userHandleForUid)
                                                                                                                            ? 0.3f
                                                                                                                            : 1.0f;
            if (f != 1.0f) {
                return isChangeEnabled2 ? f : 1.0f / f;
            }
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (activityTaskManagerService.mHasLeanbackFeature) {
            Configuration globalConfiguration = activityTaskManagerService.getGlobalConfiguration();
            int i2 =
                    (int)
                            ((globalConfiguration.smallestScreenWidthDp
                                            * (globalConfiguration.densityDpi / 160.0f))
                                    + 0.5f);
            if (i2 > 1080 && !CompatChanges.isChangeEnabled(157629738L, str, userHandleForUid)) {
                return i2 / 1080.0f;
            }
        }
        return 1.0f;
    }

    public final CompatibilityInfo.CompatScale getCompatScaleFromProvider(int i, String str) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.mProviders.size()) {
                return null;
            }
            GameManagerService.LocalService localService =
                    (GameManagerService.LocalService)
                            ((CompatScaleProvider) this.mProviders.valueAt(i2));
            localService.getClass();
            float resolutionScalingFactor =
                    localService.getResolutionScalingFactor(
                            str, UserHandle.getUserHandleForUid(i).getIdentifier());
            CompatibilityInfo.CompatScale compatScale =
                    resolutionScalingFactor > FullScreenMagnificationGestureHandler.MAX_SCALE
                            ? new CompatibilityInfo.CompatScale(1.0f / resolutionScalingFactor)
                            : null;
            if (compatScale != null) {
                return compatScale;
            }
            i2++;
        }
    }

    public final int getPackageFlags(String str) {
        Integer num = (Integer) this.mPackages.get(str);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public final void scheduleWrite() {
        CompatHandler compatHandler = this.mHandler;
        compatHandler.removeMessages(300);
        compatHandler.sendMessageDelayed(compatHandler.obtainMessage(300), 10000L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001a, code lost:

       if ((r1 & 2) == 0) goto L11;
    */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b8 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPackageScreenCompatModeLocked(
            android.content.pm.ApplicationInfo r13, int r14) {
        /*
            r12 = this;
            java.lang.String r0 = r13.packageName
            int r1 = r12.getPackageFlags(r0)
            java.lang.String r2 = "ActivityTaskManager"
            r3 = 1
            if (r14 == 0) goto L1f
            if (r14 == r3) goto L1c
            r4 = 2
            if (r14 == r4) goto L18
            java.lang.String r12 = "Unknown screen compat mode req #"
            java.lang.String r13 = "; ignoring"
            com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0.m(r14, r12, r13, r2)
            return
        L18:
            r14 = r1 & 2
            if (r14 != 0) goto L1f
        L1c:
            r14 = r1 | 2
            goto L21
        L1f:
            r14 = r1 & (-3)
        L21:
            android.content.res.CompatibilityInfo r4 = r12.compatibilityInfoForPackageLocked(r13)
            boolean r5 = r4.alwaysSupportsScreen()
            r6 = 0
            java.lang.String r7 = "Ignoring compat mode change of "
            if (r5 == 0) goto L34
            java.lang.String r14 = "; compatibility never needed"
            com.android.server.PinnerService$$ExternalSyntheticOutline0.m(r7, r0, r14, r2)
            r14 = r6
        L34:
            boolean r4 = r4.neverSupportsScreen()
            if (r4 == 0) goto L40
            java.lang.String r14 = "; compatibility always needed"
            com.android.server.PinnerService$$ExternalSyntheticOutline0.m(r7, r0, r14, r2)
            goto L41
        L40:
            r6 = r14
        L41:
            if (r6 == r1) goto Lb8
            if (r6 == 0) goto L4f
            java.util.HashMap r14 = r12.mPackages
            java.lang.Integer r1 = java.lang.Integer.valueOf(r6)
            r14.put(r0, r1)
            goto L54
        L4f:
            java.util.HashMap r14 = r12.mPackages
            r14.remove(r0)
        L54:
            android.content.res.CompatibilityInfo r13 = r12.compatibilityInfoForPackageLocked(r13)
            r12.scheduleWrite()
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            com.android.server.wm.ActivityTaskManagerService r12 = r12.mService
            com.android.server.wm.RootWindowContainer r1 = r12.mRootWindowContainer
            com.android.server.wm.CompatModePackages$$ExternalSyntheticLambda0 r2 = new com.android.server.wm.CompatModePackages$$ExternalSyntheticLambda0
            r2.<init>()
            r1.forAllWindows(r2, r3)
            com.android.server.wm.WindowProcessControllerMap r12 = r12.mProcessMap
            android.util.SparseArray r12 = r12.mPidMap
            int r1 = r12.size()
            int r1 = r1 - r3
        L75:
            if (r1 < 0) goto Lb8
            java.lang.Object r2 = r12.valueAt(r1)
            com.android.server.wm.WindowProcessController r2 = (com.android.server.wm.WindowProcessController) r2
            boolean r4 = r2.containsPackage(r0)
            if (r4 == 0) goto Lb5
            boolean r4 = r14.contains(r2)
            if (r4 == 0) goto L8a
            goto Lb5
        L8a:
            boolean r4 = r2.hasThread()     // Catch: java.lang.Exception -> Lb5
            if (r4 == 0) goto Lb5
            boolean[] r4 = com.android.internal.protolog.ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled     // Catch: java.lang.Exception -> Lb5
            boolean r4 = r4[r3]     // Catch: java.lang.Exception -> Lb5
            if (r4 == 0) goto Lb0
            java.lang.String r4 = r2.mName     // Catch: java.lang.Exception -> Lb5
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch: java.lang.Exception -> Lb5
            java.lang.String r5 = java.lang.String.valueOf(r13)     // Catch: java.lang.Exception -> Lb5
            com.android.internal.protolog.ProtoLogGroup r6 = com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION     // Catch: java.lang.Exception -> Lb5
            java.lang.Object[] r11 = new java.lang.Object[]{r4, r5}     // Catch: java.lang.Exception -> Lb5
            r7 = -74949168947384056(0xfef5ba20c0ae3108, double:-3.7249174762852256E303)
            r9 = 0
            r10 = 0
            com.android.internal.protolog.ProtoLogImpl_54989576.v(r6, r7, r9, r10, r11)     // Catch: java.lang.Exception -> Lb5
        Lb0:
            android.app.IApplicationThread r2 = r2.mThread     // Catch: java.lang.Exception -> Lb5
            r2.updatePackageCompatibilityInfo(r0, r13)     // Catch: java.lang.Exception -> Lb5
        Lb5:
            int r1 = r1 + (-1)
            goto L75
        Lb8:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.CompatModePackages.setPackageScreenCompatModeLocked(android.content.pm.ApplicationInfo,"
                    + " int):void");
    }
}
