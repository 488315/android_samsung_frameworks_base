package com.samsung.android.hardware.secinputdev.device;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputCommandService;
import com.samsung.android.hardware.secinputdev.SemInputConstants;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.SemInputDumpsysData;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALFactory;
import com.samsung.android.hardware.secinputdev.hal.SysinputHALInterface;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SemInputDevice {
    private static final String STATIC_TAG = "SemInputDevice";
    protected final String TAG;
    protected final int devid;
    private final ExecutorService executorService;
    protected final String name;
    protected final int supportFeature;
    protected final SysinputHALInterface sysinputHAL;
    private static ExecutorService staticExecutorService = null;
    private static Lock lock = new ReentrantLock();
    private static Condition recovery = lock.newCondition();
    private static boolean onRecovery = false;
    protected final HashSet<String> cmdlistSet = new HashSet<>();
    private final List<String> taskQueue = new LinkedList();
    protected List<Runnable> pendingQueue = null;
    private boolean needPending = false;

    public SemInputDevice(String name, int devid, int feature, String cmdlist) {
        this.TAG = "SemInputDevice:" + name;
        this.name = name;
        this.devid = devid;
        this.supportFeature = feature;
        splitCommandList(cmdlist);
        this.sysinputHAL = SysinputHALFactory.connectHAL();
        if (Float.compare(this.sysinputHAL.getVersion(), 2.0f) >= 0) {
            this.executorService = Executors.newSingleThreadExecutor();
        } else {
            if (staticExecutorService == null) {
                staticExecutorService = Executors.newSingleThreadExecutor();
            }
            this.executorService = null;
        }
        Log.d(this.TAG, "create " + this);
    }

    public static void setRecoveryState(boolean onRecovery2) {
        Log.i(STATIC_TAG, "setRecoveryState: " + onRecovery2);
        onRecovery = onRecovery2;
        if (!onRecovery2) {
            lock.lock();
            try {
                recovery.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private void splitCommandList(String cmdlist) {
        if ("NG".equals(cmdlist)) {
            return;
        }
        String[] cmds = cmdlist.split(",");
        for (String cmd : cmds) {
            if (cmd.length() <= 0 || (cmd.charAt(0) >= 'a' && cmd.charAt(0) <= 'z')) {
                this.cmdlistSet.add(cmd);
            }
        }
    }

    public String toString() {
        String info = this.name + "(" + this.devid + "), cmd_list:" + this.cmdlistSet.size();
        if (this.supportFeature <= 0) {
            return info;
        }
        return info
                + ", support_feature:"
                + String.format("0x%X", Integer.valueOf(this.supportFeature));
    }

    public String getName() {
        return this.name;
    }

    public int getDevid() {
        return this.devid;
    }

    public int getSupportFeature() {
        return this.supportFeature;
    }

    public String getFormatName() {
        return String.format("%-8s", this.name);
    }

    public String getSupportCommands() {
        return "";
    }

    public String getExecutorInformation() {
        if (this.taskQueue.isEmpty()) {
            return "no pending commands";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("pending commands");
        synchronized (this.taskQueue) {
            for (String taskInfo : this.taskQueue) {
                builder.append("\n     " + taskInfo);
            }
        }
        return builder.toString();
    }

    protected void addTask(Runnable runnable) {
        synchronized (this.taskQueue) {
            this.taskQueue.add(runnable.toString());
        }
    }

    protected void removeTask(Runnable runnable) {
        synchronized (this.taskQueue) {
            this.taskQueue.remove(runnable.toString());
        }
    }

    protected void runOnThread(Runnable runnable, SemInputCommandService.Result result) {
        if (this.pendingQueue != null) {
            synchronized (this.pendingQueue) {
                if (this.needPending && runnable.toString().contains("SetProperty")) {
                    Log.e(this.TAG, "INCELL: skip " + runnable.toString());
                    result.set(2);
                    this.pendingQueue.add(runnable);
                    return;
                }
            }
        }
        addTask(runnable);
        if (this.executorService != null) {
            this.executorService.submit(runnable);
        } else if (staticExecutorService != null) {
            staticExecutorService.submit(runnable);
        }
    }

    protected void runAndWaitOnThread(Runnable runnable, SemInputCommandService.Result result) {
        try {
            addTask(runnable);
            if (this.executorService != null) {
                this.executorService.submit(runnable).get();
            } else if (staticExecutorService != null) {
                staticExecutorService.submit(runnable).get();
            }
        } catch (Exception e) {
            SemInputDeviceManagerService.loggingException(this.TAG, "runAndWaitOnThread", e);
            result.set(-7);
            removeTask(runnable);
        }
    }

    protected void waitUntilRecovery() {
        lock.lock();
        while (onRecovery) {
            try {
                try {
                    recovery.await();
                    Log.i(this.TAG, "recovery done");
                } catch (InterruptedException e) {
                    Log.d(this.TAG, "waitUntilRecovery: interrupted");
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public int setProperty(
            SemInputConstants.Command command, String mode, SemInputCommandService.Result result) {
        return -5;
    }

    protected boolean supportSetProperty() {
        return false;
    }

    public final int setProperty(SemInputConstants.Property property, String mode) {
        if (!supportSetProperty()) {
            return -5;
        }
        SemInputCommandService.Result result = new SemInputCommandService.Result();
        runOnThread(new SetPropertyTask(property, mode, result), result);
        return 1;
    }

    private final class SetPropertyTask implements Runnable {
        final String command;
        final SemInputConstants.Property property;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public SetPropertyTask(
                SemInputConstants.Property property,
                String mode,
                SemInputCommandService.Result result) {
            this.property = property;
            this.command = mode;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemInputDevice.this.waitUntilRecovery();
            Log.d(
                    SemInputDevice.this.TAG,
                    "SetPropertyTask: "
                            + this.property
                            + ","
                            + this.command
                            + " ["
                            + this.time
                            + "]");
            this.result.set(
                    SemInputDevice.this.sysinputHAL.setProperty(
                            SemInputDevice.this.devid, this.property, this.command));
            SemInputDevice.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] SetPropertyTask: " + this.property + "," + this.command;
        }
    }

    protected boolean supportGetProperty() {
        return false;
    }

    public final String getProperty(SemInputConstants.Property property) {
        if (!supportGetProperty()) {
            return SemInputDeviceManager.RESULT_STR_NA;
        }
        SemInputCommandService.Result result = new SemInputCommandService.Result();
        runAndWaitOnThread(new GetPropertyTask(property, result), result);
        return result.getString();
    }

    private final class GetPropertyTask implements Runnable {
        final SemInputConstants.Property property;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public GetPropertyTask(
                SemInputConstants.Property property, SemInputCommandService.Result result) {
            this.property = property;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemInputDevice.this.waitUntilRecovery();
            Log.d(
                    SemInputDevice.this.TAG,
                    "GetPropertyTask: " + this.property + " [" + this.time + "]");
            this.result.set(
                    SemInputDevice.this.sysinputHAL.getProperty(
                            SemInputDevice.this.devid, this.property));
            SemInputDevice.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] GetPropertyTask: " + this.property;
        }
    }

    protected boolean supportRunCommand() {
        return false;
    }

    public final String runCommand(String cmd) {
        if (!supportRunCommand()) {
            return SemInputDeviceManager.RESULT_STR_NA;
        }
        String commandName = cmd.split(",")[0];
        if (this.cmdlistSet.contains(commandName)) {
            SemInputCommandService.Result result = new SemInputCommandService.Result();
            runAndWaitOnThread(new RunCommandTask(cmd, result), result);
            return result.getString();
        }
        Log.d(this.TAG, "runCommand: not support cmd \"" + commandName + "\"");
        return SemInputDeviceManager.RESULT_STR_NA;
    }

    private final class RunCommandTask implements Runnable {
        final String command;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public RunCommandTask(String command, SemInputCommandService.Result result) {
            this.command = command;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemInputDevice.this.waitUntilRecovery();
            Log.d(
                    SemInputDevice.this.TAG,
                    "RunCommandTask: " + this.command + " [" + this.time + "]");
            this.result.set(
                    SemInputDevice.this.sysinputHAL.runCommand(
                            SemInputDevice.this.devid, this.command));
            SemInputDevice.this.removeTask(this);
        }

        public String toString() {
            return "[" + this.time + "] RunCommandTask: " + this.command;
        }
    }

    protected boolean supportActivate() {
        return false;
    }

    public final int activate(int enable, boolean isEarly, SemInputCommandService.Result result) {
        if (!supportActivate()) {
            return -5;
        }
        if (this.pendingQueue != null && ((enable == 1 || enable == 2) && isEarly)) {
            this.needPending = true;
            Log.i(this.TAG, "INCELL: pending start");
        }
        runOnThread(new ActivateTask(enable, isEarly, result), result);
        if (this.pendingQueue != null && this.needPending && !isEarly) {
            synchronized (this.pendingQueue) {
                this.needPending = false;
                Log.i(this.TAG, "INCELL: pending end");
                for (Runnable runnable : this.pendingQueue) {
                    Log.i(this.TAG, "INCELL: add " + runnable.toString());
                    runOnThread(runnable, new SemInputCommandService.Result());
                }
                this.pendingQueue.clear();
            }
        }
        return 1;
    }

    private final class ActivateTask implements Runnable {
        final int enable;
        final boolean isEarly;
        final SemInputCommandService.Result result;
        final String time = SemInputDumpsysData.getCurrentTimeString();

        public ActivateTask(int enable, boolean isEarly, SemInputCommandService.Result result) {
            this.enable = enable;
            this.isEarly = isEarly;
            this.result = result;
        }

        @Override // java.lang.Runnable
        public void run() {
            SemInputDevice.this.waitUntilRecovery();
            Log.d(
                    SemInputDevice.this.TAG,
                    "ActivateTask: "
                            + this.enable
                            + (this.isEarly ? ",0" : ",1")
                            + " ["
                            + this.time
                            + "]");
            this.result.set(
                    SemInputDevice.this.sysinputHAL.activate(
                            SemInputDevice.this.devid, this.enable, this.isEarly));
            SemInputDevice.this.removeTask(this);
        }

        public String toString() {
            return "["
                    + this.time
                    + "] ActivateTask: "
                    + this.enable
                    + (this.isEarly ? ",0" : ",1");
        }
    }
}
