package com.android.settingslib.media;

import android.app.Notification;
import android.content.Context;
import com.android.settingslib.media.LocalMediaManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaManager {
    public final Context mContext;
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final List mMediaDevices = new CopyOnWriteArrayList();

    public MediaManager(Context context, Notification notification2) {
        this.mContext = context;
    }

    public final void dispatchDeviceListAdded() {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            LocalMediaManager.MediaDeviceCallback mediaDeviceCallback = (LocalMediaManager.MediaDeviceCallback) it.next();
            ArrayList arrayList = new ArrayList(this.mMediaDevices);
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                LocalMediaManager.this.mMediaDevices.clear();
                LocalMediaManager.this.mMediaDevices.addAll(arrayList);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    int i = ((MediaDevice) it2.next()).mType;
                    if (i == 2 || i == 3 || i == 1) {
                        BluetoothMediaDevice mutingExpectedDevice = mediaDeviceCallback.getMutingExpectedDevice();
                        if (mutingExpectedDevice != null) {
                            LocalMediaManager.this.mMediaDevices.add(mutingExpectedDevice);
                        }
                    }
                }
            }
            LocalMediaManager.this.mInfoMediaManager.getClass();
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = localMediaManager.updateCurrentConnectedDevice();
            LocalMediaManager localMediaManager2 = LocalMediaManager.this;
            localMediaManager2.getClass();
            ArrayList arrayList2 = new ArrayList(localMediaManager2.mMediaDevices);
            Iterator it3 = ((CopyOnWriteArrayList) localMediaManager2.getCallbacks()).iterator();
            while (it3.hasNext()) {
                ((LocalMediaManager.DeviceCallback) it3.next()).onDeviceListUpdate(arrayList2);
            }
            MediaDevice mediaDevice = LocalMediaManager.this.mOnTransferBluetoothDevice;
            if (mediaDevice != null && mediaDevice.isConnected()) {
                LocalMediaManager localMediaManager3 = LocalMediaManager.this;
                localMediaManager3.connectDevice(localMediaManager3.mOnTransferBluetoothDevice);
                LocalMediaManager localMediaManager4 = LocalMediaManager.this;
                MediaDevice mediaDevice2 = localMediaManager4.mOnTransferBluetoothDevice;
                mediaDevice2.mState = 0;
                localMediaManager4.dispatchSelectedDeviceStateChanged(mediaDevice2);
                LocalMediaManager.this.mOnTransferBluetoothDevice = null;
            }
        }
    }
}
