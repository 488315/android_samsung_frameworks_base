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
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class VoiceBoostViewModel extends BaseToggleViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundAliveManager soundAliveManager;
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

    public VoiceBoostViewModel(Context context, ModelProvider modelProvider, SoundAliveManager soundAliveManager, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.soundAliveManager = soundAliveManager;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue(this.context.getString(R.string.soundcraft_voice_boost));
        MutableLiveData mutableLiveData = this.isSelected;
        Boolean bool = this.modelProvider.effectModel.voiceBoost;
        if (bool == null) {
            bool = Boolean.FALSE;
        }
        mutableLiveData.setValue(bool);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        Unit unit;
        ModelProvider modelProvider = this.modelProvider;
        Boolean bool = modelProvider.effectModel.voiceBoost;
        if (bool != null) {
            boolean z = !bool.booleanValue();
            modelProvider.effectModel.voiceBoost = Boolean.valueOf(z);
            this.isSelected.setValue(Boolean.valueOf(z));
            AppSettingModel appSettingModel = modelProvider.appSettingModel;
            if (appSettingModel.readyToUpdateRoutine) {
                String str = appSettingModel.playingAudioPackageName;
                if (str != null) {
                    RoutineManager routineManager = this.routineManager;
                    String routineId = routineManager.getRoutineId(str);
                    if (routineId != null) {
                        routineManager.updateRoutine(str, routineId, modelProvider.effectModel);
                        unit = Unit.INSTANCE;
                    } else {
                        unit = null;
                    }
                    if (unit == null) {
                        routineManager.createRoutine(modelProvider.effectModel, str);
                    }
                }
            } else if (modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE) {
                SoundAliveManager soundAliveManager = this.soundAliveManager;
                soundAliveManager.getClass();
                soundAliveManager.setState(z ? 1 : 0, SoundAliveEffectEnum.VOICE_BOOST_EFFECT.getSettingName());
            } else {
                modelProvider.budsModel.setVoiceBoost(Boolean.valueOf(z));
                this.wearableManager.updateBudsModel(modelProvider.budsModel);
            }
        }
        notifyChange();
    }
}
