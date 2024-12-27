package com.android.server.slice;

import android.app.slice.SliceSpec;
import android.content.ContentProviderClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public final class PinnedSliceState {
    public final Object mLock;
    public final String mPkg;
    public final SliceManagerService mService;
    public boolean mSlicePinned;
    public final Uri mUri;
    public final ArraySet mPinnedPkgs = new ArraySet();
    public final ArrayMap mListeners = new ArrayMap();
    public SliceSpec[] mSupportedSpecs = null;
    public final PinnedSliceState$$ExternalSyntheticLambda0 mDeathRecipient =
            new IBinder
                    .DeathRecipient() { // from class:
                                        // com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    PinnedSliceState pinnedSliceState = PinnedSliceState.this;
                    if (pinnedSliceState.hasPinOrListener()) {
                        synchronized (pinnedSliceState.mLock) {
                            try {
                                for (int size = pinnedSliceState.mListeners.size() - 1;
                                        size >= 0;
                                        size--) {
                                    if (!((PinnedSliceState.ListenerInfo)
                                                    pinnedSliceState.mListeners.valueAt(size))
                                            .token.isBinderAlive()) {
                                        pinnedSliceState.mListeners.removeAt(size);
                                    }
                                }
                                if (!pinnedSliceState.hasPinOrListener()) {
                                    pinnedSliceState.mService.removePinnedSlice(
                                            pinnedSliceState.mUri);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
            };

    public final class ListenerInfo {
        public IBinder token;
    }

    public PinnedSliceState(SliceManagerService sliceManagerService, Uri uri, String str) {
        this.mService = sliceManagerService;
        this.mUri = uri;
        this.mPkg = str;
        this.mLock = sliceManagerService.mLock;
    }

    public boolean hasPinOrListener() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = (this.mPinnedPkgs.isEmpty() && this.mListeners.isEmpty()) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public final void mergeSpecs(final SliceSpec[] sliceSpecArr) {
        synchronized (this.mLock) {
            try {
                SliceSpec[] sliceSpecArr2 = this.mSupportedSpecs;
                if (sliceSpecArr2 == null) {
                    this.mSupportedSpecs = sliceSpecArr;
                } else {
                    this.mSupportedSpecs =
                            (SliceSpec[])
                                    Arrays.asList(sliceSpecArr2).stream()
                                            .map(
                                                    new Function() { // from class:
                                                                     // com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda3
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            SliceSpec sliceSpec;
                                                            PinnedSliceState pinnedSliceState =
                                                                    PinnedSliceState.this;
                                                            SliceSpec[] sliceSpecArr3 =
                                                                    sliceSpecArr;
                                                            SliceSpec sliceSpec2 = (SliceSpec) obj;
                                                            pinnedSliceState.getClass();
                                                            String type = sliceSpec2.getType();
                                                            int length = sliceSpecArr3.length;
                                                            int i = 0;
                                                            while (true) {
                                                                if (i >= length) {
                                                                    sliceSpec = null;
                                                                    break;
                                                                }
                                                                sliceSpec = sliceSpecArr3[i];
                                                                if (Objects.equals(
                                                                        sliceSpec.getType(),
                                                                        type)) {
                                                                    break;
                                                                }
                                                                i++;
                                                            }
                                                            if (sliceSpec == null) {
                                                                return null;
                                                            }
                                                            return sliceSpec.getRevision()
                                                                            < sliceSpec2
                                                                                    .getRevision()
                                                                    ? sliceSpec
                                                                    : sliceSpec2;
                                                        }
                                                    })
                                            .filter(
                                                    new PinnedSliceState$$ExternalSyntheticLambda4())
                                            .toArray(
                                                    new PinnedSliceState$$ExternalSyntheticLambda5());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSlicePinned(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSlicePinned == z) {
                    return;
                }
                this.mSlicePinned = z;
                if (z) {
                    final int i = 0;
                    this.mService.mHandler.post(
                            new Runnable(
                                    this) { // from class:
                                            // com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda1
                                public final /* synthetic */ PinnedSliceState f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2 = i;
                                    PinnedSliceState pinnedSliceState = this.f$0;
                                    switch (i2) {
                                        case 0:
                                            ContentProviderClient
                                                    acquireUnstableContentProviderClient =
                                                            pinnedSliceState
                                                                    .mService
                                                                    .mContext
                                                                    .getContentResolver()
                                                                    .acquireUnstableContentProviderClient(
                                                                            pinnedSliceState.mUri);
                                            if (acquireUnstableContentProviderClient == null) {
                                                acquireUnstableContentProviderClient = null;
                                            } else {
                                                acquireUnstableContentProviderClient
                                                        .setDetectNotResponding(5000L);
                                            }
                                            if (acquireUnstableContentProviderClient != null) {
                                                try {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putParcelable(
                                                            "slice_uri", pinnedSliceState.mUri);
                                                    try {
                                                        acquireUnstableContentProviderClient.call(
                                                                "pin", null, bundle);
                                                    } catch (Exception e) {
                                                        Log.w(
                                                                "PinnedSliceState",
                                                                "Unable to contact "
                                                                        + pinnedSliceState.mUri,
                                                                e);
                                                    }
                                                } catch (Throwable th) {
                                                    try {
                                                        acquireUnstableContentProviderClient
                                                                .close();
                                                    } catch (Throwable th2) {
                                                        th.addSuppressed(th2);
                                                    }
                                                    throw th;
                                                }
                                            } else if (acquireUnstableContentProviderClient
                                                    == null) {
                                                return;
                                            }
                                            acquireUnstableContentProviderClient.close();
                                            return;
                                        default:
                                            ContentProviderClient
                                                    acquireUnstableContentProviderClient2 =
                                                            pinnedSliceState
                                                                    .mService
                                                                    .mContext
                                                                    .getContentResolver()
                                                                    .acquireUnstableContentProviderClient(
                                                                            pinnedSliceState.mUri);
                                            if (acquireUnstableContentProviderClient2 == null) {
                                                acquireUnstableContentProviderClient2 = null;
                                            } else {
                                                acquireUnstableContentProviderClient2
                                                        .setDetectNotResponding(5000L);
                                            }
                                            if (acquireUnstableContentProviderClient2 != null) {
                                                try {
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putParcelable(
                                                            "slice_uri", pinnedSliceState.mUri);
                                                    try {
                                                        acquireUnstableContentProviderClient2.call(
                                                                "unpin", null, bundle2);
                                                    } catch (Exception e2) {
                                                        Log.w(
                                                                "PinnedSliceState",
                                                                "Unable to contact "
                                                                        + pinnedSliceState.mUri,
                                                                e2);
                                                    }
                                                } catch (Throwable th3) {
                                                    try {
                                                        acquireUnstableContentProviderClient2
                                                                .close();
                                                    } catch (Throwable th4) {
                                                        th3.addSuppressed(th4);
                                                    }
                                                    throw th3;
                                                }
                                            } else if (acquireUnstableContentProviderClient2
                                                    == null) {
                                                return;
                                            }
                                            acquireUnstableContentProviderClient2.close();
                                            return;
                                    }
                                }
                            });
                } else {
                    final int i2 = 1;
                    this.mService.mHandler.post(
                            new Runnable(
                                    this) { // from class:
                                            // com.android.server.slice.PinnedSliceState$$ExternalSyntheticLambda1
                                public final /* synthetic */ PinnedSliceState f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i22 = i2;
                                    PinnedSliceState pinnedSliceState = this.f$0;
                                    switch (i22) {
                                        case 0:
                                            ContentProviderClient
                                                    acquireUnstableContentProviderClient =
                                                            pinnedSliceState
                                                                    .mService
                                                                    .mContext
                                                                    .getContentResolver()
                                                                    .acquireUnstableContentProviderClient(
                                                                            pinnedSliceState.mUri);
                                            if (acquireUnstableContentProviderClient == null) {
                                                acquireUnstableContentProviderClient = null;
                                            } else {
                                                acquireUnstableContentProviderClient
                                                        .setDetectNotResponding(5000L);
                                            }
                                            if (acquireUnstableContentProviderClient != null) {
                                                try {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putParcelable(
                                                            "slice_uri", pinnedSliceState.mUri);
                                                    try {
                                                        acquireUnstableContentProviderClient.call(
                                                                "pin", null, bundle);
                                                    } catch (Exception e) {
                                                        Log.w(
                                                                "PinnedSliceState",
                                                                "Unable to contact "
                                                                        + pinnedSliceState.mUri,
                                                                e);
                                                    }
                                                } catch (Throwable th) {
                                                    try {
                                                        acquireUnstableContentProviderClient
                                                                .close();
                                                    } catch (Throwable th2) {
                                                        th.addSuppressed(th2);
                                                    }
                                                    throw th;
                                                }
                                            } else if (acquireUnstableContentProviderClient
                                                    == null) {
                                                return;
                                            }
                                            acquireUnstableContentProviderClient.close();
                                            return;
                                        default:
                                            ContentProviderClient
                                                    acquireUnstableContentProviderClient2 =
                                                            pinnedSliceState
                                                                    .mService
                                                                    .mContext
                                                                    .getContentResolver()
                                                                    .acquireUnstableContentProviderClient(
                                                                            pinnedSliceState.mUri);
                                            if (acquireUnstableContentProviderClient2 == null) {
                                                acquireUnstableContentProviderClient2 = null;
                                            } else {
                                                acquireUnstableContentProviderClient2
                                                        .setDetectNotResponding(5000L);
                                            }
                                            if (acquireUnstableContentProviderClient2 != null) {
                                                try {
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putParcelable(
                                                            "slice_uri", pinnedSliceState.mUri);
                                                    try {
                                                        acquireUnstableContentProviderClient2.call(
                                                                "unpin", null, bundle2);
                                                    } catch (Exception e2) {
                                                        Log.w(
                                                                "PinnedSliceState",
                                                                "Unable to contact "
                                                                        + pinnedSliceState.mUri,
                                                                e2);
                                                    }
                                                } catch (Throwable th3) {
                                                    try {
                                                        acquireUnstableContentProviderClient2
                                                                .close();
                                                    } catch (Throwable th4) {
                                                        th3.addSuppressed(th4);
                                                    }
                                                    throw th3;
                                                }
                                            } else if (acquireUnstableContentProviderClient2
                                                    == null) {
                                                return;
                                            }
                                            acquireUnstableContentProviderClient2.close();
                                            return;
                                    }
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
