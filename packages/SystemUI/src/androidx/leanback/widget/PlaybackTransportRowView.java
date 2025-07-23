package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PlaybackTransportRowView extends LinearLayout {
    public PlaybackTransportRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        View childAt;
        if (view != null) {
            if (i == 33) {
                for (int indexOfChild = indexOfChild(getFocusedChild()) - 1; indexOfChild >= 0; indexOfChild--) {
                    View childAt2 = getChildAt(indexOfChild);
                    if (childAt2.hasFocusable()) {
                        return childAt2;
                    }
                }
            } else {
                if (i == 130) {
                    int indexOfChild2 = indexOfChild(getFocusedChild());
                    do {
                        indexOfChild2++;
                        if (indexOfChild2 < getChildCount()) {
                            childAt = getChildAt(indexOfChild2);
                        }
                    } while (!childAt.hasFocusable());
                    return childAt;
                }
                if ((i == 17 || i == 66) && (getFocusedChild() instanceof ViewGroup)) {
                    return FocusFinder.getInstance().findNextFocus((ViewGroup) getFocusedChild(), view, i);
                }
            }
        }
        return super.focusSearch(view, i);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        View findFocus = findFocus();
        if (findFocus != null && findFocus.requestFocus(i, rect)) {
            return true;
        }
        View findViewById = findViewById(R.id.playback_progress);
        if (findViewById != null && findViewById.isFocusable() && findViewById.requestFocus(i, rect)) {
            return true;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    public PlaybackTransportRowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
