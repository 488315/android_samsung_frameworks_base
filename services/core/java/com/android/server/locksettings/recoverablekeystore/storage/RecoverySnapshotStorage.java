package com.android.server.locksettings.recoverablekeystore.storage;

import android.security.keystore.recovery.KeyChainSnapshot;
import android.util.Log;
import android.util.SparseArray;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotDeserializer;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RecoverySnapshotStorage {
    public final SparseArray mSnapshotByUid = new SparseArray();
    public final File rootDirectory;

    public RecoverySnapshotStorage(File file) {
        this.rootDirectory = file;
    }

    public final synchronized KeyChainSnapshot get(int i) {
        KeyChainSnapshot keyChainSnapshot = (KeyChainSnapshot) this.mSnapshotByUid.get(i);
        if (keyChainSnapshot != null) {
            return keyChainSnapshot;
        }
        try {
            return readFromDisk(i);
        } catch (KeyChainSnapshotParserException | IOException e) {
            Locale locale = Locale.US;
            Log.e("RecoverySnapshotStorage", "Error reading snapshot for " + i + " from disk", e);
            return null;
        }
    }

    public final File getSnapshotFile(int i) {
        File file = new File(this.rootDirectory, "recoverablekeystore/snapshots/");
        file.mkdirs();
        Locale locale = Locale.US;
        return new File(file, NandswapManager$$ExternalSyntheticOutline0.m(i, ".xml"));
    }

    public final KeyChainSnapshot readFromDisk(int i) {
        File snapshotFile = getSnapshotFile(i);
        try {
            FileInputStream fileInputStream = new FileInputStream(snapshotFile);
            try {
                try {
                    KeyChainSnapshot deserializeInternal =
                            KeyChainSnapshotDeserializer.deserializeInternal(fileInputStream);
                    fileInputStream.close();
                    return deserializeInternal;
                } catch (XmlPullParserException e) {
                    throw new KeyChainSnapshotParserException("Malformed KeyChainSnapshot XML", e);
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (KeyChainSnapshotParserException | IOException e2) {
            snapshotFile.delete();
            throw e2;
        }
    }

    public final void writeToDisk(int i, KeyChainSnapshot keyChainSnapshot) {
        File snapshotFile = getSnapshotFile(i);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(snapshotFile);
            try {
                KeyChainSnapshotSerializer.serialize(keyChainSnapshot, fileOutputStream);
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | CertificateEncodingException e) {
            snapshotFile.delete();
            throw e;
        }
    }
}
