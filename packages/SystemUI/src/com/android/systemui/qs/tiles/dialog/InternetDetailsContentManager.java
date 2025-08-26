package com.android.systemui.qs.tiles.dialog;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.lifecycle.MutableLiveData;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentController;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.wifitrackerlib.WifiEntry;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class InternetDetailsContentManager {
    public static final boolean DEBUG;
    public final Executor backgroundExecutor;
    public final Handler handler;
    public final MutableLiveData internetContentData = new MutableLiveData();
    public final InternetDetailsContentManager$internetDetailsCallback$1 internetDetailsCallback;
    public final InternetDetailsContentController internetDetailsContentController;
    public boolean isProgressBarVisible;
    public final KeyguardStateController keyguard;
    public final MutableState subTitle$delegate;
    public final MutableState title$delegate;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        InternetDetailsContentManager create(boolean z, boolean z2);
    }

    public final class InternetContent {
        public final int activeAutoSwitchNonDdsSubId;
        public final boolean activeNetworkIsCellular;
        public final boolean hasActiveSubIdOnDds;
        public final boolean hasEthernet;
        public final boolean isAirplaneModeEnabled;
        public final boolean isCarrierNetworkActive;
        public final boolean isDeviceLocked;
        public final boolean isWifiEnabled;
        public final boolean isWifiScanEnabled;
        public final boolean shouldUpdateMobileNetwork;

        public InternetContent() {
            this(false, false, false, false, false, false, false, false, false, 0, 1023, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InternetContent)) {
                return false;
            }
            InternetContent internetContent = (InternetContent) obj;
            return this.isAirplaneModeEnabled == internetContent.isAirplaneModeEnabled && this.hasEthernet == internetContent.hasEthernet && this.shouldUpdateMobileNetwork == internetContent.shouldUpdateMobileNetwork && this.activeNetworkIsCellular == internetContent.activeNetworkIsCellular && this.isCarrierNetworkActive == internetContent.isCarrierNetworkActive && this.isWifiEnabled == internetContent.isWifiEnabled && this.hasActiveSubIdOnDds == internetContent.hasActiveSubIdOnDds && this.isDeviceLocked == internetContent.isDeviceLocked && this.isWifiScanEnabled == internetContent.isWifiScanEnabled && this.activeAutoSwitchNonDdsSubId == internetContent.activeAutoSwitchNonDdsSubId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.activeAutoSwitchNonDdsSubId) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isAirplaneModeEnabled) * 31, 31, this.hasEthernet), 31, this.shouldUpdateMobileNetwork), 31, this.activeNetworkIsCellular), 31, this.isCarrierNetworkActive), 31, this.isWifiEnabled), 31, this.hasActiveSubIdOnDds), 31, this.isDeviceLocked), 31, this.isWifiScanEnabled);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("InternetContent(isAirplaneModeEnabled=");
            sb.append(this.isAirplaneModeEnabled);
            sb.append(", hasEthernet=");
            sb.append(this.hasEthernet);
            sb.append(", shouldUpdateMobileNetwork=");
            sb.append(this.shouldUpdateMobileNetwork);
            sb.append(", activeNetworkIsCellular=");
            sb.append(this.activeNetworkIsCellular);
            sb.append(", isCarrierNetworkActive=");
            sb.append(this.isCarrierNetworkActive);
            sb.append(", isWifiEnabled=");
            sb.append(this.isWifiEnabled);
            sb.append(", hasActiveSubIdOnDds=");
            sb.append(this.hasActiveSubIdOnDds);
            sb.append(", isDeviceLocked=");
            sb.append(this.isDeviceLocked);
            sb.append(", isWifiScanEnabled=");
            sb.append(this.isWifiScanEnabled);
            sb.append(", activeAutoSwitchNonDdsSubId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.activeAutoSwitchNonDdsSubId, ")", sb);
        }

        public InternetContent(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, int i) {
            this.isAirplaneModeEnabled = z;
            this.hasEthernet = z2;
            this.shouldUpdateMobileNetwork = z3;
            this.activeNetworkIsCellular = z4;
            this.isCarrierNetworkActive = z5;
            this.isWifiEnabled = z6;
            this.hasActiveSubIdOnDds = z7;
            this.isDeviceLocked = z8;
            this.isWifiScanEnabled = z9;
            this.activeAutoSwitchNonDdsSubId = i;
        }

        public /* synthetic */ InternetContent(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? false : z2, (i2 & 4) != 0 ? false : z3, (i2 & 8) != 0 ? false : z4, (i2 & 16) != 0 ? false : z5, (i2 & 32) != 0 ? false : z6, (i2 & 64) != 0 ? false : z7, (i2 & 128) != 0 ? false : z8, (i2 & 256) != 0 ? false : z9, (i2 & 512) != 0 ? -1 : i);
        }
    }

    static {
        new Companion(null);
        DEBUG = Log.isLoggable("InternetDetailsContent", 3);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.dialog.InternetDetailsContentManager$internetDetailsCallback$1] */
    public InternetDetailsContentManager(InternetDetailsContentController internetDetailsContentController, boolean z, boolean z2, UiEventLogger uiEventLogger, Handler handler, Executor executor, KeyguardStateController keyguardStateController) {
        this.internetDetailsContentController = internetDetailsContentController;
        this.uiEventLogger = uiEventLogger;
        this.handler = handler;
        this.backgroundExecutor = executor;
        this.keyguard = keyguardStateController;
        internetDetailsContentController.getClass();
        SubscriptionManager.getDefaultDataSubscriptionId();
        this.title$delegate = SnapshotStateKt.mutableStateOf$default("");
        this.subTitle$delegate = SnapshotStateKt.mutableStateOf$default("");
        this.internetDetailsCallback = new InternetDetailsContentController.InternetDialogCallback() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentManager$internetDetailsCallback$1
            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void dismissDialog() {
                if (InternetDetailsContentManager.DEBUG) {
                    Log.d("InternetDetailsContent", "dismissDialog");
                }
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onAccessPointsChanged(List list, WifiEntry wifiEntry, boolean z3) {
                this.this$0.getClass();
                throw null;
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onCapabilitiesChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onCarrierNetworkChange() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onDataConnectionStateChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onDisplayInfoChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onLost() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onRefreshCarrierInfo() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onServiceStateChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onSignalStrengthsChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onSimStateChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onSubscriptionsChanged(int i) {
                InternetDetailsContentManager internetDetailsContentManager = this.this$0;
                internetDetailsContentManager.getClass();
                internetDetailsContentManager.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onUserMobileDataStateChanged() {
                this.this$0.updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(true);
            }

            @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
            public final void onWifiScan(boolean z3) {
                boolean z4 = InternetDetailsContentManager.DEBUG;
                this.this$0.setProgressBarVisible(z3);
            }
        };
    }

    public final int getWifiListMaxCount$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        throw null;
    }

    public final void hideWifiViews$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        setProgressBarVisible(false);
        throw null;
    }

    public final void setProgressBarVisible(boolean z) {
        if (this.isProgressBarVisible == z) {
            return;
        }
        this.isProgressBarVisible = z;
        throw null;
    }

    public final void updateContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final boolean z) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDetailsContentManager$updateContent$1
            @Override // java.lang.Runnable
            public final void run() {
                WifiManager wifiManager;
                InternetDetailsContentManager internetDetailsContentManager = this.this$0;
                MutableLiveData mutableLiveData = internetDetailsContentManager.internetContentData;
                boolean z2 = z;
                boolean z3 = false;
                InternetDetailsContentController internetDetailsContentController = internetDetailsContentManager.internetDetailsContentController;
                boolean zActiveNetworkIsCellular = z2 ? internetDetailsContentController.activeNetworkIsCellular() : false;
                boolean zIsCarrierNetworkActive = z2 ? internetDetailsContentController.isCarrierNetworkActive() : false;
                boolean zIsAirplaneModeEnabled = internetDetailsContentController.isAirplaneModeEnabled();
                boolean z4 = internetDetailsContentController.mHasEthernet;
                boolean zIsWifiEnabled = internetDetailsContentController.mWifiStateWorker.isWifiEnabled();
                boolean z5 = (internetDetailsContentController.isAirplaneModeEnabled() || internetDetailsContentController.mTelephonyManager == null) ? false : internetDetailsContentController.mHasActiveSubIdOnDds;
                boolean zIsDeviceLocked = internetDetailsContentController.isDeviceLocked();
                if (((LocationControllerImpl) internetDetailsContentController.mLocationController).isLocationEnabled$1() && (wifiManager = internetDetailsContentController.mWifiManager) != null && wifiManager.isScanAlwaysAvailable()) {
                    z3 = true;
                }
                mutableLiveData.postValue(new InternetDetailsContentManager.InternetContent(zIsAirplaneModeEnabled, z4, z2, zActiveNetworkIsCellular, zIsCarrierNetworkActive, zIsWifiEnabled, z5, zIsDeviceLocked, z3, internetDetailsContentController.getActiveAutoSwitchNonDdsSubId()));
            }
        });
    }

    public static /* synthetic */ void getAdapter$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getConnectedWifiEntry$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getHasMoreWifiEntries$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getInternetContentData$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getInternetDetailsCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getLifecycleOwner$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getWifiEntriesCount$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void isProgressBarVisible$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
