package com.android.systemui.volume.view.icon;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.ColorUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class QPVolumeIcon extends FrameLayout {
    public BluetoothDeviceManager bluetoothDeviceManager;
    public int currentMediaIconState;
    public int device;
    public View icon;
    public boolean iconAnimationType;
    public int iconType;
    public boolean isShocked;
    public boolean seekBarEnabled;
    public boolean shouldUpdateIcon;
    public int stream;
    public final QPVolumeIconMotion volumeIconMotion;
    public VolumeManager volumeManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public QPVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.iconType = 1;
        this.currentMediaIconState = -1;
        this.volumeIconMotion = new QPVolumeIconMotion(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x004f, code lost:
    
        if (com.android.systemui.volume.util.BluetoothIconUtil.isBuds(r9) != false) goto L49;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initialize(int r8, int r9, int r10, boolean r11, boolean r12, boolean r13, com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager r14, com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager r15) {
        /*
            r7 = this;
            r7.bluetoothDeviceManager = r14
            r7.volumeManager = r15
            r7.device = r9
            r0 = 1
            r1 = 128(0x80, float:1.8E-43)
            r2 = 4
            r3 = 5
            r4 = 9
            r5 = 0
            r6 = 3
            if (r9 == r1) goto L5b
            r14 = 32768(0x8000, float:4.5918E-41)
            if (r9 == r14) goto L58
            r14 = 67108864(0x4000000, float:1.5046328E-36)
            if (r9 == r14) goto L56
            r14 = 134217728(0x8000000, float:3.85186E-34)
            if (r9 == r14) goto L54
            r14 = 536870912(0x20000000, float:1.0842022E-19)
            if (r9 == r14) goto L25
            r2 = r0
            goto L8e
        L25:
            if (r15 != 0) goto L28
            r15 = r5
        L28:
            com.android.systemui.volume.util.BluetoothAdapterWrapper r9 = r15.bluetoothAdapterWrapper
            java.util.List r9 = r9.getConnectedLeDevices()
            java.lang.Object r9 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r9)
            android.bluetooth.BluetoothDevice r9 = (android.bluetooth.BluetoothDevice) r9
            if (r9 == 0) goto L52
            com.android.systemui.volume.util.BluetoothIconUtil r14 = com.android.systemui.volume.util.BluetoothIconUtil.INSTANCE
            r14.getClass()
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isNextBudsModel(r9)
            if (r14 == 0) goto L43
        L41:
            r2 = r4
            goto L8e
        L43:
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isBuds3(r9)
            if (r14 == 0) goto L4b
        L49:
            r2 = r3
            goto L8e
        L4b:
            boolean r9 = com.android.systemui.volume.util.BluetoothIconUtil.isBuds(r9)
            if (r9 == 0) goto L52
            goto L8e
        L52:
            r2 = r6
            goto L8e
        L54:
            r2 = 7
            goto L8e
        L56:
            r2 = 2
            goto L8e
        L58:
            r2 = 8
            goto L8e
        L5b:
            if (r14 != 0) goto L5e
            r14 = r5
        L5e:
            android.bluetooth.BluetoothDevice r9 = r14.getActiveDevice()
            if (r9 == 0) goto L52
            com.android.systemui.volume.util.BluetoothIconUtil r14 = com.android.systemui.volume.util.BluetoothIconUtil.INSTANCE
            r14.getClass()
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isNextBudsModel(r9)
            if (r14 == 0) goto L70
            goto L41
        L70:
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isBuds3(r9)
            if (r14 == 0) goto L77
            goto L49
        L77:
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isBuds(r9)
            if (r14 == 0) goto L7e
            goto L8e
        L7e:
            boolean r14 = com.android.systemui.volume.util.BluetoothIconUtil.isHomeMini(r9)
            if (r14 == 0) goto L86
            r2 = 6
            goto L8e
        L86:
            boolean r9 = com.android.systemui.volume.util.BluetoothIconUtil.isMusicFrame(r9)
            if (r9 == 0) goto L52
            r2 = 10
        L8e:
            if (r13 != 0) goto L9a
            boolean r9 = r7.iconAnimationType
            if (r9 != r12) goto L9a
            int r9 = r7.iconType
            if (r9 == r2) goto L99
            goto L9a
        L99:
            r0 = 0
        L9a:
            r7.shouldUpdateIcon = r0
            r7.iconType = r2
            r7.stream = r8
            r7.seekBarEnabled = r11
            r7.iconAnimationType = r12
            r7.setEnabled(r11)
            boolean r8 = r7.isEnabled()
            if (r8 == 0) goto Lb1
            r8 = 1062836634(0x3f59999a, float:0.85)
            goto Lb4
        Lb1:
            r8 = 1053609165(0x3ecccccd, float:0.4)
        Lb4:
            r7.setAlpha(r8)
            r7.updateLayout(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.icon.QPVolumeIcon.initialize(int, int, int, boolean, boolean, boolean, com.android.systemui.audio.soundcraft.interfaces.connectivity.BluetoothDeviceManager, com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager):void");
    }

    public final void updateLayout(int i) {
        int i2;
        int i3;
        int i4;
        int semGetEarProtectLimit;
        if (this.shouldUpdateIcon) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View inflate = LayoutInflater.from(getContext()).inflate(this.device == 0 ? R.layout.qs_volume_animated_media_icon : R.layout.volume_default_icon, (ViewGroup) (this.iconType == 9 ? this : null), false);
            this.icon = inflate;
            addView(inflate);
        }
        boolean z = this.shouldUpdateIcon;
        if (z || this.iconAnimationType) {
            if (this.device == 0) {
                double d = i;
                int i5 = d > 75.0d ? 3 : d > 37.5d ? 2 : i > 0 ? 1 : 0;
                int i6 = this.currentMediaIconState;
                if (i5 != i6 || z) {
                    int i7 = (i6 == -1 || i5 == i6) ? i5 : i5 - i6 > 0 ? i6 + 1 : i6 - 1;
                    View view = this.icon;
                    view.getClass();
                    ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
                    View view2 = this.icon;
                    view2.getClass();
                    ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_media_icon_mute);
                    View view3 = this.icon;
                    view3.getClass();
                    ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_media_icon_note);
                    View view4 = this.icon;
                    view4.getClass();
                    ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_media_icon_wave_l);
                    View view5 = this.icon;
                    view5.getClass();
                    ImageView imageView5 = (ImageView) view5.requireViewById(R.id.volume_media_icon_wave_s);
                    boolean z2 = i6 == -1 || i6 == i5;
                    if (i7 == 1) {
                        i3 = i5;
                        this.volumeIconMotion.startMinAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, z2);
                    } else if (i7 != 2) {
                        if (i7 != 3) {
                            this.volumeIconMotion.startMuteAnimation(imageView3, imageView5, imageView4, null, imageView2, imageView, z2);
                        } else {
                            this.volumeIconMotion.startMaxAnimation(imageView3, imageView5, imageView4, null, imageView2, imageView, z2);
                        }
                        i4 = i5;
                        this.shouldUpdateIcon = false;
                        this.currentMediaIconState = i4;
                    } else {
                        i3 = i5;
                        this.volumeIconMotion.startMidAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, z2);
                    }
                    i4 = i3;
                    this.shouldUpdateIcon = false;
                    this.currentMediaIconState = i4;
                }
            } else {
                View view6 = this.icon;
                if (view6 != null && (view6 instanceof ImageView)) {
                    Resources resources = getResources();
                    switch (this.iconType) {
                        case 2:
                            i2 = R.drawable.ic_wire_earphone_solid;
                            break;
                        case 3:
                        case 9:
                            i2 = R.drawable.tw_ic_audio_bluetooth_mtrl;
                            break;
                        case 4:
                            i2 = R.drawable.soundcraft_ic_buds2_left_solid;
                            break;
                        case 5:
                            i2 = R.drawable.ic_buds3_pro;
                            break;
                        case 6:
                            i2 = R.drawable.ic_galaxy_home_mini;
                            break;
                        case 7:
                            i2 = R.drawable.tw_ic_audio_hearing_aids;
                            break;
                        case 8:
                            VolumeManager volumeManager = this.volumeManager;
                            if (volumeManager == null) {
                                volumeManager = null;
                            }
                            if (!volumeManager.remoteSpeakerConnected) {
                                i2 = R.drawable.tw_ic_audio_mirroring_mtrl;
                                break;
                            } else {
                                i2 = R.drawable.tw_ic_audio_mirroring_speaker_mtrl;
                                break;
                            }
                        case 10:
                            i2 = R.drawable.tw_ic_audio_music_frame_solid;
                            break;
                        default:
                            i2 = R.drawable.ic_audio_media_note_solid;
                            break;
                    }
                    Drawable drawable = resources.getDrawable(i2, null);
                    if (this.iconType == 9) {
                        BluetoothDeviceManager bluetoothDeviceManager = this.bluetoothDeviceManager;
                        BluetoothDevice activeDevice = (bluetoothDeviceManager != null ? bluetoothDeviceManager : null).getActiveDevice();
                        if (activeDevice != null) {
                            BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
                            Context context = getContext();
                            bluetoothIconUtil.getClass();
                            Drawable serverIconDrawableWithDevice = BluetoothIconUtil.getServerIconDrawableWithDevice(context, activeDevice);
                            if (serverIconDrawableWithDevice != null) {
                                drawable = serverIconDrawableWithDevice;
                            }
                        }
                    }
                    ((ImageView) view6).setImageDrawable(drawable);
                }
            }
        }
        Context context2 = getContext();
        int i8 = (this.iconType == 1 || !this.seekBarEnabled || (semGetEarProtectLimit = (AudioManager.semGetEarProtectLimit() - 1) * 10) <= 0 || semGetEarProtectLimit >= i) ? R.color.animated_brightness_sun_icon_color : R.color.volume_icon_earshock_color;
        this.isShocked = i8 == R.color.volume_icon_earshock_color;
        ColorStateList singleColorStateList = ColorUtils.getSingleColorStateList(i8, context2);
        View view7 = this.icon;
        if (view7 != null) {
            if (this.device != 0) {
                if (view7 instanceof ImageView) {
                    ((ImageView) view7).setImageTintList(singleColorStateList);
                    return;
                }
                return;
            }
            ImageView imageView6 = (ImageView) view7.requireViewById(R.id.volume_icon_mute_splash);
            ImageView imageView7 = (ImageView) view7.requireViewById(R.id.volume_media_icon_mute);
            ImageView imageView8 = (ImageView) view7.requireViewById(R.id.volume_media_icon_note);
            ImageView imageView9 = (ImageView) view7.requireViewById(R.id.volume_media_icon_wave_l);
            ImageView imageView10 = (ImageView) view7.requireViewById(R.id.volume_media_icon_wave_s);
            imageView6.setImageTintList(singleColorStateList);
            imageView7.setImageTintList(singleColorStateList);
            imageView8.setImageTintList(singleColorStateList);
            imageView9.setImageTintList(singleColorStateList);
            imageView10.setImageTintList(singleColorStateList);
        }
    }
}
