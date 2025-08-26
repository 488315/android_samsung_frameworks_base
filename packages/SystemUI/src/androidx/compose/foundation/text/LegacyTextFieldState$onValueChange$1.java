package androidx.compose.foundation.text;

import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeOwner;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.TextFieldValue;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class LegacyTextFieldState$onValueChange$1 extends Lambda implements Function1 {
    final /* synthetic */ LegacyTextFieldState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyTextFieldState$onValueChange$1(LegacyTextFieldState legacyTextFieldState) {
        super(1);
        this.this$0 = legacyTextFieldState;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        TextFieldValue textFieldValue = (TextFieldValue) obj;
        String str = textFieldValue.annotatedString.text;
        AnnotatedString annotatedString = this.this$0.untransformedText;
        if (!Intrinsics.areEqual(str, annotatedString != null ? annotatedString.text : null)) {
            ((SnapshotMutableStateImpl) this.this$0.handleState$delegate).setValue(HandleState.None);
            if (((Boolean) ((SnapshotMutableStateImpl) this.this$0.justAutofilled$delegate).getValue()).booleanValue()) {
                ((SnapshotMutableStateImpl) this.this$0.justAutofilled$delegate).setValue(Boolean.FALSE);
            } else {
                ((SnapshotMutableStateImpl) this.this$0.autofillHighlightOn$delegate).setValue(Boolean.FALSE);
            }
        }
        LegacyTextFieldState legacyTextFieldState = this.this$0;
        TextRange.Companion.getClass();
        long j = TextRange.Zero;
        ((SnapshotMutableStateImpl) legacyTextFieldState.selectionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j));
        ((SnapshotMutableStateImpl) this.this$0.deletionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j));
        this.this$0.onValueChangeOriginal.mo781invoke(textFieldValue);
        RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) this.this$0.recomposeScope;
        RecomposeScopeOwner recomposeScopeOwner = recomposeScopeImpl.owner;
        if (recomposeScopeOwner != null) {
            recomposeScopeOwner.invalidate(recomposeScopeImpl, null);
        }
        return Unit.INSTANCE;
    }
}
