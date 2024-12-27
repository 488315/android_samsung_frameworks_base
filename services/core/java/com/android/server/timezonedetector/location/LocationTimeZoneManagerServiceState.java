package com.android.server.timezonedetector.location;

import com.android.server.timezonedetector.LocationAlgorithmEvent;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LocationTimeZoneManagerServiceState {
    public final String mControllerState;
    public final List mControllerStates;
    public final LocationAlgorithmEvent mLastEvent;
    public final List mPrimaryProviderStates;
    public final List mSecondaryProviderStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public String mControllerState;
        public List mControllerStates;
        public LocationAlgorithmEvent mLastEvent;
        public List mPrimaryProviderStates;
        public List mSecondaryProviderStates;
    }

    public LocationTimeZoneManagerServiceState(Builder builder) {
        this.mControllerState = builder.mControllerState;
        this.mLastEvent = builder.mLastEvent;
        List list = builder.mControllerStates;
        Objects.requireNonNull(list);
        this.mControllerStates = list;
        List list2 = builder.mPrimaryProviderStates;
        Objects.requireNonNull(list2);
        this.mPrimaryProviderStates = list2;
        List list3 = builder.mSecondaryProviderStates;
        Objects.requireNonNull(list3);
        this.mSecondaryProviderStates = list3;
    }

    public final String toString() {
        return "LocationTimeZoneManagerServiceState{mControllerState="
                + this.mControllerState
                + ", mLastEvent="
                + this.mLastEvent
                + ", mControllerStates="
                + this.mControllerStates
                + ", mPrimaryProviderStates="
                + this.mPrimaryProviderStates
                + ", mSecondaryProviderStates="
                + this.mSecondaryProviderStates
                + '}';
    }
}
