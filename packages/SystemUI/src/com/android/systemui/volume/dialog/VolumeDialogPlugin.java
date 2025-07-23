package com.android.systemui.volume.dialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.dialog.dagger.factory.VolumeDialogPluginComponentFactory;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogPlugin implements com.android.systemui.plugins.VolumeDialog {
    public final CoroutineScope applicationCoroutineScope;
    public final AudioManager audioManager;
    public final Context context;
    public final CsdWarningDialog.Factory csdWarningDialogFactory;
    public StandaloneCoroutine job;
    public final VolumeDialogPluginComponentFactory volumeDialogPluginComponentFactory;

    public VolumeDialogPlugin(CoroutineScope coroutineScope, Context context, AudioManager audioManager, VolumeDialogPluginComponentFactory volumeDialogPluginComponentFactory, CsdWarningDialog.Factory factory) {
        this.applicationCoroutineScope = coroutineScope;
        this.context = context;
        this.audioManager = audioManager;
        this.volumeDialogPluginComponentFactory = volumeDialogPluginComponentFactory;
        this.csdWarningDialogFactory = factory;
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void destroy() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void init(int i, VolumeDialog.Callback callback) {
        this.job = CoroutineTracingKt.launchTraced$default(this.applicationCoroutineScope, null, null, new VolumeDialogPlugin$init$1(this, null), 7);
    }
}
