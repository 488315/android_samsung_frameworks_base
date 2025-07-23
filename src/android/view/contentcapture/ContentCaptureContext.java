package android.view.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.content.LocusId;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ContentCaptureContext implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureContext> CREATOR = new Parcelable.Creator<ContentCaptureContext>() { // from class: android.view.contentcapture.ContentCaptureContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureContext createFromParcel(Parcel parcel) {
            ContentCaptureContext contentCaptureContext;
            if (parcel.readInt() == 1) {
                LocusId locusId = (LocusId) parcel.readParcelable(null, LocusId.class);
                Bundle readBundle = parcel.readBundle();
                Builder builder = new Builder(locusId);
                if (readBundle != null) {
                    builder.setExtras(readBundle);
                }
                contentCaptureContext = new ContentCaptureContext(builder);
            } else {
                contentCaptureContext = null;
            }
            ComponentName componentName = (ComponentName) parcel.readParcelable(null, ComponentName.class);
            if (componentName == null) {
                return contentCaptureContext;
            }
            return new ContentCaptureContext(contentCaptureContext, new ActivityId(parcel), componentName, parcel.readInt(), parcel.readStrongBinder(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureContext[] newArray(int i) {
            return new ContentCaptureContext[i];
        }
    };

    @SystemApi
    public static final int FLAG_DISABLED_BY_APP = 1;

    @SystemApi
    public static final int FLAG_DISABLED_BY_FLAG_SECURE = 2;
    public static final int FLAG_DISABLED_FLUSH_FOR_VIEW_TREE_APPEARING = 8;

    @SystemApi
    public static final int FLAG_RECONNECTED = 4;
    private final ActivityId mActivityId;
    private final ComponentName mComponentName;
    private final int mDisplayId;
    private final Bundle mExtras;
    private final int mFlags;
    private final boolean mHasClientContext;
    private final LocusId mId;
    private int mParentSessionId;
    private final IBinder mWindowToken;

    @Retention(RetentionPolicy.SOURCE)
    @interface ContextCreationFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentCaptureContext(ContentCaptureContext contentCaptureContext, ActivityId activityId, ComponentName componentName, int i, IBinder iBinder, int i2) {
        this.mParentSessionId = 0;
        if (contentCaptureContext != null) {
            this.mHasClientContext = true;
            this.mExtras = contentCaptureContext.mExtras;
            this.mId = contentCaptureContext.mId;
        } else {
            this.mHasClientContext = false;
            this.mExtras = null;
            this.mId = null;
        }
        this.mComponentName = (ComponentName) Objects.requireNonNull(componentName);
        this.mFlags = i2;
        this.mDisplayId = i;
        this.mActivityId = activityId;
        this.mWindowToken = iBinder;
    }

    private ContentCaptureContext(Builder builder) {
        this.mParentSessionId = 0;
        this.mHasClientContext = true;
        this.mExtras = builder.mExtras;
        this.mId = builder.mId;
        this.mComponentName = null;
        this.mFlags = 0;
        this.mDisplayId = -1;
        this.mActivityId = null;
        this.mWindowToken = null;
    }

    public ContentCaptureContext(ContentCaptureContext contentCaptureContext, int i) {
        this.mParentSessionId = 0;
        this.mHasClientContext = contentCaptureContext.mHasClientContext;
        this.mExtras = contentCaptureContext.mExtras;
        this.mId = contentCaptureContext.mId;
        this.mComponentName = contentCaptureContext.mComponentName;
        this.mFlags = i | contentCaptureContext.mFlags;
        this.mDisplayId = contentCaptureContext.mDisplayId;
        this.mActivityId = contentCaptureContext.mActivityId;
        this.mWindowToken = contentCaptureContext.mWindowToken;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public LocusId getLocusId() {
        return this.mId;
    }

    @SystemApi
    public int getTaskId() {
        if (this.mHasClientContext) {
            return 0;
        }
        return this.mActivityId.getTaskId();
    }

    @SystemApi
    public ComponentName getActivityComponent() {
        return this.mComponentName;
    }

    @SystemApi
    public ActivityId getActivityId() {
        if (this.mHasClientContext) {
            return null;
        }
        return this.mActivityId;
    }

    @SystemApi
    public ContentCaptureSessionId getParentSessionId() {
        if (this.mParentSessionId == 0) {
            return null;
        }
        return new ContentCaptureSessionId(this.mParentSessionId);
    }

    public void setParentSessionId(int i) {
        this.mParentSessionId = i;
    }

    @SystemApi
    public int getDisplayId() {
        return this.mDisplayId;
    }

    @SystemApi
    public IBinder getWindowToken() {
        return this.mWindowToken;
    }

    @SystemApi
    public int getFlags() {
        return this.mFlags;
    }

    public static ContentCaptureContext forLocusId(String str) {
        return new Builder(new LocusId(str)).build();
    }

    public static final class Builder {
        private boolean mDestroyed;
        private Bundle mExtras;
        private final LocusId mId;

        public Builder(LocusId locusId) {
            this.mId = (LocusId) Objects.requireNonNull(locusId);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = (Bundle) Objects.requireNonNull(bundle);
            throwIfDestroyed();
            return this;
        }

        public ContentCaptureContext build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new ContentCaptureContext(this);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(!this.mDestroyed, "Already called #build()");
        }
    }

    public void dump(PrintWriter printWriter) {
        if (this.mComponentName != null) {
            printWriter.print("activity=");
            printWriter.print(this.mComponentName.flattenToShortString());
        }
        if (this.mId != null) {
            printWriter.print(", id=");
            this.mId.dump(printWriter);
        }
        printWriter.print(", activityId=");
        printWriter.print(this.mActivityId);
        printWriter.print(", displayId=");
        printWriter.print(this.mDisplayId);
        printWriter.print(", windowToken=");
        printWriter.print(this.mWindowToken);
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mFlags > 0) {
            printWriter.print(", flags=");
            printWriter.print(this.mFlags);
        }
        if (this.mExtras != null) {
            printWriter.print(", hasExtras");
        }
    }

    private boolean fromServer() {
        return this.mComponentName != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Context[");
        if (fromServer()) {
            sb.append("act=");
            sb.append(ComponentName.flattenToShortString(this.mComponentName));
            sb.append(", activityId=");
            sb.append(this.mActivityId);
            sb.append(", displayId=");
            sb.append(this.mDisplayId);
            sb.append(", windowToken=");
            sb.append(this.mWindowToken);
            sb.append(", flags=");
            sb.append(this.mFlags);
        } else {
            sb.append("id=");
            sb.append(this.mId);
            if (this.mExtras != null) {
                sb.append(", hasExtras");
            }
        }
        if (this.mParentSessionId != 0) {
            sb.append(", parentId=");
            sb.append(this.mParentSessionId);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHasClientContext ? 1 : 0);
        if (this.mHasClientContext) {
            parcel.writeParcelable(this.mId, i);
            parcel.writeBundle(this.mExtras);
        }
        parcel.writeParcelable(this.mComponentName, i);
        if (fromServer()) {
            parcel.writeInt(this.mDisplayId);
            parcel.writeStrongBinder(this.mWindowToken);
            parcel.writeInt(this.mFlags);
            this.mActivityId.writeToParcel(parcel, i);
        }
    }
}
