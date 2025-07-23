package com.google.android.setupdesign.accessibility;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LinkAccessibilityHelper extends AccessibilityDelegateCompat {
    public final AccessibilityDelegateCompat delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class PreOLinkAccessibilityHelper extends ExploreByTouchHelper {
        public final Rect tempRect;
        public final TextView view;

        public PreOLinkAccessibilityHelper(TextView textView) {
            super(textView);
            this.tempRect = new Rect();
            this.view = textView;
        }

        public final ClickableSpan getSpanForOffset(int i) {
            CharSequence text = this.view.getText();
            if (!(text instanceof Spanned)) {
                return null;
            }
            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) ((Spanned) text).getSpans(i, i, ClickableSpan.class);
            if (clickableSpanArr.length == 1) {
                return clickableSpanArr[0];
            }
            return null;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            int offsetForHorizontal;
            CharSequence text = this.view.getText();
            if (!(text instanceof Spanned)) {
                return Integer.MIN_VALUE;
            }
            Spanned spanned = (Spanned) text;
            TextView textView = this.view;
            if (textView.getLayout() == null) {
                offsetForHorizontal = -1;
            } else {
                offsetForHorizontal = textView.getLayout().getOffsetForHorizontal(textView.getLayout().getLineForVertical((int) (Math.min((textView.getHeight() - textView.getTotalPaddingBottom()) - 1, Math.max(0.0f, f2 - textView.getTotalPaddingTop())) + textView.getScrollY())), Math.min((textView.getWidth() - textView.getTotalPaddingRight()) - 1, Math.max(0.0f, f - textView.getTotalPaddingLeft())) + textView.getScrollX());
            }
            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spanned.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
            if (clickableSpanArr.length == 1) {
                return spanned.getSpanStart(clickableSpanArr[0]);
            }
            return Integer.MIN_VALUE;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            CharSequence text = this.view.getText();
            if (text instanceof Spanned) {
                Spanned spanned = (Spanned) text;
                for (ClickableSpan clickableSpan : (ClickableSpan[]) spanned.getSpans(0, spanned.length(), ClickableSpan.class)) {
                    ((ArrayList) list).add(Integer.valueOf(spanned.getSpanStart(clickableSpan)));
                }
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            ClickableSpan spanForOffset = getSpanForOffset(i);
            if (spanForOffset != null) {
                spanForOffset.onClick(this.view);
                return true;
            }
            ClockEventController$$ExternalSyntheticOutline0.m(i, "LinkSpan is null for offset: ", "LinkAccessibilityHelper");
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            ClickableSpan spanForOffset = getSpanForOffset(i);
            if (spanForOffset == null) {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "LinkSpan is null for offset: ", "LinkAccessibilityHelper");
                accessibilityEvent.setContentDescription(this.view.getText());
                return;
            }
            CharSequence text = this.view.getText();
            if (text instanceof Spanned) {
                Spanned spanned = (Spanned) text;
                text = spanned.subSequence(spanned.getSpanStart(spanForOffset), spanned.getSpanEnd(spanForOffset));
            }
            accessibilityEvent.setContentDescription(text);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Layout layout;
            ClickableSpan spanForOffset = getSpanForOffset(i);
            if (spanForOffset != null) {
                CharSequence text = this.view.getText();
                if (text instanceof Spanned) {
                    Spanned spanned = (Spanned) text;
                    text = spanned.subSequence(spanned.getSpanStart(spanForOffset), spanned.getSpanEnd(spanForOffset));
                }
                accessibilityNodeInfoCompat.setContentDescription(text);
            } else {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "LinkSpan is null for offset: ", "LinkAccessibilityHelper");
                accessibilityNodeInfoCompat.setContentDescription(this.view.getText());
            }
            accessibilityNodeInfoCompat.mInfo.setFocusable(true);
            accessibilityNodeInfoCompat.setClickable(true);
            Rect rect = this.tempRect;
            CharSequence text2 = this.view.getText();
            rect.setEmpty();
            if ((text2 instanceof Spanned) && (layout = this.view.getLayout()) != null) {
                Spanned spanned2 = (Spanned) text2;
                int spanStart = spanned2.getSpanStart(spanForOffset);
                int spanEnd = spanned2.getSpanEnd(spanForOffset);
                float primaryHorizontal = layout.getPrimaryHorizontal(spanStart);
                float primaryHorizontal2 = layout.getPrimaryHorizontal(spanEnd);
                int lineForOffset = layout.getLineForOffset(spanStart);
                int lineForOffset2 = layout.getLineForOffset(spanEnd);
                layout.getLineBounds(lineForOffset, rect);
                if (lineForOffset2 == lineForOffset) {
                    rect.left = (int) Math.min(primaryHorizontal, primaryHorizontal2);
                    rect.right = (int) Math.max(primaryHorizontal, primaryHorizontal2);
                } else if (layout.getParagraphDirection(lineForOffset) == -1) {
                    rect.right = (int) primaryHorizontal;
                } else {
                    rect.left = (int) primaryHorizontal;
                }
                rect.offset(this.view.getTotalPaddingLeft(), this.view.getTotalPaddingTop());
            }
            if (this.tempRect.isEmpty()) {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "LinkSpan bounds is empty for: ", "LinkAccessibilityHelper");
                this.tempRect.set(0, 0, 1, 1);
            }
            accessibilityNodeInfoCompat.setBoundsInParent(this.tempRect);
            accessibilityNodeInfoCompat.addAction(16);
        }
    }

    public LinkAccessibilityHelper(TextView textView) {
        this(new AccessibilityDelegateCompat());
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return this.delegate.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return this.delegate.getAccessibilityNodeProvider(view);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.delegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.delegate.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return this.delegate.performAccessibilityAction(view, i, bundle);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void sendAccessibilityEvent(View view, int i) {
        this.delegate.sendAccessibilityEvent(view, i);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }

    public LinkAccessibilityHelper(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        this.delegate = accessibilityDelegateCompat;
    }
}
