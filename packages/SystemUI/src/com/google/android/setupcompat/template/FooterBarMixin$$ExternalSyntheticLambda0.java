package com.google.android.setupcompat.template;

import android.content.res.Configuration;
import android.widget.Button;
import com.google.android.setupcompat.util.KeyboardHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class FooterBarMixin$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FooterBarMixin f$0;
    public final /* synthetic */ Button f$1;

    public /* synthetic */ FooterBarMixin$$ExternalSyntheticLambda0(FooterBarMixin footerBarMixin, Button button, int i) {
        this.$r8$classId = i;
        this.f$0 = footerBarMixin;
        this.f$1 = button;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FooterBarMixin footerBarMixin = this.f$0;
                Button button = this.f$1;
                if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(footerBarMixin.context)) {
                    Configuration configuration = footerBarMixin.context.getResources().getConfiguration();
                    if (configuration.keyboard != 1 && configuration.hardKeyboardHidden != 2) {
                        button.requestFocus();
                        break;
                    }
                }
                break;
            default:
                FooterBarMixin footerBarMixin2 = this.f$0;
                Button button2 = this.f$1;
                if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(footerBarMixin2.context)) {
                    Configuration configuration2 = footerBarMixin2.context.getResources().getConfiguration();
                    if (configuration2.keyboard != 1 && configuration2.hardKeyboardHidden != 2) {
                        if (footerBarMixin2.primaryButtonId == 0 || footerBarMixin2.getPrimaryButtonView().getVisibility() != 0) {
                            button2.requestFocus();
                            break;
                        }
                    }
                }
                break;
        }
    }
}
