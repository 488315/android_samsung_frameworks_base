package com.android.internal.view;

import android.annotation.NonNull;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes4.dex */
public class AppearanceRegion implements Parcelable {
    public static final Parcelable.Creator<AppearanceRegion> CREATOR = new Parcelable.Creator<AppearanceRegion>() { // from class: com.android.internal.view.AppearanceRegion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppearanceRegion[] newArray(int i) {
            return new AppearanceRegion[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppearanceRegion createFromParcel(Parcel parcel) {
            return new AppearanceRegion(parcel);
        }
    };
    private int mAppearance;
    private Rect mBounds;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AppearanceRegion appearanceRegion = (AppearanceRegion) obj;
            if (this.mAppearance == appearanceRegion.mAppearance && this.mBounds.equals(appearanceRegion.mBounds)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "AppearanceRegion{" + ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.mAppearance) + " bounds=" + this.mBounds.toShortString() + "}";
    }

    public AppearanceRegion(int i, Rect rect) {
        this.mAppearance = i;
        this.mBounds = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
    }

    public int getAppearance() {
        return this.mAppearance;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAppearance);
        parcel.writeTypedObject(this.mBounds, i);
    }

    protected AppearanceRegion(Parcel parcel) {
        int i = parcel.readInt();
        Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mAppearance = i;
        this.mBounds = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
    }
}
