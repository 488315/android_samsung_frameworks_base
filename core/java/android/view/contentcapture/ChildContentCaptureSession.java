package android.view.contentcapture;

import android.content.ComponentName;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.autofill.AutofillId;

import java.util.ArrayList;

/* loaded from: classes4.dex */
final class ChildContentCaptureSession extends ContentCaptureSession {
    private final ContentCaptureSession mParent;

    protected ChildContentCaptureSession(
            ContentCaptureSession parent, ContentCaptureContext clientContext) {
        super(clientContext);
        this.mParent = parent;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession getMainCaptureSession() {
        return this.mParent.getMainCaptureSession();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(IBinder token, IBinder shareableActivityToken, ComponentName component, int flags) {
        getMainCaptureSession().start(token, shareableActivityToken, component, flags);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isDisabled() {
        return getMainCaptureSession().isDisabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean setDisabled(boolean disabled) {
        return getMainCaptureSession().setDisabled(disabled);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext clientContext) {
        ContentCaptureSession child = new ChildContentCaptureSession(this, clientContext);
        internalNotifyChildSessionStarted(this.mId, child.mId, clientContext);
        return child;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void flush(int reason) {
        this.mParent.flush(reason);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext context) {
        internalNotifyContextUpdated(this.mId, context);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        internalNotifyChildSessionFinished(this.mParent.mId, this.mId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(
            int parentSessionId, int childSessionId, ContentCaptureContext clientContext) {
        getMainCaptureSession()
                .internalNotifyChildSessionStarted(parentSessionId, childSessionId, clientContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int parentSessionId, int childSessionId) {
        getMainCaptureSession().internalNotifyChildSessionFinished(parentSessionId, childSessionId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int sessionId, ContentCaptureContext context) {
        getMainCaptureSession().internalNotifyContextUpdated(sessionId, context);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int sessionId, ViewNode.ViewStructureImpl node) {
        getMainCaptureSession().internalNotifyViewAppeared(sessionId, node);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int sessionId, AutofillId id) {
        getMainCaptureSession().internalNotifyViewDisappeared(sessionId, id);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int sessionId, AutofillId id, CharSequence text) {
        getMainCaptureSession().internalNotifyViewTextChanged(sessionId, id, text);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int sessionId, Insets viewInsets) {
        getMainCaptureSession().internalNotifyViewInsetsChanged(this.mId, viewInsets);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int sessionId, boolean started) {
        getMainCaptureSession().internalNotifyViewTreeEvent(sessionId, started);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifySessionResumed() {
        getMainCaptureSession().internalNotifySessionResumed();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifySessionPaused() {
        getMainCaptureSession().internalNotifySessionPaused();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return getMainCaptureSession().isContentCaptureEnabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int sessionId, Rect bounds) {
        getMainCaptureSession().notifyWindowBoundsChanged(sessionId, bounds);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(SparseArray<ArrayList<Object>> contentCaptureEvents) {
        getMainCaptureSession().notifyContentCaptureEvents(contentCaptureEvents);
    }
}
