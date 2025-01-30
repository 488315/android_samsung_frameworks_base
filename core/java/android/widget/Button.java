package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import com.android.internal.C4337R;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class Button extends TextView {
  private boolean mIsThemeDeviceDefault;

  public Button(Context context) {
    this(context, null);
  }

  public Button(Context context, AttributeSet attrs) {
    this(context, attrs, 16842824);
  }

  public Button(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public Button(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    this.mIsThemeDeviceDefault = false;
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
    boolean z = outValue.data != 0;
    this.mIsThemeDeviceDefault = z;
    if (z && getHoverUIFeatureLevel() >= 2) {
      semSetHoverPopupType(1);
    }
  }

  @Override // android.widget.TextView, android.view.View
  public CharSequence getAccessibilityClassName() {
    return Button.class.getName();
  }

  @Override // android.widget.TextView, android.view.View
  public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
    if (!this.mIsThemeDeviceDefault && getPointerIcon() == null && isClickable() && isEnabled()) {
      return PointerIcon.getSystemIcon(getContext(), 1002);
    }
    return super.onResolvePointerIcon(event, pointerIndex);
  }
}
