package android.webkit;

import android.annotation.SystemApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebStorage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class WebChromeClient {

    public interface CustomViewCallback {
        void onCustomViewHidden();
    }

    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    public View getVideoLoadingProgressView() {
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
    }

    public void onCloseWindow(WebView webView) {
    }

    @Deprecated
    public void onConsoleMessage(String str, int i, String str2) {
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        return false;
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
    }

    public void onHideCustomView() {
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        return false;
    }

    @Deprecated
    public boolean onJsTimeout() {
        return true;
    }

    public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
    }

    public void onProgressChanged(WebView webView, int i) {
    }

    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
    }

    public void onReceivedTitle(WebView webView, String str) {
    }

    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
    }

    public void onRequestFocus(WebView webView) {
    }

    @Deprecated
    public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        return false;
    }

    @Deprecated
    public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(j);
    }

    @Deprecated
    public void onReachedMaxAppCacheSize(long j, long j2, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(j2);
    }

    public void onPermissionRequest(PermissionRequest permissionRequest) {
        permissionRequest.deny();
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
        return false;
    }

    public static abstract class FileChooserParams {

        @SystemApi
        public static final long ENABLE_FILE_SYSTEM_ACCESS = 364980165;
        public static final int MODE_OPEN = 0;
        public static final int MODE_OPEN_FOLDER = 2;
        public static final int MODE_OPEN_MULTIPLE = 1;
        public static final int MODE_SAVE = 3;
        public static final int PERMISSION_MODE_READ = 0;
        public static final int PERMISSION_MODE_READ_WRITE = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PermissionMode {
        }

        public abstract Intent createIntent();

        public abstract String[] getAcceptTypes();

        public abstract String getFilenameHint();

        public abstract int getMode();

        public int getPermissionMode() {
            return 0;
        }

        public abstract CharSequence getTitle();

        public abstract boolean isCaptureEnabled();

        public static Uri[] parseResult(int i, Intent intent) {
            return WebViewFactory.getProvider().getStatics().parseFileChooserResult(i, intent);
        }
    }

    @SystemApi
    @Deprecated
    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        valueCallback.onReceiveValue(null);
    }
}
