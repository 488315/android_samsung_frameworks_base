package android.widget;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.flags.Flags;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class Button extends TextView {
    private static final long WEAR_MATERIAL3_BUTTON = 376561342;
    private static Boolean sUseWearMaterial3Style;
    private boolean mIsThemeDeviceDefault;

    public Button(Context context) {
        this(context, null);
    }

    public Button(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, getButtonDefaultStyleAttr(context), getButtonDefaultStyleRes());
    }

    public Button(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Button(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsThemeDeviceDefault = false;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z = typedValue.data != 0;
        this.mIsThemeDeviceDefault = z;
        if (!z || getHoverUIFeatureLevel() < 2) {
            return;
        }
        semSetHoverPopupType(1);
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    @Override // android.widget.TextView, android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (!this.mIsThemeDeviceDefault && !Flags.enableArrowIconOnHoverWhenClickable() && getPointerIcon() == null && isClickable() && isEnabled() && motionEvent.isFromSource(8194)) {
            return PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private static int getButtonDefaultStyleAttr(Context context) {
        Boolean boolValueOf = Boolean.valueOf(useWearMaterial3Style(context));
        sUseWearMaterial3Style = boolValueOf;
        return boolValueOf.booleanValue() ? 0 : 16842824;
    }

    private static int getButtonDefaultStyleRes() {
        Boolean bool = sUseWearMaterial3Style;
        if (bool == null || !bool.booleanValue()) {
            return 0;
        }
        return R.style.Widget_Material3_Button;
    }

    private static boolean useWearMaterial3Style(Context context) {
        return android.widget.flags.Flags.useWearMaterial3Ui() && CompatChanges.isChangeEnabled(WEAR_MATERIAL3_BUTTON) && context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH) && context.getThemeResId() == 16974120;
    }
}
