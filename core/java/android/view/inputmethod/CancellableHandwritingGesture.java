package android.view.inputmethod;

import android.p009os.CancellationSignal;
import android.p009os.CancellationSignalBeamer;
import android.p009os.IBinder;

/* loaded from: classes4.dex */
public abstract class CancellableHandwritingGesture extends HandwritingGesture {
    CancellationSignal mCancellationSignal;
    IBinder mCancellationSignalToken;

    public void setCancellationSignal(CancellationSignal cancellationSignal) {
        this.mCancellationSignal = cancellationSignal;
    }

    CancellationSignal getCancellationSignal() {
        return this.mCancellationSignal;
    }

    public void unbeamCancellationSignal(CancellationSignalBeamer.Receiver receiver) {
        IBinder iBinder = this.mCancellationSignalToken;
        if (iBinder != null) {
            this.mCancellationSignal = receiver.unbeam(iBinder);
            this.mCancellationSignalToken = null;
        }
    }
}
