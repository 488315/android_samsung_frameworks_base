package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.contentcapture.ContentCaptureCondition;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureSessionId;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.DataShareRequest;
import android.view.contentcapture.IContentCaptureDirectManager;

import com.android.internal.os.IResultReceiver;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
public abstract class ContentCaptureService extends Service {
    public static final String ASSIST_CONTENT_ACTIVITY_START_KEY = "activity_start_assist_content";
    public static final String PROTECTION_SERVICE_INTERFACE =
            "android.service.contentcapture.ContentProtectionService";
    public static final String SERVICE_INTERFACE =
            "android.service.contentcapture.ContentCaptureService";
    public static final String SERVICE_META_DATA = "android.content_capture";
    private static final String TAG = ContentCaptureService.class.getSimpleName();
    private IContentCaptureServiceCallback mContentCaptureServiceCallback;
    private IContentProtectionAllowlistCallback mContentProtectionAllowlistCallback;
    private Handler mHandler;
    private long mLastCallerMismatchLog;
    private final LocalDataShareAdapterResourceManager mDataShareAdapterResourceManager =
            new LocalDataShareAdapterResourceManager();
    private long mCallerMismatchTimeout = 1000;
    private final IContentCaptureService mContentCaptureServerInterface = new AnonymousClass1();
    private final IContentProtectionService mContentProtectionServerInterface =
            new AnonymousClass2();
    private final IContentCaptureDirectManager mContentCaptureClientInterface =
            new AnonymousClass3();
    private final SparseIntArray mSessionUids = new SparseIntArray();

    /* renamed from: android.service.contentcapture.ContentCaptureService$1, reason: invalid class name */
    class AnonymousClass1 extends IContentCaptureService.Stub {
        AnonymousClass1() {}

