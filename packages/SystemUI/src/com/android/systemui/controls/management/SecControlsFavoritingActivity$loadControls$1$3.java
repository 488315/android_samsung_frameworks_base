package com.android.systemui.controls.management;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.service.controls.ControlsProviderInfo;
import com.android.systemui.controls.util.ControlsUtil;
import java.util.function.Consumer;

public final class SecControlsFavoritingActivity$loadControls$1$3 implements Consumer {
    public final /* synthetic */ SecControlsFavoritingActivity this$0;

    public SecControlsFavoritingActivity$loadControls$1$3(SecControlsFavoritingActivity secControlsFavoritingActivity) {
        this.this$0 = secControlsFavoritingActivity;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ControlsProviderInfo controlsProviderInfo = (ControlsProviderInfo) obj;
        this.this$0.isAutoRemove = controlsProviderInfo.getAutoRemove();
        ControlsUtil.Companion.getClass();
        boolean autoRemove = controlsProviderInfo.getAutoRemove();
        StringBuilder sb = new StringBuilder("autoRemove:");
        sb.append(autoRemove);
        sb.append("|");
        Intent intent = controlsProviderInfo.getAppIntent().getIntent();
        StringBuilder sb2 = new StringBuilder("intent:");
        sb2.append(intent);
        sb2.append("|");
        Icon icon = controlsProviderInfo.getIcon();
        String resPackage = icon != null ? icon.getResPackage() : null;
        StringBuilder sb3 = new StringBuilder("icon:");
        sb3.append(resPackage);
        sb3.append("|");
    }
}
