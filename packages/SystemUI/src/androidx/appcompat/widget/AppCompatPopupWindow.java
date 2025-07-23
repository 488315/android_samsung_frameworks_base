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
import androidx.reflect.os.SeslBuildReflector$SeslVersionReflector;
import androidx.reflect.view.SeslSemWindowManagerReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.view.SeslViewRuneReflector;
import com.android.systemui.R;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatPopupWindow extends PopupWindow {
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
        Rect rect = new Rect();
        int i2 = 0;
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
                        int i3 = point.y;
                        int i4 = point.x;
                        i2 = i3 > i4 ? i4 / 2 : i3 / 2;
                    }
                } else if (SeslViewRuneReflector.supportFoldableNoSubDisplay() && this.mContext.getResources().getConfiguration().orientation == 1) {
                    int i5 = point.y;
                    int i6 = point.x;
                    i2 = i5 > i6 ? i5 / 2 : i6 / 2;
                }
            }
        }
        int height = (((i2 == 0 || iArr[1] >= i2) ? rect.bottom : i2) - (getOverlapAnchor() ? iArr[1] : view.getHeight() + iArr[1])) - i;
        int i7 = iArr[1];
        if (i2 == 0 || i7 < i2) {
            i2 = rect.top;
        }
        int max = Math.max(height, (i7 - i2) + i);
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
        if (obtainStyledAttributes.mWrapped.hasValue(2)) {
            setOverlapAnchor(obtainStyledAttributes.mWrapped.getBoolean(2, false));
        }
        this.mContext = context;
        Transition transition = getTransition(obtainStyledAttributes.mWrapped.getResourceId(3, 0));
        Transition transition2 = getTransition(obtainStyledAttributes.mWrapped.getResourceId(4, 0));
        setEnterTransition(transition);
        setExitTransition(transition2);
        int resourceId = obtainStyledAttributes.mWrapped.getResourceId(0, -1);
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

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        init(context, attributeSet, i, i2);
    }
}
