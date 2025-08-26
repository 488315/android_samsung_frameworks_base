package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractorImpl;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class TilesBackUpRestoreInteractorImpl$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        TilesBackUpRestoreInteractorImpl.Companion companion = TilesBackUpRestoreInteractorImpl.Companion;
        return (QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class);
    }
}
