package com.android.systemui.communal;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.data.repository.CommunalMediaRepository;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepository;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalOngoingContentStartable implements CoreStartable {
    public final CoroutineScope bgScope;
    public final CommunalInteractor communalInteractor;
    public final CommunalMediaRepository communalMediaRepository;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalSmartspaceRepository communalSmartspaceRepository;
    public final boolean showUmoOnHub;

    public CommunalOngoingContentStartable(CoroutineScope coroutineScope, CommunalInteractor communalInteractor, CommunalMediaRepository communalMediaRepository, CommunalSettingsInteractor communalSettingsInteractor, CommunalSmartspaceRepository communalSmartspaceRepository, boolean z) {
        this.bgScope = coroutineScope;
        this.communalInteractor = communalInteractor;
        this.communalMediaRepository = communalMediaRepository;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSmartspaceRepository = communalSmartspaceRepository;
        this.showUmoOnHub = z;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalOngoingContentStartable$start$1(this, null), 7);
        }
    }
}
