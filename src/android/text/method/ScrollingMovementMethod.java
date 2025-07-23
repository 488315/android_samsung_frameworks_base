package android.text.method;

import android.text.Layout;
import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class ScrollingMovementMethod extends BaseMovementMethod implements MovementMethod {
    private static ScrollingMovementMethod sInstance;

    @Override // android.text.method.BaseMovementMethod
    protected boolean left(TextView textView, Spannable spannable) {
        return scrollLeft(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean right(TextView textView, Spannable spannable) {
        return scrollRight(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean up(TextView textView, Spannable spannable) {
        return scrollUp(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean down(TextView textView, Spannable spannable) {
        return scrollDown(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageUp(TextView textView, Spannable spannable) {
        return scrollPageUp(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageDown(TextView textView, Spannable spannable) {
        return scrollPageDown(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean top(TextView textView, Spannable spannable) {
        return scrollTop(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean bottom(TextView textView, Spannable spannable) {
        return scrollBottom(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineStart(TextView textView, Spannable spannable) {
        return scrollLineStart(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineEnd(TextView textView, Spannable spannable) {
        return scrollLineEnd(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean home(TextView textView, Spannable spannable) {
        return top(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean end(TextView textView, Spannable spannable) {
        return bottom(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return Touch.onTouchEvent(textView, spannable, motionEvent);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(TextView textView, Spannable spannable, int i) {
        Layout layout = textView.getLayout();
        if (layout != null && (i & 2) != 0) {
            textView.scrollTo(textView.getScrollX(), layout.getLineTop(0));
        }
        if (layout == null || (i & 1) == 0) {
            return;
        }
        int totalPaddingTop = textView.getTotalPaddingTop() + textView.getTotalPaddingBottom();
        textView.scrollTo(textView.getScrollX(), layout.getLineTop(layout.getLineCount()) - (textView.getHeight() - totalPaddingTop));
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ScrollingMovementMethod();
        }
        return sInstance;
    }
}
