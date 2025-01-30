package com.android.systemui.qs.buttons;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
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
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.TouchDelegateUtil;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSMumButton extends AlphaOptimizedFrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public boolean mExpanded;
    public boolean mListening;
    public ImageView mMultiUserAvatar;
    public MultiUserSwitch mMultiUserSwitch;
    public final MumAndDexHelper mMumAndDexHelper;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SettingsHelper mSettingsHelper;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;
    public final UserInfoController mUserInfoController;
    public final UserInteractor mUserInteractor;
    public final UserManager mUserManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MumAndDexHelper implements UserInfoController.OnUserInfoChangedListener, DesktopManager.Callback {
        public boolean IsDexEnablingOrEnabled;
        public final Uri[] SETTINGS_VALUE_LISTENER_LIST;
        public C21201 mBaseUserAdapter;
        public final QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda0 mSettingCallback;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$1 */
        public final class C21201 extends BaseUserSwitcherAdapter {
            public C21201(UserSwitcherController userSwitcherController) {
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
                    Log.d("QSMumButton", "MumAndDexHelper, UserSwitcherController.BaseUserAdapter notifyDataSetChanged()");
                }
                QSMumButton.this.post(new QSMumButton$$ExternalSyntheticLambda0(this, 1));
            }
        }

        public /* synthetic */ MumAndDexHelper(QSMumButton qSMumButton, int i) {
            this();
        }

        public final boolean checkMumRune() {
            boolean z = Operator.QUICK_IS_LDU_BRANDING;
            boolean isShopDemo = DeviceState.isShopDemo(QSMumButton.this.mContext);
            boolean z2 = (z || isShopDemo) ? false : true;
            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("checkMumRune: mumRune[", z2, "] lduBranding[", z, "] shopDemo[");
            m69m.append(isShopDemo);
            m69m.append("]");
            Log.d("QSMumButton", m69m.toString());
            return z2;
        }

        @Override // com.android.systemui.util.DesktopManager.Callback
        public final void onDesktopModeStateChanged(final SemDesktopModeState semDesktopModeState) {
            Log.d("QSMumButton", "MumAndDexHelper, onDesktopModeStateChanged()");
            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    QSMumButton.MumAndDexHelper mumAndDexHelper = QSMumButton.MumAndDexHelper.this;
                    mumAndDexHelper.updateDesktopModeState(semDesktopModeState);
                    mumAndDexHelper.updateMumSwitchVisibility();
                }
            });
        }

        @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
        public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
            if (DeviceType.isEngOrUTBinary()) {
                Log.d("QSMumButton", MotionLayout$$ExternalSyntheticOutline0.m22m("MumAndDexHelper, onUserInfoChanged(name:", str, ", userAccount:", str2, ")"));
            }
            QSMumButton qSMumButton = QSMumButton.this;
            if (drawable != null && UserManager.get(qSMumButton.mContext).isGuestUser(KeyguardUpdateMonitor.getCurrentUser()) && !(drawable instanceof UserIconDrawable)) {
                drawable = drawable.getConstantState().newDrawable(qSMumButton.mContext.getResources()).mutate();
                drawable.setColorFilter(Utils.getColorAttrDefaultColor(R.attr.colorForeground, qSMumButton.mContext, 0), PorterDuff.Mode.SRC_IN);
            }
            qSMumButton.mMultiUserAvatar.setImageDrawable(drawable);
            qSMumButton.mMultiUserSwitch.setContentDescription(qSMumButton.getResources().getString(com.android.systemui.R.string.accessibility_quick_settings_user, str));
            qSMumButton.post(new QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1(this, 0));
        }

        public final void updateDesktopModeState(SemDesktopModeState semDesktopModeState) {
            boolean z = semDesktopModeState != null && (semDesktopModeState.getEnabled() == 3 || semDesktopModeState.getEnabled() == 4);
            if (this.IsDexEnablingOrEnabled != z) {
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("MumAndDexHelper updateDesktopModeState() IsDexEnablingOrEnabled:"), this.IsDexEnablingOrEnabled, ">>", z, "QSMumButton");
                this.IsDexEnablingOrEnabled = z;
                QSMumButton.this.post(new QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1(this, 1));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x00ff  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateMumSwitchVisibility() {
            int i;
            boolean z;
            boolean z2;
            QSMumButton qSMumButton = QSMumButton.this;
            if (qSMumButton.mMultiUserSwitch != null) {
                Point point = DeviceState.sDisplaySize;
                if (!SystemProperties.getBoolean("debug.quick_mum_test_trigger", false)) {
                    if (checkMumRune()) {
                        boolean z3 = QpRune.QUICK_MANAGE_TWO_PHONE && DeviceState.supportsMultipleUsers() && qSMumButton.mSettingsHelper.isTwoPhoneRegistered() && qSMumButton.mSettingsHelper.hasTwoPhoneAccount();
                        C21201 c21201 = this.mBaseUserAdapter;
                        if (c21201 != null) {
                            i = c21201.getCount();
                            z = i != 0;
                        } else {
                            i = 0;
                            z = false;
                        }
                        boolean z4 = Prefs.getBoolean(qSMumButton.getContext(), "HasSeenMultiUser", false);
                        boolean isUserSwitcherSettingOn = qSMumButton.mSettingsHelper.isUserSwitcherSettingOn();
                        boolean z5 = z3 || (z && z4 && isUserSwitcherSettingOn);
                        boolean hasUserRestriction = qSMumButton.mUserManager.hasUserRestriction("no_user_switch");
                        if (z5 && !this.IsDexEnablingOrEnabled && !qSMumButton.mSettingsHelper.isEmergencyMode()) {
                            if (!(qSMumButton.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1) && !((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode()) {
                                CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mCustomSdkMonitor;
                                if ((customSdkMonitor != null && customSdkMonitor.mKnoxCustomQuickPanelButtonUsers) && !SemPersonaManager.isKioskModeEnabled(qSMumButton.mContext) && qSMumButton.mExpanded && !hasUserRestriction) {
                                    z2 = true;
                                    StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("needToBeVisible() result:", z2, " [MumSetting?", z5, " = (TwoPhoneSetting:");
                                    m69m.append(z3);
                                    m69m.append(" || (somethingInDetail(");
                                    m69m.append(i);
                                    m69m.append("):");
                                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z, " && Pref:", z4, " && Settings:");
                                    m69m.append(isUserSwitcherSettingOn);
                                    m69m.append(")), DEX-(DeviceState?");
                                    m69m.append(qSMumButton.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1);
                                    m69m.append(", DesktopManager?");
                                    m69m.append(((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode());
                                    m69m.append(", SemDesktopModeStateEnable?");
                                    m69m.append(this.IsDexEnablingOrEnabled);
                                    m69m.append(") Panel-mExpanded?");
                                    KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(m69m, qSMumButton.mExpanded, ", isDisallowUserSwitch = ", hasUserRestriction, "QSMumButton");
                                    r3 = z2;
                                }
                            }
                        }
                        z2 = false;
                        StringBuilder m69m2 = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("needToBeVisible() result:", z2, " [MumSetting?", z5, " = (TwoPhoneSetting:");
                        m69m2.append(z3);
                        m69m2.append(" || (somethingInDetail(");
                        m69m2.append(i);
                        m69m2.append("):");
                        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m2, z, " && Pref:", z4, " && Settings:");
                        m69m2.append(isUserSwitcherSettingOn);
                        m69m2.append(")), DEX-(DeviceState?");
                        m69m2.append(qSMumButton.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1);
                        m69m2.append(", DesktopManager?");
                        m69m2.append(((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode());
                        m69m2.append(", SemDesktopModeStateEnable?");
                        m69m2.append(this.IsDexEnablingOrEnabled);
                        m69m2.append(") Panel-mExpanded?");
                        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(m69m2, qSMumButton.mExpanded, ", isDisallowUserSwitch = ", hasUserRestriction, "QSMumButton");
                        r3 = z2;
                    } else {
                        r3 = false;
                    }
                }
                qSMumButton.setVisibility(r3 ? 0 : 8);
                qSMumButton.mMultiUserSwitch.setVisibility(r3 ? 0 : 8);
                qSMumButton.mMultiUserSwitch.setClickable(r3);
                qSMumButton.mMultiUserSwitch.setLongClickable(r3);
            }
        }

        /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda0] */
        private MumAndDexHelper() {
            this.IsDexEnablingOrEnabled = false;
            this.SETTINGS_VALUE_LISTENER_LIST = new Uri[]{Settings.Global.getUriFor("two_call_enabled"), Settings.Global.getUriFor("two_sms_enabled"), Settings.Global.getUriFor("two_account"), Settings.Global.getUriFor("two_register"), Settings.Global.getUriFor("user_switcher_enabled")};
            this.mSettingCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda0
                @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                public final void onChanged(Uri uri) {
                    QSMumButton.MumAndDexHelper mumAndDexHelper = QSMumButton.MumAndDexHelper.this;
                    mumAndDexHelper.getClass();
                    Log.d("QSMumButton", "MumAndDexHelper receive SettingsHelper callback !");
                    QSMumButton.this.post(new QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1(mumAndDexHelper, 2));
                }
            };
        }
    }

    public QSMumButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMumAndDexHelper = new MumAndDexHelper(this, 0);
        this.mContext = context;
        this.mUserInfoController = (UserInfoController) Dependency.get(UserInfoController.class);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = com.android.systemui.R.string.tooltip_quick_settings_mum;
        this.mUserInteractor = (UserInteractor) Dependency.get(UserInteractor.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        if (mumAndDexHelper == null || !mumAndDexHelper.checkMumRune()) {
            return;
        }
        ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).registerCallback(mumAndDexHelper);
        mumAndDexHelper.updateDesktopModeState(((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).getSemDesktopModeState());
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(mumAndDexHelper.mSettingCallback, mumAndDexHelper.SETTINGS_VALUE_LISTENER_LIST);
        UserInfoController userInfoController = QSMumButton.this.mUserInfoController;
        if (userInfoController != null) {
            ((UserInfoControllerImpl) userInfoController).addCallback(mumAndDexHelper);
        }
        mumAndDexHelper.mBaseUserAdapter = mumAndDexHelper.new C21201((UserSwitcherController) Dependency.get(UserSwitcherController.class));
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTouchTargetArea();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        if (this.mListening) {
            this.mListening = false;
            MumAndDexHelper mumAndDexHelper = this.mMumAndDexHelper;
        }
        super.onDetachedFromWindow();
        MumAndDexHelper mumAndDexHelper2 = this.mMumAndDexHelper;
        if (mumAndDexHelper2 == null || !mumAndDexHelper2.checkMumRune()) {
            return;
        }
        ((ArrayList) ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).mCallbacks).remove(mumAndDexHelper2);
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(mumAndDexHelper2.mSettingCallback);
        UserInfoController userInfoController = QSMumButton.this.mUserInfoController;
        if (userInfoController != null) {
            ((UserInfoControllerImpl) userInfoController).removeCallback(mumAndDexHelper2);
        }
        mumAndDexHelper2.mBaseUserAdapter = null;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateTouchTargetArea();
        MultiUserSwitch multiUserSwitch = (MultiUserSwitch) findViewById(com.android.systemui.R.id.multi_user_switch);
        this.mMultiUserSwitch = multiUserSwitch;
        this.mMultiUserAvatar = (ImageView) multiUserSwitch.findViewById(com.android.systemui.R.id.multi_user_avatar);
        TouchDelegateUtil touchDelegateUtil = TouchDelegateUtil.INSTANCE;
        View findViewById = findViewById(com.android.systemui.R.id.mum_button_container);
        MultiUserSwitch multiUserSwitch2 = this.mMultiUserSwitch;
        touchDelegateUtil.getClass();
        TouchDelegateUtil.expandTouchAreaAsParent(findViewById, multiUserSwitch2);
        this.mMultiUserSwitch.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton.1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                if (QSMumButton.this.mTipWindow.isTooltipShown()) {
                    return true;
                }
                QSMumButton qSMumButton = QSMumButton.this;
                qSMumButton.mTipWindow.showToolTip(view, qSMumButton.mToolTipString);
                ((QSButtonsContainer) QSMumButton.this.getParent()).mCloseTooltipWindow = QSMumButton.this;
                return true;
            }
        });
        this.mMultiUserSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSMumButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSMumButton qSMumButton = QSMumButton.this;
                int i = QSMumButton.$r8$clinit;
                qSMumButton.getClass();
                ((CommandQueue) Dependency.get(CommandQueue.class)).animateCollapsePanels();
                UserInteractor userInteractor = qSMumButton.mUserInteractor;
                Expandable.Companion.getClass();
                userInteractor.showUserSwitcher(new Expandable$Companion$fromView$1(view));
            }
        });
    }

    public final void updateTouchTargetArea() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        layoutParams.width = SecQSPanelResourcePicker.getButtonsWidth(context);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
