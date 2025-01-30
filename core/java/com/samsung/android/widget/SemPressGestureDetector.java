package com.samsung.android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.p009os.Bundle;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import com.android.internal.policy.DecorContext;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class SemPressGestureDetector {
    private static final int BIXBY_TOUCH_DETECTOR_VERSION = 3;
    private static final String CALL_METHOD = "send_bixby_touch_event";
    private static final String CALL_REFLECT_METHOD = "bixby_touch_reflect_widget";
    private static final int DEFAULT_CHECK_TOUCH_DOWN_DELAY_TIME = 100;
    private static final int DEFAULT_FINGER_DOWN_THRESHOLD = 100;
    private static final int DEFAULT_LONG_LONG_PRESS_TIME = 1500;
    private static final int DEFAULT_LONG_PRESS_TIME = 500;
    private static final int DOUBLE_FINGER_TOUCH_MODE = 2;
    private static final String KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD = "bixbytouch_finger_down_threshold";
    private static final String KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER = "bixbytouch_finger_touch_mode";
    private static final String KEY_BIXBYTOUCH_LONG_PRESS_TIME = "bixbytouch_long_press_timeout";
    private static final String KEY_BIXBYTOUCH_VERSION = "key_bixbytouch_version";
    private static final String KEY_CHECK_FP_DELAY_TIME = "check_touch_down_delay_time";
    private static final String KEY_LONG_LONG_PRESS_TIME = "long_long_press_timeout";
    private static final int LONG_CLICKED_BIXBY = 1;
    private static final int LONG_LONG_CANCEL_BIXBY = 3;
    private static final int LONG_LONG_CLICKED_BIXBY = 2;
    private static final String PERMISSION_WRITE_SECURE_SETTINGS = "android.permission.WRITE_SECURE_SETTINGS";
    private static final int REFLECT_FIELD_LEVEL_SELF = 0;
    private static final int REFLECT_FIELD_LEVEL_SUPER = 1;
    private static final int REFLECT_FIELD_LEVEL_SUPER_SUPER = 2;
    private static final int REFLECT_MAX_COUNT = 3;
    private static final int SINGLE_FINGER_TOUCH_MODE = 1;
    private static final long SUPPORT_DOUBLE_FINGER_MODE_MIN_VERSION = 300000000;
    private static final String TAEGET_PKG_NAME = "com.samsung.android.bixbytouch";
    private static final String TAG = "SemPressGestureDetector";
    private static final float TOUCH_MOVE_MAX_MM = 3.0f;
    private Rect mAppBounds;
    private Rect mBounds;
    private Context mContext;
    private Rect mDecorViewBounds;
    private Insets mDisplayCutoutInsets;
    private boolean mFindViewRestricted;
    private Rect mMaxBounds;
    private Insets mNavigationBarsInsets;
    private View mView;
    private static final String BIXBY_TOUCH_AUTHORITY = "content://com.samsung.android.bixbytouch";
    private static final Uri BIXBY_TOUCH_URI = Uri.parse(BIXBY_TOUCH_AUTHORITY);
    private static int sLongPressTime = 500;
    private static int sLongLongPressTime = 1500;
    private static int sCheckTouchDownDelayTime = 100;
    private static int sFingerDownThreshold = 100;
    private static int sCurrentTouchMode = 1;
    private static boolean sBixbyTouchEnable = false;
    private static boolean sHasFingerPrintFeature = false;
    private static int sHasCallReflectCount = 0;
    private static int sTouchMoveMaxPixel = 50;
    private static long sVersionCode = -1;
    private static String sPreviousPackage = null;
    private static ArrayList<String> sWidgetIdList = new ArrayList<>();
    private static ArrayList<String> sWidgetNameList = new ArrayList<>();
    private static long sRequestCode = 0;
    private boolean mDetachedFromWindow = false;
    private boolean mHasDoneLongTouch = false;
    private boolean mTouchDownRestricted = false;
    private boolean mInitFailed = false;
    private boolean mResponeLongTouch = true;
    private boolean mResponeLongLongTouch = false;
    private ArrayList<View> mTouchedViews = new ArrayList<>();
    private String mCallerPackage = null;
    private String mProcessName = null;
    private String mActivityName = null;
    private int mTaskId = -1;
    private int mWindowingMode = -1;
    private String mWindowConfig = null;
    private ArrayList<Point> mTouchedPoints = new ArrayList<>();
    private ArrayList<Point> mTouchedRawPoints = new ArrayList<>();
    private long mTouchedTime = 0;
    private long mBixbyTouchVersion = 0;
    private Runnable mLongLongTouchRunnable = new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.1
        @Override // java.lang.Runnable
        public void run() {
            Log.secD(SemPressGestureDetector.TAG, "mLongLongTouchRunnable: " + SemPressGestureDetector.this.mCallerPackage + "," + SemPressGestureDetector.this.mActivityName + "," + SemPressGestureDetector.this.mProcessName);
            SemPressGestureDetector semPressGestureDetector = SemPressGestureDetector.this;
            semPressGestureDetector.mResponeLongLongTouch = semPressGestureDetector.sendBixbyLongClickedEvent(2);
        }
    };
    private Runnable mLongTouchRunnable = new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.2
        @Override // java.lang.Runnable
        public void run() {
            if (SemPressGestureDetector.this.mTouchDownRestricted) {
                SemPressGestureDetector.this.mResponeLongTouch = false;
                return;
            }
            SemPressGestureDetector.sRequestCode = System.currentTimeMillis();
            SemPressGestureDetector.sWidgetNameList.clear();
            SemPressGestureDetector.sWidgetIdList.clear();
            SemPressGestureDetector.this.mHasDoneLongTouch = true;
            SemPressGestureDetector.this.parseInfoFromView();
            Log.secD(SemPressGestureDetector.TAG, "mLongTouchRunnable: " + SemPressGestureDetector.this.mCallerPackage + "," + SemPressGestureDetector.this.mActivityName + "," + SemPressGestureDetector.this.mProcessName);
            SemPressGestureDetector.sHasCallReflectCount = 0;
            if (SemPressGestureDetector.this.mView != null) {
                SemPressGestureDetector semPressGestureDetector = SemPressGestureDetector.this;
                semPressGestureDetector.mTouchedViews = semPressGestureDetector.getTouchedViews();
                SemPressGestureDetector semPressGestureDetector2 = SemPressGestureDetector.this;
                semPressGestureDetector2.mResponeLongTouch = semPressGestureDetector2.sendBixbyLongClickedEvent(1);
                if (SemPressGestureDetector.this.mResponeLongTouch) {
                    SemPressGestureDetector.this.mView.postDelayed(SemPressGestureDetector.this.mLongLongTouchRunnable, SemPressGestureDetector.sLongLongPressTime);
                }
            }
        }
    };
    private Runnable mCheckRestrictTouchRunnable = new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.3
        @Override // java.lang.Runnable
        public void run() {
            SemPressGestureDetector semPressGestureDetector = SemPressGestureDetector.this;
            semPressGestureDetector.mTouchDownRestricted = semPressGestureDetector.isFingerPrintInDisplay();
        }
    };
    private Runnable mGetSettingRunnable = new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.4
        @Override // java.lang.Runnable
        public void run() {
            try {
                long version = SemPressGestureDetector.getAppVersionCode(SemPressGestureDetector.this.mContext, SemPressGestureDetector.TAEGET_PKG_NAME);
                if (version < SemPressGestureDetector.SUPPORT_DOUBLE_FINGER_MODE_MIN_VERSION && version != SemPressGestureDetector.this.mBixbyTouchVersion) {
                    if (SemPressGestureDetector.this.mContext.checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == 0) {
                        Settings.Secure.putInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, 1);
                    }
                    SemPressGestureDetector.sCurrentTouchMode = 1;
                } else {
                    SemPressGestureDetector.sCurrentTouchMode = Settings.Secure.getInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, 1);
                }
                SemPressGestureDetector.sLongPressTime = Settings.Secure.getInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_BIXBYTOUCH_LONG_PRESS_TIME, 500);
                SemPressGestureDetector.sLongLongPressTime = Settings.Secure.getInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_LONG_LONG_PRESS_TIME, 1500);
                SemPressGestureDetector.sCheckTouchDownDelayTime = Settings.Secure.getInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_CHECK_FP_DELAY_TIME, 100);
                SemPressGestureDetector.sFingerDownThreshold = Settings.Secure.getInt(SemPressGestureDetector.this.mContext.getContentResolver(), SemPressGestureDetector.KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD, 100);
                SemPressGestureDetector.this.mBixbyTouchVersion = version;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static class Point {

        /* renamed from: x */
        float f3087x;

        /* renamed from: y */
        float f3088y;

        public Point(float x, float y) {
            this.f3087x = x;
            this.f3088y = y;
        }
    }

    public ArrayList<View> getTouchedViews() {
        ArrayList<View> mTouchedViews = new ArrayList<>();
        try {
            if (sCurrentTouchMode == 2) {
                if (this.mTouchedRawPoints.size() == 2) {
                    float mTouchedX0 = this.mTouchedPoints.get(0).f3087x;
                    float mTouchedY0 = this.mTouchedPoints.get(0).f3088y;
                    float mTouchedX1 = this.mTouchedPoints.get(1).f3087x;
                    float mTouchedY1 = this.mTouchedPoints.get(1).f3088y;
                    View view0 = this.mView.semDispatchFindView(mTouchedX0, mTouchedY0, true);
                    View view1 = this.mView.semDispatchFindView(mTouchedX1, mTouchedY1, true);
                    if (view0 != null) {
                        mTouchedViews.add(view0);
                    }
                    if (view1 != null) {
                        mTouchedViews.add(view1);
                    }
                }
            } else if (this.mTouchedRawPoints.size() == 1) {
                float mTouchedX = this.mTouchedPoints.get(0).f3087x;
                float mTouchedY = this.mTouchedPoints.get(0).f3088y;
                View mTouchedView = this.mView.semDispatchFindView(mTouchedX, mTouchedY, true);
                if (mTouchedView != null) {
                    mTouchedViews.add(mTouchedView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTouchedViews;
    }

    public SemPressGestureDetector(Context context, View view) {
        init(context, view);
    }

    private static long getTouchedAppVersionCode(Context context, String packageName) {
        if (sVersionCode < 0 || (packageName != null && !packageName.equals(sPreviousPackage))) {
            sVersionCode = getAppVersionCode(context, packageName);
            sPreviousPackage = packageName;
        }
        return sVersionCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getAppVersionCode(Context context, String packageName) {
        try {
            long versionCode = context.getPackageManager().getPackageInfo(packageName, 0).getLongVersionCode();
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getText(Context context, String packageName, View view) {
        int i;
        Object charSequence;
        try {
            sWidgetNameList.add(view.getClass().getName());
            if (view.getId() != -1) {
                sWidgetIdList.add(context.getResources().getResourceName(view.getId()));
            }
        } catch (Exception e) {
        }
        try {
            i = sHasCallReflectCount;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (i >= 3) {
            return null;
        }
        sHasCallReflectCount = i + 1;
        Class clazz = view.getClass();
        Bundle bundle = new Bundle();
        long versionCode = getTouchedAppVersionCode(context, packageName);
        bundle.putLong("request_code", sRequestCode);
        bundle.putString("caller_package", packageName);
        bundle.putString("caller_class", clazz.getName());
        bundle.putLong("caller_version_code", versionCode);
        Bundle result = context.getContentResolver().call(BIXBY_TOUCH_URI, CALL_REFLECT_METHOD, (String) null, bundle);
        if (result != null) {
            String fieldName = result.getString("reflect_field_name");
            String methodName = result.getString("reflect_method_name");
            int fieldLevel = result.getInt("reflect_field_level");
            if (fieldName != null) {
                Field field = null;
                switch (fieldLevel) {
                    case 0:
                        field = clazz.getDeclaredField(fieldName);
                        break;
                    case 1:
                        field = clazz.getSuperclass().getDeclaredField(fieldName);
                        break;
                    case 2:
                        field = clazz.getSuperclass().getSuperclass().getDeclaredField(fieldName);
                        break;
                }
                if (field != null) {
                    field.setAccessible(true);
                    Object charSequence2 = field.get(view);
                    if (charSequence2 != null) {
                        return charSequence2.toString();
                    }
                }
            } else {
                Method method = clazz.getMethod(methodName, new Class[0]);
                if (method != null && (charSequence = method.invoke(view, new Object[0])) != null) {
                    return charSequence.toString();
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseInfoFromView() {
        try {
            if (this.mView != null) {
                Activity activity = parseActivity();
                if (activity != null) {
                    this.mActivityName = activity.getComponentName().getClassName();
                    this.mTaskId = activity.getTaskId();
                    Configuration configuration = getCurrentConfigFromActivity(activity);
                    if (configuration != null) {
                        this.mWindowConfig = configuration.windowConfiguration.toString();
                        this.mWindowingMode = configuration.windowConfiguration.getWindowingMode();
                    } else {
                        this.mWindowConfig = this.mView.getResources().getConfiguration().windowConfiguration.toString();
                        this.mWindowingMode = this.mView.getResources().getConfiguration().windowConfiguration.getWindowingMode();
                    }
                    this.mAppBounds = activity.getWindow().getWindowManager().getCurrentWindowMetrics().getBounds();
                    this.mMaxBounds = activity.getWindow().getWindowManager().getMaximumWindowMetrics().getBounds();
                    this.mBounds = this.mAppBounds;
                    int[] location = new int[2];
                    View view = activity.getWindow().getDecorView();
                    view.getLocationOnScreen(location);
                    this.mDecorViewBounds = new Rect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
                    Log.secD(TAG, "parseInfoFromView: mDecorViewBounds = " + this.mDecorViewBounds.toString());
                    try {
                        this.mDisplayCutoutInsets = activity.getWindow().getWindowManager().getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.displayCutout());
                        this.mNavigationBarsInsets = activity.getWindow().getWindowManager().getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                this.mWindowConfig = this.mView.getResources().getConfiguration().windowConfiguration.toString();
                this.mAppBounds = this.mView.getResources().getConfiguration().windowConfiguration.getAppBounds();
                this.mWindowingMode = this.mView.getResources().getConfiguration().windowConfiguration.getWindowingMode();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private Activity parseActivity() {
        Activity activity = null;
        Context baseContext = this.mContext;
        Context context = this.mContext;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (context instanceof DecorContext) {
            baseContext = getContextFromDecorContext(this.mView.getContext());
            if (baseContext instanceof Activity) {
                activity = (Activity) baseContext;
            }
        }
        if (activity == null) {
            return getActivityFromContextWrapper(baseContext);
        }
        return activity;
    }

    private Activity getActivityFromContextWrapper(Context context) {
        if (context instanceof ContextWrapper) {
            Context res = ((ContextWrapper) context).getBaseContext();
            if (res instanceof Activity) {
                return (Activity) res;
            }
            return getActivityFromContextWrapper(res);
        }
        return null;
    }

    private Context getContextFromDecorContext(Context decorContext) {
        try {
            Class clazz = decorContext.getClass();
            Field field = clazz.getDeclaredField("mContext");
            if (field != null) {
                field.setAccessible(true);
                WeakReference<Context> context = (WeakReference) field.get(decorContext);
                return context.get();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Configuration getCurrentConfigFromActivity(Activity activity) {
        try {
            Class clazz = activity.getClass();
            return getCurrentConfig(activity, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Configuration getCurrentConfig(Activity activity, Class sonClass) {
        Class fatherClass = sonClass.getSuperclass();
        try {
            Field field = sonClass.getDeclaredField("mCurrentConfig");
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return (Configuration) field.get(activity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e2) {
            if (fatherClass == null) {
                return null;
            }
            return getCurrentConfig(activity, fatherClass);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFingerPrintInDisplay() {
        if (!sHasFingerPrintFeature) {
            return false;
        }
        try {
            FingerprintManager fpm = (FingerprintManager) this.mContext.getSystemService(Context.FINGERPRINT_SERVICE);
            boolean fingerPrintInDisplay = false;
            int fingerIconHeight = 0;
            if (fpm != null) {
                fingerPrintInDisplay = FingerprintManager.semGetSensorPosition() == 2;
                fingerIconHeight = fpm.semGetIconBottomMargin();
            }
            return fingerPrintInDisplay && fingerIconHeight > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void setBixbyTouchEnable(boolean bixbyTouchEnable) {
        if (this.mFindViewRestricted) {
            return;
        }
        sBixbyTouchEnable = bixbyTouchEnable;
        if (bixbyTouchEnable) {
            new Thread(this.mGetSettingRunnable).start();
        }
    }

    public void init(Context context, View view) {
        boolean z = true;
        if (context == null || view == null) {
            this.mInitFailed = true;
            this.mFindViewRestricted = true;
            return;
        }
        this.mInitFailed = false;
        this.mContext = context;
        this.mView = view;
        this.mCallerPackage = context.getPackageName();
        this.mProcessName = this.mContext.getApplicationInfo().processName;
        sTouchMoveMaxPixel = (int) mm2px(3.0f);
        sHasFingerPrintFeature = hasFingerPrintFeature();
        WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) this.mView.getLayoutParams();
        if (wparams != null) {
            int windowType = wparams.type;
            if (windowType < 2000) {
                z = false;
            }
            this.mFindViewRestricted = z;
            if (z) {
                return;
            }
        }
        checkBlockApp();
    }

    public boolean isInitFailed() {
        return this.mInitFailed;
    }

    public boolean hasFingerPrintFeature() {
        PackageManager packageManager = this.mContext.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendBixbyLongClickedEvent(int flag) {
        String str;
        if (this.mDetachedFromWindow || (str = this.mActivityName) == null || str.startsWith(TAEGET_PKG_NAME)) {
            return false;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putLong("request_code", sRequestCode);
            bundle.putInt("bixby_touch_flag", flag);
            bundle.putInt("bixby_touch_detector_version", 3);
            if (flag == 1) {
                bundle.putString("caller_pkg", this.mCallerPackage);
                bundle.putString("caller_activity", this.mActivityName);
                bundle.putString("caller_process", this.mProcessName);
                bundle.putStringArrayList("widget_name_list", sWidgetNameList);
                bundle.putStringArrayList("widget_id_list", sWidgetIdList);
                bundle.putInt("caller_task_id", this.mTaskId);
                bundle.putInt("window_mode", this.mWindowingMode);
                bundle.putString("window_config", this.mWindowConfig);
                Rect topActivityBounds = this.mAppBounds;
                Rect topActivityWindowBounds = this.mBounds;
                Rect topActivityWindowMaxBounds = this.mMaxBounds;
                Rect topActivityWindowDecorViewBounds = this.mDecorViewBounds;
                if (topActivityBounds != null) {
                    bundle.putInt("window_left", topActivityBounds.left);
                    bundle.putInt("window_top", topActivityBounds.top);
                    bundle.putInt("window_right", topActivityBounds.right);
                    bundle.putInt("window_bottom", topActivityBounds.bottom);
                }
                if (topActivityWindowBounds != null) {
                    bundle.putInt("window_bounds_left", topActivityWindowBounds.left);
                    bundle.putInt("window_bounds_top", topActivityWindowBounds.top);
                    bundle.putInt("window_bounds_right", topActivityWindowBounds.right);
                    bundle.putInt("window_bounds_bottom", topActivityWindowBounds.bottom);
                }
                if (topActivityWindowMaxBounds != null) {
                    bundle.putInt("window_max_bounds_left", topActivityWindowMaxBounds.left);
                    bundle.putInt("window_max_bounds_top", topActivityWindowMaxBounds.top);
                    bundle.putInt("window_max_bounds_right", topActivityWindowMaxBounds.right);
                    bundle.putInt("window_max_bounds_bottom", topActivityWindowMaxBounds.bottom);
                }
                if (topActivityWindowDecorViewBounds != null) {
                    bundle.putInt("window_decor_view_bounds_left", topActivityWindowDecorViewBounds.left);
                    bundle.putInt("window_decor_view_bounds_top", topActivityWindowDecorViewBounds.top);
                    bundle.putInt("window_decor_view_bounds_right", topActivityWindowDecorViewBounds.right);
                    bundle.putInt("window_decor_view_bounds_bottom", topActivityWindowDecorViewBounds.bottom);
                }
                Insets insets = this.mDisplayCutoutInsets;
                if (insets != null) {
                    bundle.putInt("display_cutout_insets_left", insets.left);
                    bundle.putInt("display_cutout_insets_top", this.mDisplayCutoutInsets.top);
                    bundle.putInt("display_cutout_insets_right", this.mDisplayCutoutInsets.right);
                    bundle.putInt("display_cutout_insets_bottom", this.mDisplayCutoutInsets.bottom);
                }
                Insets insets2 = this.mNavigationBarsInsets;
                if (insets2 != null) {
                    bundle.putInt("navigation_bars_insets_left", insets2.left);
                    bundle.putInt("navigation_bars_insets_top", this.mNavigationBarsInsets.top);
                    bundle.putInt("navigation_bars_insets_right", this.mNavigationBarsInsets.right);
                    bundle.putInt("navigation_bars_insets_bottom", this.mNavigationBarsInsets.bottom);
                }
                if (sCurrentTouchMode != 2) {
                    if (this.mTouchedRawPoints.size() == 1) {
                        bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).f3087x);
                        bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).f3088y);
                    }
                } else if (this.mTouchedRawPoints.size() == 2) {
                    bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).f3087x);
                    bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).f3088y);
                    bundle.putFloat("touch_raw_start_x2", this.mTouchedRawPoints.get(1).f3087x);
                    bundle.putFloat("touch_raw_start_y2", this.mTouchedRawPoints.get(1).f3088y);
                }
                if (this.mTouchedViews.size() > 0) {
                    if (sCurrentTouchMode == 1) {
                        View mTouchedView = this.mTouchedViews.get(0);
                        bundle = putTouchedViewInfoToBundle(bundle, mTouchedView);
                    } else {
                        if (this.mTouchedViews.size() == 2) {
                            View view1 = this.mTouchedViews.get(0);
                            View view2 = this.mTouchedViews.get(1);
                            if (view1 == view2) {
                                bundle.putBoolean("fingers_touch_in_same_view", true);
                            } else {
                                bundle.putBoolean("fingers_touch_in_same_view", false);
                            }
                        }
                        for (int i = 0; i < this.mTouchedViews.size(); i++) {
                            View mTouchedView2 = this.mTouchedViews.get(i);
                            Bundle subBundle = new Bundle();
                            String key = "touched_view_info_" + i;
                            bundle.putBundle(key, putTouchedViewInfoToBundle(subBundle, mTouchedView2));
                        }
                    }
                }
            }
            Bundle result = this.mContext.getContentResolver().call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
            if (result != null) {
                if (flag == 1) {
                    long version = result.getLong(KEY_BIXBYTOUCH_VERSION, 0L);
                    if (version != 0) {
                        this.mBixbyTouchVersion = version;
                    }
                    sCurrentTouchMode = result.getInt(KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, sCurrentTouchMode);
                    sLongPressTime = result.getInt(KEY_BIXBYTOUCH_LONG_PRESS_TIME, sLongPressTime);
                    sLongLongPressTime = result.getInt(KEY_LONG_LONG_PRESS_TIME, sLongLongPressTime);
                    sCheckTouchDownDelayTime = result.getInt(KEY_CHECK_FP_DELAY_TIME, sCheckTouchDownDelayTime);
                    sFingerDownThreshold = result.getInt(KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD, sFingerDownThreshold);
                }
                return result.getBoolean("bixby_touch_response", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Bundle putTouchedViewInfoToBundle(Bundle bundle, View touchedView) {
        if (touchedView != null) {
            try {
                if (touchedView instanceof TextView) {
                    bundle.putInt("input_type", ((TextView) touchedView).getInputType());
                }
                bundle.putString("bixby_touch_find_text", touchedView.semGetBixbyTouchFoundText());
                bundle.putString("found_widget_name", touchedView.getClass().getName());
                if (touchedView.getId() != -1) {
                    bundle.putString("found_widget_id", this.mContext.getResources().getResourceName(touchedView.getId()));
                }
                if (touchedView instanceof WebView) {
                    WebView webView = (WebView) touchedView;
                    bundle.putBoolean("touch_webview", true);
                    bundle.putString("touch_webview_url", webView.getUrl());
                    bundle.putString("touch_webview_originalUrl", webView.getOriginalUrl());
                    bundle.putString("touch_webview_title", webView.getTitle());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }

    private void sendBixbyLongLongCancelEvent() {
        this.mHasDoneLongTouch = false;
        sWidgetNameList.clear();
        sWidgetIdList.clear();
        Bundle bundle = new Bundle();
        bundle.putLong("request_code", sRequestCode);
        bundle.putInt("bixby_touch_flag", 3);
        try {
            this.mContext.getContentResolver().call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDetached() {
        if (this.mFindViewRestricted) {
            return;
        }
        this.mDetachedFromWindow = true;
        if (this.mHasDoneLongTouch && this.mResponeLongTouch) {
            sendBixbyLongLongCancelEvent();
        }
        this.mView.removeCallbacks(this.mLongTouchRunnable);
        this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
        this.mView.removeCallbacks(this.mLongLongTouchRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean matchPackage(String pkgName) {
        return pkgName.equals(this.mCallerPackage);
    }

    private float mm2px(float mm) {
        int dpi = this.mContext.getResources().getDisplayMetrics().densityDpi;
        return (mm / 25.4f) * dpi;
    }

    private void checkBlockApp() {
        new Thread(new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.5
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                try {
                    SemPressGestureDetector semPressGestureDetector = SemPressGestureDetector.this;
                    if (!semPressGestureDetector.isLauncherApp() && !SemPressGestureDetector.this.matchPackage(SemPressGestureDetector.TAEGET_PKG_NAME)) {
                        z = false;
                        semPressGestureDetector.mFindViewRestricted = z;
                    }
                    z = true;
                    semPressGestureDetector.mFindViewRestricted = z;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLauncherApp() {
        if (this.mCallerPackage == null) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = this.mContext.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            return false;
        }
        return this.mCallerPackage.equals(res.activityInfo.packageName);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            Log.secD(TAG, "dispatchTouchEvent:" + this.mProcessName + ",sBixbyTouchEnable=" + sBixbyTouchEnable + ",mFindViewRestricted=" + this.mFindViewRestricted);
        }
        if (!sBixbyTouchEnable || this.mFindViewRestricted || this.mDetachedFromWindow) {
            return false;
        }
        if (sCurrentTouchMode == 2) {
            boolean result = dispatchTouchEventDoubleFingers(event);
            return result;
        }
        boolean result2 = dispatchTouchEventOneFinger(event);
        return result2;
    }

    private boolean dispatchTouchEventOneFinger(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            this.mTouchedPoints.clear();
            this.mTouchedRawPoints.clear();
            this.mHasDoneLongTouch = false;
            this.mResponeLongTouch = true;
            this.mResponeLongLongTouch = false;
            this.mTouchDownRestricted = false;
            addTouchedPoint(event);
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime);
            this.mView.postDelayed(this.mCheckRestrictTouchRunnable, sCheckTouchDownDelayTime);
        } else {
            if (event.getActionMasked() == 1) {
                Log.secD(TAG, "mResponeLongTouch=" + this.mResponeLongTouch + ",mResponeLongLongTouch=" + this.mResponeLongLongTouch);
            }
            if (!this.mResponeLongTouch) {
                return false;
            }
        }
        if (!this.mHasDoneLongTouch) {
            doLongPressOneFinger(event);
        } else {
            doLongLongPressOneFinger(event);
        }
        return this.mResponeLongLongTouch;
    }

    private boolean dispatchTouchEventDoubleFingers(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            this.mTouchedTime = System.currentTimeMillis();
            this.mTouchedPoints.clear();
            this.mTouchedRawPoints.clear();
            this.mHasDoneLongTouch = false;
            this.mResponeLongTouch = true;
            this.mResponeLongLongTouch = false;
            this.mTouchDownRestricted = false;
            addTouchedPoint(event);
        } else if (event.getPointerCount() == 2 && event.getActionMasked() == 5) {
            if (System.currentTimeMillis() - this.mTouchedTime > sFingerDownThreshold) {
                return false;
            }
            addTouchedPoint(event);
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime - sFingerDownThreshold);
            this.mView.postDelayed(this.mCheckRestrictTouchRunnable, sCheckTouchDownDelayTime - sFingerDownThreshold);
        } else if (!this.mResponeLongTouch) {
            return false;
        }
        if (!this.mHasDoneLongTouch) {
            doLongPressDoubleFingers(event);
        } else {
            doLongLongPressDoubleFingers(event);
        }
        return this.mResponeLongLongTouch;
    }

    private void addTouchedPoint(MotionEvent event) {
        int mTouchPointIndex = event.getActionIndex();
        int pointId = event.getPointerId(mTouchPointIndex);
        int pointIndex = event.findPointerIndex(pointId);
        float mTouchX = event.getX(pointIndex);
        float mTouchY = event.getY(pointIndex);
        float mTouchRawX = event.getRawX(pointIndex);
        float mTouchRawY = event.getRawY(pointIndex);
        Point touchedPoint = new Point(mTouchX, mTouchY);
        Point touchedRawPoint = new Point(mTouchRawX, mTouchRawY);
        this.mTouchedPoints.add(touchedPoint);
        this.mTouchedRawPoints.add(touchedRawPoint);
    }

    private boolean checkTouchedPointIsMoved(MotionEvent event) {
        boolean isMoved = false;
        for (int i = 0; i < this.mTouchedPoints.size() && i < event.getPointerCount(); i++) {
            float mTouchX = this.mTouchedPoints.get(i).f3087x;
            float mTouchY = this.mTouchedPoints.get(i).f3088y;
            boolean z = false;
            boolean isPointMoved = Math.abs(event.getX(i) - mTouchX) > ((float) sTouchMoveMaxPixel) || Math.abs(event.getY(i) - mTouchY) > ((float) sTouchMoveMaxPixel);
            if (isMoved || isPointMoved) {
                z = true;
            }
            isMoved = z;
        }
        return isMoved;
    }

    private void doLongPressOneFinger(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 1:
            case 3:
            case 5:
                this.mView.removeCallbacks(this.mLongTouchRunnable);
                this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                break;
            case 2:
                if (checkTouchedPointIsMoved(event)) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    break;
                }
                break;
        }
    }

    private void doLongLongPressOneFinger(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 1:
            case 3:
            case 5:
                this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                sendBixbyLongLongCancelEvent();
                break;
            case 2:
                if (checkTouchedPointIsMoved(event)) {
                    this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                    sendBixbyLongLongCancelEvent();
                    break;
                }
                break;
        }
    }

    private void doLongPressDoubleFingers(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 1:
            case 3:
                this.mView.removeCallbacks(this.mLongTouchRunnable);
                this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                break;
            case 2:
                if (checkTouchedPointIsMoved(event)) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    break;
                }
                break;
            case 5:
            case 6:
                if (event.getPointerCount() != 2) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    break;
                }
                break;
        }
    }

    private void doLongLongPressDoubleFingers(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 1:
            case 3:
            case 5:
            case 6:
                this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                sendBixbyLongLongCancelEvent();
                break;
            case 2:
                if (checkTouchedPointIsMoved(event)) {
                    this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                    sendBixbyLongLongCancelEvent();
                    break;
                }
                break;
        }
    }
}
