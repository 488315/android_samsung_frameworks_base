package com.android.internal.widget.remotecompose.core.operations;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import com.android.internal.widget.remotecompose.core.CompanionOperation;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.RemoteComposeOperation;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class Header implements RemoteComposeOperation {
    public static final Companion COMPANION = new Companion();
    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 1;
    public static final int PATCH_VERSION = 0;
    long mCapabilities;
    int mHeight;
    int mMajorVersion;
    int mMinorVersion;
    int mPatchVersion;
    int mWidth;

    public Header(int majorVersion, int minorVersion, int patchVersion, int width, int height, long capabilities) {
        this.mMajorVersion = majorVersion;
        this.mMinorVersion = minorVersion;
        this.mPatchVersion = patchVersion;
        this.mWidth = width;
        this.mHeight = height;
        this.mCapabilities = capabilities;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer buffer) {
        COMPANION.apply(buffer, this.mWidth, this.mHeight, this.mCapabilities);
    }

    public String toString() {
        return "HEADER v" + this.mMajorVersion + MediaMetrics.SEPARATOR + this.mMinorVersion + MediaMetrics.SEPARATOR + this.mPatchVersion + ", " + this.mWidth + " x " + this.mHeight + " [" + this.mCapabilities + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(RemoteContext context) {
        context.header(this.mMajorVersion, this.mMinorVersion, this.mPatchVersion, this.mWidth, this.mHeight, this.mCapabilities);
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public String deepToString(String indent) {
        return toString();
    }

    public static class Companion implements CompanionOperation {
        private Companion() {
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public String name() {
            return "Header";
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public int id() {
            return 0;
        }

        public void apply(WireBuffer buffer, int width, int height, long capabilities) {
            buffer.start(0);
            buffer.writeInt(0);
            buffer.writeInt(1);
            buffer.writeInt(0);
            buffer.writeInt(width);
            buffer.writeInt(height);
            buffer.writeLong(capabilities);
        }

        @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
        public void read(WireBuffer buffer, List<Operation> operations) {
            int majorVersion = buffer.readInt();
            int minorVersion = buffer.readInt();
            int patchVersion = buffer.readInt();
            int width = buffer.readInt();
            int height = buffer.readInt();
            long capabilities = buffer.readLong();
            Header header = new Header(majorVersion, minorVersion, patchVersion, width, height, capabilities);
            operations.add(header);
        }
    }
}
