package com.android.internal.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.ScrollCaptureCallback;
import android.view.View;
import android.view.ViewGroup;
import android.view.flags.Flags;
import android.webkit.WebView;
import android.widget.ListView;

/* loaded from: classes4.dex */
public class ScrollCaptureInternal {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_VERBOSE = false;
    private static final int DOWN = 1;
    private static final String TAG = "ScrollCaptureInternal";
    public static final int TYPE_FIXED = 0;
    public static final int TYPE_OPAQUE = 3;
    public static final int TYPE_RECYCLING = 2;
    public static final int TYPE_SCROLLING = 1;
    private static final int UP = -1;

    public static int detectScrollingType(View view) {
        if (!view.canScrollVertically(1) && !view.canScrollVertically(-1)) {
            return 0;
        }
        if (!(view instanceof ViewGroup)) {
            return 3;
        }
        if (!Flags.scrollCaptureRelaxScrollViewCriteria() && ((ViewGroup) view).getChildCount() > 1) {
            return 2;
        }
        if (((ViewGroup) view).getChildCount() == 0) {
            Log.w(TAG, "scrollable but no children!");
            return 3;
        }
        if (view.getScrollY() != 0) {
            return 1;
        }
        Log.v(TAG, "hint: scrollY == 0");
        if (view.canScrollVertically(-1)) {
            return 2;
        }
        view.scrollTo(view.getScrollX(), 1);
        if (view.getScrollY() != 1) {
            return 2;
        }
        view.scrollTo(view.getScrollX(), 0);
        return 1;
    }

    public ScrollCaptureCallback requestCallback(View view, Rect rect, Point point) {
        int detectScrollingType = detectScrollingType(view);
        if (detectScrollingType == 1) {
            return new ScrollCaptureViewSupport((ViewGroup) view, new ScrollViewCaptureHelper());
        }
        if (detectScrollingType == 2) {
            if (view instanceof ListView) {
                return new ScrollCaptureViewSupport((ListView) view, new ListViewCaptureHelper());
            }
            return new ScrollCaptureViewSupport((ViewGroup) view, new RecyclerViewCaptureHelper());
        }
        if (detectScrollingType != 3 || !(view instanceof WebView)) {
            return null;
        }
        Log.d(TAG, "scroll capture: Using WebView support");
        return new ScrollCaptureViewSupport((WebView) view, new WebViewCaptureHelper());
    }

    private static String formatIntToHexString(int i) {
        return "0x" + Integer.toHexString(i).toUpperCase();
    }

    static String resolveId(Context context, int i) {
        Resources resources = context.getResources();
        if (i >= 0) {
            try {
                return resources.getResourceTypeName(i) + '/' + resources.getResourceEntryName(i);
            } catch (Resources.NotFoundException unused) {
                return "id/" + formatIntToHexString(i);
            }
        }
        return "NO_ID";
    }
}
