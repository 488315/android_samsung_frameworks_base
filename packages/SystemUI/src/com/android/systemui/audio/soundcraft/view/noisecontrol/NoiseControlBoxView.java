package com.android.systemui.audio.soundcraft.view.noisecontrol;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.utils.ContextUtils;
import com.android.systemui.audio.soundcraft.utils.TransitionManagerUtil;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.view.SoundCraftViewComponent;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlIconViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AdaptiveViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientVolumeViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlOffViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoiseControlBoxView extends LinearLayout implements SoundCraftVMComponent {
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
    public NoiseCancelingSwitchBar noiseCancelingSwitchBar;
    public final Lazy noiseCancelingSwitchBarViewModel$delegate;
    public final Lazy noiseControlBoxViewModel$delegate;
    public final Lazy noiseControlEffectBoxViewModel$delegate;
    public NoiseControlLevelView noiseControlLevelView;
    public NoiseControlIconView noiseControlOffView;
    public final Lazy noiseControlOffViewModel$delegate;
    public final ArrayList positionList;
    public final List sequenceList;
    public NoiseControlBoxViewBinding viewBinding;

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

    public NoiseControlBoxView(Context context) {
        super(context);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED;
        this.noiseCancelingSwitchBarViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$1
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingSwitchBarViewModel.class);
            }
        });
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$2
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$3
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$4
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$5
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$6
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$7
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$8
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$9
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
            }
        });
        this.iconViewList = new ArrayList();
        this.sequenceList = new ArrayList();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        initViewModel();
        this.positionList = new ArrayList();
    }

    public static final void access$updateBoxLayout(NoiseControlBoxView noiseControlBoxView) {
        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxView.getNoiseControlBoxViewModel());
        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.getNoiseControlBoxViewModel();
        Boolean bool = (Boolean) noiseControlBoxViewModel.showActiveNoiseCancelingBarOnly.getValue();
        if (bool != null) {
            if (!bool.booleanValue()) {
                bool = null;
            }
            if (bool != null) {
                NoiseControlBoxViewBinding noiseControlBoxViewBinding = noiseControlBoxView.viewBinding;
                if (noiseControlBoxViewBinding == null) {
                    noiseControlBoxViewBinding = null;
                }
                noiseControlBoxViewBinding.boxContainer.setVisibility(8);
                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = noiseControlBoxView.viewBinding;
                if (noiseControlBoxViewBinding2 == null) {
                    noiseControlBoxViewBinding2 = null;
                }
                noiseControlBoxViewBinding2.noiseCancelingBar.setVisibility(0);
                NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                if (noiseControlBoxViewBinding3 == null) {
                    noiseControlBoxViewBinding3 = null;
                }
                noiseControlBoxViewBinding3.root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_active_noise_canceling_bar_height, noiseControlBoxView.getContext()));
                if (noiseControlBoxView.noiseCancelingSwitchBar == null) {
                    NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) noiseControlBoxView.noiseCancelingSwitchBarViewModel$delegate.getValue();
                    Context context = noiseControlBoxView.getContext();
                    LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(noiseControlBoxView);
                    Intrinsics.checkNotNull(lifecycleOwner);
                    NoiseCancelingSwitchBar noiseCancelingSwitchBar = new NoiseCancelingSwitchBar(context, lifecycleOwner, noiseCancelingSwitchBarViewModel);
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = noiseControlBoxView.viewBinding;
                    (noiseControlBoxViewBinding4 != null ? noiseControlBoxViewBinding4 : null).noiseCancelingBar.addView(noiseCancelingSwitchBar.binding.root);
                    noiseControlBoxView.noiseCancelingSwitchBar = noiseCancelingSwitchBar;
                    return;
                }
                return;
            }
        }
        Boolean bool2 = (Boolean) noiseControlBoxViewModel.showNoiseEffectBoxView.getValue();
        if (bool2 != null) {
            if (!bool2.booleanValue()) {
                bool2 = null;
            }
            if (bool2 != null) {
                NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = noiseControlBoxView.viewBinding;
                if (noiseControlBoxViewBinding5 == null) {
                    noiseControlBoxViewBinding5 = null;
                }
                noiseControlBoxViewBinding5.boxContainer.setVisibility(0);
                NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = noiseControlBoxView.viewBinding;
                if (noiseControlBoxViewBinding6 == null) {
                    noiseControlBoxViewBinding6 = null;
                }
                noiseControlBoxViewBinding6.noiseCancelingBar.setVisibility(8);
                NoiseControlBoxViewBinding noiseControlBoxViewBinding7 = noiseControlBoxView.viewBinding;
                (noiseControlBoxViewBinding7 != null ? noiseControlBoxViewBinding7 : null).root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, noiseControlBoxView.getContext()));
            }
        }
    }

    public final void addEffectIconView(NoiseControlIconView noiseControlIconView) {
        addSpace();
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = this.viewBinding;
        if (noiseControlBoxViewBinding == null) {
            noiseControlBoxViewBinding = null;
        }
        noiseControlBoxViewBinding.effectView.addView(noiseControlIconView.binding.root);
        this.sequenceList.add(noiseControlIconView);
        addSpace();
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
        Intrinsics.checkNotNull(lifecycleOwner);
        return new NoiseControlIconView(context, lifecycleOwner, noiseControlIconViewModel);
    }

    public final NoiseControlBoxViewModel getNoiseControlBoxViewModel() {
        return (NoiseControlBoxViewModel) this.noiseControlBoxViewModel$delegate.getValue();
    }

    public final NoiseControlEffectBoxViewModel getNoiseControlEffectBoxViewModel() {
        return (NoiseControlEffectBoxViewModel) this.noiseControlEffectBoxViewModel$delegate.getValue();
    }

    public final void initViewModel() {
        NoiseControlBoxViewModel noiseControlBoxViewModel = getNoiseControlBoxViewModel();
        MutableLiveData mutableLiveData = noiseControlBoxViewModel.showActiveNoiseCancelingBarOnly;
        Boolean bool = Boolean.FALSE;
        mutableLiveData.setValue(bool);
        noiseControlBoxViewModel.showNoiseEffectBoxView.setValue(bool);
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
        Intrinsics.checkNotNull(lifecycleOwner);
        getNoiseControlBoxViewModel().showActiveNoiseCancelingBarOnly.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlBoxView.access$updateBoxLayout(NoiseControlBoxView.this);
            }
        });
        getNoiseControlBoxViewModel().showNoiseEffectBoxView.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlBoxView.access$updateBoxLayout(NoiseControlBoxView.this);
            }
        });
        getNoiseControlEffectBoxViewModel().showNoiseControlOff.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.noiseControlOffView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        getNoiseControlEffectBoxViewModel().showAmbientSound.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.ambientSoundView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        getNoiseControlEffectBoxViewModel().showAmbientVolumeSeekBar.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlLevelView noiseControlLevelView = NoiseControlBoxView.this.ambientSoundVolumeView;
                if (noiseControlLevelView != null) {
                    noiseControlLevelView.viewModel.notifyChange();
                }
            }
        });
        getNoiseControlEffectBoxViewModel().showAdaptive.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.adaptiveView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        getNoiseControlEffectBoxViewModel().showActiveNoiseCanceling.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.activeNoiseCancelingView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        getNoiseControlEffectBoxViewModel().showActiveNoiseCancelingSeekBar.observe(lifecycleOwner, new Observer() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$onAttachedToWindow$8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NoiseControlLevelView noiseControlLevelView = NoiseControlBoxView.this.noiseControlLevelView;
                if (noiseControlLevelView != null) {
                    noiseControlLevelView.viewModel.notifyChange();
                }
            }
        });
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ViewTreeLifecycleOwner.get(this) != null) {
            updateLayout();
        }
    }

    public final void updateLayout() {
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
        if (((ArrayList) this.iconViewList).size() == 4) {
            Iterator it = ((ArrayList) this.iconViewList).iterator();
            while (it.hasNext()) {
                addEffectIconView((NoiseControlIconView) it.next());
            }
        } else if (((ArrayList) this.iconViewList).size() == 3) {
            addEffectIconView((NoiseControlIconView) ((ArrayList) this.iconViewList).get(2));
            addEffectIconView((NoiseControlIconView) ((ArrayList) this.iconViewList).get(0));
            addEffectIconView((NoiseControlIconView) ((ArrayList) this.iconViewList).get(1));
        }
        updateSeekBarView();
        NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = this.viewBinding;
        if (noiseControlBoxViewBinding2 == null) {
            noiseControlBoxViewBinding2 = null;
        }
        ViewGroup viewGroup = noiseControlBoxViewBinding2.effectView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!viewGroup.isLaidOut() || viewGroup.isLayoutRequested()) {
            viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$updateLayout$$inlined$doOnLayout$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    view.removeOnLayoutChangeListener(this);
                    NoiseControlBoxView.this.positionList.clear();
                    for (NoiseControlIconView noiseControlIconView : NoiseControlBoxView.this.sequenceList) {
                        float x = noiseControlIconView.binding.root.getX();
                        int dimensionPixelSize = NoiseControlBoxView.this.getContext().getResources().getDimensionPixelSize(R.dimen.soundcraft_noise_effect_box_icon_stroke);
                        NoiseControlIconViewBinding noiseControlIconViewBinding = noiseControlIconView.binding;
                        int measuredWidth = noiseControlIconViewBinding.icon.getMeasuredWidth();
                        int measuredWidth2 = noiseControlIconViewBinding.root.getMeasuredWidth();
                        if (!NoiseControlBoxView.this.positionList.isEmpty()) {
                            NoiseControlBoxView.this.positionList.add(Float.valueOf((((measuredWidth2 - measuredWidth) / 2.0f) + x) - (dimensionPixelSize * 2.5f)));
                        }
                        NoiseControlBoxView.this.positionList.add(Float.valueOf((dimensionPixelSize * 2.5f) + ((measuredWidth2 - measuredWidth) / 2.0f) + x + measuredWidth));
                    }
                    NoiseControlBoxView noiseControlBoxView = NoiseControlBoxView.this;
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = noiseControlBoxView.viewBinding;
                    if (noiseControlBoxViewBinding3 == null) {
                        noiseControlBoxViewBinding3 = null;
                    }
                    NoiseControlLineView noiseControlLineView = noiseControlBoxViewBinding3.noiseControlLineView;
                    ArrayList arrayList = noiseControlBoxView.positionList;
                    noiseControlLineView.getClass();
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    noiseControlLineView.addOnLayoutChangeListener(new NoiseControlLineView$setDataList$1(noiseControlLineView, arrayList));
                    noiseControlLineView.requestLayout();
                }
            });
        } else {
            this.positionList.clear();
            Iterator it2 = ((ArrayList) this.sequenceList).iterator();
            while (it2.hasNext()) {
                NoiseControlIconView noiseControlIconView = (NoiseControlIconView) it2.next();
                float x = noiseControlIconView.binding.root.getX();
                int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.soundcraft_noise_effect_box_icon_stroke);
                NoiseControlIconViewBinding noiseControlIconViewBinding = noiseControlIconView.binding;
                int measuredWidth = noiseControlIconViewBinding.icon.getMeasuredWidth();
                int measuredWidth2 = noiseControlIconViewBinding.root.getMeasuredWidth();
                if (!this.positionList.isEmpty()) {
                    this.positionList.add(Float.valueOf((((measuredWidth2 - measuredWidth) / 2.0f) + x) - (dimensionPixelSize * 2.5f)));
                }
                this.positionList.add(Float.valueOf((dimensionPixelSize * 2.5f) + ((measuredWidth2 - measuredWidth) / 2.0f) + x + measuredWidth));
            }
            NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = this.viewBinding;
            if (noiseControlBoxViewBinding3 == null) {
                noiseControlBoxViewBinding3 = null;
            }
            NoiseControlLineView noiseControlLineView = noiseControlBoxViewBinding3.noiseControlLineView;
            ArrayList arrayList = this.positionList;
            noiseControlLineView.getClass();
            if (!arrayList.isEmpty()) {
                noiseControlLineView.addOnLayoutChangeListener(new NoiseControlLineView$setDataList$1(noiseControlLineView, arrayList));
                noiseControlLineView.requestLayout();
            }
        }
        Context context = getContext();
        int i = ContextUtils.$r8$clinit;
        int i2 = context.getResources().getConfiguration().orientation == 2 ? R.dimen.soundcraft_noise_effect_box_start_end_padding_land : R.dimen.soundcraft_noise_effect_box_start_end_padding;
        NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = this.viewBinding;
        (noiseControlBoxViewBinding4 != null ? noiseControlBoxViewBinding4 : null).boxContainer.setPadding(ContextUtils.getDimenInt(i2, getContext()), ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_padding_top, getContext()), ContextUtils.getDimenInt(i2, getContext()), 0);
    }

    public final void updateSeekBarView() {
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
                Intrinsics.checkNotNull(lifecycleOwner);
                NoiseControlLevelView noiseControlLevelView2 = new NoiseControlLevelView(context, lifecycleOwner, noiseCancelingLevelViewModel);
                noiseControlLevelView2.viewModel.notifyChange();
                NoiseControlBoxViewBinding noiseControlBoxViewBinding2 = this.viewBinding;
                if (noiseControlBoxViewBinding2 == null) {
                    noiseControlBoxViewBinding2 = null;
                }
                noiseControlBoxViewBinding2.root.addView(noiseControlLevelView2.binding.root);
                TransitionManagerUtil transitionManagerUtil = TransitionManagerUtil.INSTANCE;
                NoiseControlBoxViewBinding noiseControlBoxViewBinding3 = this.viewBinding;
                if (noiseControlBoxViewBinding3 == null) {
                    noiseControlBoxViewBinding3 = null;
                }
                NoiseControlBoxView noiseControlBoxView = noiseControlBoxViewBinding3.root;
                transitionManagerUtil.getClass();
                TransitionManagerUtil.requestLayoutTransition(noiseControlBoxView);
                this.noiseControlLevelView = noiseControlLevelView2;
            }
        }
        Boolean bool2 = (Boolean) mutableLiveData.getValue();
        if (bool2 != null) {
            if (!bool2.booleanValue()) {
                NoiseControlLevelView noiseControlLevelView3 = this.ambientSoundVolumeView;
                if (noiseControlLevelView3 != null) {
                    NoiseControlBoxViewBinding noiseControlBoxViewBinding4 = this.viewBinding;
                    if (noiseControlBoxViewBinding4 == null) {
                        noiseControlBoxViewBinding4 = null;
                    }
                    noiseControlBoxViewBinding4.root.removeView(noiseControlLevelView3.binding.root);
                }
                this.ambientSoundVolumeView = null;
                return;
            }
            if (this.ambientSoundVolumeView == null) {
                AmbientVolumeViewModel ambientVolumeViewModel = (AmbientVolumeViewModel) this.ambientVolumeViewModel$delegate.getValue();
                Context context2 = getContext();
                LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(this);
                Intrinsics.checkNotNull(lifecycleOwner2);
                NoiseControlLevelView noiseControlLevelView4 = new NoiseControlLevelView(context2, lifecycleOwner2, ambientVolumeViewModel);
                noiseControlLevelView4.viewModel.notifyChange();
                NoiseControlBoxViewBinding noiseControlBoxViewBinding5 = this.viewBinding;
                if (noiseControlBoxViewBinding5 == null) {
                    noiseControlBoxViewBinding5 = null;
                }
                noiseControlBoxViewBinding5.root.addView(noiseControlLevelView4.binding.root);
                TransitionManagerUtil transitionManagerUtil2 = TransitionManagerUtil.INSTANCE;
                NoiseControlBoxViewBinding noiseControlBoxViewBinding6 = this.viewBinding;
                NoiseControlBoxView noiseControlBoxView2 = (noiseControlBoxViewBinding6 != null ? noiseControlBoxViewBinding6 : null).root;
                transitionManagerUtil2.getClass();
                TransitionManagerUtil.requestLayoutTransition(noiseControlBoxView2);
                this.ambientSoundVolumeView = noiseControlLevelView4;
            }
        }
    }

    public NoiseControlBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED;
        this.noiseCancelingSwitchBarViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$10
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingSwitchBarViewModel.class);
            }
        });
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$11
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$12
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$13
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$14
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$15
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$16
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$17
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$18
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
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
        this.noiseCancelingSwitchBarViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$19
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingSwitchBarViewModel.class);
            }
        });
        this.noiseControlBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$20
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlBoxViewModel.class);
            }
        });
        this.noiseControlEffectBoxViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$21
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlEffectBoxViewModel.class);
            }
        });
        this.noiseCancelingLevelViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$22
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseCancelingLevelViewModel.class);
            }
        });
        this.activeNoiseCancelingViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$23
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, ActiveNoiseCancelingViewModel.class);
            }
        });
        this.adaptiveViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$24
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AdaptiveViewModel.class);
            }
        });
        this.ambientSoundViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$25
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientSoundViewModel.class);
            }
        });
        this.noiseControlOffViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$26
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, NoiseControlOffViewModel.class);
            }
        });
        this.ambientVolumeViewModel$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView$special$$inlined$lazyViewModel$27
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
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, AmbientVolumeViewModel.class);
            }
        });
        this.iconViewList = new ArrayList();
        this.sequenceList = new ArrayList();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        initViewModel();
        this.positionList = new ArrayList();
    }
}
