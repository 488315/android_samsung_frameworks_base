package androidx.core.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
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
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class NotificationManagerCompat {
    public static String sEnabledNotificationListeners;
    public static SideChannelManager sSideChannelManager;
    public final Context mContext;
    public final NotificationManager mNotificationManager;
    public static final Object sEnabledNotificationListenersLock = new Object();
    public static Set sEnabledNotificationListenerPackages = new HashSet();
    public static final Object sLock = new Object();

    public class NotifyTask {
        public final int id;
        public final Notification notif;
        public final String packageName;
        public final String tag;

        public NotifyTask(String str, int i, String str2, Notification notification2) {
            this.packageName = str;
            this.id = i;
            this.tag = str2;
            this.notif = notification2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("NotifyTask[packageName:");
            sb.append(this.packageName);
            sb.append(", id:");
            sb.append(this.id);
            sb.append(", tag:");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.tag, "]");
        }
    }

    public class ServiceConnectedEvent {
        public final ComponentName componentName;
        public final IBinder iBinder;

        public ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    public class SideChannelManager implements Handler.Callback, ServiceConnection {
        public final Context mContext;
        public final Handler mHandler;
        public final Map mRecordMap = new HashMap();
        public Set mCachedEnabledPackages = new HashSet();

        public class ListenerRecord {
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
            INotificationSideChannel proxy = null;
            if (i == 0) {
                NotifyTask notifyTask = (NotifyTask) message.obj;
                Context context = this.mContext;
                Object obj = NotificationManagerCompat.sEnabledNotificationListenersLock;
                String string = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
                synchronized (NotificationManagerCompat.sEnabledNotificationListenersLock) {
                    if (string != null) {
                        try {
                            if (!string.equals(NotificationManagerCompat.sEnabledNotificationListeners)) {
                                String[] strArrSplit = string.split(":", -1);
                                HashSet hashSet = new HashSet(strArrSplit.length);
                                for (String str : strArrSplit) {
                                    ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
                                    if (componentNameUnflattenFromString != null) {
                                        hashSet.add(componentNameUnflattenFromString.getPackageName());
                                    }
                                }
                                NotificationManagerCompat.sEnabledNotificationListenerPackages = hashSet;
                                NotificationManagerCompat.sEnabledNotificationListeners = string;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    set = NotificationManagerCompat.sEnabledNotificationListenerPackages;
                }
                if (!set.equals(this.mCachedEnabledPackages)) {
                    this.mCachedEnabledPackages = set;
                    List<ResolveInfo> listQueryIntentServices = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction("android.support.BIND_NOTIFICATION_SIDE_CHANNEL"), 0);
                    HashSet hashSet2 = new HashSet();
                    for (ResolveInfo resolveInfo : listQueryIntentServices) {
                        if (((HashSet) set).contains(resolveInfo.serviceInfo.packageName)) {
                            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                            ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                            if (resolveInfo.serviceInfo.permission != null) {
                                Log.w("NotifManCompat", "Permission present on component " + componentName + ", not adding listener record.");
                            } else {
                                hashSet2.add(componentName);
                            }
                        }
                    }
                    Iterator it = hashSet2.iterator();
                    while (it.hasNext()) {
                        ComponentName componentName2 = (ComponentName) it.next();
                        if (!((HashMap) this.mRecordMap).containsKey(componentName2)) {
                            if (Log.isLoggable("NotifManCompat", 3)) {
                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Adding listener record for ", componentName2, "NotifManCompat");
                            }
                            ((HashMap) this.mRecordMap).put(componentName2, new ListenerRecord(componentName2));
                        }
                    }
                    Iterator it2 = ((HashMap) this.mRecordMap).entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry entry = (Map.Entry) it2.next();
                        if (!hashSet2.contains(entry.getKey())) {
                            if (Log.isLoggable("NotifManCompat", 3)) {
                                Log.d("NotifManCompat", "Removing listener record for " + entry.getKey());
                            }
                            ListenerRecord listenerRecord = (ListenerRecord) entry.getValue();
                            if (listenerRecord.bound) {
                                this.mContext.unbindService(this);
                                listenerRecord.bound = false;
                            }
                            listenerRecord.service = null;
                            it2.remove();
                        }
                    }
                }
                for (ListenerRecord listenerRecord2 : ((HashMap) this.mRecordMap).values()) {
                    listenerRecord2.taskQueue.add(notifyTask);
                    processListenerQueue(listenerRecord2);
                }
            } else if (i == 1) {
                ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj;
                ComponentName componentName3 = serviceConnectedEvent.componentName;
                IBinder iBinder = serviceConnectedEvent.iBinder;
                ListenerRecord listenerRecord3 = (ListenerRecord) ((HashMap) this.mRecordMap).get(componentName3);
                if (listenerRecord3 != null) {
                    int i2 = INotificationSideChannel.Stub.$r8$clinit;
                    if (iBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INotificationSideChannel.DESCRIPTOR);
                        proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INotificationSideChannel)) ? new INotificationSideChannel.Stub.Proxy(iBinder) : (INotificationSideChannel) iInterfaceQueryLocalInterface;
                    }
                    listenerRecord3.service = proxy;
                    listenerRecord3.retryCount = 0;
                    processListenerQueue(listenerRecord3);
                    return true;
                }
            } else if (i == 2) {
                ListenerRecord listenerRecord4 = (ListenerRecord) ((HashMap) this.mRecordMap).get((ComponentName) message.obj);
                if (listenerRecord4 != null) {
                    if (listenerRecord4.bound) {
                        this.mContext.unbindService(this);
                        listenerRecord4.bound = false;
                    }
                    listenerRecord4.service = null;
                    return true;
                }
            } else {
                if (i != 3) {
                    return false;
                }
                ListenerRecord listenerRecord5 = (ListenerRecord) ((HashMap) this.mRecordMap).get((ComponentName) message.obj);
                if (listenerRecord5 != null) {
                    processListenerQueue(listenerRecord5);
                    return true;
                }
            }
            return true;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Connected to service ", componentName, "NotifManCompat");
            }
            this.mHandler.obtainMessage(1, new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable("NotifManCompat", 3)) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Disconnected from service ", componentName, "NotifManCompat");
            }
            this.mHandler.obtainMessage(2, componentName).sendToTarget();
        }

        public final void processListenerQueue(ListenerRecord listenerRecord) {
            boolean z;
            if (Log.isLoggable("NotifManCompat", 3)) {
                Log.d("NotifManCompat", "Processing component " + listenerRecord.componentName + ", " + listenerRecord.taskQueue.size() + " queued tasks");
            }
            if (listenerRecord.taskQueue.isEmpty()) {
                return;
            }
            if (listenerRecord.bound) {
                z = true;
            } else {
                boolean zBindService = this.mContext.bindService(new Intent("android.support.BIND_NOTIFICATION_SIDE_CHANNEL").setComponent(listenerRecord.componentName), this, 33);
                listenerRecord.bound = zBindService;
                if (zBindService) {
                    listenerRecord.retryCount = 0;
                } else {
                    Log.w("NotifManCompat", "Unable to bind to listener " + listenerRecord.componentName);
                    this.mContext.unbindService(this);
                }
                z = listenerRecord.bound;
            }
            if (!z || listenerRecord.service == null) {
                scheduleListenerRetry(listenerRecord);
                return;
            }
            while (true) {
                NotifyTask notifyTask = (NotifyTask) listenerRecord.taskQueue.peek();
                if (notifyTask == null) {
                    break;
                }
                try {
                    if (Log.isLoggable("NotifManCompat", 3)) {
                        Log.d("NotifManCompat", "Sending task " + notifyTask);
                    }
                    ((INotificationSideChannel.Stub.Proxy) listenerRecord.service).notify(notifyTask.packageName, notifyTask.id, notifyTask.tag, notifyTask.notif);
                    listenerRecord.taskQueue.remove();
                } catch (DeadObjectException unused) {
                    if (Log.isLoggable("NotifManCompat", 3)) {
                        Log.d("NotifManCompat", "Remote service has died: " + listenerRecord.componentName);
                    }
                } catch (RemoteException e) {
                    Log.w("NotifManCompat", "RemoteException communicating with " + listenerRecord.componentName, e);
                }
            }
            if (listenerRecord.taskQueue.isEmpty()) {
                return;
            }
            scheduleListenerRetry(listenerRecord);
        }

        public final void scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (this.mHandler.hasMessages(3, listenerRecord.componentName)) {
                return;
            }
            int i = listenerRecord.retryCount;
            int i2 = i + 1;
            listenerRecord.retryCount = i2;
            if (i2 <= 6) {
                int i3 = (1 << i) * 1000;
                if (Log.isLoggable("NotifManCompat", 3)) {
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i3, "Scheduling retry for ", " ms", "NotifManCompat");
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, listenerRecord.componentName), i3);
                return;
            }
            Log.w("NotifManCompat", "Giving up on delivering " + listenerRecord.taskQueue.size() + " tasks to " + listenerRecord.componentName + " after " + listenerRecord.retryCount + " retries");
            listenerRecord.taskQueue.clear();
        }
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }

    public final void notify(int i, Notification notification2) {
        Bundle bundle = notification2.extras;
        if (bundle == null || !bundle.getBoolean("android.support.useSideChannel")) {
            this.mNotificationManager.notify(null, i, notification2);
            return;
        }
        NotifyTask notifyTask = new NotifyTask(this.mContext.getPackageName(), i, null, notification2);
        synchronized (sLock) {
            try {
                if (sSideChannelManager == null) {
                    sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
                }
                sSideChannelManager.mHandler.obtainMessage(0, notifyTask).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mNotificationManager.cancel(null, i);
    }

    public NotificationManagerCompat(NotificationManager notificationManager, Context context) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
    }
}
