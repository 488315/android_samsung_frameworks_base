package com.android.server.uri;

import android.os.Binder;
import android.util.ArraySet;
import com.android.server.uri.UriGrantsManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UriPermissionOwner {
    public ExternalToken externalToken;
    public final Object mOwner;
    public ArraySet mReadPerms;
    public final UriGrantsManagerInternal mService;
    public ArraySet mWritePerms;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExternalToken extends Binder {
        public ExternalToken() {
        }
    }

    public UriPermissionOwner(UriGrantsManagerInternal uriGrantsManagerInternal, Object obj) {
        this.mService = uriGrantsManagerInternal;
        this.mOwner = obj;
    }

    public final void dump(PrintWriter printWriter, String str) {
        synchronized (this) {
            try {
                if (this.mReadPerms != null) {
                    printWriter.print(str);
                    printWriter.print("readUriPermissions=");
                    printWriter.println(this.mReadPerms);
                }
                if (this.mWritePerms != null) {
                    printWriter.print(str);
                    printWriter.print("writeUriPermissions=");
                    printWriter.println(this.mWritePerms);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeUriPermission(int i, int i2, GrantUri grantUri, String str) {
        ArraySet arraySet;
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            if ((i & 1) != 0) {
                try {
                    ArraySet arraySet2 = this.mReadPerms;
                    if (arraySet2 != null) {
                        Iterator it = arraySet2.iterator();
                        while (it.hasNext()) {
                            UriPermission uriPermission = (UriPermission) it.next();
                            if (grantUri == null || grantUri.equals(uriPermission.uri)) {
                                if (str == null || str.equals(uriPermission.targetPkg)) {
                                    if (i2 == -1 || i2 == uriPermission.targetUserId) {
                                        arrayList.add(uriPermission);
                                        uriPermission.removeReadOwner(this);
                                        it.remove();
                                    }
                                }
                            }
                        }
                        if (this.mReadPerms.isEmpty()) {
                            this.mReadPerms = null;
                        }
                    }
                } finally {
                }
            }
            if ((i & 2) != 0 && (arraySet = this.mWritePerms) != null) {
                Iterator it2 = arraySet.iterator();
                while (it2.hasNext()) {
                    UriPermission uriPermission2 = (UriPermission) it2.next();
                    if (grantUri == null || grantUri.equals(uriPermission2.uri)) {
                        if (str == null || str.equals(uriPermission2.targetPkg)) {
                            if (i2 == -1 || i2 == uriPermission2.targetUserId) {
                                arrayList.add(uriPermission2);
                                uriPermission2.removeWriteOwner(this);
                                it2.remove();
                            }
                        }
                    }
                }
                if (this.mWritePerms.isEmpty()) {
                    this.mWritePerms = null;
                }
            }
        }
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            UriGrantsManagerInternal uriGrantsManagerInternal = this.mService;
            UriPermission uriPermission3 = (UriPermission) arrayList.get(i3);
            UriGrantsManagerService.LocalService localService = (UriGrantsManagerService.LocalService) uriGrantsManagerInternal;
            synchronized (UriGrantsManagerService.this.mLock) {
                UriGrantsManagerService.this.removeUriPermissionIfNeededLocked(uriPermission3);
            }
        }
    }

    public final String toString() {
        return this.mOwner.toString();
    }
}
