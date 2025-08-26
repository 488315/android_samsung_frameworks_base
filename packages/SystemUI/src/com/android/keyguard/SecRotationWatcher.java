package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.IRotationWatcher;
import com.android.keyguard.SecRotationWatcher;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class SecRotationWatcher {
    public final Context mContext;
    public int mCurrentRotation;
    public final ArrayList mListeners = new ArrayList();
    public final AnonymousClass1 mWatcher = new IRotationWatcher.Stub() { // from class: com.android.keyguard.SecRotationWatcher.1
        public final void onRotationChanged(int i) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 0;
            messageObtain.obj = Integer.valueOf(i);
            SecRotationWatcher.this.mHandler.removeMessages(0);
            SecRotationWatcher.this.mHandler.sendMessage(messageObtain);
        }
    };
    public final AnonymousClass2 mHandler = new AnonymousClass2(Looper.getMainLooper());

    /* renamed from: com.android.keyguard.SecRotationWatcher$2, reason: invalid class name */
    public class AnonymousClass2 extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final int iIntValue = ((Integer) message.obj).intValue();
            SecRotationWatcher secRotationWatcher = SecRotationWatcher.this;
            secRotationWatcher.mCurrentRotation = iIntValue;
            secRotationWatcher.mListeners.forEach(new Consumer() { // from class: com.android.keyguard.SecRotationWatcher$2$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i = iIntValue;
                    int i2 = SecRotationWatcher.AnonymousClass2.$r8$clinit;
                    ((IntConsumer) obj).accept(i);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.SecRotationWatcher$1] */
    public SecRotationWatcher(Context context) {
        this.mContext = context;
    }
}
