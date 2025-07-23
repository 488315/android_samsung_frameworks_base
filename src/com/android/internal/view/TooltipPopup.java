package com.android.internal.view;

import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.ViewRune;

/* loaded from: classes4.dex */
public class TooltipPopup {
    private static final String TAG = "TooltipPopup";
    private View mContentView;
    private Context mContext;
    private boolean mIsCaptionMenuButton;
    private boolean mIsCaptionPopupButton;
    private final boolean mIsDeviceDefault;
    private boolean mIsDexMode;
    private int mLastOrientation;
    private final WindowManager.LayoutParams mLayoutParams;
    private final TextView mMessageView;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener;
    private final int[] mTmpAnchorPos;
    private final int[] mTmpAppPos;
    private final Rect mTmpDisplayFrame;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = view.getResources().getConfiguration().orientation;
        if (this.mLastOrientation == i9 || this.mContentView == null || !isShowing()) {
            return;
        }
        this.mLastOrientation = i9;
        hide();
    }

    public TooltipPopup(Context context) {
        View inflate;
        this.mContentView = null;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        this.mTmpDisplayFrame = new Rect();
        this.mTmpAnchorPos = new int[2];
        this.mTmpAppPos = new int[2];
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.internal.view.TooltipPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TooltipPopup.this.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        };
        this.mIsDexMode = false;
        this.mIsCaptionMenuButton = false;
        this.mIsCaptionPopupButton = false;
        this.mContext = context;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z = typedValue.data != 0;
        this.mIsDeviceDefault = z;
        this.mContext = context;
        if (z) {
            context.getTheme().resolveAttribute(16843945, typedValue, false);
            if (typedValue.data != 0) {
                this.mContext = new ContextThemeWrapper(context, typedValue.data);
            }
        }
        if (z) {
            inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sem_tooltip, (ViewGroup) null);
        } else {
            inflate = LayoutInflater.from(this.mContext).inflate(R.layout.tooltip, (ViewGroup) null);
        }
        this.mContentView = inflate;
        this.mMessageView = (TextView) inflate.findViewById(16908299);
        this.mLastOrientation = context.getResources().getConfiguration().orientation;
        layoutParams.setTitle(this.mContext.getString(R.string.tooltip_popup_title));
        layoutParams.packageName = this.mContext.getOpPackageName();
        layoutParams.type = 1005;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation_Tooltip;
        layoutParams.flags = 24;
    }

    public void show(View view, int i, int i2, boolean z, CharSequence charSequence) {
        if (ViewRune.WIDGET_HOVER_POPUP && view == null) {
            Log.e(TAG, "show - anchorView is null");
            return;
        }
        View view2 = this.mContentView;
        if (view2 != null) {
            view2.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        if (ViewRune.WIDGET_HOVER_POPUP && !z && view.semGetHoverPopupType() == 3) {
            return;
        }
        if (isShowing()) {
            hide();
        }
        this.mMessageView.lambda$setTextAsync$0(charSequence);
        computePosition(view, i, i2, z, this.mLayoutParams);
        if (CoreRune.MW_CAPTION_TOOLTIP && (this.mIsCaptionMenuButton || this.mIsCaptionPopupButton)) {
            this.mLayoutParams.multiWindowFlags |= 8;
            if (this.mIsCaptionPopupButton) {
                this.mLayoutParams.multiWindowFlags |= 2;
            }
        }
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).addView(this.mContentView, this.mLayoutParams);
    }

    public void hide() {
        View view = this.mContentView;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).removeView(this.mContentView);
        }
    }

    public View getContentView() {
        return this.mContentView;
    }

    public boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void computePosition(android.view.View r19, int r20, int r21, boolean r22, android.view.WindowManager.LayoutParams r23) {
        /*
            Method dump skipped, instructions count: 491
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.TooltipPopup.computePosition(android.view.View, int, int, boolean, android.view.WindowManager$LayoutParams):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void semUpdateMaxWidth() {
        /*
            r4 = this;
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            android.content.Context r1 = r4.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 17106046(0x105047e, float:2.4431465E-38)
            r3 = 1
            r1.getValue(r2, r0, r3)
            android.content.Context r1 = r4.mContext
            android.content.res.Resources r1 = r1.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            int r2 = r0.type
            r3 = 5
            if (r2 != r3) goto L27
            float r0 = r0.getDimension(r1)
        L25:
            int r0 = (int) r0
            goto L38
        L27:
            int r2 = r0.type
            r3 = 6
            if (r2 != r3) goto L37
            int r2 = r1.widthPixels
            float r2 = (float) r2
            int r1 = r1.widthPixels
            float r1 = (float) r1
            float r0 = r0.getFraction(r2, r1)
            goto L25
        L37:
            r0 = 0
        L38:
            android.view.View r1 = r4.mContentView
            android.graphics.drawable.Drawable r1 = r1.getBackground()
            if (r1 == 0) goto L4e
            android.view.View r1 = r4.mContentView
            int r1 = r1.getPaddingLeft()
            android.view.View r2 = r4.mContentView
            int r2 = r2.getPaddingRight()
            int r1 = r1 + r2
            int r0 = r0 - r1
        L4e:
            android.widget.TextView r4 = r4.mMessageView
            r4.setMaxWidth(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.view.TooltipPopup.semUpdateMaxWidth():void");
    }

    private void semComputePositionForMultiWindow(View view, Rect rect, boolean z, int i, int i2, WindowManager.LayoutParams layoutParams) {
        View view2;
        int i3;
        int i4;
        int i5;
        ViewRootImpl viewRootImpl;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(makeMeasureSpec, makeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        layoutParams.layoutInDisplayCutoutMode = 2;
        int measuredWidth = this.mContentView.getMeasuredWidth();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_hover_tooltip_popup_left_margin);
        this.mContext.getApplicationContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        int i6 = displayMetrics.widthPixels;
        int i7 = displayMetrics.heightPixels;
        int i8 = i6 - dimensionPixelOffset;
        if (measuredWidth > i8) {
            measuredWidth = i8;
        }
        int width = this.mTmpDisplayFrame.width() - dimensionPixelOffset;
        if (isSplitWindow() && measuredWidth > width && width >= this.mContext.getResources().getDimensionPixelOffset(R.dimen.default_minimal_size_resizable_task)) {
            measuredWidth = width;
        }
        if (isFreeForm() && !isEmbedded()) {
            Log.i(TAG, "Add Flag FLAG_LAYOUT_NO_LIMITS for free form mode");
            layoutParams.flags |= 512;
        } else {
            layoutParams.flags &= -513;
        }
        if (CoreRune.MW_CAPTION_FREEFORM) {
            view2 = view;
            i3 = getYAbove(view2, measuredHeight);
        } else {
            view2 = view;
            i3 = this.mTmpAnchorPos[1] - measuredHeight;
        }
        int height = this.mTmpAnchorPos[1] + view2.getHeight();
        if (z) {
            if (view2.getLayoutDirection() == 0) {
                int i9 = this.mTmpAnchorPos[0];
                i4 = ((i9 + i) - measuredWidth) + dimensionPixelOffset;
                i5 = i4 + measuredWidth;
                layoutParams.x = ((i9 + i) - ((rect.width() + measuredWidth) / 2)) + dimensionPixelOffset;
            } else {
                int i10 = this.mTmpAnchorPos[0];
                i4 = (i10 + i) - dimensionPixelOffset;
                i5 = i4 + measuredWidth;
                layoutParams.x = ((i10 + i) - ((rect.width() - measuredWidth) / 2)) - dimensionPixelOffset;
            }
            if (height + measuredHeight > this.mTmpDisplayFrame.height() && !this.mIsCaptionMenuButton && !this.mIsCaptionPopupButton) {
                layoutParams.y = i3;
            } else {
                layoutParams.y = height;
            }
        } else {
            layoutParams.x = (this.mTmpAnchorPos[0] + i) - (rect.width() / 2);
            i4 = (this.mTmpAnchorPos[0] + i) - (measuredWidth / 2);
            i5 = i4 + measuredWidth;
            if (i3 >= 0) {
                layoutParams.y = i3;
            } else if (height + measuredHeight <= this.mTmpDisplayFrame.height()) {
                layoutParams.y = height;
            } else {
                layoutParams.flags |= 512;
                if (this.mTmpDisplayFrame.top + i3 >= 0) {
                    layoutParams.y = i3;
                } else {
                    layoutParams.y = height;
                }
            }
        }
        int i11 = i5;
        int i12 = i4 + rect.left;
        int i13 = (rect.left + i11) - i6;
        int i14 = (layoutParams.y + rect.top) - i7;
        int rotation = windowManager.getDefaultDisplay().getRotation();
        if (rotation == 3) {
            if (i2 != 0) {
                int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.navigation_bar_height_landscape);
                i12 -= dimensionPixelOffset2;
                i13 -= dimensionPixelOffset2;
            }
        } else if (rotation == 0) {
            i14 += this.mContext.getResources().getDimensionPixelOffset(R.dimen.navigation_bar_height);
        }
        if (i12 < 0) {
            layoutParams.x -= i12;
        } else if (i13 > 0) {
            layoutParams.x -= i13;
        } else if (i4 < 0) {
            layoutParams.x -= i4;
        } else if (i11 > rect.width()) {
            layoutParams.x -= i11 - rect.width();
        }
        if (i14 > 0) {
            layoutParams.y = i3;
        }
        if (CoreRune.MW_CAPTION_TOOLTIP && this.mIsCaptionPopupButton && view2.getRootView() != null && (viewRootImpl = view2.getRootView().getViewRootImpl()) != null) {
            layoutParams.y += viewRootImpl.mWindowAttributes.surfaceInsets.top;
            layoutParams.x += viewRootImpl.mWindowAttributes.surfaceInsets.left;
        }
        if (this.mTmpAppPos[1] + layoutParams.y + measuredHeight > i7) {
            layoutParams.y = i3;
        }
        if (this.mTmpDisplayFrame.width() < measuredWidth || rect.height() < measuredHeight || i13 > 0) {
            if (i3 >= windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.statusBars()).top) {
                layoutParams.flags |= 512;
            }
            Log.i(TAG, "Add Flag FLAG_LAYOUT_NO_LIMITS for small window");
        }
        if (rect.height() < measuredHeight) {
            Log.i(TAG, "Add Flag LAYOUT_CHILD_WINDOW_IN_PARENT_FRAME for small height window");
            layoutParams.privateFlags |= 16384;
        } else {
            layoutParams.privateFlags &= -16385;
        }
    }

    private boolean isFreeForm() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5;
    }

    private boolean isFullScreen() {
        WindowConfiguration windowConfiguration = this.mContext.getResources().getConfiguration().windowConfiguration;
        return windowConfiguration.getActivityType() == 1 && windowConfiguration.getWindowingMode() == 1;
    }

    private boolean isSplitWindow() {
        return WindowConfiguration.isSplitScreenWindowingMode(this.mContext.getResources().getConfiguration().windowConfiguration);
    }

    private int getYAbove(View view, int i) {
        int i2;
        if (view != null && isFreeForm()) {
            int i3 = this.mTmpAnchorPos[1] - i;
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            return (rootWindowInsets == null || i3 > (i2 = rootWindowInsets.getInsets(WindowInsets.Type.captionBar()).top)) ? i3 : i3 - i2;
        }
        return this.mTmpAnchorPos[1] - i;
    }

    private boolean isEmbedded() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.isEmbedded();
    }

    public void semShowActionItemTooltip(int i, int i2, int i3, CharSequence charSequence) {
        if (isShowing()) {
            hide();
        }
        this.mMessageView.lambda$setTextAsync$0(charSequence);
        this.mLayoutParams.x = i;
        this.mLayoutParams.y = i2;
        if (i3 == 0) {
            this.mLayoutParams.gravity = 8388661;
        } else {
            this.mLayoutParams.gravity = 8388659;
        }
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).addView(this.mContentView, this.mLayoutParams);
    }

    public void setForCaptionMenuButton() {
        this.mIsCaptionMenuButton = true;
    }

    public void setForCaptionPopupButton() {
        this.mIsCaptionPopupButton = true;
    }
}
