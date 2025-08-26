package com.samsung.android.content.smartclip;

import android.app.Activity;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.VideoView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SmartClipRemoteRequestDispatcher {
    private static final String KEY_AIR_COMMAND_HIT_TEST_RESULT = "result";
    private static final String KEY_EVENT_INJECTION_EVENTS = "events";
    private static final String KEY_EVENT_INJECTION_WAIT_UNTIL_CONSUME = "waitUntilConsume";
    private static final String KEY_SCROLLABLE_AREA_INFO_ACTIVITY_NAME = "activityName";
    private static final String KEY_SCROLLABLE_AREA_INFO_DISPLAY_FRAME = "displayFrame";
    private static final String KEY_SCROLLABLE_AREA_INFO_DSS_SCALE = "dssScale";
    private static final String KEY_SCROLLABLE_AREA_INFO_PACKAGE_NAME = "packageName";
    private static final String KEY_SCROLLABLE_AREA_INFO_SCROLLABLE_VIEWS = "scrollableViews";
    private static final String KEY_SCROLLABLE_AREA_INFO_UNSCROLLABLE_VIEWS = "unscrollableViews";
    private static final String KEY_SCROLLABLE_AREA_INFO_VISIBLE_DISPLAY_FRAME = "visibleDisplayFrame";
    private static final String KEY_SCROLLABLE_AREA_INFO_WINDOW_LAYER = "windowLayer";
    private static final String KEY_SCROLLABLE_AREA_INFO_WINDOW_RECT = "windowRect";
    private static final String KEY_SCROLLABLE_VIEW_INFO_CHILD_VIEWS = "childViews";
    private static final String KEY_SCROLLABLE_VIEW_INFO_TARGET_VIEW = "targetView";
    private static final String KEY_VIEW_INFO_BROWSER_VISIBLE_RECT = "browserVisibleRect";
    private static final String KEY_VIEW_INFO_HASHCODE = "hashCode";
    private static final String KEY_VIEW_INFO_HIERARCHY = "hierarchy";
    private static final String KEY_VIEW_INFO_SCREEN_RECT = "screenRect";
    private static final String KEY_VIEW_INFO_SCROLLY = "scrollY";
    private static final String KEY_VIEW_INFO_SCROLLY_SUPPORTED = "scrollYSupported";
    public static final String PERMISSION_EXTRACT_SMARTCLIP_DATA = "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA";
    public static final String PERMISSION_INJECT_INPUT_EVENT = "android.permission.INJECT_EVENTS";
    public static final String TAG = "SmartClipRemoteRequestDispatcher";
    private boolean DEBUG = false;
    private Context mContext;
    private Handler mHandler;
    private ViewRootImplGateway mViewRootImplGateway;

    public interface ViewRootImplGateway {
        void enqueueInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver, int i, boolean z);

        Handler getHandler();

        View getRootView();

        PointF getScaleFactor();

        PointF getTranslatedPoint();

        void getTranslatedRectIfNeeded(Rect rect);

        ViewRootImpl getViewRootImpl();
    }

    public SmartClipRemoteRequestDispatcher(Context context, ViewRootImplGateway viewRootImplGateway) {
        this.mContext = context;
        this.mViewRootImplGateway = viewRootImplGateway;
        this.mHandler = viewRootImplGateway.getHandler();
    }

    public boolean isDebugMode() {
        return this.DEBUG;
    }

    public void checkPermission(String str, int i, int i2) {
        if (this.mContext.checkPermission(str, i, i2) == 0) {
            return;
        }
        String str2 = "Requires " + str + " permission";
        Log.e(TAG, "checkPermission : " + str2);
        throw new SecurityException(str2);
    }

    public void dispatchSmartClipRemoteRequest(final SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        int i = smartClipRemoteRequestInfo.mRequestType;
        if (i == 2) {
            checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA", smartClipRemoteRequestInfo.mCallerPid, smartClipRemoteRequestInfo.mCallerUid);
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.1
                @Override // java.lang.Runnable
                public void run() {
                    SmartClipRemoteRequestDispatcher.this.dispatchAirCommandHitTest(smartClipRemoteRequestInfo);
                }
            });
            return;
        }
        if (i == 3) {
            checkPermission("android.permission.INJECT_EVENTS", smartClipRemoteRequestInfo.mCallerPid, smartClipRemoteRequestInfo.mCallerUid);
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.4
                @Override // java.lang.Runnable
                public void run() {
                    SmartClipRemoteRequestDispatcher.this.dispatchInputEventInjection(smartClipRemoteRequestInfo);
                }
            });
            return;
        }
        if (i == 4) {
            checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA", smartClipRemoteRequestInfo.mCallerPid, smartClipRemoteRequestInfo.mCallerUid);
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.2
                @Override // java.lang.Runnable
                public void run() {
                    SmartClipRemoteRequestDispatcher.this.dispatchScrollableAreaInfo(smartClipRemoteRequestInfo);
                }
            });
        } else if (i == 5) {
            checkPermission("com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA", smartClipRemoteRequestInfo.mCallerPid, smartClipRemoteRequestInfo.mCallerUid);
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.3
                @Override // java.lang.Runnable
                public void run() {
                    SmartClipRemoteRequestDispatcher.this.dispatchScrollableViewInfo(smartClipRemoteRequestInfo);
                }
            });
        } else {
            Log.e(TAG, "dispatchSmartClipRemoteRequest : Unknown request type(" + smartClipRemoteRequestInfo.mRequestType + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInputEventInjection(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        SmartClipRemoteRequestDispatcher smartClipRemoteRequestDispatcher;
        final SmartClipRemoteRequestInfo smartClipRemoteRequestInfo2;
        if (smartClipRemoteRequestInfo.mRequestData != null) {
            if (smartClipRemoteRequestInfo.mRequestData instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) smartClipRemoteRequestInfo.mRequestData;
                transformTouchPosition(motionEvent);
                int action = motionEvent.getAction();
                if (this.DEBUG || action == 0 || action == 1 || action == 3) {
                    Log.d(TAG, "dispatchInputEventInjection : Touch event action=" + MotionEvent.actionToString(action) + " x=" + ((int) motionEvent.getRawX()) + " y=" + ((int) motionEvent.getRawY()));
                }
                enqueueInputEvent((InputEvent) smartClipRemoteRequestInfo.mRequestData, true);
                return;
            }
            if (smartClipRemoteRequestInfo.mRequestData instanceof Bundle) {
                Bundle bundle = (Bundle) smartClipRemoteRequestInfo.mRequestData;
                final Parcelable[] parcelableArray = bundle.getParcelableArray("events");
                if (parcelableArray != null) {
                    final boolean z = bundle.getBoolean(KEY_EVENT_INJECTION_WAIT_UNTIL_CONSUME);
                    int i = 0;
                    long eventTime = parcelableArray.length > 0 ? ((InputEvent) parcelableArray[0]).getEventTime() : -1L;
                    if (this.DEBUG) {
                        Log.d(TAG, "dispatchInputEventInjection : wait = " + z + "  eventCount=" + parcelableArray.length);
                    }
                    while (i < parcelableArray.length) {
                        final InputEvent inputEvent = (InputEvent) parcelableArray[i];
                        if (inputEvent == null) {
                            smartClipRemoteRequestDispatcher = this;
                            smartClipRemoteRequestInfo2 = smartClipRemoteRequestInfo;
                        } else {
                            if (inputEvent instanceof MotionEvent) {
                                this.transformTouchPosition((MotionEvent) inputEvent);
                            }
                            smartClipRemoteRequestDispatcher = this;
                            smartClipRemoteRequestInfo2 = smartClipRemoteRequestInfo;
                            Runnable runnable = new Runnable() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    if (SmartClipRemoteRequestDispatcher.this.DEBUG) {
                                        Log.d(SmartClipRemoteRequestDispatcher.TAG, "dispatchInputEventInjection : injecting.. " + inputEvent);
                                    }
                                    SmartClipRemoteRequestDispatcher.this.enqueueInputEvent(inputEvent, true);
                                    InputEvent inputEvent2 = inputEvent;
                                    Parcelable[] parcelableArr = parcelableArray;
                                    if (inputEvent2 == parcelableArr[parcelableArr.length - 1]) {
                                        if (z) {
                                            SmartClipRemoteRequestDispatcher.this.sendResult(smartClipRemoteRequestInfo2, null);
                                        }
                                        Log.d(SmartClipRemoteRequestDispatcher.TAG, "dispatchInputEventInjection : injection finished. Elapsed = " + (System.currentTimeMillis() - jCurrentTimeMillis));
                                    }
                                }
                            };
                            long eventTime2 = inputEvent.getEventTime() - eventTime;
                            if (eventTime2 > 0) {
                                smartClipRemoteRequestDispatcher.mHandler.postDelayed(runnable, eventTime2);
                            } else {
                                runnable.run();
                            }
                        }
                        i++;
                        this = smartClipRemoteRequestDispatcher;
                        smartClipRemoteRequestInfo = smartClipRemoteRequestInfo2;
                    }
                    return;
                }
                Log.e(TAG, "dispatchInputEventInjection : Event is null!");
                return;
            }
            return;
        }
        Log.e(TAG, "dispatchInputEventInjection : Empty input event!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchScrollableAreaInfo(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        String name;
        View rootView = this.mViewRootImplGateway.getRootView();
        if (rootView == null) {
            Log.e(TAG, "dispatchScrollableAreaInfo : Root view is null!");
            return;
        }
        ArrayList<View> arrayList = new ArrayList<>();
        ArrayList<View> arrayList2 = new ArrayList<>();
        Rect translatedViewBoundsOnScreen = getTranslatedViewBoundsOnScreen(rootView);
        Log.d(TAG, "dispatchScrollableAreaInfo : windowRect = " + translatedViewBoundsOnScreen);
        findScrollableViews(rootView, translatedViewBoundsOnScreen, arrayList, arrayList2);
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList3 = new ArrayList<>();
        Iterator<View> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList3.add(createViewInfoAsBundle(it.next()));
        }
        Log.d(TAG, "dispatchScrollableAreaInfo : Scrollable view count = " + arrayList3.size());
        bundle.putParcelableArrayList(KEY_SCROLLABLE_AREA_INFO_SCROLLABLE_VIEWS, arrayList3);
        ArrayList<? extends Parcelable> arrayList4 = new ArrayList<>();
        Iterator<View> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList4.add(createViewInfoAsBundle(it2.next()));
        }
        Log.d(TAG, "dispatchScrollableAreaInfo : Unscrollable view count = " + arrayList4.size());
        bundle.putParcelableArrayList(KEY_SCROLLABLE_AREA_INFO_UNSCROLLABLE_VIEWS, arrayList4);
        bundle.putParcelable(KEY_SCROLLABLE_AREA_INFO_WINDOW_RECT, translatedViewBoundsOnScreen);
        bundle.putInt(KEY_SCROLLABLE_AREA_INFO_WINDOW_LAYER, smartClipRemoteRequestInfo.mTargetWindowLayer);
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        CompatibilityInfo compatInfo = ActivityThread.currentActivityThread().getCompatInfo();
        float f = compatInfo != null ? compatInfo.applicationDensityScale : 1.0f;
        rootView.getWindowDisplayFrame(rect);
        rootView.getWindowVisibleDisplayFrame(rect2);
        this.mViewRootImplGateway.getTranslatedRectIfNeeded(rect2);
        bundle.putParcelable(KEY_SCROLLABLE_AREA_INFO_DISPLAY_FRAME, rect);
        bundle.putParcelable(KEY_SCROLLABLE_AREA_INFO_VISIBLE_DISPLAY_FRAME, rect2);
        bundle.putFloat(KEY_SCROLLABLE_AREA_INFO_DSS_SCALE, f);
        String packageName = this.mContext.getPackageName();
        bundle.putString("packageName", packageName);
        Context context = this.mContext;
        if (context instanceof Activity) {
            name = context.getClass().getName();
            bundle.putString("activityName", name);
        } else {
            name = null;
        }
        Log.d(TAG, "dispatchScrollableAreaInfo : Pkg=" + packageName + " Activity=" + name);
        sendResult(smartClipRemoteRequestInfo, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchScrollableViewInfo(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        View rootView = this.mViewRootImplGateway.getRootView();
        if (rootView != null) {
            int i = ((Bundle) smartClipRemoteRequestInfo.mRequestData).getInt(KEY_VIEW_INFO_HASHCODE, -1);
            if (i == -1) {
                Log.e(TAG, "dispatchScrollableViewInfo : There is no hash value in request!");
                return;
            }
            View viewFindViewByHashCode = findViewByHashCode(rootView, i);
            Bundle bundle = new Bundle();
            if (viewFindViewByHashCode == null) {
                Log.e(TAG, "dispatchScrollableViewInfo : Could not found the view! hash=" + i);
            } else {
                bundle.putParcelable(KEY_SCROLLABLE_AREA_INFO_WINDOW_RECT, getTranslatedViewBoundsOnScreen(rootView));
                bundle.putParcelable(KEY_SCROLLABLE_VIEW_INFO_TARGET_VIEW, createViewInfoAsBundle(viewFindViewByHashCode));
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                if (viewFindViewByHashCode instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) viewFindViewByHashCode;
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        arrayList.add(createViewInfoAsBundle(viewGroup.getChildAt(i2)));
                    }
                }
                bundle.putParcelableArrayList(KEY_SCROLLABLE_VIEW_INFO_CHILD_VIEWS, arrayList);
                Log.d(TAG, "dispatchScrollableViewInfo : " + viewFindViewByHashCode + "ChildCnt=" + arrayList.size());
            }
            sendResult(smartClipRemoteRequestInfo, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAirCommandHitTest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        if (this.mContext != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("result", 0);
            sendResult(smartClipRemoteRequestInfo, bundle);
        }
    }

    private Bundle createViewInfoAsBundle(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Bundle bundle = new Bundle();
        int iHashCode = view.hashCode();
        Rect translatedViewBoundsOnScreen = getTranslatedViewBoundsOnScreen(view);
        ArrayList<String> viewHierarchyTable = getViewHierarchyTable(view);
        bundle.putInt(KEY_VIEW_INFO_HASHCODE, iHashCode);
        bundle.putParcelable(KEY_VIEW_INFO_SCREEN_RECT, translatedViewBoundsOnScreen);
        bundle.putStringArrayList(KEY_VIEW_INFO_HIERARCHY, viewHierarchyTable);
        addScrollYInfoToBundle(view, bundle);
        addBrowserInfoToBundle(view, bundle);
        if (this.DEBUG) {
            Log.d(TAG, "createScrollableViewInfo : Scrollable view hash=@" + Integer.toHexString(iHashCode).toUpperCase() + " / Rect=" + translatedViewBoundsOnScreen);
            Iterator<String> it = viewHierarchyTable.iterator();
            while (it.hasNext()) {
                Log.d(TAG, "createScrollableViewInfo :   + " + it.next());
            }
        }
        return bundle;
    }

    private void addScrollYInfoToBundle(View view, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (view instanceof WebView) {
            bundle.putBoolean(KEY_VIEW_INFO_SCROLLY_SUPPORTED, true);
            bundle.putFloat(KEY_VIEW_INFO_SCROLLY, ((WebView) view).getScrollY());
            return;
        }
        Class<?> cls = view.getClass();
        if (cls.getSimpleName().equals("SpenComposer")) {
            Object objInvoke = null;
            try {
                objInvoke = cls.getMethod("getDeltaY", null).invoke(view, null);
            } catch (Exception e) {
                Log.e(TAG, "addScrollYInfoToBundle : view = " + cls.getSimpleName(), e);
            }
            if (objInvoke != null) {
                bundle.putBoolean(KEY_VIEW_INFO_SCROLLY_SUPPORTED, true);
                bundle.putFloat(KEY_VIEW_INFO_SCROLLY, ((Float) objInvoke).floatValue() * (-1.0f));
            }
        }
    }

    private void addBrowserInfoToBundle(View view, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = view.getClass();
        if (cls.getSimpleName().equals("TinContentView")) {
            Rect rect = null;
            try {
                Object objInvoke = cls.getMethod("getTinContentViewCore", null).invoke(view, null);
                rect = (Rect) objInvoke.getClass().getMethod("getCurrentVisibleRect", null).invoke(objInvoke, null);
            } catch (Exception e) {
                Log.e(TAG, "addBrowserInfoToBundle : view = " + cls.getSimpleName(), e);
            }
            if (rect != null) {
                bundle.putParcelable(KEY_VIEW_INFO_BROWSER_VISIBLE_RECT, rect);
            }
        }
    }

    private ArrayList<String> getViewHierarchyTable(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Class<?> superclass = view.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            String name = superclass.getName();
            arrayList.add(name);
            if ("android.view.View".equals(name)) {
                break;
            }
        }
        return arrayList;
    }

    private void findScrollableViews(View view, Rect rect, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        String str;
        String str2;
        boolean z;
        boolean z2;
        boolean z3;
        if (view == null || view.getVisibility() != 0 || view.getWidth() == 0 || view.getHeight() == 0) {
            return;
        }
        String name = view.getClass().getName();
        String name2 = view.getClass().getSuperclass().getName();
        Rect translatedViewBoundsOnScreen = getTranslatedViewBoundsOnScreen(view);
        if (Rect.intersects(rect, translatedViewBoundsOnScreen)) {
            String upperCase = Integer.toHexString(view.hashCode()).toUpperCase();
            String str3 = " H=";
            if ((view instanceof ScrollView) || (view instanceof AbsListView) || (view instanceof WebView)) {
                if (this.DEBUG) {
                    Log.d(TAG, "findScrollableViews : Scrollable view = @" + upperCase + " " + name + NavigationBarInflaterView.KEY_CODE_START + name2 + ") / Rect=" + translatedViewBoundsOnScreen + " H=" + translatedViewBoundsOnScreen.height() + " Rect=" + translatedViewBoundsOnScreen);
                }
                arrayList.add(view);
                return;
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                str = " Rect=";
                int childCount = viewGroup.getChildCount() - 1;
                while (true) {
                    str2 = str3;
                    if (childCount < 0) {
                        break;
                    }
                    findScrollableViews(viewGroup.getChildAt(childCount), rect, arrayList, arrayList2);
                    childCount--;
                    str3 = str2;
                }
            } else {
                str = " Rect=";
                str2 = " H=";
            }
            if ((view instanceof VideoView) || (view instanceof HorizontalScrollView)) {
                if (this.DEBUG) {
                    Log.d(TAG, "findScrollableViews : Unscrollable view = @" + upperCase + " " + name + NavigationBarInflaterView.KEY_CODE_START + name2 + ") / Rect=" + translatedViewBoundsOnScreen + str2 + translatedViewBoundsOnScreen.height() + str + translatedViewBoundsOnScreen);
                }
                arrayList2.add(view);
                return;
            }
            Class<?> superclass = view.getClass();
            boolean z4 = true;
            boolean z5 = false;
            Class<?>[] clsArr = {MotionEvent.class};
            Class<?>[] clsArr2 = {Canvas.class};
            boolean z6 = false;
            while (superclass != null) {
                String name3 = superclass.getName();
                if (name3.startsWith("android.view.") != z4 && name3.startsWith("android.widget.") != z4 && name3.startsWith("com.android.internal.") != z4) {
                    boolean z7 = z5;
                    if (isMethodDeclared(superclass, "dispatchTouchEvent", clsArr) == z4) {
                        if (this.DEBUG) {
                            Log.d(TAG, "findScrollableViews : @" + upperCase + " Have dispatchTouchEvent() " + name + " / " + superclass.getName() + " / Rect=" + translatedViewBoundsOnScreen);
                        }
                        z2 = true;
                    } else {
                        z2 = z7;
                    }
                    boolean z8 = z2;
                    if (isMethodDeclared(superclass, "onTouchEvent", clsArr)) {
                        if (this.DEBUG) {
                            Log.d(TAG, "findScrollableViews : @" + upperCase + " Have onTouchEvent() " + name + " / " + superclass.getName() + " / Rect=" + translatedViewBoundsOnScreen);
                        }
                        z3 = true;
                    } else {
                        z3 = z8;
                    }
                    z = z3;
                    if (isMethodDeclared(superclass, "onDraw", clsArr2)) {
                        if (this.DEBUG) {
                            Log.d(TAG, "findScrollableViews : @" + upperCase + " Have onDraw() " + name + " / " + superclass.getName() + " / Rect=" + translatedViewBoundsOnScreen);
                        }
                        z6 = true;
                    }
                    if (isMethodDeclared(superclass, "draw", clsArr2)) {
                        if (this.DEBUG) {
                            Log.d(TAG, "findScrollableViews : @" + upperCase + " Have draw() " + name + " / " + superclass.getName() + " / Rect=" + translatedViewBoundsOnScreen);
                        }
                        z6 = true;
                    }
                    if (isMethodDeclared(superclass, "dispatchDraw", clsArr2)) {
                        if (this.DEBUG) {
                            Log.d(TAG, "findScrollableViews : @" + upperCase + " Have dispatchDraw() " + name + " / " + superclass.getName() + " / Rect=" + translatedViewBoundsOnScreen);
                        }
                        z6 = true;
                    }
                    if (z && z6) {
                        break;
                    }
                    superclass = superclass.getSuperclass();
                    z5 = z;
                    z4 = true;
                } else {
                    break;
                }
            }
            z = z5;
            if (z) {
                arrayList.add(view);
                return;
            }
            return;
        }
        if (this.DEBUG) {
            Log.d(TAG, "findScrollableViews : Not in range - " + name + NavigationBarInflaterView.KEY_CODE_START + name2 + ") / Rect=" + translatedViewBoundsOnScreen);
        }
    }

    private boolean isMethodDeclared(Class<?> cls, String str, Class<?>[] clsArr) {
        try {
            return cls.getDeclaredMethod(str, clsArr) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    private View findViewByHashCode(View view, int i) {
        if (view == null) {
            return null;
        }
        if (view.hashCode() == i) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View viewFindViewByHashCode = findViewByHashCode(viewGroup.getChildAt(childCount), i);
                if (viewFindViewByHashCode != null) {
                    return viewFindViewByHashCode;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enqueueInputEvent(InputEvent inputEvent, boolean z) {
        ViewRootImplGateway viewRootImplGateway = this.mViewRootImplGateway;
        if (viewRootImplGateway == null) {
            Log.e(TAG, "enqueueInputEvent : Gateway is null!");
            return;
        }
        try {
            viewRootImplGateway.enqueueInputEvent(inputEvent, null, 0, z);
        } catch (Exception e) {
            Log.e(TAG, "enqueueInputEvent : Exception thrown. e = " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResult(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, Parcelable parcelable) {
        ((SpenGestureManager) this.mContext.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE)).sendSmartClipRemoteRequestResult(new SmartClipRemoteRequestResult(smartClipRemoteRequestInfo.mRequestId, smartClipRemoteRequestInfo.mRequestType, parcelable));
    }

    private void transformTouchPosition(MotionEvent motionEvent) {
        View rootView = this.mViewRootImplGateway.getRootView();
        if (rootView == null) {
            Log.e(TAG, "transformTouchPosition : Root view is not exists");
            return;
        }
        Rect translatedViewBoundsOnScreen = getTranslatedViewBoundsOnScreen(rootView);
        int i = translatedViewBoundsOnScreen.left;
        int i2 = translatedViewBoundsOnScreen.top;
        if (i != 0 || i2 != 0) {
            float rawX = motionEvent.getRawX() - i;
            float rawY = motionEvent.getRawY() - i2;
            motionEvent.setLocation(rawX, rawY);
            if (this.DEBUG) {
                Log.d(TAG, "transformMotionEvent : Window offsetX=" + i + " offsetY=" + i2 + " destX=" + rawX + " destY=" + rawY);
            }
        }
        PointF translatedPoint = this.mViewRootImplGateway.getTranslatedPoint();
        if (translatedPoint != null) {
            if (translatedPoint.x == 0.0f && translatedPoint.y == 0.0f) {
                return;
            }
            motionEvent.offsetLocation(-translatedPoint.x, -translatedPoint.y);
        }
    }

    private Rect getTranslatedViewBoundsOnScreen(View view) {
        Rect viewBoundsOnScreen = SmartClipUtils.getViewBoundsOnScreen(view);
        this.mViewRootImplGateway.getTranslatedRectIfNeeded(viewBoundsOnScreen);
        return viewBoundsOnScreen;
    }
}
