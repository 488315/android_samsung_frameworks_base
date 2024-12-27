package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AmbientVolumeViewModel extends NoiseControlLevelViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final WearableManager wearableManager;

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

    public AmbientVolumeViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlLevelViewModel
    public final String getTitle() {
        return this.context.getString(R.string.sound_craft_ambient_sound_volume);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        MutableLiveData mutableLiveData = this.level;
        ModelProvider modelProvider = this.modelProvider;
        int ambientSoundVolume = modelProvider.budsModel.getAmbientSoundVolume();
        if (ambientSoundVolume == null) {
            ambientSoundVolume = 1;
        }
        mutableLiveData.setValue(ambientSoundVolume);
        MutableLiveData mutableLiveData2 = this.levelMax;
        int maxAmbientSoundVolume = modelProvider.budsModel.getMaxAmbientSoundVolume();
        if (maxAmbientSoundVolume == null) {
            maxAmbientSoundVolume = 4;
        }
        mutableLiveData2.setValue(maxAmbientSoundVolume);
        Log.d("SoundCraft.AmbientVolumeViewModel", "notifyChange ambient level: " + mutableLiveData.getValue() + ", max: " + mutableLiveData2.getValue());
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol.NoiseControlLevelViewModel
    public final void progressChange(int i) {
        ModelProvider modelProvider = this.modelProvider;
        if (modelProvider.budsModel.getAmbientSoundVolume() != null) {
            Integer ambientSoundVolume = modelProvider.budsModel.getAmbientSoundVolume();
            if (ambientSoundVolume != null && ambientSoundVolume.intValue() == i) {
                return;
            }
            modelProvider.budsModel.setAmbientSoundVolume(Integer.valueOf(i));
            this.wearableManager.updateBudsModel(modelProvider.budsModel);
            this.level.setValue(Integer.valueOf(i));
        }
    }
}
