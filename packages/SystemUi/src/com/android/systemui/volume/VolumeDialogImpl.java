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
import android.os.SystemClock;
import android.os.Trace;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.InputFilter;
import android.util.ArrayMap;
import android.util.Log;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
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
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.AlphaTintDrawableWrapper;
import com.android.systemui.util.RoundedCornerProgressDrawable;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.VolumeDialogImpl.C36133;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeDialogImpl implements VolumeDialog, Dumpable, ConfigurationController.ConfigurationListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final String TAG = Util.logTag(VolumeDialogImpl.class);
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public int mActiveStream;
    public final ActivityManager mActivityManager;
    public final ActivityStarter mActivityStarter;
    public final boolean mChangeVolumeRowTintWhenInactive;
    public ConfigurableTexts mConfigurableTexts;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final VolumeDialogController mController;
    public final VolumeDialogImpl$$ExternalSyntheticLambda0 mCrossWindowBlurEnabledListener;
    public CsdWarningDialog mCsdDialog;
    public final CsdWarningDialog.Factory mCsdWarningDialogFactory;
    public int mDevicePosture;
    public final DevicePostureController mDevicePostureController;
    public final VolumeDialogImpl$$ExternalSyntheticLambda1 mDevicePostureControllerCallback;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public CustomDialog mDialog;
    public int mDialogCornerRadius;
    public final int mDialogHideAnimationDurationMs;
    public ViewGroup mDialogRowsView;
    public BackgroundBlurDrawable mDialogRowsViewBackground;
    public ViewGroup mDialogRowsViewContainer;
    public final int mDialogShowAnimationDurationMs;
    public ViewGroup mDialogView;
    public int mDialogWidth;
    public final HandlerC3618H mHandler;
    public boolean mHasSeenODICaptionsTooltip;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardManager mKeyguard;
    public final MediaOutputDialogFactory mMediaOutputDialogFactory;
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
    public SafetyWarningDialog mSafetyWarning;
    public ViewGroup mSelectedRingerContainer;
    public ImageView mSelectedRingerIcon;
    public ImageButton mSettingsIcon;
    public View mSettingsView;
    public boolean mShowA11yStream;
    public final boolean mShowActiveStreamOnly;
    public final boolean mShowLowMediaVolumeIcon;
    public boolean mShowVibrate;
    public boolean mShowing;
    public VolumeDialogController.State mState;
    public View mTopContainer;
    public final boolean mUseBackgroundBlur;
    public final VolumePanelFactory mVolumePanelFactory;
    public Window mWindow;
    public int mWindowGravity;
    public FrameLayout mZenIcon;
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
    public View mODICaptionsTooltipView = null;
    final int mVolumeRingerIconDrawableId = R.drawable.ic_speaker_on;
    final int mVolumeRingerMuteIconDrawableId = R.drawable.ic_speaker_mute;
    public final C36177 mControllerCallbackH = new VolumeDialogController.Callbacks() { // from class: com.android.systemui.volume.VolumeDialogImpl.7
        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            boolean booleanValue = bool == null ? false : bool.booleanValue();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            volumeDialogImpl.mShowA11yStream = booleanValue;
            VolumeRow activeRow = volumeDialogImpl.getActiveRow();
            if (volumeDialogImpl.mShowA11yStream || 10 != activeRow.stream) {
                volumeDialogImpl.updateRowsH(activeRow);
            } else {
                volumeDialogImpl.dismissH(7);
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            ViewStub viewStub;
            boolean booleanValue = bool.booleanValue();
            boolean booleanValue2 = bool2.booleanValue();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            ViewGroup viewGroup = volumeDialogImpl.mODICaptionsView;
            if (viewGroup != null) {
                viewGroup.setVisibility(booleanValue ? 0 : 8);
            }
            if (booleanValue) {
                volumeDialogImpl.updateCaptionsIcon();
                if (booleanValue2) {
                    if (!volumeDialogImpl.mHasSeenODICaptionsTooltip && (viewStub = volumeDialogImpl.mODICaptionsTooltipViewStub) != null) {
                        View inflate = viewStub.inflate();
                        volumeDialogImpl.mODICaptionsTooltipView = inflate;
                        inflate.findViewById(R.id.dismiss).setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda6(volumeDialogImpl, 3));
                        volumeDialogImpl.mODICaptionsTooltipViewStub = null;
                        volumeDialogImpl.rescheduleTimeoutH();
                    }
                    volumeDialogImpl.mHandler.post(new VolumeDialogImpl$$ExternalSyntheticLambda2(volumeDialogImpl, 5));
                }
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
        public final void onShowRequested(int i, boolean z, int i2) {
            VolumeDialogImpl.m2730$$Nest$mshowH(VolumeDialogImpl.this, i, z, i2);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSafetyWarning(int i) {
            final VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            if ((i & 1025) != 0 || volumeDialogImpl.mShowing) {
                synchronized (volumeDialogImpl.mSafetyWarningLock) {
                    if (volumeDialogImpl.mSafetyWarning != null) {
                        return;
                    }
                    SafetyWarningDialog safetyWarningDialog = new SafetyWarningDialog(volumeDialogImpl.mContext, volumeDialogImpl.mController.getAudioManager()) { // from class: com.android.systemui.volume.VolumeDialogImpl.5
                        @Override // com.android.systemui.volume.SafetyWarningDialog
                        public final void cleanUp() {
                            VolumeDialogImpl volumeDialogImpl2;
                            synchronized (VolumeDialogImpl.this.mSafetyWarningLock) {
                                volumeDialogImpl2 = VolumeDialogImpl.this;
                                volumeDialogImpl2.mSafetyWarning = null;
                            }
                            volumeDialogImpl2.recheckH(null);
                        }
                    };
                    volumeDialogImpl.mSafetyWarning = safetyWarningDialog;
                    safetyWarningDialog.show();
                    volumeDialogImpl.recheckH(null);
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
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Accessibility extends View.AccessibilityDelegate {
        public /* synthetic */ Accessibility(VolumeDialogImpl volumeDialogImpl, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            List<CharSequence> text = accessibilityEvent.getText();
            VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
            text.add(volumeDialogImpl.mContext.getString(R.string.volume_dialog_title, volumeDialogImpl.getStreamLabelH(volumeDialogImpl.getActiveRow().f396ss)));
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.VolumeDialogImpl$H */
    public final class HandlerC3618H extends Handler {
        public HandlerC3618H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    VolumeDialogImpl.m2730$$Nest$mshowH(volumeDialogImpl, message.arg1, volumeDialogImpl.mKeyguard.isKeyguardLocked(), VolumeDialogImpl.this.mActivityManager.getLockTaskModeState());
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
                    boolean z = message.arg2 != 0;
                    Iterator it = ((ArrayList) volumeDialogImpl4.mRows).iterator();
                    while (it.hasNext()) {
                        VolumeRow volumeRow2 = (VolumeRow) it.next();
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
                        CsdWarningDialog csdWarningDialog = volumeDialogImpl6.mCsdDialog;
                        if (csdWarningDialog != null) {
                            csdWarningDialog.dismiss();
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RingerDrawerItemClickListener implements View.OnClickListener {
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
                VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().setInterpolator(Interpolators.ACCELERATE_DECELERATE).setDuration(175L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda12(this, 1));
                if (VolumeDialogImpl.this.isLandscape()) {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationX(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                } else {
                    VolumeDialogImpl.this.mRingerDrawerNewSelectionBg.animate().translationY(VolumeDialogImpl.this.getTranslationInDrawerForRingerMode(this.mClickedRingerMode)).start();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeRow {
        public ObjectAnimator anim;
        public int animTargetProgress;
        public boolean defaultStream;
        public FrameLayout dndIcon;
        public TextView header;
        public ImageButton icon;
        public int iconMuteRes;
        public int iconRes;
        public int iconState;
        public boolean important;
        public int lastAudibleLevel;
        public TextView number;
        public int requestedLevel;
        public SeekBar slider;
        public AlphaTintDrawableWrapper sliderProgressIcon;
        public Drawable sliderProgressSolid;

        /* renamed from: ss */
        public VolumeDialogController.StreamState f396ss;
        public int stream;
        public boolean tracking;
        public long userAttempt;
        public View view;

        public /* synthetic */ VolumeRow(int i) {
            this();
        }

        private VolumeRow() {
            this.requestedLevel = -1;
            this.lastAudibleLevel = 1;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final VolumeRow mRow;

        public /* synthetic */ VolumeSeekBarChangeListener(VolumeDialogImpl volumeDialogImpl, VolumeRow volumeRow, int i) {
            this(volumeRow);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            int i2;
            if (this.mRow.f396ss == null) {
                return;
            }
            if (C3599D.BUG) {
                Log.d(VolumeDialogImpl.TAG, AudioSystem.streamToString(this.mRow.stream) + " onProgressChanged " + i + " fromUser=" + z);
            }
            if (z) {
                int i3 = this.mRow.f396ss.levelMin;
                if (i3 > 0 && i < (i2 = i3 * 100)) {
                    seekBar.setProgress(i2);
                    i = i2;
                }
                int impliedLevel = VolumeDialogImpl.getImpliedLevel(seekBar, i);
                VolumeRow volumeRow = this.mRow;
                VolumeDialogController.StreamState streamState = volumeRow.f396ss;
                if (streamState.level != impliedLevel || (streamState.muted && impliedLevel > 0)) {
                    volumeRow.userAttempt = SystemClock.uptimeMillis();
                    VolumeRow volumeRow2 = this.mRow;
                    if (volumeRow2.requestedLevel != impliedLevel) {
                        VolumeDialogImpl.this.mController.setActiveStream(volumeRow2.stream);
                        VolumeDialogImpl.this.mController.setStreamVolume(this.mRow.stream, impliedLevel);
                        VolumeRow volumeRow3 = this.mRow;
                        volumeRow3.requestedLevel = impliedLevel;
                        Events.writeEvent(9, Integer.valueOf(volumeRow3.stream), Integer.valueOf(impliedLevel));
                    }
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            if (C3599D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onStartTrackingTouch "), this.mRow.stream, VolumeDialogImpl.TAG);
            }
            VolumeDialogImpl.this.mController.setActiveStream(this.mRow.stream);
            this.mRow.tracking = true;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            if (C3599D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onStopTrackingTouch "), this.mRow.stream, VolumeDialogImpl.TAG);
            }
            VolumeRow volumeRow = this.mRow;
            volumeRow.tracking = false;
            volumeRow.userAttempt = SystemClock.uptimeMillis();
            int impliedLevel = VolumeDialogImpl.getImpliedLevel(seekBar, seekBar.getProgress());
            Events.writeEvent(16, Integer.valueOf(this.mRow.stream), Integer.valueOf(impliedLevel));
            VolumeRow volumeRow2 = this.mRow;
            if (volumeRow2.f396ss.level != impliedLevel) {
                HandlerC3618H handlerC3618H = VolumeDialogImpl.this.mHandler;
                handlerC3618H.sendMessageDelayed(handlerC3618H.obtainMessage(3, volumeRow2), 1000L);
            }
        }

        private VolumeSeekBarChangeListener(VolumeRow volumeRow) {
            this.mRow = volumeRow;
        }
    }

    /* renamed from: -$$Nest$mshowH, reason: not valid java name */
    public static void m2730$$Nest$mshowH(VolumeDialogImpl volumeDialogImpl, int i, boolean z, int i2) {
        volumeDialogImpl.getClass();
        Trace.beginSection("VolumeDialogImpl#showH");
        Log.i(TAG, "showH r=" + Events.SHOW_REASONS[i]);
        volumeDialogImpl.mHandler.removeMessages(1);
        volumeDialogImpl.mHandler.removeMessages(2);
        volumeDialogImpl.rescheduleTimeoutH();
        if (volumeDialogImpl.mConfigChanged) {
            volumeDialogImpl.initDialog(i2);
            ConfigurableTexts configurableTexts = volumeDialogImpl.mConfigurableTexts;
            ArrayMap arrayMap = configurableTexts.mTexts;
            if (!arrayMap.isEmpty()) {
                ((TextView) arrayMap.keyAt(0)).post(configurableTexts.mUpdateAll);
            }
            volumeDialogImpl.mConfigChanged = false;
        }
        volumeDialogImpl.initSettingsH(i2);
        volumeDialogImpl.mShowing = true;
        volumeDialogImpl.mIsAnimatingDismiss = false;
        volumeDialogImpl.mDialog.show();
        Events.writeEvent(0, Integer.valueOf(i), Boolean.valueOf(z));
        volumeDialogImpl.mController.notifyVisible(true);
        volumeDialogImpl.mController.getCaptionsComponentState(false);
        volumeDialogImpl.checkODICaptionsTooltip(false);
        volumeDialogImpl.updateBackgroundForDrawerClosedAmount();
        Trace.endSection();
    }

    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.volume.VolumeDialogImpl$7] */
    public VolumeDialogImpl(Context context, VolumeDialogController volumeDialogController, AccessibilityManagerWrapper accessibilityManagerWrapper, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, MediaOutputDialogFactory mediaOutputDialogFactory, VolumePanelFactory volumePanelFactory, ActivityStarter activityStarter, InteractionJankMonitor interactionJankMonitor, CsdWarningDialog.Factory factory, DevicePostureController devicePostureController, Looper looper, DumpManager dumpManager) {
        boolean z = true;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.volume_dialog_theme);
        this.mContext = contextThemeWrapper;
        this.mHandler = new HandlerC3618H(looper);
        this.mController = volumeDialogController;
        this.mKeyguard = (KeyguardManager) contextThemeWrapper.getSystemService("keyguard");
        this.mActivityManager = (ActivityManager) contextThemeWrapper.getSystemService("activity");
        this.mAccessibilityMgr = accessibilityManagerWrapper;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mConfigurationController = configurationController;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mVolumePanelFactory = volumePanelFactory;
        this.mCsdWarningDialogFactory = factory;
        this.mActivityStarter = activityStarter;
        if (!contextThemeWrapper.getPackageManager().hasSystemFeature("android.software.leanback") && !contextThemeWrapper.getPackageManager().hasSystemFeature("android.hardware.type.television")) {
            z = false;
        }
        this.mShowActiveStreamOnly = z;
        this.mHasSeenODICaptionsTooltip = Prefs.getBoolean(context, "HasSeenODICaptionsTooltip", false);
        this.mShowLowMediaVolumeIcon = contextThemeWrapper.getResources().getBoolean(R.bool.config_showLowMediaVolumeIcon);
        this.mChangeVolumeRowTintWhenInactive = contextThemeWrapper.getResources().getBoolean(R.bool.config_changeVolumeRowTintWhenInactive);
        this.mDialogShowAnimationDurationMs = contextThemeWrapper.getResources().getInteger(R.integer.config_dialogShowAnimationDurationMs);
        this.mDialogHideAnimationDurationMs = contextThemeWrapper.getResources().getInteger(R.integer.config_dialogHideAnimationDurationMs);
        boolean z2 = contextThemeWrapper.getResources().getBoolean(R.bool.config_volumeDialogUseBackgroundBlur);
        this.mUseBackgroundBlur = z2;
        this.mInteractionJankMonitor = interactionJankMonitor;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "VolumeDialogImpl", this);
        if (z2) {
            final int color = contextThemeWrapper.getColor(R.color.volume_dialog_background_color_above_blur);
            final int color2 = contextThemeWrapper.getColor(R.color.volume_dialog_background_color);
            this.mCrossWindowBlurEnabledListener = new Consumer() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
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
            this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).mCurrentDevicePosture;
            this.mDevicePostureControllerCallback = new DevicePostureController.Callback() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda1
                @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
                public final void onPostureChanged(int i) {
                    VolumeDialogImpl.this.onPostureChanged(i);
                }
            };
        }
    }

    public static int getImpliedLevel(SeekBar seekBar, int i) {
        int max = seekBar.getMax();
        int i2 = max / 100;
        int i3 = i2 - 1;
        if (i == 0) {
            return 0;
        }
        return i == max ? i2 : ((int) ((i / max) * i3)) + 1;
    }

    public final void addAccessibilityDescription(View view, int i, final String str) {
        view.setContentDescription(this.mContext.getString(i != 0 ? i != 1 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_status_vibrate : R.string.volume_ringer_status_silent));
        view.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.volume.VolumeDialogImpl.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str));
            }
        });
    }

    public final void addRow(int i, int i2, boolean z, boolean z2, int i3) {
        addRow(i, i2, i3, z, z2);
    }

    public final void checkODICaptionsTooltip(boolean z) {
        boolean z2 = this.mHasSeenODICaptionsTooltip;
        if (!z2 && !z && this.mODICaptionsTooltipViewStub != null) {
            this.mController.getCaptionsComponentState(true);
        } else if (z2 && z && this.mODICaptionsTooltipView != null) {
            hideCaptionsTooltip();
        }
    }

    public void clearInternalHandlerAfterTest() {
        HandlerC3618H handlerC3618H = this.mHandler;
        if (handlerC3618H != null) {
            handlerC3618H.removeCallbacksAndMessages(null);
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
        CustomDialog customDialog;
        Trace.beginSection("VolumeDialogImpl#dismissH");
        String str = TAG;
        Log.i(str, "mDialog.dismiss() reason: " + Events.DISMISS_REASONS[i] + " from: " + Debug.getCaller());
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(1);
        boolean z = (this.mShowing || (customDialog = this.mDialog) == null || !customDialog.isShowing()) ? false : true;
        if (z) {
            StringBuilder sb = new StringBuilder("dismissH: volume dialog possible in inconsistent state:mShowing=");
            sb.append(this.mShowing);
            sb.append(", mDialog==null?");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mDialog == null, str);
        }
        if (this.mIsAnimatingDismiss && !z) {
            Log.d(str, "dismissH: skipping dismiss because isAnimatingDismiss is true and showingStateInconsistent is false");
            Trace.endSection();
            return;
        }
        this.mIsAnimatingDismiss = true;
        this.mDialogView.animate().cancel();
        if (this.mShowing) {
            this.mShowing = false;
            Events.writeEvent(1, Integer.valueOf(i));
        }
        this.mDialogView.setTranslationX(0.0f);
        this.mDialogView.setAlpha(1.0f);
        ViewPropertyAnimator withEndAction = this.mDialogView.animate().alpha(0.0f).setDuration(this.mDialogHideAnimationDurationMs).setInterpolator(new SystemUIInterpolators$LogAccelerateInterpolator()).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(this, 4));
        if (!(this.mContext.getDisplay().getRotation() != 0)) {
            withEndAction.translationX((this.mDialogView.getWidth() * ((this.mWindowGravity & 3) == 3 ? -1 : 1)) / 2.0f);
        }
        withEndAction.setListener(new C36133(this.mDialogView, "dismiss", this.mDialogHideAnimationDurationMs)).start();
        checkODICaptionsTooltip(true);
        synchronized (this.mSafetyWarningLock) {
            if (this.mSafetyWarning != null) {
                if (C3599D.BUG) {
                    Log.d(str, "SafetyWarning dismissed");
                }
                this.mSafetyWarning.dismiss();
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
        Iterator it = ((ArrayList) this.mRows).iterator();
        while (it.hasNext()) {
            VolumeRow volumeRow = (VolumeRow) it.next();
            if (volumeRow.stream == this.mActiveStream) {
                return volumeRow;
            }
        }
        Iterator it2 = ((ArrayList) this.mRows).iterator();
        while (it2.hasNext()) {
            VolumeRow volumeRow2 = (VolumeRow) it2.next();
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
        Iterator it = ((ArrayList) this.mRows).iterator();
        int i = 0;
        while (it.hasNext()) {
            if (shouldBeVisibleH((VolumeRow) it.next(), activeRow)) {
                i++;
            }
        }
        return (this.mDialogWidth + this.mRingerRowsPadding) * (i - 1);
    }

    public int getWindowGravity() {
        return this.mWindowGravity;
    }

    public final void hideCaptionsTooltip() {
        View view = this.mODICaptionsTooltipView;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        this.mODICaptionsTooltipView.animate().cancel();
        this.mODICaptionsTooltipView.setAlpha(1.0f);
        this.mODICaptionsTooltipView.animate().alpha(0.0f).setStartDelay(0L).setDuration(this.mDialogHideAnimationDurationMs).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(this, 2)).start();
    }

    public final void hideRingerDrawer() {
        if (this.mRingerDrawerContainer != null && this.mIsRingerDrawerOpen) {
            getDrawerIconViewForMode(this.mState.ringerModeInternal).setVisibility(4);
            this.mRingerDrawerContainer.animate().alpha(0.0f).setDuration(250L).setStartDelay(0L).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(this, 7));
            if (isLandscape()) {
                this.mRingerDrawerContainer.animate().translationX(this.mRingerDrawerItemSize * 2).start();
            } else {
                this.mRingerDrawerContainer.animate().translationY(this.mRingerDrawerItemSize * 2).start();
            }
            this.mAnimateUpBackgroundToMatchDrawer.setDuration(250L);
            this.mAnimateUpBackgroundToMatchDrawer.setInterpolator(Interpolators.FAST_OUT_SLOW_IN_REVERSE);
            this.mAnimateUpBackgroundToMatchDrawer.reverse();
            this.mSelectedRingerContainer.animate().translationX(0.0f).translationY(0.0f).start();
            this.mSelectedRingerContainer.setContentDescription(this.mContext.getString(R.string.volume_ringer_change));
            this.mIsRingerDrawerOpen = false;
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void init(int i, VolumeDialog.Callback callback) {
        initDialog(this.mActivityManager.getLockTaskModeState());
        this.mController.addCallback(this.mControllerCallbackH, this.mHandler);
        this.mController.getState();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        DevicePostureController devicePostureController = this.mDevicePostureController;
        if (devicePostureController != null) {
            ((DevicePostureControllerImpl) devicePostureController).addCallback(this.mDevicePostureControllerCallback);
        }
    }

    public final void initDialog(int i) {
        Log.d(TAG, "initDialog: called!");
        this.mDialog = new CustomDialog(this.mContext);
        initDimens();
        this.mConfigurableTexts = new ConfigurableTexts(this.mContext);
        int i2 = 0;
        this.mHovering = false;
        this.mShowing = false;
        Window window = this.mDialog.getWindow();
        this.mWindow = window;
        int i3 = 1;
        window.requestFeature(1);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(65538);
        this.mWindow.addFlags(android.R.interpolator.launch_task_micro_alpha);
        this.mWindow.addPrivateFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
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
        this.mDialog.setContentView(R.layout.volume_dialog);
        ViewGroup viewGroup = (ViewGroup) this.mDialog.findViewById(R.id.volume_dialog);
        this.mDialogView = viewGroup;
        viewGroup.setAlpha(0.0f);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                volumeDialogImpl.mDialogView.getViewTreeObserver().addOnComputeInternalInsetsListener(volumeDialogImpl);
                if (!(volumeDialogImpl.mContext.getDisplay().getRotation() != 0)) {
                    volumeDialogImpl.mDialogView.setTranslationX((r9.getWidth() * ((volumeDialogImpl.mWindowGravity & 3) == 3 ? -1 : 1)) / 2.0f);
                }
                volumeDialogImpl.mDialogView.setAlpha(0.0f);
                volumeDialogImpl.mDialogView.animate().alpha(1.0f).translationX(0.0f).setDuration(volumeDialogImpl.mDialogShowAnimationDurationMs).setListener(volumeDialogImpl.new C36133(volumeDialogImpl.mDialogView, "show", 3000L)).setInterpolator(new SystemUIInterpolators$LogDecelerateInterpolator()).withEndAction(new VolumeDialogImpl$$ExternalSyntheticLambda2(volumeDialogImpl, 3)).start();
            }
        });
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                volumeDialogImpl.mDialogView.getViewTreeObserver().removeOnComputeInternalInsetsListener(volumeDialogImpl);
            }
        });
        this.mDialogView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda5
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
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
        View findViewById = this.mDialogView.findViewById(R.id.volume_ringer_and_drawer_container);
        this.mRingerAndDrawerContainer = findViewById;
        if (findViewById != null) {
            if (isLandscape()) {
                View view = this.mRingerAndDrawerContainer;
                view.setPadding(view.getPaddingLeft(), this.mRingerAndDrawerContainer.getPaddingTop(), this.mRingerAndDrawerContainer.getPaddingRight(), this.mRingerRowsPadding);
                this.mRingerAndDrawerContainer.setBackgroundDrawable(this.mContext.getDrawable(R.drawable.volume_background_top_rounded));
            }
            this.mRingerAndDrawerContainer.post(new VolumeDialogImpl$$ExternalSyntheticLambda2(this, i3));
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mDialog.findViewById(R.id.ringer);
        this.mRinger = viewGroup2;
        if (viewGroup2 != null) {
            this.mRingerIcon = (ImageButton) viewGroup2.findViewById(R.id.ringer_icon);
            this.mZenIcon = (FrameLayout) this.mRinger.findViewById(R.id.dnd_icon);
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
            this.mSelectedRingerContainer.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda6(this, 2));
            this.mRingerDrawerVibrate.setOnClickListener(new RingerDrawerItemClickListener(1));
            this.mRingerDrawerMute.setOnClickListener(new RingerDrawerItemClickListener(0));
            this.mRingerDrawerNormal.setOnClickListener(new RingerDrawerItemClickListener(2));
            final int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(android.R.attr.colorAccent, this.mContext, 0);
            final int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(android.R.attr.colorBackgroundFloating, this.mContext, 0);
            this.mRingerDrawerIconColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                    int i4 = colorAttrDefaultColor2;
                    int i5 = colorAttrDefaultColor;
                    volumeDialogImpl.getClass();
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(floatValue, Integer.valueOf(i4), Integer.valueOf(i5))).intValue();
                    int intValue2 = ((Integer) ArgbEvaluator.getInstance().evaluate(floatValue, Integer.valueOf(i5), Integer.valueOf(i4))).intValue();
                    volumeDialogImpl.mRingerDrawerIconAnimatingDeselected.setColorFilter(intValue);
                    volumeDialogImpl.mRingerDrawerIconAnimatingSelected.setColorFilter(intValue2);
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
            this.mAnimateUpBackgroundToMatchDrawer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
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
                addRow(4, R.drawable.ic_alarm, true, false, R.drawable.ic_volume_alarm_mute);
                addRow(0, android.R.drawable.ic_perm_group_camera, false, false, android.R.drawable.ic_perm_group_camera);
                addRow(6, R.drawable.ic_volume_bt_sco, false, false, R.drawable.ic_volume_bt_sco);
                addRow(1, R.drawable.ic_volume_system, false, false, R.drawable.ic_volume_system_mute);
            }
        } else {
            int size = ((ArrayList) this.mRows).size();
            for (int i4 = 0; i4 < size; i4++) {
                VolumeRow volumeRow = (VolumeRow) ((ArrayList) this.mRows).get(i4);
                initRow(volumeRow, volumeRow.stream, volumeRow.iconRes, volumeRow.iconMuteRes, volumeRow.important, volumeRow.defaultStream);
                this.mDialogRowsView.addView(volumeRow.view);
                updateVolumeRowH(volumeRow);
            }
        }
        updateRowsH(getActiveRow());
        ImageButton imageButton = this.mRingerIcon;
        if (imageButton != null) {
            imageButton.setAccessibilityLiveRegion(1);
            this.mRingerIcon.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda6(this, i2));
        }
        updateRingerH();
        initSettingsH(i);
        CaptionsToggleImageButton captionsToggleImageButton = this.mODICaptionsIcon;
        if (captionsToggleImageButton != null) {
            VolumeDialogImpl$$ExternalSyntheticLambda9 volumeDialogImpl$$ExternalSyntheticLambda9 = new VolumeDialogImpl$$ExternalSyntheticLambda9(this);
            HandlerC3618H handlerC3618H = this.mHandler;
            captionsToggleImageButton.mConfirmedTapListener = volumeDialogImpl$$ExternalSyntheticLambda9;
            if (captionsToggleImageButton.mGestureDetector == null) {
                captionsToggleImageButton.mGestureDetector = new GestureDetector(captionsToggleImageButton.getContext(), captionsToggleImageButton.mGestureListener, handlerC3618H);
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
        boolean hasVibrator = this.mController.hasVibrator();
        this.mShowVibrate = hasVibrator;
        this.mRingerCount = hasVibrator ? 3 : 2;
    }

    public final void initRow(final VolumeRow volumeRow, final int i, int i2, int i3, boolean z, boolean z2) {
        volumeRow.stream = i;
        volumeRow.iconRes = i2;
        volumeRow.iconMuteRes = i3;
        volumeRow.important = z;
        volumeRow.defaultStream = z2;
        View inflate = this.mDialog.getLayoutInflater().inflate(R.layout.volume_dialog_row, (ViewGroup) null);
        volumeRow.view = inflate;
        inflate.setId(volumeRow.stream);
        volumeRow.view.setTag(volumeRow);
        TextView textView = (TextView) volumeRow.view.findViewById(R.id.volume_row_header);
        volumeRow.header = textView;
        textView.setId(volumeRow.stream * 20);
        if (i == 10) {
            volumeRow.header.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        }
        volumeRow.dndIcon = (FrameLayout) volumeRow.view.findViewById(R.id.dnd_icon);
        SeekBar seekBar = (SeekBar) volumeRow.view.findViewById(R.id.volume_row_slider);
        volumeRow.slider = seekBar;
        seekBar.setOnSeekBarChangeListener(new VolumeSeekBarChangeListener(this, volumeRow, 0));
        volumeRow.number = (TextView) volumeRow.view.findViewById(R.id.volume_number);
        volumeRow.anim = null;
        LayerDrawable layerDrawable = (LayerDrawable) this.mContext.getDrawable(R.drawable.volume_row_seekbar);
        LayerDrawable layerDrawable2 = (LayerDrawable) ((RoundedCornerProgressDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress)).getDrawable();
        volumeRow.sliderProgressSolid = layerDrawable2.findDrawableByLayerId(R.id.volume_seekbar_progress_solid);
        Drawable findDrawableByLayerId = layerDrawable2.findDrawableByLayerId(R.id.volume_seekbar_progress_icon);
        volumeRow.sliderProgressIcon = findDrawableByLayerId != null ? (AlphaTintDrawableWrapper) ((RotateDrawable) findDrawableByLayerId).getDrawable() : null;
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
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        VolumeDialogImpl volumeDialogImpl = VolumeDialogImpl.this;
                        VolumeDialogImpl.VolumeRow volumeRow2 = volumeRow;
                        int i4 = i;
                        volumeDialogImpl.getClass();
                        Events.writeEvent(7, Integer.valueOf(volumeRow2.stream), Integer.valueOf(volumeRow2.iconState));
                        volumeDialogImpl.mController.setActiveStream(volumeRow2.stream);
                        if (volumeRow2.stream == 2) {
                            boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
                            if (volumeDialogImpl.mState.ringerModeInternal != 2) {
                                volumeDialogImpl.mController.setRingerMode(2, false);
                                if (volumeRow2.f396ss.level == 0) {
                                    volumeDialogImpl.mController.setStreamVolume(i4, 1);
                                }
                            } else if (hasVibrator) {
                                volumeDialogImpl.mController.setRingerMode(1, false);
                            } else {
                                volumeDialogImpl.mController.setStreamVolume(i4, volumeRow2.f396ss.level == 0 ? volumeRow2.lastAudibleLevel : 0);
                            }
                        } else {
                            VolumeDialogController.StreamState streamState = volumeRow2.f396ss;
                            int i5 = streamState.level;
                            int i6 = streamState.levelMin;
                            boolean z3 = i5 == i6;
                            VolumeDialogController volumeDialogController = volumeDialogImpl.mController;
                            if (z3) {
                                i6 = volumeRow2.lastAudibleLevel;
                            }
                            volumeDialogController.setStreamVolume(i4, i6);
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
            imageButton.setOnClickListener(new VolumeDialogImpl$$ExternalSyntheticLambda6(this, 1));
        }
    }

    public final boolean isLandscape() {
        return this.mContext.getResources().getConfiguration().orientation == 2;
    }

    public final boolean isRtl() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1;
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

    public final void onStateChangedH(VolumeDialogController.State state) {
        VolumeRow volumeRow;
        int i;
        int i2;
        if (C3599D.BUG) {
            Log.d(TAG, "onStateChangedH() state: " + state.toString());
        }
        VolumeDialogController.State state2 = this.mState;
        if (state2 != null && state != null && (i = state2.ringerModeInternal) != -1 && i != (i2 = state.ringerModeInternal) && i2 == 1) {
            this.mController.vibrate(VibrationEffect.get(1));
        }
        this.mState = state;
        this.mDynamic.clear();
        for (int i3 = 0; i3 < state.states.size(); i3++) {
            int keyAt = state.states.keyAt(i3);
            if (state.states.valueAt(i3).dynamic) {
                this.mDynamic.put(keyAt, true);
                Iterator it = ((ArrayList) this.mRows).iterator();
                while (true) {
                    if (it.hasNext()) {
                        volumeRow = (VolumeRow) it.next();
                        if (volumeRow.stream == keyAt) {
                            break;
                        }
                    } else {
                        volumeRow = null;
                        break;
                    }
                }
                if (volumeRow == null) {
                    addRow(keyAt, R.drawable.ic_volume_remote, R.drawable.ic_volume_remote_mute, true, false);
                }
            }
        }
        int i4 = this.mActiveStream;
        int i5 = state.activeStream;
        if (i4 != i5) {
            this.mPrevActiveStream = i4;
            this.mActiveStream = i5;
            updateRowsH(getActiveRow());
            if (this.mShowing) {
                rescheduleTimeoutH();
            }
        }
        Iterator it2 = ((ArrayList) this.mRows).iterator();
        while (it2.hasNext()) {
            updateVolumeRowH((VolumeRow) it2.next());
        }
        updateRingerH();
        this.mWindow.setTitle(this.mContext.getString(R.string.volume_dialog_title, getStreamLabelH(getActiveRow().f396ss)));
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        this.mContext.getTheme().applyStyle(this.mContext.getThemeResId(), true);
    }

    public final void recheckH(VolumeRow volumeRow) {
        if (volumeRow != null) {
            if (C3599D.BUG) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("recheckH "), volumeRow.stream, TAG);
            }
            updateVolumeRowH(volumeRow);
            return;
        }
        if (C3599D.BUG) {
            Log.d(TAG, "recheckH ALL");
        }
        trimObsoleteH();
        Iterator it = ((ArrayList) this.mRows).iterator();
        while (it.hasNext()) {
            updateVolumeRowH((VolumeRow) it.next());
        }
    }

    public final void rescheduleTimeoutH() {
        this.mHandler.removeMessages(2);
        int recommendedTimeoutMillis = this.mHovering ? this.mAccessibilityMgr.getRecommendedTimeoutMillis(VolumePanelState.DIALOG_HOVERING_TIMEOUT_MILLIS, 4) : this.mSafetyWarning != null ? this.mAccessibilityMgr.getRecommendedTimeoutMillis(5000, 6) : (this.mHasSeenODICaptionsTooltip || this.mODICaptionsTooltipView == null) ? this.mAccessibilityMgr.getRecommendedTimeoutMillis(3000, 4) : this.mAccessibilityMgr.getRecommendedTimeoutMillis(5000, 6);
        HandlerC3618H handlerC3618H = this.mHandler;
        handlerC3618H.sendMessageDelayed(handlerC3618H.obtainMessage(2, 3, 0), recommendedTimeoutMillis);
        String str = TAG;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("rescheduleTimeout ", recommendedTimeoutMillis, " ");
        m1m.append(Debug.getCaller());
        Log.i(str, m1m.toString());
        this.mController.userActivity();
    }

    public final void setRingerMode(int i) {
        VibrationEffect vibrationEffect;
        Events.writeEvent(18, Integer.valueOf(i));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Secure.putInt(contentResolver, "manual_ringer_toggle_count", Settings.Secure.getInt(contentResolver, "manual_ringer_toggle_count", 0) + 1);
        updateRingerH();
        String str = null;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    vibrationEffect = VibrationEffect.get(1);
                } else {
                    this.mController.scheduleTouchFeedback();
                }
            }
            vibrationEffect = null;
        } else {
            vibrationEffect = VibrationEffect.get(0);
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
            str = this.mContext.getString(17043227);
        } else if (i != 2) {
            str = this.mContext.getString(17043228);
        } else {
            if (this.mState.states.get(2) != null) {
                str = this.mContext.getString(R.string.volume_dialog_ringer_guidance_ring, NumberFormat.getPercentInstance().format(r11.level / r11.levelMax));
            }
        }
        Toast.makeText(this.mContext, str, 0).show();
        Prefs.putInt(this.mContext, "RingerGuidanceCount", i2 + 1);
    }

    public final boolean shouldBeVisibleH(VolumeRow volumeRow, VolumeRow volumeRow2) {
        int i = volumeRow.stream;
        int i2 = volumeRow2.stream;
        if (i == i2) {
            return true;
        }
        if (this.mShowActiveStreamOnly) {
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
        return false;
    }

    public void showCsdWarningH(int i, int i2) {
        synchronized (this.mSafetyWarningLock) {
            if (this.mCsdDialog != null) {
                return;
            }
            CsdWarningDialog create = this.mCsdWarningDialogFactory.create(i, new VolumeDialogImpl$$ExternalSyntheticLambda2(this, 0));
            this.mCsdDialog = create;
            create.show();
            recheckH(null);
            if (i2 > 0) {
                this.mHandler.removeMessages(8);
                HandlerC3618H handlerC3618H = this.mHandler;
                handlerC3618H.sendMessageDelayed(handlerC3618H.obtainMessage(8, 10, 0), i2);
                String str = TAG;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("scheduleCsdTimeoutH ", i2, "ms ");
                m1m.append(Debug.getCaller());
                Log.i(str, m1m.toString());
                this.mController.userActivity();
            }
            rescheduleTimeoutH();
        }
    }

    public final void trimObsoleteH() {
        if (C3599D.BUG) {
            Log.d(TAG, "trimObsoleteH");
        }
        for (int size = ((ArrayList) this.mRows).size() - 1; size >= 0; size--) {
            VolumeRow volumeRow = (VolumeRow) ((ArrayList) this.mRows).get(size);
            VolumeDialogController.StreamState streamState = volumeRow.f396ss;
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
        float f = iArr[0];
        float f2 = iArr[1];
        if (view == this.mTopContainer && !this.mIsRingerDrawerOpen) {
            if (!isLandscape()) {
                f2 += getRingerDrawerOpenExtraSize();
            } else if (getRingerDrawerOpenExtraSize() > getVisibleRowsExtraSize()) {
                f += getRingerDrawerOpenExtraSize() - getVisibleRowsExtraSize();
            }
        }
        this.mTouchableRegion.op((int) f, (int) f2, view.getWidth() + iArr[0], view.getHeight() + iArr[1], Region.Op.UNION);
    }

    public final void updateBackgroundForDrawerClosedAmount() {
        Drawable drawable = this.mRingerAndDrawerContainerBackground;
        if (drawable == null) {
            return;
        }
        Rect copyBounds = drawable.copyBounds();
        if (isLandscape()) {
            copyBounds.left = (int) (this.mRingerDrawerClosedAmount * getRingerDrawerOpenExtraSize());
        } else {
            copyBounds.top = (int) (this.mRingerDrawerClosedAmount * getRingerDrawerOpenExtraSize());
        }
        this.mRingerAndDrawerContainerBackground.setBounds(copyBounds);
    }

    public final void updateCaptionsIcon() {
        boolean areCaptionsEnabled = this.mController.areCaptionsEnabled();
        final CaptionsToggleImageButton captionsToggleImageButton = this.mODICaptionsIcon;
        if (captionsToggleImageButton.mCaptionsEnabled != areCaptionsEnabled) {
            HandlerC3618H handlerC3618H = this.mHandler;
            captionsToggleImageButton.mCaptionsEnabled = areCaptionsEnabled;
            ViewCompat.replaceAccessibilityAction(captionsToggleImageButton, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, areCaptionsEnabled ? captionsToggleImageButton.getContext().getString(R.string.volume_odi_captions_hint_disable) : captionsToggleImageButton.getContext().getString(R.string.volume_odi_captions_hint_enable), new AccessibilityViewCommand() { // from class: com.android.systemui.volume.CaptionsToggleImageButton$$ExternalSyntheticLambda0
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view) {
                    int i = CaptionsToggleImageButton.$r8$clinit;
                    return CaptionsToggleImageButton.this.tryToSendTapConfirmedEvent();
                }
            });
            handlerC3618H.post(captionsToggleImageButton.setImageResourceAsync(captionsToggleImageButton.mCaptionsEnabled ? R.drawable.ic_volume_odi_captions : R.drawable.ic_volume_odi_captions_disabled));
        }
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
        FrameLayout frameLayout = this.mZenIcon;
        if (frameLayout != null) {
            frameLayout.setVisibility(z2 ? 8 : 0);
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
            this.mRingerIcon.setImageResource(R.drawable.ic_volume_ringer_vibrate);
            this.mSelectedRingerIcon.setImageResource(R.drawable.ic_volume_ringer_vibrate);
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
        if (C3599D.BUG) {
            Log.d(TAG, "updateRowsH");
        }
        if (!this.mShowing) {
            trimObsoleteH();
        }
        int i = !isRtl() ? -1 : 32767;
        Iterator it = ((ArrayList) this.mRows).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            VolumeRow volumeRow2 = (VolumeRow) it.next();
            boolean z = volumeRow2 == volumeRow;
            boolean shouldBeVisibleH = shouldBeVisibleH(volumeRow2, volumeRow);
            View view = volumeRow2.view;
            if (view != null) {
                if ((view.getVisibility() == 0) != shouldBeVisibleH) {
                    view.setVisibility(shouldBeVisibleH ? 0 : 8);
                }
            }
            if (shouldBeVisibleH && this.mRingerAndDrawerContainerBackground != null) {
                i = !isRtl() ? Math.max(i, this.mDialogRowsView.indexOfChild(volumeRow2.view)) : Math.min(i, this.mDialogRowsView.indexOfChild(volumeRow2.view));
                ViewGroup.LayoutParams layoutParams = volumeRow2.view.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                    if (isRtl()) {
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
        if (i > -1 && i < 32767) {
            View childAt = this.mDialogRowsView.getChildAt(i);
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

    public final void updateVolumeRowH(VolumeRow volumeRow) {
        VolumeDialogController.StreamState streamState;
        int i;
        int i2;
        int i3;
        boolean z = C3599D.BUG;
        if (z) {
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("updateVolumeRowH s="), volumeRow.stream, TAG);
        }
        VolumeDialogController.State state = this.mState;
        if (state == null || (streamState = state.states.get(volumeRow.stream)) == null) {
            return;
        }
        volumeRow.f396ss = streamState;
        int i4 = streamState.level;
        if (i4 > 0) {
            volumeRow.lastAudibleLevel = i4;
        }
        if (i4 == volumeRow.requestedLevel) {
            volumeRow.requestedLevel = -1;
        }
        int i5 = volumeRow.stream;
        boolean z2 = true;
        boolean z3 = i5 == 0;
        boolean z4 = i5 == 10;
        boolean z5 = i5 == 2;
        boolean z6 = i5 == 1;
        boolean z7 = i5 == 4;
        boolean z8 = i5 == 3;
        boolean z9 = z5 && this.mState.ringerModeInternal == 1;
        boolean z10 = z5 && this.mState.ringerModeInternal == 0;
        VolumeDialogController.State state2 = this.mState;
        int i6 = state2.zenMode;
        boolean z11 = i6 == 1;
        if (i6 == 3) {
            i = 2;
        } else {
            i = 2;
            z2 = false;
        }
        boolean z12 = !z2 ? !(i6 == i) ? z11 && ((z7 && state2.disallowAlarms) || ((z8 && state2.disallowMedia) || ((z5 && state2.disallowRinger) || (z6 && state2.disallowSystem)))) : z5 || z6 || z7 || z8 : !(z5 || z6);
        int i7 = streamState.levelMax * 100;
        if (i7 != volumeRow.slider.getMax()) {
            volumeRow.slider.setMax(i7);
        }
        int i8 = streamState.levelMin * 100;
        if (i8 != volumeRow.slider.getMin()) {
            volumeRow.slider.setMin(i8);
        }
        TextView textView = volumeRow.header;
        String streamLabelH = getStreamLabelH(streamState);
        CharSequence text = textView.getText();
        String str = null;
        if (text == null || text.length() == 0) {
            text = null;
        }
        if (streamLabelH != null && streamLabelH.length() != 0) {
            str = streamLabelH;
        }
        if (!Objects.equals(text, str)) {
            textView.setText(streamLabelH);
        }
        volumeRow.slider.setContentDescription(volumeRow.header.getText());
        ConfigurableTexts configurableTexts = this.mConfigurableTexts;
        TextView textView2 = volumeRow.header;
        int i9 = streamState.name;
        if (textView2 == null) {
            configurableTexts.getClass();
        } else {
            ArrayMap arrayMap = configurableTexts.mTexts;
            if (arrayMap.containsKey(textView2)) {
                ((Integer) arrayMap.get(textView2)).intValue();
            } else {
                Resources resources = configurableTexts.mContext.getResources();
                int textSize = (int) ((textView2.getTextSize() / resources.getConfiguration().fontScale) / resources.getDisplayMetrics().density);
                arrayMap.put(textView2, Integer.valueOf(textSize));
                textView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.volume.ConfigurableTexts.1
                    public final /* synthetic */ int val$sp;
                    public final /* synthetic */ TextView val$text;

                    public ViewOnAttachStateChangeListenerC35961(TextView textView22, int textSize2) {
                        r2 = textView22;
                        r3 = textSize2;
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view) {
                        ConfigurableTexts configurableTexts2 = ConfigurableTexts.this;
                        TextView textView3 = r2;
                        int i10 = r3;
                        configurableTexts2.getClass();
                        textView3.setTextSize(2, i10);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view) {
                    }
                });
                configurableTexts.mTextLabels.put(textView22, Integer.valueOf(i9));
            }
        }
        boolean z13 = this.mAutomute;
        boolean z14 = (z13 || streamState.muteSupported) && !z12;
        if (z9) {
            i2 = R.drawable.ic_volume_ringer_vibrate;
        } else if (z10 || z12) {
            i2 = volumeRow.iconMuteRes;
        } else if (!streamState.routedToBluetooth) {
            i2 = (z13 && streamState.level == 0) || streamState.muted ? streamState.muted ? R.drawable.ic_volume_media_off : volumeRow.iconMuteRes : (!this.mShowLowMediaVolumeIcon || streamState.level * 2 >= streamState.levelMax + streamState.levelMin) ? volumeRow.iconRes : R.drawable.ic_volume_media_low;
        } else if (z3) {
            i2 = R.drawable.ic_volume_bt_sco;
        } else {
            i2 = (z13 && streamState.level == 0) || streamState.muted ? R.drawable.ic_volume_media_bt_mute : R.drawable.ic_volume_media_bt;
        }
        Resources.Theme theme = this.mContext.getTheme();
        ImageButton imageButton = volumeRow.icon;
        if (imageButton != null) {
            imageButton.setImageResource(i2);
        }
        AlphaTintDrawableWrapper alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
        if (alphaTintDrawableWrapper != null) {
            alphaTintDrawableWrapper.setDrawable(volumeRow.view.getResources().getDrawable(i2, theme));
        }
        volumeRow.iconState = i2 == R.drawable.ic_volume_ringer_vibrate ? 3 : (i2 == R.drawable.ic_volume_media_bt_mute || i2 == volumeRow.iconMuteRes) ? 2 : (i2 == R.drawable.ic_volume_media_bt || i2 == volumeRow.iconRes || i2 == R.drawable.ic_volume_media_low) ? 1 : 0;
        ImageButton imageButton2 = volumeRow.icon;
        if (imageButton2 != null) {
            if (z14) {
                int i10 = R.string.volume_stream_content_description_mute_a11y;
                if (z5) {
                    if (z9) {
                        imageButton2.setContentDescription(this.mContext.getString(R.string.volume_stream_content_description_unmute, getStreamLabelH(streamState)));
                    } else if (this.mController.hasVibrator()) {
                        volumeRow.icon.setContentDescription(this.mContext.getString(this.mShowA11yStream ? R.string.volume_stream_content_description_vibrate_a11y : R.string.volume_stream_content_description_vibrate, getStreamLabelH(streamState)));
                    } else {
                        ImageButton imageButton3 = volumeRow.icon;
                        Context context = this.mContext;
                        if (!this.mShowA11yStream) {
                            i10 = R.string.volume_stream_content_description_mute;
                        }
                        imageButton3.setContentDescription(context.getString(i10, getStreamLabelH(streamState)));
                    }
                } else if (z4) {
                    imageButton2.setContentDescription(getStreamLabelH(streamState));
                } else if (streamState.muted || (this.mAutomute && streamState.level == 0)) {
                    imageButton2.setContentDescription(this.mContext.getString(R.string.volume_stream_content_description_unmute, getStreamLabelH(streamState)));
                } else {
                    Context context2 = this.mContext;
                    if (!this.mShowA11yStream) {
                        i10 = R.string.volume_stream_content_description_mute;
                    }
                    imageButton2.setContentDescription(context2.getString(i10, getStreamLabelH(streamState)));
                }
            } else {
                imageButton2.setContentDescription(getStreamLabelH(streamState));
            }
        }
        if (z12) {
            volumeRow.tracking = false;
        }
        boolean z15 = !z12;
        volumeRow.dndIcon.setVisibility(z15 ^ true ? 0 : 8);
        VolumeDialogController.StreamState streamState2 = volumeRow.f396ss;
        int i11 = (!streamState2.muted || z5 || z12) ? streamState2.level : 0;
        Trace.beginSection("VolumeDialogImpl#updateVolumeRowSliderH");
        volumeRow.slider.setEnabled(z15);
        updateVolumeRowTintH(volumeRow, volumeRow.stream == this.mActiveStream);
        if (!volumeRow.tracking) {
            int progress = volumeRow.slider.getProgress();
            int impliedLevel = getImpliedLevel(volumeRow.slider, progress);
            boolean z16 = volumeRow.view.getVisibility() == 0;
            boolean z17 = SystemClock.uptimeMillis() - volumeRow.userAttempt < 1000;
            this.mHandler.removeMessages(3, volumeRow);
            boolean z18 = this.mShowing;
            if (z18 && z16 && z17) {
                if (z) {
                    Log.d(TAG, "inGracePeriod");
                }
                HandlerC3618H handlerC3618H = this.mHandler;
                handlerC3618H.sendMessageAtTime(handlerC3618H.obtainMessage(3, volumeRow), volumeRow.userAttempt + 1000);
            } else if ((i11 != impliedLevel || !z18 || !z16) && progress != (i3 = i11 * 100)) {
                if (z18 && z16) {
                    ObjectAnimator objectAnimator = volumeRow.anim;
                    if (objectAnimator == null || !objectAnimator.isRunning() || volumeRow.animTargetProgress != i3) {
                        ObjectAnimator objectAnimator2 = volumeRow.anim;
                        if (objectAnimator2 == null) {
                            ObjectAnimator ofInt = ObjectAnimator.ofInt(volumeRow.slider, "progress", progress, i3);
                            volumeRow.anim = ofInt;
                            ofInt.setInterpolator(new DecelerateInterpolator());
                            volumeRow.anim.addListener(new C36133(volumeRow.view, "update", 80L));
                        } else {
                            objectAnimator2.cancel();
                            volumeRow.anim.setIntValues(progress, i3);
                        }
                        volumeRow.animTargetProgress = i3;
                        volumeRow.anim.setDuration(80L);
                        volumeRow.anim.start();
                    }
                } else {
                    ObjectAnimator objectAnimator3 = volumeRow.anim;
                    if (objectAnimator3 != null) {
                        objectAnimator3.cancel();
                    }
                    volumeRow.slider.setProgress(i3, true);
                }
            }
        }
        Trace.endSection();
        TextView textView3 = volumeRow.number;
        if (textView3 != null) {
            textView3.setText(Integer.toString(i11));
        }
    }

    public final void updateVolumeRowTintH(VolumeRow volumeRow, boolean z) {
        int i;
        if (z) {
            volumeRow.slider.requestFocus();
        }
        boolean z2 = z && volumeRow.slider.isEnabled();
        if (z2 || this.mChangeVolumeRowTintWhenInactive) {
            ColorStateList colorAttr = z2 ? Utils.getColorAttr(android.R.attr.colorAccent, this.mContext) : Utils.getColorAttr(android.R.^attr-private.colorAccentSecondary, this.mContext);
            if (z2) {
                i = Color.alpha(colorAttr.getDefaultColor());
            } else {
                TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{android.R.attr.secondaryContentAlpha});
                float f = obtainStyledAttributes.getFloat(0, 0.0f);
                obtainStyledAttributes.recycle();
                i = (int) (f * 255.0f);
            }
            ColorStateList colorAttr2 = Utils.getColorAttr(android.R.attr.colorBackgroundFloating, this.mContext);
            ColorStateList colorAttr3 = Utils.getColorAttr(17957230, this.mContext);
            volumeRow.sliderProgressSolid.setTintList(colorAttr);
            AlphaTintDrawableWrapper alphaTintDrawableWrapper = volumeRow.sliderProgressIcon;
            if (alphaTintDrawableWrapper != null) {
                alphaTintDrawableWrapper.setTintList(colorAttr2);
            }
            ImageButton imageButton = volumeRow.icon;
            if (imageButton != null) {
                imageButton.setImageTintList(colorAttr3);
                volumeRow.icon.setImageAlpha(i);
            }
            TextView textView = volumeRow.number;
            if (textView != null) {
                textView.setTextColor(colorAttr);
                volumeRow.number.setAlpha(i);
            }
        }
    }

    public final void addRow(int i, int i2, int i3, boolean z, boolean z2) {
        if (C3599D.BUG) {
            Slog.d(TAG, "Adding row for stream " + i);
        }
        VolumeRow volumeRow = new VolumeRow(0);
        initRow(volumeRow, i, i2, i3, z, z2);
        this.mDialogRowsView.addView(volumeRow.view);
        ((ArrayList) this.mRows).add(volumeRow);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.VolumeDialogImpl$3 */
    public final class C36133 implements Animator.AnimatorListener {
        public final /* synthetic */ long val$timeout;
        public final /* synthetic */ String val$type;
        public final /* synthetic */ View val$v;

        public C36133(View view, String str, long j) {
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
            } else if (C3599D.BUG) {
                Log.d(VolumeDialogImpl.TAG, "onAnimationStart view do not attached to window:" + this.val$v);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
