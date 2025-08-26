package android.webkit;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.StrictMode;
import android.print.PrintDocumentAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.View$InspectionCompanion$$ExternalSyntheticLambda0;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.view.textclassifier.TextClassifier;
import android.view.translation.TranslationCapability;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import android.widget.AbsoluteLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class WebView extends AbsoluteLayout implements ViewTreeObserver.OnGlobalFocusChangeListener, ViewGroup.OnHierarchyChangeListener, ViewDebug.HierarchyHandler {
    private static final String LOGTAG = "WebView";
    public static final int RENDERER_PRIORITY_BOUND = 1;
    public static final int RENDERER_PRIORITY_IMPORTANT = 2;
    public static final int RENDERER_PRIORITY_WAIVED = 0;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";
    private static volatile boolean sEnforceThreadChecking = false;
    private FindListenerDistributor mFindListener;
    private WebViewProvider mProvider;
    private final Looper mWebViewThread;

    public interface FindListener {
        void onFindResultReceived(int i, int i2, boolean z);
    }

    @Deprecated
    public interface PictureListener {
        @Deprecated
        void onNewPicture(WebView webView, Picture picture);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RendererPriority {
    }

    public static abstract class VisualStateCallback {
        public abstract void onComplete(long j);
    }

    @Deprecated
    public static void disablePlatformNotifications() {
    }

    @Deprecated
    public static void enablePlatformNotifications() {
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    @Deprecated
    public void onChildViewAdded(View view, View view2) {
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    @Deprecated
    public void onChildViewRemoved(View view, View view2) {
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    @Deprecated
    public void onGlobalFocusChanged(View view, View view2) {
    }

    @Deprecated
    public boolean overlayHorizontalScrollbar() {
        return true;
    }

    @Deprecated
    public boolean overlayVerticalScrollbar() {
        return false;
    }

    @Deprecated
    public void setHorizontalScrollbarOverlay(boolean z) {
    }

    @Deprecated
    public void setVerticalScrollbarOverlay(boolean z) {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<WebView> {
        private int mContentHeightId;
        private int mFaviconId;
        private int mOriginalUrlId;
        private int mProgressId;
        private boolean mPropertiesMapped = false;
        private int mRendererPriorityWaivedWhenNotVisibleId;
        private int mRendererRequestedPriorityId;
        private int mTitleId;
        private int mUrlId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mContentHeightId = propertyMapper.mapInt("contentHeight", 0);
            this.mFaviconId = propertyMapper.mapObject("favicon", 0);
            this.mOriginalUrlId = propertyMapper.mapObject("originalUrl", 0);
            this.mProgressId = propertyMapper.mapInt("progress", 0);
            this.mRendererPriorityWaivedWhenNotVisibleId = propertyMapper.mapBoolean("rendererPriorityWaivedWhenNotVisible", 0);
            SparseArray sparseArray = new SparseArray();
            sparseArray.put(0, "waived");
            sparseArray.put(1, "bound");
            sparseArray.put(2, "important");
            this.mRendererRequestedPriorityId = propertyMapper.mapIntEnum("rendererRequestedPriority", 0, new View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mTitleId = propertyMapper.mapObject("title", 0);
            this.mUrlId = propertyMapper.mapObject("url", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(WebView webView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readInt(this.mContentHeightId, webView.getContentHeight());
            propertyReader.readObject(this.mFaviconId, webView.getFavicon());
            propertyReader.readObject(this.mOriginalUrlId, webView.getOriginalUrl());
            propertyReader.readInt(this.mProgressId, webView.getProgress());
            propertyReader.readBoolean(this.mRendererPriorityWaivedWhenNotVisibleId, webView.getRendererPriorityWaivedWhenNotVisible());
            propertyReader.readIntEnum(this.mRendererRequestedPriorityId, webView.getRendererRequestedPriority());
            propertyReader.readObject(this.mTitleId, webView.getTitle());
            propertyReader.readObject(this.mUrlId, webView.getUrl());
        }
    }

    public class WebViewTransport {
        private WebView mWebview;

        public WebViewTransport(WebView webView) {
        }

        public synchronized void setWebView(WebView webView) {
            this.mWebview = webView;
        }

        public synchronized WebView getWebView() {
            return this.mWebview;
        }
    }

    public static class HitTestResult {

        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;

        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        private String mExtra;
        private int mType = 0;

        @SystemApi
        public HitTestResult() {
        }

        @SystemApi
        public void setType(int i) {
            this.mType = i;
        }

        @SystemApi
        public void setExtra(String str) {
            this.mExtra = str;
        }

        public int getType() {
            return this.mType;
        }

        public String getExtra() {
            return this.mExtra;
        }
    }

    public WebView(Context context) {
        this(context, null);
    }

    public WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842885);
    }

    public WebView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WebView(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null, false);
    }

    @Deprecated
    public WebView(Context context, AttributeSet attributeSet, int i, boolean z) {
        this(context, attributeSet, i, 0, null, z);
    }

    protected WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean z) {
        this(context, attributeSet, i, 0, map, z);
    }

    protected WebView(Context context, AttributeSet attributeSet, int i, int i2, Map<String, Object> map, boolean z) {
        super(context, attributeSet, i, i2);
        Looper looperMyLooper = Looper.myLooper();
        this.mWebViewThread = looperMyLooper;
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
        if (context == null) {
            throw new IllegalArgumentException("Invalid context argument");
        }
        if (looperMyLooper == null) {
            throw new RuntimeException("WebView cannot be initialized on a thread that has no Looper.");
        }
        sEnforceThreadChecking = context.getApplicationInfo().targetSdkVersion >= 18;
        checkThread();
        ensureProviderCreated();
        this.mProvider.init(map, z);
        CookieSyncManager.setGetInstanceIsAllowed();
    }

    @Deprecated
    public int getVisibleTitleHeight() {
        checkThread();
        return this.mProvider.getVisibleTitleHeight();
    }

    public SslCertificate getCertificate() {
        checkThread();
        return this.mProvider.getCertificate();
    }

    @Deprecated
    public void setCertificate(SslCertificate sslCertificate) {
        checkThread();
        this.mProvider.setCertificate(sslCertificate);
    }

    @Deprecated
    public void savePassword(String str, String str2, String str3) {
        checkThread();
        this.mProvider.savePassword(str, str2, str3);
    }

    @Deprecated
    public void setHttpAuthUsernamePassword(String str, String str2, String str3, String str4) {
        checkThread();
        this.mProvider.setHttpAuthUsernamePassword(str, str2, str3, str4);
    }

    @Deprecated
    public String[] getHttpAuthUsernamePassword(String str, String str2) {
        checkThread();
        return this.mProvider.getHttpAuthUsernamePassword(str, str2);
    }

    public void destroy() {
        checkThread();
        this.mProvider.destroy();
    }

    public static void freeMemoryForTests() {
        getFactory().getStatics().freeMemoryForTests();
    }

    public void setNetworkAvailable(boolean z) {
        checkThread();
        this.mProvider.setNetworkAvailable(z);
    }

    public WebBackForwardList saveState(Bundle bundle) {
        checkThread();
        return this.mProvider.saveState(bundle);
    }

    @Deprecated
    public boolean savePicture(Bundle bundle, File file) {
        checkThread();
        return this.mProvider.savePicture(bundle, file);
    }

    @Deprecated
    public boolean restorePicture(Bundle bundle, File file) {
        checkThread();
        return this.mProvider.restorePicture(bundle, file);
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        checkThread();
        return this.mProvider.restoreState(bundle);
    }

    public void loadUrl(String str, Map<String, String> map) {
        checkThread();
        this.mProvider.loadUrl(str, map);
    }

    public void loadUrl(String str) {
        checkThread();
        this.mProvider.loadUrl(str);
    }

    public void postUrl(String str, byte[] bArr) {
        checkThread();
        if (URLUtil.isNetworkUrl(str)) {
            this.mProvider.postUrl(str, bArr);
        } else {
            this.mProvider.loadUrl(str);
        }
    }

    public void loadData(String str, String str2, String str3) {
        checkThread();
        this.mProvider.loadData(str, str2, str3);
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        checkThread();
        this.mProvider.loadDataWithBaseURL(str, str2, str3, str4, str5);
    }

    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        checkThread();
        this.mProvider.evaluateJavaScript(str, valueCallback);
    }

    public void saveWebArchive(String str) {
        checkThread();
        this.mProvider.saveWebArchive(str);
    }

    public void saveWebArchive(String str, boolean z, ValueCallback<String> valueCallback) {
        checkThread();
        this.mProvider.saveWebArchive(str, z, valueCallback);
    }

    public void stopLoading() {
        checkThread();
        this.mProvider.stopLoading();
    }

    public void reload() {
        checkThread();
        this.mProvider.reload();
    }

    public boolean canGoBack() {
        checkThread();
        return this.mProvider.canGoBack();
    }

    public void goBack() {
        checkThread();
        this.mProvider.goBack();
    }

    public boolean canGoForward() {
        checkThread();
        return this.mProvider.canGoForward();
    }

    public void goForward() {
        checkThread();
        this.mProvider.goForward();
    }

    public boolean canGoBackOrForward(int i) {
        checkThread();
        return this.mProvider.canGoBackOrForward(i);
    }

    public void goBackOrForward(int i) {
        checkThread();
        this.mProvider.goBackOrForward(i);
    }

    public boolean isPrivateBrowsingEnabled() {
        checkThread();
        return this.mProvider.isPrivateBrowsingEnabled();
    }

    public boolean pageUp(boolean z) {
        checkThread();
        return this.mProvider.pageUp(z);
    }

    public boolean pageDown(boolean z) {
        checkThread();
        return this.mProvider.pageDown(z);
    }

    public void postVisualStateCallback(long j, VisualStateCallback visualStateCallback) {
        checkThread();
        this.mProvider.insertVisualStateCallback(j, visualStateCallback);
    }

    @Deprecated
    public void clearView() {
        checkThread();
        this.mProvider.clearView();
    }

    @Deprecated
    public Picture capturePicture() {
        checkThread();
        return this.mProvider.capturePicture();
    }

    @Deprecated
    public PrintDocumentAdapter createPrintDocumentAdapter() {
        checkThread();
        return this.mProvider.createPrintDocumentAdapter("default");
    }

    public PrintDocumentAdapter createPrintDocumentAdapter(String str) {
        checkThread();
        return this.mProvider.createPrintDocumentAdapter(str);
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    @Deprecated
    public float getScale() {
        checkThread();
        return this.mProvider.getScale();
    }

    public void setInitialScale(int i) {
        checkThread();
        this.mProvider.setInitialScale(i);
    }

    public void invokeZoomPicker() {
        checkThread();
        this.mProvider.invokeZoomPicker();
    }

    public HitTestResult getHitTestResult() {
        checkThread();
        return this.mProvider.getHitTestResult();
    }

    public void requestFocusNodeHref(Message message) {
        checkThread();
        this.mProvider.requestFocusNodeHref(message);
    }

    public void requestImageRef(Message message) {
        checkThread();
        this.mProvider.requestImageRef(message);
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    public String getUrl() {
        checkThread();
        return this.mProvider.getUrl();
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    public String getOriginalUrl() {
        checkThread();
        return this.mProvider.getOriginalUrl();
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    public String getTitle() {
        checkThread();
        return this.mProvider.getTitle();
    }

    public Bitmap getFavicon() {
        checkThread();
        return this.mProvider.getFavicon();
    }

    public String getTouchIconUrl() {
        return this.mProvider.getTouchIconUrl();
    }

    public int getProgress() {
        checkThread();
        return this.mProvider.getProgress();
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    public int getContentHeight() {
        checkThread();
        return this.mProvider.getContentHeight();
    }

    @ViewDebug.ExportedProperty(category = TextClassifier.WIDGET_TYPE_WEBVIEW)
    public int getContentWidth() {
        return this.mProvider.getContentWidth();
    }

    public void pauseTimers() {
        checkThread();
        this.mProvider.pauseTimers();
    }

    public void resumeTimers() {
        checkThread();
        this.mProvider.resumeTimers();
    }

    public void onPause() {
        checkThread();
        this.mProvider.onPause();
    }

    public void onResume() {
        checkThread();
        this.mProvider.onResume();
    }

    public boolean isPaused() {
        return this.mProvider.isPaused();
    }

    @Deprecated
    public void freeMemory() {
        checkThread();
        this.mProvider.freeMemory();
    }

    public void clearCache(boolean z) {
        checkThread();
        this.mProvider.clearCache(z);
    }

    public void clearFormData() {
        checkThread();
        this.mProvider.clearFormData();
    }

    public void clearHistory() {
        checkThread();
        this.mProvider.clearHistory();
    }

    public void clearSslPreferences() {
        checkThread();
        this.mProvider.clearSslPreferences();
    }

    public static void clearClientCertPreferences(Runnable runnable) {
        getFactory().getStatics().clearClientCertPreferences(runnable);
    }

    @Deprecated
    public static void startSafeBrowsing(Context context, ValueCallback<Boolean> valueCallback) {
        getFactory().getStatics().initSafeBrowsing(context, valueCallback);
    }

    public static void setSafeBrowsingWhitelist(List<String> list, ValueCallback<Boolean> valueCallback) {
        getFactory().getStatics().setSafeBrowsingWhitelist(list, valueCallback);
    }

    public static Uri getSafeBrowsingPrivacyPolicyUrl() {
        return getFactory().getStatics().getSafeBrowsingPrivacyPolicyUrl();
    }

    public WebBackForwardList copyBackForwardList() {
        checkThread();
        return this.mProvider.copyBackForwardList();
    }

    public void setFindListener(FindListener findListener) {
        checkThread();
        setupFindListenerIfNeeded();
        this.mFindListener.mUserFindListener = findListener;
    }

    public void findNext(boolean z) {
        checkThread();
        this.mProvider.findNext(z);
    }

    @Deprecated
    public int findAll(String str) {
        checkThread();
        StrictMode.noteSlowCall("findAll blocks UI: prefer findAllAsync");
        return this.mProvider.findAll(str);
    }

    public void findAllAsync(String str) {
        checkThread();
        this.mProvider.findAllAsync(str);
    }

    @Deprecated
    public boolean showFindDialog(String str, boolean z) {
        checkThread();
        return this.mProvider.showFindDialog(str, z);
    }

    @Deprecated
    public static String findAddress(String str) {
        if (str == null) {
            throw new NullPointerException("addr is null");
        }
        return FindAddress.findAddress(str);
    }

    public static void enableSlowWholeDocumentDraw() {
        getFactory().getStatics().enableSlowWholeDocumentDraw();
    }

    public void clearMatches() {
        checkThread();
        this.mProvider.clearMatches();
    }

    public void documentHasImages(Message message) {
        checkThread();
        this.mProvider.documentHasImages(message);
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        checkThread();
        this.mProvider.setWebViewClient(webViewClient);
    }

    public WebViewClient getWebViewClient() {
        checkThread();
        return this.mProvider.getWebViewClient();
    }

    public WebViewRenderProcess getWebViewRenderProcess() {
        checkThread();
        return this.mProvider.getWebViewRenderProcess();
    }

    public void setWebViewRenderProcessClient(Executor executor, WebViewRenderProcessClient webViewRenderProcessClient) {
        checkThread();
        this.mProvider.setWebViewRenderProcessClient(executor, webViewRenderProcessClient);
    }

    public void setWebViewRenderProcessClient(WebViewRenderProcessClient webViewRenderProcessClient) {
        checkThread();
        this.mProvider.setWebViewRenderProcessClient(null, webViewRenderProcessClient);
    }

    public WebViewRenderProcessClient getWebViewRenderProcessClient() {
        checkThread();
        return this.mProvider.getWebViewRenderProcessClient();
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        checkThread();
        this.mProvider.setDownloadListener(downloadListener);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        checkThread();
        this.mProvider.setWebChromeClient(webChromeClient);
    }

    public WebChromeClient getWebChromeClient() {
        checkThread();
        return this.mProvider.getWebChromeClient();
    }

    @Deprecated
    public void setPictureListener(PictureListener pictureListener) {
        checkThread();
        this.mProvider.setPictureListener(pictureListener);
    }

    public void addJavascriptInterface(Object obj, String str) {
        checkThread();
        this.mProvider.addJavascriptInterface(obj, str);
    }

    public void removeJavascriptInterface(String str) {
        checkThread();
        this.mProvider.removeJavascriptInterface(str);
    }

    public WebMessagePort[] createWebMessageChannel() {
        checkThread();
        return this.mProvider.createWebMessageChannel();
    }

    public void postWebMessage(WebMessage webMessage, Uri uri) {
        checkThread();
        this.mProvider.postMessageToMainFrame(webMessage, uri);
    }

    public WebSettings getSettings() {
        checkThread();
        return this.mProvider.getSettings();
    }

    public static void setWebContentsDebuggingEnabled(boolean z) {
        getFactory().getStatics().setWebContentsDebuggingEnabled(z);
    }

    @Deprecated
    public static synchronized PluginList getPluginList() {
        return new PluginList();
    }

    public static void setDataDirectorySuffix(String str) {
        WebViewFactory.setDataDirectorySuffix(str);
    }

    public static void disableWebView() {
        WebViewFactory.disableWebView();
    }

    @Deprecated
    public void refreshPlugins(boolean z) {
        checkThread();
    }

    @Deprecated
    public void emulateShiftHeld() {
        checkThread();
    }

    @Deprecated
    public void setMapTrackballToArrowKeys(boolean z) {
        checkThread();
        this.mProvider.setMapTrackballToArrowKeys(z);
    }

    public void flingScroll(int i, int i2) {
        checkThread();
        this.mProvider.flingScroll(i, i2);
    }

    @Deprecated
    public View getZoomControls() {
        checkThread();
        return this.mProvider.getZoomControls();
    }

    @Deprecated
    public boolean canZoomIn() {
        checkThread();
        return this.mProvider.canZoomIn();
    }

    @Deprecated
    public boolean canZoomOut() {
        checkThread();
        return this.mProvider.canZoomOut();
    }

    public void zoomBy(float f) {
        checkThread();
        double d = f;
        if (d < 0.01d) {
            throw new IllegalArgumentException("zoomFactor must be greater than 0.01.");
        }
        if (d > 100.0d) {
            throw new IllegalArgumentException("zoomFactor must be less than 100.");
        }
        this.mProvider.zoomBy(f);
    }

    public boolean zoomIn() {
        checkThread();
        return this.mProvider.zoomIn();
    }

    public boolean zoomOut() {
        checkThread();
        return this.mProvider.zoomOut();
    }

    @Deprecated
    public void debugDump() {
        checkThread();
    }

    @Override // android.view.ViewDebug.HierarchyHandler
    public void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i) {
        this.mProvider.dumpViewHierarchyWithProperties(bufferedWriter, i);
    }

    @Override // android.view.ViewDebug.HierarchyHandler
    public View findHierarchyView(String str, int i) {
        return this.mProvider.findHierarchyView(str, i);
    }

    public void setRendererPriorityPolicy(int i, boolean z) {
        this.mProvider.setRendererPriorityPolicy(i, z);
    }

    public int getRendererRequestedPriority() {
        return this.mProvider.getRendererRequestedPriority();
    }

    public boolean getRendererPriorityWaivedWhenNotVisible() {
        return this.mProvider.getRendererPriorityWaivedWhenNotVisible();
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mProvider.setTextClassifier(textClassifier);
    }

    public TextClassifier getTextClassifier() {
        return this.mProvider.getTextClassifier();
    }

    public static ClassLoader getWebViewClassLoader() {
        return getFactory().getWebViewClassLoader();
    }

    public Looper getWebViewLooper() {
        return this.mWebViewThread;
    }

    @SystemApi
    public WebViewProvider getWebViewProvider() {
        return this.mProvider;
    }

    @SystemApi
    public class PrivateAccess {
        public PrivateAccess() {
        }

        public int super_getScrollBarStyle() {
            return WebView.super.getScrollBarStyle();
        }

        public void super_scrollTo(int i, int i2) {
            WebView.super.scrollTo(i, i2);
        }

        public void super_computeScroll() {
            WebView.super.computeScroll();
        }

        public boolean super_onHoverEvent(MotionEvent motionEvent) {
            return WebView.super.onHoverEvent(motionEvent);
        }

        public boolean super_performAccessibilityAction(int i, Bundle bundle) {
            return WebView.super.performAccessibilityActionInternal(i, bundle);
        }

        public boolean super_performLongClick() {
            return WebView.super.performLongClick();
        }

        public boolean super_setFrame(int i, int i2, int i3, int i4) {
            return WebView.super.setFrame(i, i2, i3, i4);
        }

        public boolean super_dispatchKeyEvent(KeyEvent keyEvent) {
            return WebView.super.dispatchKeyEvent(keyEvent);
        }

        public boolean super_onGenericMotionEvent(MotionEvent motionEvent) {
            return WebView.super.onGenericMotionEvent(motionEvent);
        }

        public boolean super_requestFocus(int i, Rect rect) {
            return WebView.super.requestFocus(i, rect);
        }

        public void super_setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            WebView.super.setLayoutParams(layoutParams);
        }

        public void super_startActivityForResult(Intent intent, int i) {
            WebView.super.startActivityForResult(intent, i);
        }

        public WindowInsets super_onApplyWindowInsets(WindowInsets windowInsets) {
            return WebView.super.onApplyWindowInsets(windowInsets);
        }

        public void overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            WebView.this.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
        }

        public void awakenScrollBars(int i) {
            WebView.this.awakenScrollBars(i);
        }

        public void awakenScrollBars(int i, boolean z) {
            WebView.this.awakenScrollBars(i, z);
        }

        public float getVerticalScrollFactor() {
            return WebView.this.getVerticalScrollFactor();
        }

        public float getHorizontalScrollFactor() {
            return WebView.this.getHorizontalScrollFactor();
        }

        public void setMeasuredDimension(int i, int i2) {
            WebView.this.setMeasuredDimension(i, i2);
        }

        public void onScrollChanged(int i, int i2, int i3, int i4) {
            WebView.this.onScrollChanged(i, i2, i3, i4);
        }

        public int getHorizontalScrollbarHeight() {
            return WebView.this.getHorizontalScrollbarHeight();
        }

        public void super_onDrawVerticalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
            WebView.super.onDrawVerticalScrollBar(canvas, drawable, i, i2, i3, i4);
        }

        public void setScrollXRaw(int i) {
            WebView.this.mScrollX = i;
        }

        public void setScrollYRaw(int i) {
            WebView.this.mScrollY = i;
        }
    }

    void setFindDialogFindListener(FindListener findListener) {
        checkThread();
        setupFindListenerIfNeeded();
        this.mFindListener.mFindDialogFindListener = findListener;
    }

    void notifyFindDialogDismissed() {
        checkThread();
        this.mProvider.notifyFindDialogDismissed();
    }

    private class FindListenerDistributor implements FindListener {
        private FindListener mFindDialogFindListener;
        private FindListener mUserFindListener;

        private FindListenerDistributor(WebView webView) {
        }

        @Override // android.webkit.WebView.FindListener
        public void onFindResultReceived(int i, int i2, boolean z) {
            FindListener findListener = this.mFindDialogFindListener;
            if (findListener != null) {
                findListener.onFindResultReceived(i, i2, z);
            }
            FindListener findListener2 = this.mUserFindListener;
            if (findListener2 != null) {
                findListener2.onFindResultReceived(i, i2, z);
            }
        }
    }

    private void setupFindListenerIfNeeded() {
        if (this.mFindListener == null) {
            FindListenerDistributor findListenerDistributor = new FindListenerDistributor();
            this.mFindListener = findListenerDistributor;
            this.mProvider.setFindListener(findListenerDistributor);
        }
    }

    private void ensureProviderCreated() {
        checkThread();
        if (this.mProvider == null) {
            this.mProvider = getFactory().createWebView(this, new PrivateAccess());
        }
    }

    private static WebViewFactoryProvider getFactory() {
        return WebViewFactory.getProvider();
    }

    private void checkThread() {
        if (this.mWebViewThread == null || Looper.myLooper() == this.mWebViewThread) {
            return;
        }
        Throwable th = new Throwable("A WebView method was called on thread '" + Thread.currentThread().getName() + "'. All WebView methods must be called on the same thread. (Expected Looper " + this.mWebViewThread + " called on " + Looper.myLooper() + ", FYI main Looper is " + Looper.getMainLooper() + NavigationBarInflaterView.KEY_CODE_END);
        Log.w(LOGTAG, Log.getStackTraceString(th));
        StrictMode.onWebViewMethodCalledOnWrongThread(th);
        if (sEnforceThreadChecking) {
            throw new RuntimeException(th);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mProvider.getViewDelegate().onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        this.mProvider.getViewDelegate().onDetachedFromWindow();
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    public void onMovedToDisplay(int i, Configuration configuration) {
        this.mProvider.getViewDelegate().onMovedToDisplay(i, configuration);
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.mProvider.getViewDelegate().setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
        ensureProviderCreated();
        this.mProvider.getViewDelegate().setOverScrollMode(i);
    }

    @Override // android.view.View
    public void setScrollBarStyle(int i) {
        this.mProvider.getViewDelegate().setScrollBarStyle(i);
        super.setScrollBarStyle(i);
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollRange();
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollOffset();
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollOffset();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mProvider.getScrollDelegate().computeScroll();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTrackballEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyUp(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyMultiple(i, i2, keyEvent);
    }

    @Override // android.view.View
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        AccessibilityNodeProvider accessibilityNodeProvider = this.mProvider.getViewDelegate().getAccessibilityNodeProvider();
        return accessibilityNodeProvider == null ? super.getAccessibilityNodeProvider() : accessibilityNodeProvider;
    }

    @Override // android.widget.AbsoluteLayout, android.view.ViewGroup
    @Deprecated
    public boolean shouldDelayChildPressedState() {
        return this.mProvider.getViewDelegate().shouldDelayChildPressedState();
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return WebView.class.getName();
    }

    @Override // android.view.View
    public void onProvideVirtualStructure(ViewStructure viewStructure) {
        this.mProvider.getViewDelegate().onProvideVirtualStructure(viewStructure);
    }

    @Override // android.view.View
    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        this.mProvider.getViewDelegate().onProvideAutofillVirtualStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void onProvideContentCaptureStructure(ViewStructure viewStructure, int i) {
        this.mProvider.getViewDelegate().onProvideContentCaptureStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void autofill(SparseArray<AutofillValue> sparseArray) {
        this.mProvider.getViewDelegate().autofill(sparseArray);
    }

    @Override // android.view.View
    public boolean isVisibleToUserForAutofill(int i) {
        return this.mProvider.getViewDelegate().isVisibleToUserForAutofill(i);
    }

    @Override // android.view.View
    public void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, Consumer<ViewTranslationRequest> consumer) {
        this.mProvider.getViewDelegate().onCreateVirtualViewTranslationRequests(jArr, iArr, consumer);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchCreateViewTranslationRequest(Map<AutofillId, long[]> map, int[] iArr, TranslationCapability translationCapability, List<ViewTranslationRequest> list) {
        super.dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
        this.mProvider.getViewDelegate().dispatchCreateViewTranslationRequest(map, iArr, translationCapability, list);
    }

    @Override // android.view.View
    public void onVirtualViewTranslationResponses(LongSparseArray<ViewTranslationResponse> longSparseArray) {
        this.mProvider.getViewDelegate().onVirtualViewTranslationResponses(longSparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        this.mProvider.getViewDelegate().onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        this.mProvider.getViewDelegate().onInitializeAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        return this.mProvider.getViewDelegate().performAccessibilityAction(i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable, int i, int i2, int i3, int i4) {
        this.mProvider.getViewDelegate().onDrawVerticalScrollBar(canvas, drawable, i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        this.mProvider.getViewDelegate().onOverScrolled(i, i2, z, z2);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mProvider.getViewDelegate().onWindowVisibilityChanged(i);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mProvider.getViewDelegate().onDraw(canvas);
    }

    @Override // android.view.View
    public boolean performLongClick() {
        return this.mProvider.getViewDelegate().performLongClick();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        this.mProvider.getViewDelegate().onConfigurationChanged(configuration);
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return this.mProvider.getViewDelegate().onCreateInputConnection(editorInfo);
    }

    @Override // android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        return this.mProvider.getViewDelegate().onDragEvent(dragEvent);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        ensureProviderCreated();
        this.mProvider.getViewDelegate().onVisibilityChanged(view, i);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        this.mProvider.getViewDelegate().onWindowFocusChanged(z);
        super.onWindowFocusChanged(z);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        this.mProvider.getViewDelegate().onFocusChanged(z, i, rect);
        super.onFocusChanged(z, i, rect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) {
        return this.mProvider.getViewDelegate().setFrame(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mProvider.getViewDelegate().onSizeChanged(i, i2, i3, i4);
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread == null || activityThreadCurrentActivityThread.getCompatInfo() == null || !activityThreadCurrentActivityThread.getCompatInfo().hasOverrideScaling()) {
            return;
        }
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
        setInitialScale(1);
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mProvider.getViewDelegate().onScrollChanged(i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, Rect rect) {
        return this.mProvider.getViewDelegate().requestFocus(i, rect);
    }

    @Override // android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mProvider.getViewDelegate().onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mProvider.getViewDelegate().requestChildRectangleOnScreen(view, rect, z);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mProvider.getViewDelegate().setBackgroundColor(i);
    }

    @Override // android.view.View
    public void setLayerType(int i, Paint paint) {
        super.setLayerType(i, paint);
        this.mProvider.getViewDelegate().setLayerType(i, paint);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        this.mProvider.getViewDelegate().preDispatchDraw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        this.mProvider.getViewDelegate().onStartTemporaryDetach();
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mProvider.getViewDelegate().onFinishTemporaryDetach();
    }

    @Override // android.view.View
    public Handler getHandler() {
        return this.mProvider.getViewDelegate().getHandler(super.getHandler());
    }

    @Override // android.view.ViewGroup, android.view.View
    public View findFocus() {
        return this.mProvider.getViewDelegate().findFocus(super.findFocus());
    }

    public static PackageInfo getCurrentWebViewPackage() {
        PackageInfo loadedPackageInfo = WebViewFactory.getLoadedPackageInfo();
        if (loadedPackageInfo != null) {
            return loadedPackageInfo;
        }
        if (Flags.updateServiceIpcWrapper()) {
            if (WebViewFactory.isWebViewSupported()) {
                return WebViewUpdateManager.getInstance().getCurrentWebViewPackage();
            }
            return null;
        }
        IWebViewUpdateService updateService = WebViewFactory.getUpdateService();
        if (updateService == null) {
            return null;
        }
        try {
            return updateService.getCurrentWebViewPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.View
    public void onActivityResult(int i, int i2, Intent intent) {
        this.mProvider.getViewDelegate().onActivityResult(i, i2, intent);
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return this.mProvider.getViewDelegate().onCheckIsTextEditor();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
        super.encodeProperties(viewHierarchyEncoder);
        checkThread();
        viewHierarchyEncoder.addProperty("webview:contentHeight", this.mProvider.getContentHeight());
        viewHierarchyEncoder.addProperty("webview:contentWidth", this.mProvider.getContentWidth());
        viewHierarchyEncoder.addProperty("webview:scale", this.mProvider.getScale());
        viewHierarchyEncoder.addProperty("webview:title", this.mProvider.getTitle());
        viewHierarchyEncoder.addProperty("webview:url", this.mProvider.getUrl());
        viewHierarchyEncoder.addProperty("webview:originalUrl", this.mProvider.getOriginalUrl());
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets windowInsetsOnApplyWindowInsets = this.mProvider.getViewDelegate().onApplyWindowInsets(windowInsets);
        return windowInsetsOnApplyWindowInsets == null ? super.onApplyWindowInsets(windowInsets) : windowInsetsOnApplyWindowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        PointerIcon pointerIconOnResolvePointerIcon = this.mProvider.getViewDelegate().onResolvePointerIcon(motionEvent, i);
        return pointerIconOnResolvePointerIcon != null ? pointerIconOnResolvePointerIcon : super.onResolvePointerIcon(motionEvent, i);
    }
}
