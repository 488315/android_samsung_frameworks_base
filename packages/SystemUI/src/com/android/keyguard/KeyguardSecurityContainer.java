package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.os.UserManager;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.Utils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractorKt;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.legacyhelper.ui.LegacyUserUiHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class KeyguardSecurityContainer extends ConstraintLayout {
    static final float MIN_BACK_SCALE = 0.9f;
    public AlertDialog mAlertDialog;
    public final AnonymousClass2 mBackCallback;
    public Executor mBgExecutor;
    public int mCurrentMode;
    public boolean mDisappearAnimRunning;
    public FalsingA11yDelegate mFalsingA11yDelegate;
    public FalsingManager mFalsingManager;
    public GlobalSettings mGlobalSettings;
    public boolean mIsAppearAnimationDelayed;
    public final List mMotionEventListeners;
    KeyguardSecurityViewFlipper mSecurityViewFlipper;
    public UserSwitcherController mUserSwitcherController;
    public ViewMediatorCallback mViewMediatorCallback;
    public ViewMode mViewMode;
    public int mWidth;

    public enum BouncerUiEvent implements UiEventLogger.UiEventEnum {
        UNKNOWN(0),
        BOUNCER_DISMISS_EXTENDED_ACCESS(413),
        BOUNCER_DISMISS_BIOMETRIC(414),
        BOUNCER_DISMISS_NONE_SECURITY(VolteConstants.ErrorCode.UNSUPPORTED_MEDIA_TYPE),
        BOUNCER_DISMISS_PASSWORD(VolteConstants.ErrorCode.UNSUPPORTED_URI_SCHEME),
        BOUNCER_DISMISS_SIM(417),
        BOUNCER_PASSWORD_SUCCESS(418),
        BOUNCER_PASSWORD_FAILURE(419);

        private final int mId;

        BouncerUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public class DefaultViewMode implements ViewMode {
        public KeyguardSecurityContainer mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void init(KeyguardSecurityContainer keyguardSecurityContainer, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
            this.mView = keyguardSecurityContainer;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.connect(this.mViewFlipper.getId(), 6, 0, 6);
            constraintSet.connect(this.mViewFlipper.getId(), 7, 0, 7);
            constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet.constrainHeight(this.mViewFlipper.getId(), 0);
            constraintSet.constrainWidth(this.mViewFlipper.getId(), 0);
            constraintSet.applyTo(this.mView);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void onDestroy() {
            if (this.mView == null) {
                return;
            }
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.mView);
            constraintSet.clear(this.mViewFlipper.getId());
            constraintSet.applyTo(this.mView);
        }
    }

    public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public /* synthetic */ DoubleTapListener(KeyguardSecurityContainer keyguardSecurityContainer, int i) {
            this();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent motionEvent) {
            return KeyguardSecurityContainer.this.handleDoubleTap(motionEvent);
        }

        private DoubleTapListener() {
        }
    }

    public class OneHandedViewMode extends SidedSecurityMode {
        public KeyguardSecurityContainer mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void init(KeyguardSecurityContainer keyguardSecurityContainer, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
            super.mView = keyguardSecurityContainer;
            this.mGlobalSettings = globalSettings;
            this.mDefaultSideSetting = 0;
            this.mView = keyguardSecurityContainer;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void onDestroy() {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.mView);
            constraintSet.clear(this.mViewFlipper.getId());
            constraintSet.applyTo(this.mView);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void updatePositionByTouchX(float f) {
            boolean z = f <= ((float) this.mView.getWidth()) / 2.0f ? 1 : 0;
            this.mGlobalSettings.putInt("one_handed_keyguard_side", !z);
            updateSecurityViewLocation(z, false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void updateSecurityViewLocation() {
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.SidedSecurityMode
        public final void updateSecurityViewLocation(boolean z, boolean z2) {
            if (z2) {
                TransitionManager.beginDelayedTransition(this.mView, new KeyguardSecurityViewTransition());
            }
            ConstraintSet constraintSet = new ConstraintSet();
            if (z) {
                constraintSet.connect(this.mViewFlipper.getId(), 1, 0, 1);
            } else {
                constraintSet.connect(this.mViewFlipper.getId(), 2, 0, 2);
            }
            constraintSet.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet.constrainPercentWidth(this.mViewFlipper.getId(), 0.5f);
            constraintSet.applyTo(this.mView);
        }
    }

    public abstract class SidedSecurityMode implements ViewMode {
        public int mDefaultSideSetting;
        public GlobalSettings mGlobalSettings;
        public KeyguardSecurityContainer mView;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void handleDoubleTap(MotionEvent motionEvent) {
            boolean zIsLeftAligned = isLeftAligned();
            float x = motionEvent.getX();
            if ((zIsLeftAligned && x > ((float) this.mView.getWidth()) / 2.0f) || (!zIsLeftAligned && x < ((float) this.mView.getWidth()) / 2.0f)) {
                boolean z = !zIsLeftAligned;
                this.mGlobalSettings.putInt("one_handed_keyguard_side", zIsLeftAligned ? 1 : 0);
                SysUiStatsLog.write(63, !zIsLeftAligned ? 5 : 6);
                updateSecurityViewLocation(z, true);
            }
        }

        public final boolean isLeftAligned() {
            return this.mGlobalSettings.getInt("one_handed_keyguard_side", this.mDefaultSideSetting) == 0;
        }

        public abstract void updateSecurityViewLocation(boolean z, boolean z2);
    }

    public class UserSwitcherViewMode extends SidedSecurityMode {
        public final Executor mBgExecutor;
        public FalsingA11yDelegate mFalsingA11yDelegate;
        public FalsingManager mFalsingManager;
        public KeyguardUserSwitcherPopupMenu mPopup;
        public Resources mResources;
        public final KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda2 mUserSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.policy.UserSwitcherController.UserSwitchCallback
            public final void onUserSwitched() {
                this.f$0.setupUserSwitcher();
            }
        };
        public TextView mUserSwitcher;
        public final UserSwitcherCallback mUserSwitcherCallback;
        public UserSwitcherController mUserSwitcherController;
        public ViewGroup mUserSwitcherViewGroup;
        public KeyguardSecurityContainer mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        public interface UserSwitcherCallback {
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda2] */
        public UserSwitcherViewMode(UserSwitcherCallback userSwitcherCallback, Executor executor) {
            this.mUserSwitcherCallback = userSwitcherCallback;
            this.mBgExecutor = executor;
        }

        public final void inflateUserSwitcher() {
            LayoutInflater.from(this.mView.getContext()).inflate(R.layout.keyguard_bouncer_user_switcher, (ViewGroup) this.mView, true);
            this.mUserSwitcherViewGroup = (ViewGroup) this.mView.findViewById(R.id.keyguard_bouncer_user_switcher);
            this.mUserSwitcher = (TextView) this.mView.findViewById(R.id.user_switcher_header);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void init(KeyguardSecurityContainer keyguardSecurityContainer, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) throws Resources.NotFoundException {
            super.mView = keyguardSecurityContainer;
            this.mGlobalSettings = globalSettings;
            this.mDefaultSideSetting = 1;
            this.mView = keyguardSecurityContainer;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            this.mFalsingManager = falsingManager;
            this.mUserSwitcherController = userSwitcherController;
            this.mResources = keyguardSecurityContainer.getContext().getResources();
            this.mFalsingA11yDelegate = falsingA11yDelegate;
            if (this.mUserSwitcherViewGroup == null) {
                inflateUserSwitcher();
                setupUserSwitcher();
                this.mUserSwitcherController.addUserSwitchCallback(this.mUserSwitchCallback);
            }
            updateSecurityViewLocation();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void onDensityOrFontScaleChanged() {
            this.mView.removeView(this.mUserSwitcherViewGroup);
            this.mView.removeView(this.mUserSwitcher);
            inflateUserSwitcher();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void onDestroy() {
            UserSwitcherController userSwitcherController = this.mUserSwitcherController;
            UserSwitcherInteractor.UserCallback userCallback = (UserSwitcherInteractor.UserCallback) userSwitcherController.callbackCompatMap.remove(this.mUserSwitchCallback);
            if (userCallback != null) {
                userSwitcherController.getMUserSwitcherInteractor().removeCallback(userCallback);
            }
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.mView);
            constraintSet.clear(this.mUserSwitcherViewGroup.getId());
            constraintSet.clear(this.mViewFlipper.getId());
            constraintSet.applyTo(this.mView);
            this.mView.removeView(this.mUserSwitcherViewGroup);
            this.mView.removeView(this.mUserSwitcher);
            this.mUserSwitcher = null;
            this.mUserSwitcherViewGroup = null;
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void reset() {
            KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = this.mPopup;
            if (keyguardUserSwitcherPopupMenu != null) {
                keyguardUserSwitcherPopupMenu.dismiss();
                this.mPopup = null;
            }
            setupUserSwitcher();
        }

        /* JADX WARN: Type inference failed for: r2v11, types: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2] */
        public final void setupUserSwitcher() {
            final UserRecord userRecord = (UserRecord) this.mUserSwitcherController.getMUserSwitcherInteractor().selectedUserRecord.$$delegate_0.getValue();
            if (userRecord == null) {
                Log.e("KeyguardSecurityView", "Current user in user switcher is null.");
                return;
            }
            if (this.mUserSwitcher == null) {
                Log.w("KeyguardSecurityView", "User switcher is not inflated, cannot setupUserSwitcher");
                return;
            }
            UserSwitcherController userSwitcherController = this.mUserSwitcherController;
            UserRecord userRecord2 = (UserRecord) userSwitcherController.getMUserSwitcherInteractor().selectedUserRecord.$$delegate_0.getValue();
            String userRecordName = userRecord2 != null ? LegacyUserUiHelper.getUserRecordName(userSwitcherController.applicationContext, userRecord2, userSwitcherController.getMUserSwitcherInteractor().isGuestUserAutoCreated, userSwitcherController.getMUserSwitcherInteractor().isGuestUserResetting) : null;
            final int i = userRecord.info.id;
            final KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0 keyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0 = new KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0(this);
            this.mBgExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    Object defaultUserIcon;
                    KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode = this.f$0;
                    int i2 = i;
                    KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0 keyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda02 = keyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0;
                    Bitmap userIcon = UserManager.get(userSwitcherViewMode.mView.getContext()).getUserIcon(i2);
                    if (userIcon != null) {
                        int dimensionPixelSize = userSwitcherViewMode.mResources.getDimensionPixelSize(R.dimen.bouncer_user_switcher_icon_size);
                        Context context = userSwitcherViewMode.mView.getContext();
                        Bitmap bitmapScaleDownIfNecessary = Icon.scaleDownIfNecessary(userIcon, dimensionPixelSize, dimensionPixelSize);
                        int i3 = CircleFramedDrawable.$r8$clinit;
                        defaultUserIcon = new CircleFramedDrawable(bitmapScaleDownIfNecessary, context.getResources().getDimensionPixelSize(R.dimen.update_user_photo_popup_min_width));
                    } else {
                        defaultUserIcon = UserIcons.getDefaultUserIcon(userSwitcherViewMode.mResources, i2, false);
                    }
                    keyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda02.accept(defaultUserIcon);
                }
            });
            this.mUserSwitcher.setText(userRecordName);
            final KeyguardUserSwitcherAnchor keyguardUserSwitcherAnchor = (KeyguardUserSwitcherAnchor) this.mView.findViewById(R.id.user_switcher_anchor);
            keyguardUserSwitcherAnchor.setAccessibilityDelegate(this.mFalsingA11yDelegate);
            final ?? r2 = new BaseUserSwitcherAdapter(this, this.mUserSwitcherController) { // from class: com.android.keyguard.KeyguardSecurityContainer.UserSwitcherViewMode.2
                @Override // android.widget.Adapter
                public final View getView(int i2, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                    Drawable layerDrawable;
                    UserRecord userRecord3 = (UserRecord) ((ArrayList) getUsers()).get(i2);
                    FrameLayout frameLayout = (FrameLayout) view;
                    if (frameLayout == null) {
                        frameLayout = (FrameLayout) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.keyguard_bouncer_user_switcher_item, viewGroup, false);
                    }
                    TextView textView = (TextView) frameLayout.getChildAt(0);
                    textView.setText(LegacyUserUiHelper.getUserRecordName(viewGroup.getContext(), userRecord3, this.controller.getMUserSwitcherInteractor().isGuestUserAutoCreated, this.controller.getMUserSwitcherInteractor().isGuestUserResetting));
                    Bitmap bitmap = userRecord3.picture;
                    boolean z = userRecord3.isSwitchToEnabled;
                    if (bitmap != null) {
                        layerDrawable = new BitmapDrawable(userRecord3.picture);
                    } else {
                        Context context = frameLayout.getContext();
                        Drawable drawable = (userRecord3.isCurrent && userRecord3.isGuest) ? context.getDrawable(R.drawable.ic_avatar_guest_user) : BaseUserSwitcherAdapter.getIconDrawable(context, userRecord3);
                        drawable.setTint(z ? Utils.getColorAttrDefaultColor(context, android.R.^attr-private.colorAccentPrimary, 0) : context.getResources().getColor(R.color.kg_user_switcher_restricted_avatar_icon_color, context.getTheme()));
                        Drawable drawable2 = context.getDrawable(R.drawable.user_avatar_bg);
                        drawable2.setTintBlendMode(BlendMode.DST);
                        drawable2.setTint(Utils.getColorAttrDefaultColor(context, android.R.^attr-private.colorSurfaceHighlight, 0));
                        layerDrawable = new LayerDrawable(new Drawable[]{drawable2, drawable});
                    }
                    int dimensionPixelSize = frameLayout.getResources().getDimensionPixelSize(R.dimen.bouncer_user_switcher_item_icon_size);
                    int dimensionPixelSize2 = frameLayout.getResources().getDimensionPixelSize(R.dimen.bouncer_user_switcher_item_icon_padding);
                    layerDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                    textView.setCompoundDrawablePadding(dimensionPixelSize2);
                    textView.setCompoundDrawablesRelative(layerDrawable, null, null, null);
                    if (userRecord3 == userRecord) {
                        textView.setBackground(frameLayout.getContext().getDrawable(R.drawable.bouncer_user_switcher_item_selected_bg));
                    } else {
                        textView.setBackground(null);
                    }
                    textView.setSelected(userRecord3 == userRecord);
                    frameLayout.setEnabled(z);
                    UserSwitcherController.Companion.getClass();
                    frameLayout.setAlpha(frameLayout.isEnabled() ? 1.0f : 0.38f);
                    return frameLayout;
                }
            };
            keyguardUserSwitcherAnchor.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    final KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode = this.f$0;
                    KeyguardUserSwitcherAnchor keyguardUserSwitcherAnchor2 = keyguardUserSwitcherAnchor;
                    final KeyguardSecurityContainer.UserSwitcherViewMode.AnonymousClass2 anonymousClass2 = r2;
                    if (userSwitcherViewMode.mFalsingManager.isFalseTap(1)) {
                        return;
                    }
                    KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = new KeyguardUserSwitcherPopupMenu(userSwitcherViewMode.mView.getContext(), userSwitcherViewMode.mFalsingManager);
                    userSwitcherViewMode.mPopup = keyguardUserSwitcherPopupMenu;
                    keyguardUserSwitcherPopupMenu.setAnchorView(keyguardUserSwitcherAnchor2);
                    userSwitcherViewMode.mPopup.setAdapter(anonymousClass2);
                    userSwitcherViewMode.mPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda4
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view2, int i2, long j) {
                            KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode2 = userSwitcherViewMode;
                            KeyguardSecurityContainer.UserSwitcherViewMode.AnonymousClass2 anonymousClass22 = anonymousClass2;
                            if (userSwitcherViewMode2.mFalsingManager.isFalseTap(1) || !view2.isEnabled() || userSwitcherViewMode2.mPopup == null) {
                                return;
                            }
                            UserRecord userRecord3 = (UserRecord) ((ArrayList) anonymousClass22.getUsers()).get(i2 - 1);
                            if (userRecord3.isManageUsers || userRecord3.isAddSupervisedUser) {
                                KeyguardSecurityContainerController keyguardSecurityContainerController = ((KeyguardSecurityContainerController$$ExternalSyntheticLambda2) userSwitcherViewMode2.mUserSwitcherCallback).f$0;
                                String string = keyguardSecurityContainerController.getContext().getString(R.string.keyguard_unlock_to_continue);
                                keyguardSecurityContainerController.showMessage(string, null, true);
                                BouncerMessageInteractor bouncerMessageInteractor = keyguardSecurityContainerController.mBouncerMessageInteractor;
                                BouncerMessageRepository.setMessage$default(bouncerMessageInteractor.repository, BouncerMessageInteractorKt.access$defaultMessage(bouncerMessageInteractor.getCurrentSecurityMode(), string, ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue()));
                            }
                            if (!userRecord3.isCurrent) {
                                anonymousClass22.onUserListItemClicked(userRecord3, null);
                            }
                            userSwitcherViewMode2.mPopup.dismiss();
                            userSwitcherViewMode2.mPopup = null;
                        }
                    });
                    userSwitcherViewMode.mPopup.show();
                }
            });
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) throws Resources.NotFoundException {
            ViewGroup viewGroup;
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password || (viewGroup = this.mUserSwitcherViewGroup) == null) {
                return;
            }
            viewGroup.setAlpha(0.0f);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            final int dimensionPixelSize = this.mView.getResources().getDimensionPixelSize(R.dimen.pin_view_trans_y_entry);
            valueAnimatorOfFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
            valueAnimatorOfFloat.setDuration(650L);
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardSecurityContainer.UserSwitcherViewMode.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ViewGroup viewGroup2 = UserSwitcherViewMode.this.mUserSwitcherViewGroup;
                    if (viewGroup2 != null) {
                        viewGroup2.setAlpha(1.0f);
                        UserSwitcherViewMode.this.mUserSwitcherViewGroup.setTranslationY(0.0f);
                    }
                }
            });
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode = this.f$0;
                    int i = dimensionPixelSize;
                    if (userSwitcherViewMode.mUserSwitcherViewGroup != null) {
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        userSwitcherViewMode.mUserSwitcherViewGroup.setAlpha(fFloatValue);
                        float f = i;
                        userSwitcherViewMode.mUserSwitcherViewGroup.setTranslationY(f - (fFloatValue * f));
                    }
                }
            });
            valueAnimatorOfFloat.start();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) throws Resources.NotFoundException {
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password) {
                return;
            }
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.disappear_y_translation);
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mViewFlipper, (Property<KeyguardSecurityViewFlipper, Float>) View.TRANSLATION_Y, dimensionPixelSize);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mUserSwitcherViewGroup, (Property<ViewGroup, Float>) View.ALPHA, 0.0f);
            animatorSet.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
            animatorSet.start();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public final void updateSecurityViewLocation() throws Resources.NotFoundException {
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.SidedSecurityMode
        public final void updateSecurityViewLocation(boolean z, boolean z2) throws Resources.NotFoundException {
            if (z2) {
                TransitionManager.beginDelayedTransition(this.mView, new KeyguardSecurityViewTransition());
            }
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.bouncer_user_switcher_y_trans);
            int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.bouncer_user_switcher_view_mode_view_flipper_bottom_margin);
            int dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.bouncer_user_switcher_view_mode_user_switcher_bottom_margin);
            if (this.mResources.getConfiguration().orientation == 1) {
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.connect(this.mUserSwitcherViewGroup.getId(), 3, 0, 3, dimensionPixelSize);
                constraintSet.connect(this.mUserSwitcherViewGroup.getId(), 4, this.mViewFlipper.getId(), 3, dimensionPixelSize3);
                constraintSet.connect(this.mViewFlipper.getId(), 3, this.mUserSwitcherViewGroup.getId(), 4);
                constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4, dimensionPixelSize2);
                constraintSet.centerHorizontally(this.mViewFlipper.getId(), 0);
                constraintSet.centerHorizontally(this.mUserSwitcherViewGroup.getId(), 0);
                constraintSet.setVerticalChainStyle(this.mViewFlipper.getId(), 0);
                constraintSet.setVerticalChainStyle(this.mUserSwitcherViewGroup.getId(), 0);
                constraintSet.constrainHeight(this.mUserSwitcherViewGroup.getId(), -2);
                constraintSet.constrainWidth(this.mUserSwitcherViewGroup.getId(), -2);
                constraintSet.constrainHeight(this.mViewFlipper.getId(), 0);
                constraintSet.applyTo(this.mView);
                return;
            }
            int id = z ? this.mViewFlipper.getId() : this.mUserSwitcherViewGroup.getId();
            int id2 = z ? this.mUserSwitcherViewGroup.getId() : this.mViewFlipper.getId();
            ConstraintSet constraintSet2 = new ConstraintSet();
            constraintSet2.connect(id, 6, 0, 6);
            constraintSet2.connect(id, 7, id2, 6);
            constraintSet2.connect(id2, 6, id, 7);
            constraintSet2.connect(id2, 7, 0, 7);
            constraintSet2.connect(this.mUserSwitcherViewGroup.getId(), 3, 0, 3);
            constraintSet2.connect(this.mUserSwitcherViewGroup.getId(), 4, 0, 4);
            constraintSet2.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet2.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet2.setHorizontalChainStyle(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.setHorizontalChainStyle(this.mViewFlipper.getId(), 0);
            constraintSet2.constrainHeight(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.constrainWidth(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.constrainWidth(this.mViewFlipper.getId(), 0);
            constraintSet2.constrainHeight(this.mViewFlipper.getId(), 0);
            constraintSet2.applyTo(this.mView);
        }
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) throws NumberFormatException {
        super.dispatchDraw(canvas);
        ViewMediatorCallback viewMediatorCallback = this.mViewMediatorCallback;
        if (viewMediatorCallback != null) {
            viewMediatorCallback.keyguardDoneDrawing();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "KeyguardSecurityView", zDispatchTouchEvent);
        return zDispatchTouchEvent;
    }

    public boolean handleDoubleTap(MotionEvent motionEvent) {
        this.mViewMode.handleDoubleTap(motionEvent);
        return true;
    }

    public void initMode(int i, GlobalSettings globalSettings, FalsingManager falsingManager, UserSwitcherController userSwitcherController, KeyguardSecurityContainerController$$ExternalSyntheticLambda2 keyguardSecurityContainerController$$ExternalSyntheticLambda2, FalsingA11yDelegate falsingA11yDelegate) {
        if (this.mCurrentMode == i) {
            return;
        }
        Log.i("KeyguardSecurityView", "Switching mode from " + modeToString(this.mCurrentMode) + " to " + modeToString(i));
        this.mCurrentMode = i;
        this.mViewMode.onDestroy();
        if (i == 1) {
            this.mViewMode = new OneHandedViewMode();
        } else if (i != 2) {
            this.mViewMode = new DefaultViewMode();
        } else {
            this.mViewMode = new UserSwitcherViewMode(keyguardSecurityContainerController$$ExternalSyntheticLambda2, this.mBgExecutor);
        }
        this.mGlobalSettings = globalSettings;
        this.mFalsingManager = falsingManager;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
        this.mUserSwitcherController = userSwitcherController;
        setupViewMode();
    }

    public String modeToString(int i) {
        if (i == -1) {
            return "Uninitialized";
        }
        if (i == 0) {
            return SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT;
        }
        if (i == 1) {
            return "OneHanded";
        }
        if (i == 2) {
            return "UserSwitcher";
        }
        throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "mode: ", " not supported"));
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int iMax = Integer.max(windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom, windowInsets.getInsets(WindowInsets.Type.ime()).bottom);
        int iMax2 = Integer.max(iMax, getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_security_view_bottom_margin));
        if (!this.mDisappearAnimRunning) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), iMax2);
        }
        return windowInsets.inset(0, 0, 0, iMax);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mViewMode.updateSecurityViewLocation();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSecurityViewFlipper = (KeyguardSecurityViewFlipper) findViewById(R.id.view_flipper);
        setupViewMode();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        if (!z || this.mWidth == i5) {
            return;
        }
        this.mWidth = i5;
        this.mViewMode.updateSecurityViewLocation();
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public void setupViewMode() {
        GlobalSettings globalSettings;
        FalsingManager falsingManager;
        UserSwitcherController userSwitcherController;
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = this.mSecurityViewFlipper;
        if (keyguardSecurityViewFlipper == null || (globalSettings = this.mGlobalSettings) == null || (falsingManager = this.mFalsingManager) == null || (userSwitcherController = this.mUserSwitcherController) == null) {
            return;
        }
        this.mViewMode.init(this, globalSettings, keyguardSecurityViewFlipper, falsingManager, userSwitcherController, this.mFalsingA11yDelegate);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public void showAlmostAtWipeDialog(final int i, final int i2, int i3) {
        showDialog(i3 != 1 ? i3 != 2 ? i3 != 3 ? null : ((ViewGroup) this).mContext.getString(R.string.kg_failed_attempts_almost_at_erase_user, Integer.valueOf(i), Integer.valueOf(i2)) : ((DevicePolicyManager) ((ViewGroup) this).mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ALMOST_ERASING_PROFILE", new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return ((ViewGroup) this.f$0).mContext.getString(R.string.kg_failed_attempts_almost_at_erase_profile, Integer.valueOf(i), Integer.valueOf(i2));
            }
        }, Integer.valueOf(i), Integer.valueOf(i2)) : ((ViewGroup) this).mContext.getString(R.string.kg_failed_attempts_almost_at_wipe, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void showDialog(String str) {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(((ViewGroup) this).mContext).setTitle((CharSequence) null).setMessage(str).setCancelable(false).setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null).create();
        this.mAlertDialog = alertDialogCreate;
        if (!(((ViewGroup) this).mContext instanceof Activity)) {
            alertDialogCreate.getWindow().setType(2009);
        }
        this.mAlertDialog.show();
    }

    public final void showWipeDialog(final int i, int i2) {
        showDialog(i2 != 1 ? i2 != 2 ? i2 != 3 ? null : ((ViewGroup) this).mContext.getString(R.string.kg_failed_attempts_now_erasing_user, Integer.valueOf(i)) : ((DevicePolicyManager) ((ViewGroup) this).mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ERASING_PROFILE", new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ((ViewGroup) this.f$0).mContext.getString(R.string.kg_failed_attempts_now_erasing_profile, Integer.valueOf(i));
            }
        }, Integer.valueOf(i)) : ((ViewGroup) this).mContext.getString(R.string.kg_failed_attempts_now_wiping, Integer.valueOf(i)));
    }

    public final void updateChildren(float f, int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            childAt.setTranslationY(i);
            childAt.setAlpha(f);
        }
    }

    public KeyguardSecurityContainer(Context context) {
        this(context, null, 0);
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2 = 0;
        VelocityTracker.obtain();
        this.mMotionEventListeners = new ArrayList();
        this.mViewMode = new DefaultViewMode();
        this.mCurrentMode = -1;
        this.mWidth = -1;
        new WindowInsetsAnimation.Callback(i2) { // from class: com.android.keyguard.KeyguardSecurityContainer.1
            public final Rect mInitialBounds = new Rect();
            public final Rect mFinalBounds = new Rect();

            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    InteractionJankMonitor.getInstance().end(20);
                    KeyguardSecurityContainer.this.setAlpha(0.0f);
                } else {
                    InteractionJankMonitor.getInstance().end(17);
                }
                KeyguardSecurityContainer.this.updateChildren(1.0f, 0);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mInitialBounds);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
                boolean z = KeyguardSecurityContainer.this.mDisappearAnimRunning;
                float f = z ? -(this.mFinalBounds.bottom - this.mInitialBounds.bottom) : this.mInitialBounds.bottom - this.mFinalBounds.bottom;
                float f2 = z ? -((this.mFinalBounds.bottom - this.mInitialBounds.bottom) * 0.75f) : 0.0f;
                Iterator it = list.iterator();
                int iLerp = 0;
                float interpolatedFraction = 1.0f;
                while (it.hasNext()) {
                    WindowInsetsAnimation windowInsetsAnimation = (WindowInsetsAnimation) it.next();
                    if ((windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) != 0) {
                        interpolatedFraction = windowInsetsAnimation.getInterpolatedFraction();
                        iLerp += (int) MathUtils.lerp(f, f2, interpolatedFraction);
                    }
                }
                KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
                KeyguardSecurityContainer.this.updateChildren(keyguardSecurityContainer.mDisappearAnimRunning ? 1.0f - interpolatedFraction : Math.max(interpolatedFraction, keyguardSecurityContainer.getAlpha()), iLerp);
                return windowInsets;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
                if (keyguardSecurityContainer.mDisappearAnimRunning) {
                    KeyguardInputView securityView = keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView();
                    if (securityView != null) {
                        InteractionJankMonitor.getInstance().begin(securityView, 20);
                    }
                } else {
                    KeyguardInputView securityView2 = keyguardSecurityContainer.mSecurityViewFlipper.getSecurityView();
                    if (securityView2 != null) {
                        InteractionJankMonitor.getInstance().begin(securityView2, 17);
                    }
                }
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mFinalBounds);
                return bounds;
            }
        };
        this.mBackCallback = new AnonymousClass2();
        new SpringAnimation(this, DynamicAnimation.TRANSLATION_Y);
        ViewConfiguration.get(context);
        new GestureDetector(context, new DoubleTapListener(this, i2));
    }

    /* renamed from: com.android.keyguard.KeyguardSecurityContainer$2, reason: invalid class name */
    public class AnonymousClass2 implements OnBackAnimationCallback {
        public AnonymousClass2() {
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackCancelled() {
            KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
            keyguardSecurityContainer.setScaleX(1.0f);
            keyguardSecurityContainer.setScaleY(1.0f);
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackProgressed(BackEvent backEvent) {
            float progress = ((1.0f - backEvent.getProgress()) * 0.100000024f) + 0.9f;
            KeyguardSecurityContainer keyguardSecurityContainer = KeyguardSecurityContainer.this;
            float f = KeyguardSecurityContainer.MIN_BACK_SCALE;
            keyguardSecurityContainer.setScaleX(progress);
            keyguardSecurityContainer.setScaleY(progress);
        }

        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
        }
    }

    public interface ViewMode {
        default void onDensityOrFontScaleChanged() {
        }

        default void onDestroy() {
        }

        default void reset() {
        }

        default void updateSecurityViewLocation() {
        }

        default void handleDoubleTap(MotionEvent motionEvent) {
        }

        default void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        default void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        default void updatePositionByTouchX(float f) {
        }

        default void init(KeyguardSecurityContainer keyguardSecurityContainer, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
        }
    }
}
