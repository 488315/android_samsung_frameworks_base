package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectBoxLayoutBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect.SpatialAudioSwitchViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect.SpatialAudioViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.EqualizerViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.VoiceBoostViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.VolumeNormalizationViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.phone.audioeffect.DolbyViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioEffectBoxView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy dolbyView$delegate;
    public final Lazy equalizerView$delegate;
    public final Lazy spatialAudioSwitchView$delegate;
    public final Lazy spatialAudioView$delegate;
    public AudioEffectBoxLayoutBinding viewBinding;
    public final Lazy viewModel$delegate;
    public final Lazy voiceBoostView$delegate;
    public final Lazy volumeNormalizationView$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public static BaseAudioEffectItemView $r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, DolbyViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    public static BaseAudioEffectItemView $r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SpatialAudioViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    /* renamed from: $r8$lambda$728lp9L-bisQF_njlpjjxgANtyA, reason: not valid java name */
    public static BaseAudioEffectItemView m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    public static BaseAudioEffectItemView $r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, VoiceBoostViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    /* renamed from: $r8$lambda$Dd2GgMtMDbYx-IywsyMatPJAVJI, reason: not valid java name */
    public static BaseAudioEffectItemView m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, SpatialAudioSwitchViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    public static BaseAudioEffectItemView $r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(AudioEffectBoxView audioEffectBoxView) {
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
        SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
        if (soundCraftViewComponent != null) {
            return audioEffectBoxView.createItemView((BaseViewModel) SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt, soundCraftViewComponent, soundCraftViewComponent, VolumeNormalizationViewModel.class));
        }
        throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
    }

    static {
        new Companion(null);
    }

    public AudioEffectBoxView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        final int i = 0;
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i2) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i2 = 1;
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i2;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i3 = 2;
        this.spatialAudioSwitchView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i3;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i4 = 3;
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i4;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i5 = 4;
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i5;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i6 = 5;
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i6;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
    }

    public final void addDivider() {
        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = this.viewBinding;
        if (audioEffectBoxLayoutBinding == null) {
            audioEffectBoxLayoutBinding = null;
        }
        LinearLayout linearLayout = audioEffectBoxLayoutBinding.effectItemList;
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.soundcraft_effect_divider, (ViewGroup) linearLayout, false));
    }

    public final BaseAudioEffectItemView createItemView(BaseViewModel baseViewModel) {
        if (baseViewModel instanceof BaseSingleChoiceViewModel) {
            Context context = getContext();
            LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
            lifecycleOwner.getClass();
            BaseSingleChoiceViewModel baseSingleChoiceViewModel = (BaseSingleChoiceViewModel) baseViewModel;
            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = this.viewBinding;
            return new AudioEffectSingleChoiceItemView(context, lifecycleOwner, baseSingleChoiceViewModel, (audioEffectBoxLayoutBinding != null ? audioEffectBoxLayoutBinding : null).effectItemList);
        }
        if (!(baseViewModel instanceof BaseToggleViewModel)) {
            throw new RuntimeException();
        }
        Context context2 = getContext();
        LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(this);
        lifecycleOwner2.getClass();
        BaseToggleViewModel baseToggleViewModel = (BaseToggleViewModel) baseViewModel;
        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = this.viewBinding;
        return new AudioEffectToggleItemView(context2, lifecycleOwner2, baseToggleViewModel, (audioEffectBoxLayoutBinding2 != null ? audioEffectBoxLayoutBinding2 : null).effectItemList);
    }

    public final AudioEffectBoxViewModel getViewModel() {
        return (AudioEffectBoxViewModel) this.viewModel$delegate.getValue();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(motionEvent.getPointerCount(), "ev.pointerCount=", "SoundCraft.AudioEffectBoxView");
        }
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public AudioEffectBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        final int i = 0;
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i2 = 1;
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i2;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i3 = 2;
        this.spatialAudioSwitchView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i3;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i4 = 3;
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i4;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i5 = 4;
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i5;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i6 = 5;
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i6;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
    }

    public AudioEffectBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        final int i2 = 0;
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i2;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i3 = 1;
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i3;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i4 = 2;
        this.spatialAudioSwitchView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i4;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i5 = 3;
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i5;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i6 = 4;
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i6;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
        final int i7 = 5;
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ AudioEffectBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i7;
                AudioEffectBoxView audioEffectBoxView = this.f$0;
                switch (i22) {
                    case 0:
                        return AudioEffectBoxView.$r8$lambda$5BP28I_DxH6vKX9x6siJ1Mp1IPg(audioEffectBoxView);
                    case 1:
                        return AudioEffectBoxView.$r8$lambda$5GO1jNqcC5323wIhpCBPP88oo0A(audioEffectBoxView);
                    case 2:
                        return AudioEffectBoxView.m1014$r8$lambda$Dd2GgMtMDbYxIywsyMatPJAVJI(audioEffectBoxView);
                    case 3:
                        return AudioEffectBoxView.m1013$r8$lambda$728lp9LbisQF_njlpjjxgANtyA(audioEffectBoxView);
                    case 4:
                        return AudioEffectBoxView.$r8$lambda$Absc27UF86ReW0UX134pZyNZuuA(audioEffectBoxView);
                    default:
                        return AudioEffectBoxView.$r8$lambda$I_v5KVl0dfid867XS9KadHdIAbE(audioEffectBoxView);
                }
            }
        });
    }
}
