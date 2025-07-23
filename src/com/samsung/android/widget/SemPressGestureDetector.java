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
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.secutil.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.policy.DecorContext;
import com.android.internal.policy.DecorView;
import com.samsung.android.emergencymode.SemEmergencyConstants;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class SemPressGestureDetector implements ISemTouchApi {
    private static final int BIXBY_TOUCH_DETECTOR_VERSION = 3;
    private static final int BIXBY_TOUCH_FOUND_TEXT_MAX_LENGTH = 500;
    private static final String CALL_METHOD = "send_bixby_touch_event";
    private static final String CALL_REFLECT_METHOD = "bixby_touch_reflect_widget";
    private static final int DEFAULT_CHECK_TOUCH_DOWN_DELAY_TIME = 100;
    private static final int DEFAULT_FINGER_DOWN_THRESHOLD = 100;
    private static final int DEFAULT_LONG_LONG_PRESS_TIME = 1500;
    private static final int DEFAULT_LONG_PRESS_TIME = 500;
    private static final int DOUBLE_FINGER_TOUCH_MODE = 2;
    private static final String FLAG_WEB_SUMMARY_HTML_FILE = "/web_summary_html_data";
    private static final String KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD = "bixbytouch_finger_down_threshold";
    private static final String KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER = "bixbytouch_finger_touch_mode";
    private static final String KEY_BIXBYTOUCH_LONG_PRESS_TIME = "bixbytouch_long_press_timeout";
    private static final String KEY_BIXBYTOUCH_VERSION = "key_bixbytouch_version";
    private static final String KEY_CHECK_FP_DELAY_TIME = "check_touch_down_delay_time";
    private static final String KEY_LONG_LONG_PRESS_TIME = "long_long_press_timeout";
    private static final int LONG_CLICKED_BIXBY = 1;
    private static final int LONG_LONG_CANCEL_BIXBY = 3;
    private static final int LONG_LONG_CLICKED_BIXBY = 2;
    private static final int OBTAIN_HTML_DATA_FLAG = 5;
    private static final int OBTAIN_VIEW_LOCATION_FLAG = 6;
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
    private String mBixbyTouchFoundText;
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
    private static ConcurrentHashMap<String, List<Long>> mRegisteredActivityMap = new ConcurrentHashMap<>();
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

    private static class Point {
        float x;
        float y;

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }

    public ArrayList<View> getTouchedViews() {
        ArrayList<View> arrayList = new ArrayList<>();
        try {
            if (sCurrentTouchMode == 2) {
                if (this.mTouchedRawPoints.size() == 2) {
                    PointF pointF = new PointF(this.mTouchedPoints.get(0).x, this.mTouchedPoints.get(0).y);
                    PointF pointF2 = new PointF(this.mTouchedPoints.get(1).x, this.mTouchedPoints.get(1).y);
                    View semDispatchFindView = this.mView.semDispatchFindView(pointF, true, this);
                    View semDispatchFindView2 = this.mView.semDispatchFindView(pointF2, true, this);
                    Log.secD(TAG, "getTouchedViews: mTouchedPoints: " + this.mTouchedPoints.get(0) + " view0: " + semDispatchFindView + " TouchedPoint0: " + pointF + " TouchedPoint1: " + pointF2);
                    if (semDispatchFindView != null) {
                        arrayList.add(semDispatchFindView);
                    }
                    if (semDispatchFindView2 != null) {
                        arrayList.add(semDispatchFindView2);
                        return arrayList;
                    }
                }
            } else if (this.mTouchedRawPoints.size() == 1) {
                PointF pointF3 = new PointF(this.mTouchedPoints.get(0).x, this.mTouchedPoints.get(0).y);
                View semDispatchFindView3 = this.mView.semDispatchFindView(pointF3, true, this);
                Log.secD(TAG, "getTouchedViews: TouchedPoint: " + pointF3 + " touchedView: " + semDispatchFindView3);
                if (semDispatchFindView3 != null) {
                    arrayList.add(semDispatchFindView3);
                    return arrayList;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public SemPressGestureDetector(Context context, View view) {
        this.mSemOneTouchApi = null;
        init(context, view);
        if (SemOneTouchApi.isOneTouchSupported()) {
            this.mSemOneTouchApi = new SemOneTouchApi(context, this.mView);
        }
    }

    private static long getTouchedAppVersionCode(Context context, String str) {
        if (sVersionCode < 0 || (str != null && !str.equals(sPreviousPackage))) {
            sVersionCode = getAppVersionCode(context, str);
            sPreviousPackage = str;
        }
        return sVersionCode;
    }

    private static long getAppVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private static String getTextFromTextView(TextView textView, PointF pointF, Object obj) {
        if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) obj;
            int offsetForPosition = textView.getOffsetForPosition(pointF.x, pointF.y);
            int length = charSequence.length();
            if (offsetForPosition == length) {
                offsetForPosition = length - 1;
            }
            if (length < 500) {
                return charSequence.toString();
            }
            if (offsetForPosition < 250) {
                return charSequence.subSequence(0, 500).toString();
            }
            return charSequence.subSequence(offsetForPosition - 250, Math.min(length, offsetForPosition + 250)).toString();
        }
        return "";
    }

    public static String getViewContentInternal(Context context, String str, View view, PointF pointF, Object obj) {
        Object invoke;
        Field declaredField;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (view instanceof TextView) {
            return getTextFromTextView((TextView) view, pointF, obj);
        }
        try {
            sWidgetNameList.add(view.getClass().getName());
            if (view.getId() != -1) {
                sWidgetIdList.add(context.getResources().getResourceName(view.getId()));
            }
        } catch (Exception unused) {
        }
        int i = sHasCallReflectCount;
        if (i >= 3) {
            return null;
        }
        sHasCallReflectCount = i + 1;
        Class<?> cls = view.getClass();
        Bundle bundle = new Bundle();
        long touchedAppVersionCode = getTouchedAppVersionCode(context, str);
        bundle.putLong("request_code", sRequestCode);
        bundle.putString("caller_package", str);
        bundle.putString("caller_class", cls.getName());
        bundle.putLong("caller_version_code", touchedAppVersionCode);
        Bundle call = context.getContentResolver().call(BIXBY_TOUCH_URI, CALL_REFLECT_METHOD, (String) null, bundle);
        if (call != null) {
            String string = call.getString("reflect_field_name");
            String string2 = call.getString("reflect_method_name");
            int i2 = call.getInt("reflect_field_level");
            if (string != null) {
                if (i2 == 0) {
                    declaredField = cls.getDeclaredField(string);
                } else if (i2 == 1) {
                    declaredField = cls.getSuperclass().getDeclaredField(string);
                } else {
                    declaredField = i2 != 2 ? null : cls.getSuperclass().getSuperclass().getDeclaredField(string);
                }
                if (declaredField != null) {
                    declaredField.setAccessible(true);
                    Object obj2 = declaredField.get(view);
                    if (obj2 != null) {
                        return obj2.toString();
                    }
                } else {
                    Log.secD(TAG, "getText: field not found");
                }
            } else {
                Class[] clsArr = new Class[0];
                Method method = cls.getMethod(string2, null);
                if (method != null && (invoke = method.invoke(view, null)) != null) {
                    return invoke.toString();
                }
            }
        }
        return null;
    }

    @Override // com.samsung.android.widget.ISemTouchApi
    public boolean getViewContent(Context context, String str, View view, PointF pointF, Object obj) {
        String viewContentInternal;
        if ((view instanceof ViewGroup) || (view instanceof ImageView) || (viewContentInternal = getViewContentInternal(context, str, view, pointF, obj)) == null) {
            return false;
        }
        this.mBixbyTouchFoundText = viewContentInternal;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseInfoFromView() {
        try {
            if (this.mView != null) {
                Activity parseActivity = parseActivity();
                if (parseActivity != null) {
                    this.mActivityName = parseActivity.getComponentName().getClassName();
                    this.mTaskId = parseActivity.getTaskId();
                    Configuration currentConfigFromActivity = getCurrentConfigFromActivity(parseActivity);
                    if (currentConfigFromActivity != null) {
                        this.mWindowConfig = currentConfigFromActivity.windowConfiguration.toString();
                        this.mWindowingMode = currentConfigFromActivity.windowConfiguration.getWindowingMode();
                    } else {
                        this.mWindowConfig = this.mView.getResources().getConfiguration().windowConfiguration.toString();
                        this.mWindowingMode = this.mView.getResources().getConfiguration().windowConfiguration.getWindowingMode();
                    }
                    this.mAppBounds = parseActivity.getWindow().getWindowManager().getCurrentWindowMetrics().getBounds();
                    this.mMaxBounds = parseActivity.getWindow().getWindowManager().getMaximumWindowMetrics().getBounds();
                    this.mBounds = this.mAppBounds;
                    int[] iArr = new int[2];
                    View decorView = parseActivity.getWindow().getDecorView();
                    decorView.getLocationOnScreen(iArr);
                    int i = iArr[0];
                    this.mDecorViewBounds = new Rect(i, iArr[1], decorView.getWidth() + i, iArr[1] + decorView.getHeight());
                    Log.secD(TAG, "parseInfoFromView: mDecorViewBounds = " + this.mDecorViewBounds.toString());
                    try {
                        this.mDisplayCutoutInsets = parseActivity.getWindow().getWindowManager().getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.displayCutout());
                        this.mNavigationBarsInsets = parseActivity.getWindow().getWindowManager().getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars());
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
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
        Activity activity;
        Context context = this.mContext;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            if (context instanceof DecorContext) {
                context = getContextFromDecorContext(this.mView.getContext());
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
            activity = null;
        }
        return activity == null ? getActivityFromContextWrapper(context) : activity;
    }

    private Activity getActivityFromContextWrapper(Context context) {
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        if (baseContext instanceof Activity) {
            return (Activity) baseContext;
        }
        return getActivityFromContextWrapper(baseContext);
    }

    private Context getContextFromDecorContext(Context context) {
        try {
            Field declaredField = context.getClass().getDeclaredField("mContext");
            if (declaredField == null) {
                return null;
            }
            declaredField.setAccessible(true);
            return (Context) ((WeakReference) declaredField.get(context)).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Configuration getCurrentConfigFromActivity(Activity activity) {
        try {
            return getCurrentConfig(activity, activity.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Configuration getCurrentConfig(Activity activity, Class cls) {
        Class superclass = cls.getSuperclass();
        try {
            Field declaredField = cls.getDeclaredField("mCurrentConfig");
            if (declaredField == null) {
                return null;
            }
            declaredField.setAccessible(true);
            return (Configuration) declaredField.get(activity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException unused) {
            if (superclass != null) {
                return getCurrentConfig(activity, superclass);
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFingerPrintInDisplay() {
        int i;
        boolean z;
        if (!sHasFingerPrintFeature) {
            return false;
        }
        try {
            FingerprintManager fingerprintManager = (FingerprintManager) this.mContext.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null) {
                z = FingerprintManager.semGetSensorPosition() == 2;
                i = fingerprintManager.semGetIconBottomMargin();
            } else {
                i = 0;
                z = false;
            }
        } catch (Exception unused) {
        }
        return z && i > 0;
    }

    public void setBixbyTouchEnable(boolean z) {
        SemOneTouchApi semOneTouchApi = this.mSemOneTouchApi;
        if (semOneTouchApi != null) {
            semOneTouchApi.updateSettingsValue(this.mContext);
        }
        if (this.mFindViewRestricted) {
            return;
        }
        sBixbyTouchEnable = z;
        if (isInitFailed()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.4
            @Override // java.lang.Runnable
            public void run() {
                if (SemPressGestureDetector.sBixbyTouchEnable) {
                    SemPressGestureDetector.this.initSetting();
                }
                SemPressGestureDetector.this.initWebSummary();
            }
        }).start();
    }

    public void init(Context context, View view) {
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
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mView.getLayoutParams();
        if (layoutParams != null) {
            Integer valueOf = Integer.valueOf(layoutParams.type);
            this.mWindowType = valueOf;
            boolean z = valueOf.intValue() >= 2000;
            this.mFindViewRestricted = z;
            if (z) {
                return;
            }
        }
        initOnChild();
    }

    public boolean isInitFailed() {
        return this.mInitFailed;
    }

    public boolean hasFingerPrintFeature() {
        return this.mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendBixbyLongClickedEvent(int i) {
        String str;
        if (!this.mDetachedFromWindow && (str = this.mActivityName) != null && !str.startsWith(TAEGET_PKG_NAME)) {
            try {
                Bundle bundle = new Bundle();
                bundle.putLong("request_code", sRequestCode);
                bundle.putInt("bixby_touch_flag", i);
                bundle.putInt("bixby_touch_detector_version", 3);
                if (i == 1) {
                    bundle.putString("caller_pkg", this.mCallerPackage);
                    bundle.putString("caller_activity", this.mActivityName);
                    bundle.putString("caller_process", this.mProcessName);
                    bundle.putStringArrayList("widget_name_list", sWidgetNameList);
                    bundle.putStringArrayList("widget_id_list", sWidgetIdList);
                    bundle.putInt("caller_task_id", this.mTaskId);
                    bundle.putInt("window_mode", this.mWindowingMode);
                    bundle.putString("window_config", this.mWindowConfig);
                    Rect rect = this.mAppBounds;
                    Rect rect2 = this.mBounds;
                    Rect rect3 = this.mMaxBounds;
                    Rect rect4 = this.mDecorViewBounds;
                    if (rect != null) {
                        bundle.putInt("window_left", rect.left);
                        bundle.putInt("window_top", rect.top);
                        bundle.putInt("window_right", rect.right);
                        bundle.putInt("window_bottom", rect.bottom);
                    }
                    if (rect2 != null) {
                        bundle.putInt("window_bounds_left", rect2.left);
                        bundle.putInt("window_bounds_top", rect2.top);
                        bundle.putInt("window_bounds_right", rect2.right);
                        bundle.putInt("window_bounds_bottom", rect2.bottom);
                    }
                    if (rect3 != null) {
                        bundle.putInt("window_max_bounds_left", rect3.left);
                        bundle.putInt("window_max_bounds_top", rect3.top);
                        bundle.putInt("window_max_bounds_right", rect3.right);
                        bundle.putInt("window_max_bounds_bottom", rect3.bottom);
                    }
                    if (rect4 != null) {
                        bundle.putInt("window_decor_view_bounds_left", rect4.left);
                        bundle.putInt("window_decor_view_bounds_top", rect4.top);
                        bundle.putInt("window_decor_view_bounds_right", rect4.right);
                        bundle.putInt("window_decor_view_bounds_bottom", rect4.bottom);
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
                    if (sCurrentTouchMode == 2) {
                        if (this.mTouchedRawPoints.size() == 2) {
                            bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).x);
                            bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).y);
                            bundle.putFloat("touch_raw_start_x2", this.mTouchedRawPoints.get(1).x);
                            bundle.putFloat("touch_raw_start_y2", this.mTouchedRawPoints.get(1).y);
                        }
                    } else if (this.mTouchedRawPoints.size() == 1) {
                        bundle.putFloat("touch_raw_start_x1", this.mTouchedRawPoints.get(0).x);
                        bundle.putFloat("touch_raw_start_y1", this.mTouchedRawPoints.get(0).y);
                    }
                    if (this.mTouchedViews.size() > 0) {
                        if (sCurrentTouchMode == 1) {
                            bundle = putTouchedViewInfoToBundle(bundle, this.mTouchedViews.get(0));
                        } else {
                            if (this.mTouchedViews.size() == 2) {
                                if (this.mTouchedViews.get(0) == this.mTouchedViews.get(1)) {
                                    bundle.putBoolean("fingers_touch_in_same_view", true);
                                } else {
                                    bundle.putBoolean("fingers_touch_in_same_view", false);
                                }
                            }
                            for (int i2 = 0; i2 < this.mTouchedViews.size(); i2++) {
                                bundle.putBundle("touched_view_info_" + i2, putTouchedViewInfoToBundle(new Bundle(), this.mTouchedViews.get(i2)));
                            }
                        }
                    }
                }
                Bundle call = this.mContext.getContentResolver().call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
                if (call != null) {
                    if (i == 1) {
                        long j = call.getLong(KEY_BIXBYTOUCH_VERSION, 0L);
                        if (j != 0) {
                            this.mBixbyTouchVersion = j;
                        }
                        sCurrentTouchMode = call.getInt(KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, sCurrentTouchMode);
                        sLongPressTime = call.getInt(KEY_BIXBYTOUCH_LONG_PRESS_TIME, sLongPressTime);
                        sLongLongPressTime = call.getInt(KEY_LONG_LONG_PRESS_TIME, sLongLongPressTime);
                        sCheckTouchDownDelayTime = call.getInt(KEY_CHECK_FP_DELAY_TIME, sCheckTouchDownDelayTime);
                        sFingerDownThreshold = call.getInt(KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD, sFingerDownThreshold);
                    }
                    return call.getBoolean("bixby_touch_response", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Bundle putTouchedViewInfoToBundle(Bundle bundle, View view) {
        if (view != null) {
            try {
                if (view instanceof TextView) {
                    bundle.putInt("input_type", ((TextView) view).getInputType());
                }
                bundle.putString("bixby_touch_find_text", this.mBixbyTouchFoundText);
                bundle.putString("found_widget_name", view.getClass().getName());
                if (view.getId() != -1) {
                    bundle.putString("found_widget_id", this.mContext.getResources().getResourceName(view.getId()));
                    return bundle;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }

    private void sendBixbyLongLongCancelEvent() {
        this.mHasDoneLongTouch = false;
        this.mBixbyTouchFoundText = null;
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
        BroadcastReceiver broadcastReceiver;
        SemOneTouchApi semOneTouchApi = this.mSemOneTouchApi;
        if (semOneTouchApi != null) {
            semOneTouchApi.onCleared(this.mContext);
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
        Context context = this.mContext;
        if (context != null && (broadcastReceiver = this.mBroadcastReceiver) != null) {
            try {
                context.unregisterReceiver(broadcastReceiver);
                removeRegisterBroadcastActivityTime();
            } catch (Exception e) {
                Log.secD(TAG, "unregisterReceiver:" + e.getMessage());
            }
            this.mBroadcastReceiver = null;
        }
        this.mBixbyTouchFoundText = null;
    }

    private boolean matchPackage(String str) {
        return str.equals(this.mCallerPackage);
    }

    private float mm2px(float f) {
        return (f / 25.4f) * this.mContext.getResources().getDisplayMetrics().densityDpi;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity.activityInfo == null) {
            return false;
        }
        return this.mCallerPackage.equals(resolveActivity.activityInfo.packageName);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean dispatchTouchEventOneFinger;
        if (motionEvent.getActionMasked() == 0) {
            Log.secD(TAG, "dispatchTouchEvent:" + this.mProcessName + ",sBixbyTouchEnable=" + sBixbyTouchEnable + ",mFindViewRestricted=" + this.mFindViewRestricted);
            sHasCallReflectCount = 0;
        }
        SemOneTouchApi semOneTouchApi = this.mSemOneTouchApi;
        if (semOneTouchApi != null) {
            z = semOneTouchApi.dispatchTouchEvent(this.mContext, motionEvent, this.mView);
            if (z) {
                return true;
            }
        } else {
            z = false;
        }
        if (!sBixbyTouchEnable || this.mFindViewRestricted || this.mDetachedFromWindow) {
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            resetFlags(motionEvent);
        }
        if (sCurrentTouchMode == 2) {
            dispatchTouchEventOneFinger = dispatchTouchEventDoubleFingers(motionEvent);
        } else {
            dispatchTouchEventOneFinger = dispatchTouchEventOneFinger(motionEvent);
        }
        return z || dispatchTouchEventOneFinger;
    }

    private void resetFlags(MotionEvent motionEvent) {
        this.mTouchedPoints.clear();
        this.mTouchedRawPoints.clear();
        this.mHasDoneLongTouch = false;
        this.mResponeLongTouch = true;
        this.mResponeLongLongTouch = false;
        this.mTouchDownRestricted = false;
        this.mTouchedTime = System.currentTimeMillis();
        addTouchedPoint(motionEvent);
        this.mBixbyTouchFoundText = null;
    }

    private boolean dispatchTouchEventOneFinger(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime);
            this.mView.postDelayed(this.mCheckRestrictTouchRunnable, sCheckTouchDownDelayTime);
        } else {
            if (motionEvent.getActionMasked() == 1) {
                Log.secD(TAG, "mResponeLongTouch=" + this.mResponeLongTouch + ",mResponeLongLongTouch=" + this.mResponeLongLongTouch);
            }
            if (!this.mResponeLongTouch) {
                return false;
            }
        }
        if (!this.mHasDoneLongTouch) {
            doLongPressOneFinger(motionEvent);
        } else {
            doLongLongPressOneFinger(motionEvent);
        }
        return this.mResponeLongLongTouch;
    }

    private boolean dispatchTouchEventDoubleFingers(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 2 && motionEvent.getActionMasked() == 5) {
            if (System.currentTimeMillis() - this.mTouchedTime > sFingerDownThreshold) {
                return false;
            }
            addTouchedPoint(motionEvent);
            this.mView.postDelayed(this.mLongTouchRunnable, sLongPressTime - sFingerDownThreshold);
            this.mView.postDelayed(this.mCheckRestrictTouchRunnable, sCheckTouchDownDelayTime - sFingerDownThreshold);
        } else if (!this.mResponeLongTouch) {
            return false;
        }
        if (!this.mHasDoneLongTouch) {
            doLongPressDoubleFingers(motionEvent);
        } else {
            doLongLongPressDoubleFingers(motionEvent);
        }
        return this.mResponeLongLongTouch;
    }

    private void addTouchedPoint(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(motionEvent.getPointerId(motionEvent.getActionIndex()));
        float x = motionEvent.getX(findPointerIndex);
        float y = motionEvent.getY(findPointerIndex);
        float rawX = motionEvent.getRawX(findPointerIndex);
        float rawY = motionEvent.getRawY(findPointerIndex);
        Point point = new Point(x, y);
        Point point2 = new Point(rawX, rawY);
        this.mTouchedPoints.add(point);
        this.mTouchedRawPoints.add(point2);
    }

    private boolean checkTouchedPointIsMoved(MotionEvent motionEvent) {
        boolean z = false;
        for (int i = 0; i < this.mTouchedPoints.size() && i < motionEvent.getPointerCount(); i++) {
            z = z || ((Math.abs(motionEvent.getX(i) - this.mTouchedPoints.get(i).x) > ((float) sTouchMoveMaxPixel) ? 1 : (Math.abs(motionEvent.getX(i) - this.mTouchedPoints.get(i).x) == ((float) sTouchMoveMaxPixel) ? 0 : -1)) > 0 || (Math.abs(motionEvent.getY(i) - this.mTouchedPoints.get(i).y) > ((float) sTouchMoveMaxPixel) ? 1 : (Math.abs(motionEvent.getY(i) - this.mTouchedPoints.get(i).y) == ((float) sTouchMoveMaxPixel) ? 0 : -1)) > 0);
        }
        return z;
    }

    private void doLongPressOneFinger(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (checkTouchedPointIsMoved(motionEvent)) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    return;
                }
                return;
            }
            if (actionMasked != 3 && actionMasked != 5) {
                return;
            }
        }
        this.mView.removeCallbacks(this.mLongTouchRunnable);
        this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
    }

    private void doLongLongPressOneFinger(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (checkTouchedPointIsMoved(motionEvent)) {
                    this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                    sendBixbyLongLongCancelEvent();
                    return;
                }
                return;
            }
            if (actionMasked != 3 && actionMasked != 5) {
                return;
            }
        }
        this.mView.removeCallbacks(this.mLongLongTouchRunnable);
        sendBixbyLongLongCancelEvent();
    }

    private void doLongPressDoubleFingers(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (checkTouchedPointIsMoved(motionEvent)) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    return;
                }
                return;
            }
            if (actionMasked != 3) {
                if ((actionMasked == 5 || actionMasked == 6) && motionEvent.getPointerCount() != 2) {
                    this.mView.removeCallbacks(this.mLongTouchRunnable);
                    this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
                    return;
                }
                return;
            }
        }
        this.mView.removeCallbacks(this.mLongTouchRunnable);
        this.mView.removeCallbacks(this.mCheckRestrictTouchRunnable);
    }

    private void doLongLongPressDoubleFingers(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (checkTouchedPointIsMoved(motionEvent)) {
                    this.mView.removeCallbacks(this.mLongLongTouchRunnable);
                    sendBixbyLongLongCancelEvent();
                    return;
                }
                return;
            }
            if (actionMasked != 3 && actionMasked != 5 && actionMasked != 6) {
                return;
            }
        }
        this.mView.removeCallbacks(this.mLongLongTouchRunnable);
        sendBixbyLongLongCancelEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSetting() {
        try {
            long appVersionCode = getAppVersionCode(this.mContext, TAEGET_PKG_NAME);
            sCurrentTouchMode = Settings.Secure.getInt(this.mContext.getContentResolver(), KEY_BIXBYTOUCH_FINGER_TOUCH_TRIGGER, SemOneTouchApi.isOneTouchSupported() ? 2 : 1);
            sLongPressTime = Settings.Secure.getInt(this.mContext.getContentResolver(), KEY_BIXBYTOUCH_LONG_PRESS_TIME, 500);
            sLongLongPressTime = Settings.Secure.getInt(this.mContext.getContentResolver(), KEY_LONG_LONG_PRESS_TIME, 1500);
            sCheckTouchDownDelayTime = Settings.Secure.getInt(this.mContext.getContentResolver(), KEY_CHECK_FP_DELAY_TIME, 100);
            sFingerDownThreshold = Settings.Secure.getInt(this.mContext.getContentResolver(), KEY_BIXBYTOUCH_FINGER_DOWN_THRESHOLD, 100);
            this.mBixbyTouchVersion = appVersionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initWebSummary() {
        if (this.mFindViewRestricted || !(this.mView instanceof DecorView)) {
            return;
        }
        try {
            registerWebSummaryBroadcast();
        } catch (Exception e) {
            Log.secD(TAG, "init : " + e.getMessage(), e);
        }
    }

    private void initOnChild() {
        new Thread(new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SemPressGestureDetector.this.checkBlockApp();
                } catch (Exception e) {
                    Log.secD(SemPressGestureDetector.TAG, "initOnChild:" + e.getMessage());
                }
            }
        }).start();
    }

    private void registerWebSummaryBroadcast() {
        if (this.mContext != null) {
            IntentFilter intentFilter = new IntentFilter();
            Activity parseActivity = parseActivity();
            if (parseActivity != null && this.mBroadcastReceiver == null) {
                String string = Settings.Global.getString(this.mContext.getContentResolver(), "web_summary_activity");
                String string2 = Settings.Global.getString(this.mContext.getContentResolver(), "view_location_activity");
                boolean isEmpty = TextUtils.isEmpty(string);
                boolean isEmpty2 = TextUtils.isEmpty(string2);
                if (isEmpty && isEmpty2) {
                    return;
                }
                int hashCode = parseActivity.getClass().getName().hashCode();
                boolean z = false;
                String substring = Integer.toHexString(hashCode).substring(0, Math.min(10, Integer.toHexString(hashCode).length()));
                boolean z2 = !isEmpty && string.contains(substring);
                if (!isEmpty2 && string2.contains(substring)) {
                    z = true;
                }
                if (z2 || z) {
                    this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.widget.SemPressGestureDetector.6
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            int lastIndexOf;
                            try {
                                String action = intent.getAction();
                                if (!TextUtils.isEmpty(action) && (lastIndexOf = action.lastIndexOf(46)) != -1) {
                                    String substring2 = action.substring(lastIndexOf + 1);
                                    Log.secD(SemPressGestureDetector.TAG, ">" + action.substring(0, lastIndexOf));
                                    long maxCreateTimeForClass = SemPressGestureDetector.this.getMaxCreateTimeForClass();
                                    if (maxCreateTimeForClass != SemPressGestureDetector.this.mRegisterBroadcastTime || maxCreateTimeForClass == 0) {
                                        return;
                                    }
                                    if (TextUtils.equals(substring2, "START_PARSE")) {
                                        SemPressGestureDetector.this.startObtainWebViewData(intent);
                                    } else if (TextUtils.equals(substring2, "START_FIND")) {
                                        SemPressGestureDetector.this.startObtainViewLocation(intent);
                                    }
                                }
                            } catch (Exception e) {
                                Log.secD(SemPressGestureDetector.TAG, "receive : " + e.getMessage(), e);
                            }
                        }
                    };
                    String name = parseActivity.getClass().getName();
                    addRegisterBroadcastActivity(name);
                    Log.secD(TAG, "<" + name);
                    if (z2) {
                        intentFilter.addAction(name + ".START_PARSE");
                    }
                    if (z) {
                        intentFilter.addAction(name + ".START_FIND");
                    }
                    this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, Manifest.permission.MANAGE_ACTIVITY_TASKS, null, 2);
                }
            }
        }
    }

    public void addRegisterBroadcastActivity(String str) {
        try {
            this.mRegisterBroadcastActivityName = str;
            mRegisteredActivityMap.putIfAbsent(str, new ArrayList());
            this.mRegisterBroadcastTime = SystemClock.elapsedRealtime();
            mRegisteredActivityMap.get(this.mRegisterBroadcastActivityName).add(Long.valueOf(this.mRegisterBroadcastTime));
        } catch (Exception e) {
            Log.secD(TAG, "addRegisterBroadcastActivity: " + e.getMessage(), e);
        }
    }

    public long getMaxCreateTimeForClass() {
        if (!mRegisteredActivityMap.containsKey(this.mRegisterBroadcastActivityName)) {
            return 0L;
        }
        List<Long> list = mRegisteredActivityMap.get(this.mRegisterBroadcastActivityName);
        if (list.isEmpty()) {
            return 0L;
        }
        long longValue = list.get(0).longValue();
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            long longValue2 = it.next().longValue();
            if (longValue2 > longValue) {
                longValue = longValue2;
            }
        }
        return longValue;
    }

    public void removeRegisterBroadcastActivityTime() {
        try {
            if (!TextUtils.isEmpty(this.mRegisterBroadcastActivityName) && mRegisteredActivityMap.containsKey(this.mRegisterBroadcastActivityName)) {
                List<Long> list = mRegisteredActivityMap.get(this.mRegisterBroadcastActivityName);
                if (list.isEmpty()) {
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).longValue() == this.mRegisterBroadcastTime) {
                        list.remove(i);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.secD(TAG, "removeRegisterBroadcastActivity: " + e.getMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startObtainWebViewData(Intent intent) {
        parseWebView(intent);
    }

    private View findWebView(ViewGroup viewGroup, String str, boolean z) {
        int childCount = viewGroup.getChildCount();
        View view = null;
        if (z) {
            for (int i = childCount - 1; i >= 0; i--) {
                View childAt = viewGroup.getChildAt(i);
                if ((childAt instanceof WebView) || childAt.getClass().getName().equals(str)) {
                    return childAt;
                }
                if ((childAt instanceof ViewGroup) && (view = findWebView((ViewGroup) childAt, str, true)) != null) {
                    return view;
                }
            }
            return view;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt2 = viewGroup.getChildAt(i2);
            if ((childAt2 instanceof WebView) || childAt2.getClass().getName().equals(str)) {
                return childAt2;
            }
            if ((childAt2 instanceof ViewGroup) && (view = findWebView((ViewGroup) childAt2, str, false)) != null) {
                return view;
            }
        }
        return view;
    }

    private void parseWebView(Intent intent) {
        if (this.mView != null) {
            final String stringExtra = intent.getStringExtra("request_code");
            String stringExtra2 = intent.getStringExtra("webview_class_name");
            boolean booleanExtra = intent.getBooleanExtra("top_down_find_view", false);
            boolean booleanExtra2 = intent.getBooleanExtra("request_body", false);
            if (TextUtils.isEmpty(stringExtra2)) {
                stringExtra2 = WebView.class.getName();
            }
            View view = this.mView;
            View findWebView = view instanceof ViewGroup ? findWebView((ViewGroup) view, stringExtra2, booleanExtra) : null;
            if (findWebView == null) {
                sendHtmlData("", stringExtra);
                return;
            }
            if (intent.getBooleanExtra("invoke_method", false)) {
                findWebView = invokeMethodGetView(findWebView, intent.getStringExtra("method_name"));
            }
            if (findWebView == null) {
                sendHtmlData("", stringExtra);
                return;
            }
            ValueCallback<String> valueCallback = new ValueCallback<String>() { // from class: com.samsung.android.widget.SemPressGestureDetector.7
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(String str) {
                    SemPressGestureDetector.this.sendHtmlData(str, stringExtra);
                }
            };
            if (findWebView instanceof WebView) {
                evaluateHtmlData((WebView) findWebView, booleanExtra2, valueCallback);
            } else {
                invokeHtmlData(findWebView, booleanExtra2, stringExtra, valueCallback);
            }
        }
    }

    private View invokeMethodGetView(View view, String str) {
        try {
            Class[] clsArr = new Class[0];
            Object invoke = view.getClass().getMethod(str, null).invoke(view, null);
            if (invoke instanceof View) {
                return (View) invoke;
            }
        } catch (Exception e) {
            Log.secD(TAG, "invoke 2: " + e.getMessage(), e);
        }
        return null;
    }

    private void evaluateHtmlData(WebView webView, boolean z, ValueCallback valueCallback) {
        webView.evaluateJavascript(getRule(z), valueCallback);
    }

    private void invokeHtmlData(View view, boolean z, String str, ValueCallback valueCallback) {
        try {
            Method method = view.getClass().getMethod("evaluateJavascript", String.class, ValueCallback.class);
            if (method != null) {
                method.invoke(view, getRule(z), valueCallback);
            }
        } catch (Exception e) {
            sendParseResult("", false, str);
            Log.secD(TAG, "invoke : " + e.getMessage(), e);
        }
    }

    private String getRule(boolean z) {
        if (z) {
            return "javascript:document.body.innerHTML";
        }
        return "javascript:document.documentElement.innerHTML";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHtmlData(final String str, final String str2) {
        new Thread(new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    SemPressGestureDetector.this.sendParseResult(str, SemPressGestureDetector.this.saveHtmlDataToFile(str), str2);
                } catch (Exception e) {
                    Log.secD(SemPressGestureDetector.TAG, "send fail: " + e.getMessage(), e);
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveHtmlDataToFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            ParcelFileDescriptor openFile = this.mContext.getContentResolver().openFile(Uri.parse("content://com.samsung.android.bixbytouch/web_summary_html_data"), String.valueOf(805306368), new CancellationSignal());
            try {
                if (openFile == null) {
                    Log.secD(TAG, "open fail");
                    if (openFile != null) {
                        openFile.close();
                    }
                    return false;
                }
                FileWriter fileWriter = new FileWriter(openFile.getFileDescriptor());
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    try {
                        bufferedWriter.write(str);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        fileWriter.close();
                        if (openFile == null) {
                            return true;
                        }
                        openFile.close();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void sendParseResult(String str, boolean z, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("request_code", str2);
        bundle.putInt("bixby_touch_flag", 5);
        bundle.putBoolean("result", z);
        bundle.putBoolean("web_activity", !TextUtils.isEmpty(str));
        this.mContext.getContentResolver().call(BIXBY_TOUCH_URI, CALL_METHOD, (String) null, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startObtainViewLocation(Intent intent) {
        int[] parseView;
        Bundle extras = intent.getExtras();
        if (extras == null || extras.isEmpty()) {
            sendFindViewLocationResult(null, "empty data");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(TextView.class.getName(), TextView.class);
        hashMap.put(EditText.class.getName(), EditText.class);
        hashMap.put(CheckBox.class.getName(), CheckBox.class);
        hashMap.put(Button.class.getName(), Button.class);
        hashMap.put(ImageView.class.getName(), ImageView.class);
        String string = extras.getString("class_name");
        String[] stringArray = extras.getStringArray("content_desc");
        String[] stringArray2 = extras.getStringArray("content_text");
        String string2 = extras.getString("id");
        String string3 = extras.getString(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG);
        View view = this.mView;
        if (!(view instanceof ViewGroup)) {
            sendFindViewLocationResult(null, string3);
            return;
        }
        View findTargetView = findTargetView(view, string2, string, stringArray, stringArray2, hashMap);
        if (findTargetView != null && (parseView = parseView(findTargetView, extras, hashMap)) != null) {
            sendFindViewLocationResult(parseView, string3);
        } else {
            sendFindViewLocationResult(null, string3);
        }
    }

    private int[] parseView(View view, Bundle bundle, Map<String, Class<?>> map) {
        View findParentView = findParentView(view, bundle.getInt("find_parent", 0));
        if (bundle.getBoolean("target_child", false)) {
            findParentView = findTargetView(findParentView, bundle.getString("child_id"), bundle.getString("child_class_name"), bundle.getStringArray("child_content_desc"), bundle.getStringArray("child_content_text"), map);
        }
        if (findParentView == null) {
            return null;
        }
        int[] iArr = new int[2];
        findParentView.getLocationOnScreen(iArr);
        iArr[0] = iArr[0] + (findParentView.getWidth() / 2);
        iArr[1] = iArr[1] + (findParentView.getHeight() / 2);
        Log.secD(TAG, "findView : " + findParentView);
        return iArr;
    }

    private View findTargetView(View view, String str, String str2, String[] strArr, String[] strArr2, Map<String, Class<?>> map) {
        View findById = findById(view, str);
        if (findById != null) {
            return findById;
        }
        if (view instanceof ViewGroup) {
            return findView((ViewGroup) view, str2, strArr, strArr2, !TextUtils.isEmpty(str2), strArr != null && strArr.length > 0, strArr2 != null && strArr2.length > 0, map);
        }
        return null;
    }

    public View findParentView(View view, int i) {
        if (i <= 0 || view == null) {
            return view;
        }
        return findParentView(view.getParent() instanceof View ? (View) view.getParent() : null, i - 1);
    }

    private View findById(View view, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return view.findViewById(view.getResources().getIdentifier(str, "id", view.getContext().getPackageName()));
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean isSameInstanceof(View view, String str, Map<String, Class<?>> map) {
        Class<?> cls;
        return view.getVisibility() == 0 && view.getWidth() != 0 && view.getHeight() != 0 && (((cls = map.get(str)) != null && cls.isInstance(view)) || view.getClass().getName().equals(str));
    }

    private View findView(ViewGroup viewGroup, String str, String[] strArr, String[] strArr2, boolean z, boolean z2, boolean z3, Map<String, Class<?>> map) {
        View findView;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (isTargetText(childAt, strArr, strArr2, z2, z3) || (z && isSameInstanceof(childAt, str, map))) {
                return childAt;
            }
            if ((childAt instanceof ViewGroup) && (findView = findView((ViewGroup) childAt, str, strArr, strArr2, z, z2, z3, map)) != null) {
                return findView;
            }
        }
        return null;
    }

    private boolean isTargetText(View view, String[] strArr, String[] strArr2, boolean z, boolean z2) {
        if (view.getVisibility() == 0 && view.getWidth() != 0 && view.getHeight() != 0) {
            if (z) {
                return containString(strArr, view.getContentDescription());
            }
            if (z2 && (view instanceof TextView)) {
                return containString(strArr2, ((TextView) view).getText());
            }
        }
        return false;
    }

    private boolean containString(String[] strArr, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        for (String str : strArr) {
            if (charSequence.toString().startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    private void sendFindViewLocationResult(final int[] iArr, final String str) {
        new Thread(new Runnable() { // from class: com.samsung.android.widget.SemPressGestureDetector.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("bixby_touch_flag", 6);
                    int[] iArr2 = iArr;
                    if (iArr2 != null && iArr2.length > 1) {
                        bundle.putIntArray("result", iArr2);
                    }
                    bundle.putString(SemEmergencyConstants.EXTRA_EMERGENCY_START_SERVICE_FLAG, str);
                    SemPressGestureDetector.this.mContext.getContentResolver().call(SemPressGestureDetector.BIXBY_TOUCH_URI, SemPressGestureDetector.CALL_METHOD, (String) null, bundle);
                } catch (Exception e) {
                    Log.secD(SemPressGestureDetector.TAG, "sendFindViewLocationResult: " + e.getMessage(), e);
                }
            }
        }).start();
    }
}
