package com.android.systemui.volume.view.subscreen.full;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.icon.VolumeIcons;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SubFullLayoutVolumeIcon extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int currentMediaIconState;
    public final ColorStateList iconActiveColor;
    public final ColorStateList iconEarShockColor;
    public final ColorStateList iconMutedColor;
    public int iconType;
    public View iconView;
    public boolean isAnimatableIcon;
    public final Lazy storeInteractor$delegate;
    public int stream;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SET_STREAM_VOLUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SET_VOLUME_STATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SubFullLayoutVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.iconActiveColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_color, context);
        this.iconMutedColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_color, context);
        this.iconEarShockColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_earshock_color, context);
        this.currentMediaIconState = -1;
        this.iconType = -1;
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeIcon$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = SubFullLayoutVolumeIcon.$r8$clinit;
                return new StoreInteractor(this.f$0, null, 2, null);
            }
        });
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) throws Resources.NotFoundException {
        VolumePanelRow volumePanelRowFindRow;
        VolumePanelRow volumePanelRowFindRow2;
        VolumePanelState volumePanelState2 = volumePanelState;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()];
        if (i == 1) {
            ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
            return;
        }
        if (i != 2) {
            if (i == 3) {
                if (this.stream == volumePanelState2.getStream() && (volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) != null && VolumeIcons.isAnimatableIcon(this.stream, volumePanelRowFindRow.getIconType())) {
                    updateIconState$1(volumePanelRowFindRow, false);
                    return;
                }
                return;
            }
            if (i == 4) {
                if (this.stream != volumePanelState2.getStream() || (volumePanelRowFindRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) == null) {
                    return;
                }
                updateIconLayout$1(volumePanelRowFindRow2, false);
                updateIconState$1(volumePanelRowFindRow2, true);
                return;
            }
            if (i == 5 && this.isAnimatableIcon && this.stream == volumePanelState2.getStream()) {
                if (VolumePanelValues.isRing(this.stream)) {
                    setSoundIconState$1(volumePanelState2.getIconTargetState(), volumePanelState2.getIconCurrentState(), this.iconType, true);
                    return;
                } else {
                    setMediaIconState$1(volumePanelState2.getIconTargetState(), volumePanelState2.getIconCurrentState(), true);
                    return;
                }
            }
            return;
        }
        VolumePanelRow volumePanelRowFindRow3 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream);
        if (volumePanelRowFindRow3 != null) {
            if (!volumePanelRowFindRow3.isVisible()) {
                volumePanelRowFindRow3 = null;
            }
            if (volumePanelRowFindRow3 != null) {
                updateIconLayout$1(volumePanelRowFindRow3, false);
                int streamType = volumePanelRowFindRow3.getStreamType();
                int iconType = volumePanelRowFindRow3.getIconType();
                if ((!VolumeIcons.isForMediaIcon(streamType) || !VolumeIcons.isAnimatableMediaIconType(iconType)) && !VolumePanelValues.isRing(streamType) && VolumeIcons.isAnimatableIcon(this.stream, volumePanelRowFindRow3.getIconType())) {
                    updateIconState$1(volumePanelRowFindRow3, false);
                }
                int iconType2 = volumePanelRowFindRow3.getIconType();
                View view = this.iconView;
                if (view != null && this.iconType != iconType2 && VolumePanelValues.isRing(this.stream) && iconType2 != 3) {
                    ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
                    ImageView imageView2 = (ImageView) view.requireViewById(R.id.volume_mute_icon);
                    ImageView imageView3 = (ImageView) view.requireViewById(R.id.volume_normal_icon);
                    ImageView imageView4 = (ImageView) view.requireViewById(R.id.volume_sound_icon_wave_l);
                    ImageView imageView5 = (ImageView) view.requireViewById(R.id.volume_sound_icon_wave_s);
                    ImageView imageView6 = (ImageView) view.requireViewById(R.id.volume_vibrate_icon);
                    if (iconType2 == 1) {
                        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                        (subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
                    } else {
                        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                        (subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2).startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
                    }
                    this.currentMediaIconState = 0;
                    this.iconType = iconType2;
                }
                updateIconTintColor(volumePanelState2, volumePanelRowFindRow3);
                updateEnableState$1(volumePanelState2, volumePanelRowFindRow3);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
    }

    public final void setMediaIconState$1(int i, int i2, boolean z) {
        int i3;
        View view = this.iconView;
        if (view == null || i == i2) {
            return;
        }
        int i4 = (!z || i2 == -1) ? i : i - i2 > 0 ? i2 + 1 : i2 - 1;
        ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
        ImageView imageView2 = (ImageView) view.requireViewById(R.id.volume_media_icon_mute);
        ImageView imageView3 = (ImageView) view.requireViewById(R.id.volume_media_icon_note);
        ImageView imageView4 = (ImageView) view.requireViewById(R.id.volume_media_icon_wave_l);
        ImageView imageView5 = (ImageView) view.requireViewById(R.id.volume_media_icon_wave_s);
        if (i4 == 1) {
            i3 = i;
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion).startMinAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView);
        } else if (i4 != 2) {
            if (i4 != 3) {
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView);
            } else {
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView);
            }
            i3 = i;
        } else {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
            i3 = i;
            (subFullLayoutVolumePanelMotion4 == null ? null : subFullLayoutVolumePanelMotion4).startMidAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView);
        }
        this.currentMediaIconState = i3;
    }

    public final void setSoundIconState$1(int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        View view = this.iconView;
        if (view == null) {
            return;
        }
        if (i == i2 && this.iconType == i3) {
            return;
        }
        this.iconType = i3;
        if (!z || i2 == -1 || i == 0) {
            i4 = i;
        } else {
            i4 = i - (i3 == 0 ? 0 : i2) > 0 ? i2 + 1 : i2 - 1;
        }
        ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
        ImageView imageView2 = (ImageView) view.requireViewById(R.id.volume_mute_icon);
        ImageView imageView3 = (ImageView) view.requireViewById(R.id.volume_normal_icon);
        ImageView imageView4 = (ImageView) view.requireViewById(R.id.volume_sound_icon_wave_l);
        ImageView imageView5 = (ImageView) view.requireViewById(R.id.volume_sound_icon_wave_s);
        ImageView imageView6 = (ImageView) view.requireViewById(R.id.volume_vibrate_icon);
        if (VolumePanelValues.isRing(this.stream) && i == 0) {
            if (i3 == 1) {
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
            } else {
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2).startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
            }
        } else {
            if (i4 != 3) {
                if (i4 == 2) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
                    i5 = i;
                    (subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3).startMidAnimation(this.stream, i5, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
                } else {
                    i5 = i;
                    if (i4 == 1) {
                        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
                        (subFullLayoutVolumePanelMotion4 == null ? null : subFullLayoutVolumePanelMotion4).startMinAnimation(this.stream, i5, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
                    }
                }
                this.currentMediaIconState = i5;
            }
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion5 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion5 == null ? null : subFullLayoutVolumePanelMotion5).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
        }
        i5 = i;
        this.currentMediaIconState = i5;
    }

    public final void updateEnableState$1(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        if (volumePanelRow.isSliderEnabled()) {
            setEnabled(true);
            setAlpha(1.0f);
        } else {
            setEnabled(volumePanelRow.isIconEnabled());
            setAlpha(volumePanelRow.isIconEnabled() ? 1.0f : 0.4f);
        }
        if (volumePanelState.isShowA11yStream()) {
            setClickable(isEnabled() && volumePanelRow.isIconClickable());
        }
    }

    public final void updateIconLayout$1(VolumePanelRow volumePanelRow, boolean z) {
        boolean zIsAnimatableIcon = VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType());
        if (z || this.isAnimatableIcon != zIsAnimatableIcon) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View viewInflate = null;
            if (!zIsAnimatableIcon) {
                viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_default_icon, (ViewGroup) null);
            } else if (VolumeIcons.isForMediaIcon(this.stream)) {
                viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_media_icon, (ViewGroup) null);
            } else if (VolumePanelValues.isRing(this.stream)) {
                viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_ringtone_icon, (ViewGroup) null);
            } else {
                View viewInflate2 = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_icon, (ViewGroup) null);
                if (viewInflate2 instanceof ViewGroup) {
                    viewInflate = (ViewGroup) viewInflate2;
                }
            }
            this.iconView = viewInflate;
            addView(viewInflate);
        }
        this.isAnimatableIcon = zIsAnimatableIcon;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0169  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconState$1(VolumePanelRow volumePanelRow, boolean z) throws Resources.NotFoundException {
        Drawable drawable;
        if (!VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType()) || !this.isAnimatableIcon) {
            if (volumePanelRow.isRoutedToBluetooth()) {
                String dualBtDeviceAddress = volumePanelRow.getDualBtDeviceAddress();
                if (TextUtils.isEmpty(dualBtDeviceAddress)) {
                    drawable = null;
                } else {
                    BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
                    Context context = getContext();
                    bluetoothIconUtil.getClass();
                    drawable = BluetoothIconUtil.getServerIconDrawable(context, dualBtDeviceAddress);
                }
            }
            if (drawable == null) {
                drawable = getResources().getDrawable(VolumeIcons.getDefaultIconResId(volumePanelRow.getStreamType(), volumePanelRow.getIconType()), null);
            }
            View view = this.iconView;
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(drawable);
                return;
            }
            return;
        }
        View view2 = this.iconView;
        if (view2 == null) {
            return;
        }
        if (VolumeIcons.isForMediaIcon(this.stream) || VolumePanelValues.isRing(this.stream)) {
            int levelMax = volumePanelRow.getLevelMax() * (VolumeIcons.isForMediaIcon(this.stream) ? 100 : 1);
            int realLevel = volumePanelRow.getRealLevel();
            double d = realLevel;
            double d2 = levelMax;
            int i = d <= 0.5d * d2 ? d > d2 * 0.25d ? 2 : realLevel > 0 ? 1 : 0 : 3;
            if (VolumePanelValues.isRing(this.stream)) {
                setSoundIconState$1(i, this.currentMediaIconState, volumePanelRow.getIconType(), z);
                return;
            } else {
                setMediaIconState$1(i, this.currentMediaIconState, z);
                return;
            }
        }
        ImageView imageView = (ImageView) view2.requireViewById(R.id.volume_normal_icon);
        ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_mute_icon);
        ImageView imageView3 = (ImageView) view2.requireViewById(R.id.volume_vibrate_icon);
        ImageView imageView4 = (ImageView) view2.requireViewById(R.id.volume_icon_mute_splash);
        int streamType = volumePanelRow.getStreamType();
        if (VolumePanelValues.isNotification(streamType)) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mute_mtrl));
            imageView3.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_vibrate_mtrl));
        } else if (VolumePanelValues.isSystem(streamType)) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mute_mtrl));
        }
        int iconType = volumePanelRow.getIconType();
        if (this.iconType != iconType) {
            this.iconType = iconType;
            if (iconType == 0) {
                ViewVisibilityUtil.INSTANCE.getClass();
                ViewVisibilityUtil.setGone(imageView);
                ViewVisibilityUtil.setGone(imageView2);
                ViewVisibilityUtil.setGone(imageView4);
                imageView3.setVisibility(0);
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                (subFullLayoutVolumePanelMotion != null ? subFullLayoutVolumePanelMotion : null).startVibrationAnimation(imageView3);
                return;
            }
            if (iconType != 1) {
                if (iconType != 3) {
                    return;
                }
                ViewVisibilityUtil.INSTANCE.getClass();
                imageView.setVisibility(0);
                ViewVisibilityUtil.setGone(imageView2);
                ViewVisibilityUtil.setGone(imageView4);
                ViewVisibilityUtil.setGone(imageView3);
                return;
            }
            ViewVisibilityUtil.INSTANCE.getClass();
            ViewVisibilityUtil.setGone(imageView);
            imageView2.setVisibility(0);
            imageView4.setVisibility(0);
            ViewVisibilityUtil.setGone(imageView3);
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion2 != null ? subFullLayoutVolumePanelMotion2 : null).getClass();
            SubFullLayoutVolumePanelMotion.startSplashAnimation(imageView4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconTintColor(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        ColorStateList colorStateList;
        View view = this.iconView;
        if (view == null) {
            return;
        }
        if (volumePanelRow.getIconType() != 0 && volumePanelRow.getIconType() != 3 && (volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0)) {
            colorStateList = this.iconMutedColor;
        } else if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
            int realLevel = volumePanelRow.getRealLevel();
            if (VolumePanelValues.isAudioSharing(volumePanelRow.getStreamType())) {
                realLevel *= 100;
            }
            int earProtectLevel = volumePanelRow.getEarProtectLevel();
            colorStateList = (1 > earProtectLevel || earProtectLevel >= realLevel) ? this.iconActiveColor : this.iconEarShockColor;
        }
        if (!VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType())) {
            ImageView imageView = view instanceof ImageView ? (ImageView) view : null;
            if (imageView != null) {
                imageView.setImageTintList(colorStateList);
            }
        } else if (VolumeIcons.isForMediaIcon(this.stream)) {
            ((ImageView) view.requireViewById(R.id.volume_media_icon_note)).setImageTintList(colorStateList);
            ((ImageView) view.requireViewById(R.id.volume_media_icon_wave_l)).setImageTintList(colorStateList);
            ((ImageView) view.requireViewById(R.id.volume_media_icon_wave_s)).setImageTintList(colorStateList);
        } else if (VolumePanelValues.isRing(this.stream)) {
            ((ImageView) view.requireViewById(R.id.volume_normal_icon)).setImageTintList(colorStateList);
            ((ImageView) view.requireViewById(R.id.volume_sound_icon_wave_l)).setImageTintList(colorStateList);
            ((ImageView) view.requireViewById(R.id.volume_sound_icon_wave_s)).setImageTintList(colorStateList);
        }
        ImageView imageView2 = (ImageView) view.findViewById(R.id.volume_icon_mute_splash);
        if (imageView2 != null) {
            imageView2.setImageTintList(colorStateList);
        }
    }
}
