package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.companion.IOnAssociationsChangedListener;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Slog;

import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.companion.utils.MetricUtils;
import com.android.server.companion.utils.PermissionsUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AssociationStore {
    public final Context mContext;
    public final AssociationDiskStore mDiskStore;
    public final UserManager mUserManager;
    public final Object mLock = new Object();
    public boolean mPersisted = false;
    public final Map mIdToAssociationMap = new HashMap();
    public int mMaxId = 0;
    public final Set mLocalListeners = new LinkedHashSet();
    public final RemoteCallbackList mRemoteListeners = new RemoteCallbackList();
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnChangeListener {
        default void onAssociationAdded(AssociationInfo associationInfo) {}

        default void onAssociationChanged(int i, AssociationInfo associationInfo) {
            if (i == 0) {
                onAssociationAdded(associationInfo);
            } else {
                if (i != 1) {
                    return;
                }
                onAssociationRemoved(associationInfo);
            }
        }

        default void onAssociationRemoved(AssociationInfo associationInfo) {}
    }

    public AssociationStore(
            Context context, UserManager userManager, AssociationDiskStore associationDiskStore) {
        this.mContext = context;
        this.mUserManager = userManager;
        this.mDiskStore = associationDiskStore;
    }

    public final void addAssociation(AssociationInfo associationInfo) {
        Slog.i("CDM_AssociationStore", "Adding new association=[" + associationInfo + "]...");
        int id = associationInfo.getId();
        int userId = associationInfo.getUserId();
        synchronized (this.mLock) {
            try {
                if (((HashMap) this.mIdToAssociationMap).containsKey(Integer.valueOf(id))) {
                    Slog.e("CDM_AssociationStore", "Association id=[" + id + "] already exists.");
                    return;
                }
                ((HashMap) this.mIdToAssociationMap).put(Integer.valueOf(id), associationInfo);
                this.mMaxId = Math.max(this.mMaxId, id);
                this.mExecutor.execute(
                        new AssociationStore$$ExternalSyntheticLambda9(this, userId));
                Slog.i("CDM_AssociationStore", "Done adding new association.");
                FrameworkStatsLog.write(
                        FrameworkStatsLog.CDM_ASSOCIATION_ACTION,
                        1,
                        ((Integer)
                                        MetricUtils.METRIC_DEVICE_PROFILE.get(
                                                associationInfo.getDeviceProfile()))
                                .intValue());
                if (associationInfo.isActive()) {
                    broadcastChange(0, associationInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void broadcastChange(int i, AssociationInfo associationInfo) {
        BootReceiver$$ExternalSyntheticOutline0.m(
                i,
                "Broadcasting association changes - changeType=[",
                "]...",
                "CDM_AssociationStore");
        synchronized (this.mLocalListeners) {
            try {
                Iterator it = this.mLocalListeners.iterator();
                while (it.hasNext()) {
                    ((OnChangeListener) it.next()).onAssociationChanged(i, associationInfo);
                }
            } finally {
            }
        }
        synchronized (this.mRemoteListeners) {
            try {
                final int userId = associationInfo.getUserId();
                final List activeAssociationsByUser = getActiveAssociationsByUser(userId);
                if (i != 3) {
                    this.mRemoteListeners.broadcast(
                            new BiConsumer() { // from class:
                                               // com.android.server.companion.association.AssociationStore$$ExternalSyntheticLambda11
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    int i2 = userId;
                                    List list = activeAssociationsByUser;
                                    IOnAssociationsChangedListener iOnAssociationsChangedListener =
                                            (IOnAssociationsChangedListener) obj;
                                    int intValue = ((Integer) obj2).intValue();
                                    if (intValue == i2 || intValue == -1) {
                                        try {
                                            iOnAssociationsChangedListener.onAssociationsChanged(
                                                    list);
                                        } catch (RemoteException unused) {
                                        }
                                    }
                                }
                            });
                }
            } finally {
            }
        }
    }

    public final List getActiveAssociations() {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getAssociations(), new AssociationStore$$ExternalSyntheticLambda2(0));
        }
        return filter;
    }

    public final List getActiveAssociationsByAddress(String str) {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getActiveAssociations(),
                            new AssociationStore$$ExternalSyntheticLambda0(str, 0));
        }
        return filter;
    }

    public final List getActiveAssociationsByPackage(int i, String str) {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getActiveAssociationsByUser(i),
                            new AssociationStore$$ExternalSyntheticLambda0(str, 1));
        }
        return filter;
    }

    public final List getActiveAssociationsByUser(int i) {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getActiveAssociations(),
                            new AssociationStore$$ExternalSyntheticLambda4(i, 0));
        }
        return filter;
    }

    public final AssociationInfo getAssociationById(int i) {
        AssociationInfo associationInfo;
        synchronized (this.mLock) {
            associationInfo =
                    (AssociationInfo) ((HashMap) this.mIdToAssociationMap).get(Integer.valueOf(i));
        }
        return associationInfo;
    }

    public final AssociationInfo getAssociationWithCallerChecks(int i) {
        AssociationInfo associationById = getAssociationById(i);
        if (associationById == null) {
            throw new IllegalArgumentException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i,
                            "getAssociationWithCallerChecks() Association id=[",
                            "] doesn't exist."));
        }
        PermissionsUtils.enforceCallerCanManageAssociationsForPackage(
                associationById.getUserId(), this.mContext, associationById.getPackageName(), null);
        return associationById;
    }

    public final List getAssociations() {
        List copyOf;
        synchronized (this.mLock) {
            if (!this.mPersisted) {
                Binder.withCleanCallingIdentity(
                        new AssociationStore$$ExternalSyntheticLambda3(this));
            }
            copyOf = List.copyOf(((HashMap) this.mIdToAssociationMap).values());
        }
        return copyOf;
    }

    public final List getAssociationsByPackage(int i, String str) {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getAssociationsByUser(i),
                            new AssociationStore$$ExternalSyntheticLambda0(str, 2));
        }
        return filter;
    }

    public final List getAssociationsByUser(int i) {
        List filter;
        synchronized (this.mLock) {
            filter =
                    CollectionUtils.filter(
                            getAssociations(),
                            new AssociationStore$$ExternalSyntheticLambda4(i, 1));
        }
        return filter;
    }

    public final AssociationInfo getFirstAssociationByAddress(int i, String str, String str2) {
        AssociationInfo associationInfo;
        synchronized (this.mLock) {
            associationInfo =
                    (AssociationInfo)
                            CollectionUtils.find(
                                    getActiveAssociationsByPackage(i, str),
                                    new AssociationStore$$ExternalSyntheticLambda0(str2, 3));
        }
        return associationInfo;
    }

    public final void registerLocalListener(OnChangeListener onChangeListener) {
        synchronized (this.mLocalListeners) {
            this.mLocalListeners.add(onChangeListener);
        }
    }

    public final void updateAssociation(AssociationInfo associationInfo) {
        Slog.i("CDM_AssociationStore", "Updating new association=[" + associationInfo + "]...");
        int id = associationInfo.getId();
        synchronized (this.mLock) {
            try {
                AssociationInfo associationInfo2 =
                        (AssociationInfo)
                                ((HashMap) this.mIdToAssociationMap).get(Integer.valueOf(id));
                if (associationInfo2 == null) {
                    Slog.w(
                            "CDM_AssociationStore",
                            "Can't update association id=[" + id + "]. It does not exist.");
                    return;
                }
                if (associationInfo2.equals(associationInfo)) {
                    Slog.w("CDM_AssociationStore", "Association is the same.");
                    return;
                }
                ((HashMap) this.mIdToAssociationMap).put(Integer.valueOf(id), associationInfo);
                this.mExecutor.execute(
                        new AssociationStore$$ExternalSyntheticLambda9(
                                this, associationInfo.getUserId()));
                Slog.i("CDM_AssociationStore", "Done updating association.");
                if (associationInfo2.isActive() && !associationInfo.isActive()) {
                    broadcastChange(1, associationInfo);
                } else if (associationInfo.isActive()) {
                    broadcastChange(
                            Objects.equals(
                                                    associationInfo2.getDeviceMacAddress(),
                                                    associationInfo.getDeviceMacAddress())
                                            ^ true
                                    ? 2
                                    : 3,
                            associationInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
