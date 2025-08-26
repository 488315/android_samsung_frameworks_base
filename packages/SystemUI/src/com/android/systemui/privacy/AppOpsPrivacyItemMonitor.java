package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        @Override // com.android.systemui.appops.AppOpsController.Callback
        public final void onActiveStateChanged(String str, int i, int i2, boolean z) {
            AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = this.this$0;
            synchronized (appOpsPrivacyItemMonitor.lock) {
                try {
                    AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
                    companion.getClass();
                    if (!ArraysKt___ArraysKt.contains(i, AppOpsPrivacyItemMonitor.OPS_MIC_CAMERA) || appOpsPrivacyItemMonitor.micCameraAvailable) {
                        companion.getClass();
                        if (!ArraysKt___ArraysKt.contains(i, AppOpsPrivacyItemMonitor.OPS_LOCATION) || appOpsPrivacyItemMonitor.locationAvailable) {
                            List userProfiles = ((UserTrackerImpl) appOpsPrivacyItemMonitor.userTracker).getUserProfiles();
                            if (!(userProfiles instanceof Collection) || !userProfiles.isEmpty()) {
                                Iterator it = userProfiles.iterator();
                                while (it.hasNext()) {
                                    if (((UserInfo) it.next()).id == UserHandle.getUserId(i2)) {
                                        break;
                                    }
                                }
                            }
                            AppOpsPrivacyItemMonitor.Companion.getClass();
                            if (ArraysKt___ArraysKt.contains(i, AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS)) {
                                PrivacyLogger privacyLogger = appOpsPrivacyItemMonitor.logger;
                                privacyLogger.getClass();
                                LogLevel logLevel = LogLevel.INFO;
                                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(5);
                                LogBuffer logBuffer = privacyLogger.buffer;
                                LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                                ((LogMessageImpl) logMessageObtain).int1 = i;
                                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                logMessageImpl.int2 = i2;
                                logMessageImpl.str1 = str;
                                logMessageImpl.bool1 = z;
                                logBuffer.commit(logMessageObtain);
                                appOpsPrivacyItemMonitor.dispatchOnPrivacyItemsChanged();
                            }
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onProfilesChanged(List list) {
            AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
            this.this$0.onCurrentProfilesChanged();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
            this.this$0.onCurrentProfilesChanged();
        }
    };

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
                AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = this.this$0;
                synchronized (appOpsPrivacyItemMonitor.lock) {
                    PrivacyConfig privacyConfig2 = appOpsPrivacyItemMonitor.privacyConfig;
                    appOpsPrivacyItemMonitor.micCameraAvailable = privacyConfig2.micCameraAvailable;
                    appOpsPrivacyItemMonitor.locationAvailable = privacyConfig2.locationAvailable;
                    appOpsPrivacyItemMonitor.setListeningStateLocked();
                    Unit unit = Unit.INSTANCE;
                }
                this.this$0.dispatchOnPrivacyItemsChanged();
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
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor.dispatchOnPrivacyItemsChanged.1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1 = (PrivacyItemController$privacyItemMonitorCallback$1) callback;
                    privacyItemController$privacyItemMonitorCallback$1.getClass();
                    int i = PrivacyItemController.$r8$clinit;
                    privacyItemController$privacyItemMonitorCallback$1.this$0.update$5$1();
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println("AppOpsPrivacyItemMonitor:");
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            synchronized (this.lock) {
                indentingPrintWriterAsIndenting.println("Listening: " + this.listening);
                indentingPrintWriterAsIndenting.println("micCameraAvailable: " + this.micCameraAvailable);
                indentingPrintWriterAsIndenting.println("locationAvailable: " + this.locationAvailable);
                indentingPrintWriterAsIndenting.println("Callback: " + this.callback);
                Unit unit = Unit.INSTANCE;
            }
            List userProfiles = ((UserTrackerImpl) this.userTracker).getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            indentingPrintWriterAsIndenting.println("Current user ids: " + arrayList);
            indentingPrintWriterAsIndenting.decreaseIndent();
            indentingPrintWriterAsIndenting.flush();
        } catch (Throwable th) {
            indentingPrintWriterAsIndenting.decreaseIndent();
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
        LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = arrayList.toString();
        logBuffer.commit(logMessageObtain);
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
        } else if (i2 == 26) {
            privacyType = PrivacyType.TYPE_CAMERA;
        } else {
            if (i2 != 27 && i2 != 100) {
                if (i2 != 101) {
                    if (i2 != 120 && i2 != 121 && i2 != 136) {
                        return null;
                    }
                }
                privacyType = PrivacyType.TYPE_CAMERA;
            }
            privacyType = PrivacyType.TYPE_MICROPHONE;
        }
        return new PrivacyItem(privacyType, new PrivacyApplication(appOpItem.mPackageName, appOpItem.mUid), appOpItem.mTimeStartedElapsed, appOpItem.mIsDisabled, 0L, 16, null);
    }

    public static /* synthetic */ void getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
