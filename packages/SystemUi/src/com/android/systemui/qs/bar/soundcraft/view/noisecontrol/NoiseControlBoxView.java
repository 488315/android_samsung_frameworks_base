package com.android.systemui.qs.bar.soundcraft.view.noisecontrol;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeClipBounds;
import androidx.transition.ChangeScroll;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.di.vm.SoundCraftViewModelProvider;
import com.android.systemui.qs.bar.soundcraft.utils.TransitionManagerUtil;
import com.android.systemui.qs.bar.soundcraft.viewbinding.NoiseControlBoxViewBinding;
import com.android.systemui.qs.bar.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlIconViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel;
import com.android.systemui.qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel;
import com.android.systemui.volume.util.ContextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlBoxView extends LinearLayout implements LifecycleOwner {
    public NoiseControlIconView activeNoiseCancelingView;
    public ActiveNoiseCancelingViewModel activeNoiseCancelingViewModel;
    public NoiseControlIconView adaptiveView;
    public AdaptiveViewModel adaptiveViewModel;
    public NoiseControlIconView ambientSoundView;
    public AmbientSoundViewModel ambientSoundViewModel;
    public final List iconViewList;
    public NoiseCancelingLevelView noiseCancelingLevelView;
    public NoiseCancelingLevelViewModel noiseCancelingLevelViewModel;
    public NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel;
    public NoiseControlBoxViewModel noiseControlBoxViewModel;
    public NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel;
    public NoiseControlIconView noiseControlOffView;
    public NoiseControlOffViewModel noiseControlOffViewModel;
    public final LifecycleRegistry registry;
    public NoiseControlBoxViewBinding viewBinding;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.iconViewList = new ArrayList();
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void access$updateBoxLayout(NoiseControlBoxView noiseControlBoxView) {
        NoiseControlBoxViewModel noiseControlBoxViewModel = noiseControlBoxView.noiseControlBoxViewModel;
        if (noiseControlBoxViewModel == null) {
            noiseControlBoxViewModel = null;
        }
        Log.d("SoundCraft.NoiseControlBoxView", "updateBoxLayout : " + noiseControlBoxViewModel);
        noiseControlBoxView.getViewBinding().noiseCancelingBar.removeAllViews();
        NoiseControlBoxViewModel noiseControlBoxViewModel2 = noiseControlBoxView.noiseControlBoxViewModel;
        if (noiseControlBoxViewModel2 == null) {
            noiseControlBoxViewModel2 = null;
        }
        Boolean bool = (Boolean) noiseControlBoxViewModel2.showActiveNoiseCancelingBarOnly.getValue();
        Context context = noiseControlBoxViewModel2.context;
        if (bool != null) {
            if (!bool.booleanValue()) {
                bool = null;
            }
            if (bool != null) {
                noiseControlBoxView.getViewBinding().boxContainer.setVisibility(8);
                noiseControlBoxView.getViewBinding().noiseCancelingBar.setVisibility(0);
                noiseControlBoxView.getViewBinding().root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_active_noise_canceling_bar_height, context));
                ViewGroup viewGroup = noiseControlBoxView.getViewBinding().noiseCancelingBar;
                NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel = noiseControlBoxView.noiseCancelingSwitchBarViewModel;
                viewGroup.addView(new NoiseCancelingSwitchBar(noiseControlBoxView.getContext(), noiseControlBoxView, noiseCancelingSwitchBarViewModel != null ? noiseCancelingSwitchBarViewModel : null).binding.root);
                return;
            }
        }
        Boolean bool2 = (Boolean) noiseControlBoxViewModel2.showNoiseEffectBoxView.getValue();
        if (bool2 != 0) {
            if ((bool2.booleanValue() ? bool2 : null) != null) {
                noiseControlBoxView.getViewBinding().boxContainer.setVisibility(0);
                noiseControlBoxView.getViewBinding().noiseCancelingBar.setVisibility(8);
                noiseControlBoxView.getViewBinding().root.setMinimumHeight(ContextUtils.getDimenInt(R.dimen.soundcraft_noise_effect_box_height, context));
            }
        }
    }

    public final void addSpace() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.weight = 1.0f;
        Space space = (Space) LayoutInflater.from(getContext()).inflate(R.layout.soundcraft_effect_space, (ViewGroup) null);
        space.setLayoutParams(layoutParams);
        getViewBinding().effectView.addView(space);
    }

    public final void bindViewModel(SoundCraftViewModelProvider soundCraftViewModelProvider) {
        NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel;
        NoiseControlBoxViewModel noiseControlBoxViewModel;
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel;
        NoiseCancelingLevelViewModel noiseCancelingLevelViewModel;
        ActiveNoiseCancelingViewModel activeNoiseCancelingViewModel;
        AdaptiveViewModel adaptiveViewModel;
        AmbientSoundViewModel ambientSoundViewModel;
        NoiseControlOffViewModel noiseControlOffViewModel;
        Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : viewModel=" + soundCraftViewModelProvider);
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) craftViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) actionBarViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel2 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) noiseControlBoxViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) audioEffectBoxViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) audioEffectHeaderViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) wearableLinkBoxViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) spatialAudioViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) equalizerViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) voiceBoostViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) volumeNormalizationViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel activeNoiseCancelingViewModel2 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) activeNoiseCancelingViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel adaptiveViewModel2 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) adaptiveViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel ambientSoundViewModel2 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) ambientSoundViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) noiseCancelingViewModel;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel2 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) noiseControlEffectBoxViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel noiseControlOffViewModel2 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) noiseControlOffViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            noiseCancelingSwitchBarViewModel = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel");
            }
            noiseCancelingSwitchBarViewModel = (NoiseCancelingSwitchBarViewModel) routineTestViewModel;
        }
        this.noiseCancelingSwitchBarViewModel = noiseCancelingSwitchBarViewModel;
        ClassReference orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel2 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) craftViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel2 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) actionBarViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            noiseControlBoxViewModel = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel2 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) audioEffectBoxViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel2 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) audioEffectHeaderViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel2 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) wearableLinkBoxViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel2 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) spatialAudioViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel2 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) equalizerViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel2 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) voiceBoostViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel2 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) volumeNormalizationViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel activeNoiseCancelingViewModel3 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) activeNoiseCancelingViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel adaptiveViewModel3 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) adaptiveViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel ambientSoundViewModel3 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) ambientSoundViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel2 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) noiseCancelingViewModel2;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel3 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) noiseControlEffectBoxViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel noiseControlOffViewModel3 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) noiseControlOffViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel2 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) noiseCancelingSwitchBarViewModel2;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass2, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel2 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel");
            }
            noiseControlBoxViewModel = (NoiseControlBoxViewModel) routineTestViewModel2;
        }
        this.noiseControlBoxViewModel = noiseControlBoxViewModel;
        ClassReference orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel3 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) craftViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel3 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) actionBarViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel3 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) noiseControlBoxViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel3 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) audioEffectBoxViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel3 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) audioEffectHeaderViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel3 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) wearableLinkBoxViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel3 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) spatialAudioViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel3 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) equalizerViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel3 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) voiceBoostViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel3 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) volumeNormalizationViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel activeNoiseCancelingViewModel4 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) activeNoiseCancelingViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel adaptiveViewModel4 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) adaptiveViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel ambientSoundViewModel4 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) ambientSoundViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel3 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) noiseCancelingViewModel3;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            noiseControlEffectBoxViewModel = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel noiseControlOffViewModel4 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) noiseControlOffViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel3 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) noiseCancelingSwitchBarViewModel3;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass3, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel3 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel");
            }
            noiseControlEffectBoxViewModel = (NoiseControlEffectBoxViewModel) routineTestViewModel3;
        }
        this.noiseControlEffectBoxViewModel = noiseControlEffectBoxViewModel;
        ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel4 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) craftViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel4 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) actionBarViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel4 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) noiseControlBoxViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel4 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) audioEffectBoxViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel4 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) audioEffectHeaderViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel4 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) wearableLinkBoxViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel4 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) spatialAudioViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel4 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) equalizerViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel4 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) voiceBoostViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel4 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) volumeNormalizationViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            BaseViewModel activeNoiseCancelingViewModel5 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) activeNoiseCancelingViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            BaseViewModel adaptiveViewModel5 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) adaptiveViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            BaseViewModel ambientSoundViewModel5 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) ambientSoundViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            noiseCancelingLevelViewModel = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingLevelViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel4 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) noiseControlEffectBoxViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            BaseViewModel noiseControlOffViewModel5 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) noiseControlOffViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel4 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) noiseCancelingSwitchBarViewModel4;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass4, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel4 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel");
            }
            noiseCancelingLevelViewModel = (NoiseCancelingLevelViewModel) routineTestViewModel4;
        }
        this.noiseCancelingLevelViewModel = noiseCancelingLevelViewModel;
        ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel5 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) craftViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel5 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) actionBarViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel5 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) noiseControlBoxViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel5 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) audioEffectBoxViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel5 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) audioEffectHeaderViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel5 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) wearableLinkBoxViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel5 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) spatialAudioViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel5 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) equalizerViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel5 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) voiceBoostViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel5 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) volumeNormalizationViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            activeNoiseCancelingViewModel = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            NoiseControlIconViewModel adaptiveViewModel6 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) adaptiveViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            NoiseControlIconViewModel ambientSoundViewModel6 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) ambientSoundViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel4 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel4 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) noiseCancelingViewModel4;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel5 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) noiseControlEffectBoxViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            NoiseControlIconViewModel noiseControlOffViewModel6 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) noiseControlOffViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel5 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) noiseCancelingSwitchBarViewModel5;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass5, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel5 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel");
            }
            activeNoiseCancelingViewModel = (ActiveNoiseCancelingViewModel) routineTestViewModel5;
        }
        this.activeNoiseCancelingViewModel = activeNoiseCancelingViewModel;
        ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel6 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) craftViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel6 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) actionBarViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel6 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) noiseControlBoxViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel6 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) audioEffectBoxViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel6 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) audioEffectHeaderViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel6 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) wearableLinkBoxViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel6 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) spatialAudioViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel6 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) equalizerViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel6 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) voiceBoostViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel6 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) volumeNormalizationViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            NoiseControlIconViewModel activeNoiseCancelingViewModel6 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) activeNoiseCancelingViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            adaptiveViewModel = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            NoiseControlIconViewModel ambientSoundViewModel7 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) ambientSoundViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel5 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) noiseCancelingViewModel5;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel6 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) noiseControlEffectBoxViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            NoiseControlIconViewModel noiseControlOffViewModel7 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) noiseControlOffViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel6 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) noiseCancelingSwitchBarViewModel6;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass6, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel6 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel");
            }
            adaptiveViewModel = (AdaptiveViewModel) routineTestViewModel6;
        }
        this.adaptiveViewModel = adaptiveViewModel;
        ClassReference orCreateKotlinClass7 = Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel7 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) craftViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel7 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) actionBarViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel7 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) noiseControlBoxViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel7 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) audioEffectBoxViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel7 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) audioEffectHeaderViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel7 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) wearableLinkBoxViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel7 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) spatialAudioViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel7 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) equalizerViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel7 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) voiceBoostViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel7 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) volumeNormalizationViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            NoiseControlIconViewModel activeNoiseCancelingViewModel7 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) activeNoiseCancelingViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            NoiseControlIconViewModel adaptiveViewModel7 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) adaptiveViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            ambientSoundViewModel = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel6 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) noiseCancelingViewModel6;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel7 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) noiseControlEffectBoxViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            NoiseControlIconViewModel noiseControlOffViewModel8 = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) noiseControlOffViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel7 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) noiseCancelingSwitchBarViewModel7;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass7, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel7 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel");
            }
            ambientSoundViewModel = (AmbientSoundViewModel) routineTestViewModel7;
        }
        this.ambientSoundViewModel = ambientSoundViewModel;
        ClassReference orCreateKotlinClass8 = Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class);
        if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(SoundCraftViewModel.class))) {
            BaseViewModel craftViewModel8 = soundCraftViewModelProvider.getCraftViewModel();
            if (craftViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) craftViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(SoundCraftActionBarViewModel.class))) {
            BaseViewModel actionBarViewModel8 = soundCraftViewModelProvider.getActionBarViewModel();
            if (actionBarViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) actionBarViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(NoiseControlBoxViewModel.class))) {
            BaseViewModel noiseControlBoxViewModel8 = soundCraftViewModelProvider.getNoiseControlBoxViewModel();
            if (noiseControlBoxViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) noiseControlBoxViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(AudioEffectBoxViewModel.class))) {
            BaseViewModel audioEffectBoxViewModel8 = soundCraftViewModelProvider.getAudioEffectBoxViewModel();
            if (audioEffectBoxViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) audioEffectBoxViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(AudioEffectHeaderViewModel.class))) {
            BaseViewModel audioEffectHeaderViewModel8 = soundCraftViewModelProvider.getAudioEffectHeaderViewModel();
            if (audioEffectHeaderViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) audioEffectHeaderViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(WearableLinkBoxViewModel.class))) {
            BaseViewModel wearableLinkBoxViewModel8 = soundCraftViewModelProvider.getWearableLinkBoxViewModel();
            if (wearableLinkBoxViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) wearableLinkBoxViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(SpatialAudioViewModel.class))) {
            BaseViewModel spatialAudioViewModel8 = soundCraftViewModelProvider.getSpatialAudioViewModel();
            if (spatialAudioViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) spatialAudioViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(EqualizerViewModel.class))) {
            BaseViewModel equalizerViewModel8 = soundCraftViewModelProvider.getEqualizerViewModel();
            if (equalizerViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) equalizerViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(VoiceBoostViewModel.class))) {
            BaseViewModel voiceBoostViewModel8 = soundCraftViewModelProvider.getVoiceBoostViewModel();
            if (voiceBoostViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) voiceBoostViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(VolumeNormalizationViewModel.class))) {
            BaseViewModel volumeNormalizationViewModel8 = soundCraftViewModelProvider.getVolumeNormalizationViewModel();
            if (volumeNormalizationViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) volumeNormalizationViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(ActiveNoiseCancelingViewModel.class))) {
            NoiseControlIconViewModel activeNoiseCancelingViewModel8 = soundCraftViewModelProvider.getActiveNoiseCancelingViewModel();
            if (activeNoiseCancelingViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) activeNoiseCancelingViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(AdaptiveViewModel.class))) {
            NoiseControlIconViewModel adaptiveViewModel8 = soundCraftViewModelProvider.getAdaptiveViewModel();
            if (adaptiveViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) adaptiveViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(AmbientSoundViewModel.class))) {
            NoiseControlIconViewModel ambientSoundViewModel8 = soundCraftViewModelProvider.getAmbientSoundViewModel();
            if (ambientSoundViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) ambientSoundViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(NoiseCancelingLevelViewModel.class))) {
            BaseViewModel noiseCancelingViewModel7 = soundCraftViewModelProvider.getNoiseCancelingViewModel();
            if (noiseCancelingViewModel7 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) noiseCancelingViewModel7;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(NoiseControlEffectBoxViewModel.class))) {
            BaseViewModel noiseControlEffectBoxViewModel8 = soundCraftViewModelProvider.getNoiseControlEffectBoxViewModel();
            if (noiseControlEffectBoxViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) noiseControlEffectBoxViewModel8;
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(NoiseControlOffViewModel.class))) {
            noiseControlOffViewModel = soundCraftViewModelProvider.getNoiseControlOffViewModel();
            if (noiseControlOffViewModel == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(NoiseCancelingSwitchBarViewModel.class))) {
            BaseViewModel noiseCancelingSwitchBarViewModel8 = soundCraftViewModelProvider.getNoiseCancelingSwitchBarViewModel();
            if (noiseCancelingSwitchBarViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) noiseCancelingSwitchBarViewModel8;
        } else {
            if (!Intrinsics.areEqual(orCreateKotlinClass8, Reflection.getOrCreateKotlinClass(RoutineTestViewModel.class))) {
                throw new RuntimeException();
            }
            BaseViewModel routineTestViewModel8 = soundCraftViewModelProvider.getRoutineTestViewModel();
            if (routineTestViewModel8 == null) {
                throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel");
            }
            noiseControlOffViewModel = (NoiseControlOffViewModel) routineTestViewModel8;
        }
        this.noiseControlOffViewModel = noiseControlOffViewModel;
        NoiseControlBoxViewModel noiseControlBoxViewModel9 = this.noiseControlBoxViewModel;
        if (noiseControlBoxViewModel9 == null) {
            noiseControlBoxViewModel9 = null;
        }
        noiseControlBoxViewModel9.notifyChange();
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel9 = this.noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel9 == null) {
            noiseControlEffectBoxViewModel9 = null;
        }
        noiseControlEffectBoxViewModel9.notifyChange();
        NoiseControlBoxViewModel noiseControlBoxViewModel10 = this.noiseControlBoxViewModel;
        if (noiseControlBoxViewModel10 == null) {
            noiseControlBoxViewModel10 = null;
        }
        noiseControlBoxViewModel10.getShowActiveNoiseCancelingBarOnly().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showActiveNoiseCancelingBarOnly=" + ((Boolean) obj));
                NoiseControlBoxView.access$updateBoxLayout(NoiseControlBoxView.this);
            }
        });
        NoiseControlBoxViewModel noiseControlBoxViewModel11 = this.noiseControlBoxViewModel;
        if (noiseControlBoxViewModel11 == null) {
            noiseControlBoxViewModel11 = null;
        }
        noiseControlBoxViewModel11.getShowNoiseEffectBoxView().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showNoiseEffectBoxView=" + ((Boolean) obj));
                NoiseControlBoxView.access$updateBoxLayout(NoiseControlBoxView.this);
            }
        });
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel10 = this.noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel10 == null) {
            noiseControlEffectBoxViewModel10 = null;
        }
        noiseControlEffectBoxViewModel10.getShowNoiseControlOff().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showNoiseControlOff=" + ((Boolean) obj));
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.noiseControlOffView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel11 = this.noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel11 == null) {
            noiseControlEffectBoxViewModel11 = null;
        }
        noiseControlEffectBoxViewModel11.getShowAmbientSound().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showAmbientSound=" + ((Boolean) obj));
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.ambientSoundView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel12 = this.noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel12 == null) {
            noiseControlEffectBoxViewModel12 = null;
        }
        noiseControlEffectBoxViewModel12.getShowAdaptive().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showAdaptive=" + ((Boolean) obj));
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.adaptiveView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel13 = this.noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel13 == null) {
            noiseControlEffectBoxViewModel13 = null;
        }
        noiseControlEffectBoxViewModel13.getShowActiveNoiseCanceling().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : showActiveNoiseCanceling=" + ((Boolean) obj));
                NoiseControlIconView noiseControlIconView = NoiseControlBoxView.this.activeNoiseCancelingView;
                if (noiseControlIconView != null) {
                    noiseControlIconView.viewModel.notifyChange();
                }
            }
        });
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel14 = this.noiseControlEffectBoxViewModel;
        (noiseControlEffectBoxViewModel14 != null ? noiseControlEffectBoxViewModel14 : null).getShowActiveNoiseCancelingSeekBar().observe(this, new Observer() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$bindViewModel$7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Log.d("SoundCraft.NoiseControlBoxView", "bindViewModel : enableActiveNoiseCanceling=" + ((Boolean) obj));
                NoiseCancelingLevelView noiseCancelingLevelView = NoiseControlBoxView.this.noiseCancelingLevelView;
                if (noiseCancelingLevelView != null) {
                    noiseCancelingLevelView.viewModel.notifyChange();
                }
            }
        });
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.registry;
    }

    public final NoiseControlBoxViewBinding getViewBinding() {
        NoiseControlBoxViewBinding noiseControlBoxViewBinding = this.viewBinding;
        if (noiseControlBoxViewBinding != null) {
            return noiseControlBoxViewBinding;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.registry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    public final void updateLayout() {
        getViewBinding().effectView.removeAllViews();
        ((ArrayList) this.iconViewList).clear();
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel = this.noiseControlEffectBoxViewModel;
        NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel2 = noiseControlEffectBoxViewModel == null ? null : noiseControlEffectBoxViewModel;
        if (noiseControlEffectBoxViewModel == null) {
            noiseControlEffectBoxViewModel = null;
        }
        Log.d("SoundCraft.NoiseControlBoxView", "updateLayout : " + noiseControlEffectBoxViewModel);
        Boolean bool = (Boolean) noiseControlEffectBoxViewModel2.showNoiseControlOff.getValue();
        if (bool != null) {
            if (!bool.booleanValue()) {
                bool = null;
            }
            if (bool != null) {
                NoiseControlOffViewModel noiseControlOffViewModel = this.noiseControlOffViewModel;
                if (noiseControlOffViewModel == null) {
                    noiseControlOffViewModel = null;
                }
                NoiseControlIconView noiseControlIconView = new NoiseControlIconView(getContext(), this, noiseControlOffViewModel);
                getViewBinding().effectView.addView(noiseControlIconView.binding.root);
                ((ArrayList) this.iconViewList).add(noiseControlIconView);
                addSpace();
                this.noiseControlOffView = noiseControlIconView;
            }
        }
        Boolean bool2 = (Boolean) noiseControlEffectBoxViewModel2.showAmbientSound.getValue();
        if (bool2 != null) {
            if (!bool2.booleanValue()) {
                bool2 = null;
            }
            if (bool2 != null) {
                AmbientSoundViewModel ambientSoundViewModel = this.ambientSoundViewModel;
                if (ambientSoundViewModel == null) {
                    ambientSoundViewModel = null;
                }
                NoiseControlIconView noiseControlIconView2 = new NoiseControlIconView(getContext(), this, ambientSoundViewModel);
                getViewBinding().effectView.addView(noiseControlIconView2.binding.root);
                ((ArrayList) this.iconViewList).add(noiseControlIconView2);
                addSpace();
                this.ambientSoundView = noiseControlIconView2;
            }
        }
        Boolean bool3 = (Boolean) noiseControlEffectBoxViewModel2.showAdaptive.getValue();
        if (bool3 != null) {
            if (!bool3.booleanValue()) {
                bool3 = null;
            }
            if (bool3 != null) {
                AdaptiveViewModel adaptiveViewModel = this.adaptiveViewModel;
                if (adaptiveViewModel == null) {
                    adaptiveViewModel = null;
                }
                NoiseControlIconView noiseControlIconView3 = new NoiseControlIconView(getContext(), this, adaptiveViewModel);
                getViewBinding().effectView.addView(noiseControlIconView3.binding.root);
                ((ArrayList) this.iconViewList).add(noiseControlIconView3);
                addSpace();
                this.adaptiveView = noiseControlIconView3;
            }
        }
        Boolean bool4 = (Boolean) noiseControlEffectBoxViewModel2.showActiveNoiseCanceling.getValue();
        if (bool4 != null) {
            if (!bool4.booleanValue()) {
                bool4 = null;
            }
            if (bool4 != null) {
                ActiveNoiseCancelingViewModel activeNoiseCancelingViewModel = this.activeNoiseCancelingViewModel;
                if (activeNoiseCancelingViewModel == null) {
                    activeNoiseCancelingViewModel = null;
                }
                NoiseControlIconView noiseControlIconView4 = new NoiseControlIconView(getContext(), this, activeNoiseCancelingViewModel);
                getViewBinding().effectView.addView(noiseControlIconView4.binding.root);
                ((ArrayList) this.iconViewList).add(noiseControlIconView4);
                this.activeNoiseCancelingView = noiseControlIconView4;
            }
        }
        Boolean bool5 = (Boolean) noiseControlEffectBoxViewModel2.showActiveNoiseCancelingSeekBar.getValue();
        if (bool5 != null) {
            if (!bool5.booleanValue()) {
                NoiseCancelingLevelView noiseCancelingLevelView = this.noiseCancelingLevelView;
                if (noiseCancelingLevelView != null) {
                    getViewBinding().root.removeView(noiseCancelingLevelView.binding.root);
                }
                this.noiseCancelingLevelView = null;
            } else if (this.noiseCancelingLevelView == null) {
                NoiseCancelingLevelViewModel noiseCancelingLevelViewModel = this.noiseCancelingLevelViewModel;
                NoiseCancelingLevelView noiseCancelingLevelView2 = new NoiseCancelingLevelView(getContext(), this, noiseCancelingLevelViewModel != null ? noiseCancelingLevelViewModel : null);
                getViewBinding().root.addView(noiseCancelingLevelView2.binding.root);
                TransitionManagerUtil transitionManagerUtil = TransitionManagerUtil.INSTANCE;
                NoiseControlBoxView noiseControlBoxView = getViewBinding().root;
                transitionManagerUtil.getClass();
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(new ChangeTransform());
                transitionSet.addTransition(new ChangeBounds());
                transitionSet.addTransition(new ChangeClipBounds());
                transitionSet.addTransition(new ChangeScroll());
                transitionSet.setDuration(250L);
                transitionSet.setInterpolator(new DecelerateInterpolator());
                TransitionManager.beginDelayedTransition(transitionSet, noiseControlBoxView);
                noiseControlBoxView.requestLayout();
                this.noiseCancelingLevelView = noiseCancelingLevelView2;
            }
        }
        if (((ArrayList) this.iconViewList).size() >= 3) {
            final int size = ((ArrayList) this.iconViewList).size();
            ViewGroup viewGroup = getViewBinding().effectView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (!ViewCompat.Api19Impl.isLaidOut(viewGroup) || viewGroup.isLayoutRequested()) {
                viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView$applyDrawable$$inlined$doOnLayout$1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        view.removeOnLayoutChangeListener(this);
                        NoiseControlBoxView.this.getViewBinding().startDrawable.setVisibility(0);
                        NoiseControlBoxView.this.getViewBinding().endDrawable.setVisibility(0);
                        NoiseControlBoxView.this.getViewBinding().middleDrawable.setVisibility(size == 4 ? 0 : 4);
                        int measuredWidth = ((NoiseControlIconView) ((ArrayList) NoiseControlBoxView.this.iconViewList).get(0)).binding.icon.getMeasuredWidth();
                        int measuredWidth2 = ((NoiseControlIconView) ((ArrayList) NoiseControlBoxView.this.iconViewList).get(0)).binding.root.getMeasuredWidth();
                        int i9 = measuredWidth2 - measuredWidth;
                        int width = NoiseControlBoxView.this.getViewBinding().effectView.getWidth();
                        int i10 = size;
                        int i11 = ((width - (measuredWidth * i10)) - i9) / (i10 - 1);
                        int i12 = measuredWidth2 - (i9 / 2);
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) NoiseControlBoxView.this.getViewBinding().startDrawable.getLayoutParams();
                        ImageView imageView = NoiseControlBoxView.this.getViewBinding().startDrawable;
                        layoutParams.width = i11;
                        layoutParams.setMarginStart(i12);
                        imageView.setLayoutParams(layoutParams);
                        NoiseControlBoxView.this.getViewBinding().middleDrawable.getLayoutParams().width = i11;
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) NoiseControlBoxView.this.getViewBinding().endDrawable.getLayoutParams();
                        ImageView imageView2 = NoiseControlBoxView.this.getViewBinding().endDrawable;
                        layoutParams2.width = i11;
                        layoutParams2.setMarginEnd(i12);
                        imageView2.setLayoutParams(layoutParams2);
                        NoiseControlBoxView.this.getViewBinding().startDrawable.requestLayout();
                        NoiseControlBoxView.this.getViewBinding().middleDrawable.requestLayout();
                        NoiseControlBoxView.this.getViewBinding().endDrawable.requestLayout();
                    }
                });
                return;
            }
            getViewBinding().startDrawable.setVisibility(0);
            getViewBinding().endDrawable.setVisibility(0);
            getViewBinding().middleDrawable.setVisibility(size == 4 ? 0 : 4);
            int measuredWidth = ((NoiseControlIconView) ((ArrayList) this.iconViewList).get(0)).binding.icon.getMeasuredWidth();
            int measuredWidth2 = ((NoiseControlIconView) ((ArrayList) this.iconViewList).get(0)).binding.root.getMeasuredWidth();
            int i = measuredWidth2 - measuredWidth;
            int width = ((getViewBinding().effectView.getWidth() - (measuredWidth * size)) - i) / (size - 1);
            int i2 = measuredWidth2 - (i / 2);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewBinding().startDrawable.getLayoutParams();
            ImageView imageView = getViewBinding().startDrawable;
            layoutParams.width = width;
            layoutParams.setMarginStart(i2);
            imageView.setLayoutParams(layoutParams);
            getViewBinding().middleDrawable.getLayoutParams().width = width;
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getViewBinding().endDrawable.getLayoutParams();
            ImageView imageView2 = getViewBinding().endDrawable;
            layoutParams2.width = width;
            layoutParams2.setMarginEnd(i2);
            imageView2.setLayoutParams(layoutParams2);
            getViewBinding().startDrawable.requestLayout();
            getViewBinding().middleDrawable.requestLayout();
            getViewBinding().endDrawable.requestLayout();
        }
    }

    public NoiseControlBoxView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.iconViewList = new ArrayList();
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    }

    public NoiseControlBoxView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.registry = lifecycleRegistry;
        this.iconViewList = new ArrayList();
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    }
}
