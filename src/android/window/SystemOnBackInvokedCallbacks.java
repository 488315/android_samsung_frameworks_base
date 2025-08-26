package android.window;

import android.app.Activity;
import android.util.ArrayMap;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public final class SystemOnBackInvokedCallbacks {
    private static final OverrideCallbackFactory<Activity> sFinishAndRemoveTaskFactory;
    private static final OverrideCallbackFactory<Activity> sMoveTaskToBackFactory;

    static {
        sMoveTaskToBackFactory = new MoveTaskToBackCallbackFactory();
        sFinishAndRemoveTaskFactory = new FinishAndRemoveTaskCallbackFactory();
    }

    private SystemOnBackInvokedCallbacks() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static OnBackInvokedCallback moveTaskToBackCallback(Activity activity) {
        return sMoveTaskToBackFactory.getOverrideCallback(activity);
    }

    public static OnBackInvokedCallback finishAndRemoveTaskCallback(Activity activity) {
        return sFinishAndRemoveTaskFactory.getOverrideCallback(activity);
    }

    private static abstract class OverrideCallbackFactory<TYPE> {
        private final ArrayMap<WeakReference<TYPE>, WeakReference<SystemOverrideOnBackInvokedCallback>> mObjectMap;

        protected abstract SystemOverrideOnBackInvokedCallback createCallback(TYPE type);

        private OverrideCallbackFactory() {
            this.mObjectMap = new ArrayMap<>();
        }

        SystemOverrideOnBackInvokedCallback getOverrideCallback(TYPE type) {
            WeakReference<SystemOverrideOnBackInvokedCallback> weakReference;
            if (type == null) {
                throw new NullPointerException("Input object cannot be null");
            }
            synchronized (this.mObjectMap) {
                int size = this.mObjectMap.size() - 1;
                while (true) {
                    if (size < 0) {
                        weakReference = null;
                        break;
                    }
                    WeakReference<TYPE> weakReferenceKeyAt = this.mObjectMap.keyAt(size);
                    if (weakReferenceKeyAt.get() == type) {
                        weakReference = this.mObjectMap.get(weakReferenceKeyAt);
                        break;
                    }
                    size--;
                }
                if (weakReference != null) {
                    return weakReference.get();
                }
                SystemOverrideOnBackInvokedCallback systemOverrideOnBackInvokedCallbackCreateCallback = createCallback(type);
                if (systemOverrideOnBackInvokedCallbackCreateCallback != null) {
                    this.mObjectMap.put(new WeakReference<>(type), new WeakReference<>(systemOverrideOnBackInvokedCallbackCreateCallback));
                }
                return systemOverrideOnBackInvokedCallbackCreateCallback;
            }
        }
    }

    private static class MoveTaskToBackCallbackFactory extends OverrideCallbackFactory<Activity> {
        private MoveTaskToBackCallbackFactory() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.window.SystemOnBackInvokedCallbacks.OverrideCallbackFactory
        public SystemOverrideOnBackInvokedCallback createCallback(Activity activity) {
            final WeakReference weakReference = new WeakReference(activity);
            return new SystemOverrideOnBackInvokedCallback(this) { // from class: android.window.SystemOnBackInvokedCallbacks.MoveTaskToBackCallbackFactory.1
                @Override // android.window.SystemOverrideOnBackInvokedCallback
                public int overrideBehavior() {
                    return 1;
                }

                @Override // android.window.OnBackInvokedCallback
                public void onBackInvoked() {
                    if (weakReference.get() != null) {
                        ((Activity) weakReference.get()).moveTaskToBack(true);
                    }
                }
            };
        }
    }

    private static class FinishAndRemoveTaskCallbackFactory extends OverrideCallbackFactory<Activity> {
        private FinishAndRemoveTaskCallbackFactory() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.window.SystemOnBackInvokedCallbacks.OverrideCallbackFactory
        public SystemOverrideOnBackInvokedCallback createCallback(Activity activity) {
            final WeakReference weakReference = new WeakReference(activity);
            return new SystemOverrideOnBackInvokedCallback(this) { // from class: android.window.SystemOnBackInvokedCallbacks.FinishAndRemoveTaskCallbackFactory.1
                @Override // android.window.SystemOverrideOnBackInvokedCallback
                public int overrideBehavior() {
                    return 2;
                }

                @Override // android.window.OnBackInvokedCallback
                public void onBackInvoked() {
                    if (weakReference.get() != null) {
                        ((Activity) weakReference.get()).finishAndRemoveTask();
                    }
                }
            };
        }
    }
}
