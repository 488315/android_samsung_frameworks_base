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
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
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
        int iFindElementIndexFromPendingList = findElementIndexFromPendingList(smartClipDataElementImpl);
        if (iFindElementIndexFromPendingList < 0) {
            return false;
        }
        this.mPendingElements.remove(iFindElementIndexFromPendingList);
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
        View parent;
        ArrayList<View> arrayList = new ArrayList<>();
        if (view instanceof ViewGroup) {
            parent = (ViewParent) view;
        } else {
            arrayList.add(view);
            parent = view.getParent();
        }
        while (parent != 0) {
            if (parent instanceof ViewGroup) {
                arrayList.add(parent);
            }
            parent = parent.getParent();
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
        Point viewLocationOnScreen = this.mUseViewPositionCache ? this.mViewPositionCache.get(view) : null;
        if (viewLocationOnScreen == null) {
            viewLocationOnScreen = SmartClipUtils.getViewLocationOnScreen(view);
            if (this.mUseViewPositionCache) {
                this.mViewPositionCache.put(view, viewLocationOnScreen);
            }
        }
        return viewLocationOnScreen;
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
                CharSequence charSequenceSubSequence = charSequence.subSequence(Math.max(0, Math.min(selectionStart, selectionEnd)), Math.max(0, Math.max(selectionStart, selectionEnd)));
                if (charSequenceSubSequence != null) {
                    smartClipDataElementImpl.addTag(new SemSmartClipMetaTag(SemSmartClipMetaTagType.TEXT_SELECTION, charSequenceSubSequence.toString()));
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

    private int extractDefaultSmartClipData_GoogleChromeView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
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
                    public void handleMessage(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

    private void extractSmartClipImageData(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
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
                public void handleMessage(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Log.d(SmartClipDataCropperImpl.TAG, "Original image data arrived from sbrowser");
                    Bundle data = message.getData();
                    if (data != null) {
                        String string = data.getString("image_uri");
                        String strValueOf = String.valueOf(data.getInt("width"));
                        String strValueOf2 = String.valueOf(data.getInt("height"));
                        String strValueOf3 = String.valueOf(data.getInt("error_code"));
                        if (!TextUtils.isEmpty(string)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("image_uri", string));
                        }
                        if (!TextUtils.isEmpty(strValueOf)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("width", strValueOf));
                        }
                        if (!TextUtils.isEmpty(strValueOf2)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("height", strValueOf2));
                        }
                        if (!TextUtils.isEmpty(strValueOf3)) {
                            this.val$resultElement.setTag(new SemSmartClipMetaTag("error_code", strValueOf3));
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

    private int extractDefaultSmartClipData_ThirdPartyInterface(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SmartClipDataElementImpl smartClipDataElementImpl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object tag;
        try {
            Method thirPartyExtractionInterfaceMethod = getThirPartyExtractionInterfaceMethod(view);
            if (thirPartyExtractionInterfaceMethod == null) {
                tag = view.getTag();
                if (tag != null) {
                    thirPartyExtractionInterfaceMethod = getThirPartyExtractionInterfaceMethod(tag);
                }
            } else {
                tag = view;
            }
            if (tag != null && thirPartyExtractionInterfaceMethod != null) {
                Log.d(TAG, "Extracting meta data using third party interface...");
                Object objInvoke = thirPartyExtractionInterfaceMethod.invoke(tag, semSmartClipCroppedArea.getRect(), new Handler(smartClipDataElementImpl, view) { // from class: com.samsung.android.content.smartclip.SmartClipDataCropperImpl.3
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
                if (objInvoke == null || !(objInvoke instanceof Bundle)) {
                    Log.d(TAG, "Null returned immediately from third party. waiting pending meta data..");
                    return 2;
                }
                Log.d(TAG, "Bundle data returned immediately from third party");
                updateDataElementWithBundle(view, (Bundle) objInvoke, smartClipDataElementImpl);
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
        for (View parent = view.getParent(); parent != 0; parent = parent.getParent()) {
            if (parent instanceof ViewGroup) {
                Rect viewBoundsOnScreen2 = getViewBoundsOnScreen(parent);
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
        Rect rectAdjustMetaAreaRect;
        Rect rect = null;
        SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = smartClipDataElementImpl;
        while (smartClipDataElementImplTraverseNextElement != null) {
            View view = smartClipDataElementImplTraverseNextElement.getView();
            if (view != null && (background = view.getBackground()) != null && background.isVisible() && background.getOpacity() != -2 && (metaAreaRect = smartClipDataElementImplTraverseNextElement.getMetaAreaRect()) != null && (rectAdjustMetaAreaRect = adjustMetaAreaRect(view, metaAreaRect)) != null) {
                if (rect == null) {
                    rect = new Rect(rectAdjustMetaAreaRect);
                } else {
                    rect.union(rectAdjustMetaAreaRect);
                }
            }
            smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(smartClipDataElementImpl);
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
        SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = smartClipDataElementImpl;
        while (smartClipDataElementImplTraverseNextElement != null) {
            SemSmartClipMetaTagArray tagTable = smartClipDataElementImplTraverseNextElement.getTagTable();
            if (tagTable != null) {
                View view = smartClipDataElementImplTraverseNextElement.getView();
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
                            String strAllocateMetaTagFilePath = allocateMetaTagFilePath();
                            if (!writeStringToFile(strAllocateMetaTagFilePath, value)) {
                                Log.e(TAG, "filterMetaTagForBrowserViews : Failed to save meta tag! - " + strAllocateMetaTagFilePath);
                            } else {
                                Log.d(TAG, "filterMetaTagForBrowserViews : Saved the meta tag to " + strAllocateMetaTagFilePath);
                            }
                            next2.setType(SemSmartClipMetaTagType.FILE_PATH_HTML);
                            next2.setValue(strAllocateMetaTagFilePath);
                        }
                    }
                }
            }
            smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(smartClipDataElementImpl);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean writeStringToFile(String str, String str2) throws Throwable {
        StringBuilder sb;
        boolean z;
        FileOutputStream fileOutputStream;
        Log.d(TAG, "writeStringToFile : " + str);
        File file = new File(str);
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(str2.getBytes("UTF-8"));
            try {
                fileOutputStream.close();
                z = true;
            } catch (Exception e2) {
                e = e2;
                sb = new StringBuilder("writeStringToFile : File close failed! ");
                sb.append(e);
                Log.e(TAG, sb.toString());
                z = false;
                file.setReadable(true, false);
                if (!file.setWritable(true, false)) {
                }
                return z;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            Log.e(TAG, "writeStringToFile : File write failed! " + e);
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception e4) {
                    e = e4;
                    sb = new StringBuilder("writeStringToFile : File close failed! ");
                    sb.append(e);
                    Log.e(TAG, sb.toString());
                    z = false;
                    file.setReadable(true, false);
                    if (!file.setWritable(true, false)) {
                    }
                    return z;
                }
            }
            z = false;
            file.setReadable(true, false);
            if (!file.setWritable(true, false)) {
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (Exception e5) {
                    Log.e(TAG, "writeStringToFile : File close failed! " + e5);
                }
            }
            throw th;
        }
        file.setReadable(true, false);
        if (!file.setWritable(true, false)) {
            Log.e(TAG, "Failed to set writable permission for file: " + str);
        }
        return z;
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

    /* JADX WARN: Removed duplicated region for block: B:37:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean traverseView(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SemSmartClipDataRepository semSmartClipDataRepository, SmartClipDataElementImpl smartClipDataElementImpl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iSemExtractSmartClipData;
        boolean z;
        Rect opaqueBackgroundRect;
        int size;
        int i;
        if (view != null && view.getVisibility() == 0 && view.getWidth() > 0 && view.getHeight() > 0) {
            Rect viewBoundsOnScreen = getViewBoundsOnScreen(view);
            if (Rect.intersects(semSmartClipCroppedArea.getRect(), viewBoundsOnScreen)) {
                SmartClipDataElementImpl smartClipDataElementImpl2 = new SmartClipDataElementImpl(semSmartClipDataRepository, view, viewBoundsOnScreen);
                SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = (SmartClipMetaTagArrayImpl) view.semGetSmartClipTags();
                if (smartClipMetaTagArrayImpl != null) {
                    smartClipDataElementImpl2.setTagTable(smartClipMetaTagArrayImpl.getCopy());
                }
                SemSmartClipDataExtractionListener semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener = view.semGetSmartClipDataExtractionListener();
                if (this.mSupportThirdPartyExtractionInterface && isSupportThirdPartyExtractionInterface(view)) {
                    iSemExtractSmartClipData = extractDefaultSmartClipData_ThirdPartyInterface(view, semSmartClipCroppedArea, smartClipDataElementImpl2);
                } else if (semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener != null) {
                    iSemExtractSmartClipData = semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener.onExtractSmartClipData(view, semSmartClipCroppedArea, smartClipDataElementImpl2);
                } else {
                    iSemExtractSmartClipData = view.semExtractSmartClipData(semSmartClipCroppedArea, smartClipDataElementImpl2);
                }
                for (SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = smartClipDataElementImpl2; smartClipDataElementImplTraverseNextElement != null; smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(smartClipDataElementImpl2)) {
                }
                int mainResultFromExtractionResult = getMainResultFromExtractionResult(iSemExtractSmartClipData);
                if (mainResultFromExtractionResult == 0) {
                    smartClipDataElementImpl2.clearMetaData();
                } else {
                    if (mainResultFromExtractionResult != 1) {
                        if (mainResultFromExtractionResult != 2) {
                            Log.e(TAG, "Unknown main extraction result value : " + mainResultFromExtractionResult + " / View = " + view.toString());
                            smartClipDataElementImpl2.clearMetaData();
                        } else {
                            this.mPendingElements.add(smartClipDataElementImpl2);
                            z = true;
                        }
                    }
                    boolean z2 = (iSemExtractSmartClipData & 256) == 0;
                    if ((view instanceof ViewGroup) && !z2) {
                        ArrayList<View> childViewsByZOrder = getChildViewsByZOrder((ViewGroup) view);
                        size = childViewsByZOrder.size();
                        for (i = 0; i < size; i++) {
                            if (traverseView(childViewsByZOrder.get(i), semSmartClipCroppedArea, semSmartClipDataRepository, smartClipDataElementImpl2)) {
                                z = true;
                            }
                        }
                    }
                    if (!smartClipDataElementImpl2.isEmptyTag(true)) {
                        z = true;
                    }
                    if (!smartClipDataElementImpl2.isEmptyTag(false)) {
                        if (DEBUG) {
                            Log.d(TAG, "traverseView : Contains meta data : " + smartClipDataElementImpl2.getDumpString(false, true));
                        } else {
                            Log.d(TAG, "traverseView : Contains meta data : " + smartClipDataElementImpl2.getDumpString(false, false));
                        }
                    }
                    if (z) {
                        if ((view instanceof FrameLayout) || (view instanceof RelativeLayout)) {
                            SmartClipDataElementImpl lastChild = smartClipDataElementImpl2.getLastChild();
                            Rect rect = null;
                            while (lastChild != null) {
                                SmartClipDataElementImpl prevSibling = lastChild.getPrevSibling();
                                if (!(rect != null ? removeSmartClipDataElementByRect(lastChild, rect) : false) && (opaqueBackgroundRect = getOpaqueBackgroundRect(lastChild)) != null) {
                                    if (rect == null) {
                                        rect = opaqueBackgroundRect;
                                    } else {
                                        rect.union(opaqueBackgroundRect);
                                    }
                                }
                                lastChild = prevSibling;
                            }
                        }
                        smartClipDataElementImpl.addChild(smartClipDataElementImpl2);
                    }
                    return z;
                }
                z = false;
                if ((iSemExtractSmartClipData & 256) == 0) {
                }
                if (view instanceof ViewGroup) {
                    ArrayList<View> childViewsByZOrder2 = getChildViewsByZOrder((ViewGroup) view);
                    size = childViewsByZOrder2.size();
                    while (i < size) {
                    }
                }
                if (!smartClipDataElementImpl2.isEmptyTag(true)) {
                }
                if (!smartClipDataElementImpl2.isEmptyTag(false)) {
                }
                if (z) {
                }
                return z;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a6 A[EDGE_INSN: B:51:0x00a6->B:42:0x00a6 BREAK  A[LOOP:1: B:37:0x0093->B:41:0x00a3], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean traverseViewForDragAndDrop(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SemSmartClipDataRepository semSmartClipDataRepository, SmartClipDataElementImpl smartClipDataElementImpl) {
        int iSemExtractSmartClipData;
        boolean z;
        boolean z2;
        int size;
        if (view != null && view.getVisibility() == 0 && view.getWidth() > 0 && view.getHeight() > 0) {
            Rect viewBoundsOnScreen = getViewBoundsOnScreen(view);
            if (Rect.intersects(semSmartClipCroppedArea.getRect(), viewBoundsOnScreen)) {
                SmartClipDataElementImpl smartClipDataElementImpl2 = new SmartClipDataElementImpl(semSmartClipDataRepository, view, viewBoundsOnScreen);
                SemSmartClipDataExtractionListener semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener = view.semGetSmartClipDataExtractionListener();
                if (semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener != null && (view instanceof SurfaceView)) {
                    iSemExtractSmartClipData = semSmartClipDataExtractionListenerSemGetSmartClipDataExtractionListener.onExtractSmartClipData(view, semSmartClipCroppedArea, smartClipDataElementImpl2);
                } else {
                    iSemExtractSmartClipData = view.semExtractSmartClipData(semSmartClipCroppedArea, smartClipDataElementImpl2);
                }
                for (SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = smartClipDataElementImpl2; smartClipDataElementImplTraverseNextElement != null; smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(smartClipDataElementImpl2)) {
                }
                int mainResultFromExtractionResult = getMainResultFromExtractionResult(iSemExtractSmartClipData);
                if (mainResultFromExtractionResult == 0) {
                    smartClipDataElementImpl2.clearMetaData();
                } else {
                    if (mainResultFromExtractionResult != 1) {
                        if (mainResultFromExtractionResult == 2) {
                            this.mPendingElements.add(smartClipDataElementImpl2);
                            z = true;
                        } else {
                            Log.e(TAG, "Unknown main extraction result value : " + mainResultFromExtractionResult + " / View = " + view.toString());
                            smartClipDataElementImpl2.clearMetaData();
                        }
                    }
                    boolean z3 = (iSemExtractSmartClipData & 256) != 0;
                    if ((view instanceof ViewGroup) && !z3) {
                        ArrayList<View> childViewsByZOrder = getChildViewsByZOrder((ViewGroup) view);
                        size = childViewsByZOrder.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            if (traverseViewForDragAndDrop(childViewsByZOrder.get(size), semSmartClipCroppedArea, semSmartClipDataRepository, smartClipDataElementImpl2)) {
                                z = true;
                                break;
                            }
                            size--;
                        }
                    }
                    z2 = smartClipDataElementImpl2.isEmptyTag(true) ? z : true;
                    if (z2) {
                        smartClipDataElementImpl.addChild(smartClipDataElementImpl2);
                    }
                    return z2;
                }
                z = false;
                if ((iSemExtractSmartClipData & 256) != 0) {
                }
                if (view instanceof ViewGroup) {
                    ArrayList<View> childViewsByZOrder2 = getChildViewsByZOrder((ViewGroup) view);
                    size = childViewsByZOrder2.size() - 1;
                    while (true) {
                        if (size < 0) {
                        }
                        size--;
                    }
                }
                if (smartClipDataElementImpl2.isEmptyTag(true)) {
                }
                if (z2) {
                }
                return z2;
            }
        }
        return false;
    }
}
