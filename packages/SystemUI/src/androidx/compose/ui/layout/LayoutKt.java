package androidx.compose.ui.layout;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.node.ComposeUiNode;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class LayoutKt {
    public static final ComposableLambdaImpl combineAsVirtualLayouts(final List list) {
        return new ComposableLambdaImpl(-1953651383, true, new Function2() { // from class: androidx.compose.ui.layout.LayoutKt.combineAsVirtualLayouts.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                int iIntValue = ((Number) obj2).intValue();
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.ui.layout.combineAsVirtualLayouts.<anonymous> (Layout.kt:176)");
                    }
                    List<Function2> list2 = list;
                    int size = list2.size();
                    for (int i = 0; i < size; i++) {
                        Function2 function2 = list2.get(i);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.VirtualConstructor;
                        if (composerImpl.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function0);
                        } else {
                            composerImpl.useNode();
                        }
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                        }
                        function2.invoke(composerImpl, 0);
                        composerImpl.end(true);
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
