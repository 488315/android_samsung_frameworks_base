package com.android.systemui.qs.buttons;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.qs.buttons.QSMumButton;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.AlphaOptimizedFrameLayout;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class QSMumButton extends AlphaOptimizedFrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isExpandedOnLockScreen;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public boolean mExpanded;
    public boolean mListening;
    public Handler mMainHandler;
    public ImageView mMultiUserAvatar;
    public MultiUserSwitch mMultiUserSwitch;
    public final MumAndDexHelper mMumAndDexHelper;
    public boolean mPanelSplitEnabled;
    public final SecPanelSplitHelper mPanelSplitHelper;
    public final QSMumButton$$ExternalSyntheticLambda0 mPanelTransitionStateListener;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final StatusBarStateController mStatusBarStateController;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;
    public final UserSwitcherInteractor mUserSwitcherInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MumAndDexHelper implements UserInfoController.OnUserInfoChangedListener, DesktopManager.Callback {
        public AnonymousClass1 mBaseUserAdapter;
        public final UserManager mUserManager;
        public final Uri[] SETTINGS_VALUE_LISTENER_LIST = {Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_CALL_ENABLED), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_SMS_ENABLED), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_ACCOUNT), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_REGISTER), Settings.Global.getUriFor(SettingsHelper.INDEX_USER_SWITCHER_ENABLED)};
        private final SettingsHelper.OnChangedCallback mSettingCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSMumButton.MumAndDexHelper mumAndDexHelper = QSMumButton.MumAndDexHelper.this;
                Log.d("QSMumButton", "MumAndDexHelper receive SettingsHelper callback !");
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda1(mumAndDexHelper, 0));
            }
        };
        public boolean mIsDexEnablingOrEnabled = false;
        public final DesktopManager mDesktopManager = (DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class);
        public final KnoxStateMonitor mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        private final SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        public final UserInfoController mUserInfoController = (UserInfoController) Dependency.sDependency.getDependencyInner(UserInfoController.class);
        public final UserSwitcherController mUserSwitcherController = (UserSwitcherController) Dependency.sDependency.getDependencyInner(UserSwitcherController.class);

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$1, reason: invalid class name */
        public class AnonymousClass1 extends BaseUserSwitcherAdapter {
            public AnonymousClass1(UserSwitcherController userSwitcherController) {
                super(userSwitcherController);
            }

            @Override // android.widget.Adapter
            public final View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }

            @Override // android.widget.BaseAdapter
            public final void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                if (DeviceType.isEngOrUTBinary()) {
                    Log.d("QSMumButton", "MumAndDexHelper, BaseUserAdapter notifyDataSetChanged()");
                }
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda1(this, 1));
            }
        }

        public MumAndDexHelper() {
            this.mUserManager = (UserManager) QSMumButton.this.mContext.getSystemService(UserManager.class);
        }

        public final void destroy() {
            if (isMumDeactivated()) {
                return;
            }
            DesktopManager desktopManager = this.mDesktopManager;
            if (desktopManager != null) {
                desktopManager.unregisterCallback(this);
            }
            this.mSettingsHelper.unregisterCallback(this.mSettingCallback);
            UserInfoController userInfoController = this.mUserInfoController;
            if (userInfoController != null) {
                ((UserInfoControllerImpl) userInfoController).removeCallback(this);
            }
            this.mBaseUserAdapter = null;
            QSMumButton.this.mMultiUserAvatar.setImageDrawable(null);
        }

        public final void init() {
            if (isMumDeactivated()) {
                return;
            }
            DesktopManager desktopManager = this.mDesktopManager;
            if (desktopManager != null) {
                desktopManager.registerCallback(this);
                updateDesktopModeState(desktopManager.getSemDesktopModeState());
            }
            this.mSettingsHelper.registerCallback(this.mSettingCallback, this.SETTINGS_VALUE_LISTENER_LIST);
            UserInfoController userInfoController = this.mUserInfoController;
            if (userInfoController != null) {
                ((UserInfoControllerImpl) userInfoController).addCallback(this);
            }
            this.mBaseUserAdapter = new AnonymousClass1(this.mUserSwitcherController);
        }

        public final boolean isMumDeactivated() {
            boolean z = Operator.QUICK_IS_LDU_BRANDING;
            boolean isShopDemo = DeviceState.isShopDemo(QSMumButton.this.mContext);
            boolean z2 = z || isShopDemo;
            StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("isMumDeactivated: !result[", "] isLduBranding[", "] isShopDemo[", z2, z);
            m.append(isShopDemo);
            m.append("]");
            Log.d("QSMumButton", m.toString());
            return z2;
        }

        @Override // com.android.systemui.util.DesktopManager.Callback
        public final void onDesktopModeStateChanged(final SemDesktopModeState semDesktopModeState) {
            QSMumButton qSMumButton = QSMumButton.this;
            if (qSMumButton.mMainHandler == null) {
                Log.d("QSMumButton", "MumAndDexHelper, onDesktopModeStateChanged(): mMainHandler is null");
            } else {
                Log.d("QSMumButton", "MumAndDexHelper, onDesktopModeStateChanged()");
                qSMumButton.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSMumButton.MumAndDexHelper mumAndDexHelper = QSMumButton.MumAndDexHelper.this;
                        mumAndDexHelper.updateDesktopModeState(semDesktopModeState);
                        mumAndDexHelper.updateMumSwitchVisibility();
                    }
                });
            }
        }

        @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
        public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
            if (DeviceType.isEngOrUTBinary()) {
                Log.d("QSMumButton", MotionLayout$$ExternalSyntheticOutline0.m("MumAndDexHelper, onUserInfoChanged(name:", str, ", userAccount:", str2, ")"));
            }
            QSMumButton qSMumButton = QSMumButton.this;
            if (drawable != null && UserManager.get(qSMumButton.mContext).isGuestUser(((SelectedUserInteractor) Dependency.sDependency.getDependencyInner(SelectedUserInteractor.class)).getSelectedUserId()) && !(drawable instanceof UserIconDrawable)) {
                drawable = drawable.getConstantState().newDrawable(qSMumButton.mContext.getResources()).mutate();
                drawable.setColorFilter(Utils.getColorAttrDefaultColor(qSMumButton.mContext, R.attr.colorForeground, 0), PorterDuff.Mode.SRC_IN);
            }
            qSMumButton.mMultiUserAvatar.setImageDrawable(drawable);
            qSMumButton.mMultiUserSwitch.setContentDescription(qSMumButton.getResources().getString(com.android.systemui.R.string.accessibility_quick_settings_user, str));
            qSMumButton.post(new QSMumButton$$ExternalSyntheticLambda1(this, 0));
        }

        public final void updateDesktopModeState(SemDesktopModeState semDesktopModeState) {
            boolean z = semDesktopModeState != null && (semDesktopModeState.getEnabled() == 3 || semDesktopModeState.getEnabled() == 4);
            if (this.mIsDexEnablingOrEnabled != z) {
                CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("MumAndDexHelper updateDesktopModeState() mIsDexEnablingOrEnabled:"), this.mIsDexEnablingOrEnabled, ">>", z, "QSMumButton");
                this.mIsDexEnablingOrEnabled = z;
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda1(this, 0));
            }
        }

        public final void updateMumSwitchVisibility() {
            int i;
            boolean z;
            CustomSdkMonitor customSdkMonitor;
            QSMumButton qSMumButton = QSMumButton.this;
            if (qSMumButton.mMultiUserSwitch == null) {
                return;
            }
            boolean z2 = false;
            if (!isMumDeactivated()) {
                boolean z3 = QpRune.QUICK_MUM_TWO_PHONE && UserManager.supportsMultipleUsers() && this.mSettingsHelper.isTwoPhoneRegistered() && this.mSettingsHelper.hasTwoPhoneAccount();
                AnonymousClass1 anonymousClass1 = this.mBaseUserAdapter;
                if (anonymousClass1 != null) {
                    i = anonymousClass1.getCount();
                    z = i != 0;
                } else {
                    i = 0;
                    z = false;
                }
                boolean z4 = Prefs.getBoolean(qSMumButton.getContext(), "HasSeenMultiUser", false);
                boolean isUserSwitcherSettingOn = this.mSettingsHelper.isUserSwitcherSettingOn();
                boolean z5 = z3 || (z && z4 && isUserSwitcherSettingOn);
                boolean hasUserRestriction = this.mUserManager.hasUserRestriction("no_user_switch");
                boolean isEmergencyMode = this.mSettingsHelper.isEmergencyMode();
                boolean isDesktopMode = DeviceState.isDesktopMode(qSMumButton.mContext);
                DesktopManager desktopManager = this.mDesktopManager;
                boolean z6 = desktopManager != null && desktopManager.isDesktopMode();
                KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
                boolean z7 = (knoxStateMonitor == null || (customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor) == null || !customSdkMonitor.mKnoxCustomQuickPanelButtonUsers) ? false : true;
                boolean isKioskModeEnabled = SemPersonaManager.isKioskModeEnabled(qSMumButton.mContext);
                z2 = (!z5 || this.mIsDexEnablingOrEnabled || isEmergencyMode || isDesktopMode || z6 || !z7 || isKioskModeEnabled || (!qSMumButton.mExpanded && (!qSMumButton.mPanelSplitEnabled || !qSMumButton.isExpandedOnLockScreen)) || hasUserRestriction) ? false : true;
                StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("needToBeVisible() result: ", " [MumSetting: ", " = (TwoPhoneSetting: ", z2, z5);
                m.append(z3);
                m.append(" || (somethingInDetail(");
                m.append(i);
                m.append("): ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z, " && Pref: ", z4, " && Settings: ");
                m.append(isUserSwitcherSettingOn);
                m.append(")), !DEX-(DeviceState: ");
                m.append(!isDesktopMode);
                m.append(", !DesktopManager: ");
                m.append(!z6);
                m.append(", !mIsDexEnablingOrEnabled: ");
                m.append(!this.mIsDexEnablingOrEnabled);
                m.append(") Panel-mExpanded: ");
                m.append(qSMumButton.mExpanded);
                m.append(", !isEmergencyMode: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, !isEmergencyMode, ", isUserEnabled: ", z7, ", !isKioskModeEnabled: ");
                m.append(!isKioskModeEnabled);
                m.append(", !isDisallowUserSwitch = ");
                ActionBarContextView$$ExternalSyntheticOutline0.m(m, !hasUserRestriction, "QSMumButton");
            }
            qSMumButton.setVisibility(z2 ? 0 : 8);
            qSMumButton.mMultiUserSwitch.setVisibility(z2 ? 0 : 8);
            qSMumButton.mMultiUserSwitch.setClickable(z2);
            qSMumButton.mMultiUserSwitch.setLongClickable(z2);
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda0] */
    public QSMumButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMainHandler = null;
        this.mPanelSplitEnabled = SecPanelSplitHelper.isEnabled();
        this.isExpandedOnLockScreen = false;
        this.mPanelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda0
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                StatusBarStateController statusBarStateController;
                int i = QSMumButton.$r8$clinit;
                QSMumButton qSMumButton = QSMumButton.this;
                boolean z = panelTransitionStateChangeEvent.enabled;
                qSMumButton.mPanelSplitEnabled = z;
                if (qSMumButton.mPanelSplitHelper == null || (statusBarStateController = qSMumButton.mStatusBarStateController) == null || !z || qSMumButton.isExpandedOnLockScreen || statusBarStateController.getState() != 1) {
                    return;
                }
                if (qSMumButton.mPanelSplitHelper.stateOnDown == (!r3.isReversed())) {
                    SecPanelSplitHelper secPanelSplitHelper = qSMumButton.mPanelSplitHelper;
                    if (secPanelSplitHelper.stateToChange == secPanelSplitHelper.isReversed()) {
                        qSMumButton.isExpandedOnLockScreen = true;
                        qSMumButton.mMumAndDexHelper.updateMumSwitchVisibility();
                    }
                }
            }
        };
        this.mContext = context;
        this.mCommandQueue = (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
        this.mMumAndDexHelper = new MumAndDexHelper();
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = com.android.systemui.R.string.tooltip_quick_settings_mum;
        this.mUserSwitcherInteractor = (UserSwitcherInteractor) Dependency.sDependency.getDependencyInner(UserSwitcherInteractor.class);
        this.mPanelSplitHelper = (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        this.mStatusBarStateController = (StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class);
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        if (mumAndDexHelper != null) {
            mumAndDexHelper.init();
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTouchTargetArea$1();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        setListening(false);
        super.onDetachedFromWindow();
        MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        if (mumAndDexHelper != null) {
            mumAndDexHelper.destroy();
        }
        this.mPanelSplitHelper.removeListener(this.mPanelTransitionStateListener);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateTouchTargetArea$1();
        MultiUserSwitch multiUserSwitch = (MultiUserSwitch) findViewById(com.android.systemui.R.id.multi_user_switch);
        this.mMultiUserSwitch = multiUserSwitch;
        this.mMultiUserAvatar = (ImageView) multiUserSwitch.findViewById(com.android.systemui.R.id.multi_user_avatar);
        findViewById(com.android.systemui.R.id.mum_button_container).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return QSMumButton.this.mMultiUserSwitch.onTouchEvent(motionEvent);
            }
        });
        this.mMultiUserSwitch.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSMumButton qSMumButton = QSMumButton.this;
                if (qSMumButton.mTipWindow.isTooltipShown()) {
                    return true;
                }
                qSMumButton.mTipWindow.showToolTip(view, qSMumButton.mToolTipString);
                ((QSButtonsContainer) qSMumButton.getParent()).mCloseTooltipWindow = qSMumButton;
                return true;
            }
        });
        this.mMultiUserSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSMumButton qSMumButton = QSMumButton.this;
                qSMumButton.mCommandQueue.animateCollapsePanels();
                UserSwitcherInteractor userSwitcherInteractor = qSMumButton.mUserSwitcherInteractor;
                Expandable.Companion.getClass();
                userSwitcherInteractor.showUserSwitcher(new Expandable$Companion$fromView$1(qSMumButton));
            }
        });
        this.mPanelSplitHelper.addListener(this.mPanelTransitionStateListener);
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.isExpandedOnLockScreen = false;
        MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        if (mumAndDexHelper == null || !z) {
            return;
        }
        post(new QSMumButton$$ExternalSyntheticLambda1(mumAndDexHelper, 0));
    }

    public final void updateTouchTargetArea$1() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getButtonsWidth(this.mContext);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
