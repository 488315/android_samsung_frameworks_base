package android.util.apk;

import android.os.incremental.IncrementalManager;
import java.io.FileDescriptor;
import java.io.IOException;
import java.security.DigestException;

/* loaded from: classes4.dex */
interface DataSource {
    void feedIntoDataDigester(DataDigester dataDigester, long j, int i) throws DigestException, IOException;

    long size();

    static DataSource create(FileDescriptor fileDescriptor, long j, long j2) {
        if (IncrementalManager.isIncrementalFileFd(fileDescriptor)) {
            return new ReadFileDataSource(fileDescriptor, j, j2);
        }
        return new MemoryMappedFileDataSource(fileDescriptor, j, j2);
    }
}
