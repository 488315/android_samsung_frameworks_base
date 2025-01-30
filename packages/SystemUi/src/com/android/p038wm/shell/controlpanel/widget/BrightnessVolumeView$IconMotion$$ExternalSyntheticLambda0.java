package com.android.p038wm.shell.controlpanel.widget;

import android.view.View;
import com.android.p038wm.shell.controlpanel.widget.BrightnessVolumeView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BrightnessVolumeView.IconMotion f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ View f$3;
    public final /* synthetic */ View f$4;
    public final /* synthetic */ View f$5;
    public final /* synthetic */ View f$6;
    public final /* synthetic */ View f$7;
    public final /* synthetic */ View f$8;

    public /* synthetic */ BrightnessVolumeView$IconMotion$$ExternalSyntheticLambda0(BrightnessVolumeView.IconMotion iconMotion, int i, int i2, View view, View view2, View view3, View view4, View view5, View view6, int i3) {
        this.$r8$classId = i3;
        this.f$0 = iconMotion;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = view;
        this.f$4 = view2;
        this.f$5 = view3;
        this.f$6 = view4;
        this.f$7 = view5;
        this.f$8 = view6;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BrightnessVolumeView.IconMotion iconMotion = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                View view = this.f$3;
                View view2 = this.f$4;
                View view3 = this.f$5;
                View view4 = this.f$6;
                View view5 = this.f$7;
                View view6 = this.f$8;
                iconMotion.getClass();
                if (i != 3) {
                    if (i == 1 || i == 0) {
                        iconMotion.startMinAnimation(i2, i, view, view2, view3, view4, view5, view6, false);
                        break;
                    }
                } else {
                    iconMotion.startMaxAnimation(i2, view, view2, view3, view4, view5, view6, false);
                    break;
                }
            default:
                BrightnessVolumeView.IconMotion iconMotion2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                View view7 = this.f$3;
                View view8 = this.f$4;
                View view9 = this.f$5;
                View view10 = this.f$6;
                View view11 = this.f$7;
                View view12 = this.f$8;
                iconMotion2.getClass();
                if (i3 != 2 && i3 != 3) {
                    if (i3 == 0) {
                        iconMotion2.startMuteAnimation(i4, view7, view8, view9, view10, view11, view12, false);
                        break;
                    }
                } else {
                    iconMotion2.startMidAnimation(i4, i3, view7, view8, view9, view10, view11, view12, false);
                    break;
                }
                break;
        }
    }
}
