package com.android.systemui.keyguard.ui.binder;

import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardDismissBinder implements CoreStartable {
    public final KeyguardDismissInteractor interactor;
    public final KeyguardLogger keyguardLogger;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ViewMediatorCallback viewMediatorCallback;

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

    public KeyguardDismissBinder(KeyguardDismissInteractor keyguardDismissInteractor, SelectedUserInteractor selectedUserInteractor, ViewMediatorCallback viewMediatorCallback, CoroutineScope coroutineScope, KeyguardLogger keyguardLogger) {
        this.interactor = keyguardDismissInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.viewMediatorCallback = viewMediatorCallback;
        this.keyguardLogger = keyguardLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ComposeBouncerFlags.INSTANCE.getClass();
    }
}
