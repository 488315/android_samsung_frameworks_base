package com.android.systemui.volume.view.subscreen.full;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ColorUtils;
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

public final class SubFullLayoutVolumeIcon extends FrameLayout implements VolumeObserver<VolumePanelState> {
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeIcon$storeInteractor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new StoreInteractor(SubFullLayoutVolumeIcon.this, null, 2, null);
            }
        });
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        VolumePanelRow findRow;
        VolumePanelRow findRow2;
        VolumePanelState volumePanelState2 = volumePanelState;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()];
        if (i == 1) {
            ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
            return;
        }
        if (i != 2) {
            if (i == 3) {
                if (this.stream == volumePanelState2.getStream() && (findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) != null && VolumeIcons.isAnimatableIcon(this.stream, findRow.getIconType())) {
                    updateIconState$1(findRow, false);
                    return;
                }
                return;
            }
            if (i == 4) {
                if (this.stream != volumePanelState2.getStream() || (findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) == null) {
                    return;
                }
                updateIconLayout$1(findRow2, false);
                updateIconState$1(findRow2, true);
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
        VolumePanelRow findRow3 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream);
        if (findRow3 != null) {
            if (!findRow3.isVisible()) {
                findRow3 = null;
            }
            if (findRow3 != null) {
                updateIconLayout$1(findRow3, false);
                int streamType = findRow3.getStreamType();
                int iconType = findRow3.getIconType();
                if ((!VolumeIcons.isForMediaIcon(streamType) || !VolumeIcons.isAnimatableMediaIconType(iconType)) && !VolumePanelValues.isRing(streamType) && VolumeIcons.isAnimatableIcon(this.stream, findRow3.getIconType())) {
                    updateIconState$1(findRow3, false);
                }
                int iconType2 = findRow3.getIconType();
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
                updateIconTintColor(volumePanelState2, findRow3);
                updateEnableState$1(volumePanelState2, findRow3);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
    }

    public final void setMediaIconState$1(int i, int i2, boolean z) {
        View view = this.iconView;
        if (view == null || i == i2) {
            return;
        }
        int i3 = (!z || i2 == -1) ? i : i - i2 > 0 ? i2 + 1 : i2 - 1;
        ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
        ImageView imageView2 = (ImageView) view.requireViewById(R.id.volume_media_icon_mute);
        ImageView imageView3 = (ImageView) view.requireViewById(R.id.volume_media_icon_note);
        ImageView imageView4 = (ImageView) view.requireViewById(R.id.volume_media_icon_wave_l);
        ImageView imageView5 = (ImageView) view.requireViewById(R.id.volume_media_icon_wave_s);
        if (i3 == 1) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion).startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView);
        } else if (i3 == 2) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2).startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView);
        } else if (i3 != 3) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView);
        } else {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion4 == null ? null : subFullLayoutVolumePanelMotion4).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView);
        }
        this.currentMediaIconState = i;
    }

    public final void setSoundIconState$1(int i, int i2, int i3, boolean z) {
        int i4;
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
        } else if (i4 == 3) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
        } else if (i4 == 2) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion4 == null ? null : subFullLayoutVolumePanelMotion4).startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
        } else if (i4 == 1) {
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion5 = this.volumePanelMotion;
            (subFullLayoutVolumePanelMotion5 == null ? null : subFullLayoutVolumePanelMotion5).startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView);
        }
        this.currentMediaIconState = i;
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
        boolean isAnimatableIcon = VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType());
        if (z || this.isAnimatableIcon != isAnimatableIcon) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View view = null;
            if (!isAnimatableIcon) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_default_icon, (ViewGroup) null);
            } else if (VolumeIcons.isForMediaIcon(this.stream)) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_media_icon, (ViewGroup) null);
            } else if (VolumePanelValues.isRing(this.stream)) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_ringtone_icon, (ViewGroup) null);
            } else {
                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_animated_icon, (ViewGroup) null);
                if (inflate instanceof ViewGroup) {
                    view = (ViewGroup) inflate;
                }
            }
            this.iconView = view;
            addView(view);
        }
        this.isAnimatableIcon = isAnimatableIcon;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIconState$1(com.samsung.systemui.splugins.volume.VolumePanelRow r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeIcon.updateIconState$1(com.samsung.systemui.splugins.volume.VolumePanelRow, boolean):void");
    }

    public final void updateIconTintColor(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        ColorStateList colorStateList;
        View view = this.iconView;
        if (view == null) {
            return;
        }
        if (volumePanelRow.getIconType() == 0 || volumePanelRow.getIconType() == 3 || !(volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0)) {
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int realLevel = volumePanelRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(volumePanelRow.getStreamType())) {
                    realLevel *= 100;
                }
                int earProtectLevel = volumePanelRow.getEarProtectLevel();
                if (1 <= earProtectLevel && earProtectLevel < realLevel) {
                    colorStateList = this.iconEarShockColor;
                }
            }
            colorStateList = this.iconActiveColor;
        } else {
            colorStateList = this.iconMutedColor;
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
