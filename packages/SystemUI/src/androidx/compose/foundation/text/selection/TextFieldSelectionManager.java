package androidx.compose.foundation.text.selection;

import android.view.ActionMode;
import androidx.compose.foundation.text.Handle;
import androidx.compose.foundation.text.HandleState;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextDelegate;
import androidx.compose.foundation.text.TextDragObserver;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.UndoManager;
import androidx.compose.foundation.text.ValidatingOffsetMappingKt;
import androidx.compose.foundation.text.selection.SelectionAdjustment;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;
import androidx.compose.ui.platform.AndroidTextToolbar;
import androidx.compose.ui.platform.Clipboard;
import androidx.compose.ui.platform.TextToolbar;
import androidx.compose.ui.platform.TextToolbarStatus;
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphKt;
import androidx.compose.ui.text.Paragraph;
import androidx.compose.ui.text.ParagraphInfo;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.android.TextLayout;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.VisualTransformation;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldSelectionManager {
    public Clipboard clipboard;
    public CoroutineScope coroutineScope;
    public final MutableState currentDragPosition$delegate;
    public Integer dragBeginOffsetInText;
    public long dragBeginPosition;
    public long dragTotalDistance;
    public final MutableState draggingHandle$delegate;
    public final MutableState editable$delegate;
    public final MutableState enabled$delegate;
    public FocusRequester focusRequester;
    public HapticFeedback hapticFeedBack;
    public final TextFieldSelectionManager$mouseSelectionObserver$1 mouseSelectionObserver;
    public OffsetMapping offsetMapping;
    public TextFieldValue oldValue;
    public Lambda onValueChange;
    public int previousRawDragOffset;
    public SelectionLayout previousSelectionLayout;
    public Lambda requestAutofillAction;
    public LegacyTextFieldState state;
    public TextToolbar textToolbar;
    public final TextFieldSelectionManager$touchSelectionObserver$1 touchSelectionObserver;
    public final UndoManager undoManager;
    public final MutableState value$delegate;
    public VisualTransformation visualTransformation;

    public TextFieldSelectionManager() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00f0, code lost:
    
        if (r3.rawEndHandleOffset == r4.rawEndHandleOffset) goto L47;
     */
    /* JADX WARN: Type inference failed for: r4v12, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* renamed from: access$updateSelection-8UEBfa8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final long m236access$updateSelection8UEBfa8(androidx.compose.foundation.text.selection.TextFieldSelectionManager r29, androidx.compose.ui.text.input.TextFieldValue r30, long r31, boolean r33, boolean r34, androidx.compose.foundation.text.selection.SelectionAdjustment r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.TextFieldSelectionManager.m236access$updateSelection8UEBfa8(androidx.compose.foundation.text.selection.TextFieldSelectionManager, androidx.compose.ui.text.input.TextFieldValue, long, boolean, boolean, androidx.compose.foundation.text.selection.SelectionAdjustment, boolean):long");
    }

    /* renamed from: createTextFieldValue-FDrldGo, reason: not valid java name */
    public static TextFieldValue m237createTextFieldValueFDrldGo(AnnotatedString annotatedString, long j) {
        return new TextFieldValue(annotatedString, j, (TextRange) null, 4, (DefaultConstructorMarker) null);
    }

    public final StandaloneCoroutine copy$foundation_release(boolean z) {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            return BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new TextFieldSelectionManager$copy$1(this, z, null), 1);
        }
        return null;
    }

    public final void cut$foundation_release() {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new TextFieldSelectionManager$cut$1(this, null), 1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* renamed from: deselect-_kEHs6E$foundation_release, reason: not valid java name */
    public final void m238deselect_kEHs6E$foundation_release(Offset offset) {
        if (!TextRange.m747getCollapsedimpl(getValue$foundation_release().selection)) {
            LegacyTextFieldState legacyTextFieldState = this.state;
            TextLayoutResultProxy layoutResult = legacyTextFieldState != null ? legacyTextFieldState.getLayoutResult() : null;
            int m749getMaximpl = (offset == null || layoutResult == null) ? TextRange.m749getMaximpl(getValue$foundation_release().selection) : this.offsetMapping.transformedToOriginal(layoutResult.m208getOffsetForPosition3MmeM6k(offset.packedValue, true));
            this.onValueChange.mo779invoke(TextFieldValue.m778copy3r_uNRQ$default(getValue$foundation_release(), null, TextRangeKt.TextRange(m749getMaximpl, m749getMaximpl), 5));
        }
        setHandleState((offset == null || getValue$foundation_release().annotatedString.text.length() <= 0) ? HandleState.None : HandleState.Cursor);
        updateFloatingToolbar(false);
    }

    public final void enterSelectionMode$foundation_release(boolean z) {
        FocusRequester focusRequester;
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null && !legacyTextFieldState.getHasFocus() && (focusRequester = this.focusRequester) != null) {
            FocusRequester.m376requestFocus3ESFkO8$default(focusRequester);
        }
        this.oldValue = getValue$foundation_release();
        updateFloatingToolbar(z);
        setHandleState(HandleState.Selection);
    }

    /* renamed from: getCurrentDragPosition-_m7T9-E, reason: not valid java name */
    public final Offset m239getCurrentDragPosition_m7T9E() {
        return (Offset) ((SnapshotMutableStateImpl) this.currentDragPosition$delegate).getValue();
    }

    public final boolean getEditable() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.editable$delegate).getValue()).booleanValue();
    }

    public final boolean getEnabled() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.enabled$delegate).getValue()).booleanValue();
    }

    /* renamed from: getHandlePosition-tuRUvjQ$foundation_release, reason: not valid java name */
    public final long m240getHandlePositiontuRUvjQ$foundation_release(boolean z) {
        TextLayoutResultProxy layoutResult;
        TextLayoutResult textLayoutResult;
        long j;
        TextDelegate textDelegate;
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState == null || (layoutResult = legacyTextFieldState.getLayoutResult()) == null || (textLayoutResult = layoutResult.value) == null) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        LegacyTextFieldState legacyTextFieldState2 = this.state;
        AnnotatedString annotatedString = (legacyTextFieldState2 == null || (textDelegate = legacyTextFieldState2.textDelegate) == null) ? null : textDelegate.text;
        if (annotatedString == null) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        if (!Intrinsics.areEqual(annotatedString.text, textLayoutResult.layoutInput.text.text)) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        TextFieldValue value$foundation_release = getValue$foundation_release();
        if (z) {
            long j2 = value$foundation_release.selection;
            TextRange.Companion companion = TextRange.Companion;
            j = j2 >> 32;
        } else {
            long j3 = value$foundation_release.selection;
            TextRange.Companion companion2 = TextRange.Companion;
            j = j3 & 4294967295L;
        }
        int originalToTransformed = this.offsetMapping.originalToTransformed((int) j);
        boolean m751getReversedimpl = TextRange.m751getReversedimpl(getValue$foundation_release().selection);
        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
        if (multiParagraph.getLineForOffset(originalToTransformed) >= multiParagraph.lineCount) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        boolean z2 = textLayoutResult.getBidiRunDirection(((!z || m751getReversedimpl) && (z || !m751getReversedimpl)) ? Math.max(originalToTransformed + (-1), 0) : originalToTransformed) == textLayoutResult.getParagraphDirection(originalToTransformed);
        multiParagraph.requireIndexInRangeInclusiveEnd(originalToTransformed);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) multiParagraph.paragraphInfoList).get(originalToTransformed == multiParagraph.intrinsics.annotatedString.text.length() ? CollectionsKt__CollectionsKt.getLastIndex(multiParagraph.paragraphInfoList) : MultiParagraphKt.findParagraphByIndex(originalToTransformed, multiParagraph.paragraphInfoList));
        Paragraph paragraph = paragraphInfo.paragraph;
        int localIndex = paragraphInfo.toLocalIndex(originalToTransformed);
        TextLayout textLayout = ((AndroidParagraph) paragraph).layout;
        float primaryHorizontal = z2 ? textLayout.getPrimaryHorizontal(localIndex, false) : textLayout.getSecondaryHorizontal(localIndex, false);
        long j4 = textLayoutResult.size;
        long floatToRawIntBits = (Float.floatToRawIntBits(RangesKt___RangesKt.coerceIn(multiParagraph.getLineBottom(r7), 0.0f, (int) (j4 & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(RangesKt___RangesKt.coerceIn(primaryHorizontal, 0.0f, (int) (j4 >> 32))) << 32);
        Offset.Companion companion3 = Offset.Companion;
        return floatToRawIntBits;
    }

    public final TextFieldValue getValue$foundation_release() {
        return (TextFieldValue) ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }

    public final void hideSelectionToolbar$foundation_release() {
        TextToolbar textToolbar = this.textToolbar;
        if ((textToolbar != null ? ((AndroidTextToolbar) textToolbar).status : null) != TextToolbarStatus.Shown || textToolbar == null) {
            return;
        }
        AndroidTextToolbar androidTextToolbar = (AndroidTextToolbar) textToolbar;
        androidTextToolbar.status = TextToolbarStatus.Hidden;
        ActionMode actionMode = androidTextToolbar.actionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        androidTextToolbar.actionMode = null;
    }

    public final void paste$foundation_release() {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new TextFieldSelectionManager$paste$1(this, null), 1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    public final void selectAll$foundation_release() {
        TextFieldValue m237createTextFieldValueFDrldGo = m237createTextFieldValueFDrldGo(getValue$foundation_release().annotatedString, TextRangeKt.TextRange(0, getValue$foundation_release().annotatedString.text.length()));
        this.onValueChange.mo779invoke(m237createTextFieldValueFDrldGo);
        this.oldValue = TextFieldValue.m778copy3r_uNRQ$default(this.oldValue, null, m237createTextFieldValueFDrldGo.selection, 5);
        enterSelectionMode$foundation_release(true);
    }

    /* renamed from: setDeletionPreviewHighlight-5zc-tL8$foundation_release, reason: not valid java name */
    public final void m241setDeletionPreviewHighlight5zctL8$foundation_release(long j) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            ((SnapshotMutableStateImpl) legacyTextFieldState.deletionPreviewHighlightRange$delegate).setValue(TextRange.m745boximpl(j));
        }
        LegacyTextFieldState legacyTextFieldState2 = this.state;
        if (legacyTextFieldState2 != null) {
            TextRange.Companion.getClass();
            long j2 = TextRange.Zero;
            ((SnapshotMutableStateImpl) legacyTextFieldState2.selectionPreviewHighlightRange$delegate).setValue(TextRange.m745boximpl(j2));
        }
        if (TextRange.m747getCollapsedimpl(j)) {
            return;
        }
        updateFloatingToolbar(false);
        setHandleState(HandleState.None);
    }

    public final void setHandleState(HandleState handleState) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            if (legacyTextFieldState.getHandleState() == handleState) {
                legacyTextFieldState = null;
            }
            if (legacyTextFieldState != null) {
                ((SnapshotMutableStateImpl) legacyTextFieldState.handleState$delegate).setValue(handleState);
            }
        }
    }

    /* renamed from: setSelectionPreviewHighlight-5zc-tL8$foundation_release, reason: not valid java name */
    public final void m242setSelectionPreviewHighlight5zctL8$foundation_release(long j) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            ((SnapshotMutableStateImpl) legacyTextFieldState.selectionPreviewHighlightRange$delegate).setValue(TextRange.m745boximpl(j));
        }
        LegacyTextFieldState legacyTextFieldState2 = this.state;
        if (legacyTextFieldState2 != null) {
            TextRange.Companion.getClass();
            long j2 = TextRange.Zero;
            ((SnapshotMutableStateImpl) legacyTextFieldState2.deletionPreviewHighlightRange$delegate).setValue(TextRange.m745boximpl(j2));
        }
        if (TextRange.m747getCollapsedimpl(j)) {
            return;
        }
        updateFloatingToolbar(false);
        setHandleState(HandleState.None);
    }

    public final void showSelectionToolbar$foundation_release() {
        CoroutineScope coroutineScope = this.coroutineScope;
        if (coroutineScope != null) {
            BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new TextFieldSelectionManager$showSelectionToolbar$1(this, null), 1);
        }
    }

    public final void updateFloatingToolbar(boolean z) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            ((SnapshotMutableStateImpl) legacyTextFieldState.showFloatingToolbar$delegate).setValue(Boolean.valueOf(z));
        }
        if (z) {
            showSelectionToolbar$foundation_release();
        } else {
            hideSelectionToolbar$foundation_release();
        }
    }

    /* JADX WARN: Type inference failed for: r8v12, types: [androidx.compose.foundation.text.selection.TextFieldSelectionManager$touchSelectionObserver$1] */
    /* JADX WARN: Type inference failed for: r8v13, types: [androidx.compose.foundation.text.selection.TextFieldSelectionManager$mouseSelectionObserver$1] */
    public TextFieldSelectionManager(UndoManager undoManager) {
        this.undoManager = undoManager;
        this.offsetMapping = ValidatingOffsetMappingKt.ValidatingEmptyOffsetMappingIdentity;
        this.onValueChange = new Function1() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$onValueChange$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this.value$delegate = SnapshotStateKt.mutableStateOf$default(new TextFieldValue((String) null, 0L, (TextRange) null, 7, (DefaultConstructorMarker) null));
        VisualTransformation.Companion.getClass();
        this.visualTransformation = VisualTransformation.Companion.None;
        Boolean bool = Boolean.TRUE;
        this.editable$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.enabled$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        Offset.Companion.getClass();
        this.dragBeginPosition = 0L;
        this.dragTotalDistance = 0L;
        this.draggingHandle$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.currentDragPosition$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.previousRawDragOffset = -1;
        this.oldValue = new TextFieldValue((String) null, 0L, (TextRange) null, 7, (DefaultConstructorMarker) null);
        this.touchSelectionObserver = new TextDragObserver() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$touchSelectionObserver$1
            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onCancel() {
                onEnd();
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onDrag-k-4lQ0M */
            public final void mo202onDragk4lQ0M(long j) {
                TextLayoutResultProxy layoutResult;
                long m236access$updateSelection8UEBfa8;
                SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda0;
                TextFieldSelectionManager textFieldSelectionManager = TextFieldSelectionManager.this;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0) {
                    return;
                }
                textFieldSelectionManager.dragTotalDistance = Offset.m401plusMKHz9U(textFieldSelectionManager.dragTotalDistance, j);
                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                if (legacyTextFieldState != null && (layoutResult = legacyTextFieldState.getLayoutResult()) != null) {
                    ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(Offset.m393boximpl(Offset.m401plusMKHz9U(textFieldSelectionManager.dragBeginPosition, textFieldSelectionManager.dragTotalDistance)));
                    if (textFieldSelectionManager.dragBeginOffsetInText == null) {
                        Offset m239getCurrentDragPosition_m7T9E = textFieldSelectionManager.m239getCurrentDragPosition_m7T9E();
                        m239getCurrentDragPosition_m7T9E.getClass();
                        if (!layoutResult.m209isPositionOnTextk4lQ0M(m239getCurrentDragPosition_m7T9E.packedValue)) {
                            int transformedToOriginal = textFieldSelectionManager.offsetMapping.transformedToOriginal(layoutResult.m208getOffsetForPosition3MmeM6k(textFieldSelectionManager.dragBeginPosition, true));
                            OffsetMapping offsetMapping = textFieldSelectionManager.offsetMapping;
                            Offset m239getCurrentDragPosition_m7T9E2 = textFieldSelectionManager.m239getCurrentDragPosition_m7T9E();
                            m239getCurrentDragPosition_m7T9E2.getClass();
                            if (transformedToOriginal == offsetMapping.transformedToOriginal(layoutResult.m208getOffsetForPosition3MmeM6k(m239getCurrentDragPosition_m7T9E2.packedValue, true))) {
                                SelectionAdjustment.Companion.getClass();
                                selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.None;
                            } else {
                                SelectionAdjustment.Companion.getClass();
                                selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.Word;
                            }
                            SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda02 = selectionAdjustment$Companion$$ExternalSyntheticLambda0;
                            TextFieldValue value$foundation_release = textFieldSelectionManager.getValue$foundation_release();
                            Offset m239getCurrentDragPosition_m7T9E3 = textFieldSelectionManager.m239getCurrentDragPosition_m7T9E();
                            m239getCurrentDragPosition_m7T9E3.getClass();
                            m236access$updateSelection8UEBfa8 = TextFieldSelectionManager.m236access$updateSelection8UEBfa8(textFieldSelectionManager, value$foundation_release, m239getCurrentDragPosition_m7T9E3.packedValue, false, false, selectionAdjustment$Companion$$ExternalSyntheticLambda02, true);
                            TextRange.m745boximpl(m236access$updateSelection8UEBfa8);
                        }
                    }
                    Integer num = textFieldSelectionManager.dragBeginOffsetInText;
                    int intValue = num != null ? num.intValue() : layoutResult.m208getOffsetForPosition3MmeM6k(textFieldSelectionManager.dragBeginPosition, false);
                    Offset m239getCurrentDragPosition_m7T9E4 = textFieldSelectionManager.m239getCurrentDragPosition_m7T9E();
                    m239getCurrentDragPosition_m7T9E4.getClass();
                    int m208getOffsetForPosition3MmeM6k = layoutResult.m208getOffsetForPosition3MmeM6k(m239getCurrentDragPosition_m7T9E4.packedValue, false);
                    if (textFieldSelectionManager.dragBeginOffsetInText == null && intValue == m208getOffsetForPosition3MmeM6k) {
                        return;
                    }
                    TextFieldValue value$foundation_release2 = textFieldSelectionManager.getValue$foundation_release();
                    Offset m239getCurrentDragPosition_m7T9E5 = textFieldSelectionManager.m239getCurrentDragPosition_m7T9E();
                    m239getCurrentDragPosition_m7T9E5.getClass();
                    SelectionAdjustment.Companion.getClass();
                    m236access$updateSelection8UEBfa8 = TextFieldSelectionManager.m236access$updateSelection8UEBfa8(textFieldSelectionManager, value$foundation_release2, m239getCurrentDragPosition_m7T9E5.packedValue, false, false, SelectionAdjustment.Companion.Word, true);
                    TextRange.m745boximpl(m236access$updateSelection8UEBfa8);
                }
                textFieldSelectionManager.updateFloatingToolbar(false);
            }

            public final void onEnd() {
                TextFieldSelectionManager textFieldSelectionManager = TextFieldSelectionManager.this;
                ((SnapshotMutableStateImpl) textFieldSelectionManager.draggingHandle$delegate).setValue(null);
                ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(null);
                textFieldSelectionManager.updateFloatingToolbar(true);
                textFieldSelectionManager.dragBeginOffsetInText = null;
                boolean m747getCollapsedimpl = TextRange.m747getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
                textFieldSelectionManager.setHandleState(m747getCollapsedimpl ? HandleState.Cursor : HandleState.Selection);
                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                if (legacyTextFieldState != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState.showSelectionHandleStart$delegate).setValue(Boolean.valueOf(!m747getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)));
                }
                LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                if (legacyTextFieldState2 != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState2.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(!m747getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, false)));
                }
                LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager.state;
                if (legacyTextFieldState3 == null) {
                    return;
                }
                ((SnapshotMutableStateImpl) legacyTextFieldState3.showCursorHandle$delegate).setValue(Boolean.valueOf(m747getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)));
            }

            /* JADX WARN: Type inference failed for: r9v5, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onStart-k-4lQ0M */
            public final void mo203onStartk4lQ0M(long j) {
                long j2;
                TextLayoutResultProxy layoutResult;
                TextLayoutResultProxy layoutResult2;
                TextFieldSelectionManager textFieldSelectionManager = TextFieldSelectionManager.this;
                if (textFieldSelectionManager.getEnabled()) {
                    MutableState mutableState = textFieldSelectionManager.draggingHandle$delegate;
                    if (((Handle) ((SnapshotMutableStateImpl) mutableState).getValue()) != null) {
                        return;
                    }
                    ((SnapshotMutableStateImpl) mutableState).setValue(Handle.SelectionEnd);
                    textFieldSelectionManager.previousRawDragOffset = -1;
                    textFieldSelectionManager.hideSelectionToolbar$foundation_release();
                    LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                    if (legacyTextFieldState == null || (layoutResult2 = legacyTextFieldState.getLayoutResult()) == null || !layoutResult2.m209isPositionOnTextk4lQ0M(j)) {
                        j2 = j;
                        LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                        if (legacyTextFieldState2 != null && (layoutResult = legacyTextFieldState2.getLayoutResult()) != null) {
                            int transformedToOriginal = textFieldSelectionManager.offsetMapping.transformedToOriginal(layoutResult.m208getOffsetForPosition3MmeM6k(j2, true));
                            TextFieldValue m237createTextFieldValueFDrldGo = TextFieldSelectionManager.m237createTextFieldValueFDrldGo(textFieldSelectionManager.getValue$foundation_release().annotatedString, TextRangeKt.TextRange(transformedToOriginal, transformedToOriginal));
                            textFieldSelectionManager.enterSelectionMode$foundation_release(false);
                            HapticFeedback hapticFeedback = textFieldSelectionManager.hapticFeedBack;
                            if (hapticFeedback != null) {
                                HapticFeedbackType.Companion.getClass();
                                hapticFeedback.mo570performHapticFeedbackCdsT49E(HapticFeedbackType.Companion.m571getTextHandleMove5zf0vsI());
                            }
                            textFieldSelectionManager.onValueChange.mo779invoke(m237createTextFieldValueFDrldGo);
                        }
                    } else {
                        if (textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0) {
                            return;
                        }
                        textFieldSelectionManager.enterSelectionMode$foundation_release(false);
                        TextFieldValue value$foundation_release = textFieldSelectionManager.getValue$foundation_release();
                        TextRange.Companion.getClass();
                        TextFieldValue m778copy3r_uNRQ$default = TextFieldValue.m778copy3r_uNRQ$default(value$foundation_release, null, TextRange.Zero, 5);
                        SelectionAdjustment.Companion.getClass();
                        j2 = j;
                        textFieldSelectionManager.dragBeginOffsetInText = Integer.valueOf((int) (TextFieldSelectionManager.m236access$updateSelection8UEBfa8(textFieldSelectionManager, m778copy3r_uNRQ$default, j, true, false, SelectionAdjustment.Companion.Word, true) >> 32));
                    }
                    textFieldSelectionManager.setHandleState(HandleState.None);
                    textFieldSelectionManager.dragBeginPosition = j2;
                    ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(Offset.m393boximpl(j2));
                    Offset.Companion.getClass();
                    textFieldSelectionManager.dragTotalDistance = 0L;
                }
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onStop() {
                onEnd();
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onDown-k-4lQ0M */
            public final void mo201onDownk4lQ0M() {
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onUp() {
            }
        };
        this.mouseSelectionObserver = new MouseSelectionObserver() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$mouseSelectionObserver$1
            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onDrag-3MmeM6k */
            public final boolean mo229onDrag3MmeM6k(long j, SelectionAdjustment selectionAdjustment) {
                LegacyTextFieldState legacyTextFieldState;
                TextFieldSelectionManager textFieldSelectionManager = TextFieldSelectionManager.this;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0 || (legacyTextFieldState = textFieldSelectionManager.state) == null || legacyTextFieldState.getLayoutResult() == null) {
                    return false;
                }
                updateMouseSelection(textFieldSelectionManager.getValue$foundation_release(), j, false, selectionAdjustment);
                return true;
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onStart-3MmeM6k */
            public final boolean mo230onStart3MmeM6k(long j, SelectionAdjustment selectionAdjustment) {
                LegacyTextFieldState legacyTextFieldState;
                TextFieldSelectionManager textFieldSelectionManager = TextFieldSelectionManager.this;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0 || (legacyTextFieldState = textFieldSelectionManager.state) == null || legacyTextFieldState.getLayoutResult() == null) {
                    return false;
                }
                FocusRequester focusRequester = textFieldSelectionManager.focusRequester;
                if (focusRequester != null) {
                    FocusRequester.m376requestFocus3ESFkO8$default(focusRequester);
                }
                textFieldSelectionManager.dragBeginPosition = j;
                textFieldSelectionManager.previousRawDragOffset = -1;
                textFieldSelectionManager.enterSelectionMode$foundation_release(true);
                updateMouseSelection(textFieldSelectionManager.getValue$foundation_release(), textFieldSelectionManager.dragBeginPosition, true, selectionAdjustment);
                return true;
            }

            public final void updateMouseSelection(TextFieldValue textFieldValue, long j, boolean z, SelectionAdjustment selectionAdjustment) {
                TextFieldSelectionManager.this.setHandleState(TextRange.m747getCollapsedimpl(TextFieldSelectionManager.m236access$updateSelection8UEBfa8(TextFieldSelectionManager.this, textFieldValue, j, z, false, selectionAdjustment, false)) ? HandleState.Cursor : HandleState.Selection);
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            public final void onDragDone() {
            }
        };
    }

    public /* synthetic */ TextFieldSelectionManager(UndoManager undoManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : undoManager);
    }
}
