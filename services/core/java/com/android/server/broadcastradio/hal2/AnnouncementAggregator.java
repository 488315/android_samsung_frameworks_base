package com.android.server.broadcastradio.hal2;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class AnnouncementAggregator extends ICloseHandle.Stub {
  public final IBinder.DeathRecipient mDeathRecipient;
  public boolean mIsClosed;
  public final IAnnouncementListener mListener;
  public final Object mLock;
  public final Collection mModuleWatchers;

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

  public class ModuleWatcher extends IAnnouncementListener.Stub {
    public List currentList;
    public ICloseHandle mCloseHandle;

    public ModuleWatcher() {
      this.currentList = new ArrayList();
    }

    public void onListUpdated(List list) {
      Objects.requireNonNull(list);
      this.currentList = list;
      AnnouncementAggregator.this.onListUpdated();
    }

    public void setCloseHandle(ICloseHandle iCloseHandle) {
      Objects.requireNonNull(iCloseHandle);
      this.mCloseHandle = iCloseHandle;
    }

    public void close() {
      ICloseHandle iCloseHandle = this.mCloseHandle;
      if (iCloseHandle != null) {
        iCloseHandle.close();
      }
    }
  }

  public class DeathRecipient implements IBinder.DeathRecipient {
    public DeathRecipient() {}

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      try {
        AnnouncementAggregator.this.close();
      } catch (RemoteException unused) {
      }
    }
  }

  public final void onListUpdated() {
    synchronized (this.mLock) {
      if (this.mIsClosed) {
        Slog.e(
            "BcRadio2Srv.AnnAggr",
            "Announcement aggregator is closed, it shouldn't receive callbacks");
        return;
      }
      ArrayList arrayList = new ArrayList();
      Iterator it = this.mModuleWatchers.iterator();
      while (it.hasNext()) {
        arrayList.addAll(((ModuleWatcher) it.next()).currentList);
      }
      try {
        this.mListener.onListUpdated(arrayList);
      } catch (RemoteException e) {
        Slog.e("BcRadio2Srv.AnnAggr", "mListener.onListUpdated() failed: ", e);
      }
    }
  }

  public void watchModule(RadioModule radioModule, int[] iArr) {
    synchronized (this.mLock) {
      if (this.mIsClosed) {
        throw new IllegalStateException(
            "Failed to watch modulesince announcement aggregator has already been closed");
      }
      ModuleWatcher moduleWatcher = new ModuleWatcher();
      try {
        moduleWatcher.setCloseHandle(radioModule.addAnnouncementListener(iArr, moduleWatcher));
        this.mModuleWatchers.add(moduleWatcher);
      } catch (RemoteException e) {
        Slog.e("BcRadio2Srv.AnnAggr", "Failed to add announcement listener", e);
      }
    }
  }

  public void close() {
    synchronized (this.mLock) {
      if (this.mIsClosed) {
        return;
      }
      this.mIsClosed = true;
      this.mListener.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
      Iterator it = this.mModuleWatchers.iterator();
      while (it.hasNext()) {
        ((ModuleWatcher) it.next()).close();
      }
      this.mModuleWatchers.clear();
    }
  }
}
