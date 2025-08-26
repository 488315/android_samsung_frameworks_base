package com.samsung.android.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FactoryTest;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.policy.DecorContext;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.google.android.mms.ContentType;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes6.dex */
public class SemOneTouchApi implements ISemTouchApi {
    private static final int BITMAP_AVAILABLE_SAMPLE_SIZE = 100;
    private static final String BUNDLE_KEY_ACTIVITY_NAME = "key_activity_name";
    private static final String BUNDLE_KEY_APP_PROCESS_NAME = "key_app_process_name";
    private static final String BUNDLE_KEY_CUSTOM_CLIP_DATA = "key_customized_clip_data";
    private static final String BUNDLE_KEY_CUSTOM_DRAG_SHADOW_WIDTH = "key_custom_drag_shadow_width";
    private static final String BUNDLE_KEY_EVENT_TOOL_TYPE = "key_event_tool_type";
    private static final String BUNDLE_KEY_LONG_PRESS_FLAG = "key_long_press_flag";
    private static final String BUNDLE_KEY_LONG_PRESS_PHASE_TWO_CUSTOM_THRESHOLD = "key_long_press_phase_two_custom_threshold";
    private static final String BUNDLE_KEY_PREVIOUS_LONG_PRESS_FLAG = "key_previous_long_press_flag";
    private static final String BUNDLE_KEY_RAW_TOUCHED_POINT = "key_raw_touched_point";
    private static final String BUNDLE_KEY_REQUEST_CODE = "key_request_code";
    private static final String BUNDLE_KEY_RESULT_BOOLEAN = "key_result_boolean";
    private static final String BUNDLE_KEY_SAVE_IMAGE_RESULT = "key_save_img_result";
    private static final String BUNDLE_KEY_SKIP_DRAG_AND_DROP = "key_skip_drag_and_drop";
    private static final String BUNDLE_KEY_TOUCHED_IMG_PFD = "key_touched_img_pfd";
    private static final String BUNDLE_KEY_TOUCHED_POINT = "key_touched_point";
    private static final String BUNDLE_KEY_TOUCHED_TEXT = "key_touched_text";
    private static final String BUNDLE_KEY_TOUCHED_VIEW_SIZE = "key_touched_view_size";
    private static final String BUNDLE_KEY_TOUCHED_WIDGET_ID = "key_touched_widget_id";
    private static final String BUNDLE_KEY_TOUCHED_WIDGET_NAME = "key_touched_widget_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_ClASS_NAME = "key_video_view_class_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_FINDING_TOP_TO_DOWN = "key_video_view_finding_top_to_down";
    private static final String BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS = "key_video_view_root_calss_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_WIDGET_ID = "key_video_view_widget_id";
    private static final String BUNDLE_KEY_VIDEO_VIEW_WIDGET_NAME = "key_video_view_widget_name";
    private static final String BUNDLE_KEY_WEB_VIEW_CONTENT = "key_webview_content";
    private static final String BUNDLE_KEY_WINDOW_FLAGS = "key_window_flags";
    private static final String BUNDLE_KEY_WINDOW_RECT = "key_window_rect";
    private static final String BUNDLE_KEY_WINDOW_TYPE = "key_window_type";
    private static final String CALL_METHOD_CUSTOM_VIDEO_CLASS_NAME = "query_custom_video_class_name";
    private static final String CALL_METHOD_ON_LONG_PRESSED = "method_on_long_pressed";
    public static final String COLUMN_CLASS_NAME = "clazz";
    public static final String COLUMN_FIELD_LEVEL = "level";
    public static final String COLUMN_FIELD_NAME = "field";
    public static final String COLUMN_METHOD_NAME = "method";
    public static final String COLUMN_VIEW_TYPE = "type";
    private static final int DEFAULT_CHECK_TOUCH_DOWN_DELAY_TIME = 100;
    private static final int DEFAULT_LONG_PRESS_PHASE_ONE_THRESHOLD = 440;
    private static final int DEFAULT_LONG_PRESS_PHASE_TWO_THRESHOLD = 810;
    private static final int DRAG_SHADOW_TEXT_VIEW_MAX_LENGTH = 100;
    private static final int FOUND_TEXT_MAX_LENGTH = 2000;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_CANCELLED = 3;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_COMPLETED = 4;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_PHASE_ONE = 1;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_PHASE_TWO = 2;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_START = 0;
    private static final int LONG_PRESS_FLAG_SAVE_IMAGE_VIEW_FINISHED = 1001;
    private static final int LONG_PRESS_FLAG_SAVE_WEB_VIEW_CONTENT = 1002;
    private static final String OPEN_URI_SAVE_TOUCHED_IMG = "uri_save_touched_img";
    private static final String OTCH_EXTERNAL_EVENT_AUTHORITY = "content://com.samsung.android.onetouch.externalEvent";
    private static final Uri OTCH_EXTERNAL_EVENT_URI = Uri.parse(OTCH_EXTERNAL_EVENT_AUTHORITY);
    public static final String PACKAGE_NAME_ONE_TOUCH = "com.samsung.android.onetouch";
    private static final String PACKAGE_NAME_SYSTEM_UI = "com.android.systemui";
    public static final String PROJECTION_PACKAGE_NAME = "pkg";
    private static final String PROJECTION_POINTF_X = "pointFX";
    private static final String PROJECTION_POINTF_Y = "pointFY";
    private static final String PROJECTION_TOUCHED_VIEW_SCALE = "touched_view_scale";
    public static final String PROJECTION_VERSION_CODE = "ver";
    private static final String QUERY_FIND_VIEW_INFO = "query_find_view_info";
    private static final String QUERY_WEBVIEW_JAVASCRIPT = "query_webview_javascript";
    private static final int REFLECT_FIELD_LEVEL_SELF = 0;
    private static final int REFLECT_FIELD_LEVEL_SUPER = 1;
    private static final int REFLECT_FIELD_LEVEL_SUPER_SUPER = 2;
    private static final String SETTING_KEY_OTCH_LONG_PRESS_ENABLE = "otch_long_press_enabled_setting";
    private static final String SETTING_KEY_OTCH_LONG_PRESS_PHASE_ONE_THRESHOLD = "otch_long_press_phase_one_threshold";
    private static final String SETTING_KEY_OTCH_LONG_PRESS_PHASE_TWO_THRESHOLD = "otch_long_press_phase_two_threshold";
    private static final String TAG = "OTCH$SemOneTouchApi";
    private static final float TOUCH_MOVE_MAX_MM = 3.0f;
    public static final String VIEW_TYPE_BLOCK = "view_type_block";
    public static final String VIEW_TYPE_IMAGE_VIEW = "android.widget.ImageView";
    public static final String VIEW_TYPE_TEXT_VIEW = "android.widget.TextView";
    public static final String VIEW_TYPE_WEB_VIEW = "android.webkit.WebView";
    private CheckRestrictTouchRunnable mCheckRestrictTouchRunnable;
    private Boolean mIsInitialized;
    private int mTouchEventMoveMaxPixel;
    private Boolean mIsOneTouchSettingsEnabled = false;
    private boolean sIsFactoryBinary = FactoryTest.isFactoryBinary();
    private AtomicReference<OtchLongPressEvent> mCurrentLongPressEvent = new AtomicReference<>();
    private LongPressPhaseOneRunnable mLongPressPhaseOneRunnable = null;
    private LongPressPhaseTwoRunnable mLongPressPhaseTwoRunnable = null;
    private LongPressPhaseTwoOnResponseRunnable mLongPressPhaseTwoOnResponseRunnable = null;
    private Pair<Integer, Integer> mOneTouchLongPressThreshold = new Pair<>(440, 810);

    /* JADX INFO: Access modifiers changed from: private */
    interface OtchDragAndDropResultCallback {
        void onDragAndDropResult(boolean z);
    }

    public static boolean isOneTouchSupported() {
        return CoreRune.FW_SUPPORT_ONE_TOUCH;
    }

