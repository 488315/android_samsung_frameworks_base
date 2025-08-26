package com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect;

import android.content.Context;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SpatialAudioSwitchViewModel extends BaseToggleViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;

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

    public SpatialAudioSwitchViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue(this.context.getString(R.string.soundcraft_spatial_audio_title));
        EffectModel effectModel = this.modelProvider.effectModel;
        if (effectModel.spatialAudio == null) {
            effectModel = null;
        }
        if (effectModel != null) {
            Boolean bool = effectModel.spatialAudio;
            boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("spatialAudio=", "SoundCraft.SpatialAudioSwitchViewModel", zBooleanValue);
            this.isChecked.setValue(Boolean.valueOf(zBooleanValue));
        }
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        ModelProvider modelProvider = this.modelProvider;
        Boolean bool = modelProvider.effectModel.spatialAudio;
        if (bool != null) {
            boolean zBooleanValue = bool.booleanValue();
            this.isSelected.setValue(Boolean.valueOf(!zBooleanValue));
            if (zBooleanValue) {
                BudsModel budsModel = modelProvider.budsModel;
                Boolean bool2 = Boolean.FALSE;
                budsModel.setSpatialAudio(bool2);
                modelProvider.effectModel.spatialAudio = bool2;
                setHeadTracking$1();
            } else {
                BudsModel budsModel2 = modelProvider.budsModel;
                Boolean bool3 = Boolean.TRUE;
                budsModel2.setSpatialAudio(bool3);
                modelProvider.effectModel.spatialAudio = bool3;
                setHeadTracking$1();
            }
            AppSettingModel appSettingModel = modelProvider.appSettingModel;
            if (appSettingModel.readyToUpdateRoutine) {
                String str = appSettingModel.playingAudioPackageName;
                if (str != null) {
                    RoutineManager routineManager = this.routineManager;
                    String routineId = routineManager.getRoutineId(str);
                    if (routineId != null) {
                        routineManager.updateRoutine(str, routineId, modelProvider.effectModel);
                    } else {
                        routineManager.createRoutine(modelProvider.effectModel, str);
                    }
                }
            } else {
                this.wearableManager.updateBudsModel(modelProvider.budsModel);
            }
            SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, SoundCraftSALogging.Event.SPATIAL_AUDIO, !zBooleanValue ? "1" : "0", 8);
        }
        notifyChange();
    }

    public final void setHeadTracking$1() {
        ModelProvider modelProvider = this.modelProvider;
        if (modelProvider.budsModel.getHeadTracking() != null) {
            modelProvider.budsModel.setHeadTracking(Boolean.FALSE);
        }
        EffectModel effectModel = modelProvider.effectModel;
        if (effectModel.headTracking != null) {
            effectModel.headTracking = Boolean.FALSE;
        }
    }
}
