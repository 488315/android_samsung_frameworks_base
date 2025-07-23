package com.android.settingslib.dream;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import android.util.Log;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DreamBackend {
    public static DreamBackend sInstance;
    public final Context mContext;
    public final Set mDisabledDreams;
    public Set mSupportedComplications;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DreamInfo {
        public final String toString() {
            return DreamInfo.class.getSimpleName() + "[null,null]";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DreamInfoComparator implements Comparator {
        public DreamInfoComparator(ComponentName componentName) {
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ((DreamInfo) obj).getClass();
            throw null;
        }
    }

    public DreamBackend(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        Resources resources = applicationContext.getResources();
        IDreamManager asInterface = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        ComponentName componentName = null;
        if (asInterface != null) {
            try {
                componentName = asInterface.getDefaultDreamComponentForUser(applicationContext.getUserId());
            } catch (RemoteException e) {
                Log.w("DreamBackend", "Failed to get default dream", e);
            }
        }
        new DreamInfoComparator(componentName);
        resources.getBoolean(R.bool.config_enableAppWidgetService);
        resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay);
        resources.getBoolean(R.bool.config_earcFeatureEnabled_default);
        resources.getBoolean(R.bool.config_emergencyGestureEnabled);
        this.mDisabledDreams = (Set) Arrays.stream(resources.getStringArray(R.array.config_udfps_enroll_stage_thresholds)).map(new DreamBackend$$ExternalSyntheticLambda0()).collect(Collectors.toSet());
        Arrays.stream(resources.getStringArray(17236254)).toList();
        this.mSupportedComplications = (Set) Arrays.stream(resources.getIntArray(17236338)).boxed().collect(Collectors.toSet());
    }

    public void setSupportedComplications(Set<Integer> set) {
        this.mSupportedComplications = set;
    }
}
