package com.android.systemui.mediaprojection;

import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaProjectionServiceHelper {
    public static final Companion Companion = new Companion(null);
    public static final IMediaProjectionManager service = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));

    public final class Companion {
        private Companion() {
        }

        public static void setReviewedConsentIfNeeded(int i, boolean z, IMediaProjection iMediaProjection) {
            if (!z || i == -1) {
                return;
            }
            try {
                MediaProjectionServiceHelper.service.setUserReviewGrantedConsentResult(i, iMediaProjection);
            } catch (RemoteException e) {
                Log.e("MediaProjectionServiceHelper", "Unable to set required consent result for token re-use", e);
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public static final IMediaProjection createOrReuseProjection(int i, String str, boolean z) {
        Companion.getClass();
        IMediaProjection projection = z ? service.getProjection(i, str) : null;
        return projection == null ? service.createProjection(i, str, 0, false) : projection;
    }
}
