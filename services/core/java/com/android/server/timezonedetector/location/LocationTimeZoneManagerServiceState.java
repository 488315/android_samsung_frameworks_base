package com.android.server.timezonedetector.location;

import com.android.server.timezonedetector.LocationAlgorithmEvent;

import java.util.List;
import java.util.Objects;

public final class LocationTimeZoneManagerServiceState {
    public final String mControllerState;
    public final List mControllerStates;
    public final LocationAlgorithmEvent mLastEvent;
    public final List mPrimaryProviderStates;
    public final List mSecondaryProviderStates;

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
