package com.android.systemui.security.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecurityRepositoryImpl implements SecurityRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final Flow security;
    public final SecurityController securityController;

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

    public SecurityRepositoryImpl(SecurityController securityController, CoroutineDispatcher coroutineDispatcher) {
        this.securityController = securityController;
        this.bgDispatcher = coroutineDispatcher;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SecurityRepositoryImpl$security$1 securityRepositoryImpl$security$1 = new SecurityRepositoryImpl$security$1(this, null);
        conflatedCallbackFlow.getClass();
        this.security = FlowConflatedKt.conflatedCallbackFlow(securityRepositoryImpl$security$1);
    }
}
