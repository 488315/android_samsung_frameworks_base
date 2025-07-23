package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.flags.Flags;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ImageButton extends ImageView {
    private boolean mIsThemeDeviceDefault;

    @Override // android.view.View
    protected boolean onSetAlpha(int i) {
        return false;
    }

    public ImageButton(Context context) {
        this(context, null);
    }

    public ImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842866);
    }

    public ImageButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsThemeDeviceDefault = false;
        setFocusable(true);
        if (getHoverUIFeatureLevel() >= 2) {
            semSetHoverPopupType(1);
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsThemeDeviceDefault = typedValue.data != 0;
    }

    @Override // android.widget.ImageView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ImageButton.class.getName();
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (!this.mIsThemeDeviceDefault && getPointerIcon() == null && isClickable() && isEnabled() && motionEvent.isFromSource(8194)) {
            return PointerIcon.getSystemIcon(getContext(), Flags.enableArrowIconOnHoverWhenClickable() ? 1000 : 1002);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }
}
