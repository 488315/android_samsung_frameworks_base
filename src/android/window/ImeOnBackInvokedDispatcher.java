package android.window;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.Pair;
import android.view.ViewRootImpl;
import android.window.IOnBackInvokedCallback;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class ImeOnBackInvokedDispatcher implements OnBackInvokedDispatcher, Parcelable {
    public static final Parcelable.Creator<ImeOnBackInvokedDispatcher> CREATOR = new Parcelable.Creator<ImeOnBackInvokedDispatcher>() { // from class: android.window.ImeOnBackInvokedDispatcher.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImeOnBackInvokedDispatcher createFromParcel(Parcel parcel) {
            return new ImeOnBackInvokedDispatcher(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImeOnBackInvokedDispatcher[] newArray(int i) {
            return new ImeOnBackInvokedDispatcher[i];
        }
    };
    static final int RESULT_CODE_REGISTER = 0;
    static final int RESULT_CODE_UNREGISTER = 1;
    static final String RESULT_KEY_CALLBACK = "callback";
    static final String RESULT_KEY_ID = "id";
    static final String RESULT_KEY_PRIORITY = "priority";
    private static final String TAG = "ImeBackDispatcher";
    private Handler mHandler;
    private final ResultReceiver mResultReceiver;
    private final ArrayDeque<Pair<Integer, Bundle>> mQueuedReceive = new ArrayDeque<>();
    private final ArrayList<ImeOnBackInvokedCallback> mImeCallbacks = new ArrayList<>();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected WindowOnBackInvokedDispatcher getReceivingDispatcher() {
        return null;
    }

    public ImeOnBackInvokedDispatcher(Handler handler) {
        this.mResultReceiver = new ResultReceiver(handler) { // from class: android.window.ImeOnBackInvokedDispatcher.1
            @Override // android.os.ResultReceiver
            public void onReceiveResult(int i, Bundle bundle) {
                WindowOnBackInvokedDispatcher receivingDispatcher = ImeOnBackInvokedDispatcher.this.getReceivingDispatcher();
                if (receivingDispatcher != null) {
                    ImeOnBackInvokedDispatcher.this.receive(i, bundle, receivingDispatcher);
                } else {
                    ImeOnBackInvokedDispatcher.this.mQueuedReceive.add(new Pair(Integer.valueOf(i), bundle));
                }
            }
        };
    }

    public void updateReceivingDispatcher(WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
        while (!this.mQueuedReceive.isEmpty()) {
            Pair<Integer, Bundle> pairPoll = this.mQueuedReceive.poll();
            receive(pairPoll.first.intValue(), pairPoll.second, windowOnBackInvokedDispatcher);
        }
    }

    void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    ImeOnBackInvokedDispatcher(Parcel parcel) {
        this.mResultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, OnBackInvokedCallback onBackInvokedCallback) {
        Bundle bundle = new Bundle();
        bundle.putBinder(RESULT_KEY_CALLBACK, new ImeOnBackInvokedCallbackWrapper(onBackInvokedCallback).asBinder());
        bundle.putInt("priority", i);
        bundle.putInt("id", onBackInvokedCallback.hashCode());
        this.mResultReceiver.send(0, bundle);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", onBackInvokedCallback.hashCode());
        this.mResultReceiver.send(1, bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mResultReceiver, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receive(int i, Bundle bundle, WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
        if (i == 0) {
            int i2 = bundle.getInt("id");
            registerReceivedCallback(IOnBackInvokedCallback.Stub.asInterface(bundle.getBinder(RESULT_KEY_CALLBACK)), bundle.getInt("priority"), i2, windowOnBackInvokedDispatcher);
        } else if (i == 1) {
            unregisterReceivedCallback(bundle.getInt("id"), windowOnBackInvokedDispatcher);
        }
    }

    private void registerReceivedCallback(IOnBackInvokedCallback iOnBackInvokedCallback, int i, int i2, WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
        int i3;
        ImeOnBackInvokedCallback imeOnBackInvokedCallback;
        if (i == -1) {
            i3 = 0;
            imeOnBackInvokedCallback = new DefaultImeOnBackAnimationCallback(iOnBackInvokedCallback, i2, 0);
        } else {
            i3 = i;
            imeOnBackInvokedCallback = new ImeOnBackInvokedCallback(iOnBackInvokedCallback, i2, i);
        }
        this.mImeCallbacks.add(imeOnBackInvokedCallback);
        windowOnBackInvokedDispatcher.registerOnBackInvokedCallbackUnchecked(imeOnBackInvokedCallback, i3);
    }

    private void unregisterReceivedCallback(int i, OnBackInvokedDispatcher onBackInvokedDispatcher) {
        ImeOnBackInvokedCallback next;
        Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (next.getId() == i) {
                    break;
                }
            }
        }
        if (next == null) {
            Log.e(TAG, "Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: " + i);
        } else {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(next);
            this.mImeCallbacks.remove(next);
        }
    }

    public void preliminaryClear() {
        if (getReceivingDispatcher() != null) {
            Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
            while (it.hasNext()) {
                getReceivingDispatcher().unregisterOnBackInvokedCallback(it.next());
            }
        }
    }

    public void undoPreliminaryClear() {
        if (getReceivingDispatcher() != null) {
            Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
            while (it.hasNext()) {
                ImeOnBackInvokedCallback next = it.next();
                getReceivingDispatcher().registerOnBackInvokedCallbackUnchecked(next, next.mPriority);
            }
        }
    }

    public void clear() {
        if (getReceivingDispatcher() != null) {
            Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
            while (it.hasNext()) {
                getReceivingDispatcher().unregisterOnBackInvokedCallback(it.next());
            }
        }
        this.mImeCallbacks.clear();
        this.mQueuedReceive.clear();
    }

    public static class ImeOnBackInvokedCallback implements OnBackAnimationCallback {
        private final IOnBackInvokedCallback mIOnBackInvokedCallback;
        private final int mId;
        private final int mPriority;

        ImeOnBackInvokedCallback(IOnBackInvokedCallback iOnBackInvokedCallback, int i, int i2) {
            this.mIOnBackInvokedCallback = iOnBackInvokedCallback;
            this.mId = i;
            this.mPriority = i2;
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackStarted(BackEvent backEvent) {
            try {
                this.mIOnBackInvokedCallback.onBackStarted(new BackMotionEvent(backEvent.getTouchX(), backEvent.getTouchY(), Flags.predictiveBackTimestampApi() ? backEvent.getFrameTimeMillis() : 0L, backEvent.getProgress(), false, backEvent.getSwipeEdge(), null));
            } catch (RemoteException e) {
                Log.e(ImeOnBackInvokedDispatcher.TAG, "Exception when invoking forwarded callback. e: ", e);
            }
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackProgressed(BackEvent backEvent) {
            try {
                this.mIOnBackInvokedCallback.onBackProgressed(new BackMotionEvent(backEvent.getTouchX(), backEvent.getTouchY(), Flags.predictiveBackTimestampApi() ? backEvent.getFrameTimeMillis() : 0L, backEvent.getProgress(), false, backEvent.getSwipeEdge(), null));
            } catch (RemoteException e) {
                Log.e(ImeOnBackInvokedDispatcher.TAG, "Exception when invoking forwarded callback. e: ", e);
            }
        }

        @Override // android.window.OnBackInvokedCallback
        public void onBackInvoked() {
            try {
                this.mIOnBackInvokedCallback.onBackInvoked();
            } catch (RemoteException e) {
                Log.e(ImeOnBackInvokedDispatcher.TAG, "Exception when invoking forwarded callback. e: ", e);
            }
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackCancelled() {
            try {
                this.mIOnBackInvokedCallback.onBackCancelled();
            } catch (RemoteException e) {
                Log.e(ImeOnBackInvokedDispatcher.TAG, "Exception when invoking forwarded callback. e: ", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getId() {
            return this.mId;
        }

        public String toString() {
            return "ImeCallback=ImeOnBackInvokedCallback@" + this.mId + " Callback=" + this.mIOnBackInvokedCallback;
        }
    }

    public static class DefaultImeOnBackAnimationCallback extends ImeOnBackInvokedCallback {
        DefaultImeOnBackAnimationCallback(IOnBackInvokedCallback iOnBackInvokedCallback, int i, int i2) {
            super(iOnBackInvokedCallback, i, i2);
        }
    }

    public void switchRootView(ViewRootImpl viewRootImpl, ViewRootImpl viewRootImpl2) {
        Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (it.hasNext()) {
            ImeOnBackInvokedCallback next = it.next();
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(next);
            }
            if (viewRootImpl2 != null) {
                viewRootImpl2.getOnBackInvokedDispatcher().registerOnBackInvokedCallbackUnchecked(next, next.mPriority);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ImeOnBackInvokedCallbackWrapper extends IOnBackInvokedCallback.Stub {
        private final OnBackInvokedCallback mCallback;

        @Override // android.window.IOnBackInvokedCallback
        public void setHandoffHandler(IBackAnimationHandoffHandler iBackAnimationHandoffHandler) {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean z) {
        }

        ImeOnBackInvokedCallbackWrapper(OnBackInvokedCallback onBackInvokedCallback) {
            this.mCallback = onBackInvokedCallback;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(final BackMotionEvent backMotionEvent) {
            maybeRunOnAnimationCallback(new Consumer() { // from class: android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnBackAnimationCallback) obj).onBackStarted(BackEvent.fromBackMotionEvent(backMotionEvent));
                }
            });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(final BackMotionEvent backMotionEvent) {
            maybeRunOnAnimationCallback(new Consumer() { // from class: android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnBackAnimationCallback) obj).onBackProgressed(BackEvent.fromBackMotionEvent(backMotionEvent));
                }
            });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() {
            maybeRunOnAnimationCallback(new Consumer() { // from class: android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((OnBackAnimationCallback) obj).onBackCancelled();
                }
            });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() {
            Handler handler = ImeOnBackInvokedDispatcher.this.mHandler;
            final OnBackInvokedCallback onBackInvokedCallback = this.mCallback;
            Objects.requireNonNull(onBackInvokedCallback);
            handler.post(new Runnable() { // from class: android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    onBackInvokedCallback.onBackInvoked();
                }
            });
        }

        private void maybeRunOnAnimationCallback(final Consumer<OnBackAnimationCallback> consumer) {
            if (this.mCallback instanceof OnBackAnimationCallback) {
                ImeOnBackInvokedDispatcher.this.mHandler.post(new Runnable() { // from class: android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$maybeRunOnAnimationCallback$2(consumer);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$maybeRunOnAnimationCallback$2(Consumer consumer) {
            consumer.accept((OnBackAnimationCallback) this.mCallback);
        }
    }
}
