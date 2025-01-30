package com.android.systemui.p016qs.tiles.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.Annotation;
import android.text.BidiFormatter;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda1;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p016qs.tiles.dialog.InternetDialogController;
import com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetOnSubscriptionChangedListener;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.wifitrackerlib.WifiEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InternetDialog extends SystemUIDialog implements InternetDialogController.InternetDialogCallback, Window.Callback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialog", 3);
    protected InternetAdapter mAdapter;
    public Button mAirplaneModeButton;
    public TextView mAirplaneModeSummaryText;
    public AlertDialog mAlertDialog;
    public final Executor mBackgroundExecutor;
    public Drawable mBackgroundOff;
    public Drawable mBackgroundOn;
    public final boolean mCanChangeWifiState;
    public final boolean mCanConfigMobileData;
    protected boolean mCanConfigWifi;
    public LinearLayout mConnectedWifListLayout;
    protected WifiEntry mConnectedWifiEntry;
    public ImageView mConnectedWifiIcon;
    public TextView mConnectedWifiSummaryText;
    public TextView mConnectedWifiTitleText;
    public final Context mContext;
    public int mDefaultDataSubId;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    protected View mDialogView;
    public View mDivider;
    public Button mDoneButton;
    public LinearLayout mEthernetLayout;
    public final Handler mHandler;
    protected boolean mHasMoreWifiEntries;
    public final InternetDialog$$ExternalSyntheticLambda0 mHideProgressBarRunnable;
    public final InternetDialog$$ExternalSyntheticLambda0 mHideSearchingRunnable;
    public final InternetDialogController mInternetDialogController;
    public final InternetDialogFactory mInternetDialogFactory;
    public TextView mInternetDialogSubTitle;
    public TextView mInternetDialogTitle;
    public boolean mIsProgressBarVisible;
    public boolean mIsSearchingHidden;
    public final KeyguardStateController mKeyguard;
    public Switch mMobileDataToggle;
    public LinearLayout mMobileNetworkLayout;
    public TextView mMobileSummaryText;
    public TextView mMobileTitleText;
    public View mMobileToggleDivider;
    public ProgressBar mProgressBar;
    public LinearLayout mSecondaryMobileNetworkLayout;
    public TextView mSecondaryMobileSummaryText;
    public TextView mSecondaryMobileTitleText;
    public LinearLayout mSeeAllLayout;
    public ImageView mSignalIcon;
    public TelephonyManager mTelephonyManager;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum InternetDialogEvent implements UiEventLogger.UiEventEnum {
        INTERNET_DIALOG_SHOW(843);

        private final int mId;

        InternetDialogEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public InternetDialog(Context context, InternetDialogFactory internetDialogFactory, InternetDialogController internetDialogController, boolean z, boolean z2, boolean z3, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, Handler handler, Executor executor, KeyguardStateController keyguardStateController) {
        super(context);
        boolean z4;
        this.mBackgroundOff = null;
        this.mDefaultDataSubId = -1;
        this.mHideProgressBarRunnable = new InternetDialog$$ExternalSyntheticLambda0(this, 2);
        this.mHideSearchingRunnable = new InternetDialog$$ExternalSyntheticLambda0(this, 3);
        if (DEBUG) {
            Log.d("InternetDialog", "Init InternetDialog");
        }
        this.mContext = getContext();
        this.mHandler = handler;
        this.mBackgroundExecutor = executor;
        this.mInternetDialogFactory = internetDialogFactory;
        this.mInternetDialogController = internetDialogController;
        internetDialogController.getClass();
        this.mDefaultDataSubId = internetDialogController.getDefaultDataSubscriptionId();
        this.mTelephonyManager = internetDialogController.mTelephonyManager;
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
        this.mUiEventLogger = uiEventLogger;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mAdapter = new InternetAdapter(internetDialogController);
        if (z3) {
            return;
        }
        getWindow().setType(2038);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getMobileNetworkSummary(int i) {
        String string;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        MobileMappings.Config config = internetDialogController.mConfig;
        TelephonyDisplayInfo orDefault = internetDialogController.mSubIdTelephonyDisplayInfoMap.getOrDefault(Integer.valueOf(i), InternetDialogController.DEFAULT_TELEPHONY_DISPLAY_INFO);
        String num = orDefault.getOverrideNetworkType() == 0 ? Integer.toString(orDefault.getNetworkType()) : MobileMappings.toDisplayIconKey(orDefault.getOverrideNetworkType());
        MobileMappings.mapIconSets(config);
        Object obj = ((HashMap) MobileMappings.mapIconSets(config)).get(num);
        Context context = internetDialogController.mContext;
        if (obj != null) {
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) ((HashMap) MobileMappings.mapIconSets(config)).get(num);
            Objects.requireNonNull(signalIcon$MobileIconGroup);
            int i2 = internetDialogController.isCarrierNetworkActive() ? TelephonyIcons.CARRIER_MERGED_WIFI.dataContentDescription : internetDialogController.mCarrierNetworkChangeMode ? TelephonyIcons.CARRIER_NETWORK_CHANGE.dataContentDescription : signalIcon$MobileIconGroup.dataContentDescription;
            if (i2 != 0) {
                string = SubscriptionManager.getResourcesForSubId(context, i).getString(i2);
                if (internetDialogController.isMobileDataEnabled()) {
                    return context.getString(R.string.mobile_data_off_summary);
                }
                boolean z = i == internetDialogController.mDefaultDataSubId;
                boolean z2 = internetDialogController.getActiveAutoSwitchNonDdsSubId() != -1;
                if (internetDialogController.activeNetworkIsCellular() || internetDialogController.isCarrierNetworkActive()) {
                    Object[] objArr = new Object[2];
                    objArr[0] = context.getString(z ? z2 ? R.string.mobile_data_poor_connection : R.string.mobile_data_connection_active : R.string.mobile_data_temp_connection_active);
                    objArr[1] = string;
                    string = context.getString(R.string.preference_summary_default_combination, objArr);
                } else if (!internetDialogController.isDataStateInService(i)) {
                    string = context.getString(R.string.mobile_data_no_connection);
                }
                return string;
            }
        } else if (InternetDialogController.DEBUG) {
            Log.d("InternetDialogController", "The description of network type is empty.");
        }
        string = "";
        if (internetDialogController.isMobileDataEnabled()) {
        }
    }

    public final CharSequence getMobileNetworkTitle(int i) {
        final InternetDialogController internetDialogController = this.mInternetDialogController;
        internetDialogController.getClass();
        Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                InternetDialogController internetDialogController2 = InternetDialogController.this;
                return internetDialogController2.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo().stream().filter(new InternetDialogController$$ExternalSyntheticLambda6()).map(new InternetDialogController$$ExternalSyntheticLambda4(internetDialogController2, 1));
            }
        };
        final HashSet hashSet = new HashSet();
        final int i2 = 0;
        final Set set = (Set) ((Stream) supplier.get()).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean add;
                switch (i2) {
                    case 0:
                        add = hashSet.add(((InternetDialogController.C1DisplayInfo) obj).originalName);
                        break;
                    default:
                        add = hashSet.add(((InternetDialogController.C1DisplayInfo) obj).uniqueName);
                        break;
                }
                return !add;
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        return ((InternetDialogController.C1DisplayInfo) obj).originalName;
                    case 1:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                    case 2:
                        return Integer.valueOf(((InternetDialogController.C1DisplayInfo) obj).subscriptionInfo.getSubscriptionId());
                    default:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                }
            }
        }).collect(Collectors.toSet());
        hashSet.clear();
        Stream stream = (Stream) supplier.get();
        final Context context = internetDialogController.mContext;
        final int i3 = 1;
        Stream map = ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda5
            /* JADX WARN: Removed duplicated region for block: B:10:0x0040  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0059  */
            /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
            /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                String str;
                String unicodeWrap;
                Set set2 = set;
                Context context2 = context;
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                boolean contains = set2.contains(c1DisplayInfo.originalName);
                CharSequence charSequence = c1DisplayInfo.originalName;
                if (contains) {
                    SubscriptionInfo subscriptionInfo = c1DisplayInfo.subscriptionInfo;
                    if (subscriptionInfo != null) {
                        String line1Number = ((TelephonyManager) context2.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getLine1Number();
                        if (!TextUtils.isEmpty(line1Number)) {
                            str = PhoneNumberUtils.formatNumber(line1Number);
                            unicodeWrap = BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.LTR);
                            if (unicodeWrap != null) {
                                unicodeWrap = "";
                            } else if (unicodeWrap.length() > 4) {
                                unicodeWrap = unicodeWrap.substring(unicodeWrap.length() - 4);
                            }
                            if (TextUtils.isEmpty(unicodeWrap)) {
                                c1DisplayInfo.uniqueName = ((Object) charSequence) + " " + unicodeWrap;
                            } else {
                                c1DisplayInfo.uniqueName = charSequence;
                            }
                        }
                    }
                    str = null;
                    unicodeWrap = BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.LTR);
                    if (unicodeWrap != null) {
                    }
                    if (TextUtils.isEmpty(unicodeWrap)) {
                    }
                } else {
                    c1DisplayInfo.uniqueName = charSequence;
                }
                return c1DisplayInfo;
            }
        }).map(new InternetDialogController$$ExternalSyntheticLambda4((Set) stream.map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda5
            /* JADX WARN: Removed duplicated region for block: B:10:0x0040  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0059  */
            /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
            /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object apply(Object obj) {
                String str;
                String unicodeWrap;
                Set set2 = set;
                Context context2 = context;
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                boolean contains = set2.contains(c1DisplayInfo.originalName);
                CharSequence charSequence = c1DisplayInfo.originalName;
                if (contains) {
                    SubscriptionInfo subscriptionInfo = c1DisplayInfo.subscriptionInfo;
                    if (subscriptionInfo != null) {
                        String line1Number = ((TelephonyManager) context2.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).getLine1Number();
                        if (!TextUtils.isEmpty(line1Number)) {
                            str = PhoneNumberUtils.formatNumber(line1Number);
                            unicodeWrap = BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.LTR);
                            if (unicodeWrap != null) {
                                unicodeWrap = "";
                            } else if (unicodeWrap.length() > 4) {
                                unicodeWrap = unicodeWrap.substring(unicodeWrap.length() - 4);
                            }
                            if (TextUtils.isEmpty(unicodeWrap)) {
                                c1DisplayInfo.uniqueName = ((Object) charSequence) + " " + unicodeWrap;
                            } else {
                                c1DisplayInfo.uniqueName = charSequence;
                            }
                        }
                    }
                    str = null;
                    unicodeWrap = BidiFormatter.getInstance().unicodeWrap(str, TextDirectionHeuristics.LTR);
                    if (unicodeWrap != null) {
                    }
                    if (TextUtils.isEmpty(unicodeWrap)) {
                    }
                } else {
                    c1DisplayInfo.uniqueName = charSequence;
                }
                return c1DisplayInfo;
            }
        }).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean add;
                switch (i3) {
                    case 0:
                        add = hashSet.add(((InternetDialogController.C1DisplayInfo) obj).originalName);
                        break;
                    default:
                        add = hashSet.add(((InternetDialogController.C1DisplayInfo) obj).uniqueName);
                        break;
                }
                return !add;
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i3) {
                    case 0:
                        return ((InternetDialogController.C1DisplayInfo) obj).originalName;
                    case 1:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                    case 2:
                        return Integer.valueOf(((InternetDialogController.C1DisplayInfo) obj).subscriptionInfo.getSubscriptionId());
                    default:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                }
            }
        }).collect(Collectors.toSet()), i2));
        final int i4 = 2;
        final int i5 = 3;
        return (CharSequence) ((Map) map.collect(Collectors.toMap(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i4) {
                    case 0:
                        return ((InternetDialogController.C1DisplayInfo) obj).originalName;
                    case 1:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                    case 2:
                        return Integer.valueOf(((InternetDialogController.C1DisplayInfo) obj).subscriptionInfo.getSubscriptionId());
                    default:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                }
            }
        }, new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i5) {
                    case 0:
                        return ((InternetDialogController.C1DisplayInfo) obj).originalName;
                    case 1:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                    case 2:
                        return Integer.valueOf(((InternetDialogController.C1DisplayInfo) obj).subscriptionInfo.getSubscriptionId());
                    default:
                        return ((InternetDialogController.C1DisplayInfo) obj).uniqueName;
                }
            }
        }))).getOrDefault(Integer.valueOf(i), "");
    }

    public final Drawable getSignalStrengthDrawable(int i) {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        Context context = internetDialogController.mContext;
        Drawable drawable = context.getDrawable(R.drawable.ic_signal_strength_zero_bar_no_internet);
        try {
            if (internetDialogController.mTelephonyManager != null) {
                boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
                if (internetDialogController.isDataStateInService(i) || internetDialogController.isVoiceStateInService(i) || isCarrierNetworkActive) {
                    AtomicReference atomicReference = new AtomicReference();
                    atomicReference.set(internetDialogController.getSignalStrengthDrawableWithLevel(i, isCarrierNetworkActive));
                    drawable = (Drawable) atomicReference.get();
                }
                int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(android.R.attr.textColorTertiary, context, 0);
                if (internetDialogController.activeNetworkIsCellular() || isCarrierNetworkActive) {
                    colorAttrDefaultColor = context.getColor(R.color.connected_network_primary_color);
                }
                drawable.setTint(colorAttrDefaultColor);
            } else if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null");
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return drawable;
    }

    public final CharSequence getSubtitleText() {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean z = this.mIsProgressBarVisible && !this.mIsSearchingHidden;
        boolean z2 = internetDialogController.mCanConfigWifi;
        boolean z3 = InternetDialogController.DEBUG;
        Context context = internetDialogController.mContext;
        if (z2 && !internetDialogController.isWifiEnabled()) {
            if (z3) {
                Log.d("InternetDialogController", "Wi-Fi off.");
            }
            return context.getText(InternetDialogController.SUBTITLE_TEXT_WIFI_IS_OFF);
        }
        if (!internetDialogController.mKeyguardStateController.isUnlocked()) {
            if (z3) {
                Log.d("InternetDialogController", "The device is locked.");
            }
            return context.getText(InternetDialogController.SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        }
        if (internetDialogController.mHasWifiEntries) {
            if (internetDialogController.mCanConfigWifi) {
                return context.getText(InternetDialogController.SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
            }
        } else {
            if (internetDialogController.mCanConfigWifi && z) {
                return context.getText(InternetDialogController.SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
            }
            boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
            int i = InternetDialogController.SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE;
            if (isCarrierNetworkActive) {
                return context.getText(i);
            }
            if (z3) {
                Log.d("InternetDialogController", "No Wi-Fi item.");
            }
            boolean z4 = internetDialogController.getActiveAutoSwitchNonDdsSubId() != -1;
            boolean hasActiveSubId = internetDialogController.hasActiveSubId();
            int i2 = InternetDialogController.SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE;
            if (!hasActiveSubId || (!internetDialogController.isVoiceStateInService(internetDialogController.mDefaultDataSubId) && !internetDialogController.isDataStateInService(internetDialogController.mDefaultDataSubId) && !z4)) {
                if (z3) {
                    Log.d("InternetDialogController", "No carrier or service is out of service.");
                }
                return context.getText(i2);
            }
            if (internetDialogController.mCanConfigWifi && !internetDialogController.isMobileDataEnabled()) {
                if (z3) {
                    Log.d("InternetDialogController", "Mobile data off");
                }
                return context.getText(i);
            }
            if (!internetDialogController.activeNetworkIsCellular()) {
                if (z3) {
                    Log.d("InternetDialogController", "No carrier data.");
                }
                return context.getText(i2);
            }
            if (internetDialogController.mCanConfigWifi) {
                return context.getText(i);
            }
        }
        return null;
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
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (DEBUG) {
            Log.d("InternetDialog", "onCreate");
        }
        this.mUiEventLogger.log(InternetDialogEvent.INTERNET_DIALOG_SHOW);
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.internet_connectivity_dialog, (ViewGroup) null);
        this.mDialogView = inflate;
        inflate.setAccessibilityPaneTitle(this.mContext.getText(R.string.accessibility_desc_quick_settings));
        Window window = getWindow();
        window.setContentView(this.mDialogView);
        window.setWindowAnimations(2132017164);
        this.mWifiNetworkHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.internet_dialog_wifi_network_height);
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
        this.mAirplaneModeButton = (Button) this.mDialogView.requireViewById(R.id.apm_button);
        this.mSignalIcon = (ImageView) this.mDialogView.requireViewById(R.id.signal_icon);
        this.mMobileTitleText = (TextView) this.mDialogView.requireViewById(R.id.mobile_title);
        this.mMobileSummaryText = (TextView) this.mDialogView.requireViewById(R.id.mobile_summary);
        this.mAirplaneModeSummaryText = (TextView) this.mDialogView.requireViewById(R.id.airplane_mode_summary);
        this.mMobileToggleDivider = this.mDialogView.requireViewById(R.id.mobile_toggle_divider);
        this.mMobileDataToggle = (Switch) this.mDialogView.requireViewById(R.id.mobile_toggle);
        this.mWiFiToggle = (Switch) this.mDialogView.requireViewById(R.id.wifi_toggle);
        this.mBackgroundOn = this.mContext.getDrawable(R.drawable.settingslib_switch_bar_bg_on);
        TextView textView = this.mInternetDialogTitle;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean isAirplaneModeEnabled = internetDialogController.isAirplaneModeEnabled();
        Context context = internetDialogController.mContext;
        textView.setText(isAirplaneModeEnabled ? context.getText(R.string.airplane_mode) : context.getText(R.string.quick_settings_internet_label));
        this.mInternetDialogTitle.setGravity(8388627);
        this.mBackgroundOff = this.mContext.getDrawable(R.drawable.internet_dialog_selected_effect);
        this.mMobileNetworkLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, r2));
        this.mMobileDataToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ InternetDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
                switch (r2) {
                    case 0:
                        final InternetDialog internetDialog = this.f$0;
                        final int i = 0;
                        if (!z) {
                            if (internetDialog.mInternetDialogController.isMobileDataEnabled() && !Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData", false)) {
                                CharSequence mobileNetworkTitle = internetDialog.getMobileNetworkTitle(internetDialog.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDialog.mInternetDialogController.isVoiceStateInService(internetDialog.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle) || !isVoiceStateInService) {
                                    mobileNetworkTitle = internetDialog.mContext.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create = new AlertDialog.Builder(internetDialog.mContext).setTitle(R.string.mobile_data_disable_title).setMessage(internetDialog.mContext.getString(R.string.mobile_data_disable_message, mobileNetworkTitle)).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        switch (i) {
                                            case 0:
                                                internetDialog.mMobileDataToggle.setChecked(true);
                                                break;
                                            default:
                                                InternetDialog internetDialog2 = internetDialog;
                                                internetDialog2.mInternetDialogController.setMobileDataEnabled(internetDialog2.mDefaultDataSubId, internetDialog2.mContext, false);
                                                internetDialog2.mMobileDataToggle.setChecked(false);
                                                Prefs.putBoolean(internetDialog2.mContext, "QsHasTurnedOffMobileData", true);
                                                break;
                                        }
                                    }
                                }).setPositiveButton(android.R.string.bugreport_screenshot_success_toast, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        switch (r2) {
                                            case 0:
                                                internetDialog.mMobileDataToggle.setChecked(true);
                                                break;
                                            default:
                                                InternetDialog internetDialog2 = internetDialog;
                                                internetDialog2.mInternetDialogController.setMobileDataEnabled(internetDialog2.mDefaultDataSubId, internetDialog2.mContext, false);
                                                internetDialog2.mMobileDataToggle.setChecked(false);
                                                Prefs.putBoolean(internetDialog2.mContext, "QsHasTurnedOffMobileData", true);
                                                break;
                                        }
                                    }
                                }).create();
                                internetDialog.mAlertDialog = create;
                                create.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda5
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        InternetDialog.this.mMobileDataToggle.setChecked(true);
                                    }
                                });
                                internetDialog.mAlertDialog.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialog.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialog.mAlertDialog, null);
                                SystemUIDialog.setWindowOnTop(internetDialog.mAlertDialog, ((KeyguardStateControllerImpl) internetDialog.mKeyguard).mShowing);
                                internetDialog.mDialogLaunchAnimator.showFromDialog(internetDialog.mAlertDialog, internetDialog, null, false);
                                break;
                            }
                        }
                        if (((!internetDialog.mInternetDialogController.isMobileDataEnabled() || Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData", false)) ? 0 : 1) == 0 && internetDialog.mInternetDialogController.isMobileDataEnabled() != z) {
                            internetDialog.mInternetDialogController.setMobileDataEnabled(internetDialog.mDefaultDataSubId, internetDialog.mContext, z);
                            break;
                        }
                        break;
                    default:
                        InternetDialog internetDialog2 = this.f$0;
                        if (internetDialog2.mInternetDialogController.isWifiEnabled() != z) {
                            final WifiStateWorker wifiStateWorker = internetDialog2.mInternetDialogController.mWifiStateWorker;
                            ((ExecutorImpl) wifiStateWorker.mBackgroundExecutor).execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WifiStateWorker wifiStateWorker2 = WifiStateWorker.this;
                                    boolean z2 = z;
                                    WifiManager wifiManager = wifiStateWorker2.mWifiManager;
                                    if (wifiManager == null) {
                                        return;
                                    }
                                    wifiStateWorker2.mWifiState = z2 ? 2 : 0;
                                    if (wifiManager.setWifiEnabled(z2)) {
                                        return;
                                    }
                                    Log.e("WifiStateWorker", "Failed to WifiManager.setWifiEnabled(" + z2 + ");");
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        });
        final int i = 1;
        this.mConnectedWifListLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, i));
        this.mSeeAllLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, 2));
        this.mWiFiToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda2
            public final /* synthetic */ InternetDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, final boolean z) {
                switch (i) {
                    case 0:
                        final InternetDialog internetDialog = this.f$0;
                        final int i2 = 0;
                        if (!z) {
                            if (internetDialog.mInternetDialogController.isMobileDataEnabled() && !Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData", false)) {
                                CharSequence mobileNetworkTitle = internetDialog.getMobileNetworkTitle(internetDialog.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDialog.mInternetDialogController.isVoiceStateInService(internetDialog.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle) || !isVoiceStateInService) {
                                    mobileNetworkTitle = internetDialog.mContext.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create = new AlertDialog.Builder(internetDialog.mContext).setTitle(R.string.mobile_data_disable_title).setMessage(internetDialog.mContext.getString(R.string.mobile_data_disable_message, mobileNetworkTitle)).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i22) {
                                        switch (i2) {
                                            case 0:
                                                internetDialog.mMobileDataToggle.setChecked(true);
                                                break;
                                            default:
                                                InternetDialog internetDialog2 = internetDialog;
                                                internetDialog2.mInternetDialogController.setMobileDataEnabled(internetDialog2.mDefaultDataSubId, internetDialog2.mContext, false);
                                                internetDialog2.mMobileDataToggle.setChecked(false);
                                                Prefs.putBoolean(internetDialog2.mContext, "QsHasTurnedOffMobileData", true);
                                                break;
                                        }
                                    }
                                }).setPositiveButton(android.R.string.bugreport_screenshot_success_toast, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i22) {
                                        switch (r2) {
                                            case 0:
                                                internetDialog.mMobileDataToggle.setChecked(true);
                                                break;
                                            default:
                                                InternetDialog internetDialog2 = internetDialog;
                                                internetDialog2.mInternetDialogController.setMobileDataEnabled(internetDialog2.mDefaultDataSubId, internetDialog2.mContext, false);
                                                internetDialog2.mMobileDataToggle.setChecked(false);
                                                Prefs.putBoolean(internetDialog2.mContext, "QsHasTurnedOffMobileData", true);
                                                break;
                                        }
                                    }
                                }).create();
                                internetDialog.mAlertDialog = create;
                                create.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda5
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        InternetDialog.this.mMobileDataToggle.setChecked(true);
                                    }
                                });
                                internetDialog.mAlertDialog.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialog.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialog.mAlertDialog, null);
                                SystemUIDialog.setWindowOnTop(internetDialog.mAlertDialog, ((KeyguardStateControllerImpl) internetDialog.mKeyguard).mShowing);
                                internetDialog.mDialogLaunchAnimator.showFromDialog(internetDialog.mAlertDialog, internetDialog, null, false);
                                break;
                            }
                        }
                        if (((!internetDialog.mInternetDialogController.isMobileDataEnabled() || Prefs.getBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData", false)) ? 0 : 1) == 0 && internetDialog.mInternetDialogController.isMobileDataEnabled() != z) {
                            internetDialog.mInternetDialogController.setMobileDataEnabled(internetDialog.mDefaultDataSubId, internetDialog.mContext, z);
                            break;
                        }
                        break;
                    default:
                        InternetDialog internetDialog2 = this.f$0;
                        if (internetDialog2.mInternetDialogController.isWifiEnabled() != z) {
                            final WifiStateWorker wifiStateWorker = internetDialog2.mInternetDialogController.mWifiStateWorker;
                            ((ExecutorImpl) wifiStateWorker.mBackgroundExecutor).execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WifiStateWorker wifiStateWorker2 = WifiStateWorker.this;
                                    boolean z2 = z;
                                    WifiManager wifiManager = wifiStateWorker2.mWifiManager;
                                    if (wifiManager == null) {
                                        return;
                                    }
                                    wifiStateWorker2.mWifiState = z2 ? 2 : 0;
                                    if (wifiManager.setWifiEnabled(z2)) {
                                        return;
                                    }
                                    Log.e("WifiStateWorker", "Failed to WifiManager.setWifiEnabled(" + z2 + ");");
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        });
        this.mDoneButton.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, 3));
        this.mAirplaneModeButton.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, 4));
        this.mTurnWifiOnLayout.setBackground(null);
        this.mAirplaneModeButton.setVisibility(this.mInternetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        this.mWifiRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mWifiRecyclerView.setAdapter(this.mAdapter);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || alertDialog.isShowing() || z || !isShowing()) {
            return;
        }
        dismiss();
    }

    public final void setProgressBarVisible(boolean z) {
        if (this.mIsProgressBarVisible == z) {
            return;
        }
        this.mIsProgressBarVisible = z;
        this.mProgressBar.setVisibility(z ? 0 : 8);
        this.mProgressBar.setIndeterminate(z);
        this.mDivider.setVisibility(z ? 8 : 0);
        this.mInternetDialogSubTitle.setText(getSubtitleText());
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        if (DEBUG) {
            Log.d("InternetDialog", "onStart");
        }
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean z = this.mCanConfigWifi;
        boolean z2 = InternetDialogController.DEBUG;
        if (z2) {
            internetDialogController.getClass();
            Log.d("InternetDialogController", "onStart");
        }
        internetDialogController.mCallback = this;
        internetDialogController.mKeyguardUpdateMonitor.registerCallback(internetDialogController.mKeyguardUpdateCallback);
        AccessPointController accessPointController = internetDialogController.mAccessPointController;
        ((AccessPointControllerImpl) accessPointController).addAccessPointCallback(internetDialogController);
        InternetDialogController.C23062 c23062 = internetDialogController.mConnectionStateReceiver;
        IntentFilter intentFilter = internetDialogController.mConnectionStateFilter;
        Executor executor = internetDialogController.mExecutor;
        BroadcastDispatcher broadcastDispatcher = internetDialogController.mBroadcastDispatcher;
        broadcastDispatcher.getClass();
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, c23062, intentFilter, executor, null, 0, null, 56);
        InternetDialogController.InternetOnSubscriptionChangedListener internetOnSubscriptionChangedListener = internetDialogController.new InternetOnSubscriptionChangedListener();
        internetDialogController.mOnSubscriptionsChangedListener = internetOnSubscriptionChangedListener;
        SubscriptionManager subscriptionManager = internetDialogController.mSubscriptionManager;
        Executor executor2 = internetDialogController.mExecutor;
        subscriptionManager.addOnSubscriptionsChangedListener(executor2, internetOnSubscriptionChangedListener);
        internetDialogController.mDefaultDataSubId = internetDialogController.getDefaultDataSubscriptionId();
        if (z2) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Init, SubId: "), internetDialogController.mDefaultDataSubId, "InternetDialogController");
        }
        internetDialogController.mConfig = MobileMappings.Config.readConfig(internetDialogController.mContext);
        internetDialogController.mTelephonyManager = internetDialogController.mTelephonyManager.createForSubscriptionId(internetDialogController.mDefaultDataSubId);
        internetDialogController.mSubIdTelephonyManagerMap.put(Integer.valueOf(internetDialogController.mDefaultDataSubId), internetDialogController.mTelephonyManager);
        InternetDialogController.InternetTelephonyCallback internetTelephonyCallback = new InternetDialogController.InternetTelephonyCallback(internetDialogController, internetDialogController.mDefaultDataSubId, 0);
        internetDialogController.mSubIdTelephonyCallbackMap.put(Integer.valueOf(internetDialogController.mDefaultDataSubId), internetTelephonyCallback);
        internetDialogController.mTelephonyManager.registerTelephonyCallback(executor2, internetTelephonyCallback);
        internetDialogController.mConnectivityManager.registerDefaultNetworkCallback(internetDialogController.mConnectivityManagerNetworkCallback);
        internetDialogController.mCanConfigWifi = z;
        if (z) {
            ((AccessPointControllerImpl) accessPointController).scanForAccessPoints();
        }
        if (this.mCanConfigWifi) {
            return;
        }
        hideWifiViews();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        if (DEBUG) {
            Log.d("InternetDialog", "onStop");
        }
        this.mHandler.removeCallbacks(this.mHideProgressBarRunnable);
        this.mHandler.removeCallbacks(this.mHideSearchingRunnable);
        this.mMobileNetworkLayout.setOnClickListener(null);
        this.mMobileDataToggle.setOnCheckedChangeListener(null);
        this.mConnectedWifListLayout.setOnClickListener(null);
        LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(null);
        }
        this.mSeeAllLayout.setOnClickListener(null);
        this.mWiFiToggle.setOnCheckedChangeListener(null);
        this.mDoneButton.setOnClickListener(null);
        this.mAirplaneModeButton.setOnClickListener(null);
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean z = InternetDialogController.DEBUG;
        if (z) {
            internetDialogController.getClass();
            Log.d("InternetDialogController", "onStop");
        }
        internetDialogController.mBroadcastDispatcher.unregisterReceiver(internetDialogController.mConnectionStateReceiver);
        for (TelephonyManager telephonyManager : internetDialogController.mSubIdTelephonyManagerMap.values()) {
            TelephonyCallback telephonyCallback = internetDialogController.mSubIdTelephonyCallbackMap.get(Integer.valueOf(telephonyManager.getSubscriptionId()));
            if (telephonyCallback != null) {
                telephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (z) {
                Log.e("InternetDialogController", "Unexpected null telephony call back for Sub " + telephonyManager.getSubscriptionId());
            }
        }
        internetDialogController.mSubIdTelephonyManagerMap.clear();
        internetDialogController.mSubIdTelephonyCallbackMap.clear();
        internetDialogController.mSubIdTelephonyDisplayInfoMap.clear();
        internetDialogController.mSubscriptionManager.removeOnSubscriptionsChangedListener(internetDialogController.mOnSubscriptionsChangedListener);
        ((AccessPointControllerImpl) internetDialogController.mAccessPointController).removeAccessPointCallback(internetDialogController);
        internetDialogController.mKeyguardUpdateMonitor.removeCallback(internetDialogController.mKeyguardUpdateCallback);
        internetDialogController.mConnectivityManager.unregisterNetworkCallback(internetDialogController.mConnectivityManagerNetworkCallback);
        InternetDialogController.ConnectedWifiInternetMonitor connectedWifiInternetMonitor = internetDialogController.mConnectedWifiInternetMonitor;
        WifiEntry wifiEntry = connectedWifiInternetMonitor.mWifiEntry;
        if (wifiEntry != null) {
            synchronized (wifiEntry) {
                wifiEntry.mListener = null;
            }
            connectedWifiInternetMonitor.mWifiEntry = null;
        }
        this.mInternetDialogFactory.getClass();
        if (InternetDialogFactoryKt.DEBUG) {
            Log.d("InternetDialogFactory", "destroyDialog");
        }
        InternetDialogFactory.internetDialog = null;
    }

    public final void updateDialog(boolean z) {
        WifiManager wifiManager;
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("InternetDialog", "updateDialog");
        }
        TextView textView = this.mInternetDialogTitle;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean isAirplaneModeEnabled = internetDialogController.isAirplaneModeEnabled();
        Context context = internetDialogController.mContext;
        textView.setText(isAirplaneModeEnabled ? context.getText(R.string.airplane_mode) : context.getText(R.string.quick_settings_internet_label));
        this.mInternetDialogSubTitle.setText(getSubtitleText());
        this.mAirplaneModeButton.setVisibility(this.mInternetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        this.mEthernetLayout.setVisibility(this.mInternetDialogController.mHasEthernet ? 0 : 8);
        if (z) {
            boolean activeNetworkIsCellular = this.mInternetDialogController.activeNetworkIsCellular();
            boolean isCarrierNetworkActive = this.mInternetDialogController.isCarrierNetworkActive();
            boolean z3 = activeNetworkIsCellular || isCarrierNetworkActive;
            if (z2) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setMobileDataLayout, isCarrierNetworkActive = ", isCarrierNetworkActive, "InternetDialog");
            }
            boolean isWifiEnabled = this.mInternetDialogController.isWifiEnabled();
            if (this.mInternetDialogController.hasActiveSubId() || (isWifiEnabled && isCarrierNetworkActive)) {
                this.mMobileNetworkLayout.setVisibility(0);
                this.mMobileDataToggle.setChecked(this.mInternetDialogController.isMobileDataEnabled());
                this.mMobileTitleText.setText(getMobileNetworkTitle(this.mDefaultDataSubId));
                String mobileNetworkSummary = getMobileNetworkSummary(this.mDefaultDataSubId);
                if (TextUtils.isEmpty(mobileNetworkSummary)) {
                    this.mMobileSummaryText.setVisibility(8);
                } else {
                    this.mMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary, 0));
                    this.mMobileSummaryText.setBreakStrategy(0);
                    this.mMobileSummaryText.setVisibility(0);
                }
                this.mBackgroundExecutor.execute(new InternetDialog$$ExternalSyntheticLambda0(this, 7));
                this.mMobileDataToggle.setVisibility(this.mCanConfigMobileData ? 0 : 4);
                this.mMobileToggleDivider.setVisibility(this.mCanConfigMobileData ? 0 : 4);
                final int activeAutoSwitchNonDdsSubId = this.mInternetDialogController.getActiveAutoSwitchNonDdsSubId();
                int i = activeAutoSwitchNonDdsSubId != -1 ? 0 : 8;
                int i2 = z3 ? 2132018168 : 2132018167;
                if (i == 0) {
                    ViewStub viewStub = (ViewStub) this.mDialogView.findViewById(R.id.secondary_mobile_network_stub);
                    if (viewStub != null) {
                        viewStub.inflate();
                    }
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.secondary_mobile_network_layout);
                    this.mSecondaryMobileNetworkLayout = linearLayout;
                    linearLayout.setOnClickListener(new InternetDialog$$ExternalSyntheticLambda1(this, 5));
                    this.mSecondaryMobileNetworkLayout.setBackground(this.mBackgroundOn);
                    TextView textView2 = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_title);
                    this.mSecondaryMobileTitleText = textView2;
                    textView2.setText(getMobileNetworkTitle(activeAutoSwitchNonDdsSubId));
                    this.mSecondaryMobileTitleText.setTextAppearance(2132018166);
                    this.mSecondaryMobileSummaryText = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_summary);
                    String mobileNetworkSummary2 = getMobileNetworkSummary(activeAutoSwitchNonDdsSubId);
                    if (!TextUtils.isEmpty(mobileNetworkSummary2)) {
                        this.mSecondaryMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary2, 0));
                        this.mSecondaryMobileSummaryText.setBreakStrategy(0);
                        this.mSecondaryMobileSummaryText.setTextAppearance(2132018166);
                    }
                    final ImageView imageView = (ImageView) this.mDialogView.requireViewById(R.id.secondary_signal_icon);
                    this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            InternetDialog internetDialog = InternetDialog.this;
                            int i3 = activeAutoSwitchNonDdsSubId;
                            internetDialog.mHandler.post(new InternetDialog$$ExternalSyntheticLambda9(imageView, internetDialog.getSignalStrengthDrawable(i3), 0));
                        }
                    });
                    ((ImageView) this.mDialogView.requireViewById(R.id.secondary_settings_icon)).setColorFilter(this.mContext.getColor(R.color.connected_network_primary_color));
                    this.mMobileNetworkLayout.setBackground(this.mBackgroundOff);
                    this.mMobileTitleText.setTextAppearance(2132018165);
                    this.mMobileSummaryText.setTextAppearance(2132018167);
                    this.mSignalIcon.setColorFilter(this.mContext.getColor(R.color.connected_network_secondary_color));
                } else {
                    this.mMobileNetworkLayout.setBackground(z3 ? this.mBackgroundOn : this.mBackgroundOff);
                    this.mMobileTitleText.setTextAppearance(z3 ? 2132018166 : 2132018165);
                    this.mMobileSummaryText.setTextAppearance(i2);
                }
                LinearLayout linearLayout2 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(i);
                }
                if (this.mInternetDialogController.isAirplaneModeEnabled()) {
                    this.mAirplaneModeSummaryText.setVisibility(0);
                    this.mAirplaneModeSummaryText.setText(this.mContext.getText(R.string.airplane_mode));
                    this.mAirplaneModeSummaryText.setTextAppearance(i2);
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
            if (!this.mInternetDialogController.isWifiEnabled() || (!this.mInternetDialogController.mKeyguardStateController.isUnlocked())) {
                setProgressBarVisible(false);
            } else {
                setProgressBarVisible(true);
                if (this.mConnectedWifiEntry != null || this.mWifiEntriesCount > 0) {
                    this.mHandler.postDelayed(this.mHideProgressBarRunnable, 1500L);
                } else if (!this.mIsSearchingHidden) {
                    this.mHandler.postDelayed(this.mHideSearchingRunnable, 1500L);
                }
            }
            boolean z4 = !this.mInternetDialogController.mKeyguardStateController.isUnlocked();
            boolean isWifiEnabled2 = this.mInternetDialogController.isWifiEnabled();
            InternetDialogController internetDialogController2 = this.mInternetDialogController;
            boolean z5 = ((LocationControllerImpl) internetDialogController2.mLocationController).isLocationEnabled() && (wifiManager = internetDialogController2.mWifiManager) != null && wifiManager.isScanAlwaysAvailable();
            if (this.mWiFiToggle.isChecked() != isWifiEnabled2) {
                this.mWiFiToggle.setChecked(isWifiEnabled2);
            }
            if (z4) {
                this.mWifiToggleTitleText.setTextAppearance(this.mConnectedWifiEntry == null ? 2132018165 : 2132018166);
            }
            Drawable drawable = null;
            this.mTurnWifiOnLayout.setBackground((!z4 || this.mConnectedWifiEntry == null) ? null : this.mBackgroundOn);
            if (!this.mCanChangeWifiState && this.mWiFiToggle.isEnabled()) {
                this.mWiFiToggle.setEnabled(false);
                this.mWifiToggleTitleText.setEnabled(false);
                TextView textView3 = (TextView) this.mDialogView.requireViewById(R.id.wifi_toggle_summary);
                textView3.setEnabled(false);
                textView3.setVisibility(0);
            }
            if (!isWifiEnabled2 || this.mConnectedWifiEntry == null || z4) {
                this.mConnectedWifListLayout.setVisibility(8);
            } else {
                this.mConnectedWifListLayout.setVisibility(0);
                this.mConnectedWifiTitleText.setText(this.mConnectedWifiEntry.getTitle());
                this.mConnectedWifiSummaryText.setText(this.mConnectedWifiEntry.getSummary(false));
                ImageView imageView2 = this.mConnectedWifiIcon;
                InternetDialogController internetDialogController3 = this.mInternetDialogController;
                WifiEntry wifiEntry = this.mConnectedWifiEntry;
                internetDialogController3.getClass();
                if (wifiEntry.mLevel != -1) {
                    Drawable icon = internetDialogController3.mWifiIconInjector.getIcon(wifiEntry.mLevel, wifiEntry.shouldShowXLevelIcon());
                    if (icon != null) {
                        icon.setTint(internetDialogController3.mContext.getColor(R.color.connected_network_primary_color));
                        drawable = icon;
                    }
                }
                imageView2.setImageDrawable(drawable);
                this.mWifiSettingsIcon.setColorFilter(this.mContext.getColor(R.color.connected_network_primary_color));
                LinearLayout linearLayout4 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(8);
                }
            }
            if (!isWifiEnabled2 || z4) {
                this.mWifiRecyclerView.setVisibility(8);
                this.mSeeAllLayout.setVisibility(8);
            } else {
                int wifiListMaxCount = getWifiListMaxCount();
                InternetAdapter internetAdapter = this.mAdapter;
                int i3 = internetAdapter.mWifiEntriesCount;
                if (i3 > wifiListMaxCount) {
                    this.mHasMoreWifiEntries = true;
                }
                if (wifiListMaxCount >= 0 && internetAdapter.mMaxEntriesCount != wifiListMaxCount) {
                    internetAdapter.mMaxEntriesCount = wifiListMaxCount;
                    if (i3 > wifiListMaxCount) {
                        internetAdapter.mWifiEntriesCount = wifiListMaxCount;
                        internetAdapter.notifyDataSetChanged();
                    }
                }
                int i4 = this.mWifiNetworkHeight * wifiListMaxCount;
                if (this.mWifiRecyclerView.getMinimumHeight() != i4) {
                    this.mWifiRecyclerView.setMinimumHeight(i4);
                }
                this.mWifiRecyclerView.setVisibility(0);
                this.mSeeAllLayout.setVisibility(this.mHasMoreWifiEntries ? 0 : 4);
            }
            if (isWifiEnabled2 || !z5 || z4) {
                this.mWifiScanNotifyLayout.setVisibility(8);
                return;
            }
            if (TextUtils.isEmpty(this.mWifiScanNotifyText.getText())) {
                InternetDialogController internetDialogController4 = this.mInternetDialogController;
                Objects.requireNonNull(internetDialogController4);
                AnnotationLinkSpan.LinkInfo linkInfo = new AnnotationLinkSpan.LinkInfo("link", new InternetDialog$$ExternalSyntheticLambda1(internetDialogController4, 6));
                TextView textView4 = this.mWifiScanNotifyText;
                CharSequence text = getContext().getText(R.string.wifi_scan_notify_message);
                int i5 = AnnotationLinkSpan.$r8$clinit;
                SpannableString spannableString = new SpannableString(text);
                Annotation[] annotationArr = (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
                Arrays.asList(annotationArr).forEach(new AnnotationLinkSpan$$ExternalSyntheticLambda1(new AnnotationLinkSpan.LinkInfo[]{linkInfo}, spannableStringBuilder, spannableString));
                textView4.setText(spannableStringBuilder);
                this.mWifiScanNotifyText.setMovementMethod(LinkMovementMethod.getInstance());
            }
            this.mWifiScanNotifyLayout.setVisibility(0);
        }
    }
}
