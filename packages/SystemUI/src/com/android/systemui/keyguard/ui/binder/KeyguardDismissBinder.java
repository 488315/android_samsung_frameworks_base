package com.android.systemui.keyguard.ui.binder;

import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardDismissBinder implements CoreStartable {
    public final KeyguardDismissInteractor interactor;
    public final KeyguardLogger keyguardLogger;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ViewMediatorCallback viewMediatorCallback;

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

    public KeyguardDismissBinder(KeyguardDismissInteractor keyguardDismissInteractor, SelectedUserInteractor selectedUserInteractor, ViewMediatorCallback viewMediatorCallback, CoroutineScope coroutineScope, KeyguardLogger keyguardLogger, FeatureFlagsClassic featureFlagsClassic) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
    }
}
