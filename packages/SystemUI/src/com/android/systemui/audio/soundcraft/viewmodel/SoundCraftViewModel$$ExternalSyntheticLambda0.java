package com.android.systemui.audio.soundcraft.viewmodel;

import android.content.Context;
import android.util.Log;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.buds.BatteryInfo;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.android.systemui.audio.soundcraft.model.buds.NoiseControl;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final /* synthetic */ class SoundCraftViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SoundCraftViewModel f$0;

    public /* synthetic */ SoundCraftViewModel$$ExternalSyntheticLambda0(SoundCraftViewModel soundCraftViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = soundCraftViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SoundCraftViewModel soundCraftViewModel = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Set set = (Set) obj;
                soundCraftViewModel.modelProvider.budsModel.setNoiseControlsList(set);
                Log.d("SoundCraft.SoundCraftViewModel", "noiseControl state changed newList=" + set);
                soundCraftViewModel.updateNoiseControlBox.setValue(Boolean.valueOf(soundCraftViewModel.hasNoiseControl()));
                break;
            case 1:
                BatteryInfo batteryInfo = (BatteryInfo) obj;
                soundCraftViewModel.modelProvider.batteryInfo.setBatteryLeft(batteryInfo.getBatteryLeft());
                ModelProvider modelProvider = soundCraftViewModel.modelProvider;
                modelProvider.batteryInfo.setBatteryRight(batteryInfo.getBatteryRight());
                modelProvider.batteryInfo.setBatteryCradle(batteryInfo.getBatteryCradle());
                soundCraftViewModel.updateBatteryInfoBox.setValue(Boolean.TRUE);
                break;
            default:
                BudsModel budsModel = (BudsModel) obj;
                int i = SoundCraftViewModel.$r8$clinit;
                Boolean connectionState = budsModel != null ? budsModel.getConnectionState() : null;
                ModelProvider modelProvider2 = soundCraftViewModel.modelProvider;
                if (connectionState != null && !Intrinsics.areEqual(budsModel.getConnectionState(), Boolean.FALSE) && budsModel.getEqualizerList() != null) {
                    Set noiseControlsList = modelProvider2.budsModel.getNoiseControlsList();
                    modelProvider2.budsModel = budsModel;
                    SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
                    Context context = soundCraftViewModel.context;
                    Boolean connectionState2 = budsModel.getConnectionState();
                    boolean zBooleanValue = connectionState2 != null ? connectionState2.booleanValue() : false;
                    soundCraftSettingConstants.getClass();
                    SoundCraftSettingConstants.isBudsPluginConnected(context, zBooleanValue);
                    if (budsModel.getNoiseControlsList() == null) {
                        modelProvider2.budsModel.setNoiseControlsList(noiseControlsList);
                    }
                    List equalizerList = budsModel.getEqualizerList();
                    if (equalizerList != null) {
                        modelProvider2.effectModel.equalizerList = equalizerList;
                    }
                    EffectModel appRoutineModel = soundCraftViewModel.getAppRoutineModel();
                    if (appRoutineModel != null) {
                        modelProvider2.effectModel = appRoutineModel;
                    } else {
                        List equalizerList2 = modelProvider2.budsModel.getEqualizerList();
                        equalizerList2.getClass();
                        modelProvider2.effectModel = new EffectModel(null, null, equalizerList2, modelProvider2.budsModel.getSpatialAudio(), modelProvider2.budsModel.getHeadTracking(), modelProvider2.budsModel.getVoiceBoost(), modelProvider2.budsModel.getVolumeNormalization(), 3, null);
                    }
                    Set noiseControlsList2 = modelProvider2.budsModel.getNoiseControlsList();
                    if (noiseControlsList2 != null) {
                        Boolean wearingL = modelProvider2.budsModel.getWearingL();
                        noiseControlsList2.add(new NoiseControl("wearing_l", wearingL != null ? wearingL.booleanValue() : false));
                    }
                    Set noiseControlsList3 = modelProvider2.budsModel.getNoiseControlsList();
                    if (noiseControlsList3 != null) {
                        Boolean wearingR = modelProvider2.budsModel.getWearingR();
                        noiseControlsList3.add(new NoiseControl("wearing_r", wearingR != null ? wearingR.booleanValue() : false));
                    }
                    soundCraftViewModel.notifyChange();
                    break;
                } else {
                    modelProvider2.budsModel.setConnectionState();
                    SoundCraftSettingConstants soundCraftSettingConstants2 = SoundCraftSettingConstants.INSTANCE;
                    Context context2 = soundCraftViewModel.context;
                    soundCraftSettingConstants2.getClass();
                    SoundCraftSettingConstants.isBudsPluginConnected(context2, false);
                    soundCraftViewModel.notifyChange();
                    break;
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
