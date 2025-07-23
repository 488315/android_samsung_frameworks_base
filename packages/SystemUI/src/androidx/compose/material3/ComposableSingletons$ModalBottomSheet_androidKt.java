package androidx.compose.material3;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposableSingletons$ModalBottomSheet_androidKt {
    public static final ComposableSingletons$ModalBottomSheet_androidKt INSTANCE = new ComposableSingletons$ModalBottomSheet_androidKt();

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f9lambda2;

    static {
        new ComposableLambdaImpl(-1187811352, false, new Function2() { // from class: androidx.compose.material3.ComposableSingletons$ModalBottomSheet_androidKt$lambda-1$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.ComposableSingletons$ModalBottomSheet_androidKt.lambda-1.<anonymous> (ModalBottomSheet.android.kt:259)");
                }
                BottomSheetDefaults.INSTANCE.m249DragHandlelgZ2HuY(null, 0.0f, 0.0f, null, 0L, composer, 196608, 31);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        });
        f9lambda2 = new ComposableLambdaImpl(-91331245, false, new Function2() { // from class: androidx.compose.material3.ComposableSingletons$ModalBottomSheet_androidKt$lambda-2$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.ComposableSingletons$ModalBottomSheet_androidKt.lambda-2.<anonymous> (ModalBottomSheet.android.kt:350)");
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
