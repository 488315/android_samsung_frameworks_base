package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.util.IndentingPrintWriter;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.privacy.AppOpsPrivacyItemMonitor;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AppOpsPrivacyItemMonitor implements PrivacyItemMonitor {
    public static final int[] OPS;
    public static final int[] OPS_LOCATION;
    public static final int[] OPS_MIC_CAMERA;
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
    public static final Companion Companion = new Companion(null);
    public static final int[] USER_INDEPENDENT_OPS = {101, 100};
    public final Object lock = new Object();
    public final AppOpsPrivacyItemMonitor$appOpsCallback$1 appOpsCallback = new AppOpsController.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:
        
            if (kotlin.collections.ArraysKt___ArraysKt.contains(r8, com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS) != false) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:
        
            r1 = r6.logger;
            r1.getClass();
            r2 = com.android.systemui.log.core.LogLevel.INFO;
            r3 = new com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0(5);
            r1 = r1.buffer;
            r2 = r1.obtain("PrivacyLog", r2, r3, null);
            ((com.android.systemui.log.LogMessageImpl) r2).int1 = r8;
            r8 = (com.android.systemui.log.LogMessageImpl) r2;
            r8.int2 = r9;
            r8.str1 = r7;
            r8.bool1 = r10;
            r1.commit(r2);
            r6.dispatchOnPrivacyItemsChanged();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0093, code lost:
        
            r6 = kotlin.Unit.INSTANCE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0096, code lost:
        
            return;
         */
        @Override // com.android.systemui.appops.AppOpsController.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onActiveStateChanged(java.lang.String r7, int r8, int r9, boolean r10) {
            /*
                r6 = this;
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor r6 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.this
                java.lang.Object r0 = r6.lock
                monitor-enter(r0)
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor$Companion r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.Companion     // Catch: java.lang.Throwable -> L41
                r1.getClass()     // Catch: java.lang.Throwable -> L41
                int[] r2 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.OPS_MIC_CAMERA     // Catch: java.lang.Throwable -> L41
                boolean r2 = kotlin.collections.ArraysKt___ArraysKt.contains(r8, r2)     // Catch: java.lang.Throwable -> L41
                if (r2 == 0) goto L18
                boolean r2 = r6.micCameraAvailable     // Catch: java.lang.Throwable -> L41
                if (r2 != 0) goto L18
                monitor-exit(r0)
                return
            L18:
                r1.getClass()     // Catch: java.lang.Throwable -> L41
                int[] r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.OPS_LOCATION     // Catch: java.lang.Throwable -> L41
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r8, r1)     // Catch: java.lang.Throwable -> L41
                if (r1 == 0) goto L29
                boolean r1 = r6.locationAvailable     // Catch: java.lang.Throwable -> L41
                if (r1 != 0) goto L29
                monitor-exit(r0)
                return
            L29:
                com.android.systemui.settings.UserTracker r1 = r6.userTracker     // Catch: java.lang.Throwable -> L41
                com.android.systemui.settings.UserTrackerImpl r1 = (com.android.systemui.settings.UserTrackerImpl) r1     // Catch: java.lang.Throwable -> L41
                java.util.List r1 = r1.getUserProfiles()     // Catch: java.lang.Throwable -> L41
                java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Throwable -> L41
                boolean r2 = r1 instanceof java.util.Collection     // Catch: java.lang.Throwable -> L41
                if (r2 == 0) goto L43
                r2 = r1
                java.util.Collection r2 = (java.util.Collection) r2     // Catch: java.lang.Throwable -> L41
                boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L41
                if (r2 == 0) goto L43
                goto L5c
            L41:
                r6 = move-exception
                goto L97
            L43:
                java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L41
            L47:
                boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L41
                if (r2 == 0) goto L5c
                java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L41
                android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2     // Catch: java.lang.Throwable -> L41
                int r2 = r2.id     // Catch: java.lang.Throwable -> L41
                int r3 = android.os.UserHandle.getUserId(r9)     // Catch: java.lang.Throwable -> L41
                if (r2 != r3) goto L47
                goto L69
            L5c:
                com.android.systemui.privacy.AppOpsPrivacyItemMonitor$Companion r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.Companion     // Catch: java.lang.Throwable -> L41
                r1.getClass()     // Catch: java.lang.Throwable -> L41
                int[] r1 = com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS     // Catch: java.lang.Throwable -> L41
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r8, r1)     // Catch: java.lang.Throwable -> L41
                if (r1 == 0) goto L93
            L69:
                com.android.systemui.privacy.logging.PrivacyLogger r1 = r6.logger     // Catch: java.lang.Throwable -> L41
                r1.getClass()     // Catch: java.lang.Throwable -> L41
                com.android.systemui.log.core.LogLevel r2 = com.android.systemui.log.core.LogLevel.INFO     // Catch: java.lang.Throwable -> L41
                com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0 r3 = new com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L41
                r4 = 5
                r3.<init>(r4)     // Catch: java.lang.Throwable -> L41
                java.lang.String r4 = "PrivacyLog"
                com.android.systemui.log.LogBuffer r1 = r1.buffer     // Catch: java.lang.Throwable -> L41
                r5 = 0
                com.android.systemui.log.core.LogMessage r2 = r1.obtain(r4, r2, r3, r5)     // Catch: java.lang.Throwable -> L41
                r3 = r2
                com.android.systemui.log.LogMessageImpl r3 = (com.android.systemui.log.LogMessageImpl) r3     // Catch: java.lang.Throwable -> L41
                r3.int1 = r8     // Catch: java.lang.Throwable -> L41
                r8 = r2
                com.android.systemui.log.LogMessageImpl r8 = (com.android.systemui.log.LogMessageImpl) r8     // Catch: java.lang.Throwable -> L41
                r8.int2 = r9     // Catch: java.lang.Throwable -> L41
                r8.str1 = r7     // Catch: java.lang.Throwable -> L41
                r8.bool1 = r10     // Catch: java.lang.Throwable -> L41
                r1.commit(r2)     // Catch: java.lang.Throwable -> L41
                r6.dispatchOnPrivacyItemsChanged()     // Catch: java.lang.Throwable -> L41
            L93:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L41
                monitor-exit(r0)
                return
            L97:
                monitor-exit(r0)
                throw r6
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        int[] iArr = {26, 101, 27, 100, 120, 121, 136};
        OPS_MIC_CAMERA = iArr;
        int[] iArr2 = {0, 1};
        OPS_LOCATION = iArr2;
        OPS = ArraysKt___ArraysJvmKt.plus(iArr, iArr2);
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
        PrivacyLogger privacyLogger = this.logger;
        privacyLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = privacyLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = arrayList.toString();
        logBuffer.commit(obtain);
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
        return new PrivacyItem(privacyType, new PrivacyApplication(appOpItem.mPackageName, appOpItem.mUid), appOpItem.mTimeStartedElapsed, appOpItem.mIsDisabled, 0L, 16, null);
    }

    public static /* synthetic */ void getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
