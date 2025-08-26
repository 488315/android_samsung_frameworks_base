package android.content.om;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayManagerTransaction implements Parcelable {
    public static final Parcelable.Creator<OverlayManagerTransaction> CREATOR = new Parcelable.Creator<OverlayManagerTransaction>() { // from class: android.content.om.OverlayManagerTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayManagerTransaction createFromParcel(Parcel parcel) {
            return new OverlayManagerTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayManagerTransaction[] newArray(int i) {
            return new OverlayManagerTransaction[i];
        }
    };
    private final List<Request> mRequests;
    private final boolean mSelfTargeting;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private OverlayManagerTransaction(List<Request> list, boolean z) {
        Objects.requireNonNull(list);
        if (list.contains(null)) {
            throw new IllegalArgumentException("null request");
        }
        this.mRequests = list;
        this.mSelfTargeting = z;
    }

    public static OverlayManagerTransaction newInstance() {
        return new OverlayManagerTransaction((List<Request>) new ArrayList(), true);
    }

    private OverlayManagerTransaction(Parcel parcel) {
        int i = parcel.readInt();
        this.mRequests = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.mRequests.add(new Request(parcel.readInt(), (OverlayIdentifier) parcel.readParcelable(null, OverlayIdentifier.class), parcel.readInt(), parcel.readBundle(null), Arrays.asList((OverlayConstraint[]) parcel.createTypedArray(OverlayConstraint.CREATOR))));
        }
        this.mSelfTargeting = false;
    }

    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public Iterator<Request> getRequests() {
        return this.mRequests.iterator();
    }

    public String toString() {
        return String.format("OverlayManagerTransaction { mRequests = %s }", this.mRequests);
    }

    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public static final class Request {
        public static final String BUNDLE_FABRICATED_OVERLAY = "fabricated_overlay";
        public static final int TYPE_REGISTER_FABRICATED = 2;
        public static final int TYPE_SET_DISABLED = 1;
        public static final int TYPE_SET_ENABLED = 0;
        public static final int TYPE_UNREGISTER_FABRICATED = 3;
        public final List<OverlayConstraint> constraints;
        public final Bundle extras;
        public final OverlayIdentifier overlay;
        public final int type;
        public final int userId;

        @Retention(RetentionPolicy.SOURCE)
        @interface RequestType {
        }

        public Request(int i, OverlayIdentifier overlayIdentifier, int i2) {
            this(i, overlayIdentifier, i2, null, Collections.EMPTY_LIST);
        }

        public Request(int i, OverlayIdentifier overlayIdentifier, int i2, Bundle bundle) {
            this(i, overlayIdentifier, i2, bundle, Collections.EMPTY_LIST);
        }

        public Request(int i, OverlayIdentifier overlayIdentifier, int i2, List<OverlayConstraint> list) {
            this(i, overlayIdentifier, i2, null, list);
        }

        public Request(int i, OverlayIdentifier overlayIdentifier, int i2, Bundle bundle, List<OverlayConstraint> list) {
            this.type = i;
            this.overlay = overlayIdentifier;
            this.userId = i2;
            this.extras = bundle;
            Objects.requireNonNull(list);
            this.constraints = list;
        }

        public String toString() {
            return TextUtils.formatSimple("Request{type=0x%02x (%s), overlay=%s, userId=%d, constraints=%s}", Integer.valueOf(this.type), typeToString(), this.overlay, Integer.valueOf(this.userId), OverlayConstraint.constraintsToString(this.constraints));
        }

        public String typeToString() {
            int i = this.type;
            if (i == 0) {
                return "TYPE_SET_ENABLED";
            }
            if (i == 1) {
                return "TYPE_SET_DISABLED";
            }
            if (i == 2) {
                return "TYPE_REGISTER_FABRICATED";
            }
            if (i == 3) {
                return "TYPE_UNREGISTER_FABRICATED";
            }
            return String.format("TYPE_UNKNOWN (0x%02x)", Integer.valueOf(i));
        }
    }

    public static final class Builder {
        private final List<Request> mRequests = new ArrayList();
        private boolean mSelfTargeting = false;

        public Builder setEnabled(OverlayIdentifier overlayIdentifier, boolean z) {
            return setEnabled(overlayIdentifier, z, UserHandle.myUserId());
        }

        public Builder setEnabled(OverlayIdentifier overlayIdentifier, boolean z, List<OverlayConstraint> list) {
            return setEnabled(overlayIdentifier, z, UserHandle.myUserId(), list);
        }

        public Builder setEnabled(OverlayIdentifier overlayIdentifier, boolean z, int i) {
            return setEnabled(overlayIdentifier, z, i, Collections.EMPTY_LIST);
        }

        public Builder setEnabled(OverlayIdentifier overlayIdentifier, boolean z, int i, List<OverlayConstraint> list) {
            Objects.requireNonNull(overlayIdentifier);
            this.mRequests.add(new Request(!z ? 1 : 0, overlayIdentifier, i, list));
            return this;
        }

        public Builder setSelfTargeting(boolean z) {
            this.mSelfTargeting = z;
            return this;
        }

        public Builder registerFabricatedOverlay(FabricatedOverlay fabricatedOverlay) {
            this.mRequests.add(OverlayManagerTransaction.generateRegisterFabricatedOverlayRequest(fabricatedOverlay));
            return this;
        }

        public Builder unregisterFabricatedOverlay(OverlayIdentifier overlayIdentifier) {
            this.mRequests.add(OverlayManagerTransaction.generateUnRegisterFabricatedOverlayRequest(overlayIdentifier));
            return this;
        }

        public OverlayManagerTransaction build() {
            return new OverlayManagerTransaction(this.mRequests, this.mSelfTargeting);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int size = this.mRequests.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Request request = this.mRequests.get(i2);
            parcel.writeInt(request.type);
            parcel.writeParcelable(request.overlay, i);
            parcel.writeInt(request.userId);
            parcel.writeBundle(request.extras);
            parcel.writeTypedArray((OverlayConstraint[]) request.constraints.toArray(new OverlayConstraint[0]), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Request generateRegisterFabricatedOverlayRequest(FabricatedOverlay fabricatedOverlay) {
        Objects.requireNonNull(fabricatedOverlay);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Request.BUNDLE_FABRICATED_OVERLAY, fabricatedOverlay.mOverlay);
        return new Request(2, fabricatedOverlay.getIdentifier(), -1, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Request generateUnRegisterFabricatedOverlayRequest(OverlayIdentifier overlayIdentifier) {
        Objects.requireNonNull(overlayIdentifier);
        return new Request(3, overlayIdentifier, -1);
    }

    public void registerFabricatedOverlay(FabricatedOverlay fabricatedOverlay) {
        this.mRequests.add(generateRegisterFabricatedOverlayRequest(fabricatedOverlay));
    }

    public void unregisterFabricatedOverlay(OverlayIdentifier overlayIdentifier) {
        this.mRequests.add(generateUnRegisterFabricatedOverlayRequest(overlayIdentifier));
    }

    boolean isSelfTargeting() {
        return this.mSelfTargeting;
    }
}
