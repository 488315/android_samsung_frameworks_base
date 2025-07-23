package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.text.Handle;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldSelectionManagerKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Handle.values().length];
            try {
                iArr[Handle.Cursor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Handle.SelectionStart.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Handle.SelectionEnd.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x007a, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009e, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0143, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:61:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TextFieldSelectionHandle(final boolean r18, final androidx.compose.ui.text.style.ResolvedTextDirection r19, final androidx.compose.foundation.text.selection.TextFieldSelectionManager r20, androidx.compose.runtime.Composer r21, final int r22) {
        /*
            Method dump skipped, instructions count: 378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldSelectionManagerKt.TextFieldSelectionHandle(boolean, androidx.compose.ui.text.style.ResolvedTextDirection, androidx.compose.foundation.text.selection.TextFieldSelectionManager, androidx.compose.runtime.Composer, int):void");
    }

    public static final boolean isSelectionHandleInVisibleBound(TextFieldSelectionManager textFieldSelectionManager, boolean z) {
        LayoutCoordinates layoutCoordinates;
        LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
        if (legacyTextFieldState == null || (layoutCoordinates = legacyTextFieldState.getLayoutCoordinates()) == null) {
            return false;
        }
        Rect visibleBounds = SelectionManagerKt.visibleBounds(layoutCoordinates);
        long m240getHandlePositiontuRUvjQ$foundation_release = textFieldSelectionManager.m240getHandlePositiontuRUvjQ$foundation_release(z);
        float intBitsToFloat = Float.intBitsToFloat((int) (m240getHandlePositiontuRUvjQ$foundation_release >> 32));
        if (visibleBounds.left > intBitsToFloat || intBitsToFloat > visibleBounds.right) {
            return false;
        }
        float intBitsToFloat2 = Float.intBitsToFloat((int) (m240getHandlePositiontuRUvjQ$foundation_release & 4294967295L));
        return visibleBounds.top <= intBitsToFloat2 && intBitsToFloat2 <= visibleBounds.bottom;
    }
}
