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

/* loaded from: classes.dex */
public class TooltipPopup {
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
        View viewInflate = LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.sesl_tooltip, (ViewGroup) null);
        this.mContentView = viewInflate;
        this.mMessageView = (TextView) viewInflate.findViewById(com.android.systemui.R.id.message);
        viewInflate.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.appcompat.widget.TooltipPopup.1
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
        layoutParams.setTitle(getClass().getSimpleName());
        layoutParams.packageName = this.mContext.getPackageName();
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 2132017161;
        layoutParams.flags = 262152;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int adjustTooltipPosition(int i, int i2, int i3) {
        int i4;
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        Context context = this.mContext;
        Resources resources = context.getResources();
        Rect rect = this.mTmpDisplayFrame;
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        defaultDisplay.getRealSize(point);
        int rotation2 = defaultDisplay.getRotation();
        int dimension = (int) resources.getDimension(com.android.systemui.R.dimen.sesl_navigation_bar_height);
        if (rotation2 == 1) {
            int i5 = rect.right;
            int i6 = i5 + dimension;
            int i7 = point.x;
            if (i6 >= i7) {
                this.mNavigationBarHeight = i7 - i5;
            } else if (rotation2 == 3 && (i4 = rect.left) <= dimension) {
                this.mNavigationBarHeight = i4;
            } else if (rotation == 1 || rotation == 3) {
                if (i <= 0) {
                    int iWidth = ((i2 - this.mTmpDisplayFrame.width()) / 2) + i3;
                    if (i < iWidth) {
                        return iWidth + i3;
                    }
                } else {
                    int iWidth2 = ((this.mTmpDisplayFrame.width() - i2) / 2) + i3;
                    if (i > iWidth2) {
                        return iWidth2 - i3;
                    }
                }
            }
            if (rotation == 1) {
                int iWidth3 = (((this.mTmpDisplayFrame.width() - i2) - this.mNavigationBarHeight) / 2) - i3;
                if (i > iWidth3) {
                    return iWidth3 - i3;
                }
            } else if (rotation == 3) {
                if (i <= 0) {
                    int iWidth4 = ((i2 - this.mTmpDisplayFrame.width()) / 2) + i3;
                    if (i <= iWidth4) {
                        return iWidth4 + i3;
                    }
                } else {
                    int iWidth5 = ((this.mTmpDisplayFrame.width() - i2) / 2) + i3;
                    if (i > iWidth5) {
                        return iWidth5 - i3;
                    }
                }
            }
        }
        return i;
    }

    public final void computePosition(View view, boolean z, WindowManager.LayoutParams layoutParams, boolean z2, boolean z3) throws Resources.NotFoundException {
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
        rootView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        Rect rect = this.mTmpDisplayFrame;
        if (rect.left < 0 && rect.top < 0) {
            Resources resources = this.mContext.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
            int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            this.mTmpDisplayFrame.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        int[] iArr = new int[2];
        rootView.getLocationOnScreen(iArr);
        int i = iArr[0];
        Rect rect2 = new Rect(i, iArr[1], rootView.getWidth() + i, rootView.getHeight() + iArr[1]);
        Rect rect3 = this.mTmpDisplayFrame;
        rect3.left = rect2.left;
        rect3.right = rect2.right;
        int[] iArr2 = this.mTmpAppPos;
        rootView.getLocationOnScreen(iArr2);
        int[] iArr3 = this.mTmpAnchorPos;
        view.getLocationOnScreen(iArr3);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame left : " + this.mTmpDisplayFrame.left);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame right : " + this.mTmpDisplayFrame.right);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame top : " + this.mTmpDisplayFrame.top);
        Log.i("SESL_TooltipPopup", "computePosition - displayFrame bottom : " + this.mTmpDisplayFrame.bottom);
        Log.i("SESL_TooltipPopup", "computePosition - anchorView locationOnScreen x: " + iArr3[0]);
        Log.i("SESL_TooltipPopup", "computePosition - anchorView locationOnScreen y : " + iArr3[1]);
        Log.i("SESL_TooltipPopup", "computePosition - appView locationOnScreen x : " + iArr2[0]);
        TooltipPopup$$ExternalSyntheticOutline0.m(iArr2[1], "SESL_TooltipPopup", new StringBuilder("computePosition - appView locationOnScreen y : "));
        int i2 = iArr3[0] - iArr2[0];
        iArr3[0] = i2;
        iArr3[1] = iArr3[1] - iArr2[1];
        layoutParams.x = (i2 + width) - (this.mTmpDisplayFrame.width() / 2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        int measuredWidth = this.mContentView.getMeasuredWidth();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_hover_tooltip_popup_right_margin);
        int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_hover_tooltip_popup_area_margin);
        int i3 = iArr3[1];
        int i4 = i3 - measuredHeight;
        int height = view.getHeight() + i3;
        if (z) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (view.getLayoutDirection() == 0) {
                int i5 = measuredWidth / 2;
                int width2 = (((view.getWidth() + iArr3[0]) - (this.mTmpDisplayFrame.width() / 2)) - i5) - dimensionPixelOffset;
                layoutParams.x = width2;
                if (width2 < ((-this.mTmpDisplayFrame.width()) / 2) + i5) {
                    layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i5 + dimensionPixelOffset;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                int iWidth = (measuredWidth / 2) + ((iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2)) + dimensionPixelOffset;
                layoutParams.x = iWidth;
                layoutParams.x = adjustTooltipPosition(iWidth, measuredWidth, dimensionPixelOffset);
            }
            if (height + measuredHeight > this.mTmpDisplayFrame.height()) {
                layoutParams.y = i4;
            } else {
                layoutParams.y = height;
            }
        } else {
            int iWidth2 = (iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2);
            layoutParams.x = iWidth2;
            int i6 = measuredWidth / 2;
            if (iWidth2 < ((-this.mTmpDisplayFrame.width()) / 2) + i6) {
                layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i6 + dimensionPixelOffset2;
            }
            layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            layoutParams.y = i4 >= 0 ? i4 : height;
        }
        if (z2) {
            layoutParams.y = view.getHeight() + iArr3[1];
        }
        if (z3) {
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            if (view.getLayoutDirection() == 0) {
                int i7 = measuredWidth / 2;
                int width3 = (((view.getWidth() + iArr3[0]) - (this.mTmpDisplayFrame.width() / 2)) - i7) - dimensionPixelOffset;
                layoutParams.x = width3;
                if (width3 < ((-this.mTmpDisplayFrame.width()) / 2) + i7) {
                    layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i7 + dimensionPixelOffset2;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                int iWidth3 = ((measuredWidth / 2) + ((iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2))) - dimensionPixelOffset;
                layoutParams.x = iWidth3;
                layoutParams.x = adjustTooltipPosition(iWidth3, measuredWidth, dimensionPixelOffset);
            }
            if (measuredHeight + height <= this.mTmpDisplayFrame.height()) {
                i4 = height;
            }
            layoutParams.y = i4;
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
