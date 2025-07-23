package com.android.internal.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.IProgressListener;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.MathUtils;

/* loaded from: classes4.dex */
public class ProgressReporter {
    private static final int STATE_FINISHED = 2;
    private static final int STATE_INIT = 0;
    private static final int STATE_STARTED = 1;
    private final int mId;
    private final RemoteCallbackList<IProgressListener> mListeners = new RemoteCallbackList<>();
    private int mState = 0;
    private int mProgress = 0;
    private Bundle mExtras = new Bundle();
    private int[] mSegmentRange = {0, 100};

    public ProgressReporter(int i) {
        this.mId = i;
    }

    public void addListener(IProgressListener iProgressListener) {
        if (iProgressListener == null) {
            return;
        }
        synchronized (this) {
            this.mListeners.register(iProgressListener);
            int i = this.mState;
            if (i == 1) {
                iProgressListener.onStarted(this.mId, null);
                iProgressListener.onProgress(this.mId, this.mProgress, this.mExtras);
            } else if (i == 2) {
                iProgressListener.onFinished(this.mId, null);
            }
        }
    }

    public void setProgress(int i) {
        setProgress(i, 100, null);
    }

    public void setProgress(int i, CharSequence charSequence) {
        setProgress(i, 100, charSequence);
    }

    public void setProgress(int i, int i2) {
        setProgress(i, i2, null);
    }

    public void setProgress(int i, int i2, CharSequence charSequence) {
        synchronized (this) {
            if (this.mState != 1) {
                throw new IllegalStateException("Must be started to change progress");
            }
            int[] iArr = this.mSegmentRange;
            int i3 = iArr[0];
            int i4 = iArr[1];
            this.mProgress = i3 + MathUtils.constrain((i * i4) / i2, 0, i4);
            if (charSequence != null) {
                this.mExtras.putCharSequence(Intent.EXTRA_TITLE, charSequence);
            }
            notifyProgress(this.mId, this.mProgress, this.mExtras);
        }
    }

    public int[] startSegment(int i) {
        int[] iArr;
        synchronized (this) {
            iArr = this.mSegmentRange;
            this.mSegmentRange = new int[]{this.mProgress, (i * iArr[1]) / 100};
        }
        return iArr;
    }

    public void endSegment(int[] iArr) {
        synchronized (this) {
            int[] iArr2 = this.mSegmentRange;
            this.mProgress = iArr2[0] + iArr2[1];
            this.mSegmentRange = iArr;
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    int[] getSegmentRange() {
        return this.mSegmentRange;
    }

    public void start() {
        synchronized (this) {
            this.mState = 1;
            notifyStarted(this.mId, null);
            notifyProgress(this.mId, this.mProgress, this.mExtras);
        }
    }

    public void finish() {
        synchronized (this) {
            this.mState = 2;
            notifyFinished(this.mId, null);
            this.mListeners.kill();
        }
    }

    private void notifyStarted(int i, Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onStarted(i, bundle);
            } catch (RemoteException unused) {
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyProgress(int i, int i2, Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onProgress(i, i2, bundle);
            } catch (RemoteException unused) {
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyFinished(int i, Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onFinished(i, bundle);
            } catch (RemoteException unused) {
            }
        }
        this.mListeners.finishBroadcast();
    }
}
