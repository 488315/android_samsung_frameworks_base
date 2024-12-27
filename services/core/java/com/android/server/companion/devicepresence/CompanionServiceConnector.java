package com.android.server.companion.devicepresence;

import android.companion.AssociationInfo;
import android.companion.ICompanionDeviceService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.companion.CompanionDeviceManagerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class CompanionServiceConnector extends ServiceConnector.Impl {
    public static volatile ServiceThread sServiceThread;
    private final ComponentName mComponentName;
    private final boolean mIsPrimary;
    private Listener mListener;
    private final int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {
    }

    public CompanionServiceConnector(int i, int i2, ComponentName componentName, Context context, boolean z) {
        super(context, new Intent("android.companion.CompanionDeviceService").setComponent(componentName), i2, i, (Function) null);
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mIsPrimary = z;
    }

    public final IInterface binderAsInterface(IBinder iBinder) {
        return ICompanionDeviceService.Stub.asInterface(iBinder);
    }

    public final void binderDied() {
        boolean contains;
        super.binderDied();
        Slog.d("CDM_CompanionServiceConnector", "binderDied() " + this.mComponentName.toShortString());
        Listener listener = this.mListener;
        if (listener != null) {
            final int i = this.mUserId;
            final String packageName = this.mComponentName.getPackageName();
            DevicePresenceProcessor devicePresenceProcessor = ((DevicePresenceProcessor$$ExternalSyntheticLambda0) listener).f$0;
            devicePresenceProcessor.getClass();
            boolean z = this.mIsPrimary;
            HeimdAllFsService$$ExternalSyntheticOutline0.m("CDM_DevicePresenceProcessor", DirEncryptService$$ExternalSyntheticOutline0.m(i, "onBinderDied() u", "/", packageName, " isPrimary: "), z);
            boolean z2 = false;
            if (z) {
                Iterator it = devicePresenceProcessor.mAssociationStore.getActiveAssociationsByPackage(i, packageName).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String deviceProfile = ((AssociationInfo) it.next()).getDeviceProfile();
                    if ("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION".equals(deviceProfile)) {
                        Slog.i("CDM_DevicePresenceProcessor", "Disable hint mode for device profile: " + deviceProfile);
                        devicePresenceProcessor.mPowerManagerInternal.setPowerMode(18, false);
                        break;
                    }
                }
                CompanionAppBinder companionAppBinder = devicePresenceProcessor.mCompanionAppBinder;
                synchronized (companionAppBinder.mBoundCompanionApplications) {
                    ((HashMap) companionAppBinder.mBoundCompanionApplications).remove(new Pair(Integer.valueOf(i), packageName));
                }
                synchronized (companionAppBinder.mScheduledForRebindingCompanionApplications) {
                    ((HashSet) companionAppBinder.mScheduledForRebindingCompanionApplications).remove(new Pair(Integer.valueOf(i), packageName));
                }
            }
            List observableUuidsForPackage = devicePresenceProcessor.mObservableUuidStore.getObservableUuidsForPackage(i, packageName);
            boolean z3 = false;
            boolean z4 = false;
            for (AssociationInfo associationInfo : devicePresenceProcessor.mAssociationStore.getActiveAssociationsByPackage(i, packageName)) {
                int id = associationInfo.getId();
                if (associationInfo.isSelfManaged()) {
                    if (z && devicePresenceProcessor.isDevicePresent(id)) {
                        devicePresenceProcessor.onDevicePresenceEvent(devicePresenceProcessor.mReportedSelfManagedDevices, id, 5);
                    }
                    z4 = devicePresenceProcessor.mCompanionAppBinder.isCompanionApplicationBound(i, packageName);
                } else if (associationInfo.isNotifyOnDeviceNearby()) {
                    z4 = true;
                }
                z3 = true;
            }
            Iterator it2 = ((ArrayList) observableUuidsForPackage).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (((HashSet) devicePresenceProcessor.mConnectedUuidDevices).contains(((ObservableUuid) it2.next()).mUuid)) {
                    z2 = true;
                    break;
                }
            }
            if ((z3 && z4) || z2) {
                final CompanionAppBinder companionAppBinder2 = devicePresenceProcessor.mCompanionAppBinder;
                companionAppBinder2.getClass();
                Slog.i("CDM_CompanionAppBinder", "scheduleRebinding() " + i + "/" + packageName);
                synchronized (companionAppBinder2.mScheduledForRebindingCompanionApplications) {
                    contains = ((HashSet) companionAppBinder2.mScheduledForRebindingCompanionApplications).contains(new Pair(Integer.valueOf(i), packageName));
                }
                if (contains) {
                    Slog.i("CDM_CompanionAppBinder", "CompanionApplication rebinding has been scheduled, skipping " + this.mComponentName);
                    return;
                }
                if (this.mIsPrimary) {
                    synchronized (companionAppBinder2.mScheduledForRebindingCompanionApplications) {
                        ((HashSet) companionAppBinder2.mScheduledForRebindingCompanionApplications).add(new Pair(Integer.valueOf(i), packageName));
                    }
                }
                Handler.getMain().postDelayed(new Runnable() { // from class: com.android.server.companion.devicepresence.CompanionAppBinder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CompanionAppBinder companionAppBinder3 = CompanionAppBinder.this;
                        int i2 = i;
                        String str = packageName;
                        CompanionServiceConnector companionServiceConnector = this;
                        companionAppBinder3.getClass();
                        if (companionServiceConnector.isPrimary()) {
                            synchronized (companionAppBinder3.mBoundCompanionApplications) {
                                try {
                                    if (!((HashMap) companionAppBinder3.mBoundCompanionApplications).containsKey(new Pair(Integer.valueOf(i2), str))) {
                                        List singletonList = Collections.singletonList(companionServiceConnector);
                                        ((HashMap) companionAppBinder3.mBoundCompanionApplications).put(new Pair(Integer.valueOf(i2), str), singletonList);
                                    }
                                } finally {
                                }
                            }
                            synchronized (companionAppBinder3.mScheduledForRebindingCompanionApplications) {
                                ((HashSet) companionAppBinder3.mScheduledForRebindingCompanionApplications).remove(new Pair(Integer.valueOf(i2), str));
                            }
                        }
                        companionServiceConnector.connect();
                    }
                }, 10000L);
            }
        }
    }

    public final long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public final Handler getJobHandler() {
        if (sServiceThread == null) {
            synchronized (CompanionDeviceManagerService.class) {
                try {
                    if (sServiceThread == null) {
                        sServiceThread = new ServiceThread(0, "companion-device-service-connector", false);
                        sServiceThread.start();
                    }
                } finally {
                }
            }
        }
        return sServiceThread.getThreadHandler();
    }

    public final boolean isPrimary() {
        return this.mIsPrimary;
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        Slog.d("CDM_CompanionServiceConnector", "onServiceConnectionStatusChanged() " + this.mComponentName.toShortString() + " connected=" + z);
    }

    public final void setListener(DevicePresenceProcessor$$ExternalSyntheticLambda0 devicePresenceProcessor$$ExternalSyntheticLambda0) {
        this.mListener = devicePresenceProcessor$$ExternalSyntheticLambda0;
    }
}
