package com.android.systemui.volume;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.media.AudioSystem;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.InputFilter;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.haptics.slider.HapticSlider;
import com.android.systemui.haptics.slider.HapticSliderPlugin;
import com.android.systemui.haptics.slider.HapticSliderViewBinder;
import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.AlphaTintDrawableWrapper;
import com.android.systemui.util.RoundedCornerProgressDrawable;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.VolumeDialogImpl.AnonymousClass3;
import com.android.systemui.volume.domain.interactor.VolumeDialogInteractor;
import com.android.systemui.volume.domain.interactor.VolumePanelNavigationInteractor;
import com.android.systemui.volume.panel.shared.flag.VolumePanelFlag;
import com.android.systemui.volume.ui.navigation.VolumeNavigator;
import com.google.android.msdl.domain.MSDLPlayer;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import dagger.Lazy;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class VolumeDialogImpl implements VolumeDialog, Dumpable, ConfigurationController.ConfigurationListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final String TAG = Util.logTag(VolumeDialogImpl.class);
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public int mActiveStream;
    public final ActivityManager mActivityManager;
    public final boolean mChangeVolumeRowTintWhenInactive;
    public ConfigurableTexts mConfigurableTexts;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final VolumeDialogController mController;
    public final VolumeDialogImpl$$ExternalSyntheticLambda1 mCrossWindowBlurEnabledListener;
    public CsdWarningDialog mCsdDialog;
    public final CsdWarningDialog.Factory mCsdWarningDialogFactory;
    public int mDevicePosture;
    public final DevicePostureController mDevicePostureController;
    public final VolumeDialogImpl$$ExternalSyntheticLambda2 mDevicePostureControllerCallback;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public CustomDialog mDialog;
    public int mDialogCornerRadius;
    public final int mDialogHideAnimationDurationMs;
    public ViewGroup mDialogRowsView;
    public BackgroundBlurDrawable mDialogRowsViewBackground;
    public ViewGroup mDialogRowsViewContainer;
    public final int mDialogShowAnimationDurationMs;
    public int mDialogTimeoutMillis;
    public ViewGroup mDialogView;
    public int mDialogWidth;
    public final H mHandler;
    public final boolean mHasSeenODICaptionsTooltip;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final VolumeDialogInteractor mInteractor;
    public final boolean mIsTv;
    public final KeyguardManager mKeyguard;
    public final MSDLPlayer mMSDLPlayer;
    public final MediaOutputDialogManager mMediaOutputDialogManager;
    public CaptionsToggleImageButton mODICaptionsIcon;
    public ViewStub mODICaptionsTooltipViewStub;
    public ViewGroup mODICaptionsView;
    public int mOrientation;
    public int mOriginalGravity;
    public int mPrevActiveStream;
    public ViewGroup mRinger;
    public View mRingerAndDrawerContainer;
    public Drawable mRingerAndDrawerContainerBackground;
    public int mRingerCount;
    public ViewGroup mRingerDrawerContainer;
    public ImageView mRingerDrawerIconAnimatingDeselected;
    public ImageView mRingerDrawerIconAnimatingSelected;
    public int mRingerDrawerItemSize;
    public ViewGroup mRingerDrawerMute;
    public ImageView mRingerDrawerMuteIcon;
    public ViewGroup mRingerDrawerNewSelectionBg;
    public ViewGroup mRingerDrawerNormal;
    public ImageView mRingerDrawerNormalIcon;
    public ViewGroup mRingerDrawerVibrate;
    public ImageView mRingerDrawerVibrateIcon;
    public ImageButton mRingerIcon;
    public int mRingerRowsPadding;
    public AnonymousClass5 mSafetyWarning;
    public final Lazy mSecureSettings;
    public ViewGroup mSelectedRingerContainer;
    public ImageView mSelectedRingerIcon;
    public ImageButton mSettingsIcon;
    public View mSettingsView;
    public final boolean mShouldListenForJank;
    public boolean mShowA11yStream;
    public final boolean mShowLowMediaVolumeIcon;
    public boolean mShowVibrate;
    public boolean mShowing;
    public VolumeDialogController.State mState;
    public final SystemClock mSystemClock;
    public View mTopContainer;
    public final boolean mUseBackgroundBlur;
    public final VibratorHelper mVibratorHelper;
    public final VolumeNavigator mVolumeNavigator;
    public final VolumePanelFlag mVolumePanelFlag;
    public final VolumePanelNavigationInteractor mVolumePanelNavigationInteractor;
    public Window mWindow;
    public int mWindowGravity;
    public final Region mTouchableRegion = new Region();
    public final ValueAnimator mRingerDrawerIconColorAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
    public final ValueAnimator mAnimateUpBackgroundToMatchDrawer = ValueAnimator.ofFloat(1.0f, 0.0f);
    public boolean mIsRingerDrawerOpen = false;
    public float mRingerDrawerClosedAmount = 1.0f;
    public final List mRows = new ArrayList();
    public final SparseBooleanArray mDynamic = new SparseBooleanArray();
    public final Object mSafetyWarningLock = new Object();
    public final Accessibility mAccessibility = new Accessibility(this, 0);
    public boolean mAutomute = true;
    public boolean mSilentMode = true;
    public boolean mHovering = false;
    public boolean mConfigChanged = false;
    public boolean mIsAnimatingDismiss = false;
    View mODICaptionsTooltipView = null;
    final int mVolumeRingerIconDrawableId = R.drawable.ic_legacy_speaker_on;
    final int mVolumeRingerMuteIconDrawableId = R.drawable.ic_legacy_speaker_mute;
    public final Optional mCsdWarningNotificationActions = Optional.of(Collections.EMPTY_LIST);
    public final AnonymousClass7 mControllerCallbackH = new VolumeDialogController.Callbacks() { // from class: com.android.systemui.volume.VolumeDialogImpl.7
        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            boolean zBooleanValue = bool == null ? false : bool.booleanValue();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            volumeDialogImpl.mShowA11yStream = zBooleanValue;
            VolumeRow activeRow = volumeDialogImpl.getActiveRow();
            if (volumeDialogImpl.mShowA11yStream || 10 != activeRow.stream) {
                volumeDialogImpl.updateRowsH(activeRow);
            } else {
                volumeDialogImpl.dismissH(7);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            boolean zBooleanValue = bool.booleanValue();
            bool2.booleanValue();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (zBooleanValue) {
                volumeDialogImpl.mVolumePanelFlag.getClass();
            }
            ViewGroup viewGroup = volumeDialogImpl.mODICaptionsView;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionEnabledStateChanged(Boolean bool, Boolean bool2) {
            boolean zBooleanValue = bool.booleanValue();
            boolean zBooleanValue2 = bool2.booleanValue();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (zBooleanValue2) {
                volumeDialogImpl.mController.setCaptionsEnabledState(!zBooleanValue);
                return;
            }
            final CaptionsToggleImageButton captionsToggleImageButton = volumeDialogImpl.mODICaptionsIcon;
            if (captionsToggleImageButton.mCaptionsEnabled != zBooleanValue) {
                H h = volumeDialogImpl.mHandler;
                captionsToggleImageButton.mCaptionsEnabled = zBooleanValue;
                ViewCompat.replaceAccessibilityAction(captionsToggleImageButton, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, zBooleanValue ? captionsToggleImageButton.getContext().getString(R.string.volume_odi_captions_hint_disable) : captionsToggleImageButton.getContext().getString(R.string.volume_odi_captions_hint_enable), new AccessibilityViewCommand() { // from class: com.android.systemui.volume.CaptionsToggleImageButton$$ExternalSyntheticLambda0
                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                    public final boolean perform(View view) {
                        int i = CaptionsToggleImageButton.$r8$clinit;
                        VolumeDialogImpl$$ExternalSyntheticLambda16 volumeDialogImpl$$ExternalSyntheticLambda16 = captionsToggleImageButton.mConfirmedTapListener;
                        if (volumeDialogImpl$$ExternalSyntheticLambda16 == null) {
                            return false;
                        }
                        volumeDialogImpl$$ExternalSyntheticLambda16.f$0.mController.getCaptionsEnabledState(true);
                        Events.writeEvent(21, new Object[0]);
                        return true;
                    }
                });
                h.post(captionsToggleImageButton.setImageResourceAsync(captionsToggleImageButton.mCaptionsEnabled ? R.drawable.ic_volume_odi_captions : R.drawable.ic_volume_odi_captions_disabled));
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onConfigurationChanged() {
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            volumeDialogImpl.mDialog.dismiss();
            volumeDialogImpl.mConfigChanged = true;
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onDismissRequested(int i) {
            VolumeDialogImpl.this.dismissH(i);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onLayoutDirectionChanged(int i) {
            VolumeDialogImpl.this.mDialogView.setLayoutDirection(i);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onScreenOff() {
            VolumeDialogImpl.this.dismissH(4);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowCsdWarning(int i, int i2) {
            VolumeDialogImpl.this.showCsdWarningH(i, i2);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowRequested(int i, boolean z, int i2) throws Resources.NotFoundException {
            VolumeDialogImpl.m3214$$Nest$mshowH(VolumeDialogImpl.this, i, z, i2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [android.app.AlertDialog, com.android.systemui.volume.VolumeDialogImpl$5] */
        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSafetyWarning(int i) {
            final VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if ((i & 1025) != 0 || volumeDialogImpl.mShowing) {
                synchronized (volumeDialogImpl.mSafetyWarningLock) {
                    try {
                        if (volumeDialogImpl.mSafetyWarning != null) {
                            return;
                        }
                        ?? r0 = new SafetyWarningDialog(volumeDialogImpl.mContext, volumeDialogImpl.mController.getAudioManager()) { // from class: com.android.systemui.volume.VolumeDialogImpl.5
                            @Override // com.android.systemui.volume.SafetyWarningDialog
                            public final void cleanUp$1() {
                                VolumeDialogImpl volumeDialogImpl2;
                                synchronized (VolumeDialogImpl.this.mSafetyWarningLock) {
                                    volumeDialogImpl2 = VolumeDialogImpl.this;
                                    volumeDialogImpl2.mSafetyWarning = null;
                                }
                                volumeDialogImpl2.recheckH(null);
                            }
                        };
                        volumeDialogImpl.mSafetyWarning = r0;
                        r0.show();
                        volumeDialogImpl.recheckH(null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            volumeDialogImpl.rescheduleTimeoutH();
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSilentHint() {
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (volumeDialogImpl.mSilentMode) {
                volumeDialogImpl.mController.setRingerMode(2, false);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVibrateHint() {
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (volumeDialogImpl.mSilentMode) {
                volumeDialogImpl.mController.setRingerMode(0, false);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onStateChanged(VolumeDialogController.State state) {
            VolumeDialogImpl.this.onStateChangedH(state);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onVolumeChangedFromKey() {
            String str = VolumeDialogImpl.TAG;
            HapticSliderPlugin hapticSliderPlugin = VolumeDialogImpl.this.getActiveRow().mHapticPlugin;
            if (hapticSliderPlugin != null) {
                hapticSliderPlugin.onKeyDown();
            }
        }
    };

    public final class Accessibility extends View.AccessibilityDelegate {
        public /* synthetic */ Accessibility(VolumeDialogImpl volumeDialogImpl, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            List<CharSequence> text = accessibilityEvent.getText();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            text.add(volumeDialogImpl.mContext.getString(R.string.volume_dialog_title, volumeDialogImpl.getStreamLabelH(volumeDialogImpl.getActiveRow().ss)));
            return true;
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        private Accessibility() {
        }
    }

    public final class CustomDialog extends Dialog implements DialogInterface {
        public CustomDialog(Context context) {
            super(context, R.style.volume_dialog_theme);
        }

        @Override // android.app.Dialog, android.view.Window.Callback
        public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
            VolumeDialogImpl.this.rescheduleTimeoutH();
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // android.app.Dialog
        public final void onStart() {
            setCanceledOnTouchOutside(true);
            super.onStart();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            boolean z = (volumeDialogImpl.mOrientation == 1) && (volumeDialogImpl.mDevicePosture == 2);
            WindowManager.LayoutParams attributes = volumeDialogImpl.mWindow.getAttributes();
            int absoluteGravity = Gravity.getAbsoluteGravity(z ? volumeDialogImpl.mOriginalGravity | 48 : volumeDialogImpl.mOriginalGravity, volumeDialogImpl.mContext.getResources().getConfiguration().getLayoutDirection());
            volumeDialogImpl.mWindowGravity = absoluteGravity;
            attributes.gravity = absoluteGravity;
        }

        @Override // android.app.Dialog
        public final void onStop() {
            super.onStop();
            VolumeDialogImpl.this.mHandler.sendEmptyMessage(4);
        }

        @Override // android.app.Dialog
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (!VolumeDialogImpl.this.mShowing || motionEvent.getAction() != 4) {
                return false;
            }
            VolumeDialogImpl.this.dismissH(1);
            return true;
        }
    }

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) throws Resources.NotFoundException {
            switch (message.what) {
                case 1:
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    VolumeDialogImpl.m3214$$Nest$mshowH(volumeDialogImpl, message.arg1, volumeDialogImpl.mKeyguard.isKeyguardLocked(), VolumeDialogImpl.this.mActivityManager.getLockTaskModeState());
                    return;
                case 2:
                    VolumeDialogImpl.this.dismissH(message.arg1);
                    return;
                case 3:
                    VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                    VolumeRow volumeRow = (VolumeRow) message.obj;
                    String str = VolumeDialogImpl.TAG;
                    volumeDialogImpl2.recheckH(volumeRow);
                    return;
                case 4:
                    VolumeDialogImpl volumeDialogImpl3 = VolumeDialogImpl.this;
                    String str2 = VolumeDialogImpl.TAG;
                    volumeDialogImpl3.recheckH(null);
                    return;
                case 5:
                    VolumeDialogImpl volumeDialogImpl4 = VolumeDialogImpl.this;
                    int i = message.arg1;
                    int i2 = 0;
                    boolean z = message.arg2 != 0;
                    ArrayList arrayList = (ArrayList) volumeDialogImpl4.mRows;
                    int size = arrayList.size();
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        VolumeRow volumeRow2 = (VolumeRow) obj;
                        if (volumeRow2.stream == i) {
                            volumeRow2.important = z;
                            return;
                        }
                    }
                    return;
                case 6:
                    VolumeDialogImpl.this.rescheduleTimeoutH();
                    return;
                case 7:
                    VolumeDialogImpl volumeDialogImpl5 = VolumeDialogImpl.this;
                    volumeDialogImpl5.onStateChangedH(volumeDialogImpl5.mState);
                    return;
                case 8:
                    VolumeDialogImpl volumeDialogImpl6 = VolumeDialogImpl.this;
                    synchronized (volumeDialogImpl6.mSafetyWarningLock) {
                        try {
                            CsdWarningDialog csdWarningDialog = volumeDialogImpl6.mCsdDialog;
                            if (csdWarningDialog == null) {
                                return;
                            }
                            csdWarningDialog.dismiss();
                            return;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                default:
                    return;
            }
        }
    }

    public class RingerDrawerItemClickListener implements View.OnClickListener {
        public final int mClickedRingerMode;

        public RingerDrawerItemClickListener(int i) {
            this.mClickedRingerMode = i;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if (volumeDialogImpl.mIsRingerDrawerOpen) {
                volumeDialogImpl.setRingerMode(this.mClickedRingerMode);
                VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                volumeDialogImpl2.mRingerDrawerIconAnimatingSelected = volumeDialogImpl2.getDrawerIconViewForMode(this.mClickedRingerMode);
                VolumeDialogImpl volumeDialogImpl3 = VolumeDialogImpl.this;
                volumeDialogImpl3.mRingerDrawerIconAnimatingDeselected = volumeDialogImpl3.getDrawerIconViewForMode(volumeDialogImpl3.mState.ringerModeInternal);
                VolumeDialogImpl.this.mRingerDrawerIconColorAnimator.start();
                VolumeDialogImpl.this.mSelectedRingerContainer.setVisibility(4);
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.setAlpha(1.0f);
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().setInterpolator(Interpolators.ACCELERATE_DECELERATE).setDuration(175L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda21(this, 1));
                if (VolumeDialogImpl.this.isLandscape()) {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationX(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                } else {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationY(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                }
            }
        }
    }

    public class VolumeRow {
        public static final SliderHapticFeedbackConfig sSliderHapticFeedbackConfig = new SliderHapticFeedbackConfig(1.0f, 1.0f, 0.0f, 0.2f, 0.25f, 0.0f, 0.05f, 4, 200.0f, 1, 1.0f, 0.05f, 1.1235955f, 0.0f, new SliderHapticFeedbackFilter());
        public static final SeekableSliderTrackerConfig sSliderTrackerConfig = new SeekableSliderTrackerConfig(100, 0.02f, 0.01f, 0.99f);
        public ObjectAnimator anim;
        public int animTargetProgress;
        public boolean defaultStream;
        public TextView header;
        public ImageButton icon;
        public int iconMuteRes;
        public int iconRes;
        public int iconState;
        public boolean important;
        public int lastAudibleLevel;
        public HapticSliderPlugin mHapticPlugin;
        public TextView number;
        public int requestedLevel;
        public SeekBar slider;
        public AlphaTintDrawableWrapper sliderProgressIcon;
        public Drawable sliderProgressSolid;
        public VolumeDialogController.StreamState ss;
        public int stream;
        public boolean tracking;
        public long userAttempt;
        public View view;

        public /* synthetic */ VolumeRow(int i) {
            this();
        }

        public final boolean deliverOnProgressChangedHaptics(int i, boolean z) {
            HapticSliderPlugin hapticSliderPlugin = this.mHapticPlugin;
            if (hapticSliderPlugin == null) {
                return false;
            }
            hapticSliderPlugin.onProgressChanged(i, z);
            if (z) {
                return true;
            }
            this.mHapticPlugin.onKeyDown();
            return true;
        }

        private VolumeRow() {
            this.requestedLevel = -1;
            this.lastAudibleLevel = 1;
        }
    }

    public final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final VolumeRow mRow;

        public /* synthetic */ VolumeSeekBarChangeListener(VolumeDialogImpl volumeDialogImpl, VolumeRow volumeRow, int i) {
            this(volumeRow);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (this.mRow.ss == null) {
                return;
            }
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            String str = VolumeDialogImpl.TAG;
            if (volumeDialogImpl.getActiveRow().equals(this.mRow) && this.mRow.slider.getVisibility() == 0 && (z || this.mRow.animTargetProgress == i)) {
                this.mRow.deliverOnProgressChangedHaptics(i, z);
            }
            if (D.BUG) {
                String str2 = VolumeDialogImpl.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(AudioSystem.streamToString(this.mRow.stream));
                sb.append(" onProgressChanged ");
                sb.append(i);
                sb.append(" fromUser=");
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, str2);
            }
            if (z) {
                VolumeDialogController.StreamState streamState = this.mRow.ss;
                int i2 = streamState.levelMin;
                if (i2 > 0) {
                    float f = i2;
                    float f2 = i2;
                    float f3 = streamState.levelMax;
                    float min = seekBar.getMin();
                    float max = seekBar.getMax();
                    float f4 = f3 - f2;
                    float f5 = max - min;
                    if (f4 != 0.0f && f5 != 0.0f) {
                        min = MathUtils.constrain((((f - f2) / f4) * f5) + min, min, max);
                    }
                    int i3 = (int) min;
                    if (i < i3) {
                        seekBar.setProgress(i3);
                        i = i3;
                    }
                }
                int volumeFromProgress = VolumeDialogImpl.getVolumeFromProgress(this.mRow.ss, seekBar, i);
                VolumeRow volumeRow = this.mRow;
                VolumeDialogController.StreamState streamState2 = volumeRow.ss;
                if (streamState2.level != volumeFromProgress || (streamState2.muted && volumeFromProgress > 0)) {
                    volumeRow.userAttempt = android.os.SystemClock.uptimeMillis();
                    VolumeRow volumeRow2 = this.mRow;
                    if (volumeRow2.requestedLevel != volumeFromProgress) {
                        VolumeDialogImpl.this.mController.setActiveStream(volumeRow2.stream);
                        VolumeDialogImpl.this.mController.setStreamVolume(this.mRow.stream, volumeFromProgress);
                        VolumeRow volumeRow3 = this.mRow;
                        volumeRow3.requestedLevel = volumeFromProgress;
                        Events.writeEvent(9, Integer.valueOf(volumeRow3.stream), Integer.valueOf(volumeFromProgress));
                    }
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            if (D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m(this.mRow.stream, VolumeDialogImpl.TAG, new StringBuilder("onStartTrackingTouch "));
            }
            Events.writeEvent(23, Boolean.TRUE);
            HapticSliderPlugin hapticSliderPlugin = this.mRow.mHapticPlugin;
            if (hapticSliderPlugin != null && hapticSliderPlugin.isTracking()) {
                hapticSliderPlugin.sliderEventProducer.onStartTracking(true);
            }
            VolumeDialogImpl.this.mController.setActiveStream(this.mRow.stream);
            this.mRow.tracking = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            if (D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m(this.mRow.stream, VolumeDialogImpl.TAG, new StringBuilder("onStopTrackingTouch "));
            }
            Events.writeEvent(23, Boolean.FALSE);
            HapticSliderPlugin hapticSliderPlugin = this.mRow.mHapticPlugin;
            if (hapticSliderPlugin != null && hapticSliderPlugin.isTracking()) {
                hapticSliderPlugin.sliderEventProducer.onStopTracking(true);
            }
            VolumeRow volumeRow = this.mRow;
            volumeRow.tracking = false;
            volumeRow.userAttempt = android.os.SystemClock.uptimeMillis();
            int volumeFromProgress = VolumeDialogImpl.getVolumeFromProgress(this.mRow.ss, seekBar, seekBar.getProgress());
            Events.writeEvent(16, Integer.valueOf(this.mRow.stream), Integer.valueOf(volumeFromProgress));
            VolumeRow volumeRow2 = this.mRow;
            if (volumeRow2.ss.level != volumeFromProgress) {
                H h = VolumeDialogImpl.this.mHandler;
                h.sendMessageDelayed(h.obtainMessage(3, volumeRow2), 1000L);
            }
        }

        private VolumeSeekBarChangeListener(VolumeRow volumeRow) {
            this.mRow = volumeRow;
        }
    }

    /* renamed from: -$$Nest$mshowH, reason: not valid java name */
    public static void m3214$$Nest$mshowH(VolumeDialogImpl volumeDialogImpl, int i, boolean z, int i2) throws Resources.NotFoundException {
        volumeDialogImpl.getClass();
        Trace.beginSection("VolumeDialogImpl#showH");
        Log.i(TAG, "showH r=" + Events.SHOW_REASONS[i]);
        volumeDialogImpl.mHandler.removeMessages(1);
        volumeDialogImpl.mHandler.removeMessages(2);
        volumeDialogImpl.rescheduleTimeoutH();
        if (volumeDialogImpl.mConfigChanged) {
            volumeDialogImpl.initDialog(i2);
            ConfigurableTexts configurableTexts = volumeDialogImpl.mConfigurableTexts;
            if (!configurableTexts.mTexts.isEmpty()) {
                ((TextView) configurableTexts.mTexts.keyAt(0)).post(configurableTexts.mUpdateAll);
            }
            volumeDialogImpl.mConfigChanged = false;
        }
        volumeDialogImpl.initSettingsH(i2);
        volumeDialogImpl.mShowing = true;
        volumeDialogImpl.mIsAnimatingDismiss = false;
        volumeDialogImpl.mDialog.show();
        volumeDialogImpl.mInteractor.repository._isDialogVisible.updateState(null, Boolean.TRUE);
        Events.writeEvent(0, Integer.valueOf(i), Boolean.valueOf(z));
        volumeDialogImpl.mController.notifyVisible(true);
        volumeDialogImpl.mController.getCaptionsComponentState(false);
        volumeDialogImpl.checkODICaptionsTooltip(false);
        volumeDialogImpl.updateBackgroundForDrawerClosedAmount();
        for (int i3 = 0; i3 < ((ArrayList) volumeDialogImpl.mRows).size(); i3++) {
            final VolumeRow volumeRow = (VolumeRow) ((ArrayList) volumeDialogImpl.mRows).get(i3);
            if (volumeRow.slider.getVisibility() == 0) {
                volumeRow.slider.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.VolumeDialogImpl.VolumeRow.1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        HapticSliderPlugin hapticSliderPlugin = VolumeRow.this.mHapticPlugin;
                        if (hapticSliderPlugin == null) {
                            return false;
                        }
                        hapticSliderPlugin.onTouchEvent(motionEvent);
                        return false;
                    }
                });
            }
        }
        Trace.endSection();
    }

    /* JADX WARN: Type inference failed for: r10v3, types: [com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.volume.VolumeDialogImpl$7] */
    /* JADX WARN: Type inference failed for: r8v18, types: [com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda2] */
    public VolumeDialogImpl(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogManager mediaOutputDialogManager, InteractionJankMonitor interactionJankMonitor, VolumePanelNavigationInteractor volumePanelNavigationInteractor, VolumeNavigator volumeNavigator, boolean z, CsdWarningDialog.Factory factory, DevicePostureController devicePostureController, Looper looper, VolumePanelFlag volumePanelFlag, DumpManager dumpManager, Lazy lazy, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, VolumeDialogInteractor volumeDialogInteractor) throws Resources.NotFoundException {
        boolean z2 = true;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.volume_dialog_theme);
        this.mContext = contextThemeWrapper;
        this.mHandler = new H(looper);
        this.mVibratorHelper = vibratorHelper;
        this.mMSDLPlayer = mSDLPlayer;
        this.mSystemClock = systemClock;
        this.mShouldListenForJank = z;
        this.mController = volumeDialogController;
        this.mKeyguard = (KeyguardManager) contextThemeWrapper.getSystemService("keyguard");
        this.mActivityManager = (ActivityManager) contextThemeWrapper.getSystemService("activity");
        this.mAccessibilityMgr = accessibilityManagerWrapper;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mConfigurationController = configurationController;
        this.mMediaOutputDialogManager = mediaOutputDialogManager;
        this.mCsdWarningDialogFactory = factory;
        if (!contextThemeWrapper.getPackageManager().hasSystemFeature("android.software.leanback") && !contextThemeWrapper.getPackageManager().hasSystemFeature("android.hardware.type.television")) {
            z2 = false;
        }
        this.mIsTv = z2;
        this.mHasSeenODICaptionsTooltip = Prefs.getBoolean(context, "HasSeenODICaptionsTooltip", false);
        this.mShowLowMediaVolumeIcon = contextThemeWrapper.getResources().getBoolean(R.bool.config_showLowMediaVolumeIcon);
        this.mChangeVolumeRowTintWhenInactive = contextThemeWrapper.getResources().getBoolean(R.bool.config_changeVolumeRowTintWhenInactive);
        this.mDialogShowAnimationDurationMs = contextThemeWrapper.getResources().getInteger(R.integer.config_dialogShowAnimationDurationMs);
        this.mDialogHideAnimationDurationMs = contextThemeWrapper.getResources().getInteger(R.integer.config_dialogHideAnimationDurationMs);
        boolean z3 = contextThemeWrapper.getResources().getBoolean(R.bool.config_volumeDialogUseBackgroundBlur);
        this.mUseBackgroundBlur = z3;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mVolumePanelNavigationInteractor = volumePanelNavigationInteractor;
        this.mVolumeNavigator = volumeNavigator;
        this.mSecureSettings = lazy;
        this.mDialogTimeoutMillis = 3000;
        this.mVolumePanelFlag = volumePanelFlag;
        this.mInteractor = volumeDialogInteractor;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "VolumeDialogImpl", this);
        if (z3) {
            final int color = contextThemeWrapper.getColor(R.color.volume_dialog_background_color_above_blur);
            final int color2 = contextThemeWrapper.getColor(R.color.volume_dialog_background_color);
            this.mCrossWindowBlurEnabledListener = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    VolumeDialogImpl volumeDialogImpl = this.f$0;
                    int i = color;
                    int i2 = color2;
                    BackgroundBlurDrawable backgroundBlurDrawable = volumeDialogImpl.mDialogRowsViewBackground;
                    if (!((Boolean) obj).booleanValue()) {
                        i = i2;
                    }
                    backgroundBlurDrawable.setColor(i);
                    volumeDialogImpl.mDialogRowsView.invalidate();
                }
            };
        }
        initDimens();
        this.mOrientation = contextThemeWrapper.getResources().getConfiguration().orientation;
        this.mDevicePostureController = devicePostureController;
        if (devicePostureController == null) {
            this.mDevicePostureControllerCallback = null;
        } else {
            this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).getDevicePosture();
            this.mDevicePostureControllerCallback = new DevicePostureController.Callback() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda2
                @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                public final void onPostureChanged(int i) {
                    this.f$0.onPostureChanged(i);
                }
            };
        }
    }

    public static int getVolumeFromProgress(VolumeDialogController.StreamState streamState, SeekBar seekBar, int i) {
        float f = i;
        float min = seekBar.getMin();
        float max = seekBar.getMax();
        float fConstrain = streamState.levelMin;
        float f2 = streamState.levelMax;
        float f3 = max - min;
        float f4 = f2 - fConstrain;
        if (f3 != 0.0f && f4 != 0.0f) {
            fConstrain = MathUtils.constrain((((f - min) / f3) * f4) + fConstrain, fConstrain, f2);
        }
        return (int) fConstrain;
    }

    public final void addAccessibilityDescription(View view, int i, final String str) {
        view.setContentDescription(this.mContext.getString(getStringDescriptionResourceForRingerMode(i)));
        view.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.volume.VolumeDialogImpl.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str));
            }
        });
    }

    public final void addRow(int i, int i2, boolean z, boolean z2, int i3) {
        if (D.BUG) {
            Slog.d(TAG, "Adding row for stream " + i);
        }
        VolumeRow volumeRow = new VolumeRow(0);
        initRow(volumeRow, i, i2, i3, z, z2);
        this.mDialogRowsView.addView(volumeRow.view);
        ((ArrayList) this.mRows).add(volumeRow);
    }

    public final void addSliderHapticsToRow(VolumeRow volumeRow) {
        SeekBar seekBar = volumeRow.slider;
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        MSDLPlayer mSDLPlayer = this.mMSDLPlayer;
        SystemClock systemClock = this.mSystemClock;
        if (volumeRow.mHapticPlugin == null) {
            volumeRow.mHapticPlugin = new HapticSliderPlugin(vibratorHelper, mSDLPlayer, systemClock, new HapticSlider.SeekBar(seekBar), VolumeRow.sSliderHapticFeedbackConfig, VolumeRow.sSliderTrackerConfig);
        }
        HapticSliderViewBinder.bind(volumeRow.slider, volumeRow.mHapticPlugin);
    }

    public void addSliderHapticsToRows() {
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            addSliderHapticsToRow((VolumeRow) obj);
        }
    }

    public boolean canDeliverProgressHapticsToStream(int i, boolean z, int i2) {
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            VolumeRow volumeRow = (VolumeRow) obj;
            if (volumeRow.stream == i) {
                return volumeRow.deliverOnProgressChangedHaptics(i2, z);
            }
        }
        return false;
    }

    public final void checkODICaptionsTooltip(boolean z) {
        View view;
        boolean z2 = this.mHasSeenODICaptionsTooltip;
        if (!z2 && !z && this.mODICaptionsTooltipViewStub != null) {
            this.mController.getCaptionsComponentState(true);
            return;
        }
        if (z2 && z && (view = this.mODICaptionsTooltipView) != null && view != null && view.getVisibility() == 0) {
            this.mODICaptionsTooltipView.animate().cancel();
            this.mODICaptionsTooltipView.setAlpha(1.0f);
            this.mODICaptionsTooltipView.animate().alpha(0.0f).setStartDelay(0L).setDuration(this.mDialogHideAnimationDurationMs).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 1)).start();
        }
    }

    public void clearInternalHandlerAfterTest() {
        H h = this.mHandler;
        if (h != null) {
            h.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void destroy() {
        Log.d(TAG, "destroy() called");
        this.mController.removeCallback(this.mControllerCallbackH);
        this.mHandler.removeCallbacksAndMessages(null);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this);
        DevicePostureController devicePostureController = this.mDevicePostureController;
        if (devicePostureController != null) {
            ((DevicePostureControllerImpl) devicePostureController).removeCallback(this.mDevicePostureControllerCallback);
        }
    }

    public final void dismissH(int i) {
        AnonymousClass3 anonymousClass3;
        VolumeDialogImpl volumeDialogImpl;
        CustomDialog customDialog;
        Trace.beginSection("VolumeDialogImpl#dismissH");
        int i2 = 0;
        while (true) {
            anonymousClass3 = null;
            if (i2 >= ((ArrayList) this.mRows).size()) {
                break;
            }
            ((VolumeRow) ((ArrayList) this.mRows).get(i2)).slider.setOnTouchListener(null);
            i2++;
        }
        String str = TAG;
        Log.i(str, "mDialog.dismiss() reason: " + Events.DISMISS_REASONS[i] + " from: " + Debug.getCaller());
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(1);
        boolean z = (this.mShowing || (customDialog = this.mDialog) == null || !customDialog.isShowing()) ? false : true;
        if (z) {
            StringBuilder sb = new StringBuilder("dismissH: volume dialog possible in inconsistent state:mShowing=");
            sb.append(this.mShowing);
            sb.append(", mDialog==null?");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mDialog == null, str);
        }
        if (this.mIsAnimatingDismiss && !z) {
            Log.d(str, "dismissH: skipping dismiss because isAnimatingDismiss is true and showingStateInconsistent is false");
            Trace.endSection();
            return;
        }
        this.mIsAnimatingDismiss = true;
        this.mDialogView.animate().cancel();
        this.mInteractor.repository._isDialogVisible.updateState(null, Boolean.FALSE);
        if (this.mShowing) {
            this.mShowing = false;
            Events.writeEvent(1, Integer.valueOf(i));
        }
        this.mDialogView.setTranslationX(0.0f);
        this.mDialogView.setAlpha(1.0f);
        ViewPropertyAnimator viewPropertyAnimatorWithEndAction = this.mDialogView.animate().alpha(0.0f).setDuration(this.mDialogHideAnimationDurationMs).setInterpolator(new SystemUIInterpolators$LogAccelerateInterpolator()).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 7));
        if (this.mContext.getDisplay().getRotation() == 0) {
            viewPropertyAnimatorWithEndAction.translationX((this.mDialogView.getWidth() * ((this.mWindowGravity & 3) == 3 ? -1 : 1)) / 2.0f);
        }
        ViewGroup viewGroup = this.mDialogView;
        long j = this.mDialogHideAnimationDurationMs;
        if (this.mShouldListenForJank) {
            volumeDialogImpl = this;
            anonymousClass3 = volumeDialogImpl.new AnonymousClass3(viewGroup, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS, j);
        } else {
            volumeDialogImpl = this;
        }
        viewPropertyAnimatorWithEndAction.setListener(anonymousClass3).start();
        volumeDialogImpl.checkODICaptionsTooltip(true);
        synchronized (volumeDialogImpl.mSafetyWarningLock) {
            try {
                if (volumeDialogImpl.mSafetyWarning != null) {
                    if (D.BUG) {
                        Log.d(str, "SafetyWarning dismissed");
                    }
                    volumeDialogImpl.mSafetyWarning.dismiss();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("VolumeDialogImpl state:");
        printWriter.print("  mShowing: ");
        printWriter.println(this.mShowing);
        printWriter.print("  mIsAnimatingDismiss: ");
        printWriter.println(this.mIsAnimatingDismiss);
        printWriter.print("  mActiveStream: ");
        printWriter.println(this.mActiveStream);
        printWriter.print("  mDynamic: ");
        printWriter.println(this.mDynamic);
        printWriter.print("  mAutomute: ");
        printWriter.println(this.mAutomute);
        printWriter.print("  mSilentMode: ");
        printWriter.println(this.mSilentMode);
    }

    public final VolumeRow getActiveRow() {
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            VolumeRow volumeRow = (VolumeRow) obj;
            if (volumeRow.stream == this.mActiveStream) {
                return volumeRow;
            }
        }
        ArrayList arrayList2 = (ArrayList) this.mRows;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            VolumeRow volumeRow2 = (VolumeRow) obj2;
            if (volumeRow2.stream == 3) {
                return volumeRow2;
            }
        }
        return (VolumeRow) ((ArrayList) this.mRows).get(0);
    }

    public final ImageView getDrawerIconViewForMode(int i) {
        return i == 1 ? this.mRingerDrawerVibrateIcon : i == 0 ? this.mRingerDrawerMuteIcon : this.mRingerDrawerNormalIcon;
    }

    public final int getRingerDrawerOpenExtraSize() {
        return (this.mRingerCount - 1) * this.mRingerDrawerItemSize;
    }

    public String getSelectedRingerContainerDescription() {
        ViewGroup viewGroup = this.mSelectedRingerContainer;
        if (viewGroup == null) {
            return null;
        }
        return viewGroup.getContentDescription().toString();
    }

    public final String getStreamLabelH(VolumeDialogController.StreamState streamState) {
        if (streamState == null) {
            return "";
        }
        String str = streamState.remoteLabel;
        if (str != null) {
            return str;
        }
        try {
            return this.mContext.getResources().getString(streamState.name);
        } catch (Resources.NotFoundException unused) {
            Slog.e(TAG, "Can't find translation for stream " + streamState);
            return "";
        }
    }

    public int getStringDescriptionResourceForRingerMode(int i) {
        return i != 0 ? i != 1 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_status_vibrate : R.string.volume_ringer_status_silent;
    }

    public final float getTranslationInDrawerForRingerMode(int i) {
        int i2;
        if (i == 1) {
            i2 = (-this.mRingerDrawerItemSize) * 2;
        } else {
            if (i != 0) {
                return 0.0f;
            }
            i2 = -this.mRingerDrawerItemSize;
        }
        return i2;
    }

    public final int getVisibleRowsExtraSize() {
        VolumeRow activeRow = getActiveRow();
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (shouldBeVisibleH((VolumeRow) obj, activeRow)) {
                i++;
            }
        }
        return (this.mDialogWidth + this.mRingerRowsPadding) * (i - 1);
    }

    public int getWindowGravity() {
        return this.mWindowGravity;
    }

    public final void hideRingerDrawer() {
        if (this.mRingerDrawerContainer != null && this.mIsRingerDrawerOpen) {
            getDrawerIconViewForMode(this.mState.ringerModeInternal).setVisibility(4);
            this.mRingerDrawerContainer.animate().alpha(0.0f).setDuration(250L).setStartDelay(0L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 6));
            if (isLandscape()) {
                this.mRingerDrawerContainer.animate().translationX(this.mRingerDrawerItemSize * 2).start();
            } else {
                this.mRingerDrawerContainer.animate().translationY(this.mRingerDrawerItemSize * 2).start();
            }
            this.mAnimateUpBackgroundToMatchDrawer.setDuration(250L);
            this.mAnimateUpBackgroundToMatchDrawer.setInterpolator(Interpolators.FAST_OUT_SLOW_IN_REVERSE);
            this.mAnimateUpBackgroundToMatchDrawer.reverse();
            this.mSelectedRingerContainer.animate().translationX(0.0f).translationY(0.0f).start();
            updateSelectedRingerContainerDescription(false);
            this.mSelectedRingerContainer.setImportantForAccessibility(0);
            this.mIsRingerDrawerOpen = false;
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void init(int i, VolumeDialog.Callback callback) throws Resources.NotFoundException {
        initDialog(this.mActivityManager.getLockTaskModeState());
        this.mController.addCallback(this.mControllerCallbackH, this.mHandler);
        this.mController.getState();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        DevicePostureController devicePostureController = this.mDevicePostureController;
        if (devicePostureController != null) {
            ((DevicePostureControllerImpl) devicePostureController).addCallback(this.mDevicePostureControllerCallback);
        }
    }

    public final void initDialog(int i) throws Resources.NotFoundException {
        Log.d(TAG, "initDialog: called!");
        this.mDialog = new CustomDialog(this.mContext);
        initDimens();
        this.mConfigurableTexts = new ConfigurableTexts(this.mContext);
        this.mHovering = false;
        this.mShowing = false;
        Window window = this.mDialog.getWindow();
        this.mWindow = window;
        window.requestFeature(1);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(65538);
        this.mWindow.addFlags(android.R.interpolator.launch_task_micro_alpha);
        this.mWindow.addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mWindow.setType(2020);
        this.mWindow.setWindowAnimations(android.R.style.Animation.Toast);
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.format = -3;
        attributes.setTitle("VolumeDialogImpl");
        attributes.windowAnimations = -1;
        int integer = this.mContext.getResources().getInteger(R.integer.volume_dialog_gravity);
        this.mOriginalGravity = integer;
        int absoluteGravity = Gravity.getAbsoluteGravity(integer, this.mContext.getResources().getConfiguration().getLayoutDirection());
        this.mWindowGravity = absoluteGravity;
        attributes.gravity = absoluteGravity;
        this.mWindow.setAttributes(attributes);
        this.mWindow.setLayout(-2, -2);
        this.mDialog.setContentView(R.layout.volume_dialog_legacy);
        ViewGroup viewGroup = (ViewGroup) this.mDialog.findViewById(R.id.volume_dialog);
        this.mDialogView = viewGroup;
        viewGroup.setAlpha(0.0f);
        this.mDialogTimeoutMillis = ((SecureSettings) this.mSecureSettings.get()).getInt("volume_dialog_dismiss_timeout", 3000);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                VolumeDialogImpl volumeDialogImpl = this.f$0;
                volumeDialogImpl.mDialogView.getViewTreeObserver().addOnComputeInternalInsetsListener(volumeDialogImpl);
                if (volumeDialogImpl.mContext.getDisplay().getRotation() == 0) {
                    volumeDialogImpl.mDialogView.setTranslationX((r6.getWidth() * ((volumeDialogImpl.mWindowGravity & 3) == 3 ? -1 : 1)) / 2.0f);
                }
                volumeDialogImpl.mDialogView.setAlpha(0.0f);
                volumeDialogImpl.mDialogView.animate().alpha(1.0f).translationX(0.0f).setDuration(volumeDialogImpl.mDialogShowAnimationDurationMs).setListener(!volumeDialogImpl.mShouldListenForJank ? null : volumeDialogImpl.new AnonymousClass3(volumeDialogImpl.mDialogView, "show", volumeDialogImpl.mDialogTimeoutMillis)).setInterpolator(new SystemUIInterpolators$LogDecelerateInterpolator()).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda0(volumeDialogImpl, 3)).start();
            }
        });
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                VolumeDialogImpl volumeDialogImpl = this.f$0;
                volumeDialogImpl.mDialogView.getViewTreeObserver().removeOnComputeInternalInsetsListener(volumeDialogImpl);
            }
        });
        this.mDialogView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda5
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                VolumeDialogImpl volumeDialogImpl = this.f$0;
                String str = VolumeDialogImpl.TAG;
                volumeDialogImpl.getClass();
                int actionMasked = motionEvent.getActionMasked();
                volumeDialogImpl.mHovering = actionMasked == 9 || actionMasked == 7;
                volumeDialogImpl.rescheduleTimeoutH();
                return true;
            }
        });
        this.mDialogRowsView = (ViewGroup) this.mDialog.findViewById(R.id.volume_dialog_rows);
        if (this.mUseBackgroundBlur) {
            this.mDialogView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.volume.VolumeDialogImpl.1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    VolumeDialogImpl.this.mWindow.getWindowManager().addCrossWindowBlurEnabledListener(VolumeDialogImpl.this.mCrossWindowBlurEnabledListener);
                    VolumeDialogImpl.this.mDialogRowsViewBackground = view.getViewRootImpl().createBackgroundBlurDrawable();
                    Resources resources = VolumeDialogImpl.this.mContext.getResources();
                    VolumeDialogImpl.this.mDialogRowsViewBackground.setCornerRadius(r0.mContext.getResources().getDimensionPixelSize(Utils.getThemeAttr(android.R.attr.dialogCornerRadius, VolumeDialogImpl.this.mContext)));
                    VolumeDialogImpl.this.mDialogRowsViewBackground.setBlurRadius(resources.getDimensionPixelSize(R.dimen.volume_dialog_background_blur_radius));
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    volumeDialogImpl.mDialogRowsView.setBackground(volumeDialogImpl.mDialogRowsViewBackground);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    VolumeDialogImpl.this.mWindow.getWindowManager().removeCrossWindowBlurEnabledListener(VolumeDialogImpl.this.mCrossWindowBlurEnabledListener);
                }
            });
        }
        this.mDialogRowsViewContainer = (ViewGroup) this.mDialogView.findViewById(R.id.volume_dialog_rows_container);
        this.mTopContainer = this.mDialogView.findViewById(R.id.volume_dialog_top_container);
        View viewFindViewById = this.mDialogView.findViewById(R.id.volume_ringer_and_drawer_container);
        this.mRingerAndDrawerContainer = viewFindViewById;
        if (viewFindViewById != null) {
            if (isLandscape()) {
                View view = this.mRingerAndDrawerContainer;
                view.setPadding(view.getPaddingLeft(), this.mRingerAndDrawerContainer.getPaddingTop(), this.mRingerAndDrawerContainer.getPaddingRight(), this.mRingerRowsPadding);
                this.mRingerAndDrawerContainer.setBackgroundDrawable(this.mContext.getDrawable(R.drawable.volume_background_top_rounded));
            }
            this.mRingerAndDrawerContainer.post(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 4));
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mDialog.findViewById(R.id.ringer);
        this.mRinger = viewGroup2;
        if (viewGroup2 != null) {
            this.mRingerIcon = (ImageButton) viewGroup2.findViewById(R.id.ringer_icon);
        }
        this.mSelectedRingerIcon = (ImageView) this.mDialog.findViewById(R.id.volume_new_ringer_active_icon);
        this.mSelectedRingerContainer = (ViewGroup) this.mDialog.findViewById(R.id.volume_new_ringer_active_icon_container);
        this.mRingerDrawerMute = (ViewGroup) this.mDialog.findViewById(R.id.volume_drawer_mute);
        this.mRingerDrawerNormal = (ViewGroup) this.mDialog.findViewById(R.id.volume_drawer_normal);
        this.mRingerDrawerVibrate = (ViewGroup) this.mDialog.findViewById(R.id.volume_drawer_vibrate);
        this.mRingerDrawerMuteIcon = (ImageView) this.mDialog.findViewById(R.id.volume_drawer_mute_icon);
        this.mRingerDrawerVibrateIcon = (ImageView) this.mDialog.findViewById(R.id.volume_drawer_vibrate_icon);
        this.mRingerDrawerNormalIcon = (ImageView) this.mDialog.findViewById(R.id.volume_drawer_normal_icon);
        this.mRingerDrawerNewSelectionBg = (ViewGroup) this.mDialog.findViewById(R.id.volume_drawer_selection_background);
        ImageView imageView = this.mRingerDrawerMuteIcon;
        if (imageView != null) {
            imageView.setImageResource(this.mVolumeRingerMuteIconDrawableId);
        }
        ImageView imageView2 = this.mRingerDrawerNormalIcon;
        if (imageView2 != null) {
            imageView2.setImageResource(this.mVolumeRingerIconDrawableId);
        }
        ViewGroup viewGroup3 = (ViewGroup) this.mDialog.findViewById(R.id.volume_drawer_container);
        this.mRingerDrawerContainer = viewGroup3;
        if (viewGroup3 != null) {
            if (!this.mShowVibrate) {
                this.mRingerDrawerVibrate.setVisibility(8);
            }
            if (isLandscape()) {
                ViewGroup viewGroup4 = this.mDialogView;
                viewGroup4.setPadding(getRingerDrawerOpenExtraSize() + viewGroup4.getPaddingLeft(), this.mDialogView.getPaddingTop(), this.mDialogView.getPaddingRight(), this.mDialogView.getPaddingBottom());
            } else {
                ViewGroup viewGroup5 = this.mDialogView;
                viewGroup5.setPadding(viewGroup5.getPaddingLeft(), this.mDialogView.getPaddingTop(), this.mDialogView.getPaddingRight(), getRingerDrawerOpenExtraSize() + this.mDialogView.getPaddingBottom());
            }
            ((LinearLayout) this.mRingerDrawerContainer.findViewById(R.id.volume_drawer_options)).setOrientation(!isLandscape() ? 1 : 0);
            this.mSelectedRingerContainer.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda11(this, 2));
            updateSelectedRingerContainerDescription(this.mIsRingerDrawerOpen);
            this.mRingerDrawerVibrate.setOnClickListener(new RingerDrawerItemClickListener(1));
            this.mRingerDrawerMute.setOnClickListener(new RingerDrawerItemClickListener(0));
            this.mRingerDrawerNormal.setOnClickListener(new RingerDrawerItemClickListener(2));
            final int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent, 0);
            final int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorBackgroundFloating, 0);
            this.mRingerDrawerIconColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda14
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    VolumeDialogImpl volumeDialogImpl = this.f$0;
                    int i2 = colorAttrDefaultColor2;
                    int i3 = colorAttrDefaultColor;
                    String str = VolumeDialogImpl.TAG;
                    volumeDialogImpl.getClass();
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    int iIntValue = ((Integer) ArgbEvaluator.getInstance().evaluate(fFloatValue, Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
                    int iIntValue2 = ((Integer) ArgbEvaluator.getInstance().evaluate(fFloatValue, Integer.valueOf(i3), Integer.valueOf(i2))).intValue();
                    volumeDialogImpl.mRingerDrawerIconAnimatingDeselected.setColorFilter(iIntValue);
                    volumeDialogImpl.mRingerDrawerIconAnimatingSelected.setColorFilter(iIntValue2);
                }
            });
            this.mRingerDrawerIconColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.VolumeDialogImpl.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    VolumeDialogImpl.this.mRingerDrawerIconAnimatingDeselected.clearColorFilter();
                    VolumeDialogImpl.this.mRingerDrawerIconAnimatingSelected.clearColorFilter();
                }
            });
            this.mRingerDrawerIconColorAnimator.setDuration(175L);
            this.mAnimateUpBackgroundToMatchDrawer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda15
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    VolumeDialogImpl volumeDialogImpl = this.f$0;
                    String str = VolumeDialogImpl.TAG;
                    volumeDialogImpl.getClass();
                    volumeDialogImpl.mRingerDrawerClosedAmount = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    volumeDialogImpl.updateBackgroundForDrawerClosedAmount();
                }
            });
        }
        ViewGroup viewGroup6 = (ViewGroup) this.mDialog.findViewById(R.id.odi_captions);
        this.mODICaptionsView = viewGroup6;
        if (viewGroup6 != null) {
            this.mODICaptionsIcon = (CaptionsToggleImageButton) viewGroup6.findViewById(R.id.odi_captions_icon);
        }
        ViewStub viewStub = (ViewStub) this.mDialog.findViewById(R.id.odi_captions_tooltip_stub);
        this.mODICaptionsTooltipViewStub = viewStub;
        if (this.mHasSeenODICaptionsTooltip && viewStub != null) {
            this.mDialogView.removeView(viewStub);
            this.mODICaptionsTooltipViewStub = null;
        }
        this.mSettingsView = this.mDialog.findViewById(R.id.settings_container);
        this.mSettingsIcon = (ImageButton) this.mDialog.findViewById(R.id.settings);
        if (((ArrayList) this.mRows).isEmpty()) {
            if (!AudioSystem.isSingleVolume(this.mContext)) {
                addRow(10, R.drawable.ic_volume_accessibility, true, false, R.drawable.ic_volume_accessibility);
            }
            addRow(3, R.drawable.ic_volume_media, true, true, R.drawable.ic_volume_media_mute);
            if (!AudioSystem.isSingleVolume(this.mContext)) {
                addRow(2, R.drawable.ic_ring_volume, true, false, R.drawable.ic_ring_volume_off);
                addRow(5, R.drawable.ic_volume_ringer, true, false, R.drawable.ic_volume_off);
                addRow(4, R.drawable.ic_alarm, true, false, R.drawable.ic_volume_alarm_mute);
                addRow(0, android.R.drawable.input_method_fullscreen_background, false, false, android.R.drawable.input_method_fullscreen_background);
                addRow(1, R.drawable.ic_volume_system, false, false, R.drawable.ic_volume_system_mute);
            }
        } else {
            int size = ((ArrayList) this.mRows).size();
            for (int i2 = 0; i2 < size; i2++) {
                VolumeRow volumeRow = (VolumeRow) ((ArrayList) this.mRows).get(i2);
                initRow(volumeRow, volumeRow.stream, volumeRow.iconRes, volumeRow.iconMuteRes, volumeRow.important, volumeRow.defaultStream);
                this.mDialogRowsView.addView(volumeRow.view);
                updateVolumeRowH(volumeRow);
            }
        }
        updateRowsH(getActiveRow());
        ImageButton imageButton = this.mRingerIcon;
        if (imageButton != null) {
            imageButton.setAccessibilityLiveRegion(1);
            this.mRingerIcon.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda11(this, 1));
        }
        updateRingerH();
        initSettingsH(i);
        CaptionsToggleImageButton captionsToggleImageButton = this.mODICaptionsIcon;
        if (captionsToggleImageButton != null) {
            VolumeDialogImpl$$ExternalSyntheticLambda16 volumeDialogImpl$$ExternalSyntheticLambda16 = new VolumeDialogImpl$$ExternalSyntheticLambda16(this);
            H h = this.mHandler;
            captionsToggleImageButton.mConfirmedTapListener = volumeDialogImpl$$ExternalSyntheticLambda16;
            if (captionsToggleImageButton.mGestureDetector == null) {
                captionsToggleImageButton.mGestureDetector = new GestureDetector(captionsToggleImageButton.getContext(), captionsToggleImageButton.mGestureListener, h);
            }
        }
        this.mController.getCaptionsComponentState(false);
        Accessibility accessibility = this.mAccessibility;
        VolumeDialogImpl.this.mDialogView.setAccessibilityDelegate(accessibility);
    }

    public final void initDimens() {
        this.mDialogWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.volume_dialog_panel_width);
        this.mDialogCornerRadius = this.mContext.getResources().getDimensionPixelSize(R.dimen.volume_dialog_panel_width_half);
        this.mRingerDrawerItemSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.volume_ringer_drawer_item_size);
        this.mRingerRowsPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.volume_dialog_ringer_rows_padding);
        boolean zHasVibrator = this.mController.hasVibrator();
        this.mShowVibrate = zHasVibrator;
        this.mRingerCount = zHasVibrator ? 3 : 2;
    }

    public final void initRow(final VolumeRow volumeRow, final int i, int i2, int i3, boolean z, boolean z2) {
        volumeRow.stream = i;
        volumeRow.iconRes = i2;
        volumeRow.iconMuteRes = i3;
        volumeRow.important = z;
        volumeRow.defaultStream = z2;
        View viewInflate = this.mDialog.getLayoutInflater().inflate(R.layout.volume_dialog_row, (ViewGroup) null);
        volumeRow.view = viewInflate;
        viewInflate.setId(volumeRow.stream);
        volumeRow.view.setTag(volumeRow);
        TextView textView = (TextView) volumeRow.view.findViewById(R.id.volume_row_header);
        volumeRow.header = textView;
        textView.setId(volumeRow.stream * 20);
        int i4 = 0;
        if (i == 10) {
            volumeRow.header.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        }
        volumeRow.slider = (SeekBar) volumeRow.view.findViewById(R.id.volume_row_slider);
        addSliderHapticsToRow(volumeRow);
        volumeRow.slider.setOnSeekBarChangeListener(new VolumeSeekBarChangeListener(this, volumeRow, i4));
        volumeRow.number = (TextView) volumeRow.view.findViewById(R.id.volume_number);
        volumeRow.slider.setAccessibilityDelegate(new VolumeDialogSeekBarAccessibilityDelegate(100));
        volumeRow.anim = null;
        LayerDrawable layerDrawable = (LayerDrawable) this.mContext.getDrawable(R.drawable.volume_row_seekbar);
        LayerDrawable layerDrawable2 = (LayerDrawable) ((RoundedCornerProgressDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress)).getDrawable();
        volumeRow.sliderProgressSolid = layerDrawable2.findDrawableByLayerId(R.id.volume_seekbar_progress_solid);
        Drawable drawableFindDrawableByLayerId = layerDrawable2.findDrawableByLayerId(R.id.volume_seekbar_progress_icon);
        volumeRow.sliderProgressIcon = drawableFindDrawableByLayerId != null ? (AlphaTintDrawableWrapper) ((RotateDrawable) drawableFindDrawableByLayerId).getDrawable() : null;
        volumeRow.slider.setProgressDrawable(layerDrawable);
        volumeRow.icon = (ImageButton) volumeRow.view.findViewById(R.id.volume_row_icon);
        Resources.Theme theme = this.mContext.getTheme();
        ImageButton imageButton = volumeRow.icon;
        if (imageButton != null) {
            imageButton.setImageResource(i2);
        }
        AlphaTintDrawableWrapper alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
        if (alphaTintDrawableWrapper != null) {
            alphaTintDrawableWrapper.setDrawable(volumeRow.view.getResources().getDrawable(i2, theme));
        }
        ImageButton imageButton2 = volumeRow.icon;
        if (imageButton2 != null) {
            if (volumeRow.stream != 10) {
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda19
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        VolumeDialogImpl volumeDialogImpl = this.f$0;
                        VolumeDialogImpl.VolumeRow volumeRow2 = volumeRow;
                        int i5 = i;
                        String str = VolumeDialogImpl.TAG;
                        volumeDialogImpl.getClass();
                        Events.writeEvent(7, Integer.valueOf(volumeRow2.stream), Integer.valueOf(volumeRow2.iconState));
                        volumeDialogImpl.mController.setActiveStream(volumeRow2.stream);
                        if (volumeRow2.stream == 2) {
                            boolean zHasVibrator = volumeDialogImpl.mController.hasVibrator();
                            if (volumeDialogImpl.mState.ringerModeInternal != 2) {
                                volumeDialogImpl.mController.setRingerMode(2, false);
                                if (volumeRow2.ss.level == 0) {
                                    volumeDialogImpl.mController.setStreamVolume(i5, 1);
                                }
                            } else if (zHasVibrator) {
                                volumeDialogImpl.mController.setRingerMode(1, false);
                            } else {
                                volumeDialogImpl.mController.setStreamVolume(i5, volumeRow2.ss.level == 0 ? volumeRow2.lastAudibleLevel : 0);
                            }
                        } else {
                            VolumeDialogController.StreamState streamState = volumeRow2.ss;
                            int i6 = streamState.level;
                            int i7 = streamState.levelMin;
                            boolean z3 = i6 == i7;
                            VolumeDialogController volumeDialogController = volumeDialogImpl.mController;
                            if (z3) {
                                i7 = volumeRow2.lastAudibleLevel;
                            }
                            volumeDialogController.setStreamVolume(i5, i7);
                        }
                        volumeRow2.userAttempt = 0L;
                    }
                });
            } else {
                imageButton2.setImportantForAccessibility(2);
            }
        }
    }

    public final void initSettingsH(int i) {
        View view = this.mSettingsView;
        if (view != null) {
            view.setVisibility((((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isCurrentUserSetup() && i == 0) ? 0 : 8);
        }
        ImageButton imageButton = this.mSettingsIcon;
        if (imageButton != null) {
            imageButton.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda11(this, 0));
        }
    }

    public final boolean isLandscape() {
        return this.mContext.getResources().getConfiguration().orientation == 2;
    }

    public final boolean isRtl$1() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1;
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        this.mTouchableRegion.setEmpty();
        for (int i = 0; i < this.mDialogView.getChildCount(); i++) {
            unionViewBoundstoTouchableRegion(this.mDialogView.getChildAt(i));
        }
        View view = this.mODICaptionsTooltipView;
        if (view != null && view.getVisibility() == 0) {
            unionViewBoundstoTouchableRegion(this.mODICaptionsTooltipView);
        }
        internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mOrientation = configuration.orientation;
    }

    public void onPostureChanged(int i) {
        this.mHandler.obtainMessage(2, 11, 0).sendToTarget();
        this.mDevicePosture = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onStateChangedH(VolumeDialogController.State state) {
        VolumeRow volumeRow;
        VolumeDialogImpl volumeDialogImpl;
        int i;
        int i2;
        if (D.BUG) {
            Log.d(TAG, "onStateChangedH() state: " + state.toString());
        }
        VolumeDialogController.State state2 = this.mState;
        if (state2 != null && state != null && (i = state2.ringerModeInternal) != -1 && i != (i2 = state.ringerModeInternal) && i2 == 1) {
            this.mController.vibrate(VibrationEffect.get(1));
        }
        this.mState = state;
        this.mDynamic.clear();
        int i3 = 0;
        int i4 = 0;
        while (i4 < state.states.size()) {
            int iKeyAt = state.states.keyAt(i4);
            if (state.states.valueAt(i4).dynamic) {
                this.mDynamic.put(iKeyAt, true);
                ArrayList arrayList = (ArrayList) this.mRows;
                int size = arrayList.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        volumeRow = null;
                        break;
                    }
                    Object obj = arrayList.get(i5);
                    i5++;
                    volumeRow = (VolumeRow) obj;
                    if (volumeRow.stream == iKeyAt) {
                        break;
                    }
                }
                if (volumeRow == null) {
                    volumeDialogImpl = this;
                    volumeDialogImpl.addRow(iKeyAt, R.drawable.ic_volume_remote, true, false, R.drawable.ic_volume_remote_mute);
                }
            } else {
                volumeDialogImpl = this;
            }
            i4++;
            this = volumeDialogImpl;
        }
        VolumeDialogImpl volumeDialogImpl2 = this;
        int i6 = volumeDialogImpl2.mActiveStream;
        int i7 = state.activeStream;
        if (i6 != i7) {
            volumeDialogImpl2.mPrevActiveStream = i6;
            volumeDialogImpl2.mActiveStream = i7;
            volumeDialogImpl2.updateRowsH(volumeDialogImpl2.getActiveRow());
            if (volumeDialogImpl2.mShowing) {
                volumeDialogImpl2.rescheduleTimeoutH();
            }
        }
        ArrayList arrayList2 = (ArrayList) volumeDialogImpl2.mRows;
        int size2 = arrayList2.size();
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            volumeDialogImpl2.updateVolumeRowH((VolumeRow) obj2);
        }
        volumeDialogImpl2.updateRingerH();
        volumeDialogImpl2.updateSelectedRingerContainerDescription(volumeDialogImpl2.mIsRingerDrawerOpen);
        volumeDialogImpl2.mWindow.setTitle(volumeDialogImpl2.mContext.getString(R.string.volume_dialog_title, volumeDialogImpl2.getStreamLabelH(volumeDialogImpl2.getActiveRow().ss)));
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        this.mContext.getTheme().applyStyle(this.mContext.getThemeResId(), true);
    }

    public final void recheckH(VolumeRow volumeRow) {
        if (volumeRow != null) {
            if (D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m(volumeRow.stream, TAG, new StringBuilder("recheckH "));
            }
            updateVolumeRowH(volumeRow);
            return;
        }
        if (D.BUG) {
            Log.d(TAG, "recheckH ALL");
        }
        trimObsoleteH();
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            updateVolumeRowH((VolumeRow) obj);
        }
    }

    public void removeDismissMessages() {
        this.mHandler.removeMessages(2);
    }

    public final void rescheduleTimeoutH() {
        int recommendedTimeoutMillis;
        this.mHandler.removeMessages(2);
        if (this.mHovering) {
            recommendedTimeoutMillis = this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(VolumePanelState.DIALOG_HOVERING_TIMEOUT_MILLIS, 4);
        } else if (this.mSafetyWarning != null) {
            recommendedTimeoutMillis = this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 6);
        } else if (this.mHasSeenODICaptionsTooltip || this.mODICaptionsTooltipView == null) {
            recommendedTimeoutMillis = this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(this.mDialogTimeoutMillis, 4);
        } else {
            recommendedTimeoutMillis = this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(5000, 6);
        }
        H h = this.mHandler;
        h.sendMessageDelayed(h.obtainMessage(2, 3, 0), recommendedTimeoutMillis);
        String str = TAG;
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(recommendedTimeoutMillis, "rescheduleTimeout ", " ");
        sbM.append(Debug.getCaller());
        Log.i(str, sbM.toString());
        this.mController.userActivity();
    }

    public final void setRingerMode(int i) {
        VibrationEffect vibrationEffect;
        Events.writeEvent(18, Integer.valueOf(i));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Secure.putInt(contentResolver, "manual_ringer_toggle_count", Settings.Secure.getInt(contentResolver, "manual_ringer_toggle_count", 0) + 1);
        updateRingerH();
        String string = null;
        if (i == 0) {
            vibrationEffect = VibrationEffect.get(0);
        } else if (i == 1) {
            vibrationEffect = null;
        } else if (i != 2) {
            vibrationEffect = VibrationEffect.get(1);
        } else {
            this.mController.scheduleTouchFeedback();
            vibrationEffect = null;
        }
        if (vibrationEffect != null) {
            this.mController.vibrate(vibrationEffect);
        }
        this.mController.setRingerMode(i, false);
        int i2 = Prefs.getInt(this.mContext, "RingerGuidanceCount", 0);
        if (i2 > 12) {
            return;
        }
        if (i == 0) {
            string = this.mContext.getString(17043598);
        } else if (i != 2) {
            string = this.mContext.getString(17043599);
        } else {
            if (this.mState.states.get(2) != null) {
                string = this.mContext.getString(R.string.volume_dialog_ringer_guidance_ring, NumberFormat.getPercentInstance().format(r11.level / r11.levelMax));
            }
        }
        Toast.makeText(this.mContext, string, 0).show();
        Prefs.putInt(this.mContext, "RingerGuidanceCount", i2 + 1);
    }

    public final boolean shouldBeVisibleH(VolumeRow volumeRow, VolumeRow volumeRow2) {
        int i = volumeRow.stream;
        int i2 = volumeRow2.stream;
        if (i == i2) {
            return true;
        }
        if (this.mIsTv) {
            return false;
        }
        if (i == 10) {
            return this.mShowA11yStream;
        }
        if (i2 == 10 && i == this.mPrevActiveStream) {
            return true;
        }
        if (volumeRow.defaultStream) {
            return i2 == 2 || i2 == 4 || i2 == 0 || i2 == 10 || this.mDynamic.get(i2);
        }
        View view = volumeRow.view;
        return view != null && this.mShowing && view.getVisibility() == 0;
    }

    public void showCsdWarningH(int i, int i2) {
        synchronized (this.mSafetyWarningLock) {
            try {
                if (this.mCsdDialog != null) {
                    return;
                }
                CsdWarningDialog csdWarningDialogCreate = this.mCsdWarningDialogFactory.create(i, new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 5), this.mCsdWarningNotificationActions);
                this.mCsdDialog = csdWarningDialogCreate;
                csdWarningDialogCreate.show();
                recheckH(null);
                if (i2 > 0) {
                    this.mHandler.removeMessages(8);
                    H h = this.mHandler;
                    h.sendMessageDelayed(h.obtainMessage(8, 10, 0), i2);
                    String str = TAG;
                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "scheduleCsdTimeoutH ", "ms ");
                    sbM.append(Debug.getCaller());
                    Log.i(str, sbM.toString());
                    this.mController.userActivity();
                }
                rescheduleTimeoutH();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void showRingerDrawer() {
        if (this.mIsRingerDrawerOpen) {
            return;
        }
        this.mRingerDrawerVibrateIcon.setVisibility(this.mState.ringerModeInternal == 1 ? 4 : 0);
        this.mRingerDrawerMuteIcon.setVisibility(this.mState.ringerModeInternal == 0 ? 4 : 0);
        this.mRingerDrawerNormalIcon.setVisibility(this.mState.ringerModeInternal != 2 ? 0 : 4);
        this.mRingerDrawerNewSelectionBg.setAlpha(0.0f);
        if (isLandscape()) {
            this.mRingerDrawerNewSelectionBg.setTranslationX(getTranslationInDrawerForRingerMode(this.mState.ringerModeInternal));
        } else {
            this.mRingerDrawerNewSelectionBg.setTranslationY(getTranslationInDrawerForRingerMode(this.mState.ringerModeInternal));
        }
        if (isLandscape()) {
            this.mRingerDrawerContainer.setTranslationX((this.mRingerCount - 1) * this.mRingerDrawerItemSize);
        } else {
            this.mRingerDrawerContainer.setTranslationY((this.mRingerCount - 1) * this.mRingerDrawerItemSize);
        }
        this.mRingerDrawerContainer.setAlpha(0.0f);
        this.mRingerDrawerContainer.setVisibility(0);
        int i = this.mState.ringerModeInternal == 1 ? 175 : IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.mRingerDrawerContainer.animate();
        Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
        long j = i;
        viewPropertyAnimatorAnimate.setInterpolator(interpolator).setDuration(j).setStartDelay(this.mState.ringerModeInternal == 1 ? 75L : 0L).alpha(1.0f).translationX(0.0f).translationY(0.0f).start();
        this.mSelectedRingerContainer.animate().setInterpolator(interpolator).setDuration(250L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda0(this, 0));
        this.mAnimateUpBackgroundToMatchDrawer.setDuration(j);
        this.mAnimateUpBackgroundToMatchDrawer.setInterpolator(interpolator);
        this.mAnimateUpBackgroundToMatchDrawer.start();
        if (isLandscape()) {
            this.mSelectedRingerContainer.animate().translationX(getTranslationInDrawerForRingerMode(this.mState.ringerModeInternal)).start();
        } else {
            this.mSelectedRingerContainer.animate().translationY(getTranslationInDrawerForRingerMode(this.mState.ringerModeInternal)).start();
        }
        updateSelectedRingerContainerDescription(true);
        this.mSelectedRingerContainer.setImportantForAccessibility(2);
        this.mSelectedRingerContainer.clearFocus();
        this.mIsRingerDrawerOpen = true;
    }

    public void toggleRingerDrawer(boolean z) {
        if (z) {
            showRingerDrawer();
        } else {
            hideRingerDrawer();
        }
    }

    public final void trimObsoleteH() {
        if (D.BUG) {
            Log.d(TAG, "trimObsoleteH");
        }
        for (int size = ((ArrayList) this.mRows).size() - 1; size >= 0; size--) {
            VolumeRow volumeRow = (VolumeRow) ((ArrayList) this.mRows).get(size);
            VolumeDialogController.StreamState streamState = volumeRow.ss;
            if (streamState != null && streamState.dynamic && !this.mDynamic.get(volumeRow.stream)) {
                ((ArrayList) this.mRows).remove(size);
                this.mDialogRowsView.removeView(volumeRow.view);
                ConfigurableTexts configurableTexts = this.mConfigurableTexts;
                TextView textView = volumeRow.header;
                configurableTexts.mTexts.remove(textView);
                configurableTexts.mTextLabels.remove(textView);
            }
        }
    }

    public final void unionViewBoundstoTouchableRegion(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        float ringerDrawerOpenExtraSize = iArr[0];
        float ringerDrawerOpenExtraSize2 = iArr[1];
        if (view == this.mTopContainer && !this.mIsRingerDrawerOpen) {
            if (!isLandscape()) {
                ringerDrawerOpenExtraSize2 += getRingerDrawerOpenExtraSize();
            } else if (getRingerDrawerOpenExtraSize() > getVisibleRowsExtraSize()) {
                ringerDrawerOpenExtraSize += getRingerDrawerOpenExtraSize() - getVisibleRowsExtraSize();
            }
        }
        this.mTouchableRegion.op((int) ringerDrawerOpenExtraSize, (int) ringerDrawerOpenExtraSize2, view.getWidth() + iArr[0], view.getHeight() + iArr[1], Region.Op.UNION);
    }

    public final void updateBackgroundForDrawerClosedAmount() {
        Drawable drawable = this.mRingerAndDrawerContainerBackground;
        if (drawable == null) {
            return;
        }
        Rect rectCopyBounds = drawable.copyBounds();
        if (isLandscape()) {
            rectCopyBounds.left = (int) (this.mRingerDrawerClosedAmount * getRingerDrawerOpenExtraSize());
        } else {
            rectCopyBounds.top = (int) (this.mRingerDrawerClosedAmount * getRingerDrawerOpenExtraSize());
        }
        this.mRingerAndDrawerContainerBackground.setBounds(rectCopyBounds);
    }

    public final void updateRingerH() {
        VolumeDialogController.State state;
        VolumeDialogController.StreamState streamState;
        if (this.mRinger == null || (state = this.mState) == null || (streamState = state.states.get(2)) == null) {
            return;
        }
        VolumeDialogController.State state2 = this.mState;
        int i = state2.zenMode;
        boolean z = i == 3 || i == 2 || (i == 1 && state2.disallowRinger);
        boolean z2 = !z;
        ImageButton imageButton = this.mRingerIcon;
        if (imageButton != null) {
            imageButton.setEnabled(z2);
        }
        int i2 = this.mState.ringerModeInternal;
        if (i2 == 0) {
            this.mRingerIcon.setImageResource(this.mVolumeRingerMuteIconDrawableId);
            this.mSelectedRingerIcon.setImageResource(this.mVolumeRingerMuteIconDrawableId);
            this.mRingerIcon.setTag(2);
            addAccessibilityDescription(this.mRingerIcon, 0, this.mContext.getString(R.string.volume_ringer_hint_unmute));
            return;
        }
        if (i2 == 1) {
            this.mRingerIcon.setImageResource(R.drawable.ic_legacy_volume_ringer_vibrate);
            this.mSelectedRingerIcon.setImageResource(R.drawable.ic_legacy_volume_ringer_vibrate);
            addAccessibilityDescription(this.mRingerIcon, 1, this.mContext.getString(R.string.volume_ringer_hint_mute));
            this.mRingerIcon.setTag(3);
            return;
        }
        boolean z3 = (this.mAutomute && streamState.level == 0) || streamState.muted;
        if (!z && z3) {
            this.mRingerIcon.setImageResource(this.mVolumeRingerMuteIconDrawableId);
            this.mSelectedRingerIcon.setImageResource(this.mVolumeRingerMuteIconDrawableId);
            addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_unmute));
            this.mRingerIcon.setTag(2);
            return;
        }
        this.mRingerIcon.setImageResource(this.mVolumeRingerIconDrawableId);
        this.mSelectedRingerIcon.setImageResource(this.mVolumeRingerIconDrawableId);
        if (this.mController.hasVibrator()) {
            addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_vibrate));
        } else {
            addAccessibilityDescription(this.mRingerIcon, 2, this.mContext.getString(R.string.volume_ringer_hint_mute));
        }
        this.mRingerIcon.setTag(1);
    }

    public final void updateRowsH(VolumeRow volumeRow) {
        Trace.beginSection("VolumeDialogImpl#updateRowsH");
        if (D.BUG) {
            Log.d(TAG, "updateRowsH");
        }
        if (!this.mShowing) {
            trimObsoleteH();
        }
        int iMax = !isRtl$1() ? -1 : 32767;
        ArrayList arrayList = (ArrayList) this.mRows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            VolumeRow volumeRow2 = (VolumeRow) obj;
            boolean z = volumeRow2 == volumeRow;
            boolean zShouldBeVisibleH = shouldBeVisibleH(volumeRow2, volumeRow);
            View view = volumeRow2.view;
            if (view != null) {
                if ((view.getVisibility() == 0) != zShouldBeVisibleH) {
                    view.setVisibility(zShouldBeVisibleH ? 0 : 8);
                }
            }
            if (zShouldBeVisibleH && this.mRingerAndDrawerContainerBackground != null) {
                iMax = !isRtl$1() ? Math.max(iMax, this.mDialogRowsView.indexOfChild(volumeRow2.view)) : Math.min(iMax, this.mDialogRowsView.indexOfChild(volumeRow2.view));
                ViewGroup.LayoutParams layoutParams = volumeRow2.view.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                    if (isRtl$1()) {
                        layoutParams2.setMarginStart(this.mRingerRowsPadding);
                    } else {
                        layoutParams2.setMarginEnd(this.mRingerRowsPadding);
                    }
                }
                volumeRow2.view.setBackgroundDrawable(this.mContext.getDrawable(R.drawable.volume_row_rounded_background));
            }
            if (volumeRow2.view.isShown()) {
                updateVolumeRowTintH(volumeRow2, z);
            }
        }
        if (iMax > -1 && iMax < 32767) {
            View childAt = this.mDialogRowsView.getChildAt(iMax);
            ViewGroup.LayoutParams layoutParams3 = childAt.getLayoutParams();
            if (layoutParams3 instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
                layoutParams4.setMarginStart(0);
                layoutParams4.setMarginEnd(0);
                childAt.setBackgroundColor(0);
            }
        }
        updateBackgroundForDrawerClosedAmount();
        Trace.endSection();
    }

    public final void updateSelectedRingerContainerDescription(boolean z) {
        String string;
        VolumeDialogController.State state = this.mState;
        if (state == null || this.mSelectedRingerContainer == null) {
            return;
        }
        String string2 = this.mContext.getString(getStringDescriptionResourceForRingerMode(state.ringerModeInternal));
        if (z) {
            string = "";
        } else {
            string2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string2, ", ");
            string = this.mContext.getString(R.string.volume_ringer_change);
        }
        this.mSelectedRingerContainer.setContentDescription(string2 + string);
    }

    /* JADX WARN: Removed duplicated region for block: B:156:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02c7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:281:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVolumeRowH(VolumeRow volumeRow) {
        VolumeDialogController.StreamState streamState;
        int i;
        int i2;
        ImageButton imageButton;
        AlphaTintDrawableWrapper alphaTintDrawableWrapper;
        ImageButton imageButton2;
        TextView textView;
        boolean z = D.BUG;
        if (z) {
            TooltipPopup$$ExternalSyntheticOutline0.m(volumeRow.stream, TAG, new StringBuilder("updateVolumeRowH s="));
        }
        VolumeDialogController.State state = this.mState;
        if (state == null || (streamState = state.states.get(volumeRow.stream)) == null) {
            return;
        }
        volumeRow.ss = streamState;
        int i3 = streamState.level;
        if (i3 > 0) {
            volumeRow.lastAudibleLevel = i3;
        }
        if (i3 == volumeRow.requestedLevel) {
            volumeRow.requestedLevel = -1;
        }
        int i4 = volumeRow.stream;
        boolean z2 = i4 == 0;
        boolean z3 = i4 == 10;
        boolean z4 = i4 == 2;
        boolean z5 = i4 == 1;
        boolean z6 = i4 == 4;
        boolean z7 = i4 == 3;
        boolean z8 = z4 && this.mState.ringerModeInternal == 1;
        boolean z9 = z4 && this.mState.ringerModeInternal == 0;
        VolumeDialogController.State state2 = this.mState;
        int i5 = state2.zenMode;
        boolean z10 = !(i5 == 3) ? !(i5 == 2) ? (i5 == 1) && ((z6 && state2.disallowAlarms) || ((z7 && state2.disallowMedia) || ((z4 && state2.disallowRinger) || (z5 && state2.disallowSystem)))) : z4 || z5 || z6 || z7 : !(z4 || z5);
        int i6 = streamState.levelMax * 100;
        if (i6 != volumeRow.slider.getMax()) {
            volumeRow.slider.setMax(i6);
        }
        int i7 = streamState.levelMin * 100;
        if (i7 != volumeRow.slider.getMin()) {
            volumeRow.slider.setMin(i7);
        }
        TextView textView2 = volumeRow.header;
        String streamLabelH = getStreamLabelH(streamState);
        CharSequence text = textView2.getText();
        if (text == null || text.length() == 0) {
            text = null;
        }
        if (!Objects.equals(text, (streamLabelH == null || streamLabelH.length() == 0) ? null : streamLabelH)) {
            textView2.setText(streamLabelH);
        }
        volumeRow.slider.setContentDescription(volumeRow.header.getText());
        ConfigurableTexts configurableTexts = this.mConfigurableTexts;
        TextView textView3 = volumeRow.header;
        int i8 = streamState.name;
        if (textView3 == null) {
            configurableTexts.getClass();
        } else if (configurableTexts.mTexts.containsKey(textView3)) {
            ((Integer) configurableTexts.mTexts.get(textView3)).getClass();
        } else {
            Resources resources = configurableTexts.mContext.getResources();
            int textSize = (int) ((textView3.getTextSize() / resources.getConfiguration().fontScale) / resources.getDisplayMetrics().density);
            configurableTexts.mTexts.put(textView3, Integer.valueOf(textSize));
            textView3.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.volume.ConfigurableTexts.1
                public final /* synthetic */ int val$sp;
                public final /* synthetic */ TextView val$text;

                public AnonymousClass1(TextView textView32, int textSize2) {
                    textView = textView32;
                    i = textSize2;
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    ConfigurableTexts configurableTexts2 = ConfigurableTexts.this;
                    TextView textView4 = textView;
                    int i9 = i;
                    configurableTexts2.getClass();
                    textView4.setTextSize(2, i9);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
            configurableTexts.mTextLabels.put(textView32, Integer.valueOf(i8));
        }
        boolean z11 = this.mAutomute;
        boolean z12 = (z11 || streamState.muteSupported) && !z10;
        if (z10) {
            i2 = android.R.drawable.jog_dial_arrow_long_middle_yellow;
        } else if (z8) {
            i2 = R.drawable.ic_legacy_volume_ringer_vibrate;
        } else if (z9) {
            i2 = volumeRow.iconMuteRes;
        } else if (streamState.routedToBluetooth) {
            i2 = z2 ? R.drawable.ic_volume_bt_sco : ((z11 && streamState.level == 0) || streamState.muted) ? R.drawable.ic_volume_media_bt_mute : R.drawable.ic_volume_media_bt;
        } else if ((z11 && streamState.level == 0) || streamState.muted) {
            i2 = volumeRow.iconMuteRes;
        } else {
            if (this.mShowLowMediaVolumeIcon) {
                i = 2;
                if (streamState.level * 2 < streamState.levelMax + streamState.levelMin) {
                    i2 = R.drawable.ic_volume_media_low;
                }
                Resources.Theme theme = this.mContext.getTheme();
                imageButton = volumeRow.icon;
                if (imageButton != null) {
                    imageButton.setImageResource(i2);
                }
                alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
                if (alphaTintDrawableWrapper != null) {
                    alphaTintDrawableWrapper.setDrawable(volumeRow.view.getResources().getDrawable(i2, theme));
                }
                volumeRow.iconState = i2 == R.drawable.ic_legacy_volume_ringer_vibrate ? 3 : (i2 == R.drawable.ic_volume_media_bt_mute || i2 == volumeRow.iconMuteRes) ? i : (i2 == R.drawable.ic_volume_media_bt || i2 == volumeRow.iconRes || i2 == R.drawable.ic_volume_media_low) ? 1 : 0;
                imageButton2 = volumeRow.icon;
                if (imageButton2 != null) {
                    if (z12) {
                        int i9 = R.string.volume_stream_content_description_mute;
                        if (z4) {
                            if (z8) {
                                imageButton2.setContentDescription(this.mContext.getString(R.string.volume_stream_content_description_unmute, getStreamLabelH(streamState)));
                            } else if (this.mController.hasVibrator()) {
                                volumeRow.icon.setContentDescription(this.mContext.getString(this.mShowA11yStream ? R.string.volume_stream_content_description_vibrate_a11y : R.string.volume_stream_content_description_vibrate, getStreamLabelH(streamState)));
                            } else {
                                ImageButton imageButton3 = volumeRow.icon;
                                Context context = this.mContext;
                                if (this.mShowA11yStream) {
                                    i9 = R.string.volume_stream_content_description_mute_a11y;
                                }
                                imageButton3.setContentDescription(context.getString(i9, getStreamLabelH(streamState)));
                            }
                        } else if (z3) {
                            imageButton2.setContentDescription(getStreamLabelH(streamState));
                        } else if (streamState.muted || (this.mAutomute && streamState.level == 0)) {
                            imageButton2.setContentDescription(this.mContext.getString(R.string.volume_stream_content_description_unmute, getStreamLabelH(streamState)));
                        } else {
                            Context context2 = this.mContext;
                            if (this.mShowA11yStream) {
                                i9 = R.string.volume_stream_content_description_mute_a11y;
                            }
                            imageButton2.setContentDescription(context2.getString(i9, getStreamLabelH(streamState)));
                        }
                    } else {
                        imageButton2.setContentDescription(getStreamLabelH(streamState));
                    }
                }
                if (z10) {
                    volumeRow.tracking = false;
                }
                boolean z13 = !z10;
                VolumeDialogController.StreamState streamState2 = volumeRow.ss;
                int i10 = (!streamState2.muted || z4 || z10) ? streamState2.level : 0;
                Trace.beginSection("VolumeDialogImpl#updateVolumeRowSliderH");
                volumeRow.slider.setEnabled(z13);
                updateVolumeRowTintH(volumeRow, volumeRow.stream == this.mActiveStream);
                if (!volumeRow.tracking) {
                    int progress = volumeRow.slider.getProgress();
                    int volumeFromProgress = getVolumeFromProgress(volumeRow.ss, volumeRow.slider, progress);
                    boolean z14 = volumeRow.view.getVisibility() == 0;
                    boolean z15 = android.os.SystemClock.uptimeMillis() - volumeRow.userAttempt < 1000;
                    this.mHandler.removeMessages(3, volumeRow);
                    boolean z16 = this.mShowing;
                    if (z16 && z14 && z15) {
                        if (z) {
                            Log.d(TAG, "inGracePeriod");
                        }
                        H h = this.mHandler;
                        h.sendMessageAtTime(h.obtainMessage(3, volumeRow), volumeRow.userAttempt + 1000);
                    } else if (i10 != volumeFromProgress || !z16 || !z14) {
                        VolumeDialogController.StreamState streamState3 = volumeRow.ss;
                        SeekBar seekBar = volumeRow.slider;
                        float f = i10;
                        float f2 = streamState3.levelMin;
                        float f3 = streamState3.levelMax;
                        float min = seekBar.getMin();
                        float max = seekBar.getMax();
                        float f4 = f3 - f2;
                        float f5 = max - min;
                        if (f4 != 0.0f && f5 != 0.0f) {
                            min = MathUtils.constrain((((f - f2) / f4) * f5) + min, min, max);
                        }
                        int i11 = (int) min;
                        if (progress != i11) {
                            if (this.mIsTv) {
                                volumeRow.slider.setProgress(i11, false);
                            } else if (this.mShowing && z14) {
                                ObjectAnimator objectAnimator = volumeRow.anim;
                                if (objectAnimator == null || !objectAnimator.isRunning() || volumeRow.animTargetProgress != i11) {
                                    ObjectAnimator objectAnimator2 = volumeRow.anim;
                                    if (objectAnimator2 == null) {
                                        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(volumeRow.slider, "progress", progress, i11);
                                        volumeRow.anim = objectAnimatorOfInt;
                                        objectAnimatorOfInt.setInterpolator(new DecelerateInterpolator());
                                        AnonymousClass3 anonymousClass3 = !this.mShouldListenForJank ? null : new AnonymousClass3(volumeRow.view, "update", 80L);
                                        if (anonymousClass3 != null) {
                                            volumeRow.anim.addListener(anonymousClass3);
                                        }
                                    } else {
                                        objectAnimator2.cancel();
                                        volumeRow.anim.setIntValues(progress, i11);
                                        volumeRow.deliverOnProgressChangedHaptics(i11, false);
                                    }
                                    volumeRow.animTargetProgress = i11;
                                    volumeRow.anim.setDuration(80L);
                                    volumeRow.anim.start();
                                }
                            } else {
                                ObjectAnimator objectAnimator3 = volumeRow.anim;
                                if (objectAnimator3 != null) {
                                    objectAnimator3.cancel();
                                }
                                volumeRow.slider.setProgress(i11, true);
                            }
                        }
                    }
                }
                Trace.endSection();
                textView = volumeRow.number;
                if (textView != null) {
                    textView.setText(Integer.toString(i10));
                    return;
                }
                return;
            }
            i = 2;
            i2 = volumeRow.iconRes;
            Resources.Theme theme2 = this.mContext.getTheme();
            imageButton = volumeRow.icon;
            if (imageButton != null) {
            }
            alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
            if (alphaTintDrawableWrapper != null) {
            }
            volumeRow.iconState = i2 == R.drawable.ic_legacy_volume_ringer_vibrate ? 3 : (i2 == R.drawable.ic_volume_media_bt_mute || i2 == volumeRow.iconMuteRes) ? i : (i2 == R.drawable.ic_volume_media_bt || i2 == volumeRow.iconRes || i2 == R.drawable.ic_volume_media_low) ? 1 : 0;
            imageButton2 = volumeRow.icon;
            if (imageButton2 != null) {
            }
            if (z10) {
            }
            boolean z132 = !z10;
            VolumeDialogController.StreamState streamState22 = volumeRow.ss;
            if (streamState22.muted) {
            }
            Trace.beginSection("VolumeDialogImpl#updateVolumeRowSliderH");
            volumeRow.slider.setEnabled(z132);
            updateVolumeRowTintH(volumeRow, volumeRow.stream == this.mActiveStream);
            if (!volumeRow.tracking) {
            }
            Trace.endSection();
            textView = volumeRow.number;
            if (textView != null) {
            }
        }
        i = 2;
        Resources.Theme theme22 = this.mContext.getTheme();
        imageButton = volumeRow.icon;
        if (imageButton != null) {
        }
        alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
        if (alphaTintDrawableWrapper != null) {
        }
        volumeRow.iconState = i2 == R.drawable.ic_legacy_volume_ringer_vibrate ? 3 : (i2 == R.drawable.ic_volume_media_bt_mute || i2 == volumeRow.iconMuteRes) ? i : (i2 == R.drawable.ic_volume_media_bt || i2 == volumeRow.iconRes || i2 == R.drawable.ic_volume_media_low) ? 1 : 0;
        imageButton2 = volumeRow.icon;
        if (imageButton2 != null) {
        }
        if (z10) {
        }
        boolean z1322 = !z10;
        VolumeDialogController.StreamState streamState222 = volumeRow.ss;
        if (streamState222.muted) {
        }
        Trace.beginSection("VolumeDialogImpl#updateVolumeRowSliderH");
        volumeRow.slider.setEnabled(z1322);
        updateVolumeRowTintH(volumeRow, volumeRow.stream == this.mActiveStream);
        if (!volumeRow.tracking) {
        }
        Trace.endSection();
        textView = volumeRow.number;
        if (textView != null) {
        }
    }

    public final void updateVolumeRowTintH(VolumeRow volumeRow, boolean z) {
        int iAlpha;
        if (z) {
            volumeRow.slider.requestFocus();
        }
        boolean z2 = z && volumeRow.slider.isEnabled();
        if (z2 || this.mChangeVolumeRowTintWhenInactive) {
            ColorStateList colorAttr = z2 ? Utils.getColorAttr(android.R.attr.colorAccent, this.mContext) : Utils.getColorAttr(android.R.^attr-private.colorAccentPrimaryVariant, this.mContext);
            if (z2) {
                iAlpha = Color.alpha(colorAttr.getDefaultColor());
            } else {
                TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{android.R.attr.secondaryContentAlpha});
                float f = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
                typedArrayObtainStyledAttributes.recycle();
                iAlpha = (int) (f * 255.0f);
            }
            ColorStateList colorAttr2 = Utils.getColorAttr(android.R.attr.colorBackgroundFloating, this.mContext);
            ColorStateList colorAttr3 = Utils.getColorAttr(android.R.^attr-private.yearListItemActivatedTextAppearance, this.mContext);
            volumeRow.sliderProgressSolid.setTintList(colorAttr);
            AlphaTintDrawableWrapper alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
            if (alphaTintDrawableWrapper != null) {
                alphaTintDrawableWrapper.setTintList(colorAttr2);
            }
            ImageButton imageButton = volumeRow.icon;
            if (imageButton != null) {
                imageButton.setImageTintList(colorAttr3);
                volumeRow.icon.setImageAlpha(iAlpha);
            }
            TextView textView = volumeRow.number;
            if (textView != null) {
                textView.setTextColor(colorAttr);
                volumeRow.number.setAlpha(iAlpha);
            }
        }
    }

    /* renamed from: com.android.systemui.volume.VolumeDialogImpl$3, reason: invalid class name */
    public class AnonymousClass3 implements Animator.AnimatorListener {
        public final /* synthetic */ long val$timeout;
        public final /* synthetic */ String val$type;
        public final /* synthetic */ View val$v;

        public AnonymousClass3(View view, String str, long j) {
            this.val$v = view;
            this.val$type = str;
            this.val$timeout = j;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            VolumeDialogImpl.this.mInteractionJankMonitor.cancel(55);
            Log.d(VolumeDialogImpl.TAG, "onAnimationCancel");
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            VolumeDialogImpl.this.mInteractionJankMonitor.end(55);
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            if (this.val$v.isAttachedToWindow()) {
                VolumeDialogImpl.this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(55, this.val$v).setTag(this.val$type).setTimeout(this.val$timeout));
            } else if (D.BUG) {
                Log.d(VolumeDialogImpl.TAG, "onAnimationStart view do not attached to window:" + this.val$v);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
