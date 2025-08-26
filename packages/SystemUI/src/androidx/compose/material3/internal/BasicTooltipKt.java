package androidx.compose.material3.internal;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.material3.TooltipState;
import androidx.compose.material3.TooltipStateImpl;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.window.AndroidPopup_androidKt;
import androidx.compose.ui.window.PopupPositionProvider;
import androidx.compose.ui.window.PopupProperties;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class BasicTooltipKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:149:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void BasicTooltipBox(final PopupPositionProvider popupPositionProvider, final Function2 function2, TooltipState tooltipState, Modifier modifier, Function0 function0, boolean z, boolean z2, final Function2 function22, Composer composer, final int i, final int i2) {
        PopupPositionProvider popupPositionProvider2;
        int i3;
        Function2 function23;
        int i4;
        Modifier modifier2;
        int i5;
        Function0 function02;
        int i6;
        boolean z3;
        int i7;
        boolean z4;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        boolean z5;
        final boolean z6;
        final Modifier modifier3;
        final Function0 function03;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final TooltipState tooltipState2 = tooltipState;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(342983150);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            popupPositionProvider2 = popupPositionProvider;
        } else if ((i & 6) == 0) {
            popupPositionProvider2 = popupPositionProvider;
            i3 = (composerImpl.changed(popupPositionProvider2) ? 4 : 2) | i;
        } else {
            popupPositionProvider2 = popupPositionProvider;
            i3 = i;
        }
        if ((2 & i2) != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                function23 = function2;
                i3 |= composerImpl.changedInstance(function23) ? 32 : 16;
            }
            if ((4 & i2) == 0) {
                i3 |= 384;
            } else if ((i & 384) == 0) {
                i3 |= (i & 512) == 0 ? composerImpl.changed(tooltipState2) : composerImpl.changedInstance(tooltipState2) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    modifier2 = modifier;
                    i3 |= composerImpl.changed(modifier2) ? 2048 : 1024;
                }
                i5 = 16 & i2;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        function02 = function0;
                        i3 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i6 = 32 & i2;
                    if (i6 == 0) {
                        i3 |= 196608;
                    } else {
                        if ((196608 & i) == 0) {
                            z3 = z;
                            i3 |= composerImpl.changed(z3) ? 131072 : 65536;
                        }
                        i7 = i2 & 64;
                        if (i7 != 0) {
                            i3 |= 1572864;
                            z4 = z2;
                        } else {
                            z4 = z2;
                            if ((i & 1572864) == 0) {
                                i3 |= composerImpl.changed(z4) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                        }
                        if ((128 & i2) == 0) {
                            if ((i & 12582912) == 0) {
                                i3 |= composerImpl.changedInstance(function22) ? 8388608 : 4194304;
                            }
                            if ((i3 & 4793491) == 4793490 || !composerImpl.getSkipping()) {
                                if (i4 != 0) {
                                    modifier2 = Modifier.Companion;
                                }
                                Function0 function04 = i5 == 0 ? null : function02;
                                if (i6 != 0) {
                                    z3 = true;
                                }
                                if (i7 != 0) {
                                    z4 = true;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.internal.BasicTooltipBox (BasicTooltip.kt:93)");
                                }
                                objRememberedValue = composerImpl.rememberedValue();
                                Composer.Companion.getClass();
                                composer$Companion$Empty$1 = Composer.Companion.Empty;
                                if (objRememberedValue == composer$Companion$Empty$1) {
                                    CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                                    composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                                    objRememberedValue = compositionScopedCoroutineScopeCanceller;
                                }
                                CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
                                Modifier.Companion companion = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
                                ComposeUiNode.Companion.getClass();
                                Function0 function05 = ComposeUiNode.Companion.Constructor;
                                Function0 function06 = function04;
                                if (composerImpl.applier != null) {
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
                                Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function24);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                if (tooltipState.isVisible()) {
                                    composerImpl.startReplaceGroup(2073083575);
                                    int i8 = i3 >> 3;
                                    TooltipPopup(popupPositionProvider2, tooltipState, function06, coroutineScope, z3, function23, composerImpl, (i3 & 14) | (i8 & 112) | ((i3 >> 6) & 896) | (i8 & 57344) | ((i3 << 12) & 458752));
                                    z5 = false;
                                    composerImpl.end(false);
                                } else {
                                    z5 = false;
                                    composerImpl.startReplaceGroup(2073364187);
                                    composerImpl.end(false);
                                }
                                int i9 = i3 >> 3;
                                int i10 = ((i3 >> 18) & 14) | (i9 & 112) | (i9 & 896) | ((i3 >> 12) & 7168);
                                tooltipState2 = tooltipState;
                                boolean z7 = z4;
                                Modifier modifier4 = modifier2;
                                WrappedAnchor(z7, tooltipState2, modifier4, function22, composerImpl, i10, 0);
                                composerImpl = composerImpl;
                                composerImpl.end(true);
                                if ((i3 & 896) == 256 || ((i3 & 512) != 0 && composerImpl.changedInstance(tooltipState2))) {
                                    z5 = true;
                                }
                                Object objRememberedValue2 = composerImpl.rememberedValue();
                                if (z5 || objRememberedValue2 == composer$Companion$Empty$1) {
                                    objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.internal.BasicTooltipKt$BasicTooltipBox$2$1
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            final TooltipState tooltipState3 = tooltipState2;
                                            return new DisposableEffectResult() { // from class: androidx.compose.material3.internal.BasicTooltipKt$BasicTooltipBox$2$1$invoke$$inlined$onDispose$1
                                                @Override // androidx.compose.runtime.DisposableEffectResult
                                                public final void dispose() {
                                                    tooltipState3.onDispose();
                                                }
                                            };
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                }
                                EffectsKt.DisposableEffect(tooltipState2, (Function1) objRememberedValue2, composerImpl);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                z6 = z7;
                                modifier3 = modifier4;
                                function03 = function06;
                            } else {
                                composerImpl.skipToGroupEnd();
                                z6 = z4;
                                modifier3 = modifier2;
                                function03 = function02;
                            }
                            ComposerImpl composerImpl2 = composerImpl;
                            final boolean z8 = z3;
                            recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                final TooltipState tooltipState3 = tooltipState2;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.BasicTooltipKt.BasicTooltipBox.3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        BasicTooltipKt.BasicTooltipBox(popupPositionProvider, function2, tooltipState3, modifier3, function03, z8, z6, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 12582912;
                        if ((i3 & 4793491) == 4793490) {
                            if (i4 != 0) {
                            }
                            if (i5 == 0) {
                            }
                            if (i6 != 0) {
                            }
                            if (i7 != 0) {
                            }
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            objRememberedValue = composerImpl.rememberedValue();
                            Composer.Companion.getClass();
                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue == composer$Companion$Empty$1) {
                            }
                            CoroutineScope coroutineScope2 = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
                            Modifier.Companion companion2 = Modifier.Companion;
                            Alignment.Companion.getClass();
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                            ComposeUiNode.Companion.getClass();
                            Function0 function052 = ComposeUiNode.Companion.Constructor;
                            Function0 function062 = function04;
                            if (composerImpl.applier != null) {
                            }
                        }
                        ComposerImpl composerImpl22 = composerImpl;
                        final boolean z82 = z3;
                        recomposeScopeImplEndRestartGroup = composerImpl22.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    z3 = z;
                    i7 = i2 & 64;
                    if (i7 != 0) {
                    }
                    if ((128 & i2) == 0) {
                    }
                    if ((i3 & 4793491) == 4793490) {
                    }
                    ComposerImpl composerImpl222 = composerImpl;
                    final boolean z822 = z3;
                    recomposeScopeImplEndRestartGroup = composerImpl222.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                function02 = function0;
                i6 = 32 & i2;
                if (i6 == 0) {
                }
                z3 = z;
                i7 = i2 & 64;
                if (i7 != 0) {
                }
                if ((128 & i2) == 0) {
                }
                if ((i3 & 4793491) == 4793490) {
                }
                ComposerImpl composerImpl2222 = composerImpl;
                final boolean z8222 = z3;
                recomposeScopeImplEndRestartGroup = composerImpl2222.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            modifier2 = modifier;
            i5 = 16 & i2;
            if (i5 != 0) {
            }
            function02 = function0;
            i6 = 32 & i2;
            if (i6 == 0) {
            }
            z3 = z;
            i7 = i2 & 64;
            if (i7 != 0) {
            }
            if ((128 & i2) == 0) {
            }
            if ((i3 & 4793491) == 4793490) {
            }
            ComposerImpl composerImpl22222 = composerImpl;
            final boolean z82222 = z3;
            recomposeScopeImplEndRestartGroup = composerImpl22222.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        function23 = function2;
        if ((4 & i2) == 0) {
        }
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        modifier2 = modifier;
        i5 = 16 & i2;
        if (i5 != 0) {
        }
        function02 = function0;
        i6 = 32 & i2;
        if (i6 == 0) {
        }
        z3 = z;
        i7 = i2 & 64;
        if (i7 != 0) {
        }
        if ((128 & i2) == 0) {
        }
        if ((i3 & 4793491) == 4793490) {
        }
        ComposerImpl composerImpl222222 = composerImpl;
        final boolean z822222 = z3;
        recomposeScopeImplEndRestartGroup = composerImpl222222.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TooltipPopup(final PopupPositionProvider popupPositionProvider, final TooltipState tooltipState, final Function0 function0, final CoroutineScope coroutineScope, final boolean z, final Function2 function2, Composer composer, final int i) throws Throwable {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(29751458);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(popupPositionProvider) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(tooltipState) : composerImpl.changedInstance(tooltipState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(coroutineScope) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.TooltipPopup (BasicTooltip.kt:145)");
            }
            BasicTooltipStrings.INSTANCE.getClass();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.BasicTooltipStrings.description (BasicTooltip.android.kt:25)");
            }
            final String strStringResource = StringResources_androidKt.stringResource(R.string.tooltip_description, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean z2 = false;
            boolean z3 = (i2 & 896) == 256;
            if ((i2 & 112) == 32 || ((i2 & 64) != 0 && composerImpl.changedInstance(tooltipState))) {
                z2 = true;
            }
            boolean zChangedInstance = z3 | z2 | composerImpl.changedInstance(coroutineScope);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: androidx.compose.material3.internal.BasicTooltipKt$TooltipPopup$1$1

                        /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$TooltipPopup$1$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            final /* synthetic */ TooltipState $state;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(TooltipState tooltipState, Continuation continuation) {
                                super(2, continuation);
                                this.$state = tooltipState;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.$state, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                if (this.label != 0) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                                this.$state.dismiss();
                                return Unit.INSTANCE;
                            }
                        }

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Function0 function02 = function0;
                            if (function02 != null) {
                                function02.invoke();
                            } else if (tooltipState.isVisible()) {
                                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(tooltipState, null), 3);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                AndroidPopup_androidKt.Popup(popupPositionProvider, (Function0) objRememberedValue, new PopupProperties(z, false, false, false, 14, (DefaultConstructorMarker) null), ComposableLambdaKt.rememberComposableLambda(-936215100, new Function2() { // from class: androidx.compose.material3.internal.BasicTooltipKt.TooltipPopup.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.internal.TooltipPopup.<anonymous> (BasicTooltip.kt:160)");
                                }
                                Modifier.Companion companion = Modifier.Companion;
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                boolean zChanged = composerImpl3.changed(strStringResource);
                                final String str = strStringResource;
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChanged) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.internal.BasicTooltipKt$TooltipPopup$2$1$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj3) {
                                                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj3;
                                                LiveRegionMode.Companion.getClass();
                                                SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, LiveRegionMode.Assertive);
                                                SemanticsPropertiesKt.setPaneTitle(semanticsPropertyReceiver, str);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    Modifier modifierSemantics = SemanticsModifierKt.semantics(companion, false, (Function1) objRememberedValue2);
                                    Function2 function22 = function2;
                                    Alignment.Companion.getClass();
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierSemantics);
                                    ComposeUiNode.Companion.getClass();
                                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                                    if (composerImpl3.applier == null) {
                                        ComposablesKt.invalidApplier();
                                        throw null;
                                    }
                                    composerImpl3.startReusableNode();
                                    if (composerImpl3.inserting) {
                                        composerImpl3.createNode(function02);
                                    } else {
                                        composerImpl3.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                                    }
                                    Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    function22.invoke(composerImpl3, 0);
                                    composerImpl3.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, (i2 & 14) | 3072, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.BasicTooltipKt.TooltipPopup.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Number) obj2).intValue();
                    BasicTooltipKt.TooltipPopup(popupPositionProvider, tooltipState, function0, coroutineScope, z, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void WrappedAnchor(final boolean z, final TooltipState tooltipState, Modifier modifier, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(354945668);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((2 & i2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= (i & 64) == 0 ? composerImpl.changed(tooltipState) : composerImpl.changedInstance(tooltipState) ? 32 : 16;
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
            i3 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.WrappedAnchor (BasicTooltip.kt:124)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                objRememberedValue = compositionScopedCoroutineScopeCanceller;
            }
            final CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
            BasicTooltipStrings.INSTANCE.getClass();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.BasicTooltipStrings.label (BasicTooltip.android.kt:23)");
            }
            final String strStringResource = StringResources_androidKt.stringResource(R.string.tooltip_label, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Modifier modifierThen = z ? modifier.then(new SuspendPointerInputElement(tooltipState, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new BasicTooltipKt$handleGestures$1(tooltipState, null)), 6, null)).then(new SuspendPointerInputElement(tooltipState, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new BasicTooltipKt$handleGestures$2(tooltipState, null)), 6, null)) : modifier;
            if (z) {
                modifierThen = modifierThen.then(new ParentSemanticsNodeElement(new Function1() { // from class: androidx.compose.material3.internal.BasicTooltipKt$anchorSemantics$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        String str = strStringResource;
                        final CoroutineScope coroutineScope2 = coroutineScope;
                        final TooltipState tooltipState2 = tooltipState;
                        SemanticsPropertiesKt.onLongClick((SemanticsPropertyReceiver) obj, str, new Function0() { // from class: androidx.compose.material3.internal.BasicTooltipKt$anchorSemantics$1.1

                            /* renamed from: androidx.compose.material3.internal.BasicTooltipKt$anchorSemantics$1$1$1, reason: invalid class name and collision with other inner class name */
                            final class C00301 extends SuspendLambda implements Function2 {
                                final /* synthetic */ TooltipState $state;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C00301(TooltipState tooltipState, Continuation continuation) {
                                    super(2, continuation);
                                    this.$state = tooltipState;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    return new C00301(this.$state, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    return ((C00301) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        TooltipState tooltipState = this.$state;
                                        this.label = 1;
                                        if (((TooltipStateImpl) tooltipState).show(MutatePriority.Default, this) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                BuildersKt.launch$default(coroutineScope2, null, null, new C00301(tooltipState2, null), 3);
                                return Boolean.TRUE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                }));
            }
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            function2.invoke(composerImpl, Integer.valueOf((i3 >> 9) & 14));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        final Modifier modifier2 = modifier;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.BasicTooltipKt.WrappedAnchor.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BasicTooltipKt.WrappedAnchor(z, tooltipState, modifier2, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
