package androidx.core.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationManagerCompat {
    public static String sEnabledNotificationListeners;
    public static SideChannelManager sSideChannelManager;
    public final Context mContext;
    public final NotificationManager mNotificationManager;
    public static final Object sEnabledNotificationListenersLock = new Object();
    public static Set sEnabledNotificationListenerPackages = new HashSet();
    public static final Object sLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotifyTask implements Task {

        /* renamed from: id */
        public final int f32id;
        public final Notification notif;
        public final String packageName;
        public final String tag;

        public NotifyTask(String str, int i, String str2, Notification notification2) {
            this.packageName = str;
            this.f32id = i;
            this.tag = str2;
            this.notif = notification2;
        }

        @Override // androidx.core.app.NotificationManagerCompat.Task
        public final void send(INotificationSideChannel iNotificationSideChannel) {
            ((INotificationSideChannel.Stub.Proxy) iNotificationSideChannel).notify(this.packageName, this.f32id, this.tag, this.notif);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("NotifyTask[packageName:");
            sb.append(this.packageName);
            sb.append(", id:");
            sb.append(this.f32id);
            sb.append(", tag:");
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.tag, "]");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ServiceConnectedEvent {
        public final ComponentName componentName;
        public final IBinder iBinder;

        public ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SideChannelManager implements Handler.Callback, ServiceConnection {
        public final Context mContext;
        public final Handler mHandler;
        public final Map mRecordMap = new HashMap();
        public Set mCachedEnabledPackages = new HashSet();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ListenerRecord {
            public final ComponentName componentName;
            public INotificationSideChannel service;
            public boolean bound = false;
            public final ArrayDeque taskQueue = new ArrayDeque();
            public int retryCount = 0;

            public ListenerRecord(ComponentName componentName) {
                this.componentName = componentName;
            }
        }

        public SideChannelManager(Context context) {
            this.mContext = context;
            HandlerThread handlerThread = new HandlerThread("NotificationManagerCompat");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper(), this);
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            Set set;
            int i = message.what;
            INotificationSideChannel iNotificationSideChannel = null;
            if (i != 0) {
                if (i == 1) {
                    ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj;
                    ComponentName componentName = serviceConnectedEvent.componentName;
                    IBinder iBinder = serviceConnectedEvent.iBinder;
                    ListenerRecord listenerRecord = (ListenerRecord) ((HashMap) this.mRecordMap).get(componentName);
                    if (listenerRecord != null) {
                        int i2 = INotificationSideChannel.Stub.$r8$clinit;
                        if (iBinder != null) {
                            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.support.v4.app.INotificationSideChannel");
                            iNotificationSideChannel = (queryLocalInterface == null || !(queryLocalInterface instanceof INotificationSideChannel)) ? new INotificationSideChannel.Stub.Proxy(iBinder) : (INotificationSideChannel) queryLocalInterface;
                        }
                        listenerRecord.service = iNotificationSideChannel;
                        listenerRecord.retryCount = 0;
                        processListenerQueue(listenerRecord);
                    }
                    return true;
                }
                if (i != 2) {
                    if (i != 3) {
                        return false;
                    }
                    ListenerRecord listenerRecord2 = (ListenerRecord) ((HashMap) this.mRecordMap).get((ComponentName) message.obj);
                    if (listenerRecord2 != null) {
                        processListenerQueue(listenerRecord2);
                    }
                    return true;
                }
                ListenerRecord listenerRecord3 = (ListenerRecord) ((HashMap) this.mRecordMap).get((ComponentName) message.obj);
                if (listenerRecord3 != null) {
                    if (listenerRecord3.bound) {
                        this.mContext.unbindService(this);
                        listenerRecord3.bound = false;
                    }
                    listenerRecord3.service = null;
                }
                return true;
            }
            Task task = (Task) message.obj;
            Context context = this.mContext;
            Object obj = NotificationManagerCompat.sEnabledNotificationListenersLock;
            String string = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
            synchronized (NotificationManagerCompat.sEnabledNotificationListenersLock) {
                if (string != null) {
                    if (!string.equals(NotificationManagerCompat.sEnabledNotificationListeners)) {
                        String[] split = string.split(":", -1);
                        HashSet hashSet = new HashSet(split.length);
                        for (String str : split) {
                            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                            if (unflattenFromString != null) {
                                hashSet.add(unflattenFromString.getPackageName());
                            }
                        }
                        NotificationManagerCompat.sEnabledNotificationListenerPackages = hashSet;
                        NotificationManagerCompat.sEnabledNotificationListeners = string;
                    }
                }
                set = NotificationManagerCompat.sEnabledNotificationListenerPackages;
            }
            if (!set.equals(this.mCachedEnabledPackages)) {
                this.mCachedEnabledPackages = set;
                List<ResolveInfo> queryIntentServices = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 0);
                HashSet hashSet2 = new HashSet();
                for (ResolveInfo resolveInfo : queryIntentServices) {
                    if (((HashSet) set).contains(resolveInfo.serviceInfo.packageName)) {
                        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                        ComponentName componentName2 = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission != null) {
                            Log.w("NotifManCompat", "Permission present on component " + componentName2 + ", not adding listener record.");
                        } else {
                            hashSet2.add(componentName2);
                        }
                    }
                }
                Iterator it = hashSet2.iterator();
                while (it.hasNext()) {
                    ComponentName componentName3 = (ComponentName) it.next();
                    if (!((HashMap) this.mRecordMap).containsKey(componentName3)) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            AbstractC0147x487e7be7.m27m("Adding listener record for ", componentName3, "NotifManCompat");
                        }
                        ((HashMap) this.mRecordMap).put(componentName3, new ListenerRecord(componentName3));
                    }
                }
                Iterator it2 = ((HashMap) this.mRecordMap).entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry entry = (Map.Entry) it2.next();
                    if (!hashSet2.contains(entry.getKey())) {
                        if (Log.isLoggable("NotifManCompat", 3)) {
                            Log.d("NotifManCompat", "Removing listener record for " + entry.getKey());
                        }
                        ListenerRecord listenerRecord4 = (ListenerRecord) entry.getValue();
                        if (listenerRecord4.bound) {
                            this.mContext.unbindService(this);
                            listenerRecord4.bound = false;
                        }
                        listenerRecord4.service = null;
                        it2.remove();
                    }
                }
            }
            for (ListenerRecord listenerRecord5 : ((HashMap) this.mRecordMap).values()) {
                listenerRecord5.taskQueue.add(task);
                processListenerQueue(listenerRecord5);
            }
            return true;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                AbstractC0147x487e7be7.m27m("Connected to service ", componentName, "NotifManCompat");
            }
            this.mHandler.obtainMessage(1, new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                AbstractC0147x487e7be7.m27m("Disconnected from service ", componentName, "NotifManCompat");
            }
            this.mHandler.obtainMessage(2, componentName).sendToTarget();
        }

        public final void processListenerQueue(ListenerRecord listenerRecord) {
            boolean z;
            boolean isLoggable = Log.isLoggable("NotifManCompat", 3);
            ComponentName componentName = listenerRecord.componentName;
            ArrayDeque arrayDeque = listenerRecord.taskQueue;
            if (isLoggable) {
                Log.d("NotifManCompat", "Processing component " + componentName + ", " + arrayDeque.size() + " queued tasks");
            }
            if (arrayDeque.isEmpty()) {
                return;
            }
            if (listenerRecord.bound) {
                z = true;
            } else {
                boolean bindService = this.mContext.bindService(new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL").setComponent(componentName), this, 33);
                listenerRecord.bound = bindService;
                if (bindService) {
                    listenerRecord.retryCount = 0;
                } else {
                    Log.w("NotifManCompat", "Unable to bind to listener " + componentName);
                    this.mContext.unbindService(this);
                }
                z = listenerRecord.bound;
            }
            if (!z || listenerRecord.service == null) {
                scheduleListenerRetry(listenerRecord);
                return;
            }
            while (true) {
                Task task = (Task) arrayDeque.peek();
                if (task == null) {
                    break;
                }
                try {
                    if (Log.isLoggable("NotifManCompat", 3)) {
                        Log.d("NotifManCompat", "Sending task " + task);
                    }
                    task.send(listenerRecord.service);
                    arrayDeque.remove();
                } catch (DeadObjectException unused) {
                    if (Log.isLoggable("NotifManCompat", 3)) {
                        AbstractC0147x487e7be7.m27m("Remote service has died: ", componentName, "NotifManCompat");
                    }
                } catch (RemoteException e) {
                    Log.w("NotifManCompat", "RemoteException communicating with " + componentName, e);
                }
            }
            if (arrayDeque.isEmpty()) {
                return;
            }
            scheduleListenerRetry(listenerRecord);
        }

        public final void scheduleListenerRetry(ListenerRecord listenerRecord) {
            Handler handler = this.mHandler;
            ComponentName componentName = listenerRecord.componentName;
            if (handler.hasMessages(3, componentName)) {
                return;
            }
            int i = listenerRecord.retryCount + 1;
            listenerRecord.retryCount = i;
            if (i <= 6) {
                int i2 = (1 << (i - 1)) * 1000;
                if (Log.isLoggable("NotifManCompat", 3)) {
                    AbstractC0147x487e7be7.m26m("Scheduling retry for ", i2, " ms", "NotifManCompat");
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, componentName), i2);
                return;
            }
            StringBuilder sb = new StringBuilder("Giving up on delivering ");
            ArrayDeque arrayDeque = listenerRecord.taskQueue;
            sb.append(arrayDeque.size());
            sb.append(" tasks to ");
            sb.append(componentName);
            sb.append(" after ");
            sb.append(listenerRecord.retryCount);
            sb.append(" retries");
            Log.w("NotifManCompat", sb.toString());
            arrayDeque.clear();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Task {
        void send(INotificationSideChannel iNotificationSideChannel);
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }
}
