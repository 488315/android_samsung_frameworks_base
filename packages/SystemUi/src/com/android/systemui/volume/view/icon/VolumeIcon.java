package com.android.systemui.volume.view.icon;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeIcon extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public int currentMediaIconState;
    public ColorStateList iconActiveColor;
    public ColorStateList iconEarShockColor;
    public ColorStateList iconMutedColor;
    public int iconType;
    public View iconView;
    public boolean isAnimatedType;
    public boolean isSubScreen;
    public boolean shouldUpdateIcon;
    public final StoreInteractor storeInteractor;
    public int stream;
    public VolumeIconMotion volumeIconMotion;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public VolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.iconActiveColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_color, context);
        this.iconMutedColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_color, context);
        this.iconEarShockColor = ColorUtils.getSingleColorStateList(R.color.volume_icon_earshock_color, context);
        this.currentMediaIconState = -1;
        this.iconType = -1;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    public final void initialize(VolumePanelStore volumePanelStore, VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        StoreInteractor storeInteractor = this.storeInteractor;
        storeInteractor.store = volumePanelStore;
        storeInteractor.observeStore();
        this.volumeIconMotion = new VolumeIconMotion(volumePanelStore, getContext());
        this.stream = volumePanelRow.getStreamType();
        this.isSubScreen = !BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && volumePanelState.isShowingSubDisplayVolumePanel();
        if (VolumePanelValues.isRing(this.stream)) {
            this.iconType = volumePanelRow.getIconType();
        }
        if (this.isSubScreen) {
            Context context = getContext();
            boolean z = BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG;
            int i = R.color.sub_large_display_volume_icon_color;
            this.iconActiveColor = ColorUtils.getSingleColorStateList(z ? R.color.sub_large_display_volume_icon_color : R.color.sub_display_volume_progress_color, context);
            Context context2 = getContext();
            if (!z) {
                i = R.color.sub_display_volume_progress_bg_color;
            }
            this.iconMutedColor = ColorUtils.getSingleColorStateList(i, context2);
            this.iconEarShockColor = ColorUtils.getSingleColorStateList(R.color.sub_display_volume_progress_earshock_color, getContext());
        }
        updateIconLayout(volumePanelRow, true);
        updateIconState(volumePanelRow, false);
        updateIconTintColor(volumePanelState, volumePanelRow);
        updateEnableState(volumePanelState, volumePanelRow);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        VolumePanelRow findRow;
        VolumePanelRow findRow2;
        VolumePanelState volumePanelState2 = volumePanelState;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()];
        if (i == 1) {
            this.storeInteractor.dispose();
            return;
        }
        if (i != 2) {
            if (i == 3) {
                if (this.stream != volumePanelState2.getStream() || (findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) == null) {
                    return;
                }
                updateIconState(findRow, false);
                return;
            }
            if (i == 4) {
                if (this.stream != volumePanelState2.getStream() || (findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, this.stream)) == null) {
                    return;
                }
                updateIconLayout(findRow2, false);
                updateIconState(findRow2, true);
                return;
            }
            if (i == 5 && this.isAnimatedType && this.stream == volumePanelState2.getStream()) {
                int iconTargetState = volumePanelState2.getIconTargetState();
                int iconCurrentState = volumePanelState2.getIconCurrentState();
                if (VolumePanelValues.isRing(this.stream) || VolumePanelValues.isAlarm(this.stream)) {
                    setSoundIconState(iconTargetState, iconCurrentState, this.iconType, true);
                    return;
                } else {
                    setMediaIconState(iconTargetState, iconCurrentState, true);
                    return;
                }
            }
            return;
        }
        List<VolumePanelRow> volumeRowList = volumePanelState2.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        for (Object obj : volumeRowList) {
            VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
            if (volumePanelRow.getStreamType() == this.stream && volumePanelRow.isVisible()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            VolumePanelRow volumePanelRow2 = (VolumePanelRow) it.next();
            updateIconLayout(volumePanelRow2, false);
            if (this.shouldUpdateIcon || !VolumeIcons.isWaveAnimatableIcon(this.stream, volumePanelRow2.getIconType())) {
                updateIconState(volumePanelRow2, false);
            }
            int iconType = volumePanelRow2.getIconType();
            if (this.iconType != iconType && VolumePanelValues.isRing(this.stream) && iconType != 3) {
                View view = this.iconView;
                if (view == null) {
                    view = null;
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.volume_icon_mute_splash);
                View view2 = this.iconView;
                if (view2 == null) {
                    view2 = null;
                }
                ImageView imageView2 = (ImageView) view2.findViewById(R.id.volume_mute_icon);
                View view3 = this.iconView;
                if (view3 == null) {
                    view3 = null;
                }
                ImageView imageView3 = (ImageView) view3.findViewById(R.id.volume_normal_icon);
                View view4 = this.iconView;
                if (view4 == null) {
                    view4 = null;
                }
                ImageView imageView4 = (ImageView) view4.findViewById(R.id.volume_sound_icon_wave_l);
                View view5 = this.iconView;
                if (view5 == null) {
                    view5 = null;
                }
                ImageView imageView5 = (ImageView) view5.findViewById(R.id.volume_sound_icon_wave_s);
                View view6 = this.iconView;
                if (view6 == null) {
                    view6 = null;
                }
                ImageView imageView6 = (ImageView) view6.findViewById(R.id.volume_vibrate_icon);
                ScreenState screenState = ScreenState.SCREEN_NORMAL;
                if (this.isSubScreen) {
                    screenState = !BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? ScreenState.SCREEN_SUB_DISPLAY : ScreenState.SCREEN_SUB_LARGE_DISPLAY;
                }
                ScreenState screenState2 = screenState;
                if (iconType == 1) {
                    VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                    if (volumeIconMotion == null) {
                        volumeIconMotion = null;
                    }
                    volumeIconMotion.startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState2);
                } else {
                    VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                    if (volumeIconMotion2 == null) {
                        volumeIconMotion2 = null;
                    }
                    volumeIconMotion2.startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
                }
                this.shouldUpdateIcon = false;
                this.currentMediaIconState = 0;
                this.iconType = iconType;
            }
            updateIconTintColor(volumePanelState2, volumePanelRow2);
            updateEnableState(volumePanelState2, volumePanelRow2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
    }

    public final void setMediaIconState(int i, int i2, boolean z) {
        if (i != i2 || this.shouldUpdateIcon) {
            int i3 = (!z || i2 == -1) ? i : i - i2 > 0 ? i2 + 1 : i2 - 1;
            View view = this.iconView;
            if (view == null) {
                view = null;
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.volume_icon_mute_splash);
            View view2 = this.iconView;
            if (view2 == null) {
                view2 = null;
            }
            ImageView imageView2 = (ImageView) view2.findViewById(R.id.volume_media_icon_mute);
            View view3 = this.iconView;
            if (view3 == null) {
                view3 = null;
            }
            ImageView imageView3 = (ImageView) view3.findViewById(R.id.volume_media_icon_note);
            View view4 = this.iconView;
            if (view4 == null) {
                view4 = null;
            }
            ImageView imageView4 = (ImageView) view4.findViewById(R.id.volume_media_icon_wave_l);
            View view5 = this.iconView;
            if (view5 == null) {
                view5 = null;
            }
            ImageView imageView5 = (ImageView) view5.findViewById(R.id.volume_media_icon_wave_s);
            ScreenState screenState = ScreenState.SCREEN_NORMAL;
            if (this.isSubScreen) {
                screenState = BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? ScreenState.SCREEN_SUB_LARGE_DISPLAY : ScreenState.SCREEN_SUB_DISPLAY;
            }
            ScreenState screenState2 = screenState;
            if (i3 == 1) {
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState2);
            } else if (i3 == 2) {
                VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                (volumeIconMotion2 == null ? null : volumeIconMotion2).startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState2);
            } else if (i3 != 3) {
                VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
                (volumeIconMotion3 == null ? null : volumeIconMotion3).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState2);
            } else {
                VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
                (volumeIconMotion4 == null ? null : volumeIconMotion4).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState2);
            }
            this.shouldUpdateIcon = false;
            this.currentMediaIconState = i;
        }
    }

    public final void setSoundIconState(int i, int i2, int i3, boolean z) {
        int i4;
        if (i == i2 && !this.shouldUpdateIcon && this.iconType == i3) {
            return;
        }
        this.iconType = i3;
        if (!z || i2 == -1 || i == 0) {
            i4 = i;
        } else {
            i4 = i - (i3 == 0 ? 0 : i2) > 0 ? i2 + 1 : i2 - 1;
        }
        View view = this.iconView;
        if (view == null) {
            view = null;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.volume_icon_mute_splash);
        View view2 = this.iconView;
        if (view2 == null) {
            view2 = null;
        }
        ImageView imageView2 = (ImageView) view2.findViewById(R.id.volume_mute_icon);
        View view3 = this.iconView;
        if (view3 == null) {
            view3 = null;
        }
        ImageView imageView3 = (ImageView) view3.findViewById(R.id.volume_normal_icon);
        View view4 = this.iconView;
        if (view4 == null) {
            view4 = null;
        }
        ImageView imageView4 = (ImageView) view4.findViewById(R.id.volume_sound_icon_wave_l);
        View view5 = this.iconView;
        if (view5 == null) {
            view5 = null;
        }
        ImageView imageView5 = (ImageView) view5.findViewById(R.id.volume_sound_icon_wave_s);
        View view6 = this.iconView;
        if (view6 == null) {
            view6 = null;
        }
        ImageView imageView6 = (ImageView) view6.findViewById(R.id.volume_vibrate_icon);
        ScreenState screenState = ScreenState.SCREEN_NORMAL;
        if (this.isSubScreen) {
            screenState = BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? ScreenState.SCREEN_SUB_LARGE_DISPLAY : ScreenState.SCREEN_SUB_DISPLAY;
        }
        ScreenState screenState2 = screenState;
        if (VolumePanelValues.isRing(this.stream) && i == 0) {
            if (i3 == 1) {
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState2);
            } else {
                VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                (volumeIconMotion2 == null ? null : volumeIconMotion2).startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
            }
        } else if (i4 == 3) {
            VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
            (volumeIconMotion3 == null ? null : volumeIconMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState2);
        } else if (i4 == 2) {
            VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
            if (volumeIconMotion4 == null) {
                volumeIconMotion4 = null;
            }
            volumeIconMotion4.startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState2);
        } else if (i4 == 1) {
            VolumeIconMotion volumeIconMotion5 = this.volumeIconMotion;
            if (volumeIconMotion5 == null) {
                volumeIconMotion5 = null;
            }
            volumeIconMotion5.startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState2);
        }
        this.shouldUpdateIcon = false;
        this.currentMediaIconState = i;
    }

    public final void updateEnableState(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
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

    public final void updateIconLayout(VolumePanelRow volumePanelRow, boolean z) {
        View inflate;
        boolean isAnimatableIcon = VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow.getIconType());
        boolean z2 = z || this.isAnimatedType != isAnimatableIcon;
        this.shouldUpdateIcon = z2;
        if (z2) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            if (isAnimatableIcon) {
                if (VolumeIcons.isForMediaIcon(this.stream)) {
                    if (this.isSubScreen) {
                        inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_media_icon : R.layout.volume_sub_display_media_icon, (ViewGroup) null);
                    } else {
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_media_icon, (ViewGroup) null);
                    }
                } else if (VolumePanelValues.isRing(this.stream) || VolumePanelValues.isAlarm(this.stream)) {
                    if (this.isSubScreen) {
                        inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_ringtone_icon : R.layout.volume_sub_display_ringtone_icon, (ViewGroup) null);
                    } else {
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_ringtone_icon, (ViewGroup) null);
                    }
                } else if (this.isSubScreen) {
                    inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_animated_icon : R.layout.volume_sub_display_animated_icon, (ViewGroup) null);
                } else {
                    inflate = LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_icon, (ViewGroup) null);
                }
            } else if (this.isSubScreen) {
                inflate = LayoutInflater.from(getContext()).inflate(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_default_icon : R.layout.volume_sub_display_default_icon, (ViewGroup) null);
            } else {
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.volume_default_icon, (ViewGroup) null);
            }
            this.iconView = inflate;
            this.isAnimatedType = isAnimatableIcon;
            addView(inflate);
        }
    }

    public final void updateIconState(VolumePanelRow volumePanelRow, boolean z) {
        if (!VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow.getIconType()) || !this.isAnimatedType) {
            int defaultIconResId = VolumeIcons.getDefaultIconResId(volumePanelRow.getStreamType(), volumePanelRow.getIconType());
            View view = this.iconView;
            if ((view == null ? null : view) instanceof ImageView) {
                if (view == null) {
                    view = null;
                }
                ((ImageView) view).setImageDrawable(getResources().getDrawable(defaultIconResId, null));
                return;
            }
            return;
        }
        int i = 0;
        if (VolumeIcons.isForMediaIcon(this.stream) || VolumePanelValues.isRing(this.stream) || VolumePanelValues.isAlarm(this.stream)) {
            int realLevel = volumePanelRow.getRealLevel();
            double d = realLevel;
            double levelMax = volumePanelRow.getLevelMax() * (VolumeIcons.isForMediaIcon(this.stream) ? 100 : 1);
            if (d > 0.5d * levelMax) {
                i = 3;
            } else if (d > levelMax * 0.25d) {
                i = 2;
            } else if (realLevel > 0) {
                i = 1;
            }
            if (VolumePanelValues.isRing(this.stream) || VolumePanelValues.isAlarm(this.stream)) {
                setSoundIconState(i, this.currentMediaIconState, volumePanelRow.getIconType(), z);
                return;
            } else {
                setMediaIconState(i, this.currentMediaIconState, z);
                return;
            }
        }
        int iconType = volumePanelRow.getIconType();
        View view2 = this.iconView;
        if (view2 == null) {
            view2 = null;
        }
        ImageView imageView = (ImageView) view2.findViewById(R.id.volume_normal_icon);
        View view3 = this.iconView;
        if (view3 == null) {
            view3 = null;
        }
        ImageView imageView2 = (ImageView) view3.findViewById(R.id.volume_mute_icon);
        View view4 = this.iconView;
        if (view4 == null) {
            view4 = null;
        }
        ImageView imageView3 = (ImageView) view4.findViewById(R.id.volume_vibrate_icon);
        View view5 = this.iconView;
        if (view5 == null) {
            view5 = null;
        }
        ImageView imageView4 = (ImageView) view5.findViewById(R.id.volume_icon_mute_splash);
        int streamType = volumePanelRow.getStreamType();
        if (streamType == 1) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mute_mtrl));
        } else if (streamType == 5) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mute_mtrl));
            imageView3.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_vibrate_mtrl));
        }
        if (this.iconType != iconType) {
            this.iconType = iconType;
            if (iconType == 0) {
                ViewVisibilityUtil.INSTANCE.getClass();
                ViewVisibilityUtil.setGone(imageView);
                ViewVisibilityUtil.setGone(imageView2);
                ViewVisibilityUtil.setGone(imageView4);
                imageView3.setVisibility(0);
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion != null ? volumeIconMotion : null).startVibrationAnimation(imageView3);
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
            VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
            (volumeIconMotion2 != null ? volumeIconMotion2 : null).getClass();
            VolumeIconMotion.startSplashAnimation(imageView4);
        }
    }

    public final void updateIconTintColor(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        ColorStateList colorStateList;
        boolean z = volumePanelState.isLeBroadcasting() && !volumePanelRow.isRoutedToBluetooth();
        if (volumePanelRow.getIconType() == 0 || volumePanelRow.getIconType() == 3 || !(volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0)) {
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int earProtectLevel = volumePanelRow.getEarProtectLevel();
                int realLevel = volumePanelRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(volumePanelRow.getStreamType())) {
                    realLevel *= 100;
                }
                if ((1 <= earProtectLevel && earProtectLevel < realLevel) && !z) {
                    colorStateList = this.iconEarShockColor;
                }
            }
            colorStateList = this.iconActiveColor;
        } else {
            colorStateList = this.iconMutedColor;
        }
        if (!VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType())) {
            View view = this.iconView;
            if (view == null) {
                view = null;
            }
            ImageView imageView = view instanceof ImageView ? (ImageView) view : null;
            if (imageView != null) {
                imageView.setImageTintList(colorStateList);
            }
        } else if (VolumeIcons.isForMediaIcon(this.stream)) {
            View view2 = this.iconView;
            if (view2 == null) {
                view2 = null;
            }
            ImageView imageView2 = (ImageView) view2.findViewById(R.id.volume_media_icon_note);
            View view3 = this.iconView;
            if (view3 == null) {
                view3 = null;
            }
            ImageView imageView3 = (ImageView) view3.findViewById(R.id.volume_media_icon_wave_l);
            View view4 = this.iconView;
            if (view4 == null) {
                view4 = null;
            }
            ImageView imageView4 = (ImageView) view4.findViewById(R.id.volume_media_icon_wave_s);
            imageView2.setImageTintList(colorStateList);
            imageView3.setImageTintList(colorStateList);
            imageView4.setImageTintList(colorStateList);
        } else if (VolumePanelValues.isRing(this.stream) || VolumePanelValues.isAlarm(this.stream)) {
            View view5 = this.iconView;
            if (view5 == null) {
                view5 = null;
            }
            ImageView imageView5 = (ImageView) view5.findViewById(R.id.volume_normal_icon);
            View view6 = this.iconView;
            if (view6 == null) {
                view6 = null;
            }
            ImageView imageView6 = (ImageView) view6.findViewById(R.id.volume_sound_icon_wave_l);
            View view7 = this.iconView;
            if (view7 == null) {
                view7 = null;
            }
            ImageView imageView7 = (ImageView) view7.findViewById(R.id.volume_sound_icon_wave_s);
            imageView5.setImageTintList(colorStateList);
            imageView6.setImageTintList(colorStateList);
            imageView7.setImageTintList(colorStateList);
        }
        View view8 = this.iconView;
        ImageView imageView8 = (ImageView) (view8 != null ? view8 : null).findViewById(R.id.volume_icon_mute_splash);
        if (imageView8 != null) {
            imageView8.setImageTintList(colorStateList);
        }
    }
}
