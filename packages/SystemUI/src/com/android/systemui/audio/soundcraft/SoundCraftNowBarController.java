package com.android.systemui.audio.soundcraft;

import android.util.Log;
import android.view.View;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda0;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftNowBarController {
    public PluginFaceWidgetManager$$ExternalSyntheticLambda0 listener;
    public final Provider soundCraftNowBarViewProvider;

    public SoundCraftNowBarController(Provider provider) {
        this.soundCraftNowBarViewProvider = provider;
    }

    public final View createView() {
        Object obj = this.soundCraftNowBarViewProvider.get();
        new Function0() { // from class: com.android.systemui.audio.soundcraft.SoundCraftNowBarController$createView$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftNowBarController.this.dismiss();
                return Unit.INSTANCE;
            }
        };
        ((SoundCraftNowBarView) obj).getClass();
        return (View) obj;
    }

    public final void dismiss() {
        PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = this.listener;
        if (pluginFaceWidgetManager$$ExternalSyntheticLambda0 != null) {
            StringBuilder sb = new StringBuilder("showBudsInfo mFaceWidgetPlugin");
            PluginFaceWidgetManager pluginFaceWidgetManager = pluginFaceWidgetManager$$ExternalSyntheticLambda0.f$0;
            sb.append(pluginFaceWidgetManager.mFaceWidgetPlugin);
            Log.i("PluginFaceWidgetManager", sb.toString());
            if (pluginFaceWidgetManager.mFaceWidgetPlugin != null) {
                Log.i("PluginFaceWidgetManager", "destroyFullNowBar");
                pluginFaceWidgetManager.mFaceWidgetPlugin.destroyFullNowBar();
            }
        }
    }
}
