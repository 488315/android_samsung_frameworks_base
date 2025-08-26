package com.android.systemui.volume.view.subscreen.full;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.SecRoundedCornerSeekBarDrawable;
import com.android.systemui.volume.view.ViewLevelConverter;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class SubFullLayoutVolumeRowView extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public ImageView bluetoothDeviceIcon;
    public Runnable buttonAnimatorRunnable;
    public int earProtectLevel;
    public HandlerWrapper handlerWrapper;
    public SubFullLayoutVolumeIcon icon;
    public boolean iconClickable;
    public boolean isAODEnabled;
    public boolean isDualViewEnabled;
    public String label;
    public final Lazy progressBarSpring$delegate;
    public final SubFullLayoutVolumeRowView$recheckCallback$1 recheckCallback;
    public SubFullLayoutVolumeSeekBar seekBar;
    public ViewGroup seekBarBackground;
    public boolean startProgress;
    public final StoreInteractor storeInteractor;
    public int stream;
    public SpringAnimation touchDownAnimation;
    public boolean touchDownIcon;
    public SpringAnimation touchUpAnimation;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;

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

    /* renamed from: $r8$lambda$oovOMvrowRvnsb1_qcZm9-hq0lo, reason: not valid java name */
    public static SpringAnimation m3221$r8$lambda$oovOMvrowRvnsb1_qcZm9hq0lo(final SubFullLayoutVolumeRowView subFullLayoutVolumeRowView) {
        SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(450.0f);
        springAnimation.mSpring = springForce;
        springAnimation.mVelocity = 0.0f;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = subFullLayoutVolumeRowView.seekBar;
        if (subFullLayoutVolumeSeekBar == null) {
            subFullLayoutVolumeSeekBar = null;
        }
        springAnimation.setStartValue(subFullLayoutVolumeSeekBar.getProgress());
        springAnimation.setMinimumVisibleChange(1.0f);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2$1$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = this.this$0.seekBar;
                if (subFullLayoutVolumeSeekBar2 == null) {
                    subFullLayoutVolumeSeekBar2 = null;
                }
                subFullLayoutVolumeSeekBar2.setProgress((int) f);
            }
        });
        return springAnimation;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1] */
    public SubFullLayoutVolumeRowView(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = this.this$0.storeInteractor;
                VolumePanelAction.Builder builderStream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.this$0.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.this$0.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(builderStream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = SubFullLayoutVolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SubFullLayoutVolumeRowView.m3221$r8$lambda$oovOMvrowRvnsb1_qcZm9hq0lo(this.f$0);
            }
        });
    }

    public final void animateSeekBarButton$1(VolumePanelState volumePanelState, final boolean z) {
        if (volumePanelState.getStream() == this.stream) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
            if (subFullLayoutVolumeSeekBar == null) {
                subFullLayoutVolumeSeekBar = null;
            }
            final SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) subFullLayoutVolumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            handlerWrapper.remove(this.buttonAnimatorRunnable);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$animateSeekBarButton$1
                @Override // java.lang.Runnable
                public final void run() {
                    secRoundedCornerSeekBarDrawable.animateButton(z);
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
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
            if (subFullLayoutVolumeIcon == null) {
                subFullLayoutVolumeIcon = null;
            }
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            viewUtil.getClass();
            if (ViewUtil.isTouched(subFullLayoutVolumeIcon, rawX, rawY)) {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(VolumePanelStore volumePanelStore, HandlerWrapper handlerWrapper, VolumePanelRow volumePanelRow, VolumePanelState volumePanelState, SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion) throws Resources.NotFoundException {
        boolean z;
        String string;
        StoreInteractor storeInteractor = this.storeInteractor;
        storeInteractor.store = volumePanelStore;
        storeInteractor.observeStore();
        this.handlerWrapper = handlerWrapper;
        this.stream = volumePanelRow.getStreamType();
        this.volumePanelMotion = subFullLayoutVolumePanelMotion;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = (SubFullLayoutVolumeSeekBar) requireViewById(com.android.systemui.R.id.volume_seekbar);
        this.seekBar = subFullLayoutVolumeSeekBar;
        if (subFullLayoutVolumeSeekBar == null) {
            subFullLayoutVolumeSeekBar = null;
        }
        subFullLayoutVolumeSeekBar.stream = this.stream;
        subFullLayoutVolumeSeekBar.isTracking = false;
        StoreInteractor storeInteractor2 = subFullLayoutVolumeSeekBar.storeInteractor;
        storeInteractor2.store = volumePanelStore;
        storeInteractor2.observeStore();
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = (SubFullLayoutVolumeIcon) requireViewById(com.android.systemui.R.id.volume_button);
        this.icon = subFullLayoutVolumeIcon;
        if (subFullLayoutVolumeIcon == null) {
            subFullLayoutVolumeIcon = null;
        }
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion2 == null) {
            subFullLayoutVolumePanelMotion2 = null;
        }
        ((StoreInteractor) subFullLayoutVolumeIcon.storeInteractor$delegate.getValue()).store = volumePanelStore;
        ((StoreInteractor) subFullLayoutVolumeIcon.storeInteractor$delegate.getValue()).observeStore();
        subFullLayoutVolumeIcon.volumePanelMotion = subFullLayoutVolumePanelMotion2;
        int streamType = volumePanelRow.getStreamType();
        subFullLayoutVolumeIcon.stream = streamType;
        if (VolumePanelValues.isRing(streamType)) {
            subFullLayoutVolumeIcon.iconType = volumePanelRow.getIconType();
        }
        boolean z2 = true;
        subFullLayoutVolumeIcon.updateIconLayout$1(volumePanelRow, true);
        subFullLayoutVolumeIcon.updateIconState$1(volumePanelRow, false);
        subFullLayoutVolumeIcon.updateIconTintColor(volumePanelState, volumePanelRow);
        subFullLayoutVolumeIcon.updateEnableState$1(volumePanelState, volumePanelRow);
        this.seekBarBackground = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_background);
        if (volumePanelState.isDualAudio()) {
            if (volumePanelState.isMultiSoundBt() ? VolumePanelValues.isMultiSound(volumePanelState.getActiveStream()) : VolumePanelValues.isMusic(volumePanelState.getActiveStream())) {
                z = true;
            }
        } else {
            z = false;
        }
        this.isDualViewEnabled = z;
        this.isAODEnabled = volumePanelState.isAodVolumePanel();
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = this.seekBar;
        if (subFullLayoutVolumeSeekBar2 == null) {
            subFullLayoutVolumeSeekBar2 = null;
        }
        subFullLayoutVolumeSeekBar2.semSetMin(ViewLevelConverter.viewMinLevel(volumePanelRow));
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar3 = this.seekBar;
        if (subFullLayoutVolumeSeekBar3 == null) {
            subFullLayoutVolumeSeekBar3 = null;
        }
        subFullLayoutVolumeSeekBar3.setMax(ViewLevelConverter.viewMaxLevel(volumePanelRow));
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar4 = this.seekBar;
        if (subFullLayoutVolumeSeekBar4 == null) {
            subFullLayoutVolumeSeekBar4 = null;
        }
        subFullLayoutVolumeSeekBar4.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow), true);
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar5 = this.seekBar;
        if (subFullLayoutVolumeSeekBar5 == null) {
            subFullLayoutVolumeSeekBar5 = null;
        }
        subFullLayoutVolumeSeekBar5.setEnabled(volumePanelRow.isSliderEnabled());
        this.iconClickable = volumePanelRow.isIconClickable();
        if (VolumePanelValues.isSmartView(volumePanelRow.getStreamType()) && !TextUtils.isEmpty(volumePanelRow.getSmartViewLabel())) {
            string = volumePanelRow.getSmartViewLabel();
        } else if (volumePanelRow.isDynamic()) {
            string = volumePanelRow.getRemoteLabel();
        } else {
            try {
                string = getContext().getString(getContext().getResources().getIdentifier(volumePanelRow.getNameRes(), null, null));
                string.getClass();
            } catch (Exception unused) {
                string = "";
            }
            if (volumePanelState.isRemoteMic()) {
                if (VolumePanelValues.isBluetoothSco(volumePanelRow.getStreamType())) {
                    string = getContext().getString(com.android.systemui.R.string.volume_amplify_ambient_sound_title);
                } else if (VolumePanelValues.isMusic(volumePanelRow.getStreamType()) && !volumePanelState.isBtScoOn()) {
                    string = getContext().getString(com.android.systemui.R.string.volume_amplify_ambient_sound_title);
                }
            }
            int streamType2 = volumePanelRow.getStreamType();
            String remoteLabel = volumePanelRow.getRemoteLabel();
            if (!TextUtils.isEmpty(remoteLabel) && (VolumePanelValues.isMusic(streamType2) || VolumePanelValues.isDualAudio(streamType2) || VolumePanelValues.isBluetoothSco(streamType2) || VolumePanelValues.isMultiSound(streamType2) || VolumePanelValues.isAudioSharing(streamType2) || VolumePanelValues.isVoiceCall(streamType2))) {
                string = string + " (" + remoteLabel + ")";
            }
        }
        this.label = string;
        if (volumePanelState.isShowA11yStream()) {
            if (VolumePanelValues.isAccessibility(this.stream)) {
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon2 = this.icon;
                if (subFullLayoutVolumeIcon2 == null) {
                    subFullLayoutVolumeIcon2 = null;
                }
                subFullLayoutVolumeIcon2.setImportantForAccessibility(2);
            } else {
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon3 = this.icon;
                if (subFullLayoutVolumeIcon3 == null) {
                    subFullLayoutVolumeIcon3 = null;
                }
                subFullLayoutVolumeIcon3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView.initialize.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SubFullLayoutVolumeRowView.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(SubFullLayoutVolumeRowView.this.stream).isFromOutside(true).build(), false);
                    }
                });
                updateContentDescription$1(volumePanelState, volumePanelRow);
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon4 = this.icon;
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon5 = subFullLayoutVolumeIcon4 == null ? null : subFullLayoutVolumeIcon4;
                if (subFullLayoutVolumeIcon4 == null) {
                    subFullLayoutVolumeIcon4 = null;
                }
                subFullLayoutVolumeIcon5.setClickable(subFullLayoutVolumeIcon4.isEnabled() && this.iconClickable);
            }
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar6 = this.seekBar;
            if (subFullLayoutVolumeSeekBar6 == null) {
                subFullLayoutVolumeSeekBar6 = null;
            }
            subFullLayoutVolumeSeekBar6.setContentDescription(this.label);
        }
        ImageView imageView = (ImageView) requireViewById(com.android.systemui.R.id.volume_panel_bluetooth_device_icon);
        this.bluetoothDeviceIcon = imageView;
        if (imageView == null) {
            imageView = null;
        }
        imageView.setImageTintList(new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(com.android.systemui.R.color.volume_panel_bluetooth_device_icon_color, null)}));
        updateBluetoothDeviceIcon$1(volumePanelRow);
        int dimenInt = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_seekbar_height, getContext());
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar7 = this.seekBar;
        if (subFullLayoutVolumeSeekBar7 == null) {
            subFullLayoutVolumeSeekBar7 = null;
        }
        subFullLayoutVolumeSeekBar7.getLayoutParams().width = dimenInt;
        ViewGroup viewGroup = this.seekBarBackground;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.getLayoutParams().height = dimenInt;
        requireViewById(com.android.systemui.R.id.volume_panel_blur).getLayoutParams().height = dimenInt;
        if (!volumePanelState.isExpanded() && !this.isDualViewEnabled) {
            z2 = false;
        }
        ImageView imageView2 = (ImageView) requireViewById(com.android.systemui.R.id.volume_panel_status_icon);
        if (!z2) {
            imageView2.setImageTintList(new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(com.android.systemui.R.color.volume_panel_status_message_color, null)}));
        }
        if (z2 || !(volumePanelState.isAllSoundOff() || volumePanelState.isZenMode() || volumePanelState.isLeBroadcasting())) {
            ViewVisibilityUtil.INSTANCE.getClass();
            ViewVisibilityUtil.setGone(imageView2);
        } else {
            ViewVisibilityUtil.INSTANCE.getClass();
            imageView2.setVisibility(0);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ViewGroup) requireViewById(com.android.systemui.R.id.volume_icon_area)).getLayoutParams();
        if (z2) {
            int dimenInt2 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_icon_margin_touch_expanded, getContext());
            int dimenInt3 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_icon_margin_bottom_expanded, getContext());
            marginLayoutParams.bottomMargin = 0;
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon6 = this.icon;
            if (subFullLayoutVolumeIcon6 == null) {
                subFullLayoutVolumeIcon6 = null;
            }
            ViewGroup.LayoutParams layoutParams = subFullLayoutVolumeIcon6.getLayoutParams();
            layoutParams.height = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_icon_touch_height, getContext());
            layoutParams.width = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_icon_touch_width, getContext());
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon7 = this.icon;
            if (subFullLayoutVolumeIcon7 == null) {
                subFullLayoutVolumeIcon7 = null;
            }
            subFullLayoutVolumeIcon7.setPadding(dimenInt2, dimenInt3, dimenInt2, dimenInt2);
        } else {
            marginLayoutParams.bottomMargin = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_seekbar_elevation_padding, getContext());
            int dimenInt4 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_icon_touch_padding, getContext());
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon8 = this.icon;
            if (subFullLayoutVolumeIcon8 == null) {
                subFullLayoutVolumeIcon8 = null;
            }
            subFullLayoutVolumeIcon8.setPadding(dimenInt4, dimenInt4, dimenInt4, dimenInt4);
        }
        ViewGroup viewGroup2 = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_container);
        if (z2) {
            viewGroup2.setPadding(0, 0, 0, ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_expand_panel_horizontal_padding_min, getContext()));
        } else {
            int dimenInt5 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_seekbar_elevation_padding, getContext());
            int dimenInt6 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_additional_margin, getContext());
            if (BasicRune.VOLUME_SUB_DISPLAY_FULLSCREEN_VOLUME_DIALOG) {
                viewGroup2.setPadding(dimenInt6, dimenInt5, dimenInt6, dimenInt5);
            } else {
                viewGroup2.setPadding(dimenInt5, dimenInt5, dimenInt5, dimenInt5);
            }
            viewGroup2.setClipChildren(false);
        }
        int i = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() ? com.android.systemui.R.drawable.volume_seekbar_bg_reduce_transparency : (BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR) ? com.android.systemui.R.drawable.sub_full_volume_seekbar_bg_blur : com.android.systemui.R.drawable.volume_seekbar_bg;
        ViewGroup viewGroup3 = this.seekBarBackground;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        viewGroup3.setBackground(getContext().getDrawable(i));
        if (z2) {
            ViewGroup viewGroup4 = (ViewGroup) requireViewById(com.android.systemui.R.id.volume_seekbar_outline_stroke_expand);
            ViewVisibilityUtil.INSTANCE.getClass();
            viewGroup4.setVisibility(0);
        } else {
            ViewGroup viewGroup5 = this.seekBarBackground;
            if (viewGroup5 == null) {
                viewGroup5 = null;
            }
            viewGroup5.setElevation(ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_seekbar_elevation, getContext()));
        }
        if (!z2) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar8 = this.seekBar;
            if (subFullLayoutVolumeSeekBar8 == null) {
                subFullLayoutVolumeSeekBar8 = null;
            }
            subFullLayoutVolumeSeekBar8.setProgressDrawable(getContext().getResources().getDrawable(com.android.systemui.R.drawable.sub_full_volume_seekbar_drawable_blur, null));
        } else if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() || !(BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR)) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar9 = this.seekBar;
            if (subFullLayoutVolumeSeekBar9 == null) {
                subFullLayoutVolumeSeekBar9 = null;
            }
            subFullLayoutVolumeSeekBar9.setProgressDrawable(getContext().getResources().getDrawable(com.android.systemui.R.drawable.volume_seekbar_circle_drawable_expand, null));
        } else {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar10 = this.seekBar;
            if (subFullLayoutVolumeSeekBar10 == null) {
                subFullLayoutVolumeSeekBar10 = null;
            }
            subFullLayoutVolumeSeekBar10.setProgressDrawable(getContext().getResources().getDrawable(com.android.systemui.R.drawable.sub_full_volume_seekbar_drawable_expand_blur, null));
        }
        updateProgressDrawable$1(volumePanelState);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion3 == null) {
            subFullLayoutVolumePanelMotion3 = null;
        }
        subFullLayoutVolumePanelMotion3.getClass();
        this.touchDownAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchDownAnimation(this);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
        (subFullLayoutVolumePanelMotion4 != null ? subFullLayoutVolumePanelMotion4 : null).getClass();
        this.touchUpAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchUpAnimation(this);
    }

    public final boolean isIconClicked(float f, float f2) {
        if (this.startProgress || !this.touchDownIcon) {
            return false;
        }
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
        if (subFullLayoutVolumeIcon == null) {
            subFullLayoutVolumeIcon = null;
        }
        if (!subFullLayoutVolumeIcon.isEnabled() || !this.iconClickable) {
            return false;
        }
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon2 = this.icon;
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon3 = subFullLayoutVolumeIcon2 != null ? subFullLayoutVolumeIcon2 : null;
        viewUtil.getClass();
        return ViewUtil.isTouched(subFullLayoutVolumeIcon3, f, f2);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        int earProtectLevel;
        VolumePanelState volumePanelState2 = volumePanelState;
        springAnimation = null;
        SpringAnimation springAnimation = null;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
                if (volumePanelStateExt.isRowVisible(volumePanelState2, this.stream)) {
                    VolumePanelRow volumePanelRowFindRow = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (volumePanelRowFindRow != null && (earProtectLevel = volumePanelRowFindRow.getEarProtectLevel()) != this.earProtectLevel) {
                        this.earProtectLevel = earProtectLevel;
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                        if (subFullLayoutVolumeSeekBar == null) {
                            subFullLayoutVolumeSeekBar = null;
                        }
                        subFullLayoutVolumeSeekBar.getClass();
                    }
                    updateProgressDrawable$1(volumePanelState2);
                    VolumePanelRow volumePanelRowFindRow2 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (volumePanelRowFindRow2 != null) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setEnabled(volumePanelRowFindRow2.isSliderEnabled());
                    }
                    VolumePanelRow volumePanelRowFindRow3 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (volumePanelRowFindRow3 != null) {
                        updateContentDescription$1(volumePanelState2, volumePanelRowFindRow3);
                    }
                    VolumePanelRow volumePanelRowFindRow4 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (volumePanelRowFindRow4 != null) {
                        updateBluetoothDeviceIcon$1(volumePanelRowFindRow4);
                    }
                    StoreInteractor storeInteractor = this.storeInteractor;
                    VolumePanelAction.Builder builderStream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.stream);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar3 = this.seekBar;
                    storeInteractor.sendAction(builderStream.progress((subFullLayoutVolumeSeekBar3 != null ? subFullLayoutVolumeSeekBar3 : null).getProgress()).build(), true);
                    break;
                }
                break;
            case 2:
                if (this.isAODEnabled && this.stream == volumePanelState2.getStream()) {
                    updateProgress$1(volumePanelState2);
                    break;
                }
                break;
            case 3:
                if (this.stream == volumePanelState2.getStream()) {
                    updateProgress$1(volumePanelState2);
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
                    StoreInteractor storeInteractor2 = this.storeInteractor;
                    VolumePanelAction.Builder builderStream2 = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.stream);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar4 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar4 == null) {
                        subFullLayoutVolumeSeekBar4 = null;
                    }
                    storeInteractor2.sendAction(builderStream2.progress(subFullLayoutVolumeSeekBar4.getProgress()).build(), true);
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
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar5 = this.seekBar;
                (subFullLayoutVolumeSeekBar5 != null ? subFullLayoutVolumeSeekBar5 : null).storeInteractor.dispose();
                break;
            case 7:
                if (volumePanelState2.getStream() == this.stream) {
                    this.startProgress = true;
                    break;
                }
                break;
            case 8:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    SpringAnimation springAnimation2 = this.touchDownAnimation;
                    if (springAnimation2 == null) {
                        springAnimation2 = null;
                    }
                    SpringAnimation springAnimation3 = this.touchUpAnimation;
                    SpringAnimation springAnimation4 = springAnimation3 != null ? springAnimation3 : null;
                    subFullLayoutVolumePanelMotion.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchDownAnimation(springAnimation2, springAnimation4, true);
                }
                animateSeekBarButton$1(volumePanelState2, true);
                break;
            case 9:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    SpringAnimation springAnimation5 = this.touchUpAnimation;
                    if (springAnimation5 == null) {
                        springAnimation5 = null;
                    }
                    SpringAnimation springAnimation6 = this.touchDownAnimation;
                    if (springAnimation6 == null) {
                        springAnimation6 = null;
                    }
                    subFullLayoutVolumePanelMotion2.getClass();
                    if (springAnimation6 != null) {
                        if (springAnimation6.mRunning && springAnimation6.canSkipToEnd()) {
                            springAnimation = springAnimation6;
                        }
                        if (springAnimation != null) {
                            springAnimation.skipToEnd();
                        }
                    }
                    springAnimation5.animateToFinalPosition(1.0f);
                }
                animateSeekBarButton$1(volumePanelState2, false);
                break;
            case 10:
                if (this.stream == volumePanelState2.getStream()) {
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar6 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar6 == null) {
                        subFullLayoutVolumeSeekBar6 = null;
                    }
                    subFullLayoutVolumeSeekBar6.setFocusable(false);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar7 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar7 == null) {
                        subFullLayoutVolumeSeekBar7 = null;
                    }
                    subFullLayoutVolumeSeekBar7.setFocusableInTouchMode(false);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar8 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar8 == null) {
                        subFullLayoutVolumeSeekBar8 = null;
                    }
                    subFullLayoutVolumeSeekBar8.clearFocus();
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar9 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar9 == null) {
                        subFullLayoutVolumeSeekBar9 = null;
                    }
                    subFullLayoutVolumeSeekBar9.setBackground(null);
                    List<VolumePanelRow> volumeRowList = volumePanelState2.getVolumeRowList();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : volumeRowList) {
                        if (VolumePanelValues.isSmartView(((VolumePanelRow) obj).getStreamType())) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList.get(i);
                        i++;
                        arrayList2.add(((VolumePanelRow) obj2).getSmartViewLabel());
                    }
                    String str = (String) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
                    if (str == null) {
                        str = "";
                    }
                    Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_phone_volume_smart_view, str), 0).show();
                    break;
                }
                break;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
        if (subFullLayoutVolumeSeekBar == null) {
            subFullLayoutVolumeSeekBar = null;
        }
        subFullLayoutVolumeSeekBar.storeInteractor.dispose();
    }

    public final void updateBluetoothDeviceIcon$1(VolumePanelRow volumePanelRow) {
        ImageView imageView;
        if (volumePanelRow.getIconType() != 2 && volumePanelRow.getIconType() != 10 && volumePanelRow.getIconType() != 13 && volumePanelRow.getIconType() != 12 && volumePanelRow.getIconType() != 15) {
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
        BluetoothUtils.AnonymousClass2 anonymousClass2 = BluetoothUtils.mOnInitCallback;
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = LocalBluetoothManager.getInstance(context, anonymousClass2).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, anonymousClass2).mLocalAdapter.mAdapter.getRemoteDevice(dualBtDeviceAddress));
        Drawable iconDrawable = cachedBluetoothDeviceFindDevice != null ? cachedBluetoothDeviceFindDevice.getIconDrawable(true) : null;
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

    public final void updateContentDescription$1(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        int iconType = volumePanelRow.getIconType();
        String string = this.stream == 2 ? iconType == 0 ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_sound) : volumePanelState.isHasVibrator() ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_vib) : getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_mute) : (iconType == 1 || volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0) ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_unmute, this.label) : getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_mute, this.label);
        string.getClass();
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
        if (subFullLayoutVolumeIcon == null) {
            subFullLayoutVolumeIcon = null;
        }
        subFullLayoutVolumeIcon.setContentDescription(string);
    }

    public final void updateProgress$1(VolumePanelState volumePanelState) {
        VolumePanelRow volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (volumePanelRowFindRow != null) {
            int iViewRealLevel = ViewLevelConverter.viewRealLevel(volumePanelRowFindRow);
            if (!volumePanelRowFindRow.isVisible()) {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).cancel();
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                (subFullLayoutVolumeSeekBar != null ? subFullLayoutVolumeSeekBar : null).setProgress(iViewRealLevel);
            } else {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).setStartValue((this.seekBar != null ? r2 : null).getProgress());
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).animateToFinalPosition(iViewRealLevel);
            }
        }
    }

    public final void updateProgressDrawable$1(VolumePanelState volumePanelState) {
        VolumePanelRow volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (volumePanelRowFindRow != null) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
            if (subFullLayoutVolumeSeekBar == null) {
                subFullLayoutVolumeSeekBar = null;
            }
            SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) subFullLayoutVolumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            secRoundedCornerSeekBarDrawable.setContext(getContext());
            boolean z = volumePanelState.isLeBroadcasting() && !volumePanelRowFindRow.isRoutedToBluetooth();
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int earProtectLevel = volumePanelRowFindRow.getEarProtectLevel();
                int realLevel = volumePanelRowFindRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(volumePanelRowFindRow.getStreamType())) {
                    realLevel *= 100;
                }
                if (1 <= earProtectLevel && earProtectLevel < realLevel && !z) {
                    secRoundedCornerSeekBarDrawable.setShockColor(true);
                    return;
                }
            }
            if (ViewLevelConverter.viewRealLevel(volumePanelRowFindRow) == ViewLevelConverter.viewMaxLevel(volumePanelRowFindRow)) {
                secRoundedCornerSeekBarDrawable.setShockColor(true);
            } else {
                secRoundedCornerSeekBarDrawable.setShockColor(false);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1] */
    public SubFullLayoutVolumeRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = this.this$0.storeInteractor;
                VolumePanelAction.Builder builderStream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.this$0.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.this$0.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(builderStream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = SubFullLayoutVolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SubFullLayoutVolumeRowView.m3221$r8$lambda$oovOMvrowRvnsb1_qcZm9hq0lo(this.f$0);
            }
        });
    }
}
