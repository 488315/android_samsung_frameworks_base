package com.android.systemui.qs.external.ui.viewmodel;

import android.app.IUriGrantsManager;
import android.content.Context;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.external.TileData;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiStateKt;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileRequestDialogViewModel extends ExclusiveActivatable {
    public static final QSTile.Icon defaultIcon;
    public final MutableState _icon$delegate = SnapshotStateKt.mutableStateOf$default(defaultIcon);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context dialogContext;
    public final IUriGrantsManager iUriGrantsManager;
    public final State iconProvider$delegate;
    public final TileData tileData;
    public final State uiState$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        TileRequestDialogViewModel create(Context context, TileData tileData);
    }

    static {
        new Companion(null);
        defaultIcon = QSTileImpl.ResourceIcon.get(R.drawable.android);
    }

    public TileRequestDialogViewModel(IUriGrantsManager iUriGrantsManager, CoroutineDispatcher coroutineDispatcher, Context context, TileData tileData) {
        this.iUriGrantsManager = iUriGrantsManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.dialogContext = context;
        this.tileData = tileData;
        final int i = 0;
        this.uiState$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ TileRequestDialogViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TileRequestDialogViewModel tileRequestDialogViewModel = this.f$0;
                switch (i) {
                    case 0:
                        QSTile.Icon icon = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state = new QSTile.State();
                        state.label = tileRequestDialogViewModel.tileData.label;
                        state.handlesLongClick = false;
                        state.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toUiState(state, tileRequestDialogViewModel.dialogContext.getResources());
                    default:
                        QSTile.Icon icon2 = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state2 = new QSTile.State();
                        state2.label = tileRequestDialogViewModel.tileData.label;
                        state2.handlesLongClick = false;
                        state2.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toIconProvider(state2);
                }
            }
        });
        final int i2 = 1;
        this.iconProvider$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ TileRequestDialogViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TileRequestDialogViewModel tileRequestDialogViewModel = this.f$0;
                switch (i2) {
                    case 0:
                        QSTile.Icon icon = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state = new QSTile.State();
                        state.label = tileRequestDialogViewModel.tileData.label;
                        state.handlesLongClick = false;
                        state.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toUiState(state, tileRequestDialogViewModel.dialogContext.getResources());
                    default:
                        QSTile.Icon icon2 = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state2 = new QSTile.State();
                        state2.label = tileRequestDialogViewModel.tileData.label;
                        state2.handlesLongClick = false;
                        state2.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toIconProvider(state2);
                }
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0047, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r5.backgroundDispatcher, r6, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1 r0 = (com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1 r0 = new com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2e:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L53
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4a
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$2 r6 = new com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.label = r4
            kotlinx.coroutines.CoroutineDispatcher r5 = r5.backgroundDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r0)
            if (r5 != r1) goto L4a
            goto L52
        L4a:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r5 != r1) goto L53
        L52:
            return r1
        L53:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
