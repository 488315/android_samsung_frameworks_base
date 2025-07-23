package android.content;

import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ComponentCallbacksController {
    private List<ComponentCallbacks> mComponentCallbacks;
    private final Object mLock = new Object();

    public void registerCallbacks(ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            if (this.mComponentCallbacks == null) {
                this.mComponentCallbacks = new ArrayList();
            }
            this.mComponentCallbacks.add(componentCallbacks);
        }
    }

    public void unregisterCallbacks(ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            List<ComponentCallbacks> list = this.mComponentCallbacks;
            if (list != null && !list.isEmpty()) {
                this.mComponentCallbacks.remove(componentCallbacks);
            }
        }
    }

    public void clearCallbacks() {
        synchronized (this.mLock) {
            List<ComponentCallbacks> list = this.mComponentCallbacks;
            if (list != null) {
                list.clear();
            }
        }
    }

    public void dispatchConfigurationChanged(final Configuration configuration) {
        forAllComponentCallbacks(new Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ComponentCallbacks) obj).onConfigurationChanged(Configuration.this);
            }
        });
    }

    public void dispatchLowMemory() {
        forAllComponentCallbacks(new Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ComponentCallbacks) obj).onLowMemory();
            }
        });
    }

    public void dispatchTrimMemory(final int i) {
        forAllComponentCallbacks(new Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ComponentCallbacksController.lambda$dispatchTrimMemory$1(i, (ComponentCallbacks) obj);
            }
        });
    }

    static /* synthetic */ void lambda$dispatchTrimMemory$1(int i, ComponentCallbacks componentCallbacks) {
        if (componentCallbacks instanceof ComponentCallbacks2) {
            ((ComponentCallbacks2) componentCallbacks).onTrimMemory(i);
        }
    }

    private void forAllComponentCallbacks(Consumer<ComponentCallbacks> consumer) {
        synchronized (this.mLock) {
            List<ComponentCallbacks> list = this.mComponentCallbacks;
            if (list != null && !list.isEmpty()) {
                int size = this.mComponentCallbacks.size();
                ComponentCallbacks[] componentCallbacksArr = new ComponentCallbacks[size];
                this.mComponentCallbacks.toArray(componentCallbacksArr);
                for (int i = 0; i < size; i++) {
                    consumer.accept(componentCallbacksArr[i]);
                }
            }
        }
    }
}
