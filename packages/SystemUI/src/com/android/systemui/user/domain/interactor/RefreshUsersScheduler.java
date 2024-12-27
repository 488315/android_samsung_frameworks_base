package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.data.repository.UserRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RefreshUsersScheduler {
    public final CoroutineScope applicationScope;
    public boolean isPaused;
    public final CoroutineDispatcher mainDispatcher;
    public final UserRepository repository;
    public Job scheduledUnpauseJob;

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

    public RefreshUsersScheduler(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.repository = userRepository;
    }

    public final void refreshIfNotPaused() {
        BuildersKt.launch$default(this.applicationScope, this.mainDispatcher, null, new RefreshUsersScheduler$refreshIfNotPaused$1(this, null), 2);
    }
}
