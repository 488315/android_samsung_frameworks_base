package android.app.smartspace;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceTargetEvent implements Parcelable {
    public static final Parcelable.Creator<SmartspaceTargetEvent> CREATOR = new Parcelable.Creator<SmartspaceTargetEvent>() { // from class: android.app.smartspace.SmartspaceTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceTargetEvent createFromParcel(Parcel parcel) {
            return new SmartspaceTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceTargetEvent[] newArray(int i) {
            return new SmartspaceTargetEvent[i];
        }
    };
    public static final int EVENT_TARGET_BLOCK = 5;
    public static final int EVENT_TARGET_DISMISS = 4;
    public static final int EVENT_TARGET_HIDDEN = 3;
    public static final int EVENT_TARGET_INTERACTION = 1;
    public static final int EVENT_TARGET_SHOWN = 2;
    public static final int EVENT_UI_SURFACE_HIDDEN = 7;
    public static final int EVENT_UI_SURFACE_SHOWN = 6;
    private final int mEventType;
    private final String mSmartspaceActionId;
    private final SmartspaceTarget mSmartspaceTarget;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SmartspaceTargetEvent(SmartspaceTarget smartspaceTarget, String str, int i) {
        this.mSmartspaceTarget = smartspaceTarget;
        this.mSmartspaceActionId = str;
        this.mEventType = i;
    }

    private SmartspaceTargetEvent(Parcel parcel) {
        this.mSmartspaceTarget = (SmartspaceTarget) parcel.readParcelable(null, SmartspaceTarget.class);
        this.mSmartspaceActionId = parcel.readString();
        this.mEventType = parcel.readInt();
    }

    public SmartspaceTarget getSmartspaceTarget() {
        return this.mSmartspaceTarget;
    }

    public String getSmartspaceActionId() {
        return this.mSmartspaceActionId;
    }

    public int getEventType() {
        return this.mEventType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mSmartspaceTarget, i);
        parcel.writeString(this.mSmartspaceActionId);
        parcel.writeInt(this.mEventType);
    }

    public String toString() {
        return "SmartspaceTargetEvent{mSmartspaceTarget=" + this.mSmartspaceTarget + ", mSmartspaceActionId='" + this.mSmartspaceActionId + "', mEventType=" + this.mEventType + '}';
    }

    @SystemApi
    public static final class Builder {
        private final int mEventType;
        private String mSmartspaceActionId;
        private SmartspaceTarget mSmartspaceTarget;

        public Builder(int i) {
            this.mEventType = i;
        }

        public Builder setSmartspaceTarget(SmartspaceTarget smartspaceTarget) {
            this.mSmartspaceTarget = smartspaceTarget;
            return this;
        }

        public Builder setSmartspaceActionId(String str) {
            this.mSmartspaceActionId = str;
            return this;
        }

        public SmartspaceTargetEvent build() {
            return new SmartspaceTargetEvent(this.mSmartspaceTarget, this.mSmartspaceActionId, this.mEventType);
        }
    }
}
