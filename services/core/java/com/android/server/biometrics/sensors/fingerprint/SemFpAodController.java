package com.android.server.biometrics.sensors.fingerprint;

import android.os.Handler;

import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;

import java.util.ArrayList;

public final class SemFpAodController {
    ISemFingerprintAodController mAodController;
    final Handler mH;
    public boolean mIsDozeMode;
    public boolean mIsHlpmMode;
    final ArrayList mPendingRequestBeforeListener = new ArrayList();

    public SemFpAodController(Handler handler) {
        this.mH = handler;
    }
}
