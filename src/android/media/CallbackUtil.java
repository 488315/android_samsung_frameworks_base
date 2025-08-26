package android.media;

import android.media.CallbackUtil;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
class CallbackUtil {
    private static final String TAG = "CallbackUtil";

    interface CallbackMethod<T> {
        void callbackMethod(T t);
    }

    interface DispatcherStub {
        void register(boolean z);
    }

    CallbackUtil() {
    }

    static class ListenerInfo<T> {
        final Executor mExecutor;
        final T mListener;

        ListenerInfo(T t, Executor executor) {
            this.mListener = t;
            this.mExecutor = executor;
        }
    }

    static <T> ListenerInfo<T> getListenerInfo(T t, ArrayList<ListenerInfo<T>> arrayList) {
        if (arrayList == null) {
            return null;
        }
        Iterator<ListenerInfo<T>> it = arrayList.iterator();
        while (it.hasNext()) {
            ListenerInfo<T> next = it.next();
            if (next.mListener == t) {
                return next;
            }
        }
        return null;
    }

    static <T> boolean hasListener(T t, ArrayList<ListenerInfo<T>> arrayList) {
        return getListenerInfo(t, arrayList) != null;
    }

    static <T> boolean removeListener(T t, ArrayList<ListenerInfo<T>> arrayList) {
        ListenerInfo listenerInfo = getListenerInfo(t, arrayList);
        if (listenerInfo == null) {
            return false;
        }
        arrayList.remove(listenerInfo);
        return true;
    }

    static <T, S> Pair<ArrayList<ListenerInfo<T>>, S> addListener(String str, Executor executor, T t, ArrayList<ListenerInfo<T>> arrayList, S s, Supplier<S> supplier, Consumer<S> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(t);
        if (hasListener(t, arrayList)) {
            throw new IllegalArgumentException("attempt to call " + str + "on a previously registered listener");
        }
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        if (arrayList.size() == 0) {
            if (s == null) {
                try {
                    s = supplier.get();
                } catch (Exception e) {
                    Log.e(TAG, "Exception while creating stub in " + str, e);
                    return new Pair<>(null, null);
                }
            }
            consumer.accept(s);
        }
        arrayList.add(new ListenerInfo<>(t, executor));
        return new Pair<>(arrayList, s);
    }

    static <T, S> Pair<ArrayList<ListenerInfo<T>>, S> removeListener(String str, T t, ArrayList<ListenerInfo<T>> arrayList, S s, Consumer<S> consumer) {
        Objects.requireNonNull(t);
        if (!removeListener(t, arrayList)) {
            throw new IllegalArgumentException("attempt to call " + str + " on an unregistered listener");
        }
        if (arrayList.size() == 0) {
            consumer.accept(s);
            return new Pair<>(null, null);
        }
        return new Pair<>(arrayList, s);
    }

    static <T> void callListeners(ArrayList<ListenerInfo<T>> arrayList, Object obj, final CallbackMethod<T> callbackMethod) {
        Objects.requireNonNull(obj);
        synchronized (obj) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    ArrayList arrayList2 = (ArrayList) arrayList.clone();
                    SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
                    try {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            final ListenerInfo listenerInfo = (ListenerInfo) it.next();
                            listenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.CallbackUtil$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    callbackMethod.callbackMethod(listenerInfo.mListener);
                                }
                            });
                        }
                        if (safeCloseableCreate != null) {
                            safeCloseableCreate.close();
                        }
                    } catch (Throwable th) {
                        if (safeCloseableCreate != null) {
                            try {
                                safeCloseableCreate.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
            }
        }
    }

    static class LazyListenerManager<T> {
        private DispatcherStub mDispatcherStub;
        private final Object mListenerLock = new Object();
        private ArrayList<ListenerInfo<T>> mListeners;

        LazyListenerManager() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        void addListener(Executor executor, T t, String str, Supplier<DispatcherStub> supplier) {
            synchronized (this.mListenerLock) {
                Pair pairAddListener = CallbackUtil.addListener(str, executor, t, this.mListeners, this.mDispatcherStub, supplier, new Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((CallbackUtil.DispatcherStub) obj).register(true);
                    }
                });
                this.mListeners = (ArrayList) pairAddListener.first;
                this.mDispatcherStub = (DispatcherStub) pairAddListener.second;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeListener(T t, String str) {
            synchronized (this.mListenerLock) {
                Pair pairRemoveListener = CallbackUtil.removeListener(str, t, this.mListeners, this.mDispatcherStub, new Consumer() { // from class: android.media.CallbackUtil$LazyListenerManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((CallbackUtil.DispatcherStub) obj).register(false);
                    }
                });
                this.mListeners = (ArrayList) pairRemoveListener.first;
                this.mDispatcherStub = (DispatcherStub) pairRemoveListener.second;
            }
        }

        void callListeners(CallbackMethod<T> callbackMethod) {
            CallbackUtil.callListeners(this.mListeners, this.mListenerLock, callbackMethod);
        }
    }
}
