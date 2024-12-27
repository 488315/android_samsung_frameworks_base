package com.android.server.broadcastradio.hal2;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class AnnouncementAggregator extends ICloseHandle.Stub {
    public final DeathRecipient mDeathRecipient;
    public boolean mIsClosed;
    public final IAnnouncementListener mListener;
    public final Object mLock;
    public final Collection mModuleWatchers;

    public final class DeathRecipient implements IBinder.DeathRecipient {
        public DeathRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                AnnouncementAggregator.this.close();
            } catch (RemoteException e) {
                Slogf.e(
                        "BcRadio2Srv.AnnAggr",
                        e,
                        "Cannot close Announcement aggregator for DeathRecipient",
                        new Object[0]);
            }
        }
    }

    public final class ModuleWatcher extends IAnnouncementListener.Stub {
        public List currentList = new ArrayList();
        public ICloseHandle mCloseHandle;

        public ModuleWatcher() {}

        public final void onListUpdated(List list) {
            Objects.requireNonNull(list);
            this.currentList = list;
            AnnouncementAggregator announcementAggregator = AnnouncementAggregator.this;
            synchronized (announcementAggregator.mLock) {
                try {
                    if (announcementAggregator.mIsClosed) {
                        Slogf.e(
                                "BcRadio2Srv.AnnAggr",
                                "Announcement aggregator is closed, it shouldn't receive"
                                    + " callbacks");
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((ArrayList) announcementAggregator.mModuleWatchers).iterator();
                    while (it.hasNext()) {
                        arrayList.addAll(((ModuleWatcher) it.next()).currentList);
                    }
                    try {
                        announcementAggregator.mListener.onListUpdated(arrayList);
                    } catch (RemoteException e) {
                        Slogf.e("BcRadio2Srv.AnnAggr", "mListener.onListUpdated() failed: ", e);
                    }
                } finally {
                }
            }
        }
    }

    public AnnouncementAggregator(IAnnouncementListener iAnnouncementListener, Object obj) {
        DeathRecipient deathRecipient = new DeathRecipient();
        this.mDeathRecipient = deathRecipient;
        this.mModuleWatchers = new ArrayList();
        this.mIsClosed = false;
        Objects.requireNonNull(iAnnouncementListener);
        this.mListener = iAnnouncementListener;
        Objects.requireNonNull(obj);
        this.mLock = obj;
        try {
            iAnnouncementListener.asBinder().linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                this.mListener.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                Iterator it = ((ArrayList) this.mModuleWatchers).iterator();
                while (it.hasNext()) {
                    ICloseHandle iCloseHandle = ((ModuleWatcher) it.next()).mCloseHandle;
                    if (iCloseHandle != null) {
                        iCloseHandle.close();
                    }
                }
                ((ArrayList) this.mModuleWatchers).clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void watchModule(RadioModule radioModule, int[] iArr) {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    throw new IllegalStateException(
                            "Failed to watch modulesince announcement aggregator has already been"
                                + " closed");
                }
                ModuleWatcher moduleWatcher = new ModuleWatcher();
                try {
                    moduleWatcher.mCloseHandle =
                            radioModule.addAnnouncementListener(iArr, moduleWatcher);
                    ((ArrayList) this.mModuleWatchers).add(moduleWatcher);
                } catch (RemoteException e) {
                    Slogf.e("BcRadio2Srv.AnnAggr", "Failed to add announcement listener", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
