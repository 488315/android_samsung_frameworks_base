package com.android.server.location.listeners;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public abstract class ListenerMultiplexer {
  public Object mMerged;
  public final Object mMultiplexerLock = new Object();
  public final ArrayMap mRegistrations = new ArrayMap();
  public final UpdateServiceBuffer mUpdateServiceBuffer = new UpdateServiceBuffer();
  public final ReentrancyGuard mReentrancyGuard = new ReentrancyGuard();
  public int mActiveRegistrationsCount = 0;
  public boolean mServiceRegistered = false;

  public abstract boolean isActive(ListenerRegistration listenerRegistration);

  public abstract Object mergeRegistrations(Collection collection);

  public void onActive() {}

  public void onInactive() {}

  public void onRegister() {}

  public void onRegistrationAdded(Object obj, ListenerRegistration listenerRegistration) {}

  public void onRegistrationRemoved(Object obj, ListenerRegistration listenerRegistration) {}

  public void onTransferUnregisteredRegistration(ListenerRegistration listenerRegistration) {}

  public void onUnregister() {}

  public abstract boolean registerWithService(Object obj, Collection collection);

  public abstract void unregisterWithService();

  public boolean reregisterWithService(Object obj, Object obj2, Collection collection) {
    return registerWithService(obj2, collection);
  }

  public void onRegistrationReplaced(
      Object obj,
      ListenerRegistration listenerRegistration,
      Object obj2,
      ListenerRegistration listenerRegistration2) {
    onRegistrationRemoved(obj, listenerRegistration);
    onRegistrationAdded(obj2, listenerRegistration2);
  }

  public final void putRegistration(Object obj, ListenerRegistration listenerRegistration) {
    replaceRegistration(obj, obj, listenerRegistration);
  }

  public boolean isRegistrationEmpty() {
    boolean isEmpty;
    synchronized (this.mMultiplexerLock) {
      isEmpty = this.mRegistrations.isEmpty();
    }
    return isEmpty;
  }

  public int getRegistrationCountWith(Predicate predicate) {
    int i;
    synchronized (this.mMultiplexerLock) {
      int size = this.mRegistrations.size();
      i = 0;
      for (int i2 = 0; i2 < size; i2++) {
        if (predicate.test((ListenerRegistration) this.mRegistrations.valueAt(i2))) {
          i++;
        }
      }
    }
    return i;
  }

  public final void replaceRegistration(
      Object obj, Object obj2, ListenerRegistration listenerRegistration) {
    ListenerRegistration listenerRegistration2;
    Objects.requireNonNull(obj);
    Objects.requireNonNull(obj2);
    Objects.requireNonNull(listenerRegistration);
    synchronized (this.mMultiplexerLock) {
      boolean z = true;
      Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
      if (obj != obj2 && this.mRegistrations.containsKey(obj2)) {
        z = false;
      }
      Preconditions.checkArgument(z);
      UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
      try {
        ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
        try {
          boolean isEmpty = this.mRegistrations.isEmpty();
          int indexOfKey = this.mRegistrations.indexOfKey(obj);
          if (indexOfKey >= 0) {
            listenerRegistration2 = (ListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
            unregister(listenerRegistration2);
            listenerRegistration2.onUnregister();
            if (obj != obj2) {
              this.mRegistrations.removeAt(indexOfKey);
            }
          } else {
            listenerRegistration2 = null;
          }
          if (obj == obj2 && indexOfKey >= 0) {
            this.mRegistrations.setValueAt(indexOfKey, listenerRegistration);
          } else {
            this.mRegistrations.put(obj2, listenerRegistration);
          }
          if (isEmpty) {
            onRegister();
          }
          listenerRegistration.onRegister(obj2);
          if (listenerRegistration2 == null) {
            onRegistrationAdded(obj2, listenerRegistration);
          } else {
            onRegistrationReplaced(obj, listenerRegistration2, obj2, listenerRegistration);
          }
          onRegistrationActiveChanged(listenerRegistration);
          if (acquire2 != null) {
            acquire2.close();
          }
          if (acquire != null) {
            acquire.close();
          }
        } finally {
        }
      } finally {
      }
    }
  }

  public final void removeRegistrationIf(Predicate predicate) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
      UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
      try {
        ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
        try {
          int size = this.mRegistrations.size();
          for (int i = 0; i < size; i++) {
            Object keyAt = this.mRegistrations.keyAt(i);
            if (predicate.test(keyAt)) {
              removeRegistration(keyAt, (ListenerRegistration) this.mRegistrations.valueAt(i));
            }
          }
          if (acquire2 != null) {
            acquire2.close();
          }
          if (acquire != null) {
            acquire.close();
          }
        } finally {
        }
      } finally {
      }
    }
  }

  public final void removeRegistration(Object obj) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
      int indexOfKey = this.mRegistrations.indexOfKey(obj);
      if (indexOfKey < 0) {
        return;
      }
      removeRegistration(indexOfKey);
    }
  }

  public final void removeRegistration(Object obj, ListenerRegistration listenerRegistration) {
    synchronized (this.mMultiplexerLock) {
      int indexOfKey = this.mRegistrations.indexOfKey(obj);
      if (indexOfKey < 0) {
        return;
      }
      ListenerRegistration listenerRegistration2 =
          (ListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
      if (listenerRegistration2 != listenerRegistration) {
        return;
      }
      if (this.mReentrancyGuard.isReentrant()) {
        unregister(listenerRegistration2);
        this.mReentrancyGuard.markForRemoval(obj, listenerRegistration2);
      } else {
        removeRegistration(indexOfKey);
      }
    }
  }

  public final void removeRegistration(int i) {
    Object keyAt = this.mRegistrations.keyAt(i);
    ListenerRegistration listenerRegistration =
        (ListenerRegistration) this.mRegistrations.valueAt(i);
    UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
    try {
      ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
      try {
        unregister(listenerRegistration);
        onRegistrationRemoved(keyAt, listenerRegistration);
        listenerRegistration.onUnregister();
        this.mRegistrations.removeAt(i);
        if (this.mRegistrations.isEmpty()) {
          onUnregister();
        }
        if (acquire2 != null) {
          acquire2.close();
        }
        if (acquire != null) {
          acquire.close();
        }
      } finally {
      }
    } catch (Throwable th) {
      if (acquire != null) {
        try {
          acquire.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public final void updateService() {
    synchronized (this.mMultiplexerLock) {
      if (this.mUpdateServiceBuffer.isBuffered()) {
        this.mUpdateServiceBuffer.markUpdateServiceRequired();
        return;
      }
      int size = this.mRegistrations.size();
      ArrayList arrayList = new ArrayList(size);
      for (int i = 0; i < size; i++) {
        ListenerRegistration listenerRegistration =
            (ListenerRegistration) this.mRegistrations.valueAt(i);
        if (listenerRegistration.isActive()) {
          arrayList.add(listenerRegistration);
        }
      }
      if (arrayList.isEmpty()) {
        if (this.mServiceRegistered) {
          this.mMerged = null;
          this.mServiceRegistered = false;
          unregisterWithService();
        }
      } else {
        Object mergeRegistrations = mergeRegistrations(arrayList);
        if (this.mServiceRegistered) {
          if (!Objects.equals(mergeRegistrations, this.mMerged)) {
            boolean reregisterWithService =
                reregisterWithService(this.mMerged, mergeRegistrations, arrayList);
            this.mServiceRegistered = reregisterWithService;
            this.mMerged = reregisterWithService ? mergeRegistrations : null;
          }
        } else {
          boolean registerWithService = registerWithService(mergeRegistrations, arrayList);
          this.mServiceRegistered = registerWithService;
          this.mMerged = registerWithService ? mergeRegistrations : null;
        }
      }
    }
  }

  public final void resetService() {
    synchronized (this.mMultiplexerLock) {
      if (this.mServiceRegistered) {
        this.mMerged = null;
        this.mServiceRegistered = false;
        unregisterWithService();
        updateService();
      }
    }
  }

  public final boolean findRegistration(Predicate predicate) {
    synchronized (this.mMultiplexerLock) {
      ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
      try {
        int size = this.mRegistrations.size();
        for (int i = 0; i < size; i++) {
          if (predicate.test((ListenerRegistration) this.mRegistrations.valueAt(i))) {
            if (acquire != null) {
              acquire.close();
            }
            return true;
          }
        }
        if (acquire != null) {
          acquire.close();
        }
        return false;
      } finally {
      }
    }
  }

  public final void updateRegistrations(Predicate predicate) {
    synchronized (this.mMultiplexerLock) {
      UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
      try {
        ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
        try {
          int size = this.mRegistrations.size();
          for (int i = 0; i < size; i++) {
            ListenerRegistration listenerRegistration =
                (ListenerRegistration) this.mRegistrations.valueAt(i);
            if (predicate.test(listenerRegistration)) {
              onRegistrationActiveChanged(listenerRegistration);
            }
          }
          if (acquire2 != null) {
            acquire2.close();
          }
          if (acquire != null) {
            acquire.close();
          }
        } finally {
        }
      } finally {
      }
    }
  }

  public final boolean updateRegistration(Object obj, Predicate predicate) {
    synchronized (this.mMultiplexerLock) {
      UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
      try {
        ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
        try {
          int indexOfKey = this.mRegistrations.indexOfKey(obj);
          if (indexOfKey < 0) {
            if (acquire2 != null) {
              acquire2.close();
            }
            if (acquire != null) {
              acquire.close();
            }
            return false;
          }
          ListenerRegistration listenerRegistration =
              (ListenerRegistration) this.mRegistrations.valueAt(indexOfKey);
          if (predicate.test(listenerRegistration)) {
            onRegistrationActiveChanged(listenerRegistration);
          }
          if (acquire2 != null) {
            acquire2.close();
          }
          if (acquire != null) {
            acquire.close();
          }
          return true;
        } finally {
        }
      } finally {
      }
    }
  }

  public final void onRegistrationActiveChanged(ListenerRegistration listenerRegistration) {
    boolean z = listenerRegistration.isRegistered() && isActive(listenerRegistration);
    if (listenerRegistration.setActive(z)) {
      if (z) {
        int i = this.mActiveRegistrationsCount + 1;
        this.mActiveRegistrationsCount = i;
        if (i == 1) {
          onActive();
        }
        listenerRegistration.onActive();
      } else {
        listenerRegistration.onInactive();
        int i2 = this.mActiveRegistrationsCount - 1;
        this.mActiveRegistrationsCount = i2;
        if (i2 == 0) {
          onInactive();
        }
      }
      updateService();
    }
  }

  public final void deliverToListeners(Function function) {
    ListenerExecutor.ListenerOperation listenerOperation;
    synchronized (this.mMultiplexerLock) {
      ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
      try {
        int size = this.mRegistrations.size();
        for (int i = 0; i < size; i++) {
          ListenerRegistration listenerRegistration =
              (ListenerRegistration) this.mRegistrations.valueAt(i);
          if ((listenerRegistration.isActive() || listenerRegistration.isActiveMotionControl())
              && (listenerOperation =
                      (ListenerExecutor.ListenerOperation) function.apply(listenerRegistration))
                  != null) {
            listenerRegistration.executeOperation(listenerOperation);
          }
        }
        if (acquire != null) {
          acquire.close();
        }
      } finally {
      }
    }
  }

  public final void deliverToListeners(ListenerExecutor.ListenerOperation listenerOperation) {
    synchronized (this.mMultiplexerLock) {
      ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
      try {
        int size = this.mRegistrations.size();
        for (int i = 0; i < size; i++) {
          ListenerRegistration listenerRegistration =
              (ListenerRegistration) this.mRegistrations.valueAt(i);
          if (listenerRegistration.isActive() || listenerRegistration.isActiveMotionControl()) {
            listenerRegistration.executeOperation(listenerOperation);
          }
        }
        if (acquire != null) {
          acquire.close();
        }
      } finally {
      }
    }
  }

  public final void unregister(ListenerRegistration listenerRegistration) {
    listenerRegistration.unregisterInternal();
    onTransferUnregisteredRegistration(listenerRegistration);
    onRegistrationActiveChanged(listenerRegistration);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    synchronized (this.mMultiplexerLock) {
      printWriter.print("service: ");
      printWriter.print(getServiceState());
      printWriter.println();
      if (!this.mRegistrations.isEmpty()) {
        printWriter.println("listeners:");
        int size = this.mRegistrations.size();
        for (int i = 0; i < size; i++) {
          ListenerRegistration listenerRegistration =
              (ListenerRegistration) this.mRegistrations.valueAt(i);
          printWriter.print("  ");
          printWriter.print(listenerRegistration);
          if (!listenerRegistration.isActive()) {
            printWriter.println(" (inactive)");
          } else {
            printWriter.println();
          }
        }
      }
    }
  }

  public String getServiceState() {
    if (!this.mServiceRegistered) {
      return "unregistered";
    }
    Object obj = this.mMerged;
    return obj != null ? obj.toString() : "registered";
  }

  public final class ReentrancyGuard implements AutoCloseable {
    public int mGuardCount = 0;
    public ArraySet mScheduledRemovals = null;

    public ReentrancyGuard() {}

    public boolean isReentrant() {
      boolean z;
      synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
        z = this.mGuardCount != 0;
      }
      return z;
    }

    public void markForRemoval(Object obj, ListenerRegistration listenerRegistration) {
      synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
        Preconditions.checkState(isReentrant());
        if (this.mScheduledRemovals == null) {
          this.mScheduledRemovals = new ArraySet(ListenerMultiplexer.this.mRegistrations.size());
        }
        this.mScheduledRemovals.add(
            new AbstractMap.SimpleImmutableEntry(obj, listenerRegistration));
      }
    }

    public ReentrancyGuard acquire() {
      synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
        this.mGuardCount++;
      }
      return this;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
      synchronized (ListenerMultiplexer.this.mMultiplexerLock) {
        Preconditions.checkState(this.mGuardCount > 0);
        int i = this.mGuardCount - 1;
        this.mGuardCount = i;
        ArraySet arraySet = null;
        if (i == 0) {
          ArraySet arraySet2 = this.mScheduledRemovals;
          this.mScheduledRemovals = null;
          arraySet = arraySet2;
        }
        if (arraySet == null) {
          return;
        }
        UpdateServiceBuffer acquire = ListenerMultiplexer.this.mUpdateServiceBuffer.acquire();
        try {
          int size = arraySet.size();
          for (int i2 = 0; i2 < size; i2++) {
            Map.Entry entry = (Map.Entry) arraySet.valueAt(i2);
            ListenerMultiplexer.this.removeRegistration(
                entry.getKey(), (ListenerRegistration) entry.getValue());
          }
          if (acquire != null) {
            acquire.close();
          }
        } finally {
        }
      }
    }
  }

  public final class UpdateServiceBuffer implements AutoCloseable {
    public int mBufferCount = 0;
    public boolean mUpdateServiceRequired = false;

    public UpdateServiceBuffer() {}

    public synchronized boolean isBuffered() {
      return this.mBufferCount != 0;
    }

    public synchronized void markUpdateServiceRequired() {
      Preconditions.checkState(isBuffered());
      this.mUpdateServiceRequired = true;
    }

    public synchronized UpdateServiceBuffer acquire() {
      this.mBufferCount++;
      return this;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
      boolean z;
      synchronized (this) {
        z = false;
        Preconditions.checkState(this.mBufferCount > 0);
        int i = this.mBufferCount - 1;
        this.mBufferCount = i;
        if (i == 0) {
          boolean z2 = this.mUpdateServiceRequired;
          this.mUpdateServiceRequired = false;
          z = z2;
        }
      }
      if (z) {
        ListenerMultiplexer.this.updateService();
      }
    }
  }
}
