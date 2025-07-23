package androidx.compose.foundation.text;

import androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt;
import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.foundation.contextmenu.ContextMenuState_androidKt;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContextMenu_androidKt {
    public static final void ContextMenuArea(final TextFieldSelectionManager textFieldSelectionManager, Function2 function2, Composer composer, final int i) {
        int i2;
        final Function2 function22;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1985516685);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(textFieldSelectionManager) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 19) != 18)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.ContextMenuArea (ContextMenu.android.kt:43)");
            }
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = new ContextMenuState(null, 1, null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final ContextMenuState contextMenuState = (ContextMenuState) rememberedValue;
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == obj) {
                rememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue2;
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (rememberedValue3 == obj) {
                MenuItemsAvailability.Companion.getClass();
                rememberedValue3 = SnapshotStateKt.mutableStateOf$default(MenuItemsAvailability.m199boximpl(0));
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            final MutableState mutableState = (MutableState) rememberedValue3;
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (rememberedValue4 == obj) {
                rememberedValue4 = new Function0() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ContextMenuState_androidKt.close(ContextMenuState.this);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            Function0 function0 = (Function0) rememberedValue4;
            Function1 contextMenuBuilder = TextFieldSelectionManager_androidKt.contextMenuBuilder(textFieldSelectionManager, contextMenuState, mutableState);
            boolean enabled = textFieldSelectionManager.getEnabled();
            boolean changedInstance = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(textFieldSelectionManager);
            Object rememberedValue5 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue5 == obj) {
                rememberedValue5 = new Function0() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$2$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$2$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        final /* synthetic */ TextFieldSelectionManager $manager;
                        final /* synthetic */ MutableState<MenuItemsAvailability> $menuItemsAvailability;
                        Object L$0;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(MutableState<MenuItemsAvailability> mutableState, TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                            super(2, continuation);
                            this.$menuItemsAvailability = mutableState;
                            this.$manager = textFieldSelectionManager;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.$menuItemsAvailability, this.$manager, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            MutableState<MenuItemsAvailability> mutableState;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                MutableState<MenuItemsAvailability> mutableState2 = this.$menuItemsAvailability;
                                TextFieldSelectionManager textFieldSelectionManager = this.$manager;
                                this.L$0 = mutableState2;
                                this.label = 1;
                                Object contextMenuItemsAvailability = ContextMenu_androidKt.getContextMenuItemsAvailability(textFieldSelectionManager, this);
                                if (contextMenuItemsAvailability == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                obj = contextMenuItemsAvailability;
                                mutableState = mutableState2;
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                mutableState = (MutableState) this.L$0;
                                ResultKt.throwOnFailure(obj);
                            }
                            mutableState.setValue(obj);
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BuildersKt.launch$default(CoroutineScope.this, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(mutableState, textFieldSelectionManager, null), 1);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue5);
            }
            function22 = function2;
            ContextMenuArea_androidKt.ContextMenuArea(contextMenuState, function0, contextMenuBuilder, null, enabled, (Function0) rememberedValue5, function22, composerImpl, ((i2 << 15) & 3670016) | 54, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            function22 = function2;
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ContextMenu_androidKt.ContextMenuArea(TextFieldSelectionManager.this, function22, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object getContextMenuItemsAvailability(androidx.compose.foundation.text.selection.TextFieldSelectionManager r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.ContextMenu_androidKt.getContextMenuItemsAvailability(androidx.compose.foundation.text.selection.TextFieldSelectionManager, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
