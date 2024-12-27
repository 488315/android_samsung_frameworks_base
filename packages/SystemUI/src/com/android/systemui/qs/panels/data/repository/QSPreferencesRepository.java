package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.settings.UserFileManager;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class QSPreferencesRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow showLabels;
    public final UserFileManager userFileManager;
    public final UserRepository userRepository;

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
