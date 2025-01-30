package com.samsung.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.p009os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.android.internal.policy.DecorView;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SemViewUtils {
  private static final float CUBIC_INIT_X0 = 0.0f;
  private static final float CUBIC_INIT_X1 = 0.0f;
  private static final float CUBIC_INIT_X2 = 15.61f;
  private static final float CUBIC_INIT_X3 = 32.76f;
  private static final float CUBIC_INIT_Y0 = 32.76f;
  private static final float CUBIC_INIT_Y1 = 8.38f;
  private static final float CUBIC_INIT_Y2 = 0.0f;
  private static final float CUBIC_INIT_Y3 = 0.0f;
  private static final float INIT_OFFSET_1 = 0.0f;
  private static final float INIT_OFFSET_2 = 32.76f;
  private static final int NOT_INITIALIZED = -1;
  private static final int NOT_SUPPORTED = 0;
  private static final int SUPPORTED = 1;
  private static String TAG_LAYOUT = "ViewUtils_layout";
  private static int sIsTablet = -1;

  public static boolean isTablet() {
    if (sIsTablet == -1) {
      sIsTablet =
          SystemProperties.get("ro.build.characteristics", "phone")
                  .contains(BnRConstants.DEVICETYPE_TABLET)
              ? 1
              : 0;
    }
    return sIsTablet == 1;
  }

  public static boolean isFoldDevice() {
    return false;
  }

  public static boolean isLightTheme(Context context) {
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(16844176, outValue, true);
    return outValue.data != 0;
  }

  public static boolean isNightMode(Context context) {
    return (context.getResources().getConfiguration().uiMode & 48) == 32;
  }

  public static boolean isDeviceDefaultFamily(Context context) {
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, false);
    return outValue.data != 0;
  }

  public static boolean isOpenThemeApplied(Context context) {
    return Settings.System.getString(
            context.getContentResolver(), "current_sec_active_themepackage")
        != null;
  }

  public static int dipToPx(float dip, Resources res) {
    return (int) TypedValue.applyDimension(1, dip, res.getDisplayMetrics());
  }

  public static boolean isTalkbackEnabled(Context context) {
    AccessibilityManager am = AccessibilityManager.getInstance(context);
    return am.semIsAccessibilityServiceEnabled(32) || am.semIsAccessibilityServiceEnabled(16);
  }

  public static DecorView getDecorView(View view) {
    ViewRootImpl viewRootImpl;
    if (view != null && (viewRootImpl = view.getViewRootImpl()) != null) {
      View rootView = viewRootImpl.getView();
      if (rootView instanceof DecorView) {
        return (DecorView) rootView;
      }
      return null;
    }
    return null;
  }

  public static int getAttrColor(Context context, int attr) {
    TypedArray ta = context.obtainStyledAttributes(new int[] {attr});
    int color = ta.getColor(0, 0);
    ta.recycle();
    return color;
  }

  static float dpToPixel(Context context, float dp) {
    return (context.getResources().getDisplayMetrics().densityDpi / 160.0f) * dp;
  }

  public static Path getPopOverSmoothRoundedRect(Context context, int width, int height) {
    Path path = new Path();
    float widthLineLen = width - (dpToPixel(context, 32.76f) * 2.0f);
    float heightLineLen = height - (dpToPixel(context, 32.76f) * 2.0f);
    float p0x = dpToPixel(context, 0.0f);
    float p0y = dpToPixel(context, 32.76f);
    float p1x = dpToPixel(context, 0.0f);
    float p1y = dpToPixel(context, CUBIC_INIT_Y1);
    float p2x = dpToPixel(context, CUBIC_INIT_X2);
    float p2y = dpToPixel(context, 0.0f);
    float p3x = dpToPixel(context, 32.76f);
    float p3y = dpToPixel(context, 0.0f);
    path.moveTo(p0x, p0y);
    path.cubicTo(p1x, p1y, p2x, p2y, p3x, p3y);
    path.lineTo(p3x + widthLineLen, p3y);
    float p3xtop2x = p3x - p2x;
    float p3xtop1x = p3x - p1x;
    float p3xtop0x = p3x - p0x;
    float p3x2 = p3x + widthLineLen;
    float p2x2 = p2x + p3xtop2x + widthLineLen + p3xtop2x;
    float p1x2 = p1x + p3xtop1x + widthLineLen + p3xtop1x;
    float p0x2 = p0x + p3xtop0x + widthLineLen + p3xtop0x;
    path.cubicTo(p2x2, p2y, p1x2, p1y, p0x2, p0y);
    path.lineTo(p0x2, p0y + heightLineLen);
    float p0ytop1y = p0y - p1y;
    float p0ytop2y = p0y - p2y;
    float p0ytop3y = p0y - p3y;
    float p0y2 = p0y + heightLineLen;
    float p1y2 = p1y + p0ytop1y + heightLineLen + p0ytop1y;
    float p2y2 = p2y + p0ytop2y + heightLineLen + p0ytop2y;
    float p3y2 = p3y + p0ytop3y + heightLineLen + p0ytop3y;
    path.cubicTo(p1x2, p1y2, p2x2, p2y2, p3x2, p3y2);
    path.lineTo(p3x2 - widthLineLen, p3y2);
    float p2xtop3x = p2x2 - p3x2;
    float p1xtop3x = p1x2 - p3x2;
    float p0xtop3x = p0x2 - p3x2;
    float p0x3 = ((p0x2 - p0xtop3x) - widthLineLen) - p0xtop3x;
    path.cubicTo(
        ((p2x2 - p2xtop3x) - widthLineLen) - p2xtop3x,
        p2y2,
        ((p1x2 - p1xtop3x) - widthLineLen) - p1xtop3x,
        p1y2,
        p0x3,
        p0y2);
    path.lineTo(p0x3, p0y2 - heightLineLen);
    path.close();
    return path;
  }

  public static Path getSmoothRoundedRect(int width, int height, int left, int top, int radius) {
    int right = left + width;
    int widthMinusCorners = width - (radius * 2);
    int heightMinusCorners = height - (radius * 2);
    Path path = new Path();
    path.moveTo(right, top + radius);
    path.rQuadTo(0.0f, -radius, -radius, -radius);
    path.rLineTo(-widthMinusCorners, 0.0f);
    path.rQuadTo(-radius, 0.0f, -radius, radius);
    path.rLineTo(0.0f, heightMinusCorners);
    path.rQuadTo(0.0f, radius, radius, radius);
    path.rLineTo(widthMinusCorners, 0.0f);
    path.rQuadTo(radius, 0.0f, radius, -radius);
    path.rLineTo(0.0f, -heightMinusCorners);
    path.close();
    return path;
  }

  public static Path getSmoothLeftRoundedRect(
      int width, int height, int left, int top, int radius) {
    int widthMinusCorners = width - (radius * 2);
    int heightMinusCorners = height - (radius * 2);
    Path path = new Path();
    path.moveTo(left + radius, top);
    path.rQuadTo(-radius, 0.0f, -radius, radius);
    path.rLineTo(0.0f, heightMinusCorners);
    path.rQuadTo(0.0f, radius, radius, radius);
    path.rLineTo(widthMinusCorners + radius, 0.0f);
    path.rLineTo(0.0f, -height);
    path.rLineTo((0 - widthMinusCorners) - radius, 0.0f);
    path.close();
    return path;
  }

  public static Path getSmoothRightRoundedRect(
      int width, int height, int left, int top, int radius) {
    int right = left + width;
    int widthMinusCorners = width - (radius * 2);
    int heightMinusCorners = height - (radius * 2);
    Path path = new Path();
    path.moveTo(right, top + radius);
    path.rQuadTo(0.0f, -radius, -radius, -radius);
    path.rLineTo((-widthMinusCorners) - radius, 0.0f);
    path.rLineTo(0.0f, height);
    path.rLineTo(widthMinusCorners + radius, 0.0f);
    path.rQuadTo(radius, 0.0f, radius, -radius);
    path.rLineTo(0.0f, -heightMinusCorners);
    path.close();
    return path;
  }

  public static Path getRoundedCorner(int flag, int left, int top, int radius) {
    int right = left + radius;
    Path path = new Path();
    switch (flag) {
      case 1:
        path.moveTo(right, top);
        path.rQuadTo(-radius, 0.0f, -radius, radius);
        path.rLineTo(0.0f, -radius);
        break;
      case 2:
        path.moveTo(right, top + radius);
        path.rQuadTo(0.0f, -radius, -radius, -radius);
        path.rLineTo(radius, 0.0f);
        break;
      case 4:
        path.moveTo(left, top);
        path.rQuadTo(0.0f, radius, radius, radius);
        path.rLineTo(-radius, 0.0f);
        break;
      case 8:
        path.moveTo(left, top + radius);
        path.rQuadTo(radius, 0.0f, radius, -radius);
        path.rLineTo(0.0f, radius);
        break;
    }
    path.close();
    return path;
  }

  public static void printViewLayoutInfo(View root) {
    try {
      Log.m98i(TAG_LAYOUT, "--------------View hierarchy info-----------");
      Log.m98i(TAG_LAYOUT, "--------------------------------------------");
      Log.m98i(TAG_LAYOUT, "package=" + root.getContext().getPackageName());
      if (root == null) {
        Log.m98i(TAG_LAYOUT, "Unable to get layout information because root is null.");
        return;
      }
      ArrayList<View> views = new ArrayList<>();
      printViewHierarchyInfo(views, root, 0, 0);
      Log.m98i(TAG_LAYOUT, "View properties:");
      int[] location = new int[2];
      Iterator<View> it = views.iterator();
      while (it.hasNext()) {
        View v = it.next();
        StringBuilder sb = new StringBuilder();
        sb.append("+ ")
            .append(v.getClass().getName())
            .append('@')
            .append(Integer.toHexString(v.hashCode()));
        String entry = null;
        int id = v.getId();
        if (id != -1 && (((-16777216) & id) != 0 || (16777215 & id) == 0)) {
          try {
            Resources res = root.getResources();
            entry = res.getResourceEntryName(id);
          } catch (Resources.NotFoundException e) {
            entry = null;
          }
        }
        if (entry != null) {
          sb.append(" id/" + entry);
        }
        sb.append("\n Accessibility: ")
            .append(" contentDescription=")
            .append(v.getContentDescription())
            .append(" importantForAccessibility=")
            .append(v.isImportantForAccessibility() ? "yes" : "no");
        sb.append("\n Drawing: ")
            .append(" alpha=")
            .append(v.getAlpha())
            .append(" elevation=")
            .append(v.getElevation())
            .append(" x=")
            .append(v.getX())
            .append(" y=")
            .append(v.getY())
            .append(" z=")
            .append(v.getZ())
            .append(" isOpaque=")
            .append(v.isOpaque());
        sb.append("\n Focus: ")
            .append(" hasFocus=")
            .append(v.hasFocus())
            .append(" isFocusable=")
            .append(v.isFocusable())
            .append(" isFocused=")
            .append(v.isFocused());
        v.getLocationOnScreen(location);
        sb.append("\n Layout: ")
            .append(" width=")
            .append(v.getMeasuredWidth())
            .append(" height=")
            .append(v.getMeasuredHeight())
            .append(" locationOnScreen_x=")
            .append(location[0])
            .append(" locationOnScreen_y=")
            .append(location[1])
            .append(" left=")
            .append(v.getLeft())
            .append(" right=")
            .append(v.getRight())
            .append(" top=")
            .append(v.getTop())
            .append(" bottom=")
            .append(v.getBottom());
        sb.append("\n Padding: ")
            .append(" paddingLeft=")
            .append(v.getPaddingLeft())
            .append(" paddingRight=")
            .append(v.getPaddingRight())
            .append(" paddingTop=")
            .append(v.getPaddingTop())
            .append(" paddingBottom=")
            .append(v.getPaddingBottom())
            .append(" paddingStart=")
            .append(v.getPaddingStart())
            .append(" paddingEnd=")
            .append(v.getPaddingEnd());
        sb.append("\n Miscellaneous: ")
            .append(" fitsSystemWindows=")
            .append(v.getFitsSystemWindows())
            .append(" visibility=")
            .append(v.getVisibility())
            .append(" isClickable=")
            .append(v.isClickable())
            .append(" isEnabled=")
            .append(v.isEnabled());
        if (v instanceof TextView) {
          TextView tv = (TextView) v;
          sb.append("\n Text: ")
              .append(" scaledTextSize=")
              .append(tv.getScaledTextSize())
              .append(" textSize=")
              .append(tv.getTextSize())
              .append(" typefaceStyle=")
              .append(tv.getTypefaceStyle())
              .append(" text=")
              .append(tv.getText());
        }
        Log.m98i(TAG_LAYOUT, sb.toString());
      }
    } catch (Exception ex) {
      Log.m96e(TAG_LAYOUT, "Failed to get view hierarchy information. ex=" + ex);
    }
  }

  private static void printViewHierarchyInfo(
      ArrayList<View> viewList, View view, int depth, int index) {
    viewList.add(view);
    StringBuilder indent = new StringBuilder();
    for (int i = 0; i < depth; i++) {
      indent.append("│   ");
    }
    int childCount = view instanceof ViewGroup ? ((ViewGroup) view).getChildCount() : 0;
    if (childCount > 0) {
      if (index < childCount) {
        Log.m98i(
            TAG_LAYOUT,
            ((Object) indent)
                + "├── "
                + view.getClass().getName()
                + '@'
                + Integer.toHexString(view.hashCode()));
      } else {
        Log.m98i(
            TAG_LAYOUT,
            ((Object) indent)
                + "└── "
                + view.getClass().getName()
                + '@'
                + Integer.toHexString(view.hashCode()));
      }
      for (int i2 = 0; i2 < childCount; i2++) {
        View child = ((ViewGroup) view).getChildAt(i2);
        printViewHierarchyInfo(viewList, child, depth + 1, i2);
      }
      return;
    }
    Log.m98i(
        TAG_LAYOUT,
        ((Object) indent)
            + "└── "
            + view.getClass().getName()
            + '@'
            + Integer.toHexString(view.hashCode()));
  }
}
