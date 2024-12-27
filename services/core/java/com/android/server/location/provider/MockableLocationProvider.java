package com.android.server.location.provider;

import android.location.Location;
import android.location.LocationResult;
import android.location.provider.ProviderRequest;
import android.os.Bundle;

import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.Preconditions;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;

public final class MockableLocationProvider extends AbstractLocationProvider {
    public MockLocationProvider mMockProvider;
    public final Object mOwnerLock;
    public AbstractLocationProvider mProvider;
    public AbstractLocationProvider mRealProvider;
    public ProviderRequest mRequest;
    public boolean mStarted;

    public final class ListenerWrapper implements AbstractLocationProvider.Listener {
        public final AbstractLocationProvider mListenerProvider;

        public ListenerWrapper(AbstractLocationProvider abstractLocationProvider) {
            this.mListenerProvider = abstractLocationProvider;
        }

        @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
        public final void onReportLocation(LocationResult locationResult) {
            synchronized (MockableLocationProvider.this.mOwnerLock) {
                try {
                    AbstractLocationProvider abstractLocationProvider = this.mListenerProvider;
                    MockableLocationProvider mockableLocationProvider =
                            MockableLocationProvider.this;
                    if (abstractLocationProvider != mockableLocationProvider.mProvider) {
                        return;
                    }
                    mockableLocationProvider.reportLocation(locationResult);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
        public final void onStateChanged(
                AbstractLocationProvider.State state, AbstractLocationProvider.State state2) {
            synchronized (MockableLocationProvider.this.mOwnerLock) {
                try {
                    AbstractLocationProvider abstractLocationProvider = this.mListenerProvider;
                    MockableLocationProvider mockableLocationProvider =
                            MockableLocationProvider.this;
                    if (abstractLocationProvider != mockableLocationProvider.mProvider) {
                        return;
                    }
                    mockableLocationProvider.setState(
                            new DelegateLocationProvider$$ExternalSyntheticLambda0(1, state2));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public MockableLocationProvider(Object obj) {
        super(ConcurrentUtils.DIRECT_EXECUTOR, null, null, Collections.emptySet());
        this.mOwnerLock = obj;
        this.mRequest = ProviderRequest.EMPTY_REQUEST;
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        AbstractLocationProvider abstractLocationProvider;
        AbstractLocationProvider.State state;
        Preconditions.checkState(!Thread.holdsLock(this.mOwnerLock));
        synchronized (this.mOwnerLock) {
            abstractLocationProvider = this.mProvider;
            state = ((AbstractLocationProvider.InternalState) this.mInternalState.get()).state;
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("allowed="), state.allowed, printWriter);
        if (state.identity != null) {
            printWriter.println("identity=" + state.identity);
        }
        if (!state.extraAttributionTags.isEmpty()) {
            printWriter.println("extra attribution tags=" + state.extraAttributionTags);
        }
        if (state.properties != null) {
            printWriter.println("properties=" + state.properties);
        }
        if (abstractLocationProvider != null) {
            abstractLocationProvider.dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean isMock() {
        boolean z;
        synchronized (this.mOwnerLock) {
            try {
                MockLocationProvider mockLocationProvider = this.mMockProvider;
                z = mockLocationProvider != null && this.mProvider == mockLocationProvider;
            } finally {
            }
        }
        return z;
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onExtraCommand(int i, String str, Bundle bundle, int i2) {
        synchronized (this.mOwnerLock) {
            try {
                AbstractLocationProvider abstractLocationProvider = this.mProvider;
                if (abstractLocationProvider != null) {
                    abstractLocationProvider.mController.sendExtraCommand(i, str, bundle, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onFlush(
            LocationProviderManager$Registration$$ExternalSyntheticLambda0
                    locationProviderManager$Registration$$ExternalSyntheticLambda0) {
        synchronized (this.mOwnerLock) {
            AbstractLocationProvider abstractLocationProvider = this.mProvider;
            if (abstractLocationProvider != null) {
                abstractLocationProvider.mController.flush(
                        locationProviderManager$Registration$$ExternalSyntheticLambda0);
            } else {
                locationProviderManager$Registration$$ExternalSyntheticLambda0.run();
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onSetRequest(ProviderRequest providerRequest) {
        synchronized (this.mOwnerLock) {
            try {
                if (providerRequest == this.mRequest) {
                    return;
                }
                this.mRequest = providerRequest;
                AbstractLocationProvider abstractLocationProvider = this.mProvider;
                if (abstractLocationProvider != null) {
                    abstractLocationProvider.mController.setRequest(providerRequest);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStart() {
        synchronized (this.mOwnerLock) {
            try {
                Preconditions.checkState(!this.mStarted);
                this.mStarted = true;
                AbstractLocationProvider abstractLocationProvider = this.mProvider;
                if (abstractLocationProvider != null) {
                    abstractLocationProvider.mController.start();
                    if (!this.mRequest.equals(ProviderRequest.EMPTY_REQUEST)) {
                        this.mProvider.mController.setRequest(this.mRequest);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStop() {
        synchronized (this.mOwnerLock) {
            try {
                Preconditions.checkState(this.mStarted);
                this.mStarted = false;
                if (this.mProvider != null) {
                    ProviderRequest providerRequest = this.mRequest;
                    ProviderRequest providerRequest2 = ProviderRequest.EMPTY_REQUEST;
                    if (!providerRequest.equals(providerRequest2)) {
                        this.mProvider.mController.setRequest(providerRequest2);
                    }
                    this.mProvider.mController.stop();
                }
                this.mRequest = ProviderRequest.EMPTY_REQUEST;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMockProviderLocation(Location location) {
        synchronized (this.mOwnerLock) {
            try {
                Preconditions.checkState(isMock());
                MockLocationProvider mockLocationProvider = this.mMockProvider;
                mockLocationProvider.getClass();
                Location location2 = new Location(location);
                location2.setIsFromMockProvider(true);
                mockLocationProvider.mLocation = location2;
                try {
                    mockLocationProvider.reportLocation(
                            LocationResult.wrap(new Location[] {location2}).validate());
                } catch (LocationResult.BadLocationException e) {
                    throw new IllegalArgumentException((Throwable) e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setProviderLocked(AbstractLocationProvider abstractLocationProvider) {
        AbstractLocationProvider.State state;
        AbstractLocationProvider abstractLocationProvider2 = this.mProvider;
        if (abstractLocationProvider2 == abstractLocationProvider) {
            return;
        }
        this.mProvider = abstractLocationProvider;
        if (abstractLocationProvider2 != null) {
            AbstractLocationProvider.Controller controller = abstractLocationProvider2.mController;
            controller.setListener(null);
            if (controller.mStarted) {
                controller.setRequest(ProviderRequest.EMPTY_REQUEST);
                controller.stop();
            }
        }
        AbstractLocationProvider abstractLocationProvider3 = this.mProvider;
        if (abstractLocationProvider3 != null) {
            state =
                    abstractLocationProvider3.mController.setListener(
                            new ListenerWrapper(abstractLocationProvider3));
            if (this.mStarted) {
                AbstractLocationProvider.Controller controller2 = this.mProvider.mController;
                if (!controller2.mStarted) {
                    controller2.start();
                }
                this.mProvider.mController.setRequest(this.mRequest);
            } else {
                AbstractLocationProvider.Controller controller3 = this.mProvider.mController;
                if (controller3.mStarted) {
                    controller3.setRequest(ProviderRequest.EMPTY_REQUEST);
                    this.mProvider.mController.stop();
                }
            }
        } else {
            state = AbstractLocationProvider.State.EMPTY_STATE;
        }
        setState(new DelegateLocationProvider$$ExternalSyntheticLambda0(1, state));
    }
}
