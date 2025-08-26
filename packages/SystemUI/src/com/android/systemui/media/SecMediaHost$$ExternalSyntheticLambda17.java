package com.android.systemui.media;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
import com.android.systemui.util.ConfigurationState;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda17 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda17(Boolean bool) {
        this.$r8$classId = 1;
        this.f$0 = bool;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) throws Resources.NotFoundException {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                Configuration configuration = (Configuration) obj2;
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                secMediaControlPanel.updateWidth();
                ConfigurationState configurationState = secMediaControlPanel.mLastConfigurationState;
                if (configurationState.needToUpdate(configuration)) {
                    configurationState.update(configuration);
                    break;
                }
                break;
            case 1:
                ((SecMediaHost.MediaPanelVisibilityListener) obj).onMediaVisibilityChanged(((Boolean) obj2).booleanValue());
                break;
            default:
                ((SecMediaControlPanel) obj).mCoverQSClickConsumer = (SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15) obj2;
                break;
        }
    }

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda17(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
