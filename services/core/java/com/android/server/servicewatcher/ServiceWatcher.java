package com.android.server.servicewatcher;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import com.android.server.FgThread;
import com.android.server.location.injector.Injector;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes3.dex */
public interface ServiceWatcher {

    public interface BinderOperation {
        default void onError(Throwable th) {
        }

        void run(IBinder iBinder);
    }

    public interface ServiceChangedListener {
        void onServiceChanged();
    }

    public interface ServiceListener {
        void onBind(IBinder iBinder, BoundServiceInfo boundServiceInfo);

        void onUnbind();
    }

    public interface ServiceSupplier {
        BoundServiceInfo getServiceInfo();

        boolean hasMatchingService();

        void register(ServiceChangedListener serviceChangedListener);

        void unregister();
    }

    boolean checkServiceResolves();

    void dump(PrintWriter printWriter);

    void register();

    void runOnBinder(BinderOperation binderOperation);

    void unregister();

    public abstract class BoundServiceInfo {
        public final String mAction;
        public final ComponentName mComponentName;
        public final int mUid;

        public BoundServiceInfo(String str, int i, ComponentName componentName) {
            this.mAction = str;
            this.mUid = i;
            Objects.requireNonNull(componentName);
            this.mComponentName = componentName;
        }

        public String getAction() {
            return this.mAction;
        }

        public ComponentName getComponentName() {
            return this.mComponentName;
        }

        public int getUserId() {
            return UserHandle.getUserId(this.mUid);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BoundServiceInfo)) {
                return false;
            }
            BoundServiceInfo boundServiceInfo = (BoundServiceInfo) obj;
            return this.mUid == boundServiceInfo.mUid && Objects.equals(this.mAction, boundServiceInfo.mAction) && this.mComponentName.equals(boundServiceInfo.mComponentName);
        }

        public final int hashCode() {
            return Objects.hash(this.mAction, Integer.valueOf(this.mUid), this.mComponentName);
        }

        public String toString() {
            if (this.mComponentName == null) {
                return "none";
            }
            return this.mUid + "/" + this.mComponentName.flattenToShortString();
        }
    }

    static ServiceWatcher create(Context context, Handler handler, String str, ServiceSupplier serviceSupplier, ServiceListener serviceListener) {
        return new ServiceWatcherImpl(context, handler, str, serviceSupplier, serviceListener, null);
    }

    static ServiceWatcher create(Context context, String str, ServiceSupplier serviceSupplier, ServiceListener serviceListener, Injector injector) {
        return create(context, FgThread.getHandler(), str, serviceSupplier, serviceListener, injector);
    }

    static ServiceWatcher create(Context context, Handler handler, String str, ServiceSupplier serviceSupplier, ServiceListener serviceListener, Injector injector) {
        return new ServiceWatcherImpl(context, handler, str, serviceSupplier, serviceListener, injector);
    }
}
