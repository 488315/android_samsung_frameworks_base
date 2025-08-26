package androidx.compose.foundation.text.input.internal;

import android.R;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.DeleteGesture;
import android.view.inputmethod.DeleteRangeGesture;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.HandwritingGesture;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InsertGesture;
import android.view.inputmethod.JoinOrSplitGesture;
import android.view.inputmethod.PreviewableHandwritingGesture;
import android.view.inputmethod.RemoveSpaceGesture;
import android.view.inputmethod.SelectGesture;
import android.view.inputmethod.SelectRangeGesture;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.TextGranularity;
import androidx.compose.ui.text.TextInclusionStrategy;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.DeleteSurroundingTextCommand;
import androidx.compose.ui.text.input.DeleteSurroundingTextInCodePointsCommand;
import androidx.compose.ui.text.input.EditCommand;
import androidx.compose.ui.text.input.FinishComposingTextCommand;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.SetComposingRegionCommand;
import androidx.compose.ui.text.input.SetComposingTextCommand;
import androidx.compose.ui.text.input.SetSelectionCommand;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextFieldValueKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.MatcherMatchResult;
import kotlin.text.Regex;

/* loaded from: classes.dex */
public final class RecordingInputConnection implements InputConnection {
    public final boolean autoCorrect;
    public int batchDepth;
    public int currentExtractedTextRequestToken;
    public final List editCommands;
    public final InputEventCallback2 eventCallback;
    public boolean extractedTextMonitorMode;
    public boolean isActive;
    public final LegacyTextFieldState legacyTextFieldState;
    public final TextFieldSelectionManager textFieldSelectionManager;
    public TextFieldValue textFieldValue;
    public final ViewConfiguration viewConfiguration;

