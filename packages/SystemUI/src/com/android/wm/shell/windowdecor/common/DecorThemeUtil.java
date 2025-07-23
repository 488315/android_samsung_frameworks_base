package com.android.wm.shell.windowdecor.common;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.DynamicTonalPaletteKt;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DecorThemeUtil {
    public final Context context;
    public final ColorScheme darkColors;
    public final ColorScheme lightColors;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Theme.values().length];
            try {
                iArr[Theme.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Theme.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DecorThemeUtil(Context context) {
        this.context = context;
        this.lightColors = DynamicTonalPaletteKt.dynamicLightColorScheme(context);
        this.darkColors = DynamicTonalPaletteKt.dynamicDarkColorScheme(context);
    }

    public final Theme getAppTheme(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
        return taskDescription != null ? ((double) Color.valueOf(taskDescription.getBackgroundColor()).luminance()) < 0.5d ? Theme.DARK : Theme.LIGHT : (this.context.getResources().getConfiguration().uiMode & 48) == 32 ? Theme.DARK : Theme.LIGHT;
    }

    public final ColorScheme getColorScheme(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = WhenMappings.$EnumSwitchMapping$0[getAppTheme(runningTaskInfo).ordinal()];
        if (i == 1) {
            return this.lightColors;
        }
        if (i == 2) {
            return this.darkColors;
        }
        throw new NoWhenBranchMatchedException();
    }
}
