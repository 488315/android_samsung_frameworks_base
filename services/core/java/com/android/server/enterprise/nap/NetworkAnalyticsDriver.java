package com.android.server.enterprise.nap;

import android.os.AsyncTask;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class NetworkAnalyticsDriver {
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static int REATTEMPT_COMMAND_ACTIVATE = 1;
    public static int REATTEMPT_COMMAND_DEACTIVATE = 2;
    public static int REATTEMPT_COMMAND_FREQUENCY = 5;
    public static final String TAG = "NetworkAnalytics:NetworkAnalyticsDriver";
    public static NetworkAnalyticsDriver mInstance;
    public AtomicBoolean atomicBoolean;
    public NetworkAnalyticsDataDelivery dataDeliver;
    public NetworkAnalyticsConnectionManager mConnectionManager;
    public KernelDataFetch dataFetchThread = null;
    public Object syncObject = new Object();
    public boolean stateOfThread = false;
    public boolean stateOfFileDescriptor = false;
    public String ncmVersion = null;
    public Integer versionMismatchCheck = null;
    public boolean stateOfIntervalSet = false;
    public String test = "{ \"src\":\"10.10.12.12\", \"dst\":\"66.7.251.20\", \"sport\":\"5000\", \"dport\":\"443\", \"uid\":\"10197\", \"pid\":\"666\", \"bsent\":\"1400\", \"brecv\":\"4500\", \"hostname\":\"www.space.com\", \"protocol\":\"tcp\", \"hash\":\"a0627953\" }";

    public native int activate(int i);

    public void checkDataCollectionState() {
    }

    public native int checkNcmVersion();

    public native int closeDevice();

    public native int deactivate();

    public native int getNcmVersion();

    public native int openDevice(int i);

    public native String readDevice();

    public native int setIntervalValue(int i);

    public NetworkAnalyticsDriver(NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager, NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery) {
        this.atomicBoolean = null;
        this.dataDeliver = networkAnalyticsDataDelivery;
        this.mConnectionManager = networkAnalyticsConnectionManager;
        this.atomicBoolean = new AtomicBoolean(false);
    }

    public static NetworkAnalyticsDriver getInstance(NetworkAnalyticsConnectionManager networkAnalyticsConnectionManager, NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery) {
        if (mInstance == null) {
            mInstance = new NetworkAnalyticsDriver(networkAnalyticsConnectionManager, networkAnalyticsDataDelivery);
        }
        return mInstance;
    }

    public synchronized int openCharDevice(int i) {
        if (!this.stateOfFileDescriptor) {
            if (openDevice(i) < 0) {
                Log.i(TAG, "beginDataRecording: Opening of character device failed.");
                return -19;
            }
            setStateOfCharDevice(true);
        }
        if (this.ncmVersion == null) {
            int ncmVersion = getNcmVersion();
            this.ncmVersion = Integer.toString(ncmVersion);
            if (ncmVersion < 0) {
                this.ncmVersion = null;
                Log.i(TAG, "beginDataRecording: Get npa version failed. Char device in open state.");
                return -1;
            }
        }
        return 0;
    }

    public final void setStateOfCharDevice(boolean z) {
        this.stateOfFileDescriptor = z;
    }

    public synchronized int checkNcmVersionMismatch() {
        if (this.versionMismatchCheck == null) {
            if (checkNcmVersion() < 0) {
                Log.i(TAG, "beginDataRecording: Mismatch between kernel and userspace npa version.");
                this.versionMismatchCheck = -20;
                return -20;
            }
            this.versionMismatchCheck = 0;
        }
        return this.versionMismatchCheck.intValue();
    }

    public synchronized int setIntervalValueForFlow(int i) {
        if (this.stateOfIntervalSet || setIntervalValue(i) >= 0) {
            return 0;
        }
        Log.i(TAG, "beginDataRecording: set interval value failed. Char device in open state.");
        return -1;
    }

    public void beginDataRecording(int i) {
        if (this.dataFetchThread != null) {
            return;
        }
        if (activate(i) < 0) {
            Log.i(TAG, "beginDataRecording: Activation ioctl failed.");
            return;
        }
        this.stateOfIntervalSet = true;
        KernelDataFetch kernelDataFetch = new KernelDataFetch();
        this.dataFetchThread = kernelDataFetch;
        kernelDataFetch.execute(new Void[0]);
        setStateOfThread(true);
    }

    public void endDataRecording() {
        if (this.dataFetchThread != null && this.mConnectionManager.getActiveProfilesNumber() <= 0) {
            this.dataFetchThread.cancel(true);
            this.dataFetchThread = null;
            setStateOfThread(false);
            if (deactivate() < 0) {
                Log.i(TAG, "endDataRecording: Deactivation ioctl failed.");
                return;
            }
            this.stateOfIntervalSet = false;
            if (closeDevice() < 0) {
                Log.i(TAG, "endDataRecording: closing of character device failed.");
            } else {
                setStateOfCharDevice(false);
            }
        }
    }

    public final void setStateOfThread(boolean z) {
        this.atomicBoolean.set(z);
    }

    public class KernelDataFetch extends AsyncTask {
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        public KernelDataFetch() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            try {
                if (NetworkAnalyticsDriver.DBG) {
                    Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Starting Async task.");
                }
            } catch (InterruptedException unused) {
            } catch (Exception e) {
                Log.e(NetworkAnalyticsDriver.TAG, "doInBackground: Exception", e);
            }
            if (NetworkAnalyticsDriver.this.dataDeliver != null) {
                if (NetworkAnalyticsDriver.DBG) {
                    Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Initialzing handler thread from Async task.");
                }
                NetworkAnalyticsDriver.this.dataDeliver.initializeHandlerThread();
                while (NetworkAnalyticsDriver.this.atomicBoolean.get()) {
                    NetworkAnalyticsDriver.this.readDevice();
                    Thread.currentThread();
                    Thread.sleep(25L);
                }
                NetworkAnalyticsDriver.this.setStateOfThread(false);
                return null;
            }
            if (NetworkAnalyticsDriver.DBG) {
                Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Data Delivery object is null. Terminate.");
            }
            NetworkAnalyticsDriver.this.setStateOfThread(false);
            return null;
        }
    }

    public void jniSendingData(String str) {
        this.dataDeliver.accumulateData(str);
    }
}
