package com.android.systemui.qs.panels.domain.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.qs.panels.domain.interactor.QSPreferencesInteractor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPanelsCoreStartable implements CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final QSPreferencesInteractor preferenceInteractor;

    public QSPanelsCoreStartable(QSPreferencesInteractor qSPreferencesInteractor, CoroutineScope coroutineScope) {
        this.preferenceInteractor = qSPreferencesInteractor;
        this.backgroundApplicationScope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.backgroundApplicationScope, null, null, new QSPanelsCoreStartable$start$1(this, null), 3);
    }
}
