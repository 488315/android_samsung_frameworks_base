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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SmartClipDataCropperImpl extends SemSmartClipDataCropper {
    private static boolean DEBUG = false;
    public static final int EXTRACTION_LEVEL_0 = 0;
    public static final int EXTRACTION_LEVEL_1 = 1;
    private static final int EXTRACTION_RESULT_MAIN_MASKING = 255;
    private static final int MAX_META_VALUE_SIZE = 102400;
    private static final String META_NAME_SUPPORT_THIRD_PARTY_EXTRACTION_INTERFACE = "com.samsung.android.smartclip.support_custom_smartclip_metaextraction";
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

    private int getMainResultFromExtractionResult(int i) {
        return i & 255;
    }

    public SmartClipDataCropperImpl(Context context, SmartClipDataExtractionEvent smartClipDataExtractionEvent) {
        this(context, smartClipDataExtractionEvent, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f), 0);
    }

    public SmartClipDataCropperImpl(Context context, SmartClipDataExtractionEvent smartClipDataExtractionEvent, Rect rect, RectF rectF, int i) {
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
        this.mExtractionRequest = smartClipDataExtractionEvent;
        this.mWinFrameRect = new Rect(rect);
        this.mScaleRect = new RectF(rectF);
        this.mPenWindowBorderWidth = i;
        String packageName = context.getPackageName();
        this.mPackageName = packageName;
        if (packageName == null) {
            this.mPackageName = "";
        }
        this.mChromeBrowserContentViewName = SmartClipUtils.getChromeViewClassNameFromManifest(context, this.mPackageName);
        this.mSupportThirdPartyExtractionInterface = isThirdPartyExtractionInterfaceEnabledOnManifest(context, this.mPackageName);
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            this.mExtractionLevel = 1;
            if (packageManager.hasSystemFeature("com.samsung.android.smartclip.DEBUG")) {
                DEBUG = true;
            }
        }
    }

    public SemSmartClipDataRepository getSmartClipDataRepository() {
        return this.mSmartClipDataRepository;
    }

    public boolean doExtractSmartClipData(View view) {
        if (this.mExtractionRequest == null) {
            Log.e(TAG, "doExtractSmartClipData : extractionRequest is null!");
            return false;
        }
        this.mExtractionStartTime = System.currentTimeMillis();
        SmartClipCroppedAreaImpl smartClipCroppedAreaImpl = new SmartClipCroppedAreaImpl(this.mExtractionRequest.mCropRect);
        Rect rect = smartClipCroppedAreaImpl.getRect();
        Log.d(TAG, "doExtractSmartClipData : Extraction start! reqId = " + this.mExtractionRequest.mRequestId + "  Cropped area = " + (rect == null ? PerfettoProtoLogImpl.NULL_STRING : rect.toString()) + "  Package = " + this.mPackageName);
        this.mIsExtractingData = true;
        SemSmartClipDataRepository semSmartClipDataRepository = new SemSmartClipDataRepository(this, this.mWinFrameRect, this.mScaleRect, this.mPenWindowBorderWidth);
        this.mSmartClipDataRepository = semSmartClipDataRepository;
        SmartClipDataElementImpl smartClipDataElementImpl = (SmartClipDataElementImpl) semSmartClipDataRepository.getRootElement();
        this.mViewPositionCache.clear();
        if (this.mExtractionRequest.mExtractionMode == 2 || this.mExtractionRequest.mExtractionMode == 3) {
            traverseViewForDragAndDrop(view, smartClipCroppedAreaImpl, this.mSmartClipDataRepository, smartClipDataElementImpl);
        } else {
            traverseView(view, smartClipCroppedAreaImpl, this.mSmartClipDataRepository, smartClipDataElementImpl);
        }
        this.mViewPositionCache.clear();
        addAppMetaTag(smartClipDataElementImpl);
        this.mSmartClipDataRepository.setAppPackageName(this.mPackageName);
        this.mIsExtractingData = false;
        if (this.mPendingElements.size() == 0) {
            this.mSmartClipDataRepository.determineContentType();
            sendExtractionResultToSmartClipService();
        }
        return true;
    }

    protected void addAppMetaTag(SemSmartClipDataElement semSmartClipDataElement) {
        if (this.mContext == null) {
            Log.e(TAG, "addAppMetaTag : mContext is null!");
            return;
        }
        Log.d(TAG, "addAppMetaTag : package name is " + this.mPackageName);
        semSmartClipDataElement.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.APP_LAUNCH_INFO, this.mPackageName));
    }

    public boolean setPendingExtractionResult(SemSmartClipDataElement semSmartClipDataElement) {
        SmartClipDataElementImpl smartClipDataElementImpl = (SmartClipDataElementImpl) semSmartClipDataElement;
        int findElementIndexFromPendingList = findElementIndexFromPendingList(smartClipDataElementImpl);
        if (findElementIndexFromPendingList < 0) {
            return false;
        }
        this.mPendingElements.remove(findElementIndexFromPendingList);
        if (!smartClipDataElementImpl.isEmptyTag(false)) {
            if (DEBUG) {
                Log.d(TAG, "setPendingExtractionResult : Contains meta data : " + smartClipDataElementImpl.getDumpString(false, true));
            } else {
                Log.d(TAG, "setPendingExtractionResult : Contains meta data : " + smartClipDataElementImpl.getDumpString(false, false));
            }
        }
        if (this.mPendingElements.size() == 0 && !this.mIsExtractingData) {
            this.mSmartClipDataRepository.determineContentType();
            sendExtractionResultToSmartClipService();
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.view.View, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
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

    protected int findElementIndexFromPendingList(SmartClipDataElementImpl smartClipDataElementImpl) {
        int size = this.mPendingElements.size();
        for (int i = 0; i < size; i++) {
            if (this.mPendingElements.get(i) == smartClipDataElementImpl) {
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
        SemSmartClipDataRepository semSmartClipDataRepository = this.mSmartClipDataRepository;
        if (semSmartClipDataRepository != null) {
            return sendExtractionResultToSmartClipService(semSmartClipDataRepository);
        }
        Log.e(TAG, "Cannot send the extraction result due to it is NULL!");
        return false;
    }

    public int getExtractionLevel() {
        return this.mExtractionLevel;
    }

    public boolean sendExtractionResultToSmartClipService(SemSmartClipDataRepository semSmartClipDataRepository) {
        SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
        if (smartClipDataExtractionEvent == null) {
            Log.e(TAG, "sendExtractionResultToSmartClipService : extractionRequest is null!");
            return false;
        }
        if (semSmartClipDataRepository != null && smartClipDataExtractionEvent.mExtractionMode == 0) {
            filterMetaTagForBrowserViews((SmartClipDataElementImpl) semSmartClipDataRepository.getRootElement());
        }
        if (semSmartClipDataRepository != null) {
            Log.d(TAG, "sendExtractionResultToSmartClipService : -- Extracted SmartClip data information --");
            Log.d(TAG, "sendExtractionResultToSmartClipService : Request Id : " + this.mExtractionRequest.mRequestId);
            Log.d(TAG, "sendExtractionResultToSmartClipService : Extraction mode : " + this.mExtractionRequest.mExtractionMode);
            semSmartClipDataRepository.dump(DEBUG);
        } else {
            Log.e(TAG, "sendExtractionResultToSmartClipService : The repository is null");
        }
        SpenGestureManager spenGestureManager = (SpenGestureManager) this.mContext.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        SmartClipDataExtractionResponse smartClipDataExtractionResponse = new SmartClipDataExtractionResponse(this.mExtractionRequest.mRequestId, this.mExtractionRequest.mExtractionMode, semSmartClipDataRepository);
        if (semSmartClipDataRepository != null && this.mExtractionRequest.mTargetWindowLayer >= 0) {
            semSmartClipDataRepository.setWindowLayer(this.mExtractionRequest.mTargetWindowLayer);
        }
        try {
            spenGestureManager.sendSmartClipRemoteRequestResult(new SmartClipRemoteRequestResult(this.mExtractionRequest.mRequestId, 1, smartClipDataExtractionResponse));
        } catch (RuntimeException e) {
            Log.e(TAG, "sendExtractionResultToSmartClipService : Failed to send the result! e=" + e);
            Log.e(TAG, "sendExtractionResultToSmartClipService : Send empty response...");
            spenGestureManager.sendSmartClipRemoteRequestResult(new SmartClipRemoteRequestResult(this.mExtractionRequest.mRequestId, 1, null));
        }
        Log.d(TAG, "sendExtractionResultToSmartClipService : Elapsed = " + (System.currentTimeMillis() - this.mExtractionStartTime));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getViewBoundsOnScreen(View view) {
        Rect rect = new Rect();
        Point viewLocationOnScreen = getViewLocationOnScreen(view);
        rect.left = viewLocationOnScreen.x;
        rect.top = viewLocationOnScreen.y;
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        return rect;
    }

    private Point getViewLocationOnScreen(View view) {
        Point point = this.mUseViewPositionCache ? this.mViewPositionCache.get(view) : null;
        if (point == null) {
            point = SmartClipUtils.getViewLocationOnScreen(view);
            if (this.mUseViewPositionCache) {
                this.mViewPositionCache.put(view, point);
            }
        }
        return point;
    }

    public int extractDefaultSmartClipData(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        String name;
        if (smartClipDataElementImpl == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The result element is null!");
            return 0;
        }
        if (semSmartClipCroppedArea == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The cropped area is null!");
            return 0;
        }
        try {
            name = view.getClass().getName();
        } catch (ClassCastException e) {
            Toast.makeText(view.getContext(), "ClassCastException in traverseView : target class is " + view.toString(), 1).show();
            e.printStackTrace();
        }
        if (this.mPackageName.equals(YOUTUBE_PACKAGE_NAME) && name.endsWith("PlayerView")) {
            return 1;
        }
        String str = this.mChromeBrowserContentViewName;
        if (str != null && SmartClipUtils.isInstanceOf(view, str)) {
            Log.d(TAG, "extractDefaultSmartClipData : Has chrome view");
            return extractDefaultSmartClipData_GoogleChromeView(view, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        if (name.equals("org.chromium.content.browser.JellyBeanContentView")) {
            return extractDefaultSmartClipData_GoogleChromeView(view, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        if (view instanceof TextView) {
            return extractDefaultSmartClipData_TextView(view, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        if (view instanceof ImageView) {
            return extractDefaultSmartClipData_ImageView(view, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        if (view instanceof TextureView) {
            return extractDefaultSmartClipData_TextureView(view, semSmartClipCroppedArea, smartClipDataElementImpl);
        }
        return 1;
    }

    private int extractDefaultSmartClipData_TextView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        if (smartClipDataElementImpl.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() != 0) {
            return 1;
        }
        TextView textView = (TextView) view;
        TransformationMethod transformationMethod = textView.getTransformationMethod();
        if (transformationMethod != null && (transformationMethod instanceof PasswordTransformationMethod)) {
            return 1;
        }
        CharSequence text = textView.getText();
        if (text == null) {
            text = "";
        }
        SmartClipDataExtractionEvent smartClipDataExtractionEvent = this.mExtractionRequest;
        if (smartClipDataExtractionEvent != null && smartClipDataExtractionEvent.mExtractionMode == 2) {
            CharSequence charSequence = textView.getSpannedTextRect(semSmartClipCroppedArea.getRect()) == null ? text : "";
            if (textView.hasSelection()) {
                int selectionStart = textView.getSelectionStart();
                int selectionEnd = textView.getSelectionEnd();
                CharSequence subSequence = charSequence.subSequence(Math.max(0, Math.min(selectionStart, selectionEnd)), Math.max(0, Math.max(selectionStart, selectionEnd)));
                if (subSequence != null) {
                    smartClipDataElementImpl.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.TEXT_SELECTION, subSequence.toString()));
                }
            }
            text = charSequence;
        }
        smartClipDataElementImpl.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, text.toString()));
        return 1;
    }

    private int extractDefaultSmartClipData_ImageView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        if (smartClipDataElementImpl.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() != 0) {
            return 1;
        }
        ImageView imageView = (ImageView) view;
        if (imageView.getDrawable() == null && imageView.getBackground() == null) {
            return 1;
        }
        smartClipDataElementImpl.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
        return 1;
    }

    private int extractDefaultSmartClipData_TextureView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        if (smartClipDataElementImpl.getTags(SemSmartClipMetaTagType.PLAIN_TEXT).size() != 0) {
            return 1;
        }
        smartClipDataElementImpl.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, ""));
        return 1;
    }

    private int extractDefaultSmartClipData_GoogleChromeView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        try {
            if (this.mExtractionRequest.mExtractionMode == 4) {
                extractSmartClipImageData(view, semSmartClipCroppedArea, smartClipDataElementImpl);
            }
            Method method = view.getClass().getMethod("extractSmartClipData", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            Method method2 = view.getClass().getMethod("setSmartClipResultHandler", Handler.class);
            if (method != null && method2 != null) {
                Log.d(TAG, "Extracting meta data from Chrome view...");
                Handler handler = new Handler(smartClipDataElementImpl, view, method2) { // from class: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.1
                    public SemSmartClipDataElement mResult;
                    final /* synthetic */ SmartClipDataElementImpl val$resultElement;
                    final /* synthetic */ Method val$setSmartClipResultHandlerMethod;
                    final /* synthetic */ View val$view;

                    {
                        this.val$resultElement = smartClipDataElementImpl;
                        this.val$view = view;
                        this.val$setSmartClipResultHandlerMethod = method2;
                        this.mResult = smartClipDataElementImpl;
                    }

                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        Log.d(SmartClipDataCropperImpl.TAG, "Meta data arrived from chrome");
                        Bundle data = message.getData();
                        if (data == null) {
                            Log.e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                            SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                            return;
                        }
                        String string = data.getString("title");
                        String string2 = data.getString("url");
                        String string3 = data.getString(SemSmartClipMetaTagType.HTML);
                        String string4 = data.getString("text");
                        Rect rect = (Rect) data.getParcelable("rect");
                        String string5 = data.getString(SemSmartClipMetaTagType.CONTEXT);
                        if (SmartClipDataCropperImpl.DEBUG) {
                            Log.d(SmartClipDataCropperImpl.TAG, String.format("Title:%s\nURL:%s\nArea:%s\nText:%s\nHTML:%s", string, string2, rect, string4, string3));
                        }
                        if (!TextUtils.isEmpty(string)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("title", string));
                        }
                        if (!TextUtils.isEmpty(string2)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("url", string2));
                        }
                        if (!TextUtils.isEmpty(string3)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.HTML, string3));
                        }
                        if (!TextUtils.isEmpty(string4)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, string4));
                        }
                        if (!TextUtils.isEmpty(string5)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.CONTEXT, string5));
                        }
                        if (rect != null) {
                            DisplayMetrics displayMetrics = SmartClipDataCropperImpl.this.mContext.getResources().getDisplayMetrics();
                            rect.left = (int) TypedValue.applyDimension(1, rect.left, displayMetrics);
                            rect.top = (int) TypedValue.applyDimension(1, rect.top, displayMetrics);
                            rect.right = (int) TypedValue.applyDimension(1, rect.right, displayMetrics);
                            rect.bottom = (int) TypedValue.applyDimension(1, rect.bottom, displayMetrics);
                            Rect viewBoundsOnScreen = SmartClipDataCropperImpl.this.getViewBoundsOnScreen(this.val$view);
                            rect.offset(viewBoundsOnScreen.left, viewBoundsOnScreen.top);
                            rect.intersect(viewBoundsOnScreen);
                        }
                        try {
                            this.val$setSmartClipResultHandlerMethod.invoke(this.val$view, null);
                        } catch (Exception e) {
                            Log.e(SmartClipDataCropperImpl.TAG, "Could not invoke set smartclip handler API");
                            e.printStackTrace();
                        }
                        SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                    }
                };
                Rect rect = new Rect(semSmartClipCroppedArea.getRect());
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                rect.offset(-iArr[0], -iArr[1]);
                method2.invoke(view, handler);
                if (DEBUG) {
                    Log.d(TAG, "Converting coordinate : " + semSmartClipCroppedArea.getRect().toString() + " -> " + rect.toString());
                }
                method.invoke(view, Integer.valueOf(rect.left), Integer.valueOf(rect.top), Integer.valueOf(rect.width()), Integer.valueOf(rect.height()));
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Current chrome view does not support smartclip");
        }
        try {
            Class[] clsArr = new Class[0];
            smartClipDataElementImpl.setTag(new SemSmartClipMetaTag("url", (String) view.getClass().getMethod("getUrl", null).invoke(view, null)));
            Class[] clsArr2 = new Class[0];
            smartClipDataElementImpl.setTag(new SemSmartClipMetaTag("title", (String) view.getClass().getMethod("getTitle", null).invoke(view, null)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 1;
    }

    private void extractSmartClipImageData(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        try {
            Method method = view.getClass().getMethod("getSmartClipImageData", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            Method method2 = view.getClass().getMethod("setSmartClipImageResultHandler", Handler.class);
            if (method == null || method2 == null) {
                return;
            }
            Log.d(TAG, "Extracting original image from SBrowser");
            Handler handler = new Handler(this, smartClipDataElementImpl, method2, view) { // from class: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.2
                public SemSmartClipDataElement mResult;
                final /* synthetic */ SmartClipDataElementImpl val$resultElement;
                final /* synthetic */ Method val$setSmartClipImageResultHandlerMethod;
                final /* synthetic */ View val$view;

                {
                    this.val$resultElement = smartClipDataElementImpl;
                    this.val$setSmartClipImageResultHandlerMethod = method2;
                    this.val$view = view;
                    this.mResult = smartClipDataElementImpl;
                }

                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Log.d(SmartClipDataCropperImpl.TAG, "Original image data arrived from sbrowser");
                    Bundle data = message.getData();
                    if (data != null) {
                        String string = data.getString("image_uri");
                        String valueOf = String.valueOf(data.getInt("width"));
                        String valueOf2 = String.valueOf(data.getInt("height"));
                        String valueOf3 = String.valueOf(data.getInt("error_code"));
                        if (!TextUtils.isEmpty(string)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("image_uri", string));
                        }
                        if (!TextUtils.isEmpty(valueOf)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("width", valueOf));
                        }
                        if (!TextUtils.isEmpty(valueOf2)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("height", valueOf2));
                        }
                        if (!TextUtils.isEmpty(valueOf3)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("error_code", valueOf3));
                        }
                        try {
                            this.val$setSmartClipImageResultHandlerMethod.invoke(this.val$view, null);
                        } catch (Exception e) {
                            Log.e(SmartClipDataCropperImpl.TAG, "Could not invoke set smartclip sbrowser handler API");
                            e.printStackTrace();
                        }
                    }
                }
            };
            Rect rect = new Rect(semSmartClipCroppedArea.getRect());
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            rect.offset(-iArr[0], -iArr[1]);
            method2.invoke(view, handler);
            method.invoke(view, Integer.valueOf(rect.left), Integer.valueOf(rect.top), Integer.valueOf(rect.width()), Integer.valueOf(rect.height()), 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Current chrome view does not support SmartClipImageData");
        }
    }

    private int extractDefaultSmartClipData_ThirdPartyInterface(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) {
        Object obj;
        try {
            Method thirPartyExtractionInterfaceMethod = getThirPartyExtractionInterfaceMethod(view);
            if (thirPartyExtractionInterfaceMethod == null) {
                obj = view.getTag();
                if (obj != null) {
                    thirPartyExtractionInterfaceMethod = getThirPartyExtractionInterfaceMethod(obj);
                }
            } else {
                obj = view;
            }
            if (obj != null && thirPartyExtractionInterfaceMethod != null) {
                Log.d(TAG, "Extracting meta data using third party interface...");
                Object invoke = thirPartyExtractionInterfaceMethod.invoke(obj, semSmartClipCroppedArea.getRect(), new Handler(smartClipDataElementImpl, view) { // from class: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.3
                    public SemSmartClipDataElement mResult;
                    final /* synthetic */ SmartClipDataElementImpl val$resultElement;
                    final /* synthetic */ View val$view;

                    {
                        this.val$resultElement = smartClipDataElementImpl;
                        this.val$view = view;
                        this.mResult = smartClipDataElementImpl;
                    }

                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        Log.d(SmartClipDataCropperImpl.TAG, "Pending meta data arrived from third party");
                        Bundle data = message.getData();
                        if (data == null) {
                            Log.e(SmartClipDataCropperImpl.TAG, "The bundle is null!");
                            SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                        } else {
                            SmartClipDataCropperImpl.this.updateDataElementWithBundle(this.val$view, data, this.val$resultElement);
                            SmartClipDataCropperImpl.this.setPendingExtractionResult(this.mResult);
                        }
                    }
                });
                if (invoke == null || !(invoke instanceof Bundle)) {
                    Log.d(TAG, "Null returned immediately from third party. waiting pending meta data..");
                    return 2;
                }
                Log.d(TAG, "Bundle data returned immediately from third party");
                updateDataElementWithBundle(view, (Bundle) invoke, smartClipDataElementImpl);
                return 1;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception is thrown during execute the third party smartclip interface. e=" + e);
            e.printStackTrace();
        }
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
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
        Log.e(TAG, "adjustMetaAreaRect : there is no intersection " + rect + " and " + viewBoundsOnScreen);
        return null;
    }

    private Rect getOpaqueBackgroundRect(SmartClipDataElementImpl smartClipDataElementImpl) {
        Drawable background;
        Rect metaAreaRect;
        Rect adjustMetaAreaRect;
        Rect rect = null;
        SmartClipDataElementImpl smartClipDataElementImpl2 = smartClipDataElementImpl;
        while (smartClipDataElementImpl2 != null) {
            View view = smartClipDataElementImpl2.getView();
            if (view != null && (background = view.getBackground()) != null && background.isVisible() && background.getOpacity() != -2 && (metaAreaRect = smartClipDataElementImpl2.getMetaAreaRect()) != null && (adjustMetaAreaRect = adjustMetaAreaRect(view, metaAreaRect)) != null) {
                if (rect == null) {
                    rect = new Rect(adjustMetaAreaRect);
                } else {
                    rect.union(adjustMetaAreaRect);
                }
            }
            smartClipDataElementImpl2 = smartClipDataElementImpl2.traverseNextElement(smartClipDataElementImpl);
        }
        Log.d(TAG, "getOpaqueBackgroundRect : opaqueRect=" + rect + "  element=" + smartClipDataElementImpl);
        return rect;
    }

    private boolean removeSmartClipDataElementByRect(SmartClipDataElementImpl smartClipDataElementImpl, Rect rect) {
        SmartClipDataElementImpl lastChild = smartClipDataElementImpl.getLastChild();
        while (lastChild != null) {
            SmartClipDataElementImpl prevSibling = lastChild.getPrevSibling();
            removeSmartClipDataElementByRect(lastChild, rect);
            lastChild = prevSibling;
        }
        if (smartClipDataElementImpl.getFirstChild() == null) {
            Rect metaAreaRect = smartClipDataElementImpl.getMetaAreaRect();
            if (smartClipDataElementImpl.isEmptyTag(false)) {
                smartClipDataElementImpl.getParent().removeChild(smartClipDataElementImpl);
                return true;
            }
            if (metaAreaRect != null && Rect.intersects(rect, metaAreaRect)) {
                Log.d(TAG, "removeSmartClipDataElementByRect : Removing element due to RECT intersection. element = " + smartClipDataElementImpl.getDumpString(false, true));
                smartClipDataElementImpl.getParent().removeChild(smartClipDataElementImpl);
                return true;
            }
        }
        return false;
    }

    private void filterMetaTagForBrowserViews(SmartClipDataElementImpl smartClipDataElementImpl) {
        if (smartClipDataElementImpl == null) {
            Log.e(TAG, "filterMetaTagForBrowserViews : element is null!");
            return;
        }
        SmartClipDataElementImpl smartClipDataElementImpl2 = smartClipDataElementImpl;
        while (smartClipDataElementImpl2 != null) {
            SemSmartClipMetaTagArray tagTable = smartClipDataElementImpl2.getTagTable();
            if (tagTable != null) {
                View view = smartClipDataElementImpl2.getView();
                String simpleName = view != null ? view.getClass().getSimpleName() : PerfettoProtoLogImpl.NULL_STRING;
                int size = tagTable.getMetaTags(SemSmartClipMetaTagType.HTML).size();
                int size2 = tagTable.getMetaTags(SemSmartClipMetaTagType.PLAIN_TEXT).size();
                if (size > 0 && size2 > 0) {
                    if (this.mExtractionLevel == 0) {
                        tagTable.removeMetaTags(SemSmartClipMetaTagType.HTML);
                        Log.d(TAG, "filterMetaTagForBrowserViews : Discarding HTML tag from " + simpleName);
                    } else {
                        Iterator<SemSmartClipMetaTag> it = tagTable.iterator();
                        while (it.hasNext()) {
                            SemSmartClipMetaTag next = it.next();
                            if (SemSmartClipMetaTagType.PLAIN_TEXT.equals(next.getType())) {
                                next.setType(SemSmartClipMetaTagType.HTML_TEXT);
                            }
                        }
                        Log.d(TAG, "filterMetaTagForBrowserViews : The TEXT tag changed to HTML_TEXT. View=" + simpleName);
                    }
                }
                Iterator<SemSmartClipMetaTag> it2 = tagTable.iterator();
                while (it2.hasNext()) {
                    SemSmartClipMetaTag next2 = it2.next();
                    if (SemSmartClipMetaTagType.HTML.equals(next2.getType())) {
                        String value = next2.getValue();
                        if (value.length() > MAX_META_VALUE_SIZE) {
                            Log.e(TAG, "filterMetaTagForBrowserViews : Have large HTML data(" + value.length() + " bytes). Converting tag..");
                            String allocateMetaTagFilePath = allocateMetaTagFilePath();
                            if (!writeStringToFile(allocateMetaTagFilePath, value)) {
                                Log.e(TAG, "filterMetaTagForBrowserViews : Failed to save meta tag! - " + allocateMetaTagFilePath);
                            } else {
                                Log.d(TAG, "filterMetaTagForBrowserViews : Saved the meta tag to " + allocateMetaTagFilePath);
                            }
                            next2.setType(SemSmartClipMetaTagType.FILE_PATH_HTML);
                            next2.setValue(allocateMetaTagFilePath);
                        }
                    }
                }
            }
            smartClipDataElementImpl2 = smartClipDataElementImpl2.traverseNextElement(smartClipDataElementImpl);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean writeStringToFile(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r7 = "writeStringToFile : File close failed! "
            java.lang.String r0 = "writeStringToFile : File write failed! "
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "writeStringToFile : "
            r1.<init>(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "SmartClipDataCropperImpl"
            android.util.Log.d(r2, r1)
            java.io.File r1 = new java.io.File
            r1.<init>(r8)
            r3 = 0
            r4 = 1
            r5 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            java.lang.String r5 = "UTF-8"
            byte[] r9 = r9.getBytes(r5)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            r6.write(r9)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            r6.close()     // Catch: java.lang.Exception -> L35
            r7 = r4
            goto L6c
        L35:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r7)
        L3b:
            r0.append(r9)
            java.lang.String r7 = r0.toString()
            android.util.Log.e(r2, r7)
        L45:
            r7 = r3
            goto L6c
        L47:
            r8 = move-exception
            r5 = r6
            goto L87
        L4a:
            r9 = move-exception
            r5 = r6
            goto L50
        L4d:
            r8 = move-exception
            goto L87
        L4f:
            r9 = move-exception
        L50:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4d
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L4d
            r6.append(r9)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r9 = r6.toString()     // Catch: java.lang.Throwable -> L4d
            android.util.Log.e(r2, r9)     // Catch: java.lang.Throwable -> L4d
            if (r5 == 0) goto L45
            r5.close()     // Catch: java.lang.Exception -> L65
            goto L45
        L65:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r7)
            goto L3b
        L6c:
            r1.setReadable(r4, r3)
            boolean r9 = r1.setWritable(r4, r3)
            if (r9 != 0) goto L86
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "Failed to set writable permission for file: "
            r9.<init>(r0)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            android.util.Log.e(r2, r8)
        L86:
            return r7
        L87:
            if (r5 == 0) goto L9d
            r5.close()     // Catch: java.lang.Exception -> L8d
            goto L9d
        L8d:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r7)
            r0.append(r9)
            java.lang.String r7 = r0.toString()
            android.util.Log.e(r2, r7)
        L9d:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.writeStringToFile(java.lang.String, java.lang.String):boolean");
    }

    private String allocateMetaTagFilePath() {
        String str = this.mContext.getFilesDir().getAbsolutePath() + "/smartclip";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
            file.setWritable(true, false);
            file.setReadable(true, false);
            file.setExecutable(true, false);
        }
        int i = this.mLastMetaFileId + 1;
        this.mLastMetaFileId = i;
        int i2 = i % 3;
        this.mLastMetaFileId = i2;
        return String.format("%s/SC%02d", str, Integer.valueOf(i2));
    }

    private ArrayList<View> getChildViewsByZOrder(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        ArrayList<View> arrayList = new ArrayList<>(childCount);
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            float z2 = childAt.getZ();
            if (z2 != 0.0f) {
                z = true;
            }
            int i2 = i;
            while (i2 > 0 && arrayList.get(i2 - 1).getZ() > z2) {
                i2--;
            }
            arrayList.add(i2, childAt);
        }
        if (z) {
            Log.d(TAG, "getChildViewsByZOrder : Z order detected");
            Iterator<View> it = arrayList.iterator();
            while (it.hasNext()) {
                View next = it.next();
                Log.d(TAG, "getChildViewsByZOrder : Parent=" + viewGroup + " / View=" + next + " / Z=" + next.getZ());
            }
        }
        return arrayList;
    }

    private boolean isThirdPartyExtractionInterfaceEnabledOnManifest(Context context, String str) {
        ApplicationInfo applicationInfo;
        boolean z = false;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (applicationInfo == null) {
            Log.e(TAG, "isSupportThirdPartyExtractionInterface : Could not get appInfo! - " + str);
            return false;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null && (z = bundle.getBoolean(META_NAME_SUPPORT_THIRD_PARTY_EXTRACTION_INTERFACE, false))) {
            Log.d(TAG, "isSupportThirdPartyExtractionInterface : Feature enabled");
        }
        return z;
    }

    private boolean isSupportThirdPartyExtractionInterface(View view) {
        if (view == null) {
            return false;
        }
        if (getThirPartyExtractionInterfaceMethod(view) != null) {
            return true;
        }
        Object tag = view.getTag();
        return (tag == null || getThirPartyExtractionInterfaceMethod(tag) == null) ? false : true;
    }

    private Method getThirPartyExtractionInterfaceMethod(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj.getClass().getMethod("extractSmartClipData", Rect.class, Handler.class);
        } catch (NoSuchMethodException | SecurityException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateDataElementWithBundle(View view, Bundle bundle, SmartClipDataElementImpl smartClipDataElementImpl) {
        boolean z;
        String string = bundle.getString("title");
        String string2 = bundle.getString("url");
        String string3 = bundle.getString("app_link");
        Rect rect = (Rect) bundle.getParcelable("rect");
        if (DEBUG) {
            Log.d(TAG, String.format("fillDataElementWithBundle : Title:%s\nLink:%s\nURL:%s\nArea:%s", string, string3, string2, rect));
        }
        if (TextUtils.isEmpty(string)) {
            z = false;
        } else {
            smartClipDataElementImpl.setTag(new SemSmartClipMetaTag("title", string));
            z = true;
        }
        if (!TextUtils.isEmpty(string2)) {
            smartClipDataElementImpl.setTag(new SemSmartClipMetaTag("url", string2));
            z = true;
        }
        if (!TextUtils.isEmpty(string3)) {
            smartClipDataElementImpl.setTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.APP_DEEP_LINK, string3));
            z = true;
        }
        if (rect == null) {
            return z;
        }
        rect.intersect(getViewBoundsOnScreen(view));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean traverseView(android.view.View r10, com.samsung.android.content.smartclip.SemSmartClipCroppedArea r11, com.samsung.android.content.smartclip.SemSmartClipDataRepository r12, com.samsung.android.content.smartclip.SmartClipDataElementImpl r13) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.traverseView(android.view.View, com.samsung.android.content.smartclip.SemSmartClipCroppedArea, com.samsung.android.content.smartclip.SemSmartClipDataRepository, com.samsung.android.content.smartclip.SmartClipDataElementImpl):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a6 A[EDGE_INSN: B:40:0x00a6->B:41:0x00a6 BREAK  A[LOOP:1: B:34:0x0093->B:37:0x00a3], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean traverseViewForDragAndDrop(android.view.View r9, com.samsung.android.content.smartclip.SemSmartClipCroppedArea r10, com.samsung.android.content.smartclip.SemSmartClipDataRepository r11, com.samsung.android.content.smartclip.SmartClipDataElementImpl r12) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto Lb4
            int r1 = r9.getVisibility()
            if (r1 != 0) goto Lb4
            int r1 = r9.getWidth()
            if (r1 <= 0) goto Lb4
            int r1 = r9.getHeight()
            if (r1 <= 0) goto Lb4
            android.graphics.Rect r1 = r8.getViewBoundsOnScreen(r9)
            android.graphics.Rect r2 = r10.getRect()
            boolean r2 = android.graphics.Rect.intersects(r2, r1)
            if (r2 == 0) goto Lb4
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r2 = new com.samsung.android.content.smartclip.SmartClipDataElementImpl
            r2.<init>(r11, r9, r1)
            com.samsung.android.content.smartclip.SemSmartClipDataExtractionListener r1 = r9.semGetSmartClipDataExtractionListener()
            if (r1 == 0) goto L37
            boolean r3 = r9 instanceof android.view.SurfaceView
            if (r3 == 0) goto L37
            int r1 = r1.onExtractSmartClipData(r9, r10, r2)
            goto L3b
        L37:
            int r1 = r9.semExtractSmartClipData(r10, r2)
        L3b:
            r3 = r2
        L3c:
            if (r3 == 0) goto L43
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r3 = r3.traverseNextElement(r2)
            goto L3c
        L43:
            int r3 = r8.getMainResultFromExtractionResult(r1)
            r4 = 1
            if (r3 == 0) goto L79
            if (r3 == r4) goto L7c
            r5 = 2
            if (r3 == r5) goto L72
            java.lang.String r5 = r9.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Unknown main extraction result value : "
            r6.<init>(r7)
            r6.append(r3)
            java.lang.String r3 = " / View = "
            r6.append(r3)
            r6.append(r5)
            java.lang.String r3 = r6.toString()
            java.lang.String r5 = "SmartClipDataCropperImpl"
            android.util.Log.e(r5, r3)
            r2.clearMetaData()
            goto L7c
        L72:
            java.util.ArrayList<com.samsung.android.content.smartclip.SmartClipDataElementImpl> r3 = r8.mPendingElements
            r3.add(r2)
            r3 = r4
            goto L7d
        L79:
            r2.clearMetaData()
        L7c:
            r3 = r0
        L7d:
            r1 = r1 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L82
            r0 = r4
        L82:
            boolean r1 = r9 instanceof android.view.ViewGroup
            if (r1 == 0) goto La6
            if (r0 != 0) goto La6
            android.view.ViewGroup r9 = (android.view.ViewGroup) r9
            java.util.ArrayList r9 = r8.getChildViewsByZOrder(r9)
            int r0 = r9.size()
            int r0 = r0 - r4
        L93:
            if (r0 < 0) goto La6
            java.lang.Object r1 = r9.get(r0)
            android.view.View r1 = (android.view.View) r1
            boolean r1 = r8.traverseViewForDragAndDrop(r1, r10, r11, r2)
            if (r1 == 0) goto La3
            r3 = r4
            goto La6
        La3:
            int r0 = r0 + (-1)
            goto L93
        La6:
            boolean r8 = r2.isEmptyTag(r4)
            if (r8 != 0) goto Lad
            goto Lae
        Lad:
            r4 = r3
        Lae:
            if (r4 == 0) goto Lb3
            r12.addChild(r2)
        Lb3:
            return r4
        Lb4:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.traverseViewForDragAndDrop(android.view.View, com.samsung.android.content.smartclip.SemSmartClipCroppedArea, com.samsung.android.content.smartclip.SemSmartClipDataRepository, com.samsung.android.content.smartclip.SmartClipDataElementImpl):boolean");
    }
}
