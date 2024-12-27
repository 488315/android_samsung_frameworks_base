package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class NoiseControlLevelViewModel extends BaseViewModel {
    public final MutableLiveData levelMax = new MutableLiveData();
    public final MutableLiveData level = new MutableLiveData();

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

    public abstract String getTitle();

    public abstract void progressChange(int i);
}
