package com.samsung.android.media.codec.client;

import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.media.codec.IVideoTranscodingService;
import com.samsung.android.media.codec.SemVideoTranscodingService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public abstract class ClientImpl {
    protected static final String TAG = "SemVideoTranscodingService";
    protected Map mArgs;
    protected final String mID;
    protected final int mMode;
    protected final SemVideoTranscodingService.ProgressCallback mProgressCallback;
    protected final IVideoTranscodingService mTranscodingService;
    protected FileInputStream mFis = null;
    protected FileOutputStream mFos = null;
    protected boolean mIsRunning = false;
    protected boolean mIgnoreError = false;

    public abstract void stop();

    public abstract void transcode();

    public ClientImpl(
            IVideoTranscodingService transcodingService,
            String id,
            int mode,
            Map args,
            SemVideoTranscodingService.ProgressCallback progressCallback) {
        this.mTranscodingService = transcodingService;
        this.mID = id;
        this.mMode = mode;
        this.mArgs = args;
        this.mProgressCallback = progressCallback;
    }

    public void start() {
        try {
            this.mTranscodingService.startTask(this.mID);
        } catch (RemoteException e) {
            Log.e("SemVideoTranscodingService", "Exception startTask()");
            e.printStackTrace();
        }
    }
}
