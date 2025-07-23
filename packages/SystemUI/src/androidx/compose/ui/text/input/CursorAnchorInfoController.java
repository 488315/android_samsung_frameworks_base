package androidx.compose.ui.text.input;

import android.view.inputmethod.CursorAnchorInfo;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import androidx.compose.ui.text.TextLayoutResult;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CursorAnchorInfoController {
    public Rect decorationBoxBounds;
    public boolean hasPendingImmediateRequest;
    public boolean includeCharacterBounds;
    public boolean includeEditorBounds;
    public boolean includeInsertionMarker;
    public boolean includeLineBounds;
    public Rect innerTextFieldBounds;
    public final InputMethodManager inputMethodManager;
    public boolean monitorEnabled;
    public OffsetMapping offsetMapping;
    public final MatrixPositionCalculator rootPositionCalculator;
    public TextFieldValue textFieldValue;
    public TextLayoutResult textLayoutResult;
    public final Object lock = new Object();
    public Lambda textFieldToRootTransform = new Function1() { // from class: androidx.compose.ui.text.input.CursorAnchorInfoController$textFieldToRootTransform$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* synthetic */ Object mo779invoke(Object obj) {
            float[] fArr = ((Matrix) obj).values;
            return Unit.INSTANCE;
        }
    };
    public final CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
    public final float[] matrix = Matrix.m481constructorimpl$default();
    public final android.graphics.Matrix androidMatrix = new android.graphics.Matrix();

    public CursorAnchorInfoController(MatrixPositionCalculator matrixPositionCalculator, InputMethodManager inputMethodManager) {
        this.rootPositionCalculator = matrixPositionCalculator;
        this.inputMethodManager = inputMethodManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x013f, code lost:
    
        if (androidx.compose.ui.text.input.CursorAnchorInfoBuilder_androidKt.containsInclusive(r13, r8.right, r8.bottom) == false) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x018e  */
    /* JADX WARN: Type inference failed for: r3v4, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCursorAnchorInfo() {
        /*
            Method dump skipped, instructions count: 467
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.CursorAnchorInfoController.updateCursorAnchorInfo():void");
    }
}
