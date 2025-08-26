package androidx.room.concurrent;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public final class FileLock {
    public FileChannel lockChannel;
    public final String lockFilename;

    public FileLock(String str) {
        this.lockFilename = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ".lck");
    }

    public final void lock() {
        String str = this.lockFilename;
        if (this.lockChannel != null) {
            return;
        }
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileChannel channel = new FileOutputStream(file).getChannel();
            this.lockChannel = channel;
            if (channel != null) {
                channel.lock();
            }
        } catch (Throwable th) {
            FileChannel fileChannel = this.lockChannel;
            if (fileChannel != null) {
                fileChannel.close();
            }
            this.lockChannel = null;
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unable to lock file: '", str, "'."), th);
        }
    }
}
