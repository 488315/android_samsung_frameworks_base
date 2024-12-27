package com.android.server.inputmethod;

import android.app.UriGrantsManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.internal.inputmethod.IInputContentUriToken;
import com.android.server.LocalServices;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InputContentUriTokenHandler extends IInputContentUriToken.Stub {
    public final Object mLock = new Object();
    public IBinder mPermissionOwnerToken = null;
    public final int mSourceUid;
    public final int mSourceUserId;
    public final String mTargetPackage;
    public final int mTargetUserId;
    public final Uri mUri;

    public InputContentUriTokenHandler(int i, String str, Uri uri, int i2, int i3) {
        this.mUri = uri;
        this.mSourceUid = i;
        this.mTargetPackage = str;
        this.mSourceUserId = i2;
        this.mTargetUserId = i3;
    }

    public final void finalize() {
        try {
            release();
        } finally {
            super /*java.lang.Object*/.finalize();
        }
    }

    public final void release() {
        synchronized (this.mLock) {
            if (this.mPermissionOwnerToken == null) {
                return;
            }
            try {
                ((UriGrantsManagerService.LocalService)
                                ((UriGrantsManagerInternal)
                                        LocalServices.getService(UriGrantsManagerInternal.class)))
                        .revokeUriPermissionFromOwner(
                                this.mPermissionOwnerToken, this.mUri, 1, this.mSourceUserId);
            } finally {
                this.mPermissionOwnerToken = null;
            }
        }
    }

    public final void take() {
        synchronized (this.mLock) {
            try {
                if (this.mPermissionOwnerToken != null) {
                    return;
                }
                IBinder newUriPermissionOwner =
                        ((UriGrantsManagerService.LocalService)
                                        ((UriGrantsManagerInternal)
                                                LocalServices.getService(
                                                        UriGrantsManagerInternal.class)))
                                .newUriPermissionOwner("InputContentUriTokenHandler");
                this.mPermissionOwnerToken = newUriPermissionOwner;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        UriGrantsManager.getService()
                                .grantUriPermissionFromOwner(
                                        newUriPermissionOwner,
                                        this.mSourceUid,
                                        this.mTargetPackage,
                                        this.mUri,
                                        1,
                                        this.mSourceUserId,
                                        this.mTargetUserId);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
