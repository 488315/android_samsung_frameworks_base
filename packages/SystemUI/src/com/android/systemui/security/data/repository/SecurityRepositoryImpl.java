package com.android.systemui.security.data.repository;

import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecurityRepositoryImpl implements SecurityRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final Flow security = FlowConflatedKt.conflatedCallbackFlow(new SecurityRepositoryImpl$security$1(this, null));
    public final SecurityController securityController;

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

    public SecurityRepositoryImpl(SecurityController securityController, CoroutineDispatcher coroutineDispatcher) {
        this.securityController = securityController;
        this.bgDispatcher = coroutineDispatcher;
    }
}
