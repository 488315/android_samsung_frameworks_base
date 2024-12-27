package com.android.systemui.volume.view.icon;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
            View inflate = VolumeIcons.isForMediaIcon(this.stream) ? LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_media_icon, (ViewGroup) null) : VolumePanelValues.isRing(this.stream) ? LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_ringtone_icon, (ViewGroup) null) : LayoutInflater.from(getContext()).inflate(R.layout.volume_animated_icon, (ViewGroup) null);
            Intrinsics.checkNotNull(inflate);
            return inflate;
        }
        View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.volume_default_icon, (ViewGroup) null);
        Intrinsics.checkNotNull(inflate2);
        return inflate2;
    }

    public void initVolumeIconColor(int i, int i2, int i3) {
        this.iconActiveColor = i;
        this.iconMutedColor = i2;
        this.iconEarShockColor = i3;
    }

    public final void initialize(VolumePanelStore volumePanelStore, VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
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

    /* JADX WARN: Code restructure failed: missing block: B:111:0x00f7, code lost:
    
        if (com.android.systemui.volume.view.icon.VolumeIcons.isAnimatableIcon(r19.stream, r4.getIconType()) == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00e4, code lost:
    
        if (com.android.systemui.volume.view.icon.VolumeIcons.isAnimatableMediaIconType(r8) != false) goto L56;
     */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onChanged(java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.icon.VolumeIcon.onChanged(java.lang.Object):void");
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
            if (i3 == 1) {
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
            } else if (i3 == 2) {
                VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                (volumeIconMotion2 == null ? null : volumeIconMotion2).startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
            } else if (i3 != 3) {
                VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
                (volumeIconMotion3 == null ? null : volumeIconMotion3).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
            } else {
                VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
                (volumeIconMotion4 == null ? null : volumeIconMotion4).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, null, imageView2, imageView, screenState);
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
        if (VolumePanelValues.isRing(this.stream) && i == 0) {
            if (i3 == 1) {
                VolumeIconMotion volumeIconMotion = this.volumeIconMotion;
                (volumeIconMotion == null ? null : volumeIconMotion).startMuteAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
            } else {
                VolumeIconMotion volumeIconMotion2 = this.volumeIconMotion;
                (volumeIconMotion2 == null ? null : volumeIconMotion2).startSoundVibrationAnimation(imageView6, imageView3, imageView5, imageView4, imageView2, imageView);
            }
        } else if (i4 == 3) {
            VolumeIconMotion volumeIconMotion3 = this.volumeIconMotion;
            (volumeIconMotion3 == null ? null : volumeIconMotion3).startMaxAnimation(this.stream, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
        } else if (i4 == 2) {
            VolumeIconMotion volumeIconMotion4 = this.volumeIconMotion;
            if (volumeIconMotion4 == null) {
                volumeIconMotion4 = null;
            }
            volumeIconMotion4.startMidAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
        } else if (i4 == 1) {
            VolumeIconMotion volumeIconMotion5 = this.volumeIconMotion;
            if (volumeIconMotion5 == null) {
                volumeIconMotion5 = null;
            }
            volumeIconMotion5.startMinAnimation(this.stream, i, imageView3, imageView5, imageView4, imageView6, imageView2, imageView, screenState);
        }
        this.shouldUpdateIcon = false;
        this.currentMediaIconState = i;
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
        boolean isAnimatableIcon = VolumeIcons.isAnimatableIcon(this.stream, volumePanelRow.getIconType());
        boolean z2 = z || this.isAnimatedType != isAnimatableIcon;
        this.shouldUpdateIcon = z2;
        if (z2) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View inflateIconView = inflateIconView(isAnimatableIcon);
            this.iconView = inflateIconView;
            this.isAnimatedType = isAnimatableIcon;
            addView(inflateIconView);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIconState(com.samsung.systemui.splugins.volume.VolumePanelRow r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.icon.VolumeIcon.updateIconState(com.samsung.systemui.splugins.volume.VolumePanelRow, boolean):void");
    }

    public final void updateIconTintColor(VolumePanelRow volumePanelRow, VolumePanelState volumePanelState, boolean z) {
        int i;
        boolean z2 = volumePanelState.isLeBroadcasting() && !volumePanelRow.isRoutedToBluetooth();
        if (volumePanelRow.getIconType() == 0 || volumePanelRow.getIconType() == 3 || !(volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0)) {
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int earProtectLevel = volumePanelRow.getEarProtectLevel();
                int realLevel = volumePanelRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(volumePanelRow.getStreamType())) {
                    realLevel *= 100;
                }
                if (1 <= earProtectLevel && earProtectLevel < realLevel && !z2) {
                    i = this.iconEarShockColor;
                }
            }
            i = this.iconActiveColor;
        } else {
            i = this.iconMutedColor;
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
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((ImageView) it.next()).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                }
            });
            this.iconColor = i;
        }
    }
}
