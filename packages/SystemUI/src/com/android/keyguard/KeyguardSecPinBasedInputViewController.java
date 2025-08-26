package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.BaseSecPasswordTextView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;

/* loaded from: classes.dex */
public class KeyguardSecPinBasedInputViewController extends KeyguardPinBasedInputViewController implements TextView.OnEditorActionListener, TextWatcher {
    public final AnonymousClass1 mAccessibilityDelegate;
    public final View[] mButtons;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public View mDeleteButton;
    public SeslRecoilDrawable mDeleteButtonRipple;
    public final KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 mInitializeBottomContainerViewRunnable;
    public boolean mInitialized;
    public boolean mIsImagePinLock;
    public boolean mIsMainDisplay;
    public boolean mIsNightModeOn;
    public View mOkButton;
    public SeslRecoilDrawable mOkButtonRipple;
    public final KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda7 mOnKeyListener;
    public int mOriginPinEntryId;
    public boolean mUpdateSkipped;
    public final Rect mWindowRect;

    public static /* synthetic */ boolean $r8$lambda$KEYW3irDfvhoAKb9FW_owagURSw(KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController, int i, KeyEvent keyEvent) {
        View view;
        if (i == 66 && (view = keyguardSecPinBasedInputViewController.mOkButton) != null && view.getAlpha() < 1.0f) {
            return true;
        }
        if (keyEvent.getAction() == 0) {
            return ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).onKeyDown(i, keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            return ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).onKeyUp(i, keyEvent);
        }
        return false;
    }

    public static /* synthetic */ void $r8$lambda$uU6id3dFiGU_1NwKYrhseXInPYo(KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController) {
        if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled()) {
            ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).resetPasswordText(true, true);
            keyguardSecPinBasedInputViewController.setOkButtonEnabled(false);
        }
        ((KeyguardSecPinBasedInputView) keyguardSecPinBasedInputViewController.mView).doHapticKeyClick();
    }

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.keyguard.KeyguardSecPinBasedInputViewController$2] */
    public KeyguardSecPinBasedInputViewController(KeyguardSecPinBasedInputView keyguardSecPinBasedInputView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, BouncerHapticPlayer bouncerHapticPlayer, UserActivityNotifier userActivityNotifier, InputManager inputManager) {
        super(keyguardSecPinBasedInputView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, emergencyButtonController, falsingCollector, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor, bouncerHapticPlayer, userActivityNotifier, inputManager);
        this.mIsMainDisplay = true;
        this.mWindowRect = new Rect(0, 0, 0, 0);
        this.mButtons = new View[]{((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key0), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key1), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key2), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key3), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key4), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key5), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key6), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key7), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key8), ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key9)};
        this.mAccessibilityDelegate = new View.AccessibilityDelegate(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setTextEntryKey(true);
            }
        };
        this.mInitialized = false;
        this.mUpdateSkipped = false;
        this.mIsNightModeOn = false;
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda7
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardSecPinBasedInputViewController.$r8$lambda$KEYW3irDfvhoAKb9FW_owagURSw(this.f$0, i, keyEvent);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
                boolean z = (configuration.uiMode & 32) != 0;
                KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = KeyguardSecPinBasedInputViewController.this;
                if (keyguardSecPinBasedInputViewController.mIsNightModeOn != z) {
                    CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("night mode changed : "), keyguardSecPinBasedInputViewController.mIsNightModeOn, " -> ", z, "KeyguardSecPinBasedInputViewController");
                    keyguardSecPinBasedInputViewController.mIsNightModeOn = z;
                    if (LsRune.SECURITY_OPEN_THEME) {
                        keyguardSecPinBasedInputViewController.updateStyle(WallpaperEventNotifier.getInstance().mCurStatusFlag, WallpaperEventNotifier.getInstance().getSemWallpaperColors(false));
                    } else {
                        Log.d("KeyguardSecPinBasedInputViewController", "Can't apply night mode! NOT supported OPEN THEME feature");
                    }
                }
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                keyguardSecPinBasedInputViewController.mIsMainDisplay = !z2 || configuration.semDisplayDeviceType == 0;
                Rect rect = new Rect(configuration.windowConfiguration.getBounds());
                if (!z2 || keyguardSecPinBasedInputViewController.mWindowRect.equals(rect)) {
                    return;
                }
                StringBuilder sb = new StringBuilder("onConfigurationChanged ");
                sb.append(keyguardSecPinBasedInputViewController.mWindowRect);
                sb.append(" -> ");
                sb.append(rect);
                sb.append(" isFolderClosed : ");
                sb.append(KeyguardSecPinBasedInputViewController.isFolderClosed());
                sb.append(" mIsMainDisplay : ");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, keyguardSecPinBasedInputViewController.mIsMainDisplay, "KeyguardSecPinBasedInputViewController");
                keyguardSecPinBasedInputViewController.mWindowRect.set(rect);
                if (keyguardSecPinBasedInputViewController.skipUpdateWhenCloseFolder()) {
                    keyguardSecPinBasedInputViewController.mUpdateSkipped = true;
                    Log.d("KeyguardSecPinBasedInputViewController", "Skip folder closed case");
                } else {
                    keyguardSecPinBasedInputViewController.updateLayout$1();
                    keyguardSecPinBasedInputViewController.initializeBottomContainerView$1();
                }
            }
        };
        this.mInitializeBottomContainerViewRunnable = new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6(this, 1);
        new DisplayLifecycle.Observer(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController.3
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onFolderStateChanged isFolderOpened : ", "KeyguardSecPinBasedInputViewController", z);
            }
        };
        this.mConfigurationController = configurationController;
    }

    public static boolean isFolderClosed() {
        return LsRune.SECURITY_SUB_DISPLAY_LOCK && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
    }

    public static void updateNumPadKeySideMargin(View view, int i) {
        if (view != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        if (TextUtils.isEmpty(editable)) {
            return;
        }
        onUserInput();
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        getKeyguardSecurityCallback().userActivity();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    public final byte[] getPasswordText() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        return passwordTextView instanceof SecPasswordTextView ? KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(((SecPasswordTextView) passwordTextView).mText) : KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(passwordTextView.getText());
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void initializeBottomContainerView() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            Log.d("KeyguardSecPinBasedInputViewController", "First initializeBottomContainerView");
            initializeBottomContainerView$1();
        } else {
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 keyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 = this.mInitializeBottomContainerViewRunnable;
            Handler handler = this.mHandler;
            if (handler.hasCallbacks(keyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6)) {
                handler.removeCallbacks(this.mInitializeBottomContainerViewRunnable);
            }
            handler.post(this.mInitializeBottomContainerViewRunnable);
        }
    }

    public final void initializeBottomContainerView$1() {
        Resources resources = getResources();
        Rect rect = new Rect(resources.getConfiguration().windowConfiguration.getBounds());
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK && !this.mWindowRect.equals(rect)) {
            Log.d("KeyguardSecPinBasedInputViewController", "window rect changed initializeBottomContainerView abnormal case return!" + this.mWindowRect + " " + rect);
            return;
        }
        int i = 0;
        this.mIsImagePinLock = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isOpenThemeLook() && resources.getBoolean(R.bool.theme_use_image_pinlock);
        View view = this.mOkButton;
        if (view != null) {
            view.setVisibility(8);
        }
        if (this.mIsImagePinLock) {
            this.mOkButton = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key_enter);
        } else {
            this.mOkButton = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.key_enter_text);
        }
        View view2 = this.mOkButton;
        if (view2 != null) {
            view2.setVisibility(0);
            this.mOkButton.setOnTouchListener(this.mActionButtonTouchListener);
            final int i2 = 0;
            this.mOkButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) throws Resources.NotFoundException {
                    int i3 = i2;
                    KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
                    switch (i3) {
                        case 0:
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled() && keyguardSecPinBasedInputViewController.mOkButton.getAlpha() > 0.4f) {
                                keyguardSecPinBasedInputViewController.verifyPasswordAndUnlock();
                                break;
                            }
                            break;
                        default:
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                                keyguardSecPinBasedInputViewController.mPasswordEntry.deleteLastChar();
                                keyguardSecPinBasedInputViewController.mDeleteButton.setLongClickable(false);
                                PasswordTextView passwordTextView = keyguardSecPinBasedInputViewController.mPasswordEntry;
                                if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
                                    keyguardSecPinBasedInputViewController.setOkButtonEnabled(false);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
            final int i3 = 0;
            this.mOkButton.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda2
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view3, int i4, KeyEvent keyEvent) {
                    switch (i3) {
                        case 0:
                            if (LsRune.SECURITY_BOUNCER_WINDOW && keyEvent.getAction() == 0 && KeyEvent.isConfirmKey(i4)) {
                                view3.performClick();
                                break;
                            }
                            break;
                        default:
                            if (LsRune.SECURITY_BOUNCER_WINDOW && keyEvent.getAction() == 0 && KeyEvent.isConfirmKey(i4)) {
                                view3.performClick();
                                view3.requestFocus();
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.mOkButton.setAccessibilityDelegate(this.mAccessibilityDelegate);
            this.mOkButton.setOnHoverListener(null);
            if (this.mIsImagePinLock) {
                ((ImageButton) this.mOkButton).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
        View viewFindViewById = ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.delete_button);
        this.mDeleteButton = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(0);
            this.mDeleteButton.setOnTouchListener(this.mActionButtonTouchListener);
            final int i4 = 1;
            this.mDeleteButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) throws Resources.NotFoundException {
                    int i32 = i4;
                    KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
                    switch (i32) {
                        case 0:
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled() && keyguardSecPinBasedInputViewController.mOkButton.getAlpha() > 0.4f) {
                                keyguardSecPinBasedInputViewController.verifyPasswordAndUnlock();
                                break;
                            }
                            break;
                        default:
                            if (keyguardSecPinBasedInputViewController.mPasswordEntry.isEnabled()) {
                                keyguardSecPinBasedInputViewController.mPasswordEntry.deleteLastChar();
                                keyguardSecPinBasedInputViewController.mDeleteButton.setLongClickable(false);
                                PasswordTextView passwordTextView = keyguardSecPinBasedInputViewController.mPasswordEntry;
                                if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
                                    keyguardSecPinBasedInputViewController.setOkButtonEnabled(false);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
            final int i5 = 1;
            this.mDeleteButton.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda2
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view3, int i42, KeyEvent keyEvent) {
                    switch (i5) {
                        case 0:
                            if (LsRune.SECURITY_BOUNCER_WINDOW && keyEvent.getAction() == 0 && KeyEvent.isConfirmKey(i42)) {
                                view3.performClick();
                                break;
                            }
                            break;
                        default:
                            if (LsRune.SECURITY_BOUNCER_WINDOW && keyEvent.getAction() == 0 && KeyEvent.isConfirmKey(i42)) {
                                view3.performClick();
                                view3.requestFocus();
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.mDeleteButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda5
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view3) {
                    KeyguardSecPinBasedInputViewController.$r8$lambda$uU6id3dFiGU_1NwKYrhseXInPYo(this.f$0);
                    return true;
                }
            });
            this.mDeleteButton.setAccessibilityDelegate(this.mAccessibilityDelegate);
            String string = resources.getString(R.string.kg_keycode_delete);
            View view3 = this.mDeleteButton;
            if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", ");
                sbM.append(String.format(resources.getString(R.string.kg_keycode_delete_help_text_to), resources.getString(R.string.kg_keycode_delete_help_text_double_tap), resources.getString(R.string.kg_keycode_delete)));
                sbM.append(", ");
                sbM.append(String.format(resources.getString(R.string.kg_keycode_delete_help_text_and_hold_to), resources.getString(R.string.kg_keycode_delete_help_text_double_tap), resources.getString(R.string.kg_keycode_delete_all)));
                string = sbM.toString();
            }
            view3.setContentDescription(string);
            this.mDeleteButton.setOnHoverListener(null);
            ((ImageButton) this.mDeleteButton).setScaleType(this.mIsImagePinLock ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.CENTER);
        }
        getContext();
        if (WallpaperUtils.isWhiteKeyguardWallpaper(512L, false)) {
            this.mOkButtonRipple = (SeslRecoilDrawable) getContext().getDrawable(R.drawable.ripple_drawable_pin_whitebg);
            this.mDeleteButtonRipple = (SeslRecoilDrawable) getContext().getDrawable(R.drawable.ripple_drawable_pin_whitebg);
        } else {
            this.mOkButtonRipple = (SeslRecoilDrawable) getContext().getDrawable(R.drawable.origin_ripple_drawable);
            this.mDeleteButtonRipple = (SeslRecoilDrawable) getContext().getDrawable(R.drawable.origin_ripple_drawable);
        }
        View view4 = this.mOkButton;
        if (view4 != null) {
            view4.setBackground(this.mOkButtonRipple);
        }
        View view5 = this.mDeleteButton;
        if (view5 != null) {
            view5.setBackground(this.mDeleteButtonRipple);
        }
        for (int i6 = 0; i6 < 10; i6++) {
            View view6 = this.mButtons[i6];
            if (!(view6 instanceof SecNumPadKey)) {
                break;
            }
            ((SecNumPadKey) view6).updateViewStyle();
        }
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        boolean z2 = z && this.mIsMainDisplay && !DeviceState.isSmartViewFitToActiveDisplay();
        Resources resources2 = getResources();
        int iWidth = z2 ? this.mWindowRect.width() : Math.min(this.mWindowRect.width(), this.mWindowRect.height());
        int iHeight = this.mWindowRect.height();
        float f = resources2.getFloat(DeviceType.isTablet() ? R.dimen.tablet_num_pad_key_size_ratio : !z2 ? !this.mIsMainDisplay ? R.dimen.fold_sub_num_pad_key_size_ratio : R.dimen.num_pad_key_size_ratio : R.dimen.fold_num_pad_key_size_ratio);
        if (z) {
            StringBuilder sbM2 = RowView$$ExternalSyntheticOutline0.m("getNumPadKeySize isDualDisplayPolicyAllowed : ", " isFolderClosed : ", z2);
            sbM2.append(isFolderClosed());
            sbM2.append(" mIsMainDisplay : ");
            sbM2.append(this.mIsMainDisplay);
            sbM2.append(" ratio : ");
            sbM2.append(f);
            sbM2.append(" isMultiFoldClosed : true displayWidth : ");
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sbM2, iWidth, " displayHeight : ", iHeight, "KeyguardSecPinBasedInputViewController");
        }
        int i7 = (int) ((z2 ? iHeight : iWidth) * f);
        int i8 = (int) (getResources().getFloat(DeviceType.isTablet() ? R.dimen.tablet_num_pad_key_side_margin_ratio : !z2 ? !this.mIsMainDisplay ? R.dimen.fold_sub_num_pad_key_side_margin_ratio : R.dimen.num_pad_key_side_margin_ratio : R.dimen.fold_num_pad_key_side_margin_ratio) * ((DeviceType.isTablet() || z2) ? this.mWindowRect.width() : Math.min(this.mWindowRect.width(), this.mWindowRect.height())));
        for (int i9 = 0; i9 < 10; i9++) {
            View view7 = this.mButtons[i9];
            if (view7 != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view7.getLayoutParams();
                layoutParams.width = i7;
                layoutParams.height = i7;
                view7.setLayoutParams(layoutParams);
            }
            View view8 = this.mButtons[i9];
            if (view8 instanceof SecNumPadKeyTablet) {
                ((SecNumPadKeyTablet) view8).updateDigitTextSize();
                ((SecNumPadKeyTablet) this.mButtons[i9]).updateKlondikeTextSize();
            } else {
                ((SecNumPadKey) view8).updateDigitTextSize();
                ((SecNumPadKey) this.mButtons[i9]).updateKlondikeTextSize();
            }
        }
        View view9 = this.mOkButton;
        if (view9 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view9.getLayoutParams();
            layoutParams2.width = i7;
            layoutParams2.height = i7;
            view9.setLayoutParams(layoutParams2);
        }
        View view10 = this.mDeleteButton;
        if (view10 != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view10.getLayoutParams();
            layoutParams3.width = i7;
            layoutParams3.height = i7;
            view10.setLayoutParams(layoutParams3);
        }
        updateNumPadKeySideMargin(this.mButtons[2], i8);
        updateNumPadKeySideMargin(this.mButtons[5], i8);
        updateNumPadKeySideMargin(this.mButtons[8], i8);
        updateNumPadKeySideMargin(this.mButtons[0], i8);
        if (!this.mIsImagePinLock) {
            View view11 = this.mOkButton;
            if (view11 instanceof SystemUITextView) {
                ((SystemUITextView) view11).setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_ok_button_font_size));
            }
        }
        ViewGroup viewGroup = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row1);
        ViewGroup viewGroup2 = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row2);
        ViewGroup viewGroup3 = (ViewGroup) ((KeyguardSecPinBasedInputView) this.mView).findViewById(R.id.row3);
        int i10 = (int) (getResources().getFloat(DeviceType.isTablet() ? R.dimen.tablet_num_pad_key_bottom_margin_ratio : z2 ? R.dimen.fold_num_pad_key_bottom_margin_ratio : !this.mIsMainDisplay ? R.dimen.fold_sub_num_pad_key_bottom_margin_ratio : R.dimen.num_pad_key_bottom_margin_ratio) * (z2 ? this.mWindowRect.height() : Math.max(this.mWindowRect.width(), this.mWindowRect.height())));
        if (viewGroup != null) {
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams4.bottomMargin = i10;
            viewGroup.setLayoutParams(layoutParams4);
            if (viewGroup2 != null) {
                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) viewGroup2.getLayoutParams();
                layoutParams5.bottomMargin = i10;
                viewGroup2.setLayoutParams(layoutParams5);
            }
            if (viewGroup3 != null) {
                LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) viewGroup3.getLayoutParams();
                layoutParams6.bottomMargin = i10;
                viewGroup3.setLayoutParams(layoutParams6);
            }
        }
        if (((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isForgotPasswordView()) {
            this.mOriginPinEntryId = this.mPasswordEntry.getId();
            int iGenerateViewId = View.generateViewId();
            this.mPasswordEntry.setId(iGenerateViewId);
            while (i < 10) {
                View view12 = this.mButtons[i];
                if (!(view12 instanceof SecNumPadKey)) {
                    return;
                }
                ((SecNumPadKey) view12).mTextViewResId = iGenerateViewId;
                i++;
            }
            return;
        }
        int id = this.mPasswordEntry.getId();
        if (this.mOriginPinEntryId != id) {
            this.mOriginPinEntryId = id;
            this.mPasswordEntry.setId(id);
            int i11 = this.mOriginPinEntryId;
            while (i < 10) {
                View view13 = this.mButtons[i];
                if (!(view13 instanceof SecNumPadKey)) {
                    return;
                }
                ((SecNumPadKey) view13).mTextViewResId = i11;
                i++;
            }
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) throws Resources.NotFoundException {
        if (i != 0 && i != 6 && i != 5) {
            return false;
        }
        verifyPasswordAndUnlock();
        return true;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK && this.mUpdateSkipped) {
            this.mUpdateSkipped = false;
            if (isFolderClosed()) {
                initializeBottomContainerView$1();
            }
        }
        this.mHandler.postDelayed(new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6(this, 0), 100L);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        Configuration configuration = getResources().getConfiguration();
        this.mWindowRect.set(configuration.windowConfiguration.getBounds());
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        this.mIsMainDisplay = !z || configuration.semDisplayDeviceType == 0;
        if (z) {
            StringBuilder sb = new StringBuilder("onViewAttached mWindowRect : ");
            sb.append(this.mWindowRect);
            sb.append(" isFolderClosed : ");
            sb.append(isFolderClosed());
            sb.append(" mIsMainDisplay : ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mIsMainDisplay, "KeyguardSecPinBasedInputViewController");
        }
        this.mPasswordEntry.mShowPassword = Settings.System.getInt(getContext().getContentResolver(), "show_password", 1) == 1;
        this.mPasswordEntry.setOnTouchListener(new KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda9());
        this.mPasswordEntry.setOnKeyListener(this.mOnKeyListener);
        PasswordTextView passwordTextView = this.mPasswordEntry;
        passwordTextView.mUserActivityListener = new BaseSecPasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda10
            @Override // com.android.keyguard.BaseSecPasswordTextView.UserActivityListener
            public final void onUserActivity() {
                KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
                keyguardSecPinBasedInputViewController.onUserInput();
                keyguardSecPinBasedInputViewController.setOkButtonEnabled(true);
            }
        };
        passwordTextView.setAutoHandwritingEnabled(false);
        this.mPasswordEntry.setLongClickable(false);
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mPasswordEntry.setSelected(false);
        } else {
            this.mPasswordEntry.requestFocus();
        }
        initializeBottomContainerView();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        if (((KeyguardSecPinBasedInputView) this.mView).getVisibility() == 0) {
            ((KeyguardSecPinBasedInputView) this.mView).setPasswordEntryEnabled(true);
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if ((passwordTextView instanceof SecPasswordTextView) && ((SecPasswordTextView) passwordTextView).mText.isEmpty()) {
            setOkButtonEnabled(false);
        }
    }

    public final void setEnabledKeypad(boolean z) {
        View view = this.mDeleteButton;
        if (view != null) {
            view.setFocusable(z);
            this.mDeleteButton.setClickable(z);
            this.mDeleteButton.setAlpha(z ? 1.0f : 0.4f);
        }
        for (int i = 0; i < 10; i++) {
            this.mButtons[i].setFocusable(z);
            this.mButtons[i].setClickable(z);
            this.mButtons[i].setAlpha(z ? 1.0f : 0.4f);
        }
    }

    public final void setOkButtonContentDescription(boolean z, boolean z2) throws Resources.NotFoundException {
        if (this.mOkButton != null) {
            String string = getResources().getString(R.string.kg_keycode_ok);
            if (z2) {
                this.mOkButton.setContentDescription(string);
                return;
            }
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", ");
            sbM.append(getResources().getString(R.string.accessibility_button));
            String string2 = sbM.toString();
            View view = this.mOkButton;
            if (!z) {
                StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string2, ", ");
                sbM2.append(getResources().getString(R.string.kg_keycode_ok_disabled));
                string2 = sbM2.toString();
            }
            view.setContentDescription(string2);
        }
    }

    public void setOkButtonEnabled(boolean z) {
        View view = this.mOkButton;
        if (view != null) {
            view.setFocusable(z);
            this.mOkButton.setClickable(z);
            this.mOkButton.setAlpha(z ? 1.0f : 0.4f);
            setOkButtonContentDescription(z, false);
            this.mDeleteButton.setLongClickable(z);
        }
    }

    public void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (skipUpdateWhenCloseFolder()) {
            return;
        }
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 keyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 = this.mInitializeBottomContainerViewRunnable;
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(keyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6)) {
            handler.removeCallbacks(this.mInitializeBottomContainerViewRunnable);
        }
        handler.post(this.mInitializeBottomContainerViewRunnable);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void verifyPasswordAndUnlock() throws Resources.NotFoundException {
        super.verifyPasswordAndUnlock();
        setOkButtonEnabled(false);
        setOkButtonContentDescription(false, true);
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
