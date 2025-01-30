package com.android.server.wm;

import com.android.server.wm.DexSizeCompatController;

/* loaded from: classes3.dex */
public interface SizeCompatPolicyCasting {
    DexSizeCompatController.DexSizeCompatPolicy asDexSizeCompatPolicy();

    SizeCompatDragPolicy asSizeCompatDragPolicy();

    int getMode();
}
