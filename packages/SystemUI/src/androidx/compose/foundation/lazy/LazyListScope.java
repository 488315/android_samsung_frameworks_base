package androidx.compose.foundation.lazy;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* loaded from: classes.dex */
public interface LazyListScope {
    static void item$default(LazyListScope lazyListScope, final Function3 function3) {
        LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) lazyListScope;
        lazyListIntervalContent.getClass();
        final Object obj = null;
        lazyListIntervalContent.intervals.addInterval(1, new LazyListInterval(null, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListIntervalContent$item$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                ((Number) obj2).intValue();
                return obj;
            }
        }, new ComposableLambdaImpl(-1010194746, true, new Function4() { // from class: androidx.compose.foundation.lazy.LazyListIntervalContent$item$3
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                LazyItemScope lazyItemScope = (LazyItemScope) obj2;
                ((Number) obj3).intValue();
                Composer composer = (Composer) obj4;
                int iIntValue = ((Number) obj5).intValue();
                if ((iIntValue & 6) == 0) {
                    iIntValue |= ((ComposerImpl) composer).changed(lazyItemScope) ? 4 : 2;
                }
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 131) != 130)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.LazyListIntervalContent.item.<anonymous> (LazyListIntervalContent.kt:59)");
                    }
                    function3.invoke(lazyItemScope, composerImpl, Integer.valueOf(iIntValue & 14));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                }
                return Unit.INSTANCE;
            }
        })));
    }
}
