package android.view.contentcapture;

import android.content.ComponentName;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ViewNode;
import java.util.ArrayList;

/* loaded from: classes4.dex */
final class ChildContentCaptureSession extends ContentCaptureSession {
    private final ContentCaptureSession mParent;

    protected ChildContentCaptureSession(ContentCaptureSession contentCaptureSession, ContentCaptureContext contentCaptureContext) {
        super(contentCaptureContext);
        this.mParent = contentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession getMainCaptureSession() {
        return this.mParent.getMainCaptureSession();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void start(IBinder iBinder, IBinder iBinder2, ComponentName componentName, int i) {
        getMainCaptureSession().start(iBinder, iBinder2, componentName, i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isDisabled() {
        return getMainCaptureSession().isDisabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean setDisabled(boolean z) {
        return getMainCaptureSession().setDisabled(z);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext contentCaptureContext) {
        ChildContentCaptureSession childContentCaptureSession = new ChildContentCaptureSession(this, contentCaptureContext);
        internalNotifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void flush(int i) {
        this.mParent.flush(i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext contentCaptureContext) {
        internalNotifyContextUpdated(this.mId, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        internalNotifyChildSessionFinished(this.mParent.mId, this.mId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionStarted(int i, int i2, ContentCaptureContext contentCaptureContext) {
        getMainCaptureSession().internalNotifyChildSessionStarted(i, i2, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyChildSessionFinished(int i, int i2) {
        getMainCaptureSession().internalNotifyChildSessionFinished(i, i2);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyContextUpdated(int i, ContentCaptureContext contentCaptureContext) {
        getMainCaptureSession().internalNotifyContextUpdated(i, contentCaptureContext);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(int i, ViewNode.ViewStructureImpl viewStructureImpl) {
        getMainCaptureSession().internalNotifyViewAppeared(i, viewStructureImpl);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(int i, AutofillId autofillId) {
        getMainCaptureSession().internalNotifyViewDisappeared(i, autofillId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(int i, AutofillId autofillId, CharSequence charSequence) {
        getMainCaptureSession().internalNotifyViewTextChanged(i, autofillId, charSequence);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewInsetsChanged(int i, Insets insets) {
        getMainCaptureSession().internalNotifyViewInsetsChanged(this.mId, insets);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(int i, boolean z) {
        getMainCaptureSession().internalNotifyViewTreeEvent(i, z);
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
    void internalNotifySessionFlushEvent(int i) {
        getMainCaptureSession().internalNotifySessionFlushEvent(i);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return getMainCaptureSession().isContentCaptureEnabled();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyWindowBoundsChanged(int i, Rect rect) {
        getMainCaptureSession().notifyWindowBoundsChanged(i, rect);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void notifyContentCaptureEvents(SparseArray<ArrayList<Object>> sparseArray) {
        getMainCaptureSession().notifyContentCaptureEvents(sparseArray);
    }
}
