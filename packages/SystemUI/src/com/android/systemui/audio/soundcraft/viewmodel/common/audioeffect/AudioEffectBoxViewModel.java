package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.SoundCraftCoverController;
import com.android.systemui.audio.soundcraft.feature.SoundCraftFeatures;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.setting.BudsSettingIntentFactory;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.phone.PhoneEffectModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.qs.bar.ColoredBGHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AudioEffectBoxViewModel extends BaseViewModel {
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public final MutableLiveData fallbackMessage;
    public final BudsSettingIntentFactory intentFactory;
    public final MutableLiveData isCoverScreen;
    public final MutableLiveData isDetailJumpButtonVisible;
    public final MutableLiveData isDolbyEnable;
    public final MutableLiveData isDolbyVisible;
    public final MutableLiveData isEqualizerVisible;
    public final MutableLiveData isFallbackTextVisible;
    public final MutableLiveData isHeadTrackingVisible;
    public final MutableLiveData isHeaderVisible;
    public final MutableLiveData isShowBoxBg;
    public final MutableLiveData isSpatialAudioVisible;
    public final MutableLiveData isVoiceBoostEnable;
    public final MutableLiveData isVoiceBoostVisible;
    public final MutableLiveData isVolumeNormalizationVisible;
    public final ModelProvider modelProvider;
    public final SoundCraftSettings settings;
    public final SoundCraftCoverController soundCraftCoverController;

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

    public AudioEffectBoxViewModel(Context context, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings, BudsSettingIntentFactory budsSettingIntentFactory, ColoredBGHelper coloredBGHelper, SoundCraftCoverController soundCraftCoverController) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
        this.intentFactory = budsSettingIntentFactory;
        this.coloredBGHelper = coloredBGHelper;
        this.soundCraftCoverController = soundCraftCoverController;
        Boolean bool = Boolean.FALSE;
        this.isHeaderVisible = new MutableLiveData(bool);
        this.isDolbyVisible = new MutableLiveData(bool);
        this.isSpatialAudioVisible = new MutableLiveData(bool);
        this.isHeadTrackingVisible = new MutableLiveData(bool);
        this.isEqualizerVisible = new MutableLiveData(bool);
        this.isVoiceBoostVisible = new MutableLiveData(bool);
        this.isVolumeNormalizationVisible = new MutableLiveData(bool);
        this.isFallbackTextVisible = new MutableLiveData(bool);
        this.fallbackMessage = new MutableLiveData("");
        this.isShowBoxBg = new MutableLiveData(bool);
        this.isDetailJumpButtonVisible = new MutableLiveData(bool);
        this.isDolbyEnable = new MutableLiveData(bool);
        this.isVoiceBoostEnable = new MutableLiveData(bool);
        this.isCoverScreen = new MutableLiveData(bool);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void notifyChange() {
        boolean z;
        Boolean boolValueOf;
        int i;
        ModelProvider modelProvider = this.modelProvider;
        EffectModel effectModel = modelProvider.effectModel;
        boolean z2 = false;
        boolean z3 = this.settings.isAppSettingEnabled && modelProvider.appSettingModel.readyToUpdateRoutine;
        boolean z4 = modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS;
        if (z4) {
            if (modelProvider.budsModel.getConnectionState() != null ? !r6.booleanValue() : true) {
                z = true;
            }
        } else {
            z = false;
        }
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isBudsManagerNotAvailable=", ", readyToUpdateRoutine=", "SoundCraft.AudioEffectBoxViewModel", z, z3);
        this.isHeaderVisible.setValue(Boolean.valueOf(z3));
        MutableLiveData mutableLiveData = this.isDolbyVisible;
        SoundCraftFeatures.INSTANCE.getClass();
        boolean z5 = SoundCraftFeatures.supportDolby;
        mutableLiveData.setValue(Boolean.valueOf(z5 && effectModel.dolbyList != null));
        this.isSpatialAudioVisible.setValue(Boolean.valueOf(effectModel.spatialAudio != null));
        this.isHeadTrackingVisible.setValue(Boolean.valueOf(modelProvider.budsModel.getHeadTracking() != null));
        this.isEqualizerVisible.setValue(Boolean.valueOf(SoundCraftFeatures.supportEQ));
        this.isVoiceBoostVisible.setValue(Boolean.valueOf(SoundCraftFeatures.supportVoiceBoost && effectModel.voiceBoost != null));
        this.isVolumeNormalizationVisible.setValue(Boolean.valueOf(z5 && effectModel.volumeNormalization != null));
        this.isFallbackTextVisible.setValue(Boolean.valueOf(z));
        this.isCoverScreen.setValue(Boolean.valueOf(modelProvider.isFromCover));
        this.isShowBoxBg.setValue(Boolean.valueOf(z4));
        this.isDetailJumpButtonVisible.setValue(Boolean.valueOf((!z4 || z || modelProvider.isFromCover) ? false : true));
        this.fallbackMessage.setValue(z ? this.context.getString(R.string.soundcraft_buds_manager_not_available) : "");
        MutableLiveData mutableLiveData2 = this.isVoiceBoostEnable;
        if (z4) {
            boolValueOf = Boolean.valueOf(modelProvider.budsModel.getUhq() != null ? !r2.booleanValue() : true);
        } else {
            boolValueOf = Boolean.valueOf(!modelProvider.phoneModel.uhqUpscaler);
        }
        mutableLiveData2.setValue(boolValueOf);
        MutableLiveData mutableLiveData3 = this.isDolbyEnable;
        if (!modelProvider.phoneModel.spatialAudio && (SoundCraftFeatures.dolbyEnabled || ((i = modelProvider.volumeModel.device) != 2 && i != 0))) {
            z2 = true;
        }
        mutableLiveData3.setValue(Boolean.valueOf(z2));
        Log.d("SoundCraft.AudioEffectBoxViewModel", "notifyChange=" + this);
    }

    public final String toString() {
        ModelProvider modelProvider = this.modelProvider;
        EffectOutDeviceType effectOutDeviceType = modelProvider.effectOutDeviceType;
        Boolean connectionState = modelProvider.budsModel.getConnectionState();
        PhoneEffectModel phoneEffectModel = modelProvider.phoneModel;
        Object value = this.isDolbyVisible.getValue();
        Object value2 = this.isSpatialAudioVisible.getValue();
        Object value3 = this.isHeadTrackingVisible.getValue();
        Object value4 = this.isEqualizerVisible.getValue();
        Object value5 = this.isFallbackTextVisible.getValue();
        Object value6 = this.isVoiceBoostVisible.getValue();
        SoundCraftFeatures.INSTANCE.getClass();
        return "[effectOutDeviceType=" + effectOutDeviceType + ", budsConnectionState=" + connectionState + ", phoneModel=" + phoneEffectModel + ", isDolbyVisible=" + value + ", isSpatialAudioVisible=" + value2 + ", isHeadTrackingVisible=" + value3 + ", isEqualizerVisible=" + value4 + ", isFallbackTextVisible=" + value5 + ", isVoiceBoostVisible=" + value6 + "(feature=" + SoundCraftFeatures.supportVoiceBoost + "), isVolumeNormalizationVisible=" + this.isVolumeNormalizationVisible.getValue() + ", isVoiceBoostEnable=" + this.isVoiceBoostEnable.getValue() + "]";
    }
}
