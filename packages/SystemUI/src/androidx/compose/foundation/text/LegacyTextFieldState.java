package androidx.compose.foundation.text;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScope;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.platform.SoftwareKeyboardController;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.EditProcessor;
import androidx.compose.ui.text.input.TextInputSession;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LegacyTextFieldState {
    public LayoutCoordinates _layoutCoordinates;
    public final MutableState autofillHighlightOn$delegate;
    public final MutableState deletionPreviewHighlightRange$delegate;
    public final MutableState handleState$delegate;
    public final MutableState hasFocus$delegate;
    public final AndroidPaint highlightPaint;
    public TextInputSession inputSession;
    public final MutableState isInTouchMode$delegate;
    public boolean isLayoutResultStale;
    public final MutableState justAutofilled$delegate;
    public final KeyboardActionRunner keyboardActionRunner;
    public final SoftwareKeyboardController keyboardController;
    public final MutableState layoutResultState;
    public final MutableState minHeightForSingleLineField$delegate;
    public final Function1 onImeActionPerformed;
    public final Function1 onValueChange;
    public Function1 onValueChangeOriginal;
    public final EditProcessor processor = new EditProcessor();
    public final RecomposeScope recomposeScope;
    public long selectionBackgroundColor;
    public final MutableState selectionPreviewHighlightRange$delegate;
    public final MutableState showCursorHandle$delegate;
    public final MutableState showFloatingToolbar$delegate;
    public final MutableState showSelectionHandleEnd$delegate;
    public final MutableState showSelectionHandleStart$delegate;
    public TextDelegate textDelegate;
    public AnnotatedString untransformedText;

    public LegacyTextFieldState(TextDelegate textDelegate, RecomposeScope recomposeScope, SoftwareKeyboardController softwareKeyboardController) {
        this.textDelegate = textDelegate;
        this.recomposeScope = recomposeScope;
        this.keyboardController = softwareKeyboardController;
        Boolean bool = Boolean.FALSE;
        this.hasFocus$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.minHeightForSingleLineField$delegate = SnapshotStateKt.mutableStateOf$default(Dp.m835boximpl(0));
        this.layoutResultState = SnapshotStateKt.mutableStateOf$default(null);
        this.handleState$delegate = SnapshotStateKt.mutableStateOf$default(HandleState.None);
        this.showFloatingToolbar$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.showSelectionHandleStart$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.showSelectionHandleEnd$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.showCursorHandle$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isLayoutResultStale = true;
        this.isInTouchMode$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
        this.keyboardActionRunner = new KeyboardActionRunner(softwareKeyboardController);
        this.autofillHighlightOn$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.justAutofilled$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.onValueChangeOriginal = new Function1() { // from class: androidx.compose.foundation.text.LegacyTextFieldState$onValueChangeOriginal$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this.onValueChange = new LegacyTextFieldState$onValueChange$1(this);
        this.onImeActionPerformed = new LegacyTextFieldState$onImeActionPerformed$1(this);
        this.highlightPaint = new AndroidPaint();
        Color.Companion.getClass();
        this.selectionBackgroundColor = Color.Unspecified;
        TextRange.Companion.getClass();
        long j = TextRange.Zero;
        this.selectionPreviewHighlightRange$delegate = SnapshotStateKt.mutableStateOf$default(TextRange.m745boximpl(j));
        this.deletionPreviewHighlightRange$delegate = SnapshotStateKt.mutableStateOf$default(TextRange.m745boximpl(j));
    }

    public final HandleState getHandleState() {
        return (HandleState) ((SnapshotMutableStateImpl) this.handleState$delegate).getValue();
    }

    public final boolean getHasFocus() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.hasFocus$delegate).getValue()).booleanValue();
    }

    public final LayoutCoordinates getLayoutCoordinates() {
        LayoutCoordinates layoutCoordinates = this._layoutCoordinates;
        if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
            return null;
        }
        return layoutCoordinates;
    }

    public final TextLayoutResultProxy getLayoutResult() {
        return (TextLayoutResultProxy) ((SnapshotMutableStateImpl) this.layoutResultState).getValue();
    }
}
