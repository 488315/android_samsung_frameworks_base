package com.samsung.android.content.smartclip;

import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.ims.options.SemCapabilities;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SmartClipDataCropperImpl extends SemSmartClipDataCropper {
  private static boolean DEBUG = false;
  public static final int EXTRACTION_LEVEL_0 = 0;
  public static final int EXTRACTION_LEVEL_1 = 1;
  private static final int EXTRACTION_RESULT_MAIN_MASKING = 255;
  private static final int MAX_META_VALUE_SIZE = 102400;
  private static final String META_NAME_SUPPORT_THIRD_PARTY_EXTRACTION_INTERFACE =
      "com.samsung.android.smartclip.support_custom_smartclip_metaextraction";
  private static final String TAG = "SmartClipDataCropperImpl";
  private static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";
  private static final String YOUTUBE_URL_PREFIX = "http://www.youtube.com/watch?v=";
  private String mChromeBrowserContentViewName;
  protected Context mContext;
  protected int mExtractionLevel;
  protected SmartClipDataExtractionEvent mExtractionRequest;
  private long mExtractionStartTime;
  protected boolean mIsExtractingData;
  private int mLastMetaFileId;
  protected String mPackageName;
  private int mPenWindowBorderWidth;
  protected ArrayList<SmartClipDataElementImpl> mPendingElements;
  private RectF mScaleRect;
  protected SemSmartClipDataRepository mSmartClipDataRepository;
  private boolean mSupportThirdPartyExtractionInterface;
  private boolean mUseViewPositionCache;
  private HashMap<View, Point> mViewPositionCache;
  private Rect mWinFrameRect;

  public SmartClipDataCropperImpl(Context context, SmartClipDataExtractionEvent extractionRequest) {
    this(context, extractionRequest, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f), 0);
  }

  public SmartClipDataCropperImpl(
      Context context,
      SmartClipDataExtractionEvent extractionRequest,
      Rect winFrameRect,
      RectF scaleRect,
      int penWindowBorderWidth) {
    this.mWinFrameRect = null;
    this.mScaleRect = null;
    this.mPenWindowBorderWidth = 0;
    this.mSmartClipDataRepository = null;
    this.mPendingElements = new ArrayList<>();
    this.mExtractionRequest = null;
    this.mIsExtractingData = false;
    this.mExtractionLevel = 0;
    this.mPackageName = null;
    this.mChromeBrowserContentViewName = null;
    this.mSupportThirdPartyExtractionInterface = false;
    this.mExtractionStartTime = 0L;
    this.mLastMetaFileId = 0;
    this.mUseViewPositionCache = false;
    this.mViewPositionCache = new HashMap<>();
    this.mContext = context;
    this.mExtractionRequest = extractionRequest;
    this.mWinFrameRect = new Rect(winFrameRect);
    this.mScaleRect = new RectF(scaleRect);
    this.mPenWindowBorderWidth = penWindowBorderWidth;
    String packageName = context.getPackageName();
    this.mPackageName = packageName;
    if (packageName == null) {
      this.mPackageName = "";
    }
    this.mChromeBrowserContentViewName =
        SmartClipUtils.getChromeViewClassNameFromManifest(context, this.mPackageName);
    this.mSupportThirdPartyExtractionInterface =
        isThirdPartyExtractionInterfaceEnabledOnManifest(context, this.mPackageName);
    PackageManager pm = context.getPackageManager();
    if (pm != null) {
      this.mExtractionLevel = 0;
      if (3 >= 3) {
        this.mExtractionLevel = 1;
      }
      if (pm.hasSystemFeature("com.samsung.android.smartclip.DEBUG")) {
        DEBUG = true;
      }
    }
  }

  public SemSmartClipDataRepository getSmartClipDataRepository() {
    return this.mSmartClipDataRepository;
  }

  public boolean doExtractSmartClipData(View rootView) {
    if (this.mExtractionRequest == null) {
      Log.m96e(TAG, "doExtractSmartClipData : extractionRequest is null!");
      return false;
    }
    this.mExtractionStartTime = System.currentTimeMillis();
    SmartClipCroppedAreaImpl croppedArea =
        new SmartClipCroppedAreaImpl(this.mExtractionRequest.mCropRect);
    Rect cropRect = croppedArea.getRect();
    String rectAreaStr = cropRect == null ? SemCapabilities.FEATURE_TAG_NULL : cropRect.toString();
    Log.m94d(
        TAG,
        "doExtractSmartClipData : Extraction start! reqId = "
            + this.mExtractionRequest.mRequestId
            + "  Cropped area = "
            + rectAreaStr
            + "  Package = "
            + this.mPackageName);
    this.mIsExtractingData = true;
    SemSmartClipDataRepository semSmartClipDataRepository =
        new SemSmartClipDataRepository(
            this, this.mWinFrameRect, this.mScaleRect, this.mPenWindowBorderWidth);
    this.mSmartClipDataRepository = semSmartClipDataRepository;
    SmartClipDataElementImpl rootElement =
        (SmartClipDataElementImpl) semSmartClipDataRepository.getRootElement();
    this.mViewPositionCache.clear();
    if (this.mExtractionRequest.mExtractionMode == 2
        || this.mExtractionRequest.mExtractionMode == 3) {
      traverseViewForDragAndDrop(rootView, croppedArea, this.mSmartClipDataRepository, rootElement);
    } else {
      traverseView(rootView, croppedArea, this.mSmartClipDataRepository, rootElement);
    }
    this.mViewPositionCache.clear();
    addAppMetaTag(rootElement);
    this.mSmartClipDataRepository.setAppPackageName(this.mPackageName);
    this.mIsExtractingData = false;
    if (this.mPendingElements.size() == 0) {
      this.mSmartClipDataRepository.determineContentType();
      sendExtractionResultToSmartClipService();
    }
    return true;
  }

  protected void addAppMetaTag(SemSmartClipDataElement element) {
    if (this.mContext == null) {
      Log.m96e(TAG, "addAppMetaTag : mContext is null!");
      return;
    }
    Log.m94d(TAG, "addAppMetaTag : package name is " + this.mPackageName);
    SemSmartClipExtendedMetaTag intentTag =
        new SemSmartClipExtendedMetaTag(SemSmartClipMetaTagType.APP_LAUNCH_INFO, this.mPackageName);
    element.addTag(intentTag);
  }

  public boolean setPendingExtractionResult(SemSmartClipDataElement resultElement) {
    int elementIndex = findElementIndexFromPendingList((SmartClipDataElementImpl) resultElement);
    if (elementIndex < 0) {
      return false;
    }
    this.mPendingElements.remove(elementIndex);
    SmartClipDataElementImpl elementImpl = (SmartClipDataElementImpl) resultElement;
    if (!elementImpl.isEmptyTag(false)) {
      if (DEBUG) {
        Log.m94d(
            TAG,
            "setPendingExtractionResult : Contains meta data : "
                + elementImpl.getDumpString(false, true));
      } else {
        Log.m94d(
            TAG,
            "setPendingExtractionResult : Contains meta data : "
                + elementImpl.getDumpString(false, false));
      }
    }
    if (this.mPendingElements.size() == 0 && !this.mIsExtractingData) {
      this.mSmartClipDataRepository.determineContentType();
      sendExtractionResultToSmartClipService();
    }
    return true;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r1v2 */
  /* JADX WARN: Type inference failed for: r1v3, types: [android.view.ViewParent] */
  /* JADX WARN: Type inference failed for: r1v4, types: [android.view.ViewParent] */
  /* JADX WARN: Type inference failed for: r1v7 */
  /* JADX WARN: Type inference failed for: r1v8 */
  /* JADX WARN: Type inference failed for: r5v0, types: [android.view.View, java.lang.Object] */
  protected ArrayList<View> getParentList(View view) {
    View view2;
    ArrayList<View> arrayList = new ArrayList<>();
    if (view instanceof ViewGroup) {
      view2 = (ViewParent) view;
    } else {
      arrayList.add(view);
      view2 = view.getParent();
    }
    while (view2 != 0) {
      if (view2 instanceof ViewGroup) {
        arrayList.add(view2);
      }
      view2 = view2.getParent();
    }
    return arrayList;
  }

  protected int findElementIndexFromPendingList(SmartClipDataElementImpl element) {
    int pendingCount = this.mPendingElements.size();
    for (int i = 0; i < pendingCount; i++) {
      if (this.mPendingElements.get(i) == element) {
        return i;
      }
    }
    return -1;
  }

  protected boolean sendExtractionResultToSmartClipService() {
    if (this.mPendingElements.size() > 0) {
      Log.m96e(TAG, "Cannot send the extraction result due to it still have pending element!");
      return false;
    }
    SemSmartClipDataRepository semSmartClipDataRepository = this.mSmartClipDataRepository;
    if (semSmartClipDataRepository != null) {
      return sendExtractionResultToSmartClipService(semSmartClipDataRepository);
    }
    Log.m96e(TAG, "Cannot send the extraction result due to it is NULL!");
    return false;
  }

  public int getExtractionMode() {
    SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
    if (smartClipDataExtractionEvent != null) {
      return smartClipDataExtractionEvent.mExtractionMode;
    }
    return 0;
  }

  public int getExtractionLevel() {
    return this.mExtractionLevel;
  }

  public boolean sendExtractionResultToSmartClipService(SemSmartClipDataRepository result) {
    SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
    if (smartClipDataExtractionEvent == null) {
      Log.m96e(TAG, "sendExtractionResultToSmartClipService : extractionRequest is null!");
      return false;
    }
    if (result != null && smartClipDataExtractionEvent.mExtractionMode == 0) {
      filterMetaTagForBrowserViews((SmartClipDataElementImpl) result.getRootElement());
    }
    if (result != null) {
      Log.m94d(
          TAG,
          "sendExtractionResultToSmartClipService : -- Extracted SmartClip data information --");
      Log.m94d(
          TAG,
          "sendExtractionResultToSmartClipService : Request Id : "
              + this.mExtractionRequest.mRequestId);
      Log.m94d(
          TAG,
          "sendExtractionResultToSmartClipService : Extraction mode : "
              + this.mExtractionRequest.mExtractionMode);
      result.dump(DEBUG);
    } else {
      Log.m96e(TAG, "sendExtractionResultToSmartClipService : The repository is null");
    }
    SpenGestureManager spenGestureManager =
        (SpenGestureManager) this.mContext.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
    SmartClipDataExtractionResponse response =
        new SmartClipDataExtractionResponse(
            this.mExtractionRequest.mRequestId, this.mExtractionRequest.mExtractionMode, result);
    if (result != null && this.mExtractionRequest.mTargetWindowLayer >= 0) {
      result.setWindowLayer(this.mExtractionRequest.mTargetWindowLayer);
    }
    try {
      SmartClipRemoteRequestResult resultData =
          new SmartClipRemoteRequestResult(this.mExtractionRequest.mRequestId, 1, response);
      spenGestureManager.sendSmartClipRemoteRequestResult(resultData);
    } catch (RuntimeException e) {
      Log.m96e(TAG, "sendExtractionResultToSmartClipService : Failed to send the result! e=" + e);
      Log.m96e(TAG, "sendExtractionResultToSmartClipService : Send empty response...");
      SmartClipRemoteRequestResult emptyResultData =
          new SmartClipRemoteRequestResult(this.mExtractionRequest.mRequestId, 1, null);
      spenGestureManager.sendSmartClipRemoteRequestResult(emptyResultData);
    }
    Log.m94d(
        TAG,
        "sendExtractionResultToSmartClipService : Elapsed = "
            + (System.currentTimeMillis() - this.mExtractionStartTime));
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public Rect getViewBoundsOnScreen(View view) {
    Rect screenRectOfView = new Rect();
    Point screenPointOfView = getViewLocationOnScreen(view);
    screenRectOfView.left = screenPointOfView.f85x;
    screenRectOfView.top = screenPointOfView.f86y;
    screenRectOfView.right = screenRectOfView.left + view.getWidth();
    screenRectOfView.bottom = screenRectOfView.top + view.getHeight();
    return screenRectOfView;
  }

  private Point getViewLocationOnScreen(View view) {
    Point screenPointOfView = null;
    if (this.mUseViewPositionCache) {
      Point screenPointOfView2 = this.mViewPositionCache.get(view);
      screenPointOfView = screenPointOfView2;
    }
    if (screenPointOfView == null) {
      screenPointOfView = SmartClipUtils.getViewLocationOnScreen(view);
      if (this.mUseViewPositionCache) {
        this.mViewPositionCache.put(view, screenPointOfView);
      }
    }
    return screenPointOfView;
  }

  public int extractDefaultSmartClipData(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    String viewClassName;
    if (resultElement == null) {
      Log.m96e(TAG, "extractDefaultSmartClipData : The result element is null!");
      return 0;
    }
    if (croppedArea == null) {
      Log.m96e(TAG, "extractDefaultSmartClipData : The cropped area is null!");
      return 0;
    }
    try {
      viewClassName = view.getClass().getName();
    } catch (ClassCastException e) {
      Log.m96e(TAG, "ClassCastException in traverseView : target class is " + view.toString());
      e.printStackTrace();
    }
    if (this.mPackageName.equals(YOUTUBE_PACKAGE_NAME) && viewClassName.endsWith("PlayerView")) {
      return extractDefaultSmartClipData_YoutubePlayerView(view, croppedArea, resultElement);
    }
    String str = this.mChromeBrowserContentViewName;
    if (str == null || !SmartClipUtils.isInstanceOf(view, str)) {
      if (viewClassName.equals("org.chromium.content.browser.JellyBeanContentView")) {
        return extractDefaultSmartClipData_GoogleChromeView(view, croppedArea, resultElement);
      }
      if (view instanceof TextView) {
        return extractDefaultSmartClipData_TextView(view, croppedArea, resultElement);
      }
      if (view instanceof ImageView) {
        return extractDefaultSmartClipData_ImageView(view, croppedArea, resultElement);
      }
      if (view instanceof TextureView) {
        return extractDefaultSmartClipData_TextureView(view, croppedArea, resultElement);
      }
      return 1;
    }
    Log.m94d(TAG, "extractDefaultSmartClipData : Has chrome view");
    return extractDefaultSmartClipData_GoogleChromeView(view, croppedArea, resultElement);
  }

  private int extractDefaultSmartClipData_YoutubePlayerView(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    return 1;
  }

  private int extractDefaultSmartClipData_TextView(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    TextView textView;
    TransformationMethod transformationMethod;
    if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0
        && ((transformationMethod = (textView = (TextView) view).getTransformationMethod()) == null
            || !(transformationMethod instanceof PasswordTransformationMethod))) {
      CharSequence charSequence = textView.getText();
      if (charSequence == null) {
        charSequence = "";
      }
      SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
      if (smartClipDataExtractionEvent != null
          && smartClipDataExtractionEvent.mExtractionMode == 2) {
        Rect spanRect = textView.getSpannedTextRect(croppedArea.getRect());
        if (spanRect != null) {
          resultElement.setMetaAreaRect(spanRect);
          charSequence = "";
        }
        if (textView.hasSelection()) {
          int selStart = textView.getSelectionStart();
          int selEnd = textView.getSelectionEnd();
          int min = Math.max(0, Math.min(selStart, selEnd));
          int max = Math.max(0, Math.max(selStart, selEnd));
          CharSequence selectedText = charSequence.subSequence(min, max);
          if (selectedText != null) {
            resultElement.addTag(
                new SemSmartClipMetaTag(
                    SemSmartClipMetaTagType.TEXT_SELECTION, selectedText.toString()));
          }
        }
      }
      resultElement.addTag(
          new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, charSequence.toString()));
    }
    return 1;
  }

  private int extractDefaultSmartClipData_ImageView(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    ImageView imageView = (ImageView) view;
    if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0
        && (imageView.getDrawable() != null || imageView.getBackground() != null)) {
      resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
    }
    SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
    if (smartClipDataExtractionEvent != null
        && smartClipDataExtractionEvent.mExtractionMode == 4
        && imageView.getDrawable() != null
        && (imageView.getDrawable() instanceof BitmapDrawable)) {
      BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
      Bitmap bitmap = drawable.getBitmap();
      resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.BITMAP, null, bitmap));
      return 1;
    }
    return 1;
  }

  private int extractDefaultSmartClipData_TextureView(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0) {
      resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
      return 1;
    }
    return 1;
  }

  private int extractDefaultSmartClipData_GoogleChromeView(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    try {
      Method extractSmartClipDataMethod =
          view.getClass()
              .getMethod(
                  "extractSmartClipData", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
      Method setSmartClipResultHandlerMethod =
          view.getClass().getMethod("setSmartClipResultHandler", Handler.class);
      if (extractSmartClipDataMethod != null && setSmartClipResultHandlerMethod != null) {
        Log.m94d(TAG, "Extracting meta data from Chrome view...");
        Handler handler =
            new Handler(
                resultElement,
                view,
                setSmartClipResultHandlerMethod) { // from class:
                                                   // com.samsung.android.content.smartclip.SmartClipDataCropperImpl.1
              public SemSmartClipDataElement mResult;
              final /* synthetic */ SmartClipDataElementImpl val$resultElement;
              final /* synthetic */ Method val$setSmartClipResultHandlerMethod;
              final /* synthetic */ View val$view;

              {
                this.val$resultElement = resultElement;
                this.val$view = view;
                this.val$setSmartClipResultHandlerMethod = setSmartClipResultHandlerMethod;
                this.mResult = resultElement;
              }

              @Override // android.p009os.Handler
              public void handleMessage(Message msg) {
                Log.m94d(SmartClipDataCropperImpl.TAG, "Meta data arrived from chrome");
                Bundle bundle = msg.getData();
                if (bundle == null) {
                  Log.m96e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                  SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                  return;
                }
                String title = bundle.getString("title");
                String url = bundle.getString("url");
                String html = bundle.getString(SemSmartClipMetaTagType.HTML);
                String text = bundle.getString("text");
                Rect area = (Rect) bundle.getParcelable("rect");
                String context = bundle.getString(SemSmartClipMetaTagType.CONTEXT);
                if (SmartClipDataCropperImpl.DEBUG) {
                  Log.m94d(
                      SmartClipDataCropperImpl.TAG,
                      String.format(
                          "Title:%s\nURL:%s\nArea:%s\nText:%s\nHTML:%s",
                          title, url, area, text, html));
                }
                if (!TextUtils.isEmpty(title)) {
                  this.val$resultElement.setTag(new SemSmartClipMetaTag("title", title));
                }
                if (!TextUtils.isEmpty(url)) {
                  this.val$resultElement.setTag(new SemSmartClipMetaTag("url", url));
                }
                if (!TextUtils.isEmpty(html)) {
                  this.val$resultElement.setTag(
                      new SemSmartClipMetaTag(SemSmartClipMetaTagType.HTML, html));
                }
                if (!TextUtils.isEmpty(text)) {
                  this.val$resultElement.setTag(
                      new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, text));
                }
                if (!TextUtils.isEmpty(context)) {
                  this.val$resultElement.setTag(
                      new SemSmartClipMetaTag(SemSmartClipMetaTagType.CONTEXT, context));
                }
                if (area != null) {
                  DisplayMetrics metrics =
                      SmartClipDataCropperImpl.this.mContext.getResources().getDisplayMetrics();
                  area.left = (int) TypedValue.applyDimension(1, area.left, metrics);
                  area.top = (int) TypedValue.applyDimension(1, area.top, metrics);
                  area.right = (int) TypedValue.applyDimension(1, area.right, metrics);
                  area.bottom = (int) TypedValue.applyDimension(1, area.bottom, metrics);
                  Rect screenRectOfView =
                      SmartClipDataCropperImpl.this.getViewBoundsOnScreen(this.val$view);
                  area.offset(screenRectOfView.left, screenRectOfView.top);
                  area.intersect(screenRectOfView);
                  this.val$resultElement.setMetaAreaRect(area);
                }
                try {
                  this.val$setSmartClipResultHandlerMethod.invoke(this.val$view, null);
                } catch (Exception e) {
                  Log.m96e(
                      SmartClipDataCropperImpl.TAG, "Could not invoke set smartclip handler API");
                  e.printStackTrace();
                }
                SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
              }
            };
        Rect cropRect = new Rect(croppedArea.getRect());
        int[] screenPosOfView = new int[2];
        view.getLocationOnScreen(screenPosOfView);
        cropRect.offset(-screenPosOfView[0], -screenPosOfView[1]);
        setSmartClipResultHandlerMethod.invoke(view, handler);
        if (DEBUG) {
          Log.m94d(
              TAG,
              "Converting coordinate : "
                  + croppedArea.getRect().toString()
                  + " -> "
                  + cropRect.toString());
        }
        extractSmartClipDataMethod.invoke(
            view,
            Integer.valueOf(cropRect.left),
            Integer.valueOf(cropRect.top),
            Integer.valueOf(cropRect.width()),
            Integer.valueOf(cropRect.height()));
        return 2;
      }
    } catch (Exception e) {
      Log.m96e(TAG, "Current chrome view does not support smartclip");
    }
    try {
      Method getUrlMethod = view.getClass().getMethod("getUrl", new Class[0]);
      String url = (String) getUrlMethod.invoke(view, new Object[0]);
      resultElement.setTag(new SemSmartClipMetaTag("url", url));
      Method getTitleMethod = view.getClass().getMethod("getTitle", new Class[0]);
      String title = (String) getTitleMethod.invoke(view, new Object[0]);
      resultElement.setTag(new SemSmartClipMetaTag("title", title));
      SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
      if (smartClipDataExtractionEvent != null) {
        if (smartClipDataExtractionEvent.mExtractionMode == 0) {
          resultElement.setMetaAreaRect(croppedArea.getRect());
        } else {
          int i = this.mExtractionRequest.mExtractionMode;
        }
      }
    } catch (Exception e2) {
      e2.printStackTrace();
    }
    return 1;
  }

  private int extractDefaultSmartClipData_ThirdPartyInterface(
      View view, SemSmartClipCroppedArea croppedArea, SmartClipDataElementImpl resultElement) {
    Object invokeObj = view;
    try {
      Method extractSmartClipDataMethod = getThirPartyExtractionInterfaceMethod(invokeObj);
      if (extractSmartClipDataMethod == null && (invokeObj = view.getTag()) != null) {
        extractSmartClipDataMethod = getThirPartyExtractionInterfaceMethod(invokeObj);
      }
      if (invokeObj != null && extractSmartClipDataMethod != null) {
        Log.m94d(TAG, "Extracting meta data using third party interface...");
        Handler handler =
            new Handler(
                resultElement,
                view) { // from class:
                        // com.samsung.android.content.smartclip.SmartClipDataCropperImpl.2
              public SemSmartClipDataElement mResult;
              final /* synthetic */ SmartClipDataElementImpl val$resultElement;
              final /* synthetic */ View val$view;

              {
                this.val$resultElement = resultElement;
                this.val$view = view;
                this.mResult = resultElement;
              }

              @Override // android.p009os.Handler
              public void handleMessage(Message msg) {
                Log.m94d(
                    SmartClipDataCropperImpl.TAG, "Pending meta data arrived from third party");
                Bundle bundle = msg.getData();
                if (bundle == null) {
                  Log.m96e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                  SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                } else {
                  SmartClipDataCropperImpl.this.updateDataElementWithBundle(
                      this.val$view, bundle, this.val$resultElement);
                  SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                }
              }
            };
        Object retValue =
            extractSmartClipDataMethod.invoke(invokeObj, croppedArea.getRect(), handler);
        if (retValue == null || !(retValue instanceof Bundle)) {
          Log.m94d(TAG, "Null returned immediately from third party. waiting pending meta data..");
          return 2;
        }
        Log.m94d(TAG, "Bundle data returned immediately from third party");
        updateDataElementWithBundle(view, (Bundle) retValue, resultElement);
        return 1;
      }
    } catch (Exception e) {
      Log.m96e(
          TAG, "Exception is thrown during execute the third party smartclip interface. e=" + e);
      e.printStackTrace();
    }
    return 1;
  }

  private int getMainResultFromExtractionResult(int extractionResult) {
    return extractionResult & 255;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r4v1, types: [android.view.ViewParent] */
  /* JADX WARN: Type inference failed for: r4v3 */
  /* JADX WARN: Type inference failed for: r4v4 */
  private Rect adjustMetaAreaRect(View view, Rect rect) {
    Rect viewBoundsOnScreen = getViewBoundsOnScreen(view);
    Rect rect2 = new Rect();
    if (rect == null) {
      Log.m96e(TAG, "adjustMetaAreaRect : rect is null");
      return null;
    }
    for (View view2 = view.getParent(); view2 != 0; view2 = view2.getParent()) {
      if (view2 instanceof ViewGroup) {
        Rect viewBoundsOnScreen2 = getViewBoundsOnScreen(view2);
        Rect rect3 = new Rect();
        if (rect3.setIntersect(viewBoundsOnScreen, viewBoundsOnScreen2)) {
          viewBoundsOnScreen = rect3;
        }
      }
    }
    if (rect2.setIntersect(rect, viewBoundsOnScreen)) {
      return rect2;
    }
    Log.m96e(
        TAG,
        "adjustMetaAreaRect : there is no intersection " + rect + " and " + viewBoundsOnScreen);
    return null;
  }

  private Rect getOpaqueBackgroundRect(SmartClipDataElementImpl element) {
    Drawable background;
    Rect metaRect;
    Rect adjustedRect;
    SmartClipDataElementImpl curElement = element;
    Rect opaqueRect = null;
    while (curElement != null) {
      View view = curElement.getView();
      if (view != null
          && (background = view.getBackground()) != null
          && background.isVisible()
          && background.getOpacity() != -2
          && (metaRect = curElement.getMetaAreaRect()) != null
          && (adjustedRect = adjustMetaAreaRect(view, metaRect)) != null) {
        if (opaqueRect == null) {
          opaqueRect = new Rect(adjustedRect);
        } else {
          opaqueRect.union(adjustedRect);
        }
      }
      curElement = curElement.traverseNextElement(element);
    }
    Log.m94d(TAG, "getOpaqueBackgroundRect : opaqueRect=" + opaqueRect + "  element=" + element);
    return opaqueRect;
  }

  private boolean removeSmartClipDataElementByRect(
      SmartClipDataElementImpl element, Rect rectToDelete) {
    SmartClipDataElementImpl child = element.getLastChild();
    while (child != null) {
      SmartClipDataElementImpl cur = child;
      child = child.getPrevSibling();
      removeSmartClipDataElementByRect(cur, rectToDelete);
    }
    if (element.getFirstChild() == null) {
      Rect metaAreaRect = element.getMetaAreaRect();
      if (element.isEmptyTag(false)) {
        element.getParent().removeChild(element);
        return true;
      }
      if (metaAreaRect != null && Rect.intersects(rectToDelete, metaAreaRect)) {
        Log.m94d(
            TAG,
            "removeSmartClipDataElementByRect : Removing element due to RECT intersection. element"
                + " = "
                + element.getDumpString(false, true));
        element.getParent().removeChild(element);
        return true;
      }
    }
    return false;
  }

  private void filterMetaTagForBrowserViews(SmartClipDataElementImpl element) {
    SmartClipDataCropperImpl smartClipDataCropperImpl = this;
    if (element == null) {
      Log.m96e(TAG, "filterMetaTagForBrowserViews : element is null!");
      return;
    }
    SmartClipDataElementImpl curElement = element;
    while (curElement != null) {
      SemSmartClipMetaTagArray tags = curElement.getTagTable();
      if (tags != null) {
        View view = curElement.getView();
        String viewName =
            view != null ? view.getClass().getSimpleName() : SemCapabilities.FEATURE_TAG_NULL;
        int htmlTagCount = tags.getMetaTags(SemSmartClipMetaTagType.HTML).size();
        int textTagCount = tags.getMetaTags(SemSmartClipMetaTagType.PLAIN_TEXT).size();
        boolean z = true;
        if (htmlTagCount > 0 && textTagCount > 0) {
          switch (smartClipDataCropperImpl.mExtractionLevel) {
            case 0:
              tags.removeMetaTags(SemSmartClipMetaTagType.HTML);
              Log.m94d(TAG, "filterMetaTagForBrowserViews : Discarding HTML tag from " + viewName);
              break;
            default:
              Iterator<SemSmartClipMetaTag> it = tags.iterator();
              while (it.hasNext()) {
                SemSmartClipMetaTag curTag = it.next();
                if (SemSmartClipMetaTagType.PLAIN_TEXT.equals(curTag.getType())) {
                  curTag.setType(SemSmartClipMetaTagType.HTML_TEXT);
                }
              }
              Log.m94d(
                  TAG,
                  "filterMetaTagForBrowserViews : The TEXT tag changed to HTML_TEXT. View="
                      + viewName);
              break;
          }
        }
        Iterator<SemSmartClipMetaTag> it2 = tags.iterator();
        while (it2.hasNext()) {
          SemSmartClipMetaTag curTag2 = it2.next();
          String type = curTag2.getType();
          if (SemSmartClipMetaTagType.HTML.equals(type) == z) {
            String value = curTag2.getValue();
            if (value.length() > MAX_META_VALUE_SIZE) {
              Log.m96e(
                  TAG,
                  "filterMetaTagForBrowserViews : Have large HTML data("
                      + value.length()
                      + " bytes). Converting tag..");
              String filePathName = allocateMetaTagFilePath();
              if (!smartClipDataCropperImpl.writeStringToFile(filePathName, value)) {
                Log.m96e(
                    TAG,
                    "filterMetaTagForBrowserViews : Failed to save meta tag! - " + filePathName);
              } else {
                Log.m94d(
                    TAG, "filterMetaTagForBrowserViews : Saved the meta tag to " + filePathName);
              }
              curTag2.setType(SemSmartClipMetaTagType.FILE_PATH_HTML);
              curTag2.setValue(filePathName);
            }
          }
          z = true;
          smartClipDataCropperImpl = this;
        }
      }
      curElement = curElement.traverseNextElement(element);
      smartClipDataCropperImpl = this;
    }
  }

  private boolean writeStringToFile(String filePathName, String value) {
    StringBuilder sb;
    boolean ret = true;
    Log.m94d(TAG, "writeStringToFile : " + filePathName);
    File file = new File(filePathName);
    FileOutputStream os = null;
    try {
      try {
        os = new FileOutputStream(file);
        os.write(value.getBytes());
      } catch (Exception e) {
        Log.m96e(TAG, "writeStringToFile : File write failed! " + e);
        ret = false;
        if (os != null) {
          try {
            os.close();
          } catch (Exception e2) {
            e = e2;
            sb = new StringBuilder();
            Log.m96e(
                TAG, sb.append("writeStringToFile : File close failed! ").append(e).toString());
            ret = false;
            file.setReadable(true, false);
            file.setWritable(true, false);
            return ret;
          }
        }
      }
      try {
        os.close();
      } catch (Exception e3) {
        e = e3;
        sb = new StringBuilder();
        Log.m96e(TAG, sb.append("writeStringToFile : File close failed! ").append(e).toString());
        ret = false;
        file.setReadable(true, false);
        file.setWritable(true, false);
        return ret;
      }
      file.setReadable(true, false);
      file.setWritable(true, false);
      return ret;
    } catch (Throwable th) {
      if (os != null) {
        try {
          os.close();
        } catch (Exception e4) {
          Log.m96e(TAG, "writeStringToFile : File close failed! " + e4);
        }
      }
      throw th;
    }
  }

  private String allocateMetaTagFilePath() {
    String baseDirPath = this.mContext.getFilesDir().getAbsolutePath() + "/smartclip";
    File baseDir = new File(baseDirPath);
    if (!baseDir.exists()) {
      baseDir.mkdir();
      baseDir.setWritable(true, false);
      baseDir.setReadable(true, false);
      baseDir.setExecutable(true, false);
    }
    int i = this.mLastMetaFileId + 1;
    this.mLastMetaFileId = i;
    int i2 = i % 3;
    this.mLastMetaFileId = i2;
    String filePathName = String.format("%s/SC%02d", baseDirPath, Integer.valueOf(i2));
    return filePathName;
  }

  private ArrayList<View> getChildViewsByZOrder(ViewGroup viewGroup) {
    int childCount = viewGroup.getChildCount();
    ArrayList<View> childViews = new ArrayList<>(childCount);
    boolean zOrderExist = false;
    for (int i = 0; i < childCount; i++) {
      View currentChild = viewGroup.getChildAt(i);
      float currentZ = currentChild.getZ();
      if (currentZ != 0.0f) {
        zOrderExist = true;
      }
      int insertIndex = i;
      while (insertIndex > 0 && childViews.get(insertIndex - 1).getZ() > currentZ) {
        insertIndex--;
      }
      childViews.add(insertIndex, currentChild);
    }
    if (zOrderExist) {
      Log.m94d(TAG, "getChildViewsByZOrder : Z order detected");
      Iterator<View> it = childViews.iterator();
      while (it.hasNext()) {
        View child = it.next();
        Log.m94d(
            TAG,
            "getChildViewsByZOrder : Parent="
                + viewGroup
                + " / View="
                + child
                + " / Z="
                + child.getZ());
      }
    }
    return childViews;
  }

  private boolean isThirdPartyExtractionInterfaceEnabledOnManifest(
      Context context, String packageName) {
    ApplicationInfo ai;
    boolean isFeatureEnabled = false;
    try {
      ai = context.getPackageManager().getApplicationInfo(packageName, 128);
    } catch (PackageManager.NameNotFoundException e) {
    }
    if (ai == null) {
      Log.m96e(
          TAG, "isSupportThirdPartyExtractionInterface : Could not get appInfo! - " + packageName);
      return false;
    }
    Bundle bundle = ai.metaData;
    if (bundle != null
        && (isFeatureEnabled =
            bundle.getBoolean(META_NAME_SUPPORT_THIRD_PARTY_EXTRACTION_INTERFACE, false))) {
      Log.m94d(TAG, "isSupportThirdPartyExtractionInterface : Feature enabled");
    }
    return isFeatureEnabled;
  }

  private boolean isSupportThirdPartyExtractionInterface(View view) {
    if (view == null) {
      return false;
    }
    if (getThirPartyExtractionInterfaceMethod(view) != null) {
      return true;
    }
    Object tagObject = view.getTag();
    return (tagObject == null || getThirPartyExtractionInterfaceMethod(tagObject) == null)
        ? false
        : true;
  }

  private Method getThirPartyExtractionInterfaceMethod(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      Method method = obj.getClass().getMethod("extractSmartClipData", Rect.class, Handler.class);
      return method;
    } catch (NoSuchMethodException | SecurityException e) {
      return null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean updateDataElementWithBundle(
      View view, Bundle bundle, SmartClipDataElementImpl resultElement) {
    boolean isElementUpdated = false;
    String title = bundle.getString("title");
    String url = bundle.getString("url");
    String appLink = bundle.getString("app_link");
    Rect area = (Rect) bundle.getParcelable("rect");
    if (DEBUG) {
      Log.m94d(
          TAG,
          String.format(
              "fillDataElementWithBundle : Title:%s\nLink:%s\nURL:%s\nArea:%s",
              title, appLink, url, area));
    }
    if (!TextUtils.isEmpty(title)) {
      resultElement.setTag(new SemSmartClipMetaTag("title", title));
      isElementUpdated = true;
    }
    if (!TextUtils.isEmpty(url)) {
      resultElement.setTag(new SemSmartClipMetaTag("url", url));
      isElementUpdated = true;
    }
    if (!TextUtils.isEmpty(appLink)) {
      resultElement.setTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.APP_DEEP_LINK, appLink));
      isElementUpdated = true;
    }
    if (area != null) {
      Rect screenRectOfView = getViewBoundsOnScreen(view);
      area.intersect(screenRectOfView);
      resultElement.setMetaAreaRect(area);
      return true;
    }
    return isElementUpdated;
  }

  private boolean traverseView(
      View view,
      SemSmartClipCroppedArea croppedArea,
      SemSmartClipDataRepository smartClipDataRepository,
      SmartClipDataElementImpl parentDataElement) {
    int extractionResult;
    Rect curOpaqueRect;
    SemSmartClipCroppedArea semSmartClipCroppedArea = croppedArea;
    boolean haveCroppedView = false;
    if (view != null && view.getVisibility() == 0 && view.getWidth() > 0 && view.getHeight() > 0) {
      Rect screenRectOfView = getViewBoundsOnScreen(view);
      if (Rect.intersects(croppedArea.getRect(), screenRectOfView)) {
        SmartClipDataElementImpl element =
            new SmartClipDataElementImpl(smartClipDataRepository, view, screenRectOfView);
        SmartClipMetaTagArrayImpl defaultViewTags =
            (SmartClipMetaTagArrayImpl) view.semGetSmartClipTags();
        if (defaultViewTags != null) {
          element.setTagTable(defaultViewTags.getCopy());
        }
        SemSmartClipDataExtractionListener listener = view.semGetSmartClipDataExtractionListener();
        if (this.mSupportThirdPartyExtractionInterface
            && isSupportThirdPartyExtractionInterface(view)) {
          extractionResult =
              extractDefaultSmartClipData_ThirdPartyInterface(
                  view, semSmartClipCroppedArea, element);
        } else if (listener != null) {
          extractionResult =
              listener.onExtractSmartClipData(view, semSmartClipCroppedArea, element);
        } else {
          extractionResult = view.semExtractSmartClipData(semSmartClipCroppedArea, element);
        }
        for (SmartClipDataElementImpl elementTraveler = element;
            elementTraveler != null;
            elementTraveler = elementTraveler.traverseNextElement(element)) {
          Rect adjustedMetaAreaRect = adjustMetaAreaRect(view, elementTraveler.getMetaAreaRect());
          elementTraveler.setMetaAreaRect(adjustedMetaAreaRect);
        }
        int mainResult = getMainResultFromExtractionResult(extractionResult);
        switch (mainResult) {
          case 0:
            element.clearMetaData();
            break;
          case 1:
            break;
          case 2:
            this.mPendingElements.add(element);
            haveCroppedView = true;
            break;
          default:
            String viewString = view.toString();
            Log.m96e(
                TAG,
                "Unknown main extraction result value : " + mainResult + " / View = " + viewString);
            element.clearMetaData();
            break;
        }
        boolean skipExtractionFromChildView = (extractionResult & 256) != 0;
        if ((view instanceof ViewGroup) && !skipExtractionFromChildView) {
          ArrayList<View> childViewArray = getChildViewsByZOrder((ViewGroup) view);
          int childCount = childViewArray.size();
          boolean haveCroppedView2 = haveCroppedView;
          int i = 0;
          while (i < childCount) {
            Rect screenRectOfView2 = screenRectOfView;
            View child = childViewArray.get(i);
            boolean skipExtractionFromChildView2 = skipExtractionFromChildView;
            boolean bFound =
                traverseView(child, semSmartClipCroppedArea, smartClipDataRepository, element);
            if (bFound) {
              haveCroppedView2 = true;
            }
            i++;
            semSmartClipCroppedArea = croppedArea;
            skipExtractionFromChildView = skipExtractionFromChildView2;
            screenRectOfView = screenRectOfView2;
          }
          haveCroppedView = haveCroppedView2;
        }
        if (!element.isEmptyTag(true)) {
          haveCroppedView = true;
        }
        if (!element.isEmptyTag(false)) {
          if (DEBUG) {
            Log.m94d(
                TAG, "traverseView : Contains meta data : " + element.getDumpString(false, true));
          } else {
            Log.m94d(
                TAG, "traverseView : Contains meta data : " + element.getDumpString(false, false));
          }
        }
        if (haveCroppedView) {
          if ((view instanceof FrameLayout) || (view instanceof RelativeLayout)) {
            SmartClipDataElementImpl childElement = element.getLastChild();
            Rect opaqueRect = null;
            while (childElement != null) {
              boolean isCurElementRemoved = false;
              SmartClipDataElementImpl curElement = childElement;
              childElement = childElement.getPrevSibling();
              if (opaqueRect != null) {
                isCurElementRemoved = removeSmartClipDataElementByRect(curElement, opaqueRect);
              }
              if (!isCurElementRemoved
                  && (curOpaqueRect = getOpaqueBackgroundRect(curElement)) != null) {
                if (opaqueRect == null) {
                  opaqueRect = curOpaqueRect;
                } else {
                  opaqueRect.union(curOpaqueRect);
                }
              }
            }
          }
          parentDataElement.addChild(element);
        }
      }
    }
    return haveCroppedView;
  }

  /* JADX WARN: Removed duplicated region for block: B:42:0x00e2  */
  /* JADX WARN: Removed duplicated region for block: B:44:0x00e6  */
  /* JADX WARN: Removed duplicated region for block: B:47:0x00ec  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean traverseViewForDragAndDrop(
      View view,
      SemSmartClipCroppedArea croppedArea,
      SemSmartClipDataRepository smartClipDataRepository,
      SmartClipDataElementImpl parentDataElement) {
    int extractionResult;
    boolean haveCroppedView;
    SmartClipDataCropperImpl smartClipDataCropperImpl = this;
    boolean haveCroppedView2 = false;
    if (view != null && view.getVisibility() == 0 && view.getWidth() > 0 && view.getHeight() > 0) {
      Rect screenRectOfView = getViewBoundsOnScreen(view);
      if (Rect.intersects(croppedArea.getRect(), screenRectOfView)) {
        SmartClipDataElementImpl element =
            new SmartClipDataElementImpl(smartClipDataRepository, view, screenRectOfView);
        boolean needToCallListener = false;
        SemSmartClipDataExtractionListener listener = view.semGetSmartClipDataExtractionListener();
        if (listener != null && (view instanceof SurfaceView)) {
          needToCallListener = true;
        }
        if (needToCallListener) {
          extractionResult = listener.onExtractSmartClipData(view, croppedArea, element);
        } else {
          extractionResult = view.semExtractSmartClipData(croppedArea, element);
        }
        for (SmartClipDataElementImpl elementTraveler = element;
            elementTraveler != null;
            elementTraveler = elementTraveler.traverseNextElement(element)) {
          Rect adjustedMetaAreaRect =
              smartClipDataCropperImpl.adjustMetaAreaRect(view, elementTraveler.getMetaAreaRect());
          elementTraveler.setMetaAreaRect(adjustedMetaAreaRect);
        }
        int mainResult =
            smartClipDataCropperImpl.getMainResultFromExtractionResult(extractionResult);
        switch (mainResult) {
          case 0:
            element.clearMetaData();
            break;
          case 1:
            break;
          case 2:
            smartClipDataCropperImpl.mPendingElements.add(element);
            haveCroppedView2 = true;
            break;
          default:
            String viewString = view.toString();
            Log.m96e(
                TAG,
                "Unknown main extraction result value : " + mainResult + " / View = " + viewString);
            element.clearMetaData();
            break;
        }
        boolean skipExtractionFromChildView = (extractionResult & 256) != 0;
        if (!(view instanceof ViewGroup) || skipExtractionFromChildView) {
          haveCroppedView = haveCroppedView2;
        } else {
          ArrayList<View> childViewArray =
              smartClipDataCropperImpl.getChildViewsByZOrder((ViewGroup) view);
          int childCount = childViewArray.size();
          int i = childCount - 1;
          while (i >= 0) {
            View child = childViewArray.get(i);
            boolean haveCroppedView3 = haveCroppedView2;
            boolean bFound =
                smartClipDataCropperImpl.traverseViewForDragAndDrop(
                    child, croppedArea, smartClipDataRepository, element);
            if (!bFound) {
              i--;
              smartClipDataCropperImpl = this;
              haveCroppedView2 = haveCroppedView3;
            } else {
              haveCroppedView2 = true;
              if (!element.isEmptyTag(true)) {
                haveCroppedView2 = true;
              }
              if (!haveCroppedView2) {
                parentDataElement.addChild(element);
              }
            }
          }
          haveCroppedView = haveCroppedView2;
        }
        haveCroppedView2 = haveCroppedView;
        if (!element.isEmptyTag(true)) {}
        if (!haveCroppedView2) {}
      }
    }
    return haveCroppedView2;
  }
}
