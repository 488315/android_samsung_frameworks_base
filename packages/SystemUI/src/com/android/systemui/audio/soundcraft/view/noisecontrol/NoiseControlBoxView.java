package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.utils.ContextUtils;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlIconViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AdaptiveViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientVolumeViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlOffViewModel;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class NoiseControlBoxView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public NoiseControlIconView activeNoiseCancelingView;
    public final Lazy activeNoiseCancelingViewModel$delegate;
    public NoiseControlIconView adaptiveView;
    public final Lazy adaptiveViewModel$delegate;
    public NoiseControlIconView ambientSoundView;
    public final Lazy ambientSoundViewModel$delegate;
    public NoiseControlLevelView ambientSoundVolumeView;
    public final Lazy ambientVolumeViewModel$delegate;
    public final List iconViewList;
    public final Lazy noiseCancelingLevelViewModel$delegate;
    public final Lazy noiseControlBoxViewModel$delegate;
    public final Lazy noiseControlEffectBoxViewModel$delegate;
    public NoiseControlLevelView noiseControlLevelView;
    public NoiseControlIconView noiseControlOffView;
    public final Lazy noiseControlOffViewModel$delegate;
    public final ArrayList positionList;
    public final List sequenceList;
    public NoiseControlBoxViewBinding viewBinding;

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

    public NoiseControlBoxView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED;
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.iconViewList = new ArrayList();
        this.sequenceList = new ArrayList();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        initViewModel();
        this.positionList = new ArrayList();
    }

    public final void addSpace() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.weight = 1.0f;
        Space space = (Space) LayoutInflater.from(getContext()).inflate(R.layout.soundcraft_effect_space, (ViewGroup) null);
        space.setLayoutParams(layoutParams);
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = this.viewBinding;
        (noiseControlBoxViewBinding != null ? noiseControlBoxViewBinding : null).effectView.addView(space);
    }

    public final NoiseControlIconView getIconView(NoiseControlIconViewModel noiseControlIconViewModel) {
        Context context = getContext();
        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
        lifecycleOwner.getClass();
        return new NoiseControlIconView(context, lifecycleOwner, noiseControlIconViewModel);
    }

    public final NoiseControlBoxViewModel getNoiseControlBoxViewModel() {
        return (NoiseControlBoxViewModel) this.noiseControlBoxViewModel$delegate.getValue();
    }

    public final NoiseControlEffectBoxViewModel getNoiseControlEffectBoxViewModel() {
        return (NoiseControlEffectBoxViewModel) this.noiseControlEffectBoxViewModel$delegate.getValue();
    }

    public final void initViewModel() {
        MutableLiveData mutableLiveData = getNoiseControlBoxViewModel().showNoiseEffectBoxView;
        Boolean bool = Boolean.FALSE;
        mutableLiveData.setValue(bool);
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel = getNoiseControlEffectBoxViewModel();
        noiseControlEffectBoxViewModel.showNoiseControlOff.setValue(bool);
        noiseControlEffectBoxViewModel.showAmbientSound.setValue(bool);
        noiseControlEffectBoxViewModel.showAmbientVolumeSeekBar.setValue(bool);
        noiseControlEffectBoxViewModel.showAdaptive.setValue(bool);
        noiseControlEffectBoxViewModel.showActiveNoiseCanceling.setValue(bool);
        noiseControlEffectBoxViewModel.showActiveNoiseCancelingSeekBar.setValue(bool);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
        lifecycleOwner.getClass();
        final int i = 0;
        getNoiseControlBoxViewModel().showNoiseEffectBoxView.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i2 = 1;
        getNoiseControlEffectBoxViewModel().showNoiseControlOff.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i3 = 2;
        getNoiseControlEffectBoxViewModel().showAmbientSound.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i4 = 3;
        getNoiseControlEffectBoxViewModel().showAmbientVolumeSeekBar.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i4) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i5 = 4;
        getNoiseControlEffectBoxViewModel().showAdaptive.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i5) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i6 = 5;
        getNoiseControlEffectBoxViewModel().showActiveNoiseCanceling.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i6) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
        final int i7 = 6;
        getNoiseControlEffectBoxViewModel().showActiveNoiseCancelingSeekBar.observe(lifecycleOwner, new NoiseControlBoxView$sam$androidx_lifecycle_Observer$0(new Function1(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$$ExternalSyntheticLambda0
            public final /* synthetic */ NoiseControlBoxView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NoiseControlBoxView noiseControlBoxView = this.f$0;
                switch (i7) {
                    case 0:
                        int i22 = NoiseControlBoxView.$r8$clinit;
                        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
                        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
                        Boolean bool = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
                        if (bool != null) {
                            if (!bool.booleanValue()) {
                                bool = null;
                            }
                            if (bool != null) {
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding == null) {
                                    noiseControlBoxViewBinding = null;
                                }
                                noiseControlBoxViewBinding.boxContainer.setVisibility(0);
                                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                                if (noiseControlBoxViewBinding2 == null) {
                                    noiseControlBoxViewBinding2 = null;
                                }
                                noiseControlBoxViewBinding2.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
                                if (noiseControlBoxViewModel.modelProvider.isFromCover) {
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding3 == null) {
                                        noiseControlBoxViewBinding3 = null;
                                    }
                                    noiseControlBoxViewBinding3.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding4 == null) {
                                        noiseControlBoxViewBinding4 = null;
                                    }
                                    noiseControlBoxViewBinding4.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding5 == null) {
                                        noiseControlBoxViewBinding5 = null;
                                    }
                                    noiseControlBoxViewBinding5.boxContainer.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_cover_noise_effect_height, noiseControlBoxView.getContext()));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding6 == null) {
                                        noiseControlBoxViewBinding6 = null;
                                    }
                                    noiseControlBoxViewBinding6.root.setBackground(noiseControlBoxView.getContext().getResources().getDrawable(R.drawable.soundcraft_cover_detailed_container_background, null));
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding7 == null) {
                                        noiseControlBoxViewBinding7 = null;
                                    }
                                    noiseControlBoxViewBinding7.root.setGravity(17);
                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                                    layoutParams.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding8 = noiseControlBoxView.viewBinding;
                                    if (noiseControlBoxViewBinding8 == null) {
                                        noiseControlBoxViewBinding8 = null;
                                    }
                                    noiseControlBoxViewBinding8.effectView.setLayoutParams(layoutParams);
                                    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, DimensionUtilsKt.dpToPx(50));
                                    layoutParams2.gravity = 17;
                                    NoiseControlBoxViewBinding noiseControlBoxViewBinding9 = noiseControlBoxView.viewBinding;
                                    (noiseControlBoxViewBinding9 != null ? noiseControlBoxViewBinding9 : null).noiseControlLineView.setLayoutParams(layoutParams2);
                                }
                            }
                        }
                        break;
                    case 1:
                        NoiseControlIconView noiseControlIconView = noiseControlBoxView.noiseControlOffView;
                        if (noiseControlIconView != null) {
                            noiseControlIconView.viewModel.notifyChange();
                        }
                        break;
                    case 2:
                        NoiseControlIconView noiseControlIconView2 = noiseControlBoxView.ambientSoundView;
                        if (noiseControlIconView2 != null) {
                            noiseControlIconView2.viewModel.notifyChange();
                        }
                        break;
                    case 3:
                        NoiseControlLevelView noiseControlLevelView = noiseControlBoxView.ambientSoundVolumeView;
                        if (noiseControlLevelView != null) {
                            noiseControlLevelView.viewModel.notifyChange();
                        }
                        break;
                    case 4:
                        NoiseControlIconView noiseControlIconView3 = noiseControlBoxView.adaptiveView;
                        if (noiseControlIconView3 != null) {
                            noiseControlIconView3.viewModel.notifyChange();
                        }
                        break;
                    case 5:
                        NoiseControlIconView noiseControlIconView4 = noiseControlBoxView.activeNoiseCancelingView;
                        if (noiseControlIconView4 != null) {
                            noiseControlIconView4.viewModel.notifyChange();
                        }
                        break;
                    default:
                        NoiseControlLevelView noiseControlLevelView2 = noiseControlBoxView.noiseControlLevelView;
                        if (noiseControlLevelView2 != null) {
                            noiseControlLevelView2.viewModel.notifyChange();
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        }));
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        if (ViewTreeLifecycleOwner.get(this) != null) {
            updateLayout$1();
        }
    }

    public final void updateLayout$1() throws Resources.NotFoundException {
        int i;
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = this.viewBinding;
        if (noiseControlBoxViewBinding == null) {
            noiseControlBoxViewBinding = null;
        }
        noiseControlBoxViewBinding.effectView.removeAllViews();
        ((ArrayList) this.iconViewList).clear();
        ((ArrayList) this.sequenceList).clear();
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel = getNoiseControlEffectBoxViewModel();
        Log.d("SoundCraft.NoiseControlBoxView", "updateLayout : " + getNoiseControlEffectBoxViewModel());
        Boolean bool = (Boolean) noiseControlEffectBoxViewModel.showNoiseControlOff.getValue();
        if (bool != null) {
            if (!bool.booleanValue()) {
                bool = null;
            }
            if (bool != null) {
                NoiseControlIconView iconView = getIconView((NoiseControlOffViewModel) this.noiseControlOffViewModel$delegate.getValue());
                ((ArrayList) this.iconViewList).add(iconView);
                this.noiseControlOffView = iconView;
            }
        }
        Boolean bool2 = (Boolean) noiseControlEffectBoxViewModel.showAmbientSound.getValue();
        if (bool2 != null) {
            if (!bool2.booleanValue()) {
                bool2 = null;
            }
            if (bool2 != null) {
                NoiseControlIconView iconView2 = getIconView((AmbientSoundViewModel) this.ambientSoundViewModel$delegate.getValue());
                ((ArrayList) this.iconViewList).add(iconView2);
                this.ambientSoundView = iconView2;
            }
        }
        Boolean bool3 = (Boolean) noiseControlEffectBoxViewModel.showAdaptive.getValue();
        if (bool3 != null) {
            if (!bool3.booleanValue()) {
                bool3 = null;
            }
            if (bool3 != null) {
                NoiseControlIconView iconView3 = getIconView((AdaptiveViewModel) this.adaptiveViewModel$delegate.getValue());
                ((ArrayList) this.iconViewList).add(iconView3);
                this.adaptiveView = iconView3;
            }
        }
        Boolean bool4 = (Boolean) noiseControlEffectBoxViewModel.showActiveNoiseCanceling.getValue();
        if (bool4 != null) {
            if (!bool4.booleanValue()) {
                bool4 = null;
            }
            if (bool4 != null) {
                NoiseControlIconView iconView4 = getIconView((ActiveNoiseCancelingViewModel) this.activeNoiseCancelingViewModel$delegate.getValue());
                ((ArrayList) this.iconViewList).add(iconView4);
                this.activeNoiseCancelingView = iconView4;
            }
        }
        ArrayList arrayList = (ArrayList) this.iconViewList;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            NoiseControlIconView noiseControlIconView = (NoiseControlIconView) obj;
            addSpace();
            NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = this.viewBinding;
            if (noiseControlBoxViewBinding2 == null) {
                noiseControlBoxViewBinding2 = null;
            }
            noiseControlBoxViewBinding2.effectView.addView(noiseControlIconView.binding.root);
            ((ArrayList) this.sequenceList).add(noiseControlIconView);
            addSpace();
        }
        updateSeekBarView$1();
        NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = this.viewBinding;
        if (noiseControlBoxViewBinding3 == null) {
            noiseControlBoxViewBinding3 = null;
        }
        ViewGroup viewGroup = noiseControlBoxViewBinding3.effectView;
        if (!viewGroup.isLaidOut() || viewGroup.isLayoutRequested()) {
            viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$updateLayout$$inlined$doOnLayout$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) throws Resources.NotFoundException {
                    view.removeOnLayoutChangeListener(this);
                    this.this$0.positionList.clear();
                    for (NoiseControlIconView noiseControlIconView2 : this.this$0.sequenceList) {
                        float x = noiseControlIconView2.binding.root.getX();
                        int dimensionPixelSize = this.this$0.getContext().getResources().getDimensionPixelSize(R.dimen.soundcraft_noise_effect_box_icon_stroke);
                        NoiseControlIconViewBinding noiseControlIconViewBinding = noiseControlIconView2.binding;
                        int measuredWidth = noiseControlIconViewBinding.icon.getMeasuredWidth();
                        int measuredWidth2 = noiseControlIconViewBinding.root.getMeasuredWidth();
                        if (!this.this$0.positionList.isEmpty()) {
                            this.this$0.positionList.add(Float.valueOf((((measuredWidth2 - measuredWidth) / 2.0f) + x) - (!this.this$0.getNoiseControlBoxViewModel().modelProvider.isFromCover ? dimensionPixelSize * 2.5f : dimensionPixelSize * 3.0f)));
                        }
                        this.this$0.positionList.add(Float.valueOf((dimensionPixelSize * 2.5f) + ((measuredWidth2 - measuredWidth) / 2.0f) + x + measuredWidth));
                    }
                    NoiseControlBoxView noiseControlBoxView = this.this$0;
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                    if (noiseControlBoxViewBinding4 == null) {
                        noiseControlBoxViewBinding4 = null;
                    }
                    NoiseControlLineView noiseControlLineView = noiseControlBoxViewBinding4.noiseControlLineView;
                    ArrayList arrayList2 = noiseControlBoxView.positionList;
                    noiseControlLineView.getClass();
                    if (arrayList2.isEmpty()) {
                        return;
                    }
                    noiseControlLineView.addOnLayoutChangeListener(new NoiseControlLineView$setDataList$1(noiseControlLineView, arrayList2));
                    noiseControlLineView.requestLayout();
                }
            });
        } else {
            this.positionList.clear();
            ArrayList arrayList2 = (ArrayList) this.sequenceList;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                NoiseControlIconView noiseControlIconView2 = (NoiseControlIconView) obj2;
                float x = noiseControlIconView2.binding.root.getX();
                int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.soundcraft_noise_effect_box_icon_stroke);
                NoiseControlIconViewBinding noiseControlIconViewBinding = noiseControlIconView2.binding;
                int measuredWidth = noiseControlIconViewBinding.icon.getMeasuredWidth();
                int measuredWidth2 = noiseControlIconViewBinding.root.getMeasuredWidth();
                if (!this.positionList.isEmpty()) {
                    this.positionList.add(Float.valueOf((((measuredWidth2 - measuredWidth) / 2.0f) + x) - (!getNoiseControlBoxViewModel().modelProvider.isFromCover ? dimensionPixelSize * 2.5f : dimensionPixelSize * 3.0f)));
                }
                this.positionList.add(Float.valueOf((dimensionPixelSize * 2.5f) + ((measuredWidth2 - measuredWidth) / 2.0f) + x + measuredWidth));
            }
            NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = this.viewBinding;
            if (noiseControlBoxViewBinding4 == null) {
                noiseControlBoxViewBinding4 = null;
            }
            NoiseControlLineView noiseControlLineView = noiseControlBoxViewBinding4.noiseControlLineView;
            ArrayList arrayList3 = this.positionList;
            noiseControlLineView.getClass();
            if (!arrayList3.isEmpty()) {
                noiseControlLineView.addOnLayoutChangeListener(new NoiseControlLineView$setDataList$1(noiseControlLineView, arrayList3));
                noiseControlLineView.requestLayout();
            }
        }
        if (getNoiseControlBoxViewModel().modelProvider.isFromCover) {
            i = R.dimen.soundcraft_noise_effect_box_line_width;
        } else {
            Context context = getContext();
            int i4 = ContextUtils.$r8$clinit;
            i = context.getResources().getConfiguration().orientation == 2 ? R.dimen.soundcraft_noise_effect_box_start_end_padding_land : R.dimen.soundcraft_noise_effect_box_start_end_padding;
        }
        int dimenInt = getNoiseControlBoxViewModel().modelProvider.isFromCover ? 0 : ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_padding_top, getContext());
        NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = this.viewBinding;
        (noiseControlBoxViewBinding5 != null ? noiseControlBoxViewBinding5 : null).boxContainer.setPadding(ContextUtils.getDimenInt(i, getContext()), dimenInt, ContextUtils.getDimenInt(i, getContext()), 0);
    }

    public final void updateSeekBarView$1() {
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel = getNoiseControlEffectBoxViewModel();
        Object value = noiseControlEffectBoxViewModel.showActiveNoiseCancelingSeekBar.getValue();
        MutableLiveData mutableLiveData = noiseControlEffectBoxViewModel.showAmbientVolumeSeekBar;
        Log.d("SoundCraft.NoiseControlBoxView", "updateSeekBarView showActiveNoiseCancelingSeekBar=" + value + ", showAmbientVolumeSeekBar=" + mutableLiveData.getValue());
        Boolean bool = (Boolean) noiseControlEffectBoxViewModel.showActiveNoiseCancelingSeekBar.getValue();
        if (bool != null) {
            if (!bool.booleanValue()) {
                NoiseControlLevelView noiseControlLevelView = this.noiseControlLevelView;
                if (noiseControlLevelView != null) {
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding = this.viewBinding;
                    if (noiseControlBoxViewBinding == null) {
                        noiseControlBoxViewBinding = null;
                    }
                    noiseControlBoxViewBinding.root.removeView(noiseControlLevelView.binding.root);
                }
                this.noiseControlLevelView = null;
            } else if (this.noiseControlLevelView == null) {
                NoiseCancelingLevelViewModel noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) this.noiseCancelingLevelViewModel$delegate.getValue();
                Context context = getContext();
                LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
                lifecycleOwner.getClass();
                NoiseControlLevelView noiseControlLevelView2 = new NoiseControlLevelView(context, lifecycleOwner, noiseCancelingLevelViewModel);
                noiseControlLevelView2.viewModel.notifyChange();
                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = this.viewBinding;
                if (noiseControlBoxViewBinding2 == null) {
                    noiseControlBoxViewBinding2 = null;
                }
                noiseControlBoxViewBinding2.root.addView(noiseControlLevelView2.binding.root);
                this.noiseControlLevelView = noiseControlLevelView2;
            }
        }
        Boolean bool2 = (Boolean) mutableLiveData.getValue();
        if (bool2 != null) {
            if (!bool2.booleanValue()) {
                NoiseControlLevelView noiseControlLevelView3 = this.ambientSoundVolumeView;
                if (noiseControlLevelView3 != null) {
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = this.viewBinding;
                    if (noiseControlBoxViewBinding3 == null) {
                        noiseControlBoxViewBinding3 = null;
                    }
                    noiseControlBoxViewBinding3.root.removeView(noiseControlLevelView3.binding.root);
                }
                this.ambientSoundVolumeView = null;
                return;
            }
            if (this.ambientSoundVolumeView == null) {
                AmbientVolumeViewModel ambientVolumeViewModel = (AmbientVolumeViewModel) this.ambientVolumeViewModel$delegate.getValue();
                Context context2 = getContext();
                LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(this);
                lifecycleOwner2.getClass();
                NoiseControlLevelView noiseControlLevelView4 = new NoiseControlLevelView(context2, lifecycleOwner2, ambientVolumeViewModel);
                noiseControlLevelView4.viewModel.notifyChange();
                NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = this.viewBinding;
                (noiseControlBoxViewBinding4 != null ? noiseControlBoxViewBinding4 : null).root.addView(noiseControlLevelView4.binding.root);
                this.ambientSoundVolumeView = noiseControlLevelView4;
            }
        }
    }

    public NoiseControlBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED;
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.iconViewList = new ArrayList();
        this.sequenceList = new ArrayList();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        initViewModel();
        this.positionList = new ArrayList();
    }

    public NoiseControlBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED;
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.iconViewList = new ArrayList();
        this.sequenceList = new ArrayList();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        initViewModel();
        this.positionList = new ArrayList();
    }
}
