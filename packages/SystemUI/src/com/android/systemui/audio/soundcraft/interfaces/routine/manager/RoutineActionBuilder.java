package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType;
import com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionUtil;
import com.android.systemui.audio.soundcraft.interfaces.routine.extension.ActionParamCreator;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEqEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveToggleEnum;
import com.android.systemui.audio.soundcraft.interfaces.wearable.SpatialAudioItem;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.common.Equalizer;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RoutineActionBuilder {
    public static final RoutineActionBuilder INSTANCE = new RoutineActionBuilder();

    private RoutineActionBuilder() {
    }

    public static HashMap buildBudsActions(EffectModel effectModel, String str) {
        HashMap hashMap = new HashMap();
        Boolean bool = effectModel.spatialAudio;
        if (bool != null) {
            ActionParamCreator actionParamCreator = ActionParamCreator.INSTANCE;
            BudsActionType budsActionType = BudsActionType.SPATIAL_AUDIO;
            BudsActionUtil budsActionUtil = BudsActionUtil.INSTANCE;
            boolean booleanValue = bool.booleanValue();
            Boolean bool2 = effectModel.headTracking;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : false;
            budsActionUtil.getClass();
            String valueOf = String.valueOf(((booleanValue || booleanValue2) ? (!booleanValue || booleanValue2) ? (booleanValue && booleanValue2) ? SpatialAudioItem.SPATIAL_AND_HEAD_TRACKING : SpatialAudioItem.OFF : SpatialAudioItem.SPATIAL_ONLY : SpatialAudioItem.OFF).getPosition());
            actionParamCreator.getClass();
            ActionParamCreator.putActionValue(hashMap, str, budsActionType, valueOf);
        }
        for (Equalizer equalizer : effectModel.equalizerList) {
            if (equalizer.getState()) {
                ActionParamCreator actionParamCreator2 = ActionParamCreator.INSTANCE;
                BudsActionType budsActionType2 = BudsActionType.EQUALIZER;
                String valueOf2 = String.valueOf(effectModel.equalizerList.indexOf(equalizer));
                actionParamCreator2.getClass();
                ActionParamCreator.putActionValue(hashMap, str, budsActionType2, valueOf2);
                Boolean bool3 = effectModel.voiceBoost;
                if (bool3 != null) {
                    ActionParamCreator.putActionValue(hashMap, str, BudsActionType.VOICE_BOOST, String.valueOf(bool3.booleanValue()));
                }
                Boolean bool4 = effectModel.volumeNormalization;
                if (bool4 != null) {
                    ActionParamCreator.putActionValue(hashMap, str, BudsActionType.VOLUME_NORMALIZATION, String.valueOf(bool4.booleanValue()));
                }
                return hashMap;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static HashMap buildPhoneActions(EffectModel effectModel) {
        SoundAliveToggleEnum soundAliveToggleEnum;
        SoundAliveToggleEnum soundAliveToggleEnum2;
        HashMap hashMap = new HashMap();
        List list = effectModel.dolbyList;
        int i = -1;
        int i2 = 0;
        if (list != null) {
            Iterator it = list.iterator();
            int i3 = 0;
            while (true) {
                if (!it.hasNext()) {
                    i3 = -1;
                    break;
                }
                if (((Dolby) it.next()).state) {
                    break;
                }
                i3++;
            }
            ActionParamCreator actionParamCreator = ActionParamCreator.INSTANCE;
            PhoneActionType phoneActionType = PhoneActionType.DOLBY;
            String routineActionValue = ((DolbyEnum) DolbyEnum.$ENTRIES.get(i3)).getRoutineActionValue();
            actionParamCreator.getClass();
            ActionParamCreator.putActionValue(hashMap, phoneActionType, routineActionValue);
        }
        Iterator it2 = effectModel.equalizerList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            if (((Equalizer) it2.next()).getState()) {
                i = i2;
                break;
            }
            i2++;
        }
        ActionParamCreator actionParamCreator2 = ActionParamCreator.INSTANCE;
        PhoneActionType phoneActionType2 = PhoneActionType.EQUALIZER;
        String routineActionValue2 = ((SoundAliveEqEnum) SoundAliveEqEnum.$ENTRIES.get(i)).getRoutineActionValue();
        actionParamCreator2.getClass();
        ActionParamCreator.putActionValue(hashMap, phoneActionType2, routineActionValue2);
        Boolean bool = effectModel.voiceBoost;
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            PhoneActionType phoneActionType3 = PhoneActionType.VOICE_BOOST;
            SoundAliveToggleEnum.Companion.getClass();
            if (booleanValue) {
                soundAliveToggleEnum2 = SoundAliveToggleEnum.ON;
            } else {
                if (booleanValue) {
                    throw new NoWhenBranchMatchedException();
                }
                soundAliveToggleEnum2 = SoundAliveToggleEnum.OFF;
            }
            ActionParamCreator.putActionValue(hashMap, phoneActionType3, soundAliveToggleEnum2.getRoutineActionValue());
        }
        Boolean bool2 = effectModel.volumeNormalization;
        if (bool2 != null) {
            boolean booleanValue2 = bool2.booleanValue();
            PhoneActionType phoneActionType4 = PhoneActionType.VOLUME_NORMALIZATION;
            SoundAliveToggleEnum.Companion.getClass();
            if (booleanValue2) {
                soundAliveToggleEnum = SoundAliveToggleEnum.ON;
            } else {
                if (booleanValue2) {
                    throw new NoWhenBranchMatchedException();
                }
                soundAliveToggleEnum = SoundAliveToggleEnum.OFF;
            }
            ActionParamCreator.putActionValue(hashMap, phoneActionType4, soundAliveToggleEnum.getRoutineActionValue());
        }
        return hashMap;
    }
}
