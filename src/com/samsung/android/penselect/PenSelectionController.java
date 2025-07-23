package com.samsung.android.penselect;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes6.dex */
public class PenSelectionController {
    private static final String TAG = "PenSelectController";
    private static PenSelectionController sInstance;

    static class PenSelectionContents {
        public String mContentStr;

        PenSelectionContents() {
        }
    }

    public static PenSelectionController getInstance() {
        if (sInstance == null) {
            sInstance = new PenSelectionController();
        }
        return sInstance;
    }

    private PenSelectionController() {
    }

    private boolean isVisibleView(View view) {
        return view != null && view.getVisibility() == 0 && view.getWidth() > 0 && view.getHeight() > 0;
    }

    private boolean getPenSelectionContents(Context context, View view, PenSelectionContents penSelectionContents) {
        if (isVisibleView(view)) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (textView.hasMultiSelection()) {
                    CharSequence multiSelectionText = textView.getMultiSelectionText();
                    if (!TextUtils.isEmpty(multiSelectionText)) {
                        if (TextUtils.isEmpty(penSelectionContents.mContentStr)) {
                            penSelectionContents.mContentStr = multiSelectionText.toString();
                        } else {
                            penSelectionContents.mContentStr += ShaderAssembler.NEWLINE + multiSelectionText.toString();
                        }
                        return true;
                    }
                }
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                boolean z = false;
                for (int i = 0; i < childCount; i++) {
                    if (getPenSelectionContents(context, viewGroup.getChildAt(i), penSelectionContents)) {
                        z = true;
                    }
                }
                return z;
            }
        }
        return false;
    }

    public String getPenSelectionContents(Context context, View view) {
        PenSelectionContents penSelectionContents = new PenSelectionContents();
        getPenSelectionContents(context, view, penSelectionContents);
        return penSelectionContents.mContentStr;
    }

    public boolean clearAllPenSelection(Context context, View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.hasMultiSelection()) {
                textView.clearMultiSelection();
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                clearAllPenSelection(context, viewGroup.getChildAt(i));
            }
        }
        return true;
    }

    public boolean isPenSelectionArea(Context context, View view, int i, int i2) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.hasMultiSelection() && textView.isMultiSelectionLinkArea(i, i2)) {
                return true;
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                if (isPenSelectionArea(context, viewGroup.getChildAt(i3), i, i2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public View findTargetTextView(Context context, View view, Rect rect) {
        Drawable background;
        View view2 = null;
        if (checkRectInView(view, rect)) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                if (childCount != 0 || (!(view instanceof WebView) && ((background = viewGroup.getBackground()) == null || !background.isVisible() || background.getOpacity() <= -2))) {
                    for (int i = childCount - 1; i >= 0; i--) {
                        view2 = findTargetTextView(context, viewGroup.getChildAt(i), rect);
                        if (view2 != null) {
                            return view2;
                        }
                    }
                    return view2;
                }
            } else if (view instanceof TextView) {
            }
            return view;
        }
        return null;
    }

    public boolean checkRectInView(View view, Rect rect) {
        if (view.getVisibility() != 0) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        return new Rect(i, iArr[1], view.getWidth() + i, iArr[1] + view.getHeight()).contains(rect);
    }
}
