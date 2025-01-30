package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouterThemeHelper {
    private MediaRouterThemeHelper() {
    }

    public static Context createThemedDialogContext(Context context, int i, boolean z) {
        if (i == 0) {
            i = getThemeResource(!z ? R.attr.dialogTheme : R.attr.alertDialogTheme, context);
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        return getThemeResource(R.attr.mediaRouteTheme, contextThemeWrapper) != 0 ? new ContextThemeWrapper(contextThemeWrapper, getRouterThemeId(contextThemeWrapper)) : contextThemeWrapper;
    }

    public static int createThemedDialogStyle(Context context) {
        int themeResource = getThemeResource(R.attr.mediaRouteTheme, context);
        return themeResource == 0 ? getRouterThemeId(context) : themeResource;
    }

    public static int getControllerColor(int i, Context context) {
        return ColorUtils.calculateContrast(-1, getThemeColor(i, context, R.attr.colorPrimary)) >= 3.0d ? -1 : -570425344;
    }

    public static float getDisabledAlpha(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.disabledAlpha, typedValue, true)) {
            return typedValue.getFloat();
        }
        return 0.5f;
    }

    public static Drawable getIconByAttrId(int i, Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        Drawable drawable = AppCompatResources.getDrawable(obtainStyledAttributes.getResourceId(0, 0), context);
        if (isLightTheme(context)) {
            Object obj = ContextCompat.sLock;
            drawable.setTint(context.getColor(R.color.mr_dynamic_dialog_icon_light));
        }
        obtainStyledAttributes.recycle();
        return drawable;
    }

    public static int getRouterThemeId(Context context) {
        return isLightTheme(context) ? getControllerColor(0, context) == -570425344 ? 2132018504 : 2132018505 : getControllerColor(0, context) == -570425344 ? 2132018506 : 2132018503;
    }

    public static int getThemeColor(int i, Context context, int i2) {
        if (i != 0) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, new int[]{i2});
            int color = obtainStyledAttributes.getColor(0, 0);
            obtainStyledAttributes.recycle();
            if (color != 0) {
                return color;
            }
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i2, typedValue, true);
        return typedValue.resourceId != 0 ? context.getResources().getColor(typedValue.resourceId) : typedValue.data;
    }

    public static int getThemeResource(int i, Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue.resourceId;
        }
        return 0;
    }

    public static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true) && typedValue.data != 0;
    }

    public static void setDialogBackgroundColor(Context context, Dialog dialog) {
        View decorView = dialog.getWindow().getDecorView();
        int i = isLightTheme(context) ? R.color.mr_dynamic_dialog_background_light : R.color.mr_dynamic_dialog_background_dark;
        Object obj = ContextCompat.sLock;
        decorView.setBackgroundColor(context.getColor(i));
    }

    public static void setIndeterminateProgressBarColor(Context context, ProgressBar progressBar) {
        if (progressBar.isIndeterminate()) {
            int i = isLightTheme(context) ? R.color.mr_cast_progressbar_progress_and_thumb_light : R.color.mr_cast_progressbar_progress_and_thumb_dark;
            Object obj = ContextCompat.sLock;
            progressBar.getIndeterminateDrawable().setColorFilter(context.getColor(i), PorterDuff.Mode.SRC_IN);
        }
    }

    public static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider, View view) {
        int controllerColor = getControllerColor(0, context);
        if (Color.alpha(controllerColor) != 255) {
            controllerColor = ColorUtils.compositeColors(controllerColor, ((Integer) view.getTag()).intValue());
        }
        mediaRouteVolumeSlider.setColor(controllerColor, controllerColor);
    }
}
