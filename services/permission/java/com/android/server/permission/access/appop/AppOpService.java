package com.android.server.permission.access.appop;

import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.appop.AppOpsCheckingServiceInterface;
import com.android.server.appop.OnOpModeChangedListener;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.Unit;
import com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__MutableCollectionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.PrintWriter;
import libcore.util.EmptyArray;

/* compiled from: AppOpService.kt */
/* loaded from: classes2.dex */
public final class AppOpService implements AppOpsCheckingServiceInterface {
    public static final Companion Companion = new Companion(null);
    public static final String LOG_TAG = AppOpService.class.getSimpleName();
    public final Context context;
    public Handler handler;
    public Object lock;
    public final SparseArray opModeWatchers;
    public final ArrayMap packageModeWatchers;
    public final PackageAppOpPolicy packagePolicy;
    public final AccessCheckingService service;
    public SparseArray switchedOps;
    public final UidAppOpPolicy uidPolicy;

    /* compiled from: AppOpService.kt */
    public final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
    }

    public AppOpService(AccessCheckingService accessCheckingService) {
        this.service = accessCheckingService;
        SchemePolicy m52x318e5b8b = accessCheckingService.m52x318e5b8b("package", "app-op");
        Intrinsics.checkNotNull(m52x318e5b8b, "null cannot be cast to non-null type com.android.server.permission.access.appop.PackageAppOpPolicy");
        this.packagePolicy = (PackageAppOpPolicy) m52x318e5b8b;
        SchemePolicy m52x318e5b8b2 = accessCheckingService.m52x318e5b8b("uid", "app-op");
        Intrinsics.checkNotNull(m52x318e5b8b2, "null cannot be cast to non-null type com.android.server.permission.access.appop.UidAppOpPolicy");
        this.uidPolicy = (UidAppOpPolicy) m52x318e5b8b2;
        this.context = accessCheckingService.getContext();
        this.opModeWatchers = new SparseArray();
        this.packageModeWatchers = new ArrayMap();
    }

    public final SparseBooleanArray evalForegroundOps(ArrayMap arrayMap, SparseBooleanArray sparseBooleanArray) {
        if (arrayMap != null) {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                String str = (String) arrayMap.keyAt(i);
                if (((Number) arrayMap.valueAt(i)).intValue() == 4) {
                    if (sparseBooleanArray == null) {
                        sparseBooleanArray = new SparseBooleanArray();
                    }
                    evalForegroundWatchers(str, sparseBooleanArray);
                }
            }
        }
        return sparseBooleanArray;
    }

    public final void initialize() {
        this.handler = new Handler(this.context.getMainLooper());
        this.lock = new Object();
        this.switchedOps = new SparseArray();
        for (int i = 0; i < 136; i++) {
            int opToSwitch = AppOpsManager.opToSwitch(i);
            SparseArray sparseArray = this.switchedOps;
            SparseArray sparseArray2 = null;
            if (sparseArray == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchedOps");
                sparseArray = null;
            }
            SparseArray sparseArray3 = this.switchedOps;
            if (sparseArray3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("switchedOps");
            } else {
                sparseArray2 = sparseArray3;
            }
            sparseArray.put(opToSwitch, ArrayUtils.appendInt((int[]) sparseArray2.get(opToSwitch), i));
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseIntArray getNonDefaultUidModes(int i) {
        return opNameMapToOpIntMap(getUidModes(i));
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseIntArray getNonDefaultPackageModes(String str, int i) {
        return opNameMapToOpIntMap(getPackageModes(str, i));
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, int i2) {
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        String opToPublicName = AppOpsManager.opToPublicName(i2);
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            accessState = null;
        }
        return this.uidPolicy.getAppOpMode(new GetStateScope(accessState), appId, userId, opToPublicName);
    }

    public final ArrayMap getUidModes(int i) {
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            accessState = null;
        }
        return this.uidPolicy.getAppOpModes(new GetStateScope(accessState), appId, userId);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, int i2, int i3) {
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        String opToPublicName = AppOpsManager.opToPublicName(i2);
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                accessState = null;
            }
            AccessState copy = accessState.copy();
            ref$BooleanRef.element = Boolean.valueOf(this.uidPolicy.setAppOpMode(new MutateStateScope(accessState, copy), appId, userId, opToPublicName, i3)).booleanValue();
            accessCheckingService.persistence.write(copy);
            accessCheckingService.state = copy;
            accessCheckingService.policy.onStateMutated(new GetStateScope(copy));
            Unit unit = Unit.INSTANCE;
        }
        return ref$BooleanRef.element;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(String str, int i, int i2) {
        String opToPublicName = AppOpsManager.opToPublicName(i);
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            accessState = null;
        }
        return this.packagePolicy.getAppOpMode(new GetStateScope(accessState), str, i2, opToPublicName);
    }

    public final ArrayMap getPackageModes(String str, int i) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            accessState = null;
        }
        return this.packagePolicy.getAppOpModes(new GetStateScope(accessState), str, i);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(String str, int i, int i2, int i3) {
        String opToPublicName = AppOpsManager.opToPublicName(i);
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                accessState = null;
            }
            AccessState copy = accessState.copy();
            this.packagePolicy.setAppOpMode(new MutateStateScope(accessState, copy), str, i3, opToPublicName, i2);
            accessCheckingService.persistence.write(copy);
            accessCheckingService.state = copy;
            accessCheckingService.policy.onStateMutated(new GetStateScope(copy));
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                accessState = null;
            }
            AccessState copy = accessState.copy();
            this.uidPolicy.removeAppOpModes(new MutateStateScope(accessState, copy), appId, userId);
            accessCheckingService.persistence.write(copy);
            accessCheckingService.state = copy;
            accessCheckingService.policy.onStateMutated(new GetStateScope(copy));
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(String str, int i) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                accessState = null;
            }
            AccessState copy = accessState.copy();
            ref$BooleanRef.element = Boolean.valueOf(this.packagePolicy.removeAppOpModes(new MutateStateScope(accessState, copy), str, i)).booleanValue();
            accessCheckingService.persistence.write(copy);
            accessCheckingService.state = copy;
            accessCheckingService.policy.onStateMutated(new GetStateScope(copy));
            Unit unit = Unit.INSTANCE;
        }
        return ref$BooleanRef.element;
    }

    public final SparseIntArray opNameMapToOpIntMap(ArrayMap arrayMap) {
        if (arrayMap == null) {
            return new SparseIntArray();
        }
        SparseIntArray sparseIntArray = new SparseIntArray(arrayMap.size());
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = arrayMap.keyAt(i);
            sparseIntArray.put(AppOpsManager.strOpToOp((String) keyAt), ((Number) arrayMap.valueAt(i)).intValue());
        }
        return sparseIntArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void startWatchingOpModeChanged(OnOpModeChangedListener onOpModeChangedListener, int i) {
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            SparseArray sparseArray = this.opModeWatchers;
            Object obj2 = sparseArray.get(i);
            if (obj2 == null) {
                obj2 = new ArraySet();
                sparseArray.put(i, obj2);
            }
            ((ArraySet) obj2).add(onOpModeChangedListener);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void startWatchingPackageModeChanged(OnOpModeChangedListener onOpModeChangedListener, String str) {
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            ArrayMap arrayMap = this.packageModeWatchers;
            Object obj2 = arrayMap.get(str);
            if (obj2 == null) {
                obj2 = new ArraySet();
                arrayMap.put(str, obj2);
            }
            ((ArraySet) obj2).add(onOpModeChangedListener);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeListener(OnOpModeChangedListener onOpModeChangedListener) {
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            SparseArray sparseArray = this.opModeWatchers;
            for (int size = sparseArray.size() - 1; -1 < size; size--) {
                sparseArray.keyAt(size);
                ArraySet arraySet = (ArraySet) sparseArray.valueAt(size);
                arraySet.remove(onOpModeChangedListener);
                if (arraySet.isEmpty()) {
                    sparseArray.removeAt(size);
                }
            }
            ArrayMap arrayMap = this.packageModeWatchers;
            for (int size2 = arrayMap.size() - 1; -1 < size2; size2--) {
                Object keyAt = arrayMap.keyAt(size2);
                ArraySet arraySet2 = (ArraySet) arrayMap.valueAt(size2);
                arraySet2.remove(onOpModeChangedListener);
                if (arraySet2.isEmpty()) {
                    arrayMap.removeAt(size2);
                }
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public ArraySet getOpModeChangedListeners(int i) {
        ArraySet arraySet;
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            ArraySet arraySet2 = (ArraySet) this.opModeWatchers.get(i);
            if (arraySet2 == null) {
                arraySet = new ArraySet();
            } else {
                arraySet = new ArraySet(arraySet2);
            }
        }
        return arraySet;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public ArraySet getPackageModeChangedListeners(String str) {
        ArraySet arraySet;
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            ArraySet arraySet2 = (ArraySet) this.packageModeWatchers.get(str);
            if (arraySet2 == null) {
                arraySet = new ArraySet();
            } else {
                arraySet = new ArraySet(arraySet2);
            }
        }
        return arraySet;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyWatchersOfChange(int i, int i2) {
        ArraySet opModeChangedListeners = getOpModeChangedListeners(i);
        int size = opModeChangedListeners.size();
        for (int i3 = 0; i3 < size; i3++) {
            notifyOpChanged((OnOpModeChangedListener) opModeChangedListeners.valueAt(i3), i, i2, null);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyOpChanged(OnOpModeChangedListener onOpModeChangedListener, int i, int i2, String str) {
        int[] iArr;
        if (i2 == -2 || onOpModeChangedListener.getWatchingUid() < 0 || onOpModeChangedListener.getWatchingUid() == i2) {
            int watchedOpCode = onOpModeChangedListener.getWatchedOpCode();
            if (watchedOpCode == -2) {
                SparseArray sparseArray = this.switchedOps;
                if (sparseArray == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("switchedOps");
                    sparseArray = null;
                }
                iArr = (int[]) sparseArray.get(i);
            } else if (watchedOpCode == -1) {
                iArr = new int[]{i};
            } else {
                iArr = new int[]{onOpModeChangedListener.getWatchedOpCode()};
            }
            for (int i3 : iArr) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!shouldIgnoreCallback(i3, onOpModeChangedListener)) {
                        onOpModeChangedListener.onOpModeChanged(i3, i2, str);
                    }
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean shouldIgnoreCallback(int i, OnOpModeChangedListener onOpModeChangedListener) {
        return AppOpsManager.opRestrictsRead(i) && this.context.checkPermission("android.permission.MANAGE_APPOPS", onOpModeChangedListener.getCallingPid(), onOpModeChangedListener.getCallingUid()) != 0;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyOpChangedForAllPkgsInUid(int i, int i2, boolean z, OnOpModeChangedListener onOpModeChangedListener) {
        AppOpService appOpService = this;
        String[] packagesForUid = appOpService.getPackagesForUid(i2);
        ArrayMap arrayMap = new ArrayMap();
        Object obj = appOpService.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            ArraySet arraySet = (ArraySet) appOpService.opModeWatchers.get(i);
            if (arraySet != null) {
                int size = arraySet.size();
                for (int i3 = 0; i3 < size; i3++) {
                    notifyOpChangedForAllPkgsInUid$associateListenerWithPackageNames(z, arrayMap, (OnOpModeChangedListener) arraySet.valueAt(i3), packagesForUid);
                }
            }
            for (String str : packagesForUid) {
                ArraySet arraySet2 = (ArraySet) appOpService.packageModeWatchers.get(str);
                if (arraySet2 != null) {
                    int size2 = arraySet2.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        notifyOpChangedForAllPkgsInUid$associateListenerWithPackageNames(z, arrayMap, (OnOpModeChangedListener) arraySet2.valueAt(i4), new String[]{str});
                    }
                }
            }
            if (onOpModeChangedListener != null) {
                arrayMap.remove(onOpModeChangedListener);
            }
            Unit unit = Unit.INSTANCE;
        }
        int size3 = arrayMap.size();
        int i5 = 0;
        while (i5 < size3) {
            Object keyAt = arrayMap.keyAt(i5);
            ArraySet arraySet3 = (ArraySet) arrayMap.valueAt(i5);
            OnOpModeChangedListener onOpModeChangedListener2 = (OnOpModeChangedListener) keyAt;
            int size4 = arraySet3.size();
            int i6 = 0;
            while (i6 < size4) {
                String str2 = (String) arraySet3.valueAt(i6);
                Handler handler = appOpService.handler;
                if (handler == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("handler");
                    handler = null;
                }
                handler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: com.android.server.permission.access.appop.AppOpService$notifyOpChangedForAllPkgsInUid$2$1$1
                    public final void accept(AppOpService appOpService2, OnOpModeChangedListener onOpModeChangedListener3, int i7, int i8, String str3) {
                        appOpService2.notifyOpChanged(onOpModeChangedListener3, i7, i8, str3);
                    }

                    public /* bridge */ /* synthetic */ void accept(Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                        accept((AppOpService) obj2, (OnOpModeChangedListener) obj3, ((Number) obj4).intValue(), ((Number) obj5).intValue(), (String) obj6);
                    }
                }, this, onOpModeChangedListener2, Integer.valueOf(i), Integer.valueOf(i2), str2));
                i6++;
                appOpService = this;
            }
            i5++;
            appOpService = this;
        }
    }

    public static final void notifyOpChangedForAllPkgsInUid$associateListenerWithPackageNames(boolean z, ArrayMap arrayMap, OnOpModeChangedListener onOpModeChangedListener, String[] strArr) {
        boolean hasBits = IntExtensionsKt.hasBits(onOpModeChangedListener.getFlags(), 1);
        if (!z || hasBits) {
            Object obj = arrayMap.get(onOpModeChangedListener);
            if (obj == null) {
                obj = new ArraySet();
                arrayMap.put(onOpModeChangedListener, obj);
            }
            CollectionsKt__MutableCollectionsKt.addAll((ArraySet) obj, strArr);
        }
    }

    public final String[] getPackagesForUid(int i) {
        String[] packagesForUid;
        try {
            IPackageManager packageManager = AppGlobals.getPackageManager();
            return (packageManager == null || (packagesForUid = packageManager.getPackagesForUid(i)) == null) ? EmptyArray.STRING : packagesForUid;
        } catch (RemoteException unused) {
            return EmptyArray.STRING;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseBooleanArray evalForegroundUidOps(int i, SparseBooleanArray sparseBooleanArray) {
        SparseBooleanArray evalForegroundOps;
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            evalForegroundOps = evalForegroundOps(getUidModes(i), sparseBooleanArray);
        }
        return evalForegroundOps;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseBooleanArray evalForegroundPackageOps(String str, SparseBooleanArray sparseBooleanArray, int i) {
        SparseBooleanArray evalForegroundOps;
        Object obj = this.lock;
        if (obj == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lock");
            obj = Unit.INSTANCE;
        }
        synchronized (obj) {
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                accessState = null;
            }
            new GetStateScope(accessState);
            evalForegroundOps = evalForegroundOps(getPackageModes(str, i), sparseBooleanArray);
        }
        return evalForegroundOps;
    }

    public final void evalForegroundWatchers(String str, SparseBooleanArray sparseBooleanArray) {
        boolean z;
        int strOpToOp = AppOpsManager.strOpToOp(str);
        ArraySet arraySet = (ArraySet) this.opModeWatchers.get(strOpToOp);
        boolean z2 = true;
        if (!sparseBooleanArray.get(strOpToOp)) {
            if (arraySet != null) {
                int size = arraySet.size();
                for (int i = 0; i < size; i++) {
                    if (IntExtensionsKt.hasBits(((OnOpModeChangedListener) arraySet.valueAt(i)).getFlags(), 1)) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (!z) {
                z2 = false;
            }
        }
        sparseBooleanArray.put(strOpToOp, z2);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean dumpListeners(int i, int i2, String str, PrintWriter printWriter) {
        boolean z;
        SparseArray sparseArray;
        int i3;
        if (this.opModeWatchers.size() > 0) {
            SparseArray sparseArray2 = this.opModeWatchers;
            int size = sparseArray2.size();
            int i4 = 0;
            z = false;
            boolean z2 = false;
            while (i4 < size) {
                int keyAt = sparseArray2.keyAt(i4);
                ArraySet arraySet = (ArraySet) sparseArray2.valueAt(i4);
                if (i < 0 || i == keyAt) {
                    String opToName = AppOpsManager.opToName(keyAt);
                    int size2 = arraySet.size();
                    int i5 = 0;
                    boolean z3 = false;
                    while (i5 < size2) {
                        OnOpModeChangedListener onOpModeChangedListener = (OnOpModeChangedListener) arraySet.valueAt(i5);
                        if (str != null) {
                            sparseArray = sparseArray2;
                            i3 = size2;
                            if (i2 != UserHandle.getAppId(onOpModeChangedListener.getWatchingUid())) {
                                i5++;
                                size2 = i3;
                                sparseArray2 = sparseArray;
                            }
                        } else {
                            sparseArray = sparseArray2;
                            i3 = size2;
                        }
                        if (!z2) {
                            printWriter.println("  Op mode watchers:");
                            z2 = true;
                        }
                        if (!z3) {
                            printWriter.print("    Op ");
                            printWriter.print(opToName);
                            printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                            z3 = true;
                        }
                        printWriter.print("      #");
                        printWriter.print(i5);
                        printWriter.print(opToName);
                        printWriter.print(": ");
                        printWriter.println(onOpModeChangedListener.toString());
                        z = true;
                        i5++;
                        size2 = i3;
                        sparseArray2 = sparseArray;
                    }
                }
                i4++;
                sparseArray2 = sparseArray2;
            }
        } else {
            z = false;
        }
        if (this.packageModeWatchers.size() > 0 && i < 0) {
            ArrayMap arrayMap = this.packageModeWatchers;
            int size3 = arrayMap.size();
            boolean z4 = false;
            for (int i6 = 0; i6 < size3; i6++) {
                Object keyAt2 = arrayMap.keyAt(i6);
                ArraySet arraySet2 = (ArraySet) arrayMap.valueAt(i6);
                String str2 = (String) keyAt2;
                if (str == null || Intrinsics.areEqual(str, str2)) {
                    if (!z4) {
                        printWriter.println("  Package mode watchers:");
                        z4 = true;
                    }
                    printWriter.print("    Pkg ");
                    printWriter.print(str2);
                    printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                    int size4 = arraySet2.size();
                    for (int i7 = 0; i7 < size4; i7++) {
                        OnOpModeChangedListener onOpModeChangedListener2 = (OnOpModeChangedListener) arraySet2.valueAt(i7);
                        printWriter.print("      #");
                        printWriter.print(i7);
                        printWriter.print(": ");
                        printWriter.println(onOpModeChangedListener2.toString());
                    }
                    z = true;
                }
            }
        }
        return z;
    }
}
