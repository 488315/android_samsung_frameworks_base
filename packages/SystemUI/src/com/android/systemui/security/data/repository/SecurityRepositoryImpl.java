package com.android.systemui.security.data.repository;

import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class SecurityRepositoryImpl implements SecurityRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final Flow security = FlowConflatedKt.conflatedCallbackFlow(new SecurityRepositoryImpl$security$1(this, null));
    public final SecurityController securityController;

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
