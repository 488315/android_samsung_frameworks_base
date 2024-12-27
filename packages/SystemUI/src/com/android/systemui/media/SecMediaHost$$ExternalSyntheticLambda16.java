package com.android.systemui.media;

import android.content.res.Configuration;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.util.ConfigurationState;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda16 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
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
            default:
                ((SecMediaHost.MediaPanelVisibilityListener) obj).onMediaVisibilityChanged(((Boolean) obj2).booleanValue());
                break;
        }
    }

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda16(Boolean bool) {
        this.f$0 = bool;
    }
}
