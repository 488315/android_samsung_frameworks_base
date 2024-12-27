package com.android.server.broadcastradio.aidl;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.server.broadcastradio.aidl.RadioModule;
import com.android.server.utils.Slogf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AnnouncementAggregator extends ICloseHandle.Stub {
    public static final boolean DEBUG = Log.isLoggable("BcRadioAidlSrv.AnnAggr", 3);
    public final DeathRecipient mDeathRecipient;
    public boolean mIsClosed;
    public final IAnnouncementListener mListener;
    public final Object mLock;
    public final List mModuleWatchers;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IBinder.DeathRecipient {
        public DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                AnnouncementAggregator.this.close();
            } catch (RemoteException e) {
                Slogf.e("BcRadioAidlSrv.AnnAggr", e, "Cannot close Announcement aggregator for DeathRecipient", new Object[0]);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModuleWatcher extends IAnnouncementListener.Stub {
        public ICloseHandle mCloseHandle;
        public List mCurrentList = new ArrayList();

        public ModuleWatcher() {
        }

        public final void onListUpdated(List list) {
            boolean z = AnnouncementAggregator.DEBUG;
            if (z) {
                Slogf.d("BcRadioAidlSrv.AnnAggr", "onListUpdate for %s", list);
            }
            Objects.requireNonNull(list, "active cannot be null");
            this.mCurrentList = list;
            AnnouncementAggregator announcementAggregator = AnnouncementAggregator.this;
            if (z) {
                announcementAggregator.getClass();
                Slogf.d("BcRadioAidlSrv.AnnAggr", "onListUpdated()");
            }
            synchronized (announcementAggregator.mLock) {
                try {
                    if (announcementAggregator.mIsClosed) {
                        Slogf.e("BcRadioAidlSrv.AnnAggr", "Announcement aggregator is closed, it shouldn't receive callbacks");
                        return;
                    }
                    ArrayList arrayList = new ArrayList(((ArrayList) announcementAggregator.mModuleWatchers).size());
                    for (int i = 0; i < ((ArrayList) announcementAggregator.mModuleWatchers).size(); i++) {
                        arrayList.addAll(((ModuleWatcher) ((ArrayList) announcementAggregator.mModuleWatchers).get(i)).mCurrentList);
                    }
                    try {
                        announcementAggregator.mListener.onListUpdated(arrayList);
                    } catch (RemoteException e) {
                        Slogf.e("BcRadioAidlSrv.AnnAggr", e, "mListener.onListUpdated() failed", new Object[0]);
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
        Objects.requireNonNull(iAnnouncementListener, "listener cannot be null");
        this.mListener = iAnnouncementListener;
        this.mLock = obj;
        try {
            iAnnouncementListener.asBinder().linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void close() {
        if (DEBUG) {
            Slogf.d("BcRadioAidlSrv.AnnAggr", "Close watchModule");
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    Slogf.w("BcRadioAidlSrv.AnnAggr", "Announcement aggregator has already been closed.");
                    return;
                }
                this.mListener.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                for (int i = 0; i < ((ArrayList) this.mModuleWatchers).size(); i++) {
                    ModuleWatcher moduleWatcher = (ModuleWatcher) ((ArrayList) this.mModuleWatchers).get(i);
                    try {
                        moduleWatcher.getClass();
                        if (DEBUG) {
                            Slogf.d("BcRadioAidlSrv.AnnAggr", "Close module watcher.");
                        }
                        ICloseHandle iCloseHandle = moduleWatcher.mCloseHandle;
                        if (iCloseHandle != null) {
                            iCloseHandle.close();
                        }
                    } catch (Exception e) {
                        Slogf.e("BcRadioAidlSrv.AnnAggr", "Failed to close module watcher %s: %s", moduleWatcher, e);
                    }
                }
                ((ArrayList) this.mModuleWatchers).clear();
                this.mIsClosed = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("AnnouncementAggregator\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf("Is session closed? %s\n", new Object[]{this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf("Module Watchers [%d]:\n", new Object[]{Integer.valueOf(((ArrayList) this.mModuleWatchers).size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < ((ArrayList) this.mModuleWatchers).size(); i++) {
                    ModuleWatcher moduleWatcher = (ModuleWatcher) ((ArrayList) this.mModuleWatchers).get(i);
                    moduleWatcher.getClass();
                    indentingPrintWriter.printf("ModuleWatcher:\n", new Object[0]);
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.printf("Close handle: %s\n", new Object[]{moduleWatcher.mCloseHandle});
                    indentingPrintWriter.printf("Current announcement list: %s\n", new Object[]{moduleWatcher.mCurrentList});
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void watchModule(RadioModule radioModule, int[] iArr) {
        boolean z = DEBUG;
        if (z) {
            Slogf.d("BcRadioAidlSrv.AnnAggr", "Watch module for %s with enabled types %s", radioModule, Arrays.toString(iArr));
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    throw new IllegalStateException("Failed to watch modulesince announcement aggregator has already been closed");
                }
                ModuleWatcher moduleWatcher = new ModuleWatcher();
                try {
                    RadioModule.AnonymousClass3 addAnnouncementListener = radioModule.addAnnouncementListener(moduleWatcher, iArr);
                    if (z) {
                        Slogf.d("BcRadioAidlSrv.AnnAggr", "Set close handle %s", addAnnouncementListener);
                    }
                    moduleWatcher.mCloseHandle = addAnnouncementListener;
                    ((ArrayList) this.mModuleWatchers).add(moduleWatcher);
                } catch (RemoteException e) {
                    Slogf.e("BcRadioAidlSrv.AnnAggr", e, "Failed to add announcement listener", new Object[0]);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
