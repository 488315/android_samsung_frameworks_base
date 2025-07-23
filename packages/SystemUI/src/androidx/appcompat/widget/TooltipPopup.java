package androidx.appcompat.widget;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        layoutParams.setTitle(getClass().getSimpleName());
        layoutParams.packageName = this.mContext.getPackageName();
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 2132017161;
        layoutParams.flags = 262152;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int adjustTooltipPosition(int r10, int r11, int r12) {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r0 = r0.getDefaultDisplay()
            int r0 = r0.getRotation()
            android.content.Context r2 = r9.mContext
            android.content.res.Resources r3 = r2.getResources()
            android.graphics.Rect r4 = r9.mTmpDisplayFrame
            android.graphics.Point r5 = new android.graphics.Point
            r5.<init>()
            java.lang.Object r1 = r2.getSystemService(r1)
            android.view.WindowManager r1 = (android.view.WindowManager) r1
            android.view.Display r1 = r1.getDefaultDisplay()
            r1.getRealSize(r5)
            int r1 = r1.getRotation()
            r2 = 2131170242(0x7f0713c2, float:1.7954837E38)
            float r2 = r3.getDimension(r2)
            int r2 = (int) r2
            r3 = 3
            r6 = 1
            if (r1 != r6) goto L49
            int r7 = r4.right
            int r8 = r7 + r2
            int r5 = r5.x
            if (r8 < r5) goto L49
            int r5 = r5 - r7
            r9.mNavigationBarHeight = r5
            goto L51
        L49:
            if (r1 != r3) goto L84
            int r1 = r4.left
            if (r1 > r2) goto L84
            r9.mNavigationBarHeight = r1
        L51:
            if (r0 != r6) goto L64
            android.graphics.Rect r0 = r9.mTmpDisplayFrame
            int r0 = r0.width()
            int r0 = r0 - r11
            int r9 = r9.mNavigationBarHeight
            int r0 = r0 - r9
            int r0 = r0 / 2
            int r0 = r0 - r12
            if (r10 <= r0) goto La6
            int r0 = r0 - r12
            return r0
        L64:
            if (r0 != r3) goto La6
            if (r10 > 0) goto L76
            android.graphics.Rect r9 = r9.mTmpDisplayFrame
            int r9 = r9.width()
            int r11 = r11 - r9
            int r11 = r11 / 2
            int r11 = r11 + r12
            if (r10 > r11) goto La6
            int r11 = r11 + r12
            return r11
        L76:
            android.graphics.Rect r9 = r9.mTmpDisplayFrame
            int r9 = r9.width()
            int r9 = r9 - r11
            int r9 = r9 / 2
            int r9 = r9 + r12
            if (r10 <= r9) goto La6
            int r9 = r9 - r12
            return r9
        L84:
            if (r0 == r6) goto L88
            if (r0 != r3) goto La6
        L88:
            if (r10 > 0) goto L98
            android.graphics.Rect r9 = r9.mTmpDisplayFrame
            int r9 = r9.width()
            int r11 = r11 - r9
            int r11 = r11 / 2
            int r11 = r11 + r12
            if (r10 >= r11) goto La6
            int r11 = r11 + r12
            return r11
        L98:
            android.graphics.Rect r9 = r9.mTmpDisplayFrame
            int r9 = r9.width()
            int r9 = r9 - r11
            int r9 = r9 / 2
            int r9 = r9 + r12
            if (r10 <= r9) goto La6
            int r9 = r9 - r12
            return r9
        La6:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.TooltipPopup.adjustTooltipPosition(int, int, int):int");
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
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(makeMeasureSpec, makeMeasureSpec);
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
                int width3 = (measuredWidth / 2) + ((iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2)) + dimensionPixelOffset;
                layoutParams.x = width3;
                layoutParams.x = adjustTooltipPosition(width3, measuredWidth, dimensionPixelOffset);
            }
            if (height + measuredHeight > this.mTmpDisplayFrame.height()) {
                layoutParams.y = i4;
            } else {
                layoutParams.y = height;
            }
        } else {
            int width4 = (iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2);
            layoutParams.x = width4;
            int i6 = measuredWidth / 2;
            if (width4 < ((-this.mTmpDisplayFrame.width()) / 2) + i6) {
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
                int width5 = (((view.getWidth() + iArr3[0]) - (this.mTmpDisplayFrame.width() / 2)) - i7) - dimensionPixelOffset;
                layoutParams.x = width5;
                if (width5 < ((-this.mTmpDisplayFrame.width()) / 2) + i7) {
                    layoutParams.x = ((-this.mTmpDisplayFrame.width()) / 2) + i7 + dimensionPixelOffset2;
                }
                layoutParams.x = adjustTooltipPosition(layoutParams.x, measuredWidth, dimensionPixelOffset);
            } else {
                int width6 = ((measuredWidth / 2) + ((iArr3[0] + width) - (this.mTmpDisplayFrame.width() / 2))) - dimensionPixelOffset;
                layoutParams.x = width6;
                layoutParams.x = adjustTooltipPosition(width6, measuredWidth, dimensionPixelOffset);
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
