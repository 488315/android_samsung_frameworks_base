package com.android.systemui.communal.shared.model;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface CommunalWidgetContentModel extends Parcelable {
    public static final CREATOR CREATOR = CREATOR.$$INSTANCE;

    public final class CREATOR implements Parcelable.Creator {
        public static final /* synthetic */ CREATOR $$INSTANCE = new CREATOR();

        private CREATOR() {
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 0) {
                return new Available(parcel);
            }
            if (i == 1) {
                return new Pending(parcel);
            }
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown type: "));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CommunalWidgetContentModel[i];
        }
    }

    int getAppWidgetId();

    public final class Available implements CommunalWidgetContentModel {
        public static final CREATOR CREATOR = new CREATOR(null);
        public final int appWidgetId;
        public final AppWidgetProviderInfo providerInfo;
        public final int rank;
        public final int spanY;

        public final class CREATOR implements Parcelable.Creator {
            public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new Available(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Available[i];
            }

            private CREATOR() {
            }
        }

        public Available(int i, AppWidgetProviderInfo appWidgetProviderInfo, int i2, int i3) {
            this.appWidgetId = i;
            this.providerInfo = appWidgetProviderInfo;
            this.rank = i2;
            this.spanY = i3;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Available)) {
                return false;
            }
            Available available = (Available) obj;
            return this.appWidgetId == available.appWidgetId && Intrinsics.areEqual(this.providerInfo, available.providerInfo) && this.rank == available.rank && this.spanY == available.spanY;
        }

        @Override // com.android.systemui.communal.shared.model.CommunalWidgetContentModel
        public final int getAppWidgetId() {
            return this.appWidgetId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.spanY) + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, (this.providerInfo.hashCode() + (Integer.hashCode(this.appWidgetId) * 31)) * 31, 31);
        }

        public final String toString() {
            return "Available(appWidgetId=" + this.appWidgetId + ", providerInfo=" + this.providerInfo + ", rank=" + this.rank + ", spanY=" + this.spanY + ")";
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(0);
            parcel.writeInt(this.appWidgetId);
            parcel.writeTypedObject(this.providerInfo, i);
            parcel.writeInt(this.rank);
            parcel.writeInt(this.spanY);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Available(Parcel parcel) {
            int i = parcel.readInt();
            Object typedObject = parcel.readTypedObject(AppWidgetProviderInfo.CREATOR);
            if (typedObject != null) {
                this(i, (AppWidgetProviderInfo) typedObject, parcel.readInt(), parcel.readInt());
                return;
            }
            throw new IllegalArgumentException("Required value was null.");
        }
    }

    public final class Pending implements CommunalWidgetContentModel {
        public static final CREATOR CREATOR = new CREATOR(null);
        public final int appWidgetId;
        public final ComponentName componentName;
        public final Bitmap icon;
        public final int rank;
        public final int spanY;
        public final int type;
        public final UserHandle user;

        public final class CREATOR implements Parcelable.Creator {
            public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new Pending(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new Pending[i];
            }

            private CREATOR() {
            }
        }

        public Pending(int i, int i2, ComponentName componentName, Bitmap bitmap, UserHandle userHandle, int i3) {
            this.appWidgetId = i;
            this.rank = i2;
            this.componentName = componentName;
            this.icon = bitmap;
            this.user = userHandle;
            this.spanY = i3;
            this.type = 1;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Pending)) {
                return false;
            }
            Pending pending = (Pending) obj;
            return this.appWidgetId == pending.appWidgetId && this.rank == pending.rank && Intrinsics.areEqual(this.componentName, pending.componentName) && Intrinsics.areEqual(this.icon, pending.icon) && Intrinsics.areEqual(this.user, pending.user) && this.spanY == pending.spanY;
        }

        @Override // com.android.systemui.communal.shared.model.CommunalWidgetContentModel
        public final int getAppWidgetId() {
            return this.appWidgetId;
        }

        public final int hashCode() {
            int iHashCode = (this.componentName.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, Integer.hashCode(this.appWidgetId) * 31, 31)) * 31;
            Bitmap bitmap = this.icon;
            return Integer.hashCode(this.spanY) + ((this.user.hashCode() + ((iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31)) * 31);
        }

        public final String toString() {
            int i = this.appWidgetId;
            int i2 = this.rank;
            ComponentName componentName = this.componentName;
            Bitmap bitmap = this.icon;
            UserHandle userHandle = this.user;
            int i3 = this.spanY;
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "Pending(appWidgetId=", ", rank=", ", componentName=");
            sbM.append(componentName);
            sbM.append(", icon=");
            sbM.append(bitmap);
            sbM.append(", user=");
            sbM.append(userHandle);
            sbM.append(", spanY=");
            sbM.append(i3);
            sbM.append(")");
            return sbM.toString();
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.type);
            parcel.writeInt(this.appWidgetId);
            parcel.writeInt(this.rank);
            parcel.writeTypedObject(this.componentName, i);
            parcel.writeTypedObject(this.icon, i);
            parcel.writeTypedObject(this.user, i);
            parcel.writeInt(this.spanY);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public Pending(Parcel parcel) {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            Object typedObject = parcel.readTypedObject(ComponentName.CREATOR);
            if (typedObject != null) {
                ComponentName componentName = (ComponentName) typedObject;
                Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                Object typedObject2 = parcel.readTypedObject(UserHandle.CREATOR);
                if (typedObject2 != null) {
                    this(i, i2, componentName, bitmap, (UserHandle) typedObject2, parcel.readInt());
                    return;
                }
                throw new IllegalArgumentException("Required value was null.");
            }
            throw new IllegalArgumentException("Required value was null.");
        }
    }
}
