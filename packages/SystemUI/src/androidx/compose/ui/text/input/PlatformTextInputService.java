package androidx.compose.ui.text.input;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextLayoutResult;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PlatformTextInputService {
    void hideSoftwareKeyboard();

    void showSoftwareKeyboard();

    default void startInput() {
    }

    void startInput(TextFieldValue textFieldValue, ImeOptions imeOptions, Function1 function1, Function1 function12);

    void stopInput();

    void updateState(TextFieldValue textFieldValue, TextFieldValue textFieldValue2);

    default void notifyFocusedRect(Rect rect) {
    }

    default void updateTextLayoutResult(TextFieldValue textFieldValue, OffsetMapping offsetMapping, TextLayoutResult textLayoutResult, Function1 function1, Rect rect, Rect rect2) {
    }
}
