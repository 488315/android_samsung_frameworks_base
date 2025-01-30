package com.android.systemui.media.dialog;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.media.MediaDevice;
import com.samsung.android.knox.net.firewall.FirewallRule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputMetricLogger {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputMetricLogger", 3);
    public int mConnectedBluetoothDeviceCount;
    public final Context mContext;
    public final String mPackageName;
    public int mRemoteDeviceCount;
    public MediaDevice mSourceDevice;
    public MediaDevice mTargetDevice;
    public int mWiredDeviceCount;

    public MediaOutputMetricLogger(Context context, String str) {
        this.mContext = context;
        this.mPackageName = str;
    }

    public static int getInteractionDeviceType(MediaDevice mediaDevice) {
        if (mediaDevice == null) {
            return 0;
        }
        int deviceType = mediaDevice.getDeviceType();
        if (deviceType == 1) {
            return 1;
        }
        if (deviceType == 2) {
            return 200;
        }
        if (deviceType == 3) {
            return 100;
        }
        if (deviceType == 5) {
            return 300;
        }
        if (deviceType != 6) {
            return deviceType != 7 ? 0 : 500;
        }
        return 400;
    }

    public static int getLoggingDeviceType(MediaDevice mediaDevice) {
        if (mediaDevice == null) {
            return 0;
        }
        int deviceType = mediaDevice.getDeviceType();
        if (deviceType == 1) {
            return 1;
        }
        if (deviceType == 2) {
            return 200;
        }
        if (deviceType == 3) {
            return 100;
        }
        if (deviceType == 5) {
            return 300;
        }
        if (deviceType != 6) {
            return deviceType != 7 ? 0 : 500;
        }
        return 400;
    }

    public final String getLoggingPackageName() {
        String str = this.mPackageName;
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            int i = this.mContext.getPackageManager().getApplicationInfo(str, 0).flags;
            return ((i & 1) == 0 && (i & 128) == 0) ? "" : str;
        } catch (Exception unused) {
            Log.e("MediaOutputMetricLogger", str + FirewallRule.IS_INVALID);
            return "";
        }
    }

    public final void updateLoggingMediaItemCount(List list) {
        this.mRemoteDeviceCount = 0;
        this.mConnectedBluetoothDeviceCount = 0;
        this.mWiredDeviceCount = 0;
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            MediaItem mediaItem = (MediaItem) it.next();
            if (mediaItem.mMediaDeviceOptional.isPresent()) {
                Optional optional = mediaItem.mMediaDeviceOptional;
                if (((MediaDevice) optional.get()).isConnected()) {
                    int deviceType = ((MediaDevice) optional.get()).getDeviceType();
                    if (deviceType == 2 || deviceType == 3) {
                        this.mWiredDeviceCount++;
                    } else if (deviceType == 5) {
                        this.mConnectedBluetoothDeviceCount++;
                    } else if (deviceType == 6 || deviceType == 7) {
                        this.mRemoteDeviceCount++;
                    }
                }
            }
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("connected devices: wired: ");
            sb.append(this.mWiredDeviceCount);
            sb.append(" bluetooth: ");
            sb.append(this.mConnectedBluetoothDeviceCount);
            sb.append(" remote: ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mRemoteDeviceCount, "MediaOutputMetricLogger");
        }
    }
}
