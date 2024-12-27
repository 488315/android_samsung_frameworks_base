package com.android.server.location.nsflp;

import android.content.Context;
import android.location.LocationConstants;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.server.location.LocationPermissions;
import com.android.server.location.provider.LocationProviderManager;
import com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda1;
import com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda12;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NSLocationProviderHelper {
    public final Context mContext;
    public boolean mIsMotionPowerSaveMode;
    public final CopyOnWriteArrayList mMotionPowerListeners;
    public final NSConnectionHelper mNSConnectionHelper;
    public final NSPermissionHelper mNSPermissionHelper;
    public String mSuplAddress;
    public final Map mRegistrationMap = new ConcurrentHashMap();
    public boolean mIsAvailableMotionStop = true;

    public NSLocationProviderHelper(
            Context context,
            NSPermissionHelper nSPermissionHelper,
            NSConnectionHelper nSConnectionHelper) {
        Log.i("NSLocationProviderHelper", "constructor");
        this.mContext = context;
        this.mNSPermissionHelper = nSPermissionHelper;
        this.mNSConnectionHelper = nSConnectionHelper;
        this.mMotionPowerListeners = new CopyOnWriteArrayList();
    }

    public final void addGnssDataListener(
            IBinder iBinder,
            String str,
            LocationConstants.LISTENER_TYPE listener_type,
            int i,
            int i2,
            boolean z,
            boolean z2) {
        if (iBinder == null) {
            Log.w("NSLocationProviderHelper", "addGnssDataListener, binder is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putParcelable("listenerType", listener_type);
        bundle.putInt("uid", i);
        bundle.putInt("pid", i2);
        bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
        bundle.putBoolean("isAllowed", z);
        bundle.putBoolean("foreground", z2);
        bundle.putInt(
                "permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
        updateUidProcState(i, bundle);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.ADD_DATA_LISTENER, bundle);
    }

    public final void notifyMotionPowerSaveModeChanged(boolean z) {
        if (this.mIsMotionPowerSaveMode == z) {
            return;
        }
        this.mIsMotionPowerSaveMode = z;
        Iterator it = this.mMotionPowerListeners.iterator();
        while (it.hasNext()) {
            LocationProviderManager locationProviderManager =
                    ((LocationProviderManager$$ExternalSyntheticLambda1) it.next()).f$0;
            synchronized (locationProviderManager.mMultiplexerLock) {
                locationProviderManager.updateRegistrations(
                        new LocationProviderManager$$ExternalSyntheticLambda12(2));
            }
        }
    }

    public final void onAvailableMotionStop(boolean z) {
        if (this.mIsAvailableMotionStop == z) {
            return;
        }
        this.mIsAvailableMotionStop = z;
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAvailableMotionStop", z);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.AVAILABLE_MOTION_STOP, bundle);
    }

    public final void removeGnssDataListener(
            IBinder iBinder, LocationConstants.LISTENER_TYPE listener_type, int i, int i2) {
        if (iBinder == null) {
            Log.w("NSLocationProviderHelper", "removeGnssDataListener, binder is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
        bundle.putParcelable("listenerType", listener_type);
        bundle.putInt("uid", i);
        bundle.putInt("pid", i2);
        updateUidProcState(i, bundle);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.REMOVE_DATA_LISTENER, bundle);
    }

    public final void reportProviderStatus(
            LocationConstants.STATE_TYPE state_type, Integer num, Integer num2) {
        Bundle bundle = new Bundle();
        if (num != null) {
            bundle.putInt(Constants.JSON_CLIENT_DATA_STATUS, num.intValue());
        }
        if (num2 != null) {
            bundle.putInt("value", num2.intValue());
        }
        this.mNSConnectionHelper.onStateUpdated(state_type, bundle);
    }

    public final void updateGnssDataListener(
            IBinder iBinder,
            boolean z,
            String str,
            LocationConstants.LISTENER_TYPE listener_type,
            int i,
            int i2) {
        if (iBinder == null) {
            Log.w("NSLocationProviderHelper", "updateGnssDataListener, binder is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("isActive", z);
        bundle.putString("packageName", str);
        bundle.putParcelable("listenerType", listener_type);
        bundle.putInt("uid", i);
        bundle.putInt("pid", i2);
        bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
        bundle.putInt(
                "permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
        updateUidProcState(i, bundle);
        this.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.UPDATE_DATA_LISTENER, bundle);
    }

    public final void updateUidProcState(int i, Bundle bundle) {
        NSPermissionHelper.UidState uidState =
                (NSPermissionHelper.UidState)
                        this.mNSPermissionHelper.mUidObserver.mUidState.get(Integer.valueOf(i));
        if (uidState != null) {
            bundle.putInt("procState", uidState.state);
            bundle.putBoolean("hasLocationCapability", (uidState.capability & 1) == 1);
        }
    }
}
