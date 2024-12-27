package com.samsung.android.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.R;
import com.android.internal.policy.DecorContext;

import com.google.android.mms.ContentType;
import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

public class SemOneTouchApi {
    private static final String BUNDLE_KEY_ACTIVITY_NAME = "key_activity_name";
    private static final String BUNDLE_KEY_APP_PROCESS_NAME = "key_app_process_name";
    private static final String BUNDLE_KEY_CUSTOM_CLIP_DATA = "key_customized_clip_data";
    private static final String BUNDLE_KEY_CUSTOM_DRAG_SHADOW_WIDTH =
            "key_custom_drag_shadow_width";
    private static final String BUNDLE_KEY_LONG_PRESS_FLAG = "key_long_press_flag";
    private static final String BUNDLE_KEY_RAW_TOUCHED_POINT = "key_raw_touched_point";
    private static final String BUNDLE_KEY_REQUEST_CODE = "key_request_code";
    private static final String BUNDLE_KEY_RESULT_BOOLEAN = "key_result_boolean";
    private static final String BUNDLE_KEY_SAVE_IMAGE_RESULT = "key_save_img_result";
    private static final String BUNDLE_KEY_TOUCHED_TEXT = "key_touched_text";
    private static final String BUNDLE_KEY_TOUCHED_VIEW_SIZE = "key_touched_view_size";
    private static final String BUNDLE_KEY_TOUCHED_WIDGET_ID = "key_touched_widget_id";
    private static final String BUNDLE_KEY_TOUCHED_WIDGET_NAME = "key_touched_widget_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_ClASS_NAME = "key_video_view_class_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_FINDING_TOP_TO_DOWN =
            "key_video_view_finding_top_to_down";
    private static final String BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS = "key_video_view_root_calss_name";
    private static final String BUNDLE_KEY_VIDEO_VIEW_WIDGET_ID = "key_video_view_widget_id";
    private static final String BUNDLE_KEY_VIDEO_VIEW_WIDGET_NAME = "key_video_view_widget_name";
    private static final String BUNDLE_KEY_WINDOW_TYPE = "key_window_type";
    public static final String CALL_METHOD_ON_LONG_PRESSED = "method_on_long_pressed";
    private static final int DEFAULT_CHECK_TOUCH_DOWN_DELAY_TIME = 100;
    private static final int DEFAULT_LONG_PRESS_PHASE_ONE_THRESHOLD = 480;
    private static final int DEFAULT_LONG_PRESS_PHASE_TWO_THRESHOLD = 1370;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_CANCELLED = 3;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_COMPLETED = 4;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_PHASE_ONE = 1;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_PHASE_TWO = 2;
    private static final int LONG_PRESS_FLAG_LONG_PRESS_START = 0;
    private static final int LONG_PRESS_FLAG_SAVE_IMAGE_VIEW_FINISHED = 1001;
    private static final String OPEN_URI_SAVE_TOUCHED_IMG = "uri_save_touched_img";
    private static final String PACKAGE_NAME_ONE_TOUCH = "com.samsung.android.onetouch";
    private static final String PACKAGE_NAME_SYSTEM_UI = "com.android.systemui";
    private static final String SETTING_KEY_OTCH_LONG_PRESS_ENABLE =
            "otch_long_press_enabled_setting";
    private static final String SETTING_KEY_OTCH_LONG_PRESS_PHASE_ONE_THRESHOLD =
            "otch_long_press_phase_one_threshold";
    private static final String SETTING_KEY_OTCH_LONG_PRESS_PHASE_TWO_THRESHOLD =
            "otch_long_press_phase_two_threshold";
    private static final String TAG = "OTCH$SemOneTouchApi";
    private static final int TEXT_VIEW_MAX_LENGTH = 100;
    private static final float TOUCH_MOVE_MAX_MM = 3.0f;
    private CheckRestrictTouchRunnable mCheckRestrictTouchRunnable;
    private Boolean mIsInitialized;
    private static final String OTCH_EXTERNAL_EVENT_AUTHORITY =
            "content://com.samsung.android.onetouch.externalEvent";
    public static final Uri OTCH_EXTERNAL_EVENT_URI = Uri.parse(OTCH_EXTERNAL_EVENT_AUTHORITY);
    private static Pair<Integer, Integer> mOneTouchLongPressThreshold = new Pair<>(480, 1370);
    private static int mTouchEventMoveMaxPixel = 50;
    private Boolean mIsOneTouchSettingsEnabled = false;
    private AtomicReference<OtchLongPressEvent> mCurrentLongPressEvent = new AtomicReference<>();
    private LongPressPhaseOneRunnable mLongPressPhaseOneRunnable = null;
    private LongPressPhaseTwoRunnable mLongPressPhaseTwoRunnable = null;

    /* JADX INFO: Access modifiers changed from: private */
    interface OtchDragAndDropResultCallback {
        void onDragAndDropResult(boolean z);
    }

    public static boolean isOneTouchSupported() {
        return CoreRune.FW_SUPPORT_ONE_TOUCH;
    }

