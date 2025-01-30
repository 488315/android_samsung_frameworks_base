package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.SubscriptionInfo;
import android.util.ArraySet;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.net.VpnConfig;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarSignalPolicy implements SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable {
    public static final boolean DEBUG = Log.isLoggable("StatusBarSignalPolicy", 3);
    public final boolean mActivityEnabled;
    public int mAirplaneResId;
    public final Context mContext;
    public final DesktopManager mDesktopManager;
    public boolean mHideAirplane;
    public boolean mHideEthernet;
    public boolean mHideMobile;
    public boolean mHideWifi;
    public final StatusBarIconController mIconController;
    public boolean mInitialized;
    public final NetworkController mNetworkController;
    public final SecurityController mSecurityController;
    public final String mSlotAirplane;
    public final String mSlotCallStrength;
    public final String mSlotEthernet;
    public final String mSlotMobile;
    public final String mSlotNoCalling;
    public final String mSlotVpn;
    public final String mSlotWifi;
    public final TunerService mTunerService;
    public final Handler mHandler = Handler.getMain();
    public boolean mIsAirplaneMode = false;
    public boolean mIsWifiEnabled = false;
    public final ArrayList mMobileStates = new ArrayList();
    public final ArrayList mCallIndicatorStates = new ArrayList();
    public WifiIconState mWifiIconState = new WifiIconState();
    public final C31591 mDesktopStatusBarIconUpdateCallback = new DesktopCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy.1
        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
        public final void updateDesktopStatusBarIcons() {
            StatusBarSignalPolicy statusBarSignalPolicy = StatusBarSignalPolicy.this;
            ((DesktopManagerImpl) statusBarSignalPolicy.mDesktopManager).setAirplaneMode(statusBarSignalPolicy.mIsAirplaneMode, statusBarSignalPolicy.mAirplaneResId);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CallIndicatorIconState {
        public final int callStrengthResId;
        public final int noCallingResId;
        public final int subId;

        public /* synthetic */ CallIndicatorIconState(int i, int i2) {
            this(i);
        }

        public final boolean equals(Object obj) {
            if (obj == null || CallIndicatorIconState.class != obj.getClass()) {
                return false;
            }
            CallIndicatorIconState callIndicatorIconState = (CallIndicatorIconState) obj;
            return this.noCallingResId == callIndicatorIconState.noCallingResId && this.callStrengthResId == callIndicatorIconState.callStrengthResId && this.subId == callIndicatorIconState.subId;
        }

        public final int hashCode() {
            return Objects.hash(Boolean.FALSE, Integer.valueOf(this.noCallingResId), Integer.valueOf(this.callStrengthResId), Integer.valueOf(this.subId), null, null);
        }

        private CallIndicatorIconState(int i) {
            this.subId = i;
            this.noCallingResId = R.drawable.ic_shade_no_calling_sms;
            this.callStrengthResId = TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[0];
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DesktopCallback {
        void updateDesktopStatusBarIcons();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MobileIconState extends SignalIconState {
        public boolean needsLeadingPadding;
        public boolean roaming;
        public boolean showTriangle;
        public int strengthId;
        public int subId;
        public CharSequence typeContentDescription;
        public int typeId;

        /* renamed from: -$$Nest$smcopyStates, reason: not valid java name */
        public static void m1719$$Nest$smcopyStates(List list) {
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                MobileIconState mobileIconState = (MobileIconState) it.next();
                MobileIconState mobileIconState2 = new MobileIconState(mobileIconState.subId);
                mobileIconState.copyTo(mobileIconState2);
                arrayList.add(mobileIconState2);
            }
        }

        public /* synthetic */ MobileIconState(int i, int i2) {
            this(i);
        }

        public final MobileIconState copy() {
            MobileIconState mobileIconState = new MobileIconState(this.subId);
            copyTo(mobileIconState);
            return mobileIconState;
        }

        public final void copyTo(MobileIconState mobileIconState) {
            mobileIconState.visible = this.visible;
            mobileIconState.activityIn = this.activityIn;
            mobileIconState.activityOut = this.activityOut;
            mobileIconState.slot = this.slot;
            mobileIconState.contentDescription = this.contentDescription;
            mobileIconState.subId = this.subId;
            mobileIconState.strengthId = this.strengthId;
            mobileIconState.typeId = this.typeId;
            mobileIconState.showTriangle = this.showTriangle;
            mobileIconState.roaming = this.roaming;
            mobileIconState.needsLeadingPadding = this.needsLeadingPadding;
            mobileIconState.typeContentDescription = this.typeContentDescription;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public final boolean equals(Object obj) {
            if (obj == null || MobileIconState.class != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            MobileIconState mobileIconState = (MobileIconState) obj;
            return this.subId == mobileIconState.subId && this.strengthId == mobileIconState.strengthId && this.typeId == mobileIconState.typeId && this.showTriangle == mobileIconState.showTriangle && this.roaming == mobileIconState.roaming && this.needsLeadingPadding == mobileIconState.needsLeadingPadding && Objects.equals(this.typeContentDescription, mobileIconState.typeContentDescription);
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public final int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.subId), Integer.valueOf(this.strengthId), Integer.valueOf(this.typeId), Boolean.valueOf(this.showTriangle), Boolean.valueOf(this.roaming), Boolean.valueOf(this.needsLeadingPadding), this.typeContentDescription);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MobileIconState(subId=");
            sb.append(this.subId);
            sb.append(", strengthId=");
            sb.append(this.strengthId);
            sb.append(", showTriangle=");
            sb.append(this.showTriangle);
            sb.append(", roaming=");
            sb.append(this.roaming);
            sb.append(", typeId=");
            sb.append(this.typeId);
            sb.append(", visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.visible, ")");
        }

        private MobileIconState(int i) {
            super(0);
            this.subId = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SignalIconState {
        public boolean activityIn;
        public boolean activityOut;
        public String contentDescription;
        public String slot;
        public boolean visible;

        private SignalIconState() {
        }

        public /* synthetic */ SignalIconState(int i) {
            this();
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SignalIconState signalIconState = (SignalIconState) obj;
            return this.visible == signalIconState.visible && this.activityOut == signalIconState.activityOut && this.activityIn == signalIconState.activityIn && Objects.equals(this.contentDescription, signalIconState.contentDescription) && Objects.equals(this.slot, signalIconState.slot);
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.visible), Boolean.valueOf(this.activityOut), this.slot);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiIconState extends SignalIconState {
        public boolean airplaneSpacerVisible;
        public boolean noDefaultNetwork;
        public boolean noNetworksAvailable;
        public boolean noValidatedNetwork;
        public int resId;
        public boolean signalSpacerVisible;

        public WifiIconState() {
            super(0);
        }

        public final WifiIconState copy() {
            WifiIconState wifiIconState = new WifiIconState();
            wifiIconState.visible = this.visible;
            wifiIconState.activityIn = this.activityIn;
            wifiIconState.activityOut = this.activityOut;
            wifiIconState.slot = this.slot;
            wifiIconState.contentDescription = this.contentDescription;
            wifiIconState.resId = this.resId;
            wifiIconState.airplaneSpacerVisible = this.airplaneSpacerVisible;
            wifiIconState.signalSpacerVisible = this.signalSpacerVisible;
            wifiIconState.noDefaultNetwork = this.noDefaultNetwork;
            wifiIconState.noValidatedNetwork = this.noValidatedNetwork;
            wifiIconState.noNetworksAvailable = this.noNetworksAvailable;
            return wifiIconState;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public final boolean equals(Object obj) {
            if (obj == null || WifiIconState.class != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            WifiIconState wifiIconState = (WifiIconState) obj;
            return this.resId == wifiIconState.resId && this.airplaneSpacerVisible == wifiIconState.airplaneSpacerVisible && this.signalSpacerVisible == wifiIconState.signalSpacerVisible && this.noDefaultNetwork == wifiIconState.noDefaultNetwork && this.noValidatedNetwork == wifiIconState.noValidatedNetwork && this.noNetworksAvailable == wifiIconState.noNetworksAvailable;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.SignalIconState
        public final int hashCode() {
            return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.resId), Boolean.valueOf(this.airplaneSpacerVisible), Boolean.valueOf(this.signalSpacerVisible), Boolean.valueOf(this.noDefaultNetwork), Boolean.valueOf(this.noValidatedNetwork), Boolean.valueOf(this.noNetworksAvailable));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("WifiIconState(resId=");
            sb.append(this.resId);
            sb.append(", visible=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.visible, ")");
        }
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.statusbar.phone.StatusBarSignalPolicy$1] */
    public StatusBarSignalPolicy(Context context, StatusBarIconController statusBarIconController, CarrierConfigTracker carrierConfigTracker, NetworkController networkController, SecurityController securityController, TunerService tunerService, DesktopManager desktopManager, CoverScreenIconController coverScreenIconController) {
        this.mContext = context;
        this.mIconController = statusBarIconController;
        this.mNetworkController = networkController;
        this.mSecurityController = securityController;
        this.mTunerService = tunerService;
        this.mSlotAirplane = context.getString(17042905);
        this.mSlotMobile = context.getString(17042930);
        this.mSlotWifi = context.getString(17042951);
        this.mSlotEthernet = context.getString(17042919);
        this.mSlotVpn = context.getString(17042950);
        this.mSlotNoCalling = context.getString(17042935);
        this.mSlotCallStrength = context.getString(17042911);
        this.mActivityEnabled = context.getResources().getBoolean(R.bool.config_showActivity);
        this.mDesktopManager = desktopManager;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public final void onStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0063, code lost:
            
                if (r0 == false) goto L28;
             */
            /* JADX WARN: Code restructure failed: missing block: B:23:0x005e, code lost:
            
                if (r0.checkPermission("android.permission.POST_NOTIFICATIONS", "com.samsung.android.fast") == 0) goto L24;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z;
                StatusBarSignalPolicy statusBarSignalPolicy = StatusBarSignalPolicy.this;
                SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) statusBarSignalPolicy.mSecurityController;
                boolean isVpnEnabled = securityControllerImpl.isVpnEnabled();
                int i = securityControllerImpl.isVpnBranded() ? R.drawable.stat_sys_branded_vpn : R.drawable.stat_sys_vpn_ic;
                if (isVpnEnabled && securityControllerImpl.isSecureWifiEnabled()) {
                    i = R.drawable.stat_sys_securewifi_ic;
                }
                VpnConfig vpnConfig = (VpnConfig) securityControllerImpl.mCurrentVpns.get(securityControllerImpl.mVpnUserId);
                boolean z2 = true;
                if (!(vpnConfig == null ? false : vpnConfig.legacy)) {
                    if (securityControllerImpl.isSecureWifiEnabled()) {
                        PackageManager packageManager = securityControllerImpl.mContext.getPackageManager();
                        if (packageManager.checkSignatures("android", "com.samsung.android.fast") == 0) {
                            try {
                                if (packageManager.getPackageInfo("com.samsung.android.fast", 0).applicationInfo.targetSdkVersion >= 33) {
                                }
                                z = true;
                            } catch (PackageManager.NameNotFoundException unused) {
                            }
                        }
                        z = false;
                    }
                    z2 = false;
                }
                String string = statusBarSignalPolicy.mContext.getResources().getString(R.string.accessibility_vpn_on);
                StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarSignalPolicy.mIconController;
                ArrayList arrayList = statusBarIconControllerImpl.mSystemIconsAllowList;
                final String str = statusBarSignalPolicy.mSlotVpn;
                boolean contains = arrayList.contains(str);
                if (z2) {
                    if (contains) {
                        arrayList.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).equals(str);
                            }
                        });
                    }
                } else if (!contains) {
                    arrayList.add(str);
                }
                if (statusBarIconControllerImpl.mStatusBarPipelineFlags.isIconControlledByFlags(str)) {
                    Log.i("StatusBarIconController", "Ignoring removal of (" + str + "). It should be controlled elsewhere");
                } else {
                    StatusBarIconList statusBarIconList = statusBarIconControllerImpl.mStatusBarIconList;
                    if (statusBarIconList.getIconHolder(0, str) != null) {
                        int viewIndex = statusBarIconList.getViewIndex(0, str);
                        ((StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(str))).removeForTag(0);
                        statusBarIconControllerImpl.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda1(viewIndex, 0));
                    }
                }
                statusBarIconControllerImpl.setIcon(string, str, i);
                statusBarIconControllerImpl.setIconVisibility(str, isVpnEnabled);
            }
        });
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            boolean contains = iconHideList.contains(this.mSlotAirplane);
            boolean contains2 = iconHideList.contains(this.mSlotMobile);
            boolean contains3 = iconHideList.contains(this.mSlotWifi);
            boolean contains4 = iconHideList.contains(this.mSlotEthernet);
            if (contains == this.mHideAirplane && contains2 == this.mHideMobile && contains4 == this.mHideEthernet && contains3 == this.mHideWifi) {
                return;
            }
            this.mHideAirplane = contains;
            this.mHideMobile = contains2;
            this.mHideEthernet = contains4;
            this.mHideWifi = contains3;
            NetworkController networkController = this.mNetworkController;
            ((NetworkControllerImpl) networkController).removeCallback(this);
            ((NetworkControllerImpl) networkController).addCallback(this);
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setEthernetIndicators(IconState iconState) {
        if (iconState.visible) {
            boolean z = this.mHideEthernet;
        }
        String str = this.mSlotEthernet;
        StatusBarIconController statusBarIconController = this.mIconController;
        int i = iconState.icon;
        if (i <= 0) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(iconState.contentDescription, str, i);
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setIsAirplaneMode(IconState iconState) {
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setIsAirplaneMode: icon = ".concat(iconState == null ? "" : iconState.toString()));
        }
        boolean z = iconState.visible && !this.mHideAirplane;
        this.mIsAirplaneMode = z;
        String str = this.mSlotAirplane;
        StatusBarIconController statusBarIconController = this.mIconController;
        int i = iconState.icon;
        if (!z || i <= 0) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        } else {
            String str2 = iconState.contentDescription;
            i = R.drawable.samsung_stat_sys_airplane_mode;
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(str2, str, R.drawable.samsung_stat_sys_airplane_mode);
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        }
        this.mAirplaneResId = i;
        ((DesktopManagerImpl) this.mDesktopManager).setAirplaneMode(this.mIsAirplaneMode, i);
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
        MobileIconState mobileIconState;
        boolean z = DEBUG;
        if (z) {
            Log.d("StatusBarSignalPolicy", "setMobileDataIndicators: " + mobileDataIndicators);
        }
        int i = mobileDataIndicators.subId;
        ArrayList arrayList = this.mMobileStates;
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("Unexpected subscription ", i, "StatusBarSignalPolicy");
                mobileIconState = null;
                break;
            } else {
                mobileIconState = (MobileIconState) it.next();
                if (mobileIconState.subId == i) {
                    break;
                }
            }
        }
        if (mobileIconState == null) {
            return;
        }
        int i2 = mobileIconState.typeId;
        boolean z2 = false;
        int i3 = mobileDataIndicators.statusType;
        boolean z3 = i3 != i2 && (i3 == 0 || i2 == 0);
        IconState iconState = mobileDataIndicators.statusIcon;
        mobileIconState.visible = iconState.visible && !this.mHideMobile;
        mobileIconState.strengthId = iconState.icon;
        mobileIconState.typeId = i3;
        mobileIconState.contentDescription = iconState.contentDescription;
        mobileIconState.typeContentDescription = mobileDataIndicators.typeContentDescription;
        mobileIconState.showTriangle = mobileDataIndicators.showTriangle;
        mobileIconState.roaming = mobileDataIndicators.roaming;
        boolean z4 = mobileDataIndicators.activityIn;
        boolean z5 = this.mActivityEnabled;
        mobileIconState.activityIn = z4 && z5;
        mobileIconState.activityOut = mobileDataIndicators.activityOut && z5;
        if (z) {
            Log.d("StatusBarSignalPolicy", "MobileIconStates: " + arrayList.toString());
        }
        MobileIconState.m1719$$Nest$smcopyStates(arrayList);
        ((StatusBarIconControllerImpl) this.mIconController).mStatusBarPipelineFlags.useNewMobileIcons();
        Log.d("StatusBarIconController", "ignoring old pipeline callbacks, because the new mobile icons are enabled");
        if (z3) {
            WifiIconState copy = this.mWifiIconState.copy();
            MobileIconState mobileIconState2 = arrayList.size() > 0 ? (MobileIconState) arrayList.get(0) : null;
            if (mobileIconState2 != null && mobileIconState2.typeId != 0) {
                z2 = true;
            }
            copy.signalSpacerVisible = z2;
            if (copy.equals(this.mWifiIconState)) {
                return;
            }
            updateWifiIconWithState(copy);
            this.mWifiIconState = copy;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setSubs(List list) {
        boolean z;
        boolean z2;
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("setSubs: "), list == null ? "" : list.toString(), "StatusBarSignalPolicy");
        }
        int size = list.size();
        ArrayList arrayList = this.mMobileStates;
        int i = 0;
        if (size == arrayList.size()) {
            for (int i2 = 0; i2 < size; i2++) {
                if (((MobileIconState) arrayList.get(i2)).subId == ((SubscriptionInfo) list.get(i2)).getSubscriptionId()) {
                }
            }
            z = true;
            if (z) {
                StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) this.mIconController;
                statusBarIconControllerImpl.removeAllIconsForSlot(this.mSlotMobile);
                statusBarIconControllerImpl.removeAllIconsForSlot(this.mSlotNoCalling);
                statusBarIconControllerImpl.removeAllIconsForSlot(this.mSlotCallStrength);
                arrayList.clear();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = this.mCallIndicatorStates;
                arrayList2.addAll(arrayList3);
                arrayList3.clear();
                int size2 = list.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayList.add(new MobileIconState(((SubscriptionInfo) list.get(i3)).getSubscriptionId(), i));
                    Iterator it = arrayList2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = true;
                            break;
                        }
                        CallIndicatorIconState callIndicatorIconState = (CallIndicatorIconState) it.next();
                        if (callIndicatorIconState.subId == ((SubscriptionInfo) list.get(i3)).getSubscriptionId()) {
                            arrayList3.add(callIndicatorIconState);
                            z2 = false;
                            break;
                        }
                    }
                    if (z2) {
                        arrayList3.add(new CallIndicatorIconState(((SubscriptionInfo) list.get(i3)).getSubscriptionId(), i));
                    }
                }
                return;
            }
            return;
        }
        z = false;
        if (z) {
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setWifiIndicators(WifiIndicators wifiIndicators) {
        boolean z;
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setWifiIndicators: " + wifiIndicators);
        }
        boolean z2 = false;
        boolean z3 = wifiIndicators.statusIcon.visible && !this.mHideWifi;
        boolean z4 = wifiIndicators.activityIn;
        boolean z5 = this.mActivityEnabled;
        boolean z6 = z4 && z5 && z3;
        boolean z7 = wifiIndicators.activityOut && z5 && z3;
        this.mIsWifiEnabled = wifiIndicators.enabled;
        WifiIconState copy = this.mWifiIconState.copy();
        WifiIconState wifiIconState = this.mWifiIconState;
        boolean z8 = wifiIconState.noDefaultNetwork;
        if (z8 && wifiIconState.noNetworksAvailable && !this.mIsAirplaneMode) {
            copy.visible = true;
            copy.resId = R.drawable.ic_qs_no_internet_unavailable;
        } else if (!z8 || wifiIconState.noNetworksAvailable || ((z = this.mIsAirplaneMode) && !(z && this.mIsWifiEnabled))) {
            copy.visible = z3;
            IconState iconState = wifiIndicators.statusIcon;
            copy.resId = iconState.icon;
            copy.activityIn = z6;
            copy.activityOut = z7;
            copy.contentDescription = iconState.contentDescription;
            ArrayList arrayList = this.mMobileStates;
            MobileIconState mobileIconState = arrayList.size() > 0 ? (MobileIconState) arrayList.get(0) : null;
            if (mobileIconState != null && mobileIconState.typeId != 0) {
                z2 = true;
            }
            copy.signalSpacerVisible = z2;
        } else {
            copy.visible = true;
            copy.resId = R.drawable.ic_qs_no_internet_available;
        }
        copy.slot = this.mSlotWifi;
        copy.airplaneSpacerVisible = this.mIsAirplaneMode;
        updateWifiIconWithState(copy);
        this.mWifiIconState = copy;
    }

    public final void updateWifiIconWithState(WifiIconState wifiIconState) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("WifiIconState: ");
            sb.append(wifiIconState);
            Log.d("StatusBarSignalPolicy", sb.toString() == null ? "" : wifiIconState.toString());
        }
        boolean z = wifiIconState.visible;
        String str = this.mSlotWifi;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (!z || wifiIconState.resId <= 0) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
            return;
        }
        StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
        statusBarIconControllerImpl.mStatusBarPipelineFlags.useNewWifiIcon();
        Log.d("StatusBarIconController", "ignoring old pipeline callback because the new wifi icon is enabled");
        statusBarIconControllerImpl.setIconVisibility(str, true);
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setMobileDataEnabled(boolean z) {
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setNoSims(boolean z, boolean z2) {
    }
}
