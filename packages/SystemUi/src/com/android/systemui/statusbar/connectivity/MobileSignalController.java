package com.android.systemui.statusbar.connectivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileSignalController extends SignalController {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public MobileMappings.Config mConfig;
    public SignalIcon$MobileIconGroup mDefaultIcons;
    public final MobileStatusTracker.SubscriptionDefaults mDefaults;
    boolean mInflateSignalStrengths;
    public final C26191 mMobileCallback;
    public final MobileMappingsProxy mMobileMappingsProxy;
    public final String[] mMobileStatusHistory;
    public int mMobileStatusHistoryIndex;
    final MobileStatusTracker mMobileStatusTracker;
    public final String mNetworkNameDefault;
    public final String mNetworkNameSeparator;
    public Map mNetworkToIconLookup;
    public final C26202 mObserver;
    public final TelephonyManager mPhone;
    public final SubscriptionInfo mSubscriptionInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.connectivity.MobileSignalController$1 */
    public final class C26191 implements MobileStatusTracker.Callback {
        public String mLastStatus;

        public C26191() {
        }

        public final void onMobileStatusChanged(boolean z, MobileStatusTracker.MobileStatus mobileStatus) {
            boolean z2 = SignalController.DEBUG;
            MobileSignalController mobileSignalController = MobileSignalController.this;
            if (z2) {
                String str = mobileSignalController.mTag;
                StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("onMobileStatusChanged= updateTelephony=", z, " mobileStatus=");
                m49m.append(mobileStatus.toString());
                Log.d(str, m49m.toString());
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        C26191 c26191 = new C26191();
        this.mMobileCallback = c26191;
        this.mConfig = config;
        this.mPhone = telephonyManager;
        this.mDefaults = subscriptionDefaults;
        this.mSubscriptionInfo = subscriptionInfo;
        this.mMobileMappingsProxy = mobileMappingsProxy;
        this.mNetworkNameSeparator = getTextIfExists(R.string.status_bar_network_name_separator).toString();
        String charSequence = getTextIfExists(android.R.string.permdesc_accessWimaxState).toString();
        this.mNetworkNameDefault = charSequence;
        MobileMappings.Config config2 = this.mConfig;
        MobileMappingsProxyImpl mobileMappingsProxyImpl = (MobileMappingsProxyImpl) mobileMappingsProxy;
        mobileMappingsProxyImpl.getClass();
        this.mNetworkToIconLookup = MobileMappings.mapIconSets(config2);
        MobileMappings.Config config3 = this.mConfig;
        mobileMappingsProxyImpl.getClass();
        this.mDefaultIcons = !config3.showAtLeast3G ? TelephonyIcons.f221G : TelephonyIcons.THREE_G;
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
        this.mMobileStatusTracker = new MobileStatusTracker(mobileStatusTrackerFactory.phone, mobileStatusTrackerFactory.receiverLooper, mobileStatusTrackerFactory.info, mobileStatusTrackerFactory.defaults, c26191);
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
        int i3 = this.mMobileStatusHistoryIndex + 64;
        while (true) {
            i3--;
            if (i3 < (this.mMobileStatusHistoryIndex + 64) - i2) {
                dumpTableData(printWriter);
                return;
            }
            printWriter.println("  Previous MobileStatus(" + ((this.mMobileStatusHistoryIndex + 64) - i3) + "): " + strArr[i3 & 63]);
        }
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
            if (!mobileState.enabled) {
                return 0;
            }
            int numSignalStrengthLevels2 = this.mInflateSignalStrengths ? CellSignalStrength.getNumSignalStrengthLevels() + 1 : CellSignalStrength.getNumSignalStrengthLevels();
            int i2 = SignalDrawable.$r8$clinit;
            return (numSignalStrengthLevels2 << 8) | 131072 | 0;
        }
        int i3 = mobileState.level;
        boolean z = this.mInflateSignalStrengths;
        if (z) {
            i3++;
        }
        boolean z2 = (mobileState.userSetup && (signalIcon$IconGroup == TelephonyIcons.DATA_DISABLED || (signalIcon$IconGroup == TelephonyIcons.NOT_DEFAULT_DATA && mobileState.defaultDataOff))) || (mobileState.inetCondition == 0);
        int numSignalStrengthLevels3 = z ? CellSignalStrength.getNumSignalStrengthLevels() + 1 : CellSignalStrength.getNumSignalStrengthLevels();
        int i4 = SignalDrawable.$r8$clinit;
        return (numSignalStrengthLevels3 << 8) | ((z2 ? 2 : 0) << 16) | i3;
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
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, stringExtra3, "CarrierLabel");
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
    /* JADX WARN: Removed duplicated region for block: B:111:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0121  */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    @Override // com.android.systemui.statusbar.connectivity.SignalController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyListeners(SignalCallback signalCallback) {
        int i;
        int i2;
        String str;
        QsInfo qsInfo;
        ?? r8;
        ?? r7;
        SubscriptionInfo subscriptionInfo = this.mSubscriptionInfo;
        int subscriptionId = subscriptionInfo.getSubscriptionId();
        WifiState wifiState = (WifiState) this.mNetworkController.mWifiSignalController.mCurrentState;
        if ((wifiState.isDefault && wifiState.isCarrierMerged && wifiState.subId == subscriptionId) == true) {
            return;
        }
        ConnectivityState connectivityState = this.mCurrentState;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) connectivityState.iconGroup;
        String charSequence = getTextIfExists(getContentDescription()).toString();
        CharSequence textIfExists = getTextIfExists(signalIcon$MobileIconGroup.dataContentDescription);
        String obj = Html.fromHtml(textIfExists.toString(), 0).toString();
        MobileState mobileState = (MobileState) connectivityState;
        int i3 = mobileState.inetCondition;
        Context context = this.mContext;
        if (i3 == 0) {
            obj = context.getString(R.string.data_connection_no_internet);
        }
        String str2 = obj;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) mobileState.iconGroup;
        int i4 = mobileState.carrierId;
        NetworkTypeResIdCache networkTypeResIdCache = mobileState.networkTypeResIdCache;
        Integer num = networkTypeResIdCache.lastCarrierId;
        if (num == null || num.intValue() != i4 || !Intrinsics.areEqual(networkTypeResIdCache.lastIconGroup, signalIcon$MobileIconGroup2)) {
            networkTypeResIdCache.lastCarrierId = Integer.valueOf(i4);
            networkTypeResIdCache.lastIconGroup = signalIcon$MobileIconGroup2;
            ((MobileIconCarrierIdOverridesImpl) networkTypeResIdCache.overrides).getClass();
            MobileIconCarrierIdOverridesImpl.Companion companion = MobileIconCarrierIdOverridesImpl.Companion;
            companion.getClass();
            Integer valueOf = Integer.valueOf(i4);
            Map map = MobileIconCarrierIdOverridesImpl.MAPPING;
            if (map.containsKey(valueOf)) {
                Resources resources = context.getResources();
                Integer num2 = (Integer) map.get(Integer.valueOf(i4));
                if (num2 != null) {
                    TypedArray obtainTypedArray = resources.obtainTypedArray(num2.intValue());
                    Map<String, Integer> parseNetworkIconOverrideTypedArray = companion.parseNetworkIconOverrideTypedArray(obtainTypedArray);
                    obtainTypedArray.recycle();
                    Integer num3 = parseNetworkIconOverrideTypedArray.get(signalIcon$MobileIconGroup2.name);
                    if (num3 != null) {
                        i = num3.intValue();
                        if (i <= 0) {
                            networkTypeResIdCache.cachedResId = i;
                            networkTypeResIdCache.isOverridden = true;
                        } else {
                            networkTypeResIdCache.cachedResId = signalIcon$MobileIconGroup2.dataType;
                            networkTypeResIdCache.isOverridden = false;
                        }
                    }
                }
            }
            i = 0;
            if (i <= 0) {
            }
        }
        int i5 = networkTypeResIdCache.cachedResId;
        IconState iconState = null;
        if (!mobileState.dataSim) {
            i2 = 0;
            str = null;
        } else {
            if (!mobileState.isDefault) {
                qsInfo = new QsInfo(0, null, null);
                SignalIcon$IconGroup signalIcon$IconGroup = mobileState.iconGroup;
                r8 = (signalIcon$IconGroup != TelephonyIcons.DATA_DISABLED || signalIcon$IconGroup == TelephonyIcons.NOT_DEFAULT_DATA) && mobileState.userSetup;
                IconState iconState2 = new IconState((mobileState.enabled || mobileState.airplaneMode) ? false : true, getCurrentIconId(), charSequence);
                if (((!mobileState.dataConnected && mobileState.isDefault) || r8 == true) == false && !this.mConfig.alwaysShowDataRatIcon) {
                    i5 = 0;
                }
                SbInfo sbInfo = new SbInfo((mobileState.enabled || mobileState.airplaneMode) ? false : true, i5, iconState2);
                IconState iconState3 = sbInfo.icon;
                IconState iconState4 = qsInfo.icon;
                int i6 = sbInfo.ratTypeIcon;
                int i7 = qsInfo.ratTypeIcon;
                boolean z = mobileState.dataConnected;
                signalCallback.setMobileDataIndicators(new MobileDataIndicators(iconState3, iconState4, i6, i7, (z || mobileState.carrierNetworkChangeMode || !mobileState.activityIn) ? false : true, (z || mobileState.carrierNetworkChangeMode || !mobileState.activityOut) ? false : true, str2, textIfExists, qsInfo.description, subscriptionInfo.getSubscriptionId(), mobileState.roaming, sbInfo.showTriangle));
            }
            if (!mobileState.dataConnected) {
                SignalIcon$IconGroup signalIcon$IconGroup2 = mobileState.iconGroup;
                if (((signalIcon$IconGroup2 == TelephonyIcons.DATA_DISABLED || signalIcon$IconGroup2 == TelephonyIcons.NOT_DEFAULT_DATA) && mobileState.userSetup) == false) {
                    r7 = false;
                    i2 = (!r7 == true || this.mConfig.alwaysShowDataRatIcon) ? i5 : 0;
                    IconState iconState5 = new IconState((mobileState.enabled || mobileState.isEmergency) ? false : true, getCurrentIconId(), charSequence);
                    str = mobileState.isEmergency ? null : mobileState.networkName;
                    iconState = iconState5;
                }
            }
            r7 = true;
            if (r7 == true) {
            }
            IconState iconState52 = new IconState((mobileState.enabled || mobileState.isEmergency) ? false : true, getCurrentIconId(), charSequence);
            str = mobileState.isEmergency ? null : mobileState.networkName;
            iconState = iconState52;
        }
        qsInfo = new QsInfo(i2, iconState, str);
        SignalIcon$IconGroup signalIcon$IconGroup3 = mobileState.iconGroup;
        if (signalIcon$IconGroup3 != TelephonyIcons.DATA_DISABLED) {
        }
        IconState iconState22 = new IconState((mobileState.enabled || mobileState.airplaneMode) ? false : true, getCurrentIconId(), charSequence);
        if (((!mobileState.dataConnected && mobileState.isDefault) || r8 == true) == false) {
            i5 = 0;
        }
        SbInfo sbInfo2 = new SbInfo((mobileState.enabled || mobileState.airplaneMode) ? false : true, i5, iconState22);
        IconState iconState32 = sbInfo2.icon;
        IconState iconState42 = qsInfo.icon;
        int i62 = sbInfo2.ratTypeIcon;
        int i72 = qsInfo.ratTypeIcon;
        boolean z2 = mobileState.dataConnected;
        signalCallback.setMobileDataIndicators(new MobileDataIndicators(iconState32, iconState42, i62, i72, (z2 || mobileState.carrierNetworkChangeMode || !mobileState.activityIn) ? false : true, (z2 || mobileState.carrierNetworkChangeMode || !mobileState.activityOut) ? false : true, str2, textIfExists, qsInfo.description, subscriptionInfo.getSubscriptionId(), mobileState.roaming, sbInfo2.showTriangle));
    }

    public final void registerListener() {
        this.mMobileStatusTracker.setListening(true);
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriFor = Settings.Global.getUriFor("mobile_data");
        C26202 c26202 = this.mObserver;
        contentResolver.registerContentObserver(uriFor, true, c26202);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data" + this.mSubscriptionInfo.getSubscriptionId()), true, c26202);
    }

    public void setActivity(int i) {
        ConnectivityState connectivityState = this.mCurrentState;
        ((MobileState) connectivityState).activityIn = i == 3 || i == 1;
        ((MobileState) connectivityState).activityOut = i == 3 || i == 2;
        notifyListenersIfNecessary();
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTelephony() {
        boolean z;
        ServiceState serviceState;
        String str;
        String str2;
        TelephonyDisplayInfo telephonyDisplayInfo;
        int i;
        boolean z2 = SignalController.DEBUG;
        ConnectivityState connectivityState = this.mCurrentState;
        if (z2) {
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
        boolean z3 = false;
        if (isInService) {
            SignalStrength signalStrength = mobileState2.signalStrength;
            if (signalStrength != null) {
                if (signalStrength.isGsm() || !this.mConfig.alwaysShowCdmaRssi) {
                    i = signalStrength.getLevel();
                } else {
                    List cellSignalStrengths = signalStrength.getCellSignalStrengths(CellSignalStrengthCdma.class);
                    if (!cellSignalStrengths.isEmpty()) {
                        i = ((CellSignalStrengthCdma) cellSignalStrengths.get(0)).getLevel();
                    }
                }
                mobileState2.level = i;
            }
            i = 0;
            mobileState2.level = i;
        }
        TelephonyManager telephonyManager = this.mPhone;
        mobileState2.carrierId = telephonyManager.getSimCarrierId();
        TelephonyDisplayInfo telephonyDisplayInfo2 = mobileState2.telephonyDisplayInfo;
        ((MobileMappingsProxyImpl) this.mMobileMappingsProxy).getClass();
        String num = telephonyDisplayInfo2.getOverrideNetworkType() == 0 ? Integer.toString(telephonyDisplayInfo2.getNetworkType()) : MobileMappings.toDisplayIconKey(telephonyDisplayInfo2.getOverrideNetworkType());
        if (((HashMap) this.mNetworkToIconLookup).get(num) != null) {
            mobileState2.iconGroup = (SignalIcon$IconGroup) ((HashMap) this.mNetworkToIconLookup).get(num);
        } else {
            mobileState2.iconGroup = this.mDefaultIcons;
        }
        mobileState2.dataConnected = mobileState2.connected && mobileState2.dataState == 2;
        if (!((MobileState) connectivityState).carrierNetworkChangeMode) {
            SignalStrength signalStrength2 = mobileState2.signalStrength;
            if (!((signalStrength2 == null || signalStrength2.isGsm()) ? false : true) ? !((telephonyDisplayInfo = mobileState2.telephonyDisplayInfo) == null || !telephonyDisplayInfo.isRoaming()) : telephonyManager.getCdmaEnhancedRoamingIndicatorDisplayNumber() != 1) {
                z = true;
                mobileState2.roaming = z;
                if (!((MobileState) connectivityState).carrierNetworkChangeMode) {
                    mobileState2.iconGroup = TelephonyIcons.CARRIER_NETWORK_CHANGE;
                } else if ((!telephonyManager.isDataConnectionAllowed()) && !this.mConfig.alwaysShowDataRatIcon) {
                    int subscriptionId = this.mSubscriptionInfo.getSubscriptionId();
                    this.mDefaults.getClass();
                    if (subscriptionId != SubscriptionManager.getDefaultDataSubscriptionId()) {
                        mobileState2.iconGroup = TelephonyIcons.NOT_DEFAULT_DATA;
                    } else {
                        mobileState2.iconGroup = TelephonyIcons.DATA_DISABLED;
                    }
                }
                serviceState = mobileState2.serviceState;
                if ((serviceState == null && serviceState.isEmergencyOnly()) != mobileState2.isEmergency) {
                    ServiceState serviceState2 = mobileState2.serviceState;
                    if (serviceState2 != null && serviceState2.isEmergencyOnly()) {
                        z3 = true;
                    }
                    mobileState2.isEmergency = z3;
                    this.mNetworkController.recalculateEmergency();
                }
                str = mobileState2.networkName;
                str2 = this.mNetworkNameDefault;
                if (str.equals(str2) && !TextUtils.isEmpty(mobileState2.getOperatorAlphaShort())) {
                    mobileState2.networkName = mobileState2.getOperatorAlphaShort();
                }
                if (mobileState2.networkNameData.equals(str2) && mobileState2.dataSim && !TextUtils.isEmpty(mobileState2.getOperatorAlphaShort())) {
                    mobileState2.networkNameData = mobileState2.getOperatorAlphaShort();
                }
                notifyListenersIfNecessary();
            }
        }
        z = false;
        mobileState2.roaming = z;
        if (!((MobileState) connectivityState).carrierNetworkChangeMode) {
        }
        serviceState = mobileState2.serviceState;
        if ((serviceState == null && serviceState.isEmergencyOnly()) != mobileState2.isEmergency) {
        }
        str = mobileState2.networkName;
        str2 = this.mNetworkNameDefault;
        if (str.equals(str2)) {
            mobileState2.networkName = mobileState2.getOperatorAlphaShort();
        }
        if (mobileState2.networkNameData.equals(str2)) {
            mobileState2.networkNameData = mobileState2.getOperatorAlphaShort();
        }
        notifyListenersIfNecessary();
    }
}
