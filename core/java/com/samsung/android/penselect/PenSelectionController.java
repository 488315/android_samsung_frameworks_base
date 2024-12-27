package com.samsung.android.penselect;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

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

    private boolean getPenSelectionContents(Context context, View view, PenSelectionContents contents) {
        boolean haveContents = false;
        if (!isVisibleView(view)) {
            return false;
        }
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (!textView.hasMultiSelection()) {
                return false;
            }
            CharSequence selectedText = textView.getMultiSelectionText();
            if (TextUtils.isEmpty(selectedText)) {
                return false;
            }
            if (TextUtils.isEmpty(contents.mContentStr)) {
                contents.mContentStr = selectedText.toString();
            } else {
                contents.mContentStr += "\n" + selectedText.toString();
            }
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup vg = (ViewGroup) view;
        int childCount = vg.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = vg.getChildAt(i);
            if (getPenSelectionContents(context, child, contents)) {
                haveContents = true;
            }
        }
        return haveContents;
    }

    public String getPenSelectionContents(Context context, View topMostView) {
        PenSelectionContents contents = new PenSelectionContents();
        getPenSelectionContents(context, topMostView, contents);
        return contents.mContentStr;
    }

    public boolean clearAllPenSelection(Context context, View topMostView) {
        if (topMostView instanceof TextView) {
            TextView textView = (TextView) topMostView;
            if (textView.hasMultiSelection()) {
                textView.clearMultiSelection();
            }
        } else if (topMostView instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) topMostView;
            int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = vg.getChildAt(i);
                clearAllPenSelection(context, child);
            }
        }
        return true;
    }

    public boolean isPenSelectionArea(Context context, View topMostView, int x, int y) {
        if (topMostView instanceof TextView) {
            TextView textView = (TextView) topMostView;
            return textView.hasMultiSelection() && textView.isMultiSelectionLinkArea(x, y);
        }
        if (topMostView instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) topMostView;
            int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = vg.getChildAt(i);
                if (isPenSelectionArea(context, child, x, y)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public View findTargetTextView(Context context, View topMostView, Rect rect) {
        if (checkRectInView(topMostView, rect)) {
            if (topMostView instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) topMostView;
                View retView = null;
                int childCount = vg.getChildCount();
                if (childCount == 0) {
                    if (topMostView instanceof WebView) {
                        return topMostView;
                    }
                    Drawable background = vg.getBackground();
                    if (background != null && background.isVisible() && background.getOpacity() > -2) {
                        return topMostView;
                    }
                }
                for (int i = childCount - 1; i >= 0; i--) {
                    View child = vg.getChildAt(i);
                    retView = findTargetTextView(context, child, rect);
                    if (retView != null) {
                        break;
                    }
                }
                return retView;
            }
            if (topMostView instanceof TextView) {
                return topMostView;
            }
            return null;
        }
        return null;
    }

    public boolean checkRectInView(View view, Rect rectSrc) {
        if (view.getVisibility() != 0) {
            return false;
        }
        int[] screenOffsetOfView = new int[2];
        view.getLocationOnScreen(screenOffsetOfView);
        Rect rect = new Rect(screenOffsetOfView[0], screenOffsetOfView[1], screenOffsetOfView[0] + view.getWidth(), screenOffsetOfView[1] + view.getHeight());
        return rect.contains(rectSrc);
    }
}
