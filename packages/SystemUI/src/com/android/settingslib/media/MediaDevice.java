package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.NearbyDevice;
import android.media.RouteListingPreference;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.R;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MediaDevice implements Comparable {
    public final AudioManager mAudioManager;
    public int mConnectedRecord;
    public final Context mContext;
    public final RouteListingPreference.Item mItem;
    public int mRangeZone = 0;
    public final MediaRoute2Info mRouteInfo;
    public int mState;
    int mType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api34Impl {
        private Api34Impl() {
        }

        public static String composeSubtext(RouteListingPreference.Item item, Context context) {
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

        public static boolean hasOngoingSession(RouteListingPreference.Item item) {
            return (item == null || (item.getFlags() & 1) == 0) ? false : true;
        }

        public static boolean isHostForOngoingSession(RouteListingPreference.Item item) {
            int flags = item != null ? item.getFlags() : 0;
            return ((flags & 1) == 0 || (flags & 2) == 0) ? false : true;
        }

        public static boolean isSuggestedDevice(RouteListingPreference.Item item) {
            return (item == null || (item.getFlags() & 4) == 0) ? false : true;
        }
    }

    public MediaDevice(Context context, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item) {
        this.mContext = context;
        this.mRouteInfo = mediaRoute2Info;
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
        if (type == 3 || type == 4 || type == 5 || type == 6 || type == 19) {
            this.mType = 3;
            return;
        }
        if (type != 26) {
            if (type != 29) {
                if (type == 1003) {
                    this.mType = 8;
                    return;
                }
                if (type == 2000) {
                    this.mType = 7;
                    return;
                }
                if (type != 22) {
                    if (type != 23) {
                        switch (type) {
                            case 8:
                                break;
                            case 9:
                            case 10:
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
            return isConnected() ? -1 : 1;
        }
        if (this.mState == 4) {
            return -1;
        }
        if (mediaDevice.mState == 4) {
            return 1;
        }
        int i = this.mType;
        int i2 = mediaDevice.mType;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        if (isMutingExpectedDevice()) {
            return -1;
        }
        if (mediaDevice.isMutingExpectedDevice()) {
            return 1;
        }
        if (isFastPairDevice()) {
            return -1;
        }
        if (mediaDevice.isFastPairDevice()) {
            return 1;
        }
        if (isCarKitDevice()) {
            return -1;
        }
        if (mediaDevice.isCarKitDevice()) {
            return 1;
        }
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
        if (TextUtils.equals(str, mediaDevice.getId())) {
            return 1;
        }
        int i3 = this.mConnectedRecord;
        int i4 = mediaDevice.mConnectedRecord;
        return (i3 == i4 || (i4 <= 0 && i3 <= 0)) ? getName().compareToIgnoreCase(mediaDevice.getName()) : i4 - i3;
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
        return 1073741824;
    }

    public final int getDeviceType() {
        return this.mType;
    }

    public abstract Drawable getIcon();

    public abstract Drawable getIconWithoutBackground();

    public abstract String getId();

    public int getMaxVolume() {
        if (this.mRouteInfo != null) {
            return this.mAudioManager.getStreamMaxVolume(3) * 10;
        }
        Log.w("MediaDevice", "Unable to get max volume. RouteInfo is empty");
        return 0;
    }

    public abstract String getName();

    public int getSelectionBehavior() {
        RouteListingPreference.Item item = this.mItem;
        if (item != null) {
            return item.getSelectionBehavior();
        }
        return 1;
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

    public boolean isCarKitDevice() {
        return false;
    }

    public abstract boolean isConnected();

    public boolean isFastPairDevice() {
        return false;
    }

    public boolean isMutingExpectedDevice() {
        return false;
    }

    public final boolean isSuggestedDevice() {
        return Api34Impl.isSuggestedDevice(this.mItem);
    }

    public boolean isVolumeFixed() {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        if (mediaRoute2Info != null) {
            return mediaRoute2Info.getVolumeHandling() == 0;
        }
        Log.w("MediaDevice", "RouteInfo is empty, regarded as volume fixed.");
        return true;
    }
}
