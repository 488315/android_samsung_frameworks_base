package com.android.internal.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import java.util.Stack;

/* loaded from: classes4.dex */
public class AsyncChannel {
    private static final int BASE = 69632;
    public static final int CMD_CHANNEL_DISCONNECT = 69635;
    public static final int CMD_CHANNEL_DISCONNECTED = 69636;
    public static final int CMD_CHANNEL_FULLY_CONNECTED = 69634;
    public static final int CMD_CHANNEL_FULL_CONNECTION = 69633;
    public static final int CMD_CHANNEL_HALF_CONNECTED = 69632;
    private static final int CMD_TO_STRING_COUNT = 5;
    private static final boolean DBG = false;
    public static final int STATUS_BINDING_UNSUCCESSFUL = 1;
    public static final int STATUS_FULL_CONNECTION_REFUSED_ALREADY_CONNECTED = 3;
    public static final int STATUS_REMOTE_DISCONNECTION = 4;
    public static final int STATUS_SEND_UNSUCCESSFUL = 2;
    public static final int STATUS_SUCCESSFUL = 0;
    private static final String TAG = "AsyncChannel";
    private static String[] sCmdToString = {"CMD_CHANNEL_HALF_CONNECTED", "CMD_CHANNEL_FULL_CONNECTION", "CMD_CHANNEL_FULLY_CONNECTED", "CMD_CHANNEL_DISCONNECT", "CMD_CHANNEL_DISCONNECTED"};
    private AsyncChannelConnection mConnection;
    private DeathMonitor mDeathMonitor;
    private Messenger mDstMessenger;
    private Context mSrcContext;
    private Handler mSrcHandler;
    private Messenger mSrcMessenger;

    protected static String cmdToString(int i) {
        int i2 = i - 69632;
        if (i2 < 0) {
            return null;
        }
        String[] strArr = sCmdToString;
        if (i2 < strArr.length) {
            return strArr[i2];
        }
        return null;
    }

    public int connectSrcHandlerToPackageSync(Context context, Handler handler, String str, String str2) {
        this.mConnection = new AsyncChannelConnection();
        this.mSrcContext = context;
        this.mSrcHandler = handler;
        this.mSrcMessenger = new Messenger(handler);
        this.mDstMessenger = null;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(str, str2);
        return !context.bindService(intent, this.mConnection, 1) ? 1 : 0;
    }

    public int connectSync(Context context, Handler handler, Messenger messenger) {
        connected(context, handler, messenger);
        return 0;
    }

    public int connectSync(Context context, Handler handler, Handler handler2) {
        return connectSync(context, handler, new Messenger(handler2));
    }

    public int fullyConnectSync(Context context, Handler handler, Handler handler2) {
        int connectSync = connectSync(context, handler, handler2);
        return connectSync == 0 ? sendMessageSynchronously(CMD_CHANNEL_FULL_CONNECTION).arg1 : connectSync;
    }

    public void connect(Context context, Handler handler, String str, String str2) {
        new Thread(new Runnable(context, handler, str, str2) { // from class: com.android.internal.util.AsyncChannel.1ConnectAsync
            String mDstClassName;
            String mDstPackageName;
            Context mSrcCtx;
            Handler mSrcHdlr;

            {
                this.mSrcCtx = context;
                this.mSrcHdlr = handler;
                this.mDstPackageName = str;
                this.mDstClassName = str2;
            }

            @Override // java.lang.Runnable
            public void run() {
                AsyncChannel.this.replyHalfConnected(AsyncChannel.this.connectSrcHandlerToPackageSync(this.mSrcCtx, this.mSrcHdlr, this.mDstPackageName, this.mDstClassName));
            }
        }).start();
    }

    public void connect(Context context, Handler handler, Class<?> cls) {
        connect(context, handler, cls.getPackage().getName(), cls.getName());
    }

    public void connect(Context context, Handler handler, Messenger messenger) {
        connected(context, handler, messenger);
        replyHalfConnected(0);
    }

    public void connected(Context context, Handler handler, Messenger messenger) {
        this.mSrcContext = context;
        this.mSrcHandler = handler;
        this.mSrcMessenger = new Messenger(this.mSrcHandler);
        this.mDstMessenger = messenger;
    }

    public void connect(Context context, Handler handler, Handler handler2) {
        connect(context, handler, new Messenger(handler2));
    }

    public void connect(AsyncService asyncService, Messenger messenger) {
        connect(asyncService, asyncService.getHandler(), messenger);
    }

