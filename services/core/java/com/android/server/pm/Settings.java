package com.android.server.pm;

import android.app.compat.ChangeIdStateCache;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackagePartitions;
import android.content.pm.PermissionInfo;
import android.content.pm.UserInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.overlay.OverlayPaths;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.PatternMatcher;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.Xml;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedComponent;
import com.android.internal.pm.pkg.component.ParsedIntentInfo;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.JournaledFile;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.permission.persistence.RuntimePermissionsPersistence;
import com.android.permission.persistence.RuntimePermissionsState;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.IntentResolver;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.pm.ResilientAtomicFile;
import com.android.server.pm.Settings;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SuspendParams;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.utils.Slogf;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watched;
import com.android.server.utils.WatchedArrayList;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.utils.Watcher;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.PMRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Settings implements Watchable, Snappable, ResilientAtomicFile.ReadEventLogger {
    public static final Object[] FLAG_DUMP_SPEC;
    public static final Object[] PRIVATE_FLAG_DUMP_SPEC;
    public final AppIdSettingMap mAppIds;
    public final File mBackupStoppedPackagesFilename;
    public final WatchedSparseArray mBlockUninstallPackages;
    public final WatchedSparseArray mCrossProfileIntentResolvers;
    public final SnapshotCache mCrossProfileIntentResolversSnapshot;

    @Watched
    final WatchedArrayMap mDisabledSysPackages;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final Handler mHandler;
    public final WatchedArraySet mInstallerPackages;
    public final SnapshotCache mInstallerPackagesSnapshot;
    public final WatchedArrayMap mKernelMapping;
    public final File mKernelMappingFilename;
    public final SnapshotCache mKernelMappingSnapshot;
    public final KeySetManagerService mKeySetManagerService;
    public final PackageManagerTracedLock mLock;
    public final WatchedSparseIntArray mNextAppLinkGeneration;
    public final AnonymousClass1 mObserver;
    public final File mPackageListFilename;
    public final Object mPackageRestrictionsLock;

    @Watched
    final WatchedArrayMap mPackages;
    public final SnapshotCache mPackagesSnapshot;
    public final SparseIntArray mPendingAsyncPackageRestrictionsWrites;
    public final WatchedSparseArray mPendingDefaultBrowser;
    public final WatchedArrayList mPendingPackages;
    public final SnapshotCache mPendingPackagesSnapshot;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionDataProvider;
    public final LegacyPermissionSettings mPermissions;
    public final WatchedSparseArray mPersistentPreferredActivities;
    public final SnapshotCache mPersistentPreferredActivitiesSnapshot;
    public final WatchedSparseArray mPreferredActivities;
    public final SnapshotCache mPreferredActivitiesSnapshot;
    public final File mPreviousSettingsFilename;
    public final StringBuilder mReadMessages;
    public final WatchedArrayMap mRenamedPackages;
    public final RuntimePermissionPersistence mRuntimePermissionsPersistence;
    public final File mSettingsFilename;
    public final File mSettingsReserveCopyFilename;
    public final WatchedArrayMap mSharedUsers;
    public final SnapshotCache mSnapshot;
    public final File mStoppedPackagesFilename;
    public final File mSystemDir;
    public VerifierDeviceIdentity mVerifierDeviceIdentity;
    public final WatchedArrayMap mVersion;
    public final WatchableImpl mWatchable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.Settings$2, reason: invalid class name */
    public final class AnonymousClass2 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            Settings settings = new Settings((Settings) this.mSource);
            settings.mWatchable.seal();
            return settings;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KernelPackageState {
        public int[] excludedUserIds;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeySetToValueMap implements Map {
        public final Set mKeySet;
        public final Object mValue;

        public KeySetToValueMap(Set set, Object obj) {
            this.mKeySet = set;
            this.mValue = obj;
        }

        @Override // java.util.Map
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public final boolean containsKey(Object obj) {
            return this.mKeySet.contains(obj);
        }

        @Override // java.util.Map
        public final boolean containsValue(Object obj) {
            return this.mValue == obj;
        }

        @Override // java.util.Map
        public final Set entrySet() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public final Object get(Object obj) {
            return this.mValue;
        }

        @Override // java.util.Map
        public final boolean isEmpty() {
            return this.mKeySet.isEmpty();
        }

        @Override // java.util.Map
        public final Set keySet() {
            return this.mKeySet;
        }

        @Override // java.util.Map
        public final Object put(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public final void putAll(Map map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public final Object remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public final int size() {
            return this.mKeySet.size();
        }

        @Override // java.util.Map
        public final Collection values() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RuntimePermissionPersistence {
        public static final Random sRandom = new Random();
        public String mExtendedFingerprint;
        public final Consumer mInvokeWriteUserStateAsyncCallback;
        public final RuntimePermissionsPersistence mPersistence;
        public final Object mPersistenceLock = new Object();
        public final MyHandler mAsyncHandler = new MyHandler(this, 0);
        public final MyHandler mPersistenceHandler = new MyHandler(this, 1);
        public final Object mLock = new Object();
        public long mLastWriteDoneTimeMillis = 0;
        public final SparseBooleanArray mWriteScheduled = new SparseBooleanArray();
        public final SparseLongArray mLastNotWrittenMutationTimesMillis = new SparseLongArray();
        public final AtomicBoolean mIsLegacyPermissionStateStale = new AtomicBoolean(false);
        public final SparseIntArray mVersions = new SparseIntArray();
        public final SparseArray mFingerprints = new SparseArray();
        public final SparseBooleanArray mPermissionUpgradeNeeded = new SparseBooleanArray();
        public final SparseArray mPendingStatesToWrite = new SparseArray();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MyHandler extends Handler {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ RuntimePermissionPersistence this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public MyHandler(RuntimePermissionPersistence runtimePermissionPersistence, int i) {
                super(BackgroundThread.getHandler().getLooper());
                this.$r8$classId = i;
                switch (i) {
                    case 1:
                        this.this$0 = runtimePermissionPersistence;
                        super(BackgroundThread.getHandler().getLooper());
                        break;
                    default:
                        this.this$0 = runtimePermissionPersistence;
                        break;
                }
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                switch (this.$r8$classId) {
                    case 0:
                        int i = message.what;
                        Runnable runnable = (Runnable) message.obj;
                        long uptimeMillis = SystemClock.uptimeMillis();
                        RuntimePermissionPersistence runtimePermissionPersistence = this.this$0;
                        long j = uptimeMillis - runtimePermissionPersistence.mLastWriteDoneTimeMillis;
                        if (j >= 500) {
                            runtimePermissionPersistence.mInvokeWriteUserStateAsyncCallback.accept(Integer.valueOf(i));
                            if (runnable != null) {
                                runnable.run();
                                break;
                            }
                        } else {
                            MyHandler myHandler = runtimePermissionPersistence.mAsyncHandler;
                            myHandler.sendMessageDelayed(myHandler.obtainMessage(i), 500 - j);
                            break;
                        }
                        break;
                    default:
                        this.this$0.writePendingStates();
                        break;
                }
            }
        }

        public RuntimePermissionPersistence(RuntimePermissionsPersistence runtimePermissionsPersistence, AnonymousClass3 anonymousClass3) {
            this.mPersistence = runtimePermissionsPersistence;
            this.mInvokeWriteUserStateAsyncCallback = anonymousClass3;
        }

        public static Map getPackagePermissions(int i, WatchedArrayMap watchedArrayMap) {
            ArrayMap arrayMap = new ArrayMap();
            int size = watchedArrayMap.mStorage.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = (String) watchedArrayMap.mStorage.keyAt(i2);
                PackageStateInternal packageStateInternal = (PackageStateInternal) watchedArrayMap.mStorage.valueAt(i2);
                if (!packageStateInternal.hasSharedUser()) {
                    List permissionsFromPermissionsState = getPermissionsFromPermissionsState(packageStateInternal.getLegacyPermissionState(), i);
                    if (!((ArrayList) permissionsFromPermissionsState).isEmpty() || packageStateInternal.isInstallPermissionsFixed()) {
                        arrayMap.put(str, permissionsFromPermissionsState);
                    }
                }
            }
            return arrayMap;
        }

        public static List getPermissionsFromPermissionsState(LegacyPermissionState legacyPermissionState, int i) {
            Collection<LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(i);
            ArrayList arrayList = new ArrayList();
            for (LegacyPermissionState.PermissionState permissionState : permissionStates) {
                arrayList.add(new RuntimePermissionsState.PermissionState(permissionState.mName, permissionState.mGranted, permissionState.mFlags));
            }
            return arrayList;
        }

        public static Map getShareUsersPermissions(int i, WatchedArrayMap watchedArrayMap) {
            ArrayMap arrayMap = new ArrayMap();
            int size = watchedArrayMap.mStorage.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayMap.put((String) watchedArrayMap.mStorage.keyAt(i2), getPermissionsFromPermissionsState(((SharedUserSetting) watchedArrayMap.mStorage.valueAt(i2)).mLegacyPermissionsState, i));
            }
            return arrayMap;
        }

        public static void readPermissionsState(List list, LegacyPermissionState legacyPermissionState, int i) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                RuntimePermissionsState.PermissionState permissionState = (RuntimePermissionsState.PermissionState) list.get(i2);
                legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), true, permissionState.isGranted(), permissionState.getFlags()), i);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x001d, code lost:
        
            if (r1 != 4) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0020, code lost:
        
            r1 = r8.getName();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x002b, code lost:
        
            if (r1.hashCode() == 3242771) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0034, code lost:
        
            if (r1.equals("item") == false) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0036, code lost:
        
            r9.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(r8.getAttributeValue((java.lang.String) null, "name"), true, r8.getAttributeBoolean((java.lang.String) null, "granted", true), r8.getAttributeIntHex((java.lang.String) null, "flags", 0)), r10);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void parseLegacyPermissionsLPr(com.android.modules.utils.TypedXmlPullParser r8, com.android.server.pm.permission.LegacyPermissionState r9, int r10) {
            /*
                r7 = this;
                java.lang.Object r7 = r7.mLock
                monitor-enter(r7)
                int r0 = r8.getDepth()     // Catch: java.lang.Throwable -> L18
            L7:
                int r1 = r8.next()     // Catch: java.lang.Throwable -> L18
                r2 = 1
                if (r1 == r2) goto L54
                r3 = 3
                if (r1 != r3) goto L1a
                int r4 = r8.getDepth()     // Catch: java.lang.Throwable -> L18
                if (r4 <= r0) goto L54
                goto L1a
            L18:
                r8 = move-exception
                goto L56
            L1a:
                if (r1 == r3) goto L7
                r3 = 4
                if (r1 != r3) goto L20
                goto L7
            L20:
                java.lang.String r1 = r8.getName()     // Catch: java.lang.Throwable -> L18
                int r3 = r1.hashCode()     // Catch: java.lang.Throwable -> L18
                r4 = 3242771(0x317b13, float:4.54409E-39)
                if (r3 == r4) goto L2e
                goto L7
            L2e:
                java.lang.String r3 = "item"
                boolean r1 = r1.equals(r3)     // Catch: java.lang.Throwable -> L18
                if (r1 == 0) goto L7
                java.lang.String r1 = "name"
                r3 = 0
                java.lang.String r1 = r8.getAttributeValue(r3, r1)     // Catch: java.lang.Throwable -> L18
                java.lang.String r4 = "granted"
                boolean r4 = r8.getAttributeBoolean(r3, r4, r2)     // Catch: java.lang.Throwable -> L18
                java.lang.String r5 = "flags"
                r6 = 0
                int r3 = r8.getAttributeIntHex(r3, r5, r6)     // Catch: java.lang.Throwable -> L18
                com.android.server.pm.permission.LegacyPermissionState$PermissionState r5 = new com.android.server.pm.permission.LegacyPermissionState$PermissionState     // Catch: java.lang.Throwable -> L18
                r5.<init>(r1, r2, r4, r3)     // Catch: java.lang.Throwable -> L18
                r9.putPermissionState(r5, r10)     // Catch: java.lang.Throwable -> L18
                goto L7
            L54:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L18
                return
            L56:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L18
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.RuntimePermissionPersistence.parseLegacyPermissionsLPr(com.android.modules.utils.TypedXmlPullParser, com.android.server.pm.permission.LegacyPermissionState, int):void");
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x001e, code lost:
        
            if (r2 != 4) goto L58;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0021, code lost:
        
            r2 = r9.getName();
            r4 = r2.hashCode();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x002e, code lost:
        
            if (r4 == 111052) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0033, code lost:
        
            if (r4 == 160289295) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0038, code lost:
        
            if (r4 == 485578803) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x005c, code lost:
        
            r2 = 65535;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x005e, code lost:
        
            if (r2 == 0) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00cd, code lost:
        
            r8.mVersions.put(r10, r9.getAttributeInt((java.lang.String) null, "version", -1));
            r8.mFingerprints.put(r10, r9.getAttributeValue((java.lang.String) null, "fingerprint"));
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0060, code lost:
        
            if (r2 == 1) goto L61;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0098, code lost:
        
            r2 = r9.getAttributeValue((java.lang.String) null, "name");
            r3 = (com.android.server.pm.pkg.PackageStateInternal) r11.mStorage.get(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00a7, code lost:
        
            if (r3 != null) goto L67;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00c4, code lost:
        
            parseLegacyPermissionsLPr(r9, r3.getLegacyPermissionState(), r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00a9, code lost:
        
            android.util.Slog.w("PackageManager", "Unknown package:" + r2);
            com.android.internal.util.XmlUtils.skipCurrentTag(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0062, code lost:
        
            if (r2 == 2) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0065, code lost:
        
            r2 = r9.getAttributeValue((java.lang.String) null, "name");
            r3 = (com.android.server.pm.SharedUserSetting) r12.mStorage.get(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x0074, code lost:
        
            if (r3 != null) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0091, code lost:
        
            parseLegacyPermissionsLPr(r9, r3.mLegacyPermissionsState, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0076, code lost:
        
            android.util.Slog.w("PackageManager", "Unknown shared user:" + r2);
            com.android.internal.util.XmlUtils.skipCurrentTag(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x0042, code lost:
        
            if (r2.equals("shared-user") == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x0044, code lost:
        
            r2 = 2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x004d, code lost:
        
            if (r2.equals("runtime-permissions") == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x004f, code lost:
        
            r2 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x0058, code lost:
        
            if (r2.equals("pkg") == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x005a, code lost:
        
            r2 = 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void parseLegacyRuntimePermissions(com.android.modules.utils.TypedXmlPullParser r9, int r10, com.android.server.utils.WatchedArrayMap r11, com.android.server.utils.WatchedArrayMap r12) {
            /*
                r8 = this;
                java.lang.Object r0 = r8.mLock
                monitor-enter(r0)
                int r1 = r9.getDepth()     // Catch: java.lang.Throwable -> L18
            L7:
                int r2 = r9.next()     // Catch: java.lang.Throwable -> L18
                r3 = 1
                if (r2 == r3) goto Le6
                r4 = 3
                if (r2 != r4) goto L1b
                int r5 = r9.getDepth()     // Catch: java.lang.Throwable -> L18
                if (r5 <= r1) goto Le6
                goto L1b
            L18:
                r8 = move-exception
                goto Le8
            L1b:
                if (r2 == r4) goto L7
                r4 = 4
                if (r2 != r4) goto L21
                goto L7
            L21:
                java.lang.String r2 = r9.getName()     // Catch: java.lang.Throwable -> L18
                int r4 = r2.hashCode()     // Catch: java.lang.Throwable -> L18
                r5 = 111052(0x1b1cc, float:1.55617E-40)
                r6 = 2
                r7 = -1
                if (r4 == r5) goto L51
                r5 = 160289295(0x98dd20f, float:3.4142053E-33)
                if (r4 == r5) goto L46
                r5 = 485578803(0x1cf15833, float:1.5970841E-21)
                if (r4 == r5) goto L3b
                goto L5c
            L3b:
                java.lang.String r4 = "shared-user"
                boolean r2 = r2.equals(r4)     // Catch: java.lang.Throwable -> L18
                if (r2 == 0) goto L5c
                r2 = r6
                goto L5d
            L46:
                java.lang.String r4 = "runtime-permissions"
                boolean r2 = r2.equals(r4)     // Catch: java.lang.Throwable -> L18
                if (r2 == 0) goto L5c
                r2 = 0
                goto L5d
            L51:
                java.lang.String r4 = "pkg"
                boolean r2 = r2.equals(r4)     // Catch: java.lang.Throwable -> L18
                if (r2 == 0) goto L5c
                r2 = r3
                goto L5d
            L5c:
                r2 = r7
            L5d:
                r4 = 0
                if (r2 == 0) goto Lcd
                if (r2 == r3) goto L98
                if (r2 == r6) goto L65
                goto L7
            L65:
                java.lang.String r2 = "name"
                java.lang.String r2 = r9.getAttributeValue(r4, r2)     // Catch: java.lang.Throwable -> L18
                android.util.ArrayMap r3 = r12.mStorage     // Catch: java.lang.Throwable -> L18
                java.lang.Object r3 = r3.get(r2)     // Catch: java.lang.Throwable -> L18
                com.android.server.pm.SharedUserSetting r3 = (com.android.server.pm.SharedUserSetting) r3     // Catch: java.lang.Throwable -> L18
                if (r3 != 0) goto L91
                java.lang.String r3 = "PackageManager"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L18
                r4.<init>()     // Catch: java.lang.Throwable -> L18
                java.lang.String r5 = "Unknown shared user:"
                r4.append(r5)     // Catch: java.lang.Throwable -> L18
                r4.append(r2)     // Catch: java.lang.Throwable -> L18
                java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L18
                android.util.Slog.w(r3, r2)     // Catch: java.lang.Throwable -> L18
                com.android.internal.util.XmlUtils.skipCurrentTag(r9)     // Catch: java.lang.Throwable -> L18
                goto L7
            L91:
                com.android.server.pm.permission.LegacyPermissionState r2 = r3.mLegacyPermissionsState     // Catch: java.lang.Throwable -> L18
                r8.parseLegacyPermissionsLPr(r9, r2, r10)     // Catch: java.lang.Throwable -> L18
                goto L7
            L98:
                java.lang.String r2 = "name"
                java.lang.String r2 = r9.getAttributeValue(r4, r2)     // Catch: java.lang.Throwable -> L18
                android.util.ArrayMap r3 = r11.mStorage     // Catch: java.lang.Throwable -> L18
                java.lang.Object r3 = r3.get(r2)     // Catch: java.lang.Throwable -> L18
                com.android.server.pm.pkg.PackageStateInternal r3 = (com.android.server.pm.pkg.PackageStateInternal) r3     // Catch: java.lang.Throwable -> L18
                if (r3 != 0) goto Lc4
                java.lang.String r3 = "PackageManager"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L18
                r4.<init>()     // Catch: java.lang.Throwable -> L18
                java.lang.String r5 = "Unknown package:"
                r4.append(r5)     // Catch: java.lang.Throwable -> L18
                r4.append(r2)     // Catch: java.lang.Throwable -> L18
                java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L18
                android.util.Slog.w(r3, r2)     // Catch: java.lang.Throwable -> L18
                com.android.internal.util.XmlUtils.skipCurrentTag(r9)     // Catch: java.lang.Throwable -> L18
                goto L7
            Lc4:
                com.android.server.pm.permission.LegacyPermissionState r2 = r3.getLegacyPermissionState()     // Catch: java.lang.Throwable -> L18
                r8.parseLegacyPermissionsLPr(r9, r2, r10)     // Catch: java.lang.Throwable -> L18
                goto L7
            Lcd:
                java.lang.String r2 = "version"
                int r2 = r9.getAttributeInt(r4, r2, r7)     // Catch: java.lang.Throwable -> L18
                android.util.SparseIntArray r3 = r8.mVersions     // Catch: java.lang.Throwable -> L18
                r3.put(r10, r2)     // Catch: java.lang.Throwable -> L18
                java.lang.String r2 = "fingerprint"
                java.lang.String r2 = r9.getAttributeValue(r4, r2)     // Catch: java.lang.Throwable -> L18
                android.util.SparseArray r3 = r8.mFingerprints     // Catch: java.lang.Throwable -> L18
                r3.put(r10, r2)     // Catch: java.lang.Throwable -> L18
                goto L7
            Le6:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L18
                return
            Le8:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L18
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.RuntimePermissionPersistence.parseLegacyRuntimePermissions(com.android.modules.utils.TypedXmlPullParser, int, com.android.server.utils.WatchedArrayMap, com.android.server.utils.WatchedArrayMap):void");
        }

        public final void readStateForUserSync(int i, VersionInfo versionInfo, WatchedArrayMap watchedArrayMap, WatchedArrayMap watchedArrayMap2, File file) {
            RuntimePermissionsState readForUser;
            synchronized (this.mPersistenceLock) {
                readForUser = this.mPersistence.readForUser(UserHandle.of(i));
            }
            if (readForUser == null) {
                synchronized (this.mLock) {
                    if (file.exists()) {
                        try {
                            FileInputStream openRead = new AtomicFile(file).openRead();
                            try {
                                try {
                                    parseLegacyRuntimePermissions(Xml.resolvePullParser(openRead), i, watchedArrayMap, watchedArrayMap2);
                                } catch (IOException | XmlPullParserException e) {
                                    throw new IllegalStateException("Failed parsing permissions file: " + file, e);
                                }
                            } finally {
                                IoUtils.closeQuietly(openRead);
                            }
                        } catch (FileNotFoundException unused) {
                            Slog.i("PackageManager", "No permissions state");
                        }
                    }
                }
                writeStateForUserAsync(i);
                return;
            }
            synchronized (this.mLock) {
                try {
                    int version = readForUser.getVersion();
                    if (version == -1) {
                        version = -1;
                    }
                    this.mVersions.put(i, version);
                    this.mFingerprints.put(i, readForUser.getFingerprint());
                    boolean z = versionInfo.sdkVersion < 30;
                    Map packagePermissions = readForUser.getPackagePermissions();
                    int size = watchedArrayMap.mStorage.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        String str = (String) watchedArrayMap.mStorage.keyAt(i2);
                        PackageSetting packageSetting = (PackageSetting) watchedArrayMap.mStorage.valueAt(i2);
                        List list = (List) packagePermissions.get(str);
                        if (list != null) {
                            readPermissionsState(list, packageSetting.mLegacyPermissionsState, i);
                            packageSetting.setBoolean(1, true);
                        } else if (!packageSetting.hasSharedUser() && !z) {
                            Slogf.w("PackageSettings", "Missing permission state for package %s on user %d", str, Integer.valueOf(i));
                            packageSetting.mLegacyPermissionsState.setMissing(i, true);
                        }
                    }
                    Map sharedUserPermissions = readForUser.getSharedUserPermissions();
                    int size2 = watchedArrayMap2.mStorage.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        String str2 = (String) watchedArrayMap2.mStorage.keyAt(i3);
                        SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap2.mStorage.valueAt(i3);
                        List list2 = (List) sharedUserPermissions.get(str2);
                        if (list2 != null) {
                            readPermissionsState(list2, sharedUserSetting.mLegacyPermissionsState, i);
                        } else if (!z) {
                            Slog.w("PackageSettings", "Missing permission state for shared user: " + str2);
                            sharedUserSetting.mLegacyPermissionsState.setMissing(i, true);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void writePendingStates() {
            int keyAt;
            RuntimePermissionsState runtimePermissionsState;
            while (true) {
                synchronized (this.mLock) {
                    try {
                        if (this.mPendingStatesToWrite.size() == 0) {
                            return;
                        }
                        keyAt = this.mPendingStatesToWrite.keyAt(0);
                        runtimePermissionsState = (RuntimePermissionsState) this.mPendingStatesToWrite.valueAt(0);
                        this.mPendingStatesToWrite.removeAt(0);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                synchronized (this.mPersistenceLock) {
                    this.mPersistence.writeForUser(runtimePermissionsState, UserHandle.of(keyAt));
                }
            }
        }

        public final void writeStateForUser(final int i, final PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl, final WatchedArrayMap watchedArrayMap, final WatchedArrayMap watchedArrayMap2, final Handler handler, final PackageManagerTracedLock packageManagerTracedLock, final boolean z) {
            Trace.traceBegin(262144L, "writePerm");
            StringBuilder sb = new StringBuilder("++ writeStateForUserSyncLPr(");
            sb.append(i);
            BootReceiver$$ExternalSyntheticOutline0.m(sb, ")", "PackageSettings");
            synchronized (this.mLock) {
                this.mAsyncHandler.removeMessages(i);
                this.mWriteScheduled.delete(i);
            }
            Runnable runnable = new Runnable() { // from class: com.android.server.pm.Settings$RuntimePermissionPersistence$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Map packagePermissions;
                    Map shareUsersPermissions;
                    Settings.RuntimePermissionPersistence runtimePermissionPersistence = Settings.RuntimePermissionPersistence.this;
                    PackageManagerTracedLock packageManagerTracedLock2 = packageManagerTracedLock;
                    boolean z2 = z;
                    PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl2 = permissionManagerServiceInternalImpl;
                    int i2 = i;
                    WatchedArrayMap watchedArrayMap3 = watchedArrayMap;
                    WatchedArrayMap watchedArrayMap4 = watchedArrayMap2;
                    Handler handler2 = handler;
                    boolean andSet = runtimePermissionPersistence.mIsLegacyPermissionStateStale.getAndSet(false);
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock2) {
                        if (z2 || andSet) {
                            try {
                                PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionStateTEMP();
                            } catch (Throwable th) {
                                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                throw th;
                            }
                        }
                        packagePermissions = Settings.RuntimePermissionPersistence.getPackagePermissions(i2, watchedArrayMap3);
                        shareUsersPermissions = Settings.RuntimePermissionPersistence.getShareUsersPermissions(i2, watchedArrayMap4);
                    }
                    synchronized (runtimePermissionPersistence.mLock) {
                        runtimePermissionPersistence.mPendingStatesToWrite.put(i2, new RuntimePermissionsState(runtimePermissionPersistence.mVersions.get(i2, 0), (String) runtimePermissionPersistence.mFingerprints.get(i2), packagePermissions, shareUsersPermissions));
                    }
                    if (handler2 != null) {
                        runtimePermissionPersistence.mPersistenceHandler.obtainMessage(i2).sendToTarget();
                    } else {
                        runtimePermissionPersistence.writePendingStates();
                    }
                }
            };
            if (handler != null) {
                handler.post(runnable);
            } else {
                runnable.run();
            }
            this.mLastWriteDoneTimeMillis = SystemClock.uptimeMillis();
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "-- writeStateForUserSyncLPr(", ")", "PackageSettings");
            Trace.traceEnd(262144L);
        }

        public final void writeStateForUserAsync(int i) {
            this.mIsLegacyPermissionStateStale.set(true);
            synchronized (this.mLock) {
                try {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long nextDouble = ((long) ((sRandom.nextDouble() * 600.0d) - 300.0d)) + 1000;
                    if (this.mWriteScheduled.get(i)) {
                        this.mAsyncHandler.removeMessages(i);
                        long j = this.mLastNotWrittenMutationTimesMillis.get(i);
                        if (uptimeMillis - j >= 2000) {
                            this.mAsyncHandler.obtainMessage(i).sendToTarget();
                        } else {
                            this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), Math.min(nextDouble, Math.max((j + 2000) - uptimeMillis, 0L)));
                        }
                    } else {
                        this.mLastNotWrittenMutationTimesMillis.put(i, uptimeMillis);
                        this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), nextDouble);
                        this.mWriteScheduled.put(i, true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VersionInfo {
        public String buildFingerprint;
        public int databaseVersion;
        public String fingerprint;
        public int sdkVersion;

        public final void forceCurrent() {
            this.sdkVersion = Build.VERSION.SDK_INT;
            this.databaseVersion = 3;
            this.buildFingerprint = Build.FINGERPRINT;
            this.fingerprint = PackagePartitions.FINGERPRINT;
        }
    }

    static {
        Integer valueOf = Integer.valueOf(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
        Integer valueOf2 = Integer.valueOf(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
        FLAG_DUMP_SPEC = new Object[]{1, "SYSTEM", 2, "DEBUGGABLE", 4, "HAS_CODE", 8, "PERSISTENT", 16, "FACTORY_TEST", 32, "ALLOW_TASK_REPARENTING", 64, "ALLOW_CLEAR_USER_DATA", 128, "UPDATED_SYSTEM_APP", 256, "TEST_ONLY", valueOf, "VM_SAFE_MODE", 32768, "ALLOW_BACKUP", valueOf2, "KILL_AFTER_RESTORE", 131072, "RESTORE_ANY_VERSION", 262144, "EXTERNAL_STORAGE", 1048576, "LARGE_HEAP"};
        PRIVATE_FLAG_DUMP_SPEC = new Object[]{1024, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE", 4096, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION", 2048, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE", 134217728, "ALLOW_AUDIO_PLAYBACK_CAPTURE", 536870912, "PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE", 8192, "BACKUP_IN_FOREGROUND", 2, "CANT_SAVE_STATE", 32, "DEFAULT_TO_DEVICE_PROTECTED_STORAGE", 64, "DIRECT_BOOT_AWARE", 16, "HAS_DOMAIN_URLS", 1, "HIDDEN", 128, "EPHEMERAL", 32768, "ISOLATED_SPLIT_LOADING", 131072, "OEM", 256, "PARTIALLY_DIRECT_BOOT_AWARE", 8, "PRIVILEGED", 512, "REQUIRED_FOR_SYSTEM_USER", valueOf, "STATIC_SHARED_LIBRARY", 262144, "VENDOR", 524288, "PRODUCT", 2097152, "SYSTEM_EXT", valueOf2, "VIRTUAL_PRELOAD", 1073741824, "ODM", Integer.MIN_VALUE, "PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING", 16777216, "PRIVATE_FLAG_HAS_FRAGILE_USER_DATA"};
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.server.pm.Settings$1] */
    public Settings(Settings settings) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        this.mDisabledSysPackages = watchedArrayMap;
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mBlockUninstallPackages = watchedSparseArray;
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
        this.mVersion = watchedArrayMap2;
        WatchedArrayMap watchedArrayMap3 = new WatchedArrayMap(0);
        this.mSharedUsers = watchedArrayMap3;
        WatchedArrayMap watchedArrayMap4 = new WatchedArrayMap(0);
        this.mRenamedPackages = watchedArrayMap4;
        this.mPendingDefaultBrowser = new WatchedSparseArray();
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray();
        this.mNextAppLinkGeneration = watchedSparseIntArray;
        this.mReadMessages = new StringBuilder();
        this.mObserver = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        WatchedArrayMap watchedArrayMap5 = (WatchedArrayMap) settings.mPackagesSnapshot.snapshot();
        this.mPackages = watchedArrayMap5;
        this.mPackagesSnapshot = new SnapshotCache.Auto();
        this.mKernelMapping = (WatchedArrayMap) settings.mKernelMappingSnapshot.snapshot();
        this.mKernelMappingSnapshot = new SnapshotCache.Auto();
        this.mInstallerPackages = (WatchedArraySet) settings.mInstallerPackagesSnapshot.snapshot();
        this.mInstallerPackagesSnapshot = new SnapshotCache.Auto();
        this.mKeySetManagerService = new KeySetManagerService(settings.mKeySetManagerService, watchedArrayMap5);
        this.mHandler = null;
        this.mLock = null;
        this.mRuntimePermissionsPersistence = settings.mRuntimePermissionsPersistence;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = settings.mDomainVerificationManager;
        WatchedArrayMap.snapshot(watchedArrayMap, settings.mDisabledSysPackages);
        WatchedSparseArray.snapshot(watchedSparseArray, settings.mBlockUninstallPackages);
        watchedArrayMap2.putAll(settings.mVersion);
        this.mVerifierDeviceIdentity = settings.mVerifierDeviceIdentity;
        this.mPreferredActivities = (WatchedSparseArray) settings.mPreferredActivitiesSnapshot.snapshot();
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Auto();
        this.mPersistentPreferredActivities = (WatchedSparseArray) settings.mPersistentPreferredActivitiesSnapshot.snapshot();
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Auto();
        this.mCrossProfileIntentResolvers = (WatchedSparseArray) settings.mCrossProfileIntentResolversSnapshot.snapshot();
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Auto();
        WatchedArrayMap.snapshot(watchedArrayMap3, settings.mSharedUsers);
        AppIdSettingMap appIdSettingMap = settings.mAppIds;
        appIdSettingMap.getClass();
        this.mAppIds = new AppIdSettingMap(appIdSettingMap);
        WatchedArrayMap.snapshot(watchedArrayMap4, settings.mRenamedPackages);
        WatchedSparseIntArray watchedSparseIntArray2 = settings.mNextAppLinkGeneration;
        if (watchedSparseIntArray.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedSparseIntArray2.mStorage.size();
        for (int i = 0; i < size; i++) {
            watchedSparseIntArray.mStorage.put(watchedSparseIntArray2.mStorage.keyAt(i), watchedSparseIntArray2.mStorage.valueAt(i));
        }
        watchedSparseIntArray.seal();
        WatchedSparseArray watchedSparseArray2 = this.mPendingDefaultBrowser;
        WatchedSparseArray watchedSparseArray3 = settings.mPendingDefaultBrowser;
        watchedSparseArray2.getClass();
        WatchedSparseArray.snapshot(watchedSparseArray2, watchedSparseArray3);
        this.mPendingPackages = (WatchedArrayList) settings.mPendingPackagesSnapshot.snapshot();
        this.mPendingPackagesSnapshot = new SnapshotCache.Auto();
        this.mSystemDir = null;
        this.mPermissions = settings.mPermissions;
        this.mPermissionDataProvider = settings.mPermissionDataProvider;
        this.mSnapshot = new SnapshotCache.Auto();
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.server.pm.Settings$1, com.android.server.utils.Watcher] */
    /* JADX WARN: Type inference failed for: r13v1, types: [com.android.server.pm.Settings$3] */
    public Settings(File file, RuntimePermissionsPersistence runtimePermissionsPersistence, PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl, DomainVerificationManagerInternal domainVerificationManagerInternal, Handler handler, PackageManagerTracedLock packageManagerTracedLock) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        this.mDisabledSysPackages = new WatchedArrayMap(0);
        this.mBlockUninstallPackages = new WatchedSparseArray();
        this.mVersion = new WatchedArrayMap(0);
        this.mSharedUsers = new WatchedArrayMap(0);
        this.mRenamedPackages = new WatchedArrayMap(0);
        this.mPendingDefaultBrowser = new WatchedSparseArray();
        this.mNextAppLinkGeneration = new WatchedSparseIntArray();
        this.mReadMessages = new StringBuilder();
        ?? r0 = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        this.mObserver = r0;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "Settings.mPackages", 0);
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
        this.mKernelMapping = watchedArrayMap2;
        this.mKernelMappingSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "Settings.mKernelMapping", 0);
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mInstallerPackages = watchedArraySet;
        this.mInstallerPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "Settings.mInstallerPackages", 0);
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mPreferredActivities = watchedSparseArray;
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray, watchedSparseArray, "Settings.mPreferredActivities", 0);
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mPersistentPreferredActivities = watchedSparseArray2;
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray2, watchedSparseArray2, "Settings.mPersistentPreferredActivities", 0);
        WatchedSparseArray watchedSparseArray3 = new WatchedSparseArray();
        this.mCrossProfileIntentResolvers = watchedSparseArray3;
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Auto(watchedSparseArray3, watchedSparseArray3, "Settings.mCrossProfileIntentResolvers", 0);
        WatchedArrayList watchedArrayList = new WatchedArrayList(0);
        this.mPendingPackages = watchedArrayList;
        this.mPendingPackagesSnapshot = new SnapshotCache.Auto(watchedArrayList, watchedArrayList, "Settings.mPendingPackages", 0);
        this.mKeySetManagerService = new KeySetManagerService(watchedArrayMap);
        this.mHandler = handler;
        this.mLock = packageManagerTracedLock;
        this.mAppIds = new AppIdSettingMap();
        this.mPermissions = new LegacyPermissionSettings();
        this.mRuntimePermissionsPersistence = new RuntimePermissionPersistence(runtimePermissionsPersistence, new Consumer() { // from class: com.android.server.pm.Settings.3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RuntimePermissionPersistence runtimePermissionPersistence = Settings.this.mRuntimePermissionsPersistence;
                int intValue = ((Integer) obj).intValue();
                Settings settings = Settings.this;
                runtimePermissionPersistence.writeStateForUser(intValue, settings.mPermissionDataProvider, settings.mPackages, settings.mSharedUsers, settings.mHandler, settings.mLock, false);
            }
        });
        this.mPermissionDataProvider = permissionManagerServiceInternalImpl;
        File file2 = new File(file, "system");
        this.mSystemDir = file2;
        file2.mkdirs();
        FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        this.mSettingsFilename = new File(file2, "packages.xml");
        this.mSettingsReserveCopyFilename = new File(file2, "packages.xml.reservecopy");
        this.mPreviousSettingsFilename = new File(file2, "packages-backup.xml");
        File file3 = new File(file2, "packages.list");
        this.mPackageListFilename = file3;
        FileUtils.setPermissions(file3, FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
        File file4 = new File("/config/sdcardfs");
        this.mKernelMappingFilename = file4.exists() ? file4 : null;
        this.mStoppedPackagesFilename = new File(file2, "packages-stopped.xml");
        this.mBackupStoppedPackagesFilename = new File(file2, "packages-stopped-backup.xml");
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        registerObservers$1();
        Watchable.verifyWatchedAttributes(this, r0, false);
        this.mSnapshot = new AnonymousClass2(this, this, null);
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.server.pm.Settings$1, com.android.server.utils.Watcher] */
    public Settings(Map map) {
        this.mWatchable = new WatchableImpl();
        this.mPackageRestrictionsLock = new Object();
        this.mPendingAsyncPackageRestrictionsWrites = new SparseIntArray();
        this.mDisabledSysPackages = new WatchedArrayMap(0);
        this.mBlockUninstallPackages = new WatchedSparseArray();
        this.mVersion = new WatchedArrayMap(0);
        this.mSharedUsers = new WatchedArrayMap(0);
        this.mRenamedPackages = new WatchedArrayMap(0);
        this.mPendingDefaultBrowser = new WatchedSparseArray();
        this.mNextAppLinkGeneration = new WatchedSparseIntArray();
        this.mReadMessages = new StringBuilder();
        ?? r0 = new Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                Settings.this.dispatchChange(watchable);
            }
        };
        this.mObserver = r0;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        this.mPackages = watchedArrayMap;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "Settings.mPackages", 0);
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
        this.mKernelMapping = watchedArrayMap2;
        this.mKernelMappingSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "Settings.mKernelMapping", 0);
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mInstallerPackages = watchedArraySet;
        this.mInstallerPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "Settings.mInstallerPackages", 0);
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mPreferredActivities = watchedSparseArray;
        this.mPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray, watchedSparseArray, "Settings.mPreferredActivities", 0);
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mPersistentPreferredActivities = watchedSparseArray2;
        this.mPersistentPreferredActivitiesSnapshot = new SnapshotCache.Auto(watchedSparseArray2, watchedSparseArray2, "Settings.mPersistentPreferredActivities", 0);
        WatchedSparseArray watchedSparseArray3 = new WatchedSparseArray();
        this.mCrossProfileIntentResolvers = watchedSparseArray3;
        this.mCrossProfileIntentResolversSnapshot = new SnapshotCache.Auto(watchedSparseArray3, watchedSparseArray3, "Settings.mCrossProfileIntentResolvers", 0);
        WatchedArrayList watchedArrayList = new WatchedArrayList(0);
        this.mPendingPackages = watchedArrayList;
        this.mPendingPackagesSnapshot = new SnapshotCache.Auto(watchedArrayList, watchedArrayList, "Settings.mPendingPackages", 0);
        this.mKeySetManagerService = new KeySetManagerService(watchedArrayMap);
        this.mHandler = new Handler(BackgroundThread.getHandler().getLooper());
        this.mLock = new PackageManagerTracedLock(null);
        watchedArrayMap.putAll(map);
        this.mAppIds = new AppIdSettingMap();
        this.mSystemDir = null;
        this.mPermissions = null;
        this.mRuntimePermissionsPersistence = null;
        this.mPermissionDataProvider = null;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = null;
        registerObservers$1();
        Watchable.verifyWatchedAttributes(this, r0, false);
        this.mSnapshot = new AnonymousClass2(this, this, null);
    }

    public static void dumpComponents(PrintWriter printWriter, String str, String str2, List list) {
        int size = CollectionUtils.size(list);
        if (size == 0) {
            return;
        }
        printWriter.print(str);
        printWriter.println(str2);
        for (int i = 0; i < size; i++) {
            ParsedComponent parsedComponent = (ParsedComponent) list.get(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.println(parsedComponent.getComponentName().flattenToShortString());
        }
    }

    public static void dumpGidsLPr(PrintWriter printWriter, String str, int[] iArr) {
        if (ArrayUtils.isEmpty(iArr)) {
            return;
        }
        printWriter.print(str);
        printWriter.print("gids=");
        boolean z = PackageManagerServiceUtils.DEBUG;
        StringBuilder sb = new StringBuilder(128);
        sb.append('[');
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(iArr[i]);
            }
        }
        sb.append(']');
        printWriter.println(sb.toString());
    }

    public static void dumpInstallPermissionsLPr(PrintWriter printWriter, String str, ArraySet arraySet, LegacyPermissionState legacyPermissionState, List list) {
        LegacyPermissionState.PermissionState permissionState;
        ArraySet arraySet2 = new ArraySet();
        ArrayList arrayList = (ArrayList) list;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            for (LegacyPermissionState.PermissionState permissionState2 : legacyPermissionState.getPermissionStates(((UserInfo) it.next()).id)) {
                if (!permissionState2.mRuntime) {
                    String str2 = permissionState2.mName;
                    if (arraySet == null || arraySet.contains(str2)) {
                        arraySet2.add(str2);
                    }
                }
            }
        }
        Iterator it2 = arraySet2.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            legacyPermissionState.getClass();
            LegacyPermissionState.checkUserId(0);
            LegacyPermissionState.UserState userState = (LegacyPermissionState.UserState) legacyPermissionState.mUserStates.get(0);
            LegacyPermissionState.PermissionState permissionState3 = userState == null ? null : (LegacyPermissionState.PermissionState) userState.mPermissionStates.get(str3);
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                int i = ((UserInfo) it3.next()).id;
                if (i == 0) {
                    permissionState = permissionState3;
                } else {
                    LegacyPermissionState.checkUserId(i);
                    LegacyPermissionState.UserState userState2 = (LegacyPermissionState.UserState) legacyPermissionState.mUserStates.get(i);
                    permissionState = userState2 == null ? null : (LegacyPermissionState.PermissionState) userState2.mPermissionStates.get(str3);
                    if (Objects.equals(permissionState, permissionState3)) {
                    }
                }
                if (!z) {
                    printWriter.print(str);
                    printWriter.println("install permissions:");
                    z = true;
                }
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print(str3);
                printWriter.print(": granted=");
                printWriter.print(permissionState != null && permissionState.mGranted);
                printWriter.print(permissionFlagsToString(permissionState != null ? permissionState.mFlags : 0));
                if (i == 0) {
                    printWriter.println();
                } else {
                    printWriter.print(", userId=");
                    printWriter.println(i);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002a, code lost:
    
        r6 = (com.android.server.pm.permission.LegacyPermissionState.PermissionState) r5.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:
    
        if (r6.mRuntime != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0035, code lost:
    
        r0 = r6.mName;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        if (r4 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003d, code lost:
    
        if (r4.contains(r0) != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0040, code lost:
    
        r2.print(r3);
        r2.print("  ");
        r2.print(r0);
        r2.print(": granted=");
        r2.print(r6.mGranted);
        r2.println(permissionFlagsToString(r6.mFlags));
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0017, code lost:
    
        r2.print(r3);
        r2.println("runtime permissions:");
        r5 = r5.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0028, code lost:
    
        if (r5.hasNext() == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void dumpRuntimePermissionsLPr(java.io.PrintWriter r2, java.lang.String r3, android.util.ArraySet r4, java.util.Collection r5, boolean r6) {
        /*
            java.util.Iterator r0 = r5.iterator()
        L4:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L15
            java.lang.Object r1 = r0.next()
            com.android.server.pm.permission.LegacyPermissionState$PermissionState r1 = (com.android.server.pm.permission.LegacyPermissionState.PermissionState) r1
            boolean r1 = r1.mRuntime
            if (r1 == 0) goto L4
            goto L17
        L15:
            if (r6 == 0) goto L5f
        L17:
            r2.print(r3)
            java.lang.String r6 = "runtime permissions:"
            r2.println(r6)
            java.util.Iterator r5 = r5.iterator()
        L24:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L5f
            java.lang.Object r6 = r5.next()
            com.android.server.pm.permission.LegacyPermissionState$PermissionState r6 = (com.android.server.pm.permission.LegacyPermissionState.PermissionState) r6
            boolean r0 = r6.mRuntime
            if (r0 != 0) goto L35
            goto L24
        L35:
            java.lang.String r0 = r6.mName
            if (r4 == 0) goto L40
            boolean r1 = r4.contains(r0)
            if (r1 != 0) goto L40
            goto L24
        L40:
            r2.print(r3)
            java.lang.String r1 = "  "
            r2.print(r1)
            r2.print(r0)
            java.lang.String r0 = ": granted="
            r2.print(r0)
            boolean r0 = r6.mGranted
            r2.print(r0)
            int r6 = r6.mFlags
            java.lang.String r6 = permissionFlagsToString(r6)
            r2.println(r6)
            goto L24
        L5f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.dumpRuntimePermissionsLPr(java.io.PrintWriter, java.lang.String, android.util.ArraySet, java.util.Collection, boolean):void");
    }

    public static List getUsers(UserManagerService userManagerService, boolean z, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List users = userManagerService.getUsers(true, z, z2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return users;
        } catch (NullPointerException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static ArchiveState parseArchiveState(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "installer-title");
        long attributeLongHex = typedXmlPullParser.getAttributeLongHex((String) null, "archive-time", 0L);
        ArrayList arrayList = new ArrayList();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("archive-activity-info")) {
                String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "activity-title");
                String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "original-component-name");
                String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "icon-path");
                Path of = attributeValue4 == null ? null : Path.of(attributeValue4, new String[0]);
                String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "monochrome-icon-path");
                Path of2 = attributeValue5 == null ? null : Path.of(attributeValue5, new String[0]);
                if (attributeValue2 == null || attributeValue3 == null || of == null) {
                    Slog.wtf("PackageSettings", TextUtils.formatSimple("Missing attributes in tag %s. %s: %s, %s: %s, %s: %s", new Object[]{"archive-activity-info", "activity-title", attributeValue2, "original-component-name", attributeValue3, "icon-path", of}));
                } else {
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue3);
                    if (unflattenFromString == null) {
                        Slog.wtf("PackageSettings", "Incorrect component name: " + attributeValue3 + " from the attributes");
                    } else {
                        arrayList.add(new ArchiveState.ArchiveActivityInfo(attributeValue2, unflattenFromString, of, of2));
                    }
                }
            }
        }
        if (attributeValue == null) {
            Slog.wtf("PackageSettings", "parseArchiveState: installerTitle is null");
            return null;
        }
        if (arrayList.size() >= 1) {
            return new ArchiveState(attributeValue, attributeLongHex, arrayList);
        }
        Slog.wtf("PackageSettings", "parseArchiveState: activityInfos is empty");
        return null;
    }

    public static String permissionFlagsToString(int i) {
        StringBuilder sb = null;
        while (i != 0) {
            if (sb == null) {
                sb = BootReceiver$$ExternalSyntheticOutline0.m(", flags=[ ");
            }
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            sb.append(PackageManager.permissionFlagToString(numberOfTrailingZeros));
            if (i != 0) {
                sb.append('|');
            }
        }
        if (sb == null) {
            return "";
        }
        sb.append(']');
        return sb.toString();
    }

    public static void printFlags(PrintWriter printWriter, int i, Object[] objArr) {
        printWriter.print("[ ");
        for (int i2 = 0; i2 < objArr.length; i2 += 2) {
            if ((((Integer) objArr[i2]).intValue() & i) != 0) {
                printWriter.print(objArr[i2 + 1]);
                printWriter.print(" ");
            }
        }
        printWriter.print("]");
    }

    public static ArraySet readComponentsLPr(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue;
        int depth = typedXmlPullParser.getDepth();
        ArraySet arraySet = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("item") && (attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name")) != null) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                }
                arraySet.add(attributeValue);
            }
        }
        return arraySet;
    }

    public static String readDefaultApps(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        String str = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("default-browser")) {
                    str = xmlPullParser.getAttributeValue(null, "packageName");
                } else if (!name.equals("default-dialer")) {
                    String str2 = "Unknown element under default-apps: " + xmlPullParser.getName();
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, str2);
                    XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return str;
    }

    public static void readInstallPermissionsLPr(TypedXmlPullParser typedXmlPullParser, LegacyPermissionState legacyPermissionState, List list) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                    boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "granted", true);
                    int attributeIntHex = typedXmlPullParser.getAttributeIntHex((String) null, "flags", 0);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(attributeValue, false, attributeBoolean, attributeIntHex), ((UserInfo) it.next()).id);
                    }
                } else {
                    Slog.w("PackageManager", "Unknown element under <permissions>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0081, code lost:
    
        if (r10 != 4) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0084, code lost:
    
        r10 = r14.getName();
        r11 = r10.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:
    
        if (r11 == (-538220657)) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0094, code lost:
    
        if (r11 == (-22768109)) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0099, code lost:
    
        if (r11 == 1627485488) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ba, code lost:
    
        r10 = 65535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00bb, code lost:
    
        if (r10 == 0) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e9, code lost:
    
        r4 = android.content.pm.SuspendDialogInfo.restoreFromXml(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00bd, code lost:
    
        if (r10 == 1) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e4, code lost:
    
        r8 = android.os.PersistableBundle.restoreFromXml(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00bf, code lost:
    
        if (r10 == 2) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00df, code lost:
    
        r9 = android.os.PersistableBundle.restoreFromXml(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00c1, code lost:
    
        android.util.Slog.w("FrameworkPackageUserState", "Unknown tag " + r14.getName() + " in SuspendParams. Ignoring");
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a2, code lost:
    
        if (r10.equals("launcher-extras") == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00a4, code lost:
    
        r10 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ac, code lost:
    
        if (r10.equals("dialog-info") == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ae, code lost:
    
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00b6, code lost:
    
        if (r10.equals("app-extras") == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00b8, code lost:
    
        r10 = 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Map.Entry readSuspensionParamsLPr(int r13, com.android.modules.utils.TypedXmlPullParser r14) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readSuspensionParamsLPr(int, com.android.modules.utils.TypedXmlPullParser):java.util.Map$Entry");
    }

    public static void readUsesSdkLibLPw(TypedXmlPullParser typedXmlPullParser, PackageSetting packageSetting) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "version", -1L);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "optional", true);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.usesSdkLibraries = (String[]) ArrayUtils.appendElement(String.class, packageSetting.getUsesSdkLibraries(), attributeValue);
            packageSetting.onChanged$2();
            packageSetting.usesSdkLibrariesVersionsMajor = ArrayUtils.appendLong(packageSetting.getUsesSdkLibrariesVersionsMajor(), attributeLong);
            packageSetting.onChanged$2();
            packageSetting.usesSdkLibrariesOptional = ArrayUtils.appendBoolean(packageSetting.getUsesSdkLibrariesOptional(), attributeBoolean);
            packageSetting.onChanged$2();
        }
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public static void readUsesStaticLibLPw(TypedXmlPullParser typedXmlPullParser, PackageSetting packageSetting) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "version", -1L);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.usesStaticLibraries = (String[]) ArrayUtils.appendElement(String.class, packageSetting.getUsesStaticLibraries(), attributeValue);
            packageSetting.onChanged$2();
            packageSetting.usesStaticLibrariesVersions = ArrayUtils.appendLong(packageSetting.getUsesStaticLibrariesVersions(), attributeLong);
            packageSetting.onChanged$2();
        }
        XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    public static void removeFilters(PreferredIntentResolver preferredIntentResolver, List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            PreferredActivity preferredActivity = (PreferredActivity) list.get(size);
            preferredIntentResolver.removeFilter((WatchedIntentFilter) preferredActivity);
            PreferredActivityLog.logPreferenceChange(preferredActivity, "Removing preference<replace>");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        if (r2.exists() != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.FileInputStream restorePackages(com.samsung.android.server.pm.rescueparty.PackageManagerBackupController r4) {
        /*
            r0 = 0
            if (r4 == 0) goto L5e
            int r1 = r4.mRebootCntByPackages
            r2 = 3
            if (r1 < r2) goto L9
            goto L5e
        L9:
            java.io.File r1 = r4.getLatestBackupItemDir()
            if (r1 == 0) goto L24
            boolean r2 = r1.exists()
            if (r2 == 0) goto L24
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "packages.xml"
            r2.<init>(r1, r3)
            boolean r3 = r2.exists()
            if (r3 == 0) goto L24
            goto L38
        L24:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "!@Invalid file or not exists in "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            java.lang.String r2 = "PmBackupController"
            android.util.Log.e(r2, r1)
            r2 = r0
        L38:
            if (r2 == 0) goto L5e
            boolean r1 = r2.exists()
            if (r1 == 0) goto L5e
            java.lang.String r0 = "Restoring "
            java.lang.String r0 = com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0.m(r2, r0)
            boolean r1 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            r1 = 4
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r1, r0)
            int r0 = r4.mRebootCntByPackages
            r1 = 1
            int r0 = r0 + r1
            r4.mRebootCntByPackages = r0
            java.lang.String r3 = "reboot_cnt_by_packages"
            r4.putBackupConfigInt(r0, r3, r1)
            java.io.FileInputStream r4 = new java.io.FileInputStream
            r4.<init>(r2)
            return r4
        L5e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.restorePackages(com.samsung.android.server.pm.rescueparty.PackageManagerBackupController):java.io.FileInputStream");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
    
        if (r2.exists() != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.FileInputStream restorePackagesState(com.samsung.android.server.pm.rescueparty.PackageManagerBackupController r5, int r6) {
        /*
            r0 = 0
            if (r5 == 0) goto L6c
            int r1 = r5.mRebootCntByPackages
            r2 = 3
            if (r1 < r2) goto L9
            goto L6c
        L9:
            java.io.File r1 = r5.getLatestBackupItemDir()
            if (r1 == 0) goto L2a
            boolean r2 = r1.exists()
            if (r2 == 0) goto L2a
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "users/"
            java.lang.String r4 = "/package-restrictions.xml"
            java.lang.String r3 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r6, r3, r4)
            r2.<init>(r1, r3)
            boolean r3 = r2.exists()
            if (r3 == 0) goto L2a
            goto L46
        L2a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "!@Invalid dir or not exists in "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = " for user "
            r2.append(r1)
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            java.lang.String r1 = "PmBackupController"
            android.util.Log.e(r1, r6)
            r2 = r0
        L46:
            if (r2 == 0) goto L6c
            boolean r6 = r2.exists()
            if (r6 == 0) goto L6c
            java.lang.String r6 = "Restoring "
            java.lang.String r6 = com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0.m(r2, r6)
            boolean r0 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            r0 = 4
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r0, r6)
            int r6 = r5.mRebootCntByPackages
            r0 = 1
            int r6 = r6 + r0
            r5.mRebootCntByPackages = r6
            java.lang.String r1 = "reboot_cnt_by_packages"
            r5.putBackupConfigInt(r6, r1, r0)
            java.io.FileInputStream r5 = new java.io.FileInputStream
            r5.<init>(r2)
            return r5
        L6c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.restorePackagesState(com.samsung.android.server.pm.rescueparty.PackageManagerBackupController, int):java.io.FileInputStream");
    }

    public static void writeArchiveStateLPr(TypedXmlSerializer typedXmlSerializer, ArchiveState archiveState) {
        if (archiveState == null) {
            return;
        }
        typedXmlSerializer.startTag((String) null, "archive-state");
        typedXmlSerializer.attribute((String) null, "installer-title", archiveState.mInstallerTitle);
        typedXmlSerializer.attributeLongHex((String) null, "archive-time", archiveState.mArchiveTimeMillis);
        for (ArchiveState.ArchiveActivityInfo archiveActivityInfo : archiveState.mActivityInfos) {
            typedXmlSerializer.startTag((String) null, "archive-activity-info");
            typedXmlSerializer.attribute((String) null, "activity-title", archiveActivityInfo.mTitle);
            typedXmlSerializer.attribute((String) null, "original-component-name", archiveActivityInfo.mOriginalComponentName.flattenToString());
            Path path = archiveActivityInfo.mIconBitmap;
            if (path != null) {
                typedXmlSerializer.attribute((String) null, "icon-path", path.toAbsolutePath().toString());
            }
            Path path2 = archiveActivityInfo.mMonochromeIconBitmap;
            if (path2 != null) {
                typedXmlSerializer.attribute((String) null, "monochrome-icon-path", path2.toAbsolutePath().toString());
            }
            typedXmlSerializer.endTag((String) null, "archive-activity-info");
        }
        typedXmlSerializer.endTag((String) null, "archive-state");
    }

    public static void writeDefaultApps(XmlSerializer xmlSerializer, String str) {
        xmlSerializer.startTag(null, "default-apps");
        if (!TextUtils.isEmpty(str)) {
            xmlSerializer.startTag(null, "default-browser");
            xmlSerializer.attribute(null, "packageName", str);
            xmlSerializer.endTag(null, "default-browser");
        }
        xmlSerializer.endTag(null, "default-apps");
    }

    public static void writeDisabledSysPackageLPr(TypedXmlSerializer typedXmlSerializer, PackageSetting packageSetting) {
        typedXmlSerializer.startTag((String) null, "updated-package");
        typedXmlSerializer.attribute((String) null, "name", packageSetting.mName);
        String str = packageSetting.mRealName;
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "realName", str);
        }
        typedXmlSerializer.attribute((String) null, "codePath", packageSetting.mPathString);
        typedXmlSerializer.attributeLongHex((String) null, "ft", packageSetting.mLastModifiedTime);
        typedXmlSerializer.attributeLongHex((String) null, "ut", packageSetting.lastUpdateTime);
        typedXmlSerializer.attributeLong((String) null, "version", packageSetting.versionCode);
        typedXmlSerializer.attributeInt((String) null, "targetSdkVersion", packageSetting.mTargetSdkVersion);
        byte[] bArr = packageSetting.mRestrictUpdateHash;
        if (bArr != null) {
            typedXmlSerializer.attributeBytesBase64((String) null, "restrictUpdateHash", bArr);
        }
        typedXmlSerializer.attributeBoolean((String) null, "scannedAsStoppedSystemApp", packageSetting.getBoolean(8));
        String str2 = packageSetting.legacyNativeLibraryPath;
        if (str2 != null) {
            typedXmlSerializer.attribute((String) null, "nativeLibraryPath", str2);
        }
        String str3 = packageSetting.mPrimaryCpuAbi;
        if (str3 != null) {
            typedXmlSerializer.attribute((String) null, "primaryCpuAbi", str3);
        }
        String str4 = packageSetting.mSecondaryCpuAbi;
        if (str4 != null) {
            typedXmlSerializer.attribute((String) null, "secondaryCpuAbi", str4);
        }
        String str5 = packageSetting.mCpuAbiOverride;
        if (str5 != null) {
            typedXmlSerializer.attribute((String) null, "cpuAbiOverride", str5);
        }
        if (packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((String) null, "sharedUserId", packageSetting.mAppId);
        } else {
            typedXmlSerializer.attributeInt((String) null, "userId", packageSetting.mAppId);
        }
        typedXmlSerializer.attributeFloat((String) null, "loadingProgress", packageSetting.mLoadingProgress);
        typedXmlSerializer.attributeLongHex((String) null, "loadingCompletedTime", packageSetting.mLoadingCompletedTime);
        String str6 = packageSetting.mAppMetadataFilePath;
        if (str6 != null) {
            typedXmlSerializer.attribute((String) null, "appMetadataFilePath", str6);
        }
        typedXmlSerializer.attributeInt((String) null, "appMetadataSource", packageSetting.mAppMetadataSource);
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor(), packageSetting.getUsesSdkLibrariesOptional());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        typedXmlSerializer.endTag((String) null, "updated-package");
    }

    public static void writeIntToFile(File file, int i) {
        try {
            FileUtils.bytesToFile(file.getAbsolutePath(), Integer.toString(i).getBytes(StandardCharsets.US_ASCII));
        } catch (IOException unused) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Couldn't write ", " to ");
            m.append(file.getAbsolutePath());
            Slog.w("PackageSettings", m.toString());
        }
    }

    public static void writePackageLPr(TypedXmlSerializer typedXmlSerializer, ArrayList arrayList, PackageSetting packageSetting) {
        typedXmlSerializer.startTag((String) null, "package");
        typedXmlSerializer.attribute((String) null, "name", packageSetting.mName);
        String str = packageSetting.mRealName;
        if (str != null) {
            typedXmlSerializer.attribute((String) null, "realName", str);
        }
        typedXmlSerializer.attribute((String) null, "codePath", packageSetting.mPathString);
        String str2 = packageSetting.legacyNativeLibraryPath;
        if (str2 != null) {
            typedXmlSerializer.attribute((String) null, "nativeLibraryPath", str2);
        }
        String str3 = packageSetting.mPrimaryCpuAbi;
        if (str3 != null) {
            typedXmlSerializer.attribute((String) null, "primaryCpuAbi", str3);
        }
        String str4 = packageSetting.mSecondaryCpuAbi;
        if (str4 != null) {
            typedXmlSerializer.attribute((String) null, "secondaryCpuAbi", str4);
        }
        String str5 = packageSetting.mCpuAbiOverride;
        if (str5 != null) {
            typedXmlSerializer.attribute((String) null, "cpuAbiOverride", str5);
        }
        typedXmlSerializer.attributeInt((String) null, "publicFlags", packageSetting.mPkgFlags);
        typedXmlSerializer.attributeInt((String) null, "privateFlags", packageSetting.mPkgPrivateFlags);
        typedXmlSerializer.attributeLongHex((String) null, "ft", packageSetting.mLastModifiedTime);
        typedXmlSerializer.attributeLongHex((String) null, "ut", packageSetting.lastUpdateTime);
        typedXmlSerializer.attributeLong((String) null, "version", packageSetting.versionCode);
        typedXmlSerializer.attributeInt((String) null, "targetSdkVersion", packageSetting.mTargetSdkVersion);
        byte[] bArr = packageSetting.mRestrictUpdateHash;
        if (bArr != null) {
            typedXmlSerializer.attributeBytesBase64((String) null, "restrictUpdateHash", bArr);
        }
        typedXmlSerializer.attributeBoolean((String) null, "scannedAsStoppedSystemApp", packageSetting.getBoolean(8));
        if (packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((String) null, "sharedUserId", packageSetting.mAppId);
        } else {
            typedXmlSerializer.attributeInt((String) null, "userId", packageSetting.mAppId);
            AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
            typedXmlSerializer.attributeBoolean((String) null, "isSdkLibrary", androidPackageInternal != null && androidPackageInternal.isSdkLibrary());
        }
        InstallSource installSource = packageSetting.installSource;
        String str6 = installSource.mInstallerPackageName;
        if (str6 != null) {
            typedXmlSerializer.attribute((String) null, "installer", str6);
        }
        int i = installSource.mInstallerPackageUid;
        if (i != -1) {
            typedXmlSerializer.attributeInt((String) null, "installerUid", i);
        }
        String str7 = installSource.mUpdateOwnerPackageName;
        if (str7 != null) {
            typedXmlSerializer.attribute((String) null, "updateOwner", str7);
        }
        String str8 = installSource.mInstallerAttributionTag;
        if (str8 != null) {
            typedXmlSerializer.attribute((String) null, "installerAttributionTag", str8);
        }
        typedXmlSerializer.attributeInt((String) null, "packageSource", installSource.mPackageSource);
        if (installSource.mIsOrphaned) {
            typedXmlSerializer.attributeBoolean((String) null, "isOrphaned", true);
        }
        String str9 = installSource.mInitiatingPackageName;
        if (str9 != null) {
            typedXmlSerializer.attribute((String) null, "installInitiator", str9);
        }
        if (installSource.mIsInitiatingPackageUninstalled) {
            typedXmlSerializer.attributeBoolean((String) null, "installInitiatorUninstalled", true);
        }
        String str10 = installSource.mOriginatingPackageName;
        if (str10 != null) {
            typedXmlSerializer.attribute((String) null, "installOriginator", str10);
        }
        String str11 = packageSetting.volumeUuid;
        if (str11 != null) {
            typedXmlSerializer.attribute((String) null, "volumeUuid", str11);
        }
        int i2 = packageSetting.categoryOverride;
        if (i2 != -1) {
            typedXmlSerializer.attributeInt((String) null, "categoryHint", i2);
        }
        if (packageSetting.getBoolean(2)) {
            typedXmlSerializer.attributeBoolean((String) null, "updateAvailable", true);
        }
        if (packageSetting.getBoolean(4)) {
            typedXmlSerializer.attributeBoolean((String) null, "forceQueryable", true);
        }
        if (packageSetting.getBoolean(16)) {
            typedXmlSerializer.attributeBoolean((String) null, "pendingRestore", true);
        }
        if (packageSetting.getBoolean(32)) {
            typedXmlSerializer.attributeBoolean((String) null, "debuggable", true);
        }
        if (packageSetting.isLoading()) {
            typedXmlSerializer.attributeBoolean((String) null, "isLoading", true);
        }
        int i3 = packageSetting.mBaseRevisionCode;
        if (i3 != 0) {
            typedXmlSerializer.attributeInt((String) null, "baseRevisionCode", i3);
        }
        typedXmlSerializer.attributeFloat((String) null, "loadingProgress", packageSetting.mLoadingProgress);
        typedXmlSerializer.attributeLongHex((String) null, "loadingCompletedTime", packageSetting.mLoadingCompletedTime);
        typedXmlSerializer.attribute((String) null, "domainSetId", packageSetting.mDomainSetId.toString());
        String str12 = packageSetting.mAppMetadataFilePath;
        if (str12 != null) {
            typedXmlSerializer.attribute((String) null, "appMetadataFilePath", str12);
        }
        typedXmlSerializer.attributeInt((String) null, "appMetadataSource", packageSetting.mAppMetadataSource);
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor(), packageSetting.getUsesSdkLibrariesOptional());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        packageSetting.signatures.writeXml(typedXmlSerializer, "sigs", arrayList);
        PackageSignatures packageSignatures = installSource.mInitiatingPackageSignatures;
        if (packageSignatures != null) {
            packageSignatures.writeXml(typedXmlSerializer, "install-initiator-sigs", arrayList);
        }
        PackageKeySetData packageKeySetData = packageSetting.keySetData;
        typedXmlSerializer.startTag((String) null, "proper-signing-keyset");
        typedXmlSerializer.attributeLong((String) null, "identifier", packageKeySetData.mProperSigningKeySet);
        typedXmlSerializer.endTag((String) null, "proper-signing-keyset");
        long[] jArr = packageSetting.keySetData.mUpgradeKeySets;
        if (jArr != null && jArr.length > 0) {
            for (long j : jArr) {
                typedXmlSerializer.startTag((String) null, "upgrade-keyset");
                typedXmlSerializer.attributeLong((String) null, "identifier", j);
                typedXmlSerializer.endTag((String) null, "upgrade-keyset");
            }
        }
        for (Map.Entry entry : packageSetting.keySetData.mKeySetAliases.entrySet()) {
            typedXmlSerializer.startTag((String) null, "defined-keyset");
            typedXmlSerializer.attribute((String) null, "alias", (String) entry.getKey());
            typedXmlSerializer.attributeLong((String) null, "identifier", ((Long) entry.getValue()).longValue());
            typedXmlSerializer.endTag((String) null, "defined-keyset");
        }
        Map mimeGroups = packageSetting.getMimeGroups();
        if (mimeGroups != null) {
            for (String str13 : mimeGroups.keySet()) {
                typedXmlSerializer.startTag((String) null, "mime-group");
                typedXmlSerializer.attribute((String) null, "name", str13);
                for (String str14 : (Set) mimeGroups.get(str13)) {
                    typedXmlSerializer.startTag((String) null, "mime-type");
                    typedXmlSerializer.attribute((String) null, "value", str14);
                    typedXmlSerializer.endTag((String) null, "mime-type");
                }
                typedXmlSerializer.endTag((String) null, "mime-group");
            }
        }
        if (packageSetting.pkg == null) {
            String[] splitNames = packageSetting.getSplitNames();
            int[] splitRevisionCodes = packageSetting.getSplitRevisionCodes();
            if (!ArrayUtils.isEmpty(splitNames) && !ArrayUtils.isEmpty(splitRevisionCodes) && splitNames.length == splitRevisionCodes.length) {
                int length = splitNames.length;
                for (int i4 = 0; i4 < length; i4++) {
                    String str15 = splitNames[i4];
                    int i5 = splitRevisionCodes[i4];
                    typedXmlSerializer.startTag((String) null, "split-version");
                    typedXmlSerializer.attribute((String) null, "name", str15);
                    typedXmlSerializer.attributeInt((String) null, "version", i5);
                    typedXmlSerializer.endTag((String) null, "split-version");
                }
            }
        }
        typedXmlSerializer.endTag((String) null, "package");
    }

    public static void writeUsesSdkLibLPw(TypedXmlSerializer typedXmlSerializer, String[] strArr, long[] jArr, boolean[] zArr) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            long j = jArr[i];
            boolean z = zArr[i];
            typedXmlSerializer.startTag((String) null, "uses-sdk-lib");
            typedXmlSerializer.attribute((String) null, "name", str);
            typedXmlSerializer.attributeLong((String) null, "version", j);
            typedXmlSerializer.attributeBoolean((String) null, "optional", z);
            typedXmlSerializer.endTag((String) null, "uses-sdk-lib");
        }
    }

    public static void writeUsesStaticLibLPw(TypedXmlSerializer typedXmlSerializer, String[] strArr, long[] jArr) {
        if (ArrayUtils.isEmpty(strArr) || ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            long j = jArr[i];
            typedXmlSerializer.startTag((String) null, "uses-static-lib");
            typedXmlSerializer.attribute((String) null, "name", str);
            typedXmlSerializer.attributeLong((String) null, "version", j);
            typedXmlSerializer.endTag((String) null, "uses-static-lib");
        }
    }

    public final void addInstallerPackageNames(InstallSource installSource) {
        String str = installSource.mInstallerPackageName;
        WatchedArraySet watchedArraySet = this.mInstallerPackages;
        if (str != null) {
            watchedArraySet.add(str);
        }
        String str2 = installSource.mInitiatingPackageName;
        if (str2 != null) {
            watchedArraySet.add(str2);
        }
        String str3 = installSource.mOriginatingPackageName;
        if (str3 != null) {
            watchedArraySet.add(str3);
        }
    }

    public final PackageSetting addPackageLPw(String str, String str2, File file, int i, int i2, int i3, UUID uuid, boolean z) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.mStorage.get(str);
        if (packageSetting != null) {
            if (packageSetting.mAppId == i) {
                return packageSetting;
            }
            String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Adding duplicate package, keeping first: ", str);
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(6, m);
            return null;
        }
        PackageSetting packageSetting2 = new PackageSetting(str, str2, file, i2, i3, uuid);
        packageSetting2.mAppId = i;
        packageSetting2.onChanged$2();
        if ((i != -1 || !z || !Flags.disallowSdkLibsToBeApps()) && !this.mAppIds.registerExistingAppId(i, packageSetting2, str)) {
            return null;
        }
        this.mPackages.put(str, packageSetting2);
        return packageSetting2;
    }

    public void addPackageSettingLPw(PackageSetting packageSetting, SharedUserSetting sharedUserSetting) {
        this.mPackages.put(packageSetting.mName, packageSetting);
        if (sharedUserSetting != null) {
            SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
            if (sharedUserSettingLPr == null || sharedUserSettingLPr == sharedUserSetting) {
                int i = packageSetting.mAppId;
                if (i != 0 && i != sharedUserSetting.mAppId) {
                    StringBuilder sb = new StringBuilder("Package ");
                    sb.append(packageSetting.mName);
                    sb.append(" was app id ");
                    sb.append(packageSetting.mAppId);
                    sb.append(" but is now user ");
                    sb.append(sharedUserSetting);
                    sb.append(" with app id ");
                    String m = AmFmBandRange$$ExternalSyntheticOutline0.m(sharedUserSetting.mAppId, sb, "; I am not changing its files so it will probably fail!");
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(6, m);
                }
            } else {
                String str = "Package " + packageSetting.mName + " was user " + sharedUserSettingLPr + " but is now " + sharedUserSetting + "; I am not changing its files so it will probably fail!";
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(6, str);
                sharedUserSettingLPr.removePackage(packageSetting);
            }
            sharedUserSetting.addPackage(packageSetting);
            packageSetting.setSharedUserAppId(sharedUserSetting.mAppId);
            packageSetting.setAppId(sharedUserSetting.mAppId);
        }
        SettingBase settingLPr = getSettingLPr(packageSetting.mAppId);
        AppIdSettingMap appIdSettingMap = this.mAppIds;
        if (sharedUserSetting == null) {
            if (settingLPr == null || settingLPr == packageSetting) {
                return;
            }
            appIdSettingMap.replaceSetting(packageSetting.mAppId, packageSetting);
            return;
        }
        if (settingLPr == null || settingLPr == sharedUserSetting) {
            return;
        }
        appIdSettingMap.replaceSetting(packageSetting.mAppId, sharedUserSetting);
    }

    public final SharedUserSetting addSharedUserLPw(int i, int i2, int i3, String str) {
        WatchedArrayMap watchedArrayMap = this.mSharedUsers;
        SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap.mStorage.get(str);
        if (sharedUserSetting != null) {
            if (sharedUserSetting.mAppId == i) {
                return sharedUserSetting;
            }
            String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Adding duplicate shared user, keeping first: ", str);
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(6, m);
            return null;
        }
        SharedUserSetting sharedUserSetting2 = new SharedUserSetting(i2, i3, str);
        sharedUserSetting2.mAppId = i;
        if (!this.mAppIds.registerExistingAppId(i, sharedUserSetting2, str)) {
            return null;
        }
        watchedArrayMap.put(str, sharedUserSetting2);
        return sharedUserSetting2;
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x015b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyDefaultPreferredActivityLPw(android.content.pm.PackageManagerInternal r20, android.content.Intent r21, int r22, android.content.ComponentName r23, java.lang.String r24, android.os.PatternMatcher r25, android.content.IntentFilter.AuthorityEntry r26, android.os.PatternMatcher r27, int r28) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.applyDefaultPreferredActivityLPw(android.content.pm.PackageManagerInternal, android.content.Intent, int, android.content.ComponentName, java.lang.String, android.os.PatternMatcher, android.content.IntentFilter$AuthorityEntry, android.os.PatternMatcher, int):void");
    }

    public final void applyDefaultPreferredActivityLPw(PackageManagerInternal packageManagerInternal, IntentFilter intentFilter, ComponentName componentName, int i) {
        int i2;
        Intent intent = new Intent();
        int i3 = 0;
        intent.setAction(intentFilter.getAction(0));
        int i4 = 786432;
        for (int i5 = 0; i5 < intentFilter.countCategories(); i5++) {
            String category = intentFilter.getCategory(i5);
            if (category.equals("android.intent.category.DEFAULT")) {
                i4 = 851968;
            } else {
                intent.addCategory(category);
            }
        }
        int countDataSchemes = intentFilter.countDataSchemes();
        int i6 = 0;
        boolean z = false;
        boolean z2 = true;
        while (i6 < countDataSchemes) {
            String dataScheme = intentFilter.getDataScheme(i6);
            if (dataScheme != null && !dataScheme.isEmpty()) {
                z = true;
            }
            int countDataSchemeSpecificParts = intentFilter.countDataSchemeSpecificParts();
            int i7 = i3;
            boolean z3 = true;
            while (i7 < countDataSchemeSpecificParts) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme(dataScheme);
                PatternMatcher dataSchemeSpecificPart = intentFilter.getDataSchemeSpecificPart(i7);
                builder.opaquePart(dataSchemeSpecificPart.getPath());
                Intent intent2 = new Intent(intent);
                intent2.setData(builder.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent2, i4, componentName, dataScheme, dataSchemeSpecificPart, null, null, i);
                i7++;
                dataScheme = dataScheme;
                countDataSchemeSpecificParts = countDataSchemeSpecificParts;
                i6 = i6;
                z3 = false;
            }
            String str = dataScheme;
            int i8 = i6;
            int countDataAuthorities = intentFilter.countDataAuthorities();
            int i9 = 0;
            while (i9 < countDataAuthorities) {
                IntentFilter.AuthorityEntry dataAuthority = intentFilter.getDataAuthority(i9);
                int countDataPaths = intentFilter.countDataPaths();
                boolean z4 = true;
                int i10 = 0;
                while (i10 < countDataPaths) {
                    Uri.Builder builder2 = new Uri.Builder();
                    builder2.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder2.authority(dataAuthority.getHost());
                    }
                    PatternMatcher dataPath = intentFilter.getDataPath(i10);
                    builder2.path(dataPath.getPath());
                    Intent intent3 = new Intent(intent);
                    intent3.setData(builder2.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent3, i4, componentName, str, null, dataAuthority, dataPath, i);
                    i10++;
                    countDataAuthorities = countDataAuthorities;
                    countDataPaths = countDataPaths;
                    i9 = i9;
                    z3 = false;
                    z4 = false;
                }
                int i11 = i9;
                int i12 = countDataAuthorities;
                if (z4) {
                    Uri.Builder builder3 = new Uri.Builder();
                    builder3.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder3.authority(dataAuthority.getHost());
                    }
                    Intent intent4 = new Intent(intent);
                    intent4.setData(builder3.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent4, i4, componentName, str, null, dataAuthority, null, i);
                    z3 = false;
                }
                i9 = i11 + 1;
                countDataAuthorities = i12;
            }
            if (z3) {
                Uri.Builder builder4 = new Uri.Builder();
                builder4.scheme(str);
                Intent intent5 = new Intent(intent);
                intent5.setData(builder4.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent5, i4, componentName, str, null, null, null, i);
            }
            i6 = i8 + 1;
            i3 = 0;
            z2 = false;
        }
        int i13 = 0;
        while (i13 < intentFilter.countDataTypes()) {
            String dataType = intentFilter.getDataType(i13);
            if (z) {
                Uri.Builder builder5 = new Uri.Builder();
                int i14 = 0;
                while (i14 < intentFilter.countDataSchemes()) {
                    String dataScheme2 = intentFilter.getDataScheme(i14);
                    if (dataScheme2 == null || dataScheme2.isEmpty()) {
                        i2 = i14;
                    } else {
                        Intent intent6 = new Intent(intent);
                        builder5.scheme(dataScheme2);
                        intent6.setDataAndType(builder5.build(), dataType);
                        i2 = i14;
                        applyDefaultPreferredActivityLPw(packageManagerInternal, intent6, i4, componentName, dataScheme2, null, null, null, i);
                    }
                    i14 = i2 + 1;
                }
            } else {
                Intent intent7 = new Intent(intent);
                intent7.setType(dataType);
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent7, i4, componentName, null, null, null, null, i);
            }
            i13++;
            z2 = false;
        }
        if (z2) {
            applyDefaultPreferredActivityLPw(packageManagerInternal, intent, i4, componentName, null, null, null, null, i);
        }
    }

    public final void applyDefaultPreferredAppsLPw(int i) {
        int i2;
        FileInputStream fileInputStream;
        int next;
        int i3;
        AndroidPackageInternal androidPackageInternal;
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        for (PackageSetting packageSetting : this.mPackages.values()) {
            if ((1 & packageSetting.mPkgFlags) != 0 && (androidPackageInternal = packageSetting.pkg) != null && !androidPackageInternal.getPreferredActivityFilters().isEmpty()) {
                List preferredActivityFilters = packageSetting.pkg.getPreferredActivityFilters();
                for (int i4 = 0; i4 < preferredActivityFilters.size(); i4++) {
                    Pair pair = (Pair) preferredActivityFilters.get(i4);
                    applyDefaultPreferredActivityLPw(packageManagerInternal, ((ParsedIntentInfo) pair.second).getIntentFilter(), new ComponentName(packageSetting.mName, (String) pair.first), i);
                }
            }
        }
        int size = PackageManagerService.SYSTEM_PARTITIONS.size();
        int i5 = 0;
        while (i5 < size) {
            File file = new File(((ScanPartition) PackageManagerService.SYSTEM_PARTITIONS.get(i5)).getFolder(), "etc/preferred-apps");
            if (file.exists() && file.isDirectory()) {
                if (file.canRead()) {
                    File[] listFiles = file.listFiles();
                    if (!ArrayUtils.isEmpty(listFiles)) {
                        int length = listFiles.length;
                        int i6 = 0;
                        while (i6 < length) {
                            File file2 = listFiles[i6];
                            if (!file2.getPath().endsWith(".xml")) {
                                Slog.i("PackageSettings", "Non-xml file " + file2 + " in " + file + " directory, ignoring");
                            } else if (file2.canRead()) {
                                try {
                                    fileInputStream = new FileInputStream(file2);
                                } catch (IOException e) {
                                    e = e;
                                    i2 = size;
                                } catch (XmlPullParserException e2) {
                                    e = e2;
                                    i2 = size;
                                }
                                try {
                                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                                    while (true) {
                                        next = resolvePullParser.next();
                                        i2 = size;
                                        i3 = 2;
                                        if (next == 2) {
                                            break;
                                        }
                                        if (next == 1) {
                                            i3 = 2;
                                            break;
                                        }
                                        size = i2;
                                    }
                                    if (next != i3) {
                                        try {
                                            Slog.w("PackageSettings", "Preferred apps file " + file2 + " does not have start tag");
                                        } catch (Throwable th) {
                                            th = th;
                                            Throwable th2 = th;
                                            try {
                                                fileInputStream.close();
                                            } catch (Throwable th3) {
                                                th2.addSuppressed(th3);
                                            }
                                            throw th2;
                                        }
                                    } else if ("preferred-activities".equals(resolvePullParser.getName())) {
                                        readDefaultPreferredActivitiesLPw(i, resolvePullParser);
                                    } else {
                                        Slog.w("PackageSettings", "Preferred apps file " + file2 + " does not start with 'preferred-activities'");
                                    }
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e3) {
                                        e = e3;
                                        Slog.w("PackageSettings", "Error reading apps file " + file2, e);
                                        i6++;
                                        size = i2;
                                    } catch (XmlPullParserException e4) {
                                        e = e4;
                                        Slog.w("PackageSettings", "Error reading apps file " + file2, e);
                                        i6++;
                                        size = i2;
                                    }
                                    i6++;
                                    size = i2;
                                } catch (Throwable th4) {
                                    th = th4;
                                    i2 = size;
                                }
                            } else {
                                Slog.w("PackageSettings", "Preferred apps file " + file2 + " cannot be read");
                            }
                            i2 = size;
                            i6++;
                            size = i2;
                        }
                    }
                } else {
                    Slog.w("PackageSettings", "Directory " + file + " cannot be read");
                }
            }
            i5++;
            size = size;
        }
    }

    public final boolean checkAndPruneSharedUserLPw(SharedUserSetting sharedUserSetting, boolean z) {
        if (!z && (!sharedUserSetting.mPackages.mStorage.isEmpty() || !sharedUserSetting.mDisabledPackages.mStorage.isEmpty())) {
            return false;
        }
        if (this.mSharedUsers.remove(sharedUserSetting.name) == null) {
            return false;
        }
        removeAppIdLPw(sharedUserSetting.mAppId);
        return true;
    }

    public final boolean clearPackagePersistentPreferredActivities(int i, String str) {
        ArrayList arrayList = null;
        int i2 = 0;
        boolean z = false;
        while (true) {
            WatchedSparseArray watchedSparseArray = this.mPersistentPreferredActivities;
            if (i2 >= watchedSparseArray.mStorage.size()) {
                break;
            }
            int keyAt = watchedSparseArray.mStorage.keyAt(i2);
            PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) watchedSparseArray.mStorage.valueAt(i2);
            if (i == keyAt) {
                IntentResolver.IteratorWrapper filterIterator = persistentPreferredIntentResolver.filterIterator();
                while (filterIterator.mI.hasNext()) {
                    PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) filterIterator.next();
                    if (persistentPreferredActivity.mComponent.getPackageName().equals(str)) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(persistentPreferredActivity);
                    }
                }
                if (arrayList != null) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        persistentPreferredIntentResolver.removeFilter((WatchedIntentFilter) arrayList.get(i3));
                    }
                    z = true;
                }
            }
            i2++;
        }
        if (z) {
            dispatchChange(this);
        }
        return z;
    }

    public final boolean clearPersistentPreferredActivity(IntentFilter intentFilter, int i) {
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.mStorage.get(i);
        IntentResolver.IteratorWrapper filterIterator = persistentPreferredIntentResolver.filterIterator();
        ArrayList arrayList = null;
        while (filterIterator.mI.hasNext()) {
            PersistentPreferredActivity persistentPreferredActivity = (PersistentPreferredActivity) filterIterator.next();
            if (IntentFilter.filterEquals(persistentPreferredActivity.mFilter, intentFilter)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(persistentPreferredActivity);
            }
        }
        boolean z = false;
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                persistentPreferredIntentResolver.removeFilter((WatchedIntentFilter) arrayList.get(i2));
            }
            z = true;
        }
        if (z) {
            dispatchChange(this);
        }
        return z;
    }

    public final void convertSharedUserSettingsLPw(SharedUserSetting sharedUserSetting) {
        PackageSetting packageSetting = (PackageSetting) sharedUserSetting.mPackages.mStorage.valueAt(0);
        this.mAppIds.replaceSetting(sharedUserSetting.mAppId, packageSetting);
        packageSetting.setSharedUserAppId(-1);
        WatchedArraySet watchedArraySet = sharedUserSetting.mDisabledPackages;
        if (!watchedArraySet.mStorage.isEmpty()) {
            ((PackageSetting) watchedArraySet.mStorage.valueAt(0)).setSharedUserAppId(-1);
        }
        this.mSharedUsers.remove(sharedUserSetting.name);
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x010c, code lost:
    
        if (((android.util.ArraySet) r29).contains(r2.mName) != false) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0123 A[Catch: all -> 0x008d, TryCatch #3 {all -> 0x008d, blocks: (B:7:0x0035, B:11:0x0051, B:13:0x0069, B:15:0x0077, B:19:0x0091, B:21:0x00ae, B:24:0x00ba, B:27:0x00c5, B:30:0x00cd, B:32:0x00d3, B:35:0x00dc, B:38:0x00e7, B:45:0x0102, B:48:0x0111, B:51:0x011c, B:52:0x011f, B:54:0x0123, B:56:0x0129, B:58:0x012f, B:62:0x013e, B:65:0x0167, B:69:0x017c, B:71:0x0181, B:74:0x0187, B:40:0x00f1, B:110:0x0048), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x013e A[Catch: all -> 0x008d, TryCatch #3 {all -> 0x008d, blocks: (B:7:0x0035, B:11:0x0051, B:13:0x0069, B:15:0x0077, B:19:0x0091, B:21:0x00ae, B:24:0x00ba, B:27:0x00c5, B:30:0x00cd, B:32:0x00d3, B:35:0x00dc, B:38:0x00e7, B:45:0x0102, B:48:0x0111, B:51:0x011c, B:52:0x011f, B:54:0x0123, B:56:0x0129, B:58:0x012f, B:62:0x013e, B:65:0x0167, B:69:0x017c, B:71:0x0181, B:74:0x0187, B:40:0x00f1, B:110:0x0048), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0177 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0181 A[Catch: all -> 0x008d, TryCatch #3 {all -> 0x008d, blocks: (B:7:0x0035, B:11:0x0051, B:13:0x0069, B:15:0x0077, B:19:0x0091, B:21:0x00ae, B:24:0x00ba, B:27:0x00c5, B:30:0x00cd, B:32:0x00d3, B:35:0x00dc, B:38:0x00e7, B:45:0x0102, B:48:0x0111, B:51:0x011c, B:52:0x011f, B:54:0x0123, B:56:0x0129, B:58:0x012f, B:62:0x013e, B:65:0x0167, B:69:0x017c, B:71:0x0181, B:74:0x0187, B:40:0x00f1, B:110:0x0048), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c7 A[Catch: all -> 0x01c5, TryCatch #0 {all -> 0x01c5, blocks: (B:18:0x01d1, B:76:0x01bc, B:113:0x020e, B:77:0x01c7, B:90:0x01e2), top: B:6:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void createNewUserLI(com.android.server.pm.PackageManagerService r26, com.android.server.pm.Installer r27, int r28, java.util.Set r29, java.lang.String[] r30) {
        /*
            Method dump skipped, instructions count: 530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.createNewUserLI(com.android.server.pm.PackageManagerService, com.android.server.pm.Installer, int, java.util.Set, java.lang.String[]):void");
    }

    public final boolean disableSystemPackageLPw(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mPackages.mStorage.get(str);
        if (packageSetting == null) {
            Log.w("PackageManager", "Package " + str + " is not an installed package");
            return false;
        }
        if (((PackageSetting) this.mDisabledSysPackages.mStorage.get(str)) == null && packageSetting.pkg != null && packageSetting.isSystem()) {
            PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
            if (!packageStateUnserialized.updatedSystemApp) {
                PackageSetting packageSetting2 = new PackageSetting(packageSetting, false);
                packageStateUnserialized.setUpdatedSystemApp(true);
                this.mDisabledSysPackages.put(str, packageSetting2);
                SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting2);
                if (sharedUserSettingLPr != null) {
                    sharedUserSettingLPr.mDisabledPackages.add(packageSetting2);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    @NeverCompile
    public final void dumpPackageLPr(PrintWriter printWriter, String str, ArraySet arraySet, PackageSetting packageSetting, LegacyPermissionState legacyPermissionState, SimpleDateFormat simpleDateFormat, Date date, List list, boolean z, boolean z2) {
        String str2;
        LegacyPermissionState legacyPermissionState2;
        List list2;
        boolean z3;
        PackageSetting packageSetting2 = packageSetting;
        AndroidPackageInternal androidPackageInternal = packageSetting2.pkg;
        if (str != null) {
            printWriter.print(str);
            printWriter.print(",");
            String str3 = packageSetting2.mRealName;
            if (str3 == null) {
                str3 = packageSetting2.mName;
            }
            printWriter.print(str3);
            printWriter.print(",");
            printWriter.print(packageSetting2.mAppId);
            printWriter.print(",");
            printWriter.print(packageSetting2.versionCode);
            printWriter.print(",");
            printWriter.print(packageSetting2.lastUpdateTime);
            printWriter.print(",");
            String str4 = packageSetting2.installSource.mInstallerPackageName;
            if (str4 == null) {
                str4 = "?";
            }
            printWriter.print(str4);
            printWriter.print(packageSetting2.installSource.mInstallerPackageUid);
            String str5 = packageSetting2.installSource.mUpdateOwnerPackageName;
            if (str5 == null) {
                str5 = "?";
            }
            printWriter.print(str5);
            printWriter.print(packageSetting2.installSource.mInstallerAttributionTag != null ? AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("("), packageSetting2.installSource.mInstallerAttributionTag, ")") : "");
            printWriter.print(",");
            printWriter.print(packageSetting2.installSource.mPackageSource);
            printWriter.println();
            if (androidPackageInternal != null) {
                printWriter.print(str);
                printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                printWriter.print("splt,");
                printWriter.print("base,");
                printWriter.println(androidPackageInternal.getBaseRevisionCode());
                int[] splitRevisionCodes = androidPackageInternal.getSplitRevisionCodes();
                for (int i = 0; i < androidPackageInternal.getSplitNames().length; i++) {
                    printWriter.print(str);
                    printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                    printWriter.print("splt,");
                    printWriter.print(androidPackageInternal.getSplitNames()[i]);
                    printWriter.print(",");
                    printWriter.println(splitRevisionCodes[i]);
                }
            }
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                UserInfo userInfo = (UserInfo) it.next();
                PackageUserStateInternal userStateOrDefault = packageSetting2.getUserStateOrDefault(userInfo.id);
                printWriter.print(str);
                printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                printWriter.print("usr");
                printWriter.print(",");
                printWriter.print(userInfo.id);
                printWriter.print(",");
                printWriter.print(userStateOrDefault.isInstalled() ? "I" : "i");
                printWriter.print(userStateOrDefault.isHidden() ? "B" : "b");
                printWriter.print(userStateOrDefault.isSuspended() ? "SU" : "su");
                printWriter.print(userStateOrDefault.isStopped() ? "S" : "s");
                printWriter.print(userStateOrDefault.isNotLaunched() ? "l" : "L");
                printWriter.print(userStateOrDefault.isInstantApp() ? "IA" : "ia");
                printWriter.print(userStateOrDefault.isVirtualPreload() ? "VPI" : "vpi");
                printWriter.print(userStateOrDefault.isQuarantined() ? "Q" : "q");
                printWriter.print(userStateOrDefault.getHarmfulAppWarning() != null ? "HA" : "ha");
                printWriter.print(",");
                printWriter.print(userStateOrDefault.getEnabledState());
                String lastDisableAppCaller = userStateOrDefault.getLastDisableAppCaller();
                printWriter.print(",");
                if (lastDisableAppCaller == null) {
                    lastDisableAppCaller = "?";
                }
                printWriter.print(lastDisableAppCaller);
                printWriter.print(",");
                printWriter.print(packageSetting2.readUserState(userInfo.id).getFirstInstallTimeMillis());
                printWriter.print(",");
                printWriter.println();
            }
            return;
        }
        printWriter.print("  ");
        printWriter.print("Package [");
        String str6 = packageSetting2.mRealName;
        if (str6 == null) {
            str6 = packageSetting2.mName;
        }
        printWriter.print(str6);
        printWriter.print("] (");
        printWriter.print(Integer.toHexString(System.identityHashCode(packageSetting)));
        printWriter.println("):");
        if (packageSetting2.mRealName != null) {
            printWriter.print("  ");
            printWriter.print("  compat name=");
            printWriter.println(packageSetting2.mName);
        }
        printWriter.print("  ");
        printWriter.print("  appId=");
        printWriter.println(packageSetting2.mAppId);
        Object sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting2);
        if (sharedUserSettingLPr != null) {
            printWriter.print("  ");
            printWriter.print("  sharedUser=");
            printWriter.println(sharedUserSettingLPr);
        }
        printWriter.print("  ");
        printWriter.print("  pkg=");
        printWriter.println(androidPackageInternal);
        printWriter.print("  ");
        printWriter.print("  codePath=");
        printWriter.println(packageSetting2.mPathString);
        LinkedHashSet linkedHashSet = packageSetting2.mOldPaths;
        if (linkedHashSet != null && linkedHashSet.size() > 0) {
            Iterator it2 = packageSetting2.mOldPaths.iterator();
            while (it2.hasNext()) {
                File file = (File) it2.next();
                StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "  ", "    oldCodePath=");
                m.append(file.getAbsolutePath());
                printWriter.println(m.toString());
            }
        }
        if (arraySet == null) {
            printWriter.print("  ");
            printWriter.print("  resourcePath=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.mPathString, "  ", "  legacyNativeLibraryDir=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.legacyNativeLibraryPath, "  ", "  extractNativeLibs=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, (packageSetting2.mPkgFlags & 268435456) != 0 ? "true" : "false", "  ", "  primaryCpuAbi=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.mPrimaryCpuAbi, "  ", "  secondaryCpuAbi=");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.mSecondaryCpuAbi, "  ", "  cpuAbiOverride=");
            printWriter.println(packageSetting2.mCpuAbiOverride);
        }
        printWriter.print("  ");
        printWriter.print("  versionCode=");
        printWriter.print(packageSetting2.versionCode);
        if (androidPackageInternal != null) {
            printWriter.print(" minSdk=");
            printWriter.print(androidPackageInternal.getMinSdkVersion());
        }
        printWriter.print(" targetSdk=");
        printWriter.println(packageSetting2.mTargetSdkVersion);
        if (androidPackageInternal != null) {
            SparseIntArray minExtensionVersions = androidPackageInternal.getMinExtensionVersions();
            printWriter.print("  ");
            printWriter.print("  minExtensionVersions=[");
            if (minExtensionVersions != null) {
                ArrayList arrayList = new ArrayList();
                int size = minExtensionVersions.size();
                int i2 = 0;
                while (i2 < size) {
                    int i3 = size;
                    arrayList.add(minExtensionVersions.keyAt(i2) + "=" + minExtensionVersions.valueAt(i2));
                    i2++;
                    size = i3;
                    minExtensionVersions = minExtensionVersions;
                }
                printWriter.print(TextUtils.join(", ", arrayList));
            }
            printWriter.print("]");
        }
        printWriter.println();
        Object[] objArr = PRIVATE_FLAG_DUMP_SPEC;
        Object[] objArr2 = FLAG_DUMP_SPEC;
        String str7 = "  pendingRestore=true";
        if (androidPackageInternal != null) {
            printWriter.print("  ");
            printWriter.print("  versionName=");
            printWriter.println(androidPackageInternal.getVersionName());
            printWriter.print("  ");
            printWriter.print("  hiddenApiEnforcementPolicy=");
            printWriter.println(AndroidPackageUtils.getHiddenApiEnforcementPolicy(packageSetting2.pkg, packageSetting2));
            printWriter.print("  ");
            printWriter.print("  usesNonSdkApi=");
            printWriter.println(androidPackageInternal.isNonSdkApiRequested());
            printWriter.print("  ");
            printWriter.print("  splits=");
            printWriter.print("[");
            printWriter.print("base");
            if (androidPackageInternal.getBaseRevisionCode() != 0) {
                printWriter.print(":");
                printWriter.print(androidPackageInternal.getBaseRevisionCode());
            }
            String[] splitNames = androidPackageInternal.getSplitNames();
            int[] splitRevisionCodes2 = androidPackageInternal.getSplitRevisionCodes();
            for (int i4 = 0; i4 < splitNames.length; i4++) {
                printWriter.print(", ");
                printWriter.print(splitNames[i4]);
                if (splitRevisionCodes2[i4] != 0) {
                    printWriter.print(":");
                    printWriter.print(splitRevisionCodes2[i4]);
                }
            }
            printWriter.print("]");
            printWriter.println();
            int signatureSchemeVersion = androidPackageInternal.getSigningDetails().getSignatureSchemeVersion();
            printWriter.print("  ");
            printWriter.print("  apkSigningVersion=");
            printWriter.println(signatureSchemeVersion);
            printWriter.print("  ");
            printWriter.print("  flags=");
            printFlags(printWriter, PackageInfoUtils.appInfoFlags(androidPackageInternal, packageSetting2), objArr2);
            printWriter.println();
            int appInfoPrivateFlags = PackageInfoUtils.appInfoPrivateFlags(androidPackageInternal);
            if (appInfoPrivateFlags != 0) {
                printWriter.print("  ");
                printWriter.print("  privateFlags=");
                printFlags(printWriter, appInfoPrivateFlags, objArr);
                printWriter.println();
            }
            if (packageSetting2.getBoolean(16)) {
                printWriter.print("  ");
                printWriter.print("  pendingRestore=true");
                printWriter.println();
            }
            if (!androidPackageInternal.isUpdatableSystem()) {
                printWriter.print("  ");
                printWriter.print("  updatableSystem=false");
                printWriter.println();
            }
            if (androidPackageInternal.getEmergencyInstaller() != null) {
                printWriter.print("  ");
                printWriter.print("  emergencyInstaller=");
                printWriter.println(androidPackageInternal.getEmergencyInstaller());
            }
            if (androidPackageInternal.hasPreserveLegacyExternalStorage()) {
                printWriter.print("  ");
                printWriter.print("  hasPreserveLegacyExternalStorage=true");
                printWriter.println();
            }
            printWriter.print("  ");
            printWriter.print("  forceQueryable=");
            printWriter.print(packageSetting2.pkg.isForceQueryable());
            if (packageSetting2.getBoolean(4)) {
                printWriter.print(" (override=true)");
            }
            printWriter.println();
            if (!packageSetting2.pkg.getQueriesPackages().isEmpty()) {
                printWriter.append("  ").append("  queriesPackages=").println(packageSetting2.pkg.getQueriesPackages());
            }
            if (!packageSetting2.pkg.getQueriesIntents().isEmpty()) {
                printWriter.append("  ").append("  queriesIntents=").println(packageSetting2.pkg.getQueriesIntents());
            }
            printWriter.print("  ");
            printWriter.print("  scannedAsStoppedSystemApp=");
            printWriter.println(packageSetting2.getBoolean(8));
            printWriter.print("  ");
            printWriter.print("  supportsScreens=[");
            if (androidPackageInternal.isSmallScreensSupported()) {
                printWriter.print("small");
                z3 = false;
            } else {
                z3 = true;
            }
            if (androidPackageInternal.isNormalScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("medium");
                z3 = false;
            }
            if (androidPackageInternal.isLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("large");
                z3 = false;
            }
            if (androidPackageInternal.isExtraLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("xlarge");
                z3 = false;
            }
            if (androidPackageInternal.isResizeable()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("resizeable");
                z3 = false;
            }
            if (androidPackageInternal.isAnyDensity()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("anyDensity");
            }
            printWriter.println("]");
            List libraryNames = androidPackageInternal.getLibraryNames();
            if (libraryNames != null && libraryNames.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  dynamic libraries:");
                for (int i5 = 0; i5 < libraryNames.size(); i5++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) libraryNames.get(i5));
                }
            }
            if (androidPackageInternal.getStaticSharedLibraryName() != null) {
                printWriter.print("  ");
                printWriter.println("  static library:");
                printWriter.print("  ");
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(androidPackageInternal.getStaticSharedLibraryName());
                printWriter.print(" version:");
                printWriter.println(androidPackageInternal.getStaticSharedLibraryVersion());
            }
            if (androidPackageInternal.getSdkLibraryName() != null) {
                printWriter.print("  ");
                printWriter.println("  SDK library:");
                printWriter.print("  ");
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(androidPackageInternal.getSdkLibraryName());
                printWriter.print(" versionMajor:");
                printWriter.println(androidPackageInternal.getSdkLibVersionMajor());
            }
            List usesLibraries = androidPackageInternal.getUsesLibraries();
            if (usesLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesLibraries:");
                for (int i6 = 0; i6 < usesLibraries.size(); i6++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) usesLibraries.get(i6));
                }
            }
            List usesStaticLibraries = androidPackageInternal.getUsesStaticLibraries();
            long[] usesStaticLibrariesVersions = androidPackageInternal.getUsesStaticLibrariesVersions();
            if (usesStaticLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesStaticLibraries:");
                int i7 = 0;
                while (i7 < usesStaticLibraries.size()) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.print((String) usesStaticLibraries.get(i7));
                    printWriter.print(" version:");
                    printWriter.println(usesStaticLibrariesVersions[i7]);
                    i7++;
                    str7 = str7;
                }
            }
            str2 = str7;
            List usesSdkLibraries = androidPackageInternal.getUsesSdkLibraries();
            long[] usesSdkLibrariesVersionsMajor = androidPackageInternal.getUsesSdkLibrariesVersionsMajor();
            boolean[] usesSdkLibrariesOptional = androidPackageInternal.getUsesSdkLibrariesOptional();
            if (usesSdkLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesSdkLibraries:");
                int size2 = usesSdkLibraries.size();
                int i8 = 0;
                while (i8 < size2) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.print((String) usesSdkLibraries.get(i8));
                    printWriter.print(" version:");
                    printWriter.println(usesSdkLibrariesVersionsMajor[i8]);
                    printWriter.print(" optional:");
                    printWriter.println(usesSdkLibrariesOptional[i8]);
                    i8++;
                    usesSdkLibraries = usesSdkLibraries;
                }
            }
            List usesOptionalLibraries = androidPackageInternal.getUsesOptionalLibraries();
            if (usesOptionalLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesOptionalLibraries:");
                for (int i9 = 0; i9 < usesOptionalLibraries.size(); i9++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) usesOptionalLibraries.get(i9));
                }
            }
            List usesNativeLibraries = androidPackageInternal.getUsesNativeLibraries();
            if (usesNativeLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesNativeLibraries:");
                for (int i10 = 0; i10 < usesNativeLibraries.size(); i10++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) usesNativeLibraries.get(i10));
                }
            }
            List usesOptionalNativeLibraries = androidPackageInternal.getUsesOptionalNativeLibraries();
            if (usesOptionalNativeLibraries.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesOptionalNativeLibraries:");
                for (int i11 = 0; i11 < usesOptionalNativeLibraries.size(); i11++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) usesOptionalNativeLibraries.get(i11));
                }
            }
            List list3 = packageSetting2.pkgState.usesLibraryFiles;
            if (list3.size() > 0) {
                printWriter.print("  ");
                printWriter.println("  usesLibraryFiles:");
                for (int i12 = 0; i12 < list3.size(); i12++) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println((String) list3.get(i12));
                }
            }
            Map processes = androidPackageInternal.getProcesses();
            if (!processes.isEmpty()) {
                printWriter.print("  ");
                printWriter.println("  processes:");
                for (ParsedProcess parsedProcess : processes.values()) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println(parsedProcess.getName());
                    if (parsedProcess.getDeniedPermissions() != null) {
                        for (String str8 : parsedProcess.getDeniedPermissions()) {
                            printWriter.print("  ");
                            printWriter.print("      deny: ");
                            printWriter.println(str8);
                        }
                    }
                }
            }
        } else {
            str2 = "  pendingRestore=true";
        }
        printWriter.print("  ");
        printWriter.print("  timeStamp=");
        date.setTime(packageSetting2.mLastModifiedTime);
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print("  ");
        printWriter.print("  lastUpdateTime=");
        date.setTime(packageSetting2.lastUpdateTime);
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print("  ");
        printWriter.print("  installerPackageName=");
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.installSource.mInstallerPackageName, "  ", "  installerPackageUid=");
        BroadcastStats$$ExternalSyntheticOutline0.m(packageSetting2.installSource.mInstallerPackageUid, printWriter, "  ", "  initiatingPackageName=");
        ProcessList$$ExternalSyntheticOutline0.m(printWriter, packageSetting2.installSource.mInitiatingPackageName, "  ", "  originatingPackageName=");
        printWriter.println(packageSetting2.installSource.mOriginatingPackageName);
        if (packageSetting2.installSource.mUpdateOwnerPackageName != null) {
            printWriter.print("  ");
            printWriter.print("  updateOwnerPackageName=");
            printWriter.println(packageSetting2.installSource.mUpdateOwnerPackageName);
        }
        if (packageSetting2.installSource.mInstallerAttributionTag != null) {
            printWriter.print("  ");
            printWriter.print("  installerAttributionTag=");
            printWriter.println(packageSetting2.installSource.mInstallerAttributionTag);
        }
        int i13 = packageSetting2.categoryOverride;
        int category = androidPackageInternal != null ? androidPackageInternal.getCategory() : -1;
        printWriter.print("  ");
        printWriter.print("  category=manifest: " + category);
        printWriter.print(", override: " + i13);
        printWriter.print(", by user: " + PackageManagerService.sAppCategoryHintHelper.getAppCategoryHintUser(packageSetting2.mName));
        printWriter.println();
        printWriter.print("  ");
        printWriter.print("  packageSource=");
        printWriter.println(packageSetting2.installSource.mPackageSource);
        if (IncrementalManager.isIncrementalPath(packageSetting2.mPathString)) {
            StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "  ", "  loadingProgress=");
            m2.append((int) (packageSetting.getLoadingProgress() * 100.0f));
            m2.append("%");
            printWriter.println(m2.toString());
            date.setTime(packageSetting.getLoadingCompletedTime());
            printWriter.print("  ");
            printWriter.println("  loadingCompletedTime=" + simpleDateFormat.format(date));
        }
        printWriter.print("  ");
        printWriter.print("  appMetadataFilePath=");
        printWriter.println(packageSetting.getAppMetadataFilePath());
        printWriter.print("  ");
        printWriter.print("  appMetadataSource=");
        printWriter.println(packageSetting.getAppMetadataSource());
        if (packageSetting.getVolumeUuid() != null) {
            printWriter.print("  ");
            printWriter.print("  volumeUuid=");
            printWriter.println(packageSetting.getVolumeUuid());
        }
        printWriter.print("  ");
        printWriter.print("  signatures=");
        printWriter.println(packageSetting2.signatures);
        printWriter.print("  ");
        printWriter.print("  installPermissionsFixed=");
        printWriter.print(packageSetting.isInstallPermissionsFixed());
        printWriter.println();
        printWriter.print("  ");
        printWriter.print("  pkgFlags=");
        printFlags(printWriter, packageSetting.getFlags(), objArr2);
        printWriter.println();
        printWriter.print("  ");
        printWriter.print("  privatePkgFlags=");
        printFlags(printWriter, packageSetting.getPrivateFlags(), objArr);
        printWriter.println();
        if (packageSetting.isPendingRestore()) {
            printWriter.print("  ");
            printWriter.println(str2);
        }
        printWriter.print("  ");
        printWriter.print("  apexModuleName=");
        printWriter.println(packageSetting.getApexModuleName());
        if (androidPackageInternal != null && androidPackageInternal.getOverlayTarget() != null) {
            printWriter.print("  ");
            printWriter.print("  overlayTarget=");
            printWriter.println(androidPackageInternal.getOverlayTarget());
            printWriter.print("  ");
            printWriter.print("  overlayCategory=");
            printWriter.println(androidPackageInternal.getOverlayCategory());
        }
        if (androidPackageInternal != null) {
            printWriter.print("  ");
            printWriter.print("  componentsDeclared=");
            printWriter.println(androidPackageInternal.getServices().size() + androidPackageInternal.getProviders().size() + androidPackageInternal.getActivities().size());
        }
        if (androidPackageInternal != null && !androidPackageInternal.getPermissions().isEmpty()) {
            List permissions = androidPackageInternal.getPermissions();
            printWriter.print("  ");
            printWriter.println("  declared permissions:");
            for (int i14 = 0; i14 < permissions.size(); i14++) {
                ParsedPermission parsedPermission = (ParsedPermission) permissions.get(i14);
                if (arraySet == null || arraySet.contains(parsedPermission.getName())) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.print(parsedPermission.getName());
                    printWriter.print(": prot=");
                    printWriter.print(PermissionInfo.protectionToString(parsedPermission.getProtectionLevel()));
                    if ((parsedPermission.getFlags() & 1) != 0) {
                        printWriter.print(", COSTS_MONEY");
                    }
                    if ((parsedPermission.getFlags() & 2) != 0) {
                        printWriter.print(", HIDDEN");
                    }
                    if ((parsedPermission.getFlags() & 1073741824) != 0) {
                        printWriter.print(", INSTALLED");
                    }
                    printWriter.println();
                }
            }
        }
        if ((arraySet != null || z) && androidPackageInternal != null && androidPackageInternal.getRequestedPermissions() != null && androidPackageInternal.getRequestedPermissions().size() > 0) {
            Set<String> requestedPermissions = androidPackageInternal.getRequestedPermissions();
            printWriter.print("  ");
            printWriter.println("  requested permissions:");
            for (String str9 : requestedPermissions) {
                if (arraySet == null || arraySet.contains(str9)) {
                    printWriter.print("  ");
                    printWriter.print("    ");
                    printWriter.println(str9);
                }
            }
        }
        if (packageSetting.hasSharedUser() && arraySet == null && !z) {
            legacyPermissionState2 = legacyPermissionState;
            list2 = list;
        } else {
            legacyPermissionState2 = legacyPermissionState;
            list2 = list;
            dumpInstallPermissionsLPr(printWriter, "    ", arraySet, legacyPermissionState2, list2);
        }
        if (z2) {
            dumpComponents(printWriter, "    ", "activities:", packageSetting2.pkg.getActivities());
            dumpComponents(printWriter, "    ", "services:", packageSetting2.pkg.getServices());
            dumpComponents(printWriter, "    ", "receivers:", packageSetting2.pkg.getReceivers());
            dumpComponents(printWriter, "    ", "providers:", packageSetting2.pkg.getProviders());
            dumpComponents(printWriter, "    ", "instrumentations:", packageSetting2.pkg.getInstrumentations());
        }
        Iterator it3 = ((ArrayList) list2).iterator();
        while (it3.hasNext()) {
            UserInfo userInfo2 = (UserInfo) it3.next();
            PackageUserStateInternal userStateOrDefault2 = packageSetting2.getUserStateOrDefault(userInfo2.id);
            printWriter.print("  ");
            printWriter.print("  User ");
            printWriter.print(userInfo2.id);
            printWriter.print(": ");
            printWriter.print("ceDataInode=");
            printWriter.print(userStateOrDefault2.getCeDataInode());
            printWriter.print(" deDataInode=");
            printWriter.print(userStateOrDefault2.getDeDataInode());
            printWriter.print(" installed=");
            printWriter.print(userStateOrDefault2.isInstalled());
            printWriter.print(" hidden=");
            printWriter.print(userStateOrDefault2.isHidden());
            printWriter.print(" suspended=");
            printWriter.print(userStateOrDefault2.isSuspended());
            printWriter.print(" distractionFlags=");
            printWriter.print(userStateOrDefault2.getDistractionFlags());
            printWriter.print(" stopped=");
            printWriter.print(userStateOrDefault2.isStopped());
            printWriter.print(" notLaunched=");
            printWriter.print(userStateOrDefault2.isNotLaunched());
            printWriter.print(" enabled=");
            printWriter.print(userStateOrDefault2.getEnabledState());
            printWriter.print(" instant=");
            printWriter.print(userStateOrDefault2.isInstantApp());
            printWriter.print(" virtual=");
            printWriter.print(userStateOrDefault2.isVirtualPreload());
            printWriter.print(" quarantined=");
            printWriter.print(userStateOrDefault2.isQuarantined());
            printWriter.println();
            printWriter.print("      installReason=");
            printWriter.println(userStateOrDefault2.getInstallReason());
            File dataDir = PackageInfoUtils.getDataDir(packageSetting2, userInfo2.id);
            printWriter.print("      dataDir=");
            printWriter.println(dataDir == null ? "null" : dataDir.getAbsolutePath());
            PackageUserStateInternal readUserState = packageSetting2.readUserState(userInfo2.id);
            printWriter.print("      firstInstallTime=");
            date.setTime(readUserState.getFirstInstallTimeMillis());
            printWriter.println(simpleDateFormat.format(date));
            if (readUserState.getArchiveState() != null) {
                ArchiveState archiveState = readUserState.getArchiveState();
                printWriter.print("      archiveTime=");
                date.setTime(archiveState.mArchiveTimeMillis);
                printWriter.println(simpleDateFormat.format(date));
                printWriter.print("      unarchiveInstallerTitle=");
                printWriter.println(archiveState.mInstallerTitle);
                for (ArchiveState.ArchiveActivityInfo archiveActivityInfo : archiveState.mActivityInfos) {
                    printWriter.print("        archiveActivityInfo=");
                    printWriter.println(archiveActivityInfo.toString());
                }
            }
            printWriter.print("      uninstallReason=");
            printWriter.println(userStateOrDefault2.getUninstallReason());
            if (userStateOrDefault2.isSuspended()) {
                printWriter.print("  ");
                printWriter.println("  Suspend params:");
                for (int i15 = 0; i15 < userStateOrDefault2.getSuspendParams().size(); i15++) {
                    printWriter.print("  ");
                    printWriter.print("    suspendingPackage=");
                    printWriter.print(userStateOrDefault2.getSuspendParams().mStorage.keyAt(i15));
                    SuspendParams suspendParams = (SuspendParams) userStateOrDefault2.getSuspendParams().mStorage.valueAt(i15);
                    if (suspendParams != null) {
                        printWriter.print(" dialogInfo=");
                        printWriter.print(suspendParams.mDialogInfo);
                        printWriter.print(" quarantined=");
                        printWriter.println(suspendParams.mQuarantined);
                    }
                    printWriter.println();
                }
            }
            OverlayPaths overlayPaths = userStateOrDefault2.getOverlayPaths();
            if (overlayPaths != null) {
                if (!overlayPaths.getOverlayPaths().isEmpty()) {
                    printWriter.print("  ");
                    printWriter.println("    overlay paths:");
                    for (String str10 : overlayPaths.getOverlayPaths()) {
                        printWriter.print("  ");
                        printWriter.print("      ");
                        printWriter.println(str10);
                    }
                }
                if (!overlayPaths.getResourceDirs().isEmpty()) {
                    printWriter.print("  ");
                    printWriter.println("    legacy overlay paths:");
                    for (String str11 : overlayPaths.getResourceDirs()) {
                        printWriter.print("  ");
                        printWriter.print("      ");
                        printWriter.println(str11);
                    }
                }
            }
            Map sharedLibraryOverlayPaths = userStateOrDefault2.getSharedLibraryOverlayPaths();
            if (sharedLibraryOverlayPaths != null) {
                for (Map.Entry entry : sharedLibraryOverlayPaths.entrySet()) {
                    OverlayPaths overlayPaths2 = (OverlayPaths) entry.getValue();
                    if (overlayPaths2 != null) {
                        if (!overlayPaths2.getOverlayPaths().isEmpty()) {
                            printWriter.print("  ");
                            printWriter.println("    ");
                            printWriter.print((String) entry.getKey());
                            printWriter.println(" overlay paths:");
                            for (String str12 : overlayPaths2.getOverlayPaths()) {
                                printWriter.print("  ");
                                printWriter.print("        ");
                                printWriter.println(str12);
                                it3 = it3;
                            }
                        }
                        Iterator it4 = it3;
                        if (!overlayPaths2.getResourceDirs().isEmpty()) {
                            printWriter.print("  ");
                            printWriter.println("      ");
                            printWriter.print((String) entry.getKey());
                            printWriter.println(" legacy overlay paths:");
                            for (String str13 : overlayPaths2.getResourceDirs()) {
                                printWriter.print("  ");
                                printWriter.print("      ");
                                printWriter.println(str13);
                            }
                        }
                        it3 = it4;
                    }
                }
            }
            Iterator it5 = it3;
            String lastDisableAppCaller2 = userStateOrDefault2.getLastDisableAppCaller();
            if (lastDisableAppCaller2 != null) {
                printWriter.print("  ");
                printWriter.print("    lastDisabledCaller: ");
                printWriter.println(lastDisableAppCaller2);
            }
            if (!packageSetting.hasSharedUser()) {
                dumpGidsLPr(printWriter, "      ", this.mPermissionDataProvider.getGidsForUid(UserHandle.getUid(userInfo2.id, packageSetting.getAppId())));
                dumpRuntimePermissionsLPr(printWriter, "      ", arraySet, legacyPermissionState2.getPermissionStates(userInfo2.id), z);
            }
            String harmfulAppWarning = userStateOrDefault2.getHarmfulAppWarning();
            if (harmfulAppWarning != null) {
                printWriter.print("  ");
                printWriter.print("      harmfulAppWarning: ");
                printWriter.println(harmfulAppWarning);
            }
            if (arraySet == null) {
                WatchedArraySet disabledComponentsNoCopy = userStateOrDefault2.getDisabledComponentsNoCopy();
                if (disabledComponentsNoCopy != null && disabledComponentsNoCopy.mStorage.size() > 0) {
                    printWriter.print("  ");
                    printWriter.println("    disabledComponents:");
                    for (int i16 = 0; i16 < disabledComponentsNoCopy.mStorage.size(); i16++) {
                        printWriter.print("  ");
                        printWriter.print("      ");
                        printWriter.println((String) disabledComponentsNoCopy.mStorage.valueAt(i16));
                    }
                }
                WatchedArraySet enabledComponentsNoCopy = userStateOrDefault2.getEnabledComponentsNoCopy();
                if (enabledComponentsNoCopy != null && enabledComponentsNoCopy.mStorage.size() > 0) {
                    printWriter.print("  ");
                    printWriter.println("    enabledComponents:");
                    for (int i17 = 0; i17 < enabledComponentsNoCopy.mStorage.size(); i17++) {
                        printWriter.print("  ");
                        printWriter.print("      ");
                        printWriter.println((String) enabledComponentsNoCopy.mStorage.valueAt(i17));
                    }
                }
            }
            it3 = it5;
            packageSetting2 = packageSetting;
        }
    }

    public final CrossProfileIntentResolver editCrossProfileIntentResolverLPw(int i) {
        WatchedSparseArray watchedSparseArray = this.mCrossProfileIntentResolvers;
        CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) watchedSparseArray.mStorage.get(i);
        if (crossProfileIntentResolver != null) {
            return crossProfileIntentResolver;
        }
        CrossProfileIntentResolver crossProfileIntentResolver2 = new CrossProfileIntentResolver();
        watchedSparseArray.put(i, crossProfileIntentResolver2);
        return crossProfileIntentResolver2;
    }

    public final PreferredIntentResolver editPreferredActivitiesLPw(int i) {
        WatchedSparseArray watchedSparseArray = this.mPreferredActivities;
        PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) watchedSparseArray.mStorage.get(i);
        if (preferredIntentResolver != null) {
            return preferredIntentResolver;
        }
        PreferredIntentResolver preferredIntentResolver2 = new PreferredIntentResolver();
        watchedSparseArray.put(i, preferredIntentResolver2);
        return preferredIntentResolver2;
    }

    public final void enableSystemPackageLPw(String str) {
        PackageSetting packageSetting = (PackageSetting) this.mDisabledSysPackages.mStorage.get(str);
        if (packageSetting == null) {
            Log.w("PackageManager", "Package " + str + " is not disabled");
            return;
        }
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.mDisabledPackages.remove(packageSetting);
        }
        packageSetting.pkgState.setUpdatedSystemApp(false);
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        String str2 = packageSetting.mRealName;
        File file = packageSetting.mPath;
        int i = packageSetting.mAppId;
        int i2 = packageSetting.mPkgFlags;
        int i3 = packageSetting.mPkgPrivateFlags;
        ((DomainVerificationService) this.mDomainVerificationManager).getClass();
        PackageSetting addPackageLPw = addPackageLPw(str, str2, file, i, i2, i3, UUID.randomUUID(), androidPackageInternal == null ? false : androidPackageInternal.isSdkLibrary());
        if (addPackageLPw != null) {
            addPackageLPw.legacyNativeLibraryPath = packageSetting.legacyNativeLibraryPath;
            addPackageLPw.onChanged$2();
            addPackageLPw.mPrimaryCpuAbi = packageSetting.mPrimaryCpuAbi;
            addPackageLPw.onChanged$2();
            addPackageLPw.mSecondaryCpuAbi = packageSetting.mSecondaryCpuAbi;
            addPackageLPw.onChanged$2();
            addPackageLPw.mCpuAbiOverride = packageSetting.mCpuAbiOverride;
            addPackageLPw.onChanged$2();
            addPackageLPw.versionCode = packageSetting.versionCode;
            addPackageLPw.onChanged$2();
            addPackageLPw.usesSdkLibraries = packageSetting.getUsesSdkLibraries();
            addPackageLPw.onChanged$2();
            addPackageLPw.usesSdkLibrariesVersionsMajor = packageSetting.getUsesSdkLibrariesVersionsMajor();
            addPackageLPw.onChanged$2();
            addPackageLPw.usesSdkLibrariesOptional = packageSetting.getUsesSdkLibrariesOptional();
            addPackageLPw.onChanged$2();
            addPackageLPw.usesStaticLibraries = packageSetting.getUsesStaticLibraries();
            addPackageLPw.onChanged$2();
            addPackageLPw.usesStaticLibrariesVersions = packageSetting.getUsesStaticLibrariesVersions();
            addPackageLPw.onChanged$2();
            Map mimeGroups = packageSetting.getMimeGroups();
            if (mimeGroups != null) {
                addPackageLPw.copyMimeGroups(mimeGroups);
                addPackageLPw.onChanged$2();
            }
            addPackageLPw.setAppMetadataFilePath(packageSetting.mAppMetadataFilePath);
            addPackageLPw.setAppMetadataSource(packageSetting.mAppMetadataSource);
            addPackageLPw.pkgState.setUpdatedSystemApp(false);
            addPackageLPw.mTargetSdkVersion = packageSetting.mTargetSdkVersion;
            addPackageLPw.onChanged$2();
            addPackageLPw.mRestrictUpdateHash = packageSetting.mRestrictUpdateHash;
            addPackageLPw.onChanged$2();
            addPackageLPw.setBoolean(8, packageSetting.getBoolean(8));
            addPackageLPw.onChanged$2();
            InstallSource installSource = packageSetting.installSource;
            Objects.requireNonNull(installSource);
            addPackageLPw.installSource = installSource;
            addPackageLPw.onChanged$2();
        }
        this.mDisabledSysPackages.remove(str);
    }

    public final VersionInfo findOrCreateVersion(String str) {
        WatchedArrayMap watchedArrayMap = this.mVersion;
        VersionInfo versionInfo = (VersionInfo) watchedArrayMap.mStorage.get(str);
        if (versionInfo != null) {
            return versionInfo;
        }
        VersionInfo versionInfo2 = new VersionInfo();
        watchedArrayMap.put(str, versionInfo2);
        return versionInfo2;
    }

    public final Collection getAllSharedUsersLPw() {
        return this.mSharedUsers.values();
    }

    public final PackageSetting getDisabledSystemPkgLPr(String str) {
        return (PackageSetting) this.mDisabledSysPackages.mStorage.get(str);
    }

    public final VersionInfo getInternalVersion() {
        return (VersionInfo) this.mVersion.mStorage.get(StorageManager.UUID_PRIVATE_INTERNAL);
    }

    public final RuntimePermissionsState getLegacyPermissionsState(int i) {
        int i2;
        String str;
        RuntimePermissionPersistence runtimePermissionPersistence = this.mRuntimePermissionsPersistence;
        WatchedArrayMap watchedArrayMap = this.mPackages;
        WatchedArrayMap watchedArrayMap2 = this.mSharedUsers;
        synchronized (runtimePermissionPersistence.mLock) {
            i2 = runtimePermissionPersistence.mVersions.get(i, 0);
            str = (String) runtimePermissionPersistence.mFingerprints.get(i);
        }
        return new RuntimePermissionsState(i2, str, RuntimePermissionPersistence.getPackagePermissions(i, watchedArrayMap), RuntimePermissionPersistence.getShareUsersPermissions(i, watchedArrayMap2));
    }

    public final PackageSetting getPackageLPr(String str) {
        return (PackageSetting) this.mPackages.mStorage.get(str);
    }

    public final String getRenamedPackageLPr(String str) {
        return (String) this.mRenamedPackages.mStorage.get(str);
    }

    public final SettingBase getSettingLPr(int i) {
        AppIdSettingMap appIdSettingMap = this.mAppIds;
        if (i < 10000) {
            return (SettingBase) appIdSettingMap.mSystemSettings.mStorage.get(i);
        }
        WatchedArrayList watchedArrayList = appIdSettingMap.mNonSystemSettings;
        int i2 = i - 10000;
        if (i2 < watchedArrayList.mStorage.size()) {
            return (SettingBase) watchedArrayList.mStorage.get(i2);
        }
        return null;
    }

    public final SharedUserSetting getSharedUserLPw(String str, boolean z) {
        WatchedArrayMap watchedArrayMap = this.mSharedUsers;
        SharedUserSetting sharedUserSetting = (SharedUserSetting) watchedArrayMap.mStorage.get(str);
        if (sharedUserSetting == null && z) {
            sharedUserSetting = new SharedUserSetting(0, 0, str);
            int acquireAndRegisterNewAppId = this.mAppIds.acquireAndRegisterNewAppId(sharedUserSetting);
            sharedUserSetting.mAppId = acquireAndRegisterNewAppId;
            if (acquireAndRegisterNewAppId < 0) {
                throw new PackageManagerException(-4, XmlUtils$$ExternalSyntheticOutline0.m("Creating shared user ", str, " failed"));
            }
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("New shared user ", str, ": id="), sharedUserSetting.mAppId, "PackageManager");
            watchedArrayMap.put(str, sharedUserSetting);
        }
        return sharedUserSetting;
    }

    public final SharedUserSetting getSharedUserSettingLPr(PackageSetting packageSetting) {
        if (packageSetting == null || !packageSetting.hasSharedUser()) {
            return null;
        }
        return (SharedUserSetting) getSettingLPr(packageSetting.mSharedUserAppId);
    }

    public final ResilientAtomicFile getUserPackagesStateFile(int i) {
        return new ResilientAtomicFile(new File(getUserSystemDirectory(i), "package-restrictions.xml"), new File(getUserSystemDirectory(i), "package-restrictions-backup.xml"), new File(getUserSystemDirectory(i), "package-restrictions.xml.reservecopy"), FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, "package restrictions", this);
    }

    public final File getUserSystemDirectory(int i) {
        return new File(new File(this.mSystemDir, "users"), Integer.toString(i));
    }

    public final List getVolumePackagesLPr(String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPackages.mStorage.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) this.mPackages.mStorage.valueAt(i);
            if (Objects.equals(str, packageSetting.volumeUuid)) {
                arrayList.add(packageSetting);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
    public final void logEvent(int i, String str) {
        this.mReadMessages.append(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "\n"));
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        PackageManagerServiceUtils.logCriticalInfo(i, str);
    }

    public final void readBlockUninstallPackagesLPw(int i, TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        ArraySet arraySet = new ArraySet();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("block-uninstall")) {
                    arraySet.add(typedXmlPullParser.getAttributeValue((String) null, "packageName"));
                } else {
                    String m = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under block-uninstall-packages: "));
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        boolean isEmpty = arraySet.isEmpty();
        WatchedSparseArray watchedSparseArray = this.mBlockUninstallPackages;
        if (isEmpty) {
            watchedSparseArray.delete(i);
        } else {
            watchedSparseArray.put(i, arraySet);
        }
    }

    public final void readCrossProfileIntentFiltersLPw(int i, TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("item")) {
                    editCrossProfileIntentResolverLPw(i).addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) new CrossProfileIntentFilter(typedXmlPullParser));
                } else {
                    String concat = "Unknown element under crossProfile-intent-filters: ".concat(name);
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, concat);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readDefaultPreferredActivitiesLPw(int i, TypedXmlPullParser typedXmlPullParser) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    PreferredActivity preferredActivity = new PreferredActivity(typedXmlPullParser);
                    PreferredComponent preferredComponent = preferredActivity.mPref;
                    if (preferredComponent.mParseError == null) {
                        applyDefaultPreferredActivityLPw(packageManagerInternal, preferredActivity.mFilter, preferredComponent.mComponent, i);
                    } else {
                        String str = "Error in package manager settings: <preferred-activity> " + preferredComponent.mParseError + " at " + typedXmlPullParser.getPositionDescription();
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        PackageManagerServiceUtils.logCriticalInfo(5, str);
                    }
                } else {
                    String m = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <preferred-activities>: "));
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readDisabledSysPackageLPw(TypedXmlPullParser typedXmlPullParser, List list) {
        LegacyPermissionState legacyPermissionState;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "realName");
        String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "codePath");
        String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "requiredCpuAbi");
        String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "nativeLibraryPath");
        String attributeValue6 = typedXmlPullParser.getAttributeValue((String) null, "primaryCpuAbi");
        String attributeValue7 = typedXmlPullParser.getAttributeValue((String) null, "secondaryCpuAbi");
        String attributeValue8 = typedXmlPullParser.getAttributeValue((String) null, "cpuAbiOverride");
        String str = (attributeValue6 != null || attributeValue4 == null) ? attributeValue6 : attributeValue4;
        long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "version", 0L);
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "targetSdkVersion", 0);
        byte[] attributeBytesBase64 = typedXmlPullParser.getAttributeBytesBase64((String) null, "restrictUpdateHash", (byte[]) null);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "scannedAsStoppedSystemApp", false);
        PackageSetting packageSetting = new PackageSetting(attributeValue, attributeValue2, new File(attributeValue3), 1, attributeValue3.contains("/priv-app/") ? 8 : 0, DomainVerificationManagerInternal.DISABLED_ID);
        packageSetting.legacyNativeLibraryPath = attributeValue5;
        packageSetting.onChanged$2();
        packageSetting.mPrimaryCpuAbi = str;
        packageSetting.onChanged$2();
        packageSetting.mSecondaryCpuAbi = attributeValue7;
        packageSetting.onChanged$2();
        packageSetting.mCpuAbiOverride = attributeValue8;
        packageSetting.onChanged$2();
        packageSetting.versionCode = attributeLong;
        packageSetting.onChanged$2();
        packageSetting.mTargetSdkVersion = attributeInt;
        packageSetting.onChanged$2();
        packageSetting.mRestrictUpdateHash = attributeBytesBase64;
        packageSetting.onChanged$2();
        packageSetting.setBoolean(8, attributeBoolean);
        packageSetting.onChanged$2();
        long attributeLongHex = typedXmlPullParser.getAttributeLongHex((String) null, "ft", 0L);
        if (attributeLongHex == 0) {
            attributeLongHex = typedXmlPullParser.getAttributeLong((String) null, "ts", 0L);
        }
        packageSetting.mLastModifiedTime = attributeLongHex;
        packageSetting.onChanged$2();
        packageSetting.setLastUpdateTime(typedXmlPullParser.getAttributeLongHex((String) null, "ut", 0L));
        packageSetting.setAppId(typedXmlPullParser.getAttributeInt((String) null, "userId", 0));
        if (packageSetting.mAppId <= 0) {
            int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "sharedUserId", 0);
            packageSetting.setAppId(attributeInt2);
            packageSetting.setSharedUserAppId(attributeInt2);
        }
        packageSetting.setAppMetadataFilePath(typedXmlPullParser.getAttributeValue((String) null, "appMetadataFilePath"));
        packageSetting.setAppMetadataSource(typedXmlPullParser.getAttributeInt((String) null, "appMetadataSource", 0));
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("perms")) {
                    if (packageSetting.hasSharedUser()) {
                        SettingBase settingLPr = getSettingLPr(packageSetting.mSharedUserAppId);
                        legacyPermissionState = settingLPr != null ? settingLPr.getLegacyPermissionState() : null;
                    } else {
                        legacyPermissionState = packageSetting.mLegacyPermissionsState;
                    }
                    if (legacyPermissionState != null) {
                        readInstallPermissionsLPr(typedXmlPullParser, legacyPermissionState, list);
                    }
                } else if (typedXmlPullParser.getName().equals("uses-static-lib")) {
                    readUsesStaticLibLPw(typedXmlPullParser, packageSetting);
                } else if (typedXmlPullParser.getName().equals("uses-sdk-lib")) {
                    readUsesSdkLibLPw(typedXmlPullParser, packageSetting);
                } else {
                    String m = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <updated-package>: "));
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        this.mDisabledSysPackages.put(attributeValue, packageSetting);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x019c A[EDGE_INSN: B:106:0x019c->B:76:0x019c BREAK  A[LOOP:4: B:71:0x0193->B:74:0x019b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x013c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0199 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019e A[Catch: IOException -> 0x0182, XmlPullParserException -> 0x0185, TryCatch #5 {IOException -> 0x0182, XmlPullParserException -> 0x0185, blocks: (B:108:0x013c, B:110:0x0144, B:111:0x015a, B:113:0x0160, B:115:0x0188, B:70:0x018f, B:71:0x0193, B:77:0x019e, B:79:0x01ad, B:80:0x01b1, B:84:0x01ba, B:90:0x01c7, B:92:0x01d4, B:94:0x01e7, B:96:0x0205, B:97:0x0228, B:100:0x0214, B:101:0x022c, B:87:0x0249), top: B:107:0x013c }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01ad A[Catch: IOException -> 0x0182, XmlPullParserException -> 0x0185, TryCatch #5 {IOException -> 0x0182, XmlPullParserException -> 0x0185, blocks: (B:108:0x013c, B:110:0x0144, B:111:0x015a, B:113:0x0160, B:115:0x0188, B:70:0x018f, B:71:0x0193, B:77:0x019e, B:79:0x01ad, B:80:0x01b1, B:84:0x01ba, B:90:0x01c7, B:92:0x01d4, B:94:0x01e7, B:96:0x0205, B:97:0x0228, B:100:0x0214, B:101:0x022c, B:87:0x0249), top: B:107:0x013c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean readLPw(com.android.server.pm.ComputerLocked r17, java.util.List r18, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController r19) {
        /*
            Method dump skipped, instructions count: 857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readLPw(com.android.server.pm.ComputerLocked, java.util.List, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController):boolean");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:(6:344|(3:347|348|(1:350))|(1:(8:384|385|386|387|388|389|111|(9:113|(1:115)|116|(10:318|319|320|321|322|323|120|(2:121|(1:1)(2:(2:139|(1:316)(8:141|142|(2:144|(2:145|(2:(5:159|160|(2:162|(1:164)(1:168))(1:169)|165|166)|167)(2:172|150)))(2:173|(2:175|(2:176|(2:(5:185|186|(2:188|(1:190)(1:194))(1:195)|191|192)|193)(2:198|181)))(2:199|(5:201|152|153|154|155)(5:202|(5:204|(2:206|(1:208)(1:213))(1:214)|209|(1:211)|212)(2:215|(3:217|(1:219)(1:221)|220)(2:222|(2:224|(1:226)(2:227|(3:229|(1:231)(1:233)|232)(5:234|(3:236|(1:238)(1:241)|239)(2:242|(3:244|a16|249)(5:254|(3:256|(1:258)(4:268|(2:269|(1:1)(2:(3:281|282|(3:288|289|(3:291|292|293)(1:294))(3:284|285|286))|287))|275|276)|(5:260|(1:262)|263|(1:265)|266))(2:298|(1:300)(2:301|(1:303)(4:304|(4:306|(2:309|(1:311)(2:312|313))|314|313)(1:315)|154|155)))|267|154|155))|240|154|155)))))|153|154|155)))|151|152|153|154|155))(3:135|136|137)|138))|128|(2:130|131)(1:133))(1:118)|119|120|(3:121|(3:123|125|127)(1:317)|138)|128|(0)(0))(2:339|340))(2:382|383))(6:394|395|396|397|398|399)|110|111|(0)(0))(1:405)|351|352|353|355|356|357|358|359|360|(1:362)(1:365)|363|111|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(27:0|1|2|3|5|6|(14:7|8|10|11|12|13|14|15|16|17|19|20|21|22)|(17:23|24|25|26|27|28|29|30|31|32|(1:35)|36|37|39|40|41|42)|(25:43|44|46|47|48|49|51|52|54|55|56|57|58|59|61|62|63|64|66|67|69|70|71|72|(11:73|74|76|77|78|79|81|82|(2:445|446)(1:84)|85|86))|(21:435|436|437|(2:439|440)|93|94|95|96|97|98|(2:411|412)|100|101|103|104|(2:406|407)(1:106)|(1:108)(1:(1:342)(14:(6:344|(3:347|348|(1:350))|(1:(8:384|385|386|387|388|389|111|(9:113|(1:115)|116|(10:318|319|320|321|322|323|120|(2:121|(1:1)(2:(2:139|(1:316)(8:141|142|(2:144|(2:145|(2:(5:159|160|(2:162|(1:164)(1:168))(1:169)|165|166)|167)(2:172|150)))(2:173|(2:175|(2:176|(2:(5:185|186|(2:188|(1:190)(1:194))(1:195)|191|192)|193)(2:198|181)))(2:199|(5:201|152|153|154|155)(5:202|(5:204|(2:206|(1:208)(1:213))(1:214)|209|(1:211)|212)(2:215|(3:217|(1:219)(1:221)|220)(2:222|(2:224|(1:226)(2:227|(3:229|(1:231)(1:233)|232)(5:234|(3:236|(1:238)(1:241)|239)(2:242|(3:244|a16|249)(5:254|(3:256|(1:258)(4:268|(2:269|(1:1)(2:(3:281|282|(3:288|289|(3:291|292|293)(1:294))(3:284|285|286))|287))|275|276)|(5:260|(1:262)|263|(1:265)|266))(2:298|(1:300)(2:301|(1:303)(4:304|(4:306|(2:309|(1:311)(2:312|313))|314|313)(1:315)|154|155)))|267|154|155))|240|154|155)))))|153|154|155)))|151|152|153|154|155))(3:135|136|137)|138))|128|(2:130|131)(1:133))(1:118)|119|120|(3:121|(3:123|125|127)(1:317)|138)|128|(0)(0))(2:339|340))(2:382|383))(6:394|395|396|397|398|399)|110|111|(0)(0))(1:405)|351|352|353|355|356|357|358|359|360|(1:362)(1:365)|363|111|(0)(0)))|109|110|111|(0)(0))(2:88|(9:419|420|421|(1:423)(1:432)|424|(1:426)|427|(1:429)|430)(2:90|(18:92|93|94|95|96|97|98|(0)|100|101|103|104|(0)(0)|(0)(0)|109|110|111|(0)(0))(17:417|94|95|96|97|98|(0)|100|101|103|104|(0)(0)|(0)(0)|109|110|111|(0)(0))))|431|94|95|96|97|98|(0)|100|101|103|104|(0)(0)|(0)(0)|109|110|111|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x0383, code lost:
    
        r2 = r6;
        r9 = r14;
        r6 = r19;
        r7 = r20;
        r13 = r23;
        r5 = r40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x03e6, code lost:
    
        r9 = r14;
        r6 = r19;
        r7 = r20;
        r13 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x03ed, code lost:
    
        r5 = r40;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x03f2, code lost:
    
        r27 = 0;
        r19 = r14;
        r0 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x03f7, code lost:
    
        r14 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x03f9, code lost:
    
        r0 = r4;
        r27 = 0;
        r19 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x03ff, code lost:
    
        r27 = 0;
        r19 = r14;
        r14 = r8;
        r0 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0407, code lost:
    
        r27 = 0;
        r19 = r14;
        r14 = r8;
        r0 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:416:0x040e, code lost:
    
        r9 = r14;
        r6 = r19;
        r13 = r23;
        r7 = r27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01fb A[Catch: NumberFormatException -> 0x01e8, TryCatch #37 {NumberFormatException -> 0x01e8, blocks: (B:407:0x01e1, B:108:0x01fb, B:342:0x0219), top: B:406:0x01e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0b6a  */
    /* JADX WARN: Removed duplicated region for block: B:133:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0b66 A[ADDED_TO_REGION, EDGE_INSN: B:317:0x0b66->B:128:0x0b66 BREAK  A[LOOP:0: B:121:0x07af->B:138:0x07af], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0b76  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x01e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x01b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v27, types: [com.android.server.utils.Watchable, com.android.server.utils.WatchableImpl, com.android.server.utils.WatchedArrayList] */
    /* JADX WARN: Type inference failed for: r7v136 */
    /* JADX WARN: Type inference failed for: r7v137 */
    /* JADX WARN: Type inference failed for: r7v155 */
    /* JADX WARN: Type inference failed for: r7v164, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v168 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 3 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPackageLPw(com.android.modules.utils.TypedXmlPullParser r74, java.util.ArrayList r75, android.util.ArrayMap r76, java.util.List r77, android.util.ArrayMap r78) {
        /*
            Method dump skipped, instructions count: 2940
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readPackageLPw(com.android.modules.utils.TypedXmlPullParser, java.util.ArrayList, android.util.ArrayMap, java.util.List, android.util.ArrayMap):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0454 A[Catch: all -> 0x046e, TryCatch #2 {all -> 0x046e, blocks: (B:102:0x044c, B:104:0x0454, B:106:0x045c, B:108:0x0467, B:110:0x0464), top: B:101:0x044c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPackageRestrictionsLPr(int r56, android.util.ArrayMap r57, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController r58) {
        /*
            Method dump skipped, instructions count: 1194
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readPackageRestrictionsLPr(int, android.util.ArrayMap, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController):void");
    }

    public final void readPersistentPreferredActivitiesLPw(int i, TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    PersistentPreferredActivity persistentPreferredActivity = new PersistentPreferredActivity(typedXmlPullParser);
                    WatchedSparseArray watchedSparseArray = this.mPersistentPreferredActivities;
                    PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) watchedSparseArray.mStorage.get(i);
                    if (persistentPreferredIntentResolver == null) {
                        persistentPreferredIntentResolver = new PersistentPreferredIntentResolver();
                        watchedSparseArray.put(i, persistentPreferredIntentResolver);
                    }
                    persistentPreferredIntentResolver.addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) persistentPreferredActivity);
                } else {
                    String m = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <persistent-preferred-activities>: "));
                    boolean z = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readPreferredActivitiesLPw(int i, TypedXmlPullParser typedXmlPullParser) {
        String[] strArr;
        String[] strArr2;
        int i2;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("item")) {
                    PreferredActivity preferredActivity = new PreferredActivity(typedXmlPullParser);
                    PreferredComponent preferredComponent = preferredActivity.mPref;
                    if (preferredComponent.mParseError == null) {
                        PreferredIntentResolver editPreferredActivitiesLPw = editPreferredActivitiesLPw(i);
                        ArrayList findFilters = editPreferredActivitiesLPw.findFilters(preferredActivity);
                        if (findFilters != null && !findFilters.isEmpty()) {
                            if (preferredComponent.mAlways) {
                                int size = findFilters.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    PreferredComponent preferredComponent2 = ((PreferredActivity) findFilters.get(i3)).mPref;
                                    if (preferredComponent2.mAlways) {
                                        if (preferredComponent2.mMatch == (preferredComponent.mMatch & 268369920) && (strArr = preferredComponent2.mSetPackages) != null && (strArr2 = preferredComponent.mSetPackages) != null) {
                                            ComponentName componentName = preferredComponent.mComponent;
                                            ComponentName componentName2 = preferredComponent2.mComponent;
                                            if (componentName2 != null && componentName != null && componentName2.getPackageName().equals(componentName.getPackageName()) && preferredComponent2.mComponent.getClassName().equals(componentName.getClassName())) {
                                                int length = strArr2.length;
                                                int length2 = strArr.length;
                                                if (length != length2) {
                                                    continue;
                                                } else {
                                                    while (i2 < length2) {
                                                        i2 = (strArr[i2].equals(strArr2[i2]) && preferredComponent2.mSetClasses[i2].equals(preferredComponent.mSetClasses[i2])) ? i2 + 1 : 0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        editPreferredActivitiesLPw.addFilter((PackageDataSnapshot) null, (WatchedIntentFilter) preferredActivity);
                    } else {
                        String str = "Error in package manager settings: <preferred-activity> " + preferredComponent.mParseError + " at " + typedXmlPullParser.getPositionDescription();
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        PackageManagerServiceUtils.logCriticalInfo(5, str);
                    }
                } else {
                    String m = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <preferred-activities>: "));
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0181, code lost:
    
        r1 = findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        r2 = findOrCreateVersion("primary_physical");
        r1.sdkVersion = r13.getAttributeInt((java.lang.String) null, "internal", r0);
        r2.sdkVersion = r13.getAttributeInt((java.lang.String) null, "external", r0);
        r4 = com.android.internal.util.XmlUtils.readStringAttribute(r13, "buildFingerprint");
        r2.buildFingerprint = r4;
        r1.buildFingerprint = r4;
        r3 = com.android.internal.util.XmlUtils.readStringAttribute(r13, "fingerprint");
        r2.fingerprint = r3;
        r1.fingerprint = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01b0, code lost:
    
        if (r1.equals("database-version") == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01b2, code lost:
    
        r1 = findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        r2 = findOrCreateVersion("primary_physical");
        r1.databaseVersion = r13.getAttributeInt((java.lang.String) null, "internal", r0);
        r2.databaseVersion = r13.getAttributeInt((java.lang.String) null, "external", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01d2, code lost:
    
        if (r1.equals("verifier") == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01d4, code lost:
    
        r17.mVerifierDeviceIdentity = android.content.pm.VerifierDeviceIdentity.parse(r13.getAttributeValue((java.lang.String) null, "device"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01ea, code lost:
    
        if ("read-external-storage".equals(r1) == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x01f4, code lost:
    
        if (r1.equals("keyset-settings") == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01f6, code lost:
    
        r17.mKeySetManagerService.readKeySetsLPw(r10, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0204, code lost:
    
        if ("version".equals(r1) == false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0206, code lost:
    
        r1 = findOrCreateVersion(com.android.internal.util.XmlUtils.readStringAttribute(r13, "volumeUuid"));
        r1.sdkVersion = r13.getAttributeInt((java.lang.String) null, "sdkVersion");
        r1.databaseVersion = r13.getAttributeInt((java.lang.String) null, "databaseVersion");
        r1.buildFingerprint = com.android.internal.util.XmlUtils.readStringAttribute(r13, "buildFingerprint");
        r1.fingerprint = com.android.internal.util.XmlUtils.readStringAttribute(r13, "fingerprint");
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0232, code lost:
    
        r2 = r1.equals("domain-verifications");
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0239, code lost:
    
        r3 = r17.mDomainVerificationManager;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x023b, code lost:
    
        if (r2 == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0250, code lost:
    
        if (r1.equals("domain-verifications-legacy") == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0252, code lost:
    
        ((com.android.server.pm.verify.domain.DomainVerificationService) r3).readLegacySettings(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0258, code lost:
    
        android.util.Slog.w(r7, "Unknown element under <packages>: " + r13.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0241, code lost:
    
        ((com.android.server.pm.verify.domain.DomainVerificationService) r3).readSettings(r18, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0245, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x027d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0064, code lost:
    
        r13 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a8, code lost:
    
        if (r1 != 4) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ab, code lost:
    
        r1 = r5.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00b6, code lost:
    
        if (r1.equals("package") == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b8, code lost:
    
        r7 = r4;
        r13 = r5;
        r16 = r0;
        r0 = r6;
        readPackageLPw(r5, r11, r10, r19, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0273, code lost:
    
        r9 = r19;
        r6 = r0;
        r4 = r7;
        r5 = r13;
        r0 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ce, code lost:
    
        r16 = r0;
        r7 = r4;
        r13 = r5;
        r0 = r6;
        r2 = r1.equals("permissions");
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00da, code lost:
    
        r3 = r17.mPermissions;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00dc, code lost:
    
        if (r2 == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00de, code lost:
    
        r3.readPermissions(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e9, code lost:
    
        if (r1.equals("permission-trees") == false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00eb, code lost:
    
        r3.readPermissionTrees(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00f6, code lost:
    
        if (r1.equals("shared-user") == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00f8, code lost:
    
        readSharedUserLPw(r13, r11, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0103, code lost:
    
        if (r1.equals("preferred-packages") == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x010d, code lost:
    
        if (r1.equals("preferred-activities") == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x010f, code lost:
    
        readPreferredActivitiesLPw(r0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x011a, code lost:
    
        if (r1.equals("persistent-preferred-activities") == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x011c, code lost:
    
        readPersistentPreferredActivitiesLPw(r0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0126, code lost:
    
        if (r1.equals("crossProfile-intent-filters") == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0128, code lost:
    
        readCrossProfileIntentFiltersLPw(r0, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0132, code lost:
    
        if (r1.equals("default-browser") == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0134, code lost:
    
        r1 = readDefaultApps(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0138, code lost:
    
        if (r1 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x013a, code lost:
    
        r17.mPendingDefaultBrowser.put(r0, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0147, code lost:
    
        if (r1.equals("updated-package") == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0149, code lost:
    
        readDisabledSysPackageLPw(r13, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0155, code lost:
    
        if (r1.equals("renamed-package") == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0157, code lost:
    
        r1 = r13.getAttributeValue((java.lang.String) null, "new");
        r3 = r13.getAttributeValue((java.lang.String) null, "old");
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0166, code lost:
    
        if (r1 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0168, code lost:
    
        if (r3 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x016a, code lost:
    
        r17.mRenamedPackages.put(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x017f, code lost:
    
        if (r1.equals("last-platform-version") == false) goto L87;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0299 A[Catch: all -> 0x003d, TryCatch #4 {all -> 0x003d, blocks: (B:3:0x0031, B:142:0x0037, B:9:0x004d, B:13:0x0067, B:14:0x006b, B:21:0x0079, B:24:0x008d, B:25:0x0091, B:29:0x009a, B:53:0x00ab, B:55:0x00b8, B:61:0x00ce, B:64:0x00de, B:65:0x00e2, B:67:0x00eb, B:68:0x00ef, B:70:0x00f8, B:71:0x00fc, B:74:0x0106, B:76:0x010f, B:77:0x0113, B:79:0x011c, B:80:0x0120, B:82:0x0128, B:83:0x012c, B:85:0x0134, B:87:0x013a, B:88:0x0140, B:90:0x0149, B:91:0x014e, B:93:0x0157, B:96:0x016a, B:97:0x0171, B:100:0x0181, B:101:0x01aa, B:103:0x01b2, B:104:0x01cb, B:106:0x01d4, B:107:0x01e3, B:110:0x01ee, B:112:0x01f6, B:113:0x01fd, B:115:0x0206, B:116:0x0232, B:124:0x023d, B:127:0x0241, B:41:0x0291, B:43:0x0299, B:45:0x02a1, B:47:0x02ac, B:48:0x02a9, B:119:0x0248, B:121:0x0252, B:122:0x0258, B:34:0x0288), top: B:2:0x0031 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean readSettingsLPw(com.android.server.pm.ComputerLocked r18, java.util.List r19, android.util.ArrayMap r20, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController r21) {
        /*
            Method dump skipped, instructions count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.readSettingsLPw(com.android.server.pm.ComputerLocked, java.util.List, android.util.ArrayMap, com.samsung.android.server.pm.rescueparty.PackageManagerBackupController):boolean");
    }

    public final void readSharedUserLPw(TypedXmlPullParser typedXmlPullParser, ArrayList arrayList, List list) {
        SharedUserSetting sharedUserSetting = null;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "userId", 0);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "system", false);
        if (attributeValue == null) {
            String m = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Error in package manager settings: <shared-user> has no name at "));
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m);
        } else if (attributeInt == 0) {
            String m2 = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, StorageManagerService$$ExternalSyntheticOutline0.m(attributeInt, "Error in package manager settings: shared-user ", attributeValue, " has bad appId ", " at "));
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m2);
        } else {
            sharedUserSetting = addSharedUserLPw(attributeInt, attributeBoolean ? 1 : 0, 0, attributeValue.intern());
            if (sharedUserSetting == null) {
                String m3 = CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Occurred while parsing settings at "));
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(6, m3);
            }
        }
        if (sharedUserSetting == null) {
            XmlUtils.skipCurrentTag(typedXmlPullParser);
            return;
        }
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("sigs")) {
                    sharedUserSetting.signatures.readXml(typedXmlPullParser, arrayList);
                } else if (name.equals("perms")) {
                    readInstallPermissionsLPr(typedXmlPullParser, sharedUserSetting.mLegacyPermissionsState, list);
                } else {
                    String m4 = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <shared-user>: "));
                    boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                    PackageManagerServiceUtils.logCriticalInfo(5, m4);
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final boolean registerAppIdLPw(PackageSetting packageSetting) {
        boolean z;
        int i = packageSetting.mAppId;
        AppIdSettingMap appIdSettingMap = this.mAppIds;
        if (i != 0) {
            z = appIdSettingMap.registerExistingAppId(i, packageSetting, packageSetting.mName);
        } else {
            packageSetting.setAppId(appIdSettingMap.acquireAndRegisterNewAppId(packageSetting));
            z = true;
        }
        if (packageSetting.mAppId >= 0) {
            return z;
        }
        String m = AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("Package "), packageSetting.mName, " could not be assigned a valid UID");
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        PackageManagerServiceUtils.logCriticalInfo(5, m);
        throw new PackageManagerException(-4, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("Package "), packageSetting.mName, " could not be assigned a valid UID"));
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    public final void registerObservers$1() {
        WatchedArrayMap watchedArrayMap = this.mPackages;
        AnonymousClass1 anonymousClass1 = this.mObserver;
        watchedArrayMap.registerObserver(anonymousClass1);
        this.mInstallerPackages.registerObserver(anonymousClass1);
        this.mKernelMapping.registerObserver(anonymousClass1);
        this.mDisabledSysPackages.registerObserver(anonymousClass1);
        this.mBlockUninstallPackages.registerObserver(anonymousClass1);
        this.mVersion.registerObserver(anonymousClass1);
        this.mPreferredActivities.registerObserver(anonymousClass1);
        this.mPersistentPreferredActivities.registerObserver(anonymousClass1);
        this.mCrossProfileIntentResolvers.registerObserver(anonymousClass1);
        this.mSharedUsers.registerObserver(anonymousClass1);
        AppIdSettingMap appIdSettingMap = this.mAppIds;
        appIdSettingMap.mNonSystemSettings.registerObserver(anonymousClass1);
        appIdSettingMap.mSystemSettings.registerObserver(anonymousClass1);
        this.mRenamedPackages.registerObserver(anonymousClass1);
        this.mNextAppLinkGeneration.registerObserver(anonymousClass1);
        this.mPendingDefaultBrowser.registerObserver(anonymousClass1);
        this.mPendingPackages.registerObserver(anonymousClass1);
    }

    public final void removeAppIdLPw(int i) {
        AppIdSettingMap appIdSettingMap = this.mAppIds;
        if (i >= 10000) {
            WatchedArrayList watchedArrayList = appIdSettingMap.mNonSystemSettings;
            int i2 = i - 10000;
            if (i2 < watchedArrayList.mStorage.size()) {
                watchedArrayList.set(i2, null);
            }
        } else {
            appIdSettingMap.mSystemSettings.delete(i);
        }
        int i3 = i + 1;
        if (i3 > appIdSettingMap.mFirstAvailableAppId) {
            appIdSettingMap.mFirstAvailableAppId = i3;
        }
    }

    public final boolean removePackageAndAppIdLPw(String str) {
        boolean z;
        boolean z2;
        String str2;
        int i;
        String str3;
        boolean z3;
        String str4;
        PackageSetting packageSetting = (PackageSetting) this.mPackages.remove(str);
        if (packageSetting == null) {
            return false;
        }
        WatchedArraySet watchedArraySet = this.mInstallerPackages;
        if (watchedArraySet.mStorage.contains(str) && ((!this.mDisabledSysPackages.mStorage.containsKey(str) || (!"com.sec.android.app.samsungapps".equals(str) && !"com.android.vending".equals(str))) && (!PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED || !"com.sec.android.app.samsungapps".equals(str) || this.mDisabledSysPackages.mStorage.get(str) == null))) {
            for (int i2 = 0; i2 < this.mPackages.mStorage.size(); i2++) {
                PackageSetting packageSetting2 = (PackageSetting) this.mPackages.mStorage.valueAt(i2);
                InstallSource installSource = packageSetting2.installSource;
                if (str == null) {
                    installSource.getClass();
                } else {
                    boolean equals = str.equals(installSource.mInitiatingPackageName);
                    boolean z4 = installSource.mIsInitiatingPackageUninstalled;
                    if (!equals || z4) {
                        z = false;
                        z2 = z4;
                    } else {
                        z = true;
                        z2 = true;
                    }
                    String str5 = installSource.mOriginatingPackageName;
                    if (str.equals(str5)) {
                        z = true;
                        str2 = null;
                    } else {
                        str2 = str5;
                    }
                    String str6 = installSource.mInstallerPackageName;
                    if (str.equals(str6)) {
                        z3 = true;
                        i = -1;
                        str3 = null;
                        z = true;
                    } else {
                        i = installSource.mInstallerPackageUid;
                        str3 = str6;
                        z3 = installSource.mIsOrphaned;
                    }
                    String str7 = installSource.mUpdateOwnerPackageName;
                    if (str.equals(str7)) {
                        z = true;
                        str4 = null;
                    } else {
                        str4 = str7;
                    }
                    if (z) {
                        installSource = InstallSource.createInternal(i, installSource.mPackageSource, installSource.mInitiatingPackageSignatures, installSource.mInitiatingPackageName, str2, str3, str4, null, z3, z2);
                    }
                }
                packageSetting2.installSource = installSource;
                packageSetting2.onChanged$2();
            }
            watchedArraySet.remove(str);
        }
        SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.removePackage(packageSetting);
            return checkAndPruneSharedUserLPw(sharedUserSettingLPr, false);
        }
        removeAppIdLPw(packageSetting.mAppId);
        return true;
    }

    public final void removeUserLPw(int i) {
        Iterator it = this.mPackages.entrySet().iterator();
        while (it.hasNext()) {
            PackageSetting packageSetting = (PackageSetting) ((Map.Entry) it.next()).getValue();
            packageSetting.mUserStates.delete(i);
            packageSetting.onChanged$2();
        }
        this.mPreferredActivities.delete(i);
        synchronized (this.mPackageRestrictionsLock) {
            ResilientAtomicFile userPackagesStateFile = getUserPackagesStateFile(i);
            userPackagesStateFile.mFile.delete();
            userPackagesStateFile.mTemporaryBackup.delete();
            userPackagesStateFile.mReserveCopy.delete();
            this.mPendingAsyncPackageRestrictionsWrites.delete(i);
        }
        synchronized (this.mCrossProfileIntentResolvers) {
            try {
                if (this.mCrossProfileIntentResolvers.mStorage.get(i) != null) {
                    this.mCrossProfileIntentResolvers.delete(i);
                    writePackageRestrictionsLPr(i, false);
                }
                int size = this.mCrossProfileIntentResolvers.mStorage.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mCrossProfileIntentResolvers.mStorage.keyAt(i2);
                    CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.mStorage.get(keyAt);
                    Iterator it2 = new ArraySet(Collections.unmodifiableSet(crossProfileIntentResolver.mFilters)).iterator();
                    boolean z = false;
                    while (it2.hasNext()) {
                        CrossProfileIntentFilter crossProfileIntentFilter = (CrossProfileIntentFilter) it2.next();
                        if (crossProfileIntentFilter.mTargetUserId == i) {
                            crossProfileIntentResolver.removeFilter((WatchedIntentFilter) crossProfileIntentFilter);
                            z = true;
                        }
                    }
                    if (z) {
                        writePackageRestrictionsLPr(keyAt, false);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        RuntimePermissionPersistence runtimePermissionPersistence = this.mRuntimePermissionsPersistence;
        synchronized (runtimePermissionPersistence.mLock) {
            runtimePermissionPersistence.mAsyncHandler.removeMessages(i);
            runtimePermissionPersistence.mPermissionUpgradeNeeded.delete(i);
            runtimePermissionPersistence.mVersions.delete(i);
            runtimePermissionPersistence.mFingerprints.remove(i);
        }
        ((DomainVerificationService) this.mDomainVerificationManager).clearUser(i);
        writePackageListLPr(-1);
        if (this.mKernelMappingFilename == null) {
            return;
        }
        writeIntToFile(new File(this.mKernelMappingFilename, "remove_userid"), i);
    }

    public final void setBlockUninstallLPw(int i, String str, boolean z) {
        WatchedSparseArray watchedSparseArray = this.mBlockUninstallPackages;
        ArraySet arraySet = (ArraySet) watchedSparseArray.mStorage.get(i);
        if (z) {
            if (arraySet == null) {
                arraySet = new ArraySet();
                watchedSparseArray.put(i, arraySet);
            }
            arraySet.add(str);
            return;
        }
        if (arraySet != null) {
            arraySet.remove(str);
            if (arraySet.isEmpty()) {
                watchedSparseArray.delete(i);
            }
        }
    }

    public final void setPermissionControllerVersion(long j) {
        RuntimePermissionPersistence runtimePermissionPersistence = this.mRuntimePermissionsPersistence;
        synchronized (runtimePermissionPersistence.mLock) {
            try {
                int size = runtimePermissionPersistence.mFingerprints.size();
                runtimePermissionPersistence.mExtendedFingerprint = PackagePartitions.FINGERPRINT + "?pc_version=" + j;
                for (int i = 0; i < size; i++) {
                    runtimePermissionPersistence.mPermissionUpgradeNeeded.put(runtimePermissionPersistence.mFingerprints.keyAt(i), !TextUtils.equals(runtimePermissionPersistence.mExtendedFingerprint, (String) runtimePermissionPersistence.mFingerprints.valueAt(i)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (Settings) this.mSnapshot.snapshot();
    }

    public final ArrayList systemReady(ComponentResolver componentResolver) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.mPreferredActivities.mStorage.size(); i++) {
            PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.mStorage.valueAt(i);
            arrayList2.clear();
            for (PreferredActivity preferredActivity : Collections.unmodifiableSet(preferredIntentResolver.mFilters)) {
                ComponentName componentName = preferredActivity.mPref.mComponent;
                PackageManagerTracedLock packageManagerTracedLock = componentResolver.mLock;
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        z = componentResolver.mActivities.mActivities.get(componentName) != null;
                    } catch (Throwable th) {
                        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                if (!z) {
                    arrayList2.add(preferredActivity);
                }
            }
            if (arrayList2.size() > 0) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    PreferredActivity preferredActivity2 = (PreferredActivity) arrayList2.get(i2);
                    Slog.w("PackageSettings", "Removing dangling preferred activity: " + preferredActivity2.mPref.mComponent);
                    preferredIntentResolver.removeFilter((WatchedIntentFilter) preferredActivity2);
                }
                arrayList.add(Integer.valueOf(this.mPreferredActivities.mStorage.keyAt(i)));
            }
        }
        dispatchChange(this);
        return arrayList;
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    public final void writeAllUsersPackageRestrictionsLPr(boolean z) {
        List users = getUsers(UserManagerService.getInstance(), false, false);
        if (users == null) {
            return;
        }
        if (z) {
            synchronized (this.mPackageRestrictionsLock) {
                this.mPendingAsyncPackageRestrictionsWrites.clear();
            }
            this.mHandler.removeMessages(30);
        }
        Iterator it = ((ArrayList) users).iterator();
        while (it.hasNext()) {
            writePackageRestrictionsLPr(((UserInfo) it.next()).id, z);
        }
    }

    public final void writeBlockUninstallPackagesLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        ArraySet arraySet = (ArraySet) this.mBlockUninstallPackages.mStorage.get(i);
        if (arraySet != null) {
            typedXmlSerializer.startTag((String) null, "block-uninstall-packages");
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                typedXmlSerializer.startTag((String) null, "block-uninstall");
                typedXmlSerializer.attribute((String) null, "packageName", (String) arraySet.valueAt(i2));
                typedXmlSerializer.endTag((String) null, "block-uninstall");
            }
            typedXmlSerializer.endTag((String) null, "block-uninstall-packages");
        }
    }

    public final void writeCrossProfileIntentFiltersLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        typedXmlSerializer.startTag((String) null, "crossProfile-intent-filters");
        CrossProfileIntentResolver crossProfileIntentResolver = (CrossProfileIntentResolver) this.mCrossProfileIntentResolvers.mStorage.get(i);
        if (crossProfileIntentResolver != null) {
            for (CrossProfileIntentFilter crossProfileIntentFilter : Collections.unmodifiableSet(crossProfileIntentResolver.mFilters)) {
                typedXmlSerializer.startTag((String) null, "item");
                typedXmlSerializer.attributeInt((String) null, "targetUserId", crossProfileIntentFilter.mTargetUserId);
                typedXmlSerializer.attributeInt((String) null, "flags", crossProfileIntentFilter.mFlags);
                typedXmlSerializer.attribute((String) null, "ownerPackage", crossProfileIntentFilter.mOwnerPackage);
                typedXmlSerializer.attributeInt((String) null, "accessControl", crossProfileIntentFilter.mAccessControlLevel);
                typedXmlSerializer.startTag((String) null, "filter");
                crossProfileIntentFilter.mFilter.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "filter");
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "crossProfile-intent-filters");
    }

    public final void writeKernelMappingLPr() {
        File file = this.mKernelMappingFilename;
        if (file == null) {
            return;
        }
        String[] list = file.list();
        ArraySet arraySet = new ArraySet(list.length);
        for (String str : list) {
            arraySet.add(str);
        }
        for (PackageSetting packageSetting : this.mPackages.values()) {
            arraySet.remove(packageSetting.mName);
            writeKernelMappingLPr(packageSetting);
        }
        for (int i = 0; i < arraySet.size(); i++) {
            String str2 = (String) arraySet.valueAt(i);
            this.mKernelMapping.remove(str2);
            new File(this.mKernelMappingFilename, str2).delete();
        }
    }

    public final void writeKernelMappingLPr(PackageSetting packageSetting) {
        String str;
        if (this.mKernelMappingFilename == null || packageSetting == null || (str = packageSetting.mName) == null) {
            return;
        }
        int i = packageSetting.mAppId;
        int[] notInstalledUserIds = packageSetting.getNotInstalledUserIds();
        WatchedArrayMap watchedArrayMap = this.mKernelMapping;
        KernelPackageState kernelPackageState = (KernelPackageState) watchedArrayMap.mStorage.get(str);
        boolean z = true;
        int i2 = 0;
        boolean z2 = kernelPackageState == null;
        if (!z2 && Arrays.equals(notInstalledUserIds, kernelPackageState.excludedUserIds)) {
            z = false;
        }
        File file = new File(this.mKernelMappingFilename, str);
        if (z2) {
            file.mkdir();
            kernelPackageState = new KernelPackageState();
            watchedArrayMap.put(str, kernelPackageState);
        }
        kernelPackageState.getClass();
        if (i != 0) {
            writeIntToFile(new File(file, "appid"), i);
        }
        if (z) {
            for (int i3 = 0; i3 < notInstalledUserIds.length; i3++) {
                int[] iArr = kernelPackageState.excludedUserIds;
                if (iArr == null || !ArrayUtils.contains(iArr, notInstalledUserIds[i3])) {
                    writeIntToFile(new File(file, "excluded_userids"), notInstalledUserIds[i3]);
                }
            }
            if (kernelPackageState.excludedUserIds != null) {
                while (true) {
                    int[] iArr2 = kernelPackageState.excludedUserIds;
                    if (i2 >= iArr2.length) {
                        break;
                    }
                    if (!ArrayUtils.contains(notInstalledUserIds, iArr2[i2])) {
                        writeIntToFile(new File(file, "clear_userid"), kernelPackageState.excludedUserIds[i2]);
                    }
                    i2++;
                }
            }
            kernelPackageState.excludedUserIds = notInstalledUserIds;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0216 A[Catch: all -> 0x01fc, TRY_LEAVE, TryCatch #7 {all -> 0x01fc, blocks: (B:89:0x01c9, B:91:0x01e2, B:93:0x01ec, B:15:0x020d, B:17:0x0216), top: B:88:0x01c9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeLPr(com.android.server.pm.Computer r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 551
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.writeLPr(com.android.server.pm.Computer, boolean):void");
    }

    public final void writePackageListLPr(int i) {
        String fileSelabelLookup = SELinux.fileSelabelLookup(this.mPackageListFilename.getAbsolutePath());
        if (fileSelabelLookup == null) {
            Slog.wtf("PackageSettings", "Failed to get SELinux context for " + this.mPackageListFilename.getAbsolutePath());
        }
        if (!SELinux.setFSCreateContext(fileSelabelLookup)) {
            Slog.wtf("PackageSettings", "Failed to set packages.list SELinux context");
        }
        try {
            writePackageListLPrInternal(i);
        } finally {
            SELinux.setFSCreateContext((String) null);
        }
    }

    public final void writePackageListLPrInternal(int i) {
        int i2;
        Settings settings = this;
        ArrayList arrayList = (ArrayList) getUsers(UserManagerService.getInstance(), true, true);
        int size = arrayList.size();
        int[] iArr = new int[size];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            iArr[i4] = ((UserInfo) arrayList.get(i4)).id;
        }
        if (i != -1) {
            iArr = ArrayUtils.appendInt(iArr, i);
        }
        JournaledFile journaledFile = new JournaledFile(settings.mPackageListFilename, new File(settings.mPackageListFilename.getAbsolutePath() + ".tmp"));
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(journaledFile.chooseForWrite());
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.defaultCharset()));
            try {
                FileUtils.setPermissions(fileOutputStream.getFD(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
                StringBuilder sb = new StringBuilder();
                for (PackageSetting packageSetting : settings.mPackages.values()) {
                    AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
                    if (androidPackageInternal == null) {
                        if (!"android".equals(packageSetting.mName)) {
                            Slog.w("PackageSettings", "Skipping " + packageSetting + " due to missing metadata");
                        }
                    } else if (!androidPackageInternal.isApex()) {
                        File dataDir = PackageInfoUtils.getDataDir(packageSetting, i3);
                        String absolutePath = dataDir == null ? "null" : dataDir.getAbsolutePath();
                        boolean isDebuggable = packageSetting.pkg.isDebuggable();
                        IntArray intArray = new IntArray();
                        int length = iArr.length;
                        int i5 = i3;
                        while (i5 < length) {
                            intArray.addAll(settings.mPermissionDataProvider.getGidsForUid(UserHandle.getUid(iArr[i5], packageSetting.mAppId)));
                            i5++;
                            settings = this;
                            iArr = iArr;
                        }
                        int[] iArr2 = iArr;
                        if (absolutePath.indexOf(32) >= 0) {
                            settings = this;
                            iArr = iArr2;
                            i3 = 0;
                        } else {
                            sb.setLength(0);
                            sb.append(packageSetting.pkg.getPackageName());
                            sb.append(" ");
                            sb.append(packageSetting.pkg.getUid());
                            sb.append(isDebuggable ? " 1 " : " 0 ");
                            sb.append(absolutePath);
                            sb.append(" ");
                            sb.append(packageSetting.getSeInfo());
                            sb.append(" ");
                            int size2 = intArray.size();
                            if (intArray.size() > 0) {
                                i2 = 0;
                                sb.append(intArray.get(0));
                                for (int i6 = 1; i6 < size2; i6++) {
                                    sb.append(",");
                                    sb.append(intArray.get(i6));
                                }
                            } else {
                                i2 = 0;
                                sb.append("none");
                            }
                            sb.append(" ");
                            sb.append(packageSetting.pkg.isProfileableByShell() ? "1" : "0");
                            sb.append(" ");
                            sb.append(packageSetting.pkg.getLongVersionCode());
                            sb.append(" ");
                            sb.append(packageSetting.pkg.isProfileable() ? "1" : "0");
                            sb.append(" ");
                            if (packageSetting.isSystem()) {
                                sb.append("@system");
                            } else if (packageSetting.isProduct()) {
                                sb.append("@product");
                            } else {
                                String str = packageSetting.installSource.mInstallerPackageName;
                                if (str == null || str.isEmpty()) {
                                    sb.append("@null");
                                } else {
                                    sb.append(packageSetting.installSource.mInstallerPackageName);
                                }
                            }
                            sb.append("\n");
                            bufferedWriter2.append((CharSequence) sb);
                            settings = this;
                            iArr = iArr2;
                            i3 = i2;
                        }
                    }
                }
                bufferedWriter2.flush();
                FileUtils.sync(fileOutputStream);
                bufferedWriter2.close();
                journaledFile.commit();
            } catch (Exception e) {
                e = e;
                bufferedWriter = bufferedWriter2;
                Slog.wtf("PackageSettings", "Failed to write packages.list", e);
                IoUtils.closeQuietly(bufferedWriter);
                journaledFile.rollback();
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01e8, code lost:
    
        r8.startTag((java.lang.String) null, "item");
        r8.attribute((java.lang.String) null, "name", (java.lang.String) r10.valueAt(r12));
        r8.endTag((java.lang.String) null, "item");
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0200, code lost:
    
        r8.endTag((java.lang.String) null, "enabled-components");
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0205, code lost:
    
        r10 = r11.getDisabledComponents();
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0209, code lost:
    
        if (r10 == null) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x020f, code lost:
    
        if (r10.size() <= 0) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0211, code lost:
    
        r8.startTag((java.lang.String) null, "disabled-components");
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x021b, code lost:
    
        if (r12 >= r10.size()) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x021d, code lost:
    
        r8.startTag((java.lang.String) null, "item");
        r8.attribute((java.lang.String) null, "name", (java.lang.String) r10.valueAt(r12));
        r8.endTag((java.lang.String) null, "item");
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0235, code lost:
    
        r8.endTag((java.lang.String) null, "disabled-components");
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x023a, code lost:
    
        writeArchiveStateLPr(r8, r11.getArchiveState());
        r8.endTag((java.lang.String) null, "pkg");
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0249, code lost:
    
        writePreferredActivitiesLPr(r17, r8, true);
        writePersistentPreferredActivitiesLPr(r8, r17);
        writeCrossProfileIntentFiltersLPr(r8, r17);
        writeDefaultApps(r8, (java.lang.String) r16.mPendingDefaultBrowser.mStorage.get(r17));
        writeBlockUninstallPackagesLPr(r8, r17);
        r8.endTag((java.lang.String) null, "package-restrictions");
        r8.endDocument();
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x026b, code lost:
    
        monitor-exit(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x026c, code lost:
    
        r0 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION;
        r4.finishWrite(r3);
        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("package-user-" + r17, android.os.SystemClock.uptimeMillis() - r18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x028c, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x028f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0093, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0297, code lost:
    
        r1 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0299, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0293, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0294, code lost:
    
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x02ae, code lost:
    
        android.util.Slog.wtf("PackageManager", "Unable to write package manager package restrictions,  current changes will be lost at reboot", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x02b5, code lost:
    
        if (r5 != null) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x02b7, code lost:
    
        r4.failWrite(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x02ba, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x02bd, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003e, code lost:
    
        r6 = r16.mLock;
        r8 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
    
        monitor-enter(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0043, code lost:
    
        r8 = android.util.Xml.resolveSerializer(r3);
        r8.startDocument((java.lang.String) null, java.lang.Boolean.TRUE);
        r8.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        r8.startTag((java.lang.String) null, "package-restrictions");
        r9 = r16.mPackages.values().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0065, code lost:
    
        if (r9.hasNext() == false) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0067, code lost:
    
        r10 = (com.android.server.pm.PackageSetting) r9.next();
        r11 = r10.readUserState(r17);
        r8.startTag((java.lang.String) null, "pkg");
        r8.attribute((java.lang.String) null, "name", r10.mName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0087, code lost:
    
        if (r11.getCeDataInode() == 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0089, code lost:
    
        r8.attributeLong((java.lang.String) null, "ceDataInode", r11.getCeDataInode());
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009c, code lost:
    
        if (r11.getDeDataInode() == 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009e, code lost:
    
        r8.attributeLong((java.lang.String) null, "deDataInode", r11.getDeDataInode());
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ab, code lost:
    
        if (r11.isInstalled() != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ad, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "inst", false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b6, code lost:
    
        if (r11.isStopped() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b8, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "stopped", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c2, code lost:
    
        if (r11.isNotLaunched() == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c4, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "nl", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ce, code lost:
    
        if (r11.isHidden() == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d0, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "hidden", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d9, code lost:
    
        if (r11.getDistractionFlags() == 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00db, code lost:
    
        r8.attributeInt((java.lang.String) null, "distraction_flags", r11.getDistractionFlags());
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e8, code lost:
    
        if (r11.isSuspended() == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ea, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "suspended", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f4, code lost:
    
        if (r11.isInstantApp() == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f6, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "instant-app", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ff, code lost:
    
        if (r11.isVirtualPreload() == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0101, code lost:
    
        r8.attributeBoolean((java.lang.String) null, "virtual-preload", true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x010b, code lost:
    
        if (r11.getEnabledState() == 0) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x010d, code lost:
    
        r8.attributeInt((java.lang.String) null, "enabled", r11.getEnabledState());
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x011a, code lost:
    
        if (r11.getLastDisableAppCaller() == null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x011c, code lost:
    
        r8.attribute((java.lang.String) null, "enabledCaller", r11.getLastDisableAppCaller());
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0129, code lost:
    
        if (r11.getInstallReason() == 0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x012b, code lost:
    
        r8.attributeInt((java.lang.String) null, "install-reason", r11.getInstallReason());
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0134, code lost:
    
        r8.attributeLongHex((java.lang.String) null, "first-install-time", r11.getFirstInstallTimeMillis());
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0141, code lost:
    
        if (r11.getUninstallReason() == 0) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0143, code lost:
    
        r8.attributeInt((java.lang.String) null, "uninstall-reason", r11.getUninstallReason());
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0151, code lost:
    
        if (r11.getHarmfulAppWarning() == null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0153, code lost:
    
        r8.attribute((java.lang.String) null, "harmful-app-warning", r11.getHarmfulAppWarning());
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0160, code lost:
    
        if (r11.getSplashScreenTheme() == null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0162, code lost:
    
        r8.attribute((java.lang.String) null, "splash-screen-theme", r11.getSplashScreenTheme());
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0170, code lost:
    
        if (r11.getMinAspectRatio() == 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0172, code lost:
    
        r8.attributeInt((java.lang.String) null, "min-aspect-ratio", r11.getMinAspectRatio());
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0180, code lost:
    
        if (r11.isSuspended() == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0182, code lost:
    
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x018d, code lost:
    
        if (r10 >= r11.getSuspendParams().mStorage.size()) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x018f, code lost:
    
        r12 = (android.content.pm.UserPackage) r11.getSuspendParams().mStorage.keyAt(r10);
        r8.startTag((java.lang.String) null, "suspend-params");
        r8.attribute((java.lang.String) null, "suspending-package", r12.packageName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01ad, code lost:
    
        if (android.app.admin.flags.Flags.crossUserSuspensionEnabledRo() == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01af, code lost:
    
        r8.attributeInt((java.lang.String) null, "suspending-user", r12.userId);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01b7, code lost:
    
        r12 = (com.android.server.pm.pkg.SuspendParams) r11.getSuspendParams().mStorage.valueAt(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01c3, code lost:
    
        if (r12 == null) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01c5, code lost:
    
        r12.saveToXml(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01c8, code lost:
    
        r8.endTag((java.lang.String) null, "suspend-params");
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01d0, code lost:
    
        r10 = r11.getEnabledComponents();
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01d4, code lost:
    
        if (r10 == null) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01da, code lost:
    
        if (r10.size() <= 0) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01dc, code lost:
    
        r8.startTag((java.lang.String) null, "enabled-components");
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01e6, code lost:
    
        if (r12 >= r10.size()) goto L159;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writePackageRestrictions(int r17, long r18, boolean r20) {
        /*
            Method dump skipped, instructions count: 712
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.Settings.writePackageRestrictions(int, long, boolean):void");
    }

    public final void writePackageRestrictionsLPr(final int i, final boolean z) {
        PackageManagerService.invalidatePackageInfoCache();
        ChangeIdStateCache.invalidate();
        dispatchChange(this);
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (z) {
            writePackageRestrictions(i, uptimeMillis, z);
            return;
        }
        synchronized (this.mPackageRestrictionsLock) {
            this.mPendingAsyncPackageRestrictionsWrites.put(i, this.mPendingAsyncPackageRestrictionsWrites.get(i, 0) + 1);
        }
        this.mHandler.obtainMessage(30, new Runnable() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Settings.this.writePackageRestrictions(i, uptimeMillis, z);
            }
        }).sendToTarget();
    }

    public final void writePersistentPreferredActivitiesLPr(TypedXmlSerializer typedXmlSerializer, int i) {
        typedXmlSerializer.startTag((String) null, "persistent-preferred-activities");
        PersistentPreferredIntentResolver persistentPreferredIntentResolver = (PersistentPreferredIntentResolver) this.mPersistentPreferredActivities.mStorage.get(i);
        if (persistentPreferredIntentResolver != null) {
            for (PersistentPreferredActivity persistentPreferredActivity : Collections.unmodifiableSet(persistentPreferredIntentResolver.mFilters)) {
                typedXmlSerializer.startTag((String) null, "item");
                typedXmlSerializer.attribute((String) null, "name", persistentPreferredActivity.mComponent.flattenToShortString());
                typedXmlSerializer.attributeBoolean((String) null, "set-by-dpm", persistentPreferredActivity.mIsSetByDpm);
                typedXmlSerializer.startTag((String) null, "filter");
                persistentPreferredActivity.mFilter.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "filter");
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "persistent-preferred-activities");
    }

    public final void writePreferredActivitiesLPr(int i, TypedXmlSerializer typedXmlSerializer, boolean z) {
        typedXmlSerializer.startTag((String) null, "preferred-activities");
        PreferredIntentResolver preferredIntentResolver = (PreferredIntentResolver) this.mPreferredActivities.mStorage.get(i);
        if (preferredIntentResolver != null) {
            for (PreferredActivity preferredActivity : Collections.unmodifiableSet(preferredIntentResolver.mFilters)) {
                typedXmlSerializer.startTag((String) null, "item");
                PreferredComponent preferredComponent = preferredActivity.mPref;
                String[] strArr = preferredComponent.mSetClasses;
                int length = strArr != null ? strArr.length : 0;
                typedXmlSerializer.attribute((String) null, "name", preferredComponent.mShortComponent);
                if (z) {
                    int i2 = preferredComponent.mMatch;
                    if (i2 != 0) {
                        typedXmlSerializer.attributeIntHex((String) null, "match", i2);
                    }
                    typedXmlSerializer.attributeBoolean((String) null, "always", preferredComponent.mAlways);
                    typedXmlSerializer.attributeInt((String) null, "set", length);
                    for (int i3 = 0; i3 < length; i3++) {
                        typedXmlSerializer.startTag((String) null, "set");
                        typedXmlSerializer.attribute((String) null, "name", preferredComponent.mSetComponents[i3]);
                        typedXmlSerializer.endTag((String) null, "set");
                    }
                }
                typedXmlSerializer.startTag((String) null, "filter");
                preferredActivity.mFilter.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "filter");
                typedXmlSerializer.endTag((String) null, "item");
            }
        }
        typedXmlSerializer.endTag((String) null, "preferred-activities");
    }
}
