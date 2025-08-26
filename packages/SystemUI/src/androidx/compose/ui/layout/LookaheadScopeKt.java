package androidx.compose.ui.layout;

import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.InnerNodeCoordinator;
import androidx.compose.ui.node.LayoutNode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class LookaheadScopeKt {
    public static final Function2 defaultPlacementApproachInProgress = new Function2() { // from class: androidx.compose.ui.layout.LookaheadScopeKt$defaultPlacementApproachInProgress$1
        @Override // kotlin.jvm.functions.Function2
        public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.FALSE;
        }
    };

    /* JADX WARN: Multi-variable type inference failed */
    public static final void LookaheadScope(final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1078066484);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function3) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        int i3 = 1;
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 3) != 2)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.layout.LookaheadScope (LookaheadScope.kt:48)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Function0 function0 = null;
            Object[] objArr = 0;
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new LookaheadScopeImpl(function0, i3, objArr == true ? 1 : 0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            Object obj = (LookaheadScopeImpl) objRememberedValue;
            AnonymousClass1 anonymousClass1 = new Function0() { // from class: androidx.compose.ui.layout.LookaheadScopeKt.LookaheadScope.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new LayoutNode(true, 0, 2, null);
                }
            };
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(anonymousClass1);
            } else {
                composerImpl.useNode();
            }
            Updater.m336initimpl(composerImpl, new Function1() { // from class: androidx.compose.ui.layout.LookaheadScopeKt$LookaheadScope$2$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    ((LayoutNode) obj2).isVirtualLookaheadRoot = true;
                    return Unit.INSTANCE;
                }
            });
            Updater.m337setimpl(composerImpl, obj, new Function2() { // from class: androidx.compose.ui.layout.LookaheadScopeKt$LookaheadScope$2$2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    final LayoutNode layoutNode = (LayoutNode) obj2;
                    ((LookaheadScopeImpl) obj3).scopeCoordinates = new Function0() { // from class: androidx.compose.ui.layout.LookaheadScopeKt$LookaheadScope$2$2.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                            parent$ui_release.getClass();
                            InnerNodeCoordinator innerNodeCoordinator = parent$ui_release.nodes.innerCoordinator;
                            innerNodeCoordinator.getClass();
                            return innerNodeCoordinator;
                        }
                    };
                    return Unit.INSTANCE;
                }
            });
            function3.invoke(obj, composerImpl, Integer.valueOf((i2 << 3) & 112));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.layout.LookaheadScopeKt.LookaheadScope.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    LookaheadScopeKt.LookaheadScope(function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier approachLayout(Modifier modifier, Function1 function1, Function2 function2, Function3 function3) {
        return modifier.then(new ApproachLayoutElement(function3, function1, function2));
    }
}
