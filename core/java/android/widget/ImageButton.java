package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.flags.Flags;

import com.android.internal.R;

@RemoteViews.RemoteView
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
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, true);
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
        int pointerIcon;
        if (!this.mIsThemeDeviceDefault
                && getPointerIcon() == null
                && isClickable()
                && isEnabled()
                && event.isFromSource(8194)) {
            if (Flags.enableArrowIconOnHoverWhenClickable()) {
                pointerIcon = 1000;
            } else {
                pointerIcon = 1002;
            }
            return PointerIcon.getSystemIcon(getContext(), pointerIcon);
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }
}
