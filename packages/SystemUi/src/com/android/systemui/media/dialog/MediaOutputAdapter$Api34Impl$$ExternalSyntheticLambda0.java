package com.android.systemui.media.dialog;

import android.view.View;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputAdapter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ MediaDevice f$1;

    public /* synthetic */ MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(Object obj, MediaDevice mediaDevice, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = mediaDevice;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ((MediaOutputController) this.f$0).tryToLaunchInAppRoutingIntent(view, this.f$1.getId());
                break;
            case 1:
                MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder = (MediaOutputAdapter.MediaDeviceViewHolder) this.f$0;
                MediaDevice mediaDevice = this.f$1;
                int i = MediaOutputAdapter.MediaDeviceViewHolder.$r8$clinit;
                mediaDeviceViewHolder.onItemClick(mediaDevice);
                break;
            case 2:
                MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder2 = (MediaOutputAdapter.MediaDeviceViewHolder) this.f$0;
                MediaDevice mediaDevice2 = this.f$1;
                int i2 = MediaOutputAdapter.MediaDeviceViewHolder.$r8$clinit;
                mediaDeviceViewHolder2.onItemClick(mediaDevice2);
                break;
            case 3:
                MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder3 = (MediaOutputAdapter.MediaDeviceViewHolder) this.f$0;
                MediaDevice mediaDevice3 = this.f$1;
                int i3 = MediaOutputAdapter.MediaDeviceViewHolder.$r8$clinit;
                mediaDeviceViewHolder3.onItemClick(mediaDevice3);
                break;
            case 4:
                MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder4 = (MediaOutputAdapter.MediaDeviceViewHolder) this.f$0;
                MediaDevice mediaDevice4 = this.f$1;
                int i4 = MediaOutputAdapter.MediaDeviceViewHolder.$r8$clinit;
                mediaDeviceViewHolder4.onItemClick(mediaDevice4);
                break;
            default:
                MediaOutputAdapter.this.mController.tryToLaunchInAppRoutingIntent(view, this.f$1.getId());
                break;
        }
    }
}
