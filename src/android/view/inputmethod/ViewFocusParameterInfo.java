package android.view.inputmethod;

/* loaded from: classes4.dex */
final class ViewFocusParameterInfo {
    final EditorInfo mPreviousEditorInfo;
    final int mPreviousSoftInputMode;
    final int mPreviousStartInputFlags;
    final int mPreviousStartInputReason;
    final int mPreviousWindowFlags;

    ViewFocusParameterInfo(EditorInfo editorInfo, int i, int i2, int i3, int i4) {
        this.mPreviousEditorInfo = editorInfo;
        this.mPreviousStartInputFlags = i;
        this.mPreviousStartInputReason = i2;
        this.mPreviousSoftInputMode = i3;
        this.mPreviousWindowFlags = i4;
    }

    boolean sameAs(EditorInfo editorInfo, int i, int i2, int i3, int i4) {
        if (this.mPreviousStartInputFlags != i || this.mPreviousStartInputReason != i2 || this.mPreviousSoftInputMode != i3 || this.mPreviousWindowFlags != i4) {
            return false;
        }
        EditorInfo editorInfo2 = this.mPreviousEditorInfo;
        if (editorInfo2 != editorInfo) {
            return editorInfo2 != null && editorInfo2.kindofEquals(editorInfo);
        }
        return true;
    }
}
