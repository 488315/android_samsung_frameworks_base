package android.text.method;

import android.graphics.Rect;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class ArrowKeyMovementMethod extends BaseMovementMethod implements MovementMethod {
    private static final Object LAST_TAP_DOWN = new Object();
    private static ArrowKeyMovementMethod sInstance;
    private boolean mIsSpanSet = false;

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean canSelectArbitrarily() {
        return true;
    }

    private static boolean isSelecting(Spannable spannable) {
        return MetaKeyKeyListener.getMetaState(spannable, 1) == 1 || MetaKeyKeyListener.getMetaState(spannable, 2048) != 0;
    }

    private static int getCurrentLineTop(Spannable spannable, Layout layout) {
        return layout.getLineTop(layout.getLineForOffset(Selection.getSelectionEnd(spannable)));
    }

    private static int getPageHeight(TextView textView) {
        Rect rect = new Rect();
        if (textView.getGlobalVisibleRect(rect)) {
            return rect.height();
        }
        return 0;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean handleMovementKey(TextView textView, Spannable spannable, int i, int i2, KeyEvent keyEvent) {
        if (i == 23 && KeyEvent.metaStateHasNoModifiers(i2) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0 && MetaKeyKeyListener.getMetaState(spannable, 2048, keyEvent) != 0) {
            return textView.showContextMenu();
        }
        return super.handleMovementKey(textView, spannable, i, i2, keyEvent);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean left(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendLeft(spannable, layout);
        }
        return Selection.moveLeft(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean right(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendRight(spannable, layout);
        }
        return Selection.moveRight(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean up(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendUp(spannable, layout);
        }
        return Selection.moveUp(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean down(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendDown(spannable, layout);
        }
        return Selection.moveDown(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageUp(TextView textView, Spannable spannable) {
        boolean z = false;
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        boolean zIsSelecting = isSelecting(spannable);
        int currentLineTop = getCurrentLineTop(spannable, layout) - getPageHeight(textView);
        do {
            int selectionEnd = Selection.getSelectionEnd(spannable);
            if (zIsSelecting) {
                Selection.extendUp(spannable, layout);
            } else {
                Selection.moveUp(spannable, layout);
            }
            if (Selection.getSelectionEnd(spannable) == selectionEnd) {
                return z;
            }
            z = true;
        } while (getCurrentLineTop(spannable, layout) > currentLineTop);
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageDown(TextView textView, Spannable spannable) {
        boolean z = false;
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        boolean zIsSelecting = isSelecting(spannable);
        int currentLineTop = getCurrentLineTop(spannable, layout) + getPageHeight(textView);
        do {
            int selectionEnd = Selection.getSelectionEnd(spannable);
            if (zIsSelecting) {
                Selection.extendDown(spannable, layout);
            } else {
                Selection.moveDown(spannable, layout);
            }
            if (Selection.getSelectionEnd(spannable) == selectionEnd) {
                return z;
            }
            z = true;
        } while (getCurrentLineTop(spannable, layout) < currentLineTop);
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean top(TextView textView, Spannable spannable) {
        if (isSelecting(spannable)) {
            Selection.extendSelection(spannable, 0);
            return true;
        }
        Selection.setSelection(spannable, 0);
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean bottom(TextView textView, Spannable spannable) {
        if (isSelecting(spannable)) {
            Selection.extendSelection(spannable, spannable.length());
            return true;
        }
        Selection.setSelection(spannable, spannable.length());
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineStart(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendToLeftEdge(spannable, layout);
        }
        return Selection.moveToLeftEdge(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineEnd(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendToRightEdge(spannable, layout);
        }
        return Selection.moveToRightEdge(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean leftWord(TextView textView, Spannable spannable) {
        int selectionEnd = textView.getSelectionEnd();
        WordIterator wordIterator = textView.getWordIterator();
        wordIterator.setCharSequence(spannable, selectionEnd, selectionEnd);
        return Selection.moveToPreceding(spannable, wordIterator, isSelecting(spannable));
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean rightWord(TextView textView, Spannable spannable) {
        int selectionEnd = textView.getSelectionEnd();
        WordIterator wordIterator = textView.getWordIterator();
        wordIterator.setCharSequence(spannable, selectionEnd, selectionEnd);
        return Selection.moveToFollowing(spannable, wordIterator, isSelecting(spannable));
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean home(TextView textView, Spannable spannable) {
        return lineStart(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean end(TextView textView, Spannable spannable) {
        return lineEnd(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    public boolean previousParagraph(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendToParagraphStart(spannable);
        }
        return Selection.moveToParagraphStart(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    public boolean nextParagraph(TextView textView, Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return Selection.extendToParagraphEnd(spannable);
        }
        return Selection.moveToParagraphEnd(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        int initialScrollX;
        int initialScrollY;
        int action = motionEvent.getAction();
        if (action == 1) {
            initialScrollX = Touch.getInitialScrollX(textView, spannable);
            initialScrollY = Touch.getInitialScrollY(textView, spannable);
        } else {
            initialScrollX = -1;
            initialScrollY = -1;
        }
        boolean zIsSelecting = isSelecting(spannable);
        boolean zOnTouchEvent = Touch.onTouchEvent(textView, spannable, motionEvent);
        if (!textView.didTouchFocusSelect()) {
            if (action == 0) {
                if (isSelecting(spannable) && (textView.isFocused() || textView.requestFocus())) {
                    int offsetForPosition = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    spannable.setSpan(LAST_TAP_DOWN, offsetForPosition, offsetForPosition, 34);
                    this.mIsSpanSet = true;
                    textView.getParent().requestDisallowInterceptTouchEvent(true);
                    return zOnTouchEvent;
                }
            } else if (textView.isFocused()) {
                if (action == 2) {
                    if (isSelecting(spannable) && zOnTouchEvent && this.mIsSpanSet) {
                        int spanStart = spannable.getSpanStart(LAST_TAP_DOWN);
                        textView.cancelLongPress();
                        int offsetForPosition2 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                        Selection.setSelection(spannable, Math.min(spanStart, offsetForPosition2), Math.max(spanStart, offsetForPosition2));
                        return true;
                    }
                } else if (action == 1) {
                    if ((initialScrollY >= 0 && initialScrollY != textView.getScrollY()) || (initialScrollX >= 0 && initialScrollX != textView.getScrollX())) {
                        textView.moveCursorToVisibleOffset();
                        return true;
                    }
                    if (zIsSelecting && this.mIsSpanSet) {
                        Object obj = LAST_TAP_DOWN;
                        int spanStart2 = spannable.getSpanStart(obj);
                        int offsetForPosition3 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                        Selection.setSelection(spannable, Math.min(spanStart2, offsetForPosition3), Math.max(spanStart2, offsetForPosition3));
                        spannable.removeSpan(obj);
                        this.mIsSpanSet = false;
                    }
                    MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
                    MetaKeyKeyListener.resetLockedMeta(spannable);
                    return true;
                }
            }
        }
        return zOnTouchEvent;
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void initialize(TextView textView, Spannable spannable) {
        Selection.setSelection(spannable, 0);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(TextView textView, Spannable spannable, int i) {
        if ((i & 130) != 0) {
            if (textView.getLayout() == null) {
                Selection.setSelection(spannable, spannable.length());
                return;
            }
            return;
        }
        Selection.setSelection(spannable, spannable.length());
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ArrowKeyMovementMethod();
        }
        return sInstance;
    }
}
