package com.android.systemui.smartspace.ui.viewmodel;

import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SmartspaceViewModel {
    public final SmartspaceViewModel$special$$inlined$filter$1 isAwake;
    public final String surfaceName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
