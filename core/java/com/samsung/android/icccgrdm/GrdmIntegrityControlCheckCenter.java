package com.samsung.android.icccgrdm;

import android.util.Log;

public class GrdmIntegrityControlCheckCenter {
    IGrdmIntegrityControlCheckCenter mService;

    public GrdmIntegrityControlCheckCenter(IGrdmIntegrityControlCheckCenter service) {
        this.mService = service;
        Log.d("GrdmIntegrityControlCheckCenter", "Service assigned: " + this.mService);
    }
}
