package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FileAlreadyExistsException extends FileSystemException {
    public /* synthetic */ FileAlreadyExistsException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : file2, (i & 4) != 0 ? null : str);
    }

    public FileAlreadyExistsException(File file, File file2, String str) {
        super(file, file2, str);
    }
}
