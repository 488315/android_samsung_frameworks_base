package com.samsung.android.widget;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.secutil.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.internal.policy.DecorContext;
import com.android.internal.policy.DecorView;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SemPressGestureDetector {
    private static final int BIXBY_TOUCH_DETECTOR_VERSION = 3;
    private static final String CALL_METHOD = "send_bixby_touch_event";
    private static final String CALL_REFLECT_METHOD = "bixby_touch_reflect_widget";
    private static final int DEFAULT_CHECK_TOUCH_DOWN_DELAY_TIME = 100;
    private static final int DEFAULT_FINGER_DOWN_THRESHOLD = 100;
    private static final int DEFAULT_LONG_LONG_PRESS_TIME = 1500;
    private static final int DEFAULT_LONG_PRESS_TIME = 500;
    private static final int DOUBLE_FINGER_TOUCH_MODE = 2;
    private static final String FLAG_WEB_SUMMARY_HTML_FILE = "/web_summary_html_data";
    private static final String KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD =
            "bixbytouch_finger_down_threshold";
    private static final String KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER =
            "bixbytouch_finger_touch_mode";
    private static final String KEY_BIXBYTOUCH_LONG_PRESS_TIME = "bixbytouch_long_press_timeout";
    private static final String KEY_BIXBYTOUCH_VERSION = "key_bixbytouch_version";
    private static final String KEY_CHECK_FP_DELAY_TIME = "check_touch_down_delay_time";
    private static final String KEY_LONG_LONG_PRESS_TIME = "long_long_press_timeout";
    private static final String KEY_TOUCHED_VIEW_TYPE = "key_touched_view_type";
    private static final int LONG_CLICKED_BIXBY = 1;
    private static final int LONG_LONG_CANCEL_BIXBY = 3;
    private static final int LONG_LONG_CLICKED_BIXBY = 2;
    private static final int OBTAIN_HTML_DATA_FLAG = 5;
    private static final String PERMISSION_WRITE_SECURE_SETTINGS =
            "android.permission.WRITE_SECURE_SETTINGS";
    private static final int REFLECT_FIELD_LEVEL_SELF = 0;
    private static final int REFLECT_FIELD_LEVEL_SUPER = 1;
    private static final int REFLECT_FIELD_LEVEL_SUPER_SUPER = 2;
    private static final int REFLECT_MAX_COUNT = 3;
    private static final int SINGLE_FINGER_TOUCH_MODE = 1;
    private static final long SUPPORT_DOUBLE_FINGER_MODE_MIN_VERSION = 300000000;
    private static final String TAEGET_PKG_NAME = "com.samsung.android.bixbytouch";
    private static final String TAG = "SemPressGestureDetector";
    private static final float TOUCH_MOVE_MAX_MM = 3.0f;
    private static final int VIEW_TYPE_IMAGE_VIEW = 1;
    private static final int VIEW_TYPE_TEXT_VIEW = 2;
    private Rect mAppBounds;
    private Rect mBounds;
    private BroadcastReceiver mBroadcastReceiver;
    private Context mContext;
    private Rect mDecorViewBounds;
    private Insets mDisplayCutoutInsets;
    private boolean mFindViewRestricted;
    private Rect mMaxBounds;
    private Insets mNavigationBarsInsets;
    private String mRegisterBroadcastActivityName;
    private SemOneTouchApi mSemOneTouchApi;
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
    private static ConcurrentHashMap<String, List<Long>> mRegisteredActivityMap =
            new ConcurrentHashMap<>();
    private static long sRequestCode = 0;
    private long mRegisterBroadcastTime = 0;
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
    private Integer mWindowType = null;
    private Runnable mLongLongTouchRunnable =
            new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.secD(
                            SemPressGestureDetector.TAG,
                            "mLongLongTouchRunnable: "
                                    + SemPressGestureDetector.this.mCallerPackage
                                    + ","
                                    + SemPressGestureDetector.this.mActivityName
                                    + ","
                                    + SemPressGestureDetector.this.mProcessName);
                    SemPressGestureDetector.this.mResponeLongLongTouch =
                            SemPressGestureDetector.this.sendBixbyLongClickedEvent(2);
                }
            };
    private Runnable mLongTouchRunnable =
            new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.2
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
                    Log.secD(
                            SemPressGestureDetector.TAG,
                            "mLongTouchRunnable: "
                                    + SemPressGestureDetector.this.mCallerPackage
                                    + ","
                                    + SemPressGestureDetector.this.mActivityName
                                    + ","
                                    + SemPressGestureDetector.this.mProcessName);
                    if (SemPressGestureDetector.this.mView != null) {
                        SemPressGestureDetector.this.mTouchedViews =
                                SemPressGestureDetector.this.getTouchedViews();
                        SemPressGestureDetector.this.mResponeLongTouch =
                                SemPressGestureDetector.this.sendBixbyLongClickedEvent(1);
                        if (SemPressGestureDetector.this.mResponeLongTouch) {
                            SemPressGestureDetector.this.mView.postDelayed(
                                    SemPressGestureDetector.this.mLongLongTouchRunnable,
                                    SemPressGestureDetector.sLongLongPressTime);
                        }
                    }
                }
            };
    private Runnable mCheckRestrictTouchRunnable =
            new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.3
                @Override // java.lang.Runnable
                public void run() {
                    SemPressGestureDetector.this.mTouchDownRestricted =
                            SemPressGestureDetector.this.isFingerPrintInDisplay();
                }
            };

    private static class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public ArrayList<View> getTouchedViews() {
        ArrayList<View> mTouchedViews = new ArrayList<>();
        try {
            if (sCurrentTouchMode == 2) {
                if (this.mTouchedRawPoints.size() == 2) {
                    float mTouchedX0 = this.mTouchedPoints.get(0).x;
                    float mTouchedY0 = this.mTouchedPoints.get(0).y;
                    float mTouchedX1 = this.mTouchedPoints.get(1).x;
                    float mTouchedY1 = this.mTouchedPoints.get(1).y;
                    View view0 = this.mView.semDispatchFindView(mTouchedX0, mTouchedY0, true);
                    View view1 = this.mView.semDispatchFindView(mTouchedX1, mTouchedY1, true);
                    Log.secD(
                            TAG,
                            "getTouchedViews: mTouchedPoints: "
                                    + this.mTouchedPoints.get(0)
                                    + " view0: "
                                    + view0
                                    + " mTouchedX0: "
                                    + mTouchedX0
                                    + " mTouchedY0: "
                                    + mTouchedY0);
                    if (view0 != null) {
                        mTouchedViews.add(view0);
                    }
                    if (view1 != null) {
                        mTouchedViews.add(view1);
                    }
                }
            } else if (this.mTouchedRawPoints.size() == 1) {
                float mTouchedX = this.mTouchedPoints.get(0).x;
                float mTouchedY = this.mTouchedPoints.get(0).y;
                View mTouchedView = this.mView.semDispatchFindView(mTouchedX, mTouchedY, true);
                Log.secD(
                        TAG,
                        "getTouchedViews: mTouchedPoints: "
                                + this.mTouchedPoints.get(0)
                                + " mTouchedView: "
                                + mTouchedView
                                + " mTouchedX: "
                                + mTouchedX
                                + " mTouchedY: "
                                + mTouchedY);
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
        this.mSemOneTouchApi = null;
        init(context, view);
        if (SemOneTouchApi.isOneTouchSupported()) {
            this.mSemOneTouchApi = new SemOneTouchApi(context, this.mView);
        }
    }

    private static long getTouchedAppVersionCode(Context context, String packageName) {
        if (sVersionCode < 0 || (packageName != null && !packageName.equals(sPreviousPackage))) {
            sVersionCode = getAppVersionCode(context, packageName);
            sPreviousPackage = packageName;
        }
        return sVersionCode;
    }

    private static long getAppVersionCode(Context context, String packageName) {
        try {
            long versionCode =
                    context.getPackageManager().getPackageInfo(packageName, 0).getLongVersionCode();
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            Log.secE(TAG, "bitmapToBase64 failed: bitmap is null");
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encodeToString = Base64.encodeToString(byteArray, 0);
                byteArrayOutputStream.close();
                return encodeToString;
            } finally {
            }
        } catch (IOException e) {
            Log.secE(TAG, "bitmapToBase64 failed: " + e.getMessage());
            return null;
        }
    }

    private static Bitmap drawable2Bitmap(Drawable drawable) {
        try {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState == null) {
                Log.secE(TAG, "drawable.getConstantState failed");
                return null;
            }
            Drawable copiedDrawable = constantState.newDrawable().mutate();
            int width = copiedDrawable.getIntrinsicWidth();
            int height = copiedDrawable.getIntrinsicHeight();
            if (width <= 0 || height <= 0) {
                width = copiedDrawable.getBounds().width();
                height = copiedDrawable.getBounds().height();
            }
            if (width > 0 && height > 0) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                copiedDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                copiedDrawable.draw(canvas);
                Log.secD(TAG, "copiedDrawable success");
                return bitmap;
            }
            Log.secE(TAG, "drawable2Bitmap fail");
            return null;
        } catch (Exception e) {
            Log.secE(TAG, "drawable2Bitmap fail: " + e.getMessage(), e);
            return null;
        }
    }

    private static String getImageBase64FromView(View view, Object reflectedObject) {
        try {
            if (reflectedObject instanceof Bitmap) {
                Bitmap reflectedBitmap = (Bitmap) reflectedObject;
                Log.secD(TAG, "reflectedBitmap " + reflectedObject);
                Bitmap copiedBitmap = reflectedBitmap.copy(reflectedBitmap.getConfig(), true);
                return bitmapToBase64(copiedBitmap);
            }
            if (reflectedObject instanceof Drawable) {
                Drawable reflectedDrawable = (Drawable) reflectedObject;
                Log.secD(TAG, "reflectedDrawable " + reflectedDrawable);
                return bitmapToBase64(drawable2Bitmap(reflectedDrawable));
            }
            Log.secD(TAG, "reflectedDrawable draw: " + reflectedObject);
            Bitmap bitmap =
                    Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmapToBase64(bitmap);
        } catch (Exception e) {
            Log.secE(TAG, "getImageBase64FromView failed: " + e.getMessage());
            return null;
        }
    }

    public static String getText(Context context, String packageName, View view) {
        Object charSequence;
        try {
            sWidgetNameList.add(view.getClass().getName());
            if (view.getId() != -1) {
                sWidgetIdList.add(context.getResources().getResourceName(view.getId()));
            }
        } catch (Exception e) {
        }
        try {
        } catch (Exception e2) {
            e = e2;
        }
        if (sHasCallReflectCount >= 3) {
            return null;
        }
        sHasCallReflectCount++;
        Class clazz = view.getClass();
        Bundle bundle = new Bundle();
        long versionCode = getTouchedAppVersionCode(context, packageName);
        bundle.putLong("request_code", sRequestCode);
        try {
            bundle.putString("caller_package", packageName);
            bundle.putString("caller_class", clazz.getName());
            bundle.putLong("caller_version_code", versionCode);
            Bundle result =
                    context.getContentResolver()
                            .call(BIXBY_TOUCH_URI, CALL_REFLECT_METHOD, (String) null, bundle);
            if (result != null) {
                String fieldName = result.getString("reflect_field_name");
                String methodName = result.getString("reflect_method_name");
                int fieldLevel = result.getInt("reflect_field_level");
                int viewType = result.getInt(KEY_TOUCHED_VIEW_TYPE, -1);
                if (fieldName != null) {
                    Log.secD(
                            TAG,
                            "getText: fieldName: "
                                    + fieldName
                                    + " methodName: "
                                    + methodName
                                    + " fieldLevel: "
                                    + fieldLevel
                                    + " viewType: "
                                    + viewType);
                    Field field = null;
                    switch (fieldLevel) {
                        case 0:
                            field = clazz.getDeclaredField(fieldName);
                            break;
                        case 1:
                            field = clazz.getSuperclass().getDeclaredField(fieldName);
                            break;
                        case 2:
                            field =
                                    clazz.getSuperclass()
                                            .getSuperclass()
                                            .getDeclaredField(fieldName);
                            break;
                    }
                    if (field != null) {
                        field.setAccessible(true);
                        Object reflectedObject = field.get(view);
                        if (viewType == 1) {
                            return getImageBase64FromView(view, reflectedObject);
                        }
                        if (reflectedObject != null) {
                            return reflectedObject.toString();
                        }
                    }
                } else {
                    Method method = clazz.getMethod(methodName, new Class[0]);
                    if (method != null
                            && (charSequence = method.invoke(view, new Object[0])) != null) {
                        return charSequence.toString();
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
        return null;
    }

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
                        this.mWindowConfig =
                                this.mView
                                        .getResources()
                                        .getConfiguration()
                                        .windowConfiguration
                                        .toString();
                        this.mWindowingMode =
                                this.mView
                                        .getResources()
                                        .getConfiguration()
                                        .windowConfiguration
                                        .getWindowingMode();
                    }
                    this.mAppBounds =
                            activity.getWindow()
                                    .getWindowManager()
                                    .getCurrentWindowMetrics()
                                    .getBounds();
                    this.mMaxBounds =
                            activity.getWindow()
                                    .getWindowManager()
                                    .getMaximumWindowMetrics()
                                    .getBounds();
                    this.mBounds = this.mAppBounds;
                    int[] location = new int[2];
                    View view = activity.getWindow().getDecorView();
                    view.getLocationOnScreen(location);
                    this.mDecorViewBounds =
                            new Rect(
                                    location[0],
                                    location[1],
                                    location[0] + view.getWidth(),
                                    location[1] + view.getHeight());
                    Log.secD(
                            TAG,
                            "parseInfoFromView: mDecorViewBounds = "
                                    + this.mDecorViewBounds.toString());
                    try {
                        this.mDisplayCutoutInsets =
                                activity.getWindow()
                                        .getWindowManager()
                                        .getCurrentWindowMetrics()
                                        .getWindowInsets()
                                        .getInsets(WindowInsets.Type.displayCutout());
                        this.mNavigationBarsInsets =
                                activity.getWindow()
                                        .getWindowManager()
                                        .getCurrentWindowMetrics()
                                        .getWindowInsets()
                                        .getInsets(WindowInsets.Type.navigationBars());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                this.mWindowConfig =
                        this.mView.getResources().getConfiguration().windowConfiguration.toString();
                this.mAppBounds =
                        this.mView
                                .getResources()
                                .getConfiguration()
                                .windowConfiguration
                                .getAppBounds();
                this.mWindowingMode =
                        this.mView
                                .getResources()
                                .getConfiguration()
                                .windowConfiguration
                                .getWindowingMode();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private Activity parseActivity() {
        Activity activity = null;
        Context baseContext = this.mContext;
        if (this.mContext instanceof Activity) {
            activity = (Activity) this.mContext;
        } else if (this.mContext instanceof DecorContext) {
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

    public boolean isFingerPrintInDisplay() {
        if (!sHasFingerPrintFeature) {
            return false;
        }
        try {
            FingerprintManager fpm =
                    (FingerprintManager)
                            this.mContext.getSystemService(Context.FINGERPRINT_SERVICE);
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
        if (this.mSemOneTouchApi != null) {
            this.mSemOneTouchApi.updateSettingsValue(this.mContext);
        }
        if (this.mFindViewRestricted) {
            return;
        }
        sBixbyTouchEnable = bixbyTouchEnable;
        if (sBixbyTouchEnable && !isInitFailed()) {
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.widget.SemPressGestureDetector.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    SemPressGestureDetector.this.initSetting();
                                    SemPressGestureDetector.this.initWebSummary();
                                }
                            })
                    .start();
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
        this.mCallerPackage = this.mContext.getPackageName();
        this.mProcessName = this.mContext.getApplicationInfo().processName;
        sTouchMoveMaxPixel = (int) mm2px(3.0f);
        sHasFingerPrintFeature = hasFingerPrintFeature();
        WindowManager.LayoutParams wparams =
                (WindowManager.LayoutParams) this.mView.getLayoutParams();
        if (wparams != null) {
            this.mWindowType = Integer.valueOf(wparams.type);
            if (this.mWindowType.intValue() < 2000) {
                z = false;
            }
            this.mFindViewRestricted = z;
            if (this.mFindViewRestricted) {
                return;
            }
        }
        initOnChild();
    }

    public boolean isInitFailed() {
        return this.mInitFailed;
    }

    public boolean hasFingerPrintFeature() {
        PackageManager packageManager = this.mContext.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    public boolean sendBixbyLongClickedEvent(int flag) {
        if (this.mDetachedFromWindow
                || this.mActivityName == null
                || this.mActivityName.startsWith(TAEGET_PKG_NAME)) {
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
                    bundle.putInt(
                            "window_decor_view_bounds_left", topActivityWindowDecorViewBounds.left);
                    bundle.putInt(
                            "window_decor_view_bounds_top", topActivityWindowDecorViewBounds.top);
                    bundle.putInt(
                            "window_decor_view_bounds_right",
                            topActivityWindowDecorViewBounds.right);
                    bundle.putInt(
                            "window_decor_view_bounds_bottom",
                            topActivityWindowDecorViewBounds.bottom);
                }
                if (this.mDisplayCutoutInsets != null) {
                    bundle.putInt("display_cutout_insets_left", this.mDisplayCutoutInsets.left);
                    bundle.putInt("display_cutout_insets_top", this.mDisplayCutoutInsets.top);
                    bundle.putInt("display_cutout_insets_right", this.mDisplayCutoutInsets.right);
                    bundle.putInt("display_cutout_insets_bottom", this.mDisplayCutoutInsets.bottom);
                }
                if (this.mNavigationBarsInsets != null) {
                    bundle.putInt("navigation_bars_insets_left", this.mNavigationBarsInsets.left);
                    bundle.putInt("navigation_bars_insets_top", this.mNavigationBarsInsets.top);
                    bundle.putInt("navigation_bars_insets_right", this.mNavigationBarsInsets.right);
                    bundle.putInt(
                            "navigation_bars_insets_bottom", this.mNavigationBarsInsets.bottom);
                }
                if (sCurrentTouchMode != 2) {
                    if (this.mTouchedRawPoints.size() == 1) {
                        bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).x);
                        bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).y);
                    }
                } else if (this.mTouchedRawPoints.size() == 2) {
                    bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).x);
                    bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).y);
                    bundle.putFloat("touch_raw_start_x2", this.mTouchedRawPoints.get(1).x);
                    bundle.putFloat("touch_raw_start_y2", this.mTouchedRawPoints.get(1).y);
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
                            bundle.putBundle(
                                    key, putTouchedViewInfoToBundle(subBundle, mTouchedView2));
                        }
                    }
                }
            }
            Bundle result =
                    this.mContext
                            .getContentResolver()
                            .call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
            if (result != null) {
                if (flag == 1) {
                    long version = result.getLong(KEY_BIXBYTOUCH_VERSION, 0L);
                    if (version != 0) {
                        this.mBixbyTouchVersion = version;
                    }
                    sCurrentTouchMode =
                            result.getInt(KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, sCurrentTouchMode);
                    sLongPressTime = result.getInt(KEY_BIXBYTOUCH_LONG_PRESS_TIME, sLongPressTime);
                    sLongLongPressTime =
                            result.getInt(KEY_LONG_LONG_PRESS_TIME, sLongLongPressTime);
                    sCheckTouchDownDelayTime =
                            result.getInt(KEY_CHECK_FP_DELAY_TIME, sCheckTouchDownDelayTime);
                    sFingerDownThreshold =
                            result.getInt(
                                    KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD, sFingerDownThreshold);
                }
                return result.getBoolean("bixby_touch_response", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean isBase64ImageString(String input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        return Boolean.valueOf(input.startsWith("/9j/4AAQSkZJRgABAQAAAQABAAD"));
    }

    public Bundle putTouchedViewInfoToBundle(Bundle bundle, View touchedView) {
        if (touchedView != null) {
            try {
                if (touchedView instanceof TextView) {
                    bundle.putInt("input_type", ((TextView) touchedView).getInputType());
                }
                String foundText = touchedView.semGetBixbyTouchFoundText();
                boolean isTouchedImageView = isBase64ImageString(foundText).booleanValue();
                if (isTouchedImageView) {
                    touchedView.semSetBixbyTouchFoundText(null);
                } else {
                    bundle.putString(
                            "bixby_touch_find_text", touchedView.semGetBixbyTouchFoundText());
                }
                bundle.putString("found_widget_name", touchedView.getClass().getName());
                if (touchedView.getId() != -1) {
                    bundle.putString(
                            "found_widget_id",
                            this.mContext.getResources().getResourceName(touchedView.getId()));
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
            this.mContext
                    .getContentResolver()
                    .call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDetached() {
        if (this.mSemOneTouchApi != null) {
            this.mSemOneTouchApi.onCleared(this.mContext);
        }
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
        if (this.mContext != null && this.mBroadcastReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                removeRegisterBroadcastActivityTime();
            } catch (Exception e) {
                Log.secD(TAG, "unregisterReceiver:" + e.getMessage());
            }
            this.mBroadcastReceiver = null;
        }
    }

    private boolean matchPackage(String pkgName) {
        return pkgName.equals(this.mCallerPackage);
    }

    private float mm2px(float mm) {
        int dpi = this.mContext.getResources().getDisplayMetrics().densityDpi;
        return (mm / 25.4f) * dpi;
    }

    public void checkBlockApp() {
        boolean z;
        try {
            if (!isLauncherApp() && !matchPackage(TAEGET_PKG_NAME)) {
                z = false;
                this.mFindViewRestricted = z;
            }
            z = true;
            this.mFindViewRestricted = z;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLauncherApp() {
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
        boolean result;
        if (event.getActionMasked() == 0) {
            Log.secD(
                    TAG,
                    "dispatchTouchEvent:"
                            + this.mProcessName
                            + ",sBixbyTouchEnable="
                            + sBixbyTouchEnable
                            + ",mFindViewRestricted="
                            + this.mFindViewRestricted);
            sHasCallReflectCount = 0;
        }
        boolean oneTouchResult = false;
        if (this.mSemOneTouchApi != null
                && (oneTouchResult =
                        this.mSemOneTouchApi.dispatchTouchEvent(
                                this.mContext, event, this.mView))) {
            return true;
        }
        if (!sBixbyTouchEnable || this.mFindViewRestricted || this.mDetachedFromWindow) {
            return false;
        }
        if (event.getActionMasked() == 0) {
            resetFlags(event);
        }
        if (sCurrentTouchMode == 2) {
            result = dispatchTouchEventDoubleFingers(event);
        } else {
            result = dispatchTouchEventOneFinger(event);
        }
        return oneTouchResult || result;
    }

    private void resetFlags(MotionEvent event) {
        this.mTouchedPoints.clear();
        this.mTouchedRawPoints.clear();
        this.mHasDoneLongTouch = false;
        this.mResponeLongTouch = true;
        this.mResponeLongLongTouch = false;
        this.mTouchDownRestricted = false;
        this.mTouchedTime = System.currentTimeMillis();
        addTouchedPoint(event);
    }

    private boolean dispatchTouchEventOneFinger(MotionEvent event) {
        if (event.getActionMasked() == 0) {
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime);
            this.mView.postDelayed(this.mCheckRestrictTouchRunnable, sCheckTouchDownDelayTime);
        } else {
            if (event.getActionMasked() == 1) {
                Log.secD(
                        TAG,
                        "mResponeLongTouch="
                                + this.mResponeLongTouch
                                + ",mResponeLongLongTouch="
                                + this.mResponeLongLongTouch);
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
        if (event.getPointerCount() == 2 && event.getActionMasked() == 5) {
            if (System.currentTimeMillis() - this.mTouchedTime > sFingerDownThreshold) {
                return false;
            }
            addTouchedPoint(event);
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime - sFingerDownThreshold);
            this.mView.postDelayed(
                    this.mCheckRestrictTouchRunnable,
                    sCheckTouchDownDelayTime - sFingerDownThreshold);
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
            float mTouchX = this.mTouchedPoints.get(i).x;
            float mTouchY = this.mTouchedPoints.get(i).y;
            boolean z = false;
            boolean isPointMoved =
                    Math.abs(event.getX(i) - mTouchX) > ((float) sTouchMoveMaxPixel)
                            || Math.abs(event.getY(i) - mTouchY) > ((float) sTouchMoveMaxPixel);
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

    public void initSetting() {
        try {
            long version = getAppVersionCode(this.mContext, TAEGET_PKG_NAME);
            sCurrentTouchMode =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(),
                            KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER,
                            SemOneTouchApi.isOneTouchSupported() ? 2 : 1);
            sLongPressTime =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(),
                            KEY_BIXBYTOUCH_LONG_PRESS_TIME,
                            500);
            sLongLongPressTime =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(), KEY_LONG_LONG_PRESS_TIME, 1500);
            sCheckTouchDownDelayTime =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(), KEY_CHECK_FP_DELAY_TIME, 100);
            sFingerDownThreshold =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(),
                            KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD,
                            100);
            this.mBixbyTouchVersion = version;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initWebSummary() {
        if (!this.mFindViewRestricted && (this.mView instanceof DecorView)) {
            try {
                registerWebSummaryBroadcast();
            } catch (Exception e) {
                Log.secD(TAG, "init : " + e.getMessage(), e);
            }
        }
    }

    private void initOnChild() {
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.widget.SemPressGestureDetector.5
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    SemPressGestureDetector.this.checkBlockApp();
                                } catch (Exception e) {
                                    Log.secD(
                                            SemPressGestureDetector.TAG,
                                            "initOnChild:" + e.getMessage());
                                }
                            }
                        })
                .start();
    }

    private void registerWebSummaryBroadcast() {
        if (this.mContext != null) {
            IntentFilter intentFilter = new IntentFilter();
            Activity activity = parseActivity();
            if (activity == null || this.mBroadcastReceiver != null) {
                return;
            }
            String targetActivity =
                    Settings.Global.getString(
                            this.mContext.getContentResolver(), "web_summary_activity");
            if (TextUtils.isEmpty(targetActivity)) {
                return;
            }
            int hash = activity.getClass().getName().hashCode();
            String targetHex =
                    Integer.toHexString(hash)
                            .substring(0, Math.min(10, Integer.toHexString(hash).length()));
            if (!targetActivity.contains(targetHex)) {
                return;
            }
            this.mBroadcastReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.widget.SemPressGestureDetector.6
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            try {
                                String action = intent.getAction();
                                if (!TextUtils.isEmpty(action)) {
                                    Log.secD(
                                            SemPressGestureDetector.TAG,
                                            ">" + action.replace(".START_PARSE", ""));
                                }
                                long maxTime =
                                        SemPressGestureDetector.this.getMaxCreateTimeForClass();
                                if (maxTime == SemPressGestureDetector.this.mRegisterBroadcastTime
                                        && maxTime != 0) {
                                    SemPressGestureDetector.this.startObtainWebViewData(intent);
                                }
                            } catch (Exception e) {
                                Log.secD(
                                        SemPressGestureDetector.TAG,
                                        "receive : " + e.getMessage(),
                                        e);
                            }
                        }
                    };
            String action = activity.getClass().getName();
            addRegisterBroadcastActivity(action);
            Log.secD(TAG, "<" + action);
            intentFilter.addAction(action + ".START_PARSE");
            this.mContext.registerReceiver(
                    this.mBroadcastReceiver,
                    intentFilter,
                    Manifest.permission.MANAGE_ACTIVITY_TASKS,
                    null,
                    2);
        }
    }

    public void addRegisterBroadcastActivity(String className) {
        try {
            this.mRegisterBroadcastActivityName = className;
            mRegisteredActivityMap.putIfAbsent(
                    this.mRegisterBroadcastActivityName, new ArrayList());
            this.mRegisterBroadcastTime = SystemClock.elapsedRealtime();
            mRegisteredActivityMap
                    .get(this.mRegisterBroadcastActivityName)
                    .add(Long.valueOf(this.mRegisterBroadcastTime));
        } catch (Exception e) {
            Log.secD(TAG, "addRegisterBroadcastActivity: " + e.getMessage(), e);
        }
    }

    public long getMaxCreateTimeForClass() {
        if (!mRegisteredActivityMap.containsKey(this.mRegisterBroadcastActivityName)) {
            return 0L;
        }
        List<Long> times = mRegisteredActivityMap.get(this.mRegisterBroadcastActivityName);
        if (times.isEmpty()) {
            return 0L;
        }
        long maxTime = times.get(0).longValue();
        Iterator<Long> it = times.iterator();
        while (it.hasNext()) {
            long time = it.next().longValue();
            if (time > maxTime) {
                maxTime = time;
            }
        }
        return maxTime;
    }

    public void removeRegisterBroadcastActivityTime() {
        try {
            if (!TextUtils.isEmpty(this.mRegisterBroadcastActivityName)
                    && mRegisteredActivityMap.containsKey(this.mRegisterBroadcastActivityName)) {
                List<Long> times = mRegisteredActivityMap.get(this.mRegisterBroadcastActivityName);
                if (!times.isEmpty()) {
                    for (int i = 0; i < times.size(); i++) {
                        if (times.get(i).longValue() == this.mRegisterBroadcastTime) {
                            times.remove(i);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.secD(TAG, "removeRegisterBroadcastActivity: " + e.getMessage(), e);
        }
    }

    public void startObtainWebViewData(Intent intent) {
        parseWebView(intent);
    }

    private View findWebView(ViewGroup viewGroup, String webClassName, boolean topDownFindView) {
        View webView = null;
        int childCount = viewGroup.getChildCount();
        if (topDownFindView) {
            for (int i = childCount - 1; i >= 0; i--) {
                View child = viewGroup.getChildAt(i);
                if ((child instanceof WebView) || child.getClass().getName().equals(webClassName)) {
                    return child;
                }
                if ((child instanceof ViewGroup)
                        && (webView = findWebView((ViewGroup) child, webClassName, true)) != null) {
                    return webView;
                }
            }
            return webView;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View child2 = viewGroup.getChildAt(i2);
            if ((child2 instanceof WebView) || child2.getClass().getName().equals(webClassName)) {
                return child2;
            }
            if ((child2 instanceof ViewGroup)
                    && (webView = findWebView((ViewGroup) child2, webClassName, false)) != null) {
                return webView;
            }
        }
        return webView;
    }

    private void parseWebView(Intent intent) {
        if (this.mView != null) {
            final String requestCode = intent.getStringExtra("request_code");
            String webviewClassName = intent.getStringExtra("webview_class_name");
            boolean topDownFindView = intent.getBooleanExtra("top_down_find_view", false);
            boolean onlyBody = intent.getBooleanExtra("request_body", false);
            if (TextUtils.isEmpty(webviewClassName)) {
                webviewClassName = WebView.class.getName();
            }
            View rootView = this.mView;
            View targetView = null;
            if (rootView instanceof ViewGroup) {
                targetView = findWebView((ViewGroup) rootView, webviewClassName, topDownFindView);
            }
            if (targetView == null) {
                sendHtmlData("", requestCode);
                return;
            }
            boolean needInvoke = intent.getBooleanExtra("invoke_method", false);
            if (needInvoke) {
                String methodName = intent.getStringExtra("method_name");
                targetView = invokeMethodGetView(targetView, methodName);
            }
            if (targetView == null) {
                sendHtmlData("", requestCode);
                return;
            }
            ValueCallback<String> valueCallback =
                    new ValueCallback<
                            String>() { // from class:
                                        // com.samsung.android.widget.SemPressGestureDetector.7
                        @Override // android.webkit.ValueCallback
                        public void onReceiveValue(String value) {
                            SemPressGestureDetector.this.sendHtmlData(value, requestCode);
                        }
                    };
            if (targetView instanceof WebView) {
                evaluateHtmlData((WebView) targetView, onlyBody, valueCallback);
            } else {
                invokeHtmlData(targetView, onlyBody, requestCode, valueCallback);
            }
        }
    }

    private View invokeMethodGetView(View targetView, String methodName) {
        Class<?> webViewClass = targetView.getClass();
        try {
            Method invokeMethod = webViewClass.getMethod(methodName, new Class[0]);
            Object methodObject = invokeMethod.invoke(targetView, new Object[0]);
            if (methodObject instanceof View) {
                return (View) methodObject;
            }
            return null;
        } catch (Exception e) {
            Log.secD(TAG, "invoke 2: " + e.getMessage(), e);
            return null;
        }
    }

    private void evaluateHtmlData(
            WebView targetView, boolean onlyBody, ValueCallback valueCallback) {
        targetView.evaluateJavascript(getRule(onlyBody), valueCallback);
    }

    private void invokeHtmlData(
            View targetView, boolean onlyBody, String requestCode, ValueCallback valueCallback) {
        Class<?> webViewClass = targetView.getClass();
        try {
            Method evaluateJavascriptMethod =
                    webViewClass.getMethod("evaluateJavascript", String.class, ValueCallback.class);
            if (evaluateJavascriptMethod != null) {
                evaluateJavascriptMethod.invoke(targetView, getRule(onlyBody), valueCallback);
            }
        } catch (Exception e) {
            sendParseResult("", false, requestCode);
            Log.secD(TAG, "invoke : " + e.getMessage(), e);
        }
    }

    private String getRule(boolean onlyBody) {
        if (onlyBody) {
            return "javascript:document.body.innerHTML";
        }
        return "javascript:document.documentElement.innerHTML";
    }

    public void sendHtmlData(final String htmlData, final String requestCode) {
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.widget.SemPressGestureDetector.8
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    boolean saveFileSuccess =
                                            SemPressGestureDetector.this.saveHtmlDataToFile(
                                                    htmlData);
                                    SemPressGestureDetector.this.sendParseResult(
                                            htmlData, saveFileSuccess, requestCode);
                                } catch (Exception e) {
                                    Log.secD(
                                            SemPressGestureDetector.TAG,
                                            "send fail: " + e.getMessage(),
                                            e);
                                }
                            }
                        })
                .start();
    }

    public boolean saveHtmlDataToFile(String htmlData) {
        if (TextUtils.isEmpty(htmlData)) {
            return false;
        }
        CancellationSignal cancellationSignal = new CancellationSignal();
        try {
            ParcelFileDescriptor pfd =
                    this.mContext
                            .getContentResolver()
                            .openFile(
                                    Uri.parse(
                                            "content://com.samsung.android.bixbytouch/web_summary_html_data"),
                                    String.valueOf(805306368),
                                    cancellationSignal);
            try {
                if (pfd == null) {
                    Log.secD(TAG, "open fail");
                    if (pfd != null) {
                        pfd.close();
                    }
                    return false;
                }
                FileWriter writer = new FileWriter(pfd.getFileDescriptor());
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    try {
                        bufferedWriter.write(htmlData);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        writer.close();
                        if (pfd != null) {
                            pfd.close();
                            return true;
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.secD(TAG, "save fail: " + e.getMessage(), e);
            return false;
        }
    }

    public void sendParseResult(String htmlData, boolean saveFileResult, String requestCode) {
        Bundle bundle = new Bundle();
        bundle.putString("request_code", requestCode);
        bundle.putInt("bixby_touch_flag", 5);
        bundle.putBoolean("result", saveFileResult);
        bundle.putBoolean("web_activity", !TextUtils.isEmpty(htmlData));
        this.mContext
                .getContentResolver()
                .call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
    }
}