        @Override // android.service.contentcapture.IContentCaptureService
        public void onConnected(IBinder callback, boolean verbose, boolean debug) {
            ContentCaptureHelper.sVerbose = verbose;
            ContentCaptureHelper.sDebug = debug;
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((ContentCaptureService) obj).handleOnConnected((IBinder) obj2);
                                }
                            },
                            ContentCaptureService.this,
                            callback));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDisconnected() {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new Consumer() { // from class:
                                             // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ((ContentCaptureService) obj).handleOnDisconnected();
                                }
                            },
                            ContentCaptureService.this));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionStarted(
                ContentCaptureContext context,
                int sessionId,
                int uid,
                IResultReceiver clientReceiver,
                int initialState) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new HexConsumer() { // from class:
                                                // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda6
                                @Override // com.android.internal.util.function.HexConsumer
                                public final void accept(
                                        Object obj,
                                        Object obj2,
                                        Object obj3,
                                        Object obj4,
                                        Object obj5,
                                        Object obj6) {
                                    ((ContentCaptureService) obj)
                                            .handleOnCreateSession(
                                                    (ContentCaptureContext) obj2,
                                                    ((Integer) obj3).intValue(),
                                                    ((Integer) obj4).intValue(),
                                                    (IResultReceiver) obj5,
                                                    ((Integer) obj6).intValue());
                                }
                            },
                            ContentCaptureService.this,
                            context,
                            Integer.valueOf(sessionId),
                            Integer.valueOf(uid),
                            clientReceiver,
                            Integer.valueOf(initialState)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivitySnapshot(int sessionId, SnapshotData snapshotData) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda7
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((ContentCaptureService) obj)
                                            .handleOnActivitySnapshot(
                                                    ((Integer) obj2).intValue(),
                                                    (SnapshotData) obj3);
                                }
                            },
                            ContentCaptureService.this,
                            Integer.valueOf(sessionId),
                            snapshotData));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionFinished(int sessionId) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((ContentCaptureService) obj)
                                            .handleFinishSession(((Integer) obj2).intValue());
                                }
                            },
                            ContentCaptureService.this,
                            Integer.valueOf(sessionId)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataRemovalRequest(DataRemovalRequest request) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((ContentCaptureService) obj)
                                            .handleOnDataRemovalRequest((DataRemovalRequest) obj2);
                                }
                            },
                            ContentCaptureService.this,
                            request));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataShared(DataShareRequest request, IDataShareCallback callback) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda3
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((ContentCaptureService) obj)
                                            .handleOnDataShared(
                                                    (DataShareRequest) obj2,
                                                    (IDataShareCallback) obj3);
                                }
                            },
                            ContentCaptureService.this,
                            request,
                            callback));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivityEvent(ActivityEvent event) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda4
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((ContentCaptureService) obj)
                                            .handleOnActivityEvent((ActivityEvent) obj2);
                                }
                            },
                            ContentCaptureService.this,
                            event));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$2, reason: invalid class name */
    class AnonymousClass2 extends IContentProtectionService.Stub {
        AnonymousClass2() {}

        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(ParceledListSlice events) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((ContentCaptureService) obj)
                                            .handleOnLoginDetected(
                                                    ((Integer) obj2).intValue(),
                                                    (ParceledListSlice) obj3);
                                }
                            },
                            ContentCaptureService.this,
                            Integer.valueOf(Binder.getCallingUid()),
                            events));
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(IBinder callback) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((ContentCaptureService) obj)
                                            .handleOnUpdateAllowlistRequest(
                                                    ((Integer) obj2).intValue(), (IBinder) obj3);
                                }
                            },
                            ContentCaptureService.this,
                            Integer.valueOf(Binder.getCallingUid()),
                            callback));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$3, reason: invalid class name */
    class AnonymousClass3 extends IContentCaptureDirectManager.Stub {
        AnonymousClass3() {}

        @Override // android.view.contentcapture.IContentCaptureDirectManager
        public void sendEvents(
                ParceledListSlice events, int reason, ContentCaptureOptions options) {
            ContentCaptureService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new QuintConsumer() { // from class:
                                                  // android.service.contentcapture.ContentCaptureService$3$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.function.QuintConsumer
                                public final void accept(
                                        Object obj,
                                        Object obj2,
                                        Object obj3,
                                        Object obj4,
                                        Object obj5) {
                                    ((ContentCaptureService) obj)
                                            .handleSendEvents(
                                                    ((Integer) obj2).intValue(),
                                                    (ParceledListSlice) obj3,
                                                    ((Integer) obj4).intValue(),
                                                    (ContentCaptureOptions) obj5);
                                }
                            },
                            ContentCaptureService.this,
                            Integer.valueOf(Binder.getCallingUid()),
                            events,
                            Integer.valueOf(reason),
                            options));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mContentCaptureServerInterface.asBinder();
        }
        if (PROTECTION_SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mContentProtectionServerInterface.asBinder();
        }
        Log.w(
                TAG,
                "Tried to bind to wrong intent (should be"
                    + " android.service.contentcapture.ContentCaptureService or"
                    + " android.service.contentcapture.ContentProtectionService): "
                        + intent);
        return null;
    }

    public final void setContentCaptureWhitelist(
            Set<String> packages, Set<ComponentName> activities) {
        IContentCaptureServiceCallback contentCaptureCallback = this.mContentCaptureServiceCallback;
        IContentProtectionAllowlistCallback contentProtectionAllowlistCallback =
                this.mContentProtectionAllowlistCallback;
        if (contentCaptureCallback == null && contentProtectionAllowlistCallback == null) {
            Log.w(TAG, "setContentCaptureWhitelist(): missing both server callbacks");
            return;
        }
        if (contentCaptureCallback != null) {
            if (contentProtectionAllowlistCallback != null) {
                throw new IllegalStateException("Have both server callbacks");
            }
            try {
                contentCaptureCallback.setContentCaptureWhitelist(
                        ContentCaptureHelper.toList(packages),
                        ContentCaptureHelper.toList(activities));
                return;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        try {
            contentProtectionAllowlistCallback.setAllowlist(ContentCaptureHelper.toList(packages));
        } catch (RemoteException e2) {
            e2.rethrowFromSystemServer();
        }
    }

    public final void setContentCaptureConditions(
            String packageName, Set<ContentCaptureCondition> conditions) {
        IContentCaptureServiceCallback callback = this.mContentCaptureServiceCallback;
        if (callback == null) {
            Log.w(TAG, "setContentCaptureConditions(): no server callback");
            return;
        }
        try {
            callback.setContentCaptureConditions(
                    packageName, ContentCaptureHelper.toList(conditions));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onConnected() {
        Slog.i(TAG, "bound to " + getClass().getName());
    }

    public void onCreateContentCaptureSession(
            ContentCaptureContext context, ContentCaptureSessionId sessionId) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "onCreateContentCaptureSession(id="
                            + sessionId
                            + ", ctx="
                            + context
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    /* renamed from: onContentCaptureEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$handleOnLoginDetected$0(
            ContentCaptureSessionId sessionId, ContentCaptureEvent event) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "onContentCaptureEventsRequest(id="
                            + sessionId
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onDataRemovalRequest(DataRemovalRequest request) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataRemovalRequest()");
        }
    }

    @SystemApi
    public void onDataShareRequest(DataShareRequest request, DataShareCallback callback) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataShareRequest()");
        }
    }

    public void onActivitySnapshot(ContentCaptureSessionId sessionId, SnapshotData snapshotData) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "onActivitySnapshot(id=" + sessionId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onActivityEvent(ActivityEvent event) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onActivityEvent(): " + event);
        }
    }

    public void onDestroyContentCaptureSession(ContentCaptureSessionId sessionId) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "onDestroyContentCaptureSession(id="
                            + sessionId
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public final void disableSelf() {
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "disableSelf()");
        }
        IContentCaptureServiceCallback callback = this.mContentCaptureServiceCallback;
        if (callback == null) {
            Log.w(TAG, "disableSelf(): no server callback");
            return;
        }
        try {
            callback.disableSelf();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onDisconnected() {
        Slog.i(TAG, "unbinding from " + getClass().getName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("Debug: ");
        pw.print(ContentCaptureHelper.sDebug);
        pw.print(" Verbose: ");
        pw.println(ContentCaptureHelper.sVerbose);
        int size = this.mSessionUids.size();
        pw.print("Number sessions: ");
        pw.println(size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                pw.print("  ");
                pw.print(this.mSessionUids.keyAt(i));
                pw.print(": uid=");
                pw.println(this.mSessionUids.valueAt(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(IBinder callback) {
        this.mContentCaptureServiceCallback =
                IContentCaptureServiceCallback.Stub.asInterface(callback);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
        this.mContentCaptureServiceCallback = null;
        this.mContentProtectionAllowlistCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCreateSession(
            ContentCaptureContext context,
            int sessionId,
            int uid,
            IResultReceiver clientReceiver,
            int initialState) {
        int stateFlags;
        this.mSessionUids.put(sessionId, uid);
        onCreateContentCaptureSession(context, new ContentCaptureSessionId(sessionId));
        int clientFlags = context.getFlags();
        int stateFlags2 = 0;
        if ((clientFlags & 2) != 0) {
            stateFlags2 = 0 | 32;
        }
        if ((clientFlags & 1) != 0) {
            stateFlags2 |= 64;
        }
        if (stateFlags2 == 0) {
            stateFlags = initialState;
        } else {
            stateFlags = stateFlags2 | 4;
        }
        setClientState(clientReceiver, stateFlags, this.mContentCaptureClientInterface.asBinder());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendEvents(
            int uid,
            ParceledListSlice<ContentCaptureEvent> parceledEvents,
            int reason,
            ContentCaptureOptions options) {
        List<ContentCaptureEvent> events = parceledEvents.getList();
        if (events.isEmpty()) {
            Log.w(TAG, "handleSendEvents() received empty list of events");
            return;
        }
        FlushMetrics metrics = new FlushMetrics();
        ComponentName activityComponent = null;
        int lastSessionId = 0;
        ContentCaptureSessionId sessionId = null;
        for (int i = 0; i < events.size(); i++) {
            ContentCaptureEvent event = events.get(i);
            if (handleIsRightCallerFor(event, uid)) {
                int sessionIdInt = event.getSessionId();
                if (sessionIdInt != lastSessionId) {
                    sessionId = new ContentCaptureSessionId(sessionIdInt);
                    if (i != 0) {
                        writeFlushMetrics(
                                sessionIdInt, activityComponent, metrics, options, reason);
                        metrics.reset();
                    }
                    lastSessionId = sessionIdInt;
                }
                ContentCaptureContext clientContext = event.getContentCaptureContext();
                if (activityComponent == null && clientContext != null) {
                    activityComponent = clientContext.getActivityComponent();
                }
                switch (event.getType()) {
                    case -2:
                        this.mSessionUids.delete(sessionIdInt);
                        onDestroyContentCaptureSession(sessionId);
                        metrics.sessionFinished++;
                        break;
                    case -1:
                        clientContext.setParentSessionId(event.getParentSessionId());
                        this.mSessionUids.put(sessionIdInt, uid);
                        onCreateContentCaptureSession(clientContext, sessionId);
                        metrics.sessionStarted++;
                        break;
                    case 0:
                    default:
                        lambda$handleOnLoginDetected$0(sessionId, event);
                        break;
                    case 1:
                        lambda$handleOnLoginDetected$0(sessionId, event);
                        metrics.viewAppearedCount++;
                        break;
                    case 2:
                        lambda$handleOnLoginDetected$0(sessionId, event);
                        metrics.viewDisappearedCount++;
                        break;
                    case 3:
                        lambda$handleOnLoginDetected$0(sessionId, event);
                        metrics.viewTextChangedCount++;
                        break;
                }
            }
        }
        writeFlushMetrics(lastSessionId, activityComponent, metrics, options, reason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnLoginDetected(
            int uid, ParceledListSlice<ContentCaptureEvent> parceledEvents) {
        if (uid != 1000) {
            Log.e(TAG, "handleOnLoginDetected() not allowed for uid: " + uid);
            return;
        }
        List<ContentCaptureEvent> events = parceledEvents.getList();
        int sessionIdInt = events.isEmpty() ? 0 : events.get(0).getSessionId();
        final ContentCaptureSessionId sessionId = new ContentCaptureSessionId(sessionIdInt);
        ContentCaptureEvent startEvent = new ContentCaptureEvent(sessionIdInt, 7);
        startEvent.setSelectionIndex(0, events.size());
        lambda$handleOnLoginDetected$0(sessionId, startEvent);
        events.forEach(
                new Consumer() { // from class:
                                 // android.service.contentcapture.ContentCaptureService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ContentCaptureService.this.lambda$handleOnLoginDetected$0(
                                sessionId, (ContentCaptureEvent) obj);
                    }
                });
        ContentCaptureEvent endEvent = new ContentCaptureEvent(sessionIdInt, 8);
        lambda$handleOnLoginDetected$0(sessionId, endEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUpdateAllowlistRequest(int uid, IBinder callback) {
        if (uid != 1000) {
            Log.e(TAG, "handleOnUpdateAllowlistRequest() not allowed for uid: " + uid);
        } else {
            this.mContentProtectionAllowlistCallback =
                    IContentProtectionAllowlistCallback.Stub.asInterface(callback);
            onConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivitySnapshot(int sessionId, SnapshotData snapshotData) {
        onActivitySnapshot(new ContentCaptureSessionId(sessionId), snapshotData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishSession(int sessionId) {
        this.mSessionUids.delete(sessionId);
        onDestroyContentCaptureSession(new ContentCaptureSessionId(sessionId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataRemovalRequest(DataRemovalRequest request) {
        onDataRemovalRequest(request);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataShared(DataShareRequest request, final IDataShareCallback callback) {
        onDataShareRequest(
                request,
                new DataShareCallback() { // from class:
                                          // android.service.contentcapture.ContentCaptureService.4
                    @Override // android.service.contentcapture.DataShareCallback
                    public void onAccept(Executor executor, DataShareReadAdapter adapter) {
                        Objects.requireNonNull(adapter);
                        Objects.requireNonNull(executor);
                        DataShareReadAdapterDelegate delegate =
                                new DataShareReadAdapterDelegate(
                                        executor,
                                        adapter,
                                        ContentCaptureService.this
                                                .mDataShareAdapterResourceManager);
                        try {
                            callback.accept(delegate);
                        } catch (RemoteException e) {
                            Slog.e(ContentCaptureService.TAG, "Failed to accept data sharing", e);
                        }
                    }

                    @Override // android.service.contentcapture.DataShareCallback
                    public void onReject() {
                        try {
                            callback.reject();
                        } catch (RemoteException e) {
                            Slog.e(ContentCaptureService.TAG, "Failed to reject data sharing", e);
                        }
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityEvent(ActivityEvent event) {
        onActivityEvent(event);
    }

    private boolean handleIsRightCallerFor(ContentCaptureEvent event, int uid) {
        int sessionId;
        switch (event.getType()) {
            case -2:
            case -1:
                sessionId = event.getParentSessionId();
                break;
            default:
                sessionId = event.getSessionId();
                break;
        }
        if (this.mSessionUids.indexOfKey(sessionId) < 0) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(
                        TAG,
                        "handleIsRightCallerFor("
                                + event
                                + "): no session for "
                                + sessionId
                                + ": "
                                + this.mSessionUids);
            }
            return false;
        }
        int rightUid = this.mSessionUids.get(sessionId);
        if (rightUid != uid) {
            Log.e(
                    TAG,
                    "invalid call from UID "
                            + uid
                            + ": session "
                            + sessionId
                            + " belongs to "
                            + rightUid);
            long now = System.currentTimeMillis();
            if (now - this.mLastCallerMismatchLog > this.mCallerMismatchTimeout) {
                FrameworkStatsLog.write(
                        206,
                        getPackageManager().getNameForUid(rightUid),
                        getPackageManager().getNameForUid(uid));
                this.mLastCallerMismatchLog = now;
            }
            return false;
        }
        return true;
    }

    public static void setClientState(
            IResultReceiver clientReceiver, int sessionState, IBinder binder) {
        Bundle extras;
        if (binder != null) {
            try {
                extras = new Bundle();
                extras.putBinder("binder", binder);
            } catch (RemoteException e) {
                Slog.w(TAG, "Error async reporting result to client: " + e);
                return;
            }
        } else {
            extras = null;
        }
        clientReceiver.send(sessionState, extras);
    }

    private void writeFlushMetrics(
            int sessionId,
            ComponentName app,
            FlushMetrics flushMetrics,
            ContentCaptureOptions options,
            int flushReason) {
        if (this.mContentCaptureServiceCallback == null) {
            Log.w(TAG, "writeSessionFlush(): no server callback");
            return;
        }
        try {
            this.mContentCaptureServiceCallback.writeSessionFlush(
                    sessionId, app, flushMetrics, options, flushReason);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to write flush metrics: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareReadAdapterDelegate extends IDataShareReadAdapter.Stub {
        private final Object mLock = new Object();
        private final WeakReference<LocalDataShareAdapterResourceManager> mResourceManagerReference;

        DataShareReadAdapterDelegate(
                Executor executor,
                DataShareReadAdapter adapter,
                LocalDataShareAdapterResourceManager resourceManager) {
            Objects.requireNonNull(executor);
            Objects.requireNonNull(adapter);
            Objects.requireNonNull(resourceManager);
            resourceManager.initializeForDelegate(this, adapter, executor);
            this.mResourceManagerReference = new WeakReference<>(resourceManager);
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void start(final ParcelFileDescriptor fd) throws RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(
                        new Consumer() { // from class:
                                         // android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((DataShareReadAdapter) obj).onStart(ParcelFileDescriptor.this);
                            }
                        },
                        "onStart");
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void error(final int errorCode) throws RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(
                        new Consumer() { // from class:
                                         // android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((DataShareReadAdapter) obj).onError(errorCode);
                            }
                        },
                        "onError");
                clearHardReferences();
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void finish() throws RemoteException {
            synchronized (this.mLock) {
                clearHardReferences();
            }
        }

        private void executeAdapterMethodLocked(
                final Consumer<DataShareReadAdapter> adapterFn, String methodName) {
            LocalDataShareAdapterResourceManager resourceManager =
                    this.mResourceManagerReference.get();
            if (resourceManager == null) {
                Slog.w(
                        ContentCaptureService.TAG,
                        "Can't execute " + methodName + "(), resource manager has been GC'ed");
                return;
            }
            final DataShareReadAdapter adapter = resourceManager.getAdapter(this);
            Executor executor = resourceManager.getExecutor(this);
            if (adapter == null || executor == null) {
                Slog.w(
                        ContentCaptureService.TAG,
                        "Can't execute " + methodName + "(), references are null");
                return;
            }
            long identity = Binder.clearCallingIdentity();
            try {
                executor.execute(
                        new Runnable() { // from class:
                                         // android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                adapterFn.accept(adapter);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        private void clearHardReferences() {
            LocalDataShareAdapterResourceManager resourceManager =
                    this.mResourceManagerReference.get();
            if (resourceManager == null) {
                Slog.w(
                        ContentCaptureService.TAG,
                        "Can't clear references, resource manager has been GC'ed");
            } else {
                resourceManager.clearHardReferences(this);
            }
        }
    }

    private static class LocalDataShareAdapterResourceManager {
        private Map<DataShareReadAdapterDelegate, DataShareReadAdapter>
                mDataShareReadAdapterHardReferences;
        private Map<DataShareReadAdapterDelegate, Executor> mExecutorHardReferences;

        private LocalDataShareAdapterResourceManager() {
            this.mDataShareReadAdapterHardReferences = new HashMap();
            this.mExecutorHardReferences = new HashMap();
        }

        void initializeForDelegate(
                DataShareReadAdapterDelegate delegate,
                DataShareReadAdapter adapter,
                Executor executor) {
            this.mDataShareReadAdapterHardReferences.put(delegate, adapter);
            this.mExecutorHardReferences.put(delegate, executor);
        }

        Executor getExecutor(DataShareReadAdapterDelegate delegate) {
            return this.mExecutorHardReferences.get(delegate);
        }

        DataShareReadAdapter getAdapter(DataShareReadAdapterDelegate delegate) {
            return this.mDataShareReadAdapterHardReferences.get(delegate);
        }

        void clearHardReferences(DataShareReadAdapterDelegate delegate) {
            this.mDataShareReadAdapterHardReferences.remove(delegate);
            this.mExecutorHardReferences.remove(delegate);
        }
    }
}
