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
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
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
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.qs.buttons.QSMumButton;
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

public class QSMumButton extends AlphaOptimizedFrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public boolean mExpanded;
    public boolean mListening;
    public Handler mMainHandler;
    public ImageView mMultiUserAvatar;
    public MultiUserSwitch mMultiUserSwitch;
    public final MumAndDexHelper mMumAndDexHelper;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;
    public final UserSwitcherInteractor mUserSwitcherInteractor;

    public final class MumAndDexHelper implements UserInfoController.OnUserInfoChangedListener, DesktopManager.Callback {
        public AnonymousClass1 mBaseUserAdapter;
        public final UserManager mUserManager;
        public final Uri[] SETTINGS_VALUE_LISTENER_LIST = {Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_CALL_ENABLED), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_SMS_ENABLED), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_ACCOUNT), Settings.Global.getUriFor(SettingsHelper.INDEX_TWO_PHONE_REGISTER), Settings.Global.getUriFor(SettingsHelper.INDEX_USER_SWITCHER_ENABLED)};
        private final SettingsHelper.OnChangedCallback mSettingCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSMumButton.MumAndDexHelper mumAndDexHelper = QSMumButton.MumAndDexHelper.this;
                mumAndDexHelper.getClass();
                Log.d("QSMumButton", "MumAndDexHelper receive SettingsHelper callback !");
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda0(mumAndDexHelper, 0));
            }
        };
        public boolean mIsDexEnablingOrEnabled = false;
        public final KnoxStateMonitor mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        private final SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        public final UserInfoController mUserInfoController = (UserInfoController) Dependency.sDependency.getDependencyInner(UserInfoController.class);
        public final UserSwitcherController mUserSwitcherController = (UserSwitcherController) Dependency.sDependency.getDependencyInner(UserSwitcherController.class);

        /* renamed from: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$1, reason: invalid class name */
        public final class AnonymousClass1 extends BaseUserSwitcherAdapter {
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
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda0(this, 1));
            }
        }

        public MumAndDexHelper() {
            this.mUserManager = (UserManager) QSMumButton.this.mContext.getSystemService(UserManager.class);
        }

        public final void destroy() {
            if (isMumDeactivated()) {
                return;
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
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isMumDeactivated: !result[", "] isLduBranding[", "] isShopDemo[", z2, z);
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
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            r5 = this;
                            com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper r0 = com.android.systemui.qs.buttons.QSMumButton.MumAndDexHelper.this
                            com.samsung.android.desktopmode.SemDesktopModeState r5 = r2
                            if (r5 == 0) goto L19
                            r0.getClass()
                            int r1 = r5.getEnabled()
                            r2 = 3
                            if (r1 == r2) goto L17
                            int r5 = r5.getEnabled()
                            r1 = 4
                            if (r5 != r1) goto L19
                        L17:
                            r5 = 1
                            goto L1a
                        L19:
                            r5 = 0
                        L1a:
                            boolean r1 = r0.mIsDexEnablingOrEnabled
                            if (r1 == r5) goto L3b
                            java.lang.StringBuilder r1 = new java.lang.StringBuilder
                            java.lang.String r2 = "MumAndDexHelper updateDesktopModeState() mIsDexEnablingOrEnabled:"
                            r1.<init>(r2)
                            boolean r2 = r0.mIsDexEnablingOrEnabled
                            java.lang.String r3 = ">>"
                            java.lang.String r4 = "QSMumButton"
                            com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(r1, r2, r3, r5, r4)
                            r0.mIsDexEnablingOrEnabled = r5
                            com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda0 r5 = new com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda0
                            r1 = 0
                            r5.<init>(r0, r1)
                            com.android.systemui.qs.buttons.QSMumButton r1 = com.android.systemui.qs.buttons.QSMumButton.this
                            r1.post(r5)
                        L3b:
                            r0.updateMumSwitchVisibility()
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1.run():void");
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
            if (drawable != null && UserManager.get(qSMumButton.mContext).isGuestUser(((SelectedUserInteractor) Dependency.sDependency.getDependencyInner(SelectedUserInteractor.class)).getSelectedUserId(false)) && !(drawable instanceof UserIconDrawable)) {
                drawable = drawable.getConstantState().newDrawable(qSMumButton.mContext.getResources()).mutate();
                drawable.setColorFilter(Utils.getColorAttrDefaultColor(qSMumButton.mContext, R.attr.colorForeground, 0), PorterDuff.Mode.SRC_IN);
            }
            qSMumButton.mMultiUserAvatar.setImageDrawable(drawable);
            qSMumButton.mMultiUserSwitch.setContentDescription(qSMumButton.getResources().getString(com.android.systemui.R.string.accessibility_quick_settings_user, str));
            qSMumButton.post(new QSMumButton$$ExternalSyntheticLambda0(this, 0));
        }

        public final void updateMumSwitchVisibility() {
            int i;
            boolean z;
            boolean z2;
            CustomSdkMonitor customSdkMonitor;
            QSMumButton qSMumButton = QSMumButton.this;
            if (qSMumButton.mMultiUserSwitch == null) {
                return;
            }
            if (isMumDeactivated()) {
                z2 = false;
            } else {
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
                KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
                boolean z6 = (knoxStateMonitor == null || (customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor) == null || !customSdkMonitor.mKnoxCustomQuickPanelButtonUsers) ? false : true;
                boolean isKioskModeEnabled = SemPersonaManager.isKioskModeEnabled(qSMumButton.mContext);
                z2 = (!z5 || this.mIsDexEnablingOrEnabled || isEmergencyMode || !z6 || isKioskModeEnabled || !qSMumButton.mExpanded || hasUserRestriction) ? false : true;
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("needToBeVisible() result: ", " [MumSetting: ", " = (TwoPhoneSetting: ", z2, z5);
                m.append(z3);
                m.append(" || (somethingInDetail(");
                m.append(i);
                m.append("): ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z, " && Pref: ", z4, " && Settings: ");
                m.append(isUserSwitcherSettingOn);
                m.append(", !mIsDexEnablingOrEnabled: ");
                m.append(!this.mIsDexEnablingOrEnabled);
                m.append(") Panel-mExpanded: ");
                m.append(qSMumButton.mExpanded);
                m.append(", !isEmergencyMode: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, !isEmergencyMode, ", isUserEnabled: ", z6, ", !isKioskModeEnabled: ");
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

    public QSMumButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMainHandler = null;
        this.mContext = context;
        this.mCommandQueue = (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
        this.mMumAndDexHelper = new MumAndDexHelper();
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = com.android.systemui.R.string.tooltip_quick_settings_mum;
        this.mUserSwitcherInteractor = (UserSwitcherInteractor) Dependency.sDependency.getDependencyInner(UserSwitcherInteractor.class);
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
        if (this.mListening) {
            this.mListening = false;
        }
        super.onDetachedFromWindow();
        MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        if (mumAndDexHelper != null) {
            mumAndDexHelper.destroy();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateTouchTargetArea$1();
        MultiUserSwitch multiUserSwitch = (MultiUserSwitch) findViewById(com.android.systemui.R.id.multi_user_switch);
        this.mMultiUserSwitch = multiUserSwitch;
        this.mMultiUserAvatar = (ImageView) multiUserSwitch.findViewById(com.android.systemui.R.id.multi_user_avatar);
        findViewById(com.android.systemui.R.id.mum_button_container).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return QSMumButton.this.mMultiUserSwitch.onTouchEvent(motionEvent);
            }
        });
        this.mMultiUserSwitch.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda2
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
        this.mMultiUserSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSMumButton qSMumButton = QSMumButton.this;
                qSMumButton.mCommandQueue.animateCollapsePanels();
                UserSwitcherInteractor userSwitcherInteractor = qSMumButton.mUserSwitcherInteractor;
                Expandable.Companion.getClass();
                userSwitcherInteractor.showUserSwitcher(new Expandable$Companion$fromView$1(qSMumButton));
            }
        });
    }

    public final void updateTouchTargetArea$1() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getButtonsWidth(this.mContext);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
