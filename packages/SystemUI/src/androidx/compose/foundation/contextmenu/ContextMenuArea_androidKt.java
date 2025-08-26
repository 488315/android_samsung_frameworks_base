package androidx.compose.foundation.contextmenu;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.IntOffsetKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ContextMenuArea_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:67:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenu(final ContextMenuState contextMenuState, final Function0 function0, Modifier modifier, final Function1 function1, Composer composer, final int i, final int i2) throws Throwable {
        int i3;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(645832757);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(contextMenuState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 8) != 0) {
            i3 |= 3072;
        } else if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 2048 : 1024;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            final Modifier modifier3 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenu (ContextMenuArea.android.kt:73)");
            }
            ContextMenuState.Status status = (ContextMenuState.Status) ((SnapshotMutableStateImpl) contextMenuState.status$delegate).getValue();
            if (!(status instanceof ContextMenuState.Status.Open)) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt.ContextMenu.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) throws Throwable {
                            ((Number) obj2).intValue();
                            ContextMenuArea_androidKt.ContextMenu(contextMenuState, function0, modifier3, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            boolean zChanged = composerImpl.changed(status);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    ContextMenuPopupPositionProvider contextMenuPopupPositionProvider = new ContextMenuPopupPositionProvider(IntOffsetKt.m856roundk4lQ0M(((ContextMenuState.Status.Open) status).offset), null, 2, null);
                    composerImpl.updateRememberedValue(contextMenuPopupPositionProvider);
                    objRememberedValue = contextMenuPopupPositionProvider;
                }
                ContextMenuUi_androidKt.ContextMenuPopup((ContextMenuPopupPositionProvider) objRememberedValue, function0, modifier3, function1, composerImpl, i3 & 8176, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = modifier3;
            }
        } else {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 != null) {
            recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt.ContextMenu.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    ContextMenuArea_androidKt.ContextMenu(contextMenuState, function0, modifier2, function1, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:131:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContextMenuArea(final ContextMenuState contextMenuState, final Function0 function0, final Function1 function1, Modifier modifier, boolean z, Function0 function02, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        Function0 function03;
        Function1 function12;
        int i4;
        Modifier modifier2;
        int i5;
        boolean z2;
        int i6;
        final Function0 function04;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Modifier modifierPointerInput;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1969259374);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(contextMenuState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                function03 = function0;
                i3 |= composerImpl.changedInstance(function03) ? 32 : 16;
            }
            if ((i2 & 4) == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    function12 = function1;
                    i3 |= composerImpl.changedInstance(function12) ? 256 : 128;
                }
                i4 = i2 & 8;
                if (i4 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        modifier2 = modifier;
                        i3 |= composerImpl.changed(modifier2) ? 2048 : 1024;
                    }
                    i5 = i2 & 16;
                    if (i5 == 0) {
                        i3 |= 24576;
                    } else {
                        if ((i & 24576) == 0) {
                            z2 = z;
                            i3 |= composerImpl.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i6 = i2 & 32;
                        if (i6 == 0) {
                            if ((196608 & i) == 0) {
                                function04 = function02;
                                i3 |= composerImpl.changedInstance(function04) ? 131072 : 65536;
                            }
                            if ((i2 & 64) == 0) {
                                i3 |= 1572864;
                            } else if ((i & 1572864) == 0) {
                                i3 |= composerImpl.changedInstance(function2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                            if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (i4 != 0) {
                                    modifier2 = Modifier.Companion;
                                }
                                if (i5 != 0) {
                                    z2 = true;
                                }
                                if (i6 != 0) {
                                    function04 = new Function0() { // from class: androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt.ContextMenuArea.1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final /* bridge */ /* synthetic */ Object invoke() {
                                            return Unit.INSTANCE;
                                        }
                                    };
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.contextmenu.ContextMenuArea (ContextMenuArea.android.kt:46)");
                                }
                                if (z2) {
                                    composerImpl.startReplaceGroup(1021907653);
                                    boolean z3 = ((458752 & i3) == 131072) | ((i3 & 14) == 4);
                                    Object objRememberedValue = composerImpl.rememberedValue();
                                    if (!z3) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new Function1() { // from class: androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt$ContextMenuArea$finalModifier$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    long j = ((Offset) obj).packedValue;
                                                    function04.invoke();
                                                    ContextMenuState contextMenuState2 = contextMenuState;
                                                    ((SnapshotMutableStateImpl) contextMenuState2.status$delegate).setValue(new ContextMenuState.Status.Open(j, null));
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                        }
                                        final Function1 function13 = (Function1) objRememberedValue;
                                        modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifier2, ContextMenuKey.INSTANCE, new PointerInputEventHandler() { // from class: androidx.compose.foundation.contextmenu.ContextMenuGestures_androidKt$contextMenuGestures$2
                                            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new ContextMenuGestures_androidKt$onRightClickDown$2(function13, null), continuation);
                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                if (objAwaitEachGesture != coroutineSingletons) {
                                                    objAwaitEachGesture = Unit.INSTANCE;
                                                }
                                                return objAwaitEachGesture == coroutineSingletons ? objAwaitEachGesture : Unit.INSTANCE;
                                            }
                                        });
                                        composerImpl.end(false);
                                    }
                                } else {
                                    composerImpl.startReplaceGroup(1022064513);
                                    composerImpl.end(false);
                                    modifierPointerInput = modifier2;
                                }
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierPointerInput);
                                ComposeUiNode.Companion.getClass();
                                Function0 function05 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function05);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                function2.invoke(composerImpl, Integer.valueOf((i3 >> 18) & 14));
                                ContextMenu(contextMenuState, function03, null, function12, composerImpl, (i3 & 126) | ((i3 << 3) & 7168), 4);
                                composerImpl.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                            final boolean z4 = z2;
                            final Function0 function06 = function04;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final Modifier modifier3 = modifier2;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt.ContextMenuArea.3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        ContextMenuArea_androidKt.ContextMenuArea(contextMenuState, function0, function1, modifier3, z4, function06, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 196608;
                        function04 = function02;
                        if ((i2 & 64) == 0) {
                        }
                        if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
                        }
                        final boolean z42 = z2;
                        final Function0 function062 = function04;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    z2 = z;
                    i6 = i2 & 32;
                    if (i6 == 0) {
                    }
                    function04 = function02;
                    if ((i2 & 64) == 0) {
                    }
                    if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
                    }
                    final boolean z422 = z2;
                    final Function0 function0622 = function04;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                modifier2 = modifier;
                i5 = i2 & 16;
                if (i5 == 0) {
                }
                z2 = z;
                i6 = i2 & 32;
                if (i6 == 0) {
                }
                function04 = function02;
                if ((i2 & 64) == 0) {
                }
                if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
                }
                final boolean z4222 = z2;
                final Function0 function06222 = function04;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            function12 = function1;
            i4 = i2 & 8;
            if (i4 != 0) {
            }
            modifier2 = modifier;
            i5 = i2 & 16;
            if (i5 == 0) {
            }
            z2 = z;
            i6 = i2 & 32;
            if (i6 == 0) {
            }
            function04 = function02;
            if ((i2 & 64) == 0) {
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
            }
            final boolean z42222 = z2;
            final Function0 function062222 = function04;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        function03 = function0;
        if ((i2 & 4) == 0) {
        }
        function12 = function1;
        i4 = i2 & 8;
        if (i4 != 0) {
        }
        modifier2 = modifier;
        i5 = i2 & 16;
        if (i5 == 0) {
        }
        z2 = z;
        i6 = i2 & 32;
        if (i6 == 0) {
        }
        function04 = function02;
        if ((i2 & 64) == 0) {
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 599187) == 599186)) {
        }
        final boolean z422222 = z2;
        final Function0 function0622222 = function04;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
