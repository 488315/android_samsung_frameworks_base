package android.hardware.camera2.params;

import android.annotation.SystemApi;
import android.graphics.ColorSpace;
import android.util.Log;
import android.util.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes2.dex */
public final class SharedSessionConfiguration {
    private static final int SHARED_OUTPUT_CONFIG_NUM_OF_ENTRIES = 11;
    private static final String TAG = "SharedSessionConfiguration";
    private int mColorSpace;
    private final ArrayList<SharedOutputConfiguration> mOutputStreamConfigurations = new ArrayList<>();

    public static final class SharedOutputConfiguration {
        private final int mDataspace;
        private final int mFormat;
        private int mMirrorMode;
        private String mPhysicalCameraId;
        private boolean mReadoutTimestampEnabled;
        private final Size mSize;
        private final long mStreamUseCase;
        private final int mSurfaceType;
        private int mTimestampBase;
        private final long mUsage;

        public SharedOutputConfiguration(int i, Size size, int i2, int i3, boolean z, int i4, int i5, long j, long j2, String str) {
            this.mSurfaceType = i;
            this.mSize = size;
            this.mFormat = i2;
            this.mMirrorMode = i3;
            this.mReadoutTimestampEnabled = z;
            this.mTimestampBase = i4;
            this.mDataspace = i5;
            this.mUsage = j;
            this.mStreamUseCase = j2;
            this.mPhysicalCameraId = str;
        }

        public int getSurfaceType() {
            return this.mSurfaceType;
        }

        public int getFormat() {
            return this.mFormat;
        }

        public Size getSize() {
            return this.mSize;
        }

        public int getDataspace() {
            return this.mDataspace;
        }

        public int getMirrorMode() {
            return this.mMirrorMode;
        }

        public long getStreamUseCase() {
            return this.mStreamUseCase;
        }

        public int getTimestampBase() {
            return this.mTimestampBase;
        }

        public boolean isReadoutTimestampEnabled() {
            return this.mReadoutTimestampEnabled;
        }

        public long getUsage() {
            return this.mUsage;
        }

        public String getPhysicalCameraId() {
            if (this.mPhysicalCameraId.isEmpty()) {
                return null;
            }
            return this.mPhysicalCameraId;
        }
    }

    public SharedSessionConfiguration(int i, long[] jArr) {
        byte b;
        int i2;
        long[] jArr2 = jArr;
        this.mColorSpace = i;
        int length = jArr2.length;
        int i3 = 0;
        while (true) {
            if (length < 11) {
                break;
            }
            int i4 = (int) jArr2[i3];
            int i5 = (int) jArr2[i3 + 1];
            int i6 = (int) jArr2[i3 + 2];
            int i7 = (int) jArr2[i3 + 3];
            int i8 = (int) jArr2[i3 + 4];
            boolean z = jArr2[i3 + 5] != 0;
            int i9 = i3;
            int i10 = (int) jArr2[i3 + 6];
            int i11 = (int) jArr2[i9 + 7];
            long j = jArr2[i9 + 8];
            long j2 = jArr2[i9 + 9];
            byte b2 = (byte) jArr[i9 + 10];
            length -= 11;
            int i12 = i9 + 11;
            if (length < b2) {
                Log.e(TAG, "Number of remaining data in shared configuration is less than physical camera id length . Malformed metadata android.info.availableSharedOutputConfigurations.");
                break;
            }
            StringBuilder sb = new StringBuilder();
            int i13 = 0;
            while (true) {
                if (i13 >= b2) {
                    b = b2;
                    i2 = length;
                    break;
                }
                b = b2;
                i2 = length;
                long j3 = jArr[i12 + i13];
                if (j3 == 0) {
                    break;
                }
                sb.append((char) j3);
                i13++;
                length = i2;
                b2 = b;
            }
            this.mOutputStreamConfigurations.add(new SharedOutputConfiguration(i4, new Size(i5, i6), i7, i8, z, i10, i11, j, j2, sb.toString()));
            i3 = i12 + b;
            length = i2 - b;
            jArr2 = jArr;
        }
        if (length != 0) {
            Log.e(TAG, "Unexpected entries left in shared output configuration. Malformed metadata android.info.availableSharedOutputConfigurations.");
        }
    }

    public ColorSpace getColorSpace() {
        if (this.mColorSpace != -1) {
            return ColorSpace.get(ColorSpace.Named.values()[this.mColorSpace]);
        }
        return null;
    }

    public List<SharedOutputConfiguration> getOutputStreamsInformation() {
        return Collections.unmodifiableList(this.mOutputStreamConfigurations);
    }
}
