package androidx.datastore.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/* loaded from: classes.dex */
public abstract class FileMoves_androidKt {
    public static final boolean atomicMoveTo(File file, File file2) throws IOException {
        Api26Impl.INSTANCE.getClass();
        try {
            Files.move(file.toPath(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }
}
