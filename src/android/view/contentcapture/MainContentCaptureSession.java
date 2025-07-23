package android.view.contentcapture;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.Log;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.View;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.IContentCaptureDirectManager;
import android.view.contentcapture.ViewNode;
import android.view.contentprotection.ContentProtectionEventProcessor;
import android.view.inputmethod.BaseInputConnection;
import com.android.internal.hidden_from_bootclasspath.android.view.contentcapture.flags.Flags;
import com.android.internal.os.IResultReceiver;
import com.android.modules.expresslog.Counter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class MainContentCaptureSession extends ContentCaptureSession {
    private static final String CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID = "content_capture.value_content_capture_wrong_thread_count";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    private static final String TAG = "MainContentCaptureSession";
    private IBinder mApplicationToken;
    public ComponentName mComponentName;
    private final Handler mContentCaptureHandler;
    public ContentProtectionEventProcessor mContentProtectionEventProcessor;
    private final ContentCaptureManager.StrippedContext mContext;
    public IContentCaptureDirectManager mDirectServiceInterface;
    private IBinder.DeathRecipient mDirectServiceVulture;
    public final ConcurrentLinkedQueue<ContentCaptureEvent> mEventProcessQueue;
    public ArrayList<ContentCaptureEvent> mEvents;
    private final LocalLog mFlushHistory;
    private final ContentCaptureManager mManager;
    private long mNextFlush;
    private final SessionStateReceiver mSessionStateReceiver;
    private IBinder mShareableActivityToken;
    private final IContentCaptureManager mSystemServerInterface;
    private final Handler mUiHandler;
    private final AtomicBoolean mDisabled = new AtomicBoolean(false);
    private int mState = 0;
    private boolean mNextFlushForTextChanged = false;
    private final AtomicInteger mWrongThreadCount = new AtomicInteger(0);

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession getMainCaptureSession() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SessionStateReceiver extends IResultReceiver.Stub {
        private final WeakReference<MainContentCaptureSession> mMainSession;

        SessionStateReceiver(MainContentCaptureSession mainContentCaptureSession) {
            this.mMainSession = new WeakReference<>(mainContentCaptureSession);
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(final int i, Bundle bundle) {
            final IBinder iBinder;
            final MainContentCaptureSession mainContentCaptureSession = this.mMainSession.get();
            if (mainContentCaptureSession == null) {
                Log.w(MainContentCaptureSession.TAG, "received result after mina session released");
                return;
            }
            if (bundle == null) {
                iBinder = null;
            } else {
                if (bundle.getBoolean("enabled")) {
                    mainContentCaptureSession.mDisabled.set(i == 2);
                    return;
                }
                iBinder = bundle.getBinder("binder");
                if (iBinder == null) {
                    Log.wtf(MainContentCaptureSession.TAG, "No binder extra result");
                    mainContentCaptureSession.runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainContentCaptureSession.this.resetSession(260);
                        }
                    });
                    return;
                }
            }
            mainContentCaptureSession.runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MainContentCaptureSession.this.onSessionStarted(i, iBinder);
                }
            });
        }
    }

    public MainContentCaptureSession(ContentCaptureManager.StrippedContext strippedContext, ContentCaptureManager contentCaptureManager, Handler handler, Handler handler2, IContentCaptureManager iContentCaptureManager) {
        this.mContext = strippedContext;
        this.mManager = contentCaptureManager;
        this.mUiHandler = handler;
        this.mContentCaptureHandler = handler2;
        this.mSystemServerInterface = iContentCaptureManager;
        int i = contentCaptureManager.mOptions.logHistorySize;
        this.mFlushHistory = i > 0 ? new LocalLog(i) : null;
        this.mSessionStateReceiver = new SessionStateReceiver(this);
        this.mEventProcessQueue = new ConcurrentLinkedQueue<>();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext contentCaptureContext) {
        ChildContentCaptureSession childContentCaptureSession = new ChildContentCaptureSession(this, contentCaptureContext);
        internalNotifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(final IBinder iBinder, final IBinder iBinder2, final ComponentName componentName, final int i) {
        runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$start$0(iBinder, iBinder2, componentName, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: startImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$start$0(IBinder iBinder, IBinder iBinder2, ComponentName componentName, int i) {
        ComponentName componentName2;
        checkOnContentCaptureThread();
        if (isContentCaptureEnabled()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "start(): token=" + iBinder + ", comp=" + ComponentName.flattenToShortString(componentName));
            }
            if (hasStarted()) {
                if (ContentCaptureHelper.sDebug) {
                    Log.d(TAG, "ignoring handleStartSession(" + iBinder + "/" + ComponentName.flattenToShortString(componentName) + " while on state " + getStateAsString(this.mState));
                    return;
                }
                return;
            }
            this.mState = 1;
            this.mApplicationToken = iBinder;
            this.mShareableActivityToken = iBinder2;
            this.mComponentName = componentName;
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleStartSession(): token=" + iBinder + ", act=" + getDebugState() + ", id=" + this.mId);
            }
            try {
                componentName2 = componentName;
            } catch (RemoteException e) {
                e = e;
                componentName2 = componentName;
            }
            try {
                this.mSystemServerInterface.startSession(this.mApplicationToken, this.mShareableActivityToken, componentName2, this.mId, i, this.mSessionStateReceiver);
            } catch (RemoteException e2) {
                e = e2;
                Log.w(TAG, "Error starting session for " + componentName2.flattenToShortString() + ": " + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        clearAndRunOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$onDestroy$1();
            }
        }, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroy$1() {
        try {
            flush(4);
        } finally {
            destroySession();
        }
    }

    public void onSessionStarted(int i, IBinder iBinder) {
        checkOnContentCaptureThread();
        if (iBinder != null) {
            this.mDirectServiceInterface = IContentCaptureDirectManager.Stub.asInterface(iBinder);
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda7
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    MainContentCaptureSession.this.lambda$onSessionStarted$2();
                }
            };
            this.mDirectServiceVulture = deathRecipient;
            try {
                iBinder.linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to link to death on " + iBinder + ": " + e);
            }
        }
        if (isContentProtectionEnabled()) {
            this.mContentProtectionEventProcessor = new ContentProtectionEventProcessor(this.mManager.getContentProtectionEventBuffer(), this.mContentCaptureHandler, this.mSystemServerInterface, this.mComponentName.getPackageName(), this.mManager.mOptions.contentProtectionOptions);
        } else {
            this.mContentProtectionEventProcessor = null;
        }
        if ((i & 4) != 0) {
            resetSession(i);
        } else {
            this.mState = i;
            this.mDisabled.set(false);
            lambda$scheduleFlush$3(7);
        }
        if (ContentCaptureHelper.sVerbose) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("handleSessionStarted() result: id=");
            sb.append(this.mId);
            sb.append(" resultCode=");
            sb.append(i);
            sb.append(", state=");
            sb.append(getStateAsString(this.mState));
            sb.append(", disabled=");
            sb.append(this.mDisabled.get());
            sb.append(", binder=");
            sb.append(iBinder);
            sb.append(", events=");
            ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
            sb.append(arrayList != null ? arrayList.size() : 0);
            Log.v(str, sb.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSessionStarted$2() {
        Log.w(TAG, "Keeping session " + this.mId + " when service died");
        this.mState = 1024;
        this.mDisabled.set(true);
    }

    public void sendEvent(ContentCaptureEvent contentCaptureEvent) {
        sendEvent(contentCaptureEvent, false);
    }

    private void sendEvent(ContentCaptureEvent contentCaptureEvent, boolean z) {
        checkOnContentCaptureThread();
        int type = contentCaptureEvent.getType();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleSendEvent(" + getDebugState() + "): " + contentCaptureEvent);
        }
        if (!hasStarted() && type != -1 && type != 6) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleSendEvent(" + getDebugState() + ", " + ContentCaptureEvent.getTypeAsString(type) + "): dropping because session not started yet");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleSendEvent(): ignoring when disabled");
                return;
            }
            return;
        }
        if (Trace.isTagEnabled(8L) && type == 4) {
            Trace.asyncTraceBegin(8L, "sendEventAsync", 0);
        }
        if (isContentProtectionReceiverEnabled()) {
            sendContentProtectionEvent(contentCaptureEvent);
        }
        if (isContentCaptureReceiverEnabled()) {
            sendContentCaptureEvent(contentCaptureEvent, z);
        }
        if (Trace.isTagEnabled(8L) && type == 5) {
            Trace.asyncTraceEnd(8L, "sendEventAsync", 0);
        }
    }

    private void sendContentProtectionEvent(ContentCaptureEvent contentCaptureEvent) {
        checkOnContentCaptureThread();
        ContentProtectionEventProcessor contentProtectionEventProcessor = this.mContentProtectionEventProcessor;
        if (contentProtectionEventProcessor != null) {
            contentProtectionEventProcessor.processEvent(contentCaptureEvent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ca A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x011b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x013b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0184  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendContentCaptureEvent(android.view.contentcapture.ContentCaptureEvent r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.contentcapture.MainContentCaptureSession.sendContentCaptureEvent(android.view.contentcapture.ContentCaptureEvent, boolean):void");
    }

    private boolean hasStarted() {
        checkOnContentCaptureThread();
        return this.mState != 0;
    }

    private void scheduleFlush(final int i, boolean z) {
        int i2;
        checkOnContentCaptureThread();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleScheduleFlush(" + getDebugState(i) + ", checkExisting=" + z);
        }
        if (!hasStarted()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleScheduleFlush(): session not started yet");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("handleScheduleFlush(");
            sb.append(getDebugState(i));
            sb.append("): should not be called when disabled. events=");
            ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
            sb.append(arrayList == null ? null : Integer.valueOf(arrayList.size()));
            Log.e(str, sb.toString());
            return;
        }
        if (z && this.mContentCaptureHandler.hasMessages(1)) {
            this.mContentCaptureHandler.removeMessages(1);
        }
        if (i == 6) {
            i2 = this.mManager.mOptions.textChangeFlushingFrequencyMs;
        } else {
            if (i != 5 && ContentCaptureHelper.sDebug) {
                Log.d(TAG, "handleScheduleFlush(" + getDebugState(i) + "): not a timeout reason because mDirectServiceInterface is not ready yet");
            }
            i2 = this.mManager.mOptions.idleFlushingFrequencyMs;
        }
        long j = i2;
        this.mNextFlush = System.currentTimeMillis() + j;
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleScheduleFlush(): scheduled to flush in " + i2 + "ms: " + TimeUtils.logTimeOfDay(this.mNextFlush));
        }
        this.mContentCaptureHandler.postDelayed(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$scheduleFlush$3(i);
            }
        }, 1, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: flushIfNeeded, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleFlush$3(int i) {
        checkOnContentCaptureThread();
        ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
        if (arrayList == null || arrayList.isEmpty()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "Nothing to flush");
                return;
            }
            return;
        }
        flush(i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void flush(final int i) {
        runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$flush$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: flushImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$flush$4(int i) {
        String str;
        checkOnContentCaptureThread();
        ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
        if (arrayList == null || arrayList.size() == 0) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "Don't flush for empty event buffer.");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            Log.e(TAG, "handleForceFlush(" + getDebugState(i) + "): should not be when disabled");
            return;
        }
        if (isContentCaptureReceiverEnabled()) {
            if (this.mDirectServiceInterface == null) {
                if (ContentCaptureHelper.sVerbose) {
                    Log.v(TAG, "handleForceFlush(" + getDebugState(i) + "): hold your horses, client not ready: " + this.mEvents);
                }
                if (this.mContentCaptureHandler.hasMessages(1)) {
                    return;
                }
                scheduleFlush(i, false);
                return;
            }
            this.mNextFlushForTextChanged = false;
            int size = this.mEvents.size();
            String flushReasonAsString = getFlushReasonAsString(i);
            if (ContentCaptureHelper.sVerbose) {
                ContentCaptureEvent contentCaptureEvent = this.mEvents.get(size - 1);
                if (i == 8) {
                    str = ". The force flush event " + ContentCaptureEvent.getTypeAsString(contentCaptureEvent.getType());
                } else {
                    str = "";
                }
                Log.v(TAG, "Flushing " + size + " event(s) for " + getDebugState(i) + str);
            }
            if (this.mFlushHistory != null) {
                this.mFlushHistory.log("r=" + flushReasonAsString + " s=" + size + " m=" + this.mManager.mOptions.maxBufferSize + " i=" + this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            try {
                this.mContentCaptureHandler.removeMessages(1);
                this.mDirectServiceInterface.sendEvents(clearEvents(), i, this.mManager.mOptions);
            } catch (RemoteException e) {
                Log.w(TAG, "Error sending " + size + " for " + getDebugState() + ": " + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext contentCaptureContext) {
        internalNotifyContextUpdated(this.mId, contentCaptureContext);
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        checkOnContentCaptureThread();
        if (this.mEvents == null) {
            return new ParceledListSlice<>(Collections.EMPTY_LIST);
        }
        ArrayList arrayList = new ArrayList(this.mEvents);
        this.mEvents.clear();
        return new ParceledListSlice<>(arrayList);
    }

    public void destroySession() {
        checkOnContentCaptureThread();
        if (ContentCaptureHelper.sDebug) {
            String str = TAG;
            StringBuilder sb = new StringBuilder("Destroying session (ctx=");
            sb.append(this.mContext);
            sb.append(", id=");
            sb.append(this.mId);
            sb.append(") with ");
            ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
            sb.append(arrayList == null ? 0 : arrayList.size());
            sb.append(" event(s) for ");
            sb.append(getDebugState());
            Log.d(str, sb.toString());
        }
        reportWrongThreadMetric();
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        } catch (RemoteException e) {
            Log.e(TAG, "Error destroying system-service session " + this.mId + " for " + getDebugState() + ": " + e);
        }
        IContentCaptureDirectManager iContentCaptureDirectManager = this.mDirectServiceInterface;
        if (iContentCaptureDirectManager != null) {
            iContentCaptureDirectManager.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
        this.mEventProcessQueue.clear();
    }

    public void resetSession(int i) {
        checkOnContentCaptureThread();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleResetSession(" + getActivityName() + "): from " + getStateAsString(this.mState) + " to " + getStateAsString(i));
        }
        this.mState = i;
        this.mDisabled.set((i & 4) != 0);
        this.mApplicationToken = null;
        this.mShareableActivityToken = null;
        this.mComponentName = null;
        this.mEvents = null;
        IContentCaptureDirectManager iContentCaptureDirectManager = this.mDirectServiceInterface;
        if (iContentCaptureDirectManager != null) {
            try {
                iContentCaptureDirectManager.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
            } catch (NoSuchElementException unused) {
                Log.w(TAG, "IContentCaptureDirectManager does not exist");
            }
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
        this.mContentCaptureHandler.removeMessages(1);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int i, ViewNode.ViewStructureImpl viewStructureImpl) {
        enqueueEvent(new ContentCaptureEvent(i, 1).setViewNode(viewStructureImpl.mNode));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int i, AutofillId autofillId) {
        enqueueEvent(new ContentCaptureEvent(i, 2).setAutofillId(autofillId));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int i, AutofillId autofillId, CharSequence charSequence) {
        int i2;
        int i3;
        CharSequence trimToParcelableSize = TextUtils.trimToParcelableSize(charSequence);
        if (trimToParcelableSize != null && trimToParcelableSize == charSequence) {
            trimToParcelableSize = trimToParcelableSize.toString();
        }
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable) charSequence;
            i2 = BaseInputConnection.getComposingSpanStart(spannable);
            i3 = BaseInputConnection.getComposingSpanEnd(spannable);
        } else {
            i2 = -1;
            i3 = -1;
        }
        enqueueEvent(new ContentCaptureEvent(i, 3).setAutofillId(autofillId).setText(trimToParcelableSize).setComposingIndex(i2, i3).setSelectionIndex(Selection.getSelectionStart(charSequence), Selection.getSelectionEnd(charSequence)));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int i, Insets insets) {
        enqueueEvent(new ContentCaptureEvent(i, 9).setInsets(insets));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int i, boolean z) {
        int i2 = z ? 4 : 5;
        boolean z2 = true;
        if (this.mManager.getFlushViewTreeAppearingEventDisabled() && z) {
            z2 = false;
        }
        enqueueEvent(new ContentCaptureEvent(i, i2), z2);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionResumed() {
        enqueueEvent(new ContentCaptureEvent(this.mId, 7), true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionPaused() {
        enqueueEvent(new ContentCaptureEvent(this.mId, 8), true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return super.isContentCaptureEnabled() && this.mManager.isContentCaptureEnabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isDisabled() {
        return this.mDisabled.get();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean setDisabled(boolean z) {
        return this.mDisabled.compareAndSet(!z, z);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(int i, int i2, ContentCaptureContext contentCaptureContext) {
        enqueueEvent(new ContentCaptureEvent(i2, -1).setParentSessionId(i).setClientContext(contentCaptureContext), true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int i, int i2) {
        enqueueEvent(new ContentCaptureEvent(i2, -2).setParentSessionId(i), true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int i, ContentCaptureContext contentCaptureContext) {
        enqueueEvent(new ContentCaptureEvent(i, 6).setClientContext(contentCaptureContext), true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int i, Rect rect) {
        enqueueEvent(new ContentCaptureEvent(i, 10).setBounds(rect));
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifySessionFlushEvent(int i) {
        enqueueEvent(new ContentCaptureEvent(i, 11), true);
    }

    private List<ContentCaptureEvent> clearBufferEvents() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            ContentCaptureEvent poll = this.mEventProcessQueue.poll();
            if (poll == null) {
                return arrayList;
            }
            arrayList.add(poll);
        }
    }

    private void enqueueEvent(ContentCaptureEvent contentCaptureEvent) {
        enqueueEvent(contentCaptureEvent, false);
    }

    private void enqueueEvent(final ContentCaptureEvent contentCaptureEvent, boolean z) {
        if (z || this.mEventProcessQueue.size() >= this.mManager.mOptions.maxBufferSize - 1) {
            final List<ContentCaptureEvent> clearBufferEvents = clearBufferEvents();
            runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MainContentCaptureSession.this.lambda$enqueueEvent$5(clearBufferEvents, contentCaptureEvent);
                }
            });
        } else {
            this.mEventProcessQueue.offer(contentCaptureEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enqueueEvent$5(List list, ContentCaptureEvent contentCaptureEvent) {
        for (int i = 0; i < list.size(); i++) {
            sendEvent((ContentCaptureEvent) list.get(i));
        }
        sendEvent(contentCaptureEvent, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(final SparseArray<ArrayList<Object>> sparseArray) {
        runOnUiThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$notifyContentCaptureEvents$7(sparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyContentCaptureEvents$7(final SparseArray sparseArray) {
        prepareViewStructures(sparseArray);
        runOnContentCaptureThread(new Runnable() { // from class: android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MainContentCaptureSession.this.lambda$notifyContentCaptureEvents$6(sparseArray);
            }
        });
    }

    private void prepareViewStructures(SparseArray<ArrayList<Object>> sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            ArrayList valueAt = sparseArray.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                Object obj = valueAt.get(i2);
                if (obj instanceof View) {
                    View view = (View) obj;
                    ContentCaptureSession contentCaptureSession = view.getContentCaptureSession();
                    ViewStructureSession viewStructureSession = new ViewStructureSession();
                    valueAt.set(i2, viewStructureSession);
                    if (contentCaptureSession == null) {
                        Log.w(TAG, "no content capture session on view: " + view);
                    } else {
                        int id = contentCaptureSession.getId();
                        if (id != keyAt) {
                            Log.w(TAG, "content capture session mismatch for view (" + view + "): was " + keyAt + " before, it's " + id + " now");
                        } else {
                            ViewStructure newViewStructure = contentCaptureSession.newViewStructure(view);
                            view.onProvideContentCaptureStructure(newViewStructure, 0);
                            viewStructureSession.setSession(contentCaptureSession);
                            viewStructureSession.setStructure(newViewStructure);
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: notifyContentCaptureEventsImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyContentCaptureEvents$6(SparseArray<ArrayList<Object>> sparseArray) {
        checkOnContentCaptureThread();
        try {
            if (Trace.isTagEnabled(8L)) {
                Trace.traceBegin(8L, "notifyContentCaptureEvents");
            }
            for (int i = 0; i < sparseArray.size(); i++) {
                int keyAt = sparseArray.keyAt(i);
                internalNotifyViewTreeEvent(keyAt, true);
                ArrayList<Object> valueAt = sparseArray.valueAt(i);
                for (int i2 = 0; i2 < valueAt.size(); i2++) {
                    Object obj = valueAt.get(i2);
                    if (obj instanceof AutofillId) {
                        internalNotifyViewDisappeared(keyAt, (AutofillId) obj);
                    } else if (obj instanceof ViewStructureSession) {
                        ((ViewStructureSession) obj).notifyViewAppeared();
                    } else if (obj instanceof Insets) {
                        internalNotifyViewInsetsChanged(keyAt, (Insets) obj);
                    } else {
                        Log.w(TAG, "invalid content capture event: " + obj);
                    }
                }
                internalNotifyViewTreeEvent(keyAt, false);
                if (Flags.flushAfterEachFrame()) {
                    internalNotifySessionFlushEvent(keyAt);
                }
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void dump(String str, PrintWriter printWriter) {
        super.dump(str, printWriter);
        printWriter.print(str);
        printWriter.print("mContext: ");
        printWriter.println(this.mContext);
        printWriter.print(str);
        printWriter.print("user: ");
        printWriter.println(this.mContext.getUserId());
        if (this.mDirectServiceInterface != null) {
            printWriter.print(str);
            printWriter.print("mDirectServiceInterface: ");
            printWriter.println(this.mDirectServiceInterface);
        }
        printWriter.print(str);
        printWriter.print("mDisabled: ");
        printWriter.println(this.mDisabled.get());
        printWriter.print(str);
        printWriter.print("isEnabled(): ");
        printWriter.println(isContentCaptureEnabled());
        printWriter.print(str);
        printWriter.print("state: ");
        printWriter.println(getStateAsString(this.mState));
        if (this.mApplicationToken != null) {
            printWriter.print(str);
            printWriter.print("app token: ");
            printWriter.println(this.mApplicationToken);
        }
        if (this.mShareableActivityToken != null) {
            printWriter.print(str);
            printWriter.print("sharable activity token: ");
            printWriter.println(this.mShareableActivityToken);
        }
        if (this.mComponentName != null) {
            printWriter.print(str);
            printWriter.print("component name: ");
            printWriter.println(this.mComponentName.flattenToShortString());
        }
        ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = this.mEvents.size();
            printWriter.print(str);
            printWriter.print("buffered events: ");
            printWriter.print(size);
            printWriter.print('/');
            printWriter.println(this.mManager.mOptions.maxBufferSize);
            if (ContentCaptureHelper.sVerbose && size > 0) {
                String str2 = str + "  ";
                for (int i = 0; i < size; i++) {
                    ContentCaptureEvent contentCaptureEvent = this.mEvents.get(i);
                    printWriter.print(str2);
                    printWriter.print(i);
                    printWriter.print(": ");
                    contentCaptureEvent.dump(printWriter);
                    printWriter.println();
                }
            }
            printWriter.print(str);
            printWriter.print("mNextFlushForTextChanged: ");
            printWriter.println(this.mNextFlushForTextChanged);
            printWriter.print(str);
            printWriter.print("flush frequency: ");
            if (this.mNextFlushForTextChanged) {
                printWriter.println(this.mManager.mOptions.textChangeFlushingFrequencyMs);
            } else {
                printWriter.println(this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            printWriter.print(str);
            printWriter.print("next flush: ");
            TimeUtils.formatDuration(this.mNextFlush - System.currentTimeMillis(), printWriter);
            printWriter.print(" (");
            printWriter.print(TimeUtils.logTimeOfDay(this.mNextFlush));
            printWriter.println(NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mFlushHistory != null) {
            printWriter.print(str);
            printWriter.println("flush history:");
            this.mFlushHistory.reverseDump(null, printWriter, null);
            printWriter.println();
        } else {
            printWriter.print(str);
            printWriter.println("not logging flush history");
        }
        super.dump(str, printWriter);
    }

    private String getActivityName() {
        if (this.mComponentName == null) {
            return "pkg:" + this.mContext.getPackageName();
        }
        return "act:" + this.mComponentName.flattenToShortString();
    }

    private String getDebugState() {
        return getActivityName() + " [state=" + getStateAsString(this.mState) + ", disabled=" + this.mDisabled.get() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private String getDebugState(int i) {
        return getDebugState() + ", reason=" + getFlushReasonAsString(i);
    }

    private boolean isContentProtectionReceiverEnabled() {
        return this.mManager.mOptions.contentProtectionOptions.enableReceiver;
    }

    private boolean isContentCaptureReceiverEnabled() {
        return this.mManager.mOptions.enableReceiver;
    }

    private boolean isContentProtectionEnabled() {
        if (!this.mManager.mOptions.contentProtectionOptions.enableReceiver || this.mManager.getContentProtectionEventBuffer() == null || this.mComponentName == null) {
            return false;
        }
        return (this.mManager.mOptions.contentProtectionOptions.requiredGroups.isEmpty() && this.mManager.mOptions.contentProtectionOptions.optionalGroups.isEmpty()) ? false : true;
    }

    private void checkOnContentCaptureThread() {
        if (this.mContentCaptureHandler.getLooper().isCurrentThread()) {
            return;
        }
        this.mWrongThreadCount.incrementAndGet();
        Log.e(TAG, "MainContentCaptureSession running on " + Thread.currentThread());
    }

    private void reportWrongThreadMetric() {
        Counter.logIncrement(CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID, this.mWrongThreadCount.getAndSet(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnContentCaptureThread(Runnable runnable) {
        if (!this.mContentCaptureHandler.getLooper().isCurrentThread()) {
            this.mContentCaptureHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    private void clearAndRunOnContentCaptureThread(Runnable runnable, int i) {
        if (!this.mContentCaptureHandler.getLooper().isCurrentThread()) {
            this.mContentCaptureHandler.removeMessages(i);
            this.mContentCaptureHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    private void runOnUiThread(Runnable runnable) {
        if (this.mUiHandler.getLooper().isCurrentThread()) {
            runnable.run();
        } else {
            this.mUiHandler.post(runnable);
        }
    }

    private static final class ViewStructureSession {
        private ContentCaptureSession mSession;
        private ViewStructure mStructure;

        ViewStructureSession() {
        }

        void setSession(ContentCaptureSession contentCaptureSession) {
            this.mSession = contentCaptureSession;
        }

        void setStructure(ViewStructure viewStructure) {
            this.mStructure = viewStructure;
        }

        void notifyViewAppeared() {
            ViewStructure viewStructure;
            ContentCaptureSession contentCaptureSession = this.mSession;
            if (contentCaptureSession == null || (viewStructure = this.mStructure) == null) {
                return;
            }
            contentCaptureSession.notifyViewAppeared(viewStructure);
        }
    }
}
