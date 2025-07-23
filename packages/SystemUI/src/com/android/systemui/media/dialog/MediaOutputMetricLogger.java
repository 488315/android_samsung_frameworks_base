package com.android.systemui.media.dialog;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.settingslib.media.MediaDevice;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MediaOutputMetricLogger {
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
        switch (mediaDevice.getDeviceType()) {
            case 1:
                return 1;
            case 2:
                return 200;
            case 3:
                return 100;
            case 4:
            default:
                return 0;
            case 5:
                return 300;
            case 6:
                return 400;
            case 7:
                return 500;
            case 8:
                return VolteConstants.ErrorCode.BUSY_EVERYWHERE;
        }
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
        int i = 0;
        this.mRemoteDeviceCount = 0;
        this.mConnectedBluetoothDeviceCount = 0;
        this.mWiredDeviceCount = 0;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            MediaItem mediaItem = (MediaItem) obj;
            if (mediaItem.mMediaDeviceOptional.isPresent() && ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).isConnected()) {
                int deviceType = ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).getDeviceType();
                if (deviceType == 2 || deviceType == 3) {
                    this.mWiredDeviceCount++;
                } else if (deviceType == 5) {
                    this.mConnectedBluetoothDeviceCount++;
                } else if (deviceType == 6 || deviceType == 7) {
                    this.mRemoteDeviceCount++;
                }
            }
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("connected devices: wired: ");
            sb.append(this.mWiredDeviceCount);
            sb.append(" bluetooth: ");
            sb.append(this.mConnectedBluetoothDeviceCount);
            sb.append(" remote: ");
            RecyclerView$$ExternalSyntheticOutline0.m(this.mRemoteDeviceCount, "MediaOutputMetricLogger", sb);
        }
    }
}
