package com.android.server.location.listeners;

import com.android.internal.listeners.ListenerExecutor;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public abstract class ListenerRegistration implements ListenerExecutor {
  public boolean mActive;
  public boolean mActiveMotionControl;
  public final Executor mExecutor;
  public volatile Object mListener;

  public final boolean equals(Object obj) {
    return this == obj;
  }

  public String getTag() {
    return "ListenerRegistration";
  }

  public void onActive() {}

  public void onInactive() {}

  public void onListenerUnregister() {}

  public void onRegister(Object obj) {}

  public void onUnregister() {}

  public ListenerRegistration(Executor executor, Object obj) {
    Objects.requireNonNull(executor);
    this.mExecutor = executor;
    this.mActive = false;
    Objects.requireNonNull(obj);
    this.mListener = obj;
  }

  public final Executor getExecutor() {
    return this.mExecutor;
  }

  public final boolean isActive() {
    return this.mActive;
  }

  public final boolean isActiveMotionControl() {
    return this.mActiveMotionControl;
  }

  public final boolean setActive(boolean z) {
    if (z == this.mActive) {
      return false;
    }
    this.mActive = z;
    return true;
  }

  public final boolean setActiveMotionControl(boolean z) {
    if (z == this.mActiveMotionControl) {
      return false;
    }
    this.mActiveMotionControl = z;
    return true;
  }

  public final boolean isRegistered() {
    return this.mListener != null;
  }

  public final void unregisterInternal() {
    this.mListener = null;
    onListenerUnregister();
  }

  public void onOperationFailure(
      ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
    throw new AssertionError(exc);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Object lambda$executeOperation$0() {
    return this.mListener;
  }

  public final void executeOperation(ListenerExecutor.ListenerOperation listenerOperation) {
    executeSafely(
        this.mExecutor,
        new Supplier() { // from class:
                         // com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda0
          @Override // java.util.function.Supplier
          public final Object get() {
            Object lambda$executeOperation$0;
            lambda$executeOperation$0 = ListenerRegistration.this.lambda$executeOperation$0();
            return lambda$executeOperation$0;
          }
        },
        listenerOperation,
        new ListenerExecutor
            .FailureCallback() { // from class:
                                 // com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda1
          public final void onFailure(
              ListenerExecutor.ListenerOperation listenerOperation2, Exception exc) {
            ListenerRegistration.this.onOperationFailure(listenerOperation2, exc);
          }
        });
  }

  public final int hashCode() {
    return super.hashCode();
  }
}
