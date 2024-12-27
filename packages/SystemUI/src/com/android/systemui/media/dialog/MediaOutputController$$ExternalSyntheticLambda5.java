package com.android.systemui.media.dialog;

import android.media.MediaRoute2Info;
import android.util.Log;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.MediaDevice;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaOutputController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ MediaOutputController f$0;
    public final /* synthetic */ MediaDevice f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ MediaOutputController$$ExternalSyntheticLambda5(MediaOutputController mediaOutputController, MediaDevice mediaDevice, int i) {
        this.f$0 = mediaOutputController;
        this.f$1 = mediaDevice;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaOutputController mediaOutputController = this.f$0;
        MediaDevice mediaDevice = this.f$1;
        int i = this.f$2;
        InfoMediaManager infoMediaManager = mediaOutputController.mLocalMediaManager.mInfoMediaManager;
        infoMediaManager.getClass();
        MediaRoute2Info mediaRoute2Info = mediaDevice.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("InfoMediaManager", "Unable to set volume. RouteInfo is empty");
        } else {
            infoMediaManager.setRouteVolume(mediaRoute2Info, i);
        }
    }
}
