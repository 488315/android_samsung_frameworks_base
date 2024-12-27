package com.android.server.location.settings;

import android.R;
import android.content.Context;
import android.os.Environment;
import android.util.SparseArray;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocationSettings {
    public final Context mContext;
    public final SparseArray mUserSettings = new SparseArray(1);
    public final CopyOnWriteArrayList mUserSettingsListeners = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LocationUserSettingsListener {
        void onLocationUserSettingsChanged(int i, LocationUserSettings locationUserSettings, LocationUserSettings locationUserSettings2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocationUserSettingsStore extends SettingsStore {
        public final int mUserId;

        public LocationUserSettingsStore(int i, File file) {
            super(file);
            this.mUserId = i;
        }

        public final LocationUserSettings filterSettings(LocationUserSettings locationUserSettings) {
            return (locationUserSettings.mAdasGnssLocationEnabled && !LocationSettings.this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") && locationUserSettings.mAdasGnssLocationEnabled) ? new LocationUserSettings(false) : locationUserSettings;
        }

        @Override // com.android.server.location.settings.SettingsStore
        public final LocationUserSettings read(int i, DataInput dataInput) {
            return filterSettings(new LocationUserSettings(i != 1 ? LocationSettings.this.mContext.getResources().getBoolean(R.bool.config_defaultEmergencyGestureSoundEnabled) : ((DataInputStream) dataInput).readBoolean()));
        }
    }

    public LocationSettings(Context context) {
        this.mContext = context;
    }

    public final void deleteFiles() throws InterruptedException {
        synchronized (this.mUserSettings) {
            try {
                int size = this.mUserSettings.size();
                for (int i = 0; i < size; i++) {
                    ((LocationUserSettingsStore) this.mUserSettings.valueAt(i)).deleteFile();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void flushFiles() throws InterruptedException {
        synchronized (this.mUserSettings) {
            try {
                int size = this.mUserSettings.size();
                for (int i = 0; i < size; i++) {
                    ((LocationUserSettingsStore) this.mUserSettings.valueAt(i)).flushFile();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final LocationUserSettings getUserSettings(int i) {
        LocationUserSettings locationUserSettings;
        LocationUserSettingsStore userSettingsStore = getUserSettingsStore(i);
        synchronized (userSettingsStore) {
            userSettingsStore.initializeCache();
            locationUserSettings = userSettingsStore.mCache;
        }
        return locationUserSettings;
    }

    public final LocationUserSettingsStore getUserSettingsStore(int i) {
        LocationUserSettingsStore locationUserSettingsStore;
        synchronized (this.mUserSettings) {
            try {
                locationUserSettingsStore = (LocationUserSettingsStore) this.mUserSettings.get(i);
                if (locationUserSettingsStore == null) {
                    LocationUserSettingsStore locationUserSettingsStore2 = new LocationUserSettingsStore(i, new File(new File(Environment.getDataSystemDeDirectory(i), "location"), "settings"));
                    this.mUserSettings.put(i, locationUserSettingsStore2);
                    locationUserSettingsStore = locationUserSettingsStore2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return locationUserSettingsStore;
    }
}
