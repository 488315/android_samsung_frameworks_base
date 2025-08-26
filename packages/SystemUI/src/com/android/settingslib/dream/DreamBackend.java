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

/* loaded from: classes.dex */
public class DreamBackend {
    public static DreamBackend sInstance;
    public final Context mContext;
    public final Set mDisabledDreams;
    public Set mSupportedComplications;

    public class DreamInfo {
        public final String toString() {
            return DreamInfo.class.getSimpleName() + "[null,null]";
        }
    }

    public class DreamInfoComparator implements Comparator {
        public DreamInfoComparator(ComponentName componentName) {
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ((DreamInfo) obj).getClass();
            throw null;
        }
    }

    public DreamBackend(Context context) throws Resources.NotFoundException {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        Resources resources = applicationContext.getResources();
        IDreamManager iDreamManagerAsInterface = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        ComponentName defaultDreamComponentForUser = null;
        if (iDreamManagerAsInterface != null) {
            try {
                defaultDreamComponentForUser = iDreamManagerAsInterface.getDefaultDreamComponentForUser(applicationContext.getUserId());
            } catch (RemoteException e) {
                Log.w("DreamBackend", "Failed to get default dream", e);
            }
        }
        new DreamInfoComparator(defaultDreamComponentForUser);
        resources.getBoolean(R.bool.config_enableAppWidgetService);
        resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay);
        resources.getBoolean(R.bool.config_earcFeatureEnabled_default);
        resources.getBoolean(R.bool.config_emergencyGestureEnabled);
        this.mDisabledDreams = (Set) Arrays.stream(resources.getStringArray(R.array.config_udfps_sensor_props)).map(new DreamBackend$$ExternalSyntheticLambda0()).collect(Collectors.toSet());
        Arrays.stream(resources.getStringArray(17236255)).toList();
        this.mSupportedComplications = (Set) Arrays.stream(resources.getIntArray(17236339)).boxed().collect(Collectors.toSet());
    }

    public void setSupportedComplications(Set<Integer> set) {
        this.mSupportedComplications = set;
    }
}
