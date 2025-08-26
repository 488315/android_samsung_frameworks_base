package com.android.systemui.mediaprojection;

import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaProjectionServiceHelper {
    public static final Companion Companion = new Companion(null);
    public static final IMediaProjectionManager service = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

        private Companion() {
        }
    }
}
