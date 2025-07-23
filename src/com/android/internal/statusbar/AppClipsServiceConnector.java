package com.android.internal.statusbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audio.Enums;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.statusbar.IAppClipsService;
import java.util.concurrent.CompletableFuture;

/* loaded from: classes4.dex */
public class AppClipsServiceConnector {
    private static final String TAG = "AppClipsServiceConnector";
    private final Context mContext;
    private final Handler mHandler;

    public AppClipsServiceConnector(Context context) {
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
    }

    public boolean canLaunchCaptureContentActivityForNote(int i) {
        try {
            CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
            connectToServiceAndProcessRequest(i, completableFuture);
            return completableFuture.get().booleanValue();
        } catch (Exception e) {
            Log.d(TAG, "Exception from service\n" + e);
            return false;
        }
    }

    private void connectToServiceAndProcessRequest(final int i, final CompletableFuture<Boolean> completableFuture) {
        ServiceConnection serviceConnection = new ServiceConnection(this) { // from class: com.android.internal.statusbar.AppClipsServiceConnector.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    completableFuture.complete(Boolean.valueOf(IAppClipsService.Stub.asInterface(iBinder).canLaunchCaptureContentActivityForNote(i)));
                } catch (Exception e) {
                    Log.d(AppClipsServiceConnector.TAG, "Exception from service\n" + e);
                }
                completableFuture.complete(false);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                if (completableFuture.isDone()) {
                    return;
                }
                completableFuture.complete(false);
            }
        };
        ComponentName unflattenFromString = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotAppClipsServiceComponent));
        Intent intent = new Intent();
        intent.setComponent(unflattenFromString);
        Context context = this.mContext;
        if (context.bindServiceAsUser(intent, serviceConnection, Enums.AUDIO_FORMAT_AAC_MAIN, this.mHandler, context.getUser())) {
            return;
        }
        completableFuture.complete(false);
    }
}
