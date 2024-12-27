package com.android.server.pm.permission;

import android.app.AppOpsManager;
import android.content.AttributionSource;
import android.content.AttributionSourceState;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.IOnPermissionsChangeListener;
import android.permission.IPermissionChecker;
import android.permission.IPermissionManager;
import android.permission.PermissionManager;
import android.permission.PermissionManagerInternal;
import android.service.voice.VoiceInteractionManagerInternal;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.voiceinteraction.HotwordDetectionConnection$2$$ExternalSyntheticLambda0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionManagerService extends IPermissionManager.Stub {
    public static final ConcurrentHashMap sRunningAttributionSources = new ConcurrentHashMap();
    public final AppOpsManager mAppOpsManager;
    public final AttributionSourceRegistry mAttributionSourceRegistry;
    public AccessCheckDelegate$AccessCheckDelegateImpl mCheckPermissionDelegate;
    public final Context mContext;
    public HotwordDetectionConnection$2$$ExternalSyntheticLambda0 mHotwordDetectionServiceProvider;
    public final Object mLock = new Object();
    public final SparseArray mOneTimePermissionUserManagers = new SparseArray();
    public final PackageManagerInternal mPackageManagerInt;
    public final PermissionManagerServiceInterface mPermissionManagerServiceImpl;
    public VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AttributionSourceRegistry {
        public final Context mContext;
        public final Object mLock = new Object();
        public final WeakHashMap mAttributions = new WeakHashMap();

        public AttributionSourceRegistry(Context context) {
            this.mContext = context;
        }

        public final boolean isRegisteredAttributionSource(AttributionSource attributionSource) {
            synchronized (this.mLock) {
                try {
                    AttributionSource attributionSource2 = (AttributionSource) this.mAttributions.get(attributionSource.getToken());
                    if (attributionSource2 == null) {
                        return false;
                    }
                    return attributionSource2.equalsExceptToken(attributionSource);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerAttributionSource(AttributionSource attributionSource) {
            VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity;
            VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity2;
            int callingUid = Binder.getCallingUid();
            VoiceInteractionManagerInternal voiceInteractionManagerInternal = (VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class);
            if (voiceInteractionManagerInternal != null && (hotwordDetectionServiceIdentity2 = voiceInteractionManagerInternal.getHotwordDetectionServiceIdentity()) != null && callingUid == hotwordDetectionServiceIdentity2.getIsolatedUid()) {
                callingUid = hotwordDetectionServiceIdentity2.getOwnerUid();
            }
            int uid = attributionSource.getUid();
            VoiceInteractionManagerInternal voiceInteractionManagerInternal2 = (VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class);
            if (voiceInteractionManagerInternal2 != null && (hotwordDetectionServiceIdentity = voiceInteractionManagerInternal2.getHotwordDetectionServiceIdentity()) != null && uid == hotwordDetectionServiceIdentity.getIsolatedUid()) {
                uid = hotwordDetectionServiceIdentity.getOwnerUid();
            }
            if (uid != callingUid && this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, callingUid) != 0) {
                throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(uid, callingUid, "Cannot register attribution source for uid:", " from uid:"));
            }
            if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageUid(attributionSource.getPackageName(), 0L, UserHandle.getUserId(callingUid == 1000 ? uid : callingUid)) != uid) {
                throw new SecurityException("Cannot register attribution source for package:" + attributionSource.getPackageName() + " from uid:" + callingUid);
            }
            AttributionSource next = attributionSource.getNext();
            if (next == null || next.getNext() == null || isRegisteredAttributionSource(next)) {
                synchronized (this.mLock) {
                    this.mAttributions.put(attributionSource.getToken(), attributionSource.withDefaultToken());
                }
            } else {
                throw new SecurityException("Cannot register forged attribution source:" + attributionSource);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PermissionCheckerService extends IPermissionChecker.Stub {
        public final Context mContext;
        public final PermissionManagerServiceInternalImpl mPermissionManagerServiceInternal = (PermissionManagerServiceInternalImpl) LocalServices.getService(PermissionManagerServiceInternalImpl.class);
        public static final ConcurrentHashMap sPlatformPermissions = new ConcurrentHashMap();
        public static final AtomicInteger sAttributionChainIds = new AtomicInteger(0);

        public PermissionCheckerService(Context context) {
            this.mContext = context;
        }

        /* JADX WARN: Code restructure failed: missing block: B:147:?, code lost:
        
            return r18 ? 1 : 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0100, code lost:
        
            return 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
        
            return 2;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v19, types: [int] */
        /* JADX WARN: Type inference failed for: r0v21, types: [int] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int checkPermission(android.content.Context r27, com.android.server.pm.permission.PermissionManagerService.PermissionManagerServiceInternalImpl r28, java.lang.String r29, android.content.AttributionSource r30, java.lang.String r31, boolean r32, boolean r33, boolean r34, int r35) {
            /*
                Method dump skipped, instructions count: 785
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.checkPermission(android.content.Context, com.android.server.pm.permission.PermissionManagerService$PermissionManagerServiceInternalImpl, java.lang.String, android.content.AttributionSource, java.lang.String, boolean, boolean, boolean, int):int");
        }

        public static boolean checkPermission(Context context, PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl, String str, AttributionSource attributionSource) {
            int uid = attributionSource.getUid();
            int deviceId = attributionSource.getDeviceId();
            if (context.getDeviceId() != deviceId) {
                context = context.createDeviceContext(deviceId);
            }
            boolean z = context.checkPermission(str, -1, uid) == 0;
            if (!z && Process.isIsolated(uid) && (str.equals("android.permission.RECORD_AUDIO") || str.equals("android.permission.CAPTURE_AUDIO_HOTWORD") || str.equals("android.permission.CAPTURE_AUDIO_OUTPUT") || str.equals("android.permission.CAMERA"))) {
                HotwordDetectionConnection$2$$ExternalSyntheticLambda0 hotwordDetectionConnection$2$$ExternalSyntheticLambda0 = PermissionManagerService.this.mHotwordDetectionServiceProvider;
                z = hotwordDetectionConnection$2$$ExternalSyntheticLambda0 != null && uid == hotwordDetectionConnection$2$$ExternalSyntheticLambda0.f$0;
            }
            Set renouncedPermissions = attributionSource.getRenouncedPermissions();
            if (z && renouncedPermissions.contains(str) && context.checkPermission("android.permission.RENOUNCE_PERMISSIONS", -1, uid) == 0) {
                return false;
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x00a2, code lost:
        
            if (r5 == null) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00a4, code lost:
        
            r7 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) r2.remove(r5.getToken());
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00ae, code lost:
        
            if (r7 == null) goto L67;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00b0, code lost:
        
            r7.unregister();
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00b3, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void finishDataDelivery(android.content.Context r7, int r8, android.content.AttributionSourceState r9, boolean r10) {
            /*
                java.util.Objects.requireNonNull(r9)
                java.lang.Class<android.app.AppOpsManager> r0 = android.app.AppOpsManager.class
                java.lang.Object r0 = r7.getSystemService(r0)
                android.app.AppOpsManager r0 = (android.app.AppOpsManager) r0
                r1 = -1
                if (r8 != r1) goto Lf
                return
            Lf:
                android.content.AttributionSource r1 = new android.content.AttributionSource
                r1.<init>(r9)
                r2 = 0
            L15:
                r3 = 1
                r4 = 0
                if (r10 != 0) goto L1e
                if (r2 == 0) goto L1c
                goto L1e
            L1c:
                r2 = r4
                goto L1f
            L1e:
                r2 = r3
            L1f:
                android.content.AttributionSource r5 = r1.getNext()
                if (r10 == 0) goto L2b
                android.content.AttributionSourceState r6 = r1.asState()
                if (r6 == r9) goto L34
            L2b:
                if (r5 == 0) goto L34
                boolean r6 = r1.isTrusted(r7)
                if (r6 != 0) goto L34
                return
            L34:
                if (r10 == 0) goto L46
                android.content.AttributionSourceState r6 = r1.asState()
                if (r6 != r9) goto L46
                if (r5 == 0) goto L46
                android.content.AttributionSource r6 = r5.getNext()
                if (r6 != 0) goto L46
                r6 = r3
                goto L47
            L46:
                r6 = r4
            L47:
                if (r6 != 0) goto L4d
                if (r5 != 0) goto L4c
                goto L4d
            L4c:
                r3 = r4
            L4d:
                if (r6 != 0) goto L51
                r4 = r1
                goto L52
            L51:
                r4 = r5
            L52:
                if (r3 == 0) goto L65
                java.lang.String r2 = resolvePackageName(r7, r4)
                if (r2 != 0) goto L5b
                return
            L5b:
                android.content.AttributionSource r2 = r4.withPackageName(r2)
                android.os.IBinder r3 = r9.token
                r0.finishOp(r3, r8, r2)
                goto L84
            L65:
                java.lang.String r3 = r4.getPackageName()
                if (r3 == 0) goto L6c
                goto L74
            L6c:
                java.lang.String r3 = resolvePackageName(r7, r4)
                android.content.AttributionSource r4 = r4.withPackageName(r3)
            L74:
                java.lang.String r3 = r4.getPackageName()
                if (r3 != 0) goto L7b
                return
            L7b:
                android.os.IBinder r3 = r9.token
                java.lang.String r6 = android.app.AppOpsManager.opToPublicName(r8)
                r0.finishProxyOp(r3, r6, r4, r2)
            L84:
                java.util.concurrent.ConcurrentHashMap r2 = com.android.server.pm.permission.PermissionManagerService.sRunningAttributionSources
                android.os.IBinder r1 = r1.getToken()
                java.lang.Object r1 = r2.remove(r1)
                com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution r1 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) r1
                if (r1 == 0) goto L95
                r1.unregister()
            L95:
                if (r5 == 0) goto La2
                android.content.AttributionSource r1 = r5.getNext()
                if (r1 != 0) goto L9e
                goto La2
            L9e:
                r1 = r5
                r2 = r1
                goto L15
            La2:
                if (r5 == 0) goto Lb3
                android.os.IBinder r7 = r5.getToken()
                java.lang.Object r7 = r2.remove(r7)
                com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution r7 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) r7
                if (r7 == 0) goto Lb3
                r7.unregister()
            Lb3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.finishDataDelivery(android.content.Context, int, android.content.AttributionSourceState, boolean):void");
        }

        public static int performOpTransaction(Context context, IBinder iBinder, int i, AttributionSource attributionSource, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, int i3, int i4, int i5) {
            int i6;
            String str2;
            int noteProxyOpNoThrow;
            int i7;
            int i8;
            int i9;
            int startProxyOpNoThrow;
            int i10 = i2;
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            AttributionSource next = !z5 ? attributionSource : attributionSource.getNext();
            if (!z) {
                String resolvePackageName = resolvePackageName(context, next);
                if (resolvePackageName == null) {
                    return 2;
                }
                int unsafeCheckOpRawNoThrow = appOpsManager.unsafeCheckOpRawNoThrow(i, next.withPackageName(resolvePackageName));
                AttributionSource next2 = next.getNext();
                if (z4 || unsafeCheckOpRawNoThrow != 0 || next2 == null) {
                    return unsafeCheckOpRawNoThrow;
                }
                String resolvePackageName2 = resolvePackageName(context, next2);
                if (resolvePackageName2 == null) {
                    return 2;
                }
                return appOpsManager.unsafeCheckOpRawNoThrow(i, next2.withPackageName(resolvePackageName2));
            }
            if (z2) {
                AttributionSource withPackageName = next.getPackageName() != null ? next : next.withPackageName(resolvePackageName(context, next));
                if (withPackageName.getPackageName() == null) {
                    return 2;
                }
                if (i10 == -1 || i10 == i) {
                    i7 = i;
                    i8 = 0;
                } else {
                    int checkOpNoThrow = appOpsManager.checkOpNoThrow(i, withPackageName);
                    if (checkOpNoThrow == 2) {
                        return checkOpNoThrow;
                    }
                    i8 = checkOpNoThrow;
                    i7 = i10;
                }
                if (z4) {
                    int i11 = i8;
                    try {
                        startProxyOpNoThrow = appOpsManager.startOpNoThrow(iBinder, i7, withPackageName, false, str, i3, i5);
                    } catch (SecurityException unused) {
                        ConcurrentHashMap concurrentHashMap = PermissionManagerService.sRunningAttributionSources;
                        Slog.w("PermissionManagerService", "Datasource " + attributionSource + " protecting data with platform defined runtime permission " + AppOpsManager.opToPermission(i) + " while not having android.permission.UPDATE_APP_OPS_STATS");
                        startProxyOpNoThrow = appOpsManager.startProxyOpNoThrow(iBinder, i2, attributionSource, str, z3, i3, i4, i5);
                    }
                    i9 = i11;
                } else {
                    i9 = i8;
                    int i12 = i7;
                    try {
                        startProxyOpNoThrow = appOpsManager.startProxyOpNoThrow(iBinder, i7, withPackageName, str, z3, i3, i4, i5);
                    } catch (SecurityException e) {
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i12, "Security exception for op ", " with source ");
                        m.append(attributionSource.getUid());
                        m.append(":");
                        m.append(attributionSource.getPackageName());
                        m.append(", ");
                        m.append(attributionSource.getNextUid());
                        m.append(":");
                        m.append(attributionSource.getNextPackageName());
                        String sb = m.toString();
                        if (attributionSource.getNext() != null) {
                            AttributionSource next3 = attributionSource.getNext();
                            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(sb, ", ");
                            m2.append(next3.getNextPackageName());
                            m2.append(":");
                            m2.append(next3.getNextUid());
                            sb = m2.toString();
                        }
                        StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(sb, ":");
                        m3.append(e.getMessage());
                        throw new SecurityException(m3.toString());
                    }
                }
                return Math.max(i9, startProxyOpNoThrow);
            }
            if (next.getPackageName() == null) {
                next = next.withPackageName(resolvePackageName(context, next));
            }
            if (next.getPackageName() == null) {
                return 2;
            }
            if (i10 == -1 || i10 == i) {
                i10 = i;
                i6 = 0;
            } else {
                i6 = appOpsManager.checkOpNoThrow(i, next);
                if (i6 == 2) {
                    return i6;
                }
            }
            if (z4) {
                try {
                    noteProxyOpNoThrow = appOpsManager.noteOpNoThrow(i10, next, str);
                    str2 = "PermissionManagerService";
                } catch (SecurityException unused2) {
                    ConcurrentHashMap concurrentHashMap2 = PermissionManagerService.sRunningAttributionSources;
                    Slog.w("PermissionManagerService", "Datasource " + attributionSource + " protecting data with platform defined runtime permission " + AppOpsManager.opToPermission(i) + " while not having android.permission.UPDATE_APP_OPS_STATS");
                    str2 = "PermissionManagerService";
                    noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i10, attributionSource, str, z3);
                }
            } else {
                str2 = "PermissionManagerService";
                try {
                    noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i10, next, str, z3);
                } catch (SecurityException e2) {
                    StringBuilder m4 = BatteryService$$ExternalSyntheticOutline0.m(i10, "Security exception for op ", " with source ");
                    m4.append(attributionSource.getUid());
                    m4.append(":");
                    m4.append(attributionSource.getPackageName());
                    m4.append(", ");
                    m4.append(attributionSource.getNextUid());
                    m4.append(":");
                    m4.append(attributionSource.getNextPackageName());
                    String sb2 = m4.toString();
                    if (attributionSource.getNext() != null) {
                        AttributionSource next4 = attributionSource.getNext();
                        StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(sb2, ", ");
                        m5.append(next4.getNextPackageName());
                        m5.append(":");
                        m5.append(next4.getNextUid());
                        sb2 = m5.toString();
                    }
                    StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(sb2, ":");
                    m6.append(e2.getMessage());
                    throw new SecurityException(m6.toString());
                }
            }
            int max = Math.max(i6, noteProxyOpNoThrow);
            if (i == 111 && max == 2) {
                if (max == i6) {
                    ConcurrentHashMap concurrentHashMap3 = PermissionManagerService.sRunningAttributionSources;
                    Slog.e(str2, "BLUETOOTH_CONNECT permission hard denied as checkOp for resolvedAttributionSource " + next + " and op " + i + " returned MODE_ERRORED");
                } else {
                    ConcurrentHashMap concurrentHashMap4 = PermissionManagerService.sRunningAttributionSources;
                    Slog.e(str2, "BLUETOOTH_CONNECT permission hard denied as noteOp for resolvedAttributionSource " + next + " and op " + i10 + " returned MODE_ERRORED");
                }
            }
            return max;
        }

        public static int resolveAttributionFlags(AttributionSource attributionSource, AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            if (attributionSource2 == null || !z2) {
                return 0;
            }
            int i = z4 ? 8 : 0;
            if (z5) {
                if (z3) {
                    return i | 1;
                }
                if (!z && attributionSource2.equals(attributionSource)) {
                    return i | 1;
                }
            } else {
                if (z3) {
                    return i | 4;
                }
                if (z && attributionSource2.equals(attributionSource.getNext())) {
                    return i | 1;
                }
                if (attributionSource2.getNext() == null) {
                    return i | 4;
                }
            }
            if (z && attributionSource2.equals(attributionSource)) {
                return 0;
            }
            return i | 2;
        }

        public static String resolvePackageName(Context context, AttributionSource attributionSource) {
            if (attributionSource.getPackageName() != null) {
                return attributionSource.getPackageName();
            }
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(attributionSource.getUid());
            return packagesForUid != null ? packagesForUid[0] : AppOpsManager.resolvePackageName(attributionSource.getUid(), attributionSource.getPackageName());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r16v0 */
        /* JADX WARN: Type inference failed for: r16v1 */
        /* JADX WARN: Type inference failed for: r16v2 */
        /* JADX WARN: Type inference failed for: r17v0 */
        /* JADX WARN: Type inference failed for: r17v1 */
        /* JADX WARN: Type inference failed for: r17v2 */
        /* JADX WARN: Type inference failed for: r1v11, types: [int] */
        /* JADX WARN: Type inference failed for: r1v9, types: [int] */
        public final int checkOp(int i, AttributionSourceState attributionSourceState, String str, boolean z, boolean z2) {
            int i2;
            int i3;
            int i4;
            AttributionSource attributionSource;
            AttributionSource attributionSource2;
            boolean z3;
            Context context = this.mContext;
            PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = this.mPermissionManagerServiceInternal;
            AttributionSource attributionSource3 = new AttributionSource(attributionSourceState);
            boolean z4 = false;
            int i5 = 2;
            if (i >= 0 && attributionSource3.getPackageName() != null) {
                if (attributionSource3.getNext() == null || !z2) {
                    i4 = -1;
                } else {
                    AtomicInteger atomicInteger = sAttributionChainIds;
                    int incrementAndGet = atomicInteger.incrementAndGet();
                    if (incrementAndGet < 0) {
                        atomicInteger.set(0);
                        incrementAndGet = 0;
                    }
                    i4 = incrementAndGet;
                }
                boolean z5 = true;
                ?? r16 = i4 != -1;
                ?? r17 = !r16 == true || checkPermission(context, permissionManagerServiceInternalImpl, "android.permission.UPDATE_APP_OPS_STATS", attributionSource3);
                AttributionSource attributionSource4 = null;
                AttributionSource attributionSource5 = attributionSource3;
                while (true) {
                    boolean z6 = attributionSource4 != null ? z5 : z4;
                    AttributionSource next = attributionSource5.getNext();
                    if (next != null && !attributionSource5.isTrusted(context)) {
                        i3 = i5;
                        break;
                    }
                    boolean z7 = next == null ? z5 : z4;
                    boolean z8 = (r17 == true && (attributionSource5.isTrusted(context) || attributionSource5.equals(attributionSource3)) && (next == null || next.isTrusted(context))) ? z5 : z4;
                    if (z6 || !r16 == true) {
                        attributionSource = next;
                        attributionSource2 = attributionSource5;
                        z3 = z4;
                    } else {
                        attributionSource = next;
                        attributionSource2 = attributionSource5;
                        z3 = resolveAttributionFlags(attributionSource3, attributionSource5, false, z2, z7, z8, true);
                    }
                    boolean resolveAttributionFlags = r16 != false ? resolveAttributionFlags(attributionSource3, attributionSource, false, z2, z7, z8, false) : z4;
                    boolean z9 = z6;
                    int i6 = i4;
                    boolean z10 = z7;
                    i2 = i5;
                    AttributionSource attributionSource6 = attributionSource3;
                    boolean z11 = z3;
                    Context context2 = context;
                    int performOpTransaction = performOpTransaction(context, attributionSource2.getToken(), i, attributionSource2, str, z, z2, z9, z10, false, -1, z11 ? 1 : 0, resolveAttributionFlags ? 1 : 0, i6);
                    if (performOpTransaction == 1) {
                        i3 = 1;
                        break;
                    }
                    if (performOpTransaction == i2) {
                        break;
                    }
                    if (attributionSource == null || attributionSource.getNext() == null) {
                        break;
                    }
                    z4 = false;
                    i5 = i2;
                    z5 = true;
                    i4 = i6;
                    attributionSource3 = attributionSource6;
                    context = context2;
                    attributionSource4 = attributionSource;
                    attributionSource5 = attributionSource4;
                }
                i3 = 0;
                if (i3 != 0 && z2) {
                    finishDataDelivery(i, attributionSourceState, false);
                }
                return i3;
            }
            i2 = 2;
            i3 = i2;
            if (i3 != 0) {
                finishDataDelivery(i, attributionSourceState, false);
            }
            return i3;
        }

        public final int checkPermission(String str, AttributionSourceState attributionSourceState, String str2, boolean z, boolean z2, boolean z3, int i) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(attributionSourceState);
            AttributionSource attributionSource = new AttributionSource(attributionSourceState);
            int checkPermission = checkPermission(this.mContext, this.mPermissionManagerServiceInternal, str, attributionSource, str2, z, z2, z3, i);
            if (z2 && checkPermission != 0 && checkPermission != 1) {
                if (i == -1) {
                    finishDataDelivery(AppOpsManager.permissionToOpCode(str), attributionSource.asState(), z3);
                } else {
                    finishDataDelivery(i, attributionSource.asState(), z3);
                }
            }
            return checkPermission;
        }

        public final void finishDataDelivery(int i, AttributionSourceState attributionSourceState, boolean z) {
            finishDataDelivery(this.mContext, i, attributionSourceState, z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PermissionManagerServiceInternalImpl implements PermissionManagerInternal {
        public PermissionManagerServiceInternalImpl() {
        }

        public final byte[] backupRuntimePermissions(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.backupRuntimePermissions(i);
        }

        public final int checkPostNotificationsPermissionGrantedOrLegacyAccess(int i) {
            int checkUidPermission = PermissionManagerService.this.checkUidPermission(i, "android.permission.POST_NOTIFICATIONS", 0);
            AndroidPackage androidPackage = PermissionManagerService.this.mPackageManagerInt.getPackage(i);
            if (androidPackage == null) {
                ConcurrentHashMap concurrentHashMap = PermissionManagerService.sRunningAttributionSources;
                NandswapManager$$ExternalSyntheticOutline0.m(i, "No package for uid ", "PermissionManagerService");
                return checkUidPermission;
            }
            if (checkUidPermission != 0 && androidPackage.getTargetSdkVersion() >= 23) {
                PermissionManagerService permissionManagerService = PermissionManagerService.this;
                int permissionFlags = permissionManagerService.mPermissionManagerServiceImpl.getPermissionFlags(androidPackage.getPackageName(), "android.permission.POST_NOTIFICATIONS", "default:0", UserHandle.getUserId(i));
                if ((permissionFlags & 64) != 0 && (permissionFlags & 1) == 0) {
                    return 0;
                }
            }
            return checkUidPermission;
        }

        public final int[] getGidsForUid(int i) {
            return PermissionManagerService.this.mPermissionManagerServiceImpl.getGidsForUid(i);
        }

        public final void onPackageInstalled(AndroidPackage androidPackage, PermissionManagerServiceInternal$PackageInstalledParams permissionManagerServiceInternal$PackageInstalledParams, int i) {
            int[] iArr;
            Objects.requireNonNull(androidPackage, "pkg");
            Objects.requireNonNull(permissionManagerServiceInternal$PackageInstalledParams, "params");
            Preconditions.checkArgument(i >= 0 || i == -1, "userId");
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageInstalled(androidPackage, permissionManagerServiceInternal$PackageInstalledParams, i);
            if (i == -1) {
                PermissionManagerService.this.getClass();
                iArr = UserManagerService.getInstance().getUserIdsIncludingPreCreated();
            } else {
                iArr = new int[]{i};
            }
            for (int i2 : iArr) {
                int i3 = permissionManagerServiceInternal$PackageInstalledParams.mAutoRevokePermissionsMode;
                if (i3 == 0 || i3 == 1) {
                    PermissionManagerService.this.setAutoRevokeExemptedInternal(androidPackage, i3 == 1, i2);
                }
            }
        }

        public final void onPackageUninstalled(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int i2) {
            if (i2 != -1) {
                PermissionManagerService.this.getClass();
                if (!ArrayUtils.contains(UserManagerService.getInstance().getUserIdsIncludingPreCreated(), i2)) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i2, "Skipping onPackageUninstalled() for non-existent user ", "PermissionManagerService");
                    return;
                }
            }
            PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
        }

        public final void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionsTEMP(legacyPermissionSettings);
        }

        public final void restoreDelayedRuntimePermissions(String str, int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.restoreDelayedRuntimePermissions(str, i);
        }

        public final void restoreRuntimePermissions(byte[] bArr, int i) {
            PermissionManagerService.this.mPermissionManagerServiceImpl.restoreRuntimePermissions(bArr, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RegisteredAttribution {
        public final PermissionManagerService$RegisteredAttribution$$ExternalSyntheticLambda0 mDeathRecipient;
        public final AtomicBoolean mFinished = new AtomicBoolean(false);
        public final IBinder mToken;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [android.os.IBinder$DeathRecipient, com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution$$ExternalSyntheticLambda0] */
        public RegisteredAttribution(final Context context, final int i, final AttributionSource attributionSource, final boolean z) {
            ?? r0 = new IBinder.DeathRecipient() { // from class: com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    PermissionManagerService.RegisteredAttribution registeredAttribution = PermissionManagerService.RegisteredAttribution.this;
                    Context context2 = context;
                    int i2 = i;
                    AttributionSource attributionSource2 = attributionSource;
                    boolean z2 = z;
                    if (registeredAttribution.unregister()) {
                        PermissionManagerService.PermissionCheckerService.finishDataDelivery(context2, i2, attributionSource2.asState(), z2);
                    }
                }
            };
            this.mDeathRecipient = r0;
            IBinder token = attributionSource.getToken();
            this.mToken = token;
            if (token != 0) {
                try {
                    token.linkToDeath(r0, 0);
                } catch (RemoteException unused) {
                    binderDied();
                }
            }
        }

        public final boolean unregister() {
            if (!this.mFinished.compareAndSet(false, true)) {
                return false;
            }
            try {
                IBinder iBinder = this.mToken;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(this.mDeathRecipient, 0);
                }
            } catch (NoSuchElementException unused) {
            }
            return true;
        }
    }

    public PermissionManagerService(Context context, ArrayMap arrayMap) {
        PackageManager.invalidatePackageInfoCache();
        PermissionManager.disablePackageNamePermissionCache();
        this.mContext = context;
        this.mPackageManagerInt = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mVirtualDeviceManagerInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
        this.mAttributionSourceRegistry = new AttributionSourceRegistry(context);
        PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = new PermissionManagerServiceInternalImpl();
        LocalServices.addService(PermissionManagerServiceInternalImpl.class, permissionManagerServiceInternalImpl);
        LocalServices.addService(PermissionManagerInternal.class, permissionManagerServiceInternalImpl);
        if (PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
            this.mPermissionManagerServiceImpl = (PermissionManagerServiceInterface) LocalServices.getService(PermissionManagerServiceInterface.class);
        } else {
            this.mPermissionManagerServiceImpl = new PermissionManagerServiceImpl(context, arrayMap);
        }
    }

    public final boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.addAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public final void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public final boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        return this.mPermissionManagerServiceImpl.addPermission(permissionInfo, z);
    }

    public final boolean checkAutoRevokeAccess(AndroidPackage androidPackage, int i) {
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInt).mService.snapshotComputer().isCallerInstallerOfRecord(androidPackage, i);
        if (!z && !isCallerInstallerOfRecord) {
            throw new SecurityException("Caller must either hold android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS or be the installer on record");
        }
        if (androidPackage != null) {
            if (!((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInt).mService.snapshotComputer().filterAppAccess(androidPackage, i, UserHandle.getUserId(i))) {
                return true;
            }
        }
        return false;
    }

    public final int checkPermission(String str, String str2, String str3, int i) {
        AccessCheckDelegate$AccessCheckDelegateImpl accessCheckDelegate$AccessCheckDelegateImpl;
        if (str == null || str2 == null) {
            return -1;
        }
        synchronized (this.mLock) {
            accessCheckDelegate$AccessCheckDelegateImpl = this.mCheckPermissionDelegate;
        }
        if (accessCheckDelegate$AccessCheckDelegateImpl == null) {
            return this.mPermissionManagerServiceImpl.checkPermission(str, str2, str3, i);
        }
        PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        Objects.requireNonNull(permissionManagerServiceInterface);
        return accessCheckDelegate$AccessCheckDelegateImpl.checkPermission(str, str2, str3, i, new PermissionManagerService$$ExternalSyntheticLambda0(permissionManagerServiceInterface));
    }

    public final int checkUidPermission(int i, String str, int i2) {
        AccessCheckDelegate$AccessCheckDelegateImpl accessCheckDelegate$AccessCheckDelegateImpl;
        if (str == null) {
            return -1;
        }
        String persistentDeviceId = getPersistentDeviceId(i2);
        synchronized (this.mLock) {
            accessCheckDelegate$AccessCheckDelegateImpl = this.mCheckPermissionDelegate;
        }
        if (accessCheckDelegate$AccessCheckDelegateImpl == null) {
            return this.mPermissionManagerServiceImpl.checkUidPermission(i, str, persistentDeviceId);
        }
        PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        Objects.requireNonNull(permissionManagerServiceInterface);
        return accessCheckDelegate$AccessCheckDelegateImpl.checkUidPermission(i, str, persistentDeviceId, new PermissionManagerService$$ExternalSyntheticLambda1(permissionManagerServiceInterface));
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mPermissionManagerServiceImpl.dump(printWriter, strArr);
    }

    public final ParceledListSlice getAllPermissionGroups(int i) {
        return new ParceledListSlice(this.mPermissionManagerServiceImpl.getAllPermissionGroups(i));
    }

    public final Map getAllPermissionStates(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.getAllPermissionStates(str, str2, i);
    }

    public final List getAllowlistedRestrictedPermissions(String str, int i, int i2) {
        return this.mPermissionManagerServiceImpl.getAllowlistedRestrictedPermissions(str, i, i2);
    }

    public final List getAutoRevokeExemptionGrantedPackages(int i) {
        this.mContext.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Must hold android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
        ArrayList arrayList = new ArrayList();
        this.mPackageManagerInt.forEachInstalledPackage(i, new PermissionManagerService$$ExternalSyntheticLambda2(2, arrayList));
        return arrayList;
    }

    public final List getAutoRevokeExemptionRequestedPackages(int i) {
        this.mContext.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Must hold android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
        ArrayList arrayList = new ArrayList();
        this.mPackageManagerInt.forEachInstalledPackage(i, new PermissionManagerService$$ExternalSyntheticLambda2(1, arrayList));
        return arrayList;
    }

    public final OneTimePermissionUserManager getOneTimePermissionUserManager(int i) {
        synchronized (this.mLock) {
            try {
                OneTimePermissionUserManager oneTimePermissionUserManager = (OneTimePermissionUserManager) this.mOneTimePermissionUserManagers.get(i);
                if (oneTimePermissionUserManager != null) {
                    return oneTimePermissionUserManager;
                }
                OneTimePermissionUserManager oneTimePermissionUserManager2 = new OneTimePermissionUserManager(this.mContext.createContextAsUser(UserHandle.of(i), 0));
                synchronized (this.mLock) {
                    try {
                        OneTimePermissionUserManager oneTimePermissionUserManager3 = (OneTimePermissionUserManager) this.mOneTimePermissionUserManagers.get(i);
                        if (oneTimePermissionUserManager3 != null) {
                            return oneTimePermissionUserManager3;
                        }
                        this.mOneTimePermissionUserManagers.put(i, oneTimePermissionUserManager2);
                        oneTimePermissionUserManager2.mContext.registerReceiver(oneTimePermissionUserManager2.mUninstallListener, new IntentFilter("android.intent.action.UID_REMOVED"));
                        return oneTimePermissionUserManager2;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final int getPermissionFlags(String str, String str2, String str3, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionFlags(str, str2, str3, i);
    }

    public final PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionGroupInfo(str, i);
    }

    public final PermissionInfo getPermissionInfo(String str, String str2, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionInfo(i, str, str2);
    }

    public final String getPersistentDeviceId(int i) {
        if (i == 0) {
            return "default:0";
        }
        if (this.mVirtualDeviceManagerInternal == null) {
            this.mVirtualDeviceManagerInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
        }
        return this.mVirtualDeviceManagerInternal.getPersistentIdForDevice(i);
    }

    public final int getRegisteredAttributionSourceCount(int i) {
        int i2;
        AttributionSourceRegistry attributionSourceRegistry = this.mAttributionSourceRegistry;
        attributionSourceRegistry.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_APP_OPS_STATS", "getting the number of registered AttributionSources requires UPDATE_APP_OPS_STATS");
        System.gc();
        System.gc();
        synchronized (attributionSourceRegistry.mLock) {
            try {
                Iterator it = attributionSourceRegistry.mAttributions.entrySet().iterator();
                i2 = 0;
                while (it.hasNext()) {
                    if (((AttributionSource) ((Map.Entry) it.next()).getValue()).getUid() == i) {
                        i2++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final List getSplitPermissions() {
        return this.mPermissionManagerServiceImpl.getSplitPermissions();
    }

    public final void grantRuntimePermission(String str, String str2, String str3, int i) {
        this.mPermissionManagerServiceImpl.grantRuntimePermission(str, str2, str3, i);
    }

    public final boolean isAutoRevokeExempted(String str, int i) {
        Objects.requireNonNull(str);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (!checkAutoRevokeAccess(androidPackage, Binder.getCallingUid())) {
            return false;
        }
        int uid = UserHandle.getUid(i, androidPackage.getUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAppOpsManager.checkOpNoThrow(97, new AttributionSource(uid, str, null)) == 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.isPermissionRevokedByPolicy(i2, str, str2, getPersistentDeviceId(i));
    }

    public final boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) {
        return this.mAttributionSourceRegistry.isRegisteredAttributionSource(new AttributionSource(attributionSourceState));
    }

    public final ParceledListSlice queryPermissionsByGroup(String str, int i) {
        List queryPermissionsByGroup = this.mPermissionManagerServiceImpl.queryPermissionsByGroup(i, str);
        if (queryPermissionsByGroup == null) {
            return null;
        }
        return new ParceledListSlice(queryPermissionsByGroup);
    }

    public final IBinder registerAttributionSource(AttributionSourceState attributionSourceState) {
        if (!Flags.serverSideAttributionRegistration()) {
            this.mAttributionSourceRegistry.registerAttributionSource(new AttributionSource(attributionSourceState));
            return attributionSourceState.token;
        }
        Binder binder = new Binder();
        this.mAttributionSourceRegistry.registerAttributionSource(new AttributionSource(attributionSourceState).withToken(binder));
        return binder;
    }

    public final boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.removeAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public final void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public final void removePermission(String str) {
        this.mPermissionManagerServiceImpl.removePermission(str);
    }

    public final void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        this.mPermissionManagerServiceImpl.revokePostNotificationPermissionWithoutKillForTest(str, i);
    }

    public final void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) {
        this.mPermissionManagerServiceImpl.revokeRuntimePermission(str, str2, str3, i, str4);
    }

    public final boolean setAutoRevokeExempted(String str, boolean z, int i) {
        Objects.requireNonNull(str);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (checkAutoRevokeAccess(androidPackage, Binder.getCallingUid())) {
            return setAutoRevokeExemptedInternal(androidPackage, z, i);
        }
        return false;
    }

    public final boolean setAutoRevokeExemptedInternal(AndroidPackage androidPackage, boolean z, int i) {
        int uid = UserHandle.getUid(i, androidPackage.getUid());
        if (this.mAppOpsManager.checkOpNoThrow(98, new AttributionSource(uid, androidPackage.getPackageName(), null)) != 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOpsManager.setMode(97, uid, androidPackage.getPackageName(), z ? 1 : 0);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.shouldShowRequestPermissionRationale(i2, str, str2, getPersistentDeviceId(i));
    }

    public final void startOneTimePermissionSession(String str, int i, int i2, long j, long j2) {
        startOneTimePermissionSession_enforcePermission();
        Objects.requireNonNull(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i2).startPackageOneTimeSession(i, j, j2, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void stopOneTimePermissionSession(String str, int i) {
        stopOneTimePermissionSession_enforcePermission();
        Objects.requireNonNull(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i).stopPackageOneTimeSession(str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
    }

    public final void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlagsForAllApps(i, i2, i3);
    }
}
