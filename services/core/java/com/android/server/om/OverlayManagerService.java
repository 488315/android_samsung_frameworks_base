package com.android.server.om;

import android.R;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.om.OverlayManagerTransaction;
import android.content.om.OverlayableInfo;
import android.content.om.wallpapertheme.ThemeUtil;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.content.pm.UserPackage;
import android.content.pm.overlay.OverlayPaths;
import android.content.res.ApkAssets;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.FabricatedOverlayInternal;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.om.OverlayActorEnforcer;
import com.android.server.om.OverlayManagerServiceImpl;
import com.android.server.om.OverlayManagerSettings;
import com.android.server.om.wallpapertheme.SemWallpaperThemeManager;
import com.android.server.om.wallpapertheme.SemWallpaperThemeManagerWrapper;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda40;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import libcore.util.EmptyArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverlayManagerService extends SystemService {
    public final OverlayActorEnforcer mActorEnforcer;
    public final OverlayManagerServiceImpl mImpl;
    public final Object mLock;
    public final OverlayManagerServiceExt mOverlayManagerServiceExt;
    public final PackageManagerHelperImpl mPackageManager;
    public final OverlayManagerPackageMonitor mPackageMonitor;
    public int mPrevStartedUserId;
    public final AnonymousClass1 mService;
    public final OverlayManagerSettings mSettings;
    public final ResilientAtomicFile mSettingsFile;
    public final UserManagerService mUserManager;
    public final SemWallpaperThemeManagerWrapper mWallpaperThemeManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.om.OverlayManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IOverlayManager.Stub {
        public static void $r8$lambda$brKP91vTER4zoSOxWJ1OnJjU4jQ(OverlayManagerService overlayManagerService, UserPackage userPackage) {
            overlayManagerService.getClass();
            if (userPackage != null) {
                overlayManagerService.updateTargetPackagesLocked(Set.of(userPackage), false);
            }
        }

        public AnonymousClass1() {
        }

        public static String checkUserOrTrialPackageJson(String str) {
            File file = new File("/data/overlays/jsonfiles/userjson");
            if (file.exists() && file.list() != null) {
                for (String str2 : file.list()) {
                    if (str2.startsWith(str)) {
                        return file.getPath() + "/" + str2;
                    }
                }
            }
            File file2 = new File("/data/overlays/jsonfiles/trialjson");
            if (!file2.exists() || file2.list() == null) {
                return null;
            }
            for (String str3 : file2.list()) {
                if (str3.startsWith(str)) {
                    return file2.getPath() + "/" + str3;
                }
            }
            return null;
        }

        public static int handleIncomingUser(int i, String str) {
            return ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, true, str, null);
        }

        public static void printJsonInfo(PrintWriter printWriter, String str) {
            printWriter.println("current theme Json: ".concat(str));
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                printWriter.println(new JSONObject(sb.toString()).toString(3) + "\n");
                                return;
                            }
                            sb.append(readLine);
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        public final void addOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                if (overlayManagerServiceExt != null) {
                    overlayManagerServiceExt.notifySystemServices(i, (List) ((Stream) OverlayManagerServiceExt.getSafeStream(list).parallel()).map(new OverlayManagerServiceExt$$ExternalSyntheticLambda0(overlayManagerServiceExt, i, iSamsungOverlayCallback, 0)).collect(Collectors.toList()), list);
                }
            }
        }

        public final void applyThemeParkWallpaperColor(Uri uri) {
            SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper = OverlayManagerService.this.mWallpaperThemeManager;
            semWallpaperThemeManagerWrapper.initTemplateMetadataIfNeeded();
            SemWallpaperThemeManager semWallpaperThemeManager = semWallpaperThemeManagerWrapper.mWallpaperThemeManager;
            semWallpaperThemeManager.mTemplateManager.loadTemplateFromUri(semWallpaperThemeManager.mContext, uri);
            try {
                InputStream openInputStream = SemWallpaperThemeManagerWrapper.mContext.getContentResolver().openInputStream(uri);
                try {
                    ArrayList arrayList = new ArrayList();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = openInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        } else {
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                    }
                    JSONArray jSONArray = new JSONObject(byteArrayOutputStream.toString("UTF-8")).getJSONArray("wallpaperColors");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(Integer.valueOf(jSONArray.optInt(i)));
                    }
                    semWallpaperThemeManagerWrapper.applyWallpaperColor(arrayList, arrayList, false, true);
                    semWallpaperThemeManagerWrapper.mIsThemeParkApplied = true;
                    openInputStream.close();
                } finally {
                }
            } catch (Exception e) {
                Log.e("SWT_WTM_Wrapper", "Failed at applyThemeParkWallpaperColor, e = ", e);
            }
        }

        public final void applyWallpaperColor(List list, List list2, boolean z) {
            OverlayManagerService.this.mWallpaperThemeManager.applyWallpaperColor(list, list2, z, false);
        }

        public final void applyWallpaperColors(List list, int i, int i2) {
            SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper = OverlayManagerService.this.mWallpaperThemeManager;
            semWallpaperThemeManagerWrapper.getClass();
            Log.i("SWT_WTM_Wrapper", "(Deprecated) invoked applyWallpaperColors, colors=" + list);
            semWallpaperThemeManagerWrapper.applyWallpaperColor(list, list, false, false);
        }

        public final boolean changeOverlayState(String str, int i, boolean z) {
            AndroidPackage androidPackage = null;
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                try {
                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                    boolean z2 = false;
                    if (overlayManagerServiceExt == null) {
                        return false;
                    }
                    OverlayManagerSettings overlayManagerSettings = overlayManagerServiceExt.mSettings;
                    if (str != null) {
                        try {
                            OverlayInfo overlayInfo = overlayManagerSettings.getOverlayInfo(new OverlayIdentifier(str), i);
                            OverlayInfoExt initFromInfo = OverlayInfoExt.initFromInfo(overlayInfo);
                            if (initFromInfo != null && initFromInfo.info != null) {
                                try {
                                    overlayManagerSettings.setEnabled(i, initFromInfo.getOverlayIdentifier(), z);
                                    PackageState packageStateForUser = overlayManagerServiceExt.mPackageManager.packageManagerHelper.getPackageStateForUser(i, overlayInfo.getTargetPackageName());
                                    if (packageStateForUser != null) {
                                        androidPackage = packageStateForUser.getAndroidPackage();
                                    }
                                    int handleStateUpdate = overlayManagerServiceExt.handleStateUpdate(androidPackage, overlayInfo, 0, i);
                                    if (handleStateUpdate == 1 || handleStateUpdate == 2) {
                                        z2 = true;
                                    }
                                } catch (OverlayManagerSettings.BadKeyException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (OverlayManagerSettings.BadKeyException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return z2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void commit(OverlayManagerTransaction overlayManagerTransaction) {
            try {
                Trace.traceBegin(67108864L, "OMS#commit " + overlayManagerTransaction);
                try {
                    executeAllRequests(overlayManagerTransaction);
                } catch (Exception e) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        OverlayManagerService.this.restoreSettings();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Slog.d("OverlayManager", "commit failed: " + e.getMessage(), e);
                        throw new SecurityException("commit failed: " + e.getMessage());
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x00f4, code lost:
        
            if (r5.equals("priority") == false) goto L38;
         */
        /* JADX WARN: Removed duplicated region for block: B:101:0x0294 A[Catch: Exception -> 0x02bf, TRY_ENTER, TryCatch #1 {Exception -> 0x02bf, blocks: (B:101:0x0294, B:103:0x02bb, B:107:0x02c1), top: B:99:0x0292 }] */
        /* JADX WARN: Removed duplicated region for block: B:107:0x02c1 A[Catch: Exception -> 0x02bf, TRY_LEAVE, TryCatch #1 {Exception -> 0x02bf, blocks: (B:101:0x0294, B:103:0x02bb, B:107:0x02c1), top: B:99:0x0292 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(java.io.FileDescriptor r11, java.io.PrintWriter r12, java.lang.String[] r13) {
            /*
                Method dump skipped, instructions count: 808
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.OverlayManagerService.AnonymousClass1.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
        }

        public final void dumpFilesInDir(File file, PrintWriter printWriter) {
            File[] listFiles;
            ApplicationInfo applicationInfo;
            if (!file.exists() || (listFiles = file.listFiles()) == null) {
                return;
            }
            for (File file2 : listFiles) {
                PackageInfo packageArchiveInfo = OverlayManagerService.this.getContext().getPackageManager().getPackageArchiveInfo(file2.getPath(), PackageManager.PackageInfoFlags.of(4096L));
                if (packageArchiveInfo != null && (applicationInfo = packageArchiveInfo.applicationInfo) != null) {
                    String charSequence = applicationInfo.loadLabel(OverlayManagerService.this.getContext().getPackageManager()).toString();
                    if (charSequence != null) {
                        printWriter.println(OptionalModelParameterRange$$ExternalSyntheticOutline0.m(new StringBuilder("   "), packageArchiveInfo.packageName, "(", charSequence, ")"));
                    } else {
                        ProxyManager$$ExternalSyntheticOutline0.m(printWriter, packageArchiveInfo.packageName, "()", new StringBuilder("   "));
                    }
                }
            }
        }

        public final void enforceActor(int i, OverlayIdentifier overlayIdentifier, String str) {
            OverlayInfo overlayInfo;
            OverlayManagerServiceImpl overlayManagerServiceImpl = OverlayManagerService.this.mImpl;
            overlayManagerServiceImpl.getClass();
            try {
                overlayInfo = overlayManagerServiceImpl.mSettings.getOverlayInfo(overlayIdentifier, i);
            } catch (OverlayManagerSettings.BadKeyException unused) {
                overlayInfo = null;
            }
            if (overlayInfo == null) {
                throw new IllegalArgumentException("Unable to retrieve overlay information for " + overlayIdentifier);
            }
            int callingUid = Binder.getCallingUid();
            OverlayActorEnforcer.ActorState isAllowedActor = OverlayManagerService.this.mActorEnforcer.isAllowedActor(str, overlayInfo, callingUid, i);
            if (isAllowedActor == OverlayActorEnforcer.ActorState.ALLOWED) {
                return;
            }
            String str2 = overlayInfo.targetOverlayableName;
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(callingUid, "UID", " is not allowed to call ", str, " for ");
            m.append(TextUtils.isEmpty(str2) ? "" : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, " in "));
            m.append(overlayInfo.targetPackageName);
            m.append(" for user ");
            m.append(i);
            String sb = m.toString();
            Slog.w("OverlayManager", sb + " because " + isAllowedActor);
            throw new SecurityException(sb);
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0059 A[Catch: all -> 0x006e, TryCatch #1 {all -> 0x006e, blocks: (B:6:0x001a, B:8:0x002a, B:10:0x0034, B:12:0x0040, B:14:0x0044, B:16:0x004c, B:20:0x0059, B:22:0x0067, B:25:0x0070, B:26:0x0073, B:27:0x0077, B:29:0x007d, B:31:0x008c, B:34:0x0095, B:35:0x0098, B:40:0x009b, B:41:0x009e, B:33:0x0090), top: B:5:0x001a, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:29:0x007d A[Catch: all -> 0x006e, LOOP:0: B:27:0x0077->B:29:0x007d, LOOP_END, TryCatch #1 {all -> 0x006e, blocks: (B:6:0x001a, B:8:0x002a, B:10:0x0034, B:12:0x0040, B:14:0x0044, B:16:0x004c, B:20:0x0059, B:22:0x0067, B:25:0x0070, B:26:0x0073, B:27:0x0077, B:29:0x007d, B:31:0x008c, B:34:0x0095, B:35:0x0098, B:40:0x009b, B:41:0x009e, B:33:0x0090), top: B:5:0x001a, inners: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void executeAllRequests(android.content.om.OverlayManagerTransaction r9) {
            /*
                r8 = this;
                java.lang.String r0 = "OverlayManager"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "commit "
                r1.<init>(r2)
                r1.append(r9)
                java.lang.String r1 = r1.toString()
                android.util.Slog.d(r0, r1)
                if (r9 == 0) goto La1
                com.android.server.om.OverlayManagerService r0 = com.android.server.om.OverlayManagerService.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                com.android.server.om.OverlayManagerService r1 = com.android.server.om.OverlayManagerService.this     // Catch: java.lang.Throwable -> L6e
                com.android.server.om.wallpapertheme.SemWallpaperThemeManagerWrapper r1 = r1.mWallpaperThemeManager     // Catch: java.lang.Throwable -> L6e
                r1.getClass()     // Catch: java.lang.Throwable -> L6e
                java.util.Iterator r1 = r9.getRequests()     // Catch: java.lang.Throwable -> L6e
                r2 = 0
                r3 = 1
                r4 = 0
                if (r1 == 0) goto L56
                java.util.Iterator r1 = r9.getRequests()     // Catch: java.lang.Throwable -> L6e
                boolean r1 = r1.hasNext()     // Catch: java.lang.Throwable -> L6e
                if (r1 == 0) goto L56
                java.util.Iterator r1 = r9.getRequests()     // Catch: java.lang.Throwable -> L6e
                java.lang.Object r1 = r1.next()     // Catch: java.lang.Throwable -> L6e
                android.content.om.OverlayManagerTransaction$Request r1 = (android.content.om.OverlayManagerTransaction.Request) r1     // Catch: java.lang.Throwable -> L6e
                if (r1 == 0) goto L49
                android.content.om.OverlayIdentifier r1 = r1.overlay     // Catch: java.lang.Throwable -> L6e
                if (r1 == 0) goto L49
                java.lang.String r1 = r1.getOverlayName()     // Catch: java.lang.Throwable -> L6e
                goto L4a
            L49:
                r1 = r4
            L4a:
                if (r1 == 0) goto L56
                java.lang.String r5 = "SemWT_"
                boolean r1 = r1.startsWith(r5)     // Catch: java.lang.Throwable -> L6e
                if (r1 == 0) goto L56
                r1 = r3
                goto L57
            L56:
                r1 = r2
            L57:
                if (r1 == 0) goto L73
                java.util.Iterator r5 = r9.getRequests()     // Catch: java.lang.Throwable -> L6e
                java.lang.Object r5 = r5.next()     // Catch: java.lang.Throwable -> L6e
                android.content.om.OverlayManagerTransaction$Request r5 = (android.content.om.OverlayManagerTransaction.Request) r5     // Catch: java.lang.Throwable -> L6e
                com.android.server.om.OverlayManagerService r6 = com.android.server.om.OverlayManagerService.this     // Catch: java.lang.Throwable -> L6e
                if (r5 == 0) goto L70
                int r5 = r5.type     // Catch: java.lang.Throwable -> L6e
                r7 = 2
                if (r5 != r7) goto L70
                r2 = r3
                goto L70
            L6e:
                r8 = move-exception
                goto L9f
            L70:
                com.android.server.om.OverlayManagerService.m738$$Nest$munregisterColorThemeGG(r6, r2)     // Catch: java.lang.Throwable -> L6e
            L73:
                java.util.Iterator r9 = r9.getRequests()     // Catch: java.lang.Throwable -> L6e
            L77:
                boolean r2 = r9.hasNext()     // Catch: java.lang.Throwable -> L6e
                if (r2 == 0) goto L8c
                java.lang.Object r2 = r9.next()     // Catch: java.lang.Throwable -> L6e
                android.content.om.OverlayManagerTransaction$Request r2 = (android.content.om.OverlayManagerTransaction.Request) r2     // Catch: java.lang.Throwable -> L6e
                java.util.Set r2 = r8.executeRequest(r2, r1)     // Catch: java.lang.Throwable -> L6e
                java.util.Set r4 = com.android.internal.util.CollectionUtils.addAll(r4, r2)     // Catch: java.lang.Throwable -> L6e
                goto L77
            L8c:
                long r2 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L6e
                com.android.server.om.OverlayManagerService r8 = com.android.server.om.OverlayManagerService.this     // Catch: java.lang.Throwable -> L9a
                r8.updateTargetPackagesLocked(r4, r1)     // Catch: java.lang.Throwable -> L9a
                android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L6e
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
                return
            L9a:
                r8 = move-exception
                android.os.Binder.restoreCallingIdentity(r2)     // Catch: java.lang.Throwable -> L6e
                throw r8     // Catch: java.lang.Throwable -> L6e
            L9f:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
                throw r8
            La1:
                java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
                java.lang.String r9 = "null transaction"
                r8.<init>(r9)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.OverlayManagerService.AnonymousClass1.executeAllRequests(android.content.om.OverlayManagerTransaction):void");
        }

        public final Set executeRequest(OverlayManagerTransaction.Request request, boolean z) {
            int i;
            String[] strArr;
            OverlayInfo overlayInfo;
            Objects.requireNonNull(request, "Transaction contains a null request");
            Objects.requireNonNull(request.overlay, "Transaction overlay identifier must be non-null");
            int callingUid = Binder.getCallingUid();
            int i2 = request.type;
            if (i2 != 2 && i2 != 3) {
                i = handleIncomingUser(request.userId, request.typeToString());
                if (z) {
                    OverlayManagerServiceImpl overlayManagerServiceImpl = OverlayManagerService.this.mImpl;
                    OverlayIdentifier overlayIdentifier = request.overlay;
                    overlayManagerServiceImpl.getClass();
                    try {
                        overlayInfo = overlayManagerServiceImpl.mSettings.getOverlayInfo(overlayIdentifier, i);
                    } catch (OverlayManagerSettings.BadKeyException unused) {
                        overlayInfo = null;
                    }
                    if (overlayInfo == null) {
                        ThemeUtil.saveSWTLog("SWT_OverlayManager", "OverlayInfo is not founded, skip request " + request);
                        return Set.of();
                    }
                }
                enforceActor(i, request.overlay, request.typeToString());
            } else {
                if (request.userId != -1) {
                    throw new IllegalArgumentException(request.typeToString() + " unsupported for user " + request.userId);
                }
                if (callingUid == 2000) {
                    EventLog.writeEvent(1397638484, "202768292", -1, "");
                    throw new IllegalArgumentException("Non-root shell cannot fabricate overlays");
                }
                String packageName = request.overlay.getPackageName();
                if (callingUid != 0) {
                    PackageManagerHelperImpl packageManagerHelperImpl = OverlayManagerService.this.mPackageManager;
                    packageManagerHelperImpl.getClass();
                    try {
                        strArr = packageManagerHelperImpl.mPackageManager.getPackagesForUid(callingUid);
                    } catch (RemoteException unused2) {
                        strArr = null;
                    }
                    if (!ArrayUtils.contains(strArr, packageName)) {
                        throw new IllegalArgumentException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "UID ", " does own packagename ", packageName));
                    }
                }
                i = -1;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int i3 = request.type;
                if (i3 == 0) {
                    return CollectionUtils.emptyIfNull(CollectionUtils.addAll(CollectionUtils.addAll((Set) null, OverlayManagerService.this.mImpl.setEnabled(i, request.overlay, true)), OverlayManagerService.this.mImpl.setHighestPriority(request.overlay, i)));
                }
                if (i3 == 1) {
                    return OverlayManagerService.this.mImpl.setEnabled(i, request.overlay, false);
                }
                if (i3 == 2) {
                    FabricatedOverlayInternal fabricatedOverlayInternal = (FabricatedOverlayInternal) request.extras.getParcelable("fabricated_overlay", FabricatedOverlayInternal.class);
                    Objects.requireNonNull(fabricatedOverlayInternal, "no fabricated overlay attached to request");
                    return OverlayManagerService.this.mImpl.registerFabricatedOverlay(fabricatedOverlayInternal);
                }
                if (i3 == 3) {
                    return OverlayManagerService.this.mImpl.unregisterFabricatedOverlay(request.overlay);
                }
                throw new IllegalArgumentException("unsupported request: " + request);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Map getAllOverlays(int i) {
            ArrayMap overlaysForUser;
            try {
                Trace.traceBegin(67108864L, "OMS#getAllOverlays " + i);
                int handleIncomingUser = handleIncomingUser(i, "getAllOverlays");
                synchronized (OverlayManagerService.this.mLock) {
                    overlaysForUser = OverlayManagerService.this.mImpl.mSettings.getOverlaysForUser(handleIncomingUser);
                }
                return overlaysForUser;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final OverlayInfoExt[] getAllOverlaysInCategory(int i, int i2) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                try {
                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                    if (overlayManagerServiceExt == null) {
                        return new OverlayInfoExt[0];
                    }
                    return overlayManagerServiceExt.getAllOverlaysInCategory(i, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String[] getDefaultOverlayPackages() {
            String[] strArr;
            try {
                Trace.traceBegin(67108864L, "OMS#getDefaultOverlayPackages");
                OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.MODIFY_THEME_OVERLAY", null);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        strArr = OverlayManagerService.this.mImpl.mDefaultOverlays;
                    }
                    return strArr;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final boolean getLastPalette(List list, List list2) {
            OverlayManagerService.this.mWallpaperThemeManager.mWallpaperThemeManager.getClass();
            List readLastPalette = SemWallpaperThemeManager.readLastPalette();
            if (((ArrayList) readLastPalette).isEmpty()) {
                return false;
            }
            return SemWallpaperThemeManager.splitPalette(readLastPalette, list, list2);
        }

        public final OverlayInfoExt getOverlayForPath(String str, int i) {
            OverlayInfoExt overlayInfoExt = null;
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                try {
                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                    if (overlayManagerServiceExt == null) {
                        return null;
                    }
                    Iterator it = overlayManagerServiceExt.mSettings.getOverlaysForUser(i).values().iterator();
                    loop0: while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        for (OverlayInfo overlayInfo : (List) it.next()) {
                            if (overlayInfo.baseCodePath.equals(str)) {
                                overlayInfoExt = OverlayInfoExt.initFromInfo(overlayInfo);
                                break loop0;
                            }
                        }
                    }
                    return overlayInfoExt;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final OverlayInfo getOverlayInfo(String str, int i) {
            return getOverlayInfoByIdentifier(new OverlayIdentifier(str), i);
        }

        public final OverlayInfo getOverlayInfoByIdentifier(OverlayIdentifier overlayIdentifier, int i) {
            OverlayInfo overlayInfo = null;
            if (overlayIdentifier == null || overlayIdentifier.getPackageName() == null) {
                return null;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#getOverlayInfo " + overlayIdentifier);
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfo");
                synchronized (OverlayManagerService.this.mLock) {
                    OverlayManagerServiceImpl overlayManagerServiceImpl = OverlayManagerService.this.mImpl;
                    overlayManagerServiceImpl.getClass();
                    try {
                        overlayInfo = overlayManagerServiceImpl.mSettings.getOverlayInfo(overlayIdentifier, handleIncomingUser);
                    } catch (OverlayManagerSettings.BadKeyException unused) {
                    }
                }
                return overlayInfo;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final List getOverlayInfosForTarget(String str, int i) {
            List overlaysForTarget;
            if (str == null) {
                return Collections.emptyList();
            }
            try {
                Trace.traceBegin(67108864L, "OMS#getOverlayInfosForTarget ".concat(str));
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfosForTarget");
                synchronized (OverlayManagerService.this.mLock) {
                    overlaysForTarget = OverlayManagerService.this.mImpl.mSettings.getOverlaysForTarget(handleIncomingUser, str);
                }
                return overlaysForTarget;
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final OverlayInfoExt[] getOverlaysForTarget(String str, int i, int i2) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                try {
                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                    if (overlayManagerServiceExt == null) {
                        return new OverlayInfoExt[0];
                    }
                    List<OverlayInfo> overlaysForTarget = overlayManagerServiceExt.mSettings.getOverlaysForTarget(i2, str);
                    ArrayList arrayList = new ArrayList();
                    for (OverlayInfo overlayInfo : overlaysForTarget) {
                        if (OverlayInfoExt.isOverlayInfoExtOfCategory(overlayInfo, i)) {
                            arrayList.add(OverlayInfoExt.initFromInfo(overlayInfo));
                        }
                    }
                    return (OverlayInfoExt[]) arrayList.toArray(new OverlayInfoExt[0]);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String getPartitionOrder() {
            return OverlayManagerService.this.mImpl.mOverlayConfig.getPartitionOrder();
        }

        public final List getThemeParkOverlayNames(String str) {
            return (List) OverlayManagerService.this.mSettings.getOverlaysForUser(handleIncomingUser(0, "getAllOverlays")).values().stream().flatMap(new OverlayManagerService$1$$ExternalSyntheticLambda1(0)).map(new OverlayManagerService$1$$ExternalSyntheticLambda1(1)).filter(new OverlayManagerService$$ExternalSyntheticLambda4(str, 1)).collect(Collectors.toList());
        }

        public final List getWallpaperColors() {
            return OverlayManagerService.this.mWallpaperThemeManager.mWallpaperThemeManager.mPalette.getPaletteSS();
        }

        public final void invalidateCachesForOverlay(String str, int i) {
            if (str == null) {
                return;
            }
            OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
            int handleIncomingUser = handleIncomingUser(i, "invalidateCachesForOverlay");
            enforceActor(handleIncomingUser, overlayIdentifier, "invalidateCachesForOverlay");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (OverlayManagerService.this.mLock) {
                    try {
                        OverlayManagerService.this.mImpl.removeIdmapForOverlay(overlayIdentifier, handleIncomingUser);
                    } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                        Slog.w("OverlayManager", "invalidate caches for overlay '" + overlayIdentifier + "' failed", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDefaultPartitionOrder() {
            return OverlayManagerService.this.mImpl.mOverlayConfig.isDefaultPartitionOrder();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new OverlayManagerShellCommand(OverlayManagerService.this.getContext(), this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final List readLastPalette() {
            OverlayManagerService.this.mWallpaperThemeManager.mWallpaperThemeManager.getClass();
            ArrayList arrayList = (ArrayList) SemWallpaperThemeManager.readLastPalette();
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.subList(0, 65);
        }

        public final void removeOverlays(List list, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                if (overlayManagerServiceExt != null) {
                    overlayManagerServiceExt.notifySystemServices(i, overlayManagerServiceExt.removeOverlaysInternal(list, iSamsungOverlayCallback, i), list);
                }
            }
        }

        public final void replaceOverlays(List list, List list2, ISamsungOverlayCallback iSamsungOverlayCallback, int i) {
            OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.MODIFY_THEME", null);
            synchronized (OverlayManagerService.this.mLock) {
                try {
                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                    if (overlayManagerServiceExt != null) {
                        overlayManagerServiceExt.replaceOverlays(list, list2, iSamsungOverlayCallback, i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean setEnabled(String str, boolean z, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabled " + str + " " + z);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabled");
                enforceActor(handleIncomingUser, overlayIdentifier, "setEnabled");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService overlayManagerService = OverlayManagerService.this;
                            overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.setEnabled(handleIncomingUser, overlayIdentifier, z), false);
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final boolean setEnabledExclusive(String str, boolean z, int i) {
            if (str == null || !z) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabledExclusive " + str + " " + z);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusive");
                enforceActor(handleIncomingUser, overlayIdentifier, "setEnabledExclusive");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setEnabledExclusive(handleIncomingUser, overlayIdentifier, false).ifPresent(new OverlayManagerService$1$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public final boolean setEnabledExclusiveInCategory(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setEnabledExclusiveInCategory ".concat(str));
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusiveInCategory");
                enforceActor(handleIncomingUser, overlayIdentifier, "setEnabledExclusiveInCategory");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setEnabledExclusive(handleIncomingUser, overlayIdentifier, true).ifPresent(new OverlayManagerService$1$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public final boolean setHighestPriority(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setHighestPriority ".concat(str));
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setHighestPriority");
                enforceActor(handleIncomingUser, overlayIdentifier, "setHighestPriority");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService overlayManagerService = OverlayManagerService.this;
                            overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.setHighestPriority(overlayIdentifier, handleIncomingUser), false);
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        public final boolean setLowestPriority(String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setLowestPriority ".concat(str));
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setLowestPriority");
                enforceActor(handleIncomingUser, overlayIdentifier, "setLowestPriority");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setLowestPriority(overlayIdentifier, handleIncomingUser).ifPresent(new OverlayManagerService$1$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public final boolean setPriority(String str, String str2, int i) {
            if (str == null || str2 == null) {
                return false;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#setPriority " + str + " " + str2);
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str);
                OverlayIdentifier overlayIdentifier2 = new OverlayIdentifier(str2);
                int handleIncomingUser = handleIncomingUser(i, "setPriority");
                enforceActor(handleIncomingUser, overlayIdentifier, "setPriority");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (OverlayManagerService.this.mLock) {
                        try {
                            OverlayManagerService.this.mImpl.setPriority(overlayIdentifier, overlayIdentifier2, handleIncomingUser).ifPresent(new OverlayManagerService$1$$ExternalSyntheticLambda0(OverlayManagerService.this));
                        } catch (OverlayManagerServiceImpl.OperationFailedException unused) {
                            Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Trace.traceEnd(67108864L);
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverlayManagerPackageMonitor extends PackageMonitor {
        public OverlayManagerPackageMonitor() {
        }

        public final void onPackageAppearedWithExtras(String str, Bundle bundle) {
            OverlayManagerService overlayManagerService = OverlayManagerService.this;
            int changingUserId = getChangingUserId();
            overlayManagerService.getClass();
            if (bundle.getBoolean("android.intent.extra.REPLACING", false)) {
                try {
                    Trace.traceBegin(67108864L, "OMS#onPackageReplaced " + str);
                    synchronized (overlayManagerService.mLock) {
                        if (overlayManagerService.mPackageManager.addPackageUser(changingUserId, str) != null && !overlayManagerService.mPackageManager.mPackageManagerInternal.isInstantApp(str, changingUserId)) {
                            try {
                                overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.reconcileSettingsForPackage(changingUserId, 0, str), false);
                            } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                                Slog.e("OverlayManager", "onPackageReplaced internal error", e);
                            }
                        }
                    }
                } finally {
                }
            } else {
                try {
                    Trace.traceBegin(67108864L, "OMS#onPackageAdded " + str);
                    synchronized (overlayManagerService.mLock) {
                        if (overlayManagerService.mPackageManager.addPackageUser(changingUserId, str) != null && !overlayManagerService.mPackageManager.mPackageManagerInternal.isInstantApp(str, changingUserId)) {
                            try {
                                OverlayManagerServiceImpl overlayManagerServiceImpl = overlayManagerService.mImpl;
                                overlayManagerServiceImpl.getClass();
                                ArraySet arraySet = new ArraySet();
                                arraySet.add(UserPackage.of(changingUserId, str));
                                arraySet.addAll(overlayManagerServiceImpl.reconcileSettingsForPackage(changingUserId, 0, str));
                                overlayManagerService.updateTargetPackagesLocked(arraySet, false);
                            } catch (OverlayManagerServiceImpl.OperationFailedException e2) {
                                Slog.e("OverlayManager", "onPackageAdded internal error", e2);
                            }
                        }
                    }
                } finally {
                }
            }
            SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper = overlayManagerService.mWallpaperThemeManager;
            SemWallpaperThemeManager semWallpaperThemeManager = semWallpaperThemeManagerWrapper.mWallpaperThemeManager;
            int colorThemeState = semWallpaperThemeManagerWrapper.getColorThemeState();
            if (colorThemeState != -1) {
                try {
                    semWallpaperThemeManagerWrapper.initTemplateMetadataIfNeeded();
                    semWallpaperThemeManager.updateTemplateMetadataFromPkg(SemWallpaperThemeManagerWrapper.mContext.getPackageManager().getPackageInfo(str, PackageManager.PackageInfoFlags.of(8832L)));
                    OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
                    semWallpaperThemeManager.updateThemeOverlay(builder, str, colorThemeState);
                    IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder.build());
                } catch (Exception e3) {
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(e3, "FAILED at commit for packageAdded, e=", "SWT_WTM_Wrapper");
                }
            }
        }

        public final void onPackageChangedWithExtras(String str, Bundle bundle) {
            OverlayManagerService overlayManagerService = OverlayManagerService.this;
            int changingUserId = getChangingUserId();
            overlayManagerService.getClass();
            if ("android.intent.action.OVERLAY_CHANGED".equals(bundle.getString("android.intent.extra.REASON"))) {
                return;
            }
            try {
                Trace.traceBegin(67108864L, "OMS#onPackageChanged " + str);
                synchronized (overlayManagerService.mLock) {
                    if (overlayManagerService.mPackageManager.addPackageUser(changingUserId, str) != null && !overlayManagerService.mPackageManager.mPackageManagerInternal.isInstantApp(str, changingUserId)) {
                        try {
                            overlayManagerService.updateTargetPackagesLocked(overlayManagerService.mImpl.reconcileSettingsForPackage(changingUserId, 8, str), false);
                        } catch (OverlayManagerServiceImpl.OperationFailedException e) {
                            Slog.e("OverlayManager", "onPackageChanged internal error", e);
                        }
                    }
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x008d, code lost:
        
            r1 = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            r2 = new android.util.ArrayMap(1);
            r2.put(r9, r0);
            r1.setEnabledOverlayPackages(r8, r2, new java.util.HashSet(), new java.util.HashSet());
            android.util.Slog.d("OverlayManager", "OM_BUG_FIX_LOST_OVERLAY_WHEN_UPDATE_UNINSTALL : " + r9);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPackageDisappearedWithExtras(java.lang.String r9, android.os.Bundle r10) {
            /*
                Method dump skipped, instructions count: 376
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.OverlayManagerService.OverlayManagerPackageMonitor.onPackageDisappearedWithExtras(java.lang.String, android.os.Bundle):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageManagerHelperImpl {
        public final Context mContext;
        public final ArrayMap mCache = new ArrayMap();
        public final ArraySet mInitializedUsers = new ArraySet();
        public final IPackageManager mPackageManager = AppGlobals.getPackageManager();
        public final PackageManagerInternal mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class PackageStateUsers {
            public final Set mInstalledUsers = new ArraySet();
            public PackageState mPackageState;

            public PackageStateUsers(PackageState packageState) {
                this.mPackageState = packageState;
            }
        }

        public PackageManagerHelperImpl(Context context) {
            this.mContext = context;
        }

        public final PackageState addPackageUser(int i, String str) {
            PackageStateInternal packageStateInternal = this.mPackageManagerInternal.getPackageStateInternal(str);
            if (packageStateInternal != null) {
                return addPackageUser(packageStateInternal, i);
            }
            Slog.w("OverlayManager", XmlUtils$$ExternalSyntheticOutline0.m("Android package for '", str, "' could not be found; continuing as if package was never added"), new Throwable());
            return null;
        }

        public final PackageState addPackageUser(PackageState packageState, int i) {
            PackageState packageState2;
            synchronized (this.mCache) {
                try {
                    PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.get(packageState.getPackageName());
                    if (packageStateUsers == null) {
                        packageStateUsers = new PackageStateUsers(packageState);
                        this.mCache.put(packageState.getPackageName(), packageStateUsers);
                    } else {
                        packageStateUsers.mPackageState = packageState;
                    }
                    ((ArraySet) packageStateUsers.mInstalledUsers).add(Integer.valueOf(i));
                    packageState2 = packageStateUsers.mPackageState;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return packageState2;
        }

        public final boolean doesTargetDefineOverlayable(int i, String str) {
            PackageState packageStateForUser = getPackageStateForUser(i, str);
            ApkAssets apkAssets = null;
            AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new IOException("Unable to get target package");
            }
            try {
                apkAssets = ApkAssets.loadFromPath(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath(), 32);
                boolean definesOverlayable = apkAssets.definesOverlayable();
                try {
                    apkAssets.close();
                } catch (Throwable unused) {
                }
                return definesOverlayable;
            } catch (Throwable th) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused2) {
                    }
                }
                throw th;
            }
        }

        public final void dump(PrintWriter printWriter, DumpState dumpState) {
            synchronized (this.mCache) {
                try {
                    printWriter.println("AndroidPackage cache");
                    if (!dumpState.mVerbose) {
                        printWriter.println("    " + this.mCache.size() + " package(s)");
                        return;
                    }
                    if (this.mCache.size() == 0) {
                        printWriter.println("    <empty>");
                        return;
                    }
                    int size = this.mCache.size();
                    for (int i = 0; i < size; i++) {
                        String str = (String) this.mCache.keyAt(i);
                        PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.valueAt(i);
                        printWriter.print("    " + str + ": " + packageStateUsers.mPackageState + " users=");
                        printWriter.println(TextUtils.join(", ", packageStateUsers.mInstalledUsers));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final OverlayableInfo getOverlayableForTarget(int i, String str, String str2) {
            PackageState packageStateForUser = getPackageStateForUser(i, str);
            ApkAssets apkAssets = null;
            AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new IOException("Unable to get target package");
            }
            try {
                apkAssets = ApkAssets.loadFromPath(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath(), 32);
                OverlayableInfo overlayableInfo = apkAssets.getOverlayableInfo(str2);
                try {
                    apkAssets.close();
                } catch (Throwable unused) {
                }
                return overlayableInfo;
            } catch (Throwable th) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (Throwable unused2) {
                    }
                }
                throw th;
            }
        }

        public final PackageState getPackageStateForUser(int i, String str) {
            synchronized (this.mCache) {
                PackageStateUsers packageStateUsers = (PackageStateUsers) this.mCache.get(str);
                if (packageStateUsers != null) {
                    if (((ArraySet) packageStateUsers.mInstalledUsers).contains(Integer.valueOf(i))) {
                        return packageStateUsers.mPackageState;
                    }
                }
                try {
                    if (!this.mPackageManager.isPackageAvailable(str, i)) {
                        return null;
                    }
                    return addPackageUser(i, str);
                } catch (RemoteException e) {
                    Slog.w("OverlayManager", "Failed to check availability of package '" + str + "' for user " + i, e);
                    return null;
                }
            }
        }

        public final void removePackageUser(PackageStateUsers packageStateUsers, int i) {
            synchronized (this.mCache) {
                try {
                    ((ArraySet) packageStateUsers.mInstalledUsers).remove(Integer.valueOf(i));
                    if (((ArraySet) packageStateUsers.mInstalledUsers).isEmpty()) {
                        this.mCache.remove(packageStateUsers.mPackageState.getPackageName());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageUpdateHelper {
        public PackageUpdateHelper() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserLifecycleListener implements UserManagerInternal.UserLifecycleListener {
        public UserLifecycleListener() {
        }

        @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
        public final void onUserCreated(UserInfo userInfo, Object obj) {
            if (userInfo == null || !userInfo.isMain()) {
                return;
            }
            int i = userInfo.id;
            try {
                Slog.i("OverlayManager", "Updating overlays for onUserCreated " + i);
                Trace.traceBegin(67108864L, "OMS#onUserCreated " + i);
                synchronized (OverlayManagerService.this.mLock) {
                    OverlayManagerService overlayManagerService = OverlayManagerService.this;
                    overlayManagerService.updatePackageManagerLocked(overlayManagerService.mImpl.updateOverlaysForUser(i));
                }
            } finally {
                Trace.traceEnd(67108864L);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserReceiver extends BroadcastReceiver {
        public UserReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            String action = intent.getAction();
            action.getClass();
            if (action.equals("android.intent.action.USER_REMOVED")) {
                if (intExtra != -10000) {
                    try {
                        Trace.traceBegin(67108864L, "OMS ACTION_USER_REMOVED");
                        synchronized (OverlayManagerService.this.mLock) {
                            OverlayManagerServiceImpl overlayManagerServiceImpl = OverlayManagerService.this.mImpl;
                            overlayManagerServiceImpl.getClass();
                            Slog.d("OverlayManager", "onUserRemoved userId=" + intExtra);
                            overlayManagerServiceImpl.mSettings.removeUser(intExtra);
                            PackageManagerHelperImpl packageManagerHelperImpl = OverlayManagerService.this.mPackageManager;
                            synchronized (packageManagerHelperImpl.mCache) {
                                try {
                                    for (int size = packageManagerHelperImpl.mCache.size() - 1; size >= 0; size--) {
                                        packageManagerHelperImpl.removePackageUser((PackageManagerHelperImpl.PackageStateUsers) packageManagerHelperImpl.mCache.valueAt(size), intExtra);
                                    }
                                } finally {
                                }
                            }
                        }
                        return;
                    } finally {
                    }
                }
                return;
            }
            if (action.equals("android.intent.action.USER_ADDED")) {
                UserInfo userInfo = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(intExtra);
                if (intExtra != -10000) {
                    if (userInfo == null || !userInfo.isMain()) {
                        try {
                            Slog.i("OverlayManager", "Updating overlays for added user " + intExtra);
                            Trace.traceBegin(67108864L, "OMS ACTION_USER_ADDED");
                            synchronized (OverlayManagerService.this.mLock) {
                                try {
                                    Slog.e("OMS_DEBUG", "[ACTION_USER_ADDED] userId : " + intExtra);
                                    OverlayManagerService overlayManagerService = OverlayManagerService.this;
                                    overlayManagerService.updatePackageManagerLocked(overlayManagerService.mImpl.updateOverlaysForUser(intExtra));
                                    OverlayManagerServiceExt overlayManagerServiceExt = OverlayManagerService.this.mOverlayManagerServiceExt;
                                    if (overlayManagerServiceExt != null) {
                                        overlayManagerServiceExt.handleUserSwitch(intExtra);
                                    }
                                    OverlayManagerService.this.mWallpaperThemeManager.mWallpaperThemeManager.syncWallpaperThemeStateForUser(intExtra);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$munregisterColorThemeGG, reason: not valid java name */
    public static void m738$$Nest$munregisterColorThemeGG(OverlayManagerService overlayManagerService, boolean z) {
        synchronized (overlayManagerService.mLock) {
            try {
                Iterator it = ((ArraySet) overlayManagerService.mSettings.getAllIdentifiersAndBaseCodePaths()).iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    String overlayName = ((OverlayIdentifier) pair.first).getOverlayName();
                    if (Constants.SYSTEMUI_PACKAGE_NAME.equals(((OverlayIdentifier) pair.first).getPackageName())) {
                        if (!overlayName.contains("neutral")) {
                            if (!overlayName.contains("accent")) {
                                if (!z && overlayName.contains("dynamic")) {
                                }
                            }
                        }
                        overlayManagerService.mImpl.unregisterFabricatedOverlay((OverlayIdentifier) pair.first);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.content.om.IOverlayManager$Stub, android.os.IBinder, com.android.server.om.OverlayManagerService$1] */
    public OverlayManagerService(Context context) {
        super(context);
        this.mLock = new Object();
        OverlayManagerPackageMonitor overlayManagerPackageMonitor = new OverlayManagerPackageMonitor();
        this.mPrevStartedUserId = -1;
        this.mOverlayManagerServiceExt = null;
        ?? anonymousClass1 = new AnonymousClass1();
        this.mService = anonymousClass1;
        try {
            Trace.traceBegin(67108864L, "OMS#OverlayManagerService");
            this.mSettingsFile = new ResilientAtomicFile(new File("/data/system/", "overlays.xml"), new File("/data/system/", "overlays-backup.xml"), new File("/data/system/", "overlays.xml.reservecopy"), this);
            PackageManagerHelperImpl packageManagerHelperImpl = new PackageManagerHelperImpl(context);
            this.mPackageManager = packageManagerHelperImpl;
            this.mUserManager = UserManagerService.getInstance();
            if (IdmapDaemon.sInstance == null) {
                IdmapDaemon.sInstance = new IdmapDaemon();
            }
            IdmapManager idmapManager = new IdmapManager(IdmapDaemon.sInstance, packageManagerHelperImpl);
            OverlayManagerSettings overlayManagerSettings = new OverlayManagerSettings();
            this.mSettings = overlayManagerSettings;
            OverlayManagerServiceImpl overlayManagerServiceImpl = new OverlayManagerServiceImpl(packageManagerHelperImpl, idmapManager, overlayManagerSettings, OverlayConfig.getSystemInstance(), getDefaultOverlayPackages());
            this.mImpl = overlayManagerServiceImpl;
            this.mActorEnforcer = new OverlayActorEnforcer(packageManagerHelperImpl);
            HandlerThread handlerThread = new HandlerThread("OverlayManager");
            handlerThread.start();
            Looper looper = handlerThread.getLooper();
            UserHandle userHandle = UserHandle.ALL;
            overlayManagerPackageMonitor.register(context, looper, userHandle, true);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_ADDED");
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            getContext().registerReceiverAsUser(new UserReceiver(), userHandle, intentFilter, null, null);
            ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).addUserLifecycleListener(new UserLifecycleListener());
            restoreSettings();
            OverlayManagerServiceExt overlayManagerServiceExt = new OverlayManagerServiceExt(context, packageManagerHelperImpl, overlayManagerSettings, idmapManager, new PackageUpdateHelper());
            this.mOverlayManagerServiceExt = overlayManagerServiceExt;
            overlayManagerServiceImpl.mOverlayManagerExt = overlayManagerServiceExt;
            SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper = SemWallpaperThemeManagerWrapper.getInstance(context, new OverlayManagerSettingsHelper(overlayManagerSettings));
            this.mWallpaperThemeManager = semWallpaperThemeManagerWrapper;
            OverlayManagerTransaction initWallpaperTheme = semWallpaperThemeManagerWrapper.initWallpaperTheme();
            overlayManagerSettings.removeIf(new OverlayManagerService$$ExternalSyntheticLambda4(TextUtils.emptyIfNull(getContext().getString(R.string.config_systemShell)), 0));
            initIfNeeded();
            onStartUser(0, true);
            publishBinderService("overlay", anonymousClass1);
            publishLocalService(OverlayManagerService.class, this);
            if (semWallpaperThemeManagerWrapper.getColorThemeState() != 1) {
                unregisterUnusedPaletteOverlays();
            }
            if (initWallpaperTheme != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                try {
                    anonymousClass1.commit(initWallpaperTheme);
                } catch (Exception e) {
                    Slog.e("SWT_OverlayManager", "failed initWallpaperTheme, wallpaper theming will not working, ex = " + e);
                }
                Slog.d("SWT_OverlayManager", "Committed restored color theme : " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
            }
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    @SystemApi
    public static void broadcastActionOverlayChangedPublic(Set set, int i) {
        CollectionUtils.forEach(set, new OverlayManagerService$$ExternalSyntheticLambda1(i, (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)));
    }

    public static String[] getDefaultOverlayPackages() {
        String str = SystemProperties.get("ro.boot.vendor.overlay.theme");
        if (TextUtils.isEmpty(str)) {
            return EmptyArray.STRING;
        }
        ArraySet arraySet = new ArraySet();
        for (String str2 : str.split(";")) {
            if (!TextUtils.isEmpty(str2)) {
                arraySet.add(str2);
            }
        }
        return (String[]) arraySet.toArray(new String[0]);
    }

    @SystemApi
    public Map getAllOverlays(int i) {
        try {
            return this.mService.getAllOverlays(i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @SystemApi
    public List getOverlayInfosForTarget(String str, int i) {
        try {
            return this.mService.getOverlayInfosForTarget(str, i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @SystemApi
    public String getTargetPath(String str) {
        synchronized (this.mLock) {
            try {
                OverlayManagerServiceExt overlayManagerServiceExt = this.mOverlayManagerServiceExt;
                if (overlayManagerServiceExt == null) {
                    return null;
                }
                return overlayManagerServiceExt.getTargetPath(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void initIfNeeded() {
        List aliveUsers = ((UserManager) getContext().getSystemService(UserManager.class)).getAliveUsers();
        synchronized (this.mLock) {
            try {
                int size = aliveUsers.size();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = (UserInfo) aliveUsers.get(i);
                    if (!userInfo.supportsSwitchTo() && userInfo.id != 0) {
                        updatePackageManagerLocked(this.mImpl.updateOverlaysForUser(((UserInfo) aliveUsers.get(i)).id));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
    }

    public final void onStartUser(int i, boolean z) {
        if (i == this.mPrevStartedUserId) {
            return;
        }
        HermesService$3$$ExternalSyntheticOutline0.m(i, "Updating overlays for starting user ", "OverlayManager");
        try {
            Trace.traceBegin(67108864L, "OMS#onStartUser " + i);
            this.mImpl.getClass();
            OverlayManagerServiceImpl.createLocaleOverlayPreferenceDir(i);
            if (!z) {
                try {
                    LocaleOverlayManagerWrapper.getInstance(getContext()).checkSanityOfOverlays(i);
                } catch (Exception e) {
                    Slog.e("OverlayManager", "onStartUser - Error in starting localeoverlaymanager: " + e);
                }
            }
            synchronized (this.mLock) {
                Slog.e("OMS_DEBUG", "[onStartUser] newUserId : " + i);
                OverlayManagerServiceExt overlayManagerServiceExt = this.mOverlayManagerServiceExt;
                if (overlayManagerServiceExt != null) {
                    overlayManagerServiceExt.mIsInitOnBoot = z;
                }
                updateTargetPackagesLocked(this.mImpl.updateOverlaysForUser(i), false);
                OverlayManagerServiceExt overlayManagerServiceExt2 = this.mOverlayManagerServiceExt;
                if (overlayManagerServiceExt2 != null) {
                    overlayManagerServiceExt2.mIsInitOnBoot = false;
                }
            }
            Trace.traceEnd(67108864L);
            this.mPrevStartedUserId = i;
        } catch (Throwable th) {
            Trace.traceEnd(67108864L);
            throw th;
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        onStartUser(targetUser.getUserIdentifier(), false);
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        UiModeManagerService.LocalService localService = (UiModeManagerService.LocalService) LocalServices.getService(UiModeManagerService.LocalService.class);
        if (localService != null) {
            int userIdentifier = targetUser2.getUserIdentifier();
            UiModeManagerService.this.getContext().getContentResolver().unregisterContentObserver(UiModeManagerService.this.mSetupWizardObserver);
            UiModeManagerService.this.verifySetupWizardCompleted();
            synchronized (UiModeManagerService.this.mLock) {
                try {
                    UiModeManagerService uiModeManagerService = UiModeManagerService.this;
                    uiModeManagerService.updateNightModeFromSettingsLocked(uiModeManagerService.getContext(), UiModeManagerService.this.getContext().getResources(), userIdentifier);
                    UiModeManagerService.this.resetNightModeOverrideLocked();
                    Set set = UiModeManagerService.SUPPORTED_NIGHT_MODE_CUSTOM_TYPES;
                    UiModeManagerService.LogWrapper.i("onEarlySwitchUser : " + UiModeManagerService.this.mNightMode + " userID : " + userIdentifier);
                    UiModeManagerService.this.updateLocked(0, 0);
                    UiModeManagerService uiModeManagerService2 = UiModeManagerService.this;
                    uiModeManagerService2.mAlarmManager.cancel(uiModeManagerService2.mCustomTimeListener);
                    UiModeManagerService uiModeManagerService3 = UiModeManagerService.this;
                    if (uiModeManagerService3.mNightMode.mNightModeValue == 3) {
                        if (!uiModeManagerService3.mIsNightModeRegistered) {
                            IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
                            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                            uiModeManagerService3.getContext().registerReceiver(uiModeManagerService3.mOnTimeChangedHandler, intentFilter);
                            UiModeManagerService.this.mIsNightModeRegistered = true;
                        }
                    } else if (uiModeManagerService3.mIsNightModeRegistered) {
                        try {
                            uiModeManagerService3.getContext().unregisterReceiver(uiModeManagerService3.mOnTimeChangedHandler);
                        } catch (IllegalArgumentException unused) {
                        }
                        UiModeManagerService.this.mIsNightModeRegistered = false;
                    }
                } finally {
                }
            }
        }
    }

    public final void persistSettingsLocked() {
        Slog.d("OverlayManager", "Writing overlay settings");
        try {
            FileOutputStream startWrite = this.mSettingsFile.startWrite();
            OverlayManagerSettings overlayManagerSettings = this.mSettings;
            synchronized (overlayManagerSettings.mItemsLock) {
                OverlayManagerSettings.Serializer.persist(overlayManagerSettings.mItems, startWrite);
            }
            this.mSettingsFile.finishWrite(startWrite);
        } catch (IOException | XmlPullParserException e) {
            ResilientAtomicFile resilientAtomicFile = this.mSettingsFile;
            if (resilientAtomicFile.mMainOutStream != null) {
                throw new IllegalStateException("Invalid incoming stream.");
            }
            resilientAtomicFile.close();
            if (resilientAtomicFile.mFile.exists() && !resilientAtomicFile.mFile.delete()) {
                Slog.i("ResilientAtomicFile", "Failed to clean up mangled file: " + resilientAtomicFile.mFile);
            }
            Slog.e("OverlayManager", "failed to persist overlay state", e);
        }
    }

    public final void restoreSettings() {
        FileInputStream openRead;
        try {
            Trace.traceBegin(67108864L, "OMS#restoreSettings");
            synchronized (this.mLock) {
                ResilientAtomicFile resilientAtomicFile = this.mSettingsFile;
                if (!resilientAtomicFile.mFile.exists() && !resilientAtomicFile.mTemporaryBackup.exists() && !resilientAtomicFile.mReserveCopy.exists()) {
                    return;
                }
                try {
                    openRead = this.mSettingsFile.openRead();
                } catch (Exception e) {
                    Slog.e("OverlayManager", "failed to restore overlay state", e);
                    ResilientAtomicFile resilientAtomicFile2 = this.mSettingsFile;
                    if (resilientAtomicFile2.mFile.exists() || resilientAtomicFile2.mTemporaryBackup.exists() || resilientAtomicFile2.mReserveCopy.exists()) {
                        this.mSettingsFile.failRead(null, e);
                    }
                    restoreSettings();
                }
                if (openRead == null) {
                    Slog.e("OverlayManager", "settings file recovery failure");
                    return;
                }
                OverlayManagerSettings overlayManagerSettings = this.mSettings;
                synchronized (overlayManagerSettings.mItemsLock) {
                    OverlayManagerSettings.Serializer.restore(overlayManagerSettings.mItems, openRead);
                }
                ArrayList arrayList = (ArrayList) this.mUserManager.getUsers(true, true, true);
                int[] iArr = new int[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    iArr[i] = ((UserInfo) arrayList.get(i)).getUserHandle().getIdentifier();
                }
                Arrays.sort(iArr);
                for (int i2 : this.mSettings.getUsers()) {
                    if (Arrays.binarySearch(iArr, i2) < 0) {
                        this.mSettings.removeUser(i2);
                    }
                }
            }
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public final void unregisterUnusedPaletteOverlays() {
        synchronized (this.mLock) {
            try {
                Iterator it = ((ArraySet) this.mSettings.getAllIdentifiersAndBaseCodePaths()).iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    String overlayName = ((OverlayIdentifier) pair.first).getOverlayName();
                    if (overlayName != null && overlayName.startsWith("SemWT_")) {
                        this.mImpl.unregisterFabricatedOverlay((OverlayIdentifier) pair.first);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @SystemApi
    public void updatePackageCache(String str, int i) {
        Slog.i("OverlayManager", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "updatePackageCache() called with: packageName = [", str, "], userId = [", "]"));
        if (str != null) {
            synchronized (this.mLock) {
                this.mPackageManager.addPackageUser(i, str);
            }
        }
    }

    public final SparseArray updatePackageManagerLocked(Set set) {
        if (CollectionUtils.isEmpty(set)) {
            return new SparseArray();
        }
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        CollectionUtils.forEach(set, new OverlayManagerService$$ExternalSyntheticLambda2(sparseArray2));
        int size = sparseArray2.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray2.keyAt(i);
            sparseArray.put(keyAt, updatePackageManagerLocked(keyAt, (Collection) sparseArray2.valueAt(i), false));
        }
        return sparseArray;
    }

    public final List updatePackageManagerLocked(int i, Collection collection, boolean z) {
        OverlayPaths overlayPaths;
        try {
            Trace.traceBegin(67108864L, "OMS#updatePackageManagerLocked " + collection);
            Slog.d("OverlayManager", "Update package manager about changed overlays");
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            if (collection.contains("android") && !z) {
                PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
                packageManagerInternalImpl.getClass();
                ArrayList arrayList = new ArrayList();
                Computer snapshotComputer = packageManagerInternalImpl.mService.snapshotComputer();
                PackageManagerService$$ExternalSyntheticLambda40 packageManagerService$$ExternalSyntheticLambda40 = new PackageManagerService$$ExternalSyntheticLambda40(1, arrayList);
                PackageManagerService.this.getClass();
                PackageManagerService.forEachPackageState(snapshotComputer, packageManagerService$$ExternalSyntheticLambda40);
                collection = arrayList;
            }
            ArrayMap arrayMap = new ArrayMap(collection.size());
            synchronized (this.mLock) {
                try {
                    OverlayPaths enabledOverlayPaths = this.mImpl.getEnabledOverlayPaths(i, "android", false);
                    for (String str : collection) {
                        OverlayPaths.Builder builder = new OverlayPaths.Builder();
                        if ("android".equals(str)) {
                            overlayPaths = null;
                        } else {
                            OverlayPaths enabledOverlayPaths2 = this.mImpl.getEnabledOverlayPaths(i, str, true);
                            overlayPaths = OverlayPolicyManager.filterByPolicy(enabledOverlayPaths2, enabledOverlayPaths2, str, i);
                        }
                        builder.addAll(OverlayPolicyManager.filterByPolicy(enabledOverlayPaths, overlayPaths, str, i));
                        builder.addAll(overlayPaths);
                        arrayMap.put(str, builder.build());
                    }
                } finally {
                }
            }
            Set hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            packageManagerInternal.setEnabledOverlayPackages(i, arrayMap, hashSet, hashSet2);
            for (String str2 : collection) {
                Slog.d("OverlayManager", "-> Updating overlay: target=" + str2 + " overlays=[" + arrayMap.get(str2) + "] userId=" + i);
                if (hashSet2.contains(str2)) {
                    Slog.e("OverlayManager", TextUtils.formatSimple("Failed to change enabled overlays for %s user %d", new Object[]{str2, Integer.valueOf(i)}));
                }
            }
            ArrayList arrayList2 = new ArrayList(hashSet);
            Trace.traceEnd(67108864L);
            return arrayList2;
        } catch (Throwable th) {
            Trace.traceEnd(67108864L);
            throw th;
        }
    }

    public final void updateTargetPackagesLocked(Set set, final boolean z) {
        if (CollectionUtils.isEmpty(set)) {
            return;
        }
        persistSettingsLocked();
        SparseArray sparseArray = new SparseArray();
        CollectionUtils.forEach(set, new OverlayManagerService$$ExternalSyntheticLambda2(sparseArray));
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            final ArraySet arraySet = (ArraySet) sparseArray.valueAt(i);
            final int keyAt = sparseArray.keyAt(i);
            final List updatePackageManagerLocked = updatePackageManagerLocked(keyAt, arraySet, false);
            if (!((ArrayList) updatePackageManagerLocked).isEmpty()) {
                FgThread.getHandler().post(new Runnable() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverlayManagerService overlayManagerService = OverlayManagerService.this;
                        List list = updatePackageManagerLocked;
                        int i2 = keyAt;
                        boolean z2 = z;
                        ArraySet arraySet2 = arraySet;
                        overlayManagerService.getClass();
                        try {
                            ActivityManager.getService().scheduleApplicationInfoChanged(list, i2);
                        } catch (RemoteException e) {
                            Slog.e("OverlayManager", "updateActivityManager remote exception", e);
                        }
                        if (!z2) {
                            CollectionUtils.forEach(arraySet2, new OverlayManagerService$$ExternalSyntheticLambda1(i2, (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)));
                        } else {
                            Slog.i("SWT_OverlayManager", "overlay changed broadcast to system for color theme");
                            CollectionUtils.forEach(new ArraySet(Arrays.asList("android")), new OverlayManagerService$$ExternalSyntheticLambda1(i2, (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)));
                        }
                    }
                });
            }
        }
    }
}
