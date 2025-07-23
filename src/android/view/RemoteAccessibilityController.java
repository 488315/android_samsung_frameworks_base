package android.view;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.RemoteAccessibilityController;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
class RemoteAccessibilityController {
    private static final String TAG = "RemoteAccessibilityController";
    private RemoteAccessibilityEmbeddedConnection mConnectionWrapper;
    private int mHostId;
    private View mHostView;
    private Matrix mWindowMatrixForEmbeddedHierarchy = new Matrix();
    private final float[] mMatrixValues = new float[9];

    RemoteAccessibilityController(View view) {
        this.mHostView = view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnUiThread(Runnable runnable) {
        Handler handler = this.mHostView.getHandler();
        if (handler != null && handler.getLooper() != Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    void assosciateHierarchy(IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, IBinder iBinder, int i) {
        this.mHostId = i;
        try {
            setRemoteAccessibilityEmbeddedConnection(iAccessibilityEmbeddedConnection, iAccessibilityEmbeddedConnection.associateEmbeddedHierarchy(iBinder, i));
        } catch (RemoteException e) {
            Log.d(TAG, "Error in associateEmbeddedHierarchy " + e);
        }
    }

    void disassosciateHierarchy() {
        setRemoteAccessibilityEmbeddedConnection(null, null);
    }

    boolean alreadyAssociated(IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection) {
        RemoteAccessibilityEmbeddedConnection remoteAccessibilityEmbeddedConnection = this.mConnectionWrapper;
        if (remoteAccessibilityEmbeddedConnection == null) {
            return false;
        }
        return remoteAccessibilityEmbeddedConnection.mConnection.equals(iAccessibilityEmbeddedConnection);
    }

    boolean connected() {
        return this.mConnectionWrapper != null;
    }

    IBinder getLeashToken() {
        return this.mConnectionWrapper.getLeashToken();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RemoteAccessibilityEmbeddedConnection implements IBinder.DeathRecipient {
        private final IAccessibilityEmbeddedConnection mConnection;
        private final WeakReference<RemoteAccessibilityController> mController;
        private final IBinder mLeashToken;

        RemoteAccessibilityEmbeddedConnection(RemoteAccessibilityController remoteAccessibilityController, IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, IBinder iBinder) {
            this.mController = new WeakReference<>(remoteAccessibilityController);
            this.mConnection = iAccessibilityEmbeddedConnection;
            this.mLeashToken = iBinder;
        }

        IAccessibilityEmbeddedConnection getConnection() {
            return this.mConnection;
        }

        IBinder getLeashToken() {
            return this.mLeashToken;
        }

        void linkToDeath() throws RemoteException {
            this.mConnection.asBinder().linkToDeath(this, 0);
        }

        void unlinkToDeath() {
            this.mConnection.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            unlinkToDeath();
            final RemoteAccessibilityController remoteAccessibilityController = this.mController.get();
            if (remoteAccessibilityController == null) {
                return;
            }
            remoteAccessibilityController.runOnUiThread(new Runnable() { // from class: android.view.RemoteAccessibilityController$RemoteAccessibilityEmbeddedConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection.this.lambda$binderDied$0(remoteAccessibilityController);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0(RemoteAccessibilityController remoteAccessibilityController) {
            if (remoteAccessibilityController.mConnectionWrapper == this) {
                remoteAccessibilityController.mConnectionWrapper = null;
            }
        }
    }

    private void setRemoteAccessibilityEmbeddedConnection(IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, IBinder iBinder) {
        try {
            RemoteAccessibilityEmbeddedConnection remoteAccessibilityEmbeddedConnection = this.mConnectionWrapper;
            if (remoteAccessibilityEmbeddedConnection != null) {
                remoteAccessibilityEmbeddedConnection.getConnection().disassociateEmbeddedHierarchy();
                this.mConnectionWrapper.unlinkToDeath();
                this.mConnectionWrapper = null;
            }
            if (iAccessibilityEmbeddedConnection == null || iBinder == null) {
                return;
            }
            RemoteAccessibilityEmbeddedConnection remoteAccessibilityEmbeddedConnection2 = new RemoteAccessibilityEmbeddedConnection(this, iAccessibilityEmbeddedConnection, iBinder);
            this.mConnectionWrapper = remoteAccessibilityEmbeddedConnection2;
            remoteAccessibilityEmbeddedConnection2.linkToDeath();
        } catch (RemoteException e) {
            Log.d(TAG, "Error while setRemoteEmbeddedConnection " + e);
        }
    }

    private RemoteAccessibilityEmbeddedConnection getRemoteAccessibilityEmbeddedConnection() {
        return this.mConnectionWrapper;
    }

    void setWindowMatrix(Matrix matrix, boolean z) {
        if (z || !matrix.equals(this.mWindowMatrixForEmbeddedHierarchy)) {
            try {
                RemoteAccessibilityEmbeddedConnection remoteAccessibilityEmbeddedConnection = getRemoteAccessibilityEmbeddedConnection();
                if (remoteAccessibilityEmbeddedConnection == null) {
                    return;
                }
                matrix.getValues(this.mMatrixValues);
                remoteAccessibilityEmbeddedConnection.getConnection().setWindowMatrix(this.mMatrixValues);
                this.mWindowMatrixForEmbeddedHierarchy.set(matrix);
            } catch (RemoteException e) {
                Log.d(TAG, "Error while setScreenMatrix " + e);
            }
        }
    }
}
