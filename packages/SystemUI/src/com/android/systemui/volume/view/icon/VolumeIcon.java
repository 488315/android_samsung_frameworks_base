package com.android.systemui.volume.view.icon;

import android.animation.ValueAnimator;
import android.content.Context;
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
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class VolumeIcon extends FrameLayout implements VolumeObserver {
    public int currentMediaIconState;
    public final float defaultAlpha;
    public final float disableAlpha;
    public int iconActiveColor;
    public int iconColor;
    public int iconEarShockColor;
    public int iconMutedColor;
    public int iconType;
    public View iconView;
    public boolean isAnimatedType;
    public boolean shouldUpdateIcon;
    public final StoreInteractor storeInteractor;
    public int stream;
    public VolumeIconMotion volumeIconMotion;

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

    public VolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.iconActiveColor = context.getColor(R.color.volume_icon_color);
        this.iconMutedColor = context.getColor(R.color.volume_icon_color);
        this.iconEarShockColor = context.getColor(R.color.volume_icon_earshock_color);
        this.defaultAlpha = 0.85f;
        this.disableAlpha = 0.3f;
        this.currentMediaIconState = -1;
        this.iconType = -1;
        this.iconColor = this.iconActiveColor;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return "android.widget.Button";
    }

    public ScreenState getScreenState() {
        return ScreenState.SCREEN_NORMAL;
    }

    public View inflateIconView(boolean z) {
        if (z) {
            View viewInflate = VolumeIcons.isForMediaIcon(this.stream) ? LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_media_icon, (ViewGroup) null) : VolumePanelValues.isRing(this.stream) ? LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_ringtone_icon, (ViewGroup) null) : LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_icon, (ViewGroup) null);
            viewInflate.getClass();
            return viewInflate;
        }
        View viewInflate2 = LayoutInflater.from(getContext()).inflate(R.layout.volume_default_icon, (ViewGroup) null);
        viewInflate2.getClass();
        return viewInflate2;
    }

    public void initVolumeIconColor(int i, int i2, int i3) {
        this.iconActiveColor = i;
        this.iconMutedColor = i2;
        this.iconEarShockColor = i3;
    }

    public final void initialize(VolumePanelStore volumePanelStore, VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) throws Resources.NotFoundException {
        StoreInteractor storeInteractor = this.storeInteractor;
        storeInteractor.store = volumePanelStore;
        storeInteractor.observeStore();
        this.volumeIconMotion = new VolumeIconMotion(volumePanelStore, getContext());
        int streamType = volumePanelRow.getStreamType();
        this.stream = streamType;
        if (VolumePanelValues.isRing(streamType)) {
            this.iconType = volumePanelRow.getIconType();
        }
        initVolumeIconColor(this.iconActiveColor, this.iconMutedColor, this.iconEarShockColor);
        updateIconLayout(volumePanelRow, true);
        updateIconState(volumePanelRow, false);
        updateIconTintColor(volumePanelRow, volumePanelState, true);
        updateEnableState(volumePanelState, volumePanelRow);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00f4  */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChanged(Object obj) throws Resources.NotFoundException {
        VolumePanelRow volumePanelRowFindRow;
        VolumePanelRow volumePanelRowFindRow2;
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()];
        if (i == 1) {
            this.storeInteractor.dispose();
            return;
        }
        if (i != 2) {
            if (i == 3) {
                if (this.stream == volumePanelState.getStream() && (volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream)) != null && VolumeIcons.isAnimatableIcon(this.stream, volumePanelRowFindRow.getIconType())) {
                    updateIconState(volumePanelRowFindRow, false);
                    return;
                }
                return;
            }
            if (i == 4) {
                if (this.stream != volumePanelState.getStream() || (volumePanelRowFindRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream)) == null) {
                    return;
                }
                updateIconLayout(volumePanelRowFindRow2, false);
                updateIconState(volumePanelRowFindRow2, true);
                return;
            }
            if (i == 5 && this.isAnimatedType && this.stream == volumePanelState.getStream()) {
                int iconTargetState = volumePanelState.getIconTargetState();
                int iconCurrentState = volumePanelState.getIconCurrentState();
                if (VolumePanelValues.isRing(this.stream)) {
                    setSoundIconState(iconTargetState, iconCurrentState, this.iconType, true);
                    return;
                } else {
                    setMediaIconState(iconTargetState, iconCurrentState, true);
                    return;
                }
            }
            return;
        }
        boolean zIsQpVolumeBarEnabled = volumePanelState.isQpVolumeBarEnabled();
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : volumeRowList) {
            VolumePanelRow volumePanelRow = (VolumePanelRow) obj2;
            if (volumePanelRow.getStreamType() == this.stream && (volumePanelRow.isVisible() || zIsQpVolumeBarEnabled)) {
                arrayList.add(obj2);
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            VolumePanelRow volumePanelRow2 = (VolumePanelRow) obj3;
            updateIconLayout(volumePanelRow2, false);
            if (!this.shouldUpdateIcon) {
                int i3 = this.stream;
                int iconType = volumePanelRow2.getIconType();
                if ((!VolumeIcons.isForMediaIcon(i3) || !VolumeIcons.isAnimatableMediaIconType(iconType)) && !VolumePanelValues.isRing(i3) && VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow2.getIconType())) {
                    updateIconState(volumePanelRow2, false);
                }
            }
            int iconType2 = volumePanelRow2.getIconType();
            if (this.iconType != iconType2 && VolumePanelValues.isRing(this.stream) && iconType2 != 3) {
                View view = this.iconView;
                if (view == null) {
                    view = null;
                }
                ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
                View view2 = this.iconView;
                if (view2 == null) {
                    view2 = null;
                }
                ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_mute_icon);
                View view3 = this.iconView;
                if (view3 == null) {
                    view3 = null;
                }
                ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_normal_icon);
                View view4 = this.iconView;
                if (view4 == null) {
                    view4 = null;
                }
                ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_sound_icon_wave_l);
                View view5 = this.iconView;
                if (view5 == null) {
                    view5 = null;
                }
                ImageView imageView5 = (ImageView) view5.requireViewById(R.id.volume_sound_icon_wave_s);
                View view6 = this.iconView;
                if (view6 == null) {
                    view6 = null;
                }
                ImageView imageView6 = (ImageView) view6.requireViewById(R.id.volume_vibrate_icon);
                ScreenState screenState = getScreenState();
                if (iconType2 == 1) {
                    VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                    (volumeIconMotion == null ? null : volumeIconMotion).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
                } else {
                    VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                    if (volumeIconMotion2 == null) {
                        volumeIconMotion2 = null;
                    }
                    volumeIconMotion2.startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
                }
                this.shouldUpdateIcon = false;
                this.currentMediaIconState = 0;
                this.iconType = iconType2;
            }
            updateIconTintColor(volumePanelRow2, volumePanelState, false);
            updateEnableState(volumePanelState, volumePanelRow2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
    }

    public final void setMediaIconState(int i, int i2, boolean z) {
        int i3;
        if (i != i2 || this.shouldUpdateIcon) {
            int i4 = (!z || i2 == -1) ? i : i - i2 > 0 ? i2 + 1 : i2 - 1;
            View view = this.iconView;
            if (view == null) {
                view = null;
            }
            ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
            View view2 = this.iconView;
            if (view2 == null) {
                view2 = null;
            }
            ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_media_icon_mute);
            View view3 = this.iconView;
            if (view3 == null) {
                view3 = null;
            }
            ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_media_icon_note);
            View view4 = this.iconView;
            if (view4 == null) {
                view4 = null;
            }
            ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_media_icon_wave_l);
            View view5 = this.iconView;
            if (view5 == null) {
                view5 = null;
            }
            ImageView imageView5 = (ImageView) view5.requireViewById(R.id.volume_media_icon_wave_s);
            ScreenState screenState = getScreenState();
            if (i4 == 1) {
                i3 = i;
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startMinAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
            } else if (i4 != 2) {
                if (i4 != 3) {
                    VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                    (volumeIconMotion2 == null ? null : volumeIconMotion2).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
                } else {
                    VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
                    (volumeIconMotion3 == null ? null : volumeIconMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
                }
                i3 = i;
            } else {
                VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
                i3 = i;
                (volumeIconMotion4 == null ? null : volumeIconMotion4).startMidAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
            }
            this.shouldUpdateIcon = false;
            this.currentMediaIconState = i3;
        }
    }

    public final void setSoundIconState(int i, int i2, int i3, boolean z) {
        int i4;
        int i5 = i;
        if (i5 == i2 && !this.shouldUpdateIcon && this.iconType == i3) {
            return;
        }
        this.iconType = i3;
        if (!z || i2 == -1 || i5 == 0) {
            i4 = i5;
        } else {
            i4 = i5 - (i3 == 0 ? 0 : i2) > 0 ? i2 + 1 : i2 - 1;
        }
        View view = this.iconView;
        if (view == null) {
            view = null;
        }
        ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
        View view2 = this.iconView;
        if (view2 == null) {
            view2 = null;
        }
        ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_mute_icon);
        View view3 = this.iconView;
        if (view3 == null) {
            view3 = null;
        }
        ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_normal_icon);
        View view4 = this.iconView;
        if (view4 == null) {
            view4 = null;
        }
        ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_sound_icon_wave_l);
        View view5 = this.iconView;
        if (view5 == null) {
            view5 = null;
        }
        ImageView imageView5 = (ImageView) view5.requireViewById(R.id.volume_sound_icon_wave_s);
        View view6 = this.iconView;
        if (view6 == null) {
            view6 = null;
        }
        ImageView imageView6 = (ImageView) view6.requireViewById(R.id.volume_vibrate_icon);
        ScreenState screenState = getScreenState();
        if (VolumePanelValues.isRing(this.stream) && i5 == 0) {
            if (i3 == 0) {
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
            } else if (i3 == 1) {
                VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                (volumeIconMotion2 == null ? null : volumeIconMotion2).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
            }
        } else if (i4 == 3) {
            VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
            (volumeIconMotion3 == null ? null : volumeIconMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
        } else {
            if (i4 == 2) {
                VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
                if (volumeIconMotion4 == null) {
                    volumeIconMotion4 = null;
                }
                volumeIconMotion4.startMidAnimation(this.stream, i5, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
            } else if (i4 == 1) {
                VolumeIconMotion volumeIconMotion5 = this.volumeIconMotion;
                if (volumeIconMotion5 == null) {
                    volumeIconMotion5 = null;
                }
                i5 = i;
                volumeIconMotion5.startMinAnimation(this.stream, i5, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
            }
            i5 = i;
        }
        this.shouldUpdateIcon = false;
        this.currentMediaIconState = i5;
    }

    public final void updateEnableState(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        if (volumePanelRow.isSliderEnabled()) {
            setEnabled(true);
            setAlpha(this.defaultAlpha);
        } else {
            setEnabled(volumePanelRow.isIconEnabled());
            setAlpha(volumePanelRow.isIconEnabled() ? this.defaultAlpha : this.disableAlpha);
        }
        if (volumePanelState.isShowA11yStream()) {
            setClickable(isEnabled() && volumePanelRow.isIconClickable());
        }
    }

    public final void updateIconLayout(VolumePanelRow volumePanelRow, boolean z) {
        boolean zIsAnimatableIcon = VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow.getIconType());
        boolean z2 = z || this.isAnimatedType != zIsAnimatableIcon;
        this.shouldUpdateIcon = z2;
        if (z2) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View viewInflateIconView = inflateIconView(zIsAnimatableIcon);
            this.iconView = viewInflateIconView;
            this.isAnimatedType = zIsAnimatableIcon;
            addView(viewInflateIconView);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x016f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconState(VolumePanelRow volumePanelRow, boolean z) throws Resources.NotFoundException {
        Drawable drawable;
        if (!VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow.getIconType()) || !this.isAnimatedType) {
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
            if ((view == null ? null : view) instanceof ImageView) {
                ((ImageView) (view != null ? view : null)).setImageDrawable(drawable);
                return;
            }
            return;
        }
        if (VolumeIcons.isForMediaIcon(this.stream) || VolumePanelValues.isRing(this.stream)) {
            int levelMax = volumePanelRow.getLevelMax() * (VolumeIcons.isForMediaIcon(this.stream) ? 100 : 1);
            int realLevel = volumePanelRow.getRealLevel();
            double d = realLevel;
            double d2 = levelMax;
            int i = d <= 0.5d * d2 ? d > d2 * 0.25d ? 2 : realLevel > 0 ? 1 : 0 : 3;
            if (VolumePanelValues.isRing(this.stream)) {
                setSoundIconState(i, this.currentMediaIconState, volumePanelRow.getIconType(), z);
                return;
            } else {
                setMediaIconState(i, this.currentMediaIconState, z);
                return;
            }
        }
        View view2 = this.iconView;
        if (view2 == null) {
            view2 = null;
        }
        ImageView imageView = (ImageView) view2.requireViewById(R.id.volume_normal_icon);
        View view3 = this.iconView;
        if (view3 == null) {
            view3 = null;
        }
        ImageView imageView2 = (ImageView) view3.requireViewById(R.id.volume_mute_icon);
        View view4 = this.iconView;
        if (view4 == null) {
            view4 = null;
        }
        ImageView imageView3 = (ImageView) view4.requireViewById(R.id.volume_vibrate_icon);
        View view5 = this.iconView;
        if (view5 == null) {
            view5 = null;
        }
        ImageView imageView4 = (ImageView) view5.requireViewById(R.id.volume_icon_mute_splash);
        int streamType = volumePanelRow.getStreamType();
        if (streamType == 1) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_system_mute_mtrl));
        } else if (streamType == 5) {
            imageView.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mtrl));
            imageView2.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_mute_mtrl));
            imageView3.setImageDrawable(getContext().getDrawable(R.drawable.tw_ic_audio_noti_vibrate_mtrl));
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconTintColor(VolumePanelRow volumePanelRow, VolumePanelState volumePanelState, boolean z) {
        int i;
        boolean z2 = volumePanelState.isLeBroadcasting() && !volumePanelRow.isRoutedToBluetooth();
        if (volumePanelRow.getIconType() != 0 && volumePanelRow.getIconType() != 3 && (volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0)) {
            i = this.iconMutedColor;
        } else if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
            int earProtectLevel = volumePanelRow.getEarProtectLevel();
            int realLevel = volumePanelRow.getRealLevel();
            if (VolumePanelValues.isAudioSharing(volumePanelRow.getStreamType())) {
                realLevel *= 100;
            }
            i = (1 > earProtectLevel || earProtectLevel >= realLevel || z2) ? this.iconActiveColor : this.iconEarShockColor;
        }
        final ArrayList arrayList = new ArrayList();
        if (!VolumeIcons.isAnimatableIcon(volumePanelRow.getStreamType(), volumePanelRow.getIconType())) {
            View view = this.iconView;
            if (view == null) {
                view = null;
            }
            ImageView imageView = view instanceof ImageView ? (ImageView) view : null;
            if (imageView != null) {
                arrayList = CollectionsKt__CollectionsKt.arrayListOf(imageView);
            }
        } else if (VolumeIcons.isForMediaIcon(this.stream)) {
            View view2 = this.iconView;
            if (view2 == null) {
                view2 = null;
            }
            ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_media_icon_mute);
            View view3 = this.iconView;
            if (view3 == null) {
                view3 = null;
            }
            ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_media_icon_note);
            View view4 = this.iconView;
            if (view4 == null) {
                view4 = null;
            }
            ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_media_icon_wave_l);
            View view5 = this.iconView;
            if (view5 == null) {
                view5 = null;
            }
            arrayList = CollectionsKt__CollectionsKt.arrayListOf(imageView2, imageView3, imageView4, (ImageView) view5.requireViewById(R.id.volume_media_icon_wave_s));
        } else if (VolumePanelValues.isRing(this.stream)) {
            View view6 = this.iconView;
            if (view6 == null) {
                view6 = null;
            }
            ImageView imageView5 = (ImageView) view6.requireViewById(R.id.volume_normal_icon);
            View view7 = this.iconView;
            if (view7 == null) {
                view7 = null;
            }
            ImageView imageView6 = (ImageView) view7.requireViewById(R.id.volume_sound_icon_wave_l);
            View view8 = this.iconView;
            if (view8 == null) {
                view8 = null;
            }
            arrayList = CollectionsKt__CollectionsKt.arrayListOf(imageView5, imageView6, (ImageView) view8.requireViewById(R.id.volume_sound_icon_wave_s));
        }
        View view9 = this.iconView;
        if (view9 == null) {
            view9 = null;
        }
        ImageView imageView7 = (ImageView) view9.findViewById(R.id.volume_icon_mute_splash);
        if (imageView7 != null) {
            arrayList.add(imageView7);
        }
        int i2 = this.iconColor;
        if (i2 != i) {
            VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
            VolumeIconMotion volumeIconMotion2 = volumeIconMotion != null ? volumeIconMotion : null;
            if (volumeIconMotion2.shockValueAnimator.isRunning()) {
                volumeIconMotion2.shockValueAnimator.pause();
            }
            ValueAnimator duration = ValueAnimator.ofArgb(i2, i).setDuration(z ? 0L : 450L);
            volumeIconMotion2.shockValueAnimator = duration;
            duration.setInterpolator(VolumeIconMotion.ALPHA_INTERPOLATOR);
            volumeIconMotion2.shockValueAnimator.start();
            volumeIconMotion2.shockValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.icon.VolumeIconMotion$startIconTintColor$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ArrayList arrayList2 = arrayList;
                    int size = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList2.get(i3);
                        i3++;
                        ((ImageView) obj).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                }
            });
            this.iconColor = i;
        }
    }
}
