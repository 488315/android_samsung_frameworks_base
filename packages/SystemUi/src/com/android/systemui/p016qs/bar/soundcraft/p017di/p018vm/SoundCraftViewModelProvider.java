package com.android.systemui.p016qs.bar.soundcraft.p017di.p018vm;

import com.android.systemui.p016qs.bar.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.EqualizerViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.SpatialAudioViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.VoiceBoostViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect.VolumeNormalizationViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.AdaptiveViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol.NoiseControlOffViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.routine.RoutineTestViewModel;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.wearable.WearableLinkBoxViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftViewModelProvider {
    public final SoundCraftActionBarViewModel actionBarViewModel;
    public final ActiveNoiseCancelingViewModel activeNoiseCancelingViewModel;
    public final AdaptiveViewModel adaptiveViewModel;
    public final AmbientSoundViewModel ambientSoundViewModel;
    public final AudioEffectBoxViewModel audioEffectBoxViewModel;
    public final AudioEffectHeaderViewModel audioEffectHeaderViewModel;
    public final SoundCraftViewModel craftViewModel;
    public final EqualizerViewModel equalizerViewModel;
    public final NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel;
    public final NoiseCancelingLevelViewModel noiseCancelingViewModel;
    public final NoiseControlBoxViewModel noiseControlBoxViewModel;
    public final NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel;
    public final NoiseControlOffViewModel noiseControlOffViewModel;
    public final RoutineTestViewModel routineTestViewModel;
    public final SpatialAudioViewModel spatialAudioViewModel;
    public final VoiceBoostViewModel voiceBoostViewModel;
    public final VolumeNormalizationViewModel volumeNormalizationViewModel;
    public final WearableLinkBoxViewModel wearableLinkBoxViewModel;

    public SoundCraftViewModelProvider(SoundCraftViewModel soundCraftViewModel, SoundCraftActionBarViewModel soundCraftActionBarViewModel, NoiseControlBoxViewModel noiseControlBoxViewModel, AudioEffectBoxViewModel audioEffectBoxViewModel, WearableLinkBoxViewModel wearableLinkBoxViewModel, AudioEffectHeaderViewModel audioEffectHeaderViewModel, SpatialAudioViewModel spatialAudioViewModel, EqualizerViewModel equalizerViewModel, VoiceBoostViewModel voiceBoostViewModel, VolumeNormalizationViewModel volumeNormalizationViewModel, ActiveNoiseCancelingViewModel activeNoiseCancelingViewModel, AdaptiveViewModel adaptiveViewModel, AmbientSoundViewModel ambientSoundViewModel, NoiseCancelingLevelViewModel noiseCancelingLevelViewModel, NoiseControlEffectBoxViewModel noiseControlEffectBoxViewModel, NoiseControlOffViewModel noiseControlOffViewModel, NoiseCancelingSwitchBarViewModel noiseCancelingSwitchBarViewModel, RoutineTestViewModel routineTestViewModel) {
        this.craftViewModel = soundCraftViewModel;
        this.actionBarViewModel = soundCraftActionBarViewModel;
        this.noiseControlBoxViewModel = noiseControlBoxViewModel;
        this.audioEffectBoxViewModel = audioEffectBoxViewModel;
        this.wearableLinkBoxViewModel = wearableLinkBoxViewModel;
        this.audioEffectHeaderViewModel = audioEffectHeaderViewModel;
        this.spatialAudioViewModel = spatialAudioViewModel;
        this.equalizerViewModel = equalizerViewModel;
        this.voiceBoostViewModel = voiceBoostViewModel;
        this.volumeNormalizationViewModel = volumeNormalizationViewModel;
        this.activeNoiseCancelingViewModel = activeNoiseCancelingViewModel;
        this.adaptiveViewModel = adaptiveViewModel;
        this.ambientSoundViewModel = ambientSoundViewModel;
        this.noiseCancelingViewModel = noiseCancelingLevelViewModel;
        this.noiseControlEffectBoxViewModel = noiseControlEffectBoxViewModel;
        this.noiseControlOffViewModel = noiseControlOffViewModel;
        this.noiseCancelingSwitchBarViewModel = noiseCancelingSwitchBarViewModel;
        this.routineTestViewModel = routineTestViewModel;
    }

    public final SoundCraftActionBarViewModel getActionBarViewModel() {
        return this.actionBarViewModel;
    }

    public final ActiveNoiseCancelingViewModel getActiveNoiseCancelingViewModel() {
        return this.activeNoiseCancelingViewModel;
    }

    public final AdaptiveViewModel getAdaptiveViewModel() {
        return this.adaptiveViewModel;
    }

    public final AmbientSoundViewModel getAmbientSoundViewModel() {
        return this.ambientSoundViewModel;
    }

    public final AudioEffectBoxViewModel getAudioEffectBoxViewModel() {
        return this.audioEffectBoxViewModel;
    }

    public final AudioEffectHeaderViewModel getAudioEffectHeaderViewModel() {
        return this.audioEffectHeaderViewModel;
    }

    public final SoundCraftViewModel getCraftViewModel() {
        return this.craftViewModel;
    }

    public final EqualizerViewModel getEqualizerViewModel() {
        return this.equalizerViewModel;
    }

    public final NoiseCancelingSwitchBarViewModel getNoiseCancelingSwitchBarViewModel() {
        return this.noiseCancelingSwitchBarViewModel;
    }

    public final NoiseCancelingLevelViewModel getNoiseCancelingViewModel() {
        return this.noiseCancelingViewModel;
    }

    public final NoiseControlBoxViewModel getNoiseControlBoxViewModel() {
        return this.noiseControlBoxViewModel;
    }

    public final NoiseControlEffectBoxViewModel getNoiseControlEffectBoxViewModel() {
        return this.noiseControlEffectBoxViewModel;
    }

    public final NoiseControlOffViewModel getNoiseControlOffViewModel() {
        return this.noiseControlOffViewModel;
    }

    public final RoutineTestViewModel getRoutineTestViewModel() {
        return this.routineTestViewModel;
    }

    public final SpatialAudioViewModel getSpatialAudioViewModel() {
        return this.spatialAudioViewModel;
    }

    public final VoiceBoostViewModel getVoiceBoostViewModel() {
        return this.voiceBoostViewModel;
    }

    public final VolumeNormalizationViewModel getVolumeNormalizationViewModel() {
        return this.volumeNormalizationViewModel;
    }

    public final WearableLinkBoxViewModel getWearableLinkBoxViewModel() {
        return this.wearableLinkBoxViewModel;
    }
}
