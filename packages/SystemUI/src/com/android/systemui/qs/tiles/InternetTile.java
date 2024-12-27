package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tiles.dialog.InternetDialogManager;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.MobileSignalController;
import com.android.systemui.statusbar.connectivity.MobileState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import java.io.PrintWriter;

public final class InternetTile extends QSTileImpl {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final AccessPointController mAccessPointController;
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final Handler mHandler;
    public final InternetDialogManager mInternetDialogManager;
    public int mLastTileState;
    public final InternetSignalCallback mSignalCallback;

    public final class CellularCallbackInfo {
        public boolean mAirplaneModeEnabled;
        public CharSequence mDataContentDescription;
        public CharSequence mDataSubscriptionName;
        public int mMobileSignalIconId;
        public boolean mMultipleSubs;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoSim;
        public boolean mNoValidatedNetwork;
        public int mQsTypeIcon;
        public boolean mRoaming;

        private CellularCallbackInfo() {
        }

        public final void copyTo(CellularCallbackInfo cellularCallbackInfo) {
            cellularCallbackInfo.mAirplaneModeEnabled = this.mAirplaneModeEnabled;
            cellularCallbackInfo.mDataSubscriptionName = this.mDataSubscriptionName;
            cellularCallbackInfo.mDataContentDescription = this.mDataContentDescription;
            cellularCallbackInfo.mMobileSignalIconId = this.mMobileSignalIconId;
            cellularCallbackInfo.mQsTypeIcon = this.mQsTypeIcon;
            cellularCallbackInfo.mNoSim = this.mNoSim;
            cellularCallbackInfo.mRoaming = this.mRoaming;
            cellularCallbackInfo.mMultipleSubs = this.mMultipleSubs;
            cellularCallbackInfo.mNoDefaultNetwork = this.mNoDefaultNetwork;
            cellularCallbackInfo.mNoValidatedNetwork = this.mNoValidatedNetwork;
            cellularCallbackInfo.mNoNetworksAvailable = this.mNoNetworksAvailable;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CellularCallbackInfo[mAirplaneModeEnabled=");
            sb.append(this.mAirplaneModeEnabled);
            sb.append(",mDataSubscriptionName=");
            sb.append(this.mDataSubscriptionName);
            sb.append(",mDataContentDescription=");
            sb.append(this.mDataContentDescription);
            sb.append(",mMobileSignalIconId=");
            sb.append(this.mMobileSignalIconId);
            sb.append(",mQsTypeIcon=");
            sb.append(this.mQsTypeIcon);
            sb.append(",mNoSim=");
            sb.append(this.mNoSim);
            sb.append(",mRoaming=");
            sb.append(this.mRoaming);
            sb.append(",mMultipleSubs=");
            sb.append(this.mMultipleSubs);
            sb.append(",mNoDefaultNetwork=");
            sb.append(this.mNoDefaultNetwork);
            sb.append(",mNoValidatedNetwork=");
            sb.append(this.mNoValidatedNetwork);
            sb.append(",mNoNetworksAvailable=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mNoNetworksAvailable, ']');
        }

        public /* synthetic */ CellularCallbackInfo(int i) {
            this();
        }
    }

    public final class EthernetCallbackInfo {
        public boolean mConnected;
        public String mEthernetContentDescription;
        public int mEthernetSignalIconId;

        private EthernetCallbackInfo() {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("EthernetCallbackInfo[mConnected=");
            sb.append(this.mConnected);
            sb.append(",mEthernetSignalIconId=");
            sb.append(this.mEthernetSignalIconId);
            sb.append(",mEthernetContentDescription=");
            return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.mEthernetContentDescription, ']');
        }

        public /* synthetic */ EthernetCallbackInfo(int i) {
            this();
        }
    }

    public final class InternetSignalCallback implements SignalCallback {
        public final CellularCallbackInfo mCellularInfo;
        public final EthernetCallbackInfo mEthernetInfo;
        public final WifiCallbackInfo mWifiInfo;