    public SemOneTouchApi(Context context, View view) {
        this.mIsInitialized = false;
        this.mCheckRestrictTouchRunnable = null;
        this.mTouchEventMoveMaxPixel = 50;
        try {
            if (isBlocked(context, view)) {
                return;
            }
            updateSettingsValue(context);
            this.mTouchEventMoveMaxPixel = (int) mm2px(context, 3.0f);
            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                this.mCheckRestrictTouchRunnable = new CheckRestrictTouchRunnable(context, view);
            }
            this.mIsInitialized = true;
        } catch (Exception e) {
            Log.secE(TAG, "init failed", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateSettingsValue(Context context) {
        boolean z;
        if (context == null) {
            return;
        }
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (CoreRune.FW_SUPPORT_ONE_TOUCH) {
                z = true;
                if (Settings.Secure.getInt(context.getContentResolver(), SETTING_KEY_OTCH_LONG_PRESS_ENABLE, 1) != 1) {
                    z = false;
                }
            }
            this.mIsOneTouchSettingsEnabled = Boolean.valueOf(z);
            this.mOneTouchLongPressThreshold = new Pair<>(Integer.valueOf(Settings.Secure.getInt(contentResolver, SETTING_KEY_OTCH_LONG_PRESS_PHASE_ONE_THRESHOLD, 440)), Integer.valueOf(Settings.Secure.getInt(contentResolver, SETTING_KEY_OTCH_LONG_PRESS_PHASE_TWO_THRESHOLD, 810)));
        } catch (Exception e) {
            Log.secE(TAG, "updateSettingsValue failed", e);
        }
    }

    public void onCleared(Context context) {
        if (this.mCurrentLongPressEvent.get() != null) {
            onLongPressCanceled(context);
        }
    }

    private static class FindVideoViewEventInfo {
        private boolean findTopToDown;
        private final String videoViewClassName;

        public FindVideoViewEventInfo(boolean z, String str) {
            this.findTopToDown = z;
            this.videoViewClassName = str;
        }

        public String toString() {
            return "FindVideoViewEventInfo( findTopToDown=" + this.findTopToDown + ", videoViewClassName=" + this.videoViewClassName + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OtchLongPressEvent {
        private Bundle bundle;
        private String componentName;
        private int eventFlag;
        private int eventToolType;
        private List<FindViewInfo> findViewInfos;
        private Object foundViewContent;
        private String packageName;
        private final long requestCode;
        private final PointF touchedPoint;
        private final PointF touchedRawPoint;
        private View touchedView;

        public OtchLongPressEvent(long j, int i, MotionEvent motionEvent) {
            this.touchedView = null;
            this.bundle = null;
            this.findViewInfos = null;
            this.requestCode = j;
            this.eventFlag = i;
            this.touchedPoint = new PointF(motionEvent.getX(), motionEvent.getY());
            this.touchedRawPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
            this.eventToolType = motionEvent.getToolType(0);
        }

        public OtchLongPressEvent(long j, int i, View view, PointF pointF, PointF pointF2, Bundle bundle) {
            this.findViewInfos = null;
            this.requestCode = j;
            this.eventFlag = i;
            this.touchedView = view;
            this.touchedPoint = pointF;
            this.touchedRawPoint = pointF2;
            this.bundle = bundle;
        }

        public String toString() {
            return "Content( requestCode=" + this.requestCode + ", packageName=" + this.packageName + ", componentName=" + this.componentName + ", eventFlag=" + this.eventFlag + ", touchedView=" + this.touchedView + ", touchedPoint=" + this.touchedPoint + ", touchRawPoint=" + this.touchedRawPoint + ", bundle=" + this.bundle + NavigationBarInflaterView.KEY_CODE_END;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public OtchLongPressEvent m9723clone() {
            OtchLongPressEvent otchLongPressEvent = new OtchLongPressEvent(this.requestCode, this.eventFlag, this.touchedView, this.touchedPoint, this.touchedRawPoint, this.bundle);
            otchLongPressEvent.packageName = this.packageName;
            otchLongPressEvent.componentName = this.componentName;
            otchLongPressEvent.findViewInfos = this.findViewInfos;
            otchLongPressEvent.foundViewContent = this.foundViewContent;
            return otchLongPressEvent;
        }
    }

    private class CheckRestrictTouchRunnable implements Runnable {
        private final Context context;
        private final View rootView;

        public CheckRestrictTouchRunnable(Context context, View view) {
            this.context = context;
            this.rootView = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SemOneTouchApi.this.isFingerPrintInDisplay(this.context)) {
                SemOneTouchApi.this.clearEventState();
            }
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    private class LongPressPhaseOneRunnable implements Runnable {
        private final Context context;
        private final View rootView;

        public LongPressPhaseOneRunnable(Context context, View view) {
            this.context = context;
            this.rootView = view;
        }

        private boolean checkCurrentViewValid() {
            try {
                Display display = this.rootView.getDisplay();
                if (display == null || display.getDisplayId() == 0) {
                    return true;
                }
                Log.secW(SemOneTouchApi.TAG, "invalid, getDisplayId " + display.getDisplayId());
                return false;
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "checkCurrentViewValid failed", e);
                return true;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (checkCurrentViewValid()) {
                    OtchLongPressEvent otchLongPressEvent = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                    if (otchLongPressEvent != null && otchLongPressEvent.eventFlag == 0) {
                        Log.secD(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable: " + otchLongPressEvent.requestCode);
                        otchLongPressEvent.eventFlag = 1;
                        Bundle bundle = new Bundle();
                        SemOneTouchApi.this.putRootViewInfoToBundle(this.context, bundle, this.rootView, otchLongPressEvent);
                        List<FindViewInfo> listQueryFindViewInfo = SemOneTouchApi.this.queryFindViewInfo(this.context, otchLongPressEvent.packageName);
                        if (!listQueryFindViewInfo.isEmpty()) {
                            Log.secD(SemOneTouchApi.TAG, "findViewInfoArray: " + Arrays.toString(listQueryFindViewInfo.toArray()));
                            otchLongPressEvent.findViewInfos = listQueryFindViewInfo;
                            SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(otchLongPressEvent);
                        }
                        View touchedView = SemOneTouchApi.this.getTouchedView(this.rootView, otchLongPressEvent.touchedPoint);
                        OtchLongPressEvent otchLongPressEvent2 = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                        otchLongPressEvent2.touchedView = touchedView;
                        Object obj = otchLongPressEvent2.foundViewContent;
                        if (otchLongPressEvent2.touchedView != null && Boolean.FALSE.equals(obj)) {
                            Log.secD(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable found block view: " + otchLongPressEvent2.touchedView.getClass().getSimpleName());
                            SemOneTouchApi.this.onLongPressError();
                            return;
                        }
                        SemOneTouchApi.this.putTouchedViewInfoToBundle(this.context, bundle, otchLongPressEvent2.touchedView, obj);
                        if ((obj == null || (obj instanceof CharSequence)) && (this.rootView instanceof ViewGroup)) {
                            FindVideoViewEventInfo findVideoViewEventInfo = SemOneTouchApi.this.getFindVideoViewEventInfo(this.context, bundle);
                            View viewFindVideoView = SemOneTouchApi.this.findVideoView((ViewGroup) this.rootView, findVideoViewEventInfo.videoViewClassName, findVideoViewEventInfo.findTopToDown);
                            if (viewFindVideoView != null) {
                                SemOneTouchApi.this.putVideoViewInfoToBundle(this.context, bundle, findVideoViewEventInfo.videoViewClassName, viewFindVideoView);
                            }
                        }
                        otchLongPressEvent2.bundle = bundle;
                        SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(otchLongPressEvent2);
                        Bundle bundleSendOnLongPressedEvent = SemOneTouchApi.this.sendOnLongPressedEvent(this.context, otchLongPressEvent2, bundle);
                        if (!SemOneTouchApi.this.isEventSuccess(bundleSendOnLongPressedEvent).booleanValue()) {
                            Log.secE(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable call fail");
                            SemOneTouchApi.this.onLongPressError();
                            return;
                        }
                        if (bundleSendOnLongPressedEvent.getBoolean(SemOneTouchApi.BUNDLE_KEY_SKIP_DRAG_AND_DROP)) {
                            Log.secD(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable skip DragAndDrop, skip LongPressPhaseTwo");
                            return;
                        }
                        if (SemOneTouchApi.this.isSaveBitmapFileNeeded(otchLongPressEvent2.touchedView)) {
                            new Thread(SemOneTouchApi.this.new SaveBitmapFileRunnable(this.context, otchLongPressEvent2.touchedView)).start();
                        } else if (obj instanceof Bitmap) {
                            new Thread(SemOneTouchApi.this.new SaveBitmapFileRunnable(this.context, (Bitmap) obj)).start();
                        }
                        long jIntValue = ((otchLongPressEvent2.requestCode + ((Integer) SemOneTouchApi.this.mOneTouchLongPressThreshold.first).intValue()) + bundleSendOnLongPressedEvent.getInt(SemOneTouchApi.BUNDLE_KEY_LONG_PRESS_PHASE_TWO_CUSTOM_THRESHOLD, ((Integer) SemOneTouchApi.this.mOneTouchLongPressThreshold.second).intValue())) - System.currentTimeMillis();
                        SemOneTouchApi.this.mLongPressPhaseTwoRunnable = SemOneTouchApi.this.new LongPressPhaseTwoRunnable(this.context, this.rootView);
                        View view = this.rootView;
                        LongPressPhaseTwoRunnable longPressPhaseTwoRunnable = SemOneTouchApi.this.mLongPressPhaseTwoRunnable;
                        if (jIntValue <= 0) {
                            jIntValue = ((Integer) SemOneTouchApi.this.mOneTouchLongPressThreshold.second).intValue();
                        }
                        view.postDelayed(longPressPhaseTwoRunnable, jIntValue);
                        return;
                    }
                    Log.secE(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable state error: " + otchLongPressEvent);
                    SemOneTouchApi.this.onLongPressError();
                }
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable call error: " + e.getMessage(), e);
                SemOneTouchApi.this.onLongPressError();
            }
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LongPressPhaseTwoRunnable implements Runnable {
        private final Context context;
        private final View rootView;

        public LongPressPhaseTwoRunnable(Context context, View view) {
            this.context = context;
            this.rootView = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                final OtchLongPressEvent otchLongPressEvent = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (otchLongPressEvent != null && otchLongPressEvent.eventFlag == 1) {
                    Log.secD(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable");
                    otchLongPressEvent.eventFlag = 2;
                    SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(otchLongPressEvent);
                    new Thread(new Runnable() { // from class: com.samsung.android.widget.SemOneTouchApi$LongPressPhaseTwoRunnable$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$run$0(otchLongPressEvent);
                        }
                    }).start();
                    return;
                }
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable state error: " + otchLongPressEvent);
                SemOneTouchApi.this.onLongPressError();
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable call error: " + e.getMessage(), e);
                SemOneTouchApi.this.onLongPressError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(OtchLongPressEvent otchLongPressEvent) {
            Bundle bundleSendOnLongPressedEvent = SemOneTouchApi.this.sendOnLongPressedEvent(this.context, otchLongPressEvent, otchLongPressEvent.bundle);
            SemOneTouchApi.this.mLongPressPhaseTwoOnResponseRunnable = SemOneTouchApi.this.new LongPressPhaseTwoOnResponseRunnable(this.context, this.rootView, bundleSendOnLongPressedEvent);
            this.rootView.post(SemOneTouchApi.this.mLongPressPhaseTwoOnResponseRunnable);
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LongPressPhaseTwoOnResponseRunnable implements Runnable {
        private final Context context;
        private final Bundle resultBundle;
        private final View rootView;

        public LongPressPhaseTwoOnResponseRunnable(Context context, View view, Bundle bundle) {
            this.context = context;
            this.rootView = view;
            this.resultBundle = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                OtchLongPressEvent otchLongPressEvent = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (otchLongPressEvent != null && otchLongPressEvent.eventFlag == 2) {
                    Log.secD(SemOneTouchApi.TAG, "LongPressPhaseTwoOnResponseRunnable");
                    if (!SemOneTouchApi.this.isEventSuccess(this.resultBundle).booleanValue()) {
                        Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoOnResponseRunnable call fail");
                        SemOneTouchApi.this.onLongPressError();
                        return;
                    }
                    Bundle bundle = this.resultBundle;
                    ClipData clipData = bundle == null ? null : (ClipData) bundle.getParcelable(SemOneTouchApi.BUNDLE_KEY_CUSTOM_CLIP_DATA, ClipData.class);
                    if (clipData == null) {
                        SemOneTouchApi.this.onLongPressError();
                        return;
                    } else {
                        SemOneTouchApi.this.performDragAndDrop(this.context, this.resultBundle.getInt(SemOneTouchApi.BUNDLE_KEY_CUSTOM_DRAG_SHADOW_WIDTH, -1), this.rootView, otchLongPressEvent.touchedRawPoint, otchLongPressEvent.touchedView, clipData, (ParcelFileDescriptor) this.resultBundle.getParcelable(SemOneTouchApi.BUNDLE_KEY_TOUCHED_IMG_PFD, ParcelFileDescriptor.class), otchLongPressEvent.foundViewContent, new OtchDragAndDropResultCallback() { // from class: com.samsung.android.widget.SemOneTouchApi$LongPressPhaseTwoOnResponseRunnable$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.widget.SemOneTouchApi.OtchDragAndDropResultCallback
                            public final void onDragAndDropResult(boolean z) {
                                this.f$0.lambda$run$0(z);
                            }
                        });
                        return;
                    }
                }
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoOnResponseRunnable state error: " + otchLongPressEvent);
                SemOneTouchApi.this.onLongPressError();
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoOnResponseRunnable call error: " + e.getMessage(), e);
                SemOneTouchApi.this.onLongPressError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(boolean z) {
            if (z) {
                SemOneTouchApi.this.onLongPressCompleted();
                return;
            }
            Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoOnResponseRunnable drag fail");
            SemOneTouchApi.this.onLongPressError();
            SemOneTouchApi.this.onLongPressCanceled(this.context);
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    private boolean isBlocked(Context context, final View view) {
        String packageName;
        if (this.sIsFactoryBinary || (packageName = context.getPackageName()) == null || packageName.equals(PACKAGE_NAME_ONE_TOUCH) || packageName.equals("com.android.systemui")) {
            return true;
        }
        view.post(new Runnable() { // from class: com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$isBlocked$0(view);
            }
        });
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) {
            return false;
        }
        return packageName.equals(resolveInfoResolveActivity.activityInfo.packageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isBlocked$0(View view) {
        try {
            ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl == null || (viewRootImpl.getWindowFlags() & 8192) == 0) {
                return;
            }
            Log.secW(TAG, "FLAG_SECURE, blocked");
            this.mIsInitialized = false;
        } catch (Exception e) {
            Log.secE(TAG, "isBlocked failed", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(Context context, MotionEvent motionEvent, View view) {
        if (this.mIsInitialized.booleanValue() && this.mIsOneTouchSettingsEnabled.booleanValue()) {
            if (motionEvent.getActionMasked() == 0) {
                onLongPressStart(context, motionEvent, view);
            } else {
                if (this.mCurrentLongPressEvent.get() == null) {
                    return false;
                }
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 1) {
                    onLongPressCanceled(context);
                    clearEventState();
                } else if (actionMasked != 2) {
                    if (actionMasked == 3 || actionMasked == 5) {
                    }
                } else if (checkTouchedPointIsMoved(motionEvent)) {
                    onLongPressCanceled(context);
                    clearEventState();
                    return false;
                }
                OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
                return otchLongPressEvent != null && otchLongPressEvent.eventFlag == 4;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPressError() {
        Log.secW(TAG, "onLongPressError");
        clearEventState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPressCompleted() {
        OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
        if (otchLongPressEvent != null) {
            otchLongPressEvent.eventFlag = 4;
            this.mCurrentLongPressEvent = new AtomicReference<>(otchLongPressEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEventState() {
        try {
            this.mCurrentLongPressEvent = new AtomicReference<>();
            LongPressPhaseOneRunnable longPressPhaseOneRunnable = this.mLongPressPhaseOneRunnable;
            if (longPressPhaseOneRunnable != null) {
                longPressPhaseOneRunnable.remove();
            }
            LongPressPhaseTwoRunnable longPressPhaseTwoRunnable = this.mLongPressPhaseTwoRunnable;
            if (longPressPhaseTwoRunnable != null) {
                longPressPhaseTwoRunnable.remove();
            }
            LongPressPhaseTwoOnResponseRunnable longPressPhaseTwoOnResponseRunnable = this.mLongPressPhaseTwoOnResponseRunnable;
            if (longPressPhaseTwoOnResponseRunnable != null) {
                longPressPhaseTwoOnResponseRunnable.remove();
            }
            CheckRestrictTouchRunnable checkRestrictTouchRunnable = this.mCheckRestrictTouchRunnable;
            if (checkRestrictTouchRunnable != null) {
                checkRestrictTouchRunnable.remove();
            }
        } catch (Exception e) {
            Log.secE(TAG, "clearEventState failed", e);
        }
    }

    private void onLongPressStart(Context context, MotionEvent motionEvent, View view) {
        OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
        if (otchLongPressEvent != null) {
            Log.secE(TAG, "onLongPressStart state error: " + otchLongPressEvent + ", replace LongPressEvent");
            clearEventState();
        }
        this.mCurrentLongPressEvent = new AtomicReference<>(new OtchLongPressEvent(System.currentTimeMillis(), 0, motionEvent));
        LongPressPhaseOneRunnable longPressPhaseOneRunnable = new LongPressPhaseOneRunnable(context, view);
        this.mLongPressPhaseOneRunnable = longPressPhaseOneRunnable;
        view.postDelayed(longPressPhaseOneRunnable, this.mOneTouchLongPressThreshold.first.intValue());
        Runnable runnable = this.mCheckRestrictTouchRunnable;
        if (runnable != null) {
            view.postDelayed(runnable, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPressCanceled(Context context) {
        OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
        clearEventState();
        if (otchLongPressEvent == null || otchLongPressEvent.eventFlag == 0) {
            return;
        }
        if (otchLongPressEvent.bundle != null) {
            otchLongPressEvent.bundle.putInt(BUNDLE_KEY_PREVIOUS_LONG_PRESS_FLAG, otchLongPressEvent.eventFlag);
        }
        otchLongPressEvent.eventFlag = 3;
        sendOnLongPressedEvent(context, otchLongPressEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putTouchedViewInfoToBundle(Context context, Bundle bundle, View view, Object obj) {
        if (view == null) {
            return;
        }
        try {
            if (obj instanceof CharSequence) {
                bundle.putString(BUNDLE_KEY_TOUCHED_TEXT, ((CharSequence) obj).toString());
            }
            bundle.putString(BUNDLE_KEY_TOUCHED_WIDGET_NAME, view.getClass().getName());
            bundle.putInt(BUNDLE_KEY_TOUCHED_VIEW_SIZE, view.getWidth() * view.getHeight());
            try {
                if (view.getId() != -1) {
                    bundle.putString(BUNDLE_KEY_TOUCHED_WIDGET_ID, context.getResources().getResourceName(view.getId()));
                }
            } catch (Resources.NotFoundException unused) {
                Log.secW(TAG, "NotFoundException: " + view.getId());
            }
        } catch (Exception e) {
            Log.secE(TAG, "putTouchedViewInfoToBundle fail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putVideoViewInfoToBundle(Context context, Bundle bundle, String str, View view) {
        try {
            String name = view.getClass().getName();
            if (view instanceof SurfaceView) {
                bundle.putString(BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS, SurfaceView.class.getName());
            } else if (view instanceof TextureView) {
                TextureView textureView = (TextureView) view;
                long timestamp = textureView.getSurfaceTexture() != null ? textureView.getSurfaceTexture().getTimestamp() : -1L;
                if (textureView.getBitmap() != null && timestamp > 0 && !TextUtils.equals(str, name)) {
                    Log.secW(TAG, "VideoView: Found TextureView but consider not a video view, class: " + name + " timestamp: " + timestamp);
                    return;
                }
                bundle.putString(BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS, TextureView.class.getName());
            }
            bundle.putString(BUNDLE_KEY_VIDEO_VIEW_WIDGET_NAME, name);
            if (view.getId() != -1) {
                bundle.putString(BUNDLE_KEY_VIDEO_VIEW_WIDGET_ID, context.getResources().getResourceName(view.getId()));
            }
        } catch (Exception e) {
            Log.secE(TAG, "putVideoViewInfoToBundle fail: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putRootViewInfoToBundle(Context context, Bundle bundle, View view, OtchLongPressEvent otchLongPressEvent) {
        try {
            otchLongPressEvent.packageName = context.getPackageName();
            bundle.putParcelable(BUNDLE_KEY_RAW_TOUCHED_POINT, otchLongPressEvent.touchedRawPoint);
            bundle.putString(BUNDLE_KEY_APP_PROCESS_NAME, context.getApplicationInfo().processName);
            Activity activity = parseActivity(context, view);
            if (activity != null) {
                otchLongPressEvent.componentName = activity.getComponentName().getClassName();
                WindowManager windowManager = activity.getWindowManager();
                if (windowManager != null) {
                    bundle.putParcelable(BUNDLE_KEY_WINDOW_RECT, windowManager.getCurrentWindowMetrics().getBounds());
                }
            }
            if (otchLongPressEvent.componentName != null) {
                bundle.putString(BUNDLE_KEY_ACTIVITY_NAME, otchLongPressEvent.componentName);
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
            if (layoutParams != null) {
                bundle.putInt(BUNDLE_KEY_WINDOW_TYPE, layoutParams.type);
            }
        } catch (Exception e) {
            Log.secE(TAG, "putRootViewInfoToBundle fail: " + e.getMessage());
        }
    }

    private Bundle sendOnLongPressedEvent(Context context, OtchLongPressEvent otchLongPressEvent) {
        return sendOnLongPressedEvent(context, otchLongPressEvent, otchLongPressEvent.bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle sendOnLongPressedEvent(Context context, OtchLongPressEvent otchLongPressEvent, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putLong(BUNDLE_KEY_REQUEST_CODE, otchLongPressEvent.requestCode);
        bundle.putInt(BUNDLE_KEY_LONG_PRESS_FLAG, otchLongPressEvent.eventFlag);
        bundle.putInt(BUNDLE_KEY_EVENT_TOOL_TYPE, otchLongPressEvent.eventToolType);
        try {
            return context.getContentResolver().call(OTCH_EXTERNAL_EVENT_URI, CALL_METHOD_ON_LONG_PRESSED, (String) null, bundle);
        } catch (Exception e) {
            Log.secE(TAG, "sendOnLongPressedEvent fail: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean isEventSuccess(Bundle bundle) {
        if (bundle == null) {
            Log.secE(TAG, "call One Touch error, bundle null");
        }
        return Boolean.valueOf(bundle != null && bundle.getBoolean(BUNDLE_KEY_RESULT_BOOLEAN));
    }

    protected static class FindViewInfo implements Parcelable {
        private final String className;
        private final Integer fieldLevel;
        private final String fieldName;
        private final String methodName;
        private final String viewType;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public FindViewInfo(String str, String str2, String str3, Integer num, String str4) {
            this.className = str;
            this.viewType = str2;
            this.fieldName = str3;
            this.fieldLevel = num;
            this.methodName = str4;
        }

        protected FindViewInfo(Parcel parcel) {
            this.className = parcel.readString();
            this.viewType = parcel.readString();
            this.fieldName = parcel.readString();
            this.fieldLevel = Integer.valueOf(parcel.readInt());
            this.methodName = parcel.readString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.className);
            parcel.writeString(this.viewType);
            parcel.writeString(this.fieldName);
            parcel.writeInt(this.fieldLevel.intValue());
            parcel.writeString(this.methodName);
        }

        public String toString() {
            return "FindViewInfo( className=" + this.className + ", viewType=" + this.viewType + ", fieldName=" + this.fieldName + ", fieldLevel=" + this.fieldLevel + ", methodName=" + this.methodName + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static long getAppVersionCode(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            Log.secE(TAG, "getAppVersionCode fail: " + e.getMessage());
            return 0L;
        }
    }

    List<FindViewInfo> queryFindViewInfo(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.samsung.android.onetouch.externalEvent/query_find_view_info"), new String[]{"pkg", PROJECTION_VERSION_CODE}, null, new String[]{str, String.valueOf(getAppVersionCode(context, str))}, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        do {
                            int columnIndex = cursorQuery.getColumnIndex(COLUMN_CLASS_NAME);
                            String string = columnIndex != -1 ? cursorQuery.getString(columnIndex) : null;
                            int columnIndex2 = cursorQuery.getColumnIndex("type");
                            String string2 = columnIndex2 != -1 ? cursorQuery.getString(columnIndex2) : VIEW_TYPE_TEXT_VIEW;
                            int columnIndex3 = cursorQuery.getColumnIndex(COLUMN_FIELD_NAME);
                            String string3 = columnIndex3 != -1 ? cursorQuery.getString(columnIndex3) : null;
                            int columnIndex4 = cursorQuery.getColumnIndex("level");
                            Integer numValueOf = Integer.valueOf(columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0);
                            int columnIndex5 = cursorQuery.getColumnIndex("method");
                            arrayList.add(new FindViewInfo(string, string2, string3, numValueOf, columnIndex5 != -1 ? cursorQuery.getString(columnIndex5) : null));
                        } while (cursorQuery.moveToNext());
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
                return arrayList;
            }
        } catch (Exception e) {
            Log.secE(TAG, "sendOnLongPressedEvent fail: " + e.getMessage());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FindVideoViewEventInfo getFindVideoViewEventInfo(Context context, Bundle bundle) {
        Bundle bundleCall;
        try {
            bundleCall = context.getContentResolver().call(OTCH_EXTERNAL_EVENT_URI, CALL_METHOD_CUSTOM_VIDEO_CLASS_NAME, (String) null, bundle);
        } catch (Exception e) {
            Log.secE(TAG, "sendOnLongPressedEvent fail: " + e.getMessage());
            bundleCall = null;
        }
        if (bundleCall == null) {
            Log.secE(TAG, "sendOnLongPressedEvent fail, result null");
            return new FindVideoViewEventInfo(true, null);
        }
        return new FindVideoViewEventInfo(bundleCall.getBoolean(BUNDLE_KEY_VIDEO_VIEW_FINDING_TOP_TO_DOWN, true), bundleCall.getString(BUNDLE_KEY_VIDEO_VIEW_ClASS_NAME, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSaveBitmapFileNeeded(View view) {
        if (view == null) {
            return false;
        }
        return view instanceof ImageView;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0093 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0094 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap drawable2Bitmap(View view, Drawable drawable) {
        Bitmap bitmap;
        Drawable.ConstantState constantState;
        Drawable drawableNewDrawable;
        int iWidth;
        int iHeight;
        try {
            bitmap = drawable instanceof BitmapDrawable ? ((BitmapDrawable) drawable).getBitmap() : null;
        } catch (Exception e) {
            e = e;
            bitmap = null;
        }
        if (bitmap != null) {
            return bitmap;
        }
        try {
            constantState = drawable.getConstantState();
            if (constantState == null) {
                Log.secW(TAG, "drawable2Bitmap, constantState null");
                drawableNewDrawable = drawable;
            } else {
                drawableNewDrawable = constantState.newDrawable();
            }
            iWidth = drawableNewDrawable.getBounds().width();
            iHeight = drawableNewDrawable.getBounds().height();
            if (iWidth <= 0 || iHeight <= 0) {
                iWidth = view.getMeasuredWidth();
                iHeight = view.getMeasuredHeight();
            }
        } catch (Exception e2) {
            e = e2;
            Log.secE(TAG, "drawable2Bitmap fail: " + e.getMessage(), e);
            if (isBitmapValid(bitmap, true)) {
            }
        }
        if (iWidth > 0 && iHeight > 0) {
            bitmap = Bitmap.createBitmap(iWidth, iHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Rect bounds = constantState == null ? drawable.getBounds() : null;
            drawableNewDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawableNewDrawable.draw(canvas);
            if (bounds != null) {
                drawable.setBounds(bounds);
            }
            if (isBitmapValid(bitmap, true)) {
                return null;
            }
            return bitmap;
        }
        Log.secE(TAG, "drawable2Bitmap fail");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Bitmap bitmap = null;
        try {
            Drawable drawable = imageView.getDrawable();
            Bitmap bitmapDrawable2Bitmap = drawable != null ? drawable2Bitmap(imageView, drawable) : null;
            if (bitmapDrawable2Bitmap == null) {
                try {
                    bitmapDrawable2Bitmap = drawViewOnBitmap(imageView);
                } catch (Exception e) {
                    e = e;
                    bitmap = bitmapDrawable2Bitmap;
                    Log.secE(TAG, "getBitmapFromView fail: " + e.getMessage(), e);
                    return bitmap;
                }
            }
            if (bitmapDrawable2Bitmap.isRecycled()) {
                return null;
            }
            return bitmapDrawable2Bitmap;
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveBitmapFile(Context context, Bitmap bitmap) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFile = context.getContentResolver().openFile(Uri.parse(OTCH_EXTERNAL_EVENT_AUTHORITY + File.separator + OPEN_URI_SAVE_TOUCHED_IMG + File.separator + "0"), String.valueOf(805306368), cancellationSignal);
            try {
                if (parcelFileDescriptorOpenFile == null) {
                    Log.secE(TAG, "openFile fail");
                    if (parcelFileDescriptorOpenFile != null) {
                        parcelFileDescriptorOpenFile.close();
                    }
                    return false;
                }
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptorOpenFile.getFileDescriptor());
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    Log.secE(TAG, "bitmap.compress fail: " + e.getMessage(), e);
                }
                if (parcelFileDescriptorOpenFile == null) {
                    return true;
                }
                parcelFileDescriptorOpenFile.close();
                return true;
            } finally {
            }
        } catch (IOException e2) {
            Log.secE(TAG, "saveBitmapFile fail: " + e2.getMessage(), e2);
            return false;
        }
    }

    private Bitmap base64ToBitmap(String str) {
        try {
            byte[] bArrDecode = Base64.decode(str, 0);
            return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
        } catch (Exception e) {
            Log.secE(TAG, "base64ToBitmap fail: " + e.getMessage(), e);
            return null;
        }
    }

    private class SaveBitmapFileRunnable implements Runnable {
        private final Context context;
        private Bitmap foundBitmap;
        private View touchedView;

        public SaveBitmapFileRunnable(Context context, View view) {
            this.foundBitmap = null;
            this.context = context;
            this.touchedView = view;
        }

        public SaveBitmapFileRunnable(Context context, Bitmap bitmap) {
            this.touchedView = null;
            this.context = context;
            this.foundBitmap = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                OtchLongPressEvent otchLongPressEvent = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (otchLongPressEvent == null) {
                    Log.secW(SemOneTouchApi.TAG, "CurrentLongPressEvent is null, abandon SaveBitmapFile");
                    return;
                }
                OtchLongPressEvent otchLongPressEventM9723clone = otchLongPressEvent.m9723clone();
                Bitmap bitmapFromImageView = this.foundBitmap;
                if (bitmapFromImageView == null) {
                    View view = this.touchedView;
                    if (view instanceof ImageView) {
                        bitmapFromImageView = SemOneTouchApi.this.getBitmapFromImageView((ImageView) view);
                        otchLongPressEventM9723clone.foundViewContent = bitmapFromImageView;
                    }
                }
                if (bitmapFromImageView == null) {
                    Log.secE(SemOneTouchApi.TAG, "getBitmapFromView fail");
                    return;
                }
                otchLongPressEvent.foundViewContent = bitmapFromImageView;
                SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(otchLongPressEvent);
                boolean zSaveBitmapFile = SemOneTouchApi.this.saveBitmapFile(this.context, bitmapFromImageView);
                Bundle bundle = new Bundle();
                bundle.putInt(SemOneTouchApi.BUNDLE_KEY_SAVE_IMAGE_RESULT, zSaveBitmapFile ? 1 : 0);
                otchLongPressEventM9723clone.eventFlag = 1001;
                SemOneTouchApi.this.sendOnLongPressedEvent(this.context, otchLongPressEventM9723clone, bundle);
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "SaveBitmapFileRunnable fail: " + e.getMessage(), e);
            }
        }
    }

    public static int pxToDp(int i) {
        return (int) (i / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int i) {
        return (int) (i * Resources.getSystem().getDisplayMetrics().density);
    }

    private class ScaledDragShadowBuilder extends View.DragShadowBuilder {
        private float mCustomDragShadowWidth;

        public ScaledDragShadowBuilder(SemOneTouchApi semOneTouchApi, View view, int i) {
            super(view);
            this.mCustomDragShadowWidth = i;
        }

        @Override // android.view.View.DragShadowBuilder
        public void onDrawShadow(Canvas canvas) {
            View view = getView();
            if (view == null) {
                return;
            }
            int iPxToDp = SemOneTouchApi.pxToDp(view.getWidth());
            int iPxToDp2 = SemOneTouchApi.pxToDp(view.getHeight());
            if (iPxToDp2 <= iPxToDp ? iPxToDp2 > this.mCustomDragShadowWidth : iPxToDp > this.mCustomDragShadowWidth) {
                float f = iPxToDp2 > iPxToDp ? this.mCustomDragShadowWidth / iPxToDp : this.mCustomDragShadowWidth / iPxToDp2;
                canvas.scale(f, f, canvas.getWidth() / 2.0f, canvas.getHeight() / 2.0f);
            }
            super.onDrawShadow(canvas);
        }
    }

    public int calculateInSampleSize(ParcelFileDescriptor parcelFileDescriptor, int i, int i2) {
        int i3 = 1;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), null, options);
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            if (i5 <= i2 && i4 <= i) {
                return 1;
            }
            int i6 = i5 / 2;
            int i7 = i4 / 2;
            while (i6 / i3 >= i2 && i7 / i3 >= i) {
                i3 *= 2;
            }
            return i3;
        } catch (Exception e) {
            Log.secE(TAG, "calculateInSampleSize failed", e);
            return i3;
        }
    }

    private Bitmap getBitmapFromFile(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            Log.secE(TAG, "pfd null");
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = calculateInSampleSize(parcelFileDescriptor, 360, 360);
            return BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), null, options);
        } catch (Exception e) {
            Log.secE(TAG, "getBitmapFromFile failed, abandon SaveBitmapFile", e);
            return null;
        }
    }

    private ImageView getCustomImageView(Context context, Bitmap bitmap, PointF pointF, int i) {
        if (bitmap == null) {
            return null;
        }
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        int iDpToPx = dpToPx(i);
        if (bitmap.getHeight() > iDpToPx || bitmap.getWidth() > iDpToPx) {
            if (bitmap.getHeight() > bitmap.getWidth()) {
                imageView.setMaxWidth(iDpToPx);
            } else {
                imageView.setMaxHeight(iDpToPx);
            }
        }
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(bitmap);
        imageView.setX(pointF.x);
        imageView.setY(pointF.y);
        return imageView;
    }

    private FrameLayout getCustomTextThumbnailView(Context context, String str, PointF pointF, int i) {
        FrameLayout frameLayout;
        TextView textView;
        if (str.isEmpty() || (frameLayout = (FrameLayout) View.inflate(context, R.layout.sem_text_drag_thumbnail, null)) == null || (textView = (TextView) frameLayout.getChildAt(1)) == null) {
            return null;
        }
        textView.setMaxWidth(dpToPx(i));
        if (str.length() > 100) {
            str = str.substring(0, 100);
        }
        textView.lambda$setTextAsync$0(str);
        frameLayout.setX(pointF.x);
        frameLayout.setY(pointF.y);
        return frameLayout;
    }

    private void setShadowViewLayout(View view) throws Resources.NotFoundException {
        view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        view.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.invalidate();
    }

    private void dragWithCustomShadowView(final View view, final View view2, final ClipData clipData, final OtchDragAndDropResultCallback otchDragAndDropResultCallback) throws Resources.NotFoundException {
        if (view == null) {
            otchDragAndDropResultCallback.onDragAndDropResult(false);
        } else {
            setShadowViewLayout(view);
            ((ViewGroup) view2).post(new Runnable() { // from class: com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    otchDragAndDropResultCallback.onDragAndDropResult(view2.startDragAndDrop(clipData, new View.DragShadowBuilder(view), null, 768));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDragAndDrop(Context context, final int i, View view, PointF pointF, final View view2, final ClipData clipData, ParcelFileDescriptor parcelFileDescriptor, Object obj, final OtchDragAndDropResultCallback otchDragAndDropResultCallback) {
        if (view2 != null) {
            try {
                if (!Boolean.TRUE.equals(obj)) {
                    if (obj instanceof CharSequence) {
                        dragWithCustomShadowView(getCustomTextThumbnailView(context, ((CharSequence) obj).toString(), pointF, i), view, clipData, otchDragAndDropResultCallback);
                        return;
                    } else if (obj instanceof Bitmap) {
                        dragWithCustomShadowView(getCustomImageView(context, (Bitmap) obj, pointF, i), view, clipData, otchDragAndDropResultCallback);
                        return;
                    } else {
                        view2.post(new Runnable() { // from class: com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$performDragAndDrop$2(view2, clipData, i, otchDragAndDropResultCallback);
                            }
                        });
                        return;
                    }
                }
            } catch (Exception e) {
                Log.secE(TAG, "dragTouchedView failed", e);
                return;
            }
        }
        ClipDescription description = clipData.getDescription();
        ClipData.Item itemAt = clipData.getItemAt(0);
        if (description != null && itemAt != null) {
            if (description.hasMimeType("text/*") && itemAt.getText() != null) {
                dragWithCustomShadowView(getCustomTextThumbnailView(context, itemAt.getText().toString(), pointF, i), view, clipData, otchDragAndDropResultCallback);
                return;
            } else if (description.hasMimeType(ContentType.IMAGE_UNSPECIFIED)) {
                dragWithCustomShadowView(getCustomImageView(context, getBitmapFromFile(parcelFileDescriptor), pointF, i), view, clipData, otchDragAndDropResultCallback);
                return;
            } else {
                otchDragAndDropResultCallback.onDragAndDropResult(false);
                return;
            }
        }
        otchDragAndDropResultCallback.onDragAndDropResult(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDragAndDrop$2(View view, ClipData clipData, int i, OtchDragAndDropResultCallback otchDragAndDropResultCallback) {
        otchDragAndDropResultCallback.onDragAndDropResult(view.startDragAndDrop(clipData, new ScaledDragShadowBuilder(this, view, i), view, 768));
    }

    private float mm2px(Context context, float f) {
        return (f / 25.4f) * context.getResources().getDisplayMetrics().densityDpi;
    }

    private boolean checkTouchedPointIsMoved(MotionEvent motionEvent) {
        PointF pointF = this.mCurrentLongPressEvent.get().touchedPoint;
        return Math.abs(motionEvent.getX() - pointF.x) > ((float) this.mTouchEventMoveMaxPixel) || Math.abs(motionEvent.getY() - pointF.y) > ((float) this.mTouchEventMoveMaxPixel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getTouchedView(View view, PointF pointF) {
        try {
            return view.semDispatchFindView(pointF, true, this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    private Context getContextFromDecorContext(Context context) throws NoSuchFieldException {
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0020 A[Catch: Exception -> 0x0026, TRY_LEAVE, TryCatch #0 {Exception -> 0x0026, blocks: (B:3:0x0001, B:5:0x0005, B:13:0x0020, B:7:0x000a, B:9:0x000e, B:11:0x001a), top: B:19:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0025 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Activity parseActivity(Context context, View view) {
        Activity activity;
        Activity activity2 = null;
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                if (context instanceof DecorContext) {
                    context = getContextFromDecorContext(view.getContext());
                    if (context instanceof Activity) {
                        activity = (Activity) context;
                    }
                }
                return activity2 != null ? getActivityFromContextWrapper(context) : activity2;
            }
            activity2 = activity;
            if (activity2 != null) {
            }
        } catch (Exception e) {
            Log.secE(TAG, "parseActivity failed", e);
            return activity2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View findVideoView(ViewGroup viewGroup, String str, boolean z) {
        View viewFindVideoView = null;
        try {
            int childCount = viewGroup.getChildCount();
            if (z) {
                for (int i = childCount - 1; i >= 0; i--) {
                    View childAt = viewGroup.getChildAt(i);
                    if (!(childAt instanceof SurfaceView) && !(childAt instanceof TextureView) && !TextUtils.equals(childAt.getClass().getName(), str)) {
                        if ((childAt instanceof ViewGroup) && (viewFindVideoView = findVideoView((ViewGroup) childAt, str, true)) != null) {
                            return viewFindVideoView;
                        }
                    }
                    return childAt;
                }
                return viewFindVideoView;
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt2 = viewGroup.getChildAt(i2);
                if (!(childAt2 instanceof SurfaceView) && !(childAt2 instanceof TextureView) && !TextUtils.equals(childAt2.getClass().getName(), str)) {
                    if ((childAt2 instanceof ViewGroup) && (viewFindVideoView = findVideoView((ViewGroup) childAt2, str, false)) != null) {
                        return viewFindVideoView;
                    }
                }
                return childAt2;
            }
            return viewFindVideoView;
        } catch (Exception e) {
            Log.secE(TAG, "findVideoView failed", e);
            return null;
        }
    }

    private static FindViewInfo checkFindViewInfoList(View view, List<FindViewInfo> list) {
        if (list == null) {
            return null;
        }
        String name = view.getClass().getName();
        for (FindViewInfo findViewInfo : list) {
            if (findViewInfo instanceof FindViewInfo) {
                FindViewInfo findViewInfo2 = findViewInfo;
                if (findViewInfo2.className != null && name.startsWith(findViewInfo2.className)) {
                    return findViewInfo2;
                }
            }
        }
        return null;
    }

    private Object getReflectedResult(View view, FindViewInfo findViewInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object objInvoke;
        try {
            Class<?> cls = view.getClass();
            if (findViewInfo.methodName != null) {
                Class[] clsArr = new Class[0];
                objInvoke = cls.getMethod(findViewInfo.methodName, null).invoke(view, null);
            } else if (findViewInfo.fieldName != null) {
                int iIntValue = findViewInfo.fieldLevel.intValue();
                Field declaredField = iIntValue != 0 ? iIntValue != 1 ? iIntValue != 2 ? null : cls.getSuperclass().getSuperclass().getDeclaredField(findViewInfo.fieldName) : cls.getSuperclass().getDeclaredField(findViewInfo.fieldName) : cls.getDeclaredField(findViewInfo.fieldName);
                if (declaredField == null) {
                    Log.secW(TAG, "getFiled null");
                    return null;
                }
                declaredField.setAccessible(true);
                objInvoke = declaredField.get(view);
            } else {
                objInvoke = null;
            }
            StringBuilder sb = new StringBuilder("reflectedObject: ");
            sb.append(objInvoke != null);
            Log.secD(TAG, sb.toString());
            if (objInvoke != null) {
                return objInvoke;
            }
            Log.secW(TAG, "get Object null");
            return null;
        } catch (Exception e) {
            Log.secE(TAG, "getFiledObject: " + e.getMessage());
            return null;
        }
    }

    private String getTextFromTextView(Object obj, View view, PointF pointF) {
        if (!(obj instanceof CharSequence)) {
            return null;
        }
        String string = obj.toString();
        int length = string.length();
        if (view instanceof TextView) {
            int offsetForPosition = ((TextView) view).getOffsetForPosition(pointF.x, pointF.y);
            if (offsetForPosition == length) {
                offsetForPosition = length - 1;
            }
            if (length >= 2000) {
                if (offsetForPosition < 1000) {
                    return string.subSequence(0, 2000).toString();
                }
                return string.subSequence(offsetForPosition - 1000, Math.min(length, offsetForPosition + 1000)).toString();
            }
        } else if (length > 2000) {
            return string.substring(0, 2000);
        }
        return string;
    }

    public boolean isBitmapValid(Bitmap bitmap, boolean z) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int pixel = bitmap.getPixel(0, 0);
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                int pixel2 = bitmap.getPixel(random.nextInt(width), random.nextInt(height));
                if (z) {
                    if (((pixel2 >> 24) & 255) != 0) {
                        return true;
                    }
                } else if (pixel2 != pixel) {
                    return true;
                }
            }
            Log.secW(TAG, "Bitmap invalid, checkTransparency: " + z);
        } catch (Exception e) {
            Log.secE(TAG, "isBitmapValid failed", e);
        }
        return false;
    }

    private Bitmap drawViewOnBitmap(View view) {
        Log.secD(TAG, "draw");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        view.draw(canvas);
        if (((canvas.isHardwareAccelerated() || canvas.getSaveCount() != 1) && isBitmapValid(bitmapCreateBitmap, true)) || isBitmapValid(bitmapCreateBitmap, false)) {
            return bitmapCreateBitmap;
        }
        Log.secE(TAG, "draw fail");
        return null;
    }

    private Bitmap getBitmapFromView(View view, Object obj) {
        try {
            if (obj instanceof Bitmap) {
                Bitmap bitmap = (Bitmap) obj;
                return bitmap.copy(bitmap.getConfig(), true);
            }
            if (obj instanceof Drawable) {
                return drawable2Bitmap(view, (Drawable) obj);
            }
            return drawViewOnBitmap(view);
        } catch (Exception e) {
            Log.secE(TAG, "getBitmapFromView failed: " + e.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Object getViewContentInternal(Context context, String str, View view, PointF pointF, Object obj) {
        char c;
        try {
        } catch (Exception e) {
            Log.secE(TAG, "getViewContentInternal failed: " + e.getMessage());
        }
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return getTextFromTextView(textView.getText(), textView, pointF);
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            if (imageView.getDrawable() != null) {
                return Boolean.TRUE;
            }
            try {
                Canvas canvas = new Canvas();
                imageView.draw(canvas);
                if (canvas.isHardwareAccelerated() || canvas.getSaveCount() != 1) {
                    return Boolean.TRUE;
                }
            } catch (IllegalStateException | UnsupportedOperationException e2) {
                Log.secW(TAG, "getViewContentInternal draw Exception", e2);
                return null;
            }
        } else if (view instanceof WebView) {
            return evaluateHtmlData(context, (WebView) view, pointF, getWebViewTextCallback(context));
        }
        FindViewInfo findViewInfoCheckFindViewInfoList = checkFindViewInfoList(view, this.mCurrentLongPressEvent.get().findViewInfos);
        if (findViewInfoCheckFindViewInfoList != null) {
            Log.secD(TAG, "findViewInfo: " + findViewInfoCheckFindViewInfoList);
            String str2 = findViewInfoCheckFindViewInfoList.viewType;
            switch (str2.hashCode()) {
                case -1142205150:
                    if (!str2.equals(VIEW_TYPE_BLOCK)) {
                        c = 65535;
                        break;
                    } else {
                        c = 3;
                        break;
                    }
                case 66104940:
                    if (str2.equals(VIEW_TYPE_WEB_VIEW)) {
                        c = 0;
                        break;
                    }
                    break;
                case 670921973:
                    if (str2.equals(VIEW_TYPE_IMAGE_VIEW)) {
                        c = 2;
                        break;
                    }
                    break;
                case 1540240509:
                    if (str2.equals(VIEW_TYPE_TEXT_VIEW)) {
                        c = 1;
                        break;
                    }
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                return invokeHtmlData(context, view, pointF, getWebViewTextCallback(context));
            }
            if (c == 1) {
                Object reflectedResult = getReflectedResult(view, findViewInfoCheckFindViewInfoList);
                if (reflectedResult != null) {
                    return getTextFromTextView(reflectedResult, view, pointF);
                }
                return null;
            }
            if (c == 2) {
                return getBitmapFromView(view, getReflectedResult(view, findViewInfoCheckFindViewInfoList));
            }
            if (c != 3) {
                return null;
            }
            Log.secD(TAG, "block: " + view.getClass().getName());
            return Boolean.FALSE;
        }
        return null;
    }

    @Override // com.samsung.android.widget.ISemTouchApi
    public boolean getViewContent(Context context, String str, View view, PointF pointF, Object obj) {
        Object viewContentInternal = getViewContentInternal(context, str, view, pointF, obj);
        if (viewContentInternal == null) {
            return false;
        }
        OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
        otchLongPressEvent.foundViewContent = viewContentInternal;
        this.mCurrentLongPressEvent = new AtomicReference<>(otchLongPressEvent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFingerPrintInDisplay(Context context) {
        int iSemGetIconBottomMargin;
        boolean z;
        try {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null) {
                z = FingerprintManager.semGetSensorPosition() == 2;
                iSemGetIconBottomMargin = fingerprintManager.semGetIconBottomMargin();
            } else {
                iSemGetIconBottomMargin = 0;
                z = false;
            }
        } catch (Exception unused) {
        }
        return z && iSemGetIconBottomMargin > 0;
    }

    private class SaveWebViewContentRunnable implements Runnable {
        private final Context context;
        private final long requestCode;
        private final String text;

        public SaveWebViewContentRunnable(Context context, String str, long j) {
            this.context = context;
            this.requestCode = j;
            this.text = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.secD(SemOneTouchApi.TAG, "SaveWebViewContentRunnable");
            try {
                OtchLongPressEvent otchLongPressEvent = (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (otchLongPressEvent != null && this.requestCode == otchLongPressEvent.requestCode && otchLongPressEvent.bundle != null) {
                    OtchLongPressEvent otchLongPressEventM9723clone = otchLongPressEvent.m9723clone();
                    Bundle bundle = otchLongPressEventM9723clone.bundle;
                    bundle.putString(SemOneTouchApi.BUNDLE_KEY_WEB_VIEW_CONTENT, this.text);
                    otchLongPressEventM9723clone.eventFlag = 1002;
                    SemOneTouchApi.this.sendOnLongPressedEvent(this.context, otchLongPressEventM9723clone, bundle);
                    return;
                }
                StringBuilder sb = new StringBuilder("SaveWebViewContentRunnable requestCode mismatch: ");
                sb.append(this.requestCode);
                sb.append(" current: ");
                sb.append(otchLongPressEvent != null ? Long.valueOf(otchLongPressEvent.requestCode) : PerfettoProtoLogImpl.NULL_STRING);
                Log.secE(SemOneTouchApi.TAG, sb.toString());
            } catch (Exception e) {
                Log.secE(SemOneTouchApi.TAG, "SaveWebViewContentRunnable fail: " + e.getMessage(), e);
            }
        }
    }

    private ValueCallback<String> getWebViewTextCallback(final Context context) {
        OtchLongPressEvent otchLongPressEvent = this.mCurrentLongPressEvent.get();
        if (otchLongPressEvent == null) {
            Log.secE(TAG, "getWebViewTextCallback eventInfo null");
            return null;
        }
        final long j = otchLongPressEvent.requestCode;
        return new ValueCallback() { // from class: com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda1
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                this.f$0.lambda$getWebViewTextCallback$3(context, j, (String) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWebViewTextCallback$3(Context context, long j, String str) {
        try {
            new Thread(new SaveWebViewContentRunnable(context, str, j)).start();
        } catch (Exception e) {
            Log.secE(TAG, "getWebViewTextCallback : " + e.getMessage(), e);
        }
    }

    private boolean isProviderLegal(Context context, Uri uri) {
        try {
            boolean zIsDualAppId = SemDualAppManager.isDualAppId(context.getUserId());
            if (zIsDualAppId) {
                Log.secD(TAG, "isDualAppId");
                context = context.createContextAsUser(UserHandle.semOf(0), 0);
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && uri != null && uri.getAuthority() != null) {
                ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
                if (providerInfoResolveContentProvider == null) {
                    Log.secW(TAG, "providerInfo null");
                    return false;
                }
                String str = providerInfoResolveContentProvider.packageName;
                if (!TextUtils.equals(str, PACKAGE_NAME_ONE_TOUCH)) {
                    Log.secW(TAG, "packageName not legal: " + str);
                    return false;
                }
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                if (packageInfo == null) {
                    Log.secW(TAG, "packageInfo null");
                    return false;
                }
                if ((packageInfo.applicationInfo.flags & 1) != 0) {
                    if (packageManager.checkSignatures("android", str) != 0) {
                        Log.secW(TAG, "package not platform: " + str);
                        return false;
                    }
                    if (!zIsDualAppId) {
                        int i = packageManager.getApplicationInfo(str, 0).uid;
                        if (packageManager.checkSignatures(1000, i) != 0) {
                            Log.secW(TAG, "package not uid signature: " + str + " uid: " + i);
                            return false;
                        }
                    }
                    return true;
                }
                Log.secW(TAG, "package illegal flag: " + str);
            }
            return false;
        } catch (Exception e) {
            Log.secE(TAG, "Exception: " + e.getMessage(), e);
            return false;
        }
    }

    private String getWebViewScript(Context context, PointF pointF, float f) {
        int columnIndex;
        Uri uri = Uri.parse("content://com.samsung.android.onetouch.externalEvent/query_webview_javascript");
        String string = null;
        if (!isProviderLegal(context, uri)) {
            Log.secE(TAG, "Provider illegal");
            return null;
        }
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{PROJECTION_POINTF_X, PROJECTION_POINTF_Y, PROJECTION_TOUCHED_VIEW_SCALE}, null, new String[]{String.valueOf(pointF.x), String.valueOf(pointF.y), String.valueOf(f)}, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("method")) != -1) {
                        string = cursorQuery.getString(columnIndex);
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception e) {
            Log.secE(TAG, "getWebViewScript fail: " + e.getMessage(), e);
        }
        if (TextUtils.isEmpty(string)) {
            Log.secE(TAG, "getWebViewScript fail");
        }
        return string;
    }

    private Boolean evaluateHtmlData(Context context, WebView webView, PointF pointF, ValueCallback<String> valueCallback) {
        String webViewScript;
        if (valueCallback == null || (webViewScript = getWebViewScript(context, pointF, webView.getScale())) == null) {
            return false;
        }
        webView.evaluateJavascript(webViewScript, valueCallback);
        return true;
    }

    private Boolean invokeHtmlData(Context context, View view, PointF pointF, ValueCallback<String> valueCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (valueCallback == null) {
            return false;
        }
        Class<?> cls = view.getClass();
        try {
            Class[] clsArr = new Class[0];
            Object objInvoke = cls.getMethod("getScale", null).invoke(view, null);
            if (objInvoke == null) {
                Log.secE(TAG, "getScale failed");
                return false;
            }
            Method method = cls.getMethod("evaluateJavascript", String.class, ValueCallback.class);
            String webViewScript = getWebViewScript(context, pointF, ((Float) objInvoke).floatValue());
            if (webViewScript == null) {
                return false;
            }
            method.invoke(view, webViewScript, valueCallback);
            return true;
        } catch (Exception e) {
            Log.secD(TAG, "invokeHtmlData fail: " + e.getMessage(), e);
            return false;
        }
    }
}
