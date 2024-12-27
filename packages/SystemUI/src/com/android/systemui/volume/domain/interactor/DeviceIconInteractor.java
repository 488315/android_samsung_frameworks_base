package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DeviceIconInteractor {
    public final Context context;
    public final DeviceIconUtil iconUtil;

    public DeviceIconInteractor(Context context) {
        this.context = context;
        this.iconUtil = new DeviceIconUtil(context);
    }
}
