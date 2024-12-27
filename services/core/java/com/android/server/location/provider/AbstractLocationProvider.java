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

public abstract class AbstractLocationProvider {
    public final Controller mController;
    public final Executor mExecutor;
    public final AtomicReference mInternalState;

    public final class Controller {
        public boolean mStarted = false;

        public Controller() {}

        public final void flush(
                LocationProviderManager$Registration$$ExternalSyntheticLambda0
                        locationProviderManager$Registration$$ExternalSyntheticLambda0) {
            Preconditions.checkState(this.mStarted);
            AbstractLocationProvider.this.mExecutor.execute(
                    new AbstractLocationProvider$Controller$$ExternalSyntheticLambda1(
                            this,
                            locationProviderManager$Registration$$ExternalSyntheticLambda0,
                            1));
        }

        public final void sendExtraCommand(
                final int i, final String str, final Bundle bundle, final int i2) {
            Preconditions.checkState(this.mStarted);
            AbstractLocationProvider.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            AbstractLocationProvider.Controller controller =
                                    AbstractLocationProvider.Controller.this;
                            int i3 = i;
                            int i4 = i2;
                            AbstractLocationProvider.this.onExtraCommand(i3, str, bundle, i4);
                        }
                    });
        }

        public final State setListener(Listener listener) {
            InternalState internalState =
                    (InternalState)
                            AbstractLocationProvider.this.mInternalState.getAndUpdate(
                                    new AbstractLocationProvider$$ExternalSyntheticLambda1(
                                            1, listener));
            Preconditions.checkState(listener == null || internalState.listener == null);
            return internalState.state;
        }

        public final void setRequest(ProviderRequest providerRequest) {
            Preconditions.checkState(this.mStarted);
            AbstractLocationProvider.this.mExecutor.execute(
                    new AbstractLocationProvider$Controller$$ExternalSyntheticLambda1(
                            this, providerRequest, 0));
        }

        public final void start() {
            Preconditions.checkState(!this.mStarted);
            this.mStarted = true;
            AbstractLocationProvider abstractLocationProvider = AbstractLocationProvider.this;
            abstractLocationProvider.mExecutor.execute(
                    new AbstractLocationProvider$Controller$$ExternalSyntheticLambda0(
                            abstractLocationProvider, 0));
        }

        public final void stop() {
            Preconditions.checkState(this.mStarted);
            this.mStarted = false;
            AbstractLocationProvider abstractLocationProvider = AbstractLocationProvider.this;
            abstractLocationProvider.mExecutor.execute(
                    new AbstractLocationProvider$Controller$$ExternalSyntheticLambda0(
                            abstractLocationProvider, 1));
        }
    }

    public final class InternalState {
        public final Listener listener;
        public final State state;

        public InternalState(Listener listener, State state) {
            this.listener = listener;
            this.state = state;
        }
    }

    public interface Listener {
        void onReportLocation(LocationResult locationResult);

        void onStateChanged(State state, State state2);
    }

    public final class State {
        public static final State EMPTY_STATE =
                new State(false, null, null, Collections.emptySet());
        public final boolean allowed;
        public final Set extraAttributionTags;
        public final CallerIdentity identity;
        public final ProviderProperties properties;

        public State(
                boolean z,
                ProviderProperties providerProperties,
                CallerIdentity callerIdentity,
                Set set) {
            this.allowed = z;
            this.properties = providerProperties;
            this.identity = callerIdentity;
            Objects.requireNonNull(set);
            this.extraAttributionTags = set;
        }

        public final boolean equals(Object obj) {
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

        public final int hashCode() {
            return Objects.hash(
                    Boolean.valueOf(this.allowed),
                    this.properties,
                    this.identity,
                    this.extraAttributionTags);
        }

        public final State withProperties(ProviderProperties providerProperties) {
            if (Objects.equals(providerProperties, this.properties)) {
                return this;
            }
            return new State(
                    this.allowed, providerProperties, this.identity, this.extraAttributionTags);
        }
    }

    public AbstractLocationProvider() {
        this.mExecutor = null;
        this.mInternalState = null;
        this.mController = new Controller();
    }

    public AbstractLocationProvider(
            Executor executor,
            CallerIdentity callerIdentity,
            ProviderProperties providerProperties,
            Set set) {
        Preconditions.checkArgument(
                callerIdentity == null || callerIdentity.getListenerId() == null);
        Objects.requireNonNull(executor);
        this.mExecutor = executor;
        State state = State.EMPTY_STATE;
        State withProperties =
                (Objects.equals(callerIdentity, state.identity)
                                ? state
                                : new State(
                                        state.allowed,
                                        state.properties,
                                        callerIdentity,
                                        state.extraAttributionTags))
                        .withProperties(providerProperties);
        this.mInternalState =
                new AtomicReference(
                        new InternalState(
                                null,
                                set.equals(withProperties.extraAttributionTags)
                                        ? withProperties
                                        : new State(
                                                withProperties.allowed,
                                                withProperties.properties,
                                                withProperties.identity,
                                                set)));
        this.mController = new Controller();
    }

    public abstract void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract void onExtraCommand(int i, String str, Bundle bundle, int i2);

    public abstract void onFlush(
            LocationProviderManager$Registration$$ExternalSyntheticLambda0
                    locationProviderManager$Registration$$ExternalSyntheticLambda0);

    public abstract void onSetRequest(ProviderRequest providerRequest);

    public void onStart() {}

    public void onStop() {}

    public final void reportLocation(LocationResult locationResult) {
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

    public final void setState(final UnaryOperator unaryOperator) {
        Listener listener;
        final AtomicReference atomicReference = new AtomicReference();
        InternalState internalState =
                (InternalState)
                        this.mInternalState.updateAndGet(
                                new UnaryOperator() { // from class:
                                                      // com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        AtomicReference atomicReference2 = atomicReference;
                                        UnaryOperator unaryOperator2 = unaryOperator;
                                        AbstractLocationProvider.InternalState internalState2 =
                                                (AbstractLocationProvider.InternalState) obj;
                                        atomicReference2.set(internalState2.state);
                                        AbstractLocationProvider.State state = internalState2.state;
                                        AbstractLocationProvider.State state2 =
                                                (AbstractLocationProvider.State)
                                                        unaryOperator2.apply(state);
                                        return state2.equals(state)
                                                ? internalState2
                                                : new AbstractLocationProvider.InternalState(
                                                        internalState2.listener, state2);
                                    }
                                });
        State state = (State) atomicReference.get();
        if (state.equals(internalState.state) || (listener = internalState.listener) == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            listener.onStateChanged(state, internalState.state);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
