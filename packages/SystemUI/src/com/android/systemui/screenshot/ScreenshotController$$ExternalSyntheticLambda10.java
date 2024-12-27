package com.android.systemui.screenshot;

import android.app.assist.AssistContent;
import android.util.Log;
import com.android.systemui.screenshot.AssistContentRequester;
import com.android.systemui.screenshot.sep.SmartClipDataExtractor;
import java.util.concurrent.CompletableFuture;

public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda10 implements AssistContentRequester.Callback {
    public final /* synthetic */ CompletableFuture f$0;
    public final /* synthetic */ ScreenshotData f$1;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda10(CompletableFuture completableFuture, ScreenshotData screenshotData) {
        this.f$0 = completableFuture;
        this.f$1 = screenshotData;
    }

    public final void onAssistContentAvailable(AssistContent assistContent) {
        CompletableFuture completableFuture = this.f$0;
        if (assistContent == null || assistContent.getWebUri() == null) {
            completableFuture.complete(null);
        } else {
            Log.d("Screenshot", "handleScreenshot: mWebData is extracted from AssistContent");
            completableFuture.complete(new SmartClipDataExtractor.WebData(assistContent.getWebUri().toString(), this.f$1.getPackageNameString()));
        }
    }
}
