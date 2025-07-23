package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutItemContentFactoryKt {
    /* renamed from: access$SkippableItem-JVlU9Rs, reason: not valid java name */
    public static final void m168access$SkippableItemJVlU9Rs(final LazyLayoutItemProvider lazyLayoutItemProvider, final Object obj, final int i, final Object obj2, Composer composer, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1439843069);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changed(lazyLayoutItemProvider) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changed(obj) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changed(i) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changed(obj2) ? 2048 : 1024;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.SkippableItem (LazyLayoutItemContentFactory.kt:127)");
            }
            ((SaveableStateHolder) obj).SaveableStateProvider(obj2, ComposableLambdaKt.rememberComposableLambda(980966366, new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt$SkippableItem$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    Composer composer2 = (Composer) obj3;
                    int intValue = ((Number) obj4).intValue();
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.SkippableItem.<anonymous> (LazyLayoutItemContentFactory.kt:129)");
                        }
                        LazyLayoutItemProvider.this.Item(i, obj2, composerImpl2);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt$SkippableItem$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    ((Number) obj4).intValue();
                    LazyLayoutItemContentFactoryKt.m168access$SkippableItemJVlU9Rs(LazyLayoutItemProvider.this, obj, i, obj2, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
