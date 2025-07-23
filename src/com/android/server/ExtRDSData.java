package com.android.server;

import android.util.Log;

/* loaded from: classes6.dex */
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

    public ExtRDSData(byte[] bArr) {
        this.blera = Byte.toUnsignedInt(bArr[1]) >> 6;
        byte b = bArr[1];
        this.blerb = (b >> 4) & 3;
        this.blerc = (b >> 2) & 3;
        this.blerd = b & 3;
        this.rssi = bArr[2];
        byte[] bArr2 = this.rdsa;
        bArr2[0] = bArr[4];
        bArr2[1] = bArr[3];
        byte[] bArr3 = this.rdsb;
        bArr3[0] = bArr[6];
        bArr3[1] = bArr[5];
        byte[] bArr4 = this.rdsc;
        bArr4[0] = bArr[8];
        bArr4[1] = bArr[7];
        byte[] bArr5 = this.rdsd;
        bArr5[0] = bArr[10];
        bArr5[1] = bArr[9];
    }

    private void logPackage() {
        Log("rdsa: " + ((char) this.rdsa[1]) + "," + ((char) this.rdsa[0]) + " -- blersa: " + this.blera);
        Log("rdsb: " + ((char) this.rdsb[1]) + "," + ((char) this.rdsb[0]) + " -- blersb: " + this.blerb);
        Log("rdsc: " + ((char) this.rdsc[1]) + "," + ((char) this.rdsc[0]) + " -- blersc: " + this.blerc);
        Log("rdsd: " + ((char) this.rdsd[1]) + "," + ((char) this.rdsd[0]) + " -- blersd: " + this.blerd);
    }

    private void Log(String str) {
        Log.d(TAG, str);
    }
}
