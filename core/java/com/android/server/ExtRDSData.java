package com.android.server;

import android.util.Log;

/* loaded from: classes5.dex */
public class ExtRDSData {
  private static final String TAG = "ExtRDSData";
  int blera;
  int blerb;
  int blerc;
  int blerd;
  byte[] rdsa = new byte[2];
  byte[] rdsb = new byte[2];
  byte[] rdsc = new byte[2];
  byte[] rdsd = new byte[2];
  private int rssi;

  public ExtRDSData(byte[] raw_data) {
    this.blera = Byte.toUnsignedInt(raw_data[1]) >> 6;
    this.blerb = (raw_data[1] >> 4) & 3;
    this.blerc = (raw_data[1] >> 2) & 3;
    this.blerd = raw_data[1] & 3;
    this.rssi = raw_data[2];
    byte[] bArr = this.rdsa;
    bArr[0] = raw_data[4];
    bArr[1] = raw_data[3];
    byte[] bArr2 = this.rdsb;
    bArr2[0] = raw_data[6];
    bArr2[1] = raw_data[5];
    byte[] bArr3 = this.rdsc;
    bArr3[0] = raw_data[8];
    bArr3[1] = raw_data[7];
    byte[] bArr4 = this.rdsd;
    bArr4[0] = raw_data[10];
    bArr4[1] = raw_data[9];
  }

  private void logPackage() {
    Log(
        "rdsa: "
            + ((char) this.rdsa[1])
            + ","
            + ((char) this.rdsa[0])
            + " -- blersa: "
            + this.blera);
    Log(
        "rdsb: "
            + ((char) this.rdsb[1])
            + ","
            + ((char) this.rdsb[0])
            + " -- blersb: "
            + this.blerb);
    Log(
        "rdsc: "
            + ((char) this.rdsc[1])
            + ","
            + ((char) this.rdsc[0])
            + " -- blersc: "
            + this.blerc);
    Log(
        "rdsd: "
            + ((char) this.rdsd[1])
            + ","
            + ((char) this.rdsd[0])
            + " -- blersd: "
            + this.blerd);
  }

  private void Log(String s) {
    Log.m94d(TAG, s);
  }
}
