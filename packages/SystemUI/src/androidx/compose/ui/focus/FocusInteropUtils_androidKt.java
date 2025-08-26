package androidx.compose.ui.focus;

import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.platform.AndroidComposeView;

/* loaded from: classes.dex */
public abstract class FocusInteropUtils_androidKt {
    public static final Rect calculateBoundingRectRelativeTo(View view, AndroidComposeView androidComposeView) {
        FocusInteropUtils.Companion.getClass();
        int[] iArr = FocusInteropUtils.tempCoordinates;
        view.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        androidComposeView.getLocationInWindow(iArr);
        float f = i - iArr[0];
        float f2 = i2 - iArr[1];
        return new Rect(f, f2, view.getWidth() + f, view.getHeight() + f2);
    }

    public static final boolean requestInteropFocus(View view, Integer num, android.graphics.Rect rect) {
        if (num == null) {
            return view.requestFocus();
        }
        if (!(view instanceof ViewGroup)) {
            return view.requestFocus(num.intValue(), rect);
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.isFocused()) {
            return true;
        }
        if (viewGroup.isFocusable() && !view.hasFocus()) {
            return view.requestFocus(num.intValue(), rect);
        }
        if (view instanceof AndroidComposeView) {
            return view.requestFocus(num.intValue(), rect);
        }
        if (rect != null) {
            View viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(viewGroup, rect, num.intValue());
            return viewFindNextFocusFromRect != null ? viewFindNextFocusFromRect.requestFocus(num.intValue(), rect) : view.requestFocus(num.intValue(), rect);
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(viewGroup, view.hasFocus() ? view.findFocus() : null, num.intValue());
        return viewFindNextFocus != null ? viewFindNextFocus.requestFocus(num.intValue()) : view.requestFocus(num.intValue());
    }

    /* renamed from: toAndroidFocusDirection-3ESFkO8, reason: not valid java name */
    public static final Integer m370toAndroidFocusDirection3ESFkO8(int i) {
        FocusDirection.Companion companion = FocusDirection.Companion;
        companion.getClass();
        if (i == FocusDirection.Up) {
            return 33;
        }
        companion.getClass();
        if (i == FocusDirection.Down) {
            return 130;
        }
        companion.getClass();
        if (i == FocusDirection.Left) {
            return 17;
        }
        companion.getClass();
        if (i == FocusDirection.Right) {
            return 66;
        }
        companion.getClass();
        if (i == FocusDirection.Next) {
            return 2;
        }
        companion.getClass();
        return i == FocusDirection.Previous ? 1 : null;
    }

    public static final FocusDirection toFocusDirection(int i) {
        if (i == 1) {
            FocusDirection.Companion.getClass();
            return FocusDirection.m368boximpl(FocusDirection.Previous);
        }
        if (i == 2) {
            FocusDirection.Companion.getClass();
            return FocusDirection.m368boximpl(FocusDirection.Next);
        }
        if (i == 17) {
            FocusDirection.Companion.getClass();
            return FocusDirection.m368boximpl(FocusDirection.Left);
        }
        if (i == 33) {
            FocusDirection.Companion.getClass();
            return FocusDirection.m368boximpl(FocusDirection.Up);
        }
        if (i == 66) {
            FocusDirection.Companion.getClass();
            return FocusDirection.m368boximpl(FocusDirection.Right);
        }
        if (i != 130) {
            return null;
        }
        FocusDirection.Companion.getClass();
        return FocusDirection.m368boximpl(FocusDirection.Down);
    }
}
