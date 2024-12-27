package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class NoiseControlLevelViewModel extends BaseViewModel {
    public final MutableLiveData levelMax = new MutableLiveData();
    public final MutableLiveData level = new MutableLiveData();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
