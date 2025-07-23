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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface CommunalWidgetContentModel extends Parcelable {
    public static final CREATOR CREATOR = CREATOR.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CREATOR implements Parcelable.Creator {
        public static final /* synthetic */ CREATOR $$INSTANCE = new CREATOR();

        private CREATOR() {
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 0) {
                return new Available(parcel);
            }
            if (readInt == 1) {
                return new Pending(parcel);
            }
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readInt, "Unknown type: "));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CommunalWidgetContentModel[i];
        }
    }

    int getAppWidgetId();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Available implements CommunalWidgetContentModel {
        public static final CREATOR CREATOR = new CREATOR(null);
        public final int appWidgetId;
        public final AppWidgetProviderInfo providerInfo;
        public final int rank;
        public final int spanY;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Available(android.os.Parcel r4) {
            /*
                r3 = this;
                int r0 = r4.readInt()
                android.os.Parcelable$Creator r1 = android.appwidget.AppWidgetProviderInfo.CREATOR
                java.lang.Object r1 = r4.readTypedObject(r1)
                if (r1 == 0) goto L1a
                android.appwidget.AppWidgetProviderInfo r1 = (android.appwidget.AppWidgetProviderInfo) r1
                int r2 = r4.readInt()
                int r4 = r4.readInt()
                r3.<init>(r0, r1, r2, r4)
                return
            L1a:
                java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
                java.lang.String r4 = "Required value was null."
                r3.<init>(r4)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.shared.model.CommunalWidgetContentModel.Available.<init>(android.os.Parcel):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Pending implements CommunalWidgetContentModel {
        public static final CREATOR CREATOR = new CREATOR(null);
        public final int appWidgetId;
        public final ComponentName componentName;
        public final Bitmap icon;
        public final int rank;
        public final int spanY;
        public final int type;
        public final UserHandle user;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.componentName.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.rank, Integer.hashCode(this.appWidgetId) * 31, 31)) * 31;
            Bitmap bitmap = this.icon;
            return Integer.hashCode(this.spanY) + ((this.user.hashCode() + ((hashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31)) * 31);
        }

        public final String toString() {
            int i = this.appWidgetId;
            int i2 = this.rank;
            ComponentName componentName = this.componentName;
            Bitmap bitmap = this.icon;
            UserHandle userHandle = this.user;
            int i3 = this.spanY;
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "Pending(appWidgetId=", ", rank=", ", componentName=");
            m.append(componentName);
            m.append(", icon=");
            m.append(bitmap);
            m.append(", user=");
            m.append(userHandle);
            m.append(", spanY=");
            m.append(i3);
            m.append(")");
            return m.toString();
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
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Pending(android.os.Parcel r8) {
            /*
                r7 = this;
                int r1 = r8.readInt()
                int r2 = r8.readInt()
                android.os.Parcelable$Creator r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r8.readTypedObject(r0)
                java.lang.String r3 = "Required value was null."
                if (r0 == 0) goto L36
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                android.os.Parcelable$Creator r4 = android.graphics.Bitmap.CREATOR
                java.lang.Object r4 = r8.readTypedObject(r4)
                android.graphics.Bitmap r4 = (android.graphics.Bitmap) r4
                android.os.Parcelable$Creator r5 = android.os.UserHandle.CREATOR
                java.lang.Object r5 = r8.readTypedObject(r5)
                if (r5 == 0) goto L30
                android.os.UserHandle r5 = (android.os.UserHandle) r5
                int r6 = r8.readInt()
                r3 = r0
                r0 = r7
                r0.<init>(r1, r2, r3, r4, r5, r6)
                return
            L30:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                r7.<init>(r3)
                throw r7
            L36:
                java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
                r7.<init>(r3)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.shared.model.CommunalWidgetContentModel.Pending.<init>(android.os.Parcel):void");
        }
    }
}
