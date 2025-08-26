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
import androidx.compose.foundation.text.selection.Selection;
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

    /* JADX WARN: Multi-variable type inference failed */
    public TextFieldSelectionManager() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00f2  */
    /* JADX WARN: Type inference failed for: r4v12, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* renamed from: access$updateSelection-8UEBfa8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final long m237access$updateSelection8UEBfa8(TextFieldSelectionManager textFieldSelectionManager, TextFieldValue textFieldValue, long j, boolean z, boolean z2, SelectionAdjustment selectionAdjustment, boolean z3) {
        TextLayoutResultProxy layoutResult;
        long j2;
        char c;
        TextLayoutResult textLayoutResult;
        Selection selection;
        boolean z4;
        boolean z5;
        HapticFeedback hapticFeedback;
        int i;
        LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
        if (legacyTextFieldState == null || (layoutResult = legacyTextFieldState.getLayoutResult()) == null) {
            TextRange.Companion.getClass();
            return TextRange.Zero;
        }
        OffsetMapping offsetMapping = textFieldSelectionManager.offsetMapping;
        long j3 = textFieldValue.selection;
        TextRange.Companion companion = TextRange.Companion;
        int iOriginalToTransformed = offsetMapping.originalToTransformed((int) (j3 >> 32));
        OffsetMapping offsetMapping2 = textFieldSelectionManager.offsetMapping;
        long j4 = textFieldValue.selection;
        long jTextRange = TextRangeKt.TextRange(iOriginalToTransformed, offsetMapping2.originalToTransformed((int) (j4 & 4294967295L)));
        int iM209getOffsetForPosition3MmeM6k = layoutResult.m209getOffsetForPosition3MmeM6k(j, false);
        int i2 = (z2 || z) ? iM209getOffsetForPosition3MmeM6k : (int) (jTextRange >> 32);
        int i3 = (!z2 || z) ? iM209getOffsetForPosition3MmeM6k : (int) (jTextRange & 4294967295L);
        SelectionLayout selectionLayout = textFieldSelectionManager.previousSelectionLayout;
        int i4 = (z || selectionLayout == null || (i = textFieldSelectionManager.previousRawDragOffset) == -1) ? -1 : i;
        TextLayoutResult textLayoutResult2 = layoutResult.value;
        if (z) {
            textLayoutResult = textLayoutResult2;
            selection = null;
            c = ' ';
            j2 = 4294967295L;
        } else {
            j2 = 4294967295L;
            int i5 = (int) (jTextRange >> 32);
            c = ' ';
            int i6 = (int) (jTextRange & 4294967295L);
            textLayoutResult = textLayoutResult2;
            selection = new Selection(new Selection.AnchorInfo(SelectionLayoutKt.getTextDirectionForOffset(textLayoutResult2, i5), i5, 1L), new Selection.AnchorInfo(SelectionLayoutKt.getTextDirectionForOffset(textLayoutResult2, i6), i6, 1L), TextRange.m753getReversedimpl(jTextRange));
        }
        SingleSelectionLayout singleSelectionLayout = new SingleSelectionLayout(z2, 1, 1, selection, new SelectableInfo(1L, 1, i2, i3, i4, textLayoutResult));
        if (singleSelectionLayout.previousSelection == null || selectionLayout == null) {
            textFieldSelectionManager.previousSelectionLayout = singleSelectionLayout;
            textFieldSelectionManager.previousRawDragOffset = iM209getOffsetForPosition3MmeM6k;
            Selection selectionAdjust = selectionAdjustment.adjust(singleSelectionLayout);
            long jTextRange2 = TextRangeKt.TextRange(textFieldSelectionManager.offsetMapping.transformedToOriginal(selectionAdjust.start.offset), textFieldSelectionManager.offsetMapping.transformedToOriginal(selectionAdjust.end.offset));
            if (!TextRange.m748equalsimpl0(jTextRange2, j4)) {
                boolean z6 = TextRange.m753getReversedimpl(jTextRange2) != TextRange.m753getReversedimpl(j4) && TextRange.m748equalsimpl0(TextRangeKt.TextRange((int) (jTextRange2 & j2), (int) (jTextRange2 >> c)), j4);
                boolean z7 = TextRange.m749getCollapsedimpl(jTextRange2) && TextRange.m749getCollapsedimpl(j4);
                AnnotatedString annotatedString = textFieldValue.annotatedString;
                if (z3 && annotatedString.text.length() > 0 && !z6 && !z7 && (hapticFeedback = textFieldSelectionManager.hapticFeedBack) != null) {
                    HapticFeedbackType.Companion.getClass();
                    hapticFeedback.mo572performHapticFeedbackCdsT49E(HapticFeedbackType.Companion.m573getTextHandleMove5zf0vsI());
                }
                textFieldSelectionManager.onValueChange.mo781invoke(m238createTextFieldValueFDrldGo(annotatedString, jTextRange2));
                if (!z3) {
                    textFieldSelectionManager.updateFloatingToolbar(!TextRange.m749getCollapsedimpl(jTextRange2));
                }
                LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                if (legacyTextFieldState2 != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState2.isInTouchMode$delegate).setValue(Boolean.valueOf(z3));
                }
                LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager.state;
                if (legacyTextFieldState3 != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState3.showSelectionHandleStart$delegate).setValue(Boolean.valueOf(!TextRange.m749getCollapsedimpl(jTextRange2) && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)));
                }
                LegacyTextFieldState legacyTextFieldState4 = textFieldSelectionManager.state;
                if (legacyTextFieldState4 == null) {
                    z4 = false;
                } else {
                    if (TextRange.m749getCollapsedimpl(jTextRange2)) {
                        z4 = false;
                    } else {
                        z4 = false;
                        if (TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, false)) {
                            z5 = true;
                        }
                        ((SnapshotMutableStateImpl) legacyTextFieldState4.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(z5));
                    }
                    z5 = z4;
                    ((SnapshotMutableStateImpl) legacyTextFieldState4.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(z5));
                }
                LegacyTextFieldState legacyTextFieldState5 = textFieldSelectionManager.state;
                if (legacyTextFieldState5 == null) {
                    return jTextRange2;
                }
                ((SnapshotMutableStateImpl) legacyTextFieldState5.showCursorHandle$delegate).setValue(Boolean.valueOf((TextRange.m749getCollapsedimpl(jTextRange2) && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)) ? true : z4));
                return jTextRange2;
            }
        } else {
            SingleSelectionLayout singleSelectionLayout2 = (SingleSelectionLayout) selectionLayout;
            if (singleSelectionLayout.startSlot == singleSelectionLayout2.startSlot && singleSelectionLayout.endSlot == singleSelectionLayout2.endSlot && singleSelectionLayout.isStartHandle == singleSelectionLayout2.isStartHandle) {
                SelectableInfo selectableInfo = singleSelectionLayout.info;
                selectableInfo.getClass();
                SelectableInfo selectableInfo2 = ((SingleSelectionLayout) selectionLayout).info;
                if (selectableInfo.selectableId != selectableInfo2.selectableId || selectableInfo.rawStartHandleOffset != selectableInfo2.rawStartHandleOffset || selectableInfo.rawEndHandleOffset != selectableInfo2.rawEndHandleOffset) {
                }
            }
        }
        return j4;
    }

    /* renamed from: createTextFieldValue-FDrldGo, reason: not valid java name */
    public static TextFieldValue m238createTextFieldValueFDrldGo(AnnotatedString annotatedString, long j) {
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
    public final void m239deselect_kEHs6E$foundation_release(Offset offset) {
        if (!TextRange.m749getCollapsedimpl(getValue$foundation_release().selection)) {
            LegacyTextFieldState legacyTextFieldState = this.state;
            TextLayoutResultProxy layoutResult = legacyTextFieldState != null ? legacyTextFieldState.getLayoutResult() : null;
            int iM751getMaximpl = (offset == null || layoutResult == null) ? TextRange.m751getMaximpl(getValue$foundation_release().selection) : this.offsetMapping.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(offset.packedValue, true));
            this.onValueChange.mo781invoke(TextFieldValue.m780copy3r_uNRQ$default(getValue$foundation_release(), null, TextRangeKt.TextRange(iM751getMaximpl, iM751getMaximpl), 5));
        }
        setHandleState((offset == null || getValue$foundation_release().annotatedString.text.length() <= 0) ? HandleState.None : HandleState.Cursor);
        updateFloatingToolbar(false);
    }

    public final void enterSelectionMode$foundation_release(boolean z) {
        FocusRequester focusRequester;
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null && !legacyTextFieldState.getHasFocus() && (focusRequester = this.focusRequester) != null) {
            FocusRequester.m378requestFocus3ESFkO8$default(focusRequester);
        }
        this.oldValue = getValue$foundation_release();
        updateFloatingToolbar(z);
        setHandleState(HandleState.Selection);
    }

    /* renamed from: getCurrentDragPosition-_m7T9-E, reason: not valid java name */
    public final Offset m240getCurrentDragPosition_m7T9E() {
        return (Offset) ((SnapshotMutableStateImpl) this.currentDragPosition$delegate).getValue();
    }

    public final boolean getEditable() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.editable$delegate).getValue()).booleanValue();
    }

    public final boolean getEnabled() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.enabled$delegate).getValue()).booleanValue();
    }

    /* renamed from: getHandlePosition-tuRUvjQ$foundation_release, reason: not valid java name */
    public final long m241getHandlePositiontuRUvjQ$foundation_release(boolean z) {
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
        int iOriginalToTransformed = this.offsetMapping.originalToTransformed((int) j);
        boolean zM753getReversedimpl = TextRange.m753getReversedimpl(getValue$foundation_release().selection);
        MultiParagraph multiParagraph = textLayoutResult.multiParagraph;
        if (multiParagraph.getLineForOffset(iOriginalToTransformed) >= multiParagraph.lineCount) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        boolean z2 = textLayoutResult.getBidiRunDirection(((!z || zM753getReversedimpl) && (z || !zM753getReversedimpl)) ? Math.max(iOriginalToTransformed + (-1), 0) : iOriginalToTransformed) == textLayoutResult.getParagraphDirection(iOriginalToTransformed);
        multiParagraph.requireIndexInRangeInclusiveEnd(iOriginalToTransformed);
        ParagraphInfo paragraphInfo = (ParagraphInfo) ((ArrayList) multiParagraph.paragraphInfoList).get(iOriginalToTransformed == multiParagraph.intrinsics.annotatedString.text.length() ? CollectionsKt__CollectionsKt.getLastIndex(multiParagraph.paragraphInfoList) : MultiParagraphKt.findParagraphByIndex(iOriginalToTransformed, multiParagraph.paragraphInfoList));
        Paragraph paragraph = paragraphInfo.paragraph;
        int localIndex = paragraphInfo.toLocalIndex(iOriginalToTransformed);
        TextLayout textLayout = ((AndroidParagraph) paragraph).layout;
        float primaryHorizontal = z2 ? textLayout.getPrimaryHorizontal(localIndex, false) : textLayout.getSecondaryHorizontal(localIndex, false);
        long j4 = textLayoutResult.size;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(RangesKt___RangesKt.coerceIn(multiParagraph.getLineBottom(r7), 0.0f, (int) (j4 & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(RangesKt___RangesKt.coerceIn(primaryHorizontal, 0.0f, (int) (j4 >> 32))) << 32);
        Offset.Companion companion3 = Offset.Companion;
        return jFloatToRawIntBits;
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
        TextFieldValue textFieldValueM238createTextFieldValueFDrldGo = m238createTextFieldValueFDrldGo(getValue$foundation_release().annotatedString, TextRangeKt.TextRange(0, getValue$foundation_release().annotatedString.text.length()));
        this.onValueChange.mo781invoke(textFieldValueM238createTextFieldValueFDrldGo);
        this.oldValue = TextFieldValue.m780copy3r_uNRQ$default(this.oldValue, null, textFieldValueM238createTextFieldValueFDrldGo.selection, 5);
        enterSelectionMode$foundation_release(true);
    }

    /* renamed from: setDeletionPreviewHighlight-5zc-tL8$foundation_release, reason: not valid java name */
    public final void m242setDeletionPreviewHighlight5zctL8$foundation_release(long j) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            ((SnapshotMutableStateImpl) legacyTextFieldState.deletionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j));
        }
        LegacyTextFieldState legacyTextFieldState2 = this.state;
        if (legacyTextFieldState2 != null) {
            TextRange.Companion.getClass();
            long j2 = TextRange.Zero;
            ((SnapshotMutableStateImpl) legacyTextFieldState2.selectionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j2));
        }
        if (TextRange.m749getCollapsedimpl(j)) {
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
    public final void m243setSelectionPreviewHighlight5zctL8$foundation_release(long j) {
        LegacyTextFieldState legacyTextFieldState = this.state;
        if (legacyTextFieldState != null) {
            ((SnapshotMutableStateImpl) legacyTextFieldState.selectionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j));
        }
        LegacyTextFieldState legacyTextFieldState2 = this.state;
        if (legacyTextFieldState2 != null) {
            TextRange.Companion.getClass();
            long j2 = TextRange.Zero;
            ((SnapshotMutableStateImpl) legacyTextFieldState2.deletionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j2));
        }
        if (TextRange.m749getCollapsedimpl(j)) {
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
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
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

            /* JADX WARN: Removed duplicated region for block: B:21:0x0099  */
            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onDrag-k-4lQ0M */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void mo203onDragk4lQ0M(long j) {
                TextLayoutResultProxy layoutResult;
                long jM237access$updateSelection8UEBfa8;
                SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda0;
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0) {
                    return;
                }
                textFieldSelectionManager.dragTotalDistance = Offset.m403plusMKHz9U(textFieldSelectionManager.dragTotalDistance, j);
                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                if (legacyTextFieldState != null && (layoutResult = legacyTextFieldState.getLayoutResult()) != null) {
                    ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(Offset.m395boximpl(Offset.m403plusMKHz9U(textFieldSelectionManager.dragBeginPosition, textFieldSelectionManager.dragTotalDistance)));
                    if (textFieldSelectionManager.dragBeginOffsetInText == null) {
                        Offset offsetM240getCurrentDragPosition_m7T9E = textFieldSelectionManager.m240getCurrentDragPosition_m7T9E();
                        offsetM240getCurrentDragPosition_m7T9E.getClass();
                        if (layoutResult.m210isPositionOnTextk4lQ0M(offsetM240getCurrentDragPosition_m7T9E.packedValue)) {
                            Integer num = textFieldSelectionManager.dragBeginOffsetInText;
                            int iIntValue = num != null ? num.intValue() : layoutResult.m209getOffsetForPosition3MmeM6k(textFieldSelectionManager.dragBeginPosition, false);
                            Offset offsetM240getCurrentDragPosition_m7T9E2 = textFieldSelectionManager.m240getCurrentDragPosition_m7T9E();
                            offsetM240getCurrentDragPosition_m7T9E2.getClass();
                            int iM209getOffsetForPosition3MmeM6k = layoutResult.m209getOffsetForPosition3MmeM6k(offsetM240getCurrentDragPosition_m7T9E2.packedValue, false);
                            if (textFieldSelectionManager.dragBeginOffsetInText == null && iIntValue == iM209getOffsetForPosition3MmeM6k) {
                                return;
                            }
                            TextFieldValue value$foundation_release = textFieldSelectionManager.getValue$foundation_release();
                            Offset offsetM240getCurrentDragPosition_m7T9E3 = textFieldSelectionManager.m240getCurrentDragPosition_m7T9E();
                            offsetM240getCurrentDragPosition_m7T9E3.getClass();
                            SelectionAdjustment.Companion.getClass();
                            jM237access$updateSelection8UEBfa8 = TextFieldSelectionManager.m237access$updateSelection8UEBfa8(textFieldSelectionManager, value$foundation_release, offsetM240getCurrentDragPosition_m7T9E3.packedValue, false, false, SelectionAdjustment.Companion.Word, true);
                        } else {
                            int iTransformedToOriginal = textFieldSelectionManager.offsetMapping.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(textFieldSelectionManager.dragBeginPosition, true));
                            OffsetMapping offsetMapping = textFieldSelectionManager.offsetMapping;
                            Offset offsetM240getCurrentDragPosition_m7T9E4 = textFieldSelectionManager.m240getCurrentDragPosition_m7T9E();
                            offsetM240getCurrentDragPosition_m7T9E4.getClass();
                            if (iTransformedToOriginal == offsetMapping.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(offsetM240getCurrentDragPosition_m7T9E4.packedValue, true))) {
                                SelectionAdjustment.Companion.getClass();
                                selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.None;
                            } else {
                                SelectionAdjustment.Companion.getClass();
                                selectionAdjustment$Companion$$ExternalSyntheticLambda0 = SelectionAdjustment.Companion.Word;
                            }
                            SelectionAdjustment$Companion$$ExternalSyntheticLambda0 selectionAdjustment$Companion$$ExternalSyntheticLambda02 = selectionAdjustment$Companion$$ExternalSyntheticLambda0;
                            TextFieldValue value$foundation_release2 = textFieldSelectionManager.getValue$foundation_release();
                            Offset offsetM240getCurrentDragPosition_m7T9E5 = textFieldSelectionManager.m240getCurrentDragPosition_m7T9E();
                            offsetM240getCurrentDragPosition_m7T9E5.getClass();
                            jM237access$updateSelection8UEBfa8 = TextFieldSelectionManager.m237access$updateSelection8UEBfa8(textFieldSelectionManager, value$foundation_release2, offsetM240getCurrentDragPosition_m7T9E5.packedValue, false, false, selectionAdjustment$Companion$$ExternalSyntheticLambda02, true);
                        }
                        TextRange.m747boximpl(jM237access$updateSelection8UEBfa8);
                    }
                }
                textFieldSelectionManager.updateFloatingToolbar(false);
            }

            public final void onEnd() {
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                ((SnapshotMutableStateImpl) textFieldSelectionManager.draggingHandle$delegate).setValue(null);
                ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(null);
                textFieldSelectionManager.updateFloatingToolbar(true);
                textFieldSelectionManager.dragBeginOffsetInText = null;
                boolean zM749getCollapsedimpl = TextRange.m749getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection);
                textFieldSelectionManager.setHandleState(zM749getCollapsedimpl ? HandleState.Cursor : HandleState.Selection);
                LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                if (legacyTextFieldState != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState.showSelectionHandleStart$delegate).setValue(Boolean.valueOf(!zM749getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)));
                }
                LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                if (legacyTextFieldState2 != null) {
                    ((SnapshotMutableStateImpl) legacyTextFieldState2.showSelectionHandleEnd$delegate).setValue(Boolean.valueOf(!zM749getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, false)));
                }
                LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager.state;
                if (legacyTextFieldState3 == null) {
                    return;
                }
                ((SnapshotMutableStateImpl) legacyTextFieldState3.showCursorHandle$delegate).setValue(Boolean.valueOf(zM749getCollapsedimpl && TextFieldSelectionManagerKt.isSelectionHandleInVisibleBound(textFieldSelectionManager, true)));
            }

            /* JADX WARN: Type inference failed for: r9v5, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onStart-k-4lQ0M */
            public final void mo204onStartk4lQ0M(long j) {
                long j2;
                TextLayoutResultProxy layoutResult;
                TextLayoutResultProxy layoutResult2;
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                if (textFieldSelectionManager.getEnabled()) {
                    MutableState mutableState = textFieldSelectionManager.draggingHandle$delegate;
                    if (((Handle) ((SnapshotMutableStateImpl) mutableState).getValue()) != null) {
                        return;
                    }
                    ((SnapshotMutableStateImpl) mutableState).setValue(Handle.SelectionEnd);
                    textFieldSelectionManager.previousRawDragOffset = -1;
                    textFieldSelectionManager.hideSelectionToolbar$foundation_release();
                    LegacyTextFieldState legacyTextFieldState = textFieldSelectionManager.state;
                    if (legacyTextFieldState == null || (layoutResult2 = legacyTextFieldState.getLayoutResult()) == null || !layoutResult2.m210isPositionOnTextk4lQ0M(j)) {
                        j2 = j;
                        LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager.state;
                        if (legacyTextFieldState2 != null && (layoutResult = legacyTextFieldState2.getLayoutResult()) != null) {
                            int iTransformedToOriginal = textFieldSelectionManager.offsetMapping.transformedToOriginal(layoutResult.m209getOffsetForPosition3MmeM6k(j2, true));
                            TextFieldValue textFieldValueM238createTextFieldValueFDrldGo = TextFieldSelectionManager.m238createTextFieldValueFDrldGo(textFieldSelectionManager.getValue$foundation_release().annotatedString, TextRangeKt.TextRange(iTransformedToOriginal, iTransformedToOriginal));
                            textFieldSelectionManager.enterSelectionMode$foundation_release(false);
                            HapticFeedback hapticFeedback = textFieldSelectionManager.hapticFeedBack;
                            if (hapticFeedback != null) {
                                HapticFeedbackType.Companion.getClass();
                                hapticFeedback.mo572performHapticFeedbackCdsT49E(HapticFeedbackType.Companion.m573getTextHandleMove5zf0vsI());
                            }
                            textFieldSelectionManager.onValueChange.mo781invoke(textFieldValueM238createTextFieldValueFDrldGo);
                        }
                    } else {
                        if (textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0) {
                            return;
                        }
                        textFieldSelectionManager.enterSelectionMode$foundation_release(false);
                        TextFieldValue value$foundation_release = textFieldSelectionManager.getValue$foundation_release();
                        TextRange.Companion.getClass();
                        TextFieldValue textFieldValueM780copy3r_uNRQ$default = TextFieldValue.m780copy3r_uNRQ$default(value$foundation_release, null, TextRange.Zero, 5);
                        SelectionAdjustment.Companion.getClass();
                        j2 = j;
                        textFieldSelectionManager.dragBeginOffsetInText = Integer.valueOf((int) (TextFieldSelectionManager.m237access$updateSelection8UEBfa8(textFieldSelectionManager, textFieldValueM780copy3r_uNRQ$default, j, true, false, SelectionAdjustment.Companion.Word, true) >> 32));
                    }
                    textFieldSelectionManager.setHandleState(HandleState.None);
                    textFieldSelectionManager.dragBeginPosition = j2;
                    ((SnapshotMutableStateImpl) textFieldSelectionManager.currentDragPosition$delegate).setValue(Offset.m395boximpl(j2));
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
            public final void mo202onDownk4lQ0M() {
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onUp() {
            }
        };
        this.mouseSelectionObserver = new MouseSelectionObserver() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$mouseSelectionObserver$1
            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onDrag-3MmeM6k */
            public final boolean mo230onDrag3MmeM6k(long j, SelectionAdjustment selectionAdjustment) {
                LegacyTextFieldState legacyTextFieldState;
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0 || (legacyTextFieldState = textFieldSelectionManager.state) == null || legacyTextFieldState.getLayoutResult() == null) {
                    return false;
                }
                updateMouseSelection(textFieldSelectionManager.getValue$foundation_release(), j, false, selectionAdjustment);
                return true;
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onStart-3MmeM6k */
            public final boolean mo231onStart3MmeM6k(long j, SelectionAdjustment selectionAdjustment) {
                LegacyTextFieldState legacyTextFieldState;
                TextFieldSelectionManager textFieldSelectionManager = this.this$0;
                if (!textFieldSelectionManager.getEnabled() || textFieldSelectionManager.getValue$foundation_release().annotatedString.text.length() == 0 || (legacyTextFieldState = textFieldSelectionManager.state) == null || legacyTextFieldState.getLayoutResult() == null) {
                    return false;
                }
                FocusRequester focusRequester = textFieldSelectionManager.focusRequester;
                if (focusRequester != null) {
                    FocusRequester.m378requestFocus3ESFkO8$default(focusRequester);
                }
                textFieldSelectionManager.dragBeginPosition = j;
                textFieldSelectionManager.previousRawDragOffset = -1;
                textFieldSelectionManager.enterSelectionMode$foundation_release(true);
                updateMouseSelection(textFieldSelectionManager.getValue$foundation_release(), textFieldSelectionManager.dragBeginPosition, true, selectionAdjustment);
                return true;
            }

            public final void updateMouseSelection(TextFieldValue textFieldValue, long j, boolean z, SelectionAdjustment selectionAdjustment) {
                this.this$0.setHandleState(TextRange.m749getCollapsedimpl(TextFieldSelectionManager.m237access$updateSelection8UEBfa8(this.this$0, textFieldValue, j, z, false, selectionAdjustment, false)) ? HandleState.Cursor : HandleState.Selection);
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
