package androidx.compose.foundation.text.input.internal;

import android.view.inputmethod.HandwritingGesture;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextGranularity;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.DeleteSurroundingTextCommand;
import androidx.compose.ui.text.input.EditCommand;
import androidx.compose.ui.text.input.SetSelectionCommand;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HandwritingGestureApi34 {
    public static final HandwritingGestureApi34 INSTANCE = new HandwritingGestureApi34();

    private HandwritingGestureApi34() {
    }

    public static int fallbackOnLegacyTextField(HandwritingGesture handwritingGesture, Function1 function1) {
        String fallbackText = handwritingGesture.getFallbackText();
        if (fallbackText == null) {
            return 3;
        }
        ((RecordingInputConnection$performHandwritingGesture$1) function1).mo779invoke(new CommitTextCommand(fallbackText, 1));
        return 5;
    }

    /* renamed from: performDeletionOnLegacyTextField-vJH6DeI, reason: not valid java name */
    public static void m213performDeletionOnLegacyTextFieldvJH6DeI(long j, AnnotatedString annotatedString, boolean z, Function1 function1) {
        if (z) {
            TextRange.Companion companion = TextRange.Companion;
            int i = (int) (j >> 32);
            int i2 = (int) (j & 4294967295L);
            int codePointBefore = i > 0 ? Character.codePointBefore(annotatedString, i) : 10;
            int codePointAt = i2 < annotatedString.text.length() ? Character.codePointAt(annotatedString, i2) : 10;
            if (HandwritingGesture_androidKt.isWhitespaceExceptNewline(codePointBefore) && (HandwritingGesture_androidKt.isWhitespace(codePointAt) || HandwritingGesture_androidKt.isPunctuation(codePointAt))) {
                do {
                    i -= Character.charCount(codePointBefore);
                    if (i == 0) {
                        break;
                    } else {
                        codePointBefore = Character.codePointBefore(annotatedString, i);
                    }
                } while (HandwritingGesture_androidKt.isWhitespaceExceptNewline(codePointBefore));
                j = TextRangeKt.TextRange(i, i2);
            } else if (HandwritingGesture_androidKt.isWhitespaceExceptNewline(codePointAt) && (HandwritingGesture_androidKt.isWhitespace(codePointBefore) || HandwritingGesture_androidKt.isPunctuation(codePointBefore))) {
                do {
                    i2 += Character.charCount(codePointAt);
                    if (i2 == annotatedString.text.length()) {
                        break;
                    } else {
                        codePointAt = Character.codePointAt(annotatedString, i2);
                    }
                } while (HandwritingGesture_androidKt.isWhitespaceExceptNewline(codePointAt));
                j = TextRangeKt.TextRange(i, i2);
            }
        }
        TextRange.Companion companion2 = TextRange.Companion;
        int i3 = (int) (4294967295L & j);
        ((RecordingInputConnection$performHandwritingGesture$1) function1).mo779invoke(new HandwritingGesture_androidKt$compoundEditCommand$1(new EditCommand[]{new SetSelectionCommand(i3, i3), new DeleteSurroundingTextCommand(TextRange.m748getLengthimpl(j), 0)}));
    }

    /* renamed from: toTextGranularity-NUwxegE, reason: not valid java name */
    public static int m214toTextGranularityNUwxegE(int i) {
        if (i == 1) {
            TextGranularity.Companion.getClass();
            return TextGranularity.Word;
        }
        if (i != 2) {
            TextGranularity.Companion.getClass();
            return 0;
        }
        TextGranularity.Companion.getClass();
        return 0;
    }
}
