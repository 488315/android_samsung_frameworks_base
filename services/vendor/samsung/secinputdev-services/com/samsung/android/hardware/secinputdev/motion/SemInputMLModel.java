package com.samsung.android.hardware.secinputdev.motion;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class SemInputMLModel {
    private static final String TAG = "SemInputMLModel";
    private FileChannel channel;
    private final String pathSDCARD;
    private final String pathSYSTEM;
    private MappedByteBuffer mappedByteBuffer = null;
    private final StringBuilder log = new StringBuilder();

    public SemInputMLModel(String fileName) {
        this.pathSDCARD = "/sdcard/etf/" + fileName;
        this.pathSYSTEM = "/system/etc/" + fileName;
    }

    public void destroy() {
        try {
            if (this.channel != null) {
                this.channel.close();
            }
        } catch (Exception e) {
            Log.e(TAG, this.log.toString());
        }
    }

    private void prepare() {
        StringBuilder sb;
        StringBuilder sb2;
        String modelFilePath = this.pathSYSTEM;
        File fileSdcard = new File(this.pathSDCARD);
        if (fileSdcard.exists()) {
            modelFilePath = this.pathSDCARD;
        }
        try {
            FileInputStream inputStream = new FileInputStream(modelFilePath);
            this.log.append(modelFilePath + ": Loaded\n");
            Log.d(TAG, this.log.toString());
            try {
                try {
                    FileChannel fileChannel = inputStream.getChannel();
                    try {
                        this.mappedByteBuffer =
                                fileChannel.map(
                                        FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
                        this.channel = fileChannel;
                        if (fileChannel != null) {
                            fileChannel.close();
                        }
                        try {
                            inputStream.close();
                        } catch (Exception e) {
                            e = e;
                            sb = this.log;
                            sb2 = new StringBuilder();
                            sb.append(
                                    sb2.append(modelFilePath)
                                            .append(": close failed. ")
                                            .append(e)
                                            .append("\n")
                                            .toString());
                            Log.e(TAG, this.log.toString());
                        }
                    } catch (Throwable th) {
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    this.mappedByteBuffer = null;
                    this.log.append(modelFilePath + ": map failed. " + e2 + "\n");
                    Log.e(TAG, this.log.toString());
                    try {
                        inputStream.close();
                    } catch (Exception e3) {
                        e = e3;
                        sb = this.log;
                        sb2 = new StringBuilder();
                        sb.append(
                                sb2.append(modelFilePath)
                                        .append(": close failed. ")
                                        .append(e)
                                        .append("\n")
                                        .toString());
                        Log.e(TAG, this.log.toString());
                    }
                }
            } catch (Throwable th3) {
                try {
                    inputStream.close();
                } catch (Exception e4) {
                    this.log.append(modelFilePath + ": close failed. " + e4 + "\n");
                    Log.e(TAG, this.log.toString());
                }
                throw th3;
            }
        } catch (Exception e5) {
            this.log.append(modelFilePath + ": " + e5.getMessage() + "\n");
            Log.e(TAG, this.log.toString());
            this.mappedByteBuffer = null;
        }
    }

    public synchronized MappedByteBuffer getBuffer() {
        if (this.mappedByteBuffer == null) {
            prepare();
        }
        return this.mappedByteBuffer;
    }

    public String getLog() {
        return this.log.toString();
    }

    public File getFile() {
        File fileSdcard = new File(this.pathSDCARD);
        if (fileSdcard.exists()) {
            return fileSdcard;
        }
        File fileSystem = new File(this.pathSYSTEM);
        if (fileSystem.exists()) {
            return fileSystem;
        }
        return null;
    }
}
