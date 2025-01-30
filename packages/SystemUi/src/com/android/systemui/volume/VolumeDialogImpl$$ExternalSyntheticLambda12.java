package com.android.systemui.volume;

import android.widget.ImageButton;
import com.android.systemui.volume.VolumeDialogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda12 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda12(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageButton imageButton = (ImageButton) this.f$0;
                if (imageButton != null) {
                    imageButton.setPressed(false);
                    break;
                }
                break;
            default:
                VolumeDialogImpl.RingerDrawerItemClickListener ringerDrawerItemClickListener = (VolumeDialogImpl.RingerDrawerItemClickListener) this.f$0;
                ringerDrawerItemClickListener.this$0.mRingerDrawerNewSelectionBg.setAlpha(0.0f);
                if (ringerDrawerItemClickListener.this$0.isLandscape()) {
                    VolumeDialogImpl volumeDialogImpl = ringerDrawerItemClickListener.this$0;
                    volumeDialogImpl.mSelectedRingerContainer.setTranslationX(volumeDialogImpl.getTranslationInDrawerForRingerMode(ringerDrawerItemClickListener.mClickedRingerMode));
                } else {
                    VolumeDialogImpl volumeDialogImpl2 = ringerDrawerItemClickListener.this$0;
                    volumeDialogImpl2.mSelectedRingerContainer.setTranslationY(volumeDialogImpl2.getTranslationInDrawerForRingerMode(ringerDrawerItemClickListener.mClickedRingerMode));
                }
                ringerDrawerItemClickListener.this$0.mSelectedRingerContainer.setVisibility(0);
                ringerDrawerItemClickListener.this$0.hideRingerDrawer();
                break;
        }
    }
}
