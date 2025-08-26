package com.android.systemui.media.dialog;

import com.android.systemui.monet.ColorScheme;
import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;

/* loaded from: classes2.dex */
public final class MediaOutputColorSchemeDynamic extends MediaOutputColorScheme {
    public final DynamicScheme mMaterialScheme;

    public MediaOutputColorSchemeDynamic(ColorScheme colorScheme) {
        this.mMaterialScheme = colorScheme.mMaterialScheme;
    }
}
