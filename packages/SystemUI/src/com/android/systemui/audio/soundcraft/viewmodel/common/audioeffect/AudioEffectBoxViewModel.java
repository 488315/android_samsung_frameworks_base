package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.SoundCraftCoverController;
import com.android.systemui.audio.soundcraft.feature.SoundCraftFeatures;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.setting.BudsSettingIntentFactory;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.phone.PhoneEffectModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import com.android.systemui.qs.bar.ColoredBGHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0067  */
    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyChange() {
        /*
            Method dump skipped, instructions count: 331
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
        Object value3 = this.isHeadTrackingVisible.getValue();
        Object value4 = this.isEqualizerVisible.getValue();
        Object value5 = this.isFallbackTextVisible.getValue();
        Object value6 = this.isVoiceBoostVisible.getValue();
        SoundCraftFeatures.INSTANCE.getClass();
        return "[effectOutDeviceType=" + effectOutDeviceType + ", budsConnectionState=" + connectionState + ", phoneModel=" + phoneEffectModel + ", isDolbyVisible=" + value + ", isSpatialAudioVisible=" + value2 + ", isHeadTrackingVisible=" + value3 + ", isEqualizerVisible=" + value4 + ", isFallbackTextVisible=" + value5 + ", isVoiceBoostVisible=" + value6 + "(feature=" + SoundCraftFeatures.supportVoiceBoost + "), isVolumeNormalizationVisible=" + this.isVolumeNormalizationVisible.getValue() + ", isVoiceBoostEnable=" + this.isVoiceBoostEnable.getValue() + "]";
    }
}
