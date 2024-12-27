package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.util.AtomicFile;
import android.util.Slog;

import com.android.internal.util.Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EntropyMixer extends Binder {
    static final String DEVICE_SPECIFIC_INFO_HEADER =
            "Copyright (C) 2009 The Android Open Source Project\n"
                + "All Your Randomness Are Belong To Us\n";
    static final int SEED_FILE_SIZE = 512;
    public final AnonymousClass2 mBroadcastReceiver;
    public final AnonymousClass1 mHandler;
    public final File randomReadDevice;
    public final File randomWriteDevice;
    public final AtomicFile seedFile;
    public static final long START_TIME = System.currentTimeMillis();
    public static final long START_NANOTIME = System.nanoTime();

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EntropyMixer(android.content.Context r5) {
        /*
            r4 = this;
            java.io.File r0 = new java.io.File
            java.io.File r1 = android.os.Environment.getDataDirectory()
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "system"
            r2.<init>(r1, r3)
            r2.mkdirs()
            java.lang.String r1 = "entropy.dat"
            r0.<init>(r2, r1)
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "/dev/urandom"
            r1.<init>(r2)
            java.io.File r3 = new java.io.File
            r3.<init>(r2)
            r4.<init>(r5, r0, r1, r3)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.EntropyMixer.<init>(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.EntropyMixer$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.EntropyMixer$2] */
    public EntropyMixer(Context context, File file, File file2, File file3) {
        this.mHandler =
                new Handler(
                        IoThread.getHandler()
                                .getLooper()) { // from class: com.android.server.EntropyMixer.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1) {
                            Slog.e("EntropyMixer", "Will not process invalid message");
                            return;
                        }
                        EntropyMixer entropyMixer = EntropyMixer.this;
                        entropyMixer.updateSeedFile();
                        entropyMixer.mHandler.removeMessages(1);
                        entropyMixer.mHandler.sendEmptyMessageDelayed(1, 10800000L);
                    }
                };
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.EntropyMixer.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        EntropyMixer.this.updateSeedFile();
                    }
                };
        this.seedFile = new AtomicFile((File) Preconditions.checkNotNull(file));
        this.randomReadDevice = (File) Preconditions.checkNotNull(file2);
        File file4 = (File) Preconditions.checkNotNull(file3);
        this.randomWriteDevice = file4;
        byte[] readSeedFile = readSeedFile();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file4);
            try {
                if (readSeedFile.length != 0) {
                    fileOutputStream.write(readSeedFile);
                    Slog.i("EntropyMixer", "Loaded existing seed file");
                }
                fileOutputStream.write(getDeviceSpecificInformation());
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("EntropyMixer", "Error writing to " + this.randomWriteDevice, e);
        }
        updateSeedFile();
        removeMessages(1);
        sendEmptyMessageDelayed(1, 10800000L);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.REBOOT");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, null, this.mHandler);
    }

    public static byte[] getDeviceSpecificInformation() {
        return (DEVICE_SPECIFIC_INFO_HEADER
                        + START_TIME
                        + '\n'
                        + START_NANOTIME
                        + '\n'
                        + SystemProperties.get("ro.serialno")
                        + '\n'
                        + SystemProperties.get("ro.bootmode")
                        + '\n'
                        + SystemProperties.get("ro.baseband")
                        + '\n'
                        + SystemProperties.get("ro.carrier")
                        + '\n'
                        + SystemProperties.get("ro.bootloader")
                        + '\n'
                        + SystemProperties.get("ro.hardware")
                        + '\n'
                        + SystemProperties.get("ro.revision")
                        + '\n'
                        + SystemProperties.get("ro.build.fingerprint")
                        + '\n'
                        + new Object().hashCode()
                        + '\n'
                        + System.currentTimeMillis()
                        + '\n'
                        + System.nanoTime()
                        + '\n')
                .getBytes();
    }

    public final byte[] readSeedFile() {
        try {
            return this.seedFile.readFully();
        } catch (FileNotFoundException unused) {
            return new byte[0];
        } catch (IOException e) {
            Slog.e("EntropyMixer", "Error reading " + this.seedFile.getBaseFile(), e);
            return new byte[0];
        }
    }

    public final void updateSeedFile() {
        FileInputStream fileInputStream;
        byte[] readSeedFile = readSeedFile();
        byte[] bArr = new byte[512];
        try {
            fileInputStream = new FileInputStream(this.randomReadDevice);
        } catch (IOException e) {
            Slog.e(
                    "EntropyMixer",
                    "Error reading "
                            + this.randomReadDevice
                            + "; seed file won't be properly updated",
                    e);
        }
        try {
            if (fileInputStream.read(bArr) != 512) {
                throw new IOException("unexpected EOF");
            }
            fileInputStream.close();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update("Android EntropyMixer v1".getBytes());
                long currentTimeMillis = System.currentTimeMillis();
                ByteBuffer allocate = ByteBuffer.allocate(8);
                allocate.putLong(currentTimeMillis);
                messageDigest.update(allocate.array());
                long nanoTime = System.nanoTime();
                ByteBuffer allocate2 = ByteBuffer.allocate(8);
                allocate2.putLong(nanoTime);
                messageDigest.update(allocate2.array());
                long length = readSeedFile.length;
                ByteBuffer allocate3 = ByteBuffer.allocate(8);
                allocate3.putLong(length);
                messageDigest.update(allocate3.array());
                messageDigest.update(readSeedFile);
                ByteBuffer allocate4 = ByteBuffer.allocate(8);
                allocate4.putLong(512);
                messageDigest.update(allocate4.array());
                messageDigest.update(bArr);
                byte[] digest = messageDigest.digest();
                System.arraycopy(digest, 0, bArr, 512 - digest.length, digest.length);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = this.seedFile.startWrite();
                    fileOutputStream.write(bArr);
                    this.seedFile.finishWrite(fileOutputStream);
                } catch (IOException e2) {
                    Slog.e("EntropyMixer", "Error writing " + this.seedFile.getBaseFile(), e2);
                    this.seedFile.failWrite(fileOutputStream);
                }
                if (readSeedFile.length == 0) {
                    Slog.i("EntropyMixer", "Created seed file");
                } else {
                    Slog.i("EntropyMixer", "Updated seed file");
                }
            } catch (NoSuchAlgorithmException e3) {
                Slog.wtf(
                        "EntropyMixer",
                        "SHA-256 algorithm not found; seed file won't be updated",
                        e3);
            }
        } finally {
        }
    }
}
