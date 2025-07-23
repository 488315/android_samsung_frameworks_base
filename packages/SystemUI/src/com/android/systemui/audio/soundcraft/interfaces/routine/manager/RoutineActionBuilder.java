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
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
            Iterator it2 = effectModel.dolbyOldList.iterator();
            int i4 = 0;
            while (true) {
                if (!it2.hasNext()) {
                    i4 = -1;
                    break;
                }
                if (((Dolby) it2.next()).state) {
                    break;
                }
                i4++;
            }
            ActionParamCreator actionParamCreator = ActionParamCreator.INSTANCE;
            PhoneActionType phoneActionType = PhoneActionType.DOLBY;
            EnumEntries enumEntries = DolbyEnum.$ENTRIES;
            String routineActionValue = ((DolbyEnum) enumEntries.get(i3)).getRoutineActionValue();
            PhoneActionType phoneActionType2 = PhoneActionType.DOLBY_OLD;
            String routineActionValue2 = ((DolbyEnum) enumEntries.get(i4)).getRoutineActionValue();
            actionParamCreator.getClass();
            MetaInfo.Companion companion = MetaInfo.Companion;
            String actionTag = phoneActionType.getActionTag();
            companion.getClass();
            MetaInfo metaInfo = new MetaInfo("com.sec.android.app.soundalive", actionTag, null);
            ParameterValues.Companion.getClass();
            ParameterValues parameterValues = new ParameterValues();
            parameterValues.put(phoneActionType.getParamTag(), routineActionValue);
            parameterValues.put(phoneActionType2.getParamTag(), routineActionValue2);
            Unit unit = Unit.INSTANCE;
            hashMap.put(metaInfo, parameterValues);
        }
        Iterator it3 = effectModel.equalizerList.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            if (((Equalizer) it3.next()).getState()) {
                i = i2;
                break;
            }
            i2++;
        }
        ActionParamCreator actionParamCreator2 = ActionParamCreator.INSTANCE;
        PhoneActionType phoneActionType3 = PhoneActionType.EQUALIZER;
        String routineActionValue3 = ((SoundAliveEqEnum) SoundAliveEqEnum.$ENTRIES.get(i)).getRoutineActionValue();
        actionParamCreator2.getClass();
        ActionParamCreator.putActionValue(hashMap, phoneActionType3, routineActionValue3);
        Boolean bool = effectModel.voiceBoost;
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            PhoneActionType phoneActionType4 = PhoneActionType.VOICE_BOOST;
            SoundAliveToggleEnum.Companion.getClass();
            if (booleanValue) {
                soundAliveToggleEnum2 = SoundAliveToggleEnum.ON;
            } else {
                if (booleanValue) {
                    throw new NoWhenBranchMatchedException();
                }
                soundAliveToggleEnum2 = SoundAliveToggleEnum.OFF;
            }
            ActionParamCreator.putActionValue(hashMap, phoneActionType4, soundAliveToggleEnum2.getRoutineActionValue());
        }
        Boolean bool2 = effectModel.volumeNormalization;
        if (bool2 == null) {
            return hashMap;
        }
        boolean booleanValue2 = bool2.booleanValue();
        PhoneActionType phoneActionType5 = PhoneActionType.VOLUME_NORMALIZATION;
        SoundAliveToggleEnum.Companion.getClass();
        if (booleanValue2) {
            soundAliveToggleEnum = SoundAliveToggleEnum.ON;
        } else {
            if (booleanValue2) {
                throw new NoWhenBranchMatchedException();
            }
            soundAliveToggleEnum = SoundAliveToggleEnum.OFF;
        }
        ActionParamCreator.putActionValue(hashMap, phoneActionType5, soundAliveToggleEnum.getRoutineActionValue());
        return hashMap;
    }
}
