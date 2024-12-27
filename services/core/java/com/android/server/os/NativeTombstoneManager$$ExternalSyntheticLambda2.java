package com.android.server.os;

import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NativeTombstoneManager$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ NativeTombstoneManager f$0;
    public final /* synthetic */ Optional f$1;
    public final /* synthetic */ Optional f$2;

    public /* synthetic */ NativeTombstoneManager$$ExternalSyntheticLambda2(
            NativeTombstoneManager nativeTombstoneManager, Optional optional, Optional optional2) {
        this.f$0 = nativeTombstoneManager;
        this.f$1 = optional;
        this.f$2 = optional2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NativeTombstoneManager nativeTombstoneManager = this.f$0;
        Optional optional = this.f$1;
        Optional optional2 = this.f$2;
        synchronized (nativeTombstoneManager.mLock) {
            try {
                for (int size = nativeTombstoneManager.mTombstones.size() - 1; size >= 0; size--) {
                    NativeTombstoneManager.TombstoneFile tombstoneFile =
                            (NativeTombstoneManager.TombstoneFile)
                                    nativeTombstoneManager.mTombstones.valueAt(size);
                    if (tombstoneFile.matches(optional, optional2)) {
                        if (!tombstoneFile.mPurged) {
                            try {
                                Os.ftruncate(tombstoneFile.mPfd.getFileDescriptor(), 0L);
                            } catch (ErrnoException e) {
                                Slog.e("NativeTombstoneManager", "Failed to truncate tombstone", e);
                            }
                            tombstoneFile.mPurged = true;
                        }
                        nativeTombstoneManager.mTombstones.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
