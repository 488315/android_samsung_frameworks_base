package androidx.mediarouter.media;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteDiscoveryRequest {
    public final Bundle mBundle;
    public MediaRouteSelector mSelector;

    public MediaRouteDiscoveryRequest(MediaRouteSelector mediaRouteSelector, boolean z) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        Bundle bundle = new Bundle();
        this.mBundle = bundle;
        this.mSelector = mediaRouteSelector;
        bundle.putBundle("selector", mediaRouteSelector.mBundle);
        bundle.putBoolean("activeScan", z);
    }

    public final void ensureSelector() {
        if (this.mSelector == null) {
            Bundle bundle = this.mBundle.getBundle("selector");
            MediaRouteSelector mediaRouteSelector = null;
            if (bundle != null) {
                mediaRouteSelector = new MediaRouteSelector(bundle, null);
            } else {
                MediaRouteSelector mediaRouteSelector2 = MediaRouteSelector.EMPTY;
            }
            this.mSelector = mediaRouteSelector;
            if (mediaRouteSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof MediaRouteDiscoveryRequest)) {
            return false;
        }
        MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest = (MediaRouteDiscoveryRequest) obj;
        ensureSelector();
        MediaRouteSelector mediaRouteSelector = this.mSelector;
        mediaRouteDiscoveryRequest.ensureSelector();
        return mediaRouteSelector.equals(mediaRouteDiscoveryRequest.mSelector) && isActiveScan() == mediaRouteDiscoveryRequest.isActiveScan();
    }

    public final int hashCode() {
        ensureSelector();
        return (isActiveScan() ? 1 : 0) ^ this.mSelector.hashCode();
    }

    public final boolean isActiveScan() {
        return this.mBundle.getBoolean("activeScan");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DiscoveryRequest{ selector=");
        ensureSelector();
        sb.append(this.mSelector);
        sb.append(", activeScan=");
        sb.append(isActiveScan());
        sb.append(", isValid=");
        ensureSelector();
        this.mSelector.ensureControlCategories();
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, !r2.mControlCategories.contains(null), " }");
    }

    private MediaRouteDiscoveryRequest(Bundle bundle) {
        this.mBundle = bundle;
    }
}