    public void disconnected() {
        this.mSrcContext = null;
        this.mSrcHandler = null;
        this.mSrcMessenger = null;
        this.mDstMessenger = null;
        this.mDeathMonitor = null;
        this.mConnection = null;
    }

    public void disconnect() {
        Messenger messenger;
        Context context;
        AsyncChannelConnection asyncChannelConnection = this.mConnection;
        if (asyncChannelConnection != null && (context = this.mSrcContext) != null) {
            context.unbindService(asyncChannelConnection);
            this.mConnection = null;
        }
        try {
            Message obtain = Message.obtain();
            obtain.what = CMD_CHANNEL_DISCONNECTED;
            obtain.replyTo = this.mSrcMessenger;
            this.mDstMessenger.send(obtain);
        } catch (Exception unused) {
        }
        replyDisconnected(0);
        this.mSrcHandler = null;
        if (this.mConnection != null || (messenger = this.mDstMessenger) == null || this.mDeathMonitor == null) {
            return;
        }
        messenger.getBinder().unlinkToDeath(this.mDeathMonitor, 0);
        this.mDeathMonitor = null;
    }

    public void sendMessage(Message message) {
        message.replyTo = this.mSrcMessenger;
        try {
            this.mDstMessenger.send(message);
        } catch (RemoteException unused) {
            replyDisconnected(2);
        }
    }

