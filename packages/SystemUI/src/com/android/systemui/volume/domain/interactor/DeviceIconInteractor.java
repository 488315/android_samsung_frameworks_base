package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;

public final class DeviceIconInteractor {
    public final Context context;
    public final DeviceIconUtil iconUtil;

    public DeviceIconInteractor(Context context) {
        this.context = context;
        this.iconUtil = new DeviceIconUtil(context);
    }
}
