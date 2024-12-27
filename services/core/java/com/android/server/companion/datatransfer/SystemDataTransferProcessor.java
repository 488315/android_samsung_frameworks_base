package com.android.server.companion.datatransfer;

import android.R;
import android.companion.IOnMessageReceivedListener;
import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.permission.PermissionControllerManager;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.companion.CompanionDeviceManagerService;
import com.android.server.companion.association.AssociationStore;
import com.android.server.companion.transport.CompanionTransportManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemDataTransferProcessor {
    public final AssociationStore mAssociationStore;
    public final ComponentName mCompanionDeviceDataTransferActivity;
    public final Context mContext;
    public final ExecutorService mExecutor;
    public final AnonymousClass2 mOnSystemDataTransferRequestConfirmationReceiver = new ResultReceiver(Handler.getMain()) { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor.2
        @Override // android.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            Slog.d("CDM_SystemDataTransferProcessor", "onReceiveResult() code=" + i + ", data=" + bundle);
            if (i != 0 && i != 1) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "Unknown result code:", "CDM_SystemDataTransferProcessor");
                return;
            }
            SystemDataTransferRequest systemDataTransferRequest = (PermissionSyncRequest) bundle.getParcelable("permission_sync_request", PermissionSyncRequest.class);
            if (systemDataTransferRequest != null) {
                systemDataTransferRequest.setUserConsented(i == 0);
                Slog.i("CDM_SystemDataTransferProcessor", "Recording request: " + systemDataTransferRequest);
                SystemDataTransferProcessor.this.mSystemDataTransferRequestStore.writeRequest(systemDataTransferRequest.getUserId(), systemDataTransferRequest);
            }
        }
    };
    public final PackageManagerInternal mPackageManager;
    public final PermissionControllerManager mPermissionControllerManager;
    public final SystemDataTransferRequestStore mSystemDataTransferRequestStore;
    public final CompanionTransportManager mTransportManager;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.companion.datatransfer.SystemDataTransferProcessor$2] */
    public SystemDataTransferProcessor(CompanionDeviceManagerService companionDeviceManagerService, PackageManagerInternal packageManagerInternal, AssociationStore associationStore, SystemDataTransferRequestStore systemDataTransferRequestStore, CompanionTransportManager companionTransportManager) {
        Context context = companionDeviceManagerService.getContext();
        this.mContext = context;
        this.mPackageManager = packageManagerInternal;
        this.mAssociationStore = associationStore;
        this.mSystemDataTransferRequestStore = systemDataTransferRequestStore;
        this.mTransportManager = companionTransportManager;
        companionTransportManager.addListener(1669491075, new IOnMessageReceivedListener() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor.1
            public final IBinder asBinder() {
                return null;
            }

            public final void onMessageReceived(int i, final byte[] bArr) {
                final SystemDataTransferProcessor systemDataTransferProcessor = SystemDataTransferProcessor.this;
                systemDataTransferProcessor.getClass();
                if (!Build.isDebuggable() && !systemDataTransferProcessor.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
                    Slog.e("CDM_SystemDataTransferProcessor", "Permissions restore is only available on watch.");
                    return;
                }
                Slog.i("CDM_SystemDataTransferProcessor", "Applying permissions.");
                final UserHandle user = systemDataTransferProcessor.mContext.getUser();
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferProcessor$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        SystemDataTransferProcessor systemDataTransferProcessor2 = SystemDataTransferProcessor.this;
                        systemDataTransferProcessor2.mPermissionControllerManager.stageAndApplyRuntimePermissionsBackup(bArr, user);
                    }
                });
            }
        });
        this.mPermissionControllerManager = (PermissionControllerManager) context.getSystemService(PermissionControllerManager.class);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mCompanionDeviceDataTransferActivity = ComponentName.createRelative(context.getString(R.string.csd_momentary_exposure_warning), ".CompanionDeviceDataTransferActivity");
    }

    public final PermissionSyncRequest getPermissionSyncRequest(int i) {
        Iterator it = ((ArrayList) this.mSystemDataTransferRequestStore.readRequestsByAssociationId(this.mAssociationStore.getAssociationWithCallerChecks(i).getUserId(), i)).iterator();
        while (it.hasNext()) {
            PermissionSyncRequest permissionSyncRequest = (SystemDataTransferRequest) it.next();
            if (permissionSyncRequest instanceof PermissionSyncRequest) {
                return permissionSyncRequest;
            }
        }
        return null;
    }
}
