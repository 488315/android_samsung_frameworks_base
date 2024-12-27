package com.android.server.uri;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.app.GrantedUriPermission;
import android.app.IUriGrantsManager;
import android.app.StatsManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.security.Flags;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.uri.UriMetricsHelper;
import com.android.server.uri.UriPermission;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.uri.UriPermissionOwner.ExternalToken;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import com.google.android.collect.Sets;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class UriGrantsManagerService extends IUriGrantsManager.Stub implements UriMetricsHelper.PersistentUriGrantsProvider {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public ActivityManagerInternal mAmInternal;
    public final AtomicFile mGrantFile;
    public UriMetricsHelper mMetricsHelper;
    public PackageManagerInternal mPmInternal;
    public final Object mLock = new Object();
    public final SparseArray mGrantedUriPermissions = new SparseArray();
    public final ArrayList mUriPermissionHistory = new ArrayList();
    public final H mH = new H(IoThread.get().getLooper());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.uri.UriGrantsManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends UriGrantsManagerService {
        @Override // com.android.server.uri.UriGrantsManagerService
        public int checkComponentPermission(String str, int i, int i2, boolean z) {
            return -1;
        }

        @Override // com.android.server.uri.UriGrantsManagerService
        public int checkUidPermission(String str, int i) {
            return -1;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            if (message.what != 1) {
                return;
            }
            UriGrantsManagerService uriGrantsManagerService = UriGrantsManagerService.this;
            uriGrantsManagerService.getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            ArrayList newArrayList = Lists.newArrayList();
            synchronized (uriGrantsManagerService.mLock) {
                try {
                    int size = uriGrantsManagerService.mGrantedUriPermissions.size();
                    i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        ArrayMap arrayMap = (ArrayMap) uriGrantsManagerService.mGrantedUriPermissions.valueAt(i2);
                        int size2 = arrayMap.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            UriPermission uriPermission = (UriPermission) arrayMap.valueAt(i3);
                            if (uriPermission.persistedModeFlags != 0) {
                                i++;
                                newArrayList.add(new UriPermission.Snapshot(uriPermission));
                            }
                        }
                    }
                } finally {
                }
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream startWrite = uriGrantsManagerService.mGrantFile.startWrite(uptimeMillis);
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "uri-grants");
                    Iterator it = newArrayList.iterator();
                    while (it.hasNext()) {
                        UriPermission.Snapshot snapshot = (UriPermission.Snapshot) it.next();
                        resolveSerializer.startTag((String) null, "uri-grant");
                        resolveSerializer.attributeInt((String) null, "sourceUserId", snapshot.uri.sourceUserId);
                        resolveSerializer.attributeInt((String) null, "targetUserId", snapshot.targetUserId);
                        resolveSerializer.attributeInterned((String) null, "sourcePkg", snapshot.sourcePkg);
                        resolveSerializer.attributeInterned((String) null, "targetPkg", snapshot.targetPkg);
                        resolveSerializer.attribute((String) null, SystemIntentProcessor.KEY_URI, String.valueOf(snapshot.uri.uri));
                        XmlUtils.writeBooleanAttribute(resolveSerializer, "prefix", snapshot.uri.prefix);
                        resolveSerializer.attributeInt((String) null, "modeFlags", snapshot.persistedModeFlags);
                        resolveSerializer.attributeLong((String) null, "createdTime", snapshot.persistedCreateTime);
                        resolveSerializer.endTag((String) null, "uri-grant");
                    }
                    resolveSerializer.endTag((String) null, "uri-grants");
                    resolveSerializer.endDocument();
                    uriGrantsManagerService.mGrantFile.finishWrite(startWrite);
                } catch (IOException unused) {
                    fileOutputStream = startWrite;
                    if (fileOutputStream != null) {
                        uriGrantsManagerService.mGrantFile.failWrite(fileOutputStream);
                    }
                    uriGrantsManagerService.mMetricsHelper.getClass();
                    FrameworkStatsLog.write(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_FLUSHED, i);
                }
            } catch (IOException unused2) {
            }
            uriGrantsManagerService.mMetricsHelper.getClass();
            FrameworkStatsLog.write(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_FLUSHED, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final Context mContext;
        public final UriGrantsManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mContext = context;
            this.mService = new UriGrantsManagerService(SystemServiceManager.ensureSystemDir(), "uri-grants");
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 500) {
                UriGrantsManagerService uriGrantsManagerService = this.mService;
                uriGrantsManagerService.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                uriGrantsManagerService.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                final UriMetricsHelper uriMetricsHelper = uriGrantsManagerService.mMetricsHelper;
                ((StatsManager) uriMetricsHelper.mContext.getSystemService(StatsManager.class)).setPullAtomCallback(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, UriMetricsHelper.DAILY_PULL_METADATA, ConcurrentUtils.DIRECT_EXECUTOR, new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.uri.UriMetricsHelper$$ExternalSyntheticLambda0
                    public final int onPullAtom(int i2, List list) {
                        UriGrantsManagerService uriGrantsManagerService2 = (UriGrantsManagerService) UriMetricsHelper.this.mPersistentUriGrantsProvider;
                        uriGrantsManagerService2.getClass();
                        ArrayList arrayList = new ArrayList();
                        synchronized (uriGrantsManagerService2.mLock) {
                            try {
                                int size = uriGrantsManagerService2.mGrantedUriPermissions.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    ArrayMap arrayMap = (ArrayMap) uriGrantsManagerService2.mGrantedUriPermissions.valueAt(i3);
                                    int size2 = arrayMap.size();
                                    for (int i4 = 0; i4 < size2; i4++) {
                                        UriPermission uriPermission = (UriPermission) arrayMap.valueAt(i4);
                                        if (uriPermission.persistedModeFlags != 0) {
                                            arrayList.add(uriPermission);
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        SparseArray sparseArray = new SparseArray();
                        int size3 = arrayList.size();
                        for (int i5 = 0; i5 < size3; i5++) {
                            int i6 = ((UriPermission) arrayList.get(i5)).targetUid;
                            sparseArray.put(i6, Integer.valueOf(((Integer) sparseArray.get(i6, 0)).intValue() + 1));
                        }
                        int size4 = sparseArray.size();
                        for (int i7 = 0; i7 < size4; i7++) {
                            list.add(FrameworkStatsLog.buildStatsEvent(FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, sparseArray.keyAt(i7), ((Integer) sparseArray.valueAt(i7)).intValue()));
                        }
                        return 0;
                    }
                });
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.uri.UriGrantsManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? r0 = this.mService;
            publishBinderService("uri_grants", r0);
            r0.mMetricsHelper = new UriMetricsHelper(this.mContext, r0);
            LocalServices.addService(UriGrantsManagerInternal.class, new LocalService());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService implements UriGrantsManagerInternal {
        public LocalService() {
        }

        public final boolean checkAuthorityGrants(int i, ProviderInfo providerInfo, int i2, boolean z) {
            boolean m1002$$Nest$mcheckAuthorityGrantsLocked;
            synchronized (UriGrantsManagerService.this.mLock) {
                m1002$$Nest$mcheckAuthorityGrantsLocked = UriGrantsManagerService.m1002$$Nest$mcheckAuthorityGrantsLocked(UriGrantsManagerService.this, i, providerInfo, i2, z);
            }
            return m1002$$Nest$mcheckAuthorityGrantsLocked;
        }

        public final int checkGrantUriPermission(int i, String str, Uri uri, int i2, int i3) {
            UriGrantsManagerService uriGrantsManagerService = UriGrantsManagerService.this;
            uriGrantsManagerService.getClass();
            UriGrantsManagerService.enforceNotIsolatedCaller("checkGrantUriPermission");
            uriGrantsManagerService.getClass();
            return uriGrantsManagerService.checkGrantUriPermissionUnlocked(i, str, new GrantUri(i3, i2, uri), i2, -1);
        }

        public final boolean checkUriPermission(GrantUri grantUri, int i, int i2, boolean z) {
            boolean checkUriPermissionLocked;
            if (z) {
                return UriGrantsManagerService.this.checkContentUriPermissionFullUnlocked(grantUri, i, i2);
            }
            synchronized (UriGrantsManagerService.this.mLock) {
                checkUriPermissionLocked = UriGrantsManagerService.this.checkUriPermissionLocked(grantUri, i, i2);
            }
            return checkUriPermissionLocked;
        }

        public final void grantUriPermissionUncheckedFromIntent(NeededUriGrants neededUriGrants, UriPermissionOwner uriPermissionOwner) {
            UriGrantsManagerService uriGrantsManagerService = UriGrantsManagerService.this;
            uriGrantsManagerService.getClass();
            if (neededUriGrants == null) {
                return;
            }
            int size = neededUriGrants.uris.size();
            for (int i = 0; i < size; i++) {
                uriGrantsManagerService.grantUriPermissionUnchecked(neededUriGrants.targetUid, neededUriGrants.targetPkg, (GrantUri) neededUriGrants.uris.valueAt(i), neededUriGrants.flags, uriPermissionOwner);
            }
        }

        public final NeededUriGrants internalCheckGrantUriPermissionFromIntent(Intent intent, int i, String str, int i2, Integer num) {
            return UriGrantsManagerService.this.checkGrantUriPermissionFromIntentUnlocked(i, str, intent, intent != null ? intent.getFlags() : 0, null, i2, num);
        }

        public final IBinder newUriPermissionOwner(String str) {
            UriGrantsManagerService.this.getClass();
            UriGrantsManagerService.enforceNotIsolatedCaller("newUriPermissionOwner");
            UriPermissionOwner uriPermissionOwner = new UriPermissionOwner(this, str);
            if (uriPermissionOwner.externalToken == null) {
                uriPermissionOwner.externalToken = uriPermissionOwner.new ExternalToken();
            }
            return uriPermissionOwner.externalToken;
        }

        public final void removeUriPermissionsForPackage(int i, String str, boolean z) {
            synchronized (UriGrantsManagerService.this.mLock) {
                UriGrantsManagerService.this.removeUriPermissionsForPackageLocked(str, z, false, i);
            }
        }

        public final void revokeUriPermission(int i, int i2, GrantUri grantUri, String str) {
            UriGrantsManagerService uriGrantsManagerService = UriGrantsManagerService.this;
            uriGrantsManagerService.getClass();
            ProviderInfo providerInfo = uriGrantsManagerService.getProviderInfo(grantUri.sourceUserId, 786432, i, grantUri.uri.getAuthority());
            if (providerInfo == null) {
                Slog.w("UriGrantsManagerService", "No content provider found for permission revoke: " + grantUri.toSafeString());
            } else {
                boolean checkHoldingPermissionsUnlocked = uriGrantsManagerService.checkHoldingPermissionsUnlocked(providerInfo, grantUri, i, i2);
                synchronized (uriGrantsManagerService.mLock) {
                    uriGrantsManagerService.revokeUriPermissionLocked(str, i, grantUri, i2, checkHoldingPermissionsUnlocked);
                }
            }
        }

        public final void revokeUriPermissionFromOwner(IBinder iBinder, Uri uri, int i, int i2) {
            revokeUriPermissionFromOwner(iBinder, uri, i, i2, null, -1);
        }

        public final void revokeUriPermissionFromOwner(IBinder iBinder, Uri uri, int i, int i2, String str, int i3) {
            UriPermissionOwner uriPermissionOwner = iBinder instanceof UriPermissionOwner.ExternalToken ? UriPermissionOwner.this : null;
            if (uriPermissionOwner != null) {
                uriPermissionOwner.removeUriPermission(i, i3, uri != null ? new GrantUri(i2, i, uri) : null, str);
            } else {
                throw new IllegalArgumentException("Unknown owner: " + iBinder);
            }
        }
    }

    /* renamed from: -$$Nest$mcheckAuthorityGrantsLocked, reason: not valid java name */
    public static boolean m1002$$Nest$mcheckAuthorityGrantsLocked(UriGrantsManagerService uriGrantsManagerService, int i, ProviderInfo providerInfo, int i2, boolean z) {
        boolean z2;
        ArrayMap arrayMap = (ArrayMap) uriGrantsManagerService.mGrantedUriPermissions.get(i);
        if (arrayMap == null) {
            return false;
        }
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            GrantUri grantUri = (GrantUri) arrayMap.keyAt(size);
            if (grantUri.sourceUserId == i2 || !z) {
                String authority = grantUri.uri.getAuthority();
                String str = providerInfo.authority;
                if (str.indexOf(59) != -1) {
                    String[] split = str.split(";");
                    int length = split.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            z2 = false;
                            break;
                        }
                        if (split[i3].equals(authority)) {
                            z2 = true;
                            break;
                        }
                        i3++;
                    }
                } else {
                    z2 = str.equals(authority);
                }
                if (z2) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$mreadGrantedUriPermissionsLocked, reason: not valid java name */
    public static void m1003$$Nest$mreadGrantedUriPermissionsLocked(UriGrantsManagerService uriGrantsManagerService) {
        FileInputStream fileInputStream;
        String str;
        long j;
        int attributeInt;
        uriGrantsManagerService.getClass();
        String str2 = "Failed reading Uri grants";
        String str3 = "UriGrantsManagerService";
        long currentTimeMillis = System.currentTimeMillis();
        FileInputStream fileInputStream2 = null;
        String str4 = null;
        try {
            try {
                try {
                } catch (FileNotFoundException unused) {
                    fileInputStream2 = null;
                } catch (XmlPullParserException e) {
                    e = e;
                    fileInputStream2 = null;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = null;
                }
                try {
                    FileInputStream openRead = uriGrantsManagerService.mGrantFile.openRead();
                    try {
                        try {
                            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                            while (true) {
                                int next = resolvePullParser.next();
                                if (next == 1) {
                                    IoUtils.closeQuietly(openRead);
                                    return;
                                }
                                String name = resolvePullParser.getName();
                                if (next == 2 && "uri-grant".equals(name)) {
                                    int attributeInt2 = resolvePullParser.getAttributeInt(str4, "userHandle", -10000);
                                    if (attributeInt2 != -10000) {
                                        attributeInt = attributeInt2;
                                    } else {
                                        attributeInt2 = resolvePullParser.getAttributeInt(str4, "sourceUserId");
                                        attributeInt = resolvePullParser.getAttributeInt(str4, "targetUserId");
                                    }
                                    String attributeValue = resolvePullParser.getAttributeValue(str4, "sourcePkg");
                                    String attributeValue2 = resolvePullParser.getAttributeValue(str4, "targetPkg");
                                    Uri parse = Uri.parse(resolvePullParser.getAttributeValue(str4, SystemIntentProcessor.KEY_URI));
                                    boolean attributeBoolean = resolvePullParser.getAttributeBoolean(str4, "prefix", false);
                                    int attributeInt3 = resolvePullParser.getAttributeInt(str4, "modeFlags");
                                    str = str2;
                                    String str5 = str3;
                                    try {
                                        long attributeLong = resolvePullParser.getAttributeLong(str4, "createdTime", currentTimeMillis);
                                        j = currentTimeMillis;
                                        ProviderInfo providerInfo = uriGrantsManagerService.getProviderInfo(attributeInt2, 786432, 1000, parse.getAuthority());
                                        if (providerInfo != null) {
                                            try {
                                                if (attributeValue.equals(providerInfo.packageName)) {
                                                    fileInputStream = openRead;
                                                    try {
                                                        try {
                                                            int packageUid = uriGrantsManagerService.mPmInternal.getPackageUid(attributeValue2, 8192L, attributeInt);
                                                            if (packageUid != -1) {
                                                                uriGrantsManagerService.findOrCreateUriPermissionLocked(attributeValue, attributeValue2, packageUid, new GrantUri(attributeInt2, attributeBoolean ? 128 : 0, parse)).initPersistedModes(attributeInt3, attributeLong);
                                                                uriGrantsManagerService.mPmInternal.grantImplicitAccess(attributeInt, null, UserHandle.getAppId(packageUid), providerInfo.applicationInfo.uid, false, true);
                                                            }
                                                            str3 = str5;
                                                        } catch (FileNotFoundException unused2) {
                                                            fileInputStream2 = fileInputStream;
                                                            IoUtils.closeQuietly(fileInputStream2);
                                                        } catch (Throwable th2) {
                                                            th = th2;
                                                            fileInputStream2 = fileInputStream;
                                                            IoUtils.closeQuietly(fileInputStream2);
                                                            throw th;
                                                        }
                                                    } catch (IOException e2) {
                                                        e = e2;
                                                        str2 = str;
                                                        str3 = str5;
                                                        fileInputStream2 = fileInputStream;
                                                        Slog.wtf(str3, str2, e);
                                                        IoUtils.closeQuietly(fileInputStream2);
                                                    } catch (XmlPullParserException e3) {
                                                        e = e3;
                                                        str2 = str;
                                                        str3 = str5;
                                                        fileInputStream2 = fileInputStream;
                                                        Slog.wtf(str3, str2, e);
                                                        IoUtils.closeQuietly(fileInputStream2);
                                                    }
                                                }
                                            } catch (IOException e4) {
                                                e = e4;
                                                fileInputStream = openRead;
                                            } catch (XmlPullParserException e5) {
                                                e = e5;
                                                fileInputStream = openRead;
                                            }
                                        }
                                        fileInputStream = openRead;
                                        try {
                                            str3 = str5;
                                        } catch (IOException e6) {
                                            e = e6;
                                            str3 = str5;
                                            str2 = str;
                                            fileInputStream2 = fileInputStream;
                                            Slog.wtf(str3, str2, e);
                                            IoUtils.closeQuietly(fileInputStream2);
                                        } catch (XmlPullParserException e7) {
                                            e = e7;
                                            str3 = str5;
                                            str2 = str;
                                            fileInputStream2 = fileInputStream;
                                            Slog.wtf(str3, str2, e);
                                            IoUtils.closeQuietly(fileInputStream2);
                                        }
                                        try {
                                            Slog.w(str3, "Persisted grant for " + parse + " had source " + attributeValue + " but instead found " + providerInfo);
                                        } catch (IOException e8) {
                                            e = e8;
                                            str2 = str;
                                            fileInputStream2 = fileInputStream;
                                            Slog.wtf(str3, str2, e);
                                            IoUtils.closeQuietly(fileInputStream2);
                                        } catch (XmlPullParserException e9) {
                                            e = e9;
                                            str2 = str;
                                            fileInputStream2 = fileInputStream;
                                            Slog.wtf(str3, str2, e);
                                            IoUtils.closeQuietly(fileInputStream2);
                                        }
                                    } catch (IOException e10) {
                                        e = e10;
                                        fileInputStream = openRead;
                                    } catch (XmlPullParserException e11) {
                                        e = e11;
                                        fileInputStream = openRead;
                                    }
                                } else {
                                    str = str2;
                                    j = currentTimeMillis;
                                    fileInputStream = openRead;
                                }
                                str2 = str;
                                currentTimeMillis = j;
                                openRead = fileInputStream;
                                str4 = null;
                            }
                        } catch (FileNotFoundException unused3) {
                            fileInputStream = openRead;
                        } catch (Throwable th3) {
                            th = th3;
                            fileInputStream = openRead;
                        }
                    } catch (IOException e12) {
                        e = e12;
                        fileInputStream = openRead;
                    } catch (XmlPullParserException e13) {
                        e = e13;
                        fileInputStream = openRead;
                    }
                } catch (IOException e14) {
                    e = e14;
                    fileInputStream2 = null;
                    Slog.wtf(str3, str2, e);
                    IoUtils.closeQuietly(fileInputStream2);
                }
            } catch (IOException e15) {
                e = e15;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public UriGrantsManagerService(File file, String str) {
        File file2 = new File(file, "urigrants.xml");
        this.mGrantFile = str != null ? new AtomicFile(file2, str) : new AtomicFile(file2);
    }

    public static UriGrantsManagerService createForTest(File file) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(file, null);
        anonymousClass1.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        anonymousClass1.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        return anonymousClass1;
    }

    public static void enforceNotIsolatedCaller(String str) {
        if (UserHandle.isIsolated(Binder.getCallingUid())) {
            throw new SecurityException("Isolated process not allowed to call ".concat(str));
        }
    }

    public final void addToHistoryLocked(String str) {
        if (this.mUriPermissionHistory.size() > 127) {
            this.mUriPermissionHistory.remove(0);
        }
        this.mUriPermissionHistory.add(TIME_FORMATTER.format(LocalDateTime.now()) + ": " + str);
    }

    public int checkComponentPermission(String str, int i, int i2, boolean z) {
        return ActivityManager.checkComponentPermission(str, i, i2, z);
    }

    public final boolean checkContentUriPermissionFullUnlocked(GrantUri grantUri, int i, int i2) {
        boolean checkUriPermissionLocked;
        if (i < 0) {
            throw new IllegalArgumentException("Uid must be positive for the content URI permission check of " + grantUri.uri.toSafeString());
        }
        if (!Intent.isAccessUriMode(i2) || !"content".equals(grantUri.uri.getScheme())) {
            throw new IllegalArgumentException("The URI must be a content URI and the mode flags must be at least read and/or write for the content URI permission check of " + grantUri.uri.toSafeString());
        }
        int appId = UserHandle.getAppId(i);
        if (appId == 1000 || appId == 0) {
            return true;
        }
        ProviderInfo providerInfo = getProviderInfo(grantUri.sourceUserId, 268435456, i, grantUri.uri.getAuthority());
        if (providerInfo == null) {
            Slog.w("UriGrantsManagerService", "No content provider found for content URI permission check: " + grantUri.uri.toSafeString());
            return false;
        }
        if (checkHoldingPermissionsUnlocked(providerInfo, grantUri, i, i2)) {
            return true;
        }
        synchronized (this.mLock) {
            checkUriPermissionLocked = checkUriPermissionLocked(grantUri, i, i2);
        }
        return checkUriPermissionLocked;
    }

    public final NeededUriGrants checkGrantUriPermissionFromIntentUnlocked(int i, String str, Intent intent, int i2, NeededUriGrants neededUriGrants, int i3, Integer num) {
        int packageUid;
        NeededUriGrants checkGrantUriPermissionFromIntentUnlocked;
        NeededUriGrants neededUriGrants2 = neededUriGrants;
        if (str == null) {
            throw new NullPointerException("targetPkg");
        }
        if (intent == null) {
            return null;
        }
        int contentUserHint = intent.getContentUserHint();
        if (contentUserHint == -2) {
            contentUserHint = UserHandle.getUserId(i);
        }
        int i4 = contentUserHint;
        if (Flags.contentUriPermissionApis()) {
            try {
                Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM", Uri.class);
                if (uri != null) {
                    enforceRequireContentUriPermissionFromCaller(num, GrantUri.resolve(i4, i2, uri), i);
                }
            } catch (BadParcelableException e) {
                Slog.w("UriGrantsManagerService", "Failed to unparcel an URI in EXTRA_STREAM, skipping requireContentUriPermissionFromCaller: " + e);
            }
            try {
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("android.intent.extra.STREAM", Uri.class);
                if (parcelableArrayListExtra != null) {
                    for (int size = parcelableArrayListExtra.size() - 1; size >= 0; size--) {
                        enforceRequireContentUriPermissionFromCaller(num, GrantUri.resolve(i4, i2, (Uri) parcelableArrayListExtra.get(size)), i);
                    }
                }
            } catch (BadParcelableException e2) {
                Slog.w("UriGrantsManagerService", "Failed to unparcel an ArrayList of URIs in EXTRA_STREAM, skipping requireContentUriPermissionFromCaller: " + e2);
            }
        }
        Uri data = intent.getData();
        ClipData clipData = intent.getClipData();
        if (data == null && clipData == null) {
            return null;
        }
        if (neededUriGrants2 != null) {
            packageUid = neededUriGrants2.targetUid;
        } else {
            packageUid = this.mPmInternal.getPackageUid(str, 268435456L, i3);
            if (packageUid < 0) {
                return null;
            }
        }
        int i5 = packageUid;
        if (data != null) {
            GrantUri resolve = GrantUri.resolve(i4, i2, data);
            if (Flags.contentUriPermissionApis()) {
                enforceRequireContentUriPermissionFromCaller(num, resolve, i);
            }
            i5 = checkGrantUriPermissionUnlocked(i, str, resolve, i2, i5);
            if (i5 > 0) {
                NeededUriGrants neededUriGrants3 = neededUriGrants2 == null ? new NeededUriGrants(i5, i2, str) : neededUriGrants2;
                neededUriGrants3.uris.add(resolve);
                neededUriGrants2 = neededUriGrants3;
            }
        }
        if (clipData == null) {
            return neededUriGrants2;
        }
        int i6 = i5;
        NeededUriGrants neededUriGrants4 = neededUriGrants2;
        for (int i7 = 0; i7 < clipData.getItemCount(); i7++) {
            Uri uri2 = clipData.getItemAt(i7).getUri();
            if (uri2 != null) {
                GrantUri resolve2 = GrantUri.resolve(i4, i2, uri2);
                if (Flags.contentUriPermissionApis()) {
                    enforceRequireContentUriPermissionFromCaller(num, resolve2, i);
                }
                int checkGrantUriPermissionUnlocked = checkGrantUriPermissionUnlocked(i, str, resolve2, i2, i6);
                if (checkGrantUriPermissionUnlocked > 0) {
                    NeededUriGrants neededUriGrants5 = neededUriGrants4 == null ? new NeededUriGrants(checkGrantUriPermissionUnlocked, i2, str) : neededUriGrants4;
                    neededUriGrants5.uris.add(resolve2);
                    neededUriGrants4 = neededUriGrants5;
                }
                i6 = checkGrantUriPermissionUnlocked;
            } else {
                Intent intent2 = clipData.getItemAt(i7).getIntent();
                if (intent2 != null && (checkGrantUriPermissionFromIntentUnlocked = checkGrantUriPermissionFromIntentUnlocked(i, str, intent2, i2, neededUriGrants4, i3, num)) != null) {
                    neededUriGrants4 = checkGrantUriPermissionFromIntentUnlocked;
                }
            }
        }
        return neededUriGrants4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x0139, code lost:
    
        if (r1 != false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00e9, code lost:
    
        if (checkHoldingPermissionsUnlocked(r14, r19, r8, r20) != false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00eb, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x013c, code lost:
    
        r1 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int checkGrantUriPermissionUnlocked(int r17, java.lang.String r18, com.android.server.uri.GrantUri r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.uri.UriGrantsManagerService.checkGrantUriPermissionUnlocked(int, java.lang.String, com.android.server.uri.GrantUri, int, int):int");
    }

    public final int checkGrantUriPermission_ignoreNonSystem(int i, String str, Uri uri, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0 && checkComponentPermission("android.permission.INTERACT_ACROSS_USERS_FULL", callingUid, -1, true) != 0) {
            return -1;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return checkGrantUriPermissionUnlocked(i, str, new GrantUri(i3, i2, uri), i2, -1);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean checkHoldingPermissionsInternalUnlocked(ProviderInfo providerInfo, GrantUri grantUri, int i, int i2, boolean z) {
        int userId;
        String writePermission;
        String readPermission;
        String str;
        String str2;
        if (Thread.holdsLock(this.mLock)) {
            throw new IllegalStateException("Must never hold local mLock");
        }
        if (providerInfo.applicationInfo.uid == i) {
            return true;
        }
        if (!providerInfo.exported) {
            return false;
        }
        boolean z2 = (i2 & 1) == 0;
        boolean z3 = (i2 & 2) == 0;
        if (!z2 && (str2 = providerInfo.readPermission) != null && z && checkUidPermission(str2, i) == 0) {
            z2 = true;
        }
        if (!z3 && (str = providerInfo.writePermission) != null && z && checkUidPermission(str, i) == 0) {
            z3 = true;
        }
        boolean z4 = providerInfo.readPermission == null;
        boolean z5 = providerInfo.writePermission == null;
        PathPermission[] pathPermissionArr = providerInfo.pathPermissions;
        if (pathPermissionArr != null) {
            String path = grantUri.uri.getPath();
            int length = pathPermissionArr.length;
            while (length > 0 && (!z2 || !z3)) {
                length--;
                PathPermission pathPermission = pathPermissionArr[length];
                if (pathPermission.match(path)) {
                    if (!z2 && (readPermission = pathPermission.getReadPermission()) != null) {
                        if (z && checkUidPermission(readPermission, i) == 0) {
                            z2 = true;
                        } else {
                            z4 = false;
                        }
                    }
                    if (!z3 && (writePermission = pathPermission.getWritePermission()) != null) {
                        if (z && checkUidPermission(writePermission, i) == 0) {
                            z3 = true;
                        } else {
                            z5 = false;
                        }
                    }
                }
            }
        }
        if (z4) {
            z2 = true;
        }
        if (z5) {
            z3 = true;
        }
        return z2 && z3 && (!providerInfo.forceUriPermissions || ((userId = UserHandle.getUserId(providerInfo.applicationInfo.uid)) == UserHandle.getUserId(i) && this.mAmInternal.checkContentProviderUriPermission(grantUri.uri, userId, i, i2) == 0));
    }

    public final boolean checkHoldingPermissionsUnlocked(ProviderInfo providerInfo, GrantUri grantUri, int i, int i2) {
        if (UserHandle.getUserId(i) == grantUri.sourceUserId || checkComponentPermission("android.permission.INTERACT_ACROSS_USERS", i, -1, true) == 0) {
            return checkHoldingPermissionsInternalUnlocked(providerInfo, grantUri, i, i2, true);
        }
        return false;
    }

    public int checkUidPermission(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkUidPermission(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final boolean checkUriPermissionLocked(GrantUri grantUri, int i, int i2) {
        char c = (i2 & 64) != 0 ? (char) 3 : (char) 1;
        if (i == 0) {
            return true;
        }
        ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(i);
        if (arrayMap == null) {
            return false;
        }
        UriPermission uriPermission = (UriPermission) arrayMap.get(grantUri);
        if (uriPermission != null) {
            int i3 = i2 & 3;
            if (((uriPermission.persistableModeFlags & i3) == i3 ? (char) 3 : (uriPermission.globalModeFlags & i3) == i3 ? (char) 2 : (uriPermission.ownedModeFlags & i3) == i3 ? (char) 1 : (char) 0) >= c) {
                return true;
            }
        }
        int size = arrayMap.size();
        for (int i4 = 0; i4 < size; i4++) {
            UriPermission uriPermission2 = (UriPermission) arrayMap.valueAt(i4);
            GrantUri grantUri2 = uriPermission2.uri;
            if (grantUri2.prefix && grantUri.uri.isPathPrefixMatch(grantUri2.uri)) {
                int i5 = i2 & 3;
                if (((uriPermission2.persistableModeFlags & i5) == i5 ? (char) 3 : (uriPermission2.globalModeFlags & i5) == i5 ? (char) 2 : (uriPermission2.ownedModeFlags & i5) == i5 ? (char) 1 : (char) 0) >= c) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void clearGrantedUriPermissions(String str, int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.CLEAR_APP_GRANTED_URI_PERMISSIONS", "clearGrantedUriPermissions");
        synchronized (this.mLock) {
            removeUriPermissionsForPackageLocked(str, true, true, i);
        }
    }

    public final void enforceRequireContentUriPermissionFromCaller(Integer num, GrantUri grantUri, int i) {
        if (num == null || num.intValue() == 0 || !"content".equals(grantUri.uri.getScheme())) {
            return;
        }
        boolean z = !ActivityInfo.isRequiredContentUriPermissionRead(num.intValue()) || checkContentUriPermissionFullUnlocked(grantUri, i, 1);
        boolean z2 = !ActivityInfo.isRequiredContentUriPermissionWrite(num.intValue()) || checkContentUriPermissionFullUnlocked(grantUri, i, 2);
        if (num.intValue() == 3) {
            if (z || z2) {
                return;
            }
        } else if (z && z2) {
            return;
        }
        throw new SecurityException("You can't launch this activity because you don't have the required " + ActivityInfo.requiredContentUriPermissionToShortString(num.intValue()) + " access to " + grantUri.uri);
    }

    public final UriPermission findOrCreateUriPermissionLocked(String str, String str2, int i, GrantUri grantUri) {
        ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(i);
        if (arrayMap == null) {
            arrayMap = Maps.newArrayMap();
            this.mGrantedUriPermissions.put(i, arrayMap);
        }
        UriPermission uriPermission = (UriPermission) arrayMap.get(grantUri);
        if (uriPermission != null) {
            return uriPermission;
        }
        UriPermission uriPermission2 = new UriPermission(str, str2, i, grantUri);
        arrayMap.put(grantUri, uriPermission2);
        addToHistoryLocked("+" + uriPermission2.targetUid + "<" + arrayMap.size() + "> " + uriPermission2.uri + "<-" + str);
        return uriPermission2;
    }

    public final UriPermission findUriPermissionLocked(int i, GrantUri grantUri) {
        ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(i);
        if (arrayMap != null) {
            return (UriPermission) arrayMap.get(grantUri);
        }
        return null;
    }

    public final ParceledListSlice getGrantedUriPermissions(String str, int i) {
        int i2;
        this.mAmInternal.enforceCallingPermission("android.permission.GET_APP_GRANTED_URI_PERMISSIONS", "getGrantedUriPermissions");
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                int size = this.mGrantedUriPermissions.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.valueAt(i3);
                    for (0; i2 < arrayMap.size(); i2 + 1) {
                        UriPermission uriPermission = (UriPermission) arrayMap.valueAt(i2);
                        i2 = (str == null || str.equals(uriPermission.targetPkg)) ? 0 : i2 + 1;
                        if (uriPermission.targetUserId == i && uriPermission.persistedModeFlags != 0) {
                            arrayList.add(new GrantedUriPermission(uriPermission.uri.uri, uriPermission.targetPkg));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new ParceledListSlice(arrayList);
    }

    public UriGrantsManagerInternal getLocalService() {
        return new LocalService();
    }

    public final ProviderInfo getProviderInfo(int i, int i2, int i3, String str) {
        return ((PackageManagerService.PackageManagerInternalImpl) this.mPmInternal).mService.snapshotComputer().resolveContentProvider(i, i3, i2 | 2048, str);
    }

    public final ParceledListSlice getUriPermissions(String str, boolean z, boolean z2) {
        enforceNotIsolatedCaller("getUriPermissions");
        Objects.requireNonNull(str, "packageName");
        int callingUid = Binder.getCallingUid();
        if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageUid(str, 786432L, UserHandle.getUserId(callingUid)) != callingUid) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Package ", str, " does not belong to calling UID "));
        }
        ArrayList newArrayList = Lists.newArrayList();
        synchronized (this.mLock) {
            try {
                if (z) {
                    ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(callingUid);
                    if (arrayMap == null) {
                        Slog.w("UriGrantsManagerService", "No permission grants found for ".concat(str));
                    } else {
                        for (int i = 0; i < arrayMap.size(); i++) {
                            UriPermission uriPermission = (UriPermission) arrayMap.valueAt(i);
                            if (str.equals(uriPermission.targetPkg) && (!z2 || uriPermission.persistedModeFlags != 0)) {
                                newArrayList.add(new android.content.UriPermission(uriPermission.uri.uri, uriPermission.persistedModeFlags, uriPermission.persistedCreateTime));
                            }
                        }
                    }
                } else {
                    int size = this.mGrantedUriPermissions.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ArrayMap arrayMap2 = (ArrayMap) this.mGrantedUriPermissions.valueAt(i2);
                        for (int i3 = 0; i3 < arrayMap2.size(); i3++) {
                            UriPermission uriPermission2 = (UriPermission) arrayMap2.valueAt(i3);
                            if (str.equals(uriPermission2.sourcePkg) && (!z2 || uriPermission2.persistedModeFlags != 0)) {
                                newArrayList.add(new android.content.UriPermission(uriPermission2.uri.uri, uriPermission2.persistedModeFlags, uriPermission2.persistedCreateTime));
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new ParceledListSlice(newArrayList);
    }

    public final void grantUriPermissionFromOwner(IBinder iBinder, int i, String str, Uri uri, int i2, int i3, int i4) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i4, false, 2, "grantUriPermissionFromOwner", (String) null);
        UriPermissionOwner uriPermissionOwner = iBinder instanceof UriPermissionOwner.ExternalToken ? UriPermissionOwner.this : null;
        if (uriPermissionOwner == null) {
            throw new IllegalArgumentException("Unknown owner: " + iBinder);
        }
        if (i != Binder.getCallingUid() && Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("nice try");
        }
        if (str == null) {
            throw new IllegalArgumentException("null target");
        }
        if (uri == null) {
            throw new IllegalArgumentException("null uri");
        }
        GrantUri grantUri = new GrantUri(i3, i2, uri);
        int checkGrantUriPermissionUnlocked = checkGrantUriPermissionUnlocked(i, str, grantUri, i2, this.mPmInternal.getPackageUid(str, 268435456L, handleIncomingUser));
        if (checkGrantUriPermissionUnlocked < 0) {
            return;
        }
        grantUriPermissionUnchecked(checkGrantUriPermissionUnlocked, str, grantUri, i2, uriPermissionOwner);
    }

    public final void grantUriPermissionUnchecked(int i, String str, GrantUri grantUri, int i2, UriPermissionOwner uriPermissionOwner) {
        UriPermission findOrCreateUriPermissionLocked;
        if (Intent.isAccessUriMode(i2)) {
            ProviderInfo providerInfo = getProviderInfo(grantUri.sourceUserId, 268435456, 1000, grantUri.uri.getAuthority());
            if (providerInfo == null) {
                Slog.w("UriGrantsManagerService", "No content provider found for grant: " + grantUri.toSafeString());
                return;
            }
            synchronized (this.mLock) {
                findOrCreateUriPermissionLocked = findOrCreateUriPermissionLocked(providerInfo.packageName, str, i, grantUri);
            }
            int i3 = i2 & 64;
            int i4 = i2 & 3;
            if (i3 != 0) {
                findOrCreateUriPermissionLocked.persistableModeFlags |= i4;
            }
            if (uriPermissionOwner == null) {
                findOrCreateUriPermissionLocked.globalModeFlags |= i4;
            } else {
                if ((i2 & 1) != 0) {
                    if (findOrCreateUriPermissionLocked.mReadOwners == null) {
                        findOrCreateUriPermissionLocked.mReadOwners = Sets.newArraySet();
                        findOrCreateUriPermissionLocked.ownedModeFlags |= 1;
                        findOrCreateUriPermissionLocked.updateModeFlags();
                    }
                    if (findOrCreateUriPermissionLocked.mReadOwners.add(uriPermissionOwner)) {
                        synchronized (uriPermissionOwner) {
                            try {
                                if (uriPermissionOwner.mReadPerms == null) {
                                    uriPermissionOwner.mReadPerms = Sets.newArraySet();
                                }
                                uriPermissionOwner.mReadPerms.add(findOrCreateUriPermissionLocked);
                            } finally {
                            }
                        }
                    }
                }
                if ((i2 & 2) != 0) {
                    if (findOrCreateUriPermissionLocked.mWriteOwners == null) {
                        findOrCreateUriPermissionLocked.mWriteOwners = Sets.newArraySet();
                        findOrCreateUriPermissionLocked.ownedModeFlags |= 2;
                        findOrCreateUriPermissionLocked.updateModeFlags();
                    }
                    if (findOrCreateUriPermissionLocked.mWriteOwners.add(uriPermissionOwner)) {
                        synchronized (uriPermissionOwner) {
                            try {
                                if (uriPermissionOwner.mWritePerms == null) {
                                    uriPermissionOwner.mWritePerms = Sets.newArraySet();
                                }
                                uriPermissionOwner.mWritePerms.add(findOrCreateUriPermissionLocked);
                            } finally {
                            }
                        }
                    }
                }
            }
            findOrCreateUriPermissionLocked.updateModeFlags();
            this.mPmInternal.grantImplicitAccess(UserHandle.getUserId(i), null, UserHandle.getAppId(i), providerInfo.applicationInfo.uid, false, i3 != 0);
        }
    }

    public final boolean maybePrunePersistedUriGrantsLocked(int i) {
        ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(i);
        if (arrayMap == null || arrayMap.size() < 512) {
            return false;
        }
        ArrayList newArrayList = Lists.newArrayList();
        for (UriPermission uriPermission : arrayMap.values()) {
            if (uriPermission.persistedModeFlags != 0) {
                newArrayList.add(uriPermission);
            }
        }
        int size = newArrayList.size() - 512;
        if (size <= 0) {
            return false;
        }
        Collections.sort(newArrayList, new UriPermission.PersistedTimeComparator());
        for (int i2 = 0; i2 < size; i2++) {
            UriPermission uriPermission2 = (UriPermission) newArrayList.get(i2);
            uriPermission2.releasePersistableModes(-1);
            removeUriPermissionIfNeededLocked(uriPermission2);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0066, code lost:
    
        r4 = r3.releasePersistableModes(r9);
        removeUriPermissionIfNeededLocked(r3);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void releasePersistableUriPermission(android.net.Uri r8, int r9, java.lang.String r10, int r11) {
        /*
            r7 = this;
            java.lang.String r0 = "No permission grants found for UID "
            if (r10 == 0) goto L17
            android.app.ActivityManagerInternal r1 = r7.mAmInternal
            java.lang.String r2 = "android.permission.FORCE_PERSISTABLE_URI_PERMISSIONS"
            java.lang.String r3 = "releasePersistableUriPermission"
            r1.enforceCallingPermission(r2, r3)
            android.content.pm.PackageManagerInternal r1 = r7.mPmInternal
            r2 = 0
            int r1 = r1.getPackageUid(r10, r2, r11)
            goto L21
        L17:
            java.lang.String r1 = "releasePersistableUriPermission"
            enforceNotIsolatedCaller(r1)
            int r1 = android.os.Binder.getCallingUid()
        L21:
            r2 = 3
            com.android.internal.util.Preconditions.checkFlagsArgument(r9, r2)
            java.lang.Object r2 = r7.mLock
            monitor-enter(r2)
            com.android.server.uri.GrantUri r3 = new com.android.server.uri.GrantUri     // Catch: java.lang.Throwable -> L62
            r4 = 0
            r3.<init>(r11, r4, r8)     // Catch: java.lang.Throwable -> L62
            com.android.server.uri.UriPermission r3 = r7.findUriPermissionLocked(r1, r3)     // Catch: java.lang.Throwable -> L62
            com.android.server.uri.GrantUri r5 = new com.android.server.uri.GrantUri     // Catch: java.lang.Throwable -> L62
            r6 = 128(0x80, float:1.794E-43)
            r5.<init>(r11, r6, r8)     // Catch: java.lang.Throwable -> L62
            com.android.server.uri.UriPermission r11 = r7.findUriPermissionLocked(r1, r5)     // Catch: java.lang.Throwable -> L62
            if (r3 != 0) goto L64
            if (r11 != 0) goto L64
            if (r10 == 0) goto L44
            goto L64
        L44:
            java.lang.SecurityException r7 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L62
            r9.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r10 = " and Uri "
            r9.append(r10)     // Catch: java.lang.Throwable -> L62
            java.lang.String r8 = r8.toSafeString()     // Catch: java.lang.Throwable -> L62
            r9.append(r8)     // Catch: java.lang.Throwable -> L62
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Throwable -> L62
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L62
            throw r7     // Catch: java.lang.Throwable -> L62
        L62:
            r7 = move-exception
            goto L7e
        L64:
            if (r3 == 0) goto L6d
            boolean r4 = r3.releasePersistableModes(r9)     // Catch: java.lang.Throwable -> L62
            r7.removeUriPermissionIfNeededLocked(r3)     // Catch: java.lang.Throwable -> L62
        L6d:
            if (r11 == 0) goto L77
            boolean r8 = r11.releasePersistableModes(r9)     // Catch: java.lang.Throwable -> L62
            r4 = r4 | r8
            r7.removeUriPermissionIfNeededLocked(r11)     // Catch: java.lang.Throwable -> L62
        L77:
            if (r4 == 0) goto L7c
            r7.schedulePersistUriGrants()     // Catch: java.lang.Throwable -> L62
        L7c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L62
            return
        L7e:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L62
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.uri.UriGrantsManagerService.releasePersistableUriPermission(android.net.Uri, int, java.lang.String, int):void");
    }

    public final void removeUriPermissionIfNeededLocked(UriPermission uriPermission) {
        if (uriPermission.modeFlags != 0) {
            return;
        }
        SparseArray sparseArray = this.mGrantedUriPermissions;
        int i = uriPermission.targetUid;
        ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
        if (arrayMap == null) {
            return;
        }
        GrantUri grantUri = uriPermission.uri;
        arrayMap.remove(grantUri);
        addToHistoryLocked(PackageManagerShellCommandDataLoader.STDIN_PATH + i + "{" + arrayMap.size() + "} " + grantUri);
        if (arrayMap.isEmpty()) {
            this.mGrantedUriPermissions.remove(i);
        }
    }

    public final void removeUriPermissionsForPackageLocked(String str, boolean z, boolean z2, int i) {
        if (i == -1 && str == null) {
            throw new IllegalArgumentException("Must narrow by either package or user");
        }
        int size = this.mGrantedUriPermissions.size();
        int i2 = 0;
        boolean z3 = false;
        while (i2 < size) {
            int keyAt = this.mGrantedUriPermissions.keyAt(i2);
            ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.valueAt(i2);
            if (i == -1 || i == UserHandle.getUserId(keyAt)) {
                Iterator it = arrayMap.values().iterator();
                while (it.hasNext()) {
                    UriPermission uriPermission = (UriPermission) it.next();
                    if (str == null || ((!z2 && uriPermission.sourcePkg.equals(str)) || uriPermission.targetPkg.equals(str))) {
                        if (!"downloads".equals(uriPermission.uri.uri.getAuthority()) || z) {
                            z3 |= uriPermission.revokeModes(z ? -1 : -65, true);
                            if (uriPermission.modeFlags == 0) {
                                it.remove();
                                addToHistoryLocked(PackageManagerShellCommandDataLoader.STDIN_PATH + uriPermission.targetUid + "[" + arrayMap.size() + "] " + uriPermission.uri);
                            }
                        }
                    }
                }
                if (arrayMap.isEmpty()) {
                    this.mGrantedUriPermissions.remove(keyAt);
                    size--;
                    i2--;
                }
            }
            i2++;
        }
        if (z3) {
            schedulePersistUriGrants();
        }
    }

    public final void revokeUriPermissionLocked(String str, int i, GrantUri grantUri, int i2, boolean z) {
        int i3 = grantUri.sourceUserId;
        if (!z) {
            ArrayMap arrayMap = (ArrayMap) this.mGrantedUriPermissions.get(i);
            if (arrayMap != null) {
                boolean z2 = false;
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    UriPermission uriPermission = (UriPermission) arrayMap.valueAt(size);
                    if (str == null || str.equals(uriPermission.targetPkg)) {
                        GrantUri grantUri2 = uriPermission.uri;
                        if (grantUri2.sourceUserId == i3 && grantUri2.uri.isPathPrefixMatch(grantUri.uri)) {
                            z2 |= uriPermission.revokeModes(i2 | 64, false);
                            if (uriPermission.modeFlags == 0) {
                                arrayMap.removeAt(size);
                                addToHistoryLocked(PackageManagerShellCommandDataLoader.STDIN_PATH + uriPermission.targetUid + "(" + arrayMap.size() + ") " + uriPermission.uri);
                            }
                        }
                    }
                }
                if (arrayMap.isEmpty()) {
                    this.mGrantedUriPermissions.remove(i);
                }
                if (z2) {
                    schedulePersistUriGrants();
                    return;
                }
                return;
            }
            return;
        }
        boolean z3 = false;
        for (int size2 = this.mGrantedUriPermissions.size() - 1; size2 >= 0; size2--) {
            this.mGrantedUriPermissions.keyAt(size2);
            ArrayMap arrayMap2 = (ArrayMap) this.mGrantedUriPermissions.valueAt(size2);
            for (int size3 = arrayMap2.size() - 1; size3 >= 0; size3--) {
                UriPermission uriPermission2 = (UriPermission) arrayMap2.valueAt(size3);
                if (str == null || str.equals(uriPermission2.targetPkg)) {
                    GrantUri grantUri3 = uriPermission2.uri;
                    if (grantUri3.sourceUserId == i3 && grantUri3.uri.isPathPrefixMatch(grantUri.uri)) {
                        z3 |= uriPermission2.revokeModes(i2 | 64, str == null);
                        if (uriPermission2.modeFlags == 0) {
                            arrayMap2.removeAt(size3);
                            addToHistoryLocked(PackageManagerShellCommandDataLoader.STDIN_PATH + uriPermission2.targetUid + "\"" + arrayMap2.size() + "\" " + uriPermission2.uri);
                        }
                    }
                }
            }
            if (arrayMap2.isEmpty()) {
                this.mGrantedUriPermissions.removeAt(size2);
            }
        }
        if (z3) {
            schedulePersistUriGrants();
        }
    }

    public final void schedulePersistUriGrants() {
        if (this.mH.hasMessages(1)) {
            return;
        }
        H h = this.mH;
        h.sendMessageDelayed(h.obtainMessage(1), 10000L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0078, code lost:
    
        r3 = r2.takePersistableModes(r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void takePersistableUriPermission(android.net.Uri r8, int r9, java.lang.String r10, int r11) {
        /*
            r7 = this;
            java.lang.String r0 = "No persistable permission grants found for UID "
            if (r10 == 0) goto L17
            android.app.ActivityManagerInternal r1 = r7.mAmInternal
            java.lang.String r2 = "android.permission.FORCE_PERSISTABLE_URI_PERMISSIONS"
            java.lang.String r3 = "takePersistableUriPermission"
            r1.enforceCallingPermission(r2, r3)
            android.content.pm.PackageManagerInternal r1 = r7.mPmInternal
            r2 = 0
            int r10 = r1.getPackageUid(r10, r2, r11)
            goto L21
        L17:
            java.lang.String r10 = "takePersistableUriPermission"
            enforceNotIsolatedCaller(r10)
            int r10 = android.os.Binder.getCallingUid()
        L21:
            r1 = 3
            com.android.internal.util.Preconditions.checkFlagsArgument(r9, r1)
            java.lang.Object r1 = r7.mLock
            monitor-enter(r1)
            com.android.server.uri.GrantUri r2 = new com.android.server.uri.GrantUri     // Catch: java.lang.Throwable -> L47
            r3 = 0
            r2.<init>(r11, r3, r8)     // Catch: java.lang.Throwable -> L47
            com.android.server.uri.UriPermission r2 = r7.findUriPermissionLocked(r10, r2)     // Catch: java.lang.Throwable -> L47
            com.android.server.uri.GrantUri r4 = new com.android.server.uri.GrantUri     // Catch: java.lang.Throwable -> L47
            r5 = 128(0x80, float:1.794E-43)
            r4.<init>(r11, r5, r8)     // Catch: java.lang.Throwable -> L47
            com.android.server.uri.UriPermission r11 = r7.findUriPermissionLocked(r10, r4)     // Catch: java.lang.Throwable -> L47
            r4 = 1
            if (r2 == 0) goto L49
            int r5 = r2.persistableModeFlags     // Catch: java.lang.Throwable -> L47
            r5 = r5 & r9
            if (r5 != r9) goto L49
            r5 = r4
            goto L4a
        L47:
            r7 = move-exception
            goto L8f
        L49:
            r5 = r3
        L4a:
            if (r11 == 0) goto L52
            int r6 = r11.persistableModeFlags     // Catch: java.lang.Throwable -> L47
            r6 = r6 & r9
            if (r6 != r9) goto L52
            goto L53
        L52:
            r4 = r3
        L53:
            if (r5 != 0) goto L76
            if (r4 == 0) goto L58
            goto L76
        L58:
            java.lang.SecurityException r7 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L47
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L47
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L47
            r9.append(r10)     // Catch: java.lang.Throwable -> L47
            java.lang.String r10 = " and Uri "
            r9.append(r10)     // Catch: java.lang.Throwable -> L47
            java.lang.String r8 = r8.toSafeString()     // Catch: java.lang.Throwable -> L47
            r9.append(r8)     // Catch: java.lang.Throwable -> L47
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Throwable -> L47
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L47
            throw r7     // Catch: java.lang.Throwable -> L47
        L76:
            if (r5 == 0) goto L7c
            boolean r3 = r2.takePersistableModes(r9)     // Catch: java.lang.Throwable -> L47
        L7c:
            if (r4 == 0) goto L83
            boolean r8 = r11.takePersistableModes(r9)     // Catch: java.lang.Throwable -> L47
            r3 = r3 | r8
        L83:
            boolean r8 = r7.maybePrunePersistedUriGrantsLocked(r10)     // Catch: java.lang.Throwable -> L47
            r8 = r8 | r3
            if (r8 == 0) goto L8d
            r7.schedulePersistUriGrants()     // Catch: java.lang.Throwable -> L47
        L8d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L47
            return
        L8f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L47
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.uri.UriGrantsManagerService.takePersistableUriPermission(android.net.Uri, int, java.lang.String, int):void");
    }
}
