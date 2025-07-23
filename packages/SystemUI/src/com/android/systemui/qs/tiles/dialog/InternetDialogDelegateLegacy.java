package com.android.systemui.qs.tiles.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.qs.flags.QsDetailedView;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentController;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetOnSubscriptionChangedListener;
import com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class InternetDialogDelegateLegacy implements SystemUIDialog.Delegate, InternetDetailsContentController.InternetDialogCallback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialog", 3);
    public final boolean mAboveStatusBar;
    protected InternetAdapter mAdapter;
    public Button mAirplaneModeButton;
    public TextView mAirplaneModeSummaryText;
    public AlertDialog mAlertDialog;
    public final Executor mBackgroundExecutor;
    public Drawable mBackgroundOn;
    public final boolean mCanChangeWifiState;
    public final boolean mCanConfigMobileData;
    protected boolean mCanConfigWifi;
    public StandaloneCoroutine mClickJob;
    public LinearLayout mConnectedWifListLayout;
    protected WifiEntry mConnectedWifiEntry;
    public ImageView mConnectedWifiIcon;
    public TextView mConnectedWifiSummaryText;
    public TextView mConnectedWifiTitleText;
    public final CoroutineScope mCoroutineScope;
    public int mDefaultDataSubId;
    public SystemUIDialog mDialog;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    protected View mDialogView;
    public View mDivider;
    public Button mDoneButton;
    public LinearLayout mEthernetLayout;
    public final Handler mHandler;
    protected boolean mHasMoreWifiEntries;
    public final InternetDetailsContentController mInternetDetailsContentController;
    public final InternetDialogManager mInternetDialogManager;
    public TextView mInternetDialogSubTitle;
    public TextView mInternetDialogTitle;
    public boolean mIsProgressBarVisible;
    public final KeyguardStateController mKeyguard;
    LifecycleOwner mLifecycleOwner;
    public LifecycleRegistry mLifecycleRegistry;
    public Switch mMobileDataToggle;
    public LinearLayout mMobileNetworkLayout;
    public TextView mMobileSummaryText;
    public TextView mMobileTitleText;
    public View mMobileToggleDivider;
    public ProgressBar mProgressBar;
    public LinearLayout mSecondaryMobileNetworkLayout;
    public LinearLayout mSeeAllLayout;
    public final ShadeDialogContextInteractor mShadeDialogContextInteractor;
    protected Button mShareWifiButton;
    public ImageView mSignalIcon;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public LinearLayout mTurnWifiOnLayout;
    public final UiEventLogger mUiEventLogger;
    public Switch mWiFiToggle;
    protected int mWifiEntriesCount;
    public int mWifiNetworkHeight;
    public RecyclerView mWifiRecyclerView;
    public LinearLayout mWifiScanNotifyLayout;
    public TextView mWifiScanNotifyText;
    public ImageView mWifiSettingsIcon;
    public TextView mWifiToggleTitleText;
    public Drawable mBackgroundOff = null;
    MutableLiveData mDataInternetContent = new MutableLiveData();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        InternetDialogDelegateLegacy create(boolean z, boolean z2, boolean z3, CoroutineScope coroutineScope);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class InternetContent {
        public CharSequence mInternetDialogTitleString = "";
        public CharSequence mInternetDialogSubTitle = "";
        public boolean mIsAirplaneModeEnabled = false;
        public boolean mHasEthernet = false;
        public boolean mShouldUpdateMobileNetwork = false;
        public boolean mActiveNetworkIsCellular = false;
        public boolean mIsCarrierNetworkActive = false;
        public boolean mIsWifiEnabled = false;
        public boolean mHasActiveSubIdOnDds = false;
        public boolean mIsDeviceLocked = false;
        public boolean mIsWifiScanEnabled = false;
        public int mActiveAutoSwitchNonDdsSubId = -1;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum InternetDialogEvent implements UiEventLogger.UiEventEnum {
        INTERNET_DIALOG_SHOW(843),
        SHARE_WIFI_QS_BUTTON_CLICKED(1462);

        private final int mId;

        InternetDialogEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public InternetDialogDelegateLegacy(Context context, InternetDialogManager internetDialogManager, InternetDetailsContentController internetDetailsContentController, boolean z, boolean z2, boolean z3, CoroutineScope coroutineScope, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator, Handler handler, Executor executor, KeyguardStateController keyguardStateController, SystemUIDialog.Factory factory, ShadeDialogContextInteractor shadeDialogContextInteractor, ShadeModeInteractor shadeModeInteractor) {
        boolean z4;
        if (shadeModeInteractor.isDualShade()) {
            int i = QsDetailedView.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        }
        this.mAboveStatusBar = z3;
        this.mSystemUIDialogFactory = factory;
        this.mShadeDialogContextInteractor = shadeDialogContextInteractor;
        if (DEBUG) {
            Log.d("InternetDialog", "Init InternetDialog");
        }
        this.mHandler = handler;
        this.mBackgroundExecutor = executor;
        this.mInternetDialogManager = internetDialogManager;
        this.mInternetDetailsContentController = internetDetailsContentController;
        internetDetailsContentController.getClass();
        this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        this.mCanConfigMobileData = z;
        this.mCanConfigWifi = z2;
        if (WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(context, "no_change_wifi_state")) {
            Log.w("WifiEntResUtils", "WI-FI state isn't allowed to change due to user restriction.");
            z4 = false;
        } else {
            z4 = true;
        }
        this.mCanChangeWifiState = z4;
        this.mKeyguard = keyguardStateController;
        this.mCoroutineScope = coroutineScope;
        this.mUiEventLogger = uiEventLogger;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mAdapter = new InternetAdapter(internetDetailsContentController, coroutineScope);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog create = this.mSystemUIDialogFactory.create(this, ((ShadeDialogContextInteractorImpl) this.mShadeDialogContextInteractor).getContext());
        if (!this.mAboveStatusBar) {
            create.getWindow().setType(2038);
        }
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
        }
        this.mDialog = create;
        this.mLifecycleOwner = new LifecycleOwner() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy.1
            @Override // androidx.lifecycle.LifecycleOwner
            public final Lifecycle getLifecycle() {
                return InternetDialogDelegateLegacy.this.mLifecycleRegistry;
            }
        };
        this.mLifecycleRegistry = new LifecycleRegistry(this.mLifecycleOwner);
        return create;
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void dismissDialog() {
        if (DEBUG) {
            Log.d("InternetDialog", "dismissDialog");
        }
        this.mInternetDialogManager.destroyDialog();
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
            this.mDialog = null;
        }
    }

    public int getWifiListMaxCount() {
        int i = this.mEthernetLayout.getVisibility() == 0 ? 3 : 4;
        if (this.mMobileNetworkLayout.getVisibility() == 0) {
            i--;
        }
        int i2 = i <= 3 ? i : 3;
        return this.mConnectedWifListLayout.getVisibility() == 0 ? i2 - 1 : i2;
    }

    public void hideWifiViews() {
        setProgressBarVisible(false);
        this.mTurnWifiOnLayout.setVisibility(8);
        this.mConnectedWifListLayout.setVisibility(8);
        this.mWifiRecyclerView.setVisibility(8);
        this.mSeeAllLayout.setVisibility(8);
        this.mShareWifiButton.setVisibility(8);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onAccessPointsChanged(final List list, final WifiEntry wifiEntry, final boolean z) {
        final boolean z2 = this.mMobileNetworkLayout.getVisibility() == 0 && this.mInternetDetailsContentController.isAirplaneModeEnabled();
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialogDelegateLegacy internetDialogDelegateLegacy = InternetDialogDelegateLegacy.this;
                WifiEntry wifiEntry2 = wifiEntry;
                List list2 = list;
                boolean z3 = z;
                boolean z4 = z2;
                internetDialogDelegateLegacy.mConnectedWifiEntry = wifiEntry2;
                internetDialogDelegateLegacy.mWifiEntriesCount = list2 == null ? 0 : list2.size();
                internetDialogDelegateLegacy.mHasMoreWifiEntries = z3;
                internetDialogDelegateLegacy.updateDialog(z4);
                InternetAdapter internetAdapter = internetDialogDelegateLegacy.mAdapter;
                int i = internetDialogDelegateLegacy.mWifiEntriesCount;
                internetAdapter.mWifiEntries = list2;
                int i2 = internetAdapter.mMaxEntriesCount;
                if (i >= i2) {
                    i = i2;
                }
                internetAdapter.mWifiEntriesCount = i;
                internetAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onCapabilitiesChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onCarrierNetworkChange() {
        updateDialog(true);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        if (DEBUG) {
            Log.d("InternetDialog", "onCreate");
        }
        Context context = systemUIDialog.getContext();
        this.mUiEventLogger.log(InternetDialogEvent.INTERNET_DIALOG_SHOW);
        View inflate = LayoutInflater.from(context).inflate(R.layout.internet_connectivity_dialog, (ViewGroup) null);
        this.mDialogView = inflate;
        inflate.setAccessibilityPaneTitle(context.getText(R.string.accessibility_desc_quick_settings));
        Window window = systemUIDialog.getWindow();
        window.setContentView(this.mDialogView);
        window.setWindowAnimations(R.style.Animation_InternetDialog);
        this.mWifiNetworkHeight = context.getResources().getDimensionPixelSize(R.dimen.internet_dialog_wifi_network_height);
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        this.mDataInternetContent.observe(this.mLifecycleOwner, new Observer() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                boolean z = InternetDialogDelegateLegacy.DEBUG;
                InternetDialogDelegateLegacy.this.updateDialogUI((InternetDialogDelegateLegacy.InternetContent) obj);
            }
        });
        this.mInternetDialogTitle = (TextView) this.mDialogView.requireViewById(R.id.internet_dialog_title);
        this.mInternetDialogSubTitle = (TextView) this.mDialogView.requireViewById(R.id.internet_dialog_subtitle);
        this.mDivider = this.mDialogView.requireViewById(R.id.divider);
        this.mProgressBar = (ProgressBar) this.mDialogView.requireViewById(R.id.wifi_searching_progress);
        this.mEthernetLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.ethernet_layout);
        this.mMobileNetworkLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.mobile_network_layout);
        this.mTurnWifiOnLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.turn_on_wifi_layout);
        this.mWifiToggleTitleText = (TextView) this.mDialogView.requireViewById(R.id.wifi_toggle_title);
        this.mWifiScanNotifyLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.wifi_scan_notify_layout);
        this.mWifiScanNotifyText = (TextView) this.mDialogView.requireViewById(R.id.wifi_scan_notify_text);
        this.mConnectedWifListLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.wifi_connected_layout);
        this.mConnectedWifiIcon = (ImageView) this.mDialogView.requireViewById(R.id.wifi_connected_icon);
        this.mConnectedWifiTitleText = (TextView) this.mDialogView.requireViewById(R.id.wifi_connected_title);
        this.mConnectedWifiSummaryText = (TextView) this.mDialogView.requireViewById(R.id.wifi_connected_summary);
        this.mWifiSettingsIcon = (ImageView) this.mDialogView.requireViewById(R.id.wifi_settings_icon);
        this.mWifiRecyclerView = (RecyclerView) this.mDialogView.requireViewById(R.id.wifi_list_layout);
        this.mSeeAllLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.see_all_layout);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(R.id.done_button);
        this.mShareWifiButton = (Button) this.mDialogView.requireViewById(R.id.share_wifi_button);
        this.mAirplaneModeButton = (Button) this.mDialogView.requireViewById(R.id.apm_button);
        this.mSignalIcon = (ImageView) this.mDialogView.requireViewById(R.id.signal_icon);
        this.mMobileTitleText = (TextView) this.mDialogView.requireViewById(R.id.mobile_title);
        this.mMobileSummaryText = (TextView) this.mDialogView.requireViewById(R.id.mobile_summary);
        this.mAirplaneModeSummaryText = (TextView) this.mDialogView.requireViewById(R.id.airplane_mode_summary);
        this.mMobileToggleDivider = this.mDialogView.requireViewById(R.id.mobile_toggle_divider);
        this.mMobileDataToggle = (Switch) this.mDialogView.requireViewById(R.id.mobile_toggle);
        this.mWiFiToggle = (Switch) this.mDialogView.requireViewById(R.id.wifi_toggle);
        this.mBackgroundOn = context.getDrawable(R.drawable.settingslib_switch_bar_bg_on);
        TextView textView = this.mInternetDialogTitle;
        InternetDetailsContentController internetDetailsContentController = this.mInternetDetailsContentController;
        textView.setText(internetDetailsContentController.getDialogTitleText());
        this.mInternetDialogTitle.setGravity(8388627);
        this.mBackgroundOff = context.getDrawable(R.drawable.internet_dialog_selected_effect);
        final int i = 0;
        this.mMobileNetworkLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda6
            public final /* synthetic */ InternetDialogDelegateLegacy f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog2;
                switch (i) {
                    case 0:
                        final InternetDialogDelegateLegacy internetDialogDelegateLegacy = this.f$0;
                        SystemUIDialog systemUIDialog3 = systemUIDialog;
                        final int i2 = internetDialogDelegateLegacy.mDataInternetContent.getValue() != null ? ((InternetDialogDelegateLegacy.InternetContent) internetDialogDelegateLegacy.mDataInternetContent.getValue()).mActiveAutoSwitchNonDdsSubId : -1;
                        InternetDetailsContentController internetDetailsContentController2 = internetDialogDelegateLegacy.mInternetDetailsContentController;
                        if (i2 != -1) {
                            Context context2 = systemUIDialog3.getContext();
                            CharSequence mobileNetworkTitle = internetDetailsContentController2.getMobileNetworkTitle(internetDialogDelegateLegacy.mDefaultDataSubId);
                            if (TextUtils.isEmpty(mobileNetworkTitle)) {
                                mobileNetworkTitle = context2.getString(R.string.mobile_data_disable_message_default_carrier);
                            }
                            AlertDialog create = new AlertDialog.Builder(context2).setTitle(context2.getString(R.string.auto_data_switch_disable_title, mobileNetworkTitle)).setMessage(R.string.auto_data_switch_disable_message).setNegativeButton(R.string.auto_data_switch_dialog_negative_button, new InternetDialogDelegateLegacy$$ExternalSyntheticLambda18()).setPositiveButton(R.string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda20
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    InternetDialogDelegateLegacy internetDialogDelegateLegacy2 = InternetDialogDelegateLegacy.this;
                                    int i4 = i2;
                                    InternetDetailsContentController internetDetailsContentController3 = internetDialogDelegateLegacy2.mInternetDetailsContentController;
                                    TelephonyManager orDefault = internetDetailsContentController3.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i4), internetDetailsContentController3.mTelephonyManager);
                                    if (orDefault != null) {
                                        orDefault.setMobileDataPolicyEnabled(3, false);
                                    } else if (InternetDetailsContentController.DEBUG) {
                                        Log.d("InternetDetailsContentController", "TelephonyManager is null, can not set mobile data.");
                                    }
                                    LinearLayout linearLayout = internetDialogDelegateLegacy2.mSecondaryMobileNetworkLayout;
                                    if (linearLayout != null) {
                                        linearLayout.setVisibility(8);
                                    }
                                }
                            }).create();
                            internetDialogDelegateLegacy.mAlertDialog = create;
                            create.getWindow().setType(2009);
                            SystemUIDialog.setShowForAllUsers(internetDialogDelegateLegacy.mAlertDialog);
                            SystemUIDialog.registerDismissListener(internetDialogDelegateLegacy.mAlertDialog);
                            SystemUIDialog.setWindowOnTop(internetDialogDelegateLegacy.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegateLegacy.mKeyguard).mShowing);
                            internetDialogDelegateLegacy.mDialogTransitionAnimator.showFromDialog(internetDialogDelegateLegacy.mAlertDialog, systemUIDialog3);
                        }
                        boolean z = internetDetailsContentController2.mIsMobileDataEnabled;
                        boolean z2 = InternetDetailsContentController.DEBUG;
                        if (!z) {
                            if (z2) {
                                Log.d("InternetDetailsContentController", "Fail to connect carrier network : settings OFF");
                                break;
                            }
                        } else if (!internetDetailsContentController2.isDeviceLocked()) {
                            if (!internetDetailsContentController2.activeNetworkIsCellular()) {
                                MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDetailsContentController2.mAccessPointController).getMergedCarrierEntry();
                                if (mergedCarrierEntry != null) {
                                    if (!mergedCarrierEntry.canConnect()) {
                                        Log.w("InternetDetailsContentController", "Fail to connect carrier network : merged entry connect state " + mergedCarrierEntry.getConnectedState());
                                        break;
                                    } else {
                                        mergedCarrierEntry.connect$1(null);
                                        internetDetailsContentController2.makeOverlayToast(R.string.wifi_wont_autoconnect_for_now);
                                        break;
                                    }
                                } else {
                                    Log.e("InternetDetailsContentController", "Fail to connect carrier network : no merged entry");
                                    break;
                                }
                            } else {
                                Log.d("InternetDetailsContentController", "Fail to connect carrier network : already active");
                                break;
                            }
                        } else if (z2) {
                            Log.d("InternetDetailsContentController", "Fail to connect carrier network : device locked");
                            break;
                        }
                        break;
                    default:
                        final InternetDialogDelegateLegacy internetDialogDelegateLegacy2 = this.f$0;
                        SystemUIDialog systemUIDialog4 = systemUIDialog;
                        boolean isChecked = internetDialogDelegateLegacy2.mMobileDataToggle.isChecked();
                        InternetDetailsContentController internetDetailsContentController3 = internetDialogDelegateLegacy2.mInternetDetailsContentController;
                        if (!isChecked && (systemUIDialog2 = internetDialogDelegateLegacy2.mDialog) != null) {
                            boolean z3 = Prefs.getBoolean(systemUIDialog2.getContext(), "QsHasTurnedOffMobileData", false);
                            if (internetDetailsContentController3.mIsMobileDataEnabled && !z3) {
                                internetDialogDelegateLegacy2.mMobileDataToggle.setChecked(true);
                                final Context context3 = systemUIDialog4.getContext();
                                CharSequence mobileNetworkTitle2 = internetDetailsContentController3.getMobileNetworkTitle(internetDialogDelegateLegacy2.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDetailsContentController3.isVoiceStateInService(internetDialogDelegateLegacy2.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle2) || !isVoiceStateInService) {
                                    mobileNetworkTitle2 = context3.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create2 = new AlertDialog.Builder(context3).setTitle(R.string.mobile_data_disable_title).setMessage(context3.getString(R.string.mobile_data_disable_message, mobileNetworkTitle2)).setNegativeButton(android.R.string.cancel, new InternetDialogDelegateLegacy$$ExternalSyntheticLambda18()).setPositiveButton(android.R.string.car_loading_profile, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda19
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i3) {
                                        InternetDialogDelegateLegacy internetDialogDelegateLegacy3 = InternetDialogDelegateLegacy.this;
                                        Context context4 = context3;
                                        internetDialogDelegateLegacy3.mInternetDetailsContentController.setMobileDataEnabled(context4, internetDialogDelegateLegacy3.mDefaultDataSubId, false);
                                        internetDialogDelegateLegacy3.mMobileDataToggle.setChecked(false);
                                        Prefs.putBoolean(context4, "QsHasTurnedOffMobileData", true);
                                    }
                                }).create();
                                internetDialogDelegateLegacy2.mAlertDialog = create2;
                                create2.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialogDelegateLegacy2.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialogDelegateLegacy2.mAlertDialog);
                                SystemUIDialog.setWindowOnTop(internetDialogDelegateLegacy2.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegateLegacy2.mKeyguard).mShowing);
                                internetDialogDelegateLegacy2.mDialogTransitionAnimator.showFromDialog(internetDialogDelegateLegacy2.mAlertDialog, systemUIDialog4);
                                break;
                            }
                        }
                        if (internetDetailsContentController3.mIsMobileDataEnabled != isChecked) {
                            internetDetailsContentController3.setMobileDataEnabled(systemUIDialog4.getContext(), internetDialogDelegateLegacy2.mDefaultDataSubId, isChecked);
                            break;
                        }
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mMobileDataToggle.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda6
            public final /* synthetic */ InternetDialogDelegateLegacy f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog2;
                switch (i2) {
                    case 0:
                        final InternetDialogDelegateLegacy internetDialogDelegateLegacy = this.f$0;
                        SystemUIDialog systemUIDialog3 = systemUIDialog;
                        final int i22 = internetDialogDelegateLegacy.mDataInternetContent.getValue() != null ? ((InternetDialogDelegateLegacy.InternetContent) internetDialogDelegateLegacy.mDataInternetContent.getValue()).mActiveAutoSwitchNonDdsSubId : -1;
                        InternetDetailsContentController internetDetailsContentController2 = internetDialogDelegateLegacy.mInternetDetailsContentController;
                        if (i22 != -1) {
                            Context context2 = systemUIDialog3.getContext();
                            CharSequence mobileNetworkTitle = internetDetailsContentController2.getMobileNetworkTitle(internetDialogDelegateLegacy.mDefaultDataSubId);
                            if (TextUtils.isEmpty(mobileNetworkTitle)) {
                                mobileNetworkTitle = context2.getString(R.string.mobile_data_disable_message_default_carrier);
                            }
                            AlertDialog create = new AlertDialog.Builder(context2).setTitle(context2.getString(R.string.auto_data_switch_disable_title, mobileNetworkTitle)).setMessage(R.string.auto_data_switch_disable_message).setNegativeButton(R.string.auto_data_switch_dialog_negative_button, new InternetDialogDelegateLegacy$$ExternalSyntheticLambda18()).setPositiveButton(R.string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda20
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    InternetDialogDelegateLegacy internetDialogDelegateLegacy2 = InternetDialogDelegateLegacy.this;
                                    int i4 = i22;
                                    InternetDetailsContentController internetDetailsContentController3 = internetDialogDelegateLegacy2.mInternetDetailsContentController;
                                    TelephonyManager orDefault = internetDetailsContentController3.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i4), internetDetailsContentController3.mTelephonyManager);
                                    if (orDefault != null) {
                                        orDefault.setMobileDataPolicyEnabled(3, false);
                                    } else if (InternetDetailsContentController.DEBUG) {
                                        Log.d("InternetDetailsContentController", "TelephonyManager is null, can not set mobile data.");
                                    }
                                    LinearLayout linearLayout = internetDialogDelegateLegacy2.mSecondaryMobileNetworkLayout;
                                    if (linearLayout != null) {
                                        linearLayout.setVisibility(8);
                                    }
                                }
                            }).create();
                            internetDialogDelegateLegacy.mAlertDialog = create;
                            create.getWindow().setType(2009);
                            SystemUIDialog.setShowForAllUsers(internetDialogDelegateLegacy.mAlertDialog);
                            SystemUIDialog.registerDismissListener(internetDialogDelegateLegacy.mAlertDialog);
                            SystemUIDialog.setWindowOnTop(internetDialogDelegateLegacy.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegateLegacy.mKeyguard).mShowing);
                            internetDialogDelegateLegacy.mDialogTransitionAnimator.showFromDialog(internetDialogDelegateLegacy.mAlertDialog, systemUIDialog3);
                        }
                        boolean z = internetDetailsContentController2.mIsMobileDataEnabled;
                        boolean z2 = InternetDetailsContentController.DEBUG;
                        if (!z) {
                            if (z2) {
                                Log.d("InternetDetailsContentController", "Fail to connect carrier network : settings OFF");
                                break;
                            }
                        } else if (!internetDetailsContentController2.isDeviceLocked()) {
                            if (!internetDetailsContentController2.activeNetworkIsCellular()) {
                                MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDetailsContentController2.mAccessPointController).getMergedCarrierEntry();
                                if (mergedCarrierEntry != null) {
                                    if (!mergedCarrierEntry.canConnect()) {
                                        Log.w("InternetDetailsContentController", "Fail to connect carrier network : merged entry connect state " + mergedCarrierEntry.getConnectedState());
                                        break;
                                    } else {
                                        mergedCarrierEntry.connect$1(null);
                                        internetDetailsContentController2.makeOverlayToast(R.string.wifi_wont_autoconnect_for_now);
                                        break;
                                    }
                                } else {
                                    Log.e("InternetDetailsContentController", "Fail to connect carrier network : no merged entry");
                                    break;
                                }
                            } else {
                                Log.d("InternetDetailsContentController", "Fail to connect carrier network : already active");
                                break;
                            }
                        } else if (z2) {
                            Log.d("InternetDetailsContentController", "Fail to connect carrier network : device locked");
                            break;
                        }
                        break;
                    default:
                        final InternetDialogDelegateLegacy internetDialogDelegateLegacy2 = this.f$0;
                        SystemUIDialog systemUIDialog4 = systemUIDialog;
                        boolean isChecked = internetDialogDelegateLegacy2.mMobileDataToggle.isChecked();
                        InternetDetailsContentController internetDetailsContentController3 = internetDialogDelegateLegacy2.mInternetDetailsContentController;
                        if (!isChecked && (systemUIDialog2 = internetDialogDelegateLegacy2.mDialog) != null) {
                            boolean z3 = Prefs.getBoolean(systemUIDialog2.getContext(), "QsHasTurnedOffMobileData", false);
                            if (internetDetailsContentController3.mIsMobileDataEnabled && !z3) {
                                internetDialogDelegateLegacy2.mMobileDataToggle.setChecked(true);
                                final Context context3 = systemUIDialog4.getContext();
                                CharSequence mobileNetworkTitle2 = internetDetailsContentController3.getMobileNetworkTitle(internetDialogDelegateLegacy2.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDetailsContentController3.isVoiceStateInService(internetDialogDelegateLegacy2.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle2) || !isVoiceStateInService) {
                                    mobileNetworkTitle2 = context3.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create2 = new AlertDialog.Builder(context3).setTitle(R.string.mobile_data_disable_title).setMessage(context3.getString(R.string.mobile_data_disable_message, mobileNetworkTitle2)).setNegativeButton(android.R.string.cancel, new InternetDialogDelegateLegacy$$ExternalSyntheticLambda18()).setPositiveButton(android.R.string.car_loading_profile, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda19
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i3) {
                                        InternetDialogDelegateLegacy internetDialogDelegateLegacy3 = InternetDialogDelegateLegacy.this;
                                        Context context4 = context3;
                                        internetDialogDelegateLegacy3.mInternetDetailsContentController.setMobileDataEnabled(context4, internetDialogDelegateLegacy3.mDefaultDataSubId, false);
                                        internetDialogDelegateLegacy3.mMobileDataToggle.setChecked(false);
                                        Prefs.putBoolean(context4, "QsHasTurnedOffMobileData", true);
                                    }
                                }).create();
                                internetDialogDelegateLegacy2.mAlertDialog = create2;
                                create2.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialogDelegateLegacy2.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialogDelegateLegacy2.mAlertDialog);
                                SystemUIDialog.setWindowOnTop(internetDialogDelegateLegacy2.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegateLegacy2.mKeyguard).mShowing);
                                internetDialogDelegateLegacy2.mDialogTransitionAnimator.showFromDialog(internetDialogDelegateLegacy2.mAlertDialog, systemUIDialog4);
                                break;
                            }
                        }
                        if (internetDetailsContentController3.mIsMobileDataEnabled != isChecked) {
                            internetDetailsContentController3.setMobileDataEnabled(systemUIDialog4.getContext(), internetDialogDelegateLegacy2.mDefaultDataSubId, isChecked);
                            break;
                        }
                        break;
                }
            }
        });
        this.mConnectedWifListLayout.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 0));
        this.mSeeAllLayout.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 5));
        this.mWiFiToggle.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 2));
        this.mDoneButton.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda11(systemUIDialog, 0));
        this.mShareWifiButton.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 1));
        this.mAirplaneModeButton.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 3));
        this.mTurnWifiOnLayout.setBackground(null);
        this.mAirplaneModeButton.setVisibility(internetDetailsContentController.isAirplaneModeEnabled() ? 0 : 8);
        this.mWifiRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.mWifiRecyclerView.setAdapter(this.mAdapter);
        InternetContent internetContent = new InternetContent();
        internetContent.mInternetDialogTitleString = internetDetailsContentController.getDialogTitleText();
        internetContent.mInternetDialogSubTitle = internetDetailsContentController.getSubtitleText(this.mIsProgressBarVisible);
        internetContent.mIsWifiEnabled = internetDetailsContentController.mWifiStateWorker.isWifiEnabled();
        internetContent.mIsDeviceLocked = internetDetailsContentController.isDeviceLocked();
        updateDialogUI(internetContent);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onDataConnectionStateChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onDisplayInfoChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onLost() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onRefreshCarrierInfo() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onServiceStateChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onSignalStrengthsChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onSimStateChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStart(Dialog dialog) {
        if (DEBUG) {
            Log.d("InternetDialog", "onStart");
        }
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        boolean z = this.mCanConfigWifi;
        boolean z2 = InternetDetailsContentController.DEBUG;
        InternetDetailsContentController internetDetailsContentController = this.mInternetDetailsContentController;
        if (z2) {
            internetDetailsContentController.getClass();
            Log.d("InternetDetailsContentController", "onStart");
        }
        internetDetailsContentController.mCallback = this;
        internetDetailsContentController.mKeyguardUpdateMonitor.registerCallback(internetDetailsContentController.mKeyguardUpdateCallback);
        ((AccessPointControllerImpl) internetDetailsContentController.mAccessPointController).addAccessPointCallback(internetDetailsContentController);
        InternetDetailsContentController.AnonymousClass2 anonymousClass2 = internetDetailsContentController.mConnectionStateReceiver;
        IntentFilter intentFilter = internetDetailsContentController.mConnectionStateFilter;
        Executor executor = internetDetailsContentController.mExecutor;
        BroadcastDispatcher broadcastDispatcher = internetDetailsContentController.mBroadcastDispatcher;
        broadcastDispatcher.getClass();
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, anonymousClass2, intentFilter, executor, null, 0, null, 56);
        internetDetailsContentController.mOnSubscriptionsChangedListener = internetDetailsContentController.new InternetOnSubscriptionChangedListener();
        internetDetailsContentController.refreshHasActiveSubIdOnDds();
        internetDetailsContentController.mSubscriptionManager.addOnSubscriptionsChangedListener(internetDetailsContentController.mExecutor, internetDetailsContentController.mOnSubscriptionsChangedListener);
        internetDetailsContentController.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (z2) {
            RecyclerView$$ExternalSyntheticOutline0.m(internetDetailsContentController.mDefaultDataSubId, "InternetDetailsContentController", new StringBuilder("Init, SubId: "));
        }
        internetDetailsContentController.mConfig = MobileMappings.Config.readConfig(internetDetailsContentController.mContext);
        internetDetailsContentController.mTelephonyManager = internetDetailsContentController.mTelephonyManager.createForSubscriptionId(internetDetailsContentController.mDefaultDataSubId);
        internetDetailsContentController.mSubIdTelephonyManagerMap.put(Integer.valueOf(internetDetailsContentController.mDefaultDataSubId), internetDetailsContentController.mTelephonyManager);
        internetDetailsContentController.registerInternetTelephonyCallback(internetDetailsContentController.mTelephonyManager, internetDetailsContentController.mDefaultDataSubId);
        internetDetailsContentController.mConnectivityManager.registerDefaultNetworkCallback(internetDetailsContentController.mConnectivityManagerNetworkCallback);
        internetDetailsContentController.mCanConfigWifi = z;
        internetDetailsContentController.scanWifiAccessPoints();
        if (this.mCanConfigWifi) {
            return;
        }
        hideWifiViews();
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        if (DEBUG) {
            Log.d("InternetDialog", "onStop");
        }
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
        this.mMobileNetworkLayout.setOnClickListener(null);
        this.mConnectedWifListLayout.setOnClickListener(null);
        LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(null);
        }
        this.mSeeAllLayout.setOnClickListener(null);
        this.mWiFiToggle.setOnCheckedChangeListener(null);
        this.mDoneButton.setOnClickListener(null);
        this.mShareWifiButton.setOnClickListener(null);
        this.mAirplaneModeButton.setOnClickListener(null);
        InternetDetailsContentController internetDetailsContentController = this.mInternetDetailsContentController;
        boolean z = InternetDetailsContentController.DEBUG;
        if (z) {
            internetDetailsContentController.getClass();
            Log.d("InternetDetailsContentController", "onStop");
        }
        internetDetailsContentController.mBroadcastDispatcher.unregisterReceiver(internetDetailsContentController.mConnectionStateReceiver);
        for (TelephonyManager telephonyManager : internetDetailsContentController.mSubIdTelephonyManagerMap.values()) {
            TelephonyCallback telephonyCallback = internetDetailsContentController.mSubIdTelephonyCallbackMap.get(Integer.valueOf(telephonyManager.getSubscriptionId()));
            if (telephonyCallback != null) {
                telephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (z) {
                Log.e("InternetDetailsContentController", "Unexpected null telephony call back for Sub " + telephonyManager.getSubscriptionId());
            }
        }
        internetDetailsContentController.mSubIdTelephonyManagerMap.clear();
        internetDetailsContentController.mSubIdTelephonyCallbackMap.clear();
        internetDetailsContentController.mSubIdTelephonyDisplayInfoMap.clear();
        internetDetailsContentController.mSubscriptionManager.removeOnSubscriptionsChangedListener(internetDetailsContentController.mOnSubscriptionsChangedListener);
        ((AccessPointControllerImpl) internetDetailsContentController.mAccessPointController).removeAccessPointCallback(internetDetailsContentController);
        internetDetailsContentController.mKeyguardUpdateMonitor.removeCallback(internetDetailsContentController.mKeyguardUpdateCallback);
        internetDetailsContentController.mConnectivityManager.unregisterNetworkCallback(internetDetailsContentController.mConnectivityManagerNetworkCallback);
        InternetDetailsContentController.ConnectedWifiInternetMonitor connectedWifiInternetMonitor = internetDetailsContentController.mConnectedWifiInternetMonitor;
        WifiEntry wifiEntry = connectedWifiInternetMonitor.mWifiEntry;
        if (wifiEntry != null) {
            synchronized (wifiEntry) {
                wifiEntry.mListener = null;
            }
            connectedWifiInternetMonitor.mWifiEntry = null;
        }
        internetDetailsContentController.mCallback = null;
        this.mInternetDialogManager.destroyDialog();
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onSubscriptionsChanged(int i) {
        this.mDefaultDataSubId = i;
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onUserMobileDataStateChanged() {
        updateDialog(true);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDetailsContentController.InternetDialogCallback
    public final void onWifiScan(boolean z) {
        setProgressBarVisible(z);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onWindowFocusChanged(Dialog dialog, boolean z) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || alertDialog.isShowing() || z || !systemUIDialog.isShowing()) {
            return;
        }
        systemUIDialog.dismiss();
    }

    public final void setProgressBarVisible(boolean z) {
        if (this.mIsProgressBarVisible == z) {
            return;
        }
        this.mIsProgressBarVisible = z;
        this.mProgressBar.setVisibility(z ? 0 : 8);
        this.mProgressBar.setIndeterminate(z);
        this.mDivider.setVisibility(z ? 8 : 0);
        this.mInternetDialogSubTitle.setText(this.mInternetDetailsContentController.getSubtitleText(this.mIsProgressBarVisible));
    }

    public final void updateDialog(final boolean z) {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WifiManager wifiManager;
                InternetDialogDelegateLegacy internetDialogDelegateLegacy = InternetDialogDelegateLegacy.this;
                boolean z2 = z;
                MutableLiveData mutableLiveData = internetDialogDelegateLegacy.mDataInternetContent;
                InternetDialogDelegateLegacy.InternetContent internetContent = new InternetDialogDelegateLegacy.InternetContent();
                internetContent.mShouldUpdateMobileNetwork = z2;
                InternetDetailsContentController internetDetailsContentController = internetDialogDelegateLegacy.mInternetDetailsContentController;
                internetContent.mInternetDialogTitleString = internetDetailsContentController.getDialogTitleText();
                internetContent.mInternetDialogSubTitle = internetDetailsContentController.getSubtitleText(internetDialogDelegateLegacy.mIsProgressBarVisible);
                if (z2) {
                    internetContent.mActiveNetworkIsCellular = internetDetailsContentController.activeNetworkIsCellular();
                    internetContent.mIsCarrierNetworkActive = internetDetailsContentController.isCarrierNetworkActive();
                }
                internetContent.mIsAirplaneModeEnabled = internetDetailsContentController.isAirplaneModeEnabled();
                internetContent.mHasEthernet = internetDetailsContentController.mHasEthernet;
                internetContent.mIsWifiEnabled = internetDetailsContentController.mWifiStateWorker.isWifiEnabled();
                boolean z3 = false;
                internetContent.mHasActiveSubIdOnDds = (internetDetailsContentController.isAirplaneModeEnabled() || internetDetailsContentController.mTelephonyManager == null) ? false : internetDetailsContentController.mHasActiveSubIdOnDds;
                internetContent.mIsDeviceLocked = internetDetailsContentController.isDeviceLocked();
                if (((LocationControllerImpl) internetDetailsContentController.mLocationController).isLocationEnabled$1() && (wifiManager = internetDetailsContentController.mWifiManager) != null && wifiManager.isScanAlwaysAvailable()) {
                    z3 = true;
                }
                internetContent.mIsWifiScanEnabled = z3;
                internetContent.mActiveAutoSwitchNonDdsSubId = internetDetailsContentController.getActiveAutoSwitchNonDdsSubId();
                mutableLiveData.postValue(internetContent);
            }
        });
    }

    public final void updateDialogUI(InternetContent internetContent) {
        int i;
        SystemUIDialog systemUIDialog;
        boolean z = DEBUG;
        if (z) {
            Log.d("InternetDialog", "updateDialog ");
        }
        this.mInternetDialogTitle.setText(internetContent.mInternetDialogTitleString);
        this.mInternetDialogSubTitle.setText(internetContent.mInternetDialogSubTitle);
        if (!internetContent.mIsWifiEnabled) {
            setProgressBarVisible(false);
        }
        this.mAirplaneModeButton.setVisibility(internetContent.mIsAirplaneModeEnabled ? 0 : 8);
        this.mEthernetLayout.setVisibility(internetContent.mHasEthernet ? 0 : 8);
        boolean z2 = internetContent.mShouldUpdateMobileNetwork;
        int i2 = R.style.TextAppearance_InternetDialog_Active;
        InternetDetailsContentController internetDetailsContentController = this.mInternetDetailsContentController;
        if (z2 && (systemUIDialog = this.mDialog) != null) {
            boolean z3 = internetContent.mActiveNetworkIsCellular || internetContent.mIsCarrierNetworkActive;
            if (z) {
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("setMobileDataLayout, isCarrierNetworkActive = "), internetContent.mIsCarrierNetworkActive, "InternetDialog");
            }
            if (internetContent.mHasActiveSubIdOnDds || (internetContent.mIsWifiEnabled && internetContent.mIsCarrierNetworkActive)) {
                this.mMobileNetworkLayout.setVisibility(0);
                this.mMobileDataToggle.setChecked(internetDetailsContentController.mIsMobileDataEnabled);
                this.mMobileTitleText.setText(internetDetailsContentController.getMobileNetworkTitle(this.mDefaultDataSubId));
                String mobileNetworkSummary = internetDetailsContentController.getMobileNetworkSummary(this.mDefaultDataSubId);
                if (TextUtils.isEmpty(mobileNetworkSummary)) {
                    this.mMobileSummaryText.setVisibility(8);
                } else {
                    this.mMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary, 0));
                    this.mMobileSummaryText.setBreakStrategy(0);
                    this.mMobileSummaryText.setVisibility(0);
                }
                this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        InternetDialogDelegateLegacy internetDialogDelegateLegacy = InternetDialogDelegateLegacy.this;
                        internetDialogDelegateLegacy.mHandler.post(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda16(internetDialogDelegateLegacy, internetDialogDelegateLegacy.mInternetDetailsContentController.getSignalStrengthDrawable(internetDialogDelegateLegacy.mDefaultDataSubId), 0));
                    }
                });
                Switch r2 = this.mMobileDataToggle;
                boolean z4 = this.mCanConfigMobileData;
                r2.setVisibility(z4 ? 0 : 4);
                this.mMobileToggleDivider.setVisibility(z4 ? 0 : 4);
                this.mMobileToggleDivider.setBackgroundColor(systemUIDialog.getContext().getColor(z3 ? R.color.connected_network_primary_color : R.color.disconnected_network_primary_color));
                final int i3 = internetContent.mActiveAutoSwitchNonDdsSubId;
                int i4 = i3 != -1 ? 0 : 8;
                int i5 = z3 ? 2132018509 : 2132018508;
                if (i4 == 0) {
                    ViewStub viewStub = (ViewStub) this.mDialogView.findViewById(R.id.secondary_mobile_network_stub);
                    if (viewStub != null) {
                        viewStub.inflate();
                    }
                    LinearLayout linearLayout = (LinearLayout) this.mDialogView.findViewById(R.id.secondary_mobile_network_layout);
                    this.mSecondaryMobileNetworkLayout = linearLayout;
                    if (z4) {
                        linearLayout.setOnClickListener(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda8(this, 4));
                    }
                    this.mSecondaryMobileNetworkLayout.setBackground(this.mBackgroundOn);
                    TextView textView = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_title);
                    textView.setText(internetDetailsContentController.getMobileNetworkTitle(i3));
                    textView.setTextAppearance(R.style.TextAppearance_InternetDialog_Active);
                    TextView textView2 = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_summary);
                    String mobileNetworkSummary2 = internetDetailsContentController.getMobileNetworkSummary(i3);
                    if (!TextUtils.isEmpty(mobileNetworkSummary2)) {
                        textView2.setText(Html.fromHtml(mobileNetworkSummary2, 0));
                        textView2.setBreakStrategy(0);
                        textView2.setTextAppearance(R.style.TextAppearance_InternetDialog_Active);
                    }
                    final ImageView imageView = (ImageView) this.mDialogView.requireViewById(R.id.secondary_signal_icon);
                    this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegateLegacy$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            InternetDialogDelegateLegacy internetDialogDelegateLegacy = InternetDialogDelegateLegacy.this;
                            internetDialogDelegateLegacy.mHandler.post(new InternetDialogDelegateLegacy$$ExternalSyntheticLambda16(imageView, internetDialogDelegateLegacy.mInternetDetailsContentController.getSignalStrengthDrawable(i3), 1));
                        }
                    });
                    ImageView imageView2 = (ImageView) this.mDialogView.requireViewById(R.id.secondary_settings_icon);
                    imageView2.setColorFilter(systemUIDialog.getContext().getColor(R.color.connected_network_primary_color));
                    imageView2.setVisibility(z4 ? 0 : 4);
                    this.mMobileNetworkLayout.setBackground(this.mBackgroundOff);
                    this.mMobileTitleText.setTextAppearance(R.style.TextAppearance_InternetDialog);
                    this.mMobileSummaryText.setTextAppearance(R.style.TextAppearance_InternetDialog_Secondary);
                    this.mSignalIcon.setColorFilter(systemUIDialog.getContext().getColor(R.color.connected_network_secondary_color));
                } else {
                    this.mMobileNetworkLayout.setBackground(z3 ? this.mBackgroundOn : this.mBackgroundOff);
                    this.mMobileTitleText.setTextAppearance(z3 ? 2132018507 : 2132018506);
                    this.mMobileSummaryText.setTextAppearance(i5);
                }
                LinearLayout linearLayout2 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(i4);
                }
                if (internetContent.mIsAirplaneModeEnabled) {
                    this.mAirplaneModeSummaryText.setVisibility(0);
                    this.mAirplaneModeSummaryText.setText(systemUIDialog.getContext().getText(R.string.airplane_mode));
                    this.mAirplaneModeSummaryText.setTextAppearance(i5);
                } else {
                    this.mAirplaneModeSummaryText.setVisibility(8);
                }
            } else {
                this.mMobileNetworkLayout.setVisibility(8);
                LinearLayout linearLayout3 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(8);
                }
            }
        }
        if (this.mCanConfigWifi) {
            boolean isChecked = this.mWiFiToggle.isChecked();
            boolean z5 = internetContent.mIsWifiEnabled;
            if (isChecked != z5) {
                this.mWiFiToggle.setChecked(z5);
            }
            if (internetContent.mIsDeviceLocked) {
                TextView textView3 = this.mWifiToggleTitleText;
                if (this.mConnectedWifiEntry == null) {
                    i2 = 2132018506;
                }
                textView3.setTextAppearance(i2);
            }
            Drawable drawable = null;
            this.mTurnWifiOnLayout.setBackground((!internetContent.mIsDeviceLocked || this.mConnectedWifiEntry == null) ? null : this.mBackgroundOn);
            if (!this.mCanChangeWifiState && this.mWiFiToggle.isEnabled()) {
                this.mWiFiToggle.setEnabled(false);
                this.mWifiToggleTitleText.setEnabled(false);
                TextView textView4 = (TextView) this.mDialogView.requireViewById(R.id.wifi_toggle_summary);
                textView4.setEnabled(false);
                textView4.setVisibility(0);
            }
            if (this.mDialog == null || !internetContent.mIsWifiEnabled || this.mConnectedWifiEntry == null || internetContent.mIsDeviceLocked) {
                this.mConnectedWifListLayout.setVisibility(8);
                this.mShareWifiButton.setVisibility(8);
            } else {
                this.mConnectedWifListLayout.setVisibility(0);
                this.mConnectedWifiTitleText.setText(this.mConnectedWifiEntry.getTitle());
                this.mConnectedWifiSummaryText.setText(this.mConnectedWifiEntry.getSummary(false));
                ImageView imageView3 = this.mConnectedWifiIcon;
                Drawable wifiDrawable = internetDetailsContentController.getWifiDrawable(this.mConnectedWifiEntry);
                if (wifiDrawable != null) {
                    wifiDrawable.setTint(internetDetailsContentController.mContext.getColor(R.color.connected_network_primary_color));
                    drawable = wifiDrawable;
                }
                imageView3.setImageDrawable(drawable);
                this.mWifiSettingsIcon.setColorFilter(this.mDialog.getContext().getColor(R.color.connected_network_primary_color));
                if (internetDetailsContentController.getConfiguratorQrCodeGeneratorIntentOrNull(this.mConnectedWifiEntry) != null) {
                    this.mShareWifiButton.setVisibility(0);
                    i = 8;
                } else {
                    i = 8;
                    this.mShareWifiButton.setVisibility(8);
                }
                LinearLayout linearLayout4 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(i);
                }
            }
            if (!internetContent.mIsWifiEnabled || internetContent.mIsDeviceLocked) {
                this.mWifiRecyclerView.setVisibility(8);
                this.mSeeAllLayout.setVisibility(8);
            } else {
                int wifiListMaxCount = getWifiListMaxCount();
                InternetAdapter internetAdapter = this.mAdapter;
                int i6 = internetAdapter.mWifiEntriesCount;
                if (i6 > wifiListMaxCount) {
                    this.mHasMoreWifiEntries = true;
                }
                if (wifiListMaxCount >= 0 && internetAdapter.mMaxEntriesCount != wifiListMaxCount) {
                    internetAdapter.mMaxEntriesCount = wifiListMaxCount;
                    if (i6 > wifiListMaxCount) {
                        internetAdapter.mWifiEntriesCount = wifiListMaxCount;
                        internetAdapter.notifyDataSetChanged();
                    }
                }
                int i7 = this.mWifiNetworkHeight * wifiListMaxCount;
                if (this.mWifiRecyclerView.getMinimumHeight() != i7) {
                    this.mWifiRecyclerView.setMinimumHeight(i7);
                }
                this.mWifiRecyclerView.setVisibility(0);
                this.mSeeAllLayout.setVisibility(this.mHasMoreWifiEntries ? 0 : 4);
            }
            if (this.mDialog == null || internetContent.mIsWifiEnabled || !internetContent.mIsWifiScanEnabled || internetContent.mIsDeviceLocked) {
                this.mWifiScanNotifyLayout.setVisibility(8);
                return;
            }
            if (TextUtils.isEmpty(this.mWifiScanNotifyText.getText())) {
                Objects.requireNonNull(internetDetailsContentController);
                this.mWifiScanNotifyText.setText(AnnotationLinkSpan.linkify(this.mDialog.getContext().getText(R.string.wifi_scan_notify_message), new AnnotationLinkSpan.LinkInfo("link", new InternetDialogDelegateLegacy$$ExternalSyntheticLambda11(internetDetailsContentController, 1))));
                this.mWifiScanNotifyText.setMovementMethod(LinkMovementMethod.getInstance());
            }
            this.mWifiScanNotifyLayout.setVisibility(0);
        }
    }
}
