package com.android.systemui.audio.soundcraft.di.vm;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect.SpatialAudioViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.battery.BatteryInfoBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.ActiveNoiseCancelingViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AdaptiveViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientSoundViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.AmbientVolumeViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingLevelViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseCancelingSwitchBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlOffViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.actionbar.SoundCraftActionBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectBoxViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectHeaderViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.EqualizerViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.VoiceBoostViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.VolumeNormalizationViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.routine.RoutineTestViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.volume.VolumeBarViewModel;
import com.android.systemui.audio.soundcraft.viewmodel.phone.audioeffect.DolbyViewModel;
import dagger.Lazy;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundCraftViewModelFactory implements ViewModelProvider.Factory {
    public final Map creators;

    public SoundCraftViewModelFactory(Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, Lazy lazy8, Lazy lazy9, Lazy lazy10, Lazy lazy11, Lazy lazy12, Lazy lazy13, Lazy lazy14, Lazy lazy15, Lazy lazy16, Lazy lazy17, Lazy lazy18, Lazy lazy19, Lazy lazy20, Lazy lazy21) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(SoundCraftViewModel.class, lazy);
        linkedHashMap.put(SoundCraftActionBarViewModel.class, lazy2);
        linkedHashMap.put(NoiseControlBoxViewModel.class, lazy3);
        linkedHashMap.put(AudioEffectBoxViewModel.class, lazy4);
        linkedHashMap.put(AudioEffectHeaderViewModel.class, lazy6);
        linkedHashMap.put(DolbyViewModel.class, lazy7);
        linkedHashMap.put(SpatialAudioViewModel.class, lazy8);
        linkedHashMap.put(EqualizerViewModel.class, lazy9);
        linkedHashMap.put(VoiceBoostViewModel.class, lazy10);
        linkedHashMap.put(VolumeNormalizationViewModel.class, lazy11);
        linkedHashMap.put(ActiveNoiseCancelingViewModel.class, lazy12);
        linkedHashMap.put(AdaptiveViewModel.class, lazy13);
        linkedHashMap.put(AmbientSoundViewModel.class, lazy14);
        linkedHashMap.put(NoiseCancelingLevelViewModel.class, lazy15);
        linkedHashMap.put(NoiseControlEffectBoxViewModel.class, lazy16);
        linkedHashMap.put(NoiseControlOffViewModel.class, lazy17);
        linkedHashMap.put(NoiseCancelingSwitchBarViewModel.class, lazy18);
        linkedHashMap.put(BatteryInfoBoxViewModel.class, lazy20);
        linkedHashMap.put(VolumeBarViewModel.class, lazy5);
        linkedHashMap.put(AmbientVolumeViewModel.class, lazy19);
        linkedHashMap.put(RoutineTestViewModel.class, lazy21);
        this.creators = linkedHashMap;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        Object obj;
        Lazy lazy = (Lazy) ((LinkedHashMap) this.creators).get(cls);
        if (lazy == null) {
            Iterator it = ((LinkedHashMap) this.creators).entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (cls.isAssignableFrom((Class) ((Map.Entry) obj).getKey())) {
                    break;
                }
            }
            Map.Entry entry = (Map.Entry) obj;
            lazy = entry != null ? (Lazy) entry.getValue() : null;
            if (lazy == null) {
                throw new IllegalArgumentException("unknown model class " + cls);
            }
        }
        try {
            return (ViewModel) lazy.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
