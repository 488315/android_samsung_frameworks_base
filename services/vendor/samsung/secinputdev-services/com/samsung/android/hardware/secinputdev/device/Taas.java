package com.samsung.android.hardware.secinputdev.device;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputCommandService;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;

/* loaded from: classes.dex */
public class Taas extends SemInputDevice {
    public Taas(String name, int devid) {
        super(name, devid, 0, "NG");
    }

    @Override // com.samsung.android.hardware.secinputdev.device.SemInputDevice
    public String toString() {
        return this.name + "(" + this.devid + ")";
    }

    public String read() {
        SemInputCommandService.Result result = new SemInputCommandService.Result();
        runAndWaitOnThread(new ReadTask(result), result);
        return result.getString();
    }

    private final class ReadTask implements Runnable {
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public ReadTask(SemInputCommandService.Result result) {
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            Taas.this.waitUntilRecovery();
            Log.d(Taas.this.TAG, "ReadTask: [" + this.time + "]");
            this.result.set(Taas.this.sysinputHAL.readTaas());
            Taas.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] ReadTask";
        }
    }

    public int write(String word) {
        SemInputCommandService.Result result = new SemInputCommandService.Result();
        runAndWaitOnThread(new WriteTask(word, result), result);
        return result.getInteger();
    }

    private final class WriteTask implements Runnable {
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();
        final String word;

        public WriteTask(String word, SemInputCommandService.Result result) {
            this.word = word;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            Taas.this.waitUntilRecovery();
            Log.d(Taas.this.TAG, "WriteTask: [" + this.time + "]");
            this.result.set(Taas.this.sysinputHAL.writeTaas(this.word));
            Taas.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] WriteTask";
        }
    }
}
