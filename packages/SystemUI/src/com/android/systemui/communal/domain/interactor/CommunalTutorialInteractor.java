package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CommunalTutorialDisabledRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalTutorialRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalTutorialInteractor {
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalTutorialRepository communalTutorialRepository;
    public final ReadonlyStateFlow isTutorialAvailable;
    public final Flow tutorialStateToUpdate;

    public CommunalTutorialInteractor(CoroutineScope coroutineScope, CommunalTutorialRepository communalTutorialRepository, KeyguardInteractor keyguardInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalInteractor communalInteractor, TableLogBuffer tableLogBuffer) {
        this.communalTutorialRepository = communalTutorialRepository;
        this.communalSettingsInteractor = communalSettingsInteractor;
        CommunalTutorialDisabledRepositoryImpl communalTutorialDisabledRepositoryImpl = (CommunalTutorialDisabledRepositoryImpl) communalTutorialRepository;
        this.isTutorialAvailable = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) FlowKt.combine(communalInteractor.isCommunalAvailable, keyguardInteractor.isKeyguardVisible, communalTutorialDisabledRepositoryImpl.tutorialSettingState, new CommunalTutorialInteractor$isTutorialAvailable$1(null)), tableLogBuffer, "", "isTutorialAvailable", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        this.tutorialStateToUpdate = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.transformLatest(communalTutorialDisabledRepositoryImpl.tutorialSettingState, new CommunalTutorialInteractor$special$$inlined$flatMapLatest$1(null, communalInteractor, this))));
        BuildersKt.launch$default(coroutineScope, null, null, new CommunalTutorialInteractor$listenForTransitionToUpdateTutorialState$1(this, null), 3);
    }
}
