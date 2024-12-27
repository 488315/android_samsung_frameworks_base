package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.settings.UserFileManager;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPreferencesRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow showLabels;
    public final UserFileManager userFileManager;
    public final UserRepository userRepository;

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

    public QSPreferencesRepository(UserFileManager userFileManager, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher) {
        this.userFileManager = userFileManager;
        this.showLabels = FlowKt.flowOn(FlowKt.transformLatest(((UserRepositoryImpl) userRepository).selectedUserInfo, new QSPreferencesRepository$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher);
    }
}
