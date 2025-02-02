package com.android.server.locksettings.recoverablekeystore.storage;

import android.os.Environment;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.util.Log;
import android.util.SparseArray;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotDeserializer;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotParserException;
import com.android.server.locksettings.recoverablekeystore.serialization.KeyChainSnapshotSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.Locale;

/* loaded from: classes2.dex */
public class RecoverySnapshotStorage {
  public final SparseArray mSnapshotByUid = new SparseArray();
  public final File rootDirectory;

  public static RecoverySnapshotStorage newInstance() {
    return new RecoverySnapshotStorage(new File(Environment.getDataDirectory(), "system"));
  }

  public RecoverySnapshotStorage(File file) {
    this.rootDirectory = file;
  }

  public synchronized void put(int i, KeyChainSnapshot keyChainSnapshot) {
    this.mSnapshotByUid.put(i, keyChainSnapshot);
    try {
      writeToDisk(i, keyChainSnapshot);
    } catch (IOException | CertificateEncodingException e) {
      Log.e(
          "RecoverySnapshotStorage",
          String.format(Locale.US, "Error persisting snapshot for %d to disk", Integer.valueOf(i)),
          e);
    }
  }

  public synchronized KeyChainSnapshot get(int i) {
    KeyChainSnapshot keyChainSnapshot = (KeyChainSnapshot) this.mSnapshotByUid.get(i);
    if (keyChainSnapshot != null) {
      return keyChainSnapshot;
    }
    try {
      return readFromDisk(i);
    } catch (KeyChainSnapshotParserException | IOException e) {
      Log.e(
          "RecoverySnapshotStorage",
          String.format(Locale.US, "Error reading snapshot for %d from disk", Integer.valueOf(i)),
          e);
      return null;
    }
  }

  public synchronized void remove(int i) {
    this.mSnapshotByUid.remove(i);
    getSnapshotFile(i).delete();
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

  public final KeyChainSnapshot readFromDisk(int i) {
    File snapshotFile = getSnapshotFile(i);
    try {
      FileInputStream fileInputStream = new FileInputStream(snapshotFile);
      try {
        KeyChainSnapshot deserialize = KeyChainSnapshotDeserializer.deserialize(fileInputStream);
        fileInputStream.close();
        return deserialize;
      } catch (Throwable th) {
        try {
          fileInputStream.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (KeyChainSnapshotParserException | IOException e) {
      snapshotFile.delete();
      throw e;
    }
  }

  public final File getSnapshotFile(int i) {
    return new File(getStorageFolder(), getSnapshotFileName(i));
  }

  public final String getSnapshotFileName(int i) {
    return String.format(Locale.US, "%d.xml", Integer.valueOf(i));
  }

  public final File getStorageFolder() {
    File file = new File(this.rootDirectory, "recoverablekeystore/snapshots/");
    file.mkdirs();
    return file;
  }
}
