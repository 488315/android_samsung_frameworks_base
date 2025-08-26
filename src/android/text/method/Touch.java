package android.text.method;

import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class Touch {
    private Touch() {
    }

    public static void scrollTo(TextView textView, Layout layout, int i, int i2) {
        int iMax;
        int i3;
        int width = textView.getWidth() - (textView.getTotalPaddingLeft() + textView.getTotalPaddingRight());
        int lineForVertical = layout.getLineForVertical(i2);
        Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(lineForVertical);
        int iMax2 = 0;
        boolean z = layout.getParagraphDirection(lineForVertical) > 0;
        if (textView.getHorizontallyScrolling()) {
            int lineForVertical2 = layout.getLineForVertical((textView.getHeight() + i2) - (textView.getTotalPaddingTop() + textView.getTotalPaddingBottom()));
            iMax = 0;
            iMax2 = Integer.MAX_VALUE;
            while (lineForVertical <= lineForVertical2) {
                iMax2 = (int) Math.min(iMax2, layout.getLineLeft(lineForVertical));
                iMax = (int) Math.max(iMax, layout.getLineRight(lineForVertical));
                lineForVertical++;
            }
        } else {
            iMax = width;
        }
        int i4 = iMax - iMax2;
        if (i4 < width) {
            if (paragraphAlignment == Layout.Alignment.ALIGN_CENTER) {
                i3 = (width - i4) / 2;
            } else if ((z && paragraphAlignment == Layout.Alignment.ALIGN_OPPOSITE) || ((!z && paragraphAlignment == Layout.Alignment.ALIGN_NORMAL) || paragraphAlignment == Layout.Alignment.ALIGN_RIGHT)) {
                i3 = width - i4;
            }
            iMax2 -= i3;
        } else {
            iMax2 = Math.max(Math.min(i, iMax - width), iMax2);
        }
        textView.scrollTo(iMax2, i2);
    }

    public static boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        float x;
        float y;
        float y2;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            for (Object obj : (DragState[]) spannable.getSpans(0, spannable.length(), DragState.class)) {
                spannable.removeSpan(obj);
            }
            spannable.setSpan(new DragState(motionEvent.getX(), motionEvent.getY(), textView.getScrollX(), textView.getScrollY()), 0, 0, 17);
            return true;
        }
        if (actionMasked == 1) {
            DragState[] dragStateArr = (DragState[]) spannable.getSpans(0, spannable.length(), DragState.class);
            for (DragState dragState : dragStateArr) {
                spannable.removeSpan(dragState);
            }
            return dragStateArr.length > 0 && dragStateArr[0].mUsed;
        }
        if (actionMasked == 2) {
            DragState[] dragStateArr2 = (DragState[]) spannable.getSpans(0, spannable.length(), DragState.class);
            if (dragStateArr2.length > 0) {
                if (!dragStateArr2[0].mFarEnough) {
                    float scaledTouchSlop = ViewConfiguration.get(textView.getContext()).getScaledTouchSlop();
                    if (Math.abs(motionEvent.getX() - dragStateArr2[0].mX) >= scaledTouchSlop || Math.abs(motionEvent.getY() - dragStateArr2[0].mY) >= scaledTouchSlop) {
                        dragStateArr2[0].mFarEnough = true;
                    }
                }
                if (dragStateArr2[0].mFarEnough) {
                    dragStateArr2[0].mUsed = true;
                    if ((motionEvent.getMetaState() & 1) != 0 || MetaKeyKeyListener.getMetaState(spannable, 1) == 1 || MetaKeyKeyListener.getMetaState(spannable, 2048) != 0) {
                        x = motionEvent.getX() - dragStateArr2[0].mX;
                        y = motionEvent.getY();
                        y2 = dragStateArr2[0].mY;
                    } else {
                        x = dragStateArr2[0].mX - motionEvent.getX();
                        y = dragStateArr2[0].mY;
                        y2 = motionEvent.getY();
                    }
                    float f = y - y2;
                    dragStateArr2[0].mX = motionEvent.getX();
                    dragStateArr2[0].mY = motionEvent.getY();
                    int scrollX = textView.getScrollX() + ((int) x);
                    int scrollY = textView.getScrollY() + ((int) f);
                    int totalPaddingTop = textView.getTotalPaddingTop() + textView.getTotalPaddingBottom();
                    Layout layout = textView.getLayout();
                    int iMax = Math.max(Math.min(scrollY, layout.getHeight() - (textView.getHeight() - totalPaddingTop)), 0);
                    int scrollX2 = textView.getScrollX();
                    int scrollY2 = textView.getScrollY();
                    scrollTo(textView, layout, scrollX, iMax);
                    if (scrollX2 != textView.getScrollX() || scrollY2 != textView.getScrollY()) {
                        textView.cancelLongPress();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static int getInitialScrollX(TextView textView, Spannable spannable) {
        DragState[] dragStateArr = (DragState[]) spannable.getSpans(0, spannable.length(), DragState.class);
        if (dragStateArr.length > 0) {
            return dragStateArr[0].mScrollX;
        }
        return -1;
    }

    public static int getInitialScrollY(TextView textView, Spannable spannable) {
        DragState[] dragStateArr = (DragState[]) spannable.getSpans(0, spannable.length(), DragState.class);
        if (dragStateArr.length > 0) {
            return dragStateArr[0].mScrollY;
        }
        return -1;
    }

    private static class DragState implements NoCopySpan {
        public boolean mFarEnough;
        public int mScrollX;
        public int mScrollY;
        public boolean mUsed;
        public float mX;
        public float mY;

        public DragState(float f, float f2, int i, int i2) {
            this.mX = f;
            this.mY = f2;
            this.mScrollX = i;
            this.mScrollY = i2;
        }
    }
}
