package android.webkit;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.Application;
import android.app.ResourcesManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewRootImpl;
import android.webkit.WebViewFactory;
import com.android.internal.util.ArrayUtils;

@SystemApi
/* loaded from: classes4.dex */
public final class WebViewDelegate {

    public interface OnTraceEnabledChangeListener {
        void onTraceEnabledChange(boolean z);
    }

    public boolean isMultiProcessEnabled() {
        return true;
    }

    WebViewDelegate() {
    }

    public void setOnTraceEnabledChangeListener(final OnTraceEnabledChangeListener onTraceEnabledChangeListener) {
        SystemProperties.addChangeCallback(new Runnable() { // from class: android.webkit.WebViewDelegate.1
            @Override // java.lang.Runnable
            public void run() {
                onTraceEnabledChangeListener.onTraceEnabledChange(WebViewDelegate.this.isTraceTagEnabled());
            }
        });
    }

    public boolean isTraceTagEnabled() {
        return Trace.isTagEnabled(16L);
    }

    @Deprecated
    public boolean canInvokeDrawGlFunctor(View view) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void invokeDrawGlFunctor(View view, long j, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long j) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long j, Runnable runnable) {
        throw new UnsupportedOperationException();
    }

    public void drawWebViewFunctor(Canvas canvas, int i) {
        if (!(canvas instanceof RecordingCanvas)) {
            throw new IllegalArgumentException(canvas.getClass().getName() + " is not a RecordingCanvas canvas");
        }
        ((RecordingCanvas) canvas).drawWebViewFunctor(i);
    }

    @Deprecated
    public void detachDrawGlFunctor(View view, long j) {
        if (Flags.mainlineApis()) {
            throw new UnsupportedOperationException();
        }
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (j == 0 || viewRootImpl == null) {
            return;
        }
        viewRootImpl.detachFunctor(j);
    }

    public int getPackageId(Resources resources, String str) {
        SparseArray<String> assignedPackageIdentifiers = resources.getAssets().getAssignedPackageIdentifiers();
        for (int i = 0; i < assignedPackageIdentifiers.size(); i++) {
            if (str.equals(assignedPackageIdentifiers.valueAt(i))) {
                return assignedPackageIdentifiers.keyAt(i);
            }
        }
        throw new RuntimeException("Package not found: " + str);
    }

    public Application getApplication() {
        return ActivityThread.currentApplication();
    }

    public String getErrorString(Context context, int i) {
        return LegacyErrorStrings.getString(i, context);
    }

    public void addWebViewAssetPath(Context context) {
        if (android.content.res.Flags.registerResourcePaths()) {
            return;
        }
        String[] allApkPaths = WebViewFactory.getLoadedPackageInfo().applicationInfo.getAllApkPaths();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String[] strArr = applicationInfo.sharedLibraryFiles;
        for (String str : allApkPaths) {
            strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, str);
        }
        if (strArr != applicationInfo.sharedLibraryFiles) {
            applicationInfo.sharedLibraryFiles = strArr;
            ResourcesManager.getInstance().appendLibAssetsForMainAssetPath(applicationInfo.getBaseResourcePath(), allApkPaths);
        }
    }

    public String getDataDirectorySuffix() {
        return WebViewFactory.getDataDirectorySuffix();
    }

    public WebViewFactory.StartupTimestamps getStartupTimestamps() {
        return WebViewFactory.getStartupTimestamps();
    }
}
