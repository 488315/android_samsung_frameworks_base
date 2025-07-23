package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEffectEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VolumeNormalizationViewModel extends BaseToggleViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundAliveManager soundAliveManager;
    public final WearableManager wearableManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public VolumeNormalizationViewModel(Context context, ModelProvider modelProvider, SoundAliveManager soundAliveManager, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.soundAliveManager = soundAliveManager;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue(this.context.getString(R.string.soundcraft_volume_normalization));
        MutableLiveData mutableLiveData = this.isSelected;
        Boolean bool = this.modelProvider.effectModel.volumeNormalization;
        if (bool == null) {
            bool = Boolean.FALSE;
        }
        mutableLiveData.setValue(bool);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        ModelProvider modelProvider = this.modelProvider;
        Boolean bool = modelProvider.effectModel.volumeNormalization;
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            boolean z = !booleanValue;
            modelProvider.effectModel.volumeNormalization = Boolean.valueOf(z);
            this.isSelected.setValue(Boolean.valueOf(z));
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
            } else if (modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE) {
                SoundAliveManager soundAliveManager = this.soundAliveManager;
                soundAliveManager.getClass();
                soundAliveManager.setState(z ? 1 : 0, SoundAliveEffectEnum.VOLUME_NORMALIZATION_EFFECT.getSettingName());
            } else {
                modelProvider.budsModel.setVolumeNormalization(Boolean.valueOf(z));
                this.wearableManager.updateBudsModel(modelProvider.budsModel);
            }
            SoundCraftSALogging soundCraftSALogging = SoundCraftSALogging.INSTANCE;
            SoundCraftSALogging.ScreenId screenId = modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE ? SoundCraftSALogging.ScreenId.EID_PHONE_DETAIL_SETTING : SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING;
            SoundCraftSALogging.Event event = SoundCraftSALogging.Event.LOUDNESS_NORMALIZATION;
            soundCraftSALogging.getClass();
            SoundCraftSALogging.sendEventLog$default(soundCraftSALogging, screenId, event, !booleanValue ? SoundCraftSALogging.ON : SoundCraftSALogging.OFF, 8);
        }
        notifyChange();
    }
}
