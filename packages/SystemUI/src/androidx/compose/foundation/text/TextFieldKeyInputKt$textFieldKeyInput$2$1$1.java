package androidx.compose.foundation.text;

import android.view.KeyCharacterMap;
import androidx.compose.foundation.text.UndoManager;
import androidx.compose.foundation.text.selection.TextFieldPreparedSelection;
import androidx.compose.foundation.text.selection.TextPreparedSelectionState;
import androidx.compose.ui.input.key.KeyEvent;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.DeleteSurroundingTextCommand;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes.dex */
final /* synthetic */ class TextFieldKeyInputKt$textFieldKeyInput$2$1$1 extends FunctionReferenceImpl implements Function1 {
    public TextFieldKeyInputKt$textFieldKeyInput$2$1$1(Object obj) {
        super(1, obj, TextFieldKeyInput.class, "process", "process-ZmokQxo(Landroid/view/KeyEvent;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final /* synthetic */ Object mo781invoke(Object obj) {
        return m207invokeZmokQxo(((KeyEvent) obj).nativeKeyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /* renamed from: invoke-ZmokQxo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Boolean m207invokeZmokQxo(android.view.KeyEvent keyEvent) throws IOException {
        CommitTextCommand commitTextCommand;
        final KeyCommand keyCommandMo199mapZmokQxo;
        Integer numValueOf;
        final TextFieldKeyInput textFieldKeyInput = (TextFieldKeyInput) this.receiver;
        textFieldKeyInput.getClass();
        boolean z = true;
        if (keyEvent.getAction() != 0 || Character.isISOControl(keyEvent.getUnicodeChar())) {
            commitTextCommand = null;
        } else {
            DeadKeyCombiner deadKeyCombiner = textFieldKeyInput.keyCombiner;
            deadKeyCombiner.getClass();
            int unicodeChar = keyEvent.getUnicodeChar();
            if ((Integer.MIN_VALUE & unicodeChar) != 0) {
                deadKeyCombiner.deadKeyCode = Integer.valueOf(unicodeChar & Integer.MAX_VALUE);
                numValueOf = null;
            } else {
                Integer num = deadKeyCombiner.deadKeyCode;
                if (num != null) {
                    deadKeyCombiner.deadKeyCode = null;
                    int deadChar = KeyCharacterMap.getDeadChar(num.intValue(), unicodeChar);
                    numValueOf = Integer.valueOf(deadChar);
                    if (deadChar == 0) {
                        numValueOf = null;
                    }
                    if (numValueOf == null) {
                        numValueOf = Integer.valueOf(unicodeChar);
                    }
                } else {
                    numValueOf = Integer.valueOf(unicodeChar);
                }
            }
            if (numValueOf != null) {
                commitTextCommand = new CommitTextCommand(new StringBuilder().appendCodePoint(numValueOf.intValue()).toString(), 1);
            }
        }
        TextPreparedSelectionState textPreparedSelectionState = textFieldKeyInput.preparedSelectionState;
        boolean z2 = textFieldKeyInput.editable;
        if (commitTextCommand == null) {
            int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
            KeyEventType.Companion.getClass();
            if (iM581getTypeZmokQxo == KeyEventType.KeyDown && (keyCommandMo199mapZmokQxo = textFieldKeyInput.keyMapping.mo199mapZmokQxo(keyEvent)) != null && (!keyCommandMo199mapZmokQxo.getEditsText() || z2)) {
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = true;
                Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2

                    /* renamed from: androidx.compose.foundation.text.TextFieldKeyInput$process$2$1, reason: invalid class name */
                    final class AnonymousClass1 extends Lambda implements Function1 {
                        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                        public AnonymousClass1() {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            TextFieldPreparedSelection textFieldPreparedSelection = (TextFieldPreparedSelection) obj;
                            TextPreparedSelectionState textPreparedSelectionState = textFieldPreparedSelection.state;
                            textPreparedSelectionState.cachedX = null;
                            if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                if (textFieldPreparedSelection.isLtr()) {
                                    textPreparedSelectionState.cachedX = null;
                                    if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                        String str = textFieldPreparedSelection.annotatedString.text;
                                        long j = textFieldPreparedSelection.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        int iFindPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak((int) (j & 4294967295L), str);
                                        if (iFindPrecedingBreak != -1) {
                                            textFieldPreparedSelection.setSelection(iFindPrecedingBreak, iFindPrecedingBreak);
                                        }
                                    }
                                } else {
                                    textPreparedSelectionState.cachedX = null;
                                    if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                        String str2 = textFieldPreparedSelection.annotatedString.text;
                                        long j2 = textFieldPreparedSelection.selection;
                                        TextRange.Companion companion2 = TextRange.Companion;
                                        int iFindFollowingBreak = StringHelpers_androidKt.findFollowingBreak((int) (j2 & 4294967295L), str2);
                                        if (iFindFollowingBreak != -1) {
                                            textFieldPreparedSelection.setSelection(iFindFollowingBreak, iFindFollowingBreak);
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    /* renamed from: androidx.compose.foundation.text.TextFieldKeyInput$process$2$2, reason: invalid class name */
                    final class AnonymousClass2 extends Lambda implements Function1 {
                        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                        public AnonymousClass2() {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            TextFieldPreparedSelection textFieldPreparedSelection = (TextFieldPreparedSelection) obj;
                            TextPreparedSelectionState textPreparedSelectionState = textFieldPreparedSelection.state;
                            textPreparedSelectionState.cachedX = null;
                            if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                if (textFieldPreparedSelection.isLtr()) {
                                    textPreparedSelectionState.cachedX = null;
                                    if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                        String str = textFieldPreparedSelection.annotatedString.text;
                                        long j = textFieldPreparedSelection.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        int iFindFollowingBreak = StringHelpers_androidKt.findFollowingBreak((int) (j & 4294967295L), str);
                                        if (iFindFollowingBreak != -1) {
                                            textFieldPreparedSelection.setSelection(iFindFollowingBreak, iFindFollowingBreak);
                                        }
                                    }
                                } else {
                                    textPreparedSelectionState.cachedX = null;
                                    if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                        String str2 = textFieldPreparedSelection.annotatedString.text;
                                        long j2 = textFieldPreparedSelection.selection;
                                        TextRange.Companion companion2 = TextRange.Companion;
                                        int iFindPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak((int) (j2 & 4294967295L), str2);
                                        if (iFindPrecedingBreak != -1) {
                                            textFieldPreparedSelection.setSelection(iFindPrecedingBreak, iFindPrecedingBreak);
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    public abstract /* synthetic */ class WhenMappings {
                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                        static {
                            int[] iArr = new int[KeyCommand.values().length];
                            try {
                                iArr[KeyCommand.COPY.ordinal()] = 1;
                            } catch (NoSuchFieldError unused) {
                            }
                            try {
                                iArr[KeyCommand.PASTE.ordinal()] = 2;
                            } catch (NoSuchFieldError unused2) {
                            }
                            try {
                                iArr[KeyCommand.CUT.ordinal()] = 3;
                            } catch (NoSuchFieldError unused3) {
                            }
                            try {
                                iArr[KeyCommand.LEFT_CHAR.ordinal()] = 4;
                            } catch (NoSuchFieldError unused4) {
                            }
                            try {
                                iArr[KeyCommand.RIGHT_CHAR.ordinal()] = 5;
                            } catch (NoSuchFieldError unused5) {
                            }
                            try {
                                iArr[KeyCommand.LEFT_WORD.ordinal()] = 6;
                            } catch (NoSuchFieldError unused6) {
                            }
                            try {
                                iArr[KeyCommand.RIGHT_WORD.ordinal()] = 7;
                            } catch (NoSuchFieldError unused7) {
                            }
                            try {
                                iArr[KeyCommand.PREV_PARAGRAPH.ordinal()] = 8;
                            } catch (NoSuchFieldError unused8) {
                            }
                            try {
                                iArr[KeyCommand.NEXT_PARAGRAPH.ordinal()] = 9;
                            } catch (NoSuchFieldError unused9) {
                            }
                            try {
                                iArr[KeyCommand.UP.ordinal()] = 10;
                            } catch (NoSuchFieldError unused10) {
                            }
                            try {
                                iArr[KeyCommand.DOWN.ordinal()] = 11;
                            } catch (NoSuchFieldError unused11) {
                            }
                            try {
                                iArr[KeyCommand.PAGE_UP.ordinal()] = 12;
                            } catch (NoSuchFieldError unused12) {
                            }
                            try {
                                iArr[KeyCommand.PAGE_DOWN.ordinal()] = 13;
                            } catch (NoSuchFieldError unused13) {
                            }
                            try {
                                iArr[KeyCommand.LINE_START.ordinal()] = 14;
                            } catch (NoSuchFieldError unused14) {
                            }
                            try {
                                iArr[KeyCommand.LINE_END.ordinal()] = 15;
                            } catch (NoSuchFieldError unused15) {
                            }
                            try {
                                iArr[KeyCommand.LINE_LEFT.ordinal()] = 16;
                            } catch (NoSuchFieldError unused16) {
                            }
                            try {
                                iArr[KeyCommand.LINE_RIGHT.ordinal()] = 17;
                            } catch (NoSuchFieldError unused17) {
                            }
                            try {
                                iArr[KeyCommand.HOME.ordinal()] = 18;
                            } catch (NoSuchFieldError unused18) {
                            }
                            try {
                                iArr[KeyCommand.END.ordinal()] = 19;
                            } catch (NoSuchFieldError unused19) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_PREV_CHAR.ordinal()] = 20;
                            } catch (NoSuchFieldError unused20) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_NEXT_CHAR.ordinal()] = 21;
                            } catch (NoSuchFieldError unused21) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_PREV_WORD.ordinal()] = 22;
                            } catch (NoSuchFieldError unused22) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_NEXT_WORD.ordinal()] = 23;
                            } catch (NoSuchFieldError unused23) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_FROM_LINE_START.ordinal()] = 24;
                            } catch (NoSuchFieldError unused24) {
                            }
                            try {
                                iArr[KeyCommand.DELETE_TO_LINE_END.ordinal()] = 25;
                            } catch (NoSuchFieldError unused25) {
                            }
                            try {
                                iArr[KeyCommand.NEW_LINE.ordinal()] = 26;
                            } catch (NoSuchFieldError unused26) {
                            }
                            try {
                                iArr[KeyCommand.TAB.ordinal()] = 27;
                            } catch (NoSuchFieldError unused27) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_ALL.ordinal()] = 28;
                            } catch (NoSuchFieldError unused28) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LEFT_CHAR.ordinal()] = 29;
                            } catch (NoSuchFieldError unused29) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_RIGHT_CHAR.ordinal()] = 30;
                            } catch (NoSuchFieldError unused30) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LEFT_WORD.ordinal()] = 31;
                            } catch (NoSuchFieldError unused31) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_RIGHT_WORD.ordinal()] = 32;
                            } catch (NoSuchFieldError unused32) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_PREV_PARAGRAPH.ordinal()] = 33;
                            } catch (NoSuchFieldError unused33) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_NEXT_PARAGRAPH.ordinal()] = 34;
                            } catch (NoSuchFieldError unused34) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LINE_START.ordinal()] = 35;
                            } catch (NoSuchFieldError unused35) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LINE_END.ordinal()] = 36;
                            } catch (NoSuchFieldError unused36) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LINE_LEFT.ordinal()] = 37;
                            } catch (NoSuchFieldError unused37) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_LINE_RIGHT.ordinal()] = 38;
                            } catch (NoSuchFieldError unused38) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_UP.ordinal()] = 39;
                            } catch (NoSuchFieldError unused39) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_DOWN.ordinal()] = 40;
                            } catch (NoSuchFieldError unused40) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_PAGE_UP.ordinal()] = 41;
                            } catch (NoSuchFieldError unused41) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_PAGE_DOWN.ordinal()] = 42;
                            } catch (NoSuchFieldError unused42) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_HOME.ordinal()] = 43;
                            } catch (NoSuchFieldError unused43) {
                            }
                            try {
                                iArr[KeyCommand.SELECT_END.ordinal()] = 44;
                            } catch (NoSuchFieldError unused44) {
                            }
                            try {
                                iArr[KeyCommand.DESELECT.ordinal()] = 45;
                            } catch (NoSuchFieldError unused45) {
                            }
                            try {
                                iArr[KeyCommand.UNDO.ordinal()] = 46;
                            } catch (NoSuchFieldError unused46) {
                            }
                            try {
                                iArr[KeyCommand.REDO.ordinal()] = 47;
                            } catch (NoSuchFieldError unused47) {
                            }
                            try {
                                iArr[KeyCommand.CHARACTER_PALETTE.ordinal()] = 48;
                            } catch (NoSuchFieldError unused48) {
                            }
                            $EnumSwitchMapping$0 = iArr;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Integer nextWordOffset;
                        Integer previousWordOffset;
                        Integer previousWordOffset2;
                        Integer nextWordOffset2;
                        TextLayoutResult textLayoutResult;
                        TextLayoutResult textLayoutResult2;
                        TextLayoutResultProxy textLayoutResultProxy;
                        TextLayoutResultProxy textLayoutResultProxy2;
                        Integer nextWordOffset3;
                        Integer previousWordOffset3;
                        Integer previousWordOffset4;
                        Integer nextWordOffset4;
                        TextLayoutResult textLayoutResult3;
                        TextLayoutResult textLayoutResult4;
                        TextLayoutResultProxy textLayoutResultProxy3;
                        TextLayoutResultProxy textLayoutResultProxy4;
                        UndoManager.Entry entry;
                        TextFieldPreparedSelection textFieldPreparedSelection = (TextFieldPreparedSelection) obj;
                        TextFieldValue textFieldValue = null;
                        switch (WhenMappings.$EnumSwitchMapping$0[keyCommandMo199mapZmokQxo.ordinal()]) {
                            case 1:
                                textFieldKeyInput.selectionManager.copy$foundation_release(false);
                                break;
                            case 2:
                                textFieldKeyInput.selectionManager.paste$foundation_release();
                                break;
                            case 3:
                                textFieldKeyInput.selectionManager.cut$foundation_release();
                                break;
                            case 4:
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!TextRange.m749getCollapsedimpl(textFieldPreparedSelection.selection)) {
                                        if (!textFieldPreparedSelection.isLtr()) {
                                            int iM751getMaximpl = TextRange.m751getMaximpl(textFieldPreparedSelection.selection);
                                            textFieldPreparedSelection.setSelection(iM751getMaximpl, iM751getMaximpl);
                                            break;
                                        } else {
                                            int iM752getMinimpl = TextRange.m752getMinimpl(textFieldPreparedSelection.selection);
                                            textFieldPreparedSelection.setSelection(iM752getMinimpl, iM752getMinimpl);
                                            break;
                                        }
                                    } else {
                                        anonymousClass1.mo781invoke(textFieldPreparedSelection);
                                        break;
                                    }
                                }
                                break;
                            case 5:
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.INSTANCE;
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!TextRange.m749getCollapsedimpl(textFieldPreparedSelection.selection)) {
                                        if (!textFieldPreparedSelection.isLtr()) {
                                            int iM752getMinimpl2 = TextRange.m752getMinimpl(textFieldPreparedSelection.selection);
                                            textFieldPreparedSelection.setSelection(iM752getMinimpl2, iM752getMinimpl2);
                                            break;
                                        } else {
                                            int iM751getMaximpl2 = TextRange.m751getMaximpl(textFieldPreparedSelection.selection);
                                            textFieldPreparedSelection.setSelection(iM751getMaximpl2, iM751getMaximpl2);
                                            break;
                                        }
                                    } else {
                                        anonymousClass2.mo781invoke(textFieldPreparedSelection);
                                        break;
                                    }
                                }
                                break;
                            case 6:
                                TextPreparedSelectionState textPreparedSelectionState2 = textFieldPreparedSelection.state;
                                textPreparedSelectionState2.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState2.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (nextWordOffset = textFieldPreparedSelection.getNextWordOffset()) != null) {
                                            int iIntValue = nextWordOffset.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue, iIntValue);
                                            break;
                                        }
                                    } else {
                                        textPreparedSelectionState2.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (previousWordOffset = textFieldPreparedSelection.getPreviousWordOffset()) != null) {
                                            int iIntValue2 = previousWordOffset.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue2, iIntValue2);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 7:
                                TextPreparedSelectionState textPreparedSelectionState3 = textFieldPreparedSelection.state;
                                textPreparedSelectionState3.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState3.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (previousWordOffset2 = textFieldPreparedSelection.getPreviousWordOffset()) != null) {
                                            int iIntValue3 = previousWordOffset2.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue3, iIntValue3);
                                            break;
                                        }
                                    } else {
                                        textPreparedSelectionState3.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (nextWordOffset2 = textFieldPreparedSelection.getNextWordOffset()) != null) {
                                            int iIntValue4 = nextWordOffset2.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue4, iIntValue4);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 8:
                                textFieldPreparedSelection.moveCursorPrevByParagraph();
                                break;
                            case 9:
                                textFieldPreparedSelection.moveCursorNextByParagraph();
                                break;
                            case 10:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResult = textFieldPreparedSelection.layoutResult) != null) {
                                    int iJumpByLinesOffset = textFieldPreparedSelection.jumpByLinesOffset(textLayoutResult, -1);
                                    textFieldPreparedSelection.setSelection(iJumpByLinesOffset, iJumpByLinesOffset);
                                    break;
                                }
                                break;
                            case 11:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResult2 = textFieldPreparedSelection.layoutResult) != null) {
                                    int iJumpByLinesOffset2 = textFieldPreparedSelection.jumpByLinesOffset(textLayoutResult2, 1);
                                    textFieldPreparedSelection.setSelection(iJumpByLinesOffset2, iJumpByLinesOffset2);
                                    break;
                                }
                                break;
                            case 12:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResultProxy = textFieldPreparedSelection.layoutResultProxy) != null) {
                                    int iJumpByPagesOffset = textFieldPreparedSelection.jumpByPagesOffset(textLayoutResultProxy, -1);
                                    textFieldPreparedSelection.setSelection(iJumpByPagesOffset, iJumpByPagesOffset);
                                    break;
                                }
                                break;
                            case 13:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResultProxy2 = textFieldPreparedSelection.layoutResultProxy) != null) {
                                    int iJumpByPagesOffset2 = textFieldPreparedSelection.jumpByPagesOffset(textLayoutResultProxy2, 1);
                                    textFieldPreparedSelection.setSelection(iJumpByPagesOffset2, iJumpByPagesOffset2);
                                    break;
                                }
                                break;
                            case 14:
                                textFieldPreparedSelection.moveCursorToLineStart();
                                break;
                            case 15:
                                textFieldPreparedSelection.moveCursorToLineEnd();
                                break;
                            case 16:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!textFieldPreparedSelection.isLtr()) {
                                        textFieldPreparedSelection.moveCursorToLineEnd();
                                        break;
                                    } else {
                                        textFieldPreparedSelection.moveCursorToLineStart();
                                        break;
                                    }
                                }
                                break;
                            case 17:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (!textFieldPreparedSelection.isLtr()) {
                                        textFieldPreparedSelection.moveCursorToLineStart();
                                        break;
                                    } else {
                                        textFieldPreparedSelection.moveCursorToLineEnd();
                                        break;
                                    }
                                }
                                break;
                            case 18:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    textFieldPreparedSelection.setSelection(0, 0);
                                    break;
                                }
                                break;
                            case 19:
                                textFieldPreparedSelection.state.cachedX = null;
                                AnnotatedString annotatedString = textFieldPreparedSelection.annotatedString;
                                if (annotatedString.text.length() > 0) {
                                    int length = annotatedString.text.length();
                                    textFieldPreparedSelection.setSelection(length, length);
                                    break;
                                }
                                break;
                            case 20:
                                List listDeleteIfSelectedOr = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.3
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        return new DeleteSurroundingTextCommand(((int) (j & 4294967295L)) - StringHelpers_androidKt.findPrecedingBreak((int) (j & 4294967295L), textFieldPreparedSelection2.annotatedString.text), 0);
                                    }
                                });
                                if (listDeleteIfSelectedOr != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr);
                                    break;
                                }
                                break;
                            case 21:
                                List listDeleteIfSelectedOr2 = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.4
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        String str = textFieldPreparedSelection2.annotatedString.text;
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        int iFindFollowingBreak = StringHelpers_androidKt.findFollowingBreak((int) (j & 4294967295L), str);
                                        if (iFindFollowingBreak != -1) {
                                            return new DeleteSurroundingTextCommand(0, iFindFollowingBreak - ((int) (textFieldPreparedSelection2.selection & 4294967295L)));
                                        }
                                        return null;
                                    }
                                });
                                if (listDeleteIfSelectedOr2 != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr2);
                                    break;
                                }
                                break;
                            case 22:
                                List listDeleteIfSelectedOr3 = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.5
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        Integer previousWordOffset5 = textFieldPreparedSelection2.getPreviousWordOffset();
                                        if (previousWordOffset5 == null) {
                                            return null;
                                        }
                                        int iIntValue5 = previousWordOffset5.intValue();
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        return new DeleteSurroundingTextCommand(((int) (j & 4294967295L)) - iIntValue5, 0);
                                    }
                                });
                                if (listDeleteIfSelectedOr3 != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr3);
                                    break;
                                }
                                break;
                            case 23:
                                List listDeleteIfSelectedOr4 = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.6
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        Integer nextWordOffset5 = textFieldPreparedSelection2.getNextWordOffset();
                                        if (nextWordOffset5 == null) {
                                            return null;
                                        }
                                        int iIntValue5 = nextWordOffset5.intValue();
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        return new DeleteSurroundingTextCommand(0, iIntValue5 - ((int) (j & 4294967295L)));
                                    }
                                });
                                if (listDeleteIfSelectedOr4 != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr4);
                                    break;
                                }
                                break;
                            case 24:
                                List listDeleteIfSelectedOr5 = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.7
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        Integer lineStartByOffset = textFieldPreparedSelection2.getLineStartByOffset();
                                        if (lineStartByOffset == null) {
                                            return null;
                                        }
                                        int iIntValue5 = lineStartByOffset.intValue();
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        return new DeleteSurroundingTextCommand(((int) (j & 4294967295L)) - iIntValue5, 0);
                                    }
                                });
                                if (listDeleteIfSelectedOr5 != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr5);
                                    break;
                                }
                                break;
                            case 25:
                                List listDeleteIfSelectedOr6 = textFieldPreparedSelection.deleteIfSelectedOr(new Function1() { // from class: androidx.compose.foundation.text.TextFieldKeyInput$process$2.8
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj2) {
                                        TextFieldPreparedSelection textFieldPreparedSelection2 = (TextFieldPreparedSelection) obj2;
                                        Integer lineEndByOffset = textFieldPreparedSelection2.getLineEndByOffset();
                                        if (lineEndByOffset == null) {
                                            return null;
                                        }
                                        int iIntValue5 = lineEndByOffset.intValue();
                                        long j = textFieldPreparedSelection2.selection;
                                        TextRange.Companion companion = TextRange.Companion;
                                        return new DeleteSurroundingTextCommand(0, iIntValue5 - ((int) (j & 4294967295L)));
                                    }
                                });
                                if (listDeleteIfSelectedOr6 != null) {
                                    textFieldKeyInput.apply(listDeleteIfSelectedOr6);
                                    break;
                                }
                                break;
                            case 26:
                                TextFieldKeyInput textFieldKeyInput2 = textFieldKeyInput;
                                if (!textFieldKeyInput2.singleLine) {
                                    textFieldKeyInput2.apply(Collections.singletonList(new CommitTextCommand("\n", 1)));
                                    break;
                                } else {
                                    ((LegacyTextFieldState$onImeActionPerformed$1) textFieldKeyInput2.state.onImeActionPerformed).mo781invoke(ImeAction.m774boximpl(textFieldKeyInput2.imeAction));
                                    break;
                                }
                            case 27:
                                TextFieldKeyInput textFieldKeyInput3 = textFieldKeyInput;
                                if (!textFieldKeyInput3.singleLine) {
                                    textFieldKeyInput3.apply(Collections.singletonList(new CommitTextCommand("\t", 1)));
                                    break;
                                } else {
                                    ref$BooleanRef.element = false;
                                    break;
                                }
                            case 28:
                                textFieldPreparedSelection.state.cachedX = null;
                                AnnotatedString annotatedString2 = textFieldPreparedSelection.annotatedString;
                                if (annotatedString2.text.length() > 0) {
                                    textFieldPreparedSelection.setSelection(0, annotatedString2.text.length());
                                    break;
                                }
                                break;
                            case 29:
                                TextPreparedSelectionState textPreparedSelectionState4 = textFieldPreparedSelection.state;
                                textPreparedSelectionState4.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState4.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                            String str = textFieldPreparedSelection.annotatedString.text;
                                            long j = textFieldPreparedSelection.selection;
                                            TextRange.Companion companion = TextRange.Companion;
                                            int iFindPrecedingBreak = StringHelpers_androidKt.findPrecedingBreak((int) (j & 4294967295L), str);
                                            if (iFindPrecedingBreak != -1) {
                                                textFieldPreparedSelection.setSelection(iFindPrecedingBreak, iFindPrecedingBreak);
                                            }
                                        }
                                    } else {
                                        textPreparedSelectionState4.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                            String str2 = textFieldPreparedSelection.annotatedString.text;
                                            long j2 = textFieldPreparedSelection.selection;
                                            TextRange.Companion companion2 = TextRange.Companion;
                                            int iFindFollowingBreak = StringHelpers_androidKt.findFollowingBreak((int) (j2 & 4294967295L), str2);
                                            if (iFindFollowingBreak != -1) {
                                                textFieldPreparedSelection.setSelection(iFindFollowingBreak, iFindFollowingBreak);
                                            }
                                        }
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 30:
                                TextPreparedSelectionState textPreparedSelectionState5 = textFieldPreparedSelection.state;
                                textPreparedSelectionState5.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState5.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                            String str3 = textFieldPreparedSelection.annotatedString.text;
                                            long j3 = textFieldPreparedSelection.selection;
                                            TextRange.Companion companion3 = TextRange.Companion;
                                            int iFindFollowingBreak2 = StringHelpers_androidKt.findFollowingBreak((int) (j3 & 4294967295L), str3);
                                            if (iFindFollowingBreak2 != -1) {
                                                textFieldPreparedSelection.setSelection(iFindFollowingBreak2, iFindFollowingBreak2);
                                            }
                                        }
                                    } else {
                                        textPreparedSelectionState5.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                            String str4 = textFieldPreparedSelection.annotatedString.text;
                                            long j4 = textFieldPreparedSelection.selection;
                                            TextRange.Companion companion4 = TextRange.Companion;
                                            int iFindPrecedingBreak2 = StringHelpers_androidKt.findPrecedingBreak((int) (j4 & 4294967295L), str4);
                                            if (iFindPrecedingBreak2 != -1) {
                                                textFieldPreparedSelection.setSelection(iFindPrecedingBreak2, iFindPrecedingBreak2);
                                            }
                                        }
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 31:
                                TextPreparedSelectionState textPreparedSelectionState6 = textFieldPreparedSelection.state;
                                textPreparedSelectionState6.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState6.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (previousWordOffset3 = textFieldPreparedSelection.getPreviousWordOffset()) != null) {
                                            int iIntValue5 = previousWordOffset3.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue5, iIntValue5);
                                        }
                                    } else {
                                        textPreparedSelectionState6.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (nextWordOffset3 = textFieldPreparedSelection.getNextWordOffset()) != null) {
                                            int iIntValue6 = nextWordOffset3.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue6, iIntValue6);
                                        }
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 32:
                                TextPreparedSelectionState textPreparedSelectionState7 = textFieldPreparedSelection.state;
                                textPreparedSelectionState7.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textPreparedSelectionState7.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (nextWordOffset4 = textFieldPreparedSelection.getNextWordOffset()) != null) {
                                            int iIntValue7 = nextWordOffset4.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue7, iIntValue7);
                                        }
                                    } else {
                                        textPreparedSelectionState7.cachedX = null;
                                        if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (previousWordOffset4 = textFieldPreparedSelection.getPreviousWordOffset()) != null) {
                                            int iIntValue8 = previousWordOffset4.intValue();
                                            textFieldPreparedSelection.setSelection(iIntValue8, iIntValue8);
                                        }
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 33:
                                textFieldPreparedSelection.moveCursorPrevByParagraph();
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 34:
                                textFieldPreparedSelection.moveCursorNextByParagraph();
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 35:
                                textFieldPreparedSelection.moveCursorToLineStart();
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 36:
                                textFieldPreparedSelection.moveCursorToLineEnd();
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 37:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textFieldPreparedSelection.moveCursorToLineStart();
                                    } else {
                                        textFieldPreparedSelection.moveCursorToLineEnd();
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 38:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    if (textFieldPreparedSelection.isLtr()) {
                                        textFieldPreparedSelection.moveCursorToLineEnd();
                                    } else {
                                        textFieldPreparedSelection.moveCursorToLineStart();
                                    }
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 39:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResult3 = textFieldPreparedSelection.layoutResult) != null) {
                                    int iJumpByLinesOffset3 = textFieldPreparedSelection.jumpByLinesOffset(textLayoutResult3, -1);
                                    textFieldPreparedSelection.setSelection(iJumpByLinesOffset3, iJumpByLinesOffset3);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 40:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResult4 = textFieldPreparedSelection.layoutResult) != null) {
                                    int iJumpByLinesOffset4 = textFieldPreparedSelection.jumpByLinesOffset(textLayoutResult4, 1);
                                    textFieldPreparedSelection.setSelection(iJumpByLinesOffset4, iJumpByLinesOffset4);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 41:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResultProxy3 = textFieldPreparedSelection.layoutResultProxy) != null) {
                                    int iJumpByPagesOffset3 = textFieldPreparedSelection.jumpByPagesOffset(textLayoutResultProxy3, -1);
                                    textFieldPreparedSelection.setSelection(iJumpByPagesOffset3, iJumpByPagesOffset3);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 42:
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0 && (textLayoutResultProxy4 = textFieldPreparedSelection.layoutResultProxy) != null) {
                                    int iJumpByPagesOffset4 = textFieldPreparedSelection.jumpByPagesOffset(textLayoutResultProxy4, 1);
                                    textFieldPreparedSelection.setSelection(iJumpByPagesOffset4, iJumpByPagesOffset4);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 43:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    textFieldPreparedSelection.setSelection(0, 0);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 44:
                                textFieldPreparedSelection.state.cachedX = null;
                                AnnotatedString annotatedString3 = textFieldPreparedSelection.annotatedString;
                                if (annotatedString3.text.length() > 0) {
                                    int length2 = annotatedString3.text.length();
                                    textFieldPreparedSelection.setSelection(length2, length2);
                                }
                                textFieldPreparedSelection.selectMovement();
                                break;
                            case 45:
                                textFieldPreparedSelection.state.cachedX = null;
                                if (textFieldPreparedSelection.annotatedString.text.length() > 0) {
                                    long j5 = textFieldPreparedSelection.selection;
                                    TextRange.Companion companion5 = TextRange.Companion;
                                    int i = (int) (j5 & 4294967295L);
                                    textFieldPreparedSelection.setSelection(i, i);
                                    break;
                                }
                                break;
                            case 46:
                                UndoManager undoManager = textFieldKeyInput.undoManager;
                                if (undoManager != null) {
                                    undoManager.makeSnapshot(TextFieldValue.m780copy3r_uNRQ$default(textFieldPreparedSelection.currentValue, textFieldPreparedSelection.annotatedString, textFieldPreparedSelection.selection, 4));
                                }
                                UndoManager undoManager2 = textFieldKeyInput.undoManager;
                                if (undoManager2 != null) {
                                    UndoManager.Entry entry2 = undoManager2.undoStack;
                                    if (entry2 != null && (entry = entry2.next) != null) {
                                        undoManager2.undoStack = entry;
                                        undoManager2.storedCharacters -= entry2.value.annotatedString.text.length();
                                        undoManager2.redoStack = new UndoManager.Entry(undoManager2.redoStack, entry2.value);
                                        textFieldValue = entry.value;
                                    }
                                    if (textFieldValue != null) {
                                        textFieldKeyInput.onValueChange.mo781invoke(textFieldValue);
                                        break;
                                    }
                                }
                                break;
                            case 47:
                                UndoManager undoManager3 = textFieldKeyInput.undoManager;
                                if (undoManager3 != null) {
                                    UndoManager.Entry entry3 = undoManager3.redoStack;
                                    if (entry3 != null) {
                                        undoManager3.redoStack = entry3.next;
                                        undoManager3.undoStack = new UndoManager.Entry(undoManager3.undoStack, entry3.value);
                                        undoManager3.storedCharacters = entry3.value.annotatedString.text.length() + undoManager3.storedCharacters;
                                        textFieldValue = entry3.value;
                                    }
                                    if (textFieldValue != null) {
                                        textFieldKeyInput.onValueChange.mo781invoke(textFieldValue);
                                        break;
                                    }
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                TextLayoutResultProxy layoutResult = textFieldKeyInput.state.getLayoutResult();
                OffsetMapping offsetMapping = textFieldKeyInput.offsetMapping;
                TextFieldValue textFieldValue = textFieldKeyInput.value;
                TextFieldPreparedSelection textFieldPreparedSelection = new TextFieldPreparedSelection(textFieldValue, offsetMapping, layoutResult, textPreparedSelectionState);
                function1.mo781invoke(textFieldPreparedSelection);
                if (!TextRange.m748equalsimpl0(textFieldPreparedSelection.selection, textFieldValue.selection) || !Intrinsics.areEqual(textFieldPreparedSelection.annotatedString, textFieldValue.annotatedString)) {
                    textFieldKeyInput.onValueChange.mo781invoke(TextFieldValue.m780copy3r_uNRQ$default(textFieldPreparedSelection.currentValue, textFieldPreparedSelection.annotatedString, textFieldPreparedSelection.selection, 4));
                }
                UndoManager undoManager = textFieldKeyInput.undoManager;
                if (undoManager != null) {
                    undoManager.forceNextSnapshot = true;
                }
                z = ref$BooleanRef.element;
            }
        } else if (z2) {
            textFieldKeyInput.apply(Collections.singletonList(commitTextCommand));
            textPreparedSelectionState.cachedX = null;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
