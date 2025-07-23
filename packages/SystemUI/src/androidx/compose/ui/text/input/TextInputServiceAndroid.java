package androidx.compose.ui.text.input;

import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.TextInputServiceAndroid;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextInputServiceAndroid implements PlatformTextInputService {
    public final Lazy baseInputConnection$delegate;
    public final CursorAnchorInfoController cursorAnchorInfoController;
    public boolean editorHasFocus;
    public Rect focusedRect;
    public TextInputServiceAndroid$$ExternalSyntheticLambda0 frameCallback;
    public final List ics;
    public ImeOptions imeOptions;
    public final Executor inputCommandProcessorExecutor;
    public final InputMethodManager inputMethodManager;
    public Lambda onEditCommand;
    public Lambda onImeActionPerformed;
    public TextFieldValue state;
    public final MutableVector textInputCommandQueue;
    public final View view;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class TextInputCommand {
        public static final /* synthetic */ TextInputCommand[] $VALUES;
        public static final TextInputCommand HideKeyboard;
        public static final TextInputCommand ShowKeyboard;
        public static final TextInputCommand StartInput;
        public static final TextInputCommand StopInput;

        static {
            TextInputCommand textInputCommand = new TextInputCommand("StartInput", 0);
            StartInput = textInputCommand;
            TextInputCommand textInputCommand2 = new TextInputCommand("StopInput", 1);
            StopInput = textInputCommand2;
            TextInputCommand textInputCommand3 = new TextInputCommand("ShowKeyboard", 2);
            ShowKeyboard = textInputCommand3;
            TextInputCommand textInputCommand4 = new TextInputCommand("HideKeyboard", 3);
            HideKeyboard = textInputCommand4;
            TextInputCommand[] textInputCommandArr = {textInputCommand, textInputCommand2, textInputCommand3, textInputCommand4};
            $VALUES = textInputCommandArr;
            EnumEntriesKt.enumEntries(textInputCommandArr);
        }

        private TextInputCommand(String str, int i) {
        }

        public static TextInputCommand valueOf(String str) {
            return (TextInputCommand) Enum.valueOf(TextInputCommand.class, str);
        }

        public static TextInputCommand[] values() {
            return (TextInputCommand[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TextInputCommand.values().length];
            try {
                iArr[TextInputCommand.StartInput.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TextInputCommand.StopInput.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TextInputCommand.ShowKeyboard.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TextInputCommand.HideKeyboard.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TextInputServiceAndroid(View view, MatrixPositionCalculator matrixPositionCalculator, InputMethodManager inputMethodManager, Executor executor) {
        this.view = view;
        this.inputMethodManager = inputMethodManager;
        this.inputCommandProcessorExecutor = executor;
        this.onEditCommand = new Function1() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$onEditCommand$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this.onImeActionPerformed = new Function1() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$onImeActionPerformed$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* synthetic */ Object mo779invoke(Object obj) {
                int i = ((ImeAction) obj).value;
                return Unit.INSTANCE;
            }
        };
        TextRange.Companion.getClass();
        this.state = new TextFieldValue("", TextRange.Zero, (TextRange) null, 4, (DefaultConstructorMarker) null);
        ImeOptions.Companion.getClass();
        this.imeOptions = ImeOptions.Default;
        this.ics = new ArrayList();
        this.baseInputConnection$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$baseInputConnection$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new BaseInputConnection(TextInputServiceAndroid.this.view, false);
            }
        });
        this.cursorAnchorInfoController = new CursorAnchorInfoController(matrixPositionCalculator, inputMethodManager);
        this.textInputCommandQueue = new MutableVector(new TextInputCommand[16], 0);
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void hideSoftwareKeyboard() {
        sendInputCommand(TextInputCommand.HideKeyboard);
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void notifyFocusedRect(androidx.compose.ui.geometry.Rect rect) {
        Rect rect2;
        this.focusedRect = new Rect(MathKt__MathJVMKt.roundToInt(rect.left), MathKt__MathJVMKt.roundToInt(rect.top), MathKt__MathJVMKt.roundToInt(rect.right), MathKt__MathJVMKt.roundToInt(rect.bottom));
        if (!((ArrayList) this.ics).isEmpty() || (rect2 = this.focusedRect) == null) {
            return;
        }
        this.view.requestRectangleOnScreen(new Rect(rect2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.text.input.TextInputServiceAndroid$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public final void sendInputCommand(TextInputCommand textInputCommand) {
        this.textInputCommandQueue.add(textInputCommand);
        if (this.frameCallback == null) {
            ?? r2 = new Runnable() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$$ExternalSyntheticLambda0
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r8v2, types: [T, java.lang.Boolean] */
                /* JADX WARN: Type inference failed for: r8v3, types: [T, java.lang.Boolean] */
                /* JADX WARN: Type inference failed for: r8v6, types: [T, java.lang.Boolean] */
                @Override // java.lang.Runnable
                public final void run() {
                    View findFocus;
                    TextInputServiceAndroid textInputServiceAndroid = TextInputServiceAndroid.this;
                    textInputServiceAndroid.frameCallback = null;
                    boolean isFocused = textInputServiceAndroid.view.isFocused();
                    MutableVector mutableVector = textInputServiceAndroid.textInputCommandQueue;
                    if (!isFocused && (findFocus = textInputServiceAndroid.view.getRootView().findFocus()) != null && findFocus.onCheckIsTextEditor()) {
                        mutableVector.clear();
                        return;
                    }
                    Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    Object[] objArr = mutableVector.content;
                    int i = mutableVector.size;
                    for (int i2 = 0; i2 < i; i2++) {
                        TextInputServiceAndroid.TextInputCommand textInputCommand2 = (TextInputServiceAndroid.TextInputCommand) objArr[i2];
                        int i3 = TextInputServiceAndroid.WhenMappings.$EnumSwitchMapping$0[textInputCommand2.ordinal()];
                        if (i3 == 1) {
                            ?? r8 = Boolean.TRUE;
                            ref$ObjectRef.element = r8;
                            ref$ObjectRef2.element = r8;
                        } else if (i3 == 2) {
                            ?? r82 = Boolean.FALSE;
                            ref$ObjectRef.element = r82;
                            ref$ObjectRef2.element = r82;
                        } else if ((i3 == 3 || i3 == 4) && !Intrinsics.areEqual(ref$ObjectRef.element, Boolean.FALSE)) {
                            ref$ObjectRef2.element = Boolean.valueOf(textInputCommand2 == TextInputServiceAndroid.TextInputCommand.ShowKeyboard);
                        }
                    }
                    mutableVector.clear();
                    boolean areEqual = Intrinsics.areEqual(ref$ObjectRef.element, Boolean.TRUE);
                    InputMethodManager inputMethodManager = textInputServiceAndroid.inputMethodManager;
                    if (areEqual) {
                        InputMethodManagerImpl inputMethodManagerImpl = (InputMethodManagerImpl) inputMethodManager;
                        ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl.imm$delegate.getValue()).restartInput(inputMethodManagerImpl.view);
                    }
                    Boolean bool = (Boolean) ref$ObjectRef2.element;
                    if (bool != null) {
                        if (bool.booleanValue()) {
                            ((InputMethodManagerImpl) inputMethodManager).softwareKeyboardControllerCompat.mImpl.show();
                        } else {
                            ((InputMethodManagerImpl) inputMethodManager).softwareKeyboardControllerCompat.mImpl.hide();
                        }
                    }
                    if (Intrinsics.areEqual(ref$ObjectRef.element, Boolean.FALSE)) {
                        InputMethodManagerImpl inputMethodManagerImpl2 = (InputMethodManagerImpl) inputMethodManager;
                        ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl2.imm$delegate.getValue()).restartInput(inputMethodManagerImpl2.view);
                    }
                }
            };
            this.inputCommandProcessorExecutor.execute(r2);
            this.frameCallback = r2;
        }
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void showSoftwareKeyboard() {
        sendInputCommand(TextInputCommand.ShowKeyboard);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void startInput(TextFieldValue textFieldValue, ImeOptions imeOptions, Function1 function1, Function1 function12) {
        this.editorHasFocus = true;
        this.state = textFieldValue;
        this.imeOptions = imeOptions;
        this.onEditCommand = (Lambda) function1;
        this.onImeActionPerformed = (Lambda) function12;
        sendInputCommand(TextInputCommand.StartInput);
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void stopInput() {
        this.editorHasFocus = false;
        this.onEditCommand = new Function1() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$stopInput$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this.onImeActionPerformed = new Function1() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid$stopInput$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* synthetic */ Object mo779invoke(Object obj) {
                int i = ((ImeAction) obj).value;
                return Unit.INSTANCE;
            }
        };
        this.focusedRect = null;
        sendInputCommand(TextInputCommand.StopInput);
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void updateState(TextFieldValue textFieldValue, TextFieldValue textFieldValue2) {
        boolean z = (TextRange.m746equalsimpl0(this.state.selection, textFieldValue2.selection) && Intrinsics.areEqual(this.state.composition, textFieldValue2.composition)) ? false : true;
        this.state = textFieldValue2;
        int size = ((ArrayList) this.ics).size();
        for (int i = 0; i < size; i++) {
            RecordingInputConnection recordingInputConnection = (RecordingInputConnection) ((WeakReference) ((ArrayList) this.ics).get(i)).get();
            if (recordingInputConnection != null) {
                recordingInputConnection.mTextFieldValue = textFieldValue2;
            }
        }
        CursorAnchorInfoController cursorAnchorInfoController = this.cursorAnchorInfoController;
        synchronized (cursorAnchorInfoController.lock) {
            cursorAnchorInfoController.textFieldValue = null;
            cursorAnchorInfoController.offsetMapping = null;
            cursorAnchorInfoController.textLayoutResult = null;
            cursorAnchorInfoController.textFieldToRootTransform = new Function1() { // from class: androidx.compose.ui.text.input.CursorAnchorInfoController$invalidate$1$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* synthetic */ Object mo779invoke(Object obj) {
                    float[] fArr = ((Matrix) obj).values;
                    return Unit.INSTANCE;
                }
            };
            cursorAnchorInfoController.innerTextFieldBounds = null;
            cursorAnchorInfoController.decorationBoxBounds = null;
            Unit unit = Unit.INSTANCE;
        }
        if (Intrinsics.areEqual(textFieldValue, textFieldValue2)) {
            if (z) {
                InputMethodManager inputMethodManager = this.inputMethodManager;
                int m750getMinimpl = TextRange.m750getMinimpl(textFieldValue2.selection);
                int m749getMaximpl = TextRange.m749getMaximpl(textFieldValue2.selection);
                TextRange textRange = this.state.composition;
                int m750getMinimpl2 = textRange != null ? TextRange.m750getMinimpl(textRange.packedValue) : -1;
                TextRange textRange2 = this.state.composition;
                InputMethodManagerImpl inputMethodManagerImpl = (InputMethodManagerImpl) inputMethodManager;
                ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl.imm$delegate.getValue()).updateSelection(inputMethodManagerImpl.view, m750getMinimpl, m749getMaximpl, m750getMinimpl2, textRange2 != null ? TextRange.m749getMaximpl(textRange2.packedValue) : -1);
                return;
            }
            return;
        }
        if (textFieldValue != null && (!Intrinsics.areEqual(textFieldValue.annotatedString.text, textFieldValue2.annotatedString.text) || (TextRange.m746equalsimpl0(textFieldValue.selection, textFieldValue2.selection) && !Intrinsics.areEqual(textFieldValue.composition, textFieldValue2.composition)))) {
            InputMethodManagerImpl inputMethodManagerImpl2 = (InputMethodManagerImpl) this.inputMethodManager;
            ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl2.imm$delegate.getValue()).restartInput(inputMethodManagerImpl2.view);
            return;
        }
        int size2 = ((ArrayList) this.ics).size();
        for (int i2 = 0; i2 < size2; i2++) {
            RecordingInputConnection recordingInputConnection2 = (RecordingInputConnection) ((WeakReference) ((ArrayList) this.ics).get(i2)).get();
            if (recordingInputConnection2 != null) {
                TextFieldValue textFieldValue3 = this.state;
                InputMethodManager inputMethodManager2 = this.inputMethodManager;
                if (recordingInputConnection2.isActive) {
                    recordingInputConnection2.mTextFieldValue = textFieldValue3;
                    if (recordingInputConnection2.extractedTextMonitorMode) {
                        InputMethodManagerImpl inputMethodManagerImpl3 = (InputMethodManagerImpl) inputMethodManager2;
                        ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl3.imm$delegate.getValue()).updateExtractedText(inputMethodManagerImpl3.view, recordingInputConnection2.currentExtractedTextRequestToken, InputState_androidKt.toExtractedText(textFieldValue3));
                    }
                    TextRange textRange3 = textFieldValue3.composition;
                    int m750getMinimpl3 = textRange3 != null ? TextRange.m750getMinimpl(textRange3.packedValue) : -1;
                    TextRange textRange4 = textFieldValue3.composition;
                    int m749getMaximpl2 = textRange4 != null ? TextRange.m749getMaximpl(textRange4.packedValue) : -1;
                    long j = textFieldValue3.selection;
                    InputMethodManagerImpl inputMethodManagerImpl4 = (InputMethodManagerImpl) inputMethodManager2;
                    ((android.view.inputmethod.InputMethodManager) inputMethodManagerImpl4.imm$delegate.getValue()).updateSelection(inputMethodManagerImpl4.view, TextRange.m750getMinimpl(j), TextRange.m749getMaximpl(j), m750getMinimpl3, m749getMaximpl2);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void updateTextLayoutResult(TextFieldValue textFieldValue, OffsetMapping offsetMapping, TextLayoutResult textLayoutResult, Function1 function1, androidx.compose.ui.geometry.Rect rect, androidx.compose.ui.geometry.Rect rect2) {
        CursorAnchorInfoController cursorAnchorInfoController = this.cursorAnchorInfoController;
        synchronized (cursorAnchorInfoController.lock) {
            try {
                cursorAnchorInfoController.textFieldValue = textFieldValue;
                cursorAnchorInfoController.offsetMapping = offsetMapping;
                cursorAnchorInfoController.textLayoutResult = textLayoutResult;
                cursorAnchorInfoController.textFieldToRootTransform = (Lambda) function1;
                cursorAnchorInfoController.innerTextFieldBounds = rect;
                cursorAnchorInfoController.decorationBoxBounds = rect2;
                if (!cursorAnchorInfoController.hasPendingImmediateRequest) {
                    if (cursorAnchorInfoController.monitorEnabled) {
                    }
                    Unit unit = Unit.INSTANCE;
                }
                cursorAnchorInfoController.updateCursorAnchorInfo();
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.ui.text.input.PlatformTextInputService
    public final void startInput() {
        sendInputCommand(TextInputCommand.StartInput);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextInputServiceAndroid(android.view.View r1, androidx.compose.ui.input.pointer.MatrixPositionCalculator r2, androidx.compose.ui.text.input.InputMethodManager r3, java.util.concurrent.Executor r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r5 = r5 & 8
            if (r5 == 0) goto Le
            android.view.Choreographer r4 = android.view.Choreographer.getInstance()
            androidx.compose.ui.text.input.TextInputServiceAndroid_androidKt$$ExternalSyntheticLambda0 r5 = new androidx.compose.ui.text.input.TextInputServiceAndroid_androidKt$$ExternalSyntheticLambda0
            r5.<init>()
            r4 = r5
        Le:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.input.TextInputServiceAndroid.<init>(android.view.View, androidx.compose.ui.input.pointer.MatrixPositionCalculator, androidx.compose.ui.text.input.InputMethodManager, java.util.concurrent.Executor, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public TextInputServiceAndroid(View view, MatrixPositionCalculator matrixPositionCalculator) {
        this(view, matrixPositionCalculator, new InputMethodManagerImpl(view), null, 8, null);
    }
}