    public void sendMessage(int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2, int i3) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        sendMessage(obtain);
    }

    public void sendMessage(int i, int i2, int i3, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        sendMessage(obtain);
    }

    public void sendMessage(int i, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        sendMessage(obtain);
    }

    public void replyToMessage(Message message, Message message2) {
        try {
            message2.replyTo = this.mSrcMessenger;
            message.replyTo.send(message2);
        } catch (RemoteException e) {
            log("TODO: handle replyToMessage RemoteException" + e);
            e.printStackTrace();
        }
    }

    public void replyToMessage(Message message, int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(Message message, int i, int i2) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(Message message, int i, int i2, int i3) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(Message message, int i, int i2, int i3, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        replyToMessage(message, obtain);
    }

    public void replyToMessage(Message message, int i, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        replyToMessage(message, obtain);
    }

    public Message sendMessageSynchronously(Message message) {
        return SyncMessenger.sendMessageSynchronously(this.mDstMessenger, message);
    }

    public Message sendMessageSynchronously(int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        return sendMessageSynchronously(obtain);
    }

    public Message sendMessageSynchronously(int i, int i2) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        return sendMessageSynchronously(obtain);
    }

    public Message sendMessageSynchronously(int i, int i2, int i3) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        return sendMessageSynchronously(obtain);
    }

    public Message sendMessageSynchronously(int i, int i2, int i3, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        obtain.obj = obj;
        return sendMessageSynchronously(obtain);
    }

    public Message sendMessageSynchronously(int i, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        return sendMessageSynchronously(obtain);
    }

    private static class SyncMessenger {
        private SyncHandler mHandler;
        private HandlerThread mHandlerThread;
        private Messenger mMessenger;
        private static Stack<SyncMessenger> sStack = new Stack<>();
        private static int sCount = 0;

        private SyncMessenger() {
        }

        private class SyncHandler extends Handler {
            private Object mLockObject;
            private Message mResultMsg;

            private SyncHandler(SyncMessenger syncMessenger, Looper looper) {
                super(looper);
                this.mLockObject = new Object();
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Message obtain = Message.obtain();
                obtain.copyFrom(message);
                synchronized (this.mLockObject) {
                    this.mResultMsg = obtain;
                    this.mLockObject.notify();
                }
            }
        }

        private static SyncMessenger obtain() {
            SyncMessenger pop;
            synchronized (sStack) {
                if (sStack.isEmpty()) {
                    pop = new SyncMessenger();
                    StringBuilder sb = new StringBuilder("SyncHandler-");
                    int i = sCount;
                    sCount = i + 1;
                    sb.append(i);
                    HandlerThread handlerThread = new HandlerThread(sb.toString());
                    pop.mHandlerThread = handlerThread;
                    handlerThread.start();
                    pop.mHandler = new SyncHandler(pop.mHandlerThread.getLooper());
                    pop.mMessenger = new Messenger(pop.mHandler);
                } else {
                    pop = sStack.pop();
                }
            }
            return pop;
        }

        private void recycle() {
            synchronized (sStack) {
                sStack.push(this);
            }
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:22:0x0047
            	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
            */
        /* JADX INFO: Access modifiers changed from: private */
        public static android.os.Message sendMessageSynchronously(android.os.Messenger r5, android.os.Message r6) {
            /*
                com.android.internal.util.AsyncChannel$SyncMessenger r0 = obtain()
                r1 = 0
                if (r5 == 0) goto L5b
                if (r6 == 0) goto L5b
                android.os.Messenger r2 = r0.mMessenger     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                r6.replyTo = r2     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r2 = r0.mHandler     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                java.lang.Object r2 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8322$$Nest$fgetmLockObject(r2)     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                monitor-enter(r2)     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r3 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                android.os.Message r3 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8323$$Nest$fgetmResultMsg(r3)     // Catch: java.lang.Throwable -> L47
                if (r3 == 0) goto L29
                java.lang.String r3 = "AsyncChannel"
                java.lang.String r4 = "mResultMsg should be null here"
                android.util.Log.wtf(r3, r4)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r3 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8324$$Nest$fputmResultMsg(r3, r1)     // Catch: java.lang.Throwable -> L47
            L29:
                r5.send(r6)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r5 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                java.lang.Object r5 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8322$$Nest$fgetmLockObject(r5)     // Catch: java.lang.Throwable -> L47
                r5.wait()     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r5 = r0.mHandler     // Catch: java.lang.Throwable -> L47
                android.os.Message r5 = com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8323$$Nest$fgetmResultMsg(r5)     // Catch: java.lang.Throwable -> L47
                com.android.internal.util.AsyncChannel$SyncMessenger$SyncHandler r6 = r0.mHandler     // Catch: java.lang.Throwable -> L43
                com.android.internal.util.AsyncChannel.SyncMessenger.SyncHandler.m8324$$Nest$fputmResultMsg(r6, r1)     // Catch: java.lang.Throwable -> L43
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L43
                r1 = r5
                goto L5b
            L43:
                r6 = move-exception
                r1 = r5
                r5 = r6
                goto L48
            L47:
                r5 = move-exception
            L48:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L47
                throw r5     // Catch: android.os.RemoteException -> L4a java.lang.InterruptedException -> L53
            L4a:
                r5 = move-exception
                java.lang.String r6 = "AsyncChannel"
                java.lang.String r2 = "error in sendMessageSynchronously"
                android.util.Log.e(r6, r2, r5)
                goto L5b
            L53:
                r5 = move-exception
                java.lang.String r6 = "AsyncChannel"
                java.lang.String r2 = "error in sendMessageSynchronously"
                android.util.Log.e(r6, r2, r5)
            L5b:
                r0.recycle()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.AsyncChannel.SyncMessenger.sendMessageSynchronously(android.os.Messenger, android.os.Message):android.os.Message");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replyHalfConnected(int i) {
        Message obtainMessage = this.mSrcHandler.obtainMessage(69632);
        obtainMessage.arg1 = i;
        obtainMessage.obj = this;
        obtainMessage.replyTo = this.mDstMessenger;
        if (!linkToDeathMonitor()) {
            obtainMessage.arg1 = 1;
        }
        this.mSrcHandler.sendMessage(obtainMessage);
    }

    private boolean linkToDeathMonitor() {
        if (this.mConnection != null || this.mDeathMonitor != null) {
            return true;
        }
        this.mDeathMonitor = new DeathMonitor();
        try {
            this.mDstMessenger.getBinder().linkToDeath(this.mDeathMonitor, 0);
            return true;
        } catch (RemoteException unused) {
            this.mDeathMonitor = null;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replyDisconnected(int i) {
        Handler handler = this.mSrcHandler;
        if (handler == null) {
            return;
        }
        Message obtainMessage = handler.obtainMessage(CMD_CHANNEL_DISCONNECTED);
        obtainMessage.arg1 = i;
        obtainMessage.obj = this;
        obtainMessage.replyTo = this.mDstMessenger;
        this.mSrcHandler.sendMessage(obtainMessage);
    }

    class AsyncChannelConnection implements ServiceConnection {
        AsyncChannelConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AsyncChannel.this.mDstMessenger = new Messenger(iBinder);
            AsyncChannel.this.replyHalfConnected(0);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            AsyncChannel.this.replyDisconnected(0);
        }
    }

    private static void log(String str) {
        Log.d(TAG, str);
    }

    private final class DeathMonitor implements IBinder.DeathRecipient {
        DeathMonitor() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            AsyncChannel.this.replyDisconnected(4);
        }
    }
}
