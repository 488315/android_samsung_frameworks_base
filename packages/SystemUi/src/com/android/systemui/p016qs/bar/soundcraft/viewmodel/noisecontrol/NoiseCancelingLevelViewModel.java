package com.android.systemui.p016qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseCancelingLevelViewModel extends BaseViewModel {
    public final MutableLiveData level = new MutableLiveData();
    public final ModelProvider modelProvider;
    public final WearableManager wearableManager;

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

    public NoiseCancelingLevelViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager) {
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        MutableLiveData mutableLiveData = this.level;
        int noiseCancelingLevel = this.modelProvider.budsInfo.getNoiseCancelingLevel();
        if (noiseCancelingLevel == null) {
            noiseCancelingLevel = 1;
        }
        mutableLiveData.setValue(noiseCancelingLevel);
        Log.d("SoundCraft.NoiseCancelingLevelViewModel", "notifyChange " + mutableLiveData.getValue());
    }
}
