package com.android.systemui.mediaprojection;

import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaProjectionServiceHelper {
    public static final Companion Companion = new Companion(null);
    public static final IMediaProjectionManager service = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
