package com.android.systemui.keyboard.shortcut.ui;

import android.content.DialogInterface;
import android.content.res.Resources;
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
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
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
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class ShortcutCustomizationDialogStarter extends ExclusiveActivatable {
    public ComponentSystemUIDialog dialog;
    public final SystemUIDialogFactory dialogFactory;
    public final Resources resources;
    public final ShortcutCustomizationViewModel viewModel;

    public interface Factory {
        ShortcutCustomizationDialogStarter create();
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutCustomizationDialogStarter.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

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
                            final ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter2 = shortcutCustomizationDialogStarter;
                            if (z || (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.DeleteShortcutDialog) || (shortcutCustomizationUiState instanceof ShortcutCustomizationUiState.ResetShortcutDialog)) {
                                if (shortcutCustomizationDialogStarter2.dialog == null) {
                                    ComponentSystemUIDialog componentSystemUIDialogCreate$default = SystemUIDialogFactoryExtKt.create$default(shortcutCustomizationDialogStarter2.dialogFactory, null, null, new ShortcutCustomizationDialogDelegate(), new ComposableLambdaImpl(-360329903, true, new Function3() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1
                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj3, Object obj4, Object obj5) throws Resources.NotFoundException {
                                            final int i2 = 0;
                                            final int i3 = 1;
                                            final SystemUIDialog systemUIDialog = (SystemUIDialog) obj3;
                                            Composer composer = (Composer) obj4;
                                            ((Number) obj5).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter.createDialog.<anonymous> (ShortcutCustomizationDialogStarter.kt:90)");
                                            }
                                            final ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter3 = shortcutCustomizationDialogStarter2;
                                            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(shortcutCustomizationDialogStarter3.viewModel.shortcutCustomizationUiState, composer);
                                            ComposerImpl composerImpl = (ComposerImpl) composer;
                                            Object objRememberedValue = composerImpl.rememberedValue();
                                            Composer.Companion.getClass();
                                            Object obj6 = Composer.Companion.Empty;
                                            if (objRememberedValue == obj6) {
                                                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                                                composerImpl.updateRememberedValue(objRememberedValue);
                                            }
                                            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
                                            ShortcutCustomizationUiState shortcutCustomizationUiState2 = (ShortcutCustomizationUiState) mutableStateCollectAsStateWithLifecycle.getValue();
                                            Dp.Companion companion = Dp.Companion;
                                            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.wrapContentHeight$default(SizeKt.m144width3ABfNKs(Modifier.Companion, 364), 3), 0.0f, 24, 1);
                                            composerImpl.startReplaceGroup(2026842643);
                                            boolean zChangedInstance = composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                            Object objRememberedValue2 = composerImpl.rememberedValue();
                                            if (zChangedInstance || objRememberedValue2 == obj6) {
                                                objRememberedValue2 = new Function1() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$1$1
                                                    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object mo781invoke(Object obj7) {
                                                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj7).nativeKeyEvent;
                                                        ShortcutCustomizationViewModel shortcutCustomizationViewModel = shortcutCustomizationDialogStarter3.viewModel;
                                                        shortcutCustomizationViewModel.getClass();
                                                        List list = ShortcutCustomizationViewModel.SUPPORTED_MODIFIERS;
                                                        boolean z2 = false;
                                                        if (!list.contains(Key.m577boximpl(KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent)))) {
                                                            if (keyEvent.isMetaPressed()) {
                                                                int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                KeyEventType.Companion.getClass();
                                                                if (iM581getTypeZmokQxo == KeyEventType.KeyDown) {
                                                                    shortcutCustomizationViewModel.keyDownEventCache = keyEvent;
                                                                } else {
                                                                    int iM581getTypeZmokQxo2 = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                                                    KeyEventType.Companion.getClass();
                                                                    if (iM581getTypeZmokQxo2 == KeyEventType.KeyUp) {
                                                                        long jKey = Key_androidKt.Key(keyEvent.getKeyCode());
                                                                        KeyEvent keyEvent2 = shortcutCustomizationViewModel.keyDownEventCache;
                                                                        Key keyM577boximpl = keyEvent2 != null ? Key.m577boximpl(Key_androidKt.Key(keyEvent2.getKeyCode())) : null;
                                                                        if (keyM577boximpl != null && jKey == keyM577boximpl.keyCode) {
                                                                            KeyEvent keyEvent3 = shortcutCustomizationViewModel.keyDownEventCache;
                                                                            keyEvent3.getClass();
                                                                            shortcutCustomizationViewModel.shortcutCustomizationInteractor.customShortcutRepository._selectedKeyCombination.setValue(new KeyCombination(keyEvent3.getModifiers(), !list.contains(Key.m577boximpl(KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent3))) ? Integer.valueOf((int) (Key_androidKt.Key(keyEvent3.getKeyCode()) >> 32)) : null));
                                                                            shortcutCustomizationViewModel.keyDownEventCache = null;
                                                                        }
                                                                    }
                                                                }
                                                                z2 = true;
                                                            }
                                                        }
                                                        return Boolean.valueOf(z2);
                                                    }
                                                };
                                                composerImpl.updateRememberedValue(objRememberedValue2);
                                            }
                                            Function1 function1 = (Function1) objRememberedValue2;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(2026846257);
                                            boolean zChangedInstance2 = composerImpl.changedInstance(systemUIDialog);
                                            Object objRememberedValue3 = composerImpl.rememberedValue();
                                            if (zChangedInstance2 || objRememberedValue3 == obj6) {
                                                objRememberedValue3 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda0
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
                                                composerImpl.updateRememberedValue(objRememberedValue3);
                                            }
                                            Function0 function0 = (Function0) objRememberedValue3;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(2026848244);
                                            boolean zChangedInstance3 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                            Object objRememberedValue4 = composerImpl.rememberedValue();
                                            if (zChangedInstance3 || objRememberedValue4 == obj6) {
                                                objRememberedValue4 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
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
                                                composerImpl.updateRememberedValue(objRememberedValue4);
                                            }
                                            Function0 function02 = (Function0) objRememberedValue4;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(2026851473);
                                            boolean zChangedInstance4 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                            Object objRememberedValue5 = composerImpl.rememberedValue();
                                            if (zChangedInstance4 || objRememberedValue5 == obj6) {
                                                objRememberedValue5 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
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
                                                composerImpl.updateRememberedValue(objRememberedValue5);
                                            }
                                            Function0 function03 = (Function0) objRememberedValue5;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(2026856546);
                                            boolean zChangedInstance5 = composerImpl.changedInstance(coroutineScope) | composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                            Object objRememberedValue6 = composerImpl.rememberedValue();
                                            if (zChangedInstance5 || objRememberedValue6 == obj6) {
                                                final int i4 = 2;
                                                objRememberedValue6 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda1
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
                                                composerImpl.updateRememberedValue(objRememberedValue6);
                                            }
                                            Function0 function04 = (Function0) objRememberedValue6;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(2026861320);
                                            boolean zChangedInstance6 = composerImpl.changedInstance(shortcutCustomizationDialogStarter3);
                                            Object objRememberedValue7 = composerImpl.rememberedValue();
                                            if (zChangedInstance6 || objRememberedValue7 == obj6) {
                                                objRememberedValue7 = new Function0() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$createDialog$1$$ExternalSyntheticLambda0
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
                                                composerImpl.updateRememberedValue(objRememberedValue7);
                                            }
                                            composerImpl.end(false);
                                            ShortcutCustomizerKt.ShortcutCustomizationDialog(shortcutCustomizationUiState2, modifierM127paddingVpY3zN4$default, function1, function0, function02, function03, function04, (Function0) objRememberedValue7, composerImpl, 48);
                                            ShortcutCustomizationUiState shortcutCustomizationUiState3 = (ShortcutCustomizationUiState) mutableStateCollectAsStateWithLifecycle.getValue();
                                            systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$setDialogProperties$1
                                                @Override // android.content.DialogInterface.OnDismissListener
                                                public final void onDismiss(DialogInterface dialogInterface) {
                                                    ShortcutCustomizationViewModel shortcutCustomizationViewModel = shortcutCustomizationDialogStarter3.viewModel;
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
                                    componentSystemUIDialogCreate$default.show();
                                    shortcutCustomizationDialogStarter2.dialog = componentSystemUIDialogCreate$default;
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

        /* renamed from: com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogStarter$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C02112 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ShortcutCustomizationDialogStarter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02112(ShortcutCustomizationDialogStarter shortcutCustomizationDialogStarter, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shortcutCustomizationDialogStarter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02112(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02112) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ShortcutCustomizationDialogStarter.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(ShortcutCustomizationDialogStarter.this, null), 3);
            return BuildersKt.launch$default(coroutineScope, null, null, new C02112(ShortcutCustomizationDialogStarter.this, null), 3);
        }
    }

    public ShortcutCustomizationDialogStarter(ShortcutCustomizationViewModel.Factory factory, SystemUIDialogFactory systemUIDialogFactory, Resources resources) {
        this.dialogFactory = systemUIDialogFactory;
        this.resources = resources;
        this.viewModel = factory.create();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        anonymousClass1.label = 2;
    }
}
