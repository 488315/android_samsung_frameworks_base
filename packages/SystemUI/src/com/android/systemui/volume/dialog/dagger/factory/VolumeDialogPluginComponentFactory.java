package com.android.systemui.volume.dialog.dagger.factory;

import com.android.systemui.volume.dialog.dagger.VolumeDialogPluginComponent;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public interface VolumeDialogPluginComponentFactory {
    VolumeDialogPluginComponent create(CoroutineScope coroutineScope);
}
