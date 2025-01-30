package com.android.systemui.security.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecurityRepositoryImpl implements SecurityRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final Flow security;
    public final SecurityController securityController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.security = ConflatedCallbackFlow.conflatedCallbackFlow(securityRepositoryImpl$security$1);
    }
}
