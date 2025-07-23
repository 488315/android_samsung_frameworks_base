package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ComplexMediaDevice extends MediaDevice {
    public ComplexMediaDevice(Context context, MediaRoute2Info mediaRoute2Info, RouteListingPreference.Item item) {
        super(context, mediaRoute2Info, item);
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
