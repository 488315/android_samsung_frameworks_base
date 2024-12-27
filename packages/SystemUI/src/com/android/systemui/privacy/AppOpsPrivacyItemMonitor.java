package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
        
            if (kotlin.collections.ArraysKt___ArraysKt.contains(r6, com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS) != false) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
        
            r4.logger.logUpdatedItemFromAppOps(r5, r6, r7, r8);
            r4.dispatchOnPrivacyItemsChanged();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:
        
            r4 = kotlin.Unit.INSTANCE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
        
            return;
         */
        @Override // com.android.systemui.appops.AppOpsController.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onActiveStateChanged(java.lang.String r5, int r6, int r7, boolean r8) {
            /*
                r4 = this;
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor r4 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.this
                java.lang.Object r0 = r4.lock
                monitor-enter(r0)
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor$Companion r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.Companion     // Catch: java.lang.Throwable -> L3e
                r1.getClass()     // Catch: java.lang.Throwable -> L3e
                int[] r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.OPS_MIC_CAMERA     // Catch: java.lang.Throwable -> L3e
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r6, r1)     // Catch: java.lang.Throwable -> L3e
                if (r1 == 0) goto L18
                boolean r1 = r4.micCameraAvailable     // Catch: java.lang.Throwable -> L3e
                if (r1 != 0) goto L18
                monitor-exit(r0)
                return
            L18:
                int[] r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.OPS_LOCATION     // Catch: java.lang.Throwable -> L3e
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r6, r1)     // Catch: java.lang.Throwable -> L3e
                if (r1 == 0) goto L26
                boolean r1 = r4.locationAvailable     // Catch: java.lang.Throwable -> L3e
                if (r1 != 0) goto L26
                monitor-exit(r0)
                return
            L26:
                com.android.systemui.settings.UserTracker r1 = r4.userTracker     // Catch: java.lang.Throwable -> L3e
                com.android.systemui.settings.UserTrackerImpl r1 = (com.android.systemui.settings.UserTrackerImpl) r1     // Catch: java.lang.Throwable -> L3e
                java.util.List r1 = r1.getUserProfiles()     // Catch: java.lang.Throwable -> L3e
                java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Throwable -> L3e
                boolean r2 = r1 instanceof java.util.Collection     // Catch: java.lang.Throwable -> L3e
                if (r2 == 0) goto L40
                r2 = r1
                java.util.Collection r2 = (java.util.Collection) r2     // Catch: java.lang.Throwable -> L3e
                boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L3e
                if (r2 == 0) goto L40
                goto L59
            L3e:
                r4 = move-exception
                goto L72
            L40:
                java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L3e
            L44:
                boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L3e
                if (r2 == 0) goto L59
                java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L3e
                android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2     // Catch: java.lang.Throwable -> L3e
                int r2 = r2.id     // Catch: java.lang.Throwable -> L3e
                int r3 = android.os.UserHandle.getUserId(r7)     // Catch: java.lang.Throwable -> L3e
                if (r2 != r3) goto L44
                goto L66
            L59:
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor$Companion r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.Companion     // Catch: java.lang.Throwable -> L3e
                r1.getClass()     // Catch: java.lang.Throwable -> L3e
                int[] r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS     // Catch: java.lang.Throwable -> L3e
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r6, r1)     // Catch: java.lang.Throwable -> L3e
                if (r1 == 0) goto L6e
            L66:
                com.android.systemui.privacy.logging.PrivacyLogger r1 = r4.logger     // Catch: java.lang.Throwable -> L3e
                r1.logUpdatedItemFromAppOps(r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L3e
                r4.dispatchOnPrivacyItemsChanged()     // Catch: java.lang.Throwable -> L3e
            L6e:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L3e
                monitor-exit(r0)
                return
            L72:
                monitor-exit(r0)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(java.lang.String, int, int, boolean):void");
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        int[] iArr = {26, 101, 27, 100, 120, 121, 136};
        OPS_MIC_CAMERA = iArr;
        int[] iArr2 = {0, 1};
        OPS_LOCATION = iArr2;
        int[] copyOf = Arrays.copyOf(iArr, 9);
        System.arraycopy(iArr2, 0, copyOf, 7, 2);
        Intrinsics.checkNotNull(copyOf);
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
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$dispatchOnPrivacyItemsChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1 = (PrivacyItemController$privacyItemMonitorCallback$1) PrivacyItemMonitor.Callback.this;
                    privacyItemController$privacyItemMonitorCallback$1.getClass();
                    int i = PrivacyItemController.$r8$clinit;
                    privacyItemController$privacyItemMonitorCallback$1.this$0.update$4$1();
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

    public final PrivacyItem toPrivacyItemLocked(AppOpItem appOpItem) {
        PrivacyType privacyType;
        int i = appOpItem.mCode;
        if (!(ArraysKt___ArraysKt.contains(i, OPS_LOCATION) ? this.locationAvailable : ArraysKt___ArraysKt.contains(i, OPS_MIC_CAMERA) ? this.micCameraAvailable : false)) {
            return null;
        }
        int i2 = appOpItem.mCode;
        if (i2 == 0 || i2 == 1) {
            privacyType = PrivacyType.TYPE_LOCATION;
        } else {
            if (i2 != 26) {
                if (i2 != 27 && i2 != 100) {
                    if (i2 != 101) {
                        if (i2 != 120 && i2 != 121 && i2 != 136) {
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

    public static /* synthetic */ void getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
