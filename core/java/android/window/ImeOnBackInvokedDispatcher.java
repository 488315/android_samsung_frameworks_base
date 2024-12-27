package android.window;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.ViewRootImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ImeOnBackInvokedDispatcher implements OnBackInvokedDispatcher, Parcelable {
    public static final Parcelable.Creator<ImeOnBackInvokedDispatcher> CREATOR =
            new Parcelable.Creator<
                    ImeOnBackInvokedDispatcher>() { // from class:
                                                    // android.window.ImeOnBackInvokedDispatcher.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ImeOnBackInvokedDispatcher createFromParcel(Parcel in) {
                    return new ImeOnBackInvokedDispatcher(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ImeOnBackInvokedDispatcher[] newArray(int size) {
                    return new ImeOnBackInvokedDispatcher[size];
                }
            };
    static final int RESULT_CODE_REGISTER = 0;
    static final int RESULT_CODE_UNREGISTER = 1;
    static final String RESULT_KEY_CALLBACK = "callback";
    static final String RESULT_KEY_ID = "id";
    static final String RESULT_KEY_PRIORITY = "priority";
    private static final String TAG = "ImeBackDispatcher";
    private Handler mHandler;
    private final ArrayList<ImeOnBackInvokedCallback> mImeCallbacks = new ArrayList<>();
    private final ResultReceiver mResultReceiver;

    public ImeOnBackInvokedDispatcher(Handler handler) {
        this.mResultReceiver =
                new ResultReceiver(
                        handler) { // from class: android.window.ImeOnBackInvokedDispatcher.1
                    @Override // android.os.ResultReceiver
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        WindowOnBackInvokedDispatcher dispatcher =
                                ImeOnBackInvokedDispatcher.this.getReceivingDispatcher();
                        if (dispatcher != null) {
                            ImeOnBackInvokedDispatcher.this.receive(
                                    resultCode, resultData, dispatcher);
                        }
                    }
                };
    }

    void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    protected WindowOnBackInvokedDispatcher getReceivingDispatcher() {
        return null;
    }

    ImeOnBackInvokedDispatcher(Parcel in) {
        this.mResultReceiver = (ResultReceiver) in.readTypedObject(ResultReceiver.CREATOR);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int priority, OnBackInvokedCallback callback) {
        Bundle bundle = new Bundle();
        IOnBackInvokedCallback iCallback = new ImeOnBackInvokedCallbackWrapper(callback);
        bundle.putBinder(RESULT_KEY_CALLBACK, iCallback.asBinder());
        bundle.putInt("priority", priority);
        bundle.putInt("id", callback.hashCode());
        this.mResultReceiver.send(0, bundle);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(OnBackInvokedCallback callback) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", callback.hashCode());
        this.mResultReceiver.send(1, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mResultReceiver, flags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receive(
            int resultCode, Bundle resultData, WindowOnBackInvokedDispatcher receivingDispatcher) {
        if (resultCode == 0) {
            int callbackId = resultData.getInt("id");
            int priority = resultData.getInt("priority");
            IOnBackInvokedCallback callback =
                    IOnBackInvokedCallback.Stub.asInterface(
                            resultData.getBinder(RESULT_KEY_CALLBACK));
            registerReceivedCallback(callback, priority, callbackId, receivingDispatcher);
            return;
        }
        if (resultCode == 1) {
            int callbackId2 = resultData.getInt("id");
            unregisterReceivedCallback(callbackId2, receivingDispatcher);
        }
    }

    private void registerReceivedCallback(
            IOnBackInvokedCallback iCallback,
            int priority,
            int callbackId,
            WindowOnBackInvokedDispatcher receivingDispatcher) {
        ImeOnBackInvokedCallback imeCallback;
        if (priority == -1) {
            priority = 0;
            imeCallback = new DefaultImeOnBackAnimationCallback(iCallback, callbackId, 0);
        } else {
            imeCallback = new ImeOnBackInvokedCallback(iCallback, callbackId, priority);
        }
        this.mImeCallbacks.add(imeCallback);
        receivingDispatcher.registerOnBackInvokedCallbackUnchecked(imeCallback, priority);
    }

    private void unregisterReceivedCallback(
            int callbackId, OnBackInvokedDispatcher receivingDispatcher) {
        ImeOnBackInvokedCallback callback = null;
        Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ImeOnBackInvokedCallback imeCallback = it.next();
            if (imeCallback.getId() == callbackId) {
                callback = imeCallback;
                break;
            }
        }
        if (callback == null) {
            Log.e(
                    TAG,
                    "Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: "
                            + callbackId);
        } else {
            receivingDispatcher.unregisterOnBackInvokedCallback(callback);
            this.mImeCallbacks.remove(callback);
        }
    }

    public void clear() {
        if (getReceivingDispatcher() != null) {
            Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
            while (it.hasNext()) {
                ImeOnBackInvokedCallback callback = it.next();
                getReceivingDispatcher().unregisterOnBackInvokedCallback(callback);
            }
        }
        this.mImeCallbacks.clear();
    }

    public static class ImeOnBackInvokedCallback implements OnBackAnimationCallback {
        private final IOnBackInvokedCallback mIOnBackInvokedCallback;
        private final int mId;
        private final int mPriority;

        ImeOnBackInvokedCallback(IOnBackInvokedCallback iCallback, int id, int priority) {
            this.mIOnBackInvokedCallback = iCallback;
            this.mId = id;
            this.mPriority = priority;
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackStarted(BackEvent backEvent) {
            try {
                this.mIOnBackInvokedCallback.onBackStarted(
                        new BackMotionEvent(
                                backEvent.getTouchX(),
                                backEvent.getTouchY(),
                                backEvent.getProgress(),
                                0.0f,
                                0.0f,
                                false,
                                backEvent.getSwipeEdge(),
                                null));
            } catch (RemoteException e) {
                Log.e(
                        ImeOnBackInvokedDispatcher.TAG,
                        "Exception when invoking forwarded callback. e: ",
                        e);
            }
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackProgressed(BackEvent backEvent) {
            try {
                this.mIOnBackInvokedCallback.onBackProgressed(
                        new BackMotionEvent(
                                backEvent.getTouchX(),
                                backEvent.getTouchY(),
                                backEvent.getProgress(),
                                0.0f,
                                0.0f,
                                false,
                                backEvent.getSwipeEdge(),
                                null));
            } catch (RemoteException e) {
                Log.e(
                        ImeOnBackInvokedDispatcher.TAG,
                        "Exception when invoking forwarded callback. e: ",
                        e);
            }
        }

        @Override // android.window.OnBackInvokedCallback
        public void onBackInvoked() {
            try {
                this.mIOnBackInvokedCallback.onBackInvoked();
            } catch (RemoteException e) {
                Log.e(
                        ImeOnBackInvokedDispatcher.TAG,
                        "Exception when invoking forwarded callback. e: ",
                        e);
            }
        }

        @Override // android.window.OnBackAnimationCallback
        public void onBackCancelled() {
            try {
                this.mIOnBackInvokedCallback.onBackCancelled();
            } catch (RemoteException e) {
                Log.e(
                        ImeOnBackInvokedDispatcher.TAG,
                        "Exception when invoking forwarded callback. e: ",
                        e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getId() {
            return this.mId;
        }

        public String toString() {
            return "ImeCallback=ImeOnBackInvokedCallback@"
                    + this.mId
                    + " Callback="
                    + this.mIOnBackInvokedCallback;
        }
    }

    public static class DefaultImeOnBackAnimationCallback extends ImeOnBackInvokedCallback {
        DefaultImeOnBackAnimationCallback(IOnBackInvokedCallback iCallback, int id, int priority) {
            super(iCallback, id, priority);
        }
    }

    public void switchRootView(ViewRootImpl previous, ViewRootImpl current) {
        Iterator<ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (it.hasNext()) {
            ImeOnBackInvokedCallback imeCallback = it.next();
            if (previous != null) {
                previous.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(imeCallback);
            }
            if (current != null) {
                current.getOnBackInvokedDispatcher()
                        .registerOnBackInvokedCallbackUnchecked(imeCallback, imeCallback.mPriority);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ImeOnBackInvokedCallbackWrapper extends IOnBackInvokedCallback.Stub {
        private final OnBackInvokedCallback mCallback;

        ImeOnBackInvokedCallbackWrapper(OnBackInvokedCallback callback) {
            this.mCallback = callback;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(final BackMotionEvent backMotionEvent) {
            maybeRunOnAnimationCallback(
                    new Consumer() { // from class:
                                     // android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((OnBackAnimationCallback) obj)
                                    .onBackStarted(
                                            BackEvent.fromBackMotionEvent(BackMotionEvent.this));
                        }
                    });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(final BackMotionEvent backMotionEvent) {
            maybeRunOnAnimationCallback(
                    new Consumer() { // from class:
                                     // android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((OnBackAnimationCallback) obj)
                                    .onBackProgressed(
                                            BackEvent.fromBackMotionEvent(BackMotionEvent.this));
                        }
                    });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() {
            maybeRunOnAnimationCallback(
                    new Consumer() { // from class:
                                     // android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((OnBackAnimationCallback) obj).onBackCancelled();
                        }
                    });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() {
            Handler handler = ImeOnBackInvokedDispatcher.this.mHandler;
            OnBackInvokedCallback onBackInvokedCallback = this.mCallback;
            Objects.requireNonNull(onBackInvokedCallback);
            handler.post(
                    new ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1(
                            onBackInvokedCallback));
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean triggerBack) {}

        private void maybeRunOnAnimationCallback(final Consumer<OnBackAnimationCallback> block) {
            if (this.mCallback instanceof OnBackAnimationCallback) {
                ImeOnBackInvokedDispatcher.this.mHandler.post(
                        new Runnable() { // from class:
                                         // android.window.ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallbackWrapper.this
                                        .lambda$maybeRunOnAnimationCallback$2(block);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$maybeRunOnAnimationCallback$2(Consumer block) {
            block.accept((OnBackAnimationCallback) this.mCallback);
        }
    }
}
