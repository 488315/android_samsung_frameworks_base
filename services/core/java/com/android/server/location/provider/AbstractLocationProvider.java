package com.android.server.location.provider;

import android.location.LocationResult;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.Bundle;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/* loaded from: classes2.dex */
public abstract class AbstractLocationProvider {
  public final LocationProviderController mController;
  public final Executor mExecutor;
  public final AtomicReference mInternalState;

  public interface Listener {
    void onReportLocation(LocationResult locationResult);

    void onStateChanged(State state, State state2);
  }

  public abstract void dump(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

  public abstract void onExtraCommand(int i, int i2, String str, Bundle bundle);

  public abstract void onFlush(Runnable runnable);

  public abstract void onSetRequest(ProviderRequest providerRequest);

  public void onStart() {}

  public void onStop() {}

  public final class State {
    public static final State EMPTY_STATE = new State(false, null, null, Collections.emptySet());
    public final boolean allowed;
    public final Set extraAttributionTags;
    public final CallerIdentity identity;
    public final ProviderProperties properties;

    public State(
        boolean z, ProviderProperties providerProperties, CallerIdentity callerIdentity, Set set) {
      this.allowed = z;
      this.properties = providerProperties;
      this.identity = callerIdentity;
      Objects.requireNonNull(set);
      this.extraAttributionTags = set;
    }

    public State withAllowed(boolean z) {
      return z == this.allowed
          ? this
          : new State(z, this.properties, this.identity, this.extraAttributionTags);
    }

    public State withProperties(ProviderProperties providerProperties) {
      return Objects.equals(providerProperties, this.properties)
          ? this
          : new State(this.allowed, providerProperties, this.identity, this.extraAttributionTags);
    }

    public State withIdentity(CallerIdentity callerIdentity) {
      return Objects.equals(callerIdentity, this.identity)
          ? this
          : new State(this.allowed, this.properties, callerIdentity, this.extraAttributionTags);
    }

    public State withExtraAttributionTags(Set set) {
      return set.equals(this.extraAttributionTags)
          ? this
          : new State(this.allowed, this.properties, this.identity, set);
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof State)) {
        return false;
      }
      State state = (State) obj;
      return this.allowed == state.allowed
          && this.properties == state.properties
          && Objects.equals(this.identity, state.identity)
          && this.extraAttributionTags.equals(state.extraAttributionTags);
    }

