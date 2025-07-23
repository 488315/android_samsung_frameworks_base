package androidx.compose.foundation.text;

import androidx.compose.foundation.text.TextFieldDelegate;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.PlatformTextInputService;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputService;
import androidx.compose.ui.text.input.TextInputSession;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CoreTextFieldKt$CoreTextField$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ImeOptions $imeOptions;
    final /* synthetic */ TextFieldSelectionManager $manager;
    final /* synthetic */ LegacyTextFieldState $state;
    final /* synthetic */ TextInputService $textInputService;
    final /* synthetic */ State<Boolean> $writeable$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoreTextFieldKt$CoreTextField$2$1(LegacyTextFieldState legacyTextFieldState, State<Boolean> state, TextInputService textInputService, TextFieldSelectionManager textFieldSelectionManager, ImeOptions imeOptions, Continuation continuation) {
        super(2, continuation);
        this.$state = legacyTextFieldState;
        this.$writeable$delegate = state;
        this.$textInputService = textInputService;
        this.$manager = textFieldSelectionManager;
        this.$imeOptions = imeOptions;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CoreTextFieldKt$CoreTextField$2$1(this.$state, this.$writeable$delegate, this.$textInputService, this.$manager, this.$imeOptions, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CoreTextFieldKt$CoreTextField$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.Object, kotlin.Unit] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final State<Boolean> state = this.$writeable$delegate;
                SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new Function0() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$2$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Boolean bool = (Boolean) state.getValue();
                        bool.booleanValue();
                        return bool;
                    }
                });
                final LegacyTextFieldState legacyTextFieldState = this.$state;
                final TextInputService textInputService = this.$textInputService;
                final TextFieldSelectionManager textFieldSelectionManager = this.$manager;
                final ImeOptions imeOptions = this.$imeOptions;
                FlowCollector flowCollector = new FlowCollector() { // from class: androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$2$1.2
                    /* JADX WARN: Type inference failed for: r6v3, types: [T, androidx.compose.ui.text.input.TextInputSession, java.lang.Object] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        LegacyTextFieldState legacyTextFieldState2 = LegacyTextFieldState.this;
                        if (booleanValue && legacyTextFieldState2.getHasFocus()) {
                            TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                            TextFieldValue value$foundation_release = textFieldSelectionManager2.getValue$foundation_release();
                            OffsetMapping offsetMapping = textFieldSelectionManager2.offsetMapping;
                            TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
                            Function1 function1 = legacyTextFieldState2.onValueChange;
                            Function1 function12 = legacyTextFieldState2.onImeActionPerformed;
                            companion.getClass();
                            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                            TextFieldDelegate$Companion$restartInput$1 textFieldDelegate$Companion$restartInput$1 = new TextFieldDelegate$Companion$restartInput$1(legacyTextFieldState2.processor, function1, ref$ObjectRef);
                            TextInputService textInputService2 = textInputService;
                            PlatformTextInputService platformTextInputService = textInputService2.platformTextInputService;
                            platformTextInputService.startInput(value$foundation_release, imeOptions, textFieldDelegate$Companion$restartInput$1, function12);
                            ?? r6 = (T) new TextInputSession(textInputService2, platformTextInputService);
                            textInputService2._currentInputSession.set(r6);
                            ref$ObjectRef.element = r6;
                            legacyTextFieldState2.inputSession = r6;
                            CoreTextFieldKt.notifyFocusedRect(legacyTextFieldState2, value$foundation_release, offsetMapping);
                        } else {
                            CoreTextFieldKt.access$endInputSession(legacyTextFieldState2);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (snapshotFlow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CoreTextFieldKt.access$endInputSession(this.$state);
            this = Unit.INSTANCE;
            return this;
        } catch (Throwable th) {
            CoreTextFieldKt.access$endInputSession(this.$state);
            throw th;
        }
    }
}
