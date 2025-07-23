package androidx.compose.foundation.text;

import androidx.compose.foundation.relocation.BringIntoViewRequester;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BringIntoViewRequester $bringIntoViewRequester;
    final /* synthetic */ TextLayoutResultProxy $layoutResult;
    final /* synthetic */ OffsetMapping $offsetMapping;
    final /* synthetic */ LegacyTextFieldState $state;
    final /* synthetic */ TextFieldValue $value;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1(BringIntoViewRequester bringIntoViewRequester, TextFieldValue textFieldValue, LegacyTextFieldState legacyTextFieldState, TextLayoutResultProxy textLayoutResultProxy, OffsetMapping offsetMapping, Continuation continuation) {
        super(2, continuation);
        this.$bringIntoViewRequester = bringIntoViewRequester;
        this.$value = textFieldValue;
        this.$state = legacyTextFieldState;
        this.$layoutResult = textLayoutResultProxy;
        this.$offsetMapping = offsetMapping;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1(this.$bringIntoViewRequester, this.$value, this.$state, this.$layoutResult, this.$offsetMapping, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CoreTextFieldKt$CoreTextField$focusModifier$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        long computeSizeForDefaultText;
        Rect rect;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BringIntoViewRequester bringIntoViewRequester = this.$bringIntoViewRequester;
            TextFieldValue textFieldValue = this.$value;
            TextDelegate textDelegate = this.$state.textDelegate;
            TextLayoutResult textLayoutResult = this.$layoutResult.value;
            OffsetMapping offsetMapping = this.$offsetMapping;
            this.label = 1;
            int originalToTransformed = offsetMapping.originalToTransformed(TextRange.m749getMaximpl(textFieldValue.selection));
            if (originalToTransformed < textLayoutResult.layoutInput.text.text.length()) {
                rect = textLayoutResult.getBoundingBox(originalToTransformed);
            } else if (originalToTransformed != 0) {
                rect = textLayoutResult.getBoundingBox(originalToTransformed - 1);
            } else {
                computeSizeForDefaultText = TextFieldDelegateKt.computeSizeForDefaultText(textDelegate.style, textDelegate.density, textDelegate.fontFamilyResolver, TextFieldDelegateKt.EmptyTextReplacement, 1);
                rect = new Rect(0.0f, 0.0f, 1.0f, (int) (computeSizeForDefaultText & 4294967295L));
            }
            Object bringIntoView = bringIntoViewRequester.bringIntoView(rect, this);
            if (bringIntoView != coroutineSingletons) {
                bringIntoView = Unit.INSTANCE;
            }
            if (bringIntoView == coroutineSingletons) {
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
