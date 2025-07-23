package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceIconInteractor {
    public final Context context;
    public final DeviceIconUtil iconUtil;

    public DeviceIconInteractor(Context context) {
        this.context = context;
        this.iconUtil = new DeviceIconUtil(context);
    }
}
