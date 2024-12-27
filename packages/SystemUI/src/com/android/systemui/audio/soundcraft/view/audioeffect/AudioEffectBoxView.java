package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectBoxLayoutBinding;
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
import kotlin.jvm.internal.Intrinsics;

public final class AudioEffectBoxView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy dolbyView$delegate;
    public final Lazy equalizerView$delegate;
    public final Lazy spatialAudioView$delegate;
    public AudioEffectBoxLayoutBinding viewBinding;
    public final Lazy viewModel$delegate;
    public final Lazy voiceBoostView$delegate;
    public final Lazy volumeNormalizationView$delegate;

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

    public AudioEffectBoxView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$1
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
            }
        });
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$dolbyView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, DolbyViewModel.class));
            }
        });
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$spatialAudioView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, SpatialAudioViewModel.class));
            }
        });
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$equalizerView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class));
            }
        });
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$voiceBoostView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VoiceBoostViewModel.class));
            }
        });
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$volumeNormalizationView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeNormalizationViewModel.class));
            }
        });
    }

    public static final BaseAudioEffectItemView access$createItemView(AudioEffectBoxView audioEffectBoxView, BaseViewModel baseViewModel) {
        BaseAudioEffectItemView audioEffectToggleItemView;
        audioEffectBoxView.getClass();
        if (baseViewModel instanceof BaseSingleChoiceViewModel) {
            Context context = audioEffectBoxView.getContext();
            LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(audioEffectBoxView);
            Intrinsics.checkNotNull(lifecycleOwner);
            BaseSingleChoiceViewModel baseSingleChoiceViewModel = (BaseSingleChoiceViewModel) baseViewModel;
            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = audioEffectBoxView.viewBinding;
            audioEffectToggleItemView = new AudioEffectSingleChoiceItemView(context, lifecycleOwner, baseSingleChoiceViewModel, (audioEffectBoxLayoutBinding != null ? audioEffectBoxLayoutBinding : null).effectItemList);
        } else {
            if (!(baseViewModel instanceof BaseToggleViewModel)) {
                throw new RuntimeException();
            }
            Context context2 = audioEffectBoxView.getContext();
            LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(audioEffectBoxView);
            Intrinsics.checkNotNull(lifecycleOwner2);
            BaseToggleViewModel baseToggleViewModel = (BaseToggleViewModel) baseViewModel;
            AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding2 = audioEffectBoxView.viewBinding;
            audioEffectToggleItemView = new AudioEffectToggleItemView(context2, lifecycleOwner2, baseToggleViewModel, (audioEffectBoxLayoutBinding2 != null ? audioEffectBoxLayoutBinding2 : null).effectItemList);
        }
        return audioEffectToggleItemView;
    }

    public final void addDivider() {
        AudioEffectBoxLayoutBinding audioEffectBoxLayoutBinding = this.viewBinding;
        if (audioEffectBoxLayoutBinding == null) {
            audioEffectBoxLayoutBinding = null;
        }
        LinearLayout linearLayout = audioEffectBoxLayoutBinding.effectItemList;
        linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.soundcraft_effect_divider, (ViewGroup) linearLayout, false));
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
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$2
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
            }
        });
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$dolbyView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, DolbyViewModel.class));
            }
        });
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$spatialAudioView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, SpatialAudioViewModel.class));
            }
        });
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$equalizerView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class));
            }
        });
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$voiceBoostView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VoiceBoostViewModel.class));
            }
        });
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$volumeNormalizationView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeNormalizationViewModel.class));
            }
        });
    }

    public AudioEffectBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$special$$inlined$lazyViewModel$3
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AudioEffectBoxViewModel.class);
            }
        });
        this.dolbyView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$dolbyView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, DolbyViewModel.class));
            }
        });
        this.spatialAudioView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$spatialAudioView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, SpatialAudioViewModel.class));
            }
        });
        this.equalizerView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$equalizerView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, EqualizerViewModel.class));
            }
        });
        this.voiceBoostView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$voiceBoostView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VoiceBoostViewModel.class));
            }
        });
        this.volumeNormalizationView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView$volumeNormalizationView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AudioEffectBoxView audioEffectBoxView = AudioEffectBoxView.this;
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return AudioEffectBoxView.access$createItemView(audioEffectBoxView, (BaseViewModel) SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, VolumeNormalizationViewModel.class));
            }
        });
    }
}
