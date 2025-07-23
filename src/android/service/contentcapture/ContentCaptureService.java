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
import android.service.contentcapture.IContentCaptureService;
import android.service.contentcapture.IContentCaptureServiceCallback;
import android.service.contentcapture.IContentProtectionAllowlistCallback;
import android.service.contentcapture.IContentProtectionService;
import android.service.contentcapture.IDataShareReadAdapter;
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
/* loaded from: classes3.dex */
public abstract class ContentCaptureService extends Service {
    public static final String ASSIST_CONTENT_ACTIVITY_START_KEY = "activity_start_assist_content";
    public static final String PROTECTION_SERVICE_INTERFACE = "android.service.contentcapture.ContentProtectionService";
    public static final String SERVICE_INTERFACE = "android.service.contentcapture.ContentCaptureService";
    public static final String SERVICE_META_DATA = "android.content_capture";
    private static final String TAG = "ContentCaptureService";
    private IContentCaptureServiceCallback mContentCaptureServiceCallback;
    private IContentProtectionAllowlistCallback mContentProtectionAllowlistCallback;
    private Handler mHandler;
    private long mLastCallerMismatchLog;
    private final LocalDataShareAdapterResourceManager mDataShareAdapterResourceManager = new LocalDataShareAdapterResourceManager();
    private long mCallerMismatchTimeout = 1000;
    private final IContentCaptureService mContentCaptureServerInterface = new AnonymousClass1();
    private final IContentProtectionService mContentProtectionServerInterface = new AnonymousClass2();
    private final IContentCaptureDirectManager mContentCaptureClientInterface = new AnonymousClass3();
    private final SparseIntArray mSessionUids = new SparseIntArray();

