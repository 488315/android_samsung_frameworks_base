package com.android.server.location.altitude;

import android.content.Context;
import android.frameworks.location.altitude.AddMslAltitudeToLocationRequest;
import android.frameworks.location.altitude.AddMslAltitudeToLocationResponse;
import android.frameworks.location.altitude.GetGeoidHeightRequest;
import android.frameworks.location.altitude.GetGeoidHeightResponse;
import android.frameworks.location.altitude.IAltitudeService;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.location.Location;
import android.location.altitude.AltitudeConverter;

import com.android.server.SystemService;

import java.io.IOException;

public final class AltitudeService extends IAltitudeService.Stub {
    public final AltitudeConverter mAltitudeConverter = new AltitudeConverter();
    public final Context mContext;

    public final class Lifecycle extends SystemService {
        public static final String SERVICE_NAME =
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), IAltitudeService.DESCRIPTOR, "/default");

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService(SERVICE_NAME, new AltitudeService(getContext()));
        }
    }

    public AltitudeService(Context context) {
        this.mContext = context;
    }

    public final AddMslAltitudeToLocationResponse addMslAltitudeToLocation(
            AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) {
        Location location = new Location("");
        location.setLatitude(addMslAltitudeToLocationRequest.latitudeDegrees);
        location.setLongitude(addMslAltitudeToLocationRequest.longitudeDegrees);
        location.setAltitude(addMslAltitudeToLocationRequest.altitudeMeters);
        location.setVerticalAccuracyMeters(addMslAltitudeToLocationRequest.verticalAccuracyMeters);
        AddMslAltitudeToLocationResponse addMslAltitudeToLocationResponse =
                new AddMslAltitudeToLocationResponse();
        try {
            this.mAltitudeConverter.addMslAltitudeToLocation(this.mContext, location);
            addMslAltitudeToLocationResponse.mslAltitudeMeters = location.getMslAltitudeMeters();
            addMslAltitudeToLocationResponse.mslAltitudeAccuracyMeters =
                    location.getMslAltitudeAccuracyMeters();
            addMslAltitudeToLocationResponse.success = true;
            return addMslAltitudeToLocationResponse;
        } catch (IOException unused) {
            addMslAltitudeToLocationResponse.success = false;
            return addMslAltitudeToLocationResponse;
        }
    }

    public final GetGeoidHeightResponse getGeoidHeight(
            GetGeoidHeightRequest getGeoidHeightRequest) {
        try {
            return this.mAltitudeConverter.getGeoidHeight(this.mContext, getGeoidHeightRequest);
        } catch (IOException unused) {
            GetGeoidHeightResponse getGeoidHeightResponse = new GetGeoidHeightResponse();
            getGeoidHeightResponse.success = false;
            return getGeoidHeightResponse;
        }
    }

    public final String getInterfaceHash() {
        return "e47d23f579ff7a897fb03e7e7f1c3006cfc6036b";
    }

    public final int getInterfaceVersion() {
        return 2;
    }
}
