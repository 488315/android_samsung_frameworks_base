package android.media;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RouteListingPreference implements Parcelable {
    public static final String ACTION_TRANSFER_MEDIA = "android.media.action.TRANSFER_MEDIA";
    public static final Parcelable.Creator<RouteListingPreference> CREATOR = new Parcelable.Creator<RouteListingPreference>() { // from class: android.media.RouteListingPreference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteListingPreference createFromParcel(Parcel parcel) {
            return new RouteListingPreference(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteListingPreference[] newArray(int i) {
            return new RouteListingPreference[i];
        }
    };
    public static final String EXTRA_ROUTE_ID = "android.media.extra.ROUTE_ID";
    private final List<Item> mItems;
    private final ComponentName mLinkedItemComponentName;
    private final boolean mUseSystemOrdering;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private RouteListingPreference(Builder builder) {
        this.mItems = builder.mItems;
        this.mUseSystemOrdering = builder.mUseSystemOrdering;
        this.mLinkedItemComponentName = builder.mLinkedItemComponentName;
    }

    private RouteListingPreference(Parcel parcel) {
        this.mItems = List.copyOf(parcel.readParcelableList(new ArrayList(), Item.class.getClassLoader(), Item.class));
        this.mUseSystemOrdering = parcel.readBoolean();
        this.mLinkedItemComponentName = ComponentName.readFromParcel(parcel);
    }

    public List<Item> getItems() {
        return this.mItems;
    }

    public boolean getUseSystemOrdering() {
        return this.mUseSystemOrdering;
    }

    public ComponentName getLinkedItemComponentName() {
        return this.mLinkedItemComponentName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableList(this.mItems, i);
        parcel.writeBoolean(this.mUseSystemOrdering);
        ComponentName.writeToParcel(this.mLinkedItemComponentName, parcel);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RouteListingPreference)) {
            return false;
        }
        RouteListingPreference routeListingPreference = (RouteListingPreference) obj;
        return this.mItems.equals(routeListingPreference.mItems) && this.mUseSystemOrdering == routeListingPreference.mUseSystemOrdering && Objects.equals(this.mLinkedItemComponentName, routeListingPreference.mLinkedItemComponentName);
    }

    public int hashCode() {
        return Objects.hash(this.mItems, Boolean.valueOf(this.mUseSystemOrdering), this.mLinkedItemComponentName);
    }

    public static final class Builder {
        private ComponentName mLinkedItemComponentName;
        private List<Item> mItems = Collections.EMPTY_LIST;
        private boolean mUseSystemOrdering = true;

        public Builder setItems(List<Item> list) {
            this.mItems = List.copyOf((Collection) Objects.requireNonNull(list));
            return this;
        }

        public Builder setUseSystemOrdering(boolean z) {
            this.mUseSystemOrdering = z;
            return this;
        }

        public Builder setLinkedItemComponentName(ComponentName componentName) {
            this.mLinkedItemComponentName = componentName;
            return this;
        }

        public RouteListingPreference build() {
            return new RouteListingPreference(this);
        }
    }

    public static final class Item implements Parcelable {
        public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() { // from class: android.media.RouteListingPreference.Item.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Item createFromParcel(Parcel parcel) {
                return new Item(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Item[] newArray(int i) {
                return new Item[i];
            }
        };
        public static final int FLAG_ONGOING_SESSION = 1;
        public static final int FLAG_ONGOING_SESSION_MANAGED = 2;
        public static final int FLAG_SUGGESTED = 4;
        public static final int SELECTION_BEHAVIOR_GO_TO_APP = 2;
        public static final int SELECTION_BEHAVIOR_NONE = 0;
        public static final int SELECTION_BEHAVIOR_TRANSFER = 1;
        public static final int SUBTEXT_AD_ROUTING_DISALLOWED = 4;
        public static final int SUBTEXT_CUSTOM = 10000;
        public static final int SUBTEXT_DEVICE_LOW_POWER = 5;
        public static final int SUBTEXT_DOWNLOADED_CONTENT_ROUTING_DISALLOWED = 3;
        public static final int SUBTEXT_ERROR_UNKNOWN = 1;
        public static final int SUBTEXT_NONE = 0;
        public static final int SUBTEXT_SUBSCRIPTION_REQUIRED = 2;
        public static final int SUBTEXT_TRACK_UNSUPPORTED = 7;
        public static final int SUBTEXT_UNAUTHORIZED = 6;
        private final CharSequence mCustomSubtextMessage;
        private final int mFlags;
        private final String mRouteId;
        private final int mSelectionBehavior;
        private final int mSubText;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SelectionBehavior {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SubText {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Item(Builder builder) {
            this.mRouteId = builder.mRouteId;
            this.mSelectionBehavior = builder.mSelectionBehavior;
            this.mFlags = builder.mFlags;
            this.mSubText = builder.mSubText;
            this.mCustomSubtextMessage = builder.mCustomSubtextMessage;
            validateCustomMessageSubtext();
        }

        private Item(Parcel parcel) {
            this.mRouteId = parcel.readString();
            Preconditions.checkArgument(!TextUtils.isEmpty(r0));
            this.mSelectionBehavior = parcel.readInt();
            this.mFlags = parcel.readInt();
            this.mSubText = parcel.readInt();
            this.mCustomSubtextMessage = parcel.readCharSequence();
            validateCustomMessageSubtext();
        }

        public String getRouteId() {
            return this.mRouteId;
        }

        public int getSelectionBehavior() {
            return this.mSelectionBehavior;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getSubText() {
            return this.mSubText;
        }

        public CharSequence getCustomSubtextMessage() {
            return this.mCustomSubtextMessage;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mRouteId);
            parcel.writeInt(this.mSelectionBehavior);
            parcel.writeInt(this.mFlags);
            parcel.writeInt(this.mSubText);
            parcel.writeCharSequence(this.mCustomSubtextMessage);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Item)) {
                return false;
            }
            Item item = (Item) obj;
            return this.mRouteId.equals(item.mRouteId) && this.mSelectionBehavior == item.mSelectionBehavior && this.mFlags == item.mFlags && this.mSubText == item.mSubText && TextUtils.equals(this.mCustomSubtextMessage, item.mCustomSubtextMessage);
        }

        public int hashCode() {
            return Objects.hash(this.mRouteId, Integer.valueOf(this.mSelectionBehavior), Integer.valueOf(this.mFlags), Integer.valueOf(this.mSubText), this.mCustomSubtextMessage);
        }

        private void validateCustomMessageSubtext() {
            Preconditions.checkArgument((this.mSubText == 10000 && this.mCustomSubtextMessage == null) ? false : true, "The custom subtext message cannot be null if subtext is SUBTEXT_CUSTOM.");
        }

        public static final class Builder {
            private CharSequence mCustomSubtextMessage;
            private int mFlags;
            private final String mRouteId;
            private int mSelectionBehavior;
            private int mSubText;

            public Builder(String str) {
                Preconditions.checkArgument(!TextUtils.isEmpty(str));
                this.mRouteId = str;
                this.mSelectionBehavior = 1;
                this.mSubText = 0;
            }

            public Builder setSelectionBehavior(int i) {
                this.mSelectionBehavior = i;
                return this;
            }

            public Builder setFlags(int i) {
                this.mFlags = i;
                return this;
            }

            public Builder setSubText(int i) {
                this.mSubText = i;
                return this;
            }

            public Builder setCustomSubtextMessage(CharSequence charSequence) {
                this.mCustomSubtextMessage = charSequence;
                return this;
            }

            public Item build() {
                return new Item(this);
            }
        }
    }
}
