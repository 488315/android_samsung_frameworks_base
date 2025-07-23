package android.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.R;
import com.android.internal.app.MediaRouteDialogPresenter;

/* loaded from: classes.dex */
public class MediaRouteButton extends View {
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private View.OnClickListener mExtendedSettingsClickListener;
    private boolean mIsConnecting;
    private int mMinHeight;
    private int mMinWidth;
    private boolean mRemoteActive;
    private Drawable mRemoteIndicator;
    private int mRouteTypes;
    private final MediaRouter mRouter;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int[] ACTIVATED_STATE_SET = {16843518};

    public MediaRouteButton(Context context) {
        this(context, null);
    }

    public MediaRouteButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843693);
    }

    public MediaRouteButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MediaRouteButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRouter = (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
        this.mCallback = new MediaRouterCallback();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MediaRouteButton, i, i2);
        setRemoteIndicatorDrawable(obtainStyledAttributes.getDrawable(3));
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        int integer = obtainStyledAttributes.getInteger(2, 1);
        obtainStyledAttributes.recycle();
        setClickable(true);
        setRouteTypes(integer);
    }

    public int getRouteTypes() {
        return this.mRouteTypes;
    }

    public void setRouteTypes(int i) {
        int i2 = this.mRouteTypes;
        if (i2 != i) {
            if (this.mAttachedToWindow && i2 != 0) {
                this.mRouter.removeCallback(this.mCallback);
            }
            this.mRouteTypes = i;
            if (this.mAttachedToWindow && i != 0) {
                this.mRouter.addCallback(i, this.mCallback, 8);
            }
            refreshRoute();
        }
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        this.mExtendedSettingsClickListener = onClickListener;
    }

    public void showDialog() {
        showDialogInternal();
    }

    boolean showDialogInternal() {
        return this.mAttachedToWindow && MediaRouteDialogPresenter.showDialogFragment(getActivity(), this.mRouteTypes, this.mExtendedSettingsClickListener) != null;
    }

    private Activity getActivity() {
        for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        throw new IllegalStateException("The MediaRouteButton's Context is not an Activity.");
    }

    @Override // android.view.View
    public void setContentDescription(CharSequence charSequence) {
        super.setContentDescription(charSequence);
        setTooltipText(charSequence);
    }

    @Override // android.view.View
    public boolean performClick() {
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        return showDialogInternal() || performClick;
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.mIsConnecting) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
            return onCreateDrawableState;
        }
        if (this.mRemoteActive) {
            mergeDrawableStates(onCreateDrawableState, ACTIVATED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    private void setRemoteIndicatorDrawable(Drawable drawable) {
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mRemoteIndicator);
        }
        this.mRemoteIndicator = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setState(getDrawableState());
            drawable.setVisible(getVisibility() == 0, false);
        }
        refreshDrawableState();
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mRemoteIndicator;
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null) {
            drawable.setVisible(getVisibility() == 0, false);
        }
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        int i = this.mRouteTypes;
        if (i != 0) {
            this.mRouter.addCallback(i, this.mCallback, 8);
        }
        refreshRoute();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        if (this.mRouteTypes != 0) {
            this.mRouter.removeCallback(this.mCallback);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i3 = this.mMinWidth;
        Drawable drawable = this.mRemoteIndicator;
        int max = Math.max(i3, drawable != null ? drawable.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight() : 0);
        int i4 = this.mMinHeight;
        Drawable drawable2 = this.mRemoteIndicator;
        int max2 = Math.max(i4, drawable2 != null ? drawable2.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom() : 0);
        if (mode == Integer.MIN_VALUE) {
            size = Math.min(size, max);
        } else if (mode != 1073741824) {
            size = max;
        }
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(size2, max2);
        } else if (mode2 != 1073741824) {
            size2 = max2;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRemoteIndicator == null) {
            return;
        }
        int paddingLeft = getPaddingLeft();
        int width = getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        int intrinsicWidth = this.mRemoteIndicator.getIntrinsicWidth();
        int intrinsicHeight = this.mRemoteIndicator.getIntrinsicHeight();
        int i = paddingLeft + (((width - paddingLeft) - intrinsicWidth) / 2);
        int i2 = paddingTop + (((height - paddingTop) - intrinsicHeight) / 2);
        this.mRemoteIndicator.setBounds(i, i2, intrinsicWidth + i, intrinsicHeight + i2);
        this.mRemoteIndicator.draw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshRoute() {
        MediaRouter.RouteInfo selectedRoute = this.mRouter.getSelectedRoute();
        boolean z = false;
        boolean z2 = !selectedRoute.isDefault() && selectedRoute.matchesTypes(this.mRouteTypes);
        boolean z3 = z2 && selectedRoute.isConnecting();
        if (this.mRemoteActive != z2) {
            this.mRemoteActive = z2;
            z = true;
        }
        if (this.mIsConnecting != z3) {
            this.mIsConnecting = z3;
            z = true;
        }
        if (z) {
            refreshDrawableState();
        }
        if (this.mAttachedToWindow) {
            setEnabled(this.mRouter.isRouteAvailable(this.mRouteTypes, 1));
        }
        Drawable drawable = this.mRemoteIndicator;
        if (drawable == null || !(drawable.getCurrent() instanceof AnimationDrawable)) {
            return;
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mRemoteIndicator.getCurrent();
        if (this.mAttachedToWindow) {
            if ((z || z3) && !animationDrawable.isRunning()) {
                animationDrawable.start();
                return;
            }
            return;
        }
        if (!z2 || z3) {
            return;
        }
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        animationDrawable.selectDrawable(animationDrawable.getNumberOfFrames() - 1);
    }

    private final class MediaRouterCallback extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteGrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup, int i) {
            MediaRouteButton.this.refreshRoute();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteUngrouped(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo, MediaRouter.RouteGroup routeGroup) {
            MediaRouteButton.this.refreshRoute();
        }
    }
}
