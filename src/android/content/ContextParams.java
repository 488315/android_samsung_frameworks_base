package android.content;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class ContextParams {
    public static final ContextParams EMPTY = new Builder().build();
    private final String mAttributionTag;
    private final AttributionSource mNext;
    private final Set<String> mRenouncedPermissions;
    private final boolean mShouldRegisterAttributionSource;

    private ContextParams(String str, AttributionSource attributionSource, Set<String> set, boolean z) {
        this.mAttributionTag = str;
        this.mNext = attributionSource;
        this.mRenouncedPermissions = set == null ? Collections.EMPTY_SET : set;
        this.mShouldRegisterAttributionSource = z;
    }

    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    @SystemApi
    public Set<String> getRenouncedPermissions() {
        return this.mRenouncedPermissions;
    }

    public boolean isRenouncedPermission(String str) {
        return this.mRenouncedPermissions.contains(str);
    }

    public AttributionSource getNextAttributionSource() {
        return this.mNext;
    }

    public boolean shouldRegisterAttributionSource() {
        return this.mShouldRegisterAttributionSource;
    }

    public static final class Builder {
        private String mAttributionTag;
        private AttributionSource mNext;
        private Set<String> mRenouncedPermissions;
        private boolean mShouldRegisterAttributionSource;

        public Builder() {
            this.mRenouncedPermissions = Collections.EMPTY_SET;
        }

        public Builder(ContextParams contextParams) {
            this.mRenouncedPermissions = Collections.EMPTY_SET;
            Objects.requireNonNull(contextParams);
            this.mAttributionTag = contextParams.mAttributionTag;
            this.mRenouncedPermissions = contextParams.mRenouncedPermissions;
            this.mNext = contextParams.mNext;
        }

        public Builder setAttributionTag(String str) {
            this.mAttributionTag = str;
            return this;
        }

        public Builder setNextAttributionSource(AttributionSource attributionSource) {
            this.mNext = attributionSource;
            return this;
        }

        public Builder setShouldRegisterAttributionSource(boolean z) {
            this.mShouldRegisterAttributionSource = z;
            return this;
        }

        @SystemApi
        public Builder setRenouncedPermissions(Set<String> set) {
            if (set != null && !set.isEmpty() && ActivityThread.currentApplication().checkSelfPermission(Manifest.permission.RENOUNCE_PERMISSIONS) != 0) {
                throw new SecurityException("Renouncing permissions requires: android.permission.RENOUNCE_PERMISSIONS");
            }
            this.mRenouncedPermissions = set;
            return this;
        }

        public ContextParams build() {
            return new ContextParams(this.mAttributionTag, this.mNext, this.mRenouncedPermissions, this.mShouldRegisterAttributionSource);
        }
    }
}
