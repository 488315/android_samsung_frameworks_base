package com.samsung.android.content.smartclip;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SmartClipUtils {
    private static final String TAG = "SmartClipUtils";

    public static boolean isValidMetaTag(SemSmartClipMetaTag metaTag) {
        if (metaTag == null || metaTag.getType() == null) {
            return false;
        }
        String metaValue = metaTag.getValue();
        return (metaTag.getType().equals("url")
                        && (metaValue == null
                                || metaValue.startsWith("about:")
                                || metaValue.startsWith("email://")))
                ? false
                : true;
    }

    public static boolean isInstanceOf(Object o, String className) {
        if (o == null || className == null) {
            return false;
        }
        try {
            Class<?> targetClass = Class.forName(className, true, o.getClass().getClassLoader());
            return targetClass.isInstance(o);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static String getChromeViewClassNameFromManifest(Context context, String packageName) {
        ApplicationInfo ai;
        String chromeViewName = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(packageName, 128);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (ai == null) {
            Log.e(
                    TAG,
                    "getChromeViewClassNameFromManifest : Could not get appInfo! - " + packageName);
            return null;
        }
        Bundle bundle = ai.metaData;
        if (bundle != null
                && (chromeViewName =
                                bundle.getString(
                                        "org.chromium.content.browser.SMART_CLIP_PROVIDER"))
                        != null) {
            Log.d(TAG, "Target chrome view = " + chromeViewName);
        }
        return chromeViewName;
    }

    public static Rect getViewBoundsOnScreen(View view) {
        Rect screenRectOfView = new Rect();
        Point screenPointOfView = getViewLocationOnScreen(view);
        screenRectOfView.left = screenPointOfView.x;
        screenRectOfView.top = screenPointOfView.y;
        screenRectOfView.right = screenRectOfView.left + view.getWidth();
        screenRectOfView.bottom = screenRectOfView.top + view.getHeight();
        return screenRectOfView;
    }

    public static Point getViewLocationOnScreen(View view) {
        Point screenPointOfView = new Point();
        int[] screenOffsetOfView = new int[2];
        view.getLocationOnScreen(screenOffsetOfView);
        screenPointOfView.x = screenOffsetOfView[0];
        screenPointOfView.y = screenOffsetOfView[1];
        return screenPointOfView;
    }
}
