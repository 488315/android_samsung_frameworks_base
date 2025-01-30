package com.android.server.backup.utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public final class DataStreamFileCodec {
  public final DataStreamCodec mCodec;
  public final File mFile;

  public DataStreamFileCodec(File file, DataStreamCodec dataStreamCodec) {
    this.mFile = file;
    this.mCodec = dataStreamCodec;
  }

  public Object deserialize() {
    FileInputStream fileInputStream = new FileInputStream(this.mFile);
    try {
      DataInputStream dataInputStream = new DataInputStream(fileInputStream);
      try {
        Object deserialize = this.mCodec.deserialize(dataInputStream);
        dataInputStream.close();
        fileInputStream.close();
        return deserialize;
      } finally {
      }
    } catch (Throwable th) {
      try {
        fileInputStream.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  public void serialize(Object obj) {
    FileOutputStream fileOutputStream = new FileOutputStream(this.mFile);
    try {
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      try {
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
        try {
          this.mCodec.serialize(obj, dataOutputStream);
          dataOutputStream.flush();
          dataOutputStream.close();
          bufferedOutputStream.close();
          fileOutputStream.close();
        } finally {
        }
      } finally {
      }
    } catch (Throwable th) {
      try {
        fileOutputStream.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }
}
