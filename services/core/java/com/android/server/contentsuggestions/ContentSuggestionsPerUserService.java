package com.android.server.contentsuggestions;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.infra.AbstractRemoteService;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentSuggestionsPerUserService extends AbstractPerUserSystemService {
    public RemoteContentSuggestionsService mRemoteService;

    public final RemoteContentSuggestionsService ensureRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            String componentNameLocked = getComponentNameLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (componentNameLocked == null) {
                if (!((ContentSuggestionsManagerService) abstractMasterSystemService).verbose) {
                    return null;
                }
                Slog.v("ContentSuggestionsPerUserService", "ensureRemoteServiceLocked(): not set");
                return null;
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(componentNameLocked);
            Context context = abstractMasterSystemService.getContext();
            AbstractRemoteService.VultureCallback vultureCallback =
                    new AbstractRemoteService
                            .VultureCallback() { // from class:
                                                 // com.android.server.contentsuggestions.ContentSuggestionsPerUserService.1
                        public final void onServiceDied(Object obj) {
                            Slog.w(
                                    "ContentSuggestionsPerUserService",
                                    "remote content suggestions service died");
                            ContentSuggestionsPerUserService contentSuggestionsPerUserService =
                                    ContentSuggestionsPerUserService.this;
                            RemoteContentSuggestionsService remoteContentSuggestionsService =
                                    contentSuggestionsPerUserService.mRemoteService;
                            if (remoteContentSuggestionsService != null) {
                                remoteContentSuggestionsService.destroy();
                                contentSuggestionsPerUserService.mRemoteService = null;
                            }
                        }
                    };
            boolean isBindInstantServiceAllowed =
                    ((ContentSuggestionsManagerService) abstractMasterSystemService)
                            .isBindInstantServiceAllowed();
            this.mRemoteService =
                    new RemoteContentSuggestionsService(
                            context,
                            "android.service.contentsuggestions.ContentSuggestionsService",
                            unflattenFromString,
                            this.mUserId,
                            vultureCallback,
                            context.getMainThreadHandler(),
                            isBindInstantServiceAllowed ? 4194304 : 0,
                            ((ContentSuggestionsManagerService) abstractMasterSystemService)
                                    .verbose,
                            1);
        }
        return this.mRemoteService;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            ServiceInfo serviceInfo =
                    AppGlobals.getPackageManager()
                            .getServiceInfo(componentName, 128L, this.mUserId);
            if ("android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE"
                    .equals(serviceInfo.permission)) {
                return serviceInfo;
            }
            Slog.w(
                    "ContentSuggestionsPerUserService",
                    "ContentSuggestionsService from '"
                            + serviceInfo.packageName
                            + "' does not require permission"
                            + " android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE");
            throw new SecurityException(
                    "Service does not require permission"
                        + " android.permission.BIND_CONTENT_SUGGESTIONS_SERVICE");
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        RemoteContentSuggestionsService remoteContentSuggestionsService = this.mRemoteService;
        if (remoteContentSuggestionsService != null) {
            remoteContentSuggestionsService.destroy();
            this.mRemoteService = null;
        }
        return updateLocked;
    }
}
