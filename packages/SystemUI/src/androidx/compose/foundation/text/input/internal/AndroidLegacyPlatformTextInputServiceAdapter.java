package androidx.compose.foundation.text.input.internal;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidLegacyPlatformTextInputServiceAdapter extends LegacyPlatformTextInputServiceAdapter {
    public SharedFlowImpl backingStylusHandwritingTrigger;
    public LegacyTextInputMethodRequest currentRequest;
    public StandaloneCoroutine job;

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void notifyFocusedRect(Rect rect) {
        android.graphics.Rect rect2;
        LegacyTextInputMethodRequest legacyTextInputMethodRequest = this.currentRequest;
        if (legacyTextInputMethodRequest != null) {
            legacyTextInputMethodRequest.focusedRect = new android.graphics.Rect(MathKt__MathJVMKt.roundToInt(rect.left), MathKt__MathJVMKt.roundToInt(rect.top), MathKt__MathJVMKt.roundToInt(rect.right), MathKt__MathJVMKt.roundToInt(rect.bottom));
            if (!((ArrayList) legacyTextInputMethodRequest.ics).isEmpty() || (rect2 = legacyTextInputMethodRequest.focusedRect) == null) {
                return;
            }
            legacyTextInputMethodRequest.view.requestRectangleOnScreen(new android.graphics.Rect(rect2));
        }
    }

    public final void startInput(Function1 function1) {
        LegacyAdaptingPlatformTextInputModifierNode legacyAdaptingPlatformTextInputModifierNode = this.textInputModifierNode;
        if (legacyAdaptingPlatformTextInputModifierNode == null) {
            return;
        }
        this.job = legacyAdaptingPlatformTextInputModifierNode.isAttached ? BuildersKt.launch$default(legacyAdaptingPlatformTextInputModifierNode.getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new LegacyAdaptingPlatformTextInputModifierNode$launchTextInputSession$1(legacyAdaptingPlatformTextInputModifierNode, new AndroidLegacyPlatformTextInputServiceAdapter$startInput$2(function1, this, legacyAdaptingPlatformTextInputModifierNode, null), null), 1) : null;
    }

    @Override // androidx.compose.foundation.text.input.internal.LegacyPlatformTextInputServiceAdapter
    public final void startStylusHandwriting() {
        SharedFlowImpl sharedFlowImpl = this.backingStylusHandwritingTrigger;
        if (sharedFlowImpl == null) {
            sharedFlowImpl = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_LATEST, 2);
            this.backingStylusHandwritingTrigger = sharedFlowImpl;
        }
        sharedFlowImpl.tryEmit(Unit.INSTANCE);
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void stopInput() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.job = null;
        SharedFlowImpl sharedFlowImpl = this.backingStylusHandwritingTrigger;
        if (sharedFlowImpl == null) {
            sharedFlowImpl = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_LATEST, 2);
            this.backingStylusHandwritingTrigger = sharedFlowImpl;
        }
        sharedFlowImpl.resetReplayCache();
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void updateState(TextFieldValue textFieldValue, TextFieldValue textFieldValue2) {
        LegacyTextInputMethodRequest legacyTextInputMethodRequest = this.currentRequest;
        if (legacyTextInputMethodRequest != null) {
            boolean z = (TextRange.m746equalsimpl0(legacyTextInputMethodRequest.state.selection, textFieldValue2.selection) && Intrinsics.areEqual(legacyTextInputMethodRequest.state.composition, textFieldValue2.composition)) ? false : true;
            legacyTextInputMethodRequest.state = textFieldValue2;
            int size = ((ArrayList) legacyTextInputMethodRequest.ics).size();
            for (int i = 0; i < size; i++) {
                RecordingInputConnection recordingInputConnection = (RecordingInputConnection) ((WeakReference) ((ArrayList) legacyTextInputMethodRequest.ics).get(i)).get();
                if (recordingInputConnection != null) {
                    recordingInputConnection.textFieldValue = textFieldValue2;
                }
            }
            LegacyCursorAnchorInfoController legacyCursorAnchorInfoController = legacyTextInputMethodRequest.cursorAnchorInfoController;
            synchronized (legacyCursorAnchorInfoController.lock) {
                legacyCursorAnchorInfoController.textFieldValue = null;
                legacyCursorAnchorInfoController.offsetMapping = null;
                legacyCursorAnchorInfoController.textLayoutResult = null;
                legacyCursorAnchorInfoController.innerTextFieldBounds = null;
                legacyCursorAnchorInfoController.decorationBoxBounds = null;
                Unit unit = Unit.INSTANCE;
            }
            if (Intrinsics.areEqual(textFieldValue, textFieldValue2)) {
                if (z) {
                    InputMethodManager inputMethodManager = legacyTextInputMethodRequest.inputMethodManager;
                    int m750getMinimpl = TextRange.m750getMinimpl(textFieldValue2.selection);
                    int m749getMaximpl = TextRange.m749getMaximpl(textFieldValue2.selection);
                    TextRange textRange = legacyTextInputMethodRequest.state.composition;
                    int m750getMinimpl2 = textRange != null ? TextRange.m750getMinimpl(textRange.packedValue) : -1;
                    TextRange textRange2 = legacyTextInputMethodRequest.state.composition;
                    InputMethodManagerImpl inputMethodManagerImpl = (InputMethodManagerImpl) inputMethodManager;
                    inputMethodManagerImpl.getImm().updateSelection(inputMethodManagerImpl.view, m750getMinimpl, m749getMaximpl, m750getMinimpl2, textRange2 != null ? TextRange.m749getMaximpl(textRange2.packedValue) : -1);
                    return;
                }
                return;
            }
            if (textFieldValue != null && (!Intrinsics.areEqual(textFieldValue.annotatedString.text, textFieldValue2.annotatedString.text) || (TextRange.m746equalsimpl0(textFieldValue.selection, textFieldValue2.selection) && !Intrinsics.areEqual(textFieldValue.composition, textFieldValue2.composition)))) {
                InputMethodManagerImpl inputMethodManagerImpl2 = (InputMethodManagerImpl) legacyTextInputMethodRequest.inputMethodManager;
                inputMethodManagerImpl2.getImm().restartInput(inputMethodManagerImpl2.view);
                return;
            }
            int size2 = ((ArrayList) legacyTextInputMethodRequest.ics).size();
            for (int i2 = 0; i2 < size2; i2++) {
                RecordingInputConnection recordingInputConnection2 = (RecordingInputConnection) ((WeakReference) ((ArrayList) legacyTextInputMethodRequest.ics).get(i2)).get();
                if (recordingInputConnection2 != null) {
                    TextFieldValue textFieldValue3 = legacyTextInputMethodRequest.state;
                    InputMethodManager inputMethodManager2 = legacyTextInputMethodRequest.inputMethodManager;
                    if (recordingInputConnection2.isActive) {
                        recordingInputConnection2.textFieldValue = textFieldValue3;
                        if (recordingInputConnection2.extractedTextMonitorMode) {
                            InputMethodManagerImpl inputMethodManagerImpl3 = (InputMethodManagerImpl) inputMethodManager2;
                            inputMethodManagerImpl3.getImm().updateExtractedText(inputMethodManagerImpl3.view, recordingInputConnection2.currentExtractedTextRequestToken, RecordingInputConnection_androidKt.access$toExtractedText(textFieldValue3));
                        }
                        TextRange textRange3 = textFieldValue3.composition;
                        int m750getMinimpl3 = textRange3 != null ? TextRange.m750getMinimpl(textRange3.packedValue) : -1;
                        TextRange textRange4 = textFieldValue3.composition;
                        int m749getMaximpl2 = textRange4 != null ? TextRange.m749getMaximpl(textRange4.packedValue) : -1;
                        long j = textFieldValue3.selection;
                        InputMethodManagerImpl inputMethodManagerImpl4 = (InputMethodManagerImpl) inputMethodManager2;
                        inputMethodManagerImpl4.getImm().updateSelection(inputMethodManagerImpl4.view, TextRange.m750getMinimpl(j), TextRange.m749getMaximpl(j), m750getMinimpl3, m749getMaximpl2);
                    }
                }
            }
        }
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void updateTextLayoutResult(TextFieldValue textFieldValue, OffsetMapping offsetMapping, TextLayoutResult textLayoutResult, Function1 function1, Rect rect, Rect rect2) {
        LegacyTextInputMethodRequest legacyTextInputMethodRequest = this.currentRequest;
        if (legacyTextInputMethodRequest != null) {
            LegacyCursorAnchorInfoController legacyCursorAnchorInfoController = legacyTextInputMethodRequest.cursorAnchorInfoController;
            synchronized (legacyCursorAnchorInfoController.lock) {
                try {
                    legacyCursorAnchorInfoController.textFieldValue = textFieldValue;
                    legacyCursorAnchorInfoController.offsetMapping = offsetMapping;
                    legacyCursorAnchorInfoController.textLayoutResult = textLayoutResult;
                    legacyCursorAnchorInfoController.innerTextFieldBounds = rect;
                    legacyCursorAnchorInfoController.decorationBoxBounds = rect2;
                    if (!legacyCursorAnchorInfoController.hasPendingImmediateRequest) {
                        if (legacyCursorAnchorInfoController.monitorEnabled) {
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    legacyCursorAnchorInfoController.updateCursorAnchorInfo();
                    Unit unit2 = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void startInput(final TextFieldValue textFieldValue, final ImeOptions imeOptions, final Function1 function1, final Function1 function12) {
        startInput(new Function1() { // from class: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LegacyTextInputMethodRequest legacyTextInputMethodRequest = (LegacyTextInputMethodRequest) obj;
                TextFieldValue textFieldValue2 = TextFieldValue.this;
                LegacyAdaptingPlatformTextInputModifierNode legacyAdaptingPlatformTextInputModifierNode = this.textInputModifierNode;
                ImeOptions imeOptions2 = imeOptions;
                Function1 function13 = function1;
                Function1 function14 = function12;
                legacyTextInputMethodRequest.state = textFieldValue2;
                legacyTextInputMethodRequest.imeOptions = imeOptions2;
                legacyTextInputMethodRequest.onEditCommand = function13;
                legacyTextInputMethodRequest.onImeActionPerformed = function14;
                legacyTextInputMethodRequest.legacyTextFieldState = legacyAdaptingPlatformTextInputModifierNode != null ? legacyAdaptingPlatformTextInputModifierNode.legacyTextFieldState : null;
                legacyTextInputMethodRequest.textFieldSelectionManager = legacyAdaptingPlatformTextInputModifierNode != null ? legacyAdaptingPlatformTextInputModifierNode.textFieldSelectionManager : null;
                legacyTextInputMethodRequest.viewConfiguration = legacyAdaptingPlatformTextInputModifierNode != null ? (ViewConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(legacyAdaptingPlatformTextInputModifierNode, CompositionLocalsKt.LocalViewConfiguration) : null;
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void startInput() {
        startInput(null);
    }
}
