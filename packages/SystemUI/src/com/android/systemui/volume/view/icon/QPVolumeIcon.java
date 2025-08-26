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
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initialize(int i, int i2, int i3, boolean z, boolean z2, boolean z3, BluetoothDeviceManager bluetoothDeviceManager, VolumeManager volumeManager) {
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.volumeManager = volumeManager;
        this.device = i2;
        boolean z4 = true;
        int i4 = 4;
        if (i2 == 128) {
            if (bluetoothDeviceManager == null) {
                bluetoothDeviceManager = null;
            }
            BluetoothDevice activeDevice = bluetoothDeviceManager.getActiveDevice();
            if (activeDevice != null) {
                BluetoothIconUtil.INSTANCE.getClass();
                if (!BluetoothIconUtil.isNextBudsModel(activeDevice)) {
                    if (!BluetoothIconUtil.isBuds3(activeDevice)) {
                        if (!BluetoothIconUtil.isBuds(activeDevice)) {
                            if (BluetoothIconUtil.isHomeMini(activeDevice)) {
                                i4 = 6;
                            } else if (BluetoothIconUtil.isMusicFrame(activeDevice)) {
                                i4 = 10;
                            }
                        }
                    }
                }
            }
        } else if (i2 == 32768) {
            i4 = 8;
        } else if (i2 == 67108864) {
            i4 = 2;
        } else if (i2 == 134217728) {
            i4 = 7;
        } else if (i2 != 536870912) {
            i4 = 1;
        } else {
            if (volumeManager == null) {
                volumeManager = null;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(volumeManager.bluetoothAdapterWrapper.getConnectedLeDevices());
            if (bluetoothDevice != null) {
                BluetoothIconUtil.INSTANCE.getClass();
                if (BluetoothIconUtil.isNextBudsModel(bluetoothDevice)) {
                    i4 = 9;
                } else if (BluetoothIconUtil.isBuds3(bluetoothDevice)) {
                    i4 = 5;
                } else if (!BluetoothIconUtil.isBuds(bluetoothDevice)) {
                    i4 = 3;
                }
            }
        }
        if (!z3 && this.iconAnimationType == z2 && this.iconType == i4) {
            z4 = false;
        }
        this.shouldUpdateIcon = z4;
        this.iconType = i4;
        this.stream = i;
        this.seekBarEnabled = z;
        this.iconAnimationType = z2;
        setEnabled(z);
        setAlpha(isEnabled() ? 0.85f : 0.4f);
        updateLayout(i3);
    }

    public final void updateLayout(int i) {
        int i2;
        int i3;
        int i4;
        int iSemGetEarProtectLimit;
        if (this.shouldUpdateIcon) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View viewInflate = LayoutInflater.from(getContext()).inflate(this.device == 0 ? R.layout.qs_volume_animated_media_icon : R.layout.volume_default_icon, (ViewGroup) (this.iconType == 9 ? this : null), false);
            this.icon = viewInflate;
            addView(viewInflate);
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
        int i8 = (this.iconType == 1 || !this.seekBarEnabled || (iSemGetEarProtectLimit = (AudioManager.semGetEarProtectLimit() - 1) * 10) <= 0 || iSemGetEarProtectLimit >= i) ? R.color.animated_brightness_sun_icon_color : R.color.volume_icon_earshock_color;
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
