package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.widget.RemoteViews;
import com.android.internal.C4337R;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class ImageButton extends ImageView {
    private boolean mIsThemeDeviceDefault;

    public ImageButton(Context context) {
        this(context, null);
    }

    public ImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 16842866);
    }

    public ImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIsThemeDeviceDefault = false;
        setFocusable(true);
        if (getHoverUIFeatureLevel() >= 2) {
            semSetHoverPopupType(1);
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
        this.mIsThemeDeviceDefault = outValue.data != 0;
    }

    @Override // android.view.View
    protected boolean onSetAlpha(int alpha) {
        return false;
    }

    @Override // android.widget.ImageView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ImageButton.class.getName();
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (!this.mIsThemeDeviceDefault && getPointerIcon() == null && isClickable() && isEnabled()) {
            return PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }
}