    /* renamed from: android.service.contentcapture.ContentCaptureService$1, reason: invalid class name */
    class AnonymousClass1 extends IContentCaptureService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onConnected(IBinder iBinder, boolean z, boolean z2) {
            ContentCaptureHelper.sVerbose = z;
            ContentCaptureHelper.sDebug = z2;
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ContentCaptureService) obj).handleOnConnected((IBinder) obj2);
                }
            }, ContentCaptureService.this, iBinder));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDisconnected() {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ContentCaptureService) obj).handleOnDisconnected();
                }
            }, ContentCaptureService.this));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionStarted(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new HexConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                    ((ContentCaptureService) obj).handleOnCreateSession((ContentCaptureContext) obj2, ((Integer) obj3).intValue(), ((Integer) obj4).intValue(), (IResultReceiver) obj5, ((Integer) obj6).intValue());
                }
            }, ContentCaptureService.this, contentCaptureContext, Integer.valueOf(i), Integer.valueOf(i2), iResultReceiver, Integer.valueOf(i3)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivitySnapshot(int i, SnapshotData snapshotData) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((ContentCaptureService) obj).handleOnActivitySnapshot(((Integer) obj2).intValue(), (SnapshotData) obj3);
                }
            }, ContentCaptureService.this, Integer.valueOf(i), snapshotData));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionFinished(int i) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ContentCaptureService) obj).handleFinishSession(((Integer) obj2).intValue());
                }
            }, ContentCaptureService.this, Integer.valueOf(i)));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ContentCaptureService) obj).handleOnDataRemovalRequest((DataRemovalRequest) obj2);
                }
            }, ContentCaptureService.this, dataRemovalRequest));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataShared(DataShareRequest dataShareRequest, IDataShareCallback iDataShareCallback) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((ContentCaptureService) obj).handleOnDataShared((DataShareRequest) obj2, (IDataShareCallback) obj3);
                }
            }, ContentCaptureService.this, dataShareRequest, iDataShareCallback));
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivityEvent(ActivityEvent activityEvent) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.contentcapture.ContentCaptureService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ContentCaptureService) obj).handleOnActivityEvent((ActivityEvent) obj2);
                }
            }, ContentCaptureService.this, activityEvent));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$2, reason: invalid class name */
    class AnonymousClass2 extends IContentProtectionService.Stub {
        AnonymousClass2() {
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(ParceledListSlice parceledListSlice) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((ContentCaptureService) obj).handleOnLoginDetected(((Integer) obj2).intValue(), (ParceledListSlice) obj3);
                }
            }, ContentCaptureService.this, Integer.valueOf(Binder.getCallingUid()), parceledListSlice));
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(IBinder iBinder) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.contentcapture.ContentCaptureService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((ContentCaptureService) obj).handleOnUpdateAllowlistRequest(((Integer) obj2).intValue(), (IBinder) obj3);
                }
            }, ContentCaptureService.this, Integer.valueOf(Binder.getCallingUid()), iBinder));
        }
    }

    /* renamed from: android.service.contentcapture.ContentCaptureService$3, reason: invalid class name */
    class AnonymousClass3 extends IContentCaptureDirectManager.Stub {
        AnonymousClass3() {
        }

        @Override // android.view.contentcapture.IContentCaptureDirectManager
        public void sendEvents(ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions) {
            ContentCaptureService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.service.contentcapture.ContentCaptureService$3$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((ContentCaptureService) obj).handleSendEvents(((Integer) obj2).intValue(), (ParceledListSlice) obj3, ((Integer) obj4).intValue(), (ContentCaptureOptions) obj5);
                }
            }, ContentCaptureService.this, Integer.valueOf(Binder.getCallingUid()), parceledListSlice, Integer.valueOf(i), contentCaptureOptions));
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
        Log.w(TAG, "Tried to bind to wrong intent (should be android.service.contentcapture.ContentCaptureService or android.service.contentcapture.ContentProtectionService): " + intent);
        return null;
    }

    public final void setContentCaptureWhitelist(Set<String> set, Set<ComponentName> set2) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        IContentProtectionAllowlistCallback iContentProtectionAllowlistCallback = this.mContentProtectionAllowlistCallback;
        if (iContentCaptureServiceCallback == null && iContentProtectionAllowlistCallback == null) {
            Log.w(TAG, "setContentCaptureWhitelist(): missing both server callbacks");
            return;
        }
        if (iContentCaptureServiceCallback == null) {
            try {
                iContentProtectionAllowlistCallback.setAllowlist(ContentCaptureHelper.toList(set));
                return;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        if (iContentProtectionAllowlistCallback != null) {
            throw new IllegalStateException("Have both server callbacks");
        }
        try {
            iContentCaptureServiceCallback.setContentCaptureWhitelist(ContentCaptureHelper.toList(set), ContentCaptureHelper.toList(set2));
        } catch (RemoteException e2) {
            e2.rethrowFromSystemServer();
        }
    }

    public final void setContentCaptureConditions(String str, Set<ContentCaptureCondition> set) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "setContentCaptureConditions(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.setContentCaptureConditions(str, ContentCaptureHelper.toList(set));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onConnected() {
        Slog.i(TAG, "bound to " + getClass().getName());
    }

    public void onCreateContentCaptureSession(ContentCaptureContext contentCaptureContext, ContentCaptureSessionId contentCaptureSessionId) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onCreateContentCaptureSession(id=" + contentCaptureSessionId + ", ctx=" + contentCaptureContext + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    /* renamed from: onContentCaptureEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$handleOnLoginDetected$0(ContentCaptureSessionId contentCaptureSessionId, ContentCaptureEvent contentCaptureEvent) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onContentCaptureEventsRequest(id=" + contentCaptureSessionId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataRemovalRequest()");
        }
    }

    @SystemApi
    public void onDataShareRequest(DataShareRequest dataShareRequest, DataShareCallback dataShareCallback) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDataShareRequest()");
        }
    }

    public void onActivitySnapshot(ContentCaptureSessionId contentCaptureSessionId, SnapshotData snapshotData) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onActivitySnapshot(id=" + contentCaptureSessionId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void onActivityEvent(ActivityEvent activityEvent) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onActivityEvent(): " + activityEvent);
        }
    }

    public void onDestroyContentCaptureSession(ContentCaptureSessionId contentCaptureSessionId) {
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "onDestroyContentCaptureSession(id=" + contentCaptureSessionId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public final void disableSelf() {
        if (ContentCaptureHelper.sDebug) {
            Log.d(TAG, "disableSelf()");
        }
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "disableSelf(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.disableSelf();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onDisconnected() {
        Slog.i(TAG, "unbinding from " + getClass().getName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("Debug: ");
        printWriter.print(ContentCaptureHelper.sDebug);
        printWriter.print(" Verbose: ");
        printWriter.println(ContentCaptureHelper.sVerbose);
        int size = this.mSessionUids.size();
        printWriter.print("Number sessions: ");
        printWriter.println(size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                printWriter.print("  ");
                printWriter.print(this.mSessionUids.keyAt(i));
                printWriter.print(": uid=");
                printWriter.println(this.mSessionUids.valueAt(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(IBinder iBinder) {
        this.mContentCaptureServiceCallback = IContentCaptureServiceCallback.Stub.asInterface(iBinder);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
        this.mContentCaptureServiceCallback = null;
        this.mContentProtectionAllowlistCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCreateSession(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) {
        this.mSessionUids.put(i, i2);
        onCreateContentCaptureSession(contentCaptureContext, new ContentCaptureSessionId(i));
        int flags = contentCaptureContext.getFlags();
        int i4 = (flags & 2) != 0 ? 32 : 0;
        if ((flags & 1) != 0) {
            i4 |= 64;
        }
        if (i4 != 0) {
            i3 = i4 | 4;
        }
        setClientState(iResultReceiver, i3, this.mContentCaptureClientInterface.asBinder());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendEvents(int i, ParceledListSlice<ContentCaptureEvent> parceledListSlice, int i2, ContentCaptureOptions contentCaptureOptions) {
        int i3;
        ContentCaptureOptions contentCaptureOptions2;
        List list = parceledListSlice.getList();
        if (list.isEmpty()) {
            Log.w(TAG, "handleSendEvents() received empty list of events");
            return;
        }
        FlushMetrics flushMetrics = new FlushMetrics();
        int i4 = 0;
        ComponentName componentName = null;
        int i5 = 0;
        ContentCaptureSessionId contentCaptureSessionId = null;
        while (i4 < list.size()) {
            ContentCaptureEvent contentCaptureEvent = (ContentCaptureEvent) list.get(i4);
            if (handleIsRightCallerFor(contentCaptureEvent, i)) {
                int i6 = i5;
                int sessionId = contentCaptureEvent.getSessionId();
                if (sessionId != i6) {
                    ContentCaptureSessionId contentCaptureSessionId2 = new ContentCaptureSessionId(sessionId);
                    if (i4 != 0) {
                        i3 = i2;
                        ContentCaptureOptions contentCaptureOptions3 = contentCaptureOptions;
                        writeFlushMetrics(sessionId, componentName, flushMetrics, contentCaptureOptions3, i3);
                        contentCaptureOptions2 = contentCaptureOptions3;
                        flushMetrics.reset();
                    } else {
                        i3 = i2;
                        contentCaptureOptions2 = contentCaptureOptions;
                    }
                    i6 = sessionId;
                    contentCaptureSessionId = contentCaptureSessionId2;
                } else {
                    i3 = i2;
                    contentCaptureOptions2 = contentCaptureOptions;
                }
                ContentCaptureContext contentCaptureContext = contentCaptureEvent.getContentCaptureContext();
                if (componentName == null && contentCaptureContext != null) {
                    componentName = contentCaptureContext.getActivityComponent();
                }
                int type = contentCaptureEvent.getType();
                if (type == -2) {
                    this.mSessionUids.delete(sessionId);
                    onDestroyContentCaptureSession(contentCaptureSessionId);
                    flushMetrics.sessionFinished++;
                } else if (type == -1) {
                    contentCaptureContext.setParentSessionId(contentCaptureEvent.getParentSessionId());
                    this.mSessionUids.put(sessionId, i);
                    onCreateContentCaptureSession(contentCaptureContext, contentCaptureSessionId);
                    flushMetrics.sessionStarted++;
                } else if (type == 1) {
                    lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                    flushMetrics.viewAppearedCount++;
                } else if (type == 2) {
                    lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                    flushMetrics.viewDisappearedCount++;
                } else if (type == 3) {
                    lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                    flushMetrics.viewTextChangedCount++;
                } else {
                    lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
                }
                i5 = i6;
            } else {
                i3 = i2;
                contentCaptureOptions2 = contentCaptureOptions;
            }
            i4++;
            contentCaptureOptions = contentCaptureOptions2;
            i2 = i3;
        }
        writeFlushMetrics(i5, componentName, flushMetrics, contentCaptureOptions, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnLoginDetected(int i, ParceledListSlice<ContentCaptureEvent> parceledListSlice) {
        if (i != 1000) {
            Log.e(TAG, "handleOnLoginDetected() not allowed for uid: " + i);
            return;
        }
        List list = parceledListSlice.getList();
        int sessionId = list.isEmpty() ? 0 : ((ContentCaptureEvent) list.get(0)).getSessionId();
        final ContentCaptureSessionId contentCaptureSessionId = new ContentCaptureSessionId(sessionId);
        ContentCaptureEvent contentCaptureEvent = new ContentCaptureEvent(sessionId, 7);
        contentCaptureEvent.setSelectionIndex(0, list.size());
        lambda$handleOnLoginDetected$0(contentCaptureSessionId, contentCaptureEvent);
        list.forEach(new Consumer() { // from class: android.service.contentcapture.ContentCaptureService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ContentCaptureService.this.lambda$handleOnLoginDetected$0(contentCaptureSessionId, (ContentCaptureEvent) obj);
            }
        });
        lambda$handleOnLoginDetected$0(contentCaptureSessionId, new ContentCaptureEvent(sessionId, 8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUpdateAllowlistRequest(int i, IBinder iBinder) {
        if (i != 1000) {
            Log.e(TAG, "handleOnUpdateAllowlistRequest() not allowed for uid: " + i);
            return;
        }
        this.mContentProtectionAllowlistCallback = IContentProtectionAllowlistCallback.Stub.asInterface(iBinder);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivitySnapshot(int i, SnapshotData snapshotData) {
        onActivitySnapshot(new ContentCaptureSessionId(i), snapshotData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishSession(int i) {
        this.mSessionUids.delete(i);
        onDestroyContentCaptureSession(new ContentCaptureSessionId(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataRemovalRequest(DataRemovalRequest dataRemovalRequest) {
        onDataRemovalRequest(dataRemovalRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDataShared(DataShareRequest dataShareRequest, final IDataShareCallback iDataShareCallback) {
        onDataShareRequest(dataShareRequest, new DataShareCallback() { // from class: android.service.contentcapture.ContentCaptureService.4
            @Override // android.service.contentcapture.DataShareCallback
            public void onAccept(Executor executor, DataShareReadAdapter dataShareReadAdapter) {
                Objects.requireNonNull(dataShareReadAdapter);
                Objects.requireNonNull(executor);
                try {
                    iDataShareCallback.accept(new DataShareReadAdapterDelegate(executor, dataShareReadAdapter, ContentCaptureService.this.mDataShareAdapterResourceManager));
                } catch (RemoteException e) {
                    Slog.e(ContentCaptureService.TAG, "Failed to accept data sharing", e);
                }
            }

            @Override // android.service.contentcapture.DataShareCallback
            public void onReject() {
                try {
                    iDataShareCallback.reject();
                } catch (RemoteException e) {
                    Slog.e(ContentCaptureService.TAG, "Failed to reject data sharing", e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnActivityEvent(ActivityEvent activityEvent) {
        onActivityEvent(activityEvent);
    }

    private boolean handleIsRightCallerFor(ContentCaptureEvent contentCaptureEvent, int i) {
        int parentSessionId;
        int type = contentCaptureEvent.getType();
        if (type == -2 || type == -1) {
            parentSessionId = contentCaptureEvent.getParentSessionId();
        } else {
            parentSessionId = contentCaptureEvent.getSessionId();
        }
        if (this.mSessionUids.indexOfKey(parentSessionId) < 0) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleIsRightCallerFor(" + contentCaptureEvent + "): no session for " + parentSessionId + ": " + this.mSessionUids);
            }
            return false;
        }
        int i2 = this.mSessionUids.get(parentSessionId);
        if (i2 == i) {
            return true;
        }
        Log.e(TAG, "invalid call from UID " + i + ": session " + parentSessionId + " belongs to " + i2);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastCallerMismatchLog > this.mCallerMismatchTimeout) {
            FrameworkStatsLog.write(206, getPackageManager().getNameForUid(i2), getPackageManager().getNameForUid(i));
            this.mLastCallerMismatchLog = currentTimeMillis;
        }
        return false;
    }

    public static void setClientState(IResultReceiver iResultReceiver, int i, IBinder iBinder) {
        Bundle bundle;
        if (iBinder != null) {
            try {
                bundle = new Bundle();
                bundle.putBinder("binder", iBinder);
            } catch (RemoteException e) {
                Slog.w(TAG, "Error async reporting result to client: " + e);
                return;
            }
        } else {
            bundle = null;
        }
        iResultReceiver.send(i, bundle);
    }

    private void writeFlushMetrics(int i, ComponentName componentName, FlushMetrics flushMetrics, ContentCaptureOptions contentCaptureOptions, int i2) {
        IContentCaptureServiceCallback iContentCaptureServiceCallback = this.mContentCaptureServiceCallback;
        if (iContentCaptureServiceCallback == null) {
            Log.w(TAG, "writeSessionFlush(): no server callback");
            return;
        }
        try {
            iContentCaptureServiceCallback.writeSessionFlush(i, componentName, flushMetrics, contentCaptureOptions, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to write flush metrics: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareReadAdapterDelegate extends IDataShareReadAdapter.Stub {
        private final Object mLock = new Object();
        private final WeakReference<LocalDataShareAdapterResourceManager> mResourceManagerReference;

        DataShareReadAdapterDelegate(Executor executor, DataShareReadAdapter dataShareReadAdapter, LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager) {
            Objects.requireNonNull(executor);
            Objects.requireNonNull(dataShareReadAdapter);
            Objects.requireNonNull(localDataShareAdapterResourceManager);
            localDataShareAdapterResourceManager.initializeForDelegate(this, dataShareReadAdapter, executor);
            this.mResourceManagerReference = new WeakReference<>(localDataShareAdapterResourceManager);
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void start(final ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(new Consumer() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DataShareReadAdapter) obj).onStart(ParcelFileDescriptor.this);
                    }
                }, "onStart");
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void error(final int i) throws RemoteException {
            synchronized (this.mLock) {
                executeAdapterMethodLocked(new Consumer() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DataShareReadAdapter) obj).onError(i);
                    }
                }, "onError");
                clearHardReferences();
            }
        }

        @Override // android.service.contentcapture.IDataShareReadAdapter
        public void finish() throws RemoteException {
            synchronized (this.mLock) {
                clearHardReferences();
            }
        }

        private void executeAdapterMethodLocked(final Consumer<DataShareReadAdapter> consumer, String str) {
            LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                Slog.w(ContentCaptureService.TAG, "Can't execute " + str + "(), resource manager has been GC'ed");
                return;
            }
            final DataShareReadAdapter adapter = localDataShareAdapterResourceManager.getAdapter(this);
            Executor executor = localDataShareAdapterResourceManager.getExecutor(this);
            if (adapter == null || executor == null) {
                Slog.w(ContentCaptureService.TAG, "Can't execute " + str + "(), references are null");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.service.contentcapture.ContentCaptureService$DataShareReadAdapterDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(adapter);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void clearHardReferences() {
            LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                Slog.w(ContentCaptureService.TAG, "Can't clear references, resource manager has been GC'ed");
            } else {
                localDataShareAdapterResourceManager.clearHardReferences(this);
            }
        }
    }

    private static class LocalDataShareAdapterResourceManager {
        private Map<DataShareReadAdapterDelegate, DataShareReadAdapter> mDataShareReadAdapterHardReferences;
        private Map<DataShareReadAdapterDelegate, Executor> mExecutorHardReferences;

        private LocalDataShareAdapterResourceManager() {
            this.mDataShareReadAdapterHardReferences = new HashMap();
            this.mExecutorHardReferences = new HashMap();
        }

        void initializeForDelegate(DataShareReadAdapterDelegate dataShareReadAdapterDelegate, DataShareReadAdapter dataShareReadAdapter, Executor executor) {
            this.mDataShareReadAdapterHardReferences.put(dataShareReadAdapterDelegate, dataShareReadAdapter);
            this.mExecutorHardReferences.put(dataShareReadAdapterDelegate, executor);
        }

        Executor getExecutor(DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            return this.mExecutorHardReferences.get(dataShareReadAdapterDelegate);
        }

        DataShareReadAdapter getAdapter(DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            return this.mDataShareReadAdapterHardReferences.get(dataShareReadAdapterDelegate);
        }

        void clearHardReferences(DataShareReadAdapterDelegate dataShareReadAdapterDelegate) {
            this.mDataShareReadAdapterHardReferences.remove(dataShareReadAdapterDelegate);
            this.mExecutorHardReferences.remove(dataShareReadAdapterDelegate);
        }
    }
}
