package androidx.appcompat.widget;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TooltipPopup {
    public final View mContentView;
    public final Context mContext;
    public final WindowManager.LayoutParams mLayoutParams;
    public final TextView mMessageView;
    public int mNavigationBarHeight;
    public final int[] mTmpAnchorPos;
    public final int[] mTmpAppPos;
    public final Rect mTmpDisplayFrame;

    public TooltipPopup(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        this.mTmpDisplayFrame = new Rect();
        this.mTmpAnchorPos = new int[2];
        this.mTmpAppPos = new int[2];
        this.mNavigationBarHeight = 0;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.popupTheme, typedValue, false);
        if (typedValue.data != 0) {
            this.mContext = new ContextThemeWrapper(context, typedValue.data);
        } else {
            this.mContext = context;
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.sesl_tooltip, (ViewGroup) null);
        this.mContentView = inflate;
        this.mMessageView = (TextView) inflate.findViewById(com.android.systemui.R.id.message);
        inflate.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.appcompat.widget.TooltipPopup.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    TooltipPopup.this.hide();
                    return true;
                }
                if (action != 4) {
                    return false;
                }
                TooltipPopup.this.hide();
                return false;
            }
        });
        layoutParams.setTitle("TooltipPopup");
        layoutParams.packageName = this.mContext.getPackageName();
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 2132017161;
        layoutParams.flags = 262152;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int adjustTooltipPosition(int i, int i2, int i3) {
        boolean z;
        int i4;
        int width;
        Context context = this.mContext;
        int rotation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
        Resources resources = context.getResources();
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        defaultDisplay.getRealSize(point);
        int rotation2 = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(com.android.systemui.R.dimen.sesl_navigation_bar_height);
        Rect rect = this.mTmpDisplayFrame;
        if (rotation2 == 1) {
            int i5 = rect.right;
            int i6 = i5 + dimension;
            int i7 = point.x;
            if (i6 >= i7) {
                this.mNavigationBarHeight = i7 - i5;
                z = true;
                if (z) {
                    if (rotation == 1) {
                        int width2 = (((rect.width() - i2) - this.mNavigationBarHeight) / 2) - i3;
                        return i > width2 ? width2 - i3 : i;
                    }
                    if (rotation != 3) {
                        return i;
                    }
                    if (i <= 0) {
                        int width3 = ((i2 - rect.width()) / 2) + i3;
                        return i <= width3 ? width3 + i3 : i;
                    }
                    width = ((rect.width() - i2) / 2) + i3;
                    if (i <= width) {
                        return i;
                    }
                } else {
                    if (rotation != 1 && rotation != 3) {
                        return i;
                    }
                    if (i <= 0) {
                        int width4 = ((i2 - rect.width()) / 2) + i3;
                        return i < width4 ? width4 + i3 : i;
                    }
                    width = ((rect.width() - i2) / 2) + i3;
                    if (i <= width) {
                        return i;
                    }
                }
                return width - i3;
            }
        }
        if (rotation2 != 3 || (i4 = rect.left) > dimension) {
            z = false;
            if (z) {
            }
            return width - i3;
        }
        this.mNavigationBarHeight = i4;
        z = true;
        if (z) {
        }
        return width - i3;
    }

    public final void computePosition(View view, boolean z, WindowManager.LayoutParams layoutParams, boolean z2, boolean z3) {
        layoutParams.token = view.getApplicationWindowToken();
        int width = view.getWidth() / 2;
        layoutParams.gravity = 49;
        View rootView = view.getRootView();
        ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
        if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
            Context context = view.getContext();
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    break;
                }
                if (context instanceof Activity) {
                    rootView = ((Activity) context).getWindow().getDecorView();
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        if (rootView == null) {
            Log.e("SESL_TooltipPopup", "Cannot find app view");
            return;
        }
        Rect rect = this.mTmpDisplayFrame;
        rootView.getWindowVisibleDisplayFrame(rect);
        int i = rect.left;
        Context context2 = this.mContext;
        if (i < 0 && rect.top < 0) {
            Resources resources = context2.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
            int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            rect.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        int[] iArr = new int[2];
        rootView.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        Rect rect2 = new Rect(i2, iArr[1], rootView.getWidth() + i2, rootView.getHeight() + iArr[1]);
        rect.left = rect2.left;
        rect.right = rect2.right;
        int[] iArr2 = this.mTmpAppPos;
        rootView.getLocationOnScreen(iArr2);
        int[] iArr3 = this.mTmpAnchorPos;
        view.getLocationOnScreen(iArr3);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame left : " + rect.left);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame right : " + rect.right);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame top : " + rect.top);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame bottom : " + rect.bottom);
        Log.i("SESL_TooltipPopup", "computePosition - anchorView locationOnScreen x: " + iArr3[0]);
        Log.i("SESL_TooltipPopup", "computePosition - anchorView locationOnScreen y : " + iArr3[1]);
        Log.i("SESL_TooltipPopup", "computePosition - appView locationOnScreen x : " + iArr2[0]);
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("computePosition - appView locationOnScreen y : "), iArr2[1], "SESL_TooltipPopup");
        int i3 = iArr3[0] - iArr2[0];
        iArr3[0] = i3;
        iArr3[1] = iArr3[1] - iArr2[1];
        layoutParams.x = (i3 + width) - (rect.width() / 2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        View view2 = this.mContentView;
        view2.measure(makeMeasureSpec, makeMeasureSpec);
        int measuredHeight = view2.getMeasuredHeight();
        int measuredWidth = view2.getMeasuredWidth();
        int dimensionPixelOffset = context2.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_hover_tooltip_popup_right_margin);
        int dimensionPixelOffset2 = context2.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_hover_tooltip_popup_area_margin);
        int i4 = iArr3[1];
        int i5 = i4 - measuredHeight;
        int height = view.getHeight() + i4;
        if (z) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                int i6 = measuredWidth / 2;
                int width2 = (((view.getWidth() + iArr3[0]) - (rect.width() / 2)) - i6) - dimensionPixelOffset;
                layoutParams.x = width2;
                if (width2 < ((-rect.width()) / 2) + i6) {
                    layoutParams.x = ((-rect.width()) / 2) + i6 + dimensionPixelOffset;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                int width3 = (measuredWidth / 2) + ((iArr3[0] + width) - (rect.width() / 2)) + dimensionPixelOffset;
                layoutParams.x = width3;
                layoutParams.x = adjustTooltipPosition(width3, measuredWidth, dimensionPixelOffset);
            }
            if (height + measuredHeight > rect.height()) {
                layoutParams.y = i5;
            } else {
                layoutParams.y = height;
            }
        } else {
            int width4 = (iArr3[0] + width) - (rect.width() / 2);
            layoutParams.x = width4;
            int i7 = measuredWidth / 2;
            if (width4 < ((-rect.width()) / 2) + i7) {
                layoutParams.x = ((-rect.width()) / 2) + i7 + dimensionPixelOffset2;
            }
            layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            layoutParams.y = i5 >= 0 ? i5 : height;
        }
        if (z2) {
            layoutParams.y = view.getHeight() + iArr3[1];
        }
        if (z3) {
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.getLayoutDirection(view) == 0) {
                int i8 = measuredWidth / 2;
                int width5 = (((view.getWidth() + iArr3[0]) - (rect.width() / 2)) - i8) - dimensionPixelOffset;
                layoutParams.x = width5;
                if (width5 < ((-rect.width()) / 2) + i8) {
                    layoutParams.x = ((-rect.width()) / 2) + i8 + dimensionPixelOffset2;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                int width6 = ((measuredWidth / 2) + ((iArr3[0] + width) - (rect.width() / 2))) - dimensionPixelOffset;
                layoutParams.x = width6;
                layoutParams.x = adjustTooltipPosition(width6, measuredWidth, dimensionPixelOffset);
            }
            if (measuredHeight + height <= rect.height()) {
                i5 = height;
            }
            layoutParams.y = i5;
        }
    }

    public final void hide() {
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mContentView);
        }
    }

    public final boolean isShowing() {
        return this.mContentView.getParent() != null;
    }
}