        public InternetSignalCallback() {
            int i = 0;
            this.mWifiInfo = new WifiCallbackInfo(i);
            this.mCellularInfo = new CellularCallbackInfo(i);
            this.mEthernetInfo = new EthernetCallbackInfo(i);
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
            WifiCallbackInfo wifiCallbackInfo;
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("setConnectivityStatus: noDefaultNetwork = ", ",noValidatedNetwork = ", ",noNetworksAvailable = ", z, z2), z3, internetTile.TAG);
            }
            synchronized (this.mCellularInfo) {
                CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
                cellularCallbackInfo.mNoDefaultNetwork = z;
                cellularCallbackInfo.mNoValidatedNetwork = z2;
                cellularCallbackInfo.mNoNetworksAvailable = z3;
            }
            synchronized (this.mWifiInfo) {
                wifiCallbackInfo = this.mWifiInfo;
                wifiCallbackInfo.mNoDefaultNetwork = z;
                wifiCallbackInfo.mNoValidatedNetwork = z2;
                wifiCallbackInfo.mNoNetworksAvailable = z3;
            }
            if (z) {
                InternetTile.this.refreshState(wifiCallbackInfo);
            }
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setEthernetIndicators(IconState iconState) {
            EthernetCallbackInfo ethernetCallbackInfo;
            boolean z;
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                Log.d(internetTile.TAG, "setEthernetIndicators: icon = ".concat(iconState == null ? "" : iconState.toString()));
            }
            synchronized (this.mEthernetInfo) {
                ethernetCallbackInfo = this.mEthernetInfo;
                z = iconState.visible;
                ethernetCallbackInfo.mConnected = z;
                ethernetCallbackInfo.mEthernetSignalIconId = iconState.icon;
                ethernetCallbackInfo.mEthernetContentDescription = iconState.contentDescription;
            }
            if (z) {
                InternetTile.this.refreshState(ethernetCallbackInfo);
            }
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setIsAirplaneMode(IconState iconState) {
            WifiCallbackInfo wifiCallbackInfo;
            boolean z;
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                Log.d(internetTile.TAG, "setIsAirplaneMode: icon = ".concat(iconState == null ? "" : iconState.toString()));
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            if (cellularCallbackInfo.mAirplaneModeEnabled == iconState.visible) {
                return;
            }
            synchronized (cellularCallbackInfo) {
                this.mCellularInfo.mAirplaneModeEnabled = iconState.visible;
            }
            synchronized (this.mWifiInfo) {
                wifiCallbackInfo = this.mWifiInfo;
                z = iconState.visible;
                wifiCallbackInfo.mAirplaneModeEnabled = z;
            }
            InternetTile internetTile2 = InternetTile.this;
            if (internetTile2.mSignalCallback.mEthernetInfo.mConnected) {
                return;
            }
            if (z) {
                internetTile2.refreshState(wifiCallbackInfo);
            } else if (!wifiCallbackInfo.mEnabled || wifiCallbackInfo.mWifiSignalIconId <= 0 || wifiCallbackInfo.mSsid == null) {
                internetTile2.refreshState(this.mCellularInfo);
            } else {
                internetTile2.refreshState(wifiCallbackInfo);
            }
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                Log.d(internetTile.TAG, "setMobileDataIndicators: " + mobileDataIndicators);
            }
            if (mobileDataIndicators.qsIcon == null) {
                return;
            }
            synchronized (this.mCellularInfo) {
                try {
                    CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
                    String str = mobileDataIndicators.qsDescription;
                    if (str == null) {
                        NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) InternetTile.this.mController;
                        networkControllerImpl.mSubDefaults.getClass();
                        MobileSignalController controllerWithSubId = networkControllerImpl.getControllerWithSubId(SubscriptionManager.getActiveDataSubscriptionId());
                        str = controllerWithSubId != null ? ((MobileState) controllerWithSubId.mCurrentState).networkNameData : "";
                    }
                    cellularCallbackInfo.mDataSubscriptionName = str;
                    CellularCallbackInfo cellularCallbackInfo2 = this.mCellularInfo;
                    cellularCallbackInfo2.mDataContentDescription = mobileDataIndicators.qsDescription != null ? mobileDataIndicators.typeContentDescriptionHtml : null;
                    cellularCallbackInfo2.mMobileSignalIconId = mobileDataIndicators.qsIcon.icon;
                    cellularCallbackInfo2.mQsTypeIcon = mobileDataIndicators.qsType;
                    cellularCallbackInfo2.mRoaming = mobileDataIndicators.roaming;
                    boolean z = true;
                    if (((NetworkControllerImpl) InternetTile.this.mController).getNumberSubscriptions() <= 1) {
                        z = false;
                    }
                    cellularCallbackInfo2.mMultipleSubs = z;
                } catch (Throwable th) {
                    throw th;
                }
            }
            InternetTile.this.refreshState(this.mCellularInfo);
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setNoSims(boolean z, boolean z2) {
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("setNoSims: show = ", ",simDetected = ", internetTile.TAG, z, z2);
            }
            synchronized (this.mCellularInfo) {
                try {
                    CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
                    cellularCallbackInfo.mNoSim = z;
                    if (z) {
                        cellularCallbackInfo.mMobileSignalIconId = 0;
                        cellularCallbackInfo.mQsTypeIcon = 0;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            WifiCallbackInfo wifiCallbackInfo;
            IconState iconState;
            InternetTile internetTile = InternetTile.this;
            Intent intent = InternetTile.WIFI_SETTINGS;
            if (internetTile.DEBUG) {
                Log.d(internetTile.TAG, "setWifiIndicators: " + wifiIndicators);
            }
            synchronized (this.mWifiInfo) {
                try {
                    wifiCallbackInfo = this.mWifiInfo;
                    wifiCallbackInfo.mEnabled = wifiIndicators.enabled;
                    wifiCallbackInfo.mSsid = wifiIndicators.description;
                    wifiCallbackInfo.mIsTransient = wifiIndicators.isTransient;
                    iconState = wifiIndicators.qsIcon;
                    if (iconState != null) {
                        wifiCallbackInfo.mConnected = iconState.visible;
                        wifiCallbackInfo.mWifiSignalIconId = iconState.icon;
                        wifiCallbackInfo.mWifiSignalContentDescription = iconState.contentDescription;
                    } else {
                        wifiCallbackInfo.mConnected = false;
                        wifiCallbackInfo.mWifiSignalIconId = 0;
                        wifiCallbackInfo.mWifiSignalContentDescription = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (iconState != null) {
                InternetTile.this.refreshState(wifiCallbackInfo);
            }
        }

        public final String toString() {
            return "InternetSignalCallback[mWifiInfo=" + this.mWifiInfo + ",mCellularInfo=" + this.mCellularInfo + ",mEthernetInfo=" + this.mEthernetInfo + ']';
        }
    }

    public final class SignalIcon extends QSTile.Icon {
        public final int mState;

        public SignalIcon(int i) {
            this.mState = i;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getDrawable(Context context) {
            SignalDrawable signalDrawable = new SignalDrawable(context);
            signalDrawable.setLevel(this.mState);
            return signalDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final String toString() {
            return String.format("SignalIcon[mState=0x%08x]", Integer.valueOf(this.mState));
        }
    }

    public final class WifiCallbackInfo {
        public boolean mAirplaneModeEnabled;
        public boolean mConnected;
        public boolean mEnabled;
        public boolean mIsTransient;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoValidatedNetwork;
        public String mSsid;
        public String mWifiSignalContentDescription;
        public int mWifiSignalIconId;

        private WifiCallbackInfo() {
        }

        public final void copyTo(WifiCallbackInfo wifiCallbackInfo) {
            wifiCallbackInfo.mAirplaneModeEnabled = this.mAirplaneModeEnabled;
            wifiCallbackInfo.mEnabled = this.mEnabled;
            wifiCallbackInfo.mConnected = this.mConnected;
            wifiCallbackInfo.mWifiSignalIconId = this.mWifiSignalIconId;
            wifiCallbackInfo.mSsid = this.mSsid;
            wifiCallbackInfo.mWifiSignalContentDescription = this.mWifiSignalContentDescription;
            wifiCallbackInfo.mIsTransient = this.mIsTransient;
            wifiCallbackInfo.mNoDefaultNetwork = this.mNoDefaultNetwork;
            wifiCallbackInfo.mNoValidatedNetwork = this.mNoValidatedNetwork;
            wifiCallbackInfo.mNoNetworksAvailable = this.mNoNetworksAvailable;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("WifiCallbackInfo[mAirplaneModeEnabled=");
            sb.append(this.mAirplaneModeEnabled);
            sb.append(",mEnabled=");
            sb.append(this.mEnabled);
            sb.append(",mConnected=");
            sb.append(this.mConnected);
            sb.append(",mWifiSignalIconId=");
            sb.append(this.mWifiSignalIconId);
            sb.append(",mSsid=");
            sb.append(this.mSsid);
            sb.append(",mWifiSignalContentDescription=");
            sb.append(this.mWifiSignalContentDescription);
            sb.append(",mIsTransient=");
            sb.append(this.mIsTransient);
            sb.append(",mNoDefaultNetwork=");
            sb.append(this.mNoDefaultNetwork);
            sb.append(",mNoValidatedNetwork=");
            sb.append(this.mNoValidatedNetwork);
            sb.append(",mNoNetworksAvailable=");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mNoNetworksAvailable, ']');
        }

        public /* synthetic */ WifiCallbackInfo(int i) {
            this();
        }
    }

    public InternetTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, InternetDialogManager internetDialogManager) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLastTileState = -1;
        InternetSignalCallback internetSignalCallback = new InternetSignalCallback();
        this.mSignalCallback = internetSignalCallback;
        this.mInternetDialogManager = internetDialogManager;
        this.mHandler = handler;
        this.mController = networkController;
        this.mAccessPointController = accessPointController;
        this.mDataController = ((NetworkControllerImpl) networkController).mDataUsageController;
        networkController.observe(this.mLifecycle, internetSignalCallback);
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("InternetTile:");
        printWriter.print("    ");
        printWriter.println(((QSTile.BooleanState) this.mState).toString());
        printWriter.print("    ");
        printWriter.println("mLastTileState=" + this.mLastTileState);
        printWriter.print("    ");
        printWriter.println("mSignalCallback=" + this.mSignalCallback.toString());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 126;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_internet_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.InternetTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                InternetTile internetTile = InternetTile.this;
                Expandable expandable2 = expandable;
                AccessPointControllerImpl accessPointControllerImpl = (AccessPointControllerImpl) internetTile.mAccessPointController;
                internetTile.mInternetDialogManager.create(accessPointControllerImpl.canConfigMobileData(), accessPointControllerImpl.canConfigWifi(), expandable2);
            }
        });
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleUpdateCellularState(com.android.systemui.plugins.qs.QSTile.BooleanState r10, com.android.systemui.qs.tiles.InternetTile.CellularCallbackInfo r11) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.InternetTile.handleUpdateCellularState(com.android.systemui.plugins.qs.QSTile$BooleanState, com.android.systemui.qs.tiles.InternetTile$CellularCallbackInfo):void");
    }

