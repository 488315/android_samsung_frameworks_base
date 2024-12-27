package com.android.systemui.statusbar.notification;

import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Pools;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.ViewTransformationHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TextViewTransformState extends TransformState {
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public TextView mText;

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final int getContentHeight() {
        return this.mText.getLineHeight();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final int getContentWidth() {
        Layout layout = this.mText.getLayout();
        return layout != null ? (int) layout.getLineWidth(0) : this.mTransformedView.getWidth();
    }

    public final int getEllipsisCount() {
        Layout layout = this.mText.getLayout();
        if (layout == null || layout.getLineCount() <= 0) {
            return 0;
        }
        return layout.getEllipsisCount(0);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, ViewTransformationHelper viewTransformationHelper) {
        super.initFrom(view, viewTransformationHelper);
        this.mText = (TextView) view;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void reset() {
        super.reset();
        this.mText = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean sameAs(TransformState transformState) {
        KeyEvent.Callback callback;
        boolean z;
        int i;
        if (this.mSameAsAny) {
            return true;
        }
        if (transformState instanceof TextViewTransformState) {
            TextViewTransformState textViewTransformState = (TextViewTransformState) transformState;
            if (TextUtils.equals(textViewTransformState.mText.getText(), this.mText.getText())) {
                if (getEllipsisCount() == textViewTransformState.getEllipsisCount() && this.mText.getLineCount() == textViewTransformState.mText.getLineCount() && (z = (callback = this.mText) instanceof Spanned) == (textViewTransformState.mText instanceof Spanned)) {
                    if (!z) {
                        return true;
                    }
                    Spanned spanned = (Spanned) callback;
                    Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                    Spanned spanned2 = (Spanned) textViewTransformState.mText;
                    Object[] spans2 = spanned2.getSpans(0, spanned2.length(), Object.class);
                    if (spans.length == spans2.length) {
                        for (0; i < spans.length; i + 1) {
                            Object obj = spans[i];
                            Object obj2 = spans2[i];
                            i = (obj.getClass().equals(obj2.getClass()) && spanned.getSpanStart(obj) == spanned2.getSpanStart(obj2) && spanned.getSpanEnd(obj) == spanned2.getSpanEnd(obj2)) ? i + 1 : 0;
                        }
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean transformScale(TransformState transformState) {
        int lineCount;
        if (!(transformState instanceof TextViewTransformState)) {
            return false;
        }
        TextViewTransformState textViewTransformState = (TextViewTransformState) transformState;
        return TextUtils.equals(this.mText.getText(), textViewTransformState.mText.getText()) && (lineCount = this.mText.getLineCount()) == 1 && lineCount == textViewTransformState.mText.getLineCount() && getEllipsisCount() == textViewTransformState.getEllipsisCount() && this.mText.getLineHeight() != textViewTransformState.mText.getLineHeight();
    }
}
