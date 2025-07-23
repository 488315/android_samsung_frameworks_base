package com.android.wm.shell.common.pip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayLayout;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipDisplayLayoutState {
    public final Context mContext;
    public int mDisplayId;
    public Point mScreenEdgeInsets = null;
    public final DisplayLayout mDisplayLayout = new DisplayLayout();

    public PipDisplayLayoutState(Context context) {
        this.mContext = context;
        reloadResources();
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "  PipDisplayLayoutState", "    mDisplayId="), this.mDisplayId, printWriter, "    getDisplayBounds=");
        m.append(getDisplayBounds());
        printWriter.println(m.toString());
        printWriter.println("    mScreenEdgeInsets=" + this.mScreenEdgeInsets);
    }

    public final Rect getDisplayBounds() {
        DisplayLayout displayLayout = this.mDisplayLayout;
        return new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
    }

    public final DisplayLayout getDisplayLayout() {
        return new DisplayLayout(this.mDisplayLayout);
    }

    public final Rect getInsetBounds() {
        Rect rect = new Rect();
        Rect rect2 = getDisplayLayout().mStableInsets;
        int i = rect2.left;
        Point point = this.mScreenEdgeInsets;
        rect.set(i + point.x, rect2.top + point.y, (getDisplayLayout().mWidth - rect2.right) - this.mScreenEdgeInsets.x, (getDisplayLayout().mHeight - rect2.bottom) - this.mScreenEdgeInsets.y);
        return rect;
    }

    public final void reloadResources() {
        Point point;
        Resources resources = this.mContext.getResources();
        String string = resources.getString(R.string.config_defaultPictureInPictureScreenEdgeInsets);
        Size parseSize = !string.isEmpty() ? Size.parseSize(string) : null;
        if (parseSize == null) {
            point = new Point();
        } else {
            float width = parseSize.getWidth();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            PipUtils pipUtils = PipUtils.INSTANCE;
            point = new Point((int) TypedValue.applyDimension(1, width, displayMetrics), (int) TypedValue.applyDimension(1, parseSize.getHeight(), resources.getDisplayMetrics()));
        }
        this.mScreenEdgeInsets = point;
    }

    public final void rotateTo(int i) {
        this.mDisplayLayout.rotateTo(this.mContext.getResources(), i);
    }
}
