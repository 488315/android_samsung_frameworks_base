package com.android.internal.view;

import android.graphics.Rect;
import android.os.CancellationSignal;
import android.util.MathUtils;
import android.webkit.WebView;
import com.android.internal.view.ScrollCaptureViewHelper;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class WebViewCaptureHelper implements ScrollCaptureViewHelper<WebView> {
    private static final String TAG = "WebViewScrollCapture";
    private int mOriginScrollX;
    private int mOriginScrollY;
    private final Rect mRequestWebViewLocal = new Rect();
    private final Rect mWebViewBounds = new Rect();

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(WebView webView, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer consumer) {
        onScrollRequested2(webView, rect, rect2, cancellationSignal, (Consumer<ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(WebView webView) {
        return webView.isVisibleToUser() && ((float) webView.getContentHeight()) * webView.getScale() > ((float) webView.getHeight());
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(WebView webView, Rect rect) {
        this.mOriginScrollX = webView.getScrollX();
        this.mOriginScrollY = webView.getScrollY();
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(WebView webView, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer<ScrollCaptureViewHelper.ScrollResult> consumer) {
        int scrollY = webView.getScrollY() - this.mOriginScrollY;
        ScrollCaptureViewHelper.ScrollResult scrollResult = new ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new Rect(rect2);
        scrollResult.availableArea = new Rect();
        scrollResult.scrollDelta = scrollY;
        this.mWebViewBounds.set(0, 0, webView.getWidth(), webView.getHeight());
        if (!webView.isVisibleToUser()) {
            consumer.accept(scrollResult);
        }
        this.mRequestWebViewLocal.set(rect2);
        this.mRequestWebViewLocal.offset(0, -scrollY);
        int constrain = MathUtils.constrain(this.mRequestWebViewLocal.centerY() - this.mWebViewBounds.centerY(), Math.min(0, -webView.getScrollY()), Math.max(0, (((int) (webView.getContentHeight() * webView.getScale())) - webView.getHeight()) - webView.getScrollY()));
        webView.scrollBy(this.mOriginScrollX, constrain);
        int scrollY2 = webView.getScrollY() - this.mOriginScrollY;
        this.mRequestWebViewLocal.offset(0, -constrain);
        scrollResult.scrollDelta = scrollY2;
        if (this.mRequestWebViewLocal.intersect(this.mWebViewBounds)) {
            scrollResult.availableArea = new Rect(this.mRequestWebViewLocal);
            scrollResult.availableArea.offset(0, scrollResult.scrollDelta);
        }
        consumer.accept(scrollResult);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(WebView webView) {
        webView.scrollTo(this.mOriginScrollX, this.mOriginScrollY);
    }
}
