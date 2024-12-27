package com.android.systemui.media.dialog;

import android.view.View;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputAdapter;

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
                ((MediaOutputAdapter.MediaDeviceViewHolder) this.f$0).onItemClick(this.f$1);
                break;
            case 2:
                ((MediaOutputAdapter.MediaDeviceViewHolder) this.f$0).onItemClick(this.f$1);
                break;
            case 3:
                ((MediaOutputAdapter.MediaDeviceViewHolder) this.f$0).onItemClick(this.f$1);
                break;
            case 4:
                ((MediaOutputAdapter.MediaDeviceViewHolder) this.f$0).onItemClick(this.f$1);
                break;
            default:
                MediaOutputAdapter.this.mController.tryToLaunchInAppRoutingIntent(view, this.f$1.getId());
                break;
        }
    }
}
