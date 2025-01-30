package com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.qs.bar.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.qs.bar.soundcraft.model.BudsInfo;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectBoxViewModel extends BaseViewModel {
    public final ModelProvider modelProvider;
    public final MutableLiveData showEqualizer;
    public final MutableLiveData showSpatialAudio;
    public final MutableLiveData showVoiceBoost;
    public final MutableLiveData showVolumeNormalization;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public AudioEffectBoxViewModel(Context context, ModelProvider modelProvider, AudioPlaybackManager audioPlaybackManager) {
        this.modelProvider = modelProvider;
        Boolean bool = Boolean.FALSE;
        this.showSpatialAudio = new MutableLiveData(bool);
        this.showEqualizer = new MutableLiveData(bool);
        this.showVoiceBoost = new MutableLiveData(bool);
        this.showVolumeNormalization = new MutableLiveData(bool);
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        ModelProvider modelProvider = this.modelProvider;
        Log.d("SoundCraft.AudioEffectBoxViewModel", "notifyChange : budsInfo=" + modelProvider.budsInfo);
        BudsInfo budsInfo = modelProvider.budsInfo;
        this.showSpatialAudio.setValue(Boolean.valueOf(budsInfo.getSpatialAudio() != null));
        this.showEqualizer.setValue(budsInfo.getEqualizerList() != null ? Boolean.valueOf(!r2.isEmpty()) : null);
        this.showVoiceBoost.setValue(Boolean.valueOf(budsInfo.getVoiceBoost() != null));
        this.showVolumeNormalization.setValue(Boolean.valueOf(budsInfo.getVolumeNormalization() != null));
    }

    public final String toString() {
        return "[showSpatialAudio=" + this.showSpatialAudio.getValue() + ", showEqualizer=" + this.showEqualizer.getValue() + ", showVoiceBoost=" + this.showVoiceBoost.getValue() + ", showVolumeNormalization=" + this.showVolumeNormalization.getValue() + "]";
    }
}
