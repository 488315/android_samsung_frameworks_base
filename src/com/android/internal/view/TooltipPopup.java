package com.android.internal.view;

import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
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
        View viewInflate;
        this.mContentView = null;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        this.mTmpDisplayFrame = new Rect();
        this.mTmpAnchorPos = new int[2];
        this.mTmpAppPos = new int[2];
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.internal.view.TooltipPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
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
            viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.sem_tooltip, (ViewGroup) null);
        } else {
            viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.tooltip, (ViewGroup) null);
        }
        this.mContentView = viewInflate;
        this.mMessageView = (TextView) viewInflate.findViewById(16908299);
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void computePosition(View view, int i, int i2, boolean z, WindowManager.LayoutParams layoutParams) throws Resources.NotFoundException {
        int i3;
        int height;
        int i4;
        View windowView;
        WindowInsets rootWindowInsets;
        int systemWindowInsetLeft;
        layoutParams.token = view.getApplicationWindowToken();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold);
        if (this.mIsDeviceDefault || view.getWidth() < dimensionPixelOffset) {
            int width = view.getWidth() / 2;
            i3 = width;
            if (view.getHeight() < dimensionPixelOffset) {
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(!z ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
            windowView = WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
            if (windowView == null) {
            }
            windowView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
            rootWindowInsets = windowView.getRootWindowInsets();
            if (rootWindowInsets == null) {
            }
            int[] iArr = new int[2];
            windowView.getLocationOnScreen(iArr);
            int i5 = height;
            int i6 = iArr[0];
            Rect rect = new Rect(i6, iArr[1], i6 + windowView.getWidth(), iArr[1] + windowView.getHeight());
            this.mTmpDisplayFrame.left = rect.left + systemWindowInsetLeft;
            this.mTmpDisplayFrame.right = rect.right;
            windowView.getLocationOnScreen(this.mTmpAppPos);
            view.getLocationOnScreen(this.mTmpAnchorPos);
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
            }
            int[] iArr2 = this.mTmpAnchorPos;
            int i7 = iArr2[0];
            int[] iArr3 = this.mTmpAppPos;
            int i8 = i7 - iArr3[0];
            iArr2[0] = i8;
            iArr2[1] = iArr2[1] - iArr3[1];
            if (!this.mIsDeviceDefault) {
            }
        } else {
            i3 = i;
            if (view.getHeight() < dimensionPixelOffset) {
                int dimensionPixelOffset3 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset);
                height = i2 + dimensionPixelOffset3;
                i4 = i2 - dimensionPixelOffset3;
            } else {
                height = view.getHeight();
                i4 = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset22 = this.mContext.getResources().getDimensionPixelOffset(!z ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
            windowView = WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
            if (windowView == null) {
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl == null) {
                    Slog.e(TAG, "Cannot find app view");
                    return;
                }
                windowView = viewRootImpl.getView();
            }
            windowView.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
            rootWindowInsets = windowView.getRootWindowInsets();
            if (rootWindowInsets == null) {
                systemWindowInsetLeft = rootWindowInsets.getSystemWindowInsetLeft();
                Log.i(TAG, "left inset = " + systemWindowInsetLeft);
            } else {
                systemWindowInsetLeft = 0;
            }
            int[] iArr4 = new int[2];
            windowView.getLocationOnScreen(iArr4);
            int i52 = height;
            int i62 = iArr4[0];
            Rect rect2 = new Rect(i62, iArr4[1], i62 + windowView.getWidth(), iArr4[1] + windowView.getHeight());
            this.mTmpDisplayFrame.left = rect2.left + systemWindowInsetLeft;
            this.mTmpDisplayFrame.right = rect2.right;
            windowView.getLocationOnScreen(this.mTmpAppPos);
            view.getLocationOnScreen(this.mTmpAnchorPos);
            if (ViewRune.COMMON_IS_PRODUCT_DEV) {
                Log.i(TAG, "computePosition - displayFrame left : " + this.mTmpDisplayFrame.left);
                Log.i(TAG, "computePosition - displayFrame right : " + this.mTmpDisplayFrame.right);
                Log.i(TAG, "computePosition - displayFrame top : " + this.mTmpDisplayFrame.top);
                Log.i(TAG, "computePosition - displayFrame bottom : " + this.mTmpDisplayFrame.bottom);
                Log.i(TAG, "computePosition - anchorView locationOnScreen x : " + this.mTmpAnchorPos[0]);
                Log.i(TAG, "computePosition - anchorView locationOnScreen y : " + this.mTmpAnchorPos[1]);
                Log.i(TAG, "computePosition - appView locationOnScreen x : " + this.mTmpAppPos[0]);
                Log.i(TAG, "computePosition - appView locationOnScreen y : " + this.mTmpAppPos[1]);
            }
            int[] iArr22 = this.mTmpAnchorPos;
            int i72 = iArr22[0];
            int[] iArr32 = this.mTmpAppPos;
            int i82 = i72 - iArr32[0];
            iArr22[0] = i82;
            iArr22[1] = iArr22[1] - iArr32[1];
            if (!this.mIsDeviceDefault) {
                semUpdateMaxWidth();
                semComputePositionForMultiWindow(view, rect2, z, i3, systemWindowInsetLeft, layoutParams);
                return;
            }
            layoutParams.x = (i82 + i3) - (rect2.width() / 2);
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            int measuredHeight = this.mContentView.getMeasuredHeight();
            int i9 = this.mTmpAnchorPos[1];
            int i10 = ((i4 + i9) - dimensionPixelOffset22) - measuredHeight;
            int i11 = i9 + i52 + dimensionPixelOffset22;
            if (z) {
                if (i10 >= 0) {
                    layoutParams.y = i10;
                    return;
                } else {
                    layoutParams.y = i11;
                    return;
                }
            }
            if (measuredHeight + i11 <= this.mTmpDisplayFrame.height()) {
                layoutParams.y = i11;
            } else {
                layoutParams.y = i10;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void semUpdateMaxWidth() throws Resources.NotFoundException {
        int paddingLeft;
        float fraction;
        TypedValue typedValue = new TypedValue();
        this.mContext.getResources().getValue(R.dimen.sem_config_prefDialogWidth, typedValue, true);
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        if (typedValue.type == 5) {
            fraction = typedValue.getDimension(displayMetrics);
        } else if (typedValue.type == 6) {
            fraction = typedValue.getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels);
        } else {
            paddingLeft = 0;
            if (this.mContentView.getBackground() != null) {
                paddingLeft -= this.mContentView.getPaddingLeft() + this.mContentView.getPaddingRight();
            }
            this.mMessageView.setMaxWidth(paddingLeft);
        }
        paddingLeft = (int) fraction;
        if (this.mContentView.getBackground() != null) {
        }
        this.mMessageView.setMaxWidth(paddingLeft);
    }

    private void semComputePositionForMultiWindow(View view, Rect rect, boolean z, int i, int i2, WindowManager.LayoutParams layoutParams) throws Resources.NotFoundException {
        View view2;
        int yAbove;
        int i3;
        int i4;
        ViewRootImpl viewRootImpl;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        layoutParams.layoutInDisplayCutoutMode = 2;
        int measuredWidth = this.mContentView.getMeasuredWidth();
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_hover_tooltip_popup_left_margin);
        this.mContext.getApplicationContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        int i5 = displayMetrics.widthPixels;
        int i6 = displayMetrics.heightPixels;
        int i7 = i5 - dimensionPixelOffset;
        if (measuredWidth > i7) {
            measuredWidth = i7;
        }
        int iWidth = this.mTmpDisplayFrame.width() - dimensionPixelOffset;
        if (isSplitWindow() && measuredWidth > iWidth && iWidth >= this.mContext.getResources().getDimensionPixelOffset(R.dimen.default_minimal_size_resizable_task)) {
            measuredWidth = iWidth;
        }
        if (isFreeForm() && !isEmbedded()) {
            Log.i(TAG, "Add Flag FLAG_LAYOUT_NO_LIMITS for free form mode");
            layoutParams.flags |= 512;
        } else {
            layoutParams.flags &= -513;
        }
        if (CoreRune.MW_CAPTION_FREEFORM) {
            view2 = view;
            yAbove = getYAbove(view2, measuredHeight);
        } else {
            view2 = view;
            yAbove = this.mTmpAnchorPos[1] - measuredHeight;
        }
        int height = this.mTmpAnchorPos[1] + view2.getHeight();
        if (z) {
            if (view2.getLayoutDirection() == 0) {
                int i8 = this.mTmpAnchorPos[0];
                i3 = ((i8 + i) - measuredWidth) + dimensionPixelOffset;
                i4 = i3 + measuredWidth;
                layoutParams.x = ((i8 + i) - ((rect.width() + measuredWidth) / 2)) + dimensionPixelOffset;
            } else {
                int i9 = this.mTmpAnchorPos[0];
                i3 = (i9 + i) - dimensionPixelOffset;
                i4 = i3 + measuredWidth;
                layoutParams.x = ((i9 + i) - ((rect.width() - measuredWidth) / 2)) - dimensionPixelOffset;
            }
            if (height + measuredHeight > this.mTmpDisplayFrame.height() && !this.mIsCaptionMenuButton && !this.mIsCaptionPopupButton) {
                layoutParams.y = yAbove;
            } else {
                layoutParams.y = height;
            }
        } else {
            layoutParams.x = (this.mTmpAnchorPos[0] + i) - (rect.width() / 2);
            i3 = (this.mTmpAnchorPos[0] + i) - (measuredWidth / 2);
            i4 = i3 + measuredWidth;
            if (yAbove >= 0) {
                layoutParams.y = yAbove;
            } else if (height + measuredHeight <= this.mTmpDisplayFrame.height()) {
                layoutParams.y = height;
            } else {
                layoutParams.flags |= 512;
                if (this.mTmpDisplayFrame.top + yAbove >= 0) {
                    layoutParams.y = yAbove;
                } else {
                    layoutParams.y = height;
                }
            }
        }
        int i10 = i4;
        int i11 = i3 + rect.left;
        int i12 = (rect.left + i10) - i5;
        int dimensionPixelOffset2 = (layoutParams.y + rect.top) - i6;
        int rotation = windowManager.getDefaultDisplay().getRotation();
        if (rotation == 3) {
            if (i2 != 0) {
                int dimensionPixelOffset3 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.navigation_bar_height_landscape);
                i11 -= dimensionPixelOffset3;
                i12 -= dimensionPixelOffset3;
            }
        } else if (rotation == 0) {
            dimensionPixelOffset2 += this.mContext.getResources().getDimensionPixelOffset(R.dimen.navigation_bar_height);
        }
        if (i11 < 0) {
            layoutParams.x -= i11;
        } else if (i12 > 0) {
            layoutParams.x -= i12;
        } else if (i3 < 0) {
            layoutParams.x -= i3;
        } else if (i10 > rect.width()) {
            layoutParams.x -= i10 - rect.width();
        }
        if (dimensionPixelOffset2 > 0) {
            layoutParams.y = yAbove;
        }
        if (CoreRune.MW_CAPTION_TOOLTIP && this.mIsCaptionPopupButton && view2.getRootView() != null && (viewRootImpl = view2.getRootView().getViewRootImpl()) != null) {
            layoutParams.y += viewRootImpl.mWindowAttributes.surfaceInsets.top;
            layoutParams.x += viewRootImpl.mWindowAttributes.surfaceInsets.left;
        }
        if (this.mTmpAppPos[1] + layoutParams.y + measuredHeight > i6) {
            layoutParams.y = yAbove;
        }
        if (this.mTmpDisplayFrame.width() < measuredWidth || rect.height() < measuredHeight || i12 > 0) {
            if (yAbove >= windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.statusBars()).top) {
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
