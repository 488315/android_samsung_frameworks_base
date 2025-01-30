package android.view.inputmethod;

/* loaded from: classes4.dex */
final class ViewFocusParameterInfo {
    final EditorInfo mPreviousEditorInfo;
    final int mPreviousSoftInputMode;
    final int mPreviousStartInputFlags;
    final int mPreviousStartInputReason;
    final int mPreviousWindowFlags;

    ViewFocusParameterInfo(EditorInfo previousEditorInfo, int previousStartInputFlags, int previousStartInputReason, int previousSoftInputMode, int previousWindowFlags) {
        this.mPreviousEditorInfo = previousEditorInfo;
        this.mPreviousStartInputFlags = previousStartInputFlags;
        this.mPreviousStartInputReason = previousStartInputReason;
        this.mPreviousSoftInputMode = previousSoftInputMode;
        this.mPreviousWindowFlags = previousWindowFlags;
    }

    boolean sameAs(EditorInfo currentEditorInfo, int startInputFlags, int startInputReason, int softInputMode, int windowFlags) {
        EditorInfo editorInfo;
        return this.mPreviousStartInputFlags == startInputFlags && this.mPreviousStartInputReason == startInputReason && this.mPreviousSoftInputMode == softInputMode && this.mPreviousWindowFlags == windowFlags && ((editorInfo = this.mPreviousEditorInfo) == currentEditorInfo || (editorInfo != null && editorInfo.kindofEquals(currentEditorInfo)));
    }
}
