package com.android.systemui.audio.soundcraft.viewmodel.common.volume;

import android.util.Log;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final /* synthetic */ class VolumeBarViewModel$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ VolumeBarViewModel f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        VolumeModel volumeModel = (VolumeModel) obj;
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        int i = VolumeBarViewModel.$r8$clinit;
        Log.d("SoundCraft.VolumeBarViewModel", "updateVolumeModel model: " + volumeModel);
        VolumeBarViewModel volumeBarViewModel = this.f$0;
        volumeBarViewModel.modelProvider.volumeModel = volumeModel;
        if (!zBooleanValue) {
            volumeBarViewModel.seekBarEnabled.postValue(Boolean.valueOf(volumeModel.enable));
            volumeBarViewModel.iconAnimationType.postValue(Boolean.valueOf(volumeBarViewModel.getSupportAnimatedIcon()));
            volumeBarViewModel.progress.postValue(Integer.valueOf(volumeModel.volume));
            volumeBarViewModel.progressMin.postValue(Integer.valueOf(volumeModel.minVolume));
            volumeBarViewModel.progressMax.postValue(Integer.valueOf(volumeModel.maxVolume));
            volumeBarViewModel.smartViewEnabled.postValue(Boolean.valueOf(volumeModel.isSmartViewEnabled));
        }
        return Unit.INSTANCE;
    }
}
