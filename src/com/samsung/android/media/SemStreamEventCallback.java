package com.samsung.android.media;

import android.media.AudioTrack;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemStreamEventCallback extends AudioTrack.StreamEventCallback {
    private static final String TAG = "SemStreamEventCallback";
    public long mAJavaAudioTrackPtr;
    public long mNativeCallbackPtr;
    public long mUserDataPtr;

    private static native void native_stream_event_onStreamDataRequest(long j, long j2, long j3, int i);

    private static native void native_stream_event_onStreamPresentationEnd(long j, long j2);

    private static native void native_stream_event_onTearDown(long j, long j2);

    static {
        System.loadLibrary("semstreameventcallback_jni");
    }

    SemStreamEventCallback(long j, long j2, long j3) {
        Log.i(TAG, TAG);
        this.mAJavaAudioTrackPtr = j;
        this.mNativeCallbackPtr = j2;
        this.mUserDataPtr = j3;
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onTearDown(AudioTrack audioTrack) {
        native_stream_event_onTearDown(this.mNativeCallbackPtr, this.mUserDataPtr);
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onPresentationEnded(AudioTrack audioTrack) {
        native_stream_event_onStreamPresentationEnd(this.mNativeCallbackPtr, this.mUserDataPtr);
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onDataRequest(AudioTrack audioTrack, int i) {
        native_stream_event_onStreamDataRequest(this.mAJavaAudioTrackPtr, this.mNativeCallbackPtr, this.mUserDataPtr, i);
    }
}
