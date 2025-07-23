package com.android.server.servicewatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.server.FgThread;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes6.dex */
public interface ServiceWatcher {

    public interface BinderOperation {
        default void onError(Throwable th) {
        }

        void run(IBinder iBinder) throws RemoteException;
    }

    public interface ServiceChangedListener {
        void onServiceChanged();
    }

    public interface ServiceListener<TBoundServiceInfo extends BoundServiceInfo> {
        void onBind(IBinder iBinder, TBoundServiceInfo tboundserviceinfo) throws RemoteException;

        void onUnbind();
    }

    public interface ServiceSupplier<TBoundServiceInfo extends BoundServiceInfo> {
        void alertUnstableService(String str);

        TBoundServiceInfo getServiceInfo();

        boolean hasMatchingService();

        void register(ServiceChangedListener serviceChangedListener);

        void unregister();
    }

    boolean checkServiceResolves();

    void dump(PrintWriter printWriter);

    void register();

    void runOnBinder(BinderOperation binderOperation);

    void unregister();

    public static class BoundServiceInfo {
        private static final int DEFAULT_FLAGS = 1073741829;
        protected final String mAction;
        protected final ComponentName mComponentName;
        private final int mFlags;
        protected final int mUid;

        protected BoundServiceInfo(String str, ResolveInfo resolveInfo) {
            this(str, resolveInfo.serviceInfo.applicationInfo.uid, resolveInfo.serviceInfo.getComponentName());
        }

        protected BoundServiceInfo(String str, int i, ComponentName componentName) {
            this(str, i, componentName, DEFAULT_FLAGS);
        }

        protected BoundServiceInfo(String str, int i, ComponentName componentName, int i2) {
            this.mAction = str;
            this.mUid = i;
            this.mComponentName = (ComponentName) Objects.requireNonNull(componentName);
            this.mFlags = i2;
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

        public int getFlags() {
            return this.mFlags;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BoundServiceInfo)) {
                return false;
            }
            BoundServiceInfo boundServiceInfo = (BoundServiceInfo) obj;
            return this.mUid == boundServiceInfo.mUid && Objects.equals(this.mAction, boundServiceInfo.mAction) && this.mComponentName.equals(boundServiceInfo.mComponentName) && this.mFlags == boundServiceInfo.mFlags;
        }

        public final int hashCode() {
            return Objects.hash(this.mAction, Integer.valueOf(this.mUid), this.mComponentName, Integer.valueOf(this.mFlags));
        }

        public String toString() {
            return this.mUid + "/" + this.mComponentName.flattenToShortString();
        }
    }

    static <TBoundServiceInfo extends BoundServiceInfo> ServiceWatcher create(Context context, String str, ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceListener<? super TBoundServiceInfo> serviceListener) {
        return create(context, FgThread.getHandler(), str, serviceSupplier, serviceListener);
    }

    static <TBoundServiceInfo extends BoundServiceInfo> ServiceWatcher create(Context context, String str, boolean z, ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceListener<? super TBoundServiceInfo> serviceListener) {
        return create(context, FgThread.getHandler(), str, z, serviceSupplier, serviceListener);
    }

    static <TBoundServiceInfo extends BoundServiceInfo> ServiceWatcher create(Context context, Handler handler, String str, ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceListener<? super TBoundServiceInfo> serviceListener) {
        return new ServiceWatcherImpl(context, handler, str, serviceSupplier, serviceListener);
    }

    static <TBoundServiceInfo extends BoundServiceInfo> ServiceWatcher create(Context context, Handler handler, String str, boolean z, ServiceSupplier<TBoundServiceInfo> serviceSupplier, ServiceListener<? super TBoundServiceInfo> serviceListener) {
        return new ServiceWatcherImpl(context, handler, str, z, serviceSupplier, serviceListener);
    }
}
