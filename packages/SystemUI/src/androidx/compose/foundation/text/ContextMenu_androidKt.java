package androidx.compose.foundation.text;

import android.content.ClipData;
import androidx.compose.foundation.contextmenu.ContextMenuArea_androidKt;
import androidx.compose.foundation.contextmenu.ContextMenuState;
import androidx.compose.foundation.contextmenu.ContextMenuState_androidKt;
import androidx.compose.foundation.internal.ClipboardUtils;
import androidx.compose.foundation.text.MenuItemsAvailability;
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
import androidx.compose.ui.platform.AndroidClipboard;
import androidx.compose.ui.platform.ClipEntry;
import androidx.compose.ui.platform.Clipboard;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: classes.dex */
public abstract class ContextMenu_androidKt {

    /* renamed from: androidx.compose.foundation.text.ContextMenu_androidKt$getContextMenuItemsAvailability$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ContextMenu_androidKt.getContextMenuItemsAvailability(null, this);
        }
    }

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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (objRememberedValue == obj) {
                objRememberedValue = new ContextMenuState(null, 1, null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final ContextMenuState contextMenuState = (ContextMenuState) objRememberedValue;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == obj) {
                objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue2;
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (objRememberedValue3 == obj) {
                MenuItemsAvailability.Companion.getClass();
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(MenuItemsAvailability.m200boximpl(0));
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            final MutableState mutableState = (MutableState) objRememberedValue3;
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (objRememberedValue4 == obj) {
                objRememberedValue4 = new Function0() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ContextMenuState_androidKt.close(contextMenuState);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            Function0 function0 = (Function0) objRememberedValue4;
            Function1 function1ContextMenuBuilder = TextFieldSelectionManager_androidKt.contextMenuBuilder(textFieldSelectionManager, contextMenuState, mutableState);
            boolean enabled = textFieldSelectionManager.getEnabled();
            boolean zChangedInstance = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(textFieldSelectionManager);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue5 == obj) {
                objRememberedValue5 = new Function0() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt$ContextMenuArea$2$1

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
                        BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(mutableState, textFieldSelectionManager, null), 1);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            function22 = function2;
            ContextMenuArea_androidKt.ContextMenuArea(contextMenuState, function0, function1ContextMenuBuilder, null, enabled, (Function0) objRememberedValue5, function22, composerImpl, ((i2 << 15) & 3670016) | 54, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            function22 = function2;
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.ContextMenu_androidKt.ContextMenuArea.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ContextMenu_androidKt.ContextMenuArea(textFieldSelectionManager, function22, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a6 A[PHI: r3 r5 r7 r8
      0x00a6: PHI (r3v3 ??) = (r3v1 ??), (r3v10 ??) binds: [B:20:0x0058, B:37:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x00a6: PHI (r5v3 int) = (r5v1 int), (r5v12 int) binds: [B:20:0x0058, B:37:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x00a6: PHI (r7v3 androidx.compose.foundation.text.selection.TextFieldSelectionManager) = 
      (r7v0 androidx.compose.foundation.text.selection.TextFieldSelectionManager)
      (r7v16 androidx.compose.foundation.text.selection.TextFieldSelectionManager)
     binds: [B:20:0x0058, B:37:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x00a6: PHI (r8v10 int) = (r8v6 int), (r8v18 int) binds: [B:20:0x0058, B:37:0x00a2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object getContextMenuItemsAvailability(TextFieldSelectionManager textFieldSelectionManager, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        ?? r3;
        int i;
        int i2;
        TextFieldSelectionManager textFieldSelectionManager2;
        int i3;
        int i4;
        int i5;
        boolean z;
        boolean z2;
        boolean z3;
        int i6;
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i7 = anonymousClass2.label;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i7 - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = new AnonymousClass2(continuationImpl);
            }
        }
        Object obj = anonymousClass2.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i8 = anonymousClass2.label;
        if (i8 == 0) {
            ResultKt.throwOnFailure(obj);
            r3 = textFieldSelectionManager.visualTransformation instanceof PasswordVisualTransformation;
            boolean zM749getCollapsedimpl = TextRange.m749getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
            i = !zM749getCollapsedimpl ? 1 : 0;
            i2 = (zM749getCollapsedimpl || r3 != 0) ? 0 : 1;
            if (textFieldSelectionManager.getEditable()) {
                Clipboard clipboard = textFieldSelectionManager.clipboard;
                i5 = r3;
                if (clipboard != null) {
                    anonymousClass2.L$0 = textFieldSelectionManager;
                    anonymousClass2.I$0 = r3;
                    anonymousClass2.I$1 = i;
                    anonymousClass2.I$2 = i2;
                    anonymousClass2.label = 1;
                    ClipData primaryClip = ((AndroidClipboard) clipboard).androidClipboardManager.clipboardManager.getPrimaryClip();
                    ClipEntry clipEntry = primaryClip != null ? new ClipEntry(primaryClip) : null;
                    if (clipEntry == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    ClipEntry clipEntry2 = clipEntry;
                    textFieldSelectionManager2 = textFieldSelectionManager;
                    i3 = i2;
                    obj = clipEntry2;
                    i4 = i;
                    i6 = r3;
                }
                z = false;
                r3 = i5;
                if (z) {
                    z2 = true;
                    z3 = r3;
                }
                boolean z4 = i == 0 && textFieldSelectionManager.getEditable() && !z3;
                boolean z5 = TextRange.m750getLengthimpl(textFieldSelectionManager.getValue$foundation_release().selection) == textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length();
                boolean z6 = !textFieldSelectionManager.getEditable() && TextRange.m749getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
                MenuItemsAvailability.Companion companion = MenuItemsAvailability.Companion;
                return MenuItemsAvailability.m200boximpl((!z2 ? 2 : 0) | i2 | (!z4 ? 4 : 0) | (!z5 ? 8 : 0) | (z6 ? 16 : 0));
            }
            z2 = false;
            z3 = r3;
            if (i == 0) {
            }
            if (TextRange.m750getLengthimpl(textFieldSelectionManager.getValue$foundation_release().selection) == textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length()) {
            }
            if (textFieldSelectionManager.getEditable()) {
            }
            MenuItemsAvailability.Companion companion2 = MenuItemsAvailability.Companion;
            return MenuItemsAvailability.m200boximpl((!z2 ? 2 : 0) | i2 | (!z4 ? 4 : 0) | (!z5 ? 8 : 0) | (z6 ? 16 : 0));
        }
        if (i8 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i3 = anonymousClass2.I$2;
        i4 = anonymousClass2.I$1;
        int i9 = anonymousClass2.I$0;
        textFieldSelectionManager2 = (TextFieldSelectionManager) anonymousClass2.L$0;
        ResultKt.throwOnFailure(obj);
        i6 = i9;
        ClipEntry clipEntry3 = (ClipEntry) obj;
        if (clipEntry3 != null) {
            int i10 = ClipboardUtils.$r8$clinit;
            if (clipEntry3.clipData.getDescription().hasMimeType("text/*")) {
                i2 = i3;
                textFieldSelectionManager = textFieldSelectionManager2;
                i = i4;
                z = true;
                r3 = i6;
                if (z) {
                    z2 = false;
                    z3 = r3;
                }
                if (i == 0) {
                }
                if (TextRange.m750getLengthimpl(textFieldSelectionManager.getValue$foundation_release().selection) == textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length()) {
                }
                if (textFieldSelectionManager.getEditable()) {
                }
                MenuItemsAvailability.Companion companion22 = MenuItemsAvailability.Companion;
                return MenuItemsAvailability.m200boximpl((!z2 ? 2 : 0) | i2 | (!z4 ? 4 : 0) | (!z5 ? 8 : 0) | (z6 ? 16 : 0));
            }
        }
        i2 = i3;
        textFieldSelectionManager = textFieldSelectionManager2;
        i = i4;
        i5 = i6;
        z = false;
        r3 = i5;
        if (z) {
        }
        if (i == 0) {
        }
        if (TextRange.m750getLengthimpl(textFieldSelectionManager.getValue$foundation_release().selection) == textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length()) {
        }
        if (textFieldSelectionManager.getEditable()) {
        }
        MenuItemsAvailability.Companion companion222 = MenuItemsAvailability.Companion;
        return MenuItemsAvailability.m200boximpl((!z2 ? 2 : 0) | i2 | (!z4 ? 4 : 0) | (!z5 ? 8 : 0) | (z6 ? 16 : 0));
    }
}
