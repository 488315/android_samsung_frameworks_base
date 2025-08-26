package androidx.compose.foundation.text.input.internal;

import android.view.inputmethod.HandwritingGesture;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextGranularity;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.DeleteSurroundingTextCommand;
import androidx.compose.ui.text.input.EditCommand;
import androidx.compose.ui.text.input.SetSelectionCommand;
import kotlin.jvm.functions.Function1;

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
        ((RecordingInputConnection.AnonymousClass1) function1).mo781invoke(new CommitTextCommand(fallbackText, 1));
        return 5;
    }

    /* renamed from: performDeletionOnLegacyTextField-vJH6DeI, reason: not valid java name */
    public static void m214performDeletionOnLegacyTextFieldvJH6DeI(long j, AnnotatedString annotatedString, boolean z, Function1 function1) {
        if (z) {
            TextRange.Companion companion = TextRange.Companion;
            int iCharCount = (int) (j >> 32);
            int iCharCount2 = (int) (j & 4294967295L);
            int iCodePointBefore = iCharCount > 0 ? Character.codePointBefore(annotatedString, iCharCount) : 10;
            int iCodePointAt = iCharCount2 < annotatedString.text.length() ? Character.codePointAt(annotatedString, iCharCount2) : 10;
            if (HandwritingGesture_androidKt.isWhitespaceExceptNewline(iCodePointBefore) && (HandwritingGesture_androidKt.isWhitespace(iCodePointAt) || HandwritingGesture_androidKt.isPunctuation(iCodePointAt))) {
                do {
                    iCharCount -= Character.charCount(iCodePointBefore);
                    if (iCharCount == 0) {
                        break;
                    } else {
                        iCodePointBefore = Character.codePointBefore(annotatedString, iCharCount);
                    }
                } while (HandwritingGesture_androidKt.isWhitespaceExceptNewline(iCodePointBefore));
                j = TextRangeKt.TextRange(iCharCount, iCharCount2);
            } else if (HandwritingGesture_androidKt.isWhitespaceExceptNewline(iCodePointAt) && (HandwritingGesture_androidKt.isWhitespace(iCodePointBefore) || HandwritingGesture_androidKt.isPunctuation(iCodePointBefore))) {
                do {
                    iCharCount2 += Character.charCount(iCodePointAt);
                    if (iCharCount2 == annotatedString.text.length()) {
                        break;
                    } else {
                        iCodePointAt = Character.codePointAt(annotatedString, iCharCount2);
                    }
                } while (HandwritingGesture_androidKt.isWhitespaceExceptNewline(iCodePointAt));
                j = TextRangeKt.TextRange(iCharCount, iCharCount2);
            }
        }
        TextRange.Companion companion2 = TextRange.Companion;
        int i = (int) (4294967295L & j);
        ((RecordingInputConnection.AnonymousClass1) function1).mo781invoke(new HandwritingGesture_androidKt$compoundEditCommand$1(new EditCommand[]{new SetSelectionCommand(i, i), new DeleteSurroundingTextCommand(TextRange.m750getLengthimpl(j), 0)}));
    }

    /* renamed from: toTextGranularity-NUwxegE, reason: not valid java name */
    public static int m215toTextGranularityNUwxegE(int i) {
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
