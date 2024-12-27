package com.samsung.android.hardware.secinputdev;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class SemInputDumpsysData {
    private static final String TAG = "SemInputDumpsysData";
    private final int MAX_QUEUE_SIZE;
    private Queue<String> dataQueue = new LinkedList();
    private DumpsysData currentData = null;

    public SemInputDumpsysData(int maxQueueSize) {
        this.MAX_QUEUE_SIZE = maxQueueSize;
        StackTraceElement[] stack = new Throwable().getStackTrace();
        if (stack.length >= 2) {
            Log.d(
                    TAG,
                    "MAX_QUEUE_SIZE: "
                            + this.MAX_QUEUE_SIZE
                            + " from "
                            + stack[1].getClassName()
                            + " at line"
                            + stack[1].getLineNumber());
        }
    }

    private class DumpsysData {
        private final StringBuilder data = new StringBuilder();
        private final String startTime = SemInputDumpsysData.getCurrentTimeString();

        public DumpsysData() {}

        public String toString() {
            return "[" + this.startTime + "] " + this.data.toString();
        }
    }

    public int getMaxQueueSize() {
        return this.MAX_QUEUE_SIZE;
    }

    public Queue<String> getQueue() {
        Queue<String> queue = new LinkedList<>();
        synchronized (this.dataQueue) {
            queue.addAll(this.dataQueue);
        }
        return queue;
    }

    private void addQueue(DumpsysData data) {
        synchronized (this.dataQueue) {
            if (this.dataQueue.size() >= this.MAX_QUEUE_SIZE) {
                this.dataQueue.poll();
            }
            this.dataQueue.add(data.toString());
        }
    }

    public void createDataAndAddQueue(String msg) {
        DumpsysData dumpsysData = new DumpsysData();
        dumpsysData.data.append(msg);
        addQueue(dumpsysData);
    }

    public void createData() {
        this.currentData = new DumpsysData();
    }

    public void setDataAndAddQueue(String msg) {
        if (this.currentData != null) {
            this.currentData.data.append(msg);
            addQueue(this.currentData);
            this.currentData = null;
            return;
        }
        Log.w(TAG, "setDataAndAddQueue: Data was not created");
    }

    public static String getCurrentTimeString() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return format.format(currentTime);
    }
}
