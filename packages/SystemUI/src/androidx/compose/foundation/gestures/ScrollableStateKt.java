package androidx.compose.foundation.gestures;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class ScrollableStateKt {
    public static final ScrollableState ScrollableState(Function1 function1) {
        return new DefaultScrollableState(function1);
    }

    public static final ScrollableState rememberScrollableState(Composer composer, Function1 function1) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.gestures.rememberScrollableState (ScrollableState.kt:159)");
        }
        final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composer);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            DefaultScrollableState defaultScrollableState = new DefaultScrollableState(new Function1() { // from class: androidx.compose.foundation.gestures.ScrollableStateKt$rememberScrollableState$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return (Float) ((Function1) mutableStateRememberUpdatedState.getValue()).mo781invoke(Float.valueOf(((Number) obj).floatValue()));
                }
            });
            composerImpl.updateRememberedValue(defaultScrollableState);
            objRememberedValue = defaultScrollableState;
        }
        ScrollableState scrollableState = (ScrollableState) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return scrollableState;
    }
}
