package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEffectEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VoiceBoostViewModel extends BaseToggleViewModel {
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

    public VoiceBoostViewModel(Context context, ModelProvider modelProvider, SoundAliveManager soundAliveManager, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.soundAliveManager = soundAliveManager;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void enable(boolean z) {
        String str;
        RoutineManager routineManager;
        String routineId;
        if (z) {
            return;
        }
        ModelProvider modelProvider = this.modelProvider;
        if (Intrinsics.areEqual(modelProvider.effectModel.voiceBoost, Boolean.TRUE)) {
            AppSettingModel appSettingModel = modelProvider.appSettingModel;
            if (!appSettingModel.readyToUpdateRoutine || (str = appSettingModel.playingAudioPackageName) == null || (routineId = (routineManager = this.routineManager).getRoutineId(str)) == null) {
                return;
            }
            Log.d("SoundCraft.VoiceBoostViewModel", "updateRoutine for voice boost disabled");
            EffectModel effectModel = modelProvider.effectModel;
            effectModel.voiceBoost = Boolean.FALSE;
            routineManager.updateRoutine(str, routineId, effectModel);
        }
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue(this.context.getString(R.string.soundcraft_voice_boost));
        MutableLiveData mutableLiveData = this.isChecked;
        ModelProvider modelProvider = this.modelProvider;
        boolean z = false;
        if (modelProvider.budsModel.getUhq() == null || Intrinsics.areEqual(modelProvider.budsModel.getUhq(), Boolean.FALSE)) {
            Boolean bool = modelProvider.effectModel.voiceBoost;
            if (bool != null ? bool.booleanValue() : false) {
                z = true;
            }
        }
        mutableLiveData.setValue(Boolean.valueOf(z));
        this.subText.setValue(this.context.getString(R.string.soundcraft_unavailable_while_uha));
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseToggleViewModel
    public final void onClick() {
        ModelProvider modelProvider = this.modelProvider;
        Boolean bool = modelProvider.effectModel.voiceBoost;
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            boolean z = !booleanValue;
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
                    } else {
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
            SoundCraftSALogging soundCraftSALogging = SoundCraftSALogging.INSTANCE;
            SoundCraftSALogging.ScreenId screenId = modelProvider.effectOutDeviceType == EffectOutDeviceType.PHONE ? SoundCraftSALogging.ScreenId.EID_PHONE_DETAIL_SETTING : SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING;
            SoundCraftSALogging.Event event = SoundCraftSALogging.Event.BOOST_DIALOGUE;
            soundCraftSALogging.getClass();
            SoundCraftSALogging.sendEventLog$default(soundCraftSALogging, screenId, event, !booleanValue ? SoundCraftSALogging.ON : SoundCraftSALogging.OFF, 8);
        }
        notifyChange();
    }
}
