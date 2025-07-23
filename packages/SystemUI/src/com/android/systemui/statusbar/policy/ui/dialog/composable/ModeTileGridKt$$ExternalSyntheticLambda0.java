package com.android.systemui.statusbar.policy.ui.dialog.composable;

import androidx.compose.foundation.lazy.grid.LazyGridIntervalContent;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModeTileViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ModeTileGridKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MutableState f$0;

    public /* synthetic */ ModeTileGridKt$$ExternalSyntheticLambda0(MutableState mutableState, int i) {
        this.$r8$classId = i;
        this.f$0 = mutableState;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final MutableState mutableState = this.f$0;
                ((LazyGridIntervalContent) ((LazyGridScope) obj)).items(((List) mutableState.getValue()).size(), new ModeTileGridKt$$ExternalSyntheticLambda0(mutableState, 1), null, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridScope$items$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
                        ((Number) obj2).intValue();
                        return null;
                    }
                }, new ComposableLambdaImpl(-1958635366, true, new Function4() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$ModeTileGrid$2$1$2
                    @Override // kotlin.jvm.functions.Function4
                    public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                        int intValue = ((Number) obj3).intValue();
                        Composer composer = (Composer) obj4;
                        int intValue2 = ((Number) obj5).intValue();
                        if ((intValue2 & 48) == 0) {
                            intValue2 |= ((ComposerImpl) composer).changed(intValue) ? 32 : 16;
                        }
                        if ((intValue2 & 145) == 144) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGrid.<anonymous>.<anonymous>.<anonymous> (ModeTileGrid.kt:85)");
                        }
                        ModeTileKt.ModeTile((ModeTileViewModel) ((List) mutableState.getValue()).get(intValue), null, composer, 0, 2);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }));
                return Unit.INSTANCE;
            default:
                return ((ModeTileViewModel) ((List) this.f$0.getValue()).get(((Integer) obj).intValue())).id;
        }
    }
}
