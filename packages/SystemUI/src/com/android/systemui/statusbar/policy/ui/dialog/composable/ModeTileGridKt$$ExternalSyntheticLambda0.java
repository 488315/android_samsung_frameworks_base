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
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final MutableState mutableState = this.f$0;
                ((LazyGridIntervalContent) ((LazyGridScope) obj)).items(((List) mutableState.getValue()).size(), new ModeTileGridKt$$ExternalSyntheticLambda0(mutableState, 1), null, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridScope$items$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                        ((Number) obj2).intValue();
                        return null;
                    }
                }, new ComposableLambdaImpl(-1958635366, true, new Function4() { // from class: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$ModeTileGrid$2$1$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                        int iIntValue = ((Number) obj3).intValue();
                        Composer composer = (Composer) obj4;
                        int iIntValue2 = ((Number) obj5).intValue();
                        if ((iIntValue2 & 48) == 0) {
                            iIntValue2 |= ((ComposerImpl) composer).changed(iIntValue) ? 32 : 16;
                        }
                        if ((iIntValue2 & 145) == 144) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGrid.<anonymous>.<anonymous>.<anonymous> (ModeTileGrid.kt:85)");
                                }
                                ModeTileKt.ModeTile((ModeTileViewModel) ((List) mutableState.getValue()).get(iIntValue), null, composer, 0, 2);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
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
