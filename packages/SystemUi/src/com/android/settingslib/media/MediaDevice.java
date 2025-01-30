package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.NearbyDevice;
import android.media.RouteListingPreference;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaDevice implements Comparable {
    public final AudioManager mAudioManager;
    public int mConnectedRecord;
    public final Context mContext;
    public final RouteListingPreference.Item mItem;
    public final String mPackageName;
    public int mRangeZone = 0;
    public final MediaRoute2Info mRouteInfo;
    public final MediaRouter2Manager mRouterManager;
    public int mState;
    int mType;

    public MediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, RouteListingPreference.Item item) {
        this.mContext = context;
        this.mRouteInfo = mediaRoute2Info;
        this.mRouterManager = mediaRouter2Manager;
        this.mPackageName = str;
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mItem = item;
        if (mediaRoute2Info == null) {
            this.mType = 5;
            return;
        }
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            this.mType = 1;
            return;
        }
        if (type == 3 || type == 4) {
            this.mType = 3;
            return;
        }
        if (type != 8) {
            if (type != 9 && type != 22) {
                if (type != 23 && type != 26) {
                    if (type == 2000) {
                        this.mType = 7;
                        return;
                    }
                    switch (type) {
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            this.mType = 6;
                            break;
                    }
                    return;
                }
            }
            this.mType = 2;
            return;
        }
        this.mType = 5;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        String str;
        MediaDevice mediaDevice = (MediaDevice) obj;
        if (mediaDevice == null) {
            return -1;
        }
        if (isConnected() ^ mediaDevice.isConnected()) {
            if (isConnected()) {
                return -1;
            }
        } else {
            if (this.mState == 4) {
                return -1;
            }
            if (mediaDevice.mState != 4) {
                int i = this.mType;
                int i2 = mediaDevice.mType;
                if (i == i2) {
                    if (isMutingExpectedDevice()) {
                        return -1;
                    }
                    if (!mediaDevice.isMutingExpectedDevice()) {
                        if (isFastPairDevice()) {
                            return -1;
                        }
                        if (!mediaDevice.isFastPairDevice()) {
                            if (isCarKitDevice()) {
                                return -1;
                            }
                            if (!mediaDevice.isCarKitDevice()) {
                                if (NearbyDevice.compareRangeZones(this.mRangeZone, mediaDevice.mRangeZone) != 0) {
                                    return NearbyDevice.compareRangeZones(this.mRangeZone, mediaDevice.mRangeZone);
                                }
                                ConnectionRecordManager connectionRecordManager = ConnectionRecordManager.getInstance();
                                synchronized (connectionRecordManager) {
                                    str = connectionRecordManager.mLastSelectedDevice;
                                }
                                if (TextUtils.equals(str, getId())) {
                                    return -1;
                                }
                                if (!TextUtils.equals(str, mediaDevice.getId())) {
                                    int i3 = this.mConnectedRecord;
                                    int i4 = mediaDevice.mConnectedRecord;
                                    return (i3 == i4 || (i4 <= 0 && i3 <= 0)) ? getName().compareToIgnoreCase(mediaDevice.getName()) : i4 - i3;
                                }
                            }
                        }
                    }
                } else if (i < i2) {
                    return -1;
                }
            }
        }
        return 1;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof MediaDevice) {
            return ((MediaDevice) obj).getId().equals(getId());
        }
        return false;
    }

    public String getAddress() {
        return "";
    }

    public int getCurrentVolume() {
        if (this.mRouteInfo != null) {
            return this.mAudioManager.semGetFineVolume(3);
        }
        Log.w("MediaDevice", "Unable to get current volume. RouteInfo is empty");
        return 0;
    }

    public int getDevice() {
        return VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
    }

    public final int getDeviceType() {
        return this.mType;
    }

    public abstract Drawable getIcon();

    public abstract Drawable getIconWithoutBackground();

    public abstract String getId();

    public abstract String getName();

    public int getSelectionBehavior() {
        RouteListingPreference.Item item = this.mItem;
        if (item != null) {
            return item.getSelectionBehavior();
        }
        return 1;
    }

    public final String getSubtextString() {
        RouteListingPreference.Item item = this.mItem;
        if (item == null) {
            return null;
        }
        Context context = this.mContext;
        int subText = item.getSubText();
        if (subText == 10000) {
            return (String) item.getCustomSubtextMessage();
        }
        switch (subText) {
            case 1:
                return context.getString(R.string.media_output_status_unknown_error);
            case 2:
                return context.getString(R.string.media_output_status_require_premium);
            case 3:
                return context.getString(R.string.media_output_status_not_support_downloads);
            case 4:
                return context.getString(R.string.media_output_status_try_after_ad);
            case 5:
                return context.getString(R.string.media_output_status_device_in_low_power_mode);
            case 6:
                return context.getString(R.string.media_output_status_unauthorized);
            case 7:
                return context.getString(R.string.media_output_status_track_unsupported);
            default:
                return "";
        }
    }

    public final boolean hasOngoingSession() {
        RouteListingPreference.Item item = this.mItem;
        return item != null && (item.getFlags() & 1) != 0;
    }

    public final void initDeviceRecord() {
        int i;
        ConnectionRecordManager connectionRecordManager = ConnectionRecordManager.getInstance();
        Context context = this.mContext;
        synchronized (connectionRecordManager) {
            connectionRecordManager.mLastSelectedDevice = context.getSharedPreferences("seamless_transfer_record", 0).getString("last_selected_device", null);
        }
        ConnectionRecordManager connectionRecordManager2 = ConnectionRecordManager.getInstance();
        Context context2 = this.mContext;
        String id = getId();
        synchronized (connectionRecordManager2) {
            i = context2.getSharedPreferences("seamless_transfer_record", 0).getInt(id, 0);
        }
        this.mConnectedRecord = i;
    }

    public final boolean isBLEDevice() {
        return this.mRouteInfo.getType() == 26;
    }

    public boolean isCarKitDevice() {
        return false;
    }

    public abstract boolean isConnected();

    public boolean isFastPairDevice() {
        return false;
    }

    public final boolean isHostForOngoingSession() {
        RouteListingPreference.Item item = this.mItem;
        int flags = item != null ? item.getFlags() : 0;
        return (flags & 1) != 0 && (flags & 2) != 0;
    }

    public boolean isMutingExpectedDevice() {
        return false;
    }

    public void requestSetVolume(int i) {
        if (this.mRouteInfo == null) {
            Log.w("MediaDevice", "Unable to set volume. RouteInfo is empty");
        } else {
            this.mAudioManager.semSetFineVolume(3, i, 0);
        }
    }
}
