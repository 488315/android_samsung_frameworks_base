package com.samsung.android.content.smartclip;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/* loaded from: classes6.dex */
public class SmartClipUtils {
    private static final String TAG = "SmartClipUtils";

    public static boolean isValidMetaTag(SemSmartClipMetaTag semSmartClipMetaTag) {
        if (semSmartClipMetaTag == null || semSmartClipMetaTag.getType() == null) {
            return false;
        }
        String value = semSmartClipMetaTag.getValue();
        return (semSmartClipMetaTag.getType().equals("url") && (value == null || value.startsWith("about:") || value.startsWith("email://"))) ? false : true;
    }

    public static boolean isInstanceOf(Object obj, String str) {
        if (obj != null && str != null) {
            try {
                return Class.forName(str, true, obj.getClass().getClassLoader()).isInstance(obj);
            } catch (ClassNotFoundException unused) {
            }
        }
        return false;
    }

    public static String getChromeViewClassNameFromManifest(Context context, String str) {
        ApplicationInfo applicationInfo;
        String str2 = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (applicationInfo == null) {
            Log.e(TAG, "getChromeViewClassNameFromManifest : Could not get appInfo! - " + str);
            return null;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null && (str2 = bundle.getString("org.chromium.content.browser.SMART_CLIP_PROVIDER")) != null) {
            Log.d(TAG, "Target chrome view = " + str2);
        }
        return str2;
    }

    public static Rect getViewBoundsOnScreen(View view) {
        Rect rect = new Rect();
        Point viewLocationOnScreen = getViewLocationOnScreen(view);
        rect.left = viewLocationOnScreen.x;
        rect.top = viewLocationOnScreen.y;
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        return rect;
    }

    public static Point getViewLocationOnScreen(View view) {
        Point point = new Point();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        point.x = iArr[0];
        point.y = iArr[1];
        return point;
    }
}
