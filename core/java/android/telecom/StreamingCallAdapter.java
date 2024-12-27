package android.telecom;

import android.os.RemoteException;

import com.android.internal.telecom.IStreamingCallAdapter;

public final class StreamingCallAdapter {
    private final IStreamingCallAdapter mAdapter;

    public StreamingCallAdapter(IStreamingCallAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setStreamingState(int state) {
        try {
            this.mAdapter.setStreamingState(state);
        } catch (RemoteException e) {
        }
    }
}