    public int hashCode() {
      return Objects.hash(
          Boolean.valueOf(this.allowed), this.properties, this.identity, this.extraAttributionTags);
    }
  }

  public class InternalState {
    public final Listener listener;
    public final State state;

    public InternalState(Listener listener, State state) {
      this.listener = listener;
      this.state = state;
    }

    public InternalState withListener(Listener listener) {
      return listener == this.listener ? this : new InternalState(listener, this.state);
    }

    public InternalState withState(State state) {
      return state.equals(this.state) ? this : new InternalState(this.listener, state);
    }

    public InternalState withState(UnaryOperator unaryOperator) {
      return withState((State) unaryOperator.apply(this.state));
    }
  }

  public AbstractLocationProvider(
      Executor executor,
      CallerIdentity callerIdentity,
      ProviderProperties providerProperties,
      Set set) {
    Preconditions.checkArgument(callerIdentity == null || callerIdentity.getListenerId() == null);
    Objects.requireNonNull(executor);
    this.mExecutor = executor;
    this.mInternalState =
        new AtomicReference(
            new InternalState(
                null,
                State.EMPTY_STATE
                    .withIdentity(callerIdentity)
                    .withProperties(providerProperties)
                    .withExtraAttributionTags(set)));
    this.mController = new Controller();
  }

  public AbstractLocationProvider() {
    this.mExecutor = null;
    this.mInternalState = null;
    this.mController = new Controller();
  }

  public LocationProviderController getController() {
    return this.mController;
  }

  public void setState(final UnaryOperator unaryOperator) {
    final AtomicReference atomicReference = new AtomicReference();
    InternalState internalState =
        (InternalState)
            this.mInternalState.updateAndGet(
                new UnaryOperator() { // from class:
                                      // com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda2
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    AbstractLocationProvider.InternalState lambda$setState$0;
                    lambda$setState$0 =
                        AbstractLocationProvider.lambda$setState$0(
                            atomicReference,
                            unaryOperator,
                            (AbstractLocationProvider.InternalState) obj);
                    return lambda$setState$0;
                  }
                });
    State state = (State) atomicReference.get();
    if (state.equals(internalState.state) || internalState.listener == null) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      internalState.listener.onStateChanged(state, internalState.state);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ InternalState lambda$setState$0(
      AtomicReference atomicReference, UnaryOperator unaryOperator, InternalState internalState) {
    atomicReference.set(internalState.state);
    return internalState.withState(unaryOperator);
  }

  public final State getState() {
    return ((InternalState) this.mInternalState.get()).state;
  }

  public static /* synthetic */ State lambda$setAllowed$1(boolean z, State state) {
    return state.withAllowed(z);
  }

  public void setAllowed(final boolean z) {
    setState(
        new UnaryOperator() { // from class:
                              // com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda1
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            AbstractLocationProvider.State lambda$setAllowed$1;
            lambda$setAllowed$1 =
                AbstractLocationProvider.lambda$setAllowed$1(
                    z, (AbstractLocationProvider.State) obj);
            return lambda$setAllowed$1;
          }
        });
  }

  public static /* synthetic */ State lambda$setProperties$2(
      ProviderProperties providerProperties, State state) {
    return state.withProperties(providerProperties);
  }

  public void setProperties(final ProviderProperties providerProperties) {
    setState(
        new UnaryOperator() { // from class:
                              // com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda0
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            AbstractLocationProvider.State lambda$setProperties$2;
            lambda$setProperties$2 =
                AbstractLocationProvider.lambda$setProperties$2(
                    providerProperties, (AbstractLocationProvider.State) obj);
            return lambda$setProperties$2;
          }
        });
  }

  public void reportLocation(LocationResult locationResult) {
    Listener listener = ((InternalState) this.mInternalState.get()).listener;
    if (listener != null) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        Objects.requireNonNull(locationResult);
        LocationResult locationResult2 = locationResult;
        listener.onReportLocation(locationResult);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public class Controller implements LocationProviderController {
    public boolean mStarted = false;

    public Controller() {}

    @Override // com.android.server.location.provider.LocationProviderController
    public State setListener(final Listener listener) {
      InternalState internalState =
          (InternalState)
              AbstractLocationProvider.this.mInternalState.getAndUpdate(
                  new UnaryOperator() { // from class:
                                        // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      AbstractLocationProvider.InternalState lambda$setListener$0;
                      lambda$setListener$0 =
                          AbstractLocationProvider.Controller.lambda$setListener$0(
                              AbstractLocationProvider.Listener.this,
                              (AbstractLocationProvider.InternalState) obj);
                      return lambda$setListener$0;
                    }
                  });
      Preconditions.checkState(listener == null || internalState.listener == null);
      return internalState.state;
    }

    public static /* synthetic */ InternalState lambda$setListener$0(
        Listener listener, InternalState internalState) {
      return internalState.withListener(listener);
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public boolean isStarted() {
      return this.mStarted;
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public void start() {
      Preconditions.checkState(!this.mStarted);
      this.mStarted = true;
      final AbstractLocationProvider abstractLocationProvider = AbstractLocationProvider.this;
      abstractLocationProvider.mExecutor.execute(
          new Runnable() { // from class:
                           // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              AbstractLocationProvider.this.onStart();
            }
          });
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public void stop() {
      Preconditions.checkState(this.mStarted);
      this.mStarted = false;
      final AbstractLocationProvider abstractLocationProvider = AbstractLocationProvider.this;
      abstractLocationProvider.mExecutor.execute(
          new Runnable() { // from class:
                           // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              AbstractLocationProvider.this.onStop();
            }
          });
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public void setRequest(final ProviderRequest providerRequest) {
      Preconditions.checkState(this.mStarted);
      AbstractLocationProvider.this.mExecutor.execute(
          new Runnable() { // from class:
                           // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              AbstractLocationProvider.Controller.this.lambda$setRequest$1(providerRequest);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRequest$1(ProviderRequest providerRequest) {
      AbstractLocationProvider.this.onSetRequest(providerRequest);
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public void flush(final Runnable runnable) {
      Preconditions.checkState(this.mStarted);
      AbstractLocationProvider.this.mExecutor.execute(
          new Runnable() { // from class:
                           // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              AbstractLocationProvider.Controller.this.lambda$flush$2(runnable);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flush$2(Runnable runnable) {
      AbstractLocationProvider.this.onFlush(runnable);
    }

    @Override // com.android.server.location.provider.LocationProviderController
    public void sendExtraCommand(final int i, final int i2, final String str, final Bundle bundle) {
      Preconditions.checkState(this.mStarted);
      AbstractLocationProvider.this.mExecutor.execute(
          new Runnable() { // from class:
                           // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              AbstractLocationProvider.Controller.this.lambda$sendExtraCommand$3(
                  i, i2, str, bundle);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendExtraCommand$3(
        int i, int i2, String str, Bundle bundle) {
      AbstractLocationProvider.this.onExtraCommand(i, i2, str, bundle);
    }
  }
}
