package com.android.systemui.volume.dialog.dagger.factory;

import com.android.systemui.volume.dialog.dagger.VolumeDialogComponent;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public interface VolumeDialogComponentFactory {
    VolumeDialogComponent create(CoroutineScope coroutineScope);
}
