package com.android.systemui.keyguard.ui.binder;

import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardDismissActionBinder implements CoreStartable {
    public final KeyguardDismissActionInteractor interactor;
    public final KeyguardLogger keyguardLogger;

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

    public KeyguardDismissActionBinder(KeyguardDismissActionInteractor keyguardDismissActionInteractor, CoroutineScope coroutineScope, KeyguardLogger keyguardLogger) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
    }
}
