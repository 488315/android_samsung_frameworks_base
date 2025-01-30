package com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeNormalizationViewModel extends BaseToggleViewModel {
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;

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

    public VolumeNormalizationViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager, RoutineManager routineManager) {
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final MutableLiveData getIcon() {
        return new MutableLiveData(Integer.valueOf(R.drawable.soundcraft_ic_volume_normalization));
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue("Volume normalization");
        MutableLiveData mutableLiveData = this.isSelected;
        Boolean volumeNormalization = this.modelProvider.budsInfo.getVolumeNormalization();
        if (volumeNormalization == null) {
            volumeNormalization = Boolean.FALSE;
        }
        mutableLiveData.setValue(volumeNormalization);
        Log.d("SoundCraft.VolumeNormalizationViewModel", "notifyChange : isSelected=" + mutableLiveData);
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final void onClick() {
        ModelProvider modelProvider = this.modelProvider;
        Boolean volumeNormalization = modelProvider.budsInfo.getVolumeNormalization();
        if (volumeNormalization != null) {
            boolean booleanValue = volumeNormalization.booleanValue();
            modelProvider.budsInfo.setVolumeNormalization(Boolean.valueOf(!booleanValue));
            this.isSelected.setValue(Boolean.valueOf(booleanValue));
            String str = modelProvider.playingAudioPackageNameForAppSetting;
            Unit unit = null;
            if (str != null) {
                RoutineManager routineManager = this.routineManager;
                String routineId = routineManager.getRoutineId(str);
                if (routineId != null) {
                    routineManager.updateRoutine(str, routineId, modelProvider.budsInfo);
                    unit = Unit.INSTANCE;
                }
                if (unit == null) {
                    routineManager.createRoutine(str, modelProvider.budsInfo);
                }
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                this.wearableManager.updateBudsInfo(modelProvider.budsInfo);
            }
        }
        notifyChange();
    }
}
