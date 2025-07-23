package androidx.compose.foundation.text.input.internal;

import android.view.inputmethod.CursorAnchorInfo;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LegacyCursorAnchorInfoController {
    public Rect decorationBoxBounds;
    public boolean hasPendingImmediateRequest;
    public boolean includeCharacterBounds;
    public boolean includeEditorBounds;
    public boolean includeInsertionMarker;
    public boolean includeLineBounds;
    public Rect innerTextFieldBounds;
    public final InputMethodManager inputMethodManager;
    public final Function1 localToScreen;
    public boolean monitorEnabled;
    public OffsetMapping offsetMapping;
    public TextFieldValue textFieldValue;
    public TextLayoutResult textLayoutResult;
    public final Object lock = new Object();
    public final CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
    public final float[] matrix = Matrix.m481constructorimpl$default();
    public final android.graphics.Matrix androidMatrix = new android.graphics.Matrix();

    public LegacyCursorAnchorInfoController(Function1 function1, InputMethodManager inputMethodManager) {
        this.localToScreen = function1;
        this.inputMethodManager = inputMethodManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x015f, code lost:
    
        if (androidx.compose.foundation.text.input.internal.LegacyCursorAnchorInfoBuilder_androidKt.containsInclusive(r13, r8.right, r8.bottom) == false) goto L51;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCursorAnchorInfo() {
        /*
            Method dump skipped, instructions count: 495
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.LegacyCursorAnchorInfoController.updateCursorAnchorInfo():void");
    }
}
