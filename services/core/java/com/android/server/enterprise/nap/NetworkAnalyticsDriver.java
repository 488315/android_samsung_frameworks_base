package com.android.server.enterprise.nap;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import com.android.server.enterprise.nap.NetworkAnalyticsDataDelivery;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KernelDataFetch extends AsyncTask {
        public KernelDataFetch() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            boolean z;
            try {
                z = NetworkAnalyticsDriver.DBG;
                if (z) {
                    Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Starting Async task.");
                }
            } catch (InterruptedException unused) {
            } catch (Exception e) {
                Log.e(NetworkAnalyticsDriver.TAG, "doInBackground: Exception", e);
            }
            if (NetworkAnalyticsDriver.this.dataDeliver == null) {
                if (z) {
                    Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Data Delivery object is null. Terminate.");
                }
                NetworkAnalyticsDriver.this.setStateOfThread(false);
                return null;
            }
            if (z) {
                Log.d(NetworkAnalyticsDriver.TAG, "_deliverDataToRecipients: Initialzing handler thread from Async task.");
            }
            NetworkAnalyticsDriver.this.dataDeliver.initializeHandlerThread();
            while (NetworkAnalyticsDriver.this.atomicBoolean.get()) {
                NetworkAnalyticsDriver.this.readDevice();
                Thread.currentThread();
                Thread.sleep(25L);
            }
            NetworkAnalyticsDriver networkAnalyticsDriver = NetworkAnalyticsDriver.this;
            String str = NetworkAnalyticsDriver.TAG;
            networkAnalyticsDriver.setStateOfThread(false);
            return null;
        }

        @Override // android.os.AsyncTask
        public final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
        }
    }

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

    public native int activate(int i);

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

    public void checkDataCollectionState() {
    }

    public native int checkNcmVersion();

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

    public native int closeDevice();

    public native int deactivate();

    public void endDataRecording() {
        KernelDataFetch kernelDataFetch = this.dataFetchThread;
        if (kernelDataFetch != null && this.mConnectionManager.activatedProfileCounter <= 0) {
            kernelDataFetch.cancel(true);
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

    public native int getNcmVersion();

    public void jniSendingData(String str) {
        NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery = this.dataDeliver;
        NetworkAnalyticsDataDelivery.DataDeliveryHandler dataDeliveryHandler = networkAnalyticsDataDelivery.mHandler;
        if (dataDeliveryHandler != null) {
            networkAnalyticsDataDelivery.mHandler.sendMessage(Message.obtain(dataDeliveryHandler, 1, 0, 0, str));
        }
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

    public native int openDevice(int i);

    public native String readDevice();

    public native int setIntervalValue(int i);

    public synchronized int setIntervalValueForFlow(int i) {
        if (this.stateOfIntervalSet || setIntervalValue(i) >= 0) {
            return 0;
        }
        Log.i(TAG, "beginDataRecording: set interval value failed. Char device in open state.");
        return -1;
    }

    public final void setStateOfCharDevice(boolean z) {
        this.stateOfFileDescriptor = z;
    }

    public final void setStateOfThread(boolean z) {
        this.atomicBoolean.set(z);
    }
}
