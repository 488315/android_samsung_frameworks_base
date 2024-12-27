package android.content.pm;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes.dex */
public final class InstallationFile {
    private final InstallationFileParcel mParcel = new InstallationFileParcel();

    public InstallationFile(int location, String name, long lengthBytes, byte[] metadata, byte[] signature) {
        this.mParcel.location = location;
        this.mParcel.name = name;
        this.mParcel.size = lengthBytes;
        this.mParcel.metadata = metadata;
        this.mParcel.signature = signature;
    }

    public int getLocation() {
        return this.mParcel.location;
    }

    public String getName() {
        return this.mParcel.name;
    }

    public long getLengthBytes() {
        return this.mParcel.size;
    }

    public byte[] getMetadata() {
        return this.mParcel.metadata;
    }

    public byte[] getSignature() {
        return this.mParcel.signature;
    }

    public InstallationFileParcel getData() {
        return this.mParcel;
    }
}
