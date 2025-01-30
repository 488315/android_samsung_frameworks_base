package com.android.systemui.settings.multisim;

import android.app.ActivityThread;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.google.android.collect.Lists;
import com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.ims.SemImsManager;
import com.samsung.android.ims.SemImsRegistrationListener;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiSIMController {
    public static final Uri INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    public ActivityStarter mActivityStarter;
    public final C24083 mChangNetModeObserver;
    public final Context mContext;
    public MultiSIMData mData;
    public DataUsageController mDataController;
    public MultiSIMData mDataNotified;
    public boolean mInitDone;
    public final C24149 mIntentReceiver;
    public String mInvalidSimInfo;
    public boolean mIsSlotReversed;
    public int mMaxSimIconNumber;
    public final C24127 mMobileDataObserver;
    public final C24105 mMultiSimDataCrossSlotObserver;
    public String mNetworkNameDefault;
    public final MultiSIMController$$ExternalSyntheticLambda1 mNotifyDataToCallbackRunnable;
    public final MultiSIMController$$ExternalSyntheticLambda1 mNotifyVisToCallbackRunnable;
    public final C24072 mOnSubscriptionsChangeListener;
    public final C24094 mPreferedVoiceObserver;
    public final MultiSIMPreferredSlotView.SIMInfoIconManager.Factory mSIMInfoIconManagerFactory;
    public final C24116 mSettingsListener;
    public SimCardManagerServiceProvider mSimCardManagerService;
    public final C24138 mSimIconAndNameObserver;
    public final HandlerC240412 mUIHandler;
    public String mUnknownPhoneNumber;
    public final HandlerC240311 mUpdateDataHandler;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public C240513 mSimCardCallback = null;
    public boolean mUIVisible = false;
    public final ArrayList mDataCallbacks = Lists.newArrayList();
    public final ArrayList mVisCallbacks = Lists.newArrayList();
    public final ArrayList mDefaultIdUpdateList = new ArrayList();
    public boolean mIsLoadedMultiSim = false;
    public boolean mHasOpportunisticESim = false;
    public boolean mNeedCheckOpportunisticESim = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.settings.multisim.MultiSIMController$13 */
    public final class C240513 {
        public C240513() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.settings.multisim.MultiSIMController$14 */
    public abstract /* synthetic */ class AbstractC240614 {

        /* renamed from: $SwitchMap$com$android$systemui$settings$multisim$MultiSIMController$ButtonType */
        public static final /* synthetic */ int[] f339x9f70d468;

        static {
            int[] iArr = new int[ButtonType.values().length];
            f339x9f70d468 = iArr;
            try {
                iArr[ButtonType.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f339x9f70d468[ButtonType.SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f339x9f70d468[ButtonType.DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum ButtonType {
        VOICE(0),
        SMS(1),
        DATA(2),
        SIMINFO1(3),
        SIMINFO2(4);

        private final int index;

        ButtonType(int i) {
            this.index = i;
        }

        public final int getIndex() {
            return this.index;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum KoreanSimCarrier {
        /* JADX INFO: Fake field, exist only in values array */
        KT("45008"),
        /* JADX INFO: Fake field, exist only in values array */
        LG_U_PLUS("45006");

        private final String mNumeric;

        KoreanSimCarrier(String str) {
            this.mNumeric = str;
        }

        public final String getNumeric() {
            return this.mNumeric;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MultiSIMDataChangedCallback {
        default boolean isPhoneNumberNeeded() {
            return false;
        }

        void onDataChanged(MultiSIMData multiSIMData);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MultiSIMVisibilityChangedCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum PhoneNumberSource {
        /* JADX INFO: Fake field, exist only in values array */
        CARRIER(2),
        /* JADX INFO: Fake field, exist only in values array */
        UICC(1),
        /* JADX INFO: Fake field, exist only in values array */
        IMS(3);

        private final int value;

        PhoneNumberSource(int i) {
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.settings.multisim.MultiSIMController$6, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.MultiSIMController$7] */
    /* JADX WARN: Type inference failed for: r13v1, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.MultiSIMController$8] */
    /* JADX WARN: Type inference failed for: r14v0, types: [com.android.systemui.settings.multisim.MultiSIMController$11] */
    /* JADX WARN: Type inference failed for: r14v1, types: [android.os.Handler, com.android.systemui.settings.multisim.MultiSIMController$12] */
    /* JADX WARN: Type inference failed for: r5v1, types: [android.telephony.SubscriptionManager$OnSubscriptionsChangedListener, com.android.systemui.settings.multisim.MultiSIMController$2] */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.MultiSIMController$3] */
    /* JADX WARN: Type inference failed for: r7v3, types: [android.content.BroadcastReceiver, com.android.systemui.settings.multisim.MultiSIMController$9] */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.MultiSIMController$4] */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.database.ContentObserver, com.android.systemui.settings.multisim.MultiSIMController$5] */
    public MultiSIMController(Context context, NetworkController networkController, UserTracker userTracker, MultiSIMPreferredSlotView.SIMInfoIconManager.Factory factory) {
        char c;
        char c2;
        new SemImsRegistrationListener[]{null, null};
        new SemImsManager[]{null, null};
        this.mIsSlotReversed = false;
        this.mInitDone = false;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.settings.multisim.MultiSIMController.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                UserInfo userInfo = multiSIMController.mUserManager.getUserInfo(i);
                multiSIMController.mData.isSecondaryUser = !userInfo.isAdmin();
                multiSIMController.notifyDataToCallback();
            }
        };
        this.mUserChangedCallback = callback;
        this.mNotifyDataToCallbackRunnable = new MultiSIMController$$ExternalSyntheticLambda1(this, 0);
        ?? r5 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.settings.multisim.MultiSIMController.2
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                Log.d("MultiSIMController", "onSubscriptionsChanged: ");
                HandlerC240311 handlerC240311 = MultiSIMController.this.mUpdateDataHandler;
                if (handlerC240311 != null) {
                    handlerC240311.removeMessages(VolteConstants.ErrorCode.CALL_FORBIDDEN);
                    HandlerC240311 handlerC2403112 = MultiSIMController.this.mUpdateDataHandler;
                    handlerC2403112.sendMessage(handlerC2403112.obtainMessage(VolteConstants.ErrorCode.CALL_FORBIDDEN));
                }
            }
        };
        this.mOnSubscriptionsChangeListener = r5;
        Dependency.DependencyKey dependencyKey = Dependency.MAIN_HANDLER;
        ?? r6 = new ContentObserver((Handler) Dependency.get(dependencyKey)) { // from class: com.android.systemui.settings.multisim.MultiSIMController.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                onChange(z);
                if (uri != null && uri.equals(Settings.Global.getUriFor("set_network_mode_by_quick_panel"))) {
                    boolean z2 = Settings.Global.getInt(MultiSIMController.this.mContext.getContentResolver(), "set_network_mode_by_quick_panel", 0) != 0;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onChanged() -set_network_mode_by_quick_panel : ", z2, "MultiSIMController");
                    if (z2) {
                        MultiSIMController multiSIMController = MultiSIMController.this;
                        multiSIMController.mData.changingNetMode = true;
                        HandlerC240412 handlerC240412 = multiSIMController.mUIHandler;
                        if (handlerC240412 != null) {
                            handlerC240412.removeMessages(1001);
                            HandlerC240412 handlerC2404122 = MultiSIMController.this.mUIHandler;
                            handlerC2404122.sendMessageDelayed(handlerC2404122.obtainMessage(1001), 1000L);
                        }
                    }
                    MultiSIMController.this.notifyDataToCallback();
                }
            }
        };
        this.mChangNetModeObserver = r6;
        ?? r8 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                ButtonType buttonType = ButtonType.VOICE;
                Uri uri = MultiSIMController.INTERNAL_URI;
                multiSIMController.updateCurrentDefaultSlot(buttonType);
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("PreferedVoiceObserver onChange():"), MultiSIMController.this.mData.defaultVoiceSimId, "MultiSIMController");
            }
        };
        this.mPreferedVoiceObserver = r8;
        ?? r9 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.isRestrictionsForMmsUse = multiSIMController.isRestrictionsForMmsUse();
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("mMultiSimDataCrossSlotObserver onChange() "), MultiSIMController.this.mData.isRestrictionsForMmsUse, "MultiSIMController");
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        this.mMultiSimDataCrossSlotObserver = r9;
        Uri[] uriArr = {Settings.System.getUriFor("airplane_mode_on")};
        ?? r11 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.settings.multisim.MultiSIMController.6
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor("airplane_mode_on"))) {
                    MultiSIMController multiSIMController = MultiSIMController.this;
                    multiSIMController.mData.airplaneMode = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAirplaneModeOn();
                    Log.d("MultiSIMController", "onChanged() - airplane_mode : " + multiSIMController.mData.airplaneMode);
                    multiSIMController.notifyDataToCallback();
                }
            }
        };
        this.mSettingsListener = r11;
        ?? r12 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.isDataEnabled = multiSIMController.isDataEnabled();
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        this.mMobileDataObserver = r12;
        ?? r13 = new ContentObserver((Handler) Dependency.get(dependencyKey)) { // from class: com.android.systemui.settings.multisim.MultiSIMController.8
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                onChange(z);
                if (uri == null) {
                    return;
                }
                if (uri.equals(Settings.Global.getUriFor("select_icon_1"))) {
                    MultiSIMController multiSIMController = MultiSIMController.this;
                    multiSIMController.mData.simImageIdx[0] = Settings.Global.getInt(multiSIMController.mContext.getContentResolver(), "select_icon_1", 0);
                    MultiSIMController multiSIMController2 = MultiSIMController.this;
                    int i = multiSIMController2.mData.simImageIdx[0];
                    if (i < 0 || i >= multiSIMController2.mMaxSimIconNumber) {
                        Log.e("MultiSIMController", "mSimIconAndNameObserver onChange() SimImageIdx[0] = " + MultiSIMController.this.mData.simImageIdx[0]);
                        MultiSIMController.this.mData.simImageIdx[0] = 0;
                    }
                } else if (uri.equals(Settings.Global.getUriFor("select_name_1"))) {
                    MultiSIMController multiSIMController3 = MultiSIMController.this;
                    multiSIMController3.mData.simName[0] = Settings.Global.getString(multiSIMController3.mContext.getContentResolver(), "select_name_1");
                } else if (uri.equals(Settings.Global.getUriFor("select_icon_2"))) {
                    MultiSIMController multiSIMController4 = MultiSIMController.this;
                    multiSIMController4.mData.simImageIdx[1] = Settings.Global.getInt(multiSIMController4.mContext.getContentResolver(), "select_icon_2", 1);
                    MultiSIMController multiSIMController5 = MultiSIMController.this;
                    int i2 = multiSIMController5.mData.simImageIdx[1];
                    if (i2 < 0 || i2 >= multiSIMController5.mMaxSimIconNumber) {
                        Log.e("MultiSIMController", "mSimIconAndNameObserver onChange() SimImageIdx[1] = " + MultiSIMController.this.mData.simImageIdx[1]);
                        MultiSIMController.this.mData.simImageIdx[1] = 1;
                    }
                } else if (uri.equals(Settings.Global.getUriFor("select_name_2"))) {
                    MultiSIMController multiSIMController6 = MultiSIMController.this;
                    multiSIMController6.mData.simName[1] = Settings.Global.getString(multiSIMController6.mContext.getContentResolver(), "select_name_2");
                }
                MultiSIMController multiSIMController7 = MultiSIMController.this;
                Uri uri2 = MultiSIMController.INTERNAL_URI;
                multiSIMController7.notifyDataToCallback();
            }
        };
        this.mSimIconAndNameObserver = r13;
        ?? r7 = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.MultiSIMController.9
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                HandlerC240412 handlerC240412;
                HandlerC240412 handlerC2404122;
                String action = intent.getAction();
                Log.d("MultiSIMController", "onReceive() - action = " + action);
                if (action.equals("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED")) {
                    MultiSIMController multiSIMController = MultiSIMController.this;
                    ButtonType buttonType = ButtonType.VOICE;
                    Uri uri = MultiSIMController.INTERNAL_URI;
                    multiSIMController.updateCurrentDefaultSlot(buttonType);
                } else {
                    if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onReceive() - subId = ", intent.getIntExtra("subscription", 0), "MultiSIMController");
                        MultiSIMController multiSIMController2 = MultiSIMController.this;
                        ButtonType buttonType2 = ButtonType.VOICE;
                        Uri uri2 = MultiSIMController.INTERNAL_URI;
                        multiSIMController2.updateCurrentDefaultSlot(buttonType2);
                    } else if (action.equals("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onReceive() - subId = ", intent.getIntExtra("subscription", 0), "MultiSIMController");
                        MultiSIMController multiSIMController3 = MultiSIMController.this;
                        ButtonType buttonType3 = ButtonType.SMS;
                        Uri uri3 = MultiSIMController.INTERNAL_URI;
                        multiSIMController3.updateCurrentDefaultSlot(buttonType3);
                    } else if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onReceive() - subId = ", intent.getIntExtra("subscription", 0), "MultiSIMController");
                        MultiSIMController multiSIMController4 = MultiSIMController.this;
                        ButtonType buttonType4 = ButtonType.DATA;
                        Uri uri4 = MultiSIMController.INTERNAL_URI;
                        multiSIMController4.updateCurrentDefaultSlot(buttonType4);
                    } else if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onReceive() - subId = ", intent.getIntExtra("subscription", 0), "MultiSIMController");
                        MultiSIMController multiSIMController5 = MultiSIMController.this;
                        if (multiSIMController5.mData.changingDataInternally && (handlerC2404122 = multiSIMController5.mUIHandler) != null) {
                            handlerC2404122.removeMessages(1000);
                            HandlerC240412 handlerC2404123 = MultiSIMController.this.mUIHandler;
                            handlerC2404123.sendMessageDelayed(handlerC2404123.obtainMessage(1000), 60000L);
                        }
                    } else if (action.equals("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH")) {
                        MultiSIMController multiSIMController6 = MultiSIMController.this;
                        if (multiSIMController6.mData.changingDataInternally && (handlerC240412 = multiSIMController6.mUIHandler) != null) {
                            handlerC240412.removeMessages(1000);
                            HandlerC240412 handlerC2404124 = MultiSIMController.this.mUIHandler;
                            handlerC2404124.sendMessage(handlerC2404124.obtainMessage(1000));
                        }
                    } else if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                        MultiSIMController multiSIMController7 = MultiSIMController.this;
                        Uri uri5 = MultiSIMController.INTERNAL_URI;
                        multiSIMController7.updateCarrierNameAndPhoneNumber(true);
                    } else if (action.equals("android.intent.action.SERVICE_STATE")) {
                        HandlerC240311 handlerC240311 = MultiSIMController.this.mUpdateDataHandler;
                        if (handlerC240311 != null) {
                            handlerC240311.removeMessages(2000);
                            HandlerC240311 handlerC2403112 = MultiSIMController.this.mUpdateDataHandler;
                            handlerC2403112.sendMessage(handlerC2403112.obtainMessage(2000));
                        }
                    } else if ("android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                        MultiSIMController multiSIMController8 = MultiSIMController.this;
                        Uri uri6 = MultiSIMController.INTERNAL_URI;
                        multiSIMController8.updateMultiSimReadyState(true);
                        String stringExtra = intent.getStringExtra(ImsProfile.SERVICE_SS);
                        if ("READY".equals(stringExtra) || "LOADED".equals(stringExtra)) {
                            MultiSIMController multiSIMController9 = MultiSIMController.this;
                            multiSIMController9.mData.isDataEnabled = multiSIMController9.isDataEnabled();
                            MultiSIMController.this.updateSimSlotType();
                        }
                        if ("LOADED".equals(stringExtra)) {
                            MultiSIMController.this.updateCurrentDefaultSlot(ButtonType.VOICE);
                            MultiSIMController.this.updateCurrentDefaultSlot(ButtonType.SMS);
                            MultiSIMController.this.updateCurrentDefaultSlot(ButtonType.DATA);
                        }
                    } else if (action.equals("com.samsung.settings.SIMCARD_MGT_ACTIVATED")) {
                        MultiSIMController multiSIMController10 = MultiSIMController.this;
                        Uri uri7 = MultiSIMController.INTERNAL_URI;
                        multiSIMController10.updateMultiSimReadyState(true);
                    } else if (action.equals("android.intent.action.PHONE_STATE")) {
                        String stringExtra2 = intent.getStringExtra("state");
                        if (!stringExtra2.isEmpty()) {
                            MultiSIMController.this.mData.isCalling = TelephonyManager.EXTRA_STATE_RINGING.equals(stringExtra2) || TelephonyManager.EXTRA_STATE_OFFHOOK.equals(stringExtra2);
                        }
                    } else if (action.equals("com.samsung.android.softsim.ServiceStatus")) {
                        MultiSIMController multiSIMController11 = MultiSIMController.this;
                        multiSIMController11.mData.isSRoaming = multiSIMController11.getSRoamingVirtualSlot() == 1;
                    }
                }
                MultiSIMController multiSIMController12 = MultiSIMController.this;
                Uri uri8 = MultiSIMController.INTERNAL_URI;
                multiSIMController12.notifyDataToCallback();
            }
        };
        this.mIntentReceiver = r7;
        this.mUpdateDataHandler = new Handler() { // from class: com.android.systemui.settings.multisim.MultiSIMController.11
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                switch (message.what) {
                    case 2000:
                        Log.d("MultiSIMController", "MESSAGE_UPDATE_SERVICE_STATE");
                        MultiSIMController multiSIMController = MultiSIMController.this;
                        multiSIMController.mData.isDataEnabled = multiSIMController.isDataEnabled();
                        MultiSIMController.this.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.CALL_FORBIDDEN /* 2001 */:
                        Log.d("MultiSIMController", "MESSAGE_UPDATE_SUBSCRIPTION_INFO");
                        MultiSIMController multiSIMController2 = MultiSIMController.this;
                        multiSIMController2.mIsSlotReversed = DeviceState.isSubInfoReversed(multiSIMController2.mContext);
                        MultiSIMController multiSIMController3 = MultiSIMController.this;
                        multiSIMController3.mNeedCheckOpportunisticESim = true;
                        multiSIMController3.updateMultiSimReadyState(false);
                        MultiSIMController multiSIMController4 = MultiSIMController.this;
                        multiSIMController4.mData.isDataEnabled = multiSIMController4.isDataEnabled();
                        MultiSIMController.this.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F /* 2002 */:
                        Log.d("MultiSIMController", "MESSAGE_IMS_MANAGER_CONNECTED");
                        MultiSIMController multiSIMController5 = MultiSIMController.this;
                        Uri uri = MultiSIMController.INTERNAL_URI;
                        multiSIMController5.updateCarrierNameAndPhoneNumber(false);
                        break;
                    default:
                        Log.d("MultiSIMController", "UpdateDataHandler MESSAGE_EMPTY");
                        break;
                }
                MultiSIMController multiSIMController6 = MultiSIMController.this;
                Uri uri2 = MultiSIMController.INTERNAL_URI;
                multiSIMController6.notifyDataToCallback();
            }
        };
        ?? r14 = new Handler() { // from class: com.android.systemui.settings.multisim.MultiSIMController.12
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i == 1000) {
                    Log.d("MultiSIMController", "MESSAGE_UPDATE_MULTISIM_PREFERRED_DATA_BUTTON");
                    MultiSIMController multiSIMController = MultiSIMController.this;
                    MultiSIMData multiSIMData = multiSIMController.mData;
                    if (multiSIMData.changingDataInternally) {
                        multiSIMData.changingDataInternally = false;
                        multiSIMController.notifyDataToCallback();
                        return;
                    }
                    return;
                }
                if (i != 1001) {
                    Log.d("MultiSIMController", "UIHandler MESSAGE_EMPTY");
                    return;
                }
                Log.d("MultiSIMController", "MESSAGE_UPDATE_SET_NETMODE_DELAY_STATE");
                MultiSIMController multiSIMController2 = MultiSIMController.this;
                MultiSIMData multiSIMData2 = multiSIMController2.mData;
                if (multiSIMData2.changingNetMode) {
                    multiSIMData2.changingNetMode = false;
                    multiSIMController2.notifyDataToCallback();
                }
            }
        };
        this.mUIHandler = r14;
        this.mNotifyVisToCallbackRunnable = new MultiSIMController$$ExternalSyntheticLambda1(this, 1);
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mSIMInfoIconManagerFactory = factory;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("com.samsung.settings.SIMCARD_MGT_ACTIVATED");
        intentFilter.addAction("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS");
        intentFilter.addAction("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("com.samsung.android.softsim.ServiceStatus");
        intentFilter.addAction(EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, r7);
        this.mSimCardManagerService = SimCardManagerServiceProvider.getService(context);
        if (networkController != null) {
            this.mDataController = ((NetworkControllerImpl) networkController).mDataUsageController;
        }
        this.mDataNotified = new MultiSIMData();
        this.mData = new MultiSIMData();
        this.mInitDone = false;
        updateCurrentDefaultSlot(ButtonType.VOICE);
        updateCurrentDefaultSlot(ButtonType.SMS);
        updateCurrentDefaultSlot(ButtonType.DATA);
        this.mMaxSimIconNumber = context.getResources().getStringArray(R.array.multisim_psim_icon_res_id_list).length;
        this.mData.simImageIdx[0] = Settings.Global.getInt(context.getContentResolver(), "select_icon_1", 0);
        int i = this.mData.simImageIdx[0];
        if (i < 0 || i >= this.mMaxSimIconNumber) {
            StringBuilder sb = new StringBuilder("MultiSIMPreferredSlotBar SimImageIdx[0] = ");
            c = 0;
            sb.append(this.mData.simImageIdx[0]);
            Log.e("MultiSIMController", sb.toString());
            this.mData.simImageIdx[0] = 0;
        } else {
            c = 0;
        }
        this.mData.simName[c] = Settings.Global.getString(context.getContentResolver(), "select_name_1");
        this.mData.simImageIdx[1] = Settings.Global.getInt(context.getContentResolver(), "select_icon_2", 1);
        int i2 = this.mData.simImageIdx[1];
        if (i2 < 0 || i2 >= this.mMaxSimIconNumber) {
            StringBuilder sb2 = new StringBuilder("MultiSIMPreferredSlotBar SimImageIdx[1] = ");
            c2 = 1;
            sb2.append(this.mData.simImageIdx[1]);
            Log.e("MultiSIMController", sb2.toString());
            this.mData.simImageIdx[1] = 1;
        } else {
            c2 = 1;
        }
        this.mData.simName[c2] = Settings.Global.getString(context.getContentResolver(), "select_name_2");
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_icon_1"), false, r13);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_name_1"), false, r13);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_icon_2"), false, r13);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_name_2"), false, r13);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("prefered_voice_call"), false, r8);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data"), false, r12);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, r12);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("multi_sim_datacross_slot"), false, r9);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("set_network_mode_by_quick_panel"), false, r6);
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(r11, uriArr);
        r11.onChanged(uriArr[0]);
        if (getSRoamingVirtualSlot() == 1) {
            this.mData.isSRoaming = true;
        }
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        SubscriptionManager.from(context).addOnSubscriptionsChangedListener(r5);
        updateSimSlotType();
        this.mData.isRestrictionsForMmsUse = isRestrictionsForMmsUse();
        updateMultiSimReadyState(true);
        MultiSIMData multiSIMData = this.mData;
        int callState = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(0));
        int callState2 = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(1));
        Log.i("MultiSIMController", "Check Call SIM1 : " + callState + ", SIM2 : " + callState2);
        multiSIMData.isCalling = callState == 1 || callState == 2 || callState2 == 1 || callState2 == 2;
        this.mData.isDataEnabled = isDataEnabled();
        this.mData.airplaneMode = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAirplaneModeOn();
        updateCarrierNameAndPhoneNumber(true);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, new HandlerExecutor((Handler) r14));
        this.mData.isSecondaryUser = userTrackerImpl.getUserId() != 0;
        this.mIsSlotReversed = DeviceState.isSubInfoReversed(context);
        this.mInitDone = true;
        notifyDataToCallback();
    }

    public static int getSRoamingStatus(String str) {
        if (str.equals("activating") || str.equals("activated")) {
            return 1;
        }
        return (str.equals("deactivating") || str.equals("deactivated")) ? 0 : 9;
    }

    public static int getSubId(int i) {
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId != null && subId.length > 0) {
            return subId[0];
        }
        Log.e("MultiSIMController", "getSubId: no valid subs");
        return -1;
    }

    public static boolean isBlockedAllMultiSimBar() {
        return (Operator.QUICK_IS_XNX_BRANDING && SystemProperties.get("ril.lockpolicy", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).equals("1")) || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBlockedEdmSettingsChange$1() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isUserMobileDataRestricted();
    }

    public final int getCurrentVoiceSlotByMethodCall() {
        try {
            Bundle call = this.mContext.getContentResolver().call(INTERNAL_URI, "getCurrentVoiceCall", (String) null, new Bundle());
            if (call == null) {
                Log.d("MultiSIMController", "bundle is null : getCurrentVoiceCall");
                return 0;
            }
            boolean z = call.getBoolean("success");
            int i = call.getInt("result");
            Log.d("MultiSIMController", "getCurrentVoiceCall, " + z + ", " + i);
            return i;
        } catch (Throwable th) {
            Log.e("MultiSIMController", "getCurrentVoiceCall, " + th);
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getSRoamingVirtualSlot() {
        boolean z;
        int i = 0;
        Context context = this.mContext;
        if (context != null) {
            try {
                context.getPackageManager().getApplicationInfo("com.samsung.android.globalroaming", 128);
                z = true;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("MultiSIMController", "Package not found : com.samsung.android.globalroaming");
            }
            if (z) {
                return 9;
            }
            Log.i("MultiSIMController", "has sroaming package");
            String mSimSystemProperty = DeviceState.getMSimSystemProperty(0, "persist.sys.softsim.status", "default");
            String mSimSystemProperty2 = DeviceState.getMSimSystemProperty(1, "persist.sys.softsim.status", "default");
            int sRoamingStatus = getSRoamingStatus(mSimSystemProperty);
            int sRoamingStatus2 = getSRoamingStatus(mSimSystemProperty2);
            if (sRoamingStatus == 1 || sRoamingStatus2 == 1) {
                i = 1;
            } else if (sRoamingStatus != 0 || sRoamingStatus2 != 0) {
                i = 9;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("sroaming status : ", i, "MultiSIMController");
            return i;
        }
        Log.d("MultiSIMController", "context is null : com.samsung.android.globalroaming");
        z = false;
        if (z) {
        }
    }

    public final boolean isDataBlocked(int i) {
        if (this.mIsSlotReversed) {
            i = 1 - i;
        }
        SimCardManagerServiceProvider simCardManagerServiceProvider = this.mSimCardManagerService;
        boolean z = false;
        Context context = this.mContext;
        if (simCardManagerServiceProvider != null && SimCardManagerServiceProvider.isServiceRunningCheck(context)) {
            try {
                return !this.mSimCardManagerService.isDefaultDataSlotAllowed(i);
            } catch (Exception e) {
                Log.d("MultiSIMController", "Caught exception from isDataBlocked", e);
                return false;
            }
        }
        if (this.mSimCardManagerService == null) {
            Log.e("MultiSIMController", "isDataBlocked : mSimCardManagerService is null.");
            return false;
        }
        Log.e("MultiSIMController", "isDataBlocked : isDefaultDataSlotAllowedByMethodCall");
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("selectItem", i);
            Bundle call = context.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
            if (call == null) {
                Log.d("MultiSIMController", "bundle is null : isDefaultDataSlotAllowed");
            } else {
                boolean z2 = call.getBoolean("success");
                boolean z3 = call.getBoolean("result");
                Log.d("MultiSIMController", "isDefaultDataSlotAllowed, " + z2 + ", " + z3);
                z = z3;
            }
        } catch (Throwable th) {
            Log.e("MultiSIMController", "isDefaultDataSlotAllowed, " + th);
        }
        boolean z4 = !z;
        this.mSimCardManagerService = SimCardManagerServiceProvider.getService(context);
        return z4;
    }

    public final boolean isDataEnabled() {
        DataUsageController dataUsageController = this.mDataController;
        return dataUsageController != null && dataUsageController.isMobileDataSupported() && this.mDataController.isMobileDataEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMultiSimAvailable() {
        String str;
        boolean z;
        if (!this.mData.isMultiSimReady) {
            return false;
        }
        if (!DeviceType.isLDUSKU()) {
            try {
                str = SystemProperties.get("ro.csc.sales_code");
                try {
                    if (TextUtils.isEmpty(str)) {
                        str = SystemProperties.get("ril.sales_code");
                    }
                } catch (Exception unused) {
                    Log.d("TAG", "readSalesCode failed");
                    if (!"PAP".equals(str)) {
                        z = false;
                        ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("isLDUModel = ", z, " isSecondaryUser = "), this.mData.isSecondaryUser, "MultiSIMController");
                        if (!z || this.mData.isSecondaryUser) {
                        }
                    }
                    z = true;
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("isLDUModel = ", z, " isSecondaryUser = "), this.mData.isSecondaryUser, "MultiSIMController");
                    if (!z || this.mData.isSecondaryUser) {
                    }
                }
            } catch (Exception unused2) {
                str = "";
            }
            if (!"PAP".equals(str) && !"FOP".equals(str) && !"LDU".equals(str)) {
                z = false;
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("isLDUModel = ", z, " isSecondaryUser = "), this.mData.isSecondaryUser, "MultiSIMController");
                return ((!z || this.mData.isSecondaryUser) || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isEmergencyMode()) ? false : true;
            }
        }
        z = true;
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("isLDUModel = ", z, " isSecondaryUser = "), this.mData.isSecondaryUser, "MultiSIMController");
        if (!z || this.mData.isSecondaryUser) {
            return false;
        }
    }

    public final boolean isRestrictionsForMmsUse() {
        int i = DeviceType.supportTablet;
        if ("qcom".equalsIgnoreCase(SemSystemProperties.get("ro.hardware", "")) || Build.VERSION.SEM_FIRST_SDK_INT >= 31) {
            return false;
        }
        int i2 = Settings.Global.getInt(this.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("isMMSuse =", i2, "MultiSIMController");
        return i2 != -1;
    }

    public final void launchSimManager() {
        if (isBlockedAllMultiSimBar()) {
            return;
        }
        Intent intent = new Intent();
        Log.e("MultiSIMController", "onClick()");
        try {
            intent.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.SimCardMgrActivity");
            intent.addFlags(268468224);
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } catch (ActivityNotFoundException unused) {
            Log.e("MultiSIMController", "activity not found");
        }
    }

    public final void notifyDataToCallback() {
        if (this.mInitDone) {
            HandlerC240412 handlerC240412 = this.mUIHandler;
            MultiSIMController$$ExternalSyntheticLambda1 multiSIMController$$ExternalSyntheticLambda1 = this.mNotifyDataToCallbackRunnable;
            handlerC240412.removeCallbacks(multiSIMController$$ExternalSyntheticLambda1);
            handlerC240412.post(multiSIMController$$ExternalSyntheticLambda1);
        }
    }

    public final void registerCallback(MultiSIMDataChangedCallback multiSIMDataChangedCallback) {
        if (multiSIMDataChangedCallback == null) {
            return;
        }
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mDataCallbacks;
            if (i >= arrayList.size()) {
                arrayList.add(new WeakReference(multiSIMDataChangedCallback));
                arrayList.removeIf(new MultiSIMController$$ExternalSyntheticLambda0(null, 0));
                return;
            } else if (((WeakReference) arrayList.get(i)).get() == multiSIMDataChangedCallback) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void reverseSlotIfNeed(MultiSIMData multiSIMData) {
        if (this.mIsSlotReversed) {
            multiSIMData.defaultDataSimId = 1 - multiSIMData.defaultDataSimId;
            int i = multiSIMData.defaultVoiceSimId;
            if (i == 1 || i == 2) {
                multiSIMData.defaultVoiceSimId = 3 - i;
            }
            multiSIMData.defaultSmsSimId = 1 - multiSIMData.defaultSmsSimId;
            int[] iArr = multiSIMData.simImageIdx;
            int i2 = iArr[0];
            String[] strArr = multiSIMData.simName;
            String str = strArr[0];
            String[] strArr2 = multiSIMData.phoneNumber;
            String str2 = strArr2[0];
            String[] strArr3 = multiSIMData.carrierName;
            String str3 = strArr3[0];
            boolean[] zArr = multiSIMData.isESimSlot;
            boolean z = zArr[0];
            iArr[0] = iArr[1];
            strArr[0] = strArr[1];
            strArr2[0] = strArr2[1];
            strArr3[0] = strArr3[1];
            zArr[0] = zArr[1];
            iArr[1] = i2;
            strArr[1] = str;
            strArr2[1] = str2;
            strArr3[1] = str3;
            zArr[1] = z;
        }
    }

    public final void setDefaultSlot(ButtonType buttonType, int i) {
        int i2 = i;
        ButtonType buttonType2 = ButtonType.VOICE;
        if (buttonType != buttonType2) {
            if (this.mIsSlotReversed) {
                i2 = 1 - i2;
            }
        } else if ((i2 == 1 || i2 == 2) && this.mIsSlotReversed) {
            i2 = 3 - i2;
        }
        Log.e("MultiSIMController", "setDefaultSlot : type = " + buttonType + ", slotId = " + i2);
        ButtonType buttonType3 = ButtonType.DATA;
        if (buttonType == buttonType3) {
            MultiSIMData multiSIMData = this.mData;
            if (i2 != multiSIMData.defaultDataSimId) {
                multiSIMData.changingDataInternally = true;
            }
        }
        Context context = this.mContext;
        String str = "PREFERRED_MOBILE_DATA";
        if (SimCardManagerServiceProvider.isServiceRunningCheck(context)) {
            int index = buttonType.getIndex();
            try {
                Bundle bundle = new Bundle();
                if (index == 0) {
                    bundle.putString("changeType", "PREFERRED_VOICE_CALLS");
                } else if (index == 1) {
                    bundle.putString("changeType", "PREFERRED_TEXT_MESSAGES");
                } else if (index == 2) {
                    bundle.putString("changeType", "PREFERRED_MOBILE_DATA");
                    SimCardManagerServiceProvider.mIsRemainCallbackCall = true;
                }
                Log.d("SimCardManagerServiceProvider", "setChangeSimCardManagerSlot : mIsRemainCallbackCall = " + SimCardManagerServiceProvider.mIsRemainCallbackCall);
                bundle.putInt("selectItem", i2);
                Bundle call = SimCardManagerServiceProvider.mContext.getContentResolver().call(SimCardManagerServiceProvider.INTERNAL_URI, "quickpanel_simcard_change", (String) null, bundle);
                if (call == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : quickpanel_simcard_change");
                    return;
                } else {
                    call.getBoolean("success");
                    return;
                }
            } catch (Throwable th) {
                Log.e("SimCardManagerServiceProvider", th.toString());
                return;
            }
        }
        Log.e("MultiSIMController", "setDefaultSlotByMethodCall");
        if (buttonType == buttonType2) {
            str = "PREFERRED_VOICE_CALLS";
        } else if (buttonType == ButtonType.SMS) {
            str = "PREFERRED_TEXT_MESSAGES";
        } else if (buttonType != buttonType3) {
            str = null;
        }
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putString("changeType", str);
            bundle2.putInt("selectItem", i2);
            Bundle call2 = context.getContentResolver().call(INTERNAL_URI, "quickpanel_simcard_change", (String) null, bundle2);
            if (call2 == null) {
                Log.d("MultiSIMController", "bundle is null : quickpanel_simcard_change");
            } else {
                Log.d("MultiSIMController", "quickpanel_simcard_change, " + call2.getBoolean("success") + ", " + ((Throwable) call2.getParcelable("error")));
            }
        } catch (Throwable th2) {
            Log.e("MultiSIMController", "quickpanel_simcard_change, " + th2);
        }
        this.mSimCardManagerService = SimCardManagerServiceProvider.getService(context);
    }

    public final void updateCarrierNameAndPhoneNumber(boolean z) {
        Context context = this.mContext;
        if (z) {
            this.mNetworkNameDefault = context.getString(android.R.string.permdesc_accessWimaxState);
            this.mUnknownPhoneNumber = context.getString(R.string.qs_multisim_unknown_number);
            this.mInvalidSimInfo = context.getString(R.string.qs_multisim_invalid_sim_info);
        }
        for (int i = 0; i < 2; i++) {
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = SubscriptionManager.from(context).getActiveSubscriptionInfoForSimSlotIndex(i);
            if (activeSubscriptionInfoForSimSlotIndex != null) {
                this.mData.carrierName[i] = (activeSubscriptionInfoForSimSlotIndex.getCarrierName() == null || activeSubscriptionInfoForSimSlotIndex.getCarrierName().length() <= 0) ? this.mNetworkNameDefault : activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
            }
        }
        updatePhoneNumberWhenNeeded();
    }

    public final void updateCurrentDefaultSlot(ButtonType buttonType) {
        if (!this.mUIVisible) {
            ArrayList arrayList = this.mDefaultIdUpdateList;
            if (!arrayList.contains(buttonType)) {
                arrayList.add(buttonType);
            }
            Log.d("MultiSIMController", "updateCurrentDefaultSlot later type = " + buttonType);
            return;
        }
        int i = AbstractC240614.f339x9f70d468[buttonType.ordinal()];
        boolean z = true;
        if (i == 1) {
            try {
                if (this.mSimCardManagerService == null || !SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
                    z = false;
                }
                Boolean valueOf = Boolean.valueOf(z);
                this.mData.defaultVoiceSimId = valueOf.booleanValue() ? this.mSimCardManagerService.GetCurrentVoiceCall() : getCurrentVoiceSlotByMethodCall();
                Log.d("MultiSIMController", "updateCurrentDefaultSlot : voice = " + this.mData.defaultVoiceSimId + " " + valueOf);
            } catch (Exception e) {
                Log.e("MultiSIMController", "Caught exception from updateCurrentDefaultSlot", e);
            }
        } else if (i == 2) {
            this.mData.defaultSmsSimId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultSmsSubscriptionId());
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("updateCurrentDefaultSlot : sms = "), this.mData.defaultSmsSimId, "MultiSIMController");
        } else if (i == 3) {
            this.mData.defaultDataSimId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("updateCurrentDefaultSlot : data = "), this.mData.defaultDataSimId, "MultiSIMController");
        }
        notifyDataToCallback();
    }

    public final void updateMultiSimReadyState(boolean z) {
        Context context = this.mContext;
        if (z) {
            int i = 0;
            int i2 = 0;
            while (i < DeviceState.sPhoneCount) {
                if ("LOADED".equals(DeviceState.getMSimSystemProperty(i, "gsm.sim.state", "NOT_READY"))) {
                    if ((i == 0 ? Settings.Global.getInt(context.getContentResolver(), "phone1_on", 1) : Settings.Global.getInt(context.getContentResolver(), "phone2_on", 1)) != 0) {
                        i2++;
                    }
                }
                i++;
            }
            this.mIsLoadedMultiSim = i2 == 2;
        }
        if (this.mIsLoadedMultiSim && this.mNeedCheckOpportunisticESim) {
            this.mHasOpportunisticESim = false;
            List<SubscriptionInfo> completeActiveSubscriptionInfoList = SubscriptionManager.from(context).getCompleteActiveSubscriptionInfoList();
            if (completeActiveSubscriptionInfoList.size() == 2) {
                SubscriptionInfo subscriptionInfo = completeActiveSubscriptionInfoList.get(0);
                SubscriptionInfo subscriptionInfo2 = completeActiveSubscriptionInfoList.get(1);
                if (subscriptionInfo.getGroupUuid() != null && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid()) && (subscriptionInfo.isOpportunistic() || subscriptionInfo2.isOpportunistic())) {
                    this.mHasOpportunisticESim = true;
                }
            }
            this.mNeedCheckOpportunisticESim = false;
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateMultiSimReadyState: mHasOpportunisticESim = "), this.mHasOpportunisticESim, "MultiSIMController");
        }
        this.mData.isMultiSimReady = this.mIsLoadedMultiSim && !this.mHasOpportunisticESim;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ab A[EDGE_INSN: B:24:0x00ab->B:25:0x00ab BREAK  A[LOOP:2: B:15:0x0055->B:56:0x00a8], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a8 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePhoneNumberWhenNeeded() {
        boolean z;
        boolean z2;
        String phoneNumber;
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mDataCallbacks;
            if (i >= arrayList.size()) {
                z = false;
                break;
            }
            MultiSIMDataChangedCallback multiSIMDataChangedCallback = (MultiSIMDataChangedCallback) ((WeakReference) arrayList.get(i)).get();
            if (multiSIMDataChangedCallback != null && multiSIMDataChangedCallback.isPhoneNumberNeeded()) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            for (int i2 = 0; i2 < 2; i2++) {
                String simOperatorNumericForPhone = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getSimOperatorNumericForPhone(i2);
                boolean z3 = Operator.QUICK_IS_VZW_BRANDING;
                String string = SemCarrierFeature.getInstance().getString(i2, "CarrierFeature_RIL_DisablePhoneNumberSource", "", false);
                this.mData.phoneNumber[i2] = "";
                for (PhoneNumberSource phoneNumberSource : PhoneNumberSource.values()) {
                    if (!string.contains(phoneNumberSource.name())) {
                        String[] strArr = this.mData.phoneNumber;
                        int subId = getSubId(i2);
                        int value = phoneNumberSource.getValue();
                        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
                        if (subscriptionManager != null) {
                            try {
                                phoneNumber = subscriptionManager.getPhoneNumber(subId, value);
                            } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                                Log.e("MultiSIMController", "failed to get SubscriptionManager.getPhoneNumber: " + e.getMessage());
                            }
                            strArr[i2] = phoneNumber;
                            if (TextUtils.isEmpty(this.mData.phoneNumber[i2])) {
                                break;
                            }
                        }
                        phoneNumber = null;
                        strArr[i2] = phoneNumber;
                        if (TextUtils.isEmpty(this.mData.phoneNumber[i2])) {
                        }
                    }
                }
                if (TextUtils.isEmpty(this.mData.phoneNumber[i2])) {
                    if ("AIS".equals(SystemProperties.get("ro.csc.sales_code", "unknown"))) {
                        String mSimSystemProperty = DeviceState.getMSimSystemProperty(0, "gsm.sim.state", "NOT_READY");
                        String mSimSystemProperty2 = DeviceState.getMSimSystemProperty(0, "gsm.sim.state", "NOT_READY");
                        if (i2 == 0) {
                            if (mSimSystemProperty.equals("NETWORK_LOCKED")) {
                                Log.d("MultiSIMController", "sim1 Network Lock!!");
                                z2 = true;
                            }
                        } else if (i2 == 1 && mSimSystemProperty2.equals("NETWORK_LOCKED")) {
                            Log.d("MultiSIMController", "sim2 Network Lock!!");
                            z2 = true;
                        }
                        if (z2) {
                            this.mData.phoneNumber[i2] = this.mUnknownPhoneNumber;
                        } else {
                            this.mData.phoneNumber[i2] = this.mInvalidSimInfo;
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                if (!TextUtils.isEmpty(this.mData.phoneNumber[i2]) && Arrays.stream(KoreanSimCarrier.values()).map(new MultiSIMController$$ExternalSyntheticLambda2()).anyMatch(new MultiSIMController$$ExternalSyntheticLambda0(simOperatorNumericForPhone, 2)) && this.mData.phoneNumber[i2].startsWith("+82")) {
                    this.mData.phoneNumber[i2] = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN + this.mData.phoneNumber[i2].substring(3);
                }
            }
            notifyDataToCallback();
        }
    }

    public final void updateSimSlotType() {
        int i = 0;
        while (true) {
            boolean[] zArr = this.mData.isESimSlot;
            if (i >= zArr.length) {
                return;
            }
            zArr[i] = DeviceState.isESIM(i, this.mContext);
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("updateSimSlotType() - sim slot ", i, " is eSim: ");
            m1m.append(this.mData.isESimSlot[i]);
            Log.d("MultiSIMController", m1m.toString());
            i++;
        }
    }
}
