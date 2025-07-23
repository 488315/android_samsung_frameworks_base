package com.android.systemui.smartspace.ui.viewmodel;

import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SmartspaceViewModel {
    public final SmartspaceViewModel$special$$inlined$filter$1 isAwake;
    public final String surfaceName;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SmartspaceViewModel create(String str);
    }

    static {
        new Companion(null);
    }

    public SmartspaceViewModel(PowerInteractor powerInteractor, String str) {
        this.surfaceName = str;
        this.isAwake = new SmartspaceViewModel$special$$inlined$filter$1(powerInteractor.isAwake, this);
    }
}
