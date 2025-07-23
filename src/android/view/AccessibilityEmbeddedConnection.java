package android.view;

import android.graphics.Matrix;
import android.os.IBinder;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
final class AccessibilityEmbeddedConnection extends IAccessibilityEmbeddedConnection.Stub {
    private final Matrix mTmpWindowMatrix = new Matrix();
    private final WeakReference<ViewRootImpl> mViewRootImpl;

    AccessibilityEmbeddedConnection(ViewRootImpl viewRootImpl) {
        this.mViewRootImpl = new WeakReference<>(viewRootImpl);
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public IBinder associateEmbeddedHierarchy(IBinder iBinder, int i) {
        ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl == null) {
            return null;
        }
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(viewRootImpl.mContext);
        viewRootImpl.mAttachInfo.mLeashedParentToken = iBinder;
        viewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId = i;
        if (accessibilityManager.isEnabled()) {
            accessibilityManager.associateEmbeddedHierarchy(iBinder, viewRootImpl.mLeashToken);
        }
        return viewRootImpl.mLeashToken;
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public void disassociateEmbeddedHierarchy() {
        ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl != null) {
            AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(viewRootImpl.mContext);
            viewRootImpl.mAttachInfo.mLeashedParentToken = null;
            viewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId = -1;
            if (accessibilityManager.isEnabled()) {
                accessibilityManager.disassociateEmbeddedHierarchy(viewRootImpl.mLeashToken);
            }
        }
    }

    @Override // android.view.accessibility.IAccessibilityEmbeddedConnection
    public void setWindowMatrix(float[] fArr) {
        ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
        if (viewRootImpl != null) {
            this.mTmpWindowMatrix.setValues(fArr);
            if (viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy == null) {
                viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy = new Matrix();
            }
            viewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy.set(this.mTmpWindowMatrix);
        }
    }
}
