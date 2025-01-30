package com.android.systemui.plugins.omni;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.permission.SafeCloseable;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.plugins.omni.MainThreadInitializedObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MainThreadInitializedObject<T> {
    public static final LooperExecutor MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
    private final ObjectProvider<T> mProvider;
    private T mValue;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ObjectProvider<T> {
        T get(Context context);
    }

    public MainThreadInitializedObject(ObjectProvider<T> objectProvider) {
        this.mProvider = objectProvider;
    }

    /* renamed from: get, reason: merged with bridge method [inline-methods] */
    public T lambda$get$0(final Context context) {
        if (context instanceof SandboxContext) {
            return (T) ((SandboxContext) context).lambda$getObject$0(this, this.mProvider);
        }
        if (this.mValue == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                try {
                    return MAIN_EXECUTOR.submit(new Callable() { // from class: com.android.systemui.plugins.omni.MainThreadInitializedObject$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            Object lambda$get$0;
                            lambda$get$0 = MainThreadInitializedObject.this.lambda$get$0(context);
                            return lambda$get$0;
                        }
                    }).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            this.mValue = this.mProvider.get(context.getApplicationContext());
        }
        return this.mValue;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class SandboxContext extends ContextWrapper {
        private static final String TAG = "SandboxContext";
        protected final Set<MainThreadInitializedObject> mAllowedObjects;
        private final Object mDestroyLock;
        private boolean mDestroyed;
        protected final Map<MainThreadInitializedObject, Object> mObjectMap;
        protected final ArrayList<Object> mOrderedObjects;

        public SandboxContext(Context context, MainThreadInitializedObject... mainThreadInitializedObjectArr) {
            super(context);
            this.mObjectMap = new HashMap();
            this.mOrderedObjects = new ArrayList<>();
            this.mDestroyLock = new Object();
            this.mDestroyed = false;
            this.mAllowedObjects = new HashSet(Arrays.asList(mainThreadInitializedObjectArr));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: getObject, reason: merged with bridge method [inline-methods] */
        public <T> T lambda$getObject$0(final MainThreadInitializedObject<T> mainThreadInitializedObject, final ObjectProvider<T> objectProvider) {
            synchronized (this.mDestroyLock) {
                if (this.mDestroyed) {
                    Log.e(TAG, "Static object access with a destroyed context");
                }
                T t = (T) this.mObjectMap.get(mainThreadInitializedObject);
                if (t != null) {
                    return t;
                }
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    try {
                        return MainThreadInitializedObject.MAIN_EXECUTOR.submit(new Callable() { // from class: com.android.systemui.plugins.omni.MainThreadInitializedObject$SandboxContext$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                Object lambda$getObject$0;
                                lambda$getObject$0 = MainThreadInitializedObject.SandboxContext.this.lambda$getObject$0(mainThreadInitializedObject, objectProvider);
                                return lambda$getObject$0;
                            }
                        }).get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
                T t2 = (T) createObject(objectProvider);
                if (!this.mAllowedObjects.contains(mainThreadInitializedObject) && !(t2 instanceof SafeCloseable)) {
                    throw new IllegalStateException("Leaking unknown objects " + mainThreadInitializedObject + "  " + objectProvider + " " + t2);
                }
                this.mObjectMap.put(mainThreadInitializedObject, t2);
                this.mOrderedObjects.add(t2);
                return t2;
            }
        }

        public <T> T createObject(ObjectProvider<T> objectProvider) {
            return objectProvider.get(this);
        }

        public void onDestroy() {
            synchronized (this.mDestroyLock) {
                for (int size = this.mOrderedObjects.size() - 1; size >= 0; size--) {
                    Object obj = this.mOrderedObjects.get(size);
                    if (obj instanceof SafeCloseable) {
                        ((SafeCloseable) obj).close();
                    }
                }
                this.mDestroyed = true;
            }
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Context getApplicationContext() {
            return this;
        }
    }
}
