package android.telecom;

import android.os.RemoteException;
import com.android.internal.telecom.IStreamingCallAdapter;

/* loaded from: classes4.dex */
public final class StreamingCallAdapter {
    private final IStreamingCallAdapter mAdapter;

    public StreamingCallAdapter(IStreamingCallAdapter iStreamingCallAdapter) {
        this.mAdapter = iStreamingCallAdapter;
    }

    public void setStreamingState(int i) {
        try {
            this.mAdapter.setStreamingState(i);
        } catch (RemoteException unused) {
        }
    }
}
