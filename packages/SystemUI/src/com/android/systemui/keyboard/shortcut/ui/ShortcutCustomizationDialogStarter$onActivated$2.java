package com.android.systemui.keyboard.shortcut.ui;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Window;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.Key_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor;
import com.android.systemui.keyboard.shortcut.shared.model.KeyCombination;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutCustomizerKt;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState;
import com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutCustomizationDialogStarter$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ShortcutCustomizationDialogStarter this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShortcutCustomizationDialogStarter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shortcutCustomizationDialogStarter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = shortcutCustomizationDialogStarter.viewModel.shortcutCustomizationUiState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter.onActivated.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ShortcutCustomizationUiState shortcutCustomizationUiState = (ShortcutCustomizationUiState) obj2;
                        boolean z = shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.AddShortcutDialog;
                        final ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter2 = ShortcutCustomizationDialogStarter.this;
                        if (z || (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.DeleteShortcutDialog) || (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.ResetShortcutDialog)) {
                            if (shortcutCustomizationDialogStarter2.dialog == null) {
                                ComponentSystemUIDialog create$default = SystemUIDialogFactoryExtKt.create$default(shortcutCustomizationDialogStarter2.dialogFactory, null, null, new ShortcutCustomizationDialogDelegate(), new ComposableLambdaImpl(-360329903, true, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1
                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        final int i2 = 0;
                                        final int i3 = 1;
                                        final SystemUIDialog systemUIDialog = (SystemUIDialog) obj3;
                                        Composer composer = (Composer) obj4;
                                        ((Number) obj5).intValue();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter.createDialog.<anonymous> (ShortcutCustomizationDialogStarter.kt:90)");
                                        }
                                        final ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter3 = ShortcutCustomizationDialogStarter.this;
                                        MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(shortcutCustomizationDialogStarter3.viewModel.shortcutCustomizationUiState, composer);
                                        ComposerImpl composerImpl = (ComposerImpl) composer;
                                        Object rememberedValue = composerImpl.rememberedValue();
                                        Composer.Companion.getClass();
                                        Object obj6 = Composer.Companion.Empty;
                                        if (rememberedValue == obj6) {
                                            rememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                                            composerImpl.updateRememberedValue(rememberedValue);
                                        }
                                        final CoroutineScope coroutineScope = (CoroutineScope) rememberedValue;
                                        ShortcutCustomizationUiState shortcutCustomizationUiState2 = (ShortcutCustomizationUiState) collectAsStateWithLifecycle.getValue();
                                        Dp.Companion companion = Dp.Companion;
                                        Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(SizeKt.wrapContentHeight$default(SizeKt.m143width3ABfNKs(Modifier.Companion, 364), 3), 0.0f, 24, 1);
                                        composerImpl.startReplaceGroup(2026842643);
                                        boolean changedInstance = composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                        Object rememberedValue2 = composerImpl.rememberedValue();
                                        if (changedInstance || rememberedValue2 == obj6) {
                                            rememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$1$1
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo779invoke(Object obj7) {
                                                    KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj7).nativeKeyEvent;
                                                    ShortcutCustomizationViewModel shortcutCustomizationViewModel = ShortcutCustomizationDialogStarter.this.viewModel;
                                                    shortcutCustomizationViewModel.getClass();
                                                    List list = ShortcutCustomizationViewModel.SUPPORTED_MODIFIERS;
                                                    boolean z2 = false;
                                                    if (!list.contains(Key.m575boximpl(KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent)))) {
                                                        if (keyEvent.isMetaPressed()) {
                                                            int m579getTypeZmokQxo = KeyEvent_androidKt.m579getTypeZmokQxo(keyEvent);
                                                            KeyEventType.Companion.getClass();
                                                            if (m579getTypeZmokQxo == KeyEventType.KeyDown) {
                                                                shortcutCustomizationViewModel.keyDownEventCache = keyEvent;
                                                                z2 = true;
                                                            }
                                                        }
                                                        int m579getTypeZmokQxo2 = KeyEvent_androidKt.m579getTypeZmokQxo(keyEvent);
                                                        KeyEventType.Companion.getClass();
                                                        if (m579getTypeZmokQxo2 == KeyEventType.KeyUp) {
                                                            long Key = Key_androidKt.Key(keyEvent.getKeyCode());
                                                            KeyEvent keyEvent2 = shortcutCustomizationViewModel.keyDownEventCache;
                                                            Key m575boximpl = keyEvent2 != null ? Key.m575boximpl(Key_androidKt.Key(keyEvent2.getKeyCode())) : null;
                                                            if (m575boximpl != null && Key == m575boximpl.keyCode) {
                                                                KeyEvent keyEvent3 = shortcutCustomizationViewModel.keyDownEventCache;
                                                                keyEvent3.getClass();
                                                                shortcutCustomizationViewModel.shortcutCustomizationInteractor.customShortcutRepository._selectedKeyCombination.setValue(new KeyCombination(keyEvent3.getModifiers(), !list.contains(Key.m575boximpl(KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent3))) ? Integer.valueOf((int) (Key_androidKt.Key(keyEvent3.getKeyCode()) >> 32)) : null));
                                                                shortcutCustomizationViewModel.keyDownEventCache = null;
                                                                z2 = true;
                                                            }
                                                        }
                                                    }
                                                    return Boolean.valueOf(z2);
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue2);
                                        }
                                        Function1 function1 = (Function1) rememberedValue2;
                                        composerImpl.end(false);
                                        composerImpl.startReplaceGroup(2026846257);
                                        boolean changedInstance2 = composerImpl.changedInstance(systemUIDialog);
                                        Object rememberedValue3 = composerImpl.rememberedValue();
                                        if (changedInstance2 || rememberedValue3 == obj6) {
                                            rememberedValue3 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i2) {
                                                        case 0:
                                                            ((SystemUIDialog) systemUIDialog).dismiss();
                                                            break;
                                                        default:
                                                            ((ShortcutCustomizationDialogStarter) systemUIDialog).viewModel.shortcutCustomizationInteractor.customShortcutRepository._selectedKeyCombination.setValue(null);
                                                            break;
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue3);
                                        }
                                        Function0 function0 = (Function0) rememberedValue3;
                                        composerImpl.end(false);
                                        composerImpl.startReplaceGroup(2026848244);
                                        boolean changedInstance3 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                        Object rememberedValue4 = composerImpl.rememberedValue();
                                        if (changedInstance3 || rememberedValue4 == obj6) {
                                            rememberedValue4 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i2) {
                                                        case 0:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$3$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        case 1:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$4$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        default:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$5$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue4);
                                        }
                                        Function0 function02 = (Function0) rememberedValue4;
                                        composerImpl.end(false);
                                        composerImpl.startReplaceGroup(2026851473);
                                        boolean changedInstance4 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                        Object rememberedValue5 = composerImpl.rememberedValue();
                                        if (changedInstance4 || rememberedValue5 == obj6) {
                                            rememberedValue5 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i3) {
                                                        case 0:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$3$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        case 1:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$4$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        default:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$5$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue5);
                                        }
                                        Function0 function03 = (Function0) rememberedValue5;
                                        composerImpl.end(false);
                                        composerImpl.startReplaceGroup(2026856546);
                                        boolean changedInstance5 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                        Object rememberedValue6 = composerImpl.rememberedValue();
                                        if (changedInstance5 || rememberedValue6 == obj6) {
                                            final int i4 = 2;
                                            rememberedValue6 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i4) {
                                                        case 0:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$3$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        case 1:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$4$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                        default:
                                                            BuildersKt.launch$default(coroutineScope, null, null, new ShortcutCustomizationDialogStarter$createDialog$1$5$1$1(shortcutCustomizationDialogStarter3, null), 3);
                                                            break;
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue6);
                                        }
                                        Function0 function04 = (Function0) rememberedValue6;
                                        composerImpl.end(false);
                                        composerImpl.startReplaceGroup(2026861320);
                                        boolean changedInstance6 = composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                        Object rememberedValue7 = composerImpl.rememberedValue();
                                        if (changedInstance6 || rememberedValue7 == obj6) {
                                            rememberedValue7 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    switch (i3) {
                                                        case 0:
                                                            ((SystemUIDialog) shortcutCustomizationDialogStarter3).dismiss();
                                                            break;
                                                        default:
                                                            ((ShortcutCustomizationDialogStarter) shortcutCustomizationDialogStarter3).viewModel.shortcutCustomizationInteractor.customShortcutRepository._selectedKeyCombination.setValue(null);
                                                            break;
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(rememberedValue7);
                                        }
                                        composerImpl.end(false);
                                        ShortcutCustomizerKt.ShortcutCustomizationDialog(shortcutCustomizationUiState2, m126paddingVpY3zN4$default, function1, function0, function02, function03, function04, (Function0) rememberedValue7, composerImpl, 48);
                                        ShortcutCustomizationUiState shortcutCustomizationUiState3 = (ShortcutCustomizationUiState) collectAsStateWithLifecycle.getValue();
                                        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$setDialogProperties$1
                                            @Override // android.content.DialogInterface.OnDismissListener
                                            public final void onDismiss(DialogInterface dialogInterface) {
                                                ShortcutCustomizationViewModel shortcutCustomizationViewModel = ShortcutCustomizationDialogStarter.this.viewModel;
                                                shortcutCustomizationViewModel._shortcutCustomizationUiState.setValue(ShortcutCustomizationUiState.Inactive.INSTANCE);
                                                ShortcutCustomizationInteractor shortcutCustomizationInteractor = shortcutCustomizationViewModel.shortcutCustomizationInteractor;
                                                shortcutCustomizationInteractor.customShortcutRepository._shortcutBeingCustomized.setValue(null);
                                                shortcutCustomizationInteractor.customShortcutRepository._selectedKeyCombination.setValue(null);
                                            }
                                        });
                                        boolean z2 = shortcutCustomizationUiState3 instanceof ShortcutCustomizationUiState.AddShortcutDialog;
                                        String string = z2 ? ((ShortcutCustomizationUiState.AddShortcutDialog) shortcutCustomizationUiState3).shortcutLabel : shortcutCustomizationUiState3 instanceof ShortcutCustomizationUiState.DeleteShortcutDialog ? shortcutCustomizationDialogStarter3.resources.getString(R.string.shortcut_customize_mode_remove_shortcut_dialog_title) : shortcutCustomizationDialogStarter3.resources.getString(R.string.shortcut_customize_mode_reset_shortcut_dialog_title);
                                        systemUIDialog.setTitle(string + ". " + shortcutCustomizationDialogStarter3.resources.getString(z2 ? R.string.shortcut_customize_mode_add_shortcut_description : shortcutCustomizationUiState3 instanceof ShortcutCustomizationUiState.DeleteShortcutDialog ? R.string.shortcut_customize_mode_remove_shortcut_description : R.string.shortcut_customize_mode_reset_shortcut_description));
                                        Window window = systemUIDialog.getWindow();
                                        if (window != null) {
                                            window.addPrivateFlags(8388608);
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }), 15);
                                create$default.show();
                                shortcutCustomizationDialogStarter2.dialog = create$default;
                            }
                        } else {
                            if (!(shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.Inactive)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            ComponentSystemUIDialog componentSystemUIDialog = shortcutCustomizationDialogStarter2.dialog;
                            if (componentSystemUIDialog != null) {
                                componentSystemUIDialog.dismiss();
                            }
                            shortcutCustomizationDialogStarter2.dialog = null;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShortcutCustomizationDialogStarter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shortcutCustomizationDialogStarter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ShortcutCustomizationViewModel shortcutCustomizationViewModel = this.this$0.viewModel;
                this.label = 1;
                if (shortcutCustomizationViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutCustomizationDialogStarter$onActivated$2(ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutCustomizationDialogStarter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShortcutCustomizationDialogStarter$onActivated$2 shortcutCustomizationDialogStarter$onActivated$2 = new ShortcutCustomizationDialogStarter$onActivated$2(this.this$0, continuation);
        shortcutCustomizationDialogStarter$onActivated$2.L$0 = obj;
        return shortcutCustomizationDialogStarter$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutCustomizationDialogStarter$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
    }
}
