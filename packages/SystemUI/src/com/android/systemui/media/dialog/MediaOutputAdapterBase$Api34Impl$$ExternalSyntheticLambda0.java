package com.android.systemui.media.dialog;

import android.view.View;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputAdapterBase;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ MediaDevice f$1;

    public /* synthetic */ MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(Object obj, MediaDevice mediaDevice, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = mediaDevice;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((MediaSwitchingController) this.f$0).tryToLaunchInAppRoutingIntent(view, this.f$1.getId());
                break;
            case 1:
                MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase = (MediaOutputAdapterBase.MediaDeviceViewHolderBase) this.f$0;
                MediaDevice mediaDevice = this.f$1;
                int i = MediaOutputAdapterBase.MediaDeviceViewHolderBase.$r8$clinit;
                mediaDeviceViewHolderBase.onItemClick(mediaDevice);
                break;
            case 2:
                MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase2 = (MediaOutputAdapterBase.MediaDeviceViewHolderBase) this.f$0;
                MediaDevice mediaDevice2 = this.f$1;
                int i2 = MediaOutputAdapterBase.MediaDeviceViewHolderBase.$r8$clinit;
                mediaDeviceViewHolderBase2.onItemClick(mediaDevice2);
                break;
            case 3:
                MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase3 = (MediaOutputAdapterBase.MediaDeviceViewHolderBase) this.f$0;
                MediaDevice mediaDevice3 = this.f$1;
                int i3 = MediaOutputAdapterBase.MediaDeviceViewHolderBase.$r8$clinit;
                mediaDeviceViewHolderBase3.onItemClick(mediaDevice3);
                break;
            default:
                MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase4 = (MediaOutputAdapterBase.MediaDeviceViewHolderBase) this.f$0;
                MediaDevice mediaDevice4 = this.f$1;
                int i4 = MediaOutputAdapterBase.MediaDeviceViewHolderBase.$r8$clinit;
                mediaDeviceViewHolderBase4.onItemClick(mediaDevice4);
                break;
        }
    }
}
