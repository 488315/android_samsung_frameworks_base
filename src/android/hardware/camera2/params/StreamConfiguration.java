package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Size;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public class StreamConfiguration {
    protected int mFormat;
    protected int mHeight;
    protected boolean mInput;
    protected int mWidth;

    public StreamConfiguration(int i, int i2, int i3, boolean z) {
        this.mFormat = StreamConfigurationMap.checkArgumentFormatInternal(i);
        this.mWidth = Preconditions.checkArgumentPositive(i2, "width must be positive");
        this.mHeight = Preconditions.checkArgumentPositive(i3, "height must be positive");
        this.mInput = z;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Size getSize() {
        return new Size(this.mWidth, this.mHeight);
    }

    public boolean isInput() {
        return this.mInput;
    }

    public boolean isOutput() {
        return !this.mInput;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof StreamConfiguration) {
            StreamConfiguration streamConfiguration = (StreamConfiguration) obj;
            if (this.mFormat == streamConfiguration.mFormat && this.mWidth == streamConfiguration.mWidth && this.mHeight == streamConfiguration.mHeight && this.mInput == streamConfiguration.mInput) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mFormat, this.mWidth, this.mHeight, this.mInput);
    }
}
