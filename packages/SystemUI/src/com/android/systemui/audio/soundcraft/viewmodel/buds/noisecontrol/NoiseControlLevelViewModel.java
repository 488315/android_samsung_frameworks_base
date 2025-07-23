package com.android.systemui.audio.soundcraft.viewmodel.buds.noisecontrol;

import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NoiseControlLevelViewModel extends BaseViewModel {
    public final MutableLiveData levelMax = new MutableLiveData();
    public final MutableLiveData level = new MutableLiveData();

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

    public abstract SoundCraftSALogging.Event getSALoggingEvent();

    public abstract String getTitle();

    public abstract void progressChange(int i);
}
