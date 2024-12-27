package com.android.server.input;

import android.hardware.input.IStickyModifierStateListener;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StickyModifierStateController {
    public static final boolean DEBUG = Log.isLoggable("ModifierStateController", 3);
    public final SparseArray mStickyModifierStateListenerRecords = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StickyModifierStateListenerRecord implements IBinder.DeathRecipient {
        public final IStickyModifierStateListener mListener;
        public final int mPid;

        public StickyModifierStateListenerRecord(
                int i, IStickyModifierStateListener iStickyModifierStateListener) {
            this.mPid = i;
            this.mListener = iStickyModifierStateListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (StickyModifierStateController.DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Sticky modifier state listener for pid "),
                        this.mPid,
                        " died.",
                        "ModifierStateController");
            }
            StickyModifierStateController stickyModifierStateController =
                    StickyModifierStateController.this;
            int i = this.mPid;
            synchronized (stickyModifierStateController.mStickyModifierStateListenerRecords) {
                stickyModifierStateController.mStickyModifierStateListenerRecords.remove(i);
            }
        }
    }
}
