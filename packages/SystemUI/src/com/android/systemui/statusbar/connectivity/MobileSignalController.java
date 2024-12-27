package com.android.systemui.statusbar.connectivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MobileSignalController extends SignalController {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public MobileMappings.Config mConfig;
    public SignalIcon$MobileIconGroup mDefaultIcons;
    public final MobileStatusTracker.SubscriptionDefaults mDefaults;
    boolean mInflateSignalStrengths;
    public final AnonymousClass1 mMobileCallback;
    public final MobileMappingsProxy mMobileMappingsProxy;
    public final String[] mMobileStatusHistory;
    public int mMobileStatusHistoryIndex;
    final MobileStatusTracker mMobileStatusTracker;
    public final String mNetworkNameDefault;
    public final String mNetworkNameSeparator;
    public Map mNetworkToIconLookup;
    public final AnonymousClass2 mObserver;
    public final TelephonyManager mPhone;
    public final SubscriptionInfo mSubscriptionInfo;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.connectivity.MobileSignalController$1, reason: invalid class name */
    public final class AnonymousClass1 implements MobileStatusTracker.Callback {
        public String mLastStatus;

        public AnonymousClass1() {
        }

        public final void onMobileStatusChanged(boolean z, MobileStatusTracker.MobileStatus mobileStatus) {
            boolean z2 = SignalController.DEBUG;
            MobileSignalController mobileSignalController = MobileSignalController.this;
            if (z2) {
                String str = mobileSignalController.mTag;
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("onMobileStatusChanged= updateTelephony=", " mobileStatus=", z);
                m.append(mobileStatus.toString());
                Log.d(str, m.toString());
            }
            String mobileStatus2 = mobileStatus.toString();
            if (!mobileStatus2.equals(this.mLastStatus)) {
                this.mLastStatus = mobileStatus2;
                String str2 = MobileSignalController.SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + mobileStatus2;
                int i = mobileSignalController.mMobileStatusHistoryIndex;
                mobileSignalController.mMobileStatusHistory[i] = str2;
                mobileSignalController.mMobileStatusHistoryIndex = (i + 1) % 64;
            }
            SimpleDateFormat simpleDateFormat = MobileSignalController.SSDF;
            MobileState mobileState = (MobileState) mobileSignalController.mCurrentState;
            mobileState.getClass();
            mobileState.activityIn = mobileStatus.activityIn;
            mobileState.activityOut = mobileStatus.activityOut;
            mobileState.dataSim = mobileStatus.dataSim;
            mobileState.carrierNetworkChangeMode = mobileStatus.carrierNetworkChangeMode;
            mobileState.dataState = mobileStatus.dataState;
            mobileState.signalStrength = mobileStatus.signalStrength;
            mobileState.telephonyDisplayInfo = mobileStatus.telephonyDisplayInfo;
            mobileState.serviceState = mobileStatus.serviceState;
            if (z) {
                mobileSignalController.updateTelephony();
            } else {
                mobileSignalController.notifyListenersIfNecessary();
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class QsInfo {
        public final CharSequence description;
        public final IconState icon;
        public final int ratTypeIcon;

        public QsInfo(int i, IconState iconState, CharSequence charSequence) {
            this.ratTypeIcon = i;
            this.icon = iconState;
            this.description = charSequence;
        }

        public final String toString() {
            return "QsInfo: ratTypeIcon=" + this.ratTypeIcon + " icon=" + this.icon;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SbInfo {
        public final IconState icon;
        public final int ratTypeIcon;
        public final boolean showTriangle;

        public SbInfo(boolean z, int i, IconState iconState) {
            this.showTriangle = z;
            this.ratTypeIcon = i;
            this.icon = iconState;
        }

        public final String toString() {
            return "SbInfo: showTriangle=" + this.showTriangle + " ratTypeIcon=" + this.ratTypeIcon + " icon=" + this.icon;
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.connectivity.MobileSignalController$2] */
    public MobileSignalController(Context context, MobileMappings.Config config, boolean z, TelephonyManager telephonyManager, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl, MobileMappingsProxy mobileMappingsProxy, SubscriptionInfo subscriptionInfo, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, Looper looper, CarrierConfigTracker carrierConfigTracker, MobileStatusTrackerFactory mobileStatusTrackerFactory) {
        super("MobileSignalController(" + subscriptionInfo.getSubscriptionId() + ")", context, 0, callbackHandler, networkControllerImpl);
        this.mInflateSignalStrengths = false;
        this.mMobileStatusHistory = new String[64];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mConfig = config;
        this.mPhone = telephonyManager;
        this.mDefaults = subscriptionDefaults;
        this.mSubscriptionInfo = subscriptionInfo;
        this.mMobileMappingsProxy = mobileMappingsProxy;
        this.mNetworkNameSeparator = getTextIfExists(R.string.status_bar_network_name_separator).toString();
        String charSequence = getTextIfExists(android.R.string.permdesc_runInBackground).toString();
        this.mNetworkNameDefault = charSequence;
        MobileMappings.Config config2 = this.mConfig;
        MobileMappingsProxyImpl mobileMappingsProxyImpl = (MobileMappingsProxyImpl) mobileMappingsProxy;
        mobileMappingsProxyImpl.getClass();
        this.mNetworkToIconLookup = MobileMappings.mapIconSets(config2);
        MobileMappings.Config config3 = this.mConfig;
        mobileMappingsProxyImpl.getClass();
        this.mDefaultIcons = !config3.showAtLeast3G ? TelephonyIcons.G : TelephonyIcons.THREE_G;
        charSequence = subscriptionInfo.getCarrierName() != null ? subscriptionInfo.getCarrierName().toString() : charSequence;
        MobileState mobileState = (MobileState) this.mLastState;
        MobileState mobileState2 = (MobileState) this.mCurrentState;
        mobileState2.networkName = charSequence;
        mobileState.networkName = charSequence;
        mobileState2.networkNameData = charSequence;
        mobileState.networkNameData = charSequence;
        mobileState2.enabled = z;
        mobileState.enabled = z;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = this.mDefaultIcons;
        mobileState2.iconGroup = signalIcon$MobileIconGroup;
        mobileState.iconGroup = signalIcon$MobileIconGroup;
        this.mObserver = new ContentObserver(new Handler(looper)) { // from class: com.android.systemui.statusbar.connectivity.MobileSignalController.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                MobileSignalController mobileSignalController = MobileSignalController.this;
                SimpleDateFormat simpleDateFormat = MobileSignalController.SSDF;
                mobileSignalController.updateTelephony();
            }
        };
        this.mMobileStatusTracker = new MobileStatusTracker(mobileStatusTrackerFactory.phone, mobileStatusTrackerFactory.receiverLooper, mobileStatusTrackerFactory.info, mobileStatusTrackerFactory.defaults, anonymousClass1);
    }

    public final void checkDefaultData() {
        MobileState mobileState = (MobileState) this.mCurrentState;
        if (mobileState.iconGroup != TelephonyIcons.NOT_DEFAULT_DATA) {
            mobileState.defaultDataOff = false;
            return;
        }
        NetworkControllerImpl networkControllerImpl = this.mNetworkController;
        networkControllerImpl.mSubDefaults.getClass();
        mobileState.defaultDataOff = networkControllerImpl.getControllerWithSubId(SubscriptionManager.getActiveDataSubscriptionId()) != null ? !r4.mPhone.isDataConnectionAllowed() : false;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final ConnectivityState cleanState() {
        return new MobileState();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final void dump(PrintWriter printWriter) {
        String[] strArr;
        super.dump(printWriter);
        printWriter.println("  mSubscription=" + this.mSubscriptionInfo + ",");
        printWriter.println("  mInflateSignalStrengths=" + this.mInflateSignalStrengths + ",");
        printWriter.println("  isDataDisabled=" + (this.mPhone.isDataConnectionAllowed() ^ true) + ",");
        printWriter.println("  mNetworkToIconLookup=" + this.mNetworkToIconLookup + ",");
        StringBuilder sb = new StringBuilder("  mMobileStatusTracker.isListening=");
        sb.append(this.mMobileStatusTracker.mListening);
        printWriter.println(sb.toString());
        printWriter.println("  MobileStatusHistory");
        int i = 0;
        int i2 = 0;
        while (true) {
            strArr = this.mMobileStatusHistory;
            if (i >= 64) {
                break;
            }
            if (strArr[i] != null) {
                i2++;
            }
            i++;
        }
        for (int i3 = this.mMobileStatusHistoryIndex + 63; i3 >= (this.mMobileStatusHistoryIndex + 64) - i2; i3 += -1) {
            printWriter.println("  Previous MobileStatus(" + ((this.mMobileStatusHistoryIndex + 64) - i3) + "): " + strArr[i3 & 63]);
        }
        dumpTableData(printWriter);
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final int getCurrentIconId() {
        MobileState mobileState = (MobileState) this.mCurrentState;
        SignalIcon$IconGroup signalIcon$IconGroup = mobileState.iconGroup;
        if (signalIcon$IconGroup == TelephonyIcons.CARRIER_NETWORK_CHANGE) {
            int numSignalStrengthLevels = this.mInflateSignalStrengths ? CellSignalStrength.getNumSignalStrengthLevels() + 1 : CellSignalStrength.getNumSignalStrengthLevels();
            int i = SignalDrawable.ICON_RES;
            return (numSignalStrengthLevels << 8) | 196608;
        }
        if (!mobileState.connected) {
            if (mobileState.enabled) {
                return SignalDrawable.getState(0, this.mInflateSignalStrengths ? CellSignalStrength.getNumSignalStrengthLevels() + 1 : CellSignalStrength.getNumSignalStrengthLevels(), true);
            }
            return 0;
        }
        int i2 = mobileState.level;
        boolean z = this.mInflateSignalStrengths;
        if (z) {
            i2++;
        }
        return SignalDrawable.getState(i2, z ? CellSignalStrength.getNumSignalStrengthLevels() + 1 : CellSignalStrength.getNumSignalStrengthLevels(), (mobileState.userSetup && (signalIcon$IconGroup == TelephonyIcons.DATA_DISABLED || (signalIcon$IconGroup == TelephonyIcons.NOT_DEFAULT_DATA && mobileState.defaultDataOff))) || (mobileState.inetCondition == 0));
    }

    public final void handleBroadcast(Intent intent) {
        String action = intent.getAction();
        boolean equals = action.equals("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        ConnectivityState connectivityState = this.mCurrentState;
        if (!equals) {
            if (!action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                if (action.equals("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED")) {
                    ((MobileState) connectivityState).carrierId = intent.getIntExtra("android.telephony.extra.CARRIER_ID", -1);
                    return;
                }
                return;
            }
            this.mDefaults.getClass();
            int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
            if (SubscriptionManager.isValidSubscriptionId(activeDataSubscriptionId)) {
                ((MobileState) connectivityState).dataSim = activeDataSubscriptionId == this.mSubscriptionInfo.getSubscriptionId();
            } else {
                ((MobileState) connectivityState).dataSim = true;
            }
            notifyListenersIfNecessary();
            return;
        }
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
        String stringExtra2 = intent.getStringExtra("android.telephony.extra.DATA_SPN");
        boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
        String stringExtra3 = intent.getStringExtra("android.telephony.extra.PLMN");
        if (SignalController.CHATTY) {
            StringBuilder sb = new StringBuilder("updateNetworkName showSpn=");
            sb.append(booleanExtra);
            sb.append(" spn=");
            sb.append(stringExtra);
            sb.append(" dataSpn=");
            sb.append(stringExtra2);
            sb.append(" showPlmn=");
            sb.append(booleanExtra2);
            sb.append(" plmn=");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, stringExtra3, "CarrierLabel");
        }
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        if (booleanExtra2 && stringExtra3 != null) {
            sb2.append(stringExtra3);
            sb3.append(stringExtra3);
        }
        String str = this.mNetworkNameSeparator;
        if (booleanExtra && stringExtra != null) {
            if (sb2.length() != 0) {
                sb2.append(str);
            }
            sb2.append(stringExtra);
        }
        int length = sb2.length();
        String str2 = this.mNetworkNameDefault;
        if (length != 0) {
            ((MobileState) connectivityState).networkName = sb2.toString();
        } else {
            ((MobileState) connectivityState).networkName = str2;
        }
        if (booleanExtra && stringExtra2 != null) {
            if (sb3.length() != 0) {
                sb3.append(str);
            }
            sb3.append(stringExtra2);
        }
        if (sb3.length() != 0) {
            ((MobileState) connectivityState).networkNameData = sb3.toString();
        } else {
            ((MobileState) connectivityState).networkNameData = str2;
        }
        notifyListenersIfNecessary();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00c9  */
    @Override // com.android.systemui.statusbar.connectivity.SignalController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback r18) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.MobileSignalController.notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback):void");
    }

    public final void registerListener() {
        this.mMobileStatusTracker.setListening(true);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA);
        AnonymousClass2 anonymousClass2 = this.mObserver;
        contentResolver.registerContentObserver(uriFor, true, anonymousClass2);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA + this.mSubscriptionInfo.getSubscriptionId()), true, anonymousClass2);
    }

    public void setActivity(int i) {
        ConnectivityState connectivityState = this.mCurrentState;
        ((MobileState) connectivityState).activityIn = i == 3 || i == 1;
        ((MobileState) connectivityState).activityOut = i == 3 || i == 2;
        notifyListenersIfNecessary();
    }

    public final void updateTelephony() {
        SignalStrength signalStrength;
        TelephonyDisplayInfo telephonyDisplayInfo;
        int level;
        boolean z = SignalController.DEBUG;
        ConnectivityState connectivityState = this.mCurrentState;
        if (z) {
            StringBuilder sb = new StringBuilder("updateTelephonySignalStrength: hasService=");
            MobileState mobileState = (MobileState) connectivityState;
            sb.append(Utils.isInService(mobileState.serviceState));
            sb.append(" ss=");
            sb.append(mobileState.signalStrength);
            sb.append(" displayInfo=");
            sb.append(mobileState.telephonyDisplayInfo);
            Log.d(this.mTag, sb.toString());
        }
        checkDefaultData();
        MobileState mobileState2 = (MobileState) connectivityState;
        boolean isInService = Utils.isInService(mobileState2.serviceState);
        mobileState2.connected = isInService;
        boolean z2 = false;
        if (isInService) {
            SignalStrength signalStrength2 = mobileState2.signalStrength;
            if (signalStrength2 != null) {
                if (signalStrength2.isGsm() || !this.mConfig.alwaysShowCdmaRssi) {
                    level = signalStrength2.getLevel();
                } else {
                    List cellSignalStrengths = signalStrength2.getCellSignalStrengths(CellSignalStrengthCdma.class);
                    if (!cellSignalStrengths.isEmpty()) {
                        level = ((CellSignalStrengthCdma) cellSignalStrengths.get(0)).getLevel();
                    }
                }
                mobileState2.level = level;
            }
            level = 0;
            mobileState2.level = level;
        }
        mobileState2.carrierId = this.mPhone.getSimCarrierId();
        TelephonyDisplayInfo telephonyDisplayInfo2 = mobileState2.telephonyDisplayInfo;
        ((MobileMappingsProxyImpl) this.mMobileMappingsProxy).getClass();
        String num = telephonyDisplayInfo2.getOverrideNetworkType() == 0 ? Integer.toString(telephonyDisplayInfo2.getNetworkType()) : MobileMappings.toDisplayIconKey(telephonyDisplayInfo2.getOverrideNetworkType());
        if (((HashMap) this.mNetworkToIconLookup).get(num) != null) {
            mobileState2.iconGroup = (SignalIcon$IconGroup) ((HashMap) this.mNetworkToIconLookup).get(num);
        } else {
            mobileState2.iconGroup = this.mDefaultIcons;
        }
        mobileState2.dataConnected = mobileState2.connected && mobileState2.dataState == 2;
        mobileState2.roaming = !((MobileState) connectivityState).carrierNetworkChangeMode && ((signalStrength = mobileState2.signalStrength) == null || signalStrength.isGsm() ? !((telephonyDisplayInfo = mobileState2.telephonyDisplayInfo) == null || !telephonyDisplayInfo.isRoaming()) : this.mPhone.getCdmaEnhancedRoamingIndicatorDisplayNumber() != 1);
        if (((MobileState) connectivityState).carrierNetworkChangeMode) {
            mobileState2.iconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        } else if ((!this.mPhone.isDataConnectionAllowed()) && !this.mConfig.alwaysShowDataRatIcon) {
            int subscriptionId = this.mSubscriptionInfo.getSubscriptionId();
            this.mDefaults.getClass();
            if (subscriptionId != SubscriptionManager.getDefaultDataSubscriptionId()) {
                mobileState2.iconGroup = TelephonyIcons.NOT_DEFAULT_DATA;
            } else {
                mobileState2.iconGroup = TelephonyIcons.DATA_DISABLED;
            }
        }
        ServiceState serviceState = mobileState2.serviceState;
        if ((serviceState != null && serviceState.isEmergencyOnly()) != mobileState2.isEmergency) {
            ServiceState serviceState2 = mobileState2.serviceState;
            if (serviceState2 != null && serviceState2.isEmergencyOnly()) {
                z2 = true;
            }
            mobileState2.isEmergency = z2;
            this.mNetworkController.recalculateEmergency();
        }
        String str = mobileState2.networkName;
        String str2 = this.mNetworkNameDefault;
        if (str.equals(str2) && !TextUtils.isEmpty(mobileState2.getOperatorAlphaShort())) {
            mobileState2.networkName = mobileState2.getOperatorAlphaShort();
        }
        if (mobileState2.networkNameData.equals(str2) && mobileState2.dataSim && !TextUtils.isEmpty(mobileState2.getOperatorAlphaShort())) {
            mobileState2.networkNameData = mobileState2.getOperatorAlphaShort();
        }
        notifyListenersIfNecessary();
    }
}
