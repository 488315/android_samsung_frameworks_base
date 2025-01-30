package com.samsung.android.multiwindow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SurfaceFreezerSnapshot implements Parcelable {
  public static final Parcelable.Creator<SurfaceFreezerSnapshot> CREATOR =
      new Parcelable.Creator<
          SurfaceFreezerSnapshot>() { // from class:
                                      // com.samsung.android.multiwindow.SurfaceFreezerSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceFreezerSnapshot createFromParcel(Parcel source) {
          return new SurfaceFreezerSnapshot(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceFreezerSnapshot[] newArray(int size) {
          return new SurfaceFreezerSnapshot[size];
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

  public SurfaceFreezerSnapshot(
      Bitmap snapshotBitmap,
      int taskId,
      boolean containsSecureLayer,
      boolean hasProtectedContent,
      Bitmap wallpaperBitmap) {
    this(snapshotBitmap, taskId, containsSecureLayer, hasProtectedContent, wallpaperBitmap, 0, 0);
  }

  public SurfaceFreezerSnapshot(
      Bitmap snapshotBitmap,
      int taskId,
      boolean containsSecureLayer,
      boolean hasProtectedContent,
      Bitmap wallpaperBitmap,
      int headerHeight,
      int headerColor) {
    this.mSnapshotBitmap = snapshotBitmap;
    this.mWallpaperBitmap = wallpaperBitmap;
    this.mTaskId = taskId;
    this.mContainsSecureLayer = containsSecureLayer;
    this.mHasProtectedContent = hasProtectedContent;
    this.mFreeformHeaderHeight = headerHeight;
    this.mFreeformHeaderColor = headerColor;
  }

  private SurfaceFreezerSnapshot(Parcel in) {
    if (in.readInt() != 0) {
      this.mSnapshotBitmap = Bitmap.CREATOR.createFromParcel(in);
    } else {
      this.mSnapshotBitmap = null;
    }
    if (in.readInt() != 0) {
      this.mWallpaperBitmap = Bitmap.CREATOR.createFromParcel(in);
    } else {
      this.mWallpaperBitmap = null;
    }
    this.mTaskId = in.readInt();
    this.mContainsSecureLayer = in.readInt() != 0;
    this.mHasProtectedContent = in.readInt() != 0;
    this.mFreeformHeaderHeight = in.readInt();
    this.mFreeformHeaderColor = in.readInt();
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

  public Bitmap createSnapshotBitmapWithWallpaper(int splitBackgroundColor) {
    Bitmap bitmap = this.mSnapshotBitmap;
    if (bitmap == null || this.mWallpaperBitmap == null) {
      Log.m96e(
          TAG,
          "createSnapshotBitmapWithWallpaper: failed, snapshot="
              + this.mSnapshotBitmap
              + ", wallpaper="
              + this.mWallpaperBitmap);
      return null;
    }
    Bitmap bitmap2 =
        Bitmap.createBitmap(
            bitmap.getWidth(), this.mSnapshotBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap2);
    canvas.drawBitmap(this.mWallpaperBitmap, 0.0f, 0.0f, (Paint) null);
    canvas.drawColor(splitBackgroundColor);
    canvas.drawBitmap(this.mSnapshotBitmap, 0.0f, 0.0f, (Paint) null);
    return bitmap2;
  }

  @Override // android.p009os.Parcelable
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