    public final void handleUpdateEthernetState(QSTile.BooleanState booleanState, EthernetCallbackInfo ethernetCallbackInfo) {
        String str = this.TAG;
        boolean z = this.DEBUG;
        if (z) {
            Log.d(str, "handleUpdateEthernetState: EthernetCallbackInfo = " + ethernetCallbackInfo.toString());
        }
        if (ethernetCallbackInfo.mConnected) {
            booleanState.label = this.mContext.getResources().getString(R.string.quick_settings_internet_label);
            booleanState.state = 2;
            booleanState.icon = QSTileImpl.ResourceIcon.get(ethernetCallbackInfo.mEthernetSignalIconId);
            booleanState.secondaryLabel = ethernetCallbackInfo.mEthernetContentDescription;
            if (z) {
                Log.d(str, "handleUpdateEthernetState: BooleanState = " + booleanState.toString());
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        this.mQSLogger.logInternetTileUpdate(this.mLastTileState, this.mTileSpec, obj == null ? "null" : obj.toString());
        int i = 0;
        if (obj instanceof CellularCallbackInfo) {
            this.mLastTileState = 0;
            CellularCallbackInfo cellularCallbackInfo = (CellularCallbackInfo) obj;
            CellularCallbackInfo cellularCallbackInfo2 = new CellularCallbackInfo(i);
            synchronized (cellularCallbackInfo) {
                cellularCallbackInfo.copyTo(cellularCallbackInfo2);
            }
            handleUpdateCellularState(booleanState, cellularCallbackInfo2);
            return;
        }
        if (obj instanceof WifiCallbackInfo) {
            this.mLastTileState = 1;
            WifiCallbackInfo wifiCallbackInfo = (WifiCallbackInfo) obj;
            WifiCallbackInfo wifiCallbackInfo2 = new WifiCallbackInfo(i);
            synchronized (wifiCallbackInfo) {
                wifiCallbackInfo.copyTo(wifiCallbackInfo2);
            }
            handleUpdateWifiState(booleanState, wifiCallbackInfo2);
            return;
        }
        if (obj instanceof EthernetCallbackInfo) {
            this.mLastTileState = 2;
            EthernetCallbackInfo ethernetCallbackInfo = (EthernetCallbackInfo) obj;
            EthernetCallbackInfo ethernetCallbackInfo2 = new EthernetCallbackInfo(i);
            synchronized (ethernetCallbackInfo) {
                ethernetCallbackInfo2.mConnected = ethernetCallbackInfo.mConnected;
                ethernetCallbackInfo2.mEthernetSignalIconId = ethernetCallbackInfo.mEthernetSignalIconId;
                ethernetCallbackInfo2.mEthernetContentDescription = ethernetCallbackInfo.mEthernetContentDescription;
            }
            handleUpdateEthernetState(booleanState, ethernetCallbackInfo2);
            return;
        }
        int i2 = this.mLastTileState;
        if (i2 == 0) {
            CellularCallbackInfo cellularCallbackInfo3 = new CellularCallbackInfo(i);
            synchronized (this.mSignalCallback.mCellularInfo) {
                this.mSignalCallback.mCellularInfo.copyTo(cellularCallbackInfo3);
            }
            handleUpdateCellularState(booleanState, cellularCallbackInfo3);
            return;
        }
        if (i2 == 1) {
            WifiCallbackInfo wifiCallbackInfo3 = new WifiCallbackInfo(i);
            synchronized (this.mSignalCallback.mWifiInfo) {
                this.mSignalCallback.mWifiInfo.copyTo(wifiCallbackInfo3);
            }
            handleUpdateWifiState(booleanState, wifiCallbackInfo3);
            return;
        }
        if (i2 == 2) {
            EthernetCallbackInfo ethernetCallbackInfo3 = new EthernetCallbackInfo(i);
            synchronized (this.mSignalCallback.mEthernetInfo) {
                EthernetCallbackInfo ethernetCallbackInfo4 = this.mSignalCallback.mEthernetInfo;
                ethernetCallbackInfo3.mConnected = ethernetCallbackInfo4.mConnected;
                ethernetCallbackInfo3.mEthernetSignalIconId = ethernetCallbackInfo4.mEthernetSignalIconId;
                ethernetCallbackInfo3.mEthernetContentDescription = ethernetCallbackInfo4.mEthernetContentDescription;
            }
            handleUpdateEthernetState(booleanState, ethernetCallbackInfo3);
        }
    }

    public final void handleUpdateWifiState(QSTile.BooleanState booleanState, WifiCallbackInfo wifiCallbackInfo) {
        String str = this.TAG;
        boolean z = this.DEBUG;
        if (z) {
            Log.d(str, "handleUpdateWifiState: WifiCallbackInfo = " + wifiCallbackInfo.toString());
        }
        boolean z2 = false;
        boolean z3 = wifiCallbackInfo.mEnabled && wifiCallbackInfo.mWifiSignalIconId > 0 && wifiCallbackInfo.mSsid != null;
        if (wifiCallbackInfo.mWifiSignalIconId > 0 && wifiCallbackInfo.mSsid == null) {
            z2 = true;
        }
        boolean z4 = wifiCallbackInfo.mIsTransient;
        String removeDoubleQuotes = removeDoubleQuotes(wifiCallbackInfo.mSsid);
        if (z4) {
            removeDoubleQuotes = this.mContext.getString(R.string.quick_settings_wifi_secondary_label_transient);
        }
        booleanState.secondaryLabel = removeDoubleQuotes;
        booleanState.state = 2;
        booleanState.dualTarget = true;
        booleanState.value = wifiCallbackInfo.mEnabled;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        Resources resources = this.mContext.getResources();
        booleanState.label = resources.getString(R.string.quick_settings_internet_label);
        if (wifiCallbackInfo.mAirplaneModeEnabled) {
            if (!booleanState.value) {
                booleanState.state = 1;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_no_internet_unavailable);
                booleanState.secondaryLabel = resources.getString(R.string.status_bar_airplane);
            } else if (z3) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
            } else {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_no_internet_unavailable);
                if (wifiCallbackInfo.mNoNetworksAvailable) {
                    booleanState.secondaryLabel = resources.getString(R.string.quick_settings_networks_unavailable);
                } else {
                    booleanState.secondaryLabel = resources.getString(R.string.quick_settings_networks_available);
                }
            }
        } else if (wifiCallbackInfo.mNoDefaultNetwork) {
            if (wifiCallbackInfo.mNoNetworksAvailable || !wifiCallbackInfo.mEnabled) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_no_internet_unavailable);
                booleanState.secondaryLabel = resources.getString(R.string.quick_settings_networks_unavailable);
            } else {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_no_internet_available);
                booleanState.secondaryLabel = resources.getString(R.string.quick_settings_networks_available);
            }
        } else if (wifiCallbackInfo.mIsTransient) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(android.R.drawable.ime_qwerty);
        } else if (!booleanState.value) {
            booleanState.state = 1;
            booleanState.icon = QSTileImpl.ResourceIcon.get(android.R.drawable.jog_tab_bar_right_sound_off);
        } else if (z3) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
        } else if (z2) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(android.R.drawable.jog_tab_bar_right_sound_off);
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(android.R.drawable.jog_tab_bar_right_sound_off);
        }
        stringBuffer.append(this.mContext.getString(R.string.quick_settings_internet_label));
        stringBuffer.append(",");
        if (booleanState.value && z3) {
            stringBuffer2.append(wifiCallbackInfo.mWifiSignalContentDescription);
            stringBuffer.append(removeDoubleQuotes(wifiCallbackInfo.mSsid));
        } else if (!TextUtils.isEmpty(booleanState.secondaryLabel)) {
            stringBuffer.append(",");
            stringBuffer.append(booleanState.secondaryLabel);
        }
        booleanState.stateDescription = stringBuffer2.toString();
        booleanState.contentDescription = stringBuffer.toString();
        booleanState.dualLabelContentDescription = resources.getString(R.string.accessibility_quick_settings_open_settings, getTileLabel());
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (z) {
            Log.d(str, "handleUpdateWifiState: BooleanState = " + booleanState.toString());
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || (((NetworkControllerImpl) this.mController).mHasMobileDataFeature && this.mHost.getUserContext().getUserId() == 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.forceExpandIcon = true;
        return booleanState;
    }
}
