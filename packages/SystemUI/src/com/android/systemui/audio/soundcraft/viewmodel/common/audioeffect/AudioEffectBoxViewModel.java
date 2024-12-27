package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.feature.SoundCraftFeatures;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.setting.BudsSettingIntentFactory;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.phone.PhoneEffectModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.qs.bar.ColoredBGHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class AudioEffectBoxViewModel extends BaseViewModel {
    public final ColoredBGHelper coloredBGHelper;
    public final Context context;
    public final MutableLiveData fallbackMessage;
    public final BudsSettingIntentFactory intentFactory;
    public final MutableLiveData isDetailJumpButtonVisible;
    public final MutableLiveData isDolbyEnable;
    public final MutableLiveData isDolbyVisible;
    public final MutableLiveData isEqualizerVisible;
    public final MutableLiveData isFallbackTextVisible;
    public final MutableLiveData isHeaderVisible;
    public final MutableLiveData isShowBoxBg;
    public final MutableLiveData isSpatialAudioVisible;
    public final MutableLiveData isVoiceBoostEnable;
    public final MutableLiveData isVoiceBoostVisible;
    public final MutableLiveData isVolumeNormalizationVisible;
    public final ModelProvider modelProvider;
    public final SoundCraftSettings settings;

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

    public AudioEffectBoxViewModel(Context context, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings, BudsSettingIntentFactory budsSettingIntentFactory, ColoredBGHelper coloredBGHelper) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
        this.intentFactory = budsSettingIntentFactory;
        this.coloredBGHelper = coloredBGHelper;
        Boolean bool = Boolean.FALSE;
        this.isHeaderVisible = new MutableLiveData(bool);
        this.isDolbyVisible = new MutableLiveData(bool);
        this.isSpatialAudioVisible = new MutableLiveData(bool);
        this.isEqualizerVisible = new MutableLiveData(bool);
        this.isVoiceBoostVisible = new MutableLiveData(bool);
        this.isVolumeNormalizationVisible = new MutableLiveData(bool);
        this.isFallbackTextVisible = new MutableLiveData(bool);
        this.fallbackMessage = new MutableLiveData("");
        this.isShowBoxBg = new MutableLiveData(bool);
        this.isDetailJumpButtonVisible = new MutableLiveData(bool);
        this.isDolbyEnable = new MutableLiveData(bool);
        this.isVoiceBoostEnable = new MutableLiveData(bool);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0067  */
    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyChange() {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect.AudioEffectBoxViewModel.notifyChange():void");
    }

    public final String toString() {
        ModelProvider modelProvider = this.modelProvider;
        EffectOutDeviceType effectOutDeviceType = modelProvider.effectOutDeviceType;
        Boolean connectionState = modelProvider.budsModel.getConnectionState();
        PhoneEffectModel phoneEffectModel = modelProvider.phoneModel;
        Object value = this.isDolbyVisible.getValue();
        Object value2 = this.isSpatialAudioVisible.getValue();
        Object value3 = this.isEqualizerVisible.getValue();
        Object value4 = this.isFallbackTextVisible.getValue();
        Object value5 = this.isVoiceBoostVisible.getValue();
        SoundCraftFeatures.INSTANCE.getClass();
        return "[effectOutDeviceType=" + effectOutDeviceType + ", budsConnectionState=" + connectionState + ", phoneModel=" + phoneEffectModel + ", isDolbyVisible=" + value + ", isSpatialAudioVisible=" + value2 + ", isEqualizerVisible=" + value3 + ", isFallbackTextVisible=" + value4 + ", isVoiceBoostVisible=" + value5 + "(feature=" + SoundCraftFeatures.supportVoiceBoost + "), isVolumeNormalizationVisible=" + this.isVolumeNormalizationVisible.getValue() + ", isVoiceBoostEnable=" + this.isVoiceBoostEnable.getValue() + "]";
    }
}
