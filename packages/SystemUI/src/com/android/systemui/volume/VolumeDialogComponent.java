package com.android.systemui.volume;

import android.content.Context;
import android.content.Intent;
import android.media.VolumePolicy;
import android.os.Bundle;
import android.util.Log;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Prefs;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class VolumeDialogComponent implements VolumeComponent, TunerService.Tunable {
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    public final VolumeDialogControllerImpl mController;
    public VolumeDialog mDialog;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final SamsungVolumeDialogImpl mSamsungVolumeDialog;
    public static final Intent ZEN_SETTINGS = new Intent("android.settings.ZEN_MODE_SETTINGS");
    public static final Intent ZEN_PRIORITY_SETTINGS = new Intent("android.settings.ZEN_MODE_PRIORITY_SETTINGS");
    public final InterestingConfigChanges mConfigChanges = new InterestingConfigChanges(-1073741308);
    public final AnonymousClass1 mVolumeDialogCallback = new VolumeDialog.Callback() { // from class: com.android.systemui.volume.VolumeDialogComponent.1
        @Override // com.android.systemui.plugins.VolumeDialog.Callback
        public final void onZenPrioritySettingsClicked() {
            VolumeDialogComponent.this.mActivityStarter.startActivity(VolumeDialogComponent.ZEN_PRIORITY_SETTINGS, true, true);
        }

        @Override // com.android.systemui.plugins.VolumeDialog.Callback
        public final void onZenSettingsClicked() {
            VolumeDialogComponent.this.mActivityStarter.startActivity(VolumeDialogComponent.ZEN_SETTINGS, true, true);
        }
    };

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.volume.VolumeDialogComponent$1] */
    public VolumeDialogComponent(Context context, KeyguardViewMediator keyguardViewMediator, ActivityStarter activityStarter, VolumeDialogControllerImpl volumeDialogControllerImpl, DemoModeController demoModeController, PluginDependencyProvider pluginDependencyProvider, ExtensionController extensionController, TunerService tunerService, final VolumeDialog volumeDialog, SamsungVolumeDialogImpl samsungVolumeDialogImpl) {
        this.mContext = context;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mActivityStarter = activityStarter;
        this.mController = volumeDialogControllerImpl;
        synchronized (volumeDialogControllerImpl) {
            volumeDialogControllerImpl.mUserActivityListener = this;
        }
        this.mSamsungVolumeDialog = samsungVolumeDialogImpl;
        pluginDependencyProvider.allowPluginDependency(VolumeDialogController.class);
        ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) extensionController;
        extensionControllerImpl.getClass();
        ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
        extensionBuilder.withPlugin(VolumeDialog.class);
        Supplier supplier = new Supplier() { // from class: com.android.systemui.volume.VolumeDialogComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return VolumeDialogComponent.this.mSamsungVolumeDialog;
            }
        };
        ExtensionControllerImpl.ExtensionImpl extensionImpl = extensionBuilder.mExtension;
        extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.Default(extensionImpl, supplier));
        extensionBuilder.mExtension.mCallbacks.add(new Consumer() { // from class: com.android.systemui.volume.VolumeDialogComponent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                VolumeDialogComponent volumeDialogComponent = VolumeDialogComponent.this;
                VolumeDialog volumeDialog2 = (VolumeDialog) obj;
                VolumeDialog volumeDialog3 = volumeDialogComponent.mDialog;
                if (volumeDialog3 != null) {
                    volumeDialog3.destroy();
                }
                volumeDialogComponent.mDialog = volumeDialog2;
                volumeDialog2.init(2020, volumeDialogComponent.mVolumeDialogCallback);
            }
        });
        extensionBuilder.build();
        new VolumePolicy(context.getResources().getBoolean(17892018), context.getResources().getBoolean(17892019), false, 400);
        if (D.BUG) {
            Log.d(VolumeDialogControllerImpl.TAG, "showDndTile");
        }
        Context context2 = volumeDialogControllerImpl.mContext;
        Intent intent = DndTile.DND_SETTINGS;
        Prefs.putBoolean(context2, "DndTileVisible", true);
        demoModeController.addCallback((DemoMode) this);
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("volume");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
    }
}
