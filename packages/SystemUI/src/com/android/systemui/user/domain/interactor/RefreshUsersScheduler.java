package com.android.systemui.user.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.user.data.repository.UserRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RefreshUsersScheduler {
    public final CoroutineScope applicationScope;
    public boolean isPaused;
    public final CoroutineDispatcher mainDispatcher;
    public final UserRepository repository;
    public StandaloneCoroutine scheduledUnpauseJob;

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

    public RefreshUsersScheduler(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.repository = userRepository;
    }

    public final void refreshIfNotPaused() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.mainDispatcher, null, new RefreshUsersScheduler$refreshIfNotPaused$1(this, null), 5);
    }
}
