package com.android.internal.util;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Insets;
import android.graphics.ParcelableColorSpace;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;

/* loaded from: classes4.dex */
public class ScreenshotRequest implements Parcelable {
    public static final Parcelable.Creator<ScreenshotRequest> CREATOR = new Parcelable.Creator<ScreenshotRequest>() { // from class: com.android.internal.util.ScreenshotRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotRequest createFromParcel(Parcel parcel) {
            return new ScreenshotRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotRequest[] newArray(int i) {
            return new ScreenshotRequest[i];
        }
    };
    private static final String TAG = "ScreenshotRequest";
    private final Bitmap mBitmap;
    private final Rect mBoundsInScreen;
    private final int mDisplayId;
    private final Insets mInsets;
    private final int mSource;
    private final int mTaskId;
    private final ComponentName mTopComponent;
    private final int mType;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ScreenshotRequest(int i, int i2, ComponentName componentName, int i3, int i4, Bitmap bitmap, Rect rect, Insets insets, int i5) {
        this.mType = i;
        this.mSource = i2;
        this.mTopComponent = componentName;
        this.mTaskId = i3;
        this.mUserId = i4;
        this.mBitmap = bitmap;
        this.mBoundsInScreen = rect;
        this.mInsets = insets;
        this.mDisplayId = i5;
    }

    ScreenshotRequest(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mTopComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mTaskId = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mBitmap = HardwareBitmapBundler.bundleToHardwareBitmap((Bundle) parcel.readTypedObject(Bundle.CREATOR));
        this.mBoundsInScreen = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mInsets = (Insets) parcel.readTypedObject(Insets.CREATOR);
        this.mDisplayId = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getSource() {
        return this.mSource;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public Insets getInsets() {
        return this.mInsets;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public ComponentName getTopComponent() {
        return this.mTopComponent;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mSource);
        parcel.writeTypedObject(this.mTopComponent, 0);
        parcel.writeInt(this.mTaskId);
        parcel.writeInt(this.mUserId);
        parcel.writeTypedObject(HardwareBitmapBundler.hardwareBitmapToBundle(this.mBitmap), 0);
        parcel.writeTypedObject(this.mBoundsInScreen, 0);
        parcel.writeTypedObject(this.mInsets, 0);
        parcel.writeInt(this.mDisplayId);
    }

    public static class Builder {
        private Bitmap mBitmap;
        private Rect mBoundsInScreen;
        private final int mSource;
        private ComponentName mTopComponent;
        private final int mType;
        private Insets mInsets = Insets.NONE;
        private int mTaskId = -1;
        private int mUserId = -10000;
        private int mDisplayId = -1;

        public Builder(int i, int i2) {
            if (i != 1 && i != 3 && i != 2 && i != 100 && i != 101) {
                throw new IllegalArgumentException("Invalid screenshot type requested!");
            }
            this.mType = i;
            this.mSource = i2;
        }

        public ScreenshotRequest build() {
            if (this.mType == 1 && this.mBitmap != null) {
                Log.w(ScreenshotRequest.TAG, "Bitmap provided, but request is fullscreen. Bitmap will be ignored.");
            }
            if (this.mType == 3 && this.mBitmap == null) {
                throw new IllegalStateException("Request is PROVIDED_IMAGE, but no bitmap is provided!");
            }
            return new ScreenshotRequest(this.mType, this.mSource, this.mTopComponent, this.mTaskId, this.mUserId, this.mBitmap, this.mBoundsInScreen, this.mInsets, this.mDisplayId);
        }

        public Builder setTopComponent(ComponentName componentName) {
            this.mTopComponent = componentName;
            return this;
        }

        public Builder setTaskId(int i) {
            this.mTaskId = i;
            return this;
        }

        public Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.mBitmap = bitmap;
            return this;
        }

        public Builder setBoundsOnScreen(Rect rect) {
            this.mBoundsInScreen = rect;
            return this;
        }

        public Builder setInsets(Insets insets) {
            this.mInsets = insets;
            return this;
        }

        public Builder setDisplayId(int i) {
            this.mDisplayId = i;
            return this;
        }
    }

    private static final class HardwareBitmapBundler {
        private static final String KEY_BUFFER = "bitmap_util_buffer";
        private static final String KEY_COLOR_SPACE = "bitmap_util_color_space";

        private HardwareBitmapBundler() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Bundle hardwareBitmapToBundle(Bitmap bitmap) {
            ParcelableColorSpace parcelableColorSpace;
            if (bitmap == null) {
                return null;
            }
            if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
                throw new IllegalArgumentException("Passed bitmap must have hardware config, found: " + bitmap.getConfig());
            }
            if (bitmap.getColorSpace() == null) {
                parcelableColorSpace = new ParcelableColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
            } else {
                parcelableColorSpace = new ParcelableColorSpace(bitmap.getColorSpace());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_BUFFER, bitmap.getHardwareBuffer());
            bundle.putParcelable(KEY_COLOR_SPACE, parcelableColorSpace);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Bitmap bundleToHardwareBitmap(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            if (bundle.containsKey(KEY_BUFFER) && bundle.containsKey(KEY_COLOR_SPACE)) {
                return Bitmap.wrapHardwareBuffer((HardwareBuffer) Objects.requireNonNull((HardwareBuffer) bundle.getParcelable(KEY_BUFFER, HardwareBuffer.class)), ((ParcelableColorSpace) bundle.getParcelable(KEY_COLOR_SPACE, ParcelableColorSpace.class)).getColorSpace());
            }
            throw new IllegalArgumentException("Bundle does not contain a hardware bitmap");
        }
    }
}
