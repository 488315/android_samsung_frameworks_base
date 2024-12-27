package com.android.systemui.media.mediaoutput;

import android.util.Log;
import android.view.View;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda0;
import com.android.systemui.media.MediaOutputView;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaOutputController {
    public PluginFaceWidgetManager$$ExternalSyntheticLambda0 listener;
    public final Provider mediaOutputViewProvider;

    public MediaOutputController(Provider provider) {
        this.mediaOutputViewProvider = provider;
    }

    public final View createView(String str) {
        Object obj = this.mediaOutputViewProvider.get();
        MediaOutputView mediaOutputView = (MediaOutputView) obj;
        int identifier = mediaOutputView.getContext().getResources().getIdentifier("dialog_with_icon_title", "id", mediaOutputView.getContext().getPackageName());
        Feature.Builder builder = new Feature.Builder();
        builder.getFeature().packageName = str;
        builder.getFeature().from = 10;
        builder.getFeature().dismissCallback = new Function0() { // from class: com.android.systemui.media.mediaoutput.MediaOutputController$createView$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = MediaOutputController.this.listener;
                if (pluginFaceWidgetManager$$ExternalSyntheticLambda0 != null) {
                    StringBuilder sb = new StringBuilder("destroyFullNowBar mFaceWidgetPlugin");
                    PluginFaceWidgetManager pluginFaceWidgetManager = pluginFaceWidgetManager$$ExternalSyntheticLambda0.f$0;
                    sb.append(pluginFaceWidgetManager.mFaceWidgetPlugin);
                    Log.i("PluginFaceWidgetManager", sb.toString());
                    if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                        Log.i("PluginFaceWidgetManager", "destroyFullNowBar");
                        pluginFaceWidgetManager.mFaceWidgetPlugin.destroyFullNowBar();
                        pluginFaceWidgetManager.mNPVController.mMediaOutputDetailShowing = false;
                    }
                }
                return Unit.INSTANCE;
            }
        };
        builder.getFeature().anchorViewId = identifier;
        mediaOutputView.feature = builder.getFeature();
        return (View) obj;
    }
}
