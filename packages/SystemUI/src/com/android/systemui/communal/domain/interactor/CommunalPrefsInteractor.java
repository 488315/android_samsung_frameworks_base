package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CommunalPrefsRepository;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* loaded from: classes2.dex */
public final class CommunalPrefsInteractor {
    public final CoroutineScope bgScope;
    public final ReadonlyStateFlow isCtaDismissed;
    public final ReadonlyStateFlow isHubOnboardingDismissed;
    public final CommunalPrefsRepository repository;
    public final UserTracker userTracker;

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

    public CommunalPrefsInteractor(CoroutineScope coroutineScope, CommunalPrefsRepository communalPrefsRepository, SelectedUserInteractor selectedUserInteractor, UserTracker userTracker, TableLogBuffer tableLogBuffer) {
        this.bgScope = coroutineScope;
        this.repository = communalPrefsRepository;
        this.userTracker = userTracker;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(selectedUserInteractor.selectedUserInfo, new CommunalPrefsInteractor$special$$inlined$flatMapLatest$1(null, this)), tableLogBuffer, "", "isCtaDismissed", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isCtaDismissed = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        CommunalPrefsInteractor$special$$inlined$flatMapLatest$2 communalPrefsInteractor$special$$inlined$flatMapLatest$2 = new CommunalPrefsInteractor$special$$inlined$flatMapLatest$2(null, this);
        UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = selectedUserInteractor.selectedUserInfo;
        this.isHubOnboardingDismissed = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, communalPrefsInteractor$special$$inlined$flatMapLatest$2), tableLogBuffer, "", "isHubOnboardingDismissed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, new CommunalPrefsInteractor$special$$inlined$flatMapLatest$3(null, this)), tableLogBuffer, "", "isDreamButtonTooltipDismissed", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }
}