    /* renamed from: androidx.compose.foundation.text.input.internal.RecordingInputConnection$performHandwritingGesture$1, reason: invalid class name */
    final class AnonymousClass1 extends Lambda implements Function1 {
        public AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            RecordingInputConnection.this.addEditCommandWithBatch((EditCommand) obj);
            return Unit.INSTANCE;
        }
    }

    public RecordingInputConnection(TextFieldValue textFieldValue, InputEventCallback2 inputEventCallback2, boolean z, LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager, ViewConfiguration viewConfiguration) {
        this.eventCallback = inputEventCallback2;
        this.autoCorrect = z;
        this.legacyTextFieldState = legacyTextFieldState;
        this.textFieldSelectionManager = textFieldSelectionManager;
        this.viewConfiguration = viewConfiguration;
        this.textFieldValue = textFieldValue;
        this.editCommands = new ArrayList();
        this.isActive = true;
    }

    public final void addEditCommandWithBatch(EditCommand editCommand) {
        this.batchDepth++;
        try {
            ((ArrayList) this.editCommands).add(editCommand);
        } finally {
            endBatchEditInternal();
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean beginBatchEdit() {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        this.batchDepth++;
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean clearMetaKeyStates(int i) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final void closeConnection() {
        ((ArrayList) this.editCommands).clear();
        this.batchDepth = 0;
        this.isActive = false;
        LegacyTextInputMethodRequest legacyTextInputMethodRequest = LegacyTextInputMethodRequest.this;
        int size = ((ArrayList) legacyTextInputMethodRequest.ics).size();
        for (int i = 0; i < size; i++) {
            if (Intrinsics.areEqual(((WeakReference) ((ArrayList) legacyTextInputMethodRequest.ics).get(i)).get(), this)) {
                ((ArrayList) legacyTextInputMethodRequest.ics).remove(i);
                return;
            }
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCompletion(CompletionInfo completionInfo) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        boolean z = this.isActive;
        if (z) {
            return false;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitCorrection(CorrectionInfo correctionInfo) {
        boolean z = this.isActive;
        return z ? this.autoCorrect : z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean commitText(CharSequence charSequence, int i) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new CommitTextCommand(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new DeleteSurroundingTextCommand(i, i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new DeleteSurroundingTextInCodePointsCommand(i, i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean endBatchEdit() {
        return endBatchEditInternal();
    }

    public final boolean endBatchEditInternal() {
        int i = this.batchDepth - 1;
        this.batchDepth = i;
        if (i == 0 && !this.editCommands.isEmpty()) {
            LegacyTextInputMethodRequest.this.onEditCommand.mo781invoke(new ArrayList(this.editCommands));
            ((ArrayList) this.editCommands).clear();
        }
        return this.batchDepth > 0;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean finishComposingText() {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new FinishComposingTextCommand());
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final int getCursorCapsMode(int i) {
        TextFieldValue textFieldValue = this.textFieldValue;
        return TextUtils.getCapsMode(textFieldValue.annotatedString.text, TextRange.m752getMinimpl(textFieldValue.selection), i);
    }

    @Override // android.view.inputmethod.InputConnection
    public final ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        boolean z = (i & 1) != 0;
        this.extractedTextMonitorMode = z;
        if (z) {
            this.currentExtractedTextRequestToken = extractedTextRequest != null ? extractedTextRequest.token : 0;
        }
        return RecordingInputConnection_androidKt.access$toExtractedText(this.textFieldValue);
    }

    @Override // android.view.inputmethod.InputConnection
    public final Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getSelectedText(int i) {
        if (TextRange.m749getCollapsedimpl(this.textFieldValue.selection)) {
            return null;
        }
        return TextFieldValueKt.getSelectedText(this.textFieldValue).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextAfterCursor(int i, int i2) {
        return TextFieldValueKt.getTextAfterSelection(this.textFieldValue, i).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final CharSequence getTextBeforeCursor(int i, int i2) {
        return TextFieldValueKt.getTextBeforeSelection(this.textFieldValue, i).text;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performContextMenuAction(int i) {
        boolean z = this.isActive;
        if (z) {
            z = false;
            switch (i) {
                case R.id.selectAll:
                    addEditCommandWithBatch(new SetSelectionCommand(0, this.textFieldValue.annotatedString.text.length()));
                    break;
                case R.id.cut:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_clearForcedDisplaySizeDensity);
                    return false;
                case R.id.copy:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_startSmartView);
                    return false;
                case R.id.paste:
                    sendSynthesizedKeyEvent(IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView);
                    return false;
                default:
                    return false;
            }
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performEditorAction(int i) {
        int i2;
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        if (i != 0) {
            switch (i) {
                case 2:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Go;
                    break;
                case 3:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Search;
                    break;
                case 4:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Send;
                    break;
                case 5:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Next;
                    break;
                case 6:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Done;
                    break;
                case 7:
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Previous;
                    break;
                default:
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "IME sends unsupported Editor Action: ", "RecordingIC");
                    ImeAction.Companion.getClass();
                    i2 = ImeAction.Default;
                    break;
            }
        } else {
            ImeAction.Companion.getClass();
            i2 = ImeAction.Default;
        }
        LegacyTextInputMethodRequest.this.onImeActionPerformed.mo781invoke(ImeAction.m774boximpl(i2));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, final IntConsumer intConsumer) {
        int i;
        long jM737getRangeForRect86BmAI;
        char c;
        String string;
        int i2;
        int iM216access$getOffsetForHandwritingGestured4ec7I;
        TextLayoutResultProxy layoutResult;
        TextLayoutResult textLayoutResult;
        int iM216access$getOffsetForHandwritingGestured4ec7I2;
        TextLayoutResultProxy layoutResult2;
        TextLayoutResult textLayoutResult2;
        TextLayoutResult textLayoutResult3;
        TextLayoutInput textLayoutInput;
        Api34LegacyPerformHandwritingGestureImpl api34LegacyPerformHandwritingGestureImpl = Api34LegacyPerformHandwritingGestureImpl.INSTANCE;
        LegacyTextFieldState legacyTextFieldState = this.legacyTextFieldState;
        TextFieldSelectionManager textFieldSelectionManager = this.textFieldSelectionManager;
        ViewConfiguration viewConfiguration = this.viewConfiguration;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        api34LegacyPerformHandwritingGestureImpl.getClass();
        final int iFallbackOnLegacyTextField = 3;
        if (legacyTextFieldState != null) {
            HandwritingGestureApi34.INSTANCE.getClass();
            AnnotatedString annotatedString = legacyTextFieldState.untransformedText;
            if (annotatedString != null) {
                TextLayoutResultProxy layoutResult3 = legacyTextFieldState.getLayoutResult();
                if (annotatedString.equals((layoutResult3 == null || (textLayoutResult3 = layoutResult3.value) == null || (textLayoutInput = textLayoutResult3.layoutInput) == null) ? null : textLayoutInput.text)) {
                    if (handwritingGesture instanceof SelectGesture) {
                        SelectGesture selectGesture = (SelectGesture) handwritingGesture;
                        Rect composeRect = RectHelper_androidKt.toComposeRect(selectGesture.getSelectionArea());
                        int iM215toTextGranularityNUwxegE = HandwritingGestureApi34.m215toTextGranularityNUwxegE(selectGesture.getGranularity());
                        TextInclusionStrategy.Companion.getClass();
                        long jM219getRangeForScreenRectOH9lIzo = HandwritingGesture_androidKt.m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, composeRect, iM215toTextGranularityNUwxegE, TextInclusionStrategy.Companion.ContainsCenter);
                        if (TextRange.m749getCollapsedimpl(jM219getRangeForScreenRectOH9lIzo)) {
                            iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(selectGesture, anonymousClass1);
                        } else {
                            anonymousClass1.mo781invoke(new SetSelectionCommand((int) (jM219getRangeForScreenRectOH9lIzo >> 32), (int) (jM219getRangeForScreenRectOH9lIzo & 4294967295L)));
                            if (textFieldSelectionManager != null) {
                                textFieldSelectionManager.enterSelectionMode$foundation_release(true);
                            }
                            iFallbackOnLegacyTextField = 1;
                        }
                    } else {
                        if (handwritingGesture instanceof DeleteGesture) {
                            DeleteGesture deleteGesture = (DeleteGesture) handwritingGesture;
                            int iM215toTextGranularityNUwxegE2 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(deleteGesture.getGranularity());
                            Rect composeRect2 = RectHelper_androidKt.toComposeRect(deleteGesture.getDeletionArea());
                            TextInclusionStrategy.Companion.getClass();
                            long jM219getRangeForScreenRectOH9lIzo2 = HandwritingGesture_androidKt.m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, composeRect2, iM215toTextGranularityNUwxegE2, TextInclusionStrategy.Companion.ContainsCenter);
                            if (TextRange.m749getCollapsedimpl(jM219getRangeForScreenRectOH9lIzo2)) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(deleteGesture, anonymousClass1);
                            } else {
                                TextGranularity.Companion.getClass();
                                HandwritingGestureApi34.m214performDeletionOnLegacyTextFieldvJH6DeI(jM219getRangeForScreenRectOH9lIzo2, annotatedString, iM215toTextGranularityNUwxegE2 == TextGranularity.Word, anonymousClass1);
                                iFallbackOnLegacyTextField = 1;
                            }
                        } else if (handwritingGesture instanceof SelectRangeGesture) {
                            SelectRangeGesture selectRangeGesture = (SelectRangeGesture) handwritingGesture;
                            Rect composeRect3 = RectHelper_androidKt.toComposeRect(selectRangeGesture.getSelectionStartArea());
                            Rect composeRect4 = RectHelper_androidKt.toComposeRect(selectRangeGesture.getSelectionEndArea());
                            int iM215toTextGranularityNUwxegE3 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(selectRangeGesture.getGranularity());
                            TextInclusionStrategy.Companion.getClass();
                            long jM217access$getRangeForScreenRectsO048IG0 = HandwritingGesture_androidKt.m217access$getRangeForScreenRectsO048IG0(legacyTextFieldState, composeRect3, composeRect4, iM215toTextGranularityNUwxegE3, TextInclusionStrategy.Companion.ContainsCenter);
                            if (TextRange.m749getCollapsedimpl(jM217access$getRangeForScreenRectsO048IG0)) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(selectRangeGesture, anonymousClass1);
                            } else {
                                anonymousClass1.mo781invoke(new SetSelectionCommand((int) (jM217access$getRangeForScreenRectsO048IG0 >> 32), (int) (jM217access$getRangeForScreenRectsO048IG0 & 4294967295L)));
                                if (textFieldSelectionManager != null) {
                                    textFieldSelectionManager.enterSelectionMode$foundation_release(true);
                                }
                                iFallbackOnLegacyTextField = 1;
                            }
                        } else if (handwritingGesture instanceof DeleteRangeGesture) {
                            DeleteRangeGesture deleteRangeGesture = (DeleteRangeGesture) handwritingGesture;
                            int iM215toTextGranularityNUwxegE4 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(deleteRangeGesture.getGranularity());
                            Rect composeRect5 = RectHelper_androidKt.toComposeRect(deleteRangeGesture.getDeletionStartArea());
                            Rect composeRect6 = RectHelper_androidKt.toComposeRect(deleteRangeGesture.getDeletionEndArea());
                            TextInclusionStrategy.Companion.getClass();
                            long jM217access$getRangeForScreenRectsO048IG02 = HandwritingGesture_androidKt.m217access$getRangeForScreenRectsO048IG0(legacyTextFieldState, composeRect5, composeRect6, iM215toTextGranularityNUwxegE4, TextInclusionStrategy.Companion.ContainsCenter);
                            if (TextRange.m749getCollapsedimpl(jM217access$getRangeForScreenRectsO048IG02)) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(deleteRangeGesture, anonymousClass1);
                            } else {
                                TextGranularity.Companion.getClass();
                                HandwritingGestureApi34.m214performDeletionOnLegacyTextFieldvJH6DeI(jM217access$getRangeForScreenRectsO048IG02, annotatedString, iM215toTextGranularityNUwxegE4 == TextGranularity.Word, anonymousClass1);
                                iFallbackOnLegacyTextField = 1;
                            }
                        } else if (handwritingGesture instanceof JoinOrSplitGesture) {
                            JoinOrSplitGesture joinOrSplitGesture = (JoinOrSplitGesture) handwritingGesture;
                            if (viewConfiguration == null || (iM216access$getOffsetForHandwritingGestured4ec7I2 = HandwritingGesture_androidKt.m216access$getOffsetForHandwritingGestured4ec7I(legacyTextFieldState, HandwritingGesture_androidKt.access$toOffset(joinOrSplitGesture.getJoinOrSplitPoint()), viewConfiguration)) == -1 || ((layoutResult2 = legacyTextFieldState.getLayoutResult()) != null && (textLayoutResult2 = layoutResult2.value) != null && HandwritingGesture_androidKt.access$isBiDiBoundary(textLayoutResult2, iM216access$getOffsetForHandwritingGestured4ec7I2))) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(joinOrSplitGesture, anonymousClass1);
                            } else {
                                int iCharCount = iM216access$getOffsetForHandwritingGestured4ec7I2;
                                while (iCharCount > 0) {
                                    int iCodePointBefore = Character.codePointBefore(annotatedString, iCharCount);
                                    if (!HandwritingGesture_androidKt.isWhitespace(iCodePointBefore)) {
                                        break;
                                    } else {
                                        iCharCount -= Character.charCount(iCodePointBefore);
                                    }
                                }
                                while (iM216access$getOffsetForHandwritingGestured4ec7I2 < annotatedString.text.length()) {
                                    int iCodePointAt = Character.codePointAt(annotatedString, iM216access$getOffsetForHandwritingGestured4ec7I2);
                                    if (!HandwritingGesture_androidKt.isWhitespace(iCodePointAt)) {
                                        break;
                                    } else {
                                        iM216access$getOffsetForHandwritingGestured4ec7I2 += Character.charCount(iCodePointAt);
                                    }
                                }
                                long jTextRange = TextRangeKt.TextRange(iCharCount, iM216access$getOffsetForHandwritingGestured4ec7I2);
                                if (TextRange.m749getCollapsedimpl(jTextRange)) {
                                    int i3 = (int) (jTextRange >> 32);
                                    anonymousClass1.mo781invoke(new HandwritingGesture_androidKt$compoundEditCommand$1(new EditCommand[]{new SetSelectionCommand(i3, i3), new CommitTextCommand(" ", 1)}));
                                } else {
                                    HandwritingGestureApi34.m214performDeletionOnLegacyTextFieldvJH6DeI(jTextRange, annotatedString, false, anonymousClass1);
                                }
                                iFallbackOnLegacyTextField = 1;
                            }
                        } else if (handwritingGesture instanceof InsertGesture) {
                            InsertGesture insertGesture = (InsertGesture) handwritingGesture;
                            if (viewConfiguration == null || (iM216access$getOffsetForHandwritingGestured4ec7I = HandwritingGesture_androidKt.m216access$getOffsetForHandwritingGestured4ec7I(legacyTextFieldState, HandwritingGesture_androidKt.access$toOffset(insertGesture.getInsertionPoint()), viewConfiguration)) == -1 || ((layoutResult = legacyTextFieldState.getLayoutResult()) != null && (textLayoutResult = layoutResult.value) != null && HandwritingGesture_androidKt.access$isBiDiBoundary(textLayoutResult, iM216access$getOffsetForHandwritingGestured4ec7I))) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(insertGesture, anonymousClass1);
                            } else {
                                anonymousClass1.mo781invoke(new HandwritingGesture_androidKt$compoundEditCommand$1(new EditCommand[]{new SetSelectionCommand(iM216access$getOffsetForHandwritingGestured4ec7I, iM216access$getOffsetForHandwritingGestured4ec7I), new CommitTextCommand(insertGesture.getTextToInsert(), 1)}));
                                iFallbackOnLegacyTextField = 1;
                            }
                        } else if (handwritingGesture instanceof RemoveSpaceGesture) {
                            RemoveSpaceGesture removeSpaceGesture = (RemoveSpaceGesture) handwritingGesture;
                            TextLayoutResultProxy layoutResult4 = legacyTextFieldState.getLayoutResult();
                            TextLayoutResult textLayoutResult4 = layoutResult4 != null ? layoutResult4.value : null;
                            long jAccess$toOffset = HandwritingGesture_androidKt.access$toOffset(removeSpaceGesture.getStartPoint());
                            long jAccess$toOffset2 = HandwritingGesture_androidKt.access$toOffset(removeSpaceGesture.getEndPoint());
                            LayoutCoordinates layoutCoordinates = legacyTextFieldState.getLayoutCoordinates();
                            if (textLayoutResult4 == null || layoutCoordinates == null) {
                                i = 0;
                                TextRange.Companion.getClass();
                                jM737getRangeForRect86BmAI = TextRange.Zero;
                            } else {
                                long jMo618screenToLocalMKHz9U = layoutCoordinates.mo618screenToLocalMKHz9U(jAccess$toOffset);
                                long jMo618screenToLocalMKHz9U2 = layoutCoordinates.mo618screenToLocalMKHz9U(jAccess$toOffset2);
                                MultiParagraph multiParagraph = textLayoutResult4.multiParagraph;
                                int iM218getLineForHandwritingGestured4ec7I = HandwritingGesture_androidKt.m218getLineForHandwritingGestured4ec7I(multiParagraph, jMo618screenToLocalMKHz9U, viewConfiguration);
                                int iM218getLineForHandwritingGestured4ec7I2 = HandwritingGesture_androidKt.m218getLineForHandwritingGestured4ec7I(multiParagraph, jMo618screenToLocalMKHz9U2, viewConfiguration);
                                if (iM218getLineForHandwritingGestured4ec7I != -1) {
                                    if (iM218getLineForHandwritingGestured4ec7I2 != -1) {
                                        iM218getLineForHandwritingGestured4ec7I = Math.min(iM218getLineForHandwritingGestured4ec7I, iM218getLineForHandwritingGestured4ec7I2);
                                    }
                                    iM218getLineForHandwritingGestured4ec7I2 = iM218getLineForHandwritingGestured4ec7I;
                                } else if (iM218getLineForHandwritingGestured4ec7I2 == -1) {
                                    TextRange.Companion.getClass();
                                    jM737getRangeForRect86BmAI = TextRange.Zero;
                                    i = 0;
                                }
                                float lineBottom = (multiParagraph.getLineBottom(iM218getLineForHandwritingGestured4ec7I2) + multiParagraph.getLineTop(iM218getLineForHandwritingGestured4ec7I2)) / 2;
                                int i4 = (int) (jMo618screenToLocalMKHz9U >> 32);
                                int i5 = (int) (jMo618screenToLocalMKHz9U2 >> 32);
                                Rect rect = new Rect(Math.min(Float.intBitsToFloat(i4), Float.intBitsToFloat(i5)), lineBottom - 0.1f, Math.max(Float.intBitsToFloat(i4), Float.intBitsToFloat(i5)), lineBottom + 0.1f);
                                TextGranularity.Companion.getClass();
                                TextInclusionStrategy.Companion.getClass();
                                i = 0;
                                jM737getRangeForRect86BmAI = multiParagraph.m737getRangeForRect86BmAI(rect, 0, TextInclusionStrategy.Companion.AnyOverlap);
                            }
                            if (TextRange.m749getCollapsedimpl(jM737getRangeForRect86BmAI)) {
                                iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(removeSpaceGesture, anonymousClass1);
                            } else {
                                final Ref$IntRef ref$IntRef = new Ref$IntRef();
                                ref$IntRef.element = -1;
                                final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
                                ref$IntRef2.element = -1;
                                String str = annotatedString.subSequence(TextRange.m752getMinimpl(jM737getRangeForRect86BmAI), TextRange.m751getMaximpl(jM737getRangeForRect86BmAI)).text;
                                Regex regex = new Regex("\\s+");
                                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.input.internal.HandwritingGestureApi34$performRemoveSpaceGesture$newText$2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        MatchResult matchResult = (MatchResult) obj;
                                        Ref$IntRef ref$IntRef3 = ref$IntRef;
                                        if (ref$IntRef3.element == -1) {
                                            Matcher matcher = ((MatcherMatchResult) matchResult).matcher;
                                            ref$IntRef3.element = RangesKt___RangesKt.until(matcher.start(), matcher.end()).first;
                                        }
                                        Ref$IntRef ref$IntRef4 = ref$IntRef2;
                                        Matcher matcher2 = ((MatcherMatchResult) matchResult).matcher;
                                        ref$IntRef4.element = RangesKt___RangesKt.until(matcher2.start(), matcher2.end()).last + 1;
                                        return "";
                                    }
                                };
                                MatcherMatchResult matcherMatchResultFind = regex.find(str);
                                if (matcherMatchResultFind == null) {
                                    string = str.toString();
                                    c = ' ';
                                } else {
                                    int length = str.length();
                                    StringBuilder sb = new StringBuilder(length);
                                    c = ' ';
                                    do {
                                        Matcher matcher = matcherMatchResultFind.matcher;
                                        sb.append((CharSequence) str, i, RangesKt___RangesKt.until(matcher.start(), matcher.end()).first);
                                        function1.mo781invoke(matcherMatchResultFind);
                                        sb.append((CharSequence) "");
                                        Matcher matcher2 = matcherMatchResultFind.matcher;
                                        i = RangesKt___RangesKt.until(matcher2.start(), matcher2.end()).last + 1;
                                        matcherMatchResultFind = matcherMatchResultFind.next();
                                        if (i >= length) {
                                            break;
                                        }
                                    } while (matcherMatchResultFind != null);
                                    if (i < length) {
                                        sb.append((CharSequence) str, i, length);
                                    }
                                    string = sb.toString();
                                }
                                int i6 = ref$IntRef.element;
                                if (i6 == -1 || (i2 = ref$IntRef2.element) == -1) {
                                    iFallbackOnLegacyTextField = HandwritingGestureApi34.fallbackOnLegacyTextField(removeSpaceGesture, anonymousClass1);
                                } else {
                                    int i7 = (int) (jM737getRangeForRect86BmAI >> c);
                                    anonymousClass1.mo781invoke(new HandwritingGesture_androidKt$compoundEditCommand$1(new EditCommand[]{new SetSelectionCommand(i7 + i6, i7 + i2), new CommitTextCommand(string.substring(i6, string.length() - (TextRange.m750getLengthimpl(jM737getRangeForRect86BmAI) - ref$IntRef2.element)), 1)}));
                                    iFallbackOnLegacyTextField = 1;
                                }
                            }
                        } else {
                            iFallbackOnLegacyTextField = 2;
                        }
                    }
                }
            }
        }
        if (intConsumer == null) {
            return;
        }
        if (executor != null) {
            executor.execute(new Runnable() { // from class: androidx.compose.foundation.text.input.internal.Api34LegacyPerformHandwritingGestureImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IntConsumer intConsumer2 = intConsumer;
                    int i8 = iFallbackOnLegacyTextField;
                    Api34LegacyPerformHandwritingGestureImpl api34LegacyPerformHandwritingGestureImpl2 = Api34LegacyPerformHandwritingGestureImpl.INSTANCE;
                    intConsumer2.accept(i8);
                }
            });
        } else {
            intConsumer.accept(iFallbackOnLegacyTextField);
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean performPrivateCommand(String str, Bundle bundle) {
        boolean z = this.isActive;
        if (z) {
            return true;
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean previewHandwritingGesture(PreviewableHandwritingGesture previewableHandwritingGesture, CancellationSignal cancellationSignal) {
        TextLayoutResult textLayoutResult;
        TextLayoutInput textLayoutInput;
        Api34LegacyPerformHandwritingGestureImpl api34LegacyPerformHandwritingGestureImpl = Api34LegacyPerformHandwritingGestureImpl.INSTANCE;
        LegacyTextFieldState legacyTextFieldState = this.legacyTextFieldState;
        final TextFieldSelectionManager textFieldSelectionManager = this.textFieldSelectionManager;
        api34LegacyPerformHandwritingGestureImpl.getClass();
        if (legacyTextFieldState == null) {
            return false;
        }
        HandwritingGestureApi34.INSTANCE.getClass();
        AnnotatedString annotatedString = legacyTextFieldState.untransformedText;
        if (annotatedString == null) {
            return false;
        }
        TextLayoutResultProxy layoutResult = legacyTextFieldState.getLayoutResult();
        if (!annotatedString.equals((layoutResult == null || (textLayoutResult = layoutResult.value) == null || (textLayoutInput = textLayoutResult.layoutInput) == null) ? null : textLayoutInput.text)) {
            return false;
        }
        if (previewableHandwritingGesture instanceof SelectGesture) {
            SelectGesture selectGesture = (SelectGesture) previewableHandwritingGesture;
            if (textFieldSelectionManager != null) {
                Rect composeRect = RectHelper_androidKt.toComposeRect(selectGesture.getSelectionArea());
                int iM215toTextGranularityNUwxegE = HandwritingGestureApi34.m215toTextGranularityNUwxegE(selectGesture.getGranularity());
                TextInclusionStrategy.Companion.getClass();
                textFieldSelectionManager.m243setSelectionPreviewHighlight5zctL8$foundation_release(HandwritingGesture_androidKt.m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, composeRect, iM215toTextGranularityNUwxegE, TextInclusionStrategy.Companion.ContainsCenter));
            }
        } else if (previewableHandwritingGesture instanceof DeleteGesture) {
            DeleteGesture deleteGesture = (DeleteGesture) previewableHandwritingGesture;
            if (textFieldSelectionManager != null) {
                Rect composeRect2 = RectHelper_androidKt.toComposeRect(deleteGesture.getDeletionArea());
                int iM215toTextGranularityNUwxegE2 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(deleteGesture.getGranularity());
                TextInclusionStrategy.Companion.getClass();
                textFieldSelectionManager.m242setDeletionPreviewHighlight5zctL8$foundation_release(HandwritingGesture_androidKt.m219getRangeForScreenRectOH9lIzo(legacyTextFieldState, composeRect2, iM215toTextGranularityNUwxegE2, TextInclusionStrategy.Companion.ContainsCenter));
            }
        } else if (previewableHandwritingGesture instanceof SelectRangeGesture) {
            SelectRangeGesture selectRangeGesture = (SelectRangeGesture) previewableHandwritingGesture;
            if (textFieldSelectionManager != null) {
                Rect composeRect3 = RectHelper_androidKt.toComposeRect(selectRangeGesture.getSelectionStartArea());
                Rect composeRect4 = RectHelper_androidKt.toComposeRect(selectRangeGesture.getSelectionEndArea());
                int iM215toTextGranularityNUwxegE3 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(selectRangeGesture.getGranularity());
                TextInclusionStrategy.Companion.getClass();
                textFieldSelectionManager.m243setSelectionPreviewHighlight5zctL8$foundation_release(HandwritingGesture_androidKt.m217access$getRangeForScreenRectsO048IG0(legacyTextFieldState, composeRect3, composeRect4, iM215toTextGranularityNUwxegE3, TextInclusionStrategy.Companion.ContainsCenter));
            }
        } else {
            if (!(previewableHandwritingGesture instanceof DeleteRangeGesture)) {
                return false;
            }
            DeleteRangeGesture deleteRangeGesture = (DeleteRangeGesture) previewableHandwritingGesture;
            if (textFieldSelectionManager != null) {
                Rect composeRect5 = RectHelper_androidKt.toComposeRect(deleteRangeGesture.getDeletionStartArea());
                Rect composeRect6 = RectHelper_androidKt.toComposeRect(deleteRangeGesture.getDeletionEndArea());
                int iM215toTextGranularityNUwxegE4 = HandwritingGestureApi34.m215toTextGranularityNUwxegE(deleteRangeGesture.getGranularity());
                TextInclusionStrategy.Companion.getClass();
                textFieldSelectionManager.m242setDeletionPreviewHighlight5zctL8$foundation_release(HandwritingGesture_androidKt.m217access$getRangeForScreenRectsO048IG0(legacyTextFieldState, composeRect5, composeRect6, iM215toTextGranularityNUwxegE4, TextInclusionStrategy.Companion.ContainsCenter));
            }
        }
        if (cancellationSignal == null) {
            return true;
        }
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.compose.foundation.text.input.internal.HandwritingGestureApi34$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                TextFieldSelectionManager textFieldSelectionManager2 = textFieldSelectionManager;
                HandwritingGestureApi34 handwritingGestureApi34 = HandwritingGestureApi34.INSTANCE;
                if (textFieldSelectionManager2 != null) {
                    LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager2.state;
                    if (legacyTextFieldState2 != null) {
                        TextRange.Companion.getClass();
                        long j = TextRange.Zero;
                        ((SnapshotMutableStateImpl) legacyTextFieldState2.deletionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j));
                    }
                    LegacyTextFieldState legacyTextFieldState3 = textFieldSelectionManager2.state;
                    if (legacyTextFieldState3 == null) {
                        return;
                    }
                    TextRange.Companion.getClass();
                    long j2 = TextRange.Zero;
                    ((SnapshotMutableStateImpl) legacyTextFieldState3.selectionPreviewHighlightRange$delegate).setValue(TextRange.m747boximpl(j2));
                }
            }
        });
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean reportFullscreenMode(boolean z) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean requestCursorUpdates(int i) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        boolean z4 = (i & 16) != 0;
        boolean z5 = (i & 8) != 0;
        boolean z6 = (i & 4) != 0;
        boolean z7 = (i & 32) != 0;
        if (!z4 && !z5 && !z6 && !z7) {
            z7 = true;
            z4 = true;
            z5 = true;
            z6 = true;
        }
        LegacyCursorAnchorInfoController legacyCursorAnchorInfoController = LegacyTextInputMethodRequest.this.cursorAnchorInfoController;
        synchronized (legacyCursorAnchorInfoController.lock) {
            try {
                legacyCursorAnchorInfoController.includeInsertionMarker = z4;
                legacyCursorAnchorInfoController.includeCharacterBounds = z5;
                legacyCursorAnchorInfoController.includeEditorBounds = z6;
                legacyCursorAnchorInfoController.includeLineBounds = z7;
                if (z2) {
                    legacyCursorAnchorInfoController.hasPendingImmediateRequest = true;
                    if (legacyCursorAnchorInfoController.textFieldValue != null) {
                        legacyCursorAnchorInfoController.updateCursorAnchorInfo();
                    }
                }
                legacyCursorAnchorInfoController.monitorEnabled = z3;
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean sendKeyEvent(KeyEvent keyEvent) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        ((BaseInputConnection) LegacyTextInputMethodRequest.this.baseInputConnection$delegate.getValue()).sendKeyEvent(keyEvent);
        return true;
    }

    public final void sendSynthesizedKeyEvent(int i) {
        sendKeyEvent(new KeyEvent(0, i));
        sendKeyEvent(new KeyEvent(1, i));
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingRegion(int i, int i2) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new SetComposingRegionCommand(i, i2));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setComposingText(CharSequence charSequence, int i) {
        boolean z = this.isActive;
        if (z) {
            addEditCommandWithBatch(new SetComposingTextCommand(String.valueOf(charSequence), i));
        }
        return z;
    }

    @Override // android.view.inputmethod.InputConnection
    public final boolean setSelection(int i, int i2) {
        boolean z = this.isActive;
        if (!z) {
            return z;
        }
        addEditCommandWithBatch(new SetSelectionCommand(i, i2));
        return true;
    }

    public /* synthetic */ RecordingInputConnection(TextFieldValue textFieldValue, InputEventCallback2 inputEventCallback2, boolean z, LegacyTextFieldState legacyTextFieldState, TextFieldSelectionManager textFieldSelectionManager, ViewConfiguration viewConfiguration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(textFieldValue, inputEventCallback2, z, (i & 8) != 0 ? null : legacyTextFieldState, (i & 16) != 0 ? null : textFieldSelectionManager, (i & 32) != 0 ? null : viewConfiguration);
    }
}
