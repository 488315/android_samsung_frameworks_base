package com.android.wm.shell.apptoweb;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IAssistDataReceiver;
import android.app.assist.AssistContent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Slog;
import com.android.wm.shell.apptoweb.AssistContentRequester;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda25;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class AssistContentRequester {
    public final String attributionTag;
    public final Executor callBackExecutor;
    public final String packageName;
    public final Executor systemInteractionExecutor;
    public final IActivityTaskManager activityTaskManager = ActivityTaskManager.getService();
    public final Map pendingCallbacks = Collections.synchronizedMap(new WeakHashMap());

    public interface Callback {
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AssistContentRequester(Context context, Executor executor, Executor executor2) {
        this.callBackExecutor = executor;
        this.systemInteractionExecutor = executor2;
        this.attributionTag = context.getAttributionTag();
        this.packageName = context.getApplicationContext().getPackageName();
    }

    public final class AssistDataReceiver extends IAssistDataReceiver.Stub {
        public final Object callbackKey;
        public final WeakReference parentRef;

        public AssistDataReceiver(Callback callback, AssistContentRequester assistContentRequester) {
            Object obj = new Object();
            this.callbackKey = obj;
            assistContentRequester.pendingCallbacks.put(obj, callback);
            this.parentRef = new WeakReference(assistContentRequester);
        }

        public final void onHandleAssistData(Bundle bundle) {
            final AssistContent assistContent = bundle != null ? (AssistContent) bundle.getParcelable("content", AssistContent.class) : null;
            if (assistContent == null) {
                Slog.d("AssistContentRequester", "Received AssistData, but no AssistContent found");
                return;
            }
            AssistContentRequester assistContentRequester = (AssistContentRequester) this.parentRef.get();
            if (assistContentRequester == null) {
                Slog.d("AssistContentRequester", "Callback received after Requester was collected");
                return;
            }
            final Callback callback = (Callback) assistContentRequester.pendingCallbacks.get(this.callbackKey);
            if (callback != null) {
                assistContentRequester.callBackExecutor.execute(new Runnable() { // from class: com.android.wm.shell.apptoweb.AssistContentRequester$AssistDataReceiver$onHandleAssistData$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AssistContentRequester.Callback callback2 = callback;
                        ((DesktopModeWindowDecoration$$ExternalSyntheticLambda25) callback2).f$0.onAssistContentReceived(assistContent);
                    }
                });
            } else {
                Slog.d("AssistContentRequester", "Callback received after calling UI was disposed of");
            }
        }

        public final void onHandleAssistScreenshot(Bitmap bitmap) {
        }
    }
}
