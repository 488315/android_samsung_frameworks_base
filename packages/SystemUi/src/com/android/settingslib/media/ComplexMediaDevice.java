package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplexMediaDevice extends MediaDevice {
    public ComplexMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str, RouteListingPreference.Item item) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str, item);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIcon() {
        return this.mContext.getDrawable(R.drawable.ic_media_avr_device);
    }

    @Override // com.android.settingslib.media.MediaDevice
    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(R.drawable.ic_media_avr_device);
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
}
