package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRotationWatcher;
import android.view.WindowManagerGlobal;
import com.android.systemui.keyguard.SecurityLog;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecRotationWatcher {
    public final Context mContext;
    public int mCurrentRotation;
    public final ArrayList mListeners = new ArrayList();
    public final C08431 mWatcher = new IRotationWatcher.Stub() { // from class: com.android.keyguard.SecRotationWatcher.1
        public final void onRotationChanged(int i) {
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = Integer.valueOf(i);
            removeMessages(0);
            sendMessage(obtain);
        }
    };
    public final HandlerC08442 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.keyguard.SecRotationWatcher.2
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final int intValue = ((Integer) message.obj).intValue();
            SecRotationWatcher secRotationWatcher = SecRotationWatcher.this;
            secRotationWatcher.mCurrentRotation = intValue;
            secRotationWatcher.mListeners.forEach(new Consumer() { // from class: com.android.keyguard.SecRotationWatcher$2$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((IntConsumer) obj).accept(intValue);
                }
            });
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.SecRotationWatcher$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.keyguard.SecRotationWatcher$2] */
    public SecRotationWatcher(Context context) {
        this.mContext = context;
    }

    public final void addCallback(IntConsumer intConsumer) {
        ArrayList arrayList = this.mListeners;
        if (arrayList.contains(intConsumer)) {
            return;
        }
        boolean isEmpty = arrayList.isEmpty();
        arrayList.add(intConsumer);
        intConsumer.accept(this.mCurrentRotation);
        if (isEmpty) {
            try {
                SecurityLog.m143d("SecRotationWatcher", "enable watchRotation");
                WindowManagerGlobal.getWindowManagerService().watchRotation(this.mWatcher, this.mContext.getDisplayId());
            } catch (RemoteException e) {
                Log.w("SecRotationWatcher", "Failed to set rotation watcher", e);
            }
        }
    }

    public final void removeCallback(IntConsumer intConsumer) {
        ArrayList arrayList = this.mListeners;
        arrayList.remove(intConsumer);
        if (arrayList.isEmpty()) {
            try {
                SecurityLog.m143d("SecRotationWatcher", "disable watchRotation");
                WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this.mWatcher);
            } catch (RemoteException e) {
                Log.w("SecRotationWatcher", "Failed to remove rotation watcher", e);
            }
        }
    }
}
