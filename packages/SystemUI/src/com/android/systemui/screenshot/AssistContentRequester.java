package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IAssistDataReceiver;
import android.app.assist.AssistContent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.screenshot.AssistContentRequester;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;

public final class AssistContentRequester {
    public final String mAttributionTag;
    public final Executor mCallbackExecutor;
    public final String mPackageName;
    public final Executor mSystemInteractionExecutor;
    public final Map mPendingCallbacks = Collections.synchronizedMap(new WeakHashMap());
    public final IActivityTaskManager mActivityTaskManager = ActivityTaskManager.getService();

    public interface Callback {
    }

    public AssistContentRequester(Context context, Executor executor, Executor executor2) {
        this.mPackageName = context.getApplicationContext().getPackageName();
        this.mCallbackExecutor = executor;
        this.mSystemInteractionExecutor = executor2;
        this.mAttributionTag = context.getAttributionTag();
    }

    public final class AssistDataReceiver extends IAssistDataReceiver.Stub {
        public final Object mCallbackKey;
        public final WeakReference mParentRef;

        public AssistDataReceiver(Callback callback, AssistContentRequester assistContentRequester) {
            Object obj = new Object();
            this.mCallbackKey = obj;
            assistContentRequester.mPendingCallbacks.put(obj, callback);
            this.mParentRef = new WeakReference(assistContentRequester);
        }

        public final void onHandleAssistData(Bundle bundle) {
            final AssistContent assistContent = bundle == null ? null : (AssistContent) bundle.getParcelable("content", AssistContent.class);
            AssistContentRequester assistContentRequester = (AssistContentRequester) this.mParentRef.get();
            if (assistContentRequester == null) {
                Log.d("AssistContentRequester", "Callback received after Requester was collected");
                return;
            }
            final Callback callback = (Callback) assistContentRequester.mPendingCallbacks.get(this.mCallbackKey);
            if (callback != null) {
                assistContentRequester.mSystemInteractionExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.AssistContentRequester$AssistDataReceiver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((ScreenshotController$$ExternalSyntheticLambda10) AssistContentRequester.Callback.this).onAssistContentAvailable(assistContent);
                    }
                });
            } else {
                Log.d("AssistContentRequester", "Callback received after calling UI was disposed of");
            }
        }

        public final void onHandleAssistScreenshot(Bitmap bitmap) {
        }
    }
}
