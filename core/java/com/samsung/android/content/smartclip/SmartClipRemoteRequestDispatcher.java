package com.samsung.android.content.smartclip;

import android.app.Activity;
import android.app.ActivityThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Parcelable;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
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
  private static final String KEY_SCROLLABLE_AREA_INFO_VISIBLE_DISPLAY_FRAME =
      "visibleDisplayFrame";
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
  public static final String PERMISSION_EXTRACT_SMARTCLIP_DATA =
      "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA";
  public static final String PERMISSION_INJECT_INPUT_EVENT = "android.permission.INJECT_EVENTS";
  private static final String SBROWSER_VIEW_CLASS_NAME = "TinContentView";
  public static final String TAG = "SmartClipRemoteRequestDispatcher";
  private boolean DEBUG = false;
  private Context mContext;
  private Handler mHandler;
  private ViewRootImplGateway mViewRootImplGateway;

  public interface ViewRootImplGateway {
    void enqueueInputEvent(
        InputEvent inputEvent, InputEventReceiver inputEventReceiver, int i, boolean z);

    Handler getHandler();

    View getRootView();

    PointF getScaleFactor();

    PointF getTranslatedPoint();

    void getTranslatedRectIfNeeded(Rect rect);

    ViewRootImpl getViewRootImpl();
  }

  public SmartClipRemoteRequestDispatcher(
      Context context, ViewRootImplGateway viewRootImplGateway) {
    this.mContext = context;
    this.mViewRootImplGateway = viewRootImplGateway;
    this.mHandler = viewRootImplGateway.getHandler();
  }

  public boolean isDebugMode() {
    return this.DEBUG;
  }

  public void checkPermission(String permission, int pid, int uid) {
    boolean havePermission = this.mContext.checkPermission(permission, pid, uid) == 0;
    if (!havePermission) {
      String errStr = "Requires " + permission + " permission";
      Log.m96e(TAG, "checkPermission : " + errStr);
      throw new SecurityException(errStr);
    }
  }

  public void dispatchSmartClipRemoteRequest(final SmartClipRemoteRequestInfo request) {
    switch (request.mRequestType) {
      case 2:
        checkPermission(
            "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA",
            request.mCallerPid,
            request.mCallerUid);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.1
              @Override // java.lang.Runnable
              public void run() {
                SmartClipRemoteRequestDispatcher.this.dispatchAirCommandHitTest(request);
              }
            });
        break;
      case 3:
        checkPermission("android.permission.INJECT_EVENTS", request.mCallerPid, request.mCallerUid);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.4
              @Override // java.lang.Runnable
              public void run() {
                SmartClipRemoteRequestDispatcher.this.dispatchInputEventInjection(request);
              }
            });
        break;
      case 4:
        checkPermission(
            "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA",
            request.mCallerPid,
            request.mCallerUid);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.2
              @Override // java.lang.Runnable
              public void run() {
                SmartClipRemoteRequestDispatcher.this.dispatchScrollableAreaInfo(request);
              }
            });
        break;
      case 5:
        checkPermission(
            "com.samsung.android.permission.EXTRACT_SMARTCLIP_DATA",
            request.mCallerPid,
            request.mCallerUid);
        this.mHandler.post(
            new Runnable() { // from class:
                             // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.3
              @Override // java.lang.Runnable
              public void run() {
                SmartClipRemoteRequestDispatcher.this.dispatchScrollableViewInfo(request);
              }
            });
        break;
      default:
        Log.m96e(
            TAG,
            "dispatchSmartClipRemoteRequest : Unknown request type("
                + request.mRequestType
                + NavigationBarInflaterView.KEY_CODE_END);
        break;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void dispatchInputEventInjection(final SmartClipRemoteRequestInfo request) {
    if (request.mRequestData != null) {
      if (request.mRequestData instanceof MotionEvent) {
        MotionEvent motionEvent = (MotionEvent) request.mRequestData;
        transformTouchPosition(motionEvent);
        int action = motionEvent.getAction();
        if (this.DEBUG || action == 0 || action == 1 || action == 3) {
          int x = (int) motionEvent.getRawX();
          int y = (int) motionEvent.getRawY();
          Log.m94d(
              TAG,
              "dispatchInputEventInjection : Touch event action="
                  + MotionEvent.actionToString(action)
                  + " x="
                  + x
                  + " y="
                  + y);
        }
        InputEvent inputEvent = (InputEvent) request.mRequestData;
        enqueueInputEvent(inputEvent, true);
        return;
      }
      if (request.mRequestData instanceof Bundle) {
        Bundle reqData = (Bundle) request.mRequestData;
        final Parcelable[] events = reqData.getParcelableArray("events");
        if (events != null) {
          final boolean waitUntilConsume =
              reqData.getBoolean(KEY_EVENT_INJECTION_WAIT_UNTIL_CONSUME);
          long firstEventTime = events.length > 0 ? ((InputEvent) events[0]).getEventTime() : -1L;
          if (this.DEBUG) {
            Log.m94d(
                TAG,
                "dispatchInputEventInjection : wait = "
                    + waitUntilConsume
                    + "  eventCount="
                    + events.length);
          }
          for (Parcelable parcelable : events) {
            final InputEvent event = (InputEvent) parcelable;
            if (event != null) {
              if (event instanceof MotionEvent) {
                transformTouchPosition((MotionEvent) event);
              }
              Runnable runnable =
                  new Runnable() { // from class:
                                   // com.samsung.android.content.smartclip.SmartClipRemoteRequestDispatcher.5
                    @Override // java.lang.Runnable
                    public void run() {
                      long startTime = System.currentTimeMillis();
                      if (SmartClipRemoteRequestDispatcher.this.DEBUG) {
                        Log.m94d(
                            SmartClipRemoteRequestDispatcher.TAG,
                            "dispatchInputEventInjection : injecting.. " + event);
                      }
                      SmartClipRemoteRequestDispatcher.this.enqueueInputEvent(event, true);
                      InputEvent inputEvent2 = event;
                      Parcelable[] parcelableArr = events;
                      if (inputEvent2 == parcelableArr[parcelableArr.length - 1]) {
                        if (waitUntilConsume) {
                          SmartClipRemoteRequestDispatcher.this.sendResult(request, null);
                        }
                        Log.m94d(
                            SmartClipRemoteRequestDispatcher.TAG,
                            "dispatchInputEventInjection : injection finished. Elapsed = "
                                + (System.currentTimeMillis() - startTime));
                      }
                    }
                  };
              long delay = event.getEventTime() - firstEventTime;
              if (delay > 0) {
                this.mHandler.postDelayed(runnable, delay);
              } else {
                runnable.run();
              }
            }
          }
          return;
        }
        Log.m96e(TAG, "dispatchInputEventInjection : Event is null!");
        return;
      }
      return;
    }
    Log.m96e(TAG, "dispatchInputEventInjection : Empty input event!");
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void dispatchScrollableAreaInfo(SmartClipRemoteRequestInfo request) {
    View rootView = this.mViewRootImplGateway.getRootView();
    if (rootView == null) {
      Log.m96e(TAG, "dispatchScrollableAreaInfo : Root view is null!");
      return;
    }
    ArrayList<View> scrollableViews = new ArrayList<>();
    ArrayList<View> unscrollableViews = new ArrayList<>();
    Rect windowRect = getTranslatedViewBoundsOnScreen(rootView);
    Log.m94d(TAG, "dispatchScrollableAreaInfo : windowRect = " + windowRect);
    findScrollableViews(rootView, windowRect, scrollableViews, unscrollableViews);
    Bundle resultData = new Bundle();
    ArrayList<Bundle> scrollableViewInfo = new ArrayList<>();
    Iterator<View> it = scrollableViews.iterator();
    while (it.hasNext()) {
      View view = it.next();
      Bundle viewInfo = createViewInfoAsBundle(view);
      scrollableViewInfo.add(viewInfo);
    }
    Log.m94d(
        TAG, "dispatchScrollableAreaInfo : Scrollable view count = " + scrollableViewInfo.size());
    resultData.putParcelableArrayList(
        KEY_SCROLLABLE_AREA_INFO_SCROLLABLE_VIEWS, scrollableViewInfo);
    ArrayList<Bundle> unscrollableViewInfo = new ArrayList<>();
    Iterator<View> it2 = unscrollableViews.iterator();
    while (it2.hasNext()) {
      View view2 = it2.next();
      Bundle viewInfo2 = createViewInfoAsBundle(view2);
      unscrollableViewInfo.add(viewInfo2);
    }
    Log.m94d(
        TAG,
        "dispatchScrollableAreaInfo : Unscrollable view count = " + unscrollableViewInfo.size());
    resultData.putParcelableArrayList(
        KEY_SCROLLABLE_AREA_INFO_UNSCROLLABLE_VIEWS, unscrollableViewInfo);
    resultData.putParcelable(KEY_SCROLLABLE_AREA_INFO_WINDOW_RECT, windowRect);
    resultData.putInt(KEY_SCROLLABLE_AREA_INFO_WINDOW_LAYER, request.mTargetWindowLayer);
    Rect displayFrame = new Rect();
    Rect visibleDisplayFrame = new Rect();
    float dssScale = ActivityThread.currentActivityThread().getDssScale();
    rootView.getWindowDisplayFrame(displayFrame);
    rootView.getWindowVisibleDisplayFrame(visibleDisplayFrame);
    this.mViewRootImplGateway.getTranslatedRectIfNeeded(visibleDisplayFrame);
    resultData.putParcelable(KEY_SCROLLABLE_AREA_INFO_DISPLAY_FRAME, displayFrame);
    resultData.putParcelable(KEY_SCROLLABLE_AREA_INFO_VISIBLE_DISPLAY_FRAME, visibleDisplayFrame);
    resultData.putFloat(KEY_SCROLLABLE_AREA_INFO_DSS_SCALE, dssScale);
    String pkgName = this.mContext.getPackageName();
    String activityName = null;
    resultData.putString("packageName", pkgName);
    Context context = this.mContext;
    if (context instanceof Activity) {
      activityName = context.getClass().getName();
      resultData.putString("activityName", activityName);
    }
    Log.m94d(TAG, "dispatchScrollableAreaInfo : Pkg=" + pkgName + " Activity=" + activityName);
    sendResult(request, resultData);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void dispatchScrollableViewInfo(SmartClipRemoteRequestInfo request) {
    View rootView = this.mViewRootImplGateway.getRootView();
    if (rootView != null) {
      int viewHash = ((Bundle) request.mRequestData).getInt(KEY_VIEW_INFO_HASHCODE, -1);
      if (viewHash == -1) {
        Log.m96e(TAG, "dispatchScrollableViewInfo : There is no hash value in request!");
        return;
      }
      View view = findViewByHashCode(rootView, viewHash);
      Bundle resultData = new Bundle();
      if (view == null) {
        Log.m96e(TAG, "dispatchScrollableViewInfo : Could not found the view! hash=" + viewHash);
      } else {
        Rect windowRect = getTranslatedViewBoundsOnScreen(rootView);
        resultData.putParcelable(KEY_SCROLLABLE_AREA_INFO_WINDOW_RECT, windowRect);
        Bundle targetViewInfo = createViewInfoAsBundle(view);
        resultData.putParcelable(KEY_SCROLLABLE_VIEW_INFO_TARGET_VIEW, targetViewInfo);
        ArrayList<Bundle> childInfoArray = new ArrayList<>();
        if (view instanceof ViewGroup) {
          ViewGroup vg = (ViewGroup) view;
          int childCount = vg.getChildCount();
          for (int i = 0; i < childCount; i++) {
            View childView = vg.getChildAt(i);
            Bundle childInfo = createViewInfoAsBundle(childView);
            childInfoArray.add(childInfo);
          }
        }
        resultData.putParcelableArrayList(KEY_SCROLLABLE_VIEW_INFO_CHILD_VIEWS, childInfoArray);
        Log.m94d(TAG, "dispatchScrollableViewInfo : " + view + "ChildCnt=" + childInfoArray.size());
      }
      sendResult(request, resultData);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void dispatchAirCommandHitTest(SmartClipRemoteRequestInfo request) {
    if (this.mContext != null) {
      Bundle resultData = new Bundle();
      resultData.putInt("result", 0);
      sendResult(request, resultData);
    }
  }

  private Bundle createViewInfoAsBundle(View view) {
    Bundle bundle = new Bundle();
    int hashCode = view.hashCode();
    Rect screenRectOfView = getTranslatedViewBoundsOnScreen(view);
    ArrayList<String> viewHierarchy = getViewHierarchyTable(view);
    bundle.putInt(KEY_VIEW_INFO_HASHCODE, hashCode);
    bundle.putParcelable(KEY_VIEW_INFO_SCREEN_RECT, screenRectOfView);
    bundle.putStringArrayList(KEY_VIEW_INFO_HIERARCHY, viewHierarchy);
    addScrollYInfoToBundle(view, bundle);
    addBrowserInfoToBundle(view, bundle);
    if (this.DEBUG) {
      Log.m94d(
          TAG,
          "createScrollableViewInfo : Scrollable view hash=@"
              + Integer.toHexString(hashCode).toUpperCase()
              + " / Rect="
              + screenRectOfView);
      Iterator<String> it = viewHierarchy.iterator();
      while (it.hasNext()) {
        String clsName = it.next();
        Log.m94d(TAG, "createScrollableViewInfo :   + " + clsName);
      }
    }
    return bundle;
  }

  private void addScrollYInfoToBundle(View view, Bundle bundle) {
    if (view instanceof WebView) {
      WebView webView = (WebView) view;
      bundle.putBoolean(KEY_VIEW_INFO_SCROLLY_SUPPORTED, true);
      bundle.putFloat(KEY_VIEW_INFO_SCROLLY, webView.getScrollY());
      return;
    }
    Class<?> cls = view.getClass();
    if (cls.getSimpleName().equals("SpenComposer")) {
      Object deltaY = null;
      try {
        Method method = cls.getMethod("getDeltaY", null);
        deltaY = method.invoke(view, null);
      } catch (Exception e) {
        Log.m97e(TAG, "addScrollYInfoToBundle : view = " + cls.getSimpleName(), e);
      }
      if (deltaY != null) {
        bundle.putBoolean(KEY_VIEW_INFO_SCROLLY_SUPPORTED, true);
        bundle.putFloat(KEY_VIEW_INFO_SCROLLY, ((Float) deltaY).floatValue() * (-1.0f));
      }
    }
  }

  private void addBrowserInfoToBundle(View view, Bundle bundle) {
    Class<?> cls = view.getClass();
    if (cls.getSimpleName().startsWith(SBROWSER_VIEW_CLASS_NAME)) {
      Rect visibleRect = null;
      try {
        Object coreTinContentView = cls.getMethod("getTinContentViewCore", null).invoke(view, null);
        visibleRect =
            (Rect)
                coreTinContentView
                    .getClass()
                    .getMethod("getCurrentVisibleRect", null)
                    .invoke(coreTinContentView, null);
      } catch (Exception e) {
        Log.m97e(TAG, "addBrowserInfoToBundle : view = " + cls.getSimpleName(), e);
      }
      if (visibleRect != null) {
        bundle.putParcelable(KEY_VIEW_INFO_BROWSER_VISIBLE_RECT, visibleRect);
      }
    }
  }

  private ArrayList<String> getViewHierarchyTable(View view) {
    ArrayList<String> hierarchy = new ArrayList<>();
    for (Class<?> cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
      String clsName = cls.getName();
      hierarchy.add(clsName);
      if ("android.view.View".equals(clsName)) {
        break;
      }
    }
    return hierarchy;
  }

  private void findScrollableViews(
      View view,
      Rect windowRect,
      ArrayList<View> scrollableViews,
      ArrayList<View> unscrollableViews) {
    String str;
    String str2;
    String str3;
    String str4;
    boolean haveCustomTouchEventHandler;
    boolean haveCustomDrawHandler;
    boolean haveCustomTouchEventHandler2;
    if (view == null || view.getVisibility() != 0 || view.getWidth() == 0) {
      return;
    }
    if (view.getHeight() == 0) {
      return;
    }
    String viewClassName = view.getClass().getName();
    String parentClassName = view.getClass().getSuperclass().getName();
    Rect screenRectOfView = getTranslatedViewBoundsOnScreen(view);
    if (!Rect.intersects(windowRect, screenRectOfView)) {
      if (this.DEBUG) {
        Log.m94d(
            TAG,
            "findScrollableViews : Not in range - "
                + viewClassName
                + NavigationBarInflaterView.KEY_CODE_START
                + parentClassName
                + ") / Rect="
                + screenRectOfView);
        return;
      }
      return;
    }
    String hashCode = Integer.toHexString(view.hashCode()).toUpperCase();
    String str5 = " H=";
    if ((view instanceof ScrollView) || (view instanceof AbsListView)) {
      str = " Rect=";
      str2 = " H=";
    } else {
      if (!(view instanceof WebView)) {
        if (!(view instanceof ViewGroup)) {
          str3 = " Rect=";
          str4 = " H=";
        } else {
          ViewGroup viewGroup = (ViewGroup) view;
          int childCount = viewGroup.getChildCount();
          str3 = " Rect=";
          int i = childCount - 1;
          while (i >= 0) {
            String str6 = str5;
            View curView = viewGroup.getChildAt(i);
            findScrollableViews(curView, windowRect, scrollableViews, unscrollableViews);
            i--;
            str5 = str6;
          }
          str4 = str5;
        }
        if ((view instanceof VideoView) || (view instanceof HorizontalScrollView)) {
          if (this.DEBUG) {
            Log.m94d(
                TAG,
                "findScrollableViews : Unscrollable view = @"
                    + hashCode
                    + " "
                    + viewClassName
                    + NavigationBarInflaterView.KEY_CODE_START
                    + parentClassName
                    + ") / Rect="
                    + screenRectOfView
                    + str4
                    + screenRectOfView.height()
                    + str3
                    + screenRectOfView);
          }
          unscrollableViews.add(view);
          return;
        }
        boolean haveCustomTouchEventHandler3 = false;
        boolean haveCustomDrawHandler2 = false;
        Class<?> cls = view.getClass();
        Class<?>[] paramEvent = {MotionEvent.class};
        Class<?>[] paramCanvas = {Canvas.class};
        while (true) {
          if (cls == null) {
            haveCustomTouchEventHandler = haveCustomTouchEventHandler3;
            haveCustomDrawHandler = haveCustomDrawHandler2;
            break;
          }
          String clsName = cls.getName();
          haveCustomTouchEventHandler = haveCustomTouchEventHandler3;
          if (clsName.startsWith("android.view.")) {
            haveCustomDrawHandler = haveCustomDrawHandler2;
            break;
          }
          if (clsName.startsWith("android.widget.")) {
            haveCustomDrawHandler = haveCustomDrawHandler2;
            break;
          }
          if (!clsName.startsWith("com.android.internal.")) {
            boolean haveCustomDrawHandler3 = haveCustomDrawHandler2;
            if (isMethodDeclared(cls, "dispatchTouchEvent", paramEvent)) {
              if (this.DEBUG) {
                haveCustomTouchEventHandler = true;
                Log.m94d(
                    TAG,
                    "findScrollableViews : @"
                        + hashCode
                        + " Have dispatchTouchEvent() "
                        + viewClassName
                        + " / "
                        + cls.getName()
                        + " / Rect="
                        + screenRectOfView);
              } else {
                haveCustomTouchEventHandler = true;
              }
            }
            if (isMethodDeclared(cls, "onTouchEvent", paramEvent)) {
              if (this.DEBUG) {
                haveCustomTouchEventHandler = true;
                Log.m94d(
                    TAG,
                    "findScrollableViews : @"
                        + hashCode
                        + " Have onTouchEvent() "
                        + viewClassName
                        + " / "
                        + cls.getName()
                        + " / Rect="
                        + screenRectOfView);
              } else {
                haveCustomTouchEventHandler = true;
              }
            }
            if (isMethodDeclared(cls, "onDraw", paramCanvas)) {
              if (this.DEBUG) {
                haveCustomDrawHandler3 = true;
                Log.m94d(
                    TAG,
                    "findScrollableViews : @"
                        + hashCode
                        + " Have onDraw() "
                        + viewClassName
                        + " / "
                        + cls.getName()
                        + " / Rect="
                        + screenRectOfView);
              } else {
                haveCustomDrawHandler3 = true;
              }
            }
            if (isMethodDeclared(cls, "draw", paramCanvas)) {
              haveCustomDrawHandler3 = true;
              if (this.DEBUG) {
                Log.m94d(
                    TAG,
                    "findScrollableViews : @"
                        + hashCode
                        + " Have draw() "
                        + viewClassName
                        + " / "
                        + cls.getName()
                        + " / Rect="
                        + screenRectOfView);
              }
            }
            if (!isMethodDeclared(cls, "dispatchDraw", paramCanvas)) {
              haveCustomDrawHandler2 = haveCustomDrawHandler3;
            } else {
              if (this.DEBUG) {
                Log.m94d(
                    TAG,
                    "findScrollableViews : @"
                        + hashCode
                        + " Have dispatchDraw() "
                        + viewClassName
                        + " / "
                        + cls.getName()
                        + " / Rect="
                        + screenRectOfView);
              }
              haveCustomDrawHandler2 = true;
            }
            if (haveCustomTouchEventHandler && haveCustomDrawHandler2) {
              haveCustomTouchEventHandler2 = haveCustomTouchEventHandler;
              break;
            } else {
              cls = cls.getSuperclass();
              haveCustomTouchEventHandler3 = haveCustomTouchEventHandler;
            }
          } else {
            haveCustomDrawHandler = haveCustomDrawHandler2;
            break;
          }
        }
        haveCustomTouchEventHandler2 = haveCustomTouchEventHandler;
        if (haveCustomTouchEventHandler2) {
          scrollableViews.add(view);
        }
        return;
      }
      str = " Rect=";
      str2 = " H=";
    }
    if (this.DEBUG) {
      Log.m94d(
          TAG,
          "findScrollableViews : Scrollable view = @"
              + hashCode
              + " "
              + viewClassName
              + NavigationBarInflaterView.KEY_CODE_START
              + parentClassName
              + ") / Rect="
              + screenRectOfView
              + str2
              + screenRectOfView.height()
              + str
              + screenRectOfView);
    }
    scrollableViews.add(view);
  }

  private boolean isMethodDeclared(Class<?> cls, String methodName, Class<?>[] paramTypes) {
    try {
      Method method = cls.getDeclaredMethod(methodName, paramTypes);
      if (method != null) {
        return true;
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }

  private View findViewByHashCode(View view, int viewHash) {
    if (view == null) {
      return null;
    }
    if (view.hashCode() == viewHash) {
      return view;
    }
    if (view instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup) view;
      int childCount = viewGroup.getChildCount();
      for (int i = childCount - 1; i >= 0; i--) {
        View curView = viewGroup.getChildAt(i);
        View foundView = findViewByHashCode(curView, viewHash);
        if (foundView != null) {
          return foundView;
        }
      }
    }
    return null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void enqueueInputEvent(InputEvent inputEvent, boolean processImmediately) {
    ViewRootImplGateway viewRootImplGateway = this.mViewRootImplGateway;
    if (viewRootImplGateway == null) {
      Log.m96e(TAG, "enqueueInputEvent : Gateway is null!");
      return;
    }
    try {
      viewRootImplGateway.enqueueInputEvent(inputEvent, null, 0, processImmediately);
    } catch (Exception e) {
      Log.m96e(TAG, "enqueueInputEvent : Exception thrown. e = " + e);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void sendResult(SmartClipRemoteRequestInfo request, Parcelable resultData) {
    SpenGestureManager spenGestureManager =
        (SpenGestureManager) this.mContext.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
    SmartClipRemoteRequestResult result =
        new SmartClipRemoteRequestResult(request.mRequestId, request.mRequestType, resultData);
    spenGestureManager.sendSmartClipRemoteRequestResult(result);
  }

  private void transformTouchPosition(MotionEvent event) {
    View rootView = this.mViewRootImplGateway.getRootView();
    if (rootView == null) {
      Log.m96e(TAG, "transformTouchPosition : Root view is not exists");
      return;
    }
    Rect windowRect = getTranslatedViewBoundsOnScreen(rootView);
    int windowX = windowRect.left;
    int windowY = windowRect.top;
    if (windowX != 0 || windowY != 0) {
      float rawX = event.getRawX();
      float rawY = event.getRawY();
      float x = rawX - windowX;
      float y = rawY - windowY;
      event.setLocation(x, y);
      if (this.DEBUG) {
        Log.m94d(
            TAG,
            "transformMotionEvent : Window offsetX="
                + windowX
                + " offsetY="
                + windowY
                + " destX="
                + x
                + " destY="
                + y);
      }
    }
    PointF translatedPoint = this.mViewRootImplGateway.getTranslatedPoint();
    if (translatedPoint != null) {
      if (translatedPoint.f87x != 0.0f || translatedPoint.f88y != 0.0f) {
        event.offsetLocation(-translatedPoint.f87x, -translatedPoint.f88y);
      }
    }
  }

  private Rect getTranslatedViewBoundsOnScreen(View view) {
    Rect boundRect = SmartClipUtils.getViewBoundsOnScreen(view);
    this.mViewRootImplGateway.getTranslatedRectIfNeeded(boundRect);
    return boundRect;
  }
}
