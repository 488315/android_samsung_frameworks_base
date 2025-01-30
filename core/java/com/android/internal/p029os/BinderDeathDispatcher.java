package com.android.internal.p029os;

import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;

/* loaded from: classes5.dex */
public class BinderDeathDispatcher<T extends IInterface> {
  private static final String TAG = "BinderDeathDispatcher";
  private final Object mLock = new Object();
  private final ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> mTargets =
      new ArrayMap<>();

  class RecipientsInfo implements IBinder.DeathRecipient {
    ArraySet<IBinder.DeathRecipient> mRecipients;
    final IBinder mTarget;

    private RecipientsInfo(IBinder target) {
      this.mRecipients = new ArraySet<>();
      this.mTarget = target;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {}

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(IBinder who) {
      ArraySet<IBinder.DeathRecipient> copy;
      synchronized (BinderDeathDispatcher.this.mLock) {
        copy = this.mRecipients;
        this.mRecipients = null;
        BinderDeathDispatcher.this.mTargets.remove(this.mTarget);
      }
      if (copy == null) {
        return;
      }
      int size = copy.size();
      for (int i = 0; i < size; i++) {
        copy.valueAt(i).binderDied(who);
      }
    }
  }

  public int linkToDeath(T target, IBinder.DeathRecipient recipient) {
    int size;
    IBinder ib = target.asBinder();
    synchronized (this.mLock) {
      BinderDeathDispatcher<T>.RecipientsInfo info = this.mTargets.get(ib);
      if (info == null) {
        info = new RecipientsInfo(ib);
        try {
          ib.linkToDeath(info, 0);
          this.mTargets.put(ib, info);
        } catch (RemoteException e) {
          return -1;
        }
      }
      info.mRecipients.add(recipient);
      size = info.mRecipients.size();
    }
    return size;
  }

  public void unlinkToDeath(T target, IBinder.DeathRecipient recipient) {
    IBinder ib = target.asBinder();
    synchronized (this.mLock) {
      BinderDeathDispatcher<T>.RecipientsInfo info = this.mTargets.get(ib);
      if (info == null) {
        return;
      }
      if (info.mRecipients.remove(recipient) && info.mRecipients.size() == 0) {
        info.mTarget.unlinkToDeath(info, 0);
        this.mTargets.remove(info.mTarget);
      }
    }
  }

  public void dump(IndentingPrintWriter pw) {
    synchronized (this.mLock) {
      pw.print("# of watched binders: ");
      pw.println(this.mTargets.size());
      pw.print("# of death recipients: ");
      int n = 0;
      for (BinderDeathDispatcher<T>.RecipientsInfo info : this.mTargets.values()) {
        n += info.mRecipients.size();
      }
      pw.println(n);
    }
  }

  public ArrayMap<IBinder, BinderDeathDispatcher<T>.RecipientsInfo> getTargetsForTest() {
    return this.mTargets;
  }
}
