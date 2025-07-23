package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.qs.panels.domain.interactor.QSColumnsInteractor;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSColumnsViewModel extends ExclusiveActivatable {
    public final State columns$delegate;
    public final State columnsWithoutMedia$delegate;
    public final Hydrator hydrator;
    public final MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QSColumnsViewModel create(Integer num);

        QSColumnsViewModel createWithoutMediaTracking();
    }

    public QSColumnsViewModel(QSColumnsInteractor qSColumnsInteractor, MediaInRowInLandscapeViewModel.Factory factory, Integer num) {
        Hydrator hydrator = new Hydrator("QSColumnsViewModelWithMedia", 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        this.columns$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                QSColumnsViewModel qSColumnsViewModel = QSColumnsViewModel.this;
                State state = qSColumnsViewModel.columnsWithoutMedia$delegate;
                MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = qSColumnsViewModel.mediaInRowInLandscapeViewModel;
                return Integer.valueOf((mediaInRowInLandscapeViewModel == null || !mediaInRowInLandscapeViewModel.getShouldMediaShowInRow()) ? ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue() : ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue() / 2);
            }
        });
        this.mediaInRowInLandscapeViewModel = num != null ? factory.create(num.intValue()) : null;
        this.columnsWithoutMedia$delegate = hydrator.hydratedStateOf(qSColumnsInteractor.columns, "columnsWithoutMedia");
    }

    public final int getColumns() {
        return ((Number) this.columns$delegate.getValue()).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$1 r0 = (com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$1 r0 = new com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$2 r5 = new com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
