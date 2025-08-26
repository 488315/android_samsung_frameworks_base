package com.samsung.android.multiwindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SurfaceFreezerSnapshot implements Parcelable {
    public static final Parcelable.Creator<SurfaceFreezerSnapshot> CREATOR = new Parcelable.Creator<SurfaceFreezerSnapshot>() { // from class: com.samsung.android.multiwindow.SurfaceFreezerSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceFreezerSnapshot createFromParcel(Parcel parcel) {
            return new SurfaceFreezerSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceFreezerSnapshot[] newArray(int i) {
            return new SurfaceFreezerSnapshot[i];
        }
    };
    private static final String TAG = "SurfaceFreezerSnapshot";
    private final boolean mContainsSecureLayer;
    private final int mFreeformHeaderColor;
    private final int mFreeformHeaderHeight;
    private final boolean mHasProtectedContent;
    private final Bitmap mSnapshotBitmap;
    private final int mTaskId;
    private final Bitmap mWallpaperBitmap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SurfaceFreezerSnapshot(Bitmap bitmap, int i, boolean z, boolean z2, Bitmap bitmap2) {
        this(bitmap, i, z, z2, bitmap2, 0, 0);
    }

    public SurfaceFreezerSnapshot(Bitmap bitmap, int i, boolean z, boolean z2, Bitmap bitmap2, int i2, int i3) {
        this.mSnapshotBitmap = bitmap;
        this.mWallpaperBitmap = bitmap2;
        this.mTaskId = i;
        this.mContainsSecureLayer = z;
        this.mHasProtectedContent = z2;
        this.mFreeformHeaderHeight = i2;
        this.mFreeformHeaderColor = i3;
    }

    private SurfaceFreezerSnapshot(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mSnapshotBitmap = Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mSnapshotBitmap = null;
        }
        if (parcel.readInt() != 0) {
            this.mWallpaperBitmap = Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mWallpaperBitmap = null;
        }
        this.mTaskId = parcel.readInt();
        this.mContainsSecureLayer = parcel.readInt() != 0;
        this.mHasProtectedContent = parcel.readInt() != 0;
        this.mFreeformHeaderHeight = parcel.readInt();
        this.mFreeformHeaderColor = parcel.readInt();
    }

    public boolean containsSecureLayer() {
        return this.mContainsSecureLayer;
    }

    public boolean hasProtectedContent() {
        return this.mHasProtectedContent;
    }

    public Bitmap getSnapshotBitmap() {
        return this.mSnapshotBitmap;
    }

    public boolean hasWallpaperBitmap() {
        return this.mWallpaperBitmap != null;
    }

    public boolean hasFreeformHeader() {
        return this.mFreeformHeaderHeight != 0;
    }

    public Bitmap createSnapshotBitmapWithWallpaper(int i) {
        Bitmap bitmap = this.mSnapshotBitmap;
        if (bitmap == null || this.mWallpaperBitmap == null) {
            Log.e(TAG, "createSnapshotBitmapWithWallpaper: failed, snapshot=" + this.mSnapshotBitmap + ", wallpaper=" + this.mWallpaperBitmap);
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), this.mSnapshotBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(this.mWallpaperBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawColor(i);
        canvas.drawBitmap(this.mSnapshotBitmap, 0.0f, 0.0f, (Paint) null);
        return bitmapCreateBitmap;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mSnapshotBitmap != null) {
            parcel.writeInt(1);
            this.mSnapshotBitmap.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mWallpaperBitmap != null) {
            parcel.writeInt(1);
            this.mWallpaperBitmap.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mTaskId);
        parcel.writeInt(this.mContainsSecureLayer ? 1 : 0);
        parcel.writeInt(this.mHasProtectedContent ? 1 : 0);
        parcel.writeInt(this.mFreeformHeaderHeight);
        parcel.writeInt(this.mFreeformHeaderColor);
    }
}
