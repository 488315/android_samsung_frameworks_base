package com.android.server.job;

import android.app.UriGrantsManager;
import android.content.ClipData;
import android.content.ContentProvider;
import android.content.Intent;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.server.LocalServices;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GrantedUriPermissions {
    public final int mGrantFlags;
    public final IBinder mPermissionOwner;
    public final int mSourceUserId;
    public final String mTag;
    public final UriGrantsManagerInternal mUriGrantsManagerInternal;
    public final ArrayList mUris = new ArrayList();

    public GrantedUriPermissions(int i, int i2, String str) {
        this.mGrantFlags = i;
        this.mSourceUserId = UserHandle.getUserId(i2);
        this.mTag = str;
        UriGrantsManagerInternal uriGrantsManagerInternal =
                (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mUriGrantsManagerInternal = uriGrantsManagerInternal;
        this.mPermissionOwner =
                ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                        .newUriPermissionOwner(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                        "job: ", str));
    }

    public static GrantedUriPermissions grantClip(
            ClipData clipData,
            int i,
            String str,
            int i2,
            int i3,
            String str2,
            GrantedUriPermissions grantedUriPermissions) {
        int itemCount = clipData.getItemCount();
        GrantedUriPermissions grantedUriPermissions2 = grantedUriPermissions;
        for (int i4 = 0; i4 < itemCount; i4++) {
            ClipData.Item itemAt = clipData.getItemAt(i4);
            if (itemAt.getUri() != null) {
                grantedUriPermissions2 =
                        grantUri(itemAt.getUri(), i, str, i2, i3, str2, grantedUriPermissions2);
            }
            GrantedUriPermissions grantedUriPermissions3 = grantedUriPermissions2;
            Intent intent = itemAt.getIntent();
            grantedUriPermissions2 =
                    (intent == null || intent.getData() == null)
                            ? grantedUriPermissions3
                            : grantUri(
                                    intent.getData(), i, str, i2, i3, str2, grantedUriPermissions3);
        }
        return grantedUriPermissions2;
    }

    public static GrantedUriPermissions grantUri(
            Uri uri,
            int i,
            String str,
            int i2,
            int i3,
            String str2,
            GrantedUriPermissions grantedUriPermissions) {
        try {
            int userIdFromUri = ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i));
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
            if (grantedUriPermissions == null) {
                grantedUriPermissions = new GrantedUriPermissions(i3, i, str2);
            }
            UriGrantsManager.getService()
                    .grantUriPermissionFromOwner(
                            grantedUriPermissions.mPermissionOwner,
                            i,
                            str,
                            uriWithoutUserId,
                            i3,
                            userIdFromUri,
                            i2);
            grantedUriPermissions.mUris.add(uriWithoutUserId);
        } catch (RemoteException unused) {
            Slog.e("JobScheduler", "AM dead");
        }
        return grantedUriPermissions;
    }

    public final void dump(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mGrantFlags);
        protoOutputStream.write(1120986464258L, this.mSourceUserId);
        protoOutputStream.write(1138166333443L, this.mTag);
        protoOutputStream.write(1138166333444L, this.mPermissionOwner.toString());
        for (int i = 0; i < this.mUris.size(); i++) {
            Uri uri = (Uri) this.mUris.get(i);
            if (uri != null) {
                protoOutputStream.write(2237677961221L, uri.toString());
            }
        }
        protoOutputStream.end(start);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.print("mGrantFlags=0x");
        printWriter.print(Integer.toHexString(this.mGrantFlags));
        printWriter.print(" mSourceUserId=");
        printWriter.println(this.mSourceUserId);
        printWriter.print("mTag=");
        printWriter.println(this.mTag);
        printWriter.print("mPermissionOwner=");
        printWriter.println(this.mPermissionOwner);
        for (int i = 0; i < this.mUris.size(); i++) {
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(this.mUris.get(i));
        }
    }

    public final void revoke() {
        for (int size = this.mUris.size() - 1; size >= 0; size--) {
            ((UriGrantsManagerService.LocalService) this.mUriGrantsManagerInternal)
                    .revokeUriPermissionFromOwner(
                            this.mPermissionOwner,
                            (Uri) this.mUris.get(size),
                            this.mGrantFlags,
                            this.mSourceUserId);
        }
        this.mUris.clear();
    }
}
