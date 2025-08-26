package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class KeyguardPasswordViewController extends KeyguardSecAbsKeyInputViewController {
    public final InputMethodManager mInputMethodManager;
    public final KeyguardPasswordViewController$$ExternalSyntheticLambda3 mKeyListener;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardViewController mKeyguardViewController;
    public final DelayableExecutor mMainExecutor;
    public final KeyguardPasswordViewController$$ExternalSyntheticLambda2 mOnEditorActionListener;
    public final EditText mPasswordEntry;
    public boolean mPaused;
    public final KeyguardPasswordViewController$$ExternalSyntheticLambda1 mPostureCallback;
    public final DevicePostureController mPostureController;
    public final boolean mShowImeAtScreenOn;
    public final ImageView mSwitchImeButton;
    public final AnonymousClass1 mTextWatcher;

    /* renamed from: $r8$lambda$1k-SdfXqdHUZhOkcOYn3WW-APZw, reason: not valid java name */
    public static /* synthetic */ void m956$r8$lambda$1kSdfXqdHUZhOkcOYn3WWAPZw(KeyguardPasswordViewController keyguardPasswordViewController) {
        keyguardPasswordViewController.mKeyguardSecurityCallback.userActivity();
        keyguardPasswordViewController.mInputMethodManager.showInputMethodPickerFromSystem(false, ((KeyguardPasswordView) keyguardPasswordViewController.mView).getContext().getDisplayId());
    }

    public static /* synthetic */ void $r8$lambda$pfvxiKS_CgCR3sXpIpEKkFQKRAc(KeyguardPasswordViewController keyguardPasswordViewController) {
        keyguardPasswordViewController.mPasswordEntry.clearFocus();
        super.onPause();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardPasswordViewController$1] */
    public KeyguardPasswordViewController(KeyguardPasswordView keyguardPasswordView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, BouncerHapticPlayer bouncerHapticPlayer, UserActivityNotifier userActivityNotifier) throws Resources.NotFoundException {
        super(keyguardPasswordView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor, bouncerHapticPlayer, userActivityNotifier);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                ((KeyguardPasswordView) this.f$0.mView).onDevicePostureChanged(i);
            }
        };
        this.mOnEditorActionListener = new TextView.OnEditorActionListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda2
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
                if (keyEvent != null) {
                    return false;
                }
                if (i != 0 && i != 6 && i != 5) {
                    return false;
                }
                keyguardPasswordViewController.verifyPasswordAndUnlock();
                return true;
            }
        };
        this.mKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
                if (keyEvent == null || !KeyEvent.isConfirmKey(i) || i == 62 || keyEvent.getAction() != 0) {
                    return false;
                }
                keyguardPasswordViewController.verifyPasswordAndUnlock();
                return true;
            }
        };
        this.mTextWatcher = new TextWatcher() { // from class: com.android.keyguard.KeyguardPasswordViewController.1
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    return;
                }
                KeyguardPasswordViewController.this.onUserInput();
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                KeyguardPasswordViewController.this.mKeyguardSecurityCallback.userActivity();
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
        this.mKeyguardSecurityCallback = keyguardSecurityCallback;
        this.mInputMethodManager = inputMethodManager;
        this.mPostureController = devicePostureController;
        this.mMainExecutor = delayableExecutor;
        this.mKeyguardViewController = keyguardViewController;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mShowImeAtScreenOn = resources.getBoolean(R.bool.kg_show_ime_at_screen_on);
        KeyguardPasswordView keyguardPasswordView2 = (KeyguardPasswordView) this.mView;
        keyguardPasswordView2.getClass();
        EditText editText = (EditText) keyguardPasswordView2.findViewById(R.id.passwordEntry);
        this.mPasswordEntry = editText;
        editText.getBackground();
        getResources().getDrawable(R.drawable.bouncer_password_view_background);
        this.mSwitchImeButton = (ImageView) ((KeyguardPasswordView) this.mView).findViewById(R.id.switch_ime_button);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R.string.keyguard_enter_your_password;
    }

    public boolean hasMultipleEnabledIMEsOrSubtypes(InputMethodManager inputMethodManager) {
        Iterator it = inputMethodManager.getEnabledInputMethodListAsUser(UserHandle.of(this.mSelectedUserInteractor.getSelectedUserId())).iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                if (i > 1 || inputMethodManager.getEnabledInputMethodSubtypeList(null, false).size() > 1) {
                    break;
                }
                return false;
            }
            InputMethodInfo inputMethodInfo = (InputMethodInfo) it.next();
            if (i > 1) {
                break;
            }
            List<InputMethodSubtype> enabledInputMethodSubtypeList = inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
            if (!enabledInputMethodSubtypeList.isEmpty()) {
                Iterator<InputMethodSubtype> it2 = enabledInputMethodSubtypeList.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    if (it2.next().isAuxiliary()) {
                        i2++;
                    }
                }
                if (enabledInputMethodSubtypeList.size() - i2 <= 0) {
                }
            }
            i++;
        }
        return true;
    }

    public final boolean isHideKeyboardByDefault() {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean z = LsRune.SECURITY_FINGERPRINT_IN_DISPLAY;
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        boolean z2 = z && keyguardUpdateMonitor.isFingerprintOptionEnabled();
        boolean userUnlockedWithBiometric = keyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId);
        return keyguardUpdateMonitor.is2StepVerification() ? z2 && !userUnlockedWithBiometric : (z2 && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) || userUnlockedWithBiometric || (LsRune.SECURITY_SUB_DISPLAY_LOCK && (keyguardUpdateMonitor.isFingerprintDetectionRunning() || keyguardUpdateMonitor.isFaceDetectionRunning()));
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return true;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        if (this.mPaused) {
            return;
        }
        this.mPaused = true;
        if (this.mPasswordEntry.isVisibleToUser()) {
            ((KeyguardPasswordView) this.mView).mOnFinishImeAnimationRunnable = new KeyguardPasswordViewController$$ExternalSyntheticLambda0(this, 1);
        } else {
            super.onPause();
        }
        KeyguardPasswordView keyguardPasswordView = (KeyguardPasswordView) this.mView;
        keyguardPasswordView.getClass();
        keyguardPasswordView.post(new KeyguardPasswordView$$ExternalSyntheticLambda0(keyguardPasswordView));
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        this.mPaused = false;
        if (isHideKeyboardByDefault()) {
            return;
        }
        if (i != 1 || this.mShowImeAtScreenOn) {
            showInput();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onStartingToHide() {
        KeyguardPasswordView keyguardPasswordView = (KeyguardPasswordView) this.mView;
        keyguardPasswordView.getClass();
        keyguardPasswordView.post(new KeyguardPasswordView$$ExternalSyntheticLambda0(keyguardPasswordView));
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        this.mPasswordEntry.setTextOperationUser(UserHandle.of(this.mSelectedUserInteractor.getSelectedUserId()));
        this.mPasswordEntry.setKeyListener(TextKeyListener.getInstance());
        this.mPasswordEntry.setInputType(129);
        KeyguardPasswordView keyguardPasswordView = (KeyguardPasswordView) this.mView;
        DevicePostureControllerImpl devicePostureControllerImpl = (DevicePostureControllerImpl) this.mPostureController;
        keyguardPasswordView.onDevicePostureChanged(devicePostureControllerImpl.getDevicePosture());
        devicePostureControllerImpl.addCallback(this.mPostureCallback);
        this.mPasswordEntry.setSelected(true);
        this.mPasswordEntry.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mPasswordEntry.setOnKeyListener(this.mKeyListener);
        this.mPasswordEntry.addTextChangedListener(this.mTextWatcher);
        this.mPasswordEntry.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPasswordViewController.2
            public final boolean mAutomotiveAndVisibleBackgroundUsers;

            {
                Context context = KeyguardPasswordViewController.this.getContext();
                this.mAutomotiveAndVisibleBackgroundUsers = context.getPackageManager().hasSystemFeature("android.hardware.type.automotive") && UserManager.isVisibleBackgroundUsersEnabled() && context.getResources().getBoolean(android.R.bool.config_perDisplayFocusEnabled);
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (this.mAutomotiveAndVisibleBackgroundUsers) {
                    KeyguardPasswordViewController.this.mInputMethodManager.restartInput(view);
                }
                KeyguardPasswordViewController.this.mKeyguardSecurityCallback.userActivity();
            }
        });
        final int i = 0;
        this.mSwitchImeButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda4
            public final /* synthetic */ KeyguardPasswordViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
                switch (i2) {
                    case 0:
                        KeyguardPasswordViewController.m956$r8$lambda$1kSdfXqdHUZhOkcOYn3WWAPZw(keyguardPasswordViewController);
                        break;
                    default:
                        KeyguardSecurityCallback keyguardSecurityCallback = keyguardPasswordViewController.mKeyguardSecurityCallback;
                        keyguardSecurityCallback.reset();
                        keyguardSecurityCallback.onCancelClicked();
                        break;
                }
            }
        });
        View viewFindViewById = ((KeyguardPasswordView) this.mView).findViewById(R.id.cancel_button);
        if (viewFindViewById != null) {
            final int i2 = 1;
            viewFindViewById.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda4
                public final /* synthetic */ KeyguardPasswordViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i2;
                    KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
                    switch (i22) {
                        case 0:
                            KeyguardPasswordViewController.m956$r8$lambda$1kSdfXqdHUZhOkcOYn3WWAPZw(keyguardPasswordViewController);
                            break;
                        default:
                            KeyguardSecurityCallback keyguardSecurityCallback = keyguardPasswordViewController.mKeyguardSecurityCallback;
                            keyguardSecurityCallback.reset();
                            keyguardSecurityCallback.onCancelClicked();
                            break;
                    }
                }
            });
        }
        updateSwitchImeButton();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mPasswordEntry.setOnEditorActionListener(null);
        ((DevicePostureControllerImpl) this.mPostureController).removeCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        this.mPasswordEntry.setTextOperationUser(UserHandle.of(this.mSelectedUserInteractor.getSelectedUserId()));
        this.mMessageAreaController.setMessage(getInitialMessageResId());
        boolean zIsEnabled = this.mPasswordEntry.isEnabled();
        ((KeyguardPasswordView) this.mView).setPasswordEntryEnabled(true);
        ((KeyguardPasswordView) this.mView).setPasswordEntryInputEnabled(true);
        if (this.mResumed && this.mPasswordEntry.isVisibleToUser() && zIsEnabled) {
            showInput();
        }
    }

    public final void showInput() {
        if (this.mKeyguardViewController.isBouncerShowing() && ((KeyguardPasswordView) this.mView).isShown() && this.mPasswordEntry.isEnabled()) {
            ((KeyguardPasswordView) this.mView).post(new KeyguardPasswordViewController$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public void updateSwitchImeButton() {
        boolean z = this.mSwitchImeButton.getVisibility() == 0;
        boolean zHasMultipleEnabledIMEsOrSubtypes = hasMultipleEnabledIMEsOrSubtypes(this.mInputMethodManager);
        if (z != zHasMultipleEnabledIMEsOrSubtypes) {
            this.mSwitchImeButton.setVisibility(zHasMultipleEnabledIMEsOrSubtypes ? 0 : 8);
        }
        if (this.mSwitchImeButton.getVisibility() != 0) {
            ViewGroup.LayoutParams layoutParams = this.mPasswordEntry.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(0);
                this.mPasswordEntry.setLayoutParams(layoutParams);
            }
        }
    }
}
