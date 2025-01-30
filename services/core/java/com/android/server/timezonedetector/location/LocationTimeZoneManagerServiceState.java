package com.android.server.timezonedetector.location;

import com.android.server.timezonedetector.LocationAlgorithmEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class LocationTimeZoneManagerServiceState {
    public final String mControllerState;
    public final List mControllerStates;
    public final LocationAlgorithmEvent mLastEvent;
    public final List mPrimaryProviderStates;
    public final List mSecondaryProviderStates;

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

    public LocationAlgorithmEvent getLastEvent() {
        return this.mLastEvent;
    }

    public List getControllerStates() {
        return this.mControllerStates;
    }

    public List getPrimaryProviderStates() {
        return Collections.unmodifiableList(this.mPrimaryProviderStates);
    }

    public List getSecondaryProviderStates() {
        return Collections.unmodifiableList(this.mSecondaryProviderStates);
    }

    public String toString() {
        return "LocationTimeZoneManagerServiceState{mControllerState=" + this.mControllerState + ", mLastEvent=" + this.mLastEvent + ", mControllerStates=" + this.mControllerStates + ", mPrimaryProviderStates=" + this.mPrimaryProviderStates + ", mSecondaryProviderStates=" + this.mSecondaryProviderStates + '}';
    }

    public final class Builder {
        public String mControllerState;
        public List mControllerStates;
        public LocationAlgorithmEvent mLastEvent;
        public List mPrimaryProviderStates;
        public List mSecondaryProviderStates;

        public Builder setControllerState(String str) {
            this.mControllerState = str;
            return this;
        }

        public Builder setLastEvent(LocationAlgorithmEvent locationAlgorithmEvent) {
            Objects.requireNonNull(locationAlgorithmEvent);
            this.mLastEvent = locationAlgorithmEvent;
            return this;
        }

        public Builder setStateChanges(List list) {
            this.mControllerStates = new ArrayList(list);
            return this;
        }

        public Builder setPrimaryProviderStateChanges(List list) {
            this.mPrimaryProviderStates = new ArrayList(list);
            return this;
        }

        public Builder setSecondaryProviderStateChanges(List list) {
            this.mSecondaryProviderStates = new ArrayList(list);
            return this;
        }

        public LocationTimeZoneManagerServiceState build() {
            return new LocationTimeZoneManagerServiceState(this);
        }
    }
}
