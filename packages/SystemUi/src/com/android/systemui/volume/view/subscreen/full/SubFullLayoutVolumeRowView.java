package com.android.systemui.volume.view.subscreen.full;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
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
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.ViewLevelConverter;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubFullLayoutVolumeRowView extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public ImageView bluetoothDeviceIcon;
    public int earProtectLevel;
    public HandlerWrapper handlerWrapper;
    public SubFullLayoutVolumeIcon icon;
    public boolean iconClickable;
    public boolean isAODEnabled;
    public boolean isDualViewEnabled;
    public boolean isInExpandView;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1] */
    public SubFullLayoutVolumeRowView(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = SubFullLayoutVolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(SubFullLayoutVolumeRowView.this.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = SubFullLayoutVolumeRowView.this.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
                final SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = SubFullLayoutVolumeRowView.this;
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
                    public final void onAnimationUpdate(float f, float f2) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = SubFullLayoutVolumeRowView.this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setProgress((int) f);
                    }
                });
                return springAnimation;
            }
        });
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

    /* JADX WARN: Removed duplicated region for block: B:102:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0104 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(VolumePanelStore volumePanelStore, HandlerWrapper handlerWrapper, VolumePanelRow volumePanelRow, VolumePanelState volumePanelState, SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion, boolean z) {
        boolean z2;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar3;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar4;
        String str;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar5;
        ViewGroup viewGroup;
        boolean z3;
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2;
        StoreInteractor storeInteractor = this.storeInteractor;
        storeInteractor.store = volumePanelStore;
        storeInteractor.observeStore();
        this.handlerWrapper = handlerWrapper;
        this.stream = volumePanelRow.getStreamType();
        this.volumePanelMotion = subFullLayoutVolumePanelMotion;
        this.isInExpandView = z;
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar6 = (SubFullLayoutVolumeSeekBar) findViewById(R.id.volume_seekbar);
        this.seekBar = subFullLayoutVolumeSeekBar6;
        subFullLayoutVolumeSeekBar6.stream = this.stream;
        subFullLayoutVolumeSeekBar6.isTracking = false;
        StoreInteractor storeInteractor2 = subFullLayoutVolumeSeekBar6.storeInteractor;
        storeInteractor2.store = volumePanelStore;
        storeInteractor2.observeStore();
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = (SubFullLayoutVolumeIcon) findViewById(R.id.volume_button);
        this.icon = subFullLayoutVolumeIcon;
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion3 == null) {
            subFullLayoutVolumePanelMotion3 = null;
        }
        ((StoreInteractor) subFullLayoutVolumeIcon.storeInteractor$delegate.getValue()).store = volumePanelStore;
        ((StoreInteractor) subFullLayoutVolumeIcon.storeInteractor$delegate.getValue()).observeStore();
        subFullLayoutVolumeIcon.volumePanelMotion = subFullLayoutVolumePanelMotion3;
        int streamType = volumePanelRow.getStreamType();
        subFullLayoutVolumeIcon.stream = streamType;
        if (VolumePanelValues.isRing(streamType)) {
            subFullLayoutVolumeIcon.iconType = volumePanelRow.getIconType();
        }
        boolean z4 = true;
        subFullLayoutVolumeIcon.updateIconLayout(volumePanelRow, true);
        subFullLayoutVolumeIcon.updateIconState(volumePanelRow, false);
        subFullLayoutVolumeIcon.updateIconTintColor(volumePanelState, volumePanelRow);
        subFullLayoutVolumeIcon.updateEnableState(volumePanelState, volumePanelRow);
        this.seekBarBackground = (ViewGroup) findViewById(R.id.volume_seekbar_background);
        if (volumePanelState.isDualAudio()) {
            if (volumePanelState.isMultiSoundBt() ? VolumePanelValues.isMultiSound(volumePanelState.getActiveStream()) : VolumePanelValues.isMusic(volumePanelState.getActiveStream())) {
                z2 = true;
                this.isDualViewEnabled = z2;
                this.isAODEnabled = volumePanelState.isAodVolumePanel();
                subFullLayoutVolumeSeekBar = this.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                subFullLayoutVolumeSeekBar.semSetMin(ViewLevelConverter.viewMinLevel(volumePanelRow));
                subFullLayoutVolumeSeekBar2 = this.seekBar;
                if (subFullLayoutVolumeSeekBar2 == null) {
                    subFullLayoutVolumeSeekBar2 = null;
                }
                subFullLayoutVolumeSeekBar2.setMax(ViewLevelConverter.viewMaxLevel(volumePanelRow));
                subFullLayoutVolumeSeekBar3 = this.seekBar;
                if (subFullLayoutVolumeSeekBar3 == null) {
                    subFullLayoutVolumeSeekBar3 = null;
                }
                subFullLayoutVolumeSeekBar3.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow), true);
                subFullLayoutVolumeSeekBar4 = this.seekBar;
                if (subFullLayoutVolumeSeekBar4 == null) {
                    subFullLayoutVolumeSeekBar4 = null;
                }
                subFullLayoutVolumeSeekBar4.setEnabled(volumePanelRow.isSliderEnabled());
                this.iconClickable = volumePanelRow.isIconClickable();
                if (!VolumePanelValues.isSmartView(volumePanelRow.getStreamType()) && !TextUtils.isEmpty(volumePanelRow.getSmartViewLabel())) {
                    str = volumePanelRow.getSmartViewLabel();
                } else if (volumePanelRow.isDynamic()) {
                    try {
                        str = getContext().getString(getContext().getResources().getIdentifier(volumePanelRow.getNameRes(), null, null));
                    } catch (Exception unused) {
                        str = "";
                    }
                    if (volumePanelState.isRemoteMic()) {
                        if (VolumePanelValues.isBluetoothSco(volumePanelRow.getStreamType())) {
                            str = getContext().getString(R.string.volume_amplify_ambient_sound_title);
                        } else if (VolumePanelValues.isMusic(volumePanelRow.getStreamType()) && !volumePanelState.isBtScoOn()) {
                            str = getContext().getString(R.string.volume_amplify_ambient_sound_title);
                        }
                    }
                    int streamType2 = volumePanelRow.getStreamType();
                    String remoteLabel = volumePanelRow.getRemoteLabel();
                    if (!TextUtils.isEmpty(remoteLabel) && (VolumePanelValues.isMusic(streamType2) || VolumePanelValues.isDualAudio(streamType2) || VolumePanelValues.isBluetoothSco(streamType2) || VolumePanelValues.isMultiSound(streamType2) || VolumePanelValues.isAudioSharing(streamType2) || VolumePanelValues.isVoiceCall(streamType2))) {
                        str = str + " (" + remoteLabel + ")";
                    }
                } else {
                    str = volumePanelRow.getRemoteLabel();
                }
                this.label = str;
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
                        subFullLayoutVolumeIcon3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$initialize$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SubFullLayoutVolumeRowView.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(SubFullLayoutVolumeRowView.this.stream).isFromOutside(true).build(), false);
                            }
                        });
                        updateContentDescription(volumePanelState, volumePanelRow);
                        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon4 = this.icon;
                        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon5 = subFullLayoutVolumeIcon4 == null ? null : subFullLayoutVolumeIcon4;
                        if (subFullLayoutVolumeIcon4 == null) {
                            subFullLayoutVolumeIcon4 = null;
                        }
                        subFullLayoutVolumeIcon5.setClickable(subFullLayoutVolumeIcon4.isEnabled() && this.iconClickable);
                    }
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar7 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar7 == null) {
                        subFullLayoutVolumeSeekBar7 = null;
                    }
                    subFullLayoutVolumeSeekBar7.setContentDescription(this.label);
                }
                ImageView imageView = (ImageView) findViewById(R.id.volume_panel_bluetooth_device_icon);
                this.bluetoothDeviceIcon = imageView;
                imageView.setImageTintList(new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(R.color.volume_panel_bluetooth_device_icon_color, null)}));
                updateBluetoothDeviceIcon(volumePanelRow);
                int dimenInt = ((!volumePanelState.isZenMode() || volumePanelState.isAllSoundOff()) || !this.isInExpandView) ? ContextUtils.getDimenInt(R.dimen.sub_full_volume_seekbar_height, getContext()) : ContextUtils.getDimenInt(R.dimen.sub_full_volume_seekbar_height_dnd, getContext());
                subFullLayoutVolumeSeekBar5 = this.seekBar;
                if (subFullLayoutVolumeSeekBar5 == null) {
                    subFullLayoutVolumeSeekBar5 = null;
                }
                subFullLayoutVolumeSeekBar5.getLayoutParams().width = dimenInt;
                viewGroup = this.seekBarBackground;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                viewGroup.getLayoutParams().height = dimenInt;
                findViewById(R.id.volume_panel_blur).getLayoutParams().height = dimenInt;
                z3 = !volumePanelState.isExpanded() || this.isDualViewEnabled;
                ImageView imageView2 = (ImageView) findViewById(R.id.volume_panel_status_icon);
                if (!z3) {
                    imageView2.setImageTintList(new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(R.color.volume_panel_status_message_color, null)}));
                }
                if (!z3 || (!volumePanelState.isAllSoundOff() && !volumePanelState.isZenMode() && !volumePanelState.isLeBroadcasting())) {
                    z4 = false;
                }
                if (z4) {
                    ViewVisibilityUtil.INSTANCE.getClass();
                    ViewVisibilityUtil.setGone(imageView2);
                } else {
                    ViewVisibilityUtil.INSTANCE.getClass();
                    imageView2.setVisibility(0);
                }
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ViewGroup) findViewById(R.id.volume_icon_area)).getLayoutParams();
                if (z3) {
                    marginLayoutParams.bottomMargin = ContextUtils.getDimenInt(R.dimen.sub_full_volume_seekbar_elevation_padding, getContext());
                    int dimenInt2 = ContextUtils.getDimenInt(R.dimen.sub_full_volume_icon_touch_padding, getContext());
                    SubFullLayoutVolumeIcon subFullLayoutVolumeIcon6 = this.icon;
                    if (subFullLayoutVolumeIcon6 == null) {
                        subFullLayoutVolumeIcon6 = null;
                    }
                    subFullLayoutVolumeIcon6.setPadding(dimenInt2, dimenInt2, dimenInt2, dimenInt2);
                } else {
                    int dimenInt3 = ContextUtils.getDimenInt(R.dimen.sub_full_volume_panel_icon_margin_touch_expanded, getContext());
                    int dimenInt4 = ContextUtils.getDimenInt(R.dimen.sub_full_volume_panel_icon_margin_bottom_expanded, getContext());
                    marginLayoutParams.bottomMargin = 0;
                    SubFullLayoutVolumeIcon subFullLayoutVolumeIcon7 = this.icon;
                    if (subFullLayoutVolumeIcon7 == null) {
                        subFullLayoutVolumeIcon7 = null;
                    }
                    ViewGroup.LayoutParams layoutParams = subFullLayoutVolumeIcon7.getLayoutParams();
                    layoutParams.height = ContextUtils.getDimenInt(R.dimen.sub_full_volume_icon_touch_height, getContext());
                    layoutParams.width = ContextUtils.getDimenInt(R.dimen.sub_full_volume_icon_touch_width, getContext());
                    SubFullLayoutVolumeIcon subFullLayoutVolumeIcon8 = this.icon;
                    if (subFullLayoutVolumeIcon8 == null) {
                        subFullLayoutVolumeIcon8 = null;
                    }
                    subFullLayoutVolumeIcon8.setPadding(dimenInt3, dimenInt4, dimenInt3, dimenInt3);
                }
                ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.volume_seekbar_container);
                if (z3) {
                    int dimenInt5 = ContextUtils.getDimenInt(R.dimen.sub_full_volume_seekbar_elevation_padding, getContext());
                    viewGroup2.setPadding(dimenInt5, dimenInt5, dimenInt5, dimenInt5);
                    viewGroup2.setClipChildren(false);
                } else {
                    viewGroup2.setPadding(0, 0, 0, ContextUtils.getDimenInt(R.dimen.sub_full_volume_expand_panel_horizontal_padding_min, getContext()));
                }
                if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    ViewGroup viewGroup3 = this.seekBarBackground;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    viewGroup3.setBackground(getContext().getDrawable(R.drawable.sub_full_volume_seekbar_bg_blur));
                } else {
                    ViewGroup viewGroup4 = this.seekBarBackground;
                    if (viewGroup4 == null) {
                        viewGroup4 = null;
                    }
                    viewGroup4.setBackground(getContext().getDrawable(R.drawable.sub_full_volume_seekbar_bg_reduce_transparency));
                }
                if (!z3) {
                    ViewGroup viewGroup5 = this.seekBarBackground;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    viewGroup5.setElevation(ContextUtils.getDimenInt(R.dimen.sub_full_volume_seekbar_elevation, getContext()));
                }
                if (z3) {
                    if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar8 = this.seekBar;
                        if (subFullLayoutVolumeSeekBar8 == null) {
                            subFullLayoutVolumeSeekBar8 = null;
                        }
                        subFullLayoutVolumeSeekBar8.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.sub_full_volume_seekbar_drawable_reduce_transparency, null));
                    } else {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar9 = this.seekBar;
                        if (subFullLayoutVolumeSeekBar9 == null) {
                            subFullLayoutVolumeSeekBar9 = null;
                        }
                        subFullLayoutVolumeSeekBar9.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.sub_full_volume_seekbar_drawable_blur, null));
                    }
                } else if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar10 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar10 == null) {
                        subFullLayoutVolumeSeekBar10 = null;
                    }
                    subFullLayoutVolumeSeekBar10.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.sub_full_volume_seekbar_drawable_expand_reduce_transparency, null));
                } else {
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar11 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar11 == null) {
                        subFullLayoutVolumeSeekBar11 = null;
                    }
                    subFullLayoutVolumeSeekBar11.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.sub_full_volume_seekbar_drawable_expand_blur, null));
                }
                subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                if (subFullLayoutVolumePanelMotion2 == null) {
                    subFullLayoutVolumePanelMotion2 = null;
                }
                subFullLayoutVolumePanelMotion2.getClass();
                this.touchDownAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchDownAnimation(this);
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion4 != null ? subFullLayoutVolumePanelMotion4 : null).getClass();
                this.touchUpAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchUpAnimation(this);
            }
        }
        z2 = false;
        this.isDualViewEnabled = z2;
        this.isAODEnabled = volumePanelState.isAodVolumePanel();
        subFullLayoutVolumeSeekBar = this.seekBar;
        if (subFullLayoutVolumeSeekBar == null) {
        }
        subFullLayoutVolumeSeekBar.semSetMin(ViewLevelConverter.viewMinLevel(volumePanelRow));
        subFullLayoutVolumeSeekBar2 = this.seekBar;
        if (subFullLayoutVolumeSeekBar2 == null) {
        }
        subFullLayoutVolumeSeekBar2.setMax(ViewLevelConverter.viewMaxLevel(volumePanelRow));
        subFullLayoutVolumeSeekBar3 = this.seekBar;
        if (subFullLayoutVolumeSeekBar3 == null) {
        }
        subFullLayoutVolumeSeekBar3.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow), true);
        subFullLayoutVolumeSeekBar4 = this.seekBar;
        if (subFullLayoutVolumeSeekBar4 == null) {
        }
        subFullLayoutVolumeSeekBar4.setEnabled(volumePanelRow.isSliderEnabled());
        this.iconClickable = volumePanelRow.isIconClickable();
        if (!VolumePanelValues.isSmartView(volumePanelRow.getStreamType())) {
        }
        if (volumePanelRow.isDynamic()) {
        }
        this.label = str;
        if (volumePanelState.isShowA11yStream()) {
        }
        ImageView imageView3 = (ImageView) findViewById(R.id.volume_panel_bluetooth_device_icon);
        this.bluetoothDeviceIcon = imageView3;
        imageView3.setImageTintList(new ColorStateList(new int[][]{new int[0]}, new int[]{getContext().getResources().getColor(R.color.volume_panel_bluetooth_device_icon_color, null)}));
        updateBluetoothDeviceIcon(volumePanelRow);
        if (!volumePanelState.isZenMode() || volumePanelState.isAllSoundOff()) {
        }
        subFullLayoutVolumeSeekBar5 = this.seekBar;
        if (subFullLayoutVolumeSeekBar5 == null) {
        }
        subFullLayoutVolumeSeekBar5.getLayoutParams().width = dimenInt;
        viewGroup = this.seekBarBackground;
        if (viewGroup == null) {
        }
        viewGroup.getLayoutParams().height = dimenInt;
        findViewById(R.id.volume_panel_blur).getLayoutParams().height = dimenInt;
        if (volumePanelState.isExpanded()) {
        }
        ImageView imageView22 = (ImageView) findViewById(R.id.volume_panel_status_icon);
        if (!z3) {
        }
        if (!z3) {
        }
        z4 = false;
        if (z4) {
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) ((ViewGroup) findViewById(R.id.volume_icon_area)).getLayoutParams();
        if (z3) {
        }
        ViewGroup viewGroup22 = (ViewGroup) findViewById(R.id.volume_seekbar_container);
        if (z3) {
        }
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
        }
        if (!z3) {
        }
        if (z3) {
        }
        subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion2 == null) {
        }
        subFullLayoutVolumePanelMotion2.getClass();
        this.touchDownAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchDownAnimation(this);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion42 = this.volumePanelMotion;
        (subFullLayoutVolumePanelMotion42 != null ? subFullLayoutVolumePanelMotion42 : null).getClass();
        this.touchUpAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchUpAnimation(this);
    }

    public final boolean isIconClicked(float f, float f2) {
        if (!this.startProgress && this.touchDownIcon) {
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
            if (subFullLayoutVolumeIcon == null) {
                subFullLayoutVolumeIcon = null;
            }
            if (subFullLayoutVolumeIcon.isEnabled() && this.iconClickable) {
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon2 = this.icon;
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon3 = subFullLayoutVolumeIcon2 != null ? subFullLayoutVolumeIcon2 : null;
                viewUtil.getClass();
                if (ViewUtil.isTouched(subFullLayoutVolumeIcon3, f, f2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        int earProtectLevel;
        VolumePanelState volumePanelState2 = volumePanelState;
        boolean z = false;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
                if (volumePanelStateExt.isRowVisible(volumePanelState2, this.stream)) {
                    VolumePanelRow findRow = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow != null && (earProtectLevel = findRow.getEarProtectLevel()) != this.earProtectLevel) {
                        this.earProtectLevel = earProtectLevel;
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                        if (subFullLayoutVolumeSeekBar == null) {
                            subFullLayoutVolumeSeekBar = null;
                        }
                        subFullLayoutVolumeSeekBar.getClass();
                    }
                    VolumePanelRow findRow2 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow2 != null) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setEnabled(findRow2.isSliderEnabled());
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
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar3 = this.seekBar;
                    storeInteractor.sendAction(stream.progress((subFullLayoutVolumeSeekBar3 != null ? subFullLayoutVolumeSeekBar3 : null).getProgress()).build(), true);
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
                    (handlerWrapper2 != null ? handlerWrapper2 : null).postDelayed(1000L, this.recheckCallback);
                    break;
                }
                break;
            case 5:
                if (this.stream == volumePanelState2.getStream()) {
                    StoreInteractor storeInteractor2 = this.storeInteractor;
                    VolumePanelAction.Builder stream2 = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.stream);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar4 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar4 == null) {
                        subFullLayoutVolumeSeekBar4 = null;
                    }
                    storeInteractor2.sendAction(stream2.progress(subFullLayoutVolumeSeekBar4.getProgress()).build(), true);
                    HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                    if (handlerWrapper3 == null) {
                        handlerWrapper3 = null;
                    }
                    handlerWrapper3.remove(this.recheckCallback);
                    HandlerWrapper handlerWrapper4 = this.handlerWrapper;
                    (handlerWrapper4 != null ? handlerWrapper4 : null).postDelayed(1000L, this.recheckCallback);
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
                    z = true;
                }
                if (z) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    SpringAnimation springAnimation = this.touchDownAnimation;
                    if (springAnimation == null) {
                        springAnimation = null;
                    }
                    SpringAnimation springAnimation2 = this.touchUpAnimation;
                    SpringAnimation springAnimation3 = springAnimation2 != null ? springAnimation2 : null;
                    subFullLayoutVolumePanelMotion.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchDownAnimation(springAnimation, springAnimation3, true);
                    break;
                }
                break;
            case 9:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    z = true;
                }
                if (z) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    SpringAnimation springAnimation4 = this.touchUpAnimation;
                    if (springAnimation4 == null) {
                        springAnimation4 = null;
                    }
                    SpringAnimation springAnimation5 = this.touchDownAnimation;
                    SpringAnimation springAnimation6 = springAnimation5 != null ? springAnimation5 : null;
                    subFullLayoutVolumePanelMotion2.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchUpAnimation(springAnimation4, springAnimation6);
                    break;
                }
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
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((VolumePanelRow) it.next()).getSmartViewLabel());
                    }
                    String str = (String) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
                    if (str == null) {
                        str = "";
                    }
                    Toast.makeText(getContext(), getContext().getString(R.string.volume_use_your_phone_volume_smart_view, str), 0).show();
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

    public final void updateBluetoothDeviceIcon(VolumePanelRow volumePanelRow) {
        Drawable drawable;
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
        int iconType = volumePanelRow.getIconType();
        if (iconType == 10) {
            drawable = getContext().getResources().getDrawable(R.drawable.tw_ic_audio_buds, null);
        } else if (iconType != 13) {
            Context context = getContext();
            BluetoothUtils.C09152 c09152 = BluetoothUtils.mOnInitCallback;
            drawable = BluetoothUtils.getHostOverlayIconDrawable(context, LocalBluetoothManager.getInstance(context, c09152).mCachedDeviceManager.findDevice(LocalBluetoothManager.getInstance(context, c09152).mLocalAdapter.mAdapter.getRemoteDevice(dualBtDeviceAddress)));
        } else {
            drawable = getContext().getResources().getDrawable(R.drawable.tw_ic_audio_buds3, null);
        }
        ImageView imageView3 = this.bluetoothDeviceIcon;
        if (imageView3 == null) {
            imageView3 = null;
        }
        imageView3.setImageDrawable(drawable);
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView4 = this.bluetoothDeviceIcon;
        imageView = imageView4 != null ? imageView4 : null;
        viewVisibilityUtil2.getClass();
        imageView.setVisibility(0);
    }

    public final void updateContentDescription(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        int iconType = volumePanelRow.getIconType();
        String string = this.stream == 2 ? iconType == 0 ? getContext().getString(R.string.volume_icon_content_description_ringtone_to_sound) : volumePanelState.isHasVibrator() ? getContext().getString(R.string.volume_icon_content_description_ringtone_to_vib) : getContext().getString(R.string.volume_icon_content_description_ringtone_to_mute) : (iconType == 1 || volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0) ? getContext().getString(R.string.volume_icon_content_description_to_unmute, this.label) : getContext().getString(R.string.volume_icon_content_description_to_mute, this.label);
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
        if (subFullLayoutVolumeIcon == null) {
            subFullLayoutVolumeIcon = null;
        }
        subFullLayoutVolumeIcon.setContentDescription(string);
    }

    public final void updateProgress(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (findRow != null) {
            int viewRealLevel = ViewLevelConverter.viewRealLevel(findRow);
            if (!findRow.isVisible()) {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).cancel();
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                (subFullLayoutVolumeSeekBar != null ? subFullLayoutVolumeSeekBar : null).setProgress(viewRealLevel);
            } else {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).setStartValue((this.seekBar != null ? r2 : null).getProgress());
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).animateToFinalPosition(viewRealLevel);
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
                StoreInteractor storeInteractor = SubFullLayoutVolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(SubFullLayoutVolumeRowView.this.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = SubFullLayoutVolumeRowView.this.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
                final SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = SubFullLayoutVolumeRowView.this;
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
                    public final void onAnimationUpdate(float f, float f2) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = SubFullLayoutVolumeRowView.this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setProgress((int) f);
                    }
                });
                return springAnimation;
            }
        });
    }
}
