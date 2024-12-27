package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.SeslSeekBar;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class Utils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public static String getColorName(int i) {
        if (i == 0) {
            return "app color";
        }
        if (i == 99) {
            return "custom";
        }
        switch (i) {
            case 3:
                return "blue";
            case 4:
                return "pink";
            case 5:
                return "red";
            case 6:
                return "orange";
            case 7:
                return "light green";
            case 8:
                return "green";
            case 9:
                return "turquoise";
            case 10:
                return "skyblue";
            case 11:
                return "deep blue";
            case 12:
                return "indie pink";
            case 13:
                return "purple";
            default:
                return "";
        }
    }

    public static String getEffectEnglishName(String str) {
        str.getClass();
        switch (str) {
            case "preload/spotlight":
                return "spotlight";
            case "preload/fireworks":
                return "fireworks";
            case "preload/noframe":
                return SignalSeverity.NONE;
            case "preload/eclipse":
                return "elicpse";
            case "preload/reflection":
                return "glitter";
            case "preload/basic":
                return "basic";
            case "preload/heart":
                return "heart";
            case "preload/echo":
                return "echo";
            default:
                return "";
        }
    }

    public static Size getScreenSize(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(WindowManager.class)).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return new Size(point.x, point.y);
    }

    public static boolean isLargeCoverFlipFolded() {
        SemWindowManager semWindowManager;
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        if (string.contains("COVER") && string.contains("LARGESCREEN") && (semWindowManager = SemWindowManager.getInstance()) != null) {
            return semWindowManager.isFolded();
        }
        return false;
    }

    public static void setSeekBarContentDescription(Context context, SeslSeekBar seslSeekBar, CharSequence charSequence) {
        TalkBackUtil talkBackUtil = TalkBackUtil.getInstance(context);
        AccessibilityManager accessibilityManager = talkBackUtil.mAccessibilityManager;
        if (accessibilityManager != null && accessibilityManager.isEnabled() && talkBackUtil.mIsTalkbackMode) {
            seslSeekBar.setContentDescription(charSequence);
        } else {
            seslSeekBar.setContentDescription(Integer.toString(seslSeekBar.getProgress()));
        }
    }
}
