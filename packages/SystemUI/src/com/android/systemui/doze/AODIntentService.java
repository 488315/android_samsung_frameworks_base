package com.android.systemui.doze;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.plugins.aod.PluginAOD;
import dagger.Lazy;

public class AODIntentService extends IntentService {
    public Handler mHandler;
    public Lazy mPluginAODManagerLazy;

    public AODIntentService() {
        this("AODIntentService");
    }

    @Override // android.app.IntentService
    public final void onHandleIntent(final Intent intent) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.doze.AODIntentService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AODIntentService aODIntentService = AODIntentService.this;
                final Intent intent2 = intent;
                final PluginAODManager pluginAODManager = (PluginAODManager) aODIntentService.mPluginAODManagerLazy.get();
                pluginAODManager.mHandler.post(new Runnable() { // from class: com.android.systemui.doze.PluginAODManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PluginAODManager pluginAODManager2 = PluginAODManager.this;
                        Intent intent3 = intent2;
                        PluginAOD pluginAOD = pluginAODManager2.mAODPlugin;
                        if (pluginAOD != null) {
                            pluginAOD.sendIntent(intent3);
                        }
                    }
                });
            }
        });
    }

    public AODIntentService(String str) {
        super(str);
        SystemUIAppComponentFactoryBase.Companion.getClass();
        SystemUIAppComponentFactoryBase.systemUIInitializer.getSysUIComponent().inject(this);
    }
}
