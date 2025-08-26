package com.android.internal.util;

import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.audio.Enums;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.util.ScreenshotRequest;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ScreenshotHelper {
    public static final int SCREENSHOT_MSG_PROCESS_COMPLETE = 2;
    public static final int SCREENSHOT_MSG_URI = 1;
    private static final String TAG = "ScreenshotHelper";
    private final Context mContext;
    private final int SCREENSHOT_TIMEOUT_MS = 10000;
    private final Object mScreenshotLock = new Object();
    private IBinder mScreenshotService = null;
    private ServiceConnection mScreenshotConnection = null;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.internal.util.ScreenshotHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (ScreenshotHelper.this.mScreenshotLock) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    ScreenshotHelper.this.resetConnection();
                }
            }
        }
    };

    public ScreenshotHelper(Context context) {
        this.mContext = context;
    }

    public void takeScreenshot(int i, Handler handler, Consumer<Uri> consumer) {
        takeScreenshot(new ScreenshotRequest.Builder(1, i).build(), handler, consumer);
    }

    public void takeScreenshot(ScreenshotRequest screenshotRequest, Handler handler, Consumer<Uri> consumer) {
        takeScreenshotInternal(screenshotRequest, handler, consumer, JobInfo.MIN_BACKOFF_MILLIS);
    }

    public void takeScreenshotInternal(ScreenshotRequest screenshotRequest, final Handler handler, final Consumer<Uri> consumer, long j) {
        synchronized (this.mScreenshotLock) {
            this.mContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"), 2);
            final Runnable runnable = new Runnable() { // from class: com.android.internal.util.ScreenshotHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$takeScreenshotInternal$0(consumer);
                }
            };
            final Message messageObtain = Message.obtain(null, 0, screenshotRequest);
            messageObtain.replyTo = new Messenger(new Handler(handler.getLooper()) { // from class: com.android.internal.util.ScreenshotHelper.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        Consumer consumer2 = consumer;
                        if (consumer2 != null) {
                            consumer2.accept((Uri) message.obj);
                        }
                        handler.removeCallbacks(runnable);
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    synchronized (ScreenshotHelper.this.mScreenshotLock) {
                        ScreenshotHelper.this.resetConnection();
                    }
                }
            });
            ServiceConnection serviceConnection = this.mScreenshotConnection;
            if (serviceConnection == null || this.mScreenshotService == null) {
                if (serviceConnection != null) {
                    resetConnection();
                }
                ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotServiceComponent));
                Intent intent = new Intent();
                intent.setComponent(componentNameUnflattenFromString);
                ServiceConnection serviceConnection2 = new ServiceConnection() { // from class: com.android.internal.util.ScreenshotHelper.3
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        synchronized (ScreenshotHelper.this.mScreenshotLock) {
                            if (ScreenshotHelper.this.mScreenshotConnection != this) {
                                return;
                            }
                            ScreenshotHelper.this.mScreenshotService = iBinder;
                            try {
                                new Messenger(ScreenshotHelper.this.mScreenshotService).send(messageObtain);
                            } catch (RemoteException e) {
                                Log.e(ScreenshotHelper.TAG, "Couldn't take screenshot: " + e);
                                Consumer consumer2 = consumer;
                                if (consumer2 != null) {
                                    consumer2.accept(null);
                                }
                            }
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName componentName) {
                        synchronized (ScreenshotHelper.this.mScreenshotLock) {
                            if (ScreenshotHelper.this.mScreenshotConnection != null) {
                                ScreenshotHelper.this.resetConnection();
                                if (handler.hasCallbacks(runnable)) {
                                    Log.e(ScreenshotHelper.TAG, "Screenshot service disconnected");
                                    handler.removeCallbacks(runnable);
                                    ScreenshotHelper.this.notifyScreenshotError();
                                }
                            }
                        }
                    }
                };
                if (this.mContext.bindServiceAsUser(intent, serviceConnection2, Enums.AUDIO_FORMAT_AAC_MAIN, UserHandle.CURRENT)) {
                    this.mScreenshotConnection = serviceConnection2;
                    handler.postDelayed(runnable, j);
                } else {
                    this.mContext.unbindService(serviceConnection2);
                }
            } else {
                try {
                    new Messenger(this.mScreenshotService).send(messageObtain);
                } catch (RemoteException e) {
                    Log.e(TAG, "Couldn't take screenshot: " + e);
                    if (consumer != null) {
                        consumer.accept(null);
                    }
                }
                handler.postDelayed(runnable, j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotInternal$0(Consumer consumer) {
        synchronized (this.mScreenshotLock) {
            if (this.mScreenshotConnection != null) {
                Log.e(TAG, "Timed out before getting screenshot capture response");
                resetConnection();
                notifyScreenshotError();
            }
        }
        if (consumer != null) {
            consumer.accept(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConnection() {
        ServiceConnection serviceConnection = this.mScreenshotConnection;
        if (serviceConnection != null) {
            this.mContext.unbindService(serviceConnection);
            this.mScreenshotConnection = null;
            this.mScreenshotService = null;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "Attempted to remove broadcast receiver twice");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenshotError() {
        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(this.mContext.getResources().getString(R.string.config_screenshotErrorReceiverComponent));
        Intent intent = new Intent(Intent.ACTION_USER_PRESENT);
        intent.setComponent(componentNameUnflattenFromString);
        intent.addFlags(335544320);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }
}
