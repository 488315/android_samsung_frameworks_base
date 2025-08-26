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
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
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
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class MobileSignalController extends SignalController {
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

    /* renamed from: com.android.systemui.statusbar.connectivity.MobileSignalController$1, reason: invalid class name */
    public class AnonymousClass1 implements MobileStatusTracker.Callback {
        public String mLastStatus;

        public AnonymousClass1() {
        }

        public final void onMobileStatusChanged(boolean z, MobileStatusTracker.MobileStatus mobileStatus) {
            boolean z2 = SignalController.DEBUG;
            MobileSignalController mobileSignalController = MobileSignalController.this;
            if (z2) {
                String str = mobileSignalController.mTag;
                StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("onMobileStatusChanged= updateTelephony=", " mobileStatus=", z);
                sbM.append(mobileStatus.toString());
                Log.d(str, sbM.toString());
            }
            String string = mobileStatus.toString();
            if (!string.equals(this.mLastStatus)) {
                this.mLastStatus = string;
                String str2 = MobileSignalController.SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + string;
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

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.connectivity.MobileSignalController$2] */
    public MobileSignalController(Context context, MobileMappings.Config config, boolean z, TelephonyManager telephonyManager, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl, MobileMappingsProxy mobileMappingsProxy, SubscriptionInfo subscriptionInfo, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, Looper looper, CarrierConfigTracker carrierConfigTracker, MobileStatusTrackerFactory mobileStatusTrackerFactory) {
        super("MobileSignalController(" + subscriptionInfo.getSubscriptionId() + ")", context, 0, callbackHandler, networkControllerImpl);
        this.mInflateSignalStrengths = false;
        this.mMobileStatusHistory = new String[64];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mMobileCallback = anonymousClass1;
        this.mConfig = config;
        this.mPhone = telephonyManager;
        this.mDefaults = subscriptionDefaults;
        this.mSubscriptionInfo = subscriptionInfo;
        this.mMobileMappingsProxy = mobileMappingsProxy;
        this.mNetworkNameSeparator = getTextIfExists(R.string.status_bar_network_name_separator).toString();
        String string = getTextIfExists(android.R.string.permlab_accessLastKnownCellId).toString();
        this.mNetworkNameDefault = string;
        MobileMappings.Config config2 = this.mConfig;
        MobileMappingsProxyImpl mobileMappingsProxyImpl = (MobileMappingsProxyImpl) mobileMappingsProxy;
        mobileMappingsProxyImpl.getClass();
        this.mNetworkToIconLookup = MobileMappings.mapIconSets(config2);
        this.mDefaultIcons = mobileMappingsProxyImpl.getDefaultIcons(this.mConfig);
        string = subscriptionInfo.getCarrierName() != null ? subscriptionInfo.getCarrierName().toString() : string;
        MobileState mobileState = (MobileState) this.mLastState;
        MobileState mobileState2 = (MobileState) this.mCurrentState;
        mobileState2.networkName = string;
        mobileState.networkName = string;
        mobileState2.networkNameData = string;
        mobileState.networkNameData = string;
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
            int i = SignalDrawable.$r8$clinit;
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
        boolean zEquals = action.equals("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        ConnectivityState connectivityState = this.mCurrentState;
        if (!zEquals) {
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
    /* JADX WARN: Removed duplicated region for block: B:101:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0158  */
    @Override // com.android.systemui.statusbar.connectivity.SignalController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyListeners(SignalCallback signalCallback) {
        int overrideFor;
        int i;
        String str;
        QsInfo qsInfo;
        boolean z;
        boolean z2;
        SignalIcon$IconGroup signalIcon$IconGroup;
        int subscriptionId = this.mSubscriptionInfo.getSubscriptionId();
        WifiState wifiState = (WifiState) this.mNetworkController.mWifiSignalController.mCurrentState;
        if (wifiState.isDefault && wifiState.isCarrierMerged && wifiState.subId == subscriptionId) {
            return;
        }
        ConnectivityState connectivityState = this.mCurrentState;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) connectivityState.iconGroup;
        String string = getTextIfExists(getContentDescription()).toString();
        CharSequence textIfExists = getTextIfExists(signalIcon$MobileIconGroup.dataContentDescription);
        String string2 = Html.fromHtml(textIfExists.toString(), 0).toString();
        MobileState mobileState = (MobileState) connectivityState;
        if (mobileState.inetCondition == 0) {
            string2 = this.mContext.getString(R.string.data_connection_no_internet);
        }
        String str2 = string2;
        Context context = this.mContext;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) mobileState.iconGroup;
        int i2 = mobileState.carrierId;
        NetworkTypeResIdCache networkTypeResIdCache = mobileState.networkTypeResIdCache;
        Integer num = networkTypeResIdCache.lastCarrierId;
        boolean z3 = true;
        if (num == null || num.intValue() != i2 || !Intrinsics.areEqual(networkTypeResIdCache.lastIconGroup, signalIcon$MobileIconGroup2)) {
            networkTypeResIdCache.lastCarrierId = Integer.valueOf(i2);
            networkTypeResIdCache.lastIconGroup = signalIcon$MobileIconGroup2;
            String str3 = signalIcon$MobileIconGroup2.name;
            MobileIconCarrierIdOverridesImpl mobileIconCarrierIdOverridesImpl = (MobileIconCarrierIdOverridesImpl) networkTypeResIdCache.overrides;
            mobileIconCarrierIdOverridesImpl.getClass();
            Map map = MobileIconCarrierIdOverridesImpl.MAPPING;
            MobileIconCarrierIdOverridesImpl.Companion.getClass();
            if (map.containsKey(Integer.valueOf(i2))) {
                str3.getClass();
                overrideFor = mobileIconCarrierIdOverridesImpl.getOverrideFor(i2, context.getResources(), str3);
            } else {
                overrideFor = 0;
            }
            if (overrideFor > 0) {
                networkTypeResIdCache.cachedResId = overrideFor;
                networkTypeResIdCache.isOverridden = true;
            } else {
                networkTypeResIdCache.cachedResId = signalIcon$MobileIconGroup2.dataType;
                networkTypeResIdCache.isOverridden = false;
            }
        }
        int i3 = networkTypeResIdCache.cachedResId;
        IconState iconState = null;
        if (!mobileState.dataSim) {
            i = 0;
            str = null;
        } else {
            if (!mobileState.isDefault) {
                qsInfo = new QsInfo(0, null, null);
                SignalIcon$IconGroup signalIcon$IconGroup2 = mobileState.iconGroup;
                Object[] objArr = (signalIcon$IconGroup2 != TelephonyIcons.DATA_DISABLED || signalIcon$IconGroup2 == TelephonyIcons.NOT_DEFAULT_DATA) && mobileState.userSetup;
                IconState iconState2 = new IconState((mobileState.enabled || mobileState.airplaneMode) ? false : true, getCurrentIconId(), string);
                if ((mobileState.dataConnected || !mobileState.isDefault) && objArr == false && !this.mConfig.alwaysShowDataRatIcon) {
                }
                SbInfo sbInfo = new SbInfo((mobileState.enabled || mobileState.airplaneMode) ? false : true, i3, iconState2);
                z = mobileState.dataConnected;
                if (z || mobileState.carrierNetworkChangeMode || !mobileState.activityIn) {
                    z2 = true;
                    z3 = false;
                } else {
                    z2 = true;
                }
                signalCallback.setMobileDataIndicators(new MobileDataIndicators(sbInfo.icon, qsInfo.icon, sbInfo.ratTypeIcon, qsInfo.ratTypeIcon, z3, (z || mobileState.carrierNetworkChangeMode || !mobileState.activityOut) ? false : z2, str2, textIfExists, qsInfo.description, this.mSubscriptionInfo.getSubscriptionId(), mobileState.roaming, sbInfo.showTriangle));
            }
            i = (mobileState.dataConnected || (((signalIcon$IconGroup = mobileState.iconGroup) == TelephonyIcons.DATA_DISABLED || signalIcon$IconGroup == TelephonyIcons.NOT_DEFAULT_DATA) && mobileState.userSetup) || this.mConfig.alwaysShowDataRatIcon) ? i3 : 0;
            IconState iconState3 = new IconState(mobileState.enabled && !mobileState.isEmergency, getCurrentIconId(), string);
            str = mobileState.isEmergency ? null : mobileState.networkName;
            iconState = iconState3;
        }
        qsInfo = new QsInfo(i, iconState, str);
        SignalIcon$IconGroup signalIcon$IconGroup22 = mobileState.iconGroup;
        if (signalIcon$IconGroup22 != TelephonyIcons.DATA_DISABLED) {
        }
        IconState iconState22 = new IconState((mobileState.enabled || mobileState.airplaneMode) ? false : true, getCurrentIconId(), string);
        i3 = mobileState.dataConnected ? 0 : 0;
        SbInfo sbInfo2 = new SbInfo((mobileState.enabled || mobileState.airplaneMode) ? false : true, i3, iconState22);
        z = mobileState.dataConnected;
        if (z) {
            z2 = true;
            z3 = false;
        }
        signalCallback.setMobileDataIndicators(new MobileDataIndicators(sbInfo2.icon, qsInfo.icon, sbInfo2.ratTypeIcon, qsInfo.ratTypeIcon, z3, (z || mobileState.carrierNetworkChangeMode || !mobileState.activityOut) ? false : z2, str2, textIfExists, qsInfo.description, this.mSubscriptionInfo.getSubscriptionId(), mobileState.roaming, sbInfo2.showTriangle));
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

    /* JADX WARN: Removed duplicated region for block: B:9:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTelephony() {
        SignalStrength signalStrength;
        TelephonyDisplayInfo telephonyDisplayInfo;
        String operatorAlphaShort;
        String operatorAlphaShort2;
        String operatorAlphaShort3;
        String operatorAlphaShort4;
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
        boolean zIsInService = Utils.isInService(mobileState2.serviceState);
        mobileState2.connected = zIsInService;
        boolean z2 = false;
        if (zIsInService) {
            SignalStrength signalStrength2 = mobileState2.signalStrength;
            if (signalStrength2 != null) {
                if (signalStrength2.isGsm() || !this.mConfig.alwaysShowCdmaRssi) {
                    level = signalStrength2.getLevel();
                } else {
                    List cellSignalStrengths = signalStrength2.getCellSignalStrengths(CellSignalStrengthCdma.class);
                    level = !cellSignalStrengths.isEmpty() ? ((CellSignalStrengthCdma) cellSignalStrengths.get(0)).getLevel() : 0;
                }
                mobileState2.level = level;
            }
        }
        mobileState2.carrierId = this.mPhone.getSimCarrierId();
        TelephonyDisplayInfo telephonyDisplayInfo2 = mobileState2.telephonyDisplayInfo;
        ((MobileMappingsProxyImpl) this.mMobileMappingsProxy).getClass();
        String string = telephonyDisplayInfo2.getOverrideNetworkType() == 0 ? Integer.toString(telephonyDisplayInfo2.getNetworkType()) : MobileMappings.toDisplayIconKey(telephonyDisplayInfo2.getOverrideNetworkType());
        if (((HashMap) this.mNetworkToIconLookup).get(string) != null) {
            mobileState2.iconGroup = (SignalIcon$IconGroup) ((HashMap) this.mNetworkToIconLookup).get(string);
        } else {
            mobileState2.iconGroup = this.mDefaultIcons;
        }
        mobileState2.dataConnected = mobileState2.connected && mobileState2.dataState == 2;
        mobileState2.roaming = !((MobileState) connectivityState).carrierNetworkChangeMode && ((signalStrength = mobileState2.signalStrength) == null || signalStrength.isGsm() ? !((telephonyDisplayInfo = mobileState2.telephonyDisplayInfo) == null || !telephonyDisplayInfo.isRoaming()) : this.mPhone.getCdmaEnhancedRoamingIndicatorDisplayNumber() != 1);
        if (((MobileState) connectivityState).carrierNetworkChangeMode) {
            mobileState2.iconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
        } else if (!this.mPhone.isDataConnectionAllowed() && !this.mConfig.alwaysShowDataRatIcon) {
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
        String str3 = "";
        if (str.equals(str2)) {
            ServiceState serviceState3 = mobileState2.serviceState;
            if (serviceState3 == null || (operatorAlphaShort3 = serviceState3.getOperatorAlphaShort()) == null) {
                operatorAlphaShort3 = "";
            }
            if (!TextUtils.isEmpty(operatorAlphaShort3)) {
                ServiceState serviceState4 = mobileState2.serviceState;
                if (serviceState4 == null || (operatorAlphaShort4 = serviceState4.getOperatorAlphaShort()) == null) {
                    operatorAlphaShort4 = "";
                }
                mobileState2.networkName = operatorAlphaShort4;
            }
        }
        if (mobileState2.networkNameData.equals(str2) && mobileState2.dataSim) {
            ServiceState serviceState5 = mobileState2.serviceState;
            if (serviceState5 == null || (operatorAlphaShort = serviceState5.getOperatorAlphaShort()) == null) {
                operatorAlphaShort = "";
            }
            if (!TextUtils.isEmpty(operatorAlphaShort)) {
                ServiceState serviceState6 = mobileState2.serviceState;
                if (serviceState6 != null && (operatorAlphaShort2 = serviceState6.getOperatorAlphaShort()) != null) {
                    str3 = operatorAlphaShort2;
                }
                mobileState2.networkNameData = str3;
            }
        }
        notifyListenersIfNecessary();
    }
}
