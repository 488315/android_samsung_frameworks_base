package androidx.compose.foundation.text;

import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.platform.DelegatingSoftwareKeyboardController;
import androidx.compose.ui.platform.SoftwareKeyboardController;
import androidx.compose.ui.text.input.ImeAction;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LegacyTextFieldState$onImeActionPerformed$1 extends Lambda implements Function1 {
    final /* synthetic */ LegacyTextFieldState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyTextFieldState$onImeActionPerformed$1(LegacyTextFieldState legacyTextFieldState) {
        super(1);
        this.this$0 = legacyTextFieldState;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Function1 function1;
        Unit unit;
        FocusManager focusManager;
        int i = ((ImeAction) obj).value;
        KeyboardActionRunner keyboardActionRunner = this.this$0.keyboardActionRunner;
        keyboardActionRunner.getClass();
        ImeAction.Companion.getClass();
        int i2 = ImeAction.Done;
        if (i == i2) {
            KeyboardActions keyboardActions = keyboardActionRunner.keyboardActions;
            if (keyboardActions == null) {
                keyboardActions = null;
            }
            function1 = keyboardActions.onDone;
        } else if (i == ImeAction.Go) {
            KeyboardActions keyboardActions2 = keyboardActionRunner.keyboardActions;
            if (keyboardActions2 == null) {
                keyboardActions2 = null;
            }
            function1 = keyboardActions2.onGo;
        } else if (i == ImeAction.Next) {
            KeyboardActions keyboardActions3 = keyboardActionRunner.keyboardActions;
            if (keyboardActions3 == null) {
                keyboardActions3 = null;
            }
            function1 = keyboardActions3.onNext;
        } else if (i == ImeAction.Previous) {
            KeyboardActions keyboardActions4 = keyboardActionRunner.keyboardActions;
            if (keyboardActions4 == null) {
                keyboardActions4 = null;
            }
            function1 = keyboardActions4.onPrevious;
        } else if (i == ImeAction.Search) {
            KeyboardActions keyboardActions5 = keyboardActionRunner.keyboardActions;
            if (keyboardActions5 == null) {
                keyboardActions5 = null;
            }
            function1 = keyboardActions5.onSearch;
        } else if (i == ImeAction.Send) {
            KeyboardActions keyboardActions6 = keyboardActionRunner.keyboardActions;
            if (keyboardActions6 == null) {
                keyboardActions6 = null;
            }
            function1 = keyboardActions6.onSend;
        } else {
            if (i != ImeAction.Default && i != 0) {
                throw new IllegalStateException("invalid ImeAction");
            }
            function1 = null;
        }
        if (function1 != null) {
            function1.mo779invoke(keyboardActionRunner);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            if (i == ImeAction.Next) {
                FocusManager focusManager2 = keyboardActionRunner.focusManager;
                focusManager = focusManager2 != null ? focusManager2 : null;
                FocusDirection.Companion.getClass();
                ((FocusOwnerImpl) focusManager).m373moveFocus3ESFkO8(FocusDirection.Next);
            } else if (i == ImeAction.Previous) {
                FocusManager focusManager3 = keyboardActionRunner.focusManager;
                focusManager = focusManager3 != null ? focusManager3 : null;
                FocusDirection.Companion.getClass();
                ((FocusOwnerImpl) focusManager).m373moveFocus3ESFkO8(FocusDirection.Previous);
            } else if (i == i2) {
                SoftwareKeyboardController softwareKeyboardController = keyboardActionRunner.keyboardController;
                if (softwareKeyboardController != null) {
                    ((DelegatingSoftwareKeyboardController) softwareKeyboardController).hide();
                }
            } else if (i != ImeAction.Go) {
                ImeAction.Companion companion = ImeAction.Companion;
            }
        }
        return Unit.INSTANCE;
    }
}
