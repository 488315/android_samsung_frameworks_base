package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;
import java.util.Collection;

/* loaded from: classes2.dex */
public final class InputConfiguration {
    private final int mFormat;
    private final int mHeight;
    private final boolean mIsMultiResolution;
    private final int mWidth;

    public InputConfiguration(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mIsMultiResolution = false;
    }

    public InputConfiguration(Collection<MultiResolutionStreamInfo> collection, int i) {
        Preconditions.checkCollectionNotEmpty(collection, "Input multi-resolution stream info");
        MultiResolutionStreamInfo next = collection.iterator().next();
        this.mWidth = next.getWidth();
        this.mHeight = next.getHeight();
        this.mFormat = i;
        this.mIsMultiResolution = true;
    }

    public InputConfiguration(int i, int i2, int i3, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mIsMultiResolution = z;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public boolean isMultiResolution() {
        return this.mIsMultiResolution;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InputConfiguration)) {
            return false;
        }
        InputConfiguration inputConfiguration = (InputConfiguration) obj;
        return inputConfiguration.getWidth() == this.mWidth && inputConfiguration.getHeight() == this.mHeight && inputConfiguration.getFormat() == this.mFormat && inputConfiguration.isMultiResolution() == this.mIsMultiResolution;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mWidth, this.mHeight, this.mFormat, this.mIsMultiResolution);
    }

    public String toString() {
        return String.format("InputConfiguration(w:%d, h:%d, format:%d, isMultiResolution %b)", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat), Boolean.valueOf(this.mIsMultiResolution));
    }
}
