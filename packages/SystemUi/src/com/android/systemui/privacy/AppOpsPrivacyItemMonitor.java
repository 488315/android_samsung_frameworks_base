package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.privacy.AppOpsPrivacyItemMonitor;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppOpsPrivacyItemMonitor implements PrivacyItemMonitor {
    public static final Companion Companion = new Companion(null);
    public static final int[] OPS;
    public static final int[] OPS_LOCATION;
    public static final int[] OPS_MIC_CAMERA;
    public static final int[] USER_INDEPENDENT_OPS;
    public final AppOpsController appOpsController;
    public final DelayableExecutor bgExecutor;
    public PrivacyItemMonitor.Callback callback;
    public final AppOpsPrivacyItemMonitor$configCallback$1 configCallback;
    public boolean listening;
    public boolean locationAvailable;
    public final PrivacyLogger logger;
    public boolean micCameraAvailable;
    public final PrivacyConfig privacyConfig;
    public final UserTracker userTracker;
    public final Object lock = new Object();
    public final AppOpsPrivacyItemMonitor$appOpsCallback$1 appOpsCallback = new AppOpsController.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
        
            if (kotlin.collections.ArraysKt___ArraysKt.contains(r9, com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS) != false) goto L33;
         */
        @Override // com.android.systemui.appops.AppOpsController.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onActiveStateChanged(boolean z, String str, int i, int i2) {
            AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = AppOpsPrivacyItemMonitor.this;
            synchronized (appOpsPrivacyItemMonitor.lock) {
                AppOpsPrivacyItemMonitor.Companion.getClass();
                if (!ArraysKt___ArraysKt.contains(i, AppOpsPrivacyItemMonitor.OPS_MIC_CAMERA) || appOpsPrivacyItemMonitor.micCameraAvailable) {
                    if (!ArraysKt___ArraysKt.contains(i, AppOpsPrivacyItemMonitor.OPS_LOCATION) || appOpsPrivacyItemMonitor.locationAvailable) {
                        List userProfiles = ((UserTrackerImpl) appOpsPrivacyItemMonitor.userTracker).getUserProfiles();
                        boolean z2 = false;
                        if (!(userProfiles instanceof Collection) || !userProfiles.isEmpty()) {
                            Iterator it = userProfiles.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                if (((UserInfo) it.next()).id == UserHandle.getUserId(i2)) {
                                    z2 = true;
                                    break;
                                }
                            }
                        }
                        if (!z2) {
                            AppOpsPrivacyItemMonitor.Companion.getClass();
                        }
                        appOpsPrivacyItemMonitor.logger.logUpdatedItemFromAppOps(z, str, i, i2);
                        appOpsPrivacyItemMonitor.dispatchOnPrivacyItemsChanged();
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
    };
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
            AppOpsPrivacyItemMonitor.this.onCurrentProfilesChanged();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
            AppOpsPrivacyItemMonitor.this.onCurrentProfilesChanged();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        int[] iArr = {26, 101, 27, 100, 120, 121};
        OPS_MIC_CAMERA = iArr;
        int[] iArr2 = {0, 1};
        OPS_LOCATION = iArr2;
        int length = iArr.length;
        int length2 = iArr2.length;
        int[] copyOf = Arrays.copyOf(iArr, length + length2);
        System.arraycopy(iArr2, 0, copyOf, length, length2);
        OPS = copyOf;
        USER_INDEPENDENT_OPS = new int[]{101, 100};
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1, com.android.systemui.privacy.PrivacyConfig$Callback] */
    public AppOpsPrivacyItemMonitor(AppOpsController appOpsController, UserTracker userTracker, PrivacyConfig privacyConfig, DelayableExecutor delayableExecutor, PrivacyLogger privacyLogger) {
        this.appOpsController = appOpsController;
        this.userTracker = userTracker;
        this.privacyConfig = privacyConfig;
        this.bgExecutor = delayableExecutor;
        this.logger = privacyLogger;
        this.micCameraAvailable = privacyConfig.micCameraAvailable;
        this.locationAvailable = privacyConfig.locationAvailable;
        ?? r1 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1
            public final void onFlagChanged() {
                AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = AppOpsPrivacyItemMonitor.this;
                synchronized (appOpsPrivacyItemMonitor.lock) {
                    PrivacyConfig privacyConfig2 = appOpsPrivacyItemMonitor.privacyConfig;
                    appOpsPrivacyItemMonitor.micCameraAvailable = privacyConfig2.micCameraAvailable;
                    appOpsPrivacyItemMonitor.locationAvailable = privacyConfig2.locationAvailable;
                    appOpsPrivacyItemMonitor.setListeningStateLocked();
                    Unit unit = Unit.INSTANCE;
                }
                AppOpsPrivacyItemMonitor.this.dispatchOnPrivacyItemsChanged();
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagLocationChanged(boolean z) {
                onFlagChanged();
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagMicCameraChanged(boolean z) {
                onFlagChanged();
            }
        };
        this.configCallback = r1;
        privacyConfig.addCallback(r1);
    }

    public final void dispatchOnPrivacyItemsChanged() {
        final PrivacyItemMonitor.Callback callback;
        synchronized (this.lock) {
            callback = this.callback;
        }
        if (callback != null) {
            ((ExecutorImpl) this.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$dispatchOnPrivacyItemsChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1 = (PrivacyItemController$privacyItemMonitorCallback$1) PrivacyItemMonitor.Callback.this;
                    privacyItemController$privacyItemMonitorCallback$1.getClass();
                    int i = PrivacyItemController.$r8$clinit;
                    PrivacyItemController privacyItemController = privacyItemController$privacyItemMonitorCallback$1.this$0;
                    privacyItemController.getClass();
                    ((ExecutorImpl) privacyItemController.bgExecutor).execute(new PrivacyItemController$update$1(privacyItemController));
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("AppOpsPrivacyItemMonitor:");
        asIndenting.increaseIndent();
        try {
            synchronized (this.lock) {
                asIndenting.println("Listening: " + this.listening);
                asIndenting.println("micCameraAvailable: " + this.micCameraAvailable);
                asIndenting.println("locationAvailable: " + this.locationAvailable);
                asIndenting.println("Callback: " + this.callback);
                Unit unit = Unit.INSTANCE;
            }
            List userProfiles = ((UserTrackerImpl) this.userTracker).getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            asIndenting.println("Current user ids: " + arrayList);
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0064, code lost:
    
        if (kotlin.collections.ArraysKt___ArraysKt.contains(r6.mCode, com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS) != false) goto L25;
     */
    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List getActivePrivacyItems() {
        ArrayList arrayList;
        boolean z;
        List activeAppOps = ((AppOpsControllerImpl) this.appOpsController).getActiveAppOps(true);
        List userProfiles = ((UserTrackerImpl) this.userTracker).getUserProfiles();
        synchronized (this.lock) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = ((ArrayList) activeAppOps).iterator();
            while (it.hasNext()) {
                Object next = it.next();
                AppOpItem appOpItem = (AppOpItem) next;
                boolean z2 = false;
                if (!(userProfiles instanceof Collection) || !userProfiles.isEmpty()) {
                    Iterator it2 = userProfiles.iterator();
                    while (it2.hasNext()) {
                        if (((UserInfo) it2.next()).id == UserHandle.getUserId(appOpItem.mUid)) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (!z) {
                }
                z2 = true;
                if (z2) {
                    arrayList2.add(next);
                }
            }
            arrayList = new ArrayList();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                PrivacyItem privacyItemLocked = toPrivacyItemLocked((AppOpItem) it3.next());
                if (privacyItemLocked != null) {
                    arrayList.add(privacyItemLocked);
                }
            }
        }
        return CollectionsKt___CollectionsKt.distinct(arrayList);
    }

    public final void onCurrentProfilesChanged() {
        List userProfiles = ((UserTrackerImpl) this.userTracker).getUserProfiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
        Iterator it = userProfiles.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
        }
        this.logger.logCurrentProfilesChanged(arrayList);
        dispatchOnPrivacyItemsChanged();
    }

    public final void setListeningStateLocked() {
        boolean z = this.callback != null && (this.micCameraAvailable || this.locationAvailable);
        if (this.listening == z) {
            return;
        }
        this.listening = z;
        UserTracker userTracker = this.userTracker;
        AppOpsPrivacyItemMonitor$appOpsCallback$1 appOpsPrivacyItemMonitor$appOpsCallback$1 = this.appOpsCallback;
        int[] iArr = OPS;
        AppOpsController appOpsController = this.appOpsController;
        if (z) {
            ((AppOpsControllerImpl) appOpsController).addCallback(iArr, appOpsPrivacyItemMonitor$appOpsCallback$1);
            ((UserTrackerImpl) userTracker).addCallback(this.userTrackerCallback, this.bgExecutor);
            onCurrentProfilesChanged();
            return;
        }
        AppOpsControllerImpl appOpsControllerImpl = (AppOpsControllerImpl) appOpsController;
        appOpsControllerImpl.getClass();
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (appOpsControllerImpl.mCallbacksByCode.contains(iArr[i])) {
                ((Set) appOpsControllerImpl.mCallbacksByCode.get(iArr[i])).remove(appOpsPrivacyItemMonitor$appOpsCallback$1);
            }
        }
        ((ArrayList) appOpsControllerImpl.mCallbacks).remove(appOpsPrivacyItemMonitor$appOpsCallback$1);
        if (((ArrayList) appOpsControllerImpl.mCallbacks).isEmpty()) {
            appOpsControllerImpl.setListening(false);
        }
        ((UserTrackerImpl) userTracker).removeCallback(this.userTrackerCallback);
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public final void startListening(PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1) {
        synchronized (this.lock) {
            this.callback = privacyItemController$privacyItemMonitorCallback$1;
            setListeningStateLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public final void stopListening() {
        synchronized (this.lock) {
            this.callback = null;
            setListeningStateLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final PrivacyItem toPrivacyItemLocked(AppOpItem appOpItem) {
        PrivacyType privacyType;
        int[] iArr = OPS_LOCATION;
        int i = appOpItem.mCode;
        if (!(ArraysKt___ArraysKt.contains(i, iArr) ? this.locationAvailable : ArraysKt___ArraysKt.contains(i, OPS_MIC_CAMERA) ? this.micCameraAvailable : false)) {
            return null;
        }
        if (i == 0 || i == 1) {
            privacyType = PrivacyType.TYPE_LOCATION;
        } else {
            if (i != 26) {
                if (i != 27 && i != 100) {
                    if (i != 101) {
                        if (i != 120 && i != 121) {
                            return null;
                        }
                    }
                }
                privacyType = PrivacyType.TYPE_MICROPHONE;
            }
            privacyType = PrivacyType.TYPE_CAMERA;
        }
        return new PrivacyItem(privacyType, new PrivacyApplication(appOpItem.mPackageName, appOpItem.mUid), appOpItem.mTimeStartedElapsed, appOpItem.mIsDisabled);
    }

    /* renamed from: getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m172x9bde9e2() {
    }
}
