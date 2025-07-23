package com.samsung.android.icccgrdm;

import android.util.Log;

/* loaded from: classes6.dex */
public class GrdmIntegrityControlCheckCenter {
    IGrdmIntegrityControlCheckCenter mService;

    public GrdmIntegrityControlCheckCenter(IGrdmIntegrityControlCheckCenter iGrdmIntegrityControlCheckCenter) {
        this.mService = iGrdmIntegrityControlCheckCenter;
        Log.d("GrdmIntegrityControlCheckCenter", "Service assigned: " + this.mService);
    }
}
