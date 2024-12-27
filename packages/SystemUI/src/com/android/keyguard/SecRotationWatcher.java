package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.IRotationWatcher;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecRotationWatcher {
    public final Context mContext;
    public int mCurrentRotation;
    public final ArrayList mListeners = new ArrayList();
    public final AnonymousClass1 mWatcher = new IRotationWatcher.Stub() { // from class: com.android.keyguard.SecRotationWatcher.1
        public final void onRotationChanged(int i) {
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = Integer.valueOf(i);
            removeMessages(0);
            sendMessage(obtain);
        }
    };
    public final AnonymousClass2 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.keyguard.SecRotationWatcher.2
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
}
