package com.android.server.backup;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FileMetadata {
    public String domain;
    public boolean hasApk;
    public String installerPackageName;
    public long mode;
    public long mtime;
    public String packageName;
    public String path;
    public long size;
    public int splitCount;
    public int type;
    public long version;

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "FileMetadata{");
        m.append(this.packageName);
        m.append(',');
        m.append(this.type);
        m.append(',');
        m.append(this.domain);
        m.append(':');
        m.append(this.path);
        m.append(',');
        m.append(this.size);
        m.append('}');
        return m.toString();
    }
}
