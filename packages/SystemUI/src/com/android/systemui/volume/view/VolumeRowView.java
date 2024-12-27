package com.android.systemui.volume.view;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.SemSystemProperties;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.icon.VPVolumeIcon;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Result;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public final class VolumeRowView extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public ImageView bluetoothDeviceIcon;
    public Runnable buttonAnimatorRunnable;
    public int earProtectLevel;
    public HandlerWrapper handlerWrapper;
    public VPVolumeIcon icon;
    public boolean isAODEnabled;
    public boolean isDualViewEnabled;
    public boolean isIconClickable;
    public String label;
    public final SpringAnimation progressBarSpring;
    public final VolumeRowView$recheckCallback$1 recheckCallback;
    public VolumeSeekBar seekBar;
    public ViewGroup seekBarBackground;
    public int springFinalPosition;
    public boolean startProgress;
    public final StoreInteractor storeInteractor;
    public int stream;
    public SpringAnimation touchDownAnimation;
    public boolean touchDownIcon;
    public SpringAnimation touchUpAnimation;
    public VolumePanelMotion volumePanelMotion;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SET_STREAM_VOLUME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR_LATER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STOP_SLIDER_TRACKING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SMART_VIEW_SEEKBAR_TOUCHED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VolumeRowView(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.progressBarSpring = new SpringAnimation(new FloatValueHolder());
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.VolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = VolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(VolumeRowView.this.stream);
                VolumeSeekBar volumeSeekBar = VolumeRowView.this.seekBar;
                if (volumeSeekBar == null) {
                    volumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(volumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = VolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
    }

    public final void animateSeekBarButton(VolumePanelState volumePanelState, final boolean z) {
        if (volumePanelState.getStream() == this.stream) {
            VolumeSeekBar volumeSeekBar = this.seekBar;
            if (volumeSeekBar == null) {
                volumeSeekBar = null;
            }
            final SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) volumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            handlerWrapper.remove(this.buttonAnimatorRunnable);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.VolumeRowView$animateSeekBarButton$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecRoundedCornerSeekBarDrawable.this.animateButton(z);
                }
            };
            this.buttonAnimatorRunnable = runnable;
            if (z) {
                HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                (handlerWrapper2 != null ? handlerWrapper2 : null).post(runnable);
            } else {
                HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                (handlerWrapper3 != null ? handlerWrapper3 : null).postDelayed(runnable, 500L);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            VPVolumeIcon vPVolumeIcon = this.icon;
            if (vPVolumeIcon == null) {
                vPVolumeIcon = null;
            }
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            viewUtil.getClass();
            if (ViewUtil.isTouched(vPVolumeIcon, rawX, rawY)) {
                this.touchDownIcon = true;
                ViewGroup viewGroup = this.seekBarBackground;
                if (!ViewUtil.isTouched(viewGroup != null ? viewGroup : null, motionEvent.getRawX(), motionEvent.getRawY())) {
                    return true;
                }
            } else if (VolumePanelValues.isSmartView(this.stream)) {
                this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SMART_VIEW_SEEKBAR_TOUCHED).isFromOutside(true).stream(this.stream).build(), false);
            }
        } else if (action == 1) {
            if (isIconClicked(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(this.stream).isFromOutside(true).build(), false);
            }
            this.touchDownIcon = false;
            this.startProgress = false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void initialize(VolumePanelStore volumePanelStore, HandlerWrapper handlerWrapper, VolumePanelRow volumePanelRow, VolumePanelState volumePanelState, VolumePanelMotion volumePanelMotion) {
        Object failure;
        String str;
        StoreInteractor storeInteractor = this.storeInteractor;
        storeInteractor.store = volumePanelStore;
        storeInteractor.observeStore();
        this.handlerWrapper = handlerWrapper;
        this.stream = volumePanelRow.getStreamType();
        this.volumePanelMotion = volumePanelMotion;
        VolumeSeekBar volumeSeekBar = (VolumeSeekBar) requireViewById(com.android.systemui.R.id.volume_seekbar);
        this.seekBar = volumeSeekBar;
        volumeSeekBar.stream = this.stream;
        volumeSeekBar.isTracking = false;
        StoreInteractor storeInteractor2 = volumeSeekBar.storeInteractor;
        storeInteractor2.store = volumePanelStore;
        storeInteractor2.observeStore();
        VPVolumeIcon vPVolumeIcon = (VPVolumeIcon) requireViewById(com.android.systemui.R.id.volume_button);
        this.icon = vPVolumeIcon;
        vPVolumeIcon.initialize(volumePanelStore, volumePanelState, volumePanelRow);
        this.seekBarBackground = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_background);
        this.isDualViewEnabled = VolumePanelStateExt.isDualViewEnabled(volumePanelState);
        this.isAODEnabled = volumePanelState.isAodVolumePanel();
        int viewMinLevel = ViewLevelConverter.viewMinLevel(volumePanelRow);
        int viewMaxLevel = ViewLevelConverter.viewMaxLevel(volumePanelRow);
        VolumeSeekBar volumeSeekBar2 = this.seekBar;
        if (volumeSeekBar2 == null) {
            volumeSeekBar2 = null;
        }
        volumeSeekBar2.semSetMin(viewMinLevel);
        VolumeSeekBar volumeSeekBar3 = this.seekBar;
        if (volumeSeekBar3 == null) {
            volumeSeekBar3 = null;
        }
        volumeSeekBar3.setMax(viewMaxLevel);
        VolumeSeekBar volumeSeekBar4 = this.seekBar;
        if (volumeSeekBar4 == null) {
            volumeSeekBar4 = null;
        }
        volumeSeekBar4.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow), true);
        boolean isSliderEnabled = volumePanelRow.isSliderEnabled();
        VolumeSeekBar volumeSeekBar5 = this.seekBar;
        if (volumeSeekBar5 == null) {
            volumeSeekBar5 = null;
        }
        volumeSeekBar5.setEnabled(isSliderEnabled);
        this.isIconClickable = volumePanelRow.isIconClickable();
        if (VolumePanelValues.isSmartView(volumePanelRow.getStreamType()) && !TextUtils.isEmpty(volumePanelRow.getSmartViewLabel())) {
            str = volumePanelRow.getSmartViewLabel();
        } else if (volumePanelRow.isDynamic()) {
            str = volumePanelRow.getRemoteLabel();
        } else {
            try {
                int i = Result.$r8$clinit;
                failure = getContext().getString(getContext().getResources().getIdentifier(volumePanelRow.getNameRes(), null, null));
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = "";
            }
            str = (String) failure;
            if (volumePanelState.isRemoteMic()) {
                if (VolumePanelValues.isBluetoothSco(volumePanelRow.getStreamType())) {
                    str = getContext().getString(com.android.systemui.R.string.volume_amplify_ambient_sound_title);
                } else if (VolumePanelValues.isMusic(volumePanelRow.getStreamType()) && !volumePanelState.isBtScoOn()) {
                    str = getContext().getString(com.android.systemui.R.string.volume_amplify_ambient_sound_title);
                }
            }
            int streamType = volumePanelRow.getStreamType();
            String remoteLabel = volumePanelRow.getRemoteLabel();
            if (!TextUtils.isEmpty(remoteLabel) && (VolumePanelValues.isMusic(streamType) || VolumePanelValues.isDualAudio(streamType) || VolumePanelValues.isBluetoothSco(streamType) || VolumePanelValues.isMultiSound(streamType) || VolumePanelValues.isAudioSharing(streamType) || VolumePanelValues.isVoiceCall(streamType))) {
                str = str + " (" + remoteLabel + ")";
            }
        }
        this.label = str;
        if (volumePanelState.isShowA11yStream()) {
            if (VolumePanelValues.isAccessibility(this.stream)) {
                VPVolumeIcon vPVolumeIcon2 = this.icon;
                if (vPVolumeIcon2 == null) {
                    vPVolumeIcon2 = null;
                }
                vPVolumeIcon2.setImportantForAccessibility(2);
            } else {
                VPVolumeIcon vPVolumeIcon3 = this.icon;
                if (vPVolumeIcon3 == null) {
                    vPVolumeIcon3 = null;
                }
                vPVolumeIcon3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.VolumeRowView$initialize$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        VolumeRowView.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(VolumeRowView.this.stream).isFromOutside(true).build(), false);
                    }
                });
                updateContentDescription(volumePanelState, volumePanelRow);
                VPVolumeIcon vPVolumeIcon4 = this.icon;
                VPVolumeIcon vPVolumeIcon5 = vPVolumeIcon4 == null ? null : vPVolumeIcon4;
                if (vPVolumeIcon4 == null) {
                    vPVolumeIcon4 = null;
                }
                vPVolumeIcon5.setClickable(vPVolumeIcon4.isEnabled() && this.isIconClickable);
            }
            VolumeSeekBar volumeSeekBar6 = this.seekBar;
            if (volumeSeekBar6 == null) {
                volumeSeekBar6 = null;
            }
            volumeSeekBar6.setContentDescription(this.label);
        }
        ImageView imageView = (ImageView) requireViewById(com.android.systemui.R.id.volume_panel_bluetooth_device_icon);
        this.bluetoothDeviceIcon = imageView;
        imageView.setImageTintList(ColorUtils.getSingleColorStateList(com.android.systemui.R.color.volume_panel_bluetooth_device_icon_color, getContext()));
        updateBluetoothDeviceIcon(volumePanelRow);
        ViewGroup viewGroup = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_container);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ViewGroup) requireViewById(com.android.systemui.R.id.volume_icon_area)).getLayoutParams();
        if (volumePanelState.isExpanded() || this.isDualViewEnabled) {
            marginLayoutParams.bottomMargin = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_panel_icon_margin_bottom_expanded, getContext());
            viewGroup.setPadding(0, 0, 0, ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_expand_panel_seekbar_container_padding_bottom, getContext()));
            marginLayoutParams.bottomMargin = 0;
            int dimenInt = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_panel_icon_margin_touch_expanded_start_end, getContext());
            int dimenInt2 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_panel_icon_margin_touch_expanded_bottom, getContext());
            int dimenInt3 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_panel_icon_margin_bottom_expanded, getContext());
            VPVolumeIcon vPVolumeIcon6 = this.icon;
            if (vPVolumeIcon6 == null) {
                vPVolumeIcon6 = null;
            }
            vPVolumeIcon6.getLayoutParams().height = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_icon_touch_height, getContext());
            VPVolumeIcon vPVolumeIcon7 = this.icon;
            if (vPVolumeIcon7 == null) {
                vPVolumeIcon7 = null;
            }
            vPVolumeIcon7.getLayoutParams().width = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_icon_touch_width, getContext());
            VPVolumeIcon vPVolumeIcon8 = this.icon;
            if (vPVolumeIcon8 == null) {
                vPVolumeIcon8 = null;
            }
            vPVolumeIcon8.setPadding(dimenInt, dimenInt3, dimenInt, dimenInt2);
            VolumeSeekBar volumeSeekBar7 = this.seekBar;
            if (volumeSeekBar7 == null) {
                volumeSeekBar7 = null;
            }
            volumeSeekBar7.setProgressDrawable(getContext().getResources().getDrawable((((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() || !(BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR)) ? com.android.systemui.R.drawable.volume_seekbar_circle_drawable_expand : com.android.systemui.R.drawable.volume_seekbar_circle_drawable_expand_blur, null));
            updateProgressDrawable(volumePanelState);
            ViewGroup viewGroup2 = this.seekBarBackground;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.setBackground(null);
            ViewGroup viewGroup3 = this.seekBarBackground;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            viewGroup3.setElevation(0.0f);
            viewGroup.setOnTouchListener(null);
            ViewGroup viewGroup4 = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_outline_stroke_expand);
            ViewVisibilityUtil.INSTANCE.getClass();
            viewGroup4.setVisibility(0);
        } else {
            boolean isAllSoundOff = volumePanelState.isAllSoundOff();
            boolean isZenMode = volumePanelState.isZenMode();
            boolean isLeBroadcasting = volumePanelState.isLeBroadcasting();
            ImageView imageView2 = (ImageView) requireViewById(com.android.systemui.R.id.volume_panel_status_icon);
            imageView2.setImageDrawable(getContext().getResources().getDrawable((isAllSoundOff || isZenMode) ? com.android.systemui.R.drawable.ic_volume_control_dnd : com.android.systemui.R.drawable.ic_auracast, null));
            if (isAllSoundOff || isZenMode || isLeBroadcasting) {
                ViewVisibilityUtil.INSTANCE.getClass();
                imageView2.setVisibility(0);
            } else {
                ViewVisibilityUtil.INSTANCE.getClass();
                imageView2.setVisibility(8);
            }
            VolumeSeekBar volumeSeekBar8 = this.seekBar;
            if (volumeSeekBar8 == null) {
                volumeSeekBar8 = null;
            }
            volumeSeekBar8.setProgressDrawable(getContext().getResources().getDrawable(com.android.systemui.R.drawable.volume_seekbar_drawable_blur, null));
            updateProgressDrawable(volumePanelState);
            int i3 = (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() || !(BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR)) ? com.android.systemui.R.drawable.volume_seekbar_bg : com.android.systemui.R.drawable.volume_seekbar_bg_blur;
            ViewGroup viewGroup5 = this.seekBarBackground;
            if (viewGroup5 == null) {
                viewGroup5 = null;
            }
            viewGroup5.setBackground(getContext().getDrawable(i3));
            ViewGroup viewGroup6 = this.seekBarBackground;
            if (viewGroup6 == null) {
                viewGroup6 = null;
            }
            viewGroup6.setElevation(ContextUtils.getDimenFloat(com.android.systemui.R.dimen.volume_seekbar_elevation, getContext()));
            int dimenInt4 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_seekbar_elevation_padding, getContext());
            int dimenInt5 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_seekbar_elevation_padding_bottom, getContext());
            viewGroup.setPadding(dimenInt4, dimenInt4, dimenInt4, dimenInt5);
            viewGroup.setClipChildren(false);
            marginLayoutParams.bottomMargin = dimenInt5;
            int dimenInt6 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.volume_icon_touch_padding, getContext());
            VPVolumeIcon vPVolumeIcon9 = this.icon;
            if (vPVolumeIcon9 == null) {
                vPVolumeIcon9 = null;
            }
            vPVolumeIcon9.setPadding(dimenInt6, dimenInt6, dimenInt6, dimenInt6);
        }
        VolumePanelMotion volumePanelMotion2 = this.volumePanelMotion;
        if (volumePanelMotion2 == null) {
            volumePanelMotion2 = null;
        }
        volumePanelMotion2.getClass();
        this.touchDownAnimation = VolumePanelMotion.getSeekBarTouchDownAnimation(this);
        VolumePanelMotion volumePanelMotion3 = this.volumePanelMotion;
        if (volumePanelMotion3 == null) {
            volumePanelMotion3 = null;
        }
        volumePanelMotion3.getClass();
        this.touchUpAnimation = VolumePanelMotion.getSeekBarTouchUpAnimation(this);
        SpringAnimation springAnimation = this.progressBarSpring;
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        SpringAnimation springAnimation2 = this.progressBarSpring;
        springAnimation2.mVelocity = 0.0f;
        springAnimation2.setStartValue((this.seekBar != null ? r10 : null).getProgress());
        this.progressBarSpring.setMinimumVisibleChange(1.0f);
        this.progressBarSpring.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.VolumeRowView$initialize$3
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                VolumeSeekBar volumeSeekBar9 = VolumeRowView.this.seekBar;
                if (volumeSeekBar9 == null) {
                    volumeSeekBar9 = null;
                }
                volumeSeekBar9.setProgress((int) f);
            }
        });
    }

    public final boolean isIconClicked(float f, float f2) {
        if (!this.startProgress && this.touchDownIcon) {
            VPVolumeIcon vPVolumeIcon = this.icon;
            if (vPVolumeIcon == null) {
                vPVolumeIcon = null;
            }
            if (vPVolumeIcon.isEnabled() && this.isIconClickable) {
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                VPVolumeIcon vPVolumeIcon2 = this.icon;
                VPVolumeIcon vPVolumeIcon3 = vPVolumeIcon2 != null ? vPVolumeIcon2 : null;
                viewUtil.getClass();
                if (ViewUtil.isTouched(vPVolumeIcon3, f, f2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        int earProtectLevel;
        String str;
        VolumePanelState volumePanelState2 = volumePanelState;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
                if (volumePanelStateExt.isRowVisible(volumePanelState2, this.stream)) {
                    VolumePanelRow findRow = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow != null && (earProtectLevel = findRow.getEarProtectLevel()) != this.earProtectLevel) {
                        this.earProtectLevel = earProtectLevel;
                        VolumeSeekBar volumeSeekBar = this.seekBar;
                        if (volumeSeekBar == null) {
                            volumeSeekBar = null;
                        }
                        volumeSeekBar.getClass();
                    }
                    updateProgressDrawable(volumePanelState2);
                    VolumePanelRow findRow2 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow2 != null) {
                        VolumeSeekBar volumeSeekBar2 = this.seekBar;
                        if (volumeSeekBar2 == null) {
                            volumeSeekBar2 = null;
                        }
                        volumeSeekBar2.setEnabled(findRow2.isSliderEnabled());
                    }
                    VolumePanelRow findRow3 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow3 != null) {
                        updateContentDescription(volumePanelState2, findRow3);
                    }
                    VolumePanelRow findRow4 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow4 != null) {
                        updateBluetoothDeviceIcon(findRow4);
                    }
                    StoreInteractor storeInteractor = this.storeInteractor;
                    VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.stream);
                    VolumeSeekBar volumeSeekBar3 = this.seekBar;
                    storeInteractor.sendAction(stream.progress((volumeSeekBar3 != null ? volumeSeekBar3 : null).getProgress()).build(), true);
                    break;
                }
                break;
            case 2:
                if (this.isAODEnabled && this.stream == volumePanelState2.getStream()) {
                    updateProgress(volumePanelState2);
                    break;
                }
                break;
            case 3:
                if (this.stream == volumePanelState2.getStream()) {
                    updateProgress(volumePanelState2);
                    break;
                }
                break;
            case 4:
                if (this.stream == volumePanelState2.getStream()) {
                    HandlerWrapper handlerWrapper = this.handlerWrapper;
                    if (handlerWrapper == null) {
                        handlerWrapper = null;
                    }
                    handlerWrapper.remove(this.recheckCallback);
                    HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                    (handlerWrapper2 != null ? handlerWrapper2 : null).postDelayed(this.recheckCallback, 1000L);
                    break;
                }
                break;
            case 5:
                if (this.stream == volumePanelState2.getStream()) {
                    VolumeSeekBar volumeSeekBar4 = this.seekBar;
                    if (volumeSeekBar4 == null) {
                        volumeSeekBar4 = null;
                    }
                    int progress = volumeSeekBar4.getProgress();
                    if (this.progressBarSpring.mRunning) {
                        progress = this.springFinalPosition;
                    }
                    this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.stream).progress(progress).build(), true);
                    HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                    if (handlerWrapper3 == null) {
                        handlerWrapper3 = null;
                    }
                    handlerWrapper3.remove(this.recheckCallback);
                    HandlerWrapper handlerWrapper4 = this.handlerWrapper;
                    (handlerWrapper4 != null ? handlerWrapper4 : null).postDelayed(this.recheckCallback, 1000L);
                    break;
                }
                break;
            case 6:
                this.storeInteractor.dispose();
                VolumeSeekBar volumeSeekBar5 = this.seekBar;
                (volumeSeekBar5 != null ? volumeSeekBar5 : null).storeInteractor.dispose();
                break;
            case 7:
                if (volumePanelState2.getStream() == this.stream) {
                    this.startProgress = true;
                    animateSeekBarButton(volumePanelState2, true);
                    break;
                }
                break;
            case 8:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    VolumePanelMotion volumePanelMotion = this.volumePanelMotion;
                    if (volumePanelMotion == null) {
                        volumePanelMotion = null;
                    }
                    SpringAnimation springAnimation = this.touchDownAnimation;
                    if (springAnimation == null) {
                        springAnimation = null;
                    }
                    SpringAnimation springAnimation2 = this.touchUpAnimation;
                    SpringAnimation springAnimation3 = springAnimation2 != null ? springAnimation2 : null;
                    volumePanelMotion.getClass();
                    if (springAnimation3 != null && springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                        springAnimation3.skipToEnd();
                    }
                    springAnimation.animateToFinalPosition(1.04f);
                    break;
                }
                break;
            case 9:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    VolumePanelMotion volumePanelMotion2 = this.volumePanelMotion;
                    if (volumePanelMotion2 == null) {
                        volumePanelMotion2 = null;
                    }
                    SpringAnimation springAnimation4 = this.touchUpAnimation;
                    if (springAnimation4 == null) {
                        springAnimation4 = null;
                    }
                    SpringAnimation springAnimation5 = this.touchDownAnimation;
                    SpringAnimation springAnimation6 = springAnimation5 != null ? springAnimation5 : null;
                    volumePanelMotion2.getClass();
                    VolumePanelMotion.startSeekBarTouchUpAnimation(springAnimation4, springAnimation6);
                }
                animateSeekBarButton(volumePanelState2, false);
                break;
            case 10:
                if (this.stream == volumePanelState2.getStream()) {
                    VolumeSeekBar volumeSeekBar6 = this.seekBar;
                    if (volumeSeekBar6 == null) {
                        volumeSeekBar6 = null;
                    }
                    volumeSeekBar6.setFocusable(false);
                    VolumeSeekBar volumeSeekBar7 = this.seekBar;
                    if (volumeSeekBar7 == null) {
                        volumeSeekBar7 = null;
                    }
                    volumeSeekBar7.setFocusableInTouchMode(false);
                    VolumeSeekBar volumeSeekBar8 = this.seekBar;
                    if (volumeSeekBar8 == null) {
                        volumeSeekBar8 = null;
                    }
                    volumeSeekBar8.clearFocus();
                    VolumeSeekBar volumeSeekBar9 = this.seekBar;
                    if (volumeSeekBar9 == null) {
                        volumeSeekBar9 = null;
                    }
                    volumeSeekBar9.setBackground(null);
                    VolumePanelRow findRow5 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, 20);
                    if (findRow5 == null || (str = findRow5.getSmartViewLabel()) == null) {
                        str = "";
                    }
                    (StringsKt__StringsKt.contains(SemSystemProperties.get("ro.build.characteristics"), "tablet", false) ? Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_tablet_volume_smart_view, str), 0) : Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_phone_volume_smart_view, str), 0)).show();
                    break;
                }
                break;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
        VolumeSeekBar volumeSeekBar = this.seekBar;
        if (volumeSeekBar == null) {
            volumeSeekBar = null;
        }
        volumeSeekBar.storeInteractor.dispose();
    }

    public final void updateBluetoothDeviceIcon(VolumePanelRow volumePanelRow) {
        ImageView imageView;
        if (volumePanelRow.getIconType() != 2 && volumePanelRow.getIconType() != 10 && volumePanelRow.getIconType() != 13 && volumePanelRow.getIconType() != 12) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            ImageView imageView2 = this.bluetoothDeviceIcon;
            imageView = imageView2 != null ? imageView2 : null;
            viewVisibilityUtil.getClass();
            ViewVisibilityUtil.setGone(imageView);
            return;
        }
        String dualBtDeviceAddress = volumePanelRow.getDualBtDeviceAddress();
        if (!this.isDualViewEnabled || TextUtils.isEmpty(dualBtDeviceAddress)) {
            return;
        }
        BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
        Context context = getContext();
        bluetoothIconUtil.getClass();
        Drawable iconDrawable = BluetoothIconUtil.getIconDrawable(context, dualBtDeviceAddress);
        ImageView imageView3 = this.bluetoothDeviceIcon;
        if (imageView3 == null) {
            imageView3 = null;
        }
        imageView3.setImageDrawable(iconDrawable);
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView4 = this.bluetoothDeviceIcon;
        imageView = imageView4 != null ? imageView4 : null;
        viewVisibilityUtil2.getClass();
        imageView.setVisibility(0);
    }

    public final void updateContentDescription(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        String string;
        if (this.stream == 2) {
            string = volumePanelRow.getIconType() == 0 ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_sound) : volumePanelState.isHasVibrator() ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_vib) : getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_mute);
            Intrinsics.checkNotNull(string);
        } else if (volumePanelRow.getIconType() == 1 || volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0) {
            string = getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_unmute, this.label);
            Intrinsics.checkNotNull(string);
        } else {
            string = getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_mute, this.label);
            Intrinsics.checkNotNull(string);
        }
        VPVolumeIcon vPVolumeIcon = this.icon;
        if (vPVolumeIcon == null) {
            vPVolumeIcon = null;
        }
        vPVolumeIcon.setContentDescription(string);
    }

    public final void updateProgress(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (findRow != null) {
            this.springFinalPosition = ViewLevelConverter.viewRealLevel(findRow);
            if (!findRow.isVisible()) {
                this.progressBarSpring.cancel();
                VolumeSeekBar volumeSeekBar = this.seekBar;
                (volumeSeekBar != null ? volumeSeekBar : null).setProgress(this.springFinalPosition);
            } else {
                this.progressBarSpring.setStartValue((this.seekBar != null ? r1 : null).getProgress());
                this.progressBarSpring.animateToFinalPosition(this.springFinalPosition);
            }
        }
    }

    public final void updateProgressDrawable(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (findRow != null) {
            VolumeSeekBar volumeSeekBar = this.seekBar;
            if (volumeSeekBar == null) {
                volumeSeekBar = null;
            }
            SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) volumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            secRoundedCornerSeekBarDrawable.setContext(getContext());
            boolean z = volumePanelState.isLeBroadcasting() && !findRow.isRoutedToBluetooth();
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int earProtectLevel = findRow.getEarProtectLevel();
                int realLevel = findRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(findRow.getStreamType())) {
                    realLevel *= 100;
                }
                if (1 <= earProtectLevel && earProtectLevel < realLevel && !z) {
                    secRoundedCornerSeekBarDrawable.setShockColor(true);
                    return;
                }
            }
            if (ViewLevelConverter.viewRealLevel(findRow) == ViewLevelConverter.viewMaxLevel(findRow)) {
                secRoundedCornerSeekBarDrawable.setShockColor(true);
            } else {
                secRoundedCornerSeekBarDrawable.setShockColor(false);
            }
        }
    }

    public VolumeRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.progressBarSpring = new SpringAnimation(new FloatValueHolder());
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.VolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = VolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(VolumeRowView.this.stream);
                VolumeSeekBar volumeSeekBar = VolumeRowView.this.seekBar;
                if (volumeSeekBar == null) {
                    volumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(volumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = VolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
    }
}
