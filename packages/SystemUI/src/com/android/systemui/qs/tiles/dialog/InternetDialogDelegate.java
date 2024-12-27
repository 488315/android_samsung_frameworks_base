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
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.Annotation;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
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
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda0;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetOnSubscriptionChangedListener;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.wifitrackerlib.MergedCarrierEntry;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class InternetDialogDelegate implements SystemUIDialog.Delegate, InternetDialogController.InternetDialogCallback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialog", 3);
    public final boolean mAboveStatusBar;
    protected InternetAdapter mAdapter;
    public Button mAirplaneModeButton;
    public TextView mAirplaneModeSummaryText;
    public AlertDialog mAlertDialog;
    public final Executor mBackgroundExecutor;
    public Drawable mBackgroundOff = null;
    public Drawable mBackgroundOn;
    public final boolean mCanChangeWifiState;
    public final boolean mCanConfigMobileData;
    protected boolean mCanConfigWifi;
    public Job mClickJob;
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
    public final InternetDialogController mInternetDialogController;
    public final InternetDialogManager mInternetDialogManager;
    public TextView mInternetDialogSubTitle;
    public TextView mInternetDialogTitle;
    public boolean mIsProgressBarVisible;
    public final KeyguardStateController mKeyguard;
    public Switch mMobileDataToggle;
    public LinearLayout mMobileNetworkLayout;
    public TextView mMobileSummaryText;
    public TextView mMobileTitleText;
    public View mMobileToggleDivider;
    public ProgressBar mProgressBar;
    public LinearLayout mSecondaryMobileNetworkLayout;
    public LinearLayout mSeeAllLayout;
    protected Button mShareWifiButton;
    public ImageView mSignalIcon;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
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

    public interface Factory {
        InternetDialogDelegate create(boolean z, boolean z2, boolean z3, CoroutineScope coroutineScope);
    }

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

    public InternetDialogDelegate(Context context, InternetDialogManager internetDialogManager, InternetDialogController internetDialogController, boolean z, boolean z2, boolean z3, CoroutineScope coroutineScope, UiEventLogger uiEventLogger, DialogTransitionAnimator dialogTransitionAnimator, Handler handler, Executor executor, KeyguardStateController keyguardStateController, SystemUIDialog.Factory factory) {
        boolean z4;
        this.mAboveStatusBar = z3;
        this.mSystemUIDialogFactory = factory;
        if (DEBUG) {
            Log.d("InternetDialog", "Init InternetDialog");
        }
        this.mHandler = handler;
        this.mBackgroundExecutor = executor;
        this.mInternetDialogManager = internetDialogManager;
        this.mInternetDialogController = internetDialogController;
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
        this.mCoroutineScope = coroutineScope;
        this.mUiEventLogger = uiEventLogger;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mAdapter = new InternetAdapter(internetDialogController, coroutineScope);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        SystemUIDialog create = factory.create(this, factory.mContext);
        if (!this.mAboveStatusBar) {
            create.getWindow().setType(2038);
        }
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
        }
        this.mDialog = create;
        return create;
    }

    public final String getMobileNetworkSummary(int i) {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        Context context = internetDialogController.mContext;
        MobileMappings.Config config = internetDialogController.mConfig;
        TelephonyDisplayInfo orDefault = internetDialogController.mSubIdTelephonyDisplayInfoMap.getOrDefault(Integer.valueOf(i), InternetDialogController.DEFAULT_TELEPHONY_DISPLAY_INFO);
        String num = orDefault.getOverrideNetworkType() == 0 ? Integer.toString(orDefault.getNetworkType()) : MobileMappings.toDisplayIconKey(orDefault.getOverrideNetworkType());
        MobileMappings.mapIconSets(config);
        String str = "";
        if (((HashMap) MobileMappings.mapIconSets(config)).get(num) != null) {
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) ((HashMap) MobileMappings.mapIconSets(config)).get(num);
            Objects.requireNonNull(signalIcon$MobileIconGroup);
            int i2 = internetDialogController.isCarrierNetworkActive() ? TelephonyIcons.CARRIER_MERGED_WIFI.dataContentDescription : internetDialogController.mCarrierNetworkChangeMode ? TelephonyIcons.CARRIER_NETWORK_CHANGE.dataContentDescription : signalIcon$MobileIconGroup.dataContentDescription;
            if (i2 != 0) {
                str = SubscriptionManager.getResourcesForSubId(context, i).getString(i2);
            }
        } else if (InternetDialogController.DEBUG) {
            Log.d("InternetDialogController", "The description of network type is empty.");
        }
        Context context2 = internetDialogController.mContext;
        if (!internetDialogController.isMobileDataEnabled()) {
            return context2.getString(R.string.mobile_data_off_summary);
        }
        boolean z = i == internetDialogController.mDefaultDataSubId;
        boolean z2 = internetDialogController.getActiveAutoSwitchNonDdsSubId() != -1;
        if (internetDialogController.activeNetworkIsCellular() || internetDialogController.isCarrierNetworkActive()) {
            str = context2.getString(R.string.preference_summary_default_combination, context2.getString(z ? z2 ? R.string.mobile_data_poor_connection : R.string.mobile_data_connection_active : R.string.mobile_data_temp_connection_active), str);
        } else if (!internetDialogController.isDataStateInService(i)) {
            str = context2.getString(R.string.mobile_data_no_connection);
        }
        return str;
    }

    public final CharSequence getMobileNetworkTitle(int i) {
        final InternetDialogController internetDialogController = this.mInternetDialogController;
        final Context context = internetDialogController.mContext;
        Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                InternetDialogController internetDialogController2 = InternetDialogController.this;
                return internetDialogController2.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo().stream().filter(new InternetDialogController$$ExternalSyntheticLambda10()).map(new InternetDialogController$$ExternalSyntheticLambda6(internetDialogController2, 1));
            }
        };
        final HashSet hashSet = new HashSet();
        final int i2 = 0;
        Stream filter = ((Stream) supplier.get()).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i2;
                Set set = hashSet;
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i3) {
                    case 0:
                        return !set.add(c1DisplayInfo.originalName);
                    default:
                        return !set.add(c1DisplayInfo.uniqueName);
                }
            }
        });
        final int i3 = 0;
        final Set set = (Set) filter.map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i3) {
                    case 0:
                        return c1DisplayInfo.originalName;
                    case 1:
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        return c1DisplayInfo.uniqueName;
                }
            }
        }).collect(Collectors.toSet());
        hashSet.clear();
        final int i4 = 1;
        final int i5 = 1;
        Stream map = ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object apply(java.lang.Object r3) {
                /*
                    r2 = this;
                    java.util.Set r0 = r1
                    android.content.Context r2 = r2
                    com.android.systemui.qs.tiles.dialog.InternetDialogController$1DisplayInfo r3 = (com.android.systemui.qs.tiles.dialog.InternetDialogController.C1DisplayInfo) r3
                    java.lang.CharSequence r1 = r3.originalName
                    boolean r0 = r0.contains(r1)
                    if (r0 == 0) goto L75
                    android.telephony.SubscriptionInfo r0 = r3.subscriptionInfo
                    if (r0 == 0) goto L31
                    java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
                    java.lang.Object r2 = r2.getSystemService(r1)
                    android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2
                    int r0 = r0.getSubscriptionId()
                    android.telephony.TelephonyManager r2 = r2.createForSubscriptionId(r0)
                    java.lang.String r2 = r2.getLine1Number()
                    boolean r0 = android.text.TextUtils.isEmpty(r2)
                    if (r0 != 0) goto L31
                    java.lang.String r2 = android.telephony.PhoneNumberUtils.formatNumber(r2)
                    goto L32
                L31:
                    r2 = 0
                L32:
                    android.text.BidiFormatter r0 = android.text.BidiFormatter.getInstance()
                    android.text.TextDirectionHeuristic r1 = android.text.TextDirectionHeuristics.LTR
                    java.lang.String r2 = r0.unicodeWrap(r2, r1)
                    if (r2 == 0) goto L4f
                    int r0 = r2.length()
                    r1 = 4
                    if (r0 <= r1) goto L51
                    int r0 = r2.length()
                    int r0 = r0 - r1
                    java.lang.String r2 = r2.substring(r0)
                    goto L51
                L4f:
                    java.lang.String r2 = ""
                L51:
                    boolean r0 = android.text.TextUtils.isEmpty(r2)
                    if (r0 == 0) goto L5c
                    java.lang.CharSequence r2 = r3.originalName
                    r3.uniqueName = r2
                    goto L79
                L5c:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.CharSequence r1 = r3.originalName
                    r0.append(r1)
                    java.lang.String r1 = " "
                    r0.append(r1)
                    r0.append(r2)
                    java.lang.String r2 = r0.toString()
                    r3.uniqueName = r2
                    goto L79
                L75:
                    java.lang.CharSequence r2 = r3.originalName
                    r3.uniqueName = r2
                L79:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9.apply(java.lang.Object):java.lang.Object");
            }
        }).map(new InternetDialogController$$ExternalSyntheticLambda6((Set) ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                /*
                    this = this;
                    java.util.Set r0 = r1
                    android.content.Context r2 = r2
                    com.android.systemui.qs.tiles.dialog.InternetDialogController$1DisplayInfo r3 = (com.android.systemui.qs.tiles.dialog.InternetDialogController.C1DisplayInfo) r3
                    java.lang.CharSequence r1 = r3.originalName
                    boolean r0 = r0.contains(r1)
                    if (r0 == 0) goto L75
                    android.telephony.SubscriptionInfo r0 = r3.subscriptionInfo
                    if (r0 == 0) goto L31
                    java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
                    java.lang.Object r2 = r2.getSystemService(r1)
                    android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2
                    int r0 = r0.getSubscriptionId()
                    android.telephony.TelephonyManager r2 = r2.createForSubscriptionId(r0)
                    java.lang.String r2 = r2.getLine1Number()
                    boolean r0 = android.text.TextUtils.isEmpty(r2)
                    if (r0 != 0) goto L31
                    java.lang.String r2 = android.telephony.PhoneNumberUtils.formatNumber(r2)
                    goto L32
                L31:
                    r2 = 0
                L32:
                    android.text.BidiFormatter r0 = android.text.BidiFormatter.getInstance()
                    android.text.TextDirectionHeuristic r1 = android.text.TextDirectionHeuristics.LTR
                    java.lang.String r2 = r0.unicodeWrap(r2, r1)
                    if (r2 == 0) goto L4f
                    int r0 = r2.length()
                    r1 = 4
                    if (r0 <= r1) goto L51
                    int r0 = r2.length()
                    int r0 = r0 - r1
                    java.lang.String r2 = r2.substring(r0)
                    goto L51
                L4f:
                    java.lang.String r2 = ""
                L51:
                    boolean r0 = android.text.TextUtils.isEmpty(r2)
                    if (r0 == 0) goto L5c
                    java.lang.CharSequence r2 = r3.originalName
                    r3.uniqueName = r2
                    goto L79
                L5c:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.CharSequence r1 = r3.originalName
                    r0.append(r1)
                    java.lang.String r1 = " "
                    r0.append(r1)
                    r0.append(r2)
                    java.lang.String r2 = r0.toString()
                    r3.uniqueName = r2
                    goto L79
                L75:
                    java.lang.CharSequence r2 = r3.originalName
                    r3.uniqueName = r2
                L79:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9.apply(java.lang.Object):java.lang.Object");
            }
        }).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i32 = i4;
                Set set2 = hashSet;
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i32) {
                    case 0:
                        return !set2.add(c1DisplayInfo.originalName);
                    default:
                        return !set2.add(c1DisplayInfo.uniqueName);
                }
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i5) {
                    case 0:
                        return c1DisplayInfo.originalName;
                    case 1:
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        return c1DisplayInfo.uniqueName;
                }
            }
        }).collect(Collectors.toSet()), 0));
        final int i6 = 2;
        final int i7 = 3;
        return (CharSequence) ((Map) map.collect(Collectors.toMap(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i6) {
                    case 0:
                        return c1DisplayInfo.originalName;
                    case 1:
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        return c1DisplayInfo.uniqueName;
                }
            }
        }, new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                switch (i7) {
                    case 0:
                        return c1DisplayInfo.originalName;
                    case 1:
                        return c1DisplayInfo.uniqueName;
                    case 2:
                        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
                    default:
                        return c1DisplayInfo.uniqueName;
                }
            }
        }))).getOrDefault(Integer.valueOf(i), "");
    }

    public final Drawable getSignalStrengthDrawable(int i) {
        InternetDialogController internetDialogController = this.mInternetDialogController;
        Drawable drawable = internetDialogController.mContext.getDrawable(R.drawable.ic_signal_strength_zero_bar_no_internet);
        try {
            if (internetDialogController.mTelephonyManager != null) {
                boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
                if (internetDialogController.isDataStateInService(i) || internetDialogController.isVoiceStateInService(i) || isCarrierNetworkActive) {
                    AtomicReference atomicReference = new AtomicReference();
                    atomicReference.set(internetDialogController.getSignalStrengthDrawableWithLevel(i, isCarrierNetworkActive));
                    drawable = (Drawable) atomicReference.get();
                }
                int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(internetDialogController.mContext, android.R.attr.textColorTertiary, 0);
                if (internetDialogController.activeNetworkIsCellular() || isCarrierNetworkActive) {
                    colorAttrDefaultColor = internetDialogController.mContext.getColor(R.color.connected_network_primary_color);
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
        boolean z = this.mIsProgressBarVisible;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        boolean z2 = internetDialogController.mCanConfigWifi;
        boolean z3 = InternetDialogController.DEBUG;
        if (z2 && !internetDialogController.isWifiEnabled()) {
            if (z3) {
                Log.d("InternetDialogController", "Wi-Fi off.");
            }
            return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_WIFI_IS_OFF);
        }
        if (!internetDialogController.mKeyguardStateController.isUnlocked()) {
            if (z3) {
                Log.d("InternetDialogController", "The device is locked.");
            }
            return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        }
        if (internetDialogController.mHasWifiEntries) {
            if (internetDialogController.mCanConfigWifi) {
                return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
            }
        } else {
            if (internetDialogController.mCanConfigWifi && z) {
                return internetDialogController.mContext.getText(InternetDialogController.SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
            }
            boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
            int i = InternetDialogController.SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE;
            if (isCarrierNetworkActive) {
                return internetDialogController.mContext.getText(i);
            }
            if (z3) {
                Log.d("InternetDialogController", "No Wi-Fi item.");
            }
            boolean z4 = false;
            boolean z5 = internetDialogController.getActiveAutoSwitchNonDdsSubId() != -1;
            if (!internetDialogController.isAirplaneModeEnabled() && internetDialogController.mTelephonyManager != null) {
                z4 = internetDialogController.mHasActiveSubIdOnDds;
            }
            int i2 = InternetDialogController.SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE;
            if (!z4 || (!internetDialogController.isVoiceStateInService(internetDialogController.mDefaultDataSubId) && !internetDialogController.isDataStateInService(internetDialogController.mDefaultDataSubId) && !z5)) {
                if (z3) {
                    Log.d("InternetDialogController", "No carrier or service is out of service.");
                }
                return internetDialogController.mContext.getText(i2);
            }
            if (internetDialogController.mCanConfigWifi && !internetDialogController.isMobileDataEnabled()) {
                if (z3) {
                    Log.d("InternetDialogController", "Mobile data off");
                }
                return internetDialogController.mContext.getText(i);
            }
            if (!internetDialogController.activeNetworkIsCellular()) {
                if (z3) {
                    Log.d("InternetDialogController", "No carrier data.");
                }
                return internetDialogController.mContext.getText(i2);
            }
            if (internetDialogController.mCanConfigWifi) {
                return internetDialogController.mContext.getText(i);
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
        this.mShareWifiButton.setVisibility(8);
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
        InternetDialogController internetDialogController = this.mInternetDialogController;
        textView.setText(internetDialogController.isAirplaneModeEnabled() ? internetDialogController.mContext.getText(R.string.airplane_mode) : internetDialogController.mContext.getText(R.string.quick_settings_internet_label));
        this.mInternetDialogTitle.setGravity(8388627);
        this.mBackgroundOff = context.getDrawable(R.drawable.internet_dialog_selected_effect);
        final int i = 0;
        this.mMobileNetworkLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda8
            public final /* synthetic */ InternetDialogDelegate f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog2;
                switch (i) {
                    case 0:
                        final InternetDialogDelegate internetDialogDelegate = this.f$0;
                        SystemUIDialog systemUIDialog3 = systemUIDialog;
                        InternetDialogController internetDialogController2 = internetDialogDelegate.mInternetDialogController;
                        final int activeAutoSwitchNonDdsSubId = internetDialogController2.getActiveAutoSwitchNonDdsSubId();
                        if (activeAutoSwitchNonDdsSubId != -1) {
                            Context context2 = systemUIDialog3.getContext();
                            CharSequence mobileNetworkTitle = internetDialogDelegate.getMobileNetworkTitle(internetDialogDelegate.mDefaultDataSubId);
                            if (TextUtils.isEmpty(mobileNetworkTitle)) {
                                mobileNetworkTitle = context2.getString(R.string.mobile_data_disable_message_default_carrier);
                            }
                            AlertDialog create = new AlertDialog.Builder(context2).setTitle(context2.getString(R.string.auto_data_switch_disable_title, mobileNetworkTitle)).setMessage(R.string.auto_data_switch_disable_message).setNegativeButton(R.string.auto_data_switch_dialog_negative_button, new InternetDialogDelegate$$ExternalSyntheticLambda18()).setPositiveButton(R.string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda19
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    InternetDialogDelegate internetDialogDelegate2 = InternetDialogDelegate.this;
                                    int i3 = activeAutoSwitchNonDdsSubId;
                                    InternetDialogController internetDialogController3 = internetDialogDelegate2.mInternetDialogController;
                                    TelephonyManager orDefault = internetDialogController3.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i3), internetDialogController3.mTelephonyManager);
                                    if (orDefault != null) {
                                        orDefault.setMobileDataPolicyEnabled(3, false);
                                    } else if (InternetDialogController.DEBUG) {
                                        Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
                                    }
                                    LinearLayout linearLayout = internetDialogDelegate2.mSecondaryMobileNetworkLayout;
                                    if (linearLayout != null) {
                                        linearLayout.setVisibility(8);
                                    }
                                }
                            }).create();
                            internetDialogDelegate.mAlertDialog = create;
                            create.getWindow().setType(2009);
                            SystemUIDialog.setShowForAllUsers(internetDialogDelegate.mAlertDialog);
                            SystemUIDialog.registerDismissListener(internetDialogDelegate.mAlertDialog);
                            SystemUIDialog.setWindowOnTop(internetDialogDelegate.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegate.mKeyguard).mShowing);
                            internetDialogDelegate.mDialogTransitionAnimator.showFromDialog(internetDialogDelegate.mAlertDialog, systemUIDialog3, null, false);
                        }
                        boolean isMobileDataEnabled = internetDialogController2.isMobileDataEnabled();
                        boolean z = InternetDialogController.DEBUG;
                        if (!isMobileDataEnabled) {
                            if (z) {
                                Log.d("InternetDialogController", "Fail to connect carrier network : settings OFF");
                                break;
                            }
                        } else if (!(!internetDialogController2.mKeyguardStateController.isUnlocked())) {
                            if (!internetDialogController2.activeNetworkIsCellular()) {
                                MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDialogController2.mAccessPointController).getMergedCarrierEntry();
                                if (mergedCarrierEntry != null) {
                                    if (!mergedCarrierEntry.canConnect()) {
                                        Log.w("InternetDialogController", "Fail to connect carrier network : merged entry connect state " + mergedCarrierEntry.getConnectedState());
                                        break;
                                    } else {
                                        mergedCarrierEntry.connect$1(null);
                                        internetDialogController2.makeOverlayToast(R.string.wifi_wont_autoconnect_for_now);
                                        break;
                                    }
                                } else {
                                    Log.e("InternetDialogController", "Fail to connect carrier network : no merged entry");
                                    break;
                                }
                            } else {
                                Log.d("InternetDialogController", "Fail to connect carrier network : already active");
                                break;
                            }
                        } else if (z) {
                            Log.d("InternetDialogController", "Fail to connect carrier network : device locked");
                            break;
                        }
                        break;
                    default:
                        final InternetDialogDelegate internetDialogDelegate2 = this.f$0;
                        SystemUIDialog systemUIDialog4 = systemUIDialog;
                        boolean isChecked = internetDialogDelegate2.mMobileDataToggle.isChecked();
                        InternetDialogController internetDialogController3 = internetDialogDelegate2.mInternetDialogController;
                        if (!isChecked && (systemUIDialog2 = internetDialogDelegate2.mDialog) != null) {
                            boolean z2 = Prefs.getBoolean(systemUIDialog2.getContext(), "QsHasTurnedOffMobileData", false);
                            if (internetDialogController3.isMobileDataEnabled() && !z2) {
                                internetDialogDelegate2.mMobileDataToggle.setChecked(true);
                                final Context context3 = systemUIDialog4.getContext();
                                CharSequence mobileNetworkTitle2 = internetDialogDelegate2.getMobileNetworkTitle(internetDialogDelegate2.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDialogController3.isVoiceStateInService(internetDialogDelegate2.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle2) || !isVoiceStateInService) {
                                    mobileNetworkTitle2 = context3.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create2 = new AlertDialog.Builder(context3).setTitle(R.string.mobile_data_disable_title).setMessage(context3.getString(R.string.mobile_data_disable_message, mobileNetworkTitle2)).setNegativeButton(android.R.string.cancel, new InternetDialogDelegate$$ExternalSyntheticLambda18()).setPositiveButton(android.R.string.call_notification_screening_text, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda26
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i2) {
                                        InternetDialogDelegate internetDialogDelegate3 = InternetDialogDelegate.this;
                                        Context context4 = context3;
                                        internetDialogDelegate3.mInternetDialogController.setMobileDataEnabled(context4, internetDialogDelegate3.mDefaultDataSubId, false);
                                        internetDialogDelegate3.mMobileDataToggle.setChecked(false);
                                        Prefs.putBoolean(context4, "QsHasTurnedOffMobileData", true);
                                    }
                                }).create();
                                internetDialogDelegate2.mAlertDialog = create2;
                                create2.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialogDelegate2.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialogDelegate2.mAlertDialog);
                                SystemUIDialog.setWindowOnTop(internetDialogDelegate2.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegate2.mKeyguard).mShowing);
                                internetDialogDelegate2.mDialogTransitionAnimator.showFromDialog(internetDialogDelegate2.mAlertDialog, systemUIDialog4, null, false);
                                break;
                            }
                        }
                        if (internetDialogController3.isMobileDataEnabled() != isChecked) {
                            internetDialogController3.setMobileDataEnabled(systemUIDialog4.getContext(), internetDialogDelegate2.mDefaultDataSubId, isChecked);
                            break;
                        }
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mMobileDataToggle.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda8
            public final /* synthetic */ InternetDialogDelegate f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog systemUIDialog2;
                switch (i2) {
                    case 0:
                        final InternetDialogDelegate internetDialogDelegate = this.f$0;
                        SystemUIDialog systemUIDialog3 = systemUIDialog;
                        InternetDialogController internetDialogController2 = internetDialogDelegate.mInternetDialogController;
                        final int activeAutoSwitchNonDdsSubId = internetDialogController2.getActiveAutoSwitchNonDdsSubId();
                        if (activeAutoSwitchNonDdsSubId != -1) {
                            Context context2 = systemUIDialog3.getContext();
                            CharSequence mobileNetworkTitle = internetDialogDelegate.getMobileNetworkTitle(internetDialogDelegate.mDefaultDataSubId);
                            if (TextUtils.isEmpty(mobileNetworkTitle)) {
                                mobileNetworkTitle = context2.getString(R.string.mobile_data_disable_message_default_carrier);
                            }
                            AlertDialog create = new AlertDialog.Builder(context2).setTitle(context2.getString(R.string.auto_data_switch_disable_title, mobileNetworkTitle)).setMessage(R.string.auto_data_switch_disable_message).setNegativeButton(R.string.auto_data_switch_dialog_negative_button, new InternetDialogDelegate$$ExternalSyntheticLambda18()).setPositiveButton(R.string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda19
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i22) {
                                    InternetDialogDelegate internetDialogDelegate2 = InternetDialogDelegate.this;
                                    int i3 = activeAutoSwitchNonDdsSubId;
                                    InternetDialogController internetDialogController3 = internetDialogDelegate2.mInternetDialogController;
                                    TelephonyManager orDefault = internetDialogController3.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i3), internetDialogController3.mTelephonyManager);
                                    if (orDefault != null) {
                                        orDefault.setMobileDataPolicyEnabled(3, false);
                                    } else if (InternetDialogController.DEBUG) {
                                        Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
                                    }
                                    LinearLayout linearLayout = internetDialogDelegate2.mSecondaryMobileNetworkLayout;
                                    if (linearLayout != null) {
                                        linearLayout.setVisibility(8);
                                    }
                                }
                            }).create();
                            internetDialogDelegate.mAlertDialog = create;
                            create.getWindow().setType(2009);
                            SystemUIDialog.setShowForAllUsers(internetDialogDelegate.mAlertDialog);
                            SystemUIDialog.registerDismissListener(internetDialogDelegate.mAlertDialog);
                            SystemUIDialog.setWindowOnTop(internetDialogDelegate.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegate.mKeyguard).mShowing);
                            internetDialogDelegate.mDialogTransitionAnimator.showFromDialog(internetDialogDelegate.mAlertDialog, systemUIDialog3, null, false);
                        }
                        boolean isMobileDataEnabled = internetDialogController2.isMobileDataEnabled();
                        boolean z = InternetDialogController.DEBUG;
                        if (!isMobileDataEnabled) {
                            if (z) {
                                Log.d("InternetDialogController", "Fail to connect carrier network : settings OFF");
                                break;
                            }
                        } else if (!(!internetDialogController2.mKeyguardStateController.isUnlocked())) {
                            if (!internetDialogController2.activeNetworkIsCellular()) {
                                MergedCarrierEntry mergedCarrierEntry = ((AccessPointControllerImpl) internetDialogController2.mAccessPointController).getMergedCarrierEntry();
                                if (mergedCarrierEntry != null) {
                                    if (!mergedCarrierEntry.canConnect()) {
                                        Log.w("InternetDialogController", "Fail to connect carrier network : merged entry connect state " + mergedCarrierEntry.getConnectedState());
                                        break;
                                    } else {
                                        mergedCarrierEntry.connect$1(null);
                                        internetDialogController2.makeOverlayToast(R.string.wifi_wont_autoconnect_for_now);
                                        break;
                                    }
                                } else {
                                    Log.e("InternetDialogController", "Fail to connect carrier network : no merged entry");
                                    break;
                                }
                            } else {
                                Log.d("InternetDialogController", "Fail to connect carrier network : already active");
                                break;
                            }
                        } else if (z) {
                            Log.d("InternetDialogController", "Fail to connect carrier network : device locked");
                            break;
                        }
                        break;
                    default:
                        final InternetDialogDelegate internetDialogDelegate2 = this.f$0;
                        SystemUIDialog systemUIDialog4 = systemUIDialog;
                        boolean isChecked = internetDialogDelegate2.mMobileDataToggle.isChecked();
                        InternetDialogController internetDialogController3 = internetDialogDelegate2.mInternetDialogController;
                        if (!isChecked && (systemUIDialog2 = internetDialogDelegate2.mDialog) != null) {
                            boolean z2 = Prefs.getBoolean(systemUIDialog2.getContext(), "QsHasTurnedOffMobileData", false);
                            if (internetDialogController3.isMobileDataEnabled() && !z2) {
                                internetDialogDelegate2.mMobileDataToggle.setChecked(true);
                                final Context context3 = systemUIDialog4.getContext();
                                CharSequence mobileNetworkTitle2 = internetDialogDelegate2.getMobileNetworkTitle(internetDialogDelegate2.mDefaultDataSubId);
                                boolean isVoiceStateInService = internetDialogController3.isVoiceStateInService(internetDialogDelegate2.mDefaultDataSubId);
                                if (TextUtils.isEmpty(mobileNetworkTitle2) || !isVoiceStateInService) {
                                    mobileNetworkTitle2 = context3.getString(R.string.mobile_data_disable_message_default_carrier);
                                }
                                AlertDialog create2 = new AlertDialog.Builder(context3).setTitle(R.string.mobile_data_disable_title).setMessage(context3.getString(R.string.mobile_data_disable_message, mobileNetworkTitle2)).setNegativeButton(android.R.string.cancel, new InternetDialogDelegate$$ExternalSyntheticLambda18()).setPositiveButton(android.R.string.call_notification_screening_text, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda26
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(DialogInterface dialogInterface, int i22) {
                                        InternetDialogDelegate internetDialogDelegate3 = InternetDialogDelegate.this;
                                        Context context4 = context3;
                                        internetDialogDelegate3.mInternetDialogController.setMobileDataEnabled(context4, internetDialogDelegate3.mDefaultDataSubId, false);
                                        internetDialogDelegate3.mMobileDataToggle.setChecked(false);
                                        Prefs.putBoolean(context4, "QsHasTurnedOffMobileData", true);
                                    }
                                }).create();
                                internetDialogDelegate2.mAlertDialog = create2;
                                create2.getWindow().setType(2009);
                                SystemUIDialog.setShowForAllUsers(internetDialogDelegate2.mAlertDialog);
                                SystemUIDialog.registerDismissListener(internetDialogDelegate2.mAlertDialog);
                                SystemUIDialog.setWindowOnTop(internetDialogDelegate2.mAlertDialog, ((KeyguardStateControllerImpl) internetDialogDelegate2.mKeyguard).mShowing);
                                internetDialogDelegate2.mDialogTransitionAnimator.showFromDialog(internetDialogDelegate2.mAlertDialog, systemUIDialog4, null, false);
                                break;
                            }
                        }
                        if (internetDialogController3.isMobileDataEnabled() != isChecked) {
                            internetDialogController3.setMobileDataEnabled(systemUIDialog4.getContext(), internetDialogDelegate2.mDefaultDataSubId, isChecked);
                            break;
                        }
                        break;
                }
            }
        });
        this.mConnectedWifListLayout.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, 0));
        this.mSeeAllLayout.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, 2));
        this.mWiFiToggle.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, 3));
        this.mDoneButton.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda13(systemUIDialog, 0));
        this.mShareWifiButton.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, 1));
        this.mAirplaneModeButton.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, 4));
        this.mTurnWifiOnLayout.setBackground(null);
        this.mAirplaneModeButton.setVisibility(internetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        this.mWifiRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.mWifiRecyclerView.setAdapter(this.mAdapter);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStart(Dialog dialog) {
        if (DEBUG) {
            Log.d("InternetDialog", "onStart");
        }
        boolean z = this.mCanConfigWifi;
        boolean z2 = InternetDialogController.DEBUG;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        if (z2) {
            internetDialogController.getClass();
            Log.d("InternetDialogController", "onStart");
        }
        internetDialogController.mCallback = this;
        internetDialogController.mKeyguardUpdateMonitor.registerCallback(internetDialogController.mKeyguardUpdateCallback);
        ((AccessPointControllerImpl) internetDialogController.mAccessPointController).addAccessPointCallback(internetDialogController);
        IntentFilter intentFilter = internetDialogController.mConnectionStateFilter;
        Executor executor = internetDialogController.mExecutor;
        BroadcastDispatcher broadcastDispatcher = internetDialogController.mBroadcastDispatcher;
        broadcastDispatcher.getClass();
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, internetDialogController.mConnectionStateReceiver, intentFilter, executor, null, 0, null, 56);
        internetDialogController.mOnSubscriptionsChangedListener = internetDialogController.new InternetOnSubscriptionChangedListener();
        internetDialogController.refreshHasActiveSubIdOnDds();
        internetDialogController.mSubscriptionManager.addOnSubscriptionsChangedListener(internetDialogController.mExecutor, internetDialogController.mOnSubscriptionsChangedListener);
        internetDialogController.mDefaultDataSubId = internetDialogController.getDefaultDataSubscriptionId();
        if (z2) {
            RecyclerView$$ExternalSyntheticOutline0.m(internetDialogController.mDefaultDataSubId, "InternetDialogController", new StringBuilder("Init, SubId: "));
        }
        internetDialogController.mConfig = MobileMappings.Config.readConfig(internetDialogController.mContext);
        internetDialogController.mTelephonyManager = internetDialogController.mTelephonyManager.createForSubscriptionId(internetDialogController.mDefaultDataSubId);
        internetDialogController.mSubIdTelephonyManagerMap.put(Integer.valueOf(internetDialogController.mDefaultDataSubId), internetDialogController.mTelephonyManager);
        InternetDialogController.InternetTelephonyCallback internetTelephonyCallback = new InternetDialogController.InternetTelephonyCallback(internetDialogController, internetDialogController.mDefaultDataSubId, 0);
        internetDialogController.mSubIdTelephonyCallbackMap.put(Integer.valueOf(internetDialogController.mDefaultDataSubId), internetTelephonyCallback);
        internetDialogController.mTelephonyManager.registerTelephonyCallback(internetDialogController.mExecutor, internetTelephonyCallback);
        internetDialogController.mConnectivityManager.registerDefaultNetworkCallback(internetDialogController.mConnectivityManagerNetworkCallback);
        internetDialogController.mCanConfigWifi = z;
        internetDialogController.scanWifiAccessPoints();
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
        boolean z = InternetDialogController.DEBUG;
        InternetDialogController internetDialogController = this.mInternetDialogController;
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
        internetDialogController.mCallback = null;
        this.mInternetDialogManager.destroyDialog();
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
        this.mInternetDialogSubTitle.setText(getSubtitleText());
    }

    public final void updateDialog(boolean z) {
        WifiManager wifiManager;
        int i = 5;
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("InternetDialog", "updateDialog");
        }
        TextView textView = this.mInternetDialogTitle;
        InternetDialogController internetDialogController = this.mInternetDialogController;
        textView.setText(internetDialogController.isAirplaneModeEnabled() ? internetDialogController.mContext.getText(R.string.airplane_mode) : internetDialogController.mContext.getText(R.string.quick_settings_internet_label));
        this.mInternetDialogSubTitle.setText(getSubtitleText());
        this.mAirplaneModeButton.setVisibility(internetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        this.mEthernetLayout.setVisibility(internetDialogController.mHasEthernet ? 0 : 8);
        int i2 = R.style.TextAppearance_InternetDialog_Active;
        if (z) {
            boolean activeNetworkIsCellular = internetDialogController.activeNetworkIsCellular();
            boolean isCarrierNetworkActive = internetDialogController.isCarrierNetworkActive();
            SystemUIDialog systemUIDialog = this.mDialog;
            if (systemUIDialog != null) {
                boolean z3 = activeNetworkIsCellular || isCarrierNetworkActive;
                if (z2) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setMobileDataLayout, isCarrierNetworkActive = ", "InternetDialog", isCarrierNetworkActive);
                }
                boolean isWifiEnabled = internetDialogController.isWifiEnabled();
                if (((internetDialogController.isAirplaneModeEnabled() || internetDialogController.mTelephonyManager == null) ? false : internetDialogController.mHasActiveSubIdOnDds) || (isWifiEnabled && isCarrierNetworkActive)) {
                    this.mMobileNetworkLayout.setVisibility(0);
                    this.mMobileDataToggle.setChecked(internetDialogController.isMobileDataEnabled());
                    this.mMobileTitleText.setText(getMobileNetworkTitle(this.mDefaultDataSubId));
                    String mobileNetworkSummary = getMobileNetworkSummary(this.mDefaultDataSubId);
                    if (TextUtils.isEmpty(mobileNetworkSummary)) {
                        this.mMobileSummaryText.setVisibility(8);
                    } else {
                        this.mMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary, 0));
                        this.mMobileSummaryText.setBreakStrategy(0);
                        this.mMobileSummaryText.setVisibility(0);
                    }
                    this.mBackgroundExecutor.execute(new InternetDialogDelegate$$ExternalSyntheticLambda0(this, i));
                    Switch r3 = this.mMobileDataToggle;
                    boolean z4 = this.mCanConfigMobileData;
                    r3.setVisibility(z4 ? 0 : 4);
                    this.mMobileToggleDivider.setVisibility(z4 ? 0 : 4);
                    this.mMobileToggleDivider.setBackgroundColor(systemUIDialog.getContext().getColor(z3 ? R.color.connected_network_primary_color : R.color.disconnected_network_primary_color));
                    final int activeAutoSwitchNonDdsSubId = internetDialogController.getActiveAutoSwitchNonDdsSubId();
                    int i3 = activeAutoSwitchNonDdsSubId != -1 ? 0 : 8;
                    int i4 = z3 ? 2132018316 : 2132018315;
                    if (i3 == 0) {
                        ViewStub viewStub = (ViewStub) this.mDialogView.findViewById(R.id.secondary_mobile_network_stub);
                        if (viewStub != null) {
                            viewStub.inflate();
                        }
                        LinearLayout linearLayout = (LinearLayout) systemUIDialog.findViewById(R.id.secondary_mobile_network_layout);
                        this.mSecondaryMobileNetworkLayout = linearLayout;
                        linearLayout.setOnClickListener(new InternetDialogDelegate$$ExternalSyntheticLambda10(this, i));
                        this.mSecondaryMobileNetworkLayout.setBackground(this.mBackgroundOn);
                        TextView textView2 = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_title);
                        textView2.setText(getMobileNetworkTitle(activeAutoSwitchNonDdsSubId));
                        textView2.setTextAppearance(R.style.TextAppearance_InternetDialog_Active);
                        TextView textView3 = (TextView) this.mDialogView.requireViewById(R.id.secondary_mobile_summary);
                        String mobileNetworkSummary2 = getMobileNetworkSummary(activeAutoSwitchNonDdsSubId);
                        if (!TextUtils.isEmpty(mobileNetworkSummary2)) {
                            textView3.setText(Html.fromHtml(mobileNetworkSummary2, 0));
                            textView3.setBreakStrategy(0);
                            textView3.setTextAppearance(R.style.TextAppearance_InternetDialog_Active);
                        }
                        final ImageView imageView = (ImageView) this.mDialogView.requireViewById(R.id.secondary_signal_icon);
                        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogDelegate$$ExternalSyntheticLambda23
                            @Override // java.lang.Runnable
                            public final void run() {
                                InternetDialogDelegate internetDialogDelegate = InternetDialogDelegate.this;
                                internetDialogDelegate.mHandler.post(new InternetDialogDelegate$$ExternalSyntheticLambda27(imageView, internetDialogDelegate.getSignalStrengthDrawable(activeAutoSwitchNonDdsSubId), 1));
                            }
                        });
                        ((ImageView) this.mDialogView.requireViewById(R.id.secondary_settings_icon)).setColorFilter(systemUIDialog.getContext().getColor(R.color.connected_network_primary_color));
                        this.mMobileNetworkLayout.setBackground(this.mBackgroundOff);
                        this.mMobileTitleText.setTextAppearance(R.style.TextAppearance_InternetDialog);
                        this.mMobileSummaryText.setTextAppearance(R.style.TextAppearance_InternetDialog_Secondary);
                        this.mSignalIcon.setColorFilter(systemUIDialog.getContext().getColor(R.color.connected_network_secondary_color));
                    } else {
                        this.mMobileNetworkLayout.setBackground(z3 ? this.mBackgroundOn : this.mBackgroundOff);
                        this.mMobileTitleText.setTextAppearance(z3 ? 2132018314 : 2132018313);
                        this.mMobileSummaryText.setTextAppearance(i4);
                    }
                    LinearLayout linearLayout2 = this.mSecondaryMobileNetworkLayout;
                    if (linearLayout2 != null) {
                        linearLayout2.setVisibility(i3);
                    }
                    if (internetDialogController.isAirplaneModeEnabled()) {
                        this.mAirplaneModeSummaryText.setVisibility(0);
                        this.mAirplaneModeSummaryText.setText(systemUIDialog.getContext().getText(R.string.airplane_mode));
                        this.mAirplaneModeSummaryText.setTextAppearance(i4);
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
        }
        if (this.mCanConfigWifi) {
            boolean z5 = !internetDialogController.mKeyguardStateController.isUnlocked();
            boolean isWifiEnabled2 = internetDialogController.isWifiEnabled();
            boolean z6 = ((LocationControllerImpl) internetDialogController.mLocationController).isLocationEnabled$1() && (wifiManager = internetDialogController.mWifiManager) != null && wifiManager.isScanAlwaysAvailable();
            if (this.mWiFiToggle.isChecked() != isWifiEnabled2) {
                this.mWiFiToggle.setChecked(isWifiEnabled2);
            }
            if (z5) {
                TextView textView4 = this.mWifiToggleTitleText;
                if (this.mConnectedWifiEntry == null) {
                    i2 = 2132018313;
                }
                textView4.setTextAppearance(i2);
            }
            Drawable drawable = null;
            this.mTurnWifiOnLayout.setBackground((!z5 || this.mConnectedWifiEntry == null) ? null : this.mBackgroundOn);
            if (!this.mCanChangeWifiState && this.mWiFiToggle.isEnabled()) {
                this.mWiFiToggle.setEnabled(false);
                this.mWifiToggleTitleText.setEnabled(false);
                TextView textView5 = (TextView) this.mDialogView.requireViewById(R.id.wifi_toggle_summary);
                textView5.setEnabled(false);
                textView5.setVisibility(0);
            }
            if (this.mDialog == null || !isWifiEnabled2 || this.mConnectedWifiEntry == null || z5) {
                this.mConnectedWifListLayout.setVisibility(8);
                this.mShareWifiButton.setVisibility(8);
            } else {
                this.mConnectedWifListLayout.setVisibility(0);
                this.mConnectedWifiTitleText.setText(this.mConnectedWifiEntry.getTitle());
                this.mConnectedWifiSummaryText.setText(this.mConnectedWifiEntry.getSummary(false));
                ImageView imageView2 = this.mConnectedWifiIcon;
                Drawable wifiDrawable = internetDialogController.getWifiDrawable(this.mConnectedWifiEntry);
                if (wifiDrawable != null) {
                    wifiDrawable.setTint(internetDialogController.mContext.getColor(R.color.connected_network_primary_color));
                    drawable = wifiDrawable;
                }
                imageView2.setImageDrawable(drawable);
                this.mWifiSettingsIcon.setColorFilter(this.mDialog.getContext().getColor(R.color.connected_network_primary_color));
                if (internetDialogController.getConfiguratorQrCodeGeneratorIntentOrNull(this.mConnectedWifiEntry) != null) {
                    this.mShareWifiButton.setVisibility(0);
                } else {
                    this.mShareWifiButton.setVisibility(8);
                }
                LinearLayout linearLayout4 = this.mSecondaryMobileNetworkLayout;
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(8);
                }
            }
            if (!isWifiEnabled2 || z5) {
                this.mWifiRecyclerView.setVisibility(8);
                this.mSeeAllLayout.setVisibility(8);
            } else {
                int wifiListMaxCount = getWifiListMaxCount();
                InternetAdapter internetAdapter = this.mAdapter;
                int i5 = internetAdapter.mWifiEntriesCount;
                if (i5 > wifiListMaxCount) {
                    this.mHasMoreWifiEntries = true;
                }
                if (wifiListMaxCount >= 0 && internetAdapter.mMaxEntriesCount != wifiListMaxCount) {
                    internetAdapter.mMaxEntriesCount = wifiListMaxCount;
                    if (i5 > wifiListMaxCount) {
                        internetAdapter.mWifiEntriesCount = wifiListMaxCount;
                        internetAdapter.notifyDataSetChanged();
                    }
                }
                int i6 = this.mWifiNetworkHeight * wifiListMaxCount;
                if (this.mWifiRecyclerView.getMinimumHeight() != i6) {
                    this.mWifiRecyclerView.setMinimumHeight(i6);
                }
                this.mWifiRecyclerView.setVisibility(0);
                this.mSeeAllLayout.setVisibility(this.mHasMoreWifiEntries ? 0 : 4);
            }
            if (this.mDialog == null || isWifiEnabled2 || !z6 || z5) {
                this.mWifiScanNotifyLayout.setVisibility(8);
                return;
            }
            if (TextUtils.isEmpty(this.mWifiScanNotifyText.getText())) {
                AnnotationLinkSpan.LinkInfo linkInfo = new AnnotationLinkSpan.LinkInfo("link", new InternetDialogDelegate$$ExternalSyntheticLambda13(internetDialogController, 1));
                TextView textView6 = this.mWifiScanNotifyText;
                CharSequence text = this.mDialog.getContext().getText(R.string.wifi_scan_notify_message);
                int i7 = AnnotationLinkSpan.$r8$clinit;
                SpannableString spannableString = new SpannableString(text);
                Annotation[] annotationArr = (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
                Arrays.asList(annotationArr).forEach(new AnnotationLinkSpan$$ExternalSyntheticLambda0(new AnnotationLinkSpan.LinkInfo[]{linkInfo}, spannableStringBuilder, spannableString));
                textView6.setText(spannableStringBuilder);
                this.mWifiScanNotifyText.setMovementMethod(LinkMovementMethod.getInstance());
            }
            this.mWifiScanNotifyLayout.setVisibility(0);
        }
    }
}
