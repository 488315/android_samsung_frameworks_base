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
import android.view.contentprotection.ContentProtectionEventProcessor;
import android.view.inputmethod.BaseInputConnection;

import com.android.internal.os.IResultReceiver;
import com.android.modules.expresslog.Counter;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class MainContentCaptureSession extends ContentCaptureSession {
    private static final String CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID =
            "content_capture.value_content_capture_wrong_thread_count";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    private static final String TAG = MainContentCaptureSession.class.getSimpleName();
    private IBinder mApplicationToken;
    public ComponentName mComponentName;
    public ContentProtectionEventProcessor mContentProtectionEventProcessor;
    private final ContentCaptureManager.StrippedContext mContext;
    public IContentCaptureDirectManager mDirectServiceInterface;
    private IBinder.DeathRecipient mDirectServiceVulture;
    public ArrayList<ContentCaptureEvent> mEvents;
    private final LocalLog mFlushHistory;
    private final Handler mHandler;
    private final ContentCaptureManager mManager;
    private long mNextFlush;
    private final SessionStateReceiver mSessionStateReceiver;
    private IBinder mShareableActivityToken;
    private final IContentCaptureManager mSystemServerInterface;
    private final AtomicBoolean mDisabled = new AtomicBoolean(false);
    private int mState = 0;
    private boolean mNextFlushForTextChanged = false;
    private final AtomicInteger mWrongThreadCount = new AtomicInteger(0);

    /* JADX INFO: Access modifiers changed from: private */
    static class SessionStateReceiver extends IResultReceiver.Stub {
        private final WeakReference<MainContentCaptureSession> mMainSession;

        SessionStateReceiver(MainContentCaptureSession session) {
            this.mMainSession = new WeakReference<>(session);
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(final int resultCode, Bundle resultData) {
            final IBinder binder;
            final MainContentCaptureSession mainSession = this.mMainSession.get();
            if (mainSession == null) {
                Log.w(MainContentCaptureSession.TAG, "received result after mina session released");
                return;
            }
            if (resultData != null) {
                boolean hasEnabled = resultData.getBoolean("enabled");
                if (hasEnabled) {
                    boolean disabled = resultCode == 2;
                    mainSession.mDisabled.set(disabled);
                    return;
                } else {
                    binder = resultData.getBinder("binder");
                    if (binder == null) {
                        Log.wtf(MainContentCaptureSession.TAG, "No binder extra result");
                        mainSession.mHandler.post(
                                new Runnable() { // from class:
                                                 // android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MainContentCaptureSession.this.resetSession(260);
                                    }
                                });
                        return;
                    }
                }
            } else {
                binder = null;
            }
            mainSession.mHandler.post(
                    new Runnable() { // from class:
                                     // android.view.contentcapture.MainContentCaptureSession$SessionStateReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainContentCaptureSession.this.onSessionStarted(resultCode, binder);
                        }
                    });
        }
    }

    public MainContentCaptureSession(
            ContentCaptureManager.StrippedContext context,
            ContentCaptureManager manager,
            Handler handler,
            IContentCaptureManager systemServerInterface) {
        this.mContext = context;
        this.mManager = manager;
        this.mHandler = handler;
        this.mSystemServerInterface = systemServerInterface;
        int logHistorySize = this.mManager.mOptions.logHistorySize;
        this.mFlushHistory = logHistorySize > 0 ? new LocalLog(logHistorySize) : null;
        this.mSessionStateReceiver = new SessionStateReceiver(this);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession getMainCaptureSession() {
        return this;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext clientContext) {
        ContentCaptureSession child = new ChildContentCaptureSession(this, clientContext);
        internalNotifyChildSessionStarted(this.mId, child.mId, clientContext);
        return child;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(IBinder token, IBinder shareableActivityToken, ComponentName component, int flags) {
        checkOnUiThread();
        if (isContentCaptureEnabled()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(
                        TAG,
                        "start(): token="
                                + token
                                + ", comp="
                                + ComponentName.flattenToShortString(component));
            }
            if (hasStarted()) {
                if (ContentCaptureHelper.sDebug) {
                    Log.d(
                            TAG,
                            "ignoring handleStartSession("
                                    + token
                                    + "/"
                                    + ComponentName.flattenToShortString(component)
                                    + " while on state "
                                    + getStateAsString(this.mState));
                    return;
                }
                return;
            }
            this.mState = 1;
            this.mApplicationToken = token;
            this.mShareableActivityToken = shareableActivityToken;
            this.mComponentName = component;
            if (ContentCaptureHelper.sVerbose) {
                Log.v(
                        TAG,
                        "handleStartSession(): token="
                                + token
                                + ", act="
                                + getDebugState()
                                + ", id="
                                + this.mId);
            }
            try {
                this.mSystemServerInterface.startSession(
                        this.mApplicationToken,
                        this.mShareableActivityToken,
                        component,
                        this.mId,
                        flags,
                        this.mSessionStateReceiver);
            } catch (RemoteException e) {
                Log.w(
                        TAG,
                        "Error starting session for "
                                + component.flattenToShortString()
                                + ": "
                                + e);
            }
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$onDestroy$0();
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroy$0() {
        try {
            flush(4);
        } finally {
            destroySession();
        }
    }

    public void onSessionStarted(int resultCode, IBinder binder) {
        checkOnUiThread();
        if (binder != null) {
            this.mDirectServiceInterface = IContentCaptureDirectManager.Stub.asInterface(binder);
            this.mDirectServiceVulture =
                    new IBinder
                            .DeathRecipient() { // from class:
                                                // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda13
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            MainContentCaptureSession.this.lambda$onSessionStarted$1();
                        }
                    };
            try {
                binder.linkToDeath(this.mDirectServiceVulture, 0);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to link to death on " + binder + ": " + e);
            }
        }
        if (isContentProtectionEnabled()) {
            this.mContentProtectionEventProcessor =
                    new ContentProtectionEventProcessor(
                            this.mManager.getContentProtectionEventBuffer(),
                            this.mHandler,
                            this.mSystemServerInterface,
                            this.mComponentName.getPackageName(),
                            this.mManager.mOptions.contentProtectionOptions);
        } else {
            this.mContentProtectionEventProcessor = null;
        }
        if ((resultCode & 4) != 0) {
            resetSession(resultCode);
        } else {
            this.mState = resultCode;
            this.mDisabled.set(false);
            lambda$scheduleFlush$2(7);
        }
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "handleSessionStarted() result: id="
                            + this.mId
                            + " resultCode="
                            + resultCode
                            + ", state="
                            + getStateAsString(this.mState)
                            + ", disabled="
                            + this.mDisabled.get()
                            + ", binder="
                            + binder
                            + ", events="
                            + (this.mEvents != null ? this.mEvents.size() : 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSessionStarted$1() {
        Log.w(TAG, "Keeping session " + this.mId + " when service died");
        this.mState = 1024;
        this.mDisabled.set(true);
    }

    /* renamed from: sendEvent, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$notifyWindowBoundsChanged$13(ContentCaptureEvent event) {
        sendEvent(event, false);
    }

    private void sendEvent(ContentCaptureEvent event, boolean forceFlush) {
        checkOnUiThread();
        int eventType = event.getType();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "handleSendEvent(" + getDebugState() + "): " + event);
        }
        if (!hasStarted() && eventType != -1 && eventType != 6) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(
                        TAG,
                        "handleSendEvent("
                                + getDebugState()
                                + ", "
                                + ContentCaptureEvent.getTypeAsString(eventType)
                                + "): dropping because session not started yet");
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
        if (Trace.isTagEnabled(8L) && eventType == 4) {
            Trace.asyncTraceBegin(8L, "sendEventAsync", 0);
        }
        if (isContentProtectionReceiverEnabled()) {
            sendContentProtectionEvent(event);
        }
        if (isContentCaptureReceiverEnabled()) {
            sendContentCaptureEvent(event, forceFlush);
        }
        if (Trace.isTagEnabled(8L) && eventType == 5) {
            Trace.asyncTraceEnd(8L, "sendEventAsync", 0);
        }
    }

    private void sendContentProtectionEvent(ContentCaptureEvent event) {
        checkOnUiThread();
        if (this.mContentProtectionEventProcessor != null) {
            this.mContentProtectionEventProcessor.processEvent(event);
        }
    }

    private void sendContentCaptureEvent(ContentCaptureEvent event, boolean forceFlush) {
        int flushReason;
        int flushReason2;
        checkOnUiThread();
        int eventType = event.getType();
        int maxBufferSize = this.mManager.mOptions.maxBufferSize;
        if (this.mEvents == null) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleSendEvent(): creating buffer for " + maxBufferSize + " events");
            }
            this.mEvents = new ArrayList<>(maxBufferSize);
        }
        boolean addEvent = true;
        if (eventType == 3) {
            CharSequence text = event.getText();
            boolean hasComposingSpan = event.hasComposingSpan();
            if (hasComposingSpan) {
                ContentCaptureEvent lastEvent = null;
                int index = this.mEvents.size() - 1;
                while (true) {
                    if (index < 0) {
                        break;
                    }
                    ContentCaptureEvent tmpEvent = this.mEvents.get(index);
                    if (!event.getId().equals(tmpEvent.getId())) {
                        index--;
                    } else {
                        lastEvent = tmpEvent;
                        break;
                    }
                }
                if (lastEvent != null && lastEvent.hasComposingSpan()) {
                    CharSequence lastText = lastEvent.getText();
                    boolean bothNonEmpty =
                            (TextUtils.isEmpty(lastText) || TextUtils.isEmpty(text)) ? false : true;
                    boolean equalContent =
                            TextUtils.equals(lastText, text)
                                    && lastEvent.hasSameComposingSpan(event)
                                    && lastEvent.hasSameSelectionSpan(event);
                    if (equalContent) {
                        addEvent = false;
                    } else if (bothNonEmpty) {
                        lastEvent.mergeEvent(event);
                        addEvent = false;
                    }
                    if (!addEvent && ContentCaptureHelper.sVerbose) {
                        Log.v(
                                TAG,
                                "Buffering VIEW_TEXT_CHANGED event, updated text="
                                        + ContentCaptureHelper.getSanitizedString(text));
                    }
                }
            }
        }
        if (!this.mEvents.isEmpty() && eventType == 2) {
            ContentCaptureEvent lastEvent2 = this.mEvents.get(this.mEvents.size() - 1);
            if (lastEvent2.getType() == 2 && event.getSessionId() == lastEvent2.getSessionId()) {
                if (ContentCaptureHelper.sVerbose) {
                    Log.v(
                            TAG,
                            "Buffering TYPE_VIEW_DISAPPEARED events for session "
                                    + lastEvent2.getSessionId());
                }
                lastEvent2.mergeEvent(event);
                addEvent = false;
            }
        }
        if (addEvent) {
            this.mEvents.add(event);
        }
        int numberEvents = this.mEvents.size();
        boolean bufferEvent = numberEvents < maxBufferSize;
        if (bufferEvent && !forceFlush) {
            if (eventType == 3) {
                this.mNextFlushForTextChanged = true;
                flushReason2 = 6;
            } else {
                if (this.mNextFlushForTextChanged) {
                    if (ContentCaptureHelper.sVerbose) {
                        Log.i(TAG, "Not scheduling flush because next flush is for text changed");
                        return;
                    }
                    return;
                }
                flushReason2 = 5;
            }
            scheduleFlush(flushReason2, true);
            return;
        }
        int flushReason3 = this.mState;
        if (flushReason3 != 2 && numberEvents >= maxBufferSize) {
            if (ContentCaptureHelper.sDebug) {
                Log.d(
                        TAG,
                        "Closing session for "
                                + getDebugState()
                                + " after "
                                + numberEvents
                                + " delayed events");
            }
            resetSession(132);
            return;
        }
        switch (eventType) {
            case -2:
                flushReason = 4;
                break;
            case -1:
                flushReason = 3;
                break;
            case 4:
                flushReason = 9;
                break;
            case 5:
                flushReason = 10;
                break;
            default:
                flushReason = forceFlush ? 8 : 1;
                break;
        }
        flush(flushReason);
    }

    private boolean hasStarted() {
        checkOnUiThread();
        return this.mState != 0;
    }

    private void scheduleFlush(final int reason, boolean checkExisting) {
        int flushFrequencyMs;
        checkOnUiThread();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "handleScheduleFlush("
                            + getDebugState(reason)
                            + ", checkExisting="
                            + checkExisting);
        }
        if (!hasStarted()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleScheduleFlush(): session not started yet");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            Log.e(
                    TAG,
                    "handleScheduleFlush("
                            + getDebugState(reason)
                            + "): should not be called when disabled. events="
                            + (this.mEvents == null ? null : Integer.valueOf(this.mEvents.size())));
            return;
        }
        if (checkExisting && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        if (reason == 6) {
            flushFrequencyMs = this.mManager.mOptions.textChangeFlushingFrequencyMs;
        } else {
            if (reason != 5 && ContentCaptureHelper.sDebug) {
                Log.d(
                        TAG,
                        "handleScheduleFlush("
                                + getDebugState(reason)
                                + "): not a timeout reason because mDirectServiceInterface is not"
                                + " ready yet");
            }
            flushFrequencyMs = this.mManager.mOptions.idleFlushingFrequencyMs;
        }
        this.mNextFlush = System.currentTimeMillis() + flushFrequencyMs;
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "handleScheduleFlush(): scheduled to flush in "
                            + flushFrequencyMs
                            + "ms: "
                            + TimeUtils.logTimeOfDay(this.mNextFlush));
        }
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$scheduleFlush$2(reason);
                    }
                },
                1,
                flushFrequencyMs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: flushIfNeeded, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleFlush$2(int reason) {
        checkOnUiThread();
        if (this.mEvents == null || this.mEvents.isEmpty()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "Nothing to flush");
                return;
            }
            return;
        }
        flush(reason);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void flush(int reason) {
        checkOnUiThread();
        if (this.mEvents == null || this.mEvents.size() == 0) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "Don't flush for empty event buffer.");
                return;
            }
            return;
        }
        if (this.mDisabled.get()) {
            Log.e(
                    TAG,
                    "handleForceFlush(" + getDebugState(reason) + "): should not be when disabled");
            return;
        }
        if (!isContentCaptureReceiverEnabled()) {
            return;
        }
        if (this.mDirectServiceInterface == null) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(
                        TAG,
                        "handleForceFlush("
                                + getDebugState(reason)
                                + "): hold your horses, client not ready: "
                                + this.mEvents);
            }
            if (!this.mHandler.hasMessages(1)) {
                scheduleFlush(reason, false);
                return;
            }
            return;
        }
        this.mNextFlushForTextChanged = false;
        int numberEvents = this.mEvents.size();
        String reasonString = getFlushReasonAsString(reason);
        if (ContentCaptureHelper.sVerbose) {
            ContentCaptureEvent event = this.mEvents.get(numberEvents - 1);
            String forceString =
                    reason == 8
                            ? ". The force flush event "
                                    + ContentCaptureEvent.getTypeAsString(event.getType())
                            : "";
            Log.v(
                    TAG,
                    "Flushing "
                            + numberEvents
                            + " event(s) for "
                            + getDebugState(reason)
                            + forceString);
        }
        if (this.mFlushHistory != null) {
            String logRecord =
                    "r="
                            + reasonString
                            + " s="
                            + numberEvents
                            + " m="
                            + this.mManager.mOptions.maxBufferSize
                            + " i="
                            + this.mManager.mOptions.idleFlushingFrequencyMs;
            this.mFlushHistory.log(logRecord);
        }
        try {
            this.mHandler.removeMessages(1);
            ParceledListSlice<ContentCaptureEvent> events = clearEvents();
            this.mDirectServiceInterface.sendEvents(events, reason, this.mManager.mOptions);
        } catch (RemoteException e) {
            Log.w(TAG, "Error sending " + numberEvents + " for " + getDebugState() + ": " + e);
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext context) {
        internalNotifyContextUpdated(this.mId, context);
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        checkOnUiThread();
        if (this.mEvents == null) {
            return new ParceledListSlice<>(Collections.EMPTY_LIST);
        }
        List<ContentCaptureEvent> events = new ArrayList<>(this.mEvents);
        this.mEvents.clear();
        return new ParceledListSlice<>(events);
    }

    public void destroySession() {
        checkOnUiThread();
        if (ContentCaptureHelper.sDebug) {
            Log.d(
                    TAG,
                    "Destroying session (ctx="
                            + this.mContext
                            + ", id="
                            + this.mId
                            + ") with "
                            + (this.mEvents == null ? 0 : this.mEvents.size())
                            + " event(s) for "
                            + getDebugState());
        }
        reportWrongThreadMetric();
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Error destroying system-service session "
                            + this.mId
                            + " for "
                            + getDebugState()
                            + ": "
                            + e);
        }
        if (this.mDirectServiceInterface != null) {
            this.mDirectServiceInterface.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
    }

    public void resetSession(int newState) {
        checkOnUiThread();
        if (ContentCaptureHelper.sVerbose) {
            Log.v(
                    TAG,
                    "handleResetSession("
                            + getActivityName()
                            + "): from "
                            + getStateAsString(this.mState)
                            + " to "
                            + getStateAsString(newState));
        }
        this.mState = newState;
        this.mDisabled.set((newState & 4) != 0);
        this.mApplicationToken = null;
        this.mShareableActivityToken = null;
        this.mComponentName = null;
        this.mEvents = null;
        if (this.mDirectServiceInterface != null) {
            try {
                this.mDirectServiceInterface
                        .asBinder()
                        .unlinkToDeath(this.mDirectServiceVulture, 0);
            } catch (NoSuchElementException e) {
                Log.w(TAG, "IContentCaptureDirectManager does not exist");
            }
        }
        this.mDirectServiceInterface = null;
        this.mContentProtectionEventProcessor = null;
        this.mHandler.removeMessages(1);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int sessionId, ViewNode.ViewStructureImpl node) {
        final ContentCaptureEvent event =
                new ContentCaptureEvent(sessionId, 1).setViewNode(node.mNode);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyViewAppeared$3(event);
                    }
                });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int sessionId, AutofillId id) {
        final ContentCaptureEvent event = new ContentCaptureEvent(sessionId, 2).setAutofillId(id);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyViewDisappeared$4(
                                event);
                    }
                });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int sessionId, AutofillId id, CharSequence text) {
        CharSequence eventText;
        int composingStart;
        int composingEnd;
        CharSequence trimmed = TextUtils.trimToParcelableSize(text);
        if (trimmed != null && trimmed == text) {
            eventText = trimmed.toString();
        } else {
            eventText = trimmed;
        }
        if (text instanceof Spannable) {
            composingStart = BaseInputConnection.getComposingSpanStart((Spannable) text);
            composingEnd = BaseInputConnection.getComposingSpanEnd((Spannable) text);
        } else {
            composingStart = -1;
            composingEnd = -1;
        }
        int startIndex = Selection.getSelectionStart(text);
        int endIndex = Selection.getSelectionEnd(text);
        final ContentCaptureEvent event =
                new ContentCaptureEvent(sessionId, 3)
                        .setAutofillId(id)
                        .setText(eventText)
                        .setComposingIndex(composingStart, composingEnd)
                        .setSelectionIndex(startIndex, endIndex);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyViewTextChanged$5(
                                event);
                    }
                });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int sessionId, Insets viewInsets) {
        final ContentCaptureEvent event =
                new ContentCaptureEvent(sessionId, 9).setInsets(viewInsets);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyViewInsetsChanged$6(
                                event);
                    }
                });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int sessionId, boolean started) {
        int type = started ? 4 : 5;
        boolean disableFlush = this.mManager.getFlushViewTreeAppearingEventDisabled();
        if (disableFlush && started) {}
        final ContentCaptureEvent event = new ContentCaptureEvent(sessionId, type);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyViewTreeEvent$7(event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyViewTreeEvent$7(ContentCaptureEvent event) {
        sendEvent(event, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionResumed() {
        final ContentCaptureEvent event = new ContentCaptureEvent(this.mId, 7);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifySessionResumed$8(event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifySessionResumed$8(ContentCaptureEvent event) {
        sendEvent(event, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifySessionPaused() {
        final ContentCaptureEvent event = new ContentCaptureEvent(this.mId, 8);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifySessionPaused$9(event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifySessionPaused$9(ContentCaptureEvent event) {
        sendEvent(event, true);
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
    boolean setDisabled(boolean disabled) {
        return this.mDisabled.compareAndSet(!disabled, disabled);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(
            int parentSessionId, int childSessionId, ContentCaptureContext clientContext) {
        final ContentCaptureEvent event =
                new ContentCaptureEvent(childSessionId, -1)
                        .setParentSessionId(parentSessionId)
                        .setClientContext(clientContext);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyChildSessionStarted$10(
                                event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyChildSessionStarted$10(
            ContentCaptureEvent event) {
        sendEvent(event, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int parentSessionId, int childSessionId) {
        final ContentCaptureEvent event =
                new ContentCaptureEvent(childSessionId, -2).setParentSessionId(parentSessionId);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyChildSessionFinished$11(
                                event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyChildSessionFinished$11(
            ContentCaptureEvent event) {
        sendEvent(event, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int sessionId, ContentCaptureContext context) {
        final ContentCaptureEvent event =
                new ContentCaptureEvent(sessionId, 6).setClientContext(context);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$internalNotifyContextUpdated$12(
                                event);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$internalNotifyContextUpdated$12(ContentCaptureEvent event) {
        sendEvent(event, true);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int sessionId, Rect bounds) {
        final ContentCaptureEvent event = new ContentCaptureEvent(sessionId, 10).setBounds(bounds);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.view.contentcapture.MainContentCaptureSession$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainContentCaptureSession.this.lambda$notifyWindowBoundsChanged$13(event);
                    }
                });
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(SparseArray<ArrayList<Object>> contentCaptureEvents) {
        notifyContentCaptureEventsImpl(contentCaptureEvents);
    }

    private void notifyContentCaptureEventsImpl(
            SparseArray<ArrayList<Object>> contentCaptureEvents) {
        checkOnUiThread();
        try {
            if (Trace.isTagEnabled(8L)) {
                Trace.traceBegin(8L, "notifyContentCaptureEvents");
            }
            for (int i = 0; i < contentCaptureEvents.size(); i++) {
                int sessionId = contentCaptureEvents.keyAt(i);
                internalNotifyViewTreeEvent(sessionId, true);
                ArrayList<Object> events = contentCaptureEvents.valueAt(i);
                for (int j = 0; j < events.size(); j++) {
                    Object event = events.get(j);
                    if (event instanceof AutofillId) {
                        internalNotifyViewDisappeared(sessionId, (AutofillId) event);
                    } else if (event instanceof View) {
                        View view = (View) event;
                        ContentCaptureSession session = view.getContentCaptureSession();
                        if (session == null) {
                            Log.w(TAG, "no content capture session on view: " + view);
                        } else {
                            int actualId = session.getId();
                            if (actualId != sessionId) {
                                Log.w(
                                        TAG,
                                        "content capture session mismatch for view ("
                                                + view
                                                + "): was "
                                                + sessionId
                                                + " before, it's "
                                                + actualId
                                                + " now");
                            } else {
                                ViewStructure structure = session.newViewStructure(view);
                                view.onProvideContentCaptureStructure(structure, 0);
                                session.notifyViewAppeared(structure);
                            }
                        }
                    } else if (event instanceof Insets) {
                        internalNotifyViewInsetsChanged(sessionId, (Insets) event);
                    } else {
                        Log.w(TAG, "invalid content capture event: " + event);
                    }
                }
                internalNotifyViewTreeEvent(sessionId, false);
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void dump(String prefix, PrintWriter pw) {
        super.dump(prefix, pw);
        pw.print(prefix);
        pw.print("mContext: ");
        pw.println(this.mContext);
        pw.print(prefix);
        pw.print("user: ");
        pw.println(this.mContext.getUserId());
        if (this.mDirectServiceInterface != null) {
            pw.print(prefix);
            pw.print("mDirectServiceInterface: ");
            pw.println(this.mDirectServiceInterface);
        }
        pw.print(prefix);
        pw.print("mDisabled: ");
        pw.println(this.mDisabled.get());
        pw.print(prefix);
        pw.print("isEnabled(): ");
        pw.println(isContentCaptureEnabled());
        pw.print(prefix);
        pw.print("state: ");
        pw.println(getStateAsString(this.mState));
        if (this.mApplicationToken != null) {
            pw.print(prefix);
            pw.print("app token: ");
            pw.println(this.mApplicationToken);
        }
        if (this.mShareableActivityToken != null) {
            pw.print(prefix);
            pw.print("sharable activity token: ");
            pw.println(this.mShareableActivityToken);
        }
        if (this.mComponentName != null) {
            pw.print(prefix);
            pw.print("component name: ");
            pw.println(this.mComponentName.flattenToShortString());
        }
        if (this.mEvents != null && !this.mEvents.isEmpty()) {
            int numberEvents = this.mEvents.size();
            pw.print(prefix);
            pw.print("buffered events: ");
            pw.print(numberEvents);
            pw.print('/');
            pw.println(this.mManager.mOptions.maxBufferSize);
            if (ContentCaptureHelper.sVerbose && numberEvents > 0) {
                String prefix3 = prefix + "  ";
                for (int i = 0; i < numberEvents; i++) {
                    ContentCaptureEvent event = this.mEvents.get(i);
                    pw.print(prefix3);
                    pw.print(i);
                    pw.print(": ");
                    event.dump(pw);
                    pw.println();
                }
            }
            pw.print(prefix);
            pw.print("mNextFlushForTextChanged: ");
            pw.println(this.mNextFlushForTextChanged);
            pw.print(prefix);
            pw.print("flush frequency: ");
            if (this.mNextFlushForTextChanged) {
                pw.println(this.mManager.mOptions.textChangeFlushingFrequencyMs);
            } else {
                pw.println(this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            pw.print(prefix);
            pw.print("next flush: ");
            TimeUtils.formatDuration(this.mNextFlush - System.currentTimeMillis(), pw);
            pw.print(" (");
            pw.print(TimeUtils.logTimeOfDay(this.mNextFlush));
            pw.println(NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mFlushHistory != null) {
            pw.print(prefix);
            pw.println("flush history:");
            this.mFlushHistory.reverseDump(null, pw, null);
            pw.println();
        } else {
            pw.print(prefix);
            pw.println("not logging flush history");
        }
        super.dump(prefix, pw);
    }

    private String getActivityName() {
        if (this.mComponentName == null) {
            return "pkg:" + this.mContext.getPackageName();
        }
        return "act:" + this.mComponentName.flattenToShortString();
    }

    private String getDebugState() {
        return getActivityName()
                + " [state="
                + getStateAsString(this.mState)
                + ", disabled="
                + this.mDisabled.get()
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private String getDebugState(int reason) {
        return getDebugState() + ", reason=" + getFlushReasonAsString(reason);
    }

    private boolean isContentProtectionReceiverEnabled() {
        return this.mManager.mOptions.contentProtectionOptions.enableReceiver;
    }

    private boolean isContentCaptureReceiverEnabled() {
        return this.mManager.mOptions.enableReceiver;
    }

    private boolean isContentProtectionEnabled() {
        return (!this.mManager.mOptions.contentProtectionOptions.enableReceiver
                        || this.mManager.getContentProtectionEventBuffer() == null
                        || this.mComponentName == null
                        || (this.mManager.mOptions.contentProtectionOptions.requiredGroups.isEmpty()
                                && this.mManager.mOptions.contentProtectionOptions.optionalGroups
                                        .isEmpty()))
                ? false
                : true;
    }

    private void checkOnUiThread() {
        boolean onUiThread = this.mHandler.getLooper().isCurrentThread();
        if (!onUiThread) {
            this.mWrongThreadCount.incrementAndGet();
            Log.e(TAG, "MainContentCaptureSession running on " + Thread.currentThread());
        }
    }

    private void reportWrongThreadMetric() {
        Counter.logIncrement(
                CONTENT_CAPTURE_WRONG_THREAD_METRIC_ID, this.mWrongThreadCount.getAndSet(0));
    }
}
