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
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.knox.EdmMonitor;
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
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.ims.SemImsManager;
import com.samsung.android.ims.SemImsRegistrationListener;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MultiSIMController {
    public static final Uri INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    public final ActivityStarter mActivityStarter;
    public final AnonymousClass6 mAirplaneModeObserver;
    public final AnonymousClass3 mChangNetModeObserver;
    public final Context mContext;
    public MultiSIMData mData;
    public final DataUsageController mDataController;
    public final MultiSIMData mDataNotified;
    public final boolean mInitDone;
    public final AnonymousClass10 mIntentReceiver;
    public String mInvalidSimInfo;
    public boolean mIsSlotReversed;
    public final int mMaxSimIconNumber;
    public final AnonymousClass7 mMobileDataObserver;
    public final AnonymousClass5 mMultiSimDataCrossSlotObserver;
    public String mNetworkNameDefault;
    public final MultiSIMController$$ExternalSyntheticLambda6 mNotifyDataToCallbackRunnable;
    public final MultiSIMController$$ExternalSyntheticLambda6 mNotifyVisToCallbackRunnable;
    public final AnonymousClass2 mOnSubscriptionsChangeListener;
    public final AnonymousClass4 mPreferedVoiceObserver;
    public final MultiSIMPreferredSlotView.SIMInfoIconManager.Factory mSIMInfoIconManagerFactory;
    public final AnonymousClass9 mSatelliteModeObserver;
    public SimCardManagerServiceProvider mSimCardManagerService;
    public final AnonymousClass8 mSimIconAndNameObserver;
    public final AnonymousClass13 mUIHandler;
    public String mUnknownPhoneNumber;
    public final AnonymousClass12 mUpdateDataHandler;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    public AnonymousClass14 mSimCardCallback = null;
    public boolean mUIVisible = false;
    public final ArrayList mDataCallbacks = Lists.newArrayList();
    public final ArrayList mVisCallbacks = Lists.newArrayList();
    public final ArrayList mDefaultIdUpdateList = new ArrayList();
    public boolean mIsLoadedMultiSim = false;
    public boolean mHasOpportunisticESim = false;
    public boolean mNeedCheckOpportunisticESim = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.settings.multisim.MultiSIMController$14, reason: invalid class name */
    public final class AnonymousClass14 {
        public AnonymousClass14() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MultiSIMDataChangedCallback {
        default boolean isPhoneNumberNeeded() {
            return false;
        }

        void onDataChanged(MultiSIMData multiSIMData);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MultiSIMVisibilityChangedCallback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r15v0, types: [com.android.systemui.settings.multisim.MultiSIMController$12] */
    /* JADX WARN: Type inference failed for: r15v1, types: [android.os.Handler, com.android.systemui.settings.multisim.MultiSIMController$13] */
    public MultiSIMController(Context context, NetworkController networkController, UserTracker userTracker, MultiSIMPreferredSlotView.SIMInfoIconManager.Factory factory) {
        char c;
        char c2;
        boolean z;
        boolean z2;
        SemImsRegistrationListener[] semImsRegistrationListenerArr = new SemImsRegistrationListener[2];
        SemImsManager[] semImsManagerArr = new SemImsManager[2];
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
        this.mNotifyDataToCallbackRunnable = new MultiSIMController$$ExternalSyntheticLambda6(this, 1);
        SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.systemui.settings.multisim.MultiSIMController.2
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                Log.d("MultiSIMController", "onSubscriptionsChanged: ");
                AnonymousClass12 anonymousClass12 = MultiSIMController.this.mUpdateDataHandler;
                if (anonymousClass12 != null) {
                    anonymousClass12.removeMessages(VolteConstants.ErrorCode.CALL_FORBIDDEN);
                    AnonymousClass12 anonymousClass122 = MultiSIMController.this.mUpdateDataHandler;
                    anonymousClass122.sendMessage(anonymousClass122.obtainMessage(VolteConstants.ErrorCode.CALL_FORBIDDEN));
                }
            }
        };
        Dependency.DependencyKey dependencyKey = Dependency.MAIN_HANDLER;
        ContentObserver contentObserver = new ContentObserver((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)) { // from class: com.android.systemui.settings.multisim.MultiSIMController.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3, Uri uri) {
                onChange(z3);
                if (uri != null && uri.equals(Settings.Global.getUriFor("set_network_mode_by_quick_panel"))) {
                    boolean z4 = Settings.Global.getInt(MultiSIMController.this.mContext.getContentResolver(), "set_network_mode_by_quick_panel", 0) != 0;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onChanged() -set_network_mode_by_quick_panel : ", "MultiSIMController", z4);
                    if (z4) {
                        MultiSIMController multiSIMController = MultiSIMController.this;
                        multiSIMController.mData.changingNetMode = true;
                        AnonymousClass13 anonymousClass13 = multiSIMController.mUIHandler;
                        if (anonymousClass13 != null) {
                            anonymousClass13.removeMessages(1001);
                            AnonymousClass13 anonymousClass132 = MultiSIMController.this.mUIHandler;
                            anonymousClass132.sendMessageDelayed(anonymousClass132.obtainMessage(1001), 1000L);
                        }
                    }
                    MultiSIMController.this.notifyDataToCallback();
                }
            }
        };
        ContentObserver contentObserver2 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                ButtonType buttonType = ButtonType.VOICE;
                Uri uri = MultiSIMController.INTERNAL_URI;
                multiSIMController.updateCurrentDefaultSlot(buttonType);
                RecyclerView$$ExternalSyntheticOutline0.m(MultiSIMController.this.mData.defaultVoiceSimId, "MultiSIMController", new StringBuilder("PreferedVoiceObserver onChange():"));
            }
        };
        ContentObserver contentObserver3 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.isRestrictionsForMmsUse = multiSIMController.isRestrictionsForMmsUse();
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("mMultiSimDataCrossSlotObserver onChange() "), MultiSIMController.this.mData.isRestrictionsForMmsUse, "MultiSIMController");
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        ContentObserver contentObserver4 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.6
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.airplaneMode = Settings.Global.getInt(multiSIMController.mContext.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged() - airplane_mode : "), MultiSIMController.this.mData.airplaneMode, "MultiSIMController");
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        ContentObserver contentObserver5 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.7
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.isDataEnabled = multiSIMController.isDataEnabled();
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        ContentObserver contentObserver6 = new ContentObserver((Handler) Dependency.sDependency.getDependencyInner(dependencyKey)) { // from class: com.android.systemui.settings.multisim.MultiSIMController.8
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3, Uri uri) {
                onChange(z3);
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
                } else if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_1))) {
                    MultiSIMController multiSIMController3 = MultiSIMController.this;
                    multiSIMController3.mData.simName[0] = Settings.Global.getString(multiSIMController3.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
                } else if (uri.equals(Settings.Global.getUriFor("select_icon_2"))) {
                    MultiSIMController multiSIMController4 = MultiSIMController.this;
                    multiSIMController4.mData.simImageIdx[1] = Settings.Global.getInt(multiSIMController4.mContext.getContentResolver(), "select_icon_2", 1);
                    MultiSIMController multiSIMController5 = MultiSIMController.this;
                    int i2 = multiSIMController5.mData.simImageIdx[1];
                    if (i2 < 0 || i2 >= multiSIMController5.mMaxSimIconNumber) {
                        Log.e("MultiSIMController", "mSimIconAndNameObserver onChange() SimImageIdx[1] = " + MultiSIMController.this.mData.simImageIdx[1]);
                        MultiSIMController.this.mData.simImageIdx[1] = 1;
                    }
                } else if (uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_2))) {
                    MultiSIMController multiSIMController6 = MultiSIMController.this;
                    multiSIMController6.mData.simName[1] = Settings.Global.getString(multiSIMController6.mContext.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
                }
                MultiSIMController multiSIMController7 = MultiSIMController.this;
                Uri uri2 = MultiSIMController.INTERNAL_URI;
                multiSIMController7.notifyDataToCallback();
            }
        };
        ContentObserver contentObserver7 = new ContentObserver(new Handler()) { // from class: com.android.systemui.settings.multisim.MultiSIMController.9
            @Override // android.database.ContentObserver
            public final void onChange(boolean z3) {
                MultiSIMController multiSIMController = MultiSIMController.this;
                multiSIMController.mData.isSatelliteMode = Settings.Global.getInt(multiSIMController.mContext.getContentResolver(), SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED, 0) == 1;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged() - isSatelliteMode : "), MultiSIMController.this.mData.isSatelliteMode, "MultiSIMController");
                MultiSIMController.this.notifyDataToCallback();
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.settings.multisim.MultiSIMController.10
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                AnonymousClass13 anonymousClass13;
                AnonymousClass13 anonymousClass132;
                String action = intent.getAction();
                Log.d("MultiSIMController", "onReceive() - action = " + action);
                if (action.equals("com.samsung.telecom.action.DEFAULT_OUTGOING_PHONE_ACCOUNT_CHANGED")) {
                    MultiSIMController multiSIMController = MultiSIMController.this;
                    ButtonType buttonType = ButtonType.VOICE;
                    Uri uri = MultiSIMController.INTERNAL_URI;
                    multiSIMController.updateCurrentDefaultSlot(buttonType);
                } else {
                    if (action.equals("android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MultiSIMController");
                        MultiSIMController multiSIMController2 = MultiSIMController.this;
                        ButtonType buttonType2 = ButtonType.VOICE;
                        Uri uri2 = MultiSIMController.INTERNAL_URI;
                        multiSIMController2.updateCurrentDefaultSlot(buttonType2);
                    } else if (action.equals("android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MultiSIMController");
                        MultiSIMController multiSIMController3 = MultiSIMController.this;
                        ButtonType buttonType3 = ButtonType.SMS;
                        Uri uri3 = MultiSIMController.INTERNAL_URI;
                        multiSIMController3.updateCurrentDefaultSlot(buttonType3);
                    } else if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MultiSIMController");
                        MultiSIMController multiSIMController4 = MultiSIMController.this;
                        ButtonType buttonType4 = ButtonType.DATA;
                        Uri uri4 = MultiSIMController.INTERNAL_URI;
                        multiSIMController4.updateCurrentDefaultSlot(buttonType4);
                    } else if (action.equals("com.samsung.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGE_SUCCESS")) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(intent.getIntExtra("subscription", 0), "onReceive() - subId = ", "MultiSIMController");
                        MultiSIMController multiSIMController5 = MultiSIMController.this;
                        if (multiSIMController5.mData.changingDataInternally && (anonymousClass132 = multiSIMController5.mUIHandler) != null) {
                            anonymousClass132.removeMessages(1000);
                            AnonymousClass13 anonymousClass133 = MultiSIMController.this.mUIHandler;
                            anonymousClass133.sendMessageDelayed(anonymousClass133.obtainMessage(1000), 60000L);
                        }
                    } else if (action.equals("android.samsung.action.ACTION_NETWORK_SLOT_CHANGING_FINISH")) {
                        MultiSIMController multiSIMController6 = MultiSIMController.this;
                        if (multiSIMController6.mData.changingDataInternally && (anonymousClass13 = multiSIMController6.mUIHandler) != null) {
                            anonymousClass13.removeMessages(1000);
                            AnonymousClass13 anonymousClass134 = MultiSIMController.this.mUIHandler;
                            anonymousClass134.sendMessage(anonymousClass134.obtainMessage(1000));
                        }
                    } else if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                        MultiSIMController multiSIMController7 = MultiSIMController.this;
                        Uri uri5 = MultiSIMController.INTERNAL_URI;
                        multiSIMController7.updateCarrierNameAndPhoneNumber(true);
                    } else if (action.equals("android.intent.action.SERVICE_STATE")) {
                        AnonymousClass12 anonymousClass12 = MultiSIMController.this.mUpdateDataHandler;
                        if (anonymousClass12 != null) {
                            anonymousClass12.removeMessages(2000);
                            AnonymousClass12 anonymousClass122 = MultiSIMController.this.mUpdateDataHandler;
                            anonymousClass122.sendMessage(anonymousClass122.obtainMessage(2000));
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
        this.mUpdateDataHandler = new Handler() { // from class: com.android.systemui.settings.multisim.MultiSIMController.12
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                MultiSIMController multiSIMController = MultiSIMController.this;
                switch (i) {
                    case 2000:
                        Log.d("MultiSIMController", "MESSAGE_UPDATE_SERVICE_STATE");
                        multiSIMController.mData.isDataEnabled = multiSIMController.isDataEnabled();
                        multiSIMController.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.CALL_FORBIDDEN /* 2001 */:
                        Log.d("MultiSIMController", "MESSAGE_UPDATE_SUBSCRIPTION_INFO");
                        multiSIMController.mIsSlotReversed = DeviceState.isSubInfoReversed(multiSIMController.mContext);
                        multiSIMController.mNeedCheckOpportunisticESim = true;
                        multiSIMController.updateMultiSimReadyState(false);
                        multiSIMController.mData.isDataEnabled = multiSIMController.isDataEnabled();
                        multiSIMController.updateCarrierNameAndPhoneNumber(false);
                        break;
                    case VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F /* 2002 */:
                        Log.d("MultiSIMController", "MESSAGE_IMS_MANAGER_CONNECTED");
                        Uri uri = MultiSIMController.INTERNAL_URI;
                        multiSIMController.updateCarrierNameAndPhoneNumber(false);
                        break;
                    default:
                        Log.d("MultiSIMController", "UpdateDataHandler MESSAGE_EMPTY");
                        break;
                }
                Uri uri2 = MultiSIMController.INTERNAL_URI;
                multiSIMController.notifyDataToCallback();
            }
        };
        ?? r15 = new Handler() { // from class: com.android.systemui.settings.multisim.MultiSIMController.13
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                MultiSIMController multiSIMController = MultiSIMController.this;
                if (i == 1000) {
                    Log.d("MultiSIMController", "MESSAGE_UPDATE_MULTISIM_PREFERRED_DATA_BUTTON");
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
                MultiSIMData multiSIMData2 = multiSIMController.mData;
                if (multiSIMData2.changingNetMode) {
                    multiSIMData2.changingNetMode = false;
                    multiSIMController.notifyDataToCallback();
                }
            }
        };
        this.mUIHandler = r15;
        this.mNotifyVisToCallbackRunnable = new MultiSIMController$$ExternalSyntheticLambda6(this, 2);
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
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, broadcastReceiver);
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
        this.mData.simName[c] = Settings.Global.getString(context.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_1);
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
        this.mData.simName[c2] = Settings.Global.getString(context.getContentResolver(), SettingsHelper.INDEX_SIM_SELECT_NAME_2);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_icon_1"), false, contentObserver6);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_1), false, contentObserver6);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("select_icon_2"), false, contentObserver6);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_SIM_SELECT_NAME_2), false, contentObserver6);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("prefered_voice_call"), false, contentObserver2);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA), false, contentObserver5);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, contentObserver5);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("multi_sim_datacross_slot"), false, contentObserver3);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("set_network_mode_by_quick_panel"), false, contentObserver);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_AIRPLANE_MODE_ON), false, contentObserver4);
        this.mData.airplaneMode = Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1;
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED), false, contentObserver7);
        this.mData.isSatelliteMode = Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED, 0) == 1;
        if (getSRoamingVirtualSlot() == 1) {
            this.mData.isSRoaming = true;
        }
        this.mActivityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);
        SubscriptionManager.from(context).addOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
        updateSimSlotType();
        this.mData.isRestrictionsForMmsUse = isRestrictionsForMmsUse();
        updateMultiSimReadyState(true);
        MultiSIMData multiSIMData = this.mData;
        if (DeviceState.isVoiceCapable(context)) {
            z = false;
            int callState = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(0));
            int callState2 = TelephonyManager.from(ActivityThread.currentApplication().getApplicationContext()).getCallState(getSubId(1));
            Log.i("MultiSIMController", "Check Call SIM1 : " + callState + ", SIM2 : " + callState2);
            z2 = callState == 1 || callState == 2 || callState2 == 1 || callState2 == 2;
        } else {
            z2 = false;
            z = false;
        }
        multiSIMData.isCalling = z2;
        this.mData.isDataEnabled = isDataEnabled();
        this.mData.airplaneMode = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAirplaneModeOn();
        updateCarrierNameAndPhoneNumber(true);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, new HandlerExecutor((Handler) r15));
        this.mData.isSecondaryUser = userTrackerImpl.getUserId() != 0 ? true : z;
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
        if (Operator.QUICK_IS_XNX_BRANDING && SystemProperties.get("ril.lockpolicy", "0").equals("1")) {
            return true;
        }
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        return (edmMonitor != null && (edmMonitor.mSettingsChangesAllowed ^ true)) || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isUserMobileDataRestricted();
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

    public final int getSRoamingVirtualSlot() {
        Context context = this.mContext;
        int i = 9;
        if (context == null) {
            Log.d("MultiSIMController", "context is null : com.samsung.android.globalroaming");
        } else {
            try {
                context.getPackageManager().getApplicationInfo("com.samsung.android.globalroaming", 128);
                Log.i("MultiSIMController", "has sroaming package");
                String mSimSystemProperty = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 0, "default");
                String mSimSystemProperty2 = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", 1, "default");
                int sRoamingStatus = getSRoamingStatus(mSimSystemProperty);
                int sRoamingStatus2 = getSRoamingStatus(mSimSystemProperty2);
                if (sRoamingStatus == 1 || sRoamingStatus2 == 1) {
                    i = 1;
                } else if (sRoamingStatus == 0 && sRoamingStatus2 == 0) {
                    i = 0;
                }
                KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "sroaming status : ", "MultiSIMController");
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("MultiSIMController", "Package not found : com.samsung.android.globalroaming");
            }
        }
        return i;
    }

    public final boolean isDataBlocked(int i) {
        if (this.mIsSlotReversed) {
            i = 1 - i;
        }
        boolean z = false;
        if (this.mSimCardManagerService != null && SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
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
            Bundle call = this.mContext.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
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
        this.mSimCardManagerService = SimCardManagerServiceProvider.getService(this.mContext);
        return z4;
    }

    public final boolean isDataEnabled() {
        DataUsageController dataUsageController = this.mDataController;
        return dataUsageController != null && dataUsageController.isMobileDataSupported() && this.mDataController.isMobileDataEnabled();
    }

    public final boolean isMultiSimAvailable() {
        if (this.mData.isMultiSimReady) {
            boolean isLDUOLDModel = DeviceType.isLDUSKU() ? true : DeviceType.isLDUOLDModel();
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("isLDUModel = ", " isSecondaryUser = ", isLDUOLDModel), this.mData.isSecondaryUser, "MultiSIMController");
            if (!isLDUOLDModel && !this.mData.isSecondaryUser && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEmergencyMode()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isRestrictionsForMmsUse() {
        if (!DeviceType.isQcomChipType() && Build.VERSION.SEM_FIRST_SDK_INT < 31) {
            int i = Settings.Global.getInt(this.mContext.getContentResolver(), "multi_sim_datacross_slot", -1);
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "isMMSuse =", "MultiSIMController");
            if (i != -1) {
                return true;
            }
        }
        return false;
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
            AnonymousClass13 anonymousClass13 = this.mUIHandler;
            MultiSIMController$$ExternalSyntheticLambda6 multiSIMController$$ExternalSyntheticLambda6 = this.mNotifyDataToCallbackRunnable;
            anonymousClass13.removeCallbacks(multiSIMController$$ExternalSyntheticLambda6);
            anonymousClass13.post(multiSIMController$$ExternalSyntheticLambda6);
        }
    }

    public final void registerCallback(MultiSIMDataChangedCallback multiSIMDataChangedCallback) {
        if (multiSIMDataChangedCallback != null) {
            for (int i = 0; i < this.mDataCallbacks.size(); i++) {
                if (((WeakReference) this.mDataCallbacks.get(i)).get() == multiSIMDataChangedCallback) {
                    return;
                }
            }
            this.mDataCallbacks.add(new WeakReference(multiSIMDataChangedCallback));
            this.mDataCallbacks.removeIf(new MultiSIMController$$ExternalSyntheticLambda0(null, 1));
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
        String str = "PREFERRED_MOBILE_DATA";
        if (SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
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
            Bundle call2 = this.mContext.getContentResolver().call(INTERNAL_URI, "quickpanel_simcard_change", (String) null, bundle2);
            if (call2 == null) {
                Log.d("MultiSIMController", "bundle is null : quickpanel_simcard_change");
            } else {
                Log.d("MultiSIMController", "quickpanel_simcard_change, " + call2.getBoolean("success") + ", " + ((Throwable) call2.getParcelable("error")));
            }
        } catch (Throwable th2) {
            Log.e("MultiSIMController", "quickpanel_simcard_change, " + th2);
        }
        this.mSimCardManagerService = SimCardManagerServiceProvider.getService(this.mContext);
    }

    public final void updateCarrierNameAndPhoneNumber(boolean z) {
        if (z) {
            this.mNetworkNameDefault = this.mContext.getString(android.R.string.permdesc_runInBackground);
            this.mUnknownPhoneNumber = this.mContext.getString(R.string.qs_multisim_unknown_number);
            this.mInvalidSimInfo = this.mContext.getString(R.string.qs_multisim_invalid_sim_info);
        }
        for (int i = 0; i < 2; i++) {
            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = SubscriptionManager.from(this.mContext).getActiveSubscriptionInfoForSimSlotIndex(i);
            if (activeSubscriptionInfoForSimSlotIndex != null) {
                this.mData.carrierName[i] = (activeSubscriptionInfoForSimSlotIndex.getCarrierName() == null || activeSubscriptionInfoForSimSlotIndex.getCarrierName().length() <= 0) ? this.mNetworkNameDefault : activeSubscriptionInfoForSimSlotIndex.getCarrierName().toString();
            }
        }
        updatePhoneNumberWhenNeeded();
    }

    public final void updateCurrentDefaultSlot(ButtonType buttonType) {
        if (!this.mUIVisible) {
            if (!this.mDefaultIdUpdateList.contains(buttonType)) {
                this.mDefaultIdUpdateList.add(buttonType);
            }
            Log.d("MultiSIMController", "updateCurrentDefaultSlot later type = " + buttonType);
            return;
        }
        int ordinal = buttonType.ordinal();
        boolean z = true;
        if (ordinal == 0) {
            try {
                if (this.mSimCardManagerService == null || !SimCardManagerServiceProvider.isServiceRunningCheck(this.mContext)) {
                    z = false;
                }
                Boolean valueOf = Boolean.valueOf(z);
                this.mData.defaultVoiceSimId = z ? this.mSimCardManagerService.GetCurrentVoiceCall() : getCurrentVoiceSlotByMethodCall();
                Log.d("MultiSIMController", "updateCurrentDefaultSlot : voice = " + this.mData.defaultVoiceSimId + " " + valueOf);
            } catch (Exception e) {
                Log.e("MultiSIMController", "Caught exception from updateCurrentDefaultSlot", e);
            }
        } else if (ordinal == 1) {
            this.mData.defaultSmsSimId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultSmsSubscriptionId());
            RecyclerView$$ExternalSyntheticOutline0.m(this.mData.defaultSmsSimId, "MultiSIMController", new StringBuilder("updateCurrentDefaultSlot : sms = "));
        } else if (ordinal == 2) {
            this.mData.defaultDataSimId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
            RecyclerView$$ExternalSyntheticOutline0.m(this.mData.defaultDataSimId, "MultiSIMController", new StringBuilder("updateCurrentDefaultSlot : data = "));
        }
        notifyDataToCallback();
    }

    public final void updateMultiSimReadyState(boolean z) {
        if (z) {
            this.mIsLoadedMultiSim = DeviceState.getLoadedSimCount(this.mContext) == 2;
        }
        if (this.mIsLoadedMultiSim && this.mNeedCheckOpportunisticESim) {
            this.mHasOpportunisticESim = false;
            List<SubscriptionInfo> completeActiveSubscriptionInfoList = SubscriptionManager.from(this.mContext).getCompleteActiveSubscriptionInfoList();
            if (completeActiveSubscriptionInfoList.size() == 2) {
                SubscriptionInfo subscriptionInfo = completeActiveSubscriptionInfoList.get(0);
                SubscriptionInfo subscriptionInfo2 = completeActiveSubscriptionInfoList.get(1);
                if (subscriptionInfo.getGroupUuid() != null && subscriptionInfo.getGroupUuid().equals(subscriptionInfo2.getGroupUuid()) && (subscriptionInfo.isOpportunistic() || subscriptionInfo2.isOpportunistic())) {
                    this.mHasOpportunisticESim = true;
                }
            }
            this.mNeedCheckOpportunisticESim = false;
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("updateMultiSimReadyState: mHasOpportunisticESim = "), this.mHasOpportunisticESim, "MultiSIMController");
        }
        this.mData.isMultiSimReady = this.mIsLoadedMultiSim && !this.mHasOpportunisticESim;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00a4 A[EDGE_INSN: B:21:0x00a4->B:22:0x00a4 BREAK  A[LOOP:2: B:12:0x004f->B:50:0x00a2], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePhoneNumberWhenNeeded() {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.multisim.MultiSIMController.updatePhoneNumberWhenNeeded():void");
    }

    public final void updateSimSlotType() {
        int i = 0;
        while (true) {
            boolean[] zArr = this.mData.isESimSlot;
            if (i >= zArr.length) {
                return;
            }
            zArr[i] = DeviceState.isESIM(this.mContext, i);
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateSimSlotType() - sim slot ", " is eSim: ");
            m.append(this.mData.isESimSlot[i]);
            Log.d("MultiSIMController", m.toString());
            i++;
        }
    }
}
