package com.android.server.backup;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.Preconditions;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.OnTransportRegisteredListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportConnectionManager;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportNotRegisteredException;
import com.android.server.backup.transport.TransportStats;
import com.android.server.backup.transport.TransportStats$$ExternalSyntheticLambda0;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TransportManager {
    public static final String SERVICE_ACTION_TRANSPORT_HOST = "android.backup.TRANSPORT_HOST";
    public volatile String mCurrentTransportName;
    public OnTransportRegisteredListener mOnTransportRegisteredListener;
    public final PackageManager mPackageManager;
    public final Map mRegisteredTransportsDescriptionMap;
    public final TransportConnectionManager mTransportConnectionManager;
    public final Object mTransportLock;
    public final Intent mTransportServiceIntent;
    public final TransportStats mTransportStats;
    public final Set mTransportWhitelist;
    public final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransportDescription {
        public Intent configurationIntent;
        public String currentDestinationString;
        public Intent dataManagementIntent;
        public CharSequence dataManagementLabel;
        public String name;
        public final String transportDirName;

        public TransportDescription(
                String str,
                String str2,
                Intent intent,
                String str3,
                Intent intent2,
                CharSequence charSequence) {
            this.name = str;
            this.transportDirName = str2;
            this.configurationIntent = intent;
            this.currentDestinationString = str3;
            this.dataManagementIntent = intent2;
            this.dataManagementLabel = charSequence;
        }
    }

    public TransportManager(int i, Context context, Set set, String str) {
        this.mTransportServiceIntent = new Intent(SERVICE_ACTION_TRANSPORT_HOST);
        this.mOnTransportRegisteredListener = new TransportManager$$ExternalSyntheticLambda0();
        this.mTransportLock = new Object();
        this.mRegisteredTransportsDescriptionMap = new ArrayMap();
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
        this.mTransportWhitelist = (Set) Preconditions.checkNotNull(set);
        this.mCurrentTransportName = str;
        TransportStats transportStats = new TransportStats();
        this.mTransportStats = transportStats;
        this.mTransportConnectionManager =
                new TransportConnectionManager(i, context, transportStats);
    }

    public TransportManager(
            int i,
            Context context,
            Set set,
            String str,
            TransportConnectionManager transportConnectionManager) {
        this.mTransportServiceIntent = new Intent(SERVICE_ACTION_TRANSPORT_HOST);
        this.mOnTransportRegisteredListener = new TransportManager$$ExternalSyntheticLambda0();
        this.mTransportLock = new Object();
        this.mRegisteredTransportsDescriptionMap = new ArrayMap();
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
        this.mTransportWhitelist = (Set) Preconditions.checkNotNull(set);
        this.mCurrentTransportName = str;
        this.mTransportStats = new TransportStats();
        this.mTransportConnectionManager = transportConnectionManager;
    }

    public static String addUserIdToLogMessage(int i, String str) {
        return AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "[UserID:", "] ", str);
    }

    public final void disposeOfTransportClient(
            TransportConnection transportConnection, String str) {
        this.mTransportConnectionManager.disposeOfTransportClient(transportConnection, str);
    }

    public final void dumpTransportClients(PrintWriter printWriter) {
        List unmodifiableList;
        TransportConnectionManager transportConnectionManager = this.mTransportConnectionManager;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                new StringBuilder("Transport clients created: "),
                transportConnectionManager.mTransportClientsCreated,
                printWriter);
        synchronized (transportConnectionManager.mTransportClientsLock) {
            printWriter.println(
                    "Current transport clients: "
                            + ((WeakHashMap) transportConnectionManager.mTransportClientsCallerMap)
                                    .size());
            for (TransportConnection transportConnection :
                    ((WeakHashMap) transportConnectionManager.mTransportClientsCallerMap)
                            .keySet()) {
                printWriter.println(
                        "    "
                                + transportConnection
                                + " ["
                                + ((String)
                                        ((WeakHashMap)
                                                        transportConnectionManager
                                                                .mTransportClientsCallerMap)
                                                .get(transportConnection))
                                + "]");
                synchronized (transportConnection.mLogBufferLock) {
                    unmodifiableList = Collections.unmodifiableList(transportConnection.mLogBuffer);
                }
                Iterator it = unmodifiableList.iterator();
                while (it.hasNext()) {
                    printWriter.println("        " + ((String) it.next()));
                }
            }
        }
    }

    public final void dumpTransportStats(PrintWriter printWriter) {
        TransportStats transportStats = this.mTransportStats;
        synchronized (transportStats.mStatsLock) {
            try {
                Optional reduce =
                        ((HashMap) transportStats.mTransportStats)
                                .values().stream()
                                        .reduce(new TransportStats$$ExternalSyntheticLambda0());
                if (reduce.isPresent()) {
                    TransportStats.dumpStats(printWriter, "", (TransportStats.Stats) reduce.get());
                }
                if (!((HashMap) transportStats.mTransportStats).isEmpty()) {
                    printWriter.println("Per transport:");
                    for (ComponentName componentName :
                            ((HashMap) transportStats.mTransportStats).keySet()) {
                        TransportStats.Stats stats =
                                (TransportStats.Stats)
                                        ((HashMap) transportStats.mTransportStats)
                                                .get(componentName);
                        printWriter.println("    " + componentName.flattenToShortString());
                        TransportStats.dumpStats(printWriter, "        ", stats);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forEachRegisteredTransport(Consumer consumer) {
        synchronized (this.mTransportLock) {
            try {
                Iterator it =
                        ((ArrayMap) this.mRegisteredTransportsDescriptionMap).values().iterator();
                while (it.hasNext()) {
                    consumer.accept(((TransportDescription) it.next()).name);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final TransportConnection getCurrentTransportClient(String str) {
        TransportConnection transportClient;
        if (this.mCurrentTransportName == null) {
            throw new IllegalStateException("No transport selected");
        }
        synchronized (this.mTransportLock) {
            transportClient = getTransportClient(this.mCurrentTransportName, str);
        }
        return transportClient;
    }

    public final TransportConnection getCurrentTransportClientOrThrow() {
        TransportConnection transportClientOrThrow;
        if (this.mCurrentTransportName == null) {
            throw new IllegalStateException("No transport selected");
        }
        synchronized (this.mTransportLock) {
            transportClientOrThrow =
                    getTransportClientOrThrow(this.mCurrentTransportName, "BMS.requestBackup()");
        }
        return transportClientOrThrow;
    }

    public final ComponentName getCurrentTransportComponent() {
        synchronized (this.mTransportLock) {
            try {
                ComponentName componentName = null;
                if (this.mCurrentTransportName == null) {
                    return null;
                }
                String str = this.mCurrentTransportName;
                Map.Entry registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
                if (registeredTransportEntryLocked != null) {
                    componentName = (ComponentName) registeredTransportEntryLocked.getKey();
                }
                if (componentName != null) {
                    return componentName;
                }
                throw new TransportNotRegisteredException(str);
            } finally {
            }
        }
    }

    public final TransportDescription getRegisteredTransportDescriptionOrThrowLocked(
            ComponentName componentName) {
        TransportDescription transportDescription =
                (TransportDescription) this.mRegisteredTransportsDescriptionMap.get(componentName);
        if (transportDescription != null) {
            return transportDescription;
        }
        throw new TransportNotRegisteredException(
                "Transport for host " + componentName + " not registered");
    }

    public final TransportDescription getRegisteredTransportDescriptionOrThrowLocked(String str) {
        Map.Entry registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
        TransportDescription transportDescription =
                registeredTransportEntryLocked == null
                        ? null
                        : (TransportDescription) registeredTransportEntryLocked.getValue();
        if (transportDescription != null) {
            return transportDescription;
        }
        throw new TransportNotRegisteredException(str);
    }

    public final Map.Entry getRegisteredTransportEntryLocked(String str) {
        for (Map.Entry entry : this.mRegisteredTransportsDescriptionMap.entrySet()) {
            if (str.equals(((TransportDescription) entry.getValue()).name)) {
                return entry;
            }
        }
        return null;
    }

    public final TransportConnection getTransportClient(String str, String str2) {
        try {
            return getTransportClientOrThrow(str, str2);
        } catch (TransportNotRegisteredException unused) {
            Slog.w(
                    "BackupTransportManager",
                    addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered"));
            return null;
        }
    }

    public final TransportConnection getTransportClientOrThrow(String str, String str2) {
        TransportConnection transportClient;
        synchronized (this.mTransportLock) {
            try {
                Map.Entry registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
                ComponentName componentName =
                        registeredTransportEntryLocked == null
                                ? null
                                : (ComponentName) registeredTransportEntryLocked.getKey();
                if (componentName == null) {
                    throw new TransportNotRegisteredException(str);
                }
                transportClient =
                        this.mTransportConnectionManager.getTransportClient(
                                componentName, null, str2);
            } finally {
            }
        }
        return transportClient;
    }

    public final String getTransportDirName(ComponentName componentName) {
        String str;
        synchronized (this.mTransportLock) {
            str = getRegisteredTransportDescriptionOrThrowLocked(componentName).transportDirName;
        }
        return str;
    }

    public final String getTransportDirName(String str) {
        String str2;
        synchronized (this.mTransportLock) {
            str2 = getRegisteredTransportDescriptionOrThrowLocked(str).transportDirName;
        }
        return str2;
    }

    public final String getTransportName(ComponentName componentName) {
        String str;
        synchronized (this.mTransportLock) {
            str = getRegisteredTransportDescriptionOrThrowLocked(componentName).name;
        }
        return str;
    }

    public final boolean isTransportRegistered(String str) {
        boolean z;
        synchronized (this.mTransportLock) {
            z = getRegisteredTransportEntryLocked(str) != null;
        }
        return z;
    }

    public final boolean isTransportTrusted(ComponentName componentName) {
        boolean contains = this.mTransportWhitelist.contains(componentName);
        int i = this.mUserId;
        if (!contains) {
            Slog.w(
                    "BackupTransportManager",
                    addUserIdToLogMessage(
                            i,
                            "BackupTransport "
                                    + componentName.flattenToShortString()
                                    + " not whitelisted."));
            return false;
        }
        try {
            if ((this.mPackageManager.getPackageInfoAsUser(componentName.getPackageName(), 0, i)
                                    .applicationInfo
                                    .privateFlags
                            & 8)
                    != 0) {
                return true;
            }
            Slog.w(
                    "BackupTransportManager",
                    addUserIdToLogMessage(
                            i,
                            "Transport package "
                                    + componentName.getPackageName()
                                    + " not privileged"));
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.w("BackupTransportManager", addUserIdToLogMessage(i, "Package not found."), e);
            return false;
        }
    }

    public final void onPackageRemoved(String str) {
        synchronized (this.mTransportLock) {
            this.mRegisteredTransportsDescriptionMap
                    .keySet()
                    .removeIf(new TransportManager$$ExternalSyntheticLambda2(1, str));
        }
    }

    public final int registerTransport(ComponentName componentName) {
        String name;
        String str;
        int i = this.mUserId;
        Preconditions.checkState(
                !Thread.holdsLock(this.mTransportLock),
                "Can't call transport with transport lock held");
        if (!isTransportTrusted(componentName)) {
            return -2;
        }
        String flattenToShortString = componentName.flattenToShortString();
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.app.backup.extra.TRANSPORT_REGISTRATION", true);
        TransportConnectionManager transportConnectionManager = this.mTransportConnectionManager;
        TransportConnection transportClient =
                transportConnectionManager.getTransportClient(
                        componentName, bundle, "TransportManager.registerTransport()");
        int i2 = -1;
        try {
            BackupTransportClient connectOrThrow =
                    transportClient.connectOrThrow("TransportManager.registerTransport()");
            try {
                name = connectOrThrow.name();
                AndroidFuture newFuture = connectOrThrow.mTransportFutures.newFuture();
                connectOrThrow.mTransportBinder.transportDirName(newFuture);
                str = (String) connectOrThrow.getFutureResult(newFuture);
            } catch (RemoteException unused) {
                Slog.e(
                        "BackupTransportManager",
                        addUserIdToLogMessage(
                                i,
                                "Transport " + flattenToShortString + " died while registering"));
            }
            if (name != null && str != null) {
                registerTransport(componentName, connectOrThrow);
                Slog.d(
                        "BackupTransportManager",
                        addUserIdToLogMessage(
                                i, "Transport " + flattenToShortString + " registered"));
                this.mOnTransportRegisteredListener.onTransportRegistered(name, str);
                i2 = 0;
                transportConnectionManager.disposeOfTransportClient(
                        transportClient, "TransportManager.registerTransport()");
                return i2;
            }
            return -2;
        } catch (TransportNotAvailableException unused2) {
            Slog.e(
                    "BackupTransportManager",
                    addUserIdToLogMessage(
                            i,
                            "Couldn't connect to transport "
                                    + flattenToShortString
                                    + " for registration"));
            transportConnectionManager.disposeOfTransportClient(
                    transportClient, "TransportManager.registerTransport()");
            return -1;
        }
    }

    public final void registerTransport(
            ComponentName componentName, BackupTransportClient backupTransportClient) {
        Preconditions.checkState(
                !Thread.holdsLock(this.mTransportLock),
                "Can't call transport with transport lock held");
        String name = backupTransportClient.name();
        AndroidFuture newFuture = backupTransportClient.mTransportFutures.newFuture();
        backupTransportClient.mTransportBinder.transportDirName(newFuture);
        String str = (String) backupTransportClient.getFutureResult(newFuture);
        AndroidFuture newFuture2 = backupTransportClient.mTransportFutures.newFuture();
        backupTransportClient.mTransportBinder.configurationIntent(newFuture2);
        Intent intent = (Intent) backupTransportClient.getFutureResult(newFuture2);
        AndroidFuture newFuture3 = backupTransportClient.mTransportFutures.newFuture();
        backupTransportClient.mTransportBinder.currentDestinationString(newFuture3);
        String str2 = (String) backupTransportClient.getFutureResult(newFuture3);
        AndroidFuture newFuture4 = backupTransportClient.mTransportFutures.newFuture();
        backupTransportClient.mTransportBinder.dataManagementIntent(newFuture4);
        Intent intent2 = (Intent) backupTransportClient.getFutureResult(newFuture4);
        AndroidFuture newFuture5 = backupTransportClient.mTransportFutures.newFuture();
        backupTransportClient.mTransportBinder.dataManagementIntentLabel(newFuture5);
        TransportDescription transportDescription =
                new TransportDescription(
                        name,
                        str,
                        intent,
                        str2,
                        intent2,
                        (CharSequence) backupTransportClient.getFutureResult(newFuture5));
        synchronized (this.mTransportLock) {
            ((ArrayMap) this.mRegisteredTransportsDescriptionMap)
                    .put(componentName, transportDescription);
        }
    }

    public final void registerTransportsForIntent(Intent intent, Predicate predicate) {
        List queryIntentServicesAsUser =
                this.mPackageManager.queryIntentServicesAsUser(intent, 0, this.mUserId);
        if (queryIntentServicesAsUser == null) {
            return;
        }
        Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            ComponentName componentName = ((ResolveInfo) it.next()).serviceInfo.getComponentName();
            if (predicate.test(componentName) && isTransportTrusted(componentName)) {
                registerTransport(componentName);
            }
        }
    }

    public final void registerTransportsFromPackage(String str, Predicate predicate) {
        int i = this.mUserId;
        try {
            this.mPackageManager.getPackageInfoAsUser(str, 0, i);
            registerTransportsForIntent(
                    new Intent(this.mTransportServiceIntent).setPackage(str),
                    predicate.and(new TransportManager$$ExternalSyntheticLambda2(1, str)));
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e(
                    "BackupTransportManager",
                    addUserIdToLogMessage(
                            i, "Trying to register transports from package not found " + str));
        }
    }

    public final String selectTransport(String str) {
        String str2;
        synchronized (this.mTransportLock) {
            str2 = this.mCurrentTransportName;
            this.mCurrentTransportName = str;
        }
        return str2;
    }

    public final void updateTransportAttributes(
            ComponentName componentName,
            String str,
            Intent intent,
            String str2,
            Intent intent2,
            CharSequence charSequence) {
        synchronized (this.mTransportLock) {
            try {
                TransportDescription transportDescription =
                        (TransportDescription)
                                this.mRegisteredTransportsDescriptionMap.get(componentName);
                if (transportDescription == null) {
                    Slog.e(
                            "BackupTransportManager",
                            addUserIdToLogMessage(
                                    this.mUserId,
                                    "Transport "
                                            + str
                                            + " not registered tried to change description"));
                    return;
                }
                transportDescription.name = str;
                transportDescription.configurationIntent = intent;
                transportDescription.currentDestinationString = str2;
                transportDescription.dataManagementIntent = intent2;
                transportDescription.dataManagementLabel = charSequence;
                Slog.d(
                        "BackupTransportManager",
                        addUserIdToLogMessage(
                                this.mUserId, "Transport " + str + " updated its attributes"));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
