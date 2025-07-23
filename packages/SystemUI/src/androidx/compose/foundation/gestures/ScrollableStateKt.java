package androidx.compose.foundation.gestures;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScrollableStateKt {
    public static final ScrollableState ScrollableState(Function1 function1) {
        return new DefaultScrollableState(function1);
    }

    public static final ScrollableState rememberScrollableState(Composer composer, Function1 function1) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.gestures.rememberScrollableState (ScrollableState.kt:159)");
        }
        final MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composer);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            DefaultScrollableState defaultScrollableState = new DefaultScrollableState(new Function1() { // from class: androidx.compose.foundation.gestures.ScrollableStateKt$rememberScrollableState$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    return (Float) ((Function1) rememberUpdatedState.getValue()).mo779invoke(Float.valueOf(((Number) obj).floatValue()));
                }
            });
            composerImpl.updateRememberedValue(defaultScrollableState);
            rememberedValue = defaultScrollableState;
        }
        ScrollableState scrollableState = (ScrollableState) rememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return scrollableState;
    }
}
