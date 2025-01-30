package com.android.systemui.statusbar.notification;

import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Pools;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.notification.TransformState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        return layout != null ? (int) layout.getLineWidth(0) : super.getContentWidth();
    }

    public final int getEllipsisCount() {
        Layout layout = this.mText.getLayout();
        if (layout == null || layout.getLineCount() <= 0) {
            return 0;
        }
        return layout.getEllipsisCount(0);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
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

    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.statusbar.notification.TransformState
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean sameAs(TransformState transformState) {
        boolean z;
        if (this.mSameAsAny) {
            return true;
        }
        if (transformState instanceof TextViewTransformState) {
            TextViewTransformState textViewTransformState = (TextViewTransformState) transformState;
            if (TextUtils.equals(textViewTransformState.mText.getText(), this.mText.getText())) {
                if (getEllipsisCount() == textViewTransformState.getEllipsisCount() && this.mText.getLineCount() == textViewTransformState.mText.getLineCount()) {
                    KeyEvent.Callback callback = this.mText;
                    boolean z2 = callback instanceof Spanned;
                    if (z2 == (textViewTransformState.mText instanceof Spanned)) {
                        if (z2) {
                            Spanned spanned = (Spanned) callback;
                            Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                            Spanned spanned2 = (Spanned) textViewTransformState.mText;
                            Object[] spans2 = spanned2.getSpans(0, spanned2.length(), Object.class);
                            if (spans.length == spans2.length) {
                                for (int i = 0; i < spans.length; i++) {
                                    Object obj = spans[i];
                                    Object obj2 = spans2[i];
                                    if (obj.getClass().equals(obj2.getClass()) && spanned.getSpanStart(obj) == spanned2.getSpanStart(obj2) && spanned.getSpanEnd(obj) == spanned2.getSpanEnd(obj2)) {
                                    }
                                }
                            }
                        }
                        z = true;
                        if (z) {
                            return true;
                        }
                    }
                    z = false;
                    if (z) {
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
        return TextUtils.equals(this.mText.getText(), textViewTransformState.mText.getText()) && (lineCount = this.mText.getLineCount()) == 1 && lineCount == textViewTransformState.mText.getLineCount() && getEllipsisCount() == textViewTransformState.getEllipsisCount() && getContentHeight() != textViewTransformState.getContentHeight();
    }
}
