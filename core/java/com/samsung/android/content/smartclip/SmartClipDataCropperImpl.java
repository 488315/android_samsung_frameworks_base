package com.samsung.android.content.smartclip;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
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

    public SmartClipDataCropperImpl(
            Context context, SmartClipDataExtractionEvent extractionRequest) {
        this(
                context,
                extractionRequest,
                new Rect(0, 0, 0, 0),
                new RectF(0.0f, 0.0f, 1.0f, 1.0f),
                0);
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
        this.mPackageName = context.getPackageName();
        if (this.mPackageName == null) {
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
            Log.e(TAG, "doExtractSmartClipData : extractionRequest is null!");
            return false;
        }
        this.mExtractionStartTime = System.currentTimeMillis();
        SmartClipCroppedAreaImpl croppedArea =
                new SmartClipCroppedAreaImpl(this.mExtractionRequest.mCropRect);
        Rect cropRect = croppedArea.getRect();
        String rectAreaStr = cropRect == null ? "null" : cropRect.toString();
        Log.d(
                TAG,
                "doExtractSmartClipData : Extraction start! reqId = "
                        + this.mExtractionRequest.mRequestId
                        + "  Cropped area = "
                        + rectAreaStr
                        + "  Package = "
                        + this.mPackageName);
        this.mIsExtractingData = true;
        this.mSmartClipDataRepository =
                new SemSmartClipDataRepository(
                        this, this.mWinFrameRect, this.mScaleRect, this.mPenWindowBorderWidth);
        SmartClipDataElementImpl rootElement =
                (SmartClipDataElementImpl) this.mSmartClipDataRepository.getRootElement();
        this.mViewPositionCache.clear();
        if (this.mExtractionRequest.mExtractionMode == 2
                || this.mExtractionRequest.mExtractionMode == 3) {
            traverseViewForDragAndDrop(
                    rootView, croppedArea, this.mSmartClipDataRepository, rootElement);
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
            Log.e(TAG, "addAppMetaTag : mContext is null!");
            return;
        }
        Log.d(TAG, "addAppMetaTag : package name is " + this.mPackageName);
        SemSmartClipExtendedMetaTag intentTag =
                new SemSmartClipExtendedMetaTag(
                        SemSmartClipMetaTagType.APP_LAUNCH_INFO, this.mPackageName);
        element.addTag(intentTag);
    }

    public boolean setPendingExtractionResult(SemSmartClipDataElement resultElement) {
        int elementIndex =
                findElementIndexFromPendingList((SmartClipDataElementImpl) resultElement);
        if (elementIndex < 0) {
            return false;
        }
        this.mPendingElements.remove(elementIndex);
        SmartClipDataElementImpl elementImpl = (SmartClipDataElementImpl) resultElement;
        if (!elementImpl.isEmptyTag(false)) {
            if (DEBUG) {
                Log.d(
                        TAG,
                        "setPendingExtractionResult : Contains meta data : "
                                + elementImpl.getDumpString(false, true));
            } else {
                Log.d(
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
            Log.e(TAG, "Cannot send the extraction result due to it still have pending element!");
            return false;
        }
        if (this.mSmartClipDataRepository != null) {
            return sendExtractionResultToSmartClipService(this.mSmartClipDataRepository);
        }
        Log.e(TAG, "Cannot send the extraction result due to it is NULL!");
        return false;
    }

    public int getExtractionMode() {
        if (this.mExtractionRequest != null) {
            return this.mExtractionRequest.mExtractionMode;
        }
        return 0;
    }

    public int getExtractionLevel() {
        return this.mExtractionLevel;
    }

    public boolean sendExtractionResultToSmartClipService(SemSmartClipDataRepository result) {
        if (this.mExtractionRequest == null) {
            Log.e(TAG, "sendExtractionResultToSmartClipService : extractionRequest is null!");
            return false;
        }
        if (result != null && this.mExtractionRequest.mExtractionMode == 0) {
            filterMetaTagForBrowserViews((SmartClipDataElementImpl) result.getRootElement());
        }
        if (result != null) {
            Log.d(
                    TAG,
                    "sendExtractionResultToSmartClipService : -- Extracted SmartClip data"
                        + " information --");
            Log.d(
                    TAG,
                    "sendExtractionResultToSmartClipService : Request Id : "
                            + this.mExtractionRequest.mRequestId);
            Log.d(
                    TAG,
                    "sendExtractionResultToSmartClipService : Extraction mode : "
                            + this.mExtractionRequest.mExtractionMode);
            result.dump(DEBUG);
        } else {
            Log.e(TAG, "sendExtractionResultToSmartClipService : The repository is null");
        }
        SpenGestureManager spenGestureManager =
                (SpenGestureManager)
                        this.mContext.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        SmartClipDataExtractionResponse response =
                new SmartClipDataExtractionResponse(
                        this.mExtractionRequest.mRequestId,
                        this.mExtractionRequest.mExtractionMode,
                        result);
        if (result != null && this.mExtractionRequest.mTargetWindowLayer >= 0) {
            result.setWindowLayer(this.mExtractionRequest.mTargetWindowLayer);
        }
        try {
            SmartClipRemoteRequestResult resultData =
                    new SmartClipRemoteRequestResult(
                            this.mExtractionRequest.mRequestId, 1, response);
            spenGestureManager.sendSmartClipRemoteRequestResult(resultData);
        } catch (RuntimeException e) {
            Log.e(
                    TAG,
                    "sendExtractionResultToSmartClipService : Failed to send the result! e=" + e);
            Log.e(TAG, "sendExtractionResultToSmartClipService : Send empty response...");
            SmartClipRemoteRequestResult emptyResultData =
                    new SmartClipRemoteRequestResult(this.mExtractionRequest.mRequestId, 1, null);
            spenGestureManager.sendSmartClipRemoteRequestResult(emptyResultData);
        }
        Log.d(
                TAG,
                "sendExtractionResultToSmartClipService : Elapsed = "
                        + (System.currentTimeMillis() - this.mExtractionStartTime));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getViewBoundsOnScreen(View view) {
        Rect screenRectOfView = new Rect();
        Point screenPointOfView = getViewLocationOnScreen(view);
        screenRectOfView.left = screenPointOfView.x;
        screenRectOfView.top = screenPointOfView.y;
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
            View view,
            SemSmartClipCroppedArea croppedArea,
            SmartClipDataElementImpl resultElement) {
        String viewClassName;
        if (resultElement == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The result element is null!");
            return 0;
        }
        if (croppedArea == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The cropped area is null!");
            return 0;
        }
        try {
            viewClassName = view.getClass().getName();
        } catch (ClassCastException e) {
            Toast.makeText(
                            view.getContext(),
                            "ClassCastException in traverseView : target class is "
                                    + view.toString(),
                            1)
                    .show();
            e.printStackTrace();
        }
        if (this.mPackageName.equals(YOUTUBE_PACKAGE_NAME)
                && viewClassName.endsWith("PlayerView")) {
            return extractDefaultSmartClipData_YoutubePlayerView(view, croppedArea, resultElement);
        }
        if (this.mChromeBrowserContentViewName == null
                || !SmartClipUtils.isInstanceOf(view, this.mChromeBrowserContentViewName)) {
            if (viewClassName.equals("org.chromium.content.browser.JellyBeanContentView")) {
                return extractDefaultSmartClipData_GoogleChromeView(
                        view, croppedArea, resultElement);
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
        Log.d(TAG, "extractDefaultSmartClipData : Has chrome view");
        return extractDefaultSmartClipData_GoogleChromeView(view, croppedArea, resultElement);
    }

    private int extractDefaultSmartClipData_YoutubePlayerView(
            View view,
            SemSmartClipCroppedArea croppedArea,
            SmartClipDataElementImpl resultElement) {
        return 1;
    }

    private int extractDefaultSmartClipData_TextView(
            View view,
            SemSmartClipCroppedArea croppedArea,
            SmartClipDataElementImpl resultElement) {
        TextView textView;
        TransformationMethod transformationMethod;
        if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0
                && ((transformationMethod = (textView = (TextView) view).getTransformationMethod())
                                == null
                        || !(transformationMethod instanceof PasswordTransformationMethod))) {
            CharSequence charSequence = textView.getText();
            if (charSequence == null) {
                charSequence = "";
            }
            if (this.mExtractionRequest != null && this.mExtractionRequest.mExtractionMode == 2) {
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
                                        SemSmartClipMetaTagType.TEXT_SELECTION,
                                        selectedText.toString()));
                    }
                }
            }
            resultElement.addTag(
                    new SemSmartClipMetaTag(
                            SemSmartClipMetaTagType.PLAIN_TEXT, charSequence.toString()));
        }
        return 1;
    }

    private int extractDefaultSmartClipData_ImageView(
            View view,
            SemSmartClipCroppedArea croppedArea,
            SmartClipDataElementImpl resultElement) {
        if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0) {
            ImageView imageView = (ImageView) view;
            if (imageView.getDrawable() != null || imageView.getBackground() != null) {
                resultElement.addTag(
                        new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
                return 1;
            }
            return 1;
        }
        return 1;
    }

    private int extractDefaultSmartClipData_TextureView(
            View view,
            SemSmartClipCroppedArea croppedArea,
            SmartClipDataElementImpl resultElement) {
        if (resultElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() == 0) {
            resultElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
            return 1;
        }
        return 1;
    }

    private int extractDefaultSmartClipData_GoogleChromeView(
            final View view,
            SemSmartClipCroppedArea croppedArea,
            final SmartClipDataElementImpl resultElement) {
        try {
            extractSmartClipImageData(view, croppedArea, resultElement);
            Method extractSmartClipDataMethod =
                    view.getClass()
                            .getMethod(
                                    "extractSmartClipData",
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE);
            final Method setSmartClipResultHandlerMethod =
                    view.getClass().getMethod("setSmartClipResultHandler", Handler.class);
            if (extractSmartClipDataMethod != null && setSmartClipResultHandlerMethod != null) {
                Log.d(TAG, "Extracting meta data from Chrome view...");
                Handler handler =
                        new Handler() { // from class:
                                        // com.samsung.android.content.smartclip.SmartClipDataCropperImpl.1
                            public SemSmartClipDataElement mResult;

                            {
                                this.mResult = resultElement;
                            }

                            @Override // android.os.Handler
                            public void handleMessage(Message msg) {
                                Log.d(
                                        SmartClipDataCropperImpl.TAG,
                                        "Meta data arrived from chrome");
                                Bundle bundle = msg.getData();
                                if (bundle == null) {
                                    Log.e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                                    SmartClipDataCropperImpl.this.setPendingExtractionResult(
                                            this.mResult);
                                    return;
                                }
                                String title = bundle.getString("title");
                                String url = bundle.getString("url");
                                String html = bundle.getString(SemSmartClipMetaTagType.HTML);
                                String text = bundle.getString("text");
                                Rect area = (Rect) bundle.getParcelable("rect");
                                String context = bundle.getString(SemSmartClipMetaTagType.CONTEXT);
                                if (SmartClipDataCropperImpl.DEBUG) {
                                    Log.d(
                                            SmartClipDataCropperImpl.TAG,
                                            String.format(
                                                    "Title:%s\nURL:%s\nArea:%s\nText:%s\nHTML:%s",
                                                    title, url, area, text, html));
                                }
                                if (!TextUtils.isEmpty(title)) {
                                    resultElement.setTag(new SemSmartClipMetaTag("title", title));
                                }
                                if (!TextUtils.isEmpty(url)) {
                                    resultElement.setTag(new SemSmartClipMetaTag("url", url));
                                }
                                if (!TextUtils.isEmpty(html)) {
                                    resultElement.setTag(
                                            new SemSmartClipMetaTag(
                                                    SemSmartClipMetaTagType.HTML, html));
                                }
                                if (!TextUtils.isEmpty(text)) {
                                    resultElement.setTag(
                                            new SemSmartClipMetaTag(
                                                    SemSmartClipMetaTagType.PLAIN_TEXT, text));
                                }
                                if (!TextUtils.isEmpty(context)) {
                                    resultElement.setTag(
                                            new SemSmartClipMetaTag(
                                                    SemSmartClipMetaTagType.CONTEXT, context));
                                }
                                if (area != null) {
                                    DisplayMetrics metrics =
                                            SmartClipDataCropperImpl.this
                                                    .mContext
                                                    .getResources()
                                                    .getDisplayMetrics();
                                    area.left =
                                            (int) TypedValue.applyDimension(1, area.left, metrics);
                                    area.top =
                                            (int) TypedValue.applyDimension(1, area.top, metrics);
                                    area.right =
                                            (int) TypedValue.applyDimension(1, area.right, metrics);
                                    area.bottom =
                                            (int)
                                                    TypedValue.applyDimension(
                                                            1, area.bottom, metrics);
                                    Rect screenRectOfView =
                                            SmartClipDataCropperImpl.this.getViewBoundsOnScreen(
                                                    view);
                                    area.offset(screenRectOfView.left, screenRectOfView.top);
                                    area.intersect(screenRectOfView);
                                    resultElement.setMetaAreaRect(area);
                                }
                                try {
                                    setSmartClipResultHandlerMethod.invoke(view, null);
                                } catch (Exception e) {
                                    Log.e(
                                            SmartClipDataCropperImpl.TAG,
                                            "Could not invoke set smartclip handler API");
                                    e.printStackTrace();
                                }
                                SmartClipDataCropperImpl.this.setPendingExtractionResult(
                                        this.mResult);
                            }
                        };
                Rect cropRect = new Rect(croppedArea.getRect());
                int[] screenPosOfView = new int[2];
                view.getLocationOnScreen(screenPosOfView);
                cropRect.offset(-screenPosOfView[0], -screenPosOfView[1]);
                setSmartClipResultHandlerMethod.invoke(view, handler);
                if (DEBUG) {
                    Log.d(
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
            e.printStackTrace();
            Log.e(TAG, "Current chrome view does not support smartclip");
        }
        try {
            Method getUrlMethod = view.getClass().getMethod("getUrl", new Class[0]);
            String url = (String) getUrlMethod.invoke(view, new Object[0]);
            resultElement.setTag(new SemSmartClipMetaTag("url", url));
            Method getTitleMethod = view.getClass().getMethod("getTitle", new Class[0]);
            String title = (String) getTitleMethod.invoke(view, new Object[0]);
            resultElement.setTag(new SemSmartClipMetaTag("title", title));
            if (this.mExtractionRequest != null) {
                if (this.mExtractionRequest.mExtractionMode == 0) {
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

    private void extractSmartClipImageData(
            final View view,
            SemSmartClipCroppedArea croppedArea,
            final SmartClipDataElementImpl resultElement) {
        try {
            Method getSmartClipImageDataMethod =
                    view.getClass()
                            .getMethod(
                                    "getSmartClipImageData",
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE,
                                    Integer.TYPE);
            final Method setSmartClipImageResultHandlerMethod =
                    view.getClass().getMethod("setSmartClipImageResultHandler", Handler.class);
            if (getSmartClipImageDataMethod != null
                    && setSmartClipImageResultHandlerMethod != null) {
                Log.d(TAG, "Extracting original image from SBrowser");
                try {
                    Handler handler = new Handler() { // from class:
                                // com.samsung.android.content.smartclip.SmartClipDataCropperImpl.2
                                public SemSmartClipDataElement mResult;

                                {
                                    this.mResult = resultElement;
                                }

                                @Override // android.os.Handler
                                public void handleMessage(Message msg) {
                                    Log.d(
                                            SmartClipDataCropperImpl.TAG,
                                            "Original image data arrived from sbrowser");
                                    Bundle bundle = msg.getData();
                                    if (bundle != null) {
                                        String image_uri = bundle.getString("image_uri");
                                        String width = String.valueOf(bundle.getInt("width"));
                                        String height = String.valueOf(bundle.getInt("height"));
                                        String error_code =
                                                String.valueOf(bundle.getInt("error_code"));
                                        if (!TextUtils.isEmpty(image_uri)) {
                                            resultElement.setTag(
                                                    new SemSmartClipMetaTag(
                                                            "image_uri", image_uri));
                                        }
                                        if (!TextUtils.isEmpty(width)) {
                                            resultElement.setTag(
                                                    new SemSmartClipMetaTag("width", width));
                                        }
                                        if (!TextUtils.isEmpty(height)) {
                                            resultElement.setTag(
                                                    new SemSmartClipMetaTag("height", height));
                                        }
                                        if (!TextUtils.isEmpty(error_code)) {
                                            resultElement.setTag(
                                                    new SemSmartClipMetaTag(
                                                            "error_code", error_code));
                                        }
                                        try {
                                            setSmartClipImageResultHandlerMethod.invoke(view, null);
                                        } catch (Exception e) {
                                            Log.e(
                                                    SmartClipDataCropperImpl.TAG,
                                                    "Could not invoke set smartclip sbrowser"
                                                        + " handler API");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                    Rect cropRect = new Rect(croppedArea.getRect());
                    int[] screenPosOfView = new int[2];
                    view.getLocationOnScreen(screenPosOfView);
                    cropRect.offset(-screenPosOfView[0], -screenPosOfView[1]);
                    setSmartClipImageResultHandlerMethod.invoke(view, handler);
                    getSmartClipImageDataMethod.invoke(
                            view,
                            Integer.valueOf(cropRect.left),
                            Integer.valueOf(cropRect.top),
                            Integer.valueOf(cropRect.width()),
                            Integer.valueOf(cropRect.height()),
                            0,
                            0);
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    Log.e(TAG, "Current chrome view does not support SmartClipImageData");
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private int extractDefaultSmartClipData_ThirdPartyInterface(
            final View view,
            SemSmartClipCroppedArea croppedArea,
            final SmartClipDataElementImpl resultElement) {
        Object invokeObj = view;
        try {
            Method extractSmartClipDataMethod = getThirPartyExtractionInterfaceMethod(invokeObj);
            if (extractSmartClipDataMethod == null && (invokeObj = view.getTag()) != null) {
                extractSmartClipDataMethod = getThirPartyExtractionInterfaceMethod(invokeObj);
            }
            if (invokeObj != null && extractSmartClipDataMethod != null) {
                Log.d(TAG, "Extracting meta data using third party interface...");
                Handler handler =
                        new Handler() { // from class:
                                        // com.samsung.android.content.smartclip.SmartClipDataCropperImpl.3
                            public SemSmartClipDataElement mResult;

                            {
                                this.mResult = resultElement;
                            }

                            @Override // android.os.Handler
                            public void handleMessage(Message msg) {
                                Log.d(
                                        SmartClipDataCropperImpl.TAG,
                                        "Pending meta data arrived from third party");
                                Bundle bundle = msg.getData();
                                if (bundle == null) {
                                    Log.e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                                    SmartClipDataCropperImpl.this.setPendingExtractionResult(
                                            this.mResult);
                                } else {
                                    SmartClipDataCropperImpl.this.updateDataElementWithBundle(
                                            view, bundle, resultElement);
                                    SmartClipDataCropperImpl.this.setPendingExtractionResult(
                                            this.mResult);
                                }
                            }
                        };
                Object retValue =
                        extractSmartClipDataMethod.invoke(
                                invokeObj, croppedArea.getRect(), handler);
                if (retValue == null || !(retValue instanceof Bundle)) {
                    Log.d(
                            TAG,
                            "Null returned immediately from third party. waiting pending meta"
                                + " data..");
                    return 2;
                }
                Log.d(TAG, "Bundle data returned immediately from third party");
                updateDataElementWithBundle(view, (Bundle) retValue, resultElement);
                return 1;
            }
        } catch (Exception e) {
            Log.e(
                    TAG,
                    "Exception is thrown during execute the third party smartclip interface. e="
                            + e);
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
            Log.e(TAG, "adjustMetaAreaRect : rect is null");
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
        Log.e(
                TAG,
                "adjustMetaAreaRect : there is no intersection "
                        + rect
                        + " and "
                        + viewBoundsOnScreen);
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
        Log.d(TAG, "getOpaqueBackgroundRect : opaqueRect=" + opaqueRect + "  element=" + element);
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
                Log.d(
                        TAG,
                        "removeSmartClipDataElementByRect : Removing element due to RECT"
                            + " intersection. element = "
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
            Log.e(TAG, "filterMetaTagForBrowserViews : element is null!");
            return;
        }
        SmartClipDataElementImpl curElement = element;
        while (curElement != null) {
            SemSmartClipMetaTagArray tags = curElement.getTagTable();
            if (tags != null) {
                View view = curElement.getView();
                String viewName = view != null ? view.getClass().getSimpleName() : "null";
                int htmlTagCount = tags.getMetaTags(SemSmartClipMetaTagType.HTML).size();
                int textTagCount = tags.getMetaTags(SemSmartClipMetaTagType.PLAIN_TEXT).size();
                boolean z = true;
                if (htmlTagCount > 0 && textTagCount > 0) {
                    switch (smartClipDataCropperImpl.mExtractionLevel) {
                        case 0:
                            tags.removeMetaTags(SemSmartClipMetaTagType.HTML);
                            Log.d(
                                    TAG,
                                    "filterMetaTagForBrowserViews : Discarding HTML tag from "
                                            + viewName);
                            break;
                        default:
                            Iterator<SemSmartClipMetaTag> it = tags.iterator();
                            while (it.hasNext()) {
                                SemSmartClipMetaTag curTag = it.next();
                                if (SemSmartClipMetaTagType.PLAIN_TEXT.equals(curTag.getType())) {
                                    curTag.setType(SemSmartClipMetaTagType.HTML_TEXT);
                                }
                            }
                            Log.d(
                                    TAG,
                                    "filterMetaTagForBrowserViews : The TEXT tag changed to"
                                        + " HTML_TEXT. View="
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
                            Log.e(
                                    TAG,
                                    "filterMetaTagForBrowserViews : Have large HTML data("
                                            + value.length()
                                            + " bytes). Converting tag..");
                            String filePathName = allocateMetaTagFilePath();
                            if (!smartClipDataCropperImpl.writeStringToFile(filePathName, value)) {
                                Log.e(
                                        TAG,
                                        "filterMetaTagForBrowserViews : Failed to save meta tag! - "
                                                + filePathName);
                            } else {
                                Log.d(
                                        TAG,
                                        "filterMetaTagForBrowserViews : Saved the meta tag to "
                                                + filePathName);
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

    /* JADX WARN: Removed duplicated region for block: B:9:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean writeStringToFile(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            java.lang.String r0 = "writeStringToFile : File close failed! "
            r1 = 1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "writeStringToFile : "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r9)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "SmartClipDataCropperImpl"
            android.util.Log.d(r3, r2)
            java.io.File r2 = new java.io.File
            r2.<init>(r9)
            r4 = 0
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r4 = r5
            java.lang.String r5 = "UTF-8"
            byte[] r5 = r10.getBytes(r5)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r4.write(r5)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L50
            r4.close()     // Catch: java.lang.Exception -> L37
            goto L4d
        L37:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
        L3d:
            java.lang.StringBuilder r0 = r6.append(r0)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r3, r0)
            r1 = 0
        L4d:
            goto L76
        L4e:
            r5 = move-exception
            goto L98
        L50:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4e
            r6.<init>()     // Catch: java.lang.Throwable -> L4e
            java.lang.String r7 = "writeStringToFile : File write failed! "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> L4e
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch: java.lang.Throwable -> L4e
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L4e
            android.util.Log.e(r3, r6)     // Catch: java.lang.Throwable -> L4e
            r1 = 0
            if (r4 == 0) goto L76
            r4.close()     // Catch: java.lang.Exception -> L6f
            goto L4d
        L6f:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            goto L3d
        L76:
            r0 = 1
            r5 = 0
            r2.setReadable(r0, r5)
            boolean r0 = r2.setWritable(r0, r5)
            if (r0 != 0) goto L97
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to set writable permission for file: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r9)
            java.lang.String r5 = r5.toString()
            android.util.Log.e(r3, r5)
        L97:
            return r1
        L98:
            if (r4 == 0) goto Lb4
            r4.close()     // Catch: java.lang.Exception -> L9e
            goto Lb4
        L9e:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r0 = r7.append(r0)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            android.util.Log.e(r3, r0)
            r1 = 0
        Lb4:
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.content.smartclip.SmartClipDataCropperImpl.writeStringToFile(java.lang.String,"
                    + " java.lang.String):boolean");
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
        this.mLastMetaFileId++;
        this.mLastMetaFileId %= 3;
        String filePathName =
                String.format("%s/SC%02d", baseDirPath, Integer.valueOf(this.mLastMetaFileId));
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
            Log.d(TAG, "getChildViewsByZOrder : Z order detected");
            Iterator<View> it = childViews.iterator();
            while (it.hasNext()) {
                View child = it.next();
                Log.d(
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
            Log.e(
                    TAG,
                    "isSupportThirdPartyExtractionInterface : Could not get appInfo! - "
                            + packageName);
            return false;
        }
        Bundle bundle = ai.metaData;
        if (bundle != null
                && (isFeatureEnabled =
                        bundle.getBoolean(
                                META_NAME_SUPPORT_THIRD_PARTY_EXTRACTION_INTERFACE, false))) {
            Log.d(TAG, "isSupportThirdPartyExtractionInterface : Feature enabled");
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
            Method method =
                    obj.getClass().getMethod("extractSmartClipData", Rect.class, Handler.class);
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
            Log.d(
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
            resultElement.setTag(
                    new SemSmartClipMetaTag(SemSmartClipMetaTagType.APP_DEEP_LINK, appLink));
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
        if (view != null
                && view.getVisibility() == 0
                && view.getWidth() > 0
                && view.getHeight() > 0) {
            Rect screenRectOfView = getViewBoundsOnScreen(view);
            if (Rect.intersects(croppedArea.getRect(), screenRectOfView)) {
                SmartClipDataElementImpl element =
                        new SmartClipDataElementImpl(
                                smartClipDataRepository, view, screenRectOfView);
                SmartClipMetaTagArrayImpl defaultViewTags =
                        (SmartClipMetaTagArrayImpl) view.semGetSmartClipTags();
                if (defaultViewTags != null) {
                    element.setTagTable(defaultViewTags.getCopy());
                }
                SemSmartClipDataExtractionListener listener =
                        view.semGetSmartClipDataExtractionListener();
                if (this.mSupportThirdPartyExtractionInterface
                        && isSupportThirdPartyExtractionInterface(view)) {
                    extractionResult =
                            extractDefaultSmartClipData_ThirdPartyInterface(
                                    view, semSmartClipCroppedArea, element);
                } else if (listener != null) {
                    extractionResult =
                            listener.onExtractSmartClipData(view, semSmartClipCroppedArea, element);
                } else {
                    extractionResult =
                            view.semExtractSmartClipData(semSmartClipCroppedArea, element);
                }
                for (SmartClipDataElementImpl elementTraveler = element;
                        elementTraveler != null;
                        elementTraveler = elementTraveler.traverseNextElement(element)) {
                    Rect adjustedMetaAreaRect =
                            adjustMetaAreaRect(view, elementTraveler.getMetaAreaRect());
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
                        Log.e(
                                TAG,
                                "Unknown main extraction result value : "
                                        + mainResult
                                        + " / View = "
                                        + viewString);
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
                                traverseView(
                                        child,
                                        semSmartClipCroppedArea,
                                        smartClipDataRepository,
                                        element);
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
                        Log.d(
                                TAG,
                                "traverseView : Contains meta data : "
                                        + element.getDumpString(false, true));
                    } else {
                        Log.d(
                                TAG,
                                "traverseView : Contains meta data : "
                                        + element.getDumpString(false, false));
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
                                isCurElementRemoved =
                                        removeSmartClipDataElementByRect(curElement, opaqueRect);
                            }
                            if (!isCurElementRemoved
                                    && (curOpaqueRect = getOpaqueBackgroundRect(curElement))
                                            != null) {
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean traverseViewForDragAndDrop(
            android.view.View r18,
            com.samsung.android.content.smartclip.SemSmartClipCroppedArea r19,
            com.samsung.android.content.smartclip.SemSmartClipDataRepository r20,
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r21) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.content.smartclip.SmartClipDataCropperImpl.traverseViewForDragAndDrop(android.view.View,"
                    + " com.samsung.android.content.smartclip.SemSmartClipCroppedArea,"
                    + " com.samsung.android.content.smartclip.SemSmartClipDataRepository,"
                    + " com.samsung.android.content.smartclip.SmartClipDataElementImpl):boolean");
    }
}
