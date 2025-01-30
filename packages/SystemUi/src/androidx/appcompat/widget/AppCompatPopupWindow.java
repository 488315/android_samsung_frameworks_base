package androidx.appcompat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.PopupWindow;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.p001os.SeslBuildReflector$SeslVersionReflector;
import androidx.reflect.view.SeslSemWindowManagerReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.view.SeslViewRuneReflector;
import com.android.systemui.R;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatPopupWindow extends PopupWindow {
    public static final boolean ONEUI_5_1_1;
    public static final int[] ONEUI_BLUR_POPUP_BACKGROUND_RES;
    public Context mContext;
    public boolean mHasNavigationBar;
    public boolean mIsReplacedPoupBackground;
    public int mNavigationBarHeight;
    public final Rect mTempRect;

    static {
        ONEUI_5_1_1 = SeslBuildReflector$SeslVersionReflector.getField_SEM_PLATFORM_INT() >= 140500;
        ONEUI_BLUR_POPUP_BACKGROUND_RES = new int[]{R.drawable.sesl_menu_popup_background, R.drawable.sesl_menu_popup_background_dark};
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        init(context, attributeSet, i, 0);
    }

    @Override // android.widget.PopupWindow
    public final int getMaxAvailableHeight(View view, int i, boolean z) {
        Context context;
        DisplayManager displayManager;
        Display display;
        Activity activity;
        int i2;
        Rect rect = new Rect();
        if (z) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslViewReflector.mClass, "getWindowDisplayFrame", Rect.class);
            if (declaredMethod != null) {
                SeslBaseReflector.invoke(view, declaredMethod, rect);
            }
            if (this.mHasNavigationBar && this.mContext.getResources().getConfiguration().orientation != 2) {
                rect.bottom -= this.mNavigationBarHeight;
            }
        } else {
            view.getWindowVisibleDisplayFrame(rect);
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i3 = 0;
        if (ONEUI_5_1_1 && (context = this.mContext) != null && (displayManager = (DisplayManager) context.getSystemService("display")) != null && (display = displayManager.getDisplay(0)) != null && SeslSemWindowManagerReflector.isTableMode()) {
            Context context2 = this.mContext;
            while (true) {
                if (!(context2 instanceof ContextWrapper)) {
                    activity = null;
                    break;
                }
                if (context2 instanceof Activity) {
                    activity = (Activity) context2;
                    break;
                }
                context2 = ((ContextWrapper) context2).getBaseContext();
            }
            if (activity == null || !activity.isInMultiWindowMode()) {
                Point point = new Point();
                display.getRealSize(point);
                if (SeslViewRuneReflector.supportFoldableDualDisplay()) {
                    if (this.mContext.getResources().getConfiguration().orientation == 2) {
                        int i4 = point.y;
                        int i5 = point.x;
                        if (i4 > i5) {
                            i3 = i5 / 2;
                        } else {
                            i2 = i4 / 2;
                            i3 = i2;
                        }
                    }
                } else if (SeslViewRuneReflector.supportFoldableNoSubDisplay() && this.mContext.getResources().getConfiguration().orientation == 1) {
                    int i6 = point.y;
                    int i7 = point.x;
                    if (i6 > i7) {
                        i2 = i6 / 2;
                        i3 = i2;
                    } else {
                        i3 = i7 / 2;
                    }
                }
            }
        }
        int height = (((i3 == 0 || iArr[1] >= i3) ? rect.bottom : i3) - (getOverlapAnchor() ? iArr[1] : view.getHeight() + iArr[1])) - i;
        int i8 = iArr[1];
        if (i3 == 0 || i8 < i3) {
            i3 = rect.top;
        }
        int max = Math.max(height, (i8 - i3) + i);
        if (getBackground() == null) {
            return max;
        }
        getBackground().getPadding(this.mTempRect);
        Rect rect2 = this.mTempRect;
        return max - (rect2.top + rect2.bottom);
    }

    public final Transition getTransition(int i) {
        Transition inflateTransition;
        if (i == 0 || i == 17760256 || (inflateTransition = TransitionInflater.from(this.mContext).inflateTransition(i)) == null) {
            return null;
        }
        if ((inflateTransition instanceof TransitionSet) && ((TransitionSet) inflateTransition).getTransitionCount() == 0) {
            return null;
        }
        return inflateTransition;
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R$styleable.PopupWindow, i, i2);
        boolean z = false;
        if (obtainStyledAttributes.hasValue(2)) {
            setOverlapAnchor(obtainStyledAttributes.getBoolean(2, false));
        }
        this.mContext = context;
        Transition transition = getTransition(obtainStyledAttributes.getResourceId(3, 0));
        Transition transition2 = getTransition(obtainStyledAttributes.getResourceId(4, 0));
        setEnterTransition(transition);
        setExitTransition(transition2);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        boolean z2 = false;
        for (int i3 : ONEUI_BLUR_POPUP_BACKGROUND_RES) {
            if (i3 == resourceId) {
                z2 = true;
            }
        }
        setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
        this.mIsReplacedPoupBackground = !z2;
        obtainStyledAttributes.recycle();
        if (!ViewConfiguration.get(ActionBarPolicy.get(context).mContext).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(4)) {
            z = true;
        }
        this.mHasNavigationBar = z;
        this.mNavigationBarHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_height);
    }

    @Override // android.widget.PopupWindow
    public final void setBackgroundDrawable(Drawable drawable) {
        this.mIsReplacedPoupBackground = true;
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.widget.PopupWindow
    public final void showAsDropDown(View view, int i, int i2) {
        super.showAsDropDown(view, i, i2);
    }

    @Override // android.widget.PopupWindow
    public final void update(View view, int i, int i2, int i3, int i4) {
        super.update(view, i, i2, i3, i4);
    }

    @Override // android.widget.PopupWindow
    public final void showAsDropDown(View view, int i, int i2, int i3) {
        super.showAsDropDown(view, i, i2, i3);
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        init(context, attributeSet, i, i2);
    }
}
