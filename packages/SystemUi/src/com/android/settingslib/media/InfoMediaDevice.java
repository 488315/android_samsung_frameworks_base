package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InfoMediaDevice extends MediaDevice {
    public InfoMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, RouteListingPreference.Item item) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str, item);
        initDeviceRecord();
    }

    public int getDrawableResIdByType() {
        int type = this.mRouteInfo.getType();
        if (type == 1001) {
            return R.drawable.ic_media_display_device;
        }
        if (type == 2000) {
            return R.drawable.ic_media_group_device;
        }
        switch (type) {
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                return R.drawable.ic_media_tablet;
            case 1005:
                return R.drawable.ic_dock_device;
            case 1006:
                return R.drawable.ic_media_computer;
            case 1007:
                return R.drawable.ic_media_game_console;
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                return R.drawable.ic_media_car;
            case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                return R.drawable.ic_media_smartwatch;
            case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                return R.drawable.ic_smartphone;
            default:
                return R.drawable.ic_media_speaker_device;
        }
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        return getIconWithoutBackground();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResIdByType());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getId() {
        return this.mRouteInfo.getId();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final String getName() {
        return this.mRouteInfo.getName().toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final boolean isConnected() {
        return true;
    }

    public InfoMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        this(context, mediaRouter2Manager, mediaRoute2Info, str, null);
    }
}