    public SemOneTouchApi(Context context, View rootView) {
        this.mIsInitialized = false;
        this.mCheckRestrictTouchRunnable = null;
        if (isBlocked(context)) {
            return;
        }
        updateSettingsValue(context);
        mTouchEventMoveMaxPixel = (int) mm2px(context, 3.0f);
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            this.mCheckRestrictTouchRunnable = new CheckRestrictTouchRunnable(context, rootView);
        }
        this.mIsInitialized = true;
    }

    public void updateSettingsValue(Context context) {
        boolean z;
        if (context == null) {
            return;
        }
        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (CoreRune.FW_SUPPORT_ONE_TOUCH) {
                z = true;
                if (Settings.Secure.getInt(
                                context.getContentResolver(), SETTING_KEY_OTCH_LONG_PRESS_ENABLE, 1)
                        == 1) {
                    this.mIsOneTouchSettingsEnabled = Boolean.valueOf(z);
                    mOneTouchLongPressThreshold =
                            new Pair<>(
                                    Integer.valueOf(
                                            Settings.Secure.getInt(
                                                    contentResolver,
                                                    SETTING_KEY_OTCH_LONG_PRESS_PHASE_ONE_THRESHOLD,
                                                    480)),
                                    Integer.valueOf(
                                            Settings.Secure.getInt(
                                                    contentResolver,
                                                    SETTING_KEY_OTCH_LONG_PRESS_PHASE_TWO_THRESHOLD,
                                                    1370)));
                }
            }
            z = false;
            this.mIsOneTouchSettingsEnabled = Boolean.valueOf(z);
            mOneTouchLongPressThreshold =
                    new Pair<>(
                            Integer.valueOf(
                                    Settings.Secure.getInt(
                                            contentResolver,
                                            SETTING_KEY_OTCH_LONG_PRESS_PHASE_ONE_THRESHOLD,
                                            480)),
                            Integer.valueOf(
                                    Settings.Secure.getInt(
                                            contentResolver,
                                            SETTING_KEY_OTCH_LONG_PRESS_PHASE_TWO_THRESHOLD,
                                            1370)));
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

        public FindVideoViewEventInfo(boolean findTopToDown, String videoViewClassName) {
            this.findTopToDown = true;
            this.findTopToDown = findTopToDown;
            this.videoViewClassName = videoViewClassName;
        }

        public String toString() {
            return "FindVideoViewEventInfo( findTopToDown="
                    + this.findTopToDown
                    + ", videoViewClassName="
                    + this.videoViewClassName
                    + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static class OtchLongPressEvent {
        private Bundle bundle;
        private int eventFlag;
        private FindVideoViewEventInfo findVideoViewEventInfo;
        private final long requestCode;
        private final PointF touchedPoint;
        private final PointF touchedRawPoint;
        private View touchedView;

        public OtchLongPressEvent(long triggerTime, int initialEventFlag, MotionEvent motionEvent) {
            this.touchedView = null;
            this.bundle = null;
            this.findVideoViewEventInfo = null;
            this.requestCode = triggerTime;
            this.eventFlag = initialEventFlag;
            this.touchedPoint = new PointF(motionEvent.getX(), motionEvent.getY());
            this.touchedRawPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
        }

        public OtchLongPressEvent(
                long requestCode,
                int eventFlag,
                View touchedView,
                PointF touchedPoint,
                PointF touchedRawPoint,
                Bundle bundle) {
            this.touchedView = null;
            this.bundle = null;
            this.findVideoViewEventInfo = null;
            this.requestCode = requestCode;
            this.eventFlag = eventFlag;
            this.touchedView = touchedView;
            this.touchedPoint = touchedPoint;
            this.touchedRawPoint = touchedRawPoint;
            this.bundle = bundle;
        }

        public String toString() {
            return "Content( requestCode="
                    + this.requestCode
                    + ", eventFlag="
                    + this.eventFlag
                    + ", touchedView="
                    + this.touchedView
                    + ", touchedPoint="
                    + this.touchedPoint
                    + ", touchRawPoint="
                    + this.touchedRawPoint
                    + ", bundle="
                    + this.bundle
                    + NavigationBarInflaterView.KEY_CODE_END;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public OtchLongPressEvent m9294clone() {
            return new OtchLongPressEvent(
                    this.requestCode,
                    this.eventFlag,
                    this.touchedView,
                    this.touchedPoint,
                    this.touchedRawPoint,
                    this.bundle);
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

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            try {
                OtchLongPressEvent eventInfo =
                        (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (eventInfo != null && eventInfo.eventFlag == 0) {
                    Log.secD(
                            SemOneTouchApi.TAG,
                            "LongPressPhaseOneRunnable: " + eventInfo.requestCode);
                    eventInfo.touchedView =
                            SemOneTouchApi.this.getTouchedView(
                                    this.rootView, eventInfo.touchedPoint);
                    boolean z = true;
                    eventInfo.eventFlag = 1;
                    Bundle bundle = new Bundle();
                    SemOneTouchApi.this.putRootViewInfoToBundle(
                            this.context, bundle, this.rootView, eventInfo);
                    String imageViewBitmapString =
                            SemOneTouchApi.this.isBase64ImageString(eventInfo.touchedView);
                    SemOneTouchApi semOneTouchApi = SemOneTouchApi.this;
                    Context context = this.context;
                    View view = eventInfo.touchedView;
                    if (imageViewBitmapString == null) {
                        z = false;
                    }
                    semOneTouchApi.putTouchedViewInfoToBundle(context, bundle, view, z);
                    eventInfo.bundle = bundle;
                    SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(eventInfo);
                    Bundle responseBundle =
                            SemOneTouchApi.this.sendOnLongPressedEvent(
                                    this.context, eventInfo, bundle);
                    boolean isSuccess =
                            SemOneTouchApi.this.isEventSuccess(responseBundle).booleanValue();
                    if (!isSuccess) {
                        Log.secE(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable call fail");
                        SemOneTouchApi.this.onLongPressError();
                    } else {
                        SemOneTouchApi.this.parseCallResultDetail(
                                eventInfo.requestCode, responseBundle);
                        if (SemOneTouchApi.this.isSaveBitmapFileNeeded(eventInfo.touchedView)) {
                            new Thread(
                                            SemOneTouchApi.this
                                            .new SaveBitmapFileRunnable(
                                                    this.context, eventInfo.touchedView))
                                    .start();
                        } else if (imageViewBitmapString != null) {
                            new Thread(
                                            SemOneTouchApi.this
                                            .new SaveBitmapFileRunnable(
                                                    this.context, imageViewBitmapString))
                                    .start();
                        }
                        SemOneTouchApi.this.mLongPressPhaseTwoRunnable =
                                SemOneTouchApi.this
                                .new LongPressPhaseTwoRunnable(this.context, this.rootView);
                        long delay =
                                ((eventInfo.requestCode
                                                        + ((Integer)
                                                                        SemOneTouchApi
                                                                                .mOneTouchLongPressThreshold
                                                                                .first)
                                                                .intValue())
                                                + ((Integer)
                                                                SemOneTouchApi
                                                                        .mOneTouchLongPressThreshold
                                                                        .second)
                                                        .intValue())
                                        - System.currentTimeMillis();
                        this.rootView.postDelayed(
                                SemOneTouchApi.this.mLongPressPhaseTwoRunnable,
                                delay > 0
                                        ? delay
                                        : ((Integer)
                                                        SemOneTouchApi.mOneTouchLongPressThreshold
                                                                .second)
                                                .intValue());
                    }
                    return;
                }
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseOneRunnable state error: " + eventInfo);
                SemOneTouchApi.this.onLongPressError();
            } catch (Exception e) {
                Log.secE(
                        SemOneTouchApi.TAG,
                        "LongPressPhaseOneRunnable call error: " + e.getMessage(),
                        e);
                SemOneTouchApi.this.onLongPressError();
            }
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LongPressPhaseTwoRunnable implements Runnable {
        private Context context;
        private View rootView;

        public LongPressPhaseTwoRunnable(Context context, View view) {
            this.context = context;
            this.rootView = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                OtchLongPressEvent eventInfo =
                        (OtchLongPressEvent) SemOneTouchApi.this.mCurrentLongPressEvent.get();
                if (eventInfo != null) {
                    boolean z = true;
                    if (eventInfo.eventFlag == 1) {
                        Log.secD(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable");
                        eventInfo.eventFlag = 2;
                        SemOneTouchApi.this.mCurrentLongPressEvent = new AtomicReference(eventInfo);
                        Bundle bundle = eventInfo.bundle;
                        if (!(eventInfo.touchedView instanceof TextView)
                                && (this.rootView instanceof ViewGroup)) {
                            FindVideoViewEventInfo videoViewInfo = eventInfo.findVideoViewEventInfo;
                            SemOneTouchApi semOneTouchApi = SemOneTouchApi.this;
                            ViewGroup viewGroup = (ViewGroup) this.rootView;
                            String str =
                                    videoViewInfo != null ? videoViewInfo.videoViewClassName : null;
                            if (videoViewInfo != null && !videoViewInfo.findTopToDown) {
                                z = false;
                            }
                            View videoView = semOneTouchApi.findVideoView(viewGroup, str, z);
                            if (videoView != null) {
                                SemOneTouchApi.this.putVideoViewInfoToBundle(
                                        this.context,
                                        bundle,
                                        videoViewInfo.videoViewClassName,
                                        videoView);
                            }
                        }
                        Bundle resultBundle =
                                SemOneTouchApi.this.sendOnLongPressedEvent(
                                        this.context, eventInfo, bundle);
                        boolean isSuccess =
                                SemOneTouchApi.this.isEventSuccess(resultBundle).booleanValue();
                        if (!isSuccess) {
                            Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable call fail");
                            SemOneTouchApi.this.onLongPressError();
                            return;
                        } else {
                            ClipData clipData =
                                    resultBundle != null
                                            ? (ClipData)
                                                    resultBundle.getParcelable(
                                                            SemOneTouchApi
                                                                    .BUNDLE_KEY_CUSTOM_CLIP_DATA,
                                                            ClipData.class)
                                            : null;
                            if (clipData != null) {
                                int customDragShadowWidth =
                                        resultBundle.getInt(
                                                SemOneTouchApi.BUNDLE_KEY_CUSTOM_DRAG_SHADOW_WIDTH,
                                                -1);
                                SemOneTouchApi.this.performDragAndDrop(
                                        this.context,
                                        customDragShadowWidth,
                                        this.rootView,
                                        eventInfo.touchedRawPoint,
                                        eventInfo.touchedView,
                                        clipData,
                                        new OtchDragAndDropResultCallback() { // from class:
                                                                              // com.samsung.android.widget.SemOneTouchApi$LongPressPhaseTwoRunnable$$ExternalSyntheticLambda0
                                            @Override // com.samsung.android.widget.SemOneTouchApi.OtchDragAndDropResultCallback
                                            public final void onDragAndDropResult(boolean z2) {
                                                SemOneTouchApi.LongPressPhaseTwoRunnable.this
                                                        .lambda$run$0(z2);
                                            }
                                        });
                            }
                            return;
                        }
                    }
                }
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable state error: " + eventInfo);
                SemOneTouchApi.this.onLongPressError();
            } catch (Exception e) {
                Log.secE(
                        SemOneTouchApi.TAG,
                        "LongPressPhaseTwoRunnable call error: " + e.getMessage(),
                        e);
                SemOneTouchApi.this.onLongPressError();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(boolean dragResult) {
            if (dragResult) {
                SemOneTouchApi.this.onLongPressCompleted();
            } else {
                Log.secE(SemOneTouchApi.TAG, "LongPressPhaseTwoRunnable drag fail");
                SemOneTouchApi.this.onLongPressError();
            }
        }

        public void remove() {
            this.rootView.removeCallbacks(this);
        }
    }

    private boolean isBlocked(Context context) {
        String callerPackage = context.getPackageName();
        if (callerPackage == null) {
            return false;
        }
        if (callerPackage.equals(PACKAGE_NAME_ONE_TOUCH)
                || callerPackage.equals("com.android.systemui")) {
            return true;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            return false;
        }
        return callerPackage.equals(res.activityInfo.packageName);
    }

    public boolean dispatchTouchEvent(Context context, MotionEvent event, View rootView) {
        if (!this.mIsInitialized.booleanValue()
                || !this.mIsOneTouchSettingsEnabled.booleanValue()) {
            return false;
        }
        switch (event.getActionMasked()) {
            case 0:
                onLongPressStart(context, event, rootView);
                break;
            default:
                if (this.mCurrentLongPressEvent.get() != null) {
                    switch (event.getActionMasked()) {
                        case 1:
                        case 3:
                        case 5:
                            onLongPressCanceled(context);
                            clearEventState();
                            break;
                        case 2:
                            if (checkTouchedPointIsMoved(event)) {
                                onLongPressCanceled(context);
                                clearEventState();
                                break;
                            }
                            break;
                    }
                    OtchLongPressEvent eventInfo = this.mCurrentLongPressEvent.get();
                    if (eventInfo == null || eventInfo.eventFlag != 4) {}
                }
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPressError() {
        Log.secD(TAG, "onLongPressError");
        clearEventState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPressCompleted() {
        Log.secD(TAG, "onLongPressCompleted");
        OtchLongPressEvent eventInfo = this.mCurrentLongPressEvent.get();
        if (eventInfo != null) {
            eventInfo.eventFlag = 4;
            this.mCurrentLongPressEvent = new AtomicReference<>(eventInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearEventState() {
        this.mCurrentLongPressEvent = new AtomicReference<>();
        try {
            if (this.mLongPressPhaseOneRunnable != null) {
                this.mLongPressPhaseOneRunnable.remove();
            }
            if (this.mLongPressPhaseTwoRunnable != null) {
                this.mLongPressPhaseTwoRunnable.remove();
            }
            if (this.mCheckRestrictTouchRunnable != null) {
                this.mCheckRestrictTouchRunnable.remove();
            }
        } catch (Exception e) {
            Log.secE(TAG, "clearEventState failed", e);
        }
    }

    private void onLongPressStart(Context context, MotionEvent event, View rootView) {
        OtchLongPressEvent eventInfo = this.mCurrentLongPressEvent.get();
        if (eventInfo != null) {
            Log.secE(
                    TAG, "onLongPressStart state error: " + eventInfo + ", replace LongPressEvent");
            clearEventState();
        }
        OtchLongPressEvent longPressEvent =
                new OtchLongPressEvent(System.currentTimeMillis(), 0, event);
        this.mCurrentLongPressEvent = new AtomicReference<>(longPressEvent);
        this.mLongPressPhaseOneRunnable = new LongPressPhaseOneRunnable(context, rootView);
        rootView.postDelayed(
                this.mLongPressPhaseOneRunnable, mOneTouchLongPressThreshold.first.intValue());
        if (this.mCheckRestrictTouchRunnable != null) {
            rootView.postDelayed(this.mCheckRestrictTouchRunnable, 100L);
        }
    }

    private void onLongPressCanceled(Context context) {
        OtchLongPressEvent eventInfo = this.mCurrentLongPressEvent.get();
        clearEventState();
        if (eventInfo == null || eventInfo.eventFlag == 0) {
            return;
        }
        eventInfo.eventFlag = 3;
        sendOnLongPressedEvent(context, eventInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String isBase64ImageString(View touchedView) {
        if (touchedView == null || (touchedView instanceof TextView)) {
            return null;
        }
        String input = touchedView.semGetBixbyTouchFoundText();
        if (TextUtils.isEmpty(input) || !input.startsWith("/9j/4AAQSkZJRgABAQAAAQABAAD")) {
            return null;
        }
        Log.secD(TAG, "isBase64ImageString");
        return input;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putTouchedViewInfoToBundle(
            Context context, Bundle bundle, View touchedView, boolean isImageView) {
        if (touchedView == null) {
            return;
        }
        try {
            if (!isImageView) {
                if (touchedView instanceof TextView) {
                    CharSequence text = ((TextView) touchedView).getText();
                    if (text != null) {
                        if (TextUtils.isEmpty(text.toString().trim())) {
                            bundle.putString(
                                    BUNDLE_KEY_TOUCHED_TEXT,
                                    touchedView.semGetBixbyTouchFoundText());
                        } else {
                            bundle.putString(BUNDLE_KEY_TOUCHED_TEXT, text.toString());
                        }
                    }
                } else {
                    bundle.putString(
                            BUNDLE_KEY_TOUCHED_TEXT, touchedView.semGetBixbyTouchFoundText());
                }
            } else {
                touchedView.semSetBixbyTouchFoundText(null);
            }
            bundle.putString(BUNDLE_KEY_TOUCHED_WIDGET_NAME, touchedView.getClass().getName());
            bundle.putInt(
                    BUNDLE_KEY_TOUCHED_VIEW_SIZE, touchedView.getWidth() * touchedView.getHeight());
            if (touchedView.getId() != -1) {
                bundle.putString(
                        BUNDLE_KEY_TOUCHED_WIDGET_ID,
                        context.getResources().getResourceName(touchedView.getId()));
            }
        } catch (Exception e) {
            Log.secE(TAG, "putTouchedViewInfoToBundle fail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putVideoViewInfoToBundle(
            Context context, Bundle bundle, String targetVideoClass, View videoView) {
        try {
            String videoClassName = videoView.getClass().getName();
            if (videoView instanceof SurfaceView) {
                bundle.putString(BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS, SurfaceView.class.getName());
            } else if (videoView instanceof TextureView) {
                TextureView textureView = (TextureView) videoView;
                long timestamp = -1;
                if (textureView.getSurfaceTexture() != null) {
                    timestamp = textureView.getSurfaceTexture().getTimestamp();
                }
                if (textureView.getBitmap() != null
                        && timestamp > 0
                        && !TextUtils.equals(targetVideoClass, videoClassName)) {
                    Log.secW(
                            TAG,
                            "VideoView: Found TextureView but consider not a video view, class: "
                                    + videoClassName
                                    + " timestamp: "
                                    + timestamp);
                    return;
                }
                bundle.putString(BUNDLE_KEY_VIDEO_VIEW_ROOT_CLASS, TextureView.class.getName());
            }
            bundle.putString(BUNDLE_KEY_VIDEO_VIEW_WIDGET_NAME, videoClassName);
            if (videoView.getId() != -1) {
                bundle.putString(
                        BUNDLE_KEY_VIDEO_VIEW_WIDGET_ID,
                        context.getResources().getResourceName(videoView.getId()));
            }
        } catch (Exception e) {
            Log.secE(TAG, "putVideoViewInfoToBundle fail: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putRootViewInfoToBundle(
            Context context, Bundle bundle, View rootView, OtchLongPressEvent eventInfo) {
        bundle.putParcelable(BUNDLE_KEY_RAW_TOUCHED_POINT, eventInfo.touchedRawPoint);
        bundle.putString(BUNDLE_KEY_APP_PROCESS_NAME, context.getApplicationInfo().processName);
        Activity activity = parseActivity(context, rootView);
        if (activity != null) {
            bundle.putString(BUNDLE_KEY_ACTIVITY_NAME, activity.getComponentName().getClassName());
        }
        WindowManager.LayoutParams wparams =
                (WindowManager.LayoutParams) rootView.getLayoutParams();
        if (wparams != null) {
            bundle.putInt(BUNDLE_KEY_WINDOW_TYPE, wparams.type);
        }
    }

    private Bundle sendOnLongPressedEvent(Context context, OtchLongPressEvent event) {
        return sendOnLongPressedEvent(context, event, event.bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle sendOnLongPressedEvent(Context context, OtchLongPressEvent event, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putLong(BUNDLE_KEY_REQUEST_CODE, event.requestCode);
        bundle.putInt(BUNDLE_KEY_LONG_PRESS_FLAG, event.eventFlag);
        try {
            return context.getContentResolver()
                    .call(
                            OTCH_EXTERNAL_EVENT_URI,
                            CALL_METHOD_ON_LONG_PRESSED,
                            (String) null,
                            bundle);
        } catch (Exception e) {
            Log.secE(TAG, "sendOnLongPressedEvent fail: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean isEventSuccess(Bundle result) {
        if (result == null) {
            Log.secE(TAG, "call One Touch error, bundle null");
        }
        return Boolean.valueOf(result != null && result.getBoolean(BUNDLE_KEY_RESULT_BOOLEAN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseCallResultDetail(long requestCode, Bundle bundle) {
        FindVideoViewEventInfo findVideoViewEventInfo =
                new FindVideoViewEventInfo(
                        bundle.getBoolean(BUNDLE_KEY_VIDEO_VIEW_FINDING_TOP_TO_DOWN, true),
                        bundle.getString(BUNDLE_KEY_VIDEO_VIEW_ClASS_NAME, null));
        OtchLongPressEvent eventInfo = this.mCurrentLongPressEvent.get();
        if (eventInfo.requestCode == requestCode) {
            eventInfo.findVideoViewEventInfo = findVideoViewEventInfo;
            this.mCurrentLongPressEvent = new AtomicReference<>(eventInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSaveBitmapFileNeeded(View touchedView) {
        if (touchedView == null) {
            return false;
        }
        return touchedView instanceof ImageView;
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        try {
            Drawable copiedDrawable = drawable.mutate();
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
                return bitmap;
            }
            Log.secE(TAG, "drawable2Bitmap fail");
            return null;
        } catch (Exception e) {
            Log.secE(TAG, "drawable2Bitmap fail: " + e.getMessage(), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBitmapFromView(ImageView view) {
        try {
            Drawable drawable = view.getDrawable();
            bitmap = drawable != null ? drawable2Bitmap(drawable) : null;
            if (bitmap == null) {
                bitmap =
                        Bitmap.createBitmap(
                                view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
                if (!canvas.isHardwareAccelerated() && canvas.getSaveCount() == 1) {
                    Log.secE(TAG, "getBitmapFromView draw fail");
                    return null;
                }
            }
        } catch (Exception e) {
            Log.secE(TAG, "getBitmapFromView fail: " + e.getMessage(), e);
        }
        return bitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveBitmapFile(Context context, Bitmap bitmap) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        try {
            ParcelFileDescriptor pfd =
                    context.getContentResolver()
                            .openFile(
                                    Uri.parse(
                                            OTCH_EXTERNAL_EVENT_AUTHORITY
                                                    + File.separator
                                                    + OPEN_URI_SAVE_TOUCHED_IMG
                                                    + File.separator
                                                    + "0"),
                                    String.valueOf(805306368),
                                    cancellationSignal);
            try {
                if (pfd == null) {
                    Log.secE(TAG, "openFile fail");
                    if (pfd != null) {
                        pfd.close();
                    }
                    return false;
                }
                FileOutputStream out = new FileOutputStream(pfd.getFileDescriptor());
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    if (pfd != null) {
                        pfd.close();
                        return true;
                    }
                    return true;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            Log.secE(TAG, "saveBitmapFile fail: " + e.getMessage(), e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap base64ToBitmap(String base64String) {
        try {
            byte[] decodedBytes = Base64.decode(base64String, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            Log.secE(TAG, "base64ToBitmap fail: " + e.getMessage(), e);
            return null;
        }
    }

    private class SaveBitmapFileRunnable implements Runnable {
        private String base64Input;
        private final Context context;
        private View touchedView;

        public SaveBitmapFileRunnable(Context context, View touchedView) {
            this.touchedView = null;
            this.base64Input = null;
            this.context = context;
            this.touchedView = touchedView;
        }

        public SaveBitmapFileRunnable(Context context, String base64Input) {
            this.touchedView = null;
            this.base64Input = null;
            this.context = context;
            this.base64Input = base64Input;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r7 = this;
                r0 = 0
                java.lang.String r1 = r7.base64Input
                if (r1 != 0) goto L14
                android.view.View r1 = r7.touchedView
                boolean r2 = r1 instanceof android.widget.ImageView
                if (r2 == 0) goto L14
                android.widget.ImageView r1 = (android.widget.ImageView) r1
                com.samsung.android.widget.SemOneTouchApi r2 = com.samsung.android.widget.SemOneTouchApi.this
                android.graphics.Bitmap r0 = com.samsung.android.widget.SemOneTouchApi.m9265$$Nest$mgetBitmapFromView(r2, r1)
                goto L1c
            L14:
                com.samsung.android.widget.SemOneTouchApi r1 = com.samsung.android.widget.SemOneTouchApi.this
                java.lang.String r2 = r7.base64Input
                android.graphics.Bitmap r0 = com.samsung.android.widget.SemOneTouchApi.m9262$$Nest$mbase64ToBitmap(r1, r2)
            L1c:
                java.lang.String r1 = "OTCH$SemOneTouchApi"
                if (r0 != 0) goto L26
                java.lang.String r2 = "getBitmapFromView fail"
                android.util.Log.secE(r1, r2)
                return
            L26:
                com.samsung.android.widget.SemOneTouchApi r2 = com.samsung.android.widget.SemOneTouchApi.this
                android.content.Context r3 = r7.context
                boolean r2 = com.samsung.android.widget.SemOneTouchApi.m9278$$Nest$msaveBitmapFile(r2, r3, r0)
                android.os.Bundle r3 = new android.os.Bundle
                r3.<init>()
                java.lang.String r4 = "key_save_img_result"
                r3.putInt(r4, r2)
                com.samsung.android.widget.SemOneTouchApi r4 = com.samsung.android.widget.SemOneTouchApi.this
                java.util.concurrent.atomic.AtomicReference r4 = com.samsung.android.widget.SemOneTouchApi.m9258$$Nest$fgetmCurrentLongPressEvent(r4)
                java.lang.Object r4 = r4.get()
                com.samsung.android.widget.SemOneTouchApi$OtchLongPressEvent r4 = (com.samsung.android.widget.SemOneTouchApi.OtchLongPressEvent) r4
                if (r4 != 0) goto L4c
                java.lang.String r5 = "CurrentLongPressEvent is null, abandon SaveBitmapFile"
                android.util.Log.secW(r1, r5)
                return
            L4c:
                com.samsung.android.widget.SemOneTouchApi$OtchLongPressEvent r1 = r4.m9294clone()
                r5 = 1001(0x3e9, float:1.403E-42)
                com.samsung.android.widget.SemOneTouchApi.OtchLongPressEvent.m9291$$Nest$fputeventFlag(r1, r5)
                com.samsung.android.widget.SemOneTouchApi r5 = com.samsung.android.widget.SemOneTouchApi.this
                android.content.Context r6 = r7.context
                com.samsung.android.widget.SemOneTouchApi.m9279$$Nest$msendOnLongPressedEvent(r5, r6, r1, r3)
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.widget.SemOneTouchApi.SaveBitmapFileRunnable.run():void");
        }
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    private class ScaledDragShadowBuilder extends View.DragShadowBuilder {
        private float mCustomDragShadowWidth;

        public ScaledDragShadowBuilder(View view, int customDragShadowWidth) {
            super(view);
            this.mCustomDragShadowWidth = 100.0f;
            this.mCustomDragShadowWidth = customDragShadowWidth;
        }

        @Override // android.view.View.DragShadowBuilder
        public void onDrawShadow(Canvas canvas) {
            View view = getView();
            if (view == null) {
                return;
            }
            int width = SemOneTouchApi.pxToDp(view.getWidth());
            int height = SemOneTouchApi.pxToDp(view.getHeight());
            boolean performScale =
                    ((float) width) > this.mCustomDragShadowWidth
                            || ((float) height) > this.mCustomDragShadowWidth;
            if (performScale) {
                float scale = this.mCustomDragShadowWidth / (height > width ? width : height);
                float px = canvas.getWidth() / 2.0f;
                float py = canvas.getHeight() / 2.0f;
                canvas.scale(scale, scale, px, py);
            }
            super.onDrawShadow(canvas);
        }
    }

    public int calculateInSampleSize(Context context, Uri uri, int reqWidth, int reqHeight) {
        InputStream inputStream;
        int inSampleSize = 1;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (IOException e) {
            Log.secE(TAG, "calculateInSampleSize failed", e);
        }
        if (inputStream == null) {
            return 1;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.close();
        int width = options.outWidth;
        int height = options.outHeight;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap getBitmapFromFile(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int inSampleSize = calculateInSampleSize(context, uri, 360, 360);
            options.inSampleSize = inSampleSize;
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            Log.secE(TAG, "getBitmapFromFile failed, abandon SaveBitmapFile", e);
            return null;
        }
    }

    private ImageView getCustomImageView(
            Context context, Bitmap bitmap, PointF point, int customDragShadowWidth) {
        if (bitmap == null) {
            return null;
        }
        ImageView shadowView = new ImageView(context);
        shadowView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        int customDragShadowWidthPx = dpToPx(customDragShadowWidth);
        boolean performScale =
                bitmap.getHeight() > customDragShadowWidthPx
                        || bitmap.getWidth() > customDragShadowWidthPx;
        if (performScale) {
            if (bitmap.getHeight() > bitmap.getWidth()) {
                shadowView.setMaxWidth(customDragShadowWidthPx);
            } else {
                shadowView.setMaxHeight(customDragShadowWidthPx);
            }
        }
        shadowView.setAdjustViewBounds(true);
        shadowView.setImageBitmap(bitmap);
        shadowView.setX(point.x);
        shadowView.setY(point.y);
        return shadowView;
    }

    private FrameLayout getCustomTextThumbnailView(
            Context context, String text, PointF point, int customDragShadowWidth) {
        FrameLayout shadowView;
        TextView shadowViewContents;
        if (text.isEmpty()
                || (shadowView =
                                (FrameLayout)
                                        View.inflate(
                                                context, R.layout.sem_text_drag_thumbnail, null))
                        == null
                || (shadowViewContents = (TextView) shadowView.getChildAt(1)) == null) {
            return null;
        }
        shadowViewContents.setMaxWidth(dpToPx(customDragShadowWidth));
        shadowViewContents.lambda$setTextAsync$0(
                text.length() > 100 ? text.substring(0, 100) : text);
        shadowView.setX(point.x);
        shadowView.setY(point.y);
        return shadowView;
    }

    private void setShadowViewLayout(View shadowView) {
        shadowView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        int size = View.MeasureSpec.makeMeasureSpec(0, 0);
        shadowView.measure(size, size);
        int width = shadowView.getMeasuredWidth();
        int height = shadowView.getMeasuredHeight();
        shadowView.layout(0, 0, width, height);
        shadowView.invalidate();
    }

    private void dragWithCustomShadowView(
            final View shadowView,
            final View rootView,
            final ClipData clipData,
            final OtchDragAndDropResultCallback callback) {
        if (shadowView == null) {
            callback.onDragAndDropResult(false);
            return;
        }
        setShadowViewLayout(shadowView);
        ViewGroup viewContainer = (ViewGroup) rootView;
        viewContainer.post(
                new Runnable() { // from class:
                                 // com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemOneTouchApi.lambda$dragWithCustomShadowView$0(
                                View.this, clipData, shadowView, callback);
                    }
                });
    }

    static /* synthetic */ void lambda$dragWithCustomShadowView$0(
            View rootView,
            ClipData clipData,
            View shadowView,
            OtchDragAndDropResultCallback callback) {
        boolean res =
                rootView.startDragAndDrop(
                        clipData, new View.DragShadowBuilder(shadowView), null, 768);
        callback.onDragAndDropResult(res);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDragAndDrop(
            Context context,
            final int customDragShadowWidth,
            View rootView,
            PointF touchedPoint,
            final View touchedView,
            final ClipData clipData,
            final OtchDragAndDropResultCallback callback) {
        try {
            if (touchedView == null) {
                ClipDescription clipDescription = clipData.getDescription();
                ClipData.Item item = clipData.getItemAt(0);
                if (clipDescription != null && item != null) {
                    if (clipDescription.hasMimeType("text/*") && item.getText() != null) {
                        View shadowView =
                                getCustomTextThumbnailView(
                                        context,
                                        item.getText().toString(),
                                        touchedPoint,
                                        customDragShadowWidth);
                        dragWithCustomShadowView(shadowView, rootView, clipData, callback);
                    } else if (clipDescription.hasMimeType(ContentType.IMAGE_UNSPECIFIED)) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        Bitmap fileBitmap = getBitmapFromFile(context, uri);
                        View shadowView2 =
                                getCustomImageView(
                                        context, fileBitmap, touchedPoint, customDragShadowWidth);
                        dragWithCustomShadowView(shadowView2, rootView, clipData, callback);
                    } else {
                        callback.onDragAndDropResult(false);
                    }
                    return;
                }
                callback.onDragAndDropResult(false);
                return;
            }
            if (touchedView.semGetBixbyTouchFoundText() != null) {
                View shadowView3 =
                        getCustomTextThumbnailView(
                                context,
                                touchedView.semGetBixbyTouchFoundText(),
                                touchedPoint,
                                customDragShadowWidth);
                dragWithCustomShadowView(shadowView3, rootView, clipData, callback);
            } else {
                touchedView.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.widget.SemOneTouchApi$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemOneTouchApi.this.lambda$performDragAndDrop$1(
                                        touchedView, clipData, customDragShadowWidth, callback);
                            }
                        });
            }
        } catch (Exception e) {
            Log.secE(TAG, "dragTouchedView failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDragAndDrop$1(
            View touchedView,
            ClipData clipData,
            int customDragShadowWidth,
            OtchDragAndDropResultCallback callback) {
        boolean res =
                touchedView.startDragAndDrop(
                        clipData,
                        new ScaledDragShadowBuilder(touchedView, customDragShadowWidth),
                        touchedView,
                        768);
        callback.onDragAndDropResult(res);
    }

    private float mm2px(Context context, float mm) {
        int dpi = context.getResources().getDisplayMetrics().densityDpi;
        return (mm / 25.4f) * dpi;
    }

    private boolean checkTouchedPointIsMoved(MotionEvent event) {
        PointF downPoint = this.mCurrentLongPressEvent.get().touchedPoint;
        boolean isPointMoved =
                Math.abs(event.getX() - downPoint.x) > ((float) mTouchEventMoveMaxPixel)
                        || Math.abs(event.getY() - downPoint.y) > ((float) mTouchEventMoveMaxPixel);
        boolean isMoved = 0 != 0 || isPointMoved;
        return isMoved;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getTouchedView(View view, PointF touchedPoint) {
        View touchedView = null;
        try {
            touchedView = view.semDispatchFindView(touchedPoint.x, touchedPoint.y, true);
            Log.secD(
                    TAG,
                    "getTouchedView: touchedPoint: "
                            + touchedPoint
                            + " touchedView: "
                            + touchedView);
            return touchedView;
        } catch (Exception e) {
            e.printStackTrace();
            return touchedView;
        }
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

    private Activity parseActivity(Context context, View rootView) {
        Activity activity = null;
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else if (context instanceof DecorContext) {
                context = getContextFromDecorContext(rootView.getContext());
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
            if (activity == null) {
                return getActivityFromContextWrapper(context);
            }
        } catch (Exception e) {
            Log.secE(TAG, "parseActivity failed", e);
        }
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View findVideoView(ViewGroup viewGroup, String videoClassName, boolean topDownFindView) {
        View resultView = null;
        try {
            int childCount = viewGroup.getChildCount();
            if (topDownFindView) {
                for (int i = childCount - 1; i >= 0; i--) {
                    View child = viewGroup.getChildAt(i);
                    if (!(child instanceof SurfaceView)
                            && !(child instanceof TextureView)
                            && !child.getClass().getName().equals(videoClassName)) {
                        if ((child instanceof ViewGroup)
                                && (resultView =
                                                findVideoView(
                                                        (ViewGroup) child, videoClassName, true))
                                        != null) {
                            break;
                        }
                    }
                    resultView = child;
                    break;
                }
                return resultView;
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View child2 = viewGroup.getChildAt(i2);
                if (!(child2 instanceof SurfaceView)
                        && !(child2 instanceof TextureView)
                        && !child2.getClass().getName().equals(videoClassName)) {
                    if ((child2 instanceof ViewGroup)
                            && (resultView =
                                            findVideoView(
                                                    (ViewGroup) child2, videoClassName, false))
                                    != null) {
                        return resultView;
                    }
                }
                return child2;
            }
            return resultView;
        } catch (Exception e) {
            Log.secE(TAG, "findVideoView failed", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFingerPrintInDisplay(Context context) {
        boolean z;
        try {
            FingerprintManager fpm =
                    (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            boolean fingerPrintInDisplay = false;
            int fingerIconHeight = 0;
            if (fpm != null) {
                if (FingerprintManager.semGetSensorPosition() != 2) {
                    z = false;
                } else {
                    z = true;
                }
                fingerPrintInDisplay = z;
                fingerIconHeight = fpm.semGetIconBottomMargin();
            }
            if (!fingerPrintInDisplay || fingerIconHeight <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
