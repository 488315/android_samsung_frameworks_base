package com.android.systemui.media;

import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionServiceHelper {
    public static final Companion Companion = new Companion(null);
    public static final IMediaProjectionManager service = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

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
    }
}
