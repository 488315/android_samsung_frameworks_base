package com.android.systemui.audio.soundcraft.view.volume;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import com.android.systemui.volume.util.BluetoothIconUtil;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftVolumeIcon extends FrameLayout implements SoundCraftVMComponent {
    public final Context context;
    public int currentMediaIconState;
    public View icon;
    public int iconColor;
    public boolean init;
    public boolean isAnimateType;
    public boolean shouldUpdateIcon;
    public int stream;
    public final Lazy viewModel$delegate;
    public final SoundCraftVolumeIconMotion volumeIconMotion;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SoundCraftVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon$special$$inlined$lazyViewModel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeBarViewModel.class);
            }
        });
        this.init = true;
        this.iconColor = context.getColor(R.color.qs_tile_volume_icon_color);
        this.currentMediaIconState = -1;
        this.volumeIconMotion = new SoundCraftVolumeIconMotion(context);
    }

    public final VolumeBarViewModel getViewModel() {
        return (VolumeBarViewModel) this.viewModel$delegate.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateLayout$2(int i) {
        int i2;
        BluetoothDevice activeDevice;
        if (this.shouldUpdateIcon) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            View inflate = LayoutInflater.from(this.context).inflate(getViewModel().getSupportAnimatedIcon() ? R.layout.soundcraft_volume_animated_media_icon : R.layout.soundcraft_volume_default_icon, (ViewGroup) null);
            this.icon = inflate;
            addView(inflate);
        }
        if (this.shouldUpdateIcon || this.isAnimateType) {
            if (getViewModel().getSupportAnimatedIcon()) {
                getViewModel().getClass();
                double d = i;
                int i3 = d > 75.0d ? 3 : d > 37.5d ? 2 : i > 0 ? 1 : 0;
                int i4 = this.currentMediaIconState;
                if (i3 != i4 || this.shouldUpdateIcon) {
                    int i5 = (i4 == -1 || i3 == i4) ? i3 : i3 - i4 > 0 ? i4 + 1 : i4 - 1;
                    View view = this.icon;
                    Intrinsics.checkNotNull(view);
                    ImageView imageView = (ImageView) view.requireViewById(R.id.volume_icon_mute_splash);
                    View view2 = this.icon;
                    Intrinsics.checkNotNull(view2);
                    ImageView imageView2 = (ImageView) view2.requireViewById(R.id.volume_media_icon_mute);
                    View view3 = this.icon;
                    Intrinsics.checkNotNull(view3);
                    ImageView imageView3 = (ImageView) view3.requireViewById(R.id.volume_media_icon_note);
                    View view4 = this.icon;
                    Intrinsics.checkNotNull(view4);
                    ImageView imageView4 = (ImageView) view4.requireViewById(R.id.volume_media_icon_wave_l);
                    View view5 = this.icon;
                    Intrinsics.checkNotNull(view5);
                    ImageView imageView5 = (ImageView) view5.requireViewById(R.id.volume_media_icon_wave_s);
                    boolean z = i4 == -1 || i4 == i3;
                    if (i5 == 1) {
                        this.volumeIconMotion.startMinAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, z);
                    } else if (i5 == 2) {
                        this.volumeIconMotion.startMidAnimation(this.stream, i3, imageView3, imageView5, imageView4, null, imageView2, imageView, z);
                    } else if (i5 != 3) {
                        this.volumeIconMotion.startMuteAnimation(imageView3, imageView5, imageView4, null, imageView2, imageView, z);
                    } else {
                        this.volumeIconMotion.startMaxAnimation(imageView3, imageView5, imageView4, null, imageView2, imageView, z);
                    }
                    this.shouldUpdateIcon = false;
                    this.currentMediaIconState = i3;
                }
            } else {
                View view6 = this.icon;
                if (view6 != null && (view6 instanceof ImageView)) {
                    ImageView imageView6 = (ImageView) view6;
                    VolumeBarViewModel viewModel = getViewModel();
                    Context context = this.context;
                    switch (viewModel.getIconType()) {
                        case 2:
                            i2 = R.drawable.ic_wire_earphone_solid;
                            break;
                        case 3:
                        case 9:
                            i2 = R.drawable.tw_ic_audio_bluetooth_mtrl;
                            break;
                        case 4:
                            i2 = R.drawable.ic_buds2;
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
                            if (!viewModel.volumeManager.remoteSpeakerConnected) {
                                i2 = R.drawable.tw_ic_audio_mirroring_mtrl;
                                break;
                            } else {
                                i2 = R.drawable.tw_ic_audio_mirroring_speaker_mtrl;
                                break;
                            }
                        default:
                            i2 = R.drawable.ic_audio_media_note_solid;
                            break;
                    }
                    Drawable drawable = context.getDrawable(i2);
                    if (viewModel.getIconType() == 9 && (activeDevice = viewModel.bluetoothDeviceManager.getActiveDevice()) != null) {
                        BluetoothIconUtil.INSTANCE.getClass();
                        Drawable serverIconDrawableWithDevice = BluetoothIconUtil.getServerIconDrawableWithDevice(context, activeDevice, 0);
                        if (serverIconDrawableWithDevice != null) {
                            drawable = serverIconDrawableWithDevice;
                        }
                    }
                    imageView6.setImageDrawable(drawable);
                }
            }
        }
        int color = this.context.getColor(getViewModel().isVolumeShocked(i) ? R.color.volume_icon_earshock_color : R.color.qs_tile_volume_icon_color);
        View view7 = this.icon;
        if (view7 != null) {
            try {
                ArrayList arrayList = new ArrayList();
                if (getViewModel().getSupportAnimatedIcon()) {
                    arrayList = CollectionsKt__CollectionsKt.arrayListOf((ImageView) view7.requireViewById(R.id.volume_media_icon_note), (ImageView) view7.requireViewById(R.id.volume_media_icon_wave_l), (ImageView) view7.requireViewById(R.id.volume_media_icon_wave_s));
                } else if (view7 instanceof ImageView) {
                    arrayList = CollectionsKt__CollectionsKt.arrayListOf(view7);
                }
                int i6 = this.iconColor;
                if (i6 != color) {
                    this.volumeIconMotion.startIconTintColor(arrayList, i6, color, this.init);
                    this.iconColor = color;
                }
            } catch (Exception unused) {
            }
        }
        this.init = false;
    }
}
