package com.android.internal.util;

import java.io.File;
import java.io.IOException;

@Deprecated
/* loaded from: classes4.dex */
public class JournaledFile {
    File mReal;
    File mTemp;
    boolean mWriting;

    public JournaledFile(File file, File file2) {
        this.mReal = file;
        this.mTemp = file2;
    }

    public File chooseForRead() {
        if (this.mReal.exists()) {
            File file = this.mReal;
            if (this.mTemp.exists()) {
                this.mTemp.delete();
            }
            return file;
        }
        if (this.mTemp.exists()) {
            File file2 = this.mTemp;
            file2.renameTo(this.mReal);
            return file2;
        }
        return this.mReal;
    }

    public File chooseForWrite() throws IOException {
        if (this.mWriting) {
            throw new IllegalStateException("uncommitted write already in progress");
        }
        if (!this.mReal.exists()) {
            try {
                this.mReal.createNewFile();
            } catch (IOException unused) {
            }
        }
        if (this.mTemp.exists()) {
            this.mTemp.delete();
        }
        this.mWriting = true;
        return this.mTemp;
    }

    public void commit() {
        if (!this.mWriting) {
            throw new IllegalStateException("no file to commit");
        }
        this.mWriting = false;
        this.mTemp.renameTo(this.mReal);
    }

    public void rollback() {
        if (!this.mWriting) {
            throw new IllegalStateException("no file to roll back");
        }
        this.mWriting = false;
        this.mTemp.delete();
    }
}
