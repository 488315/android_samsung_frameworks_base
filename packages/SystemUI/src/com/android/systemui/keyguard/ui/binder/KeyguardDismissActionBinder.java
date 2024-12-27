package com.android.systemui.keyguard.ui.binder;

import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardDismissActionBinder implements CoreStartable {
    public final KeyguardDismissActionInteractor interactor;
    public final KeyguardLogger keyguardLogger;

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

    public KeyguardDismissActionBinder(KeyguardDismissActionInteractor keyguardDismissActionInteractor, CoroutineScope coroutineScope, KeyguardLogger keyguardLogger) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
    }
}
