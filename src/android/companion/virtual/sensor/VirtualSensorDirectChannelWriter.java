package android.companion.virtual.sensor;

import android.annotation.SystemApi;
import android.os.SharedMemory;
import android.system.ErrnoException;
import android.util.Log;
import android.util.SparseArray;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorDirectChannelWriter implements AutoCloseable {
    private static final String TAG = "VirtualSensorWriter";
    private static final long UINT32_MAX = 4294967295L;
    private final SparseArray<SharedMemoryWrapper> mChannels = new SparseArray<>();
    private final Object mChannelsLock = new Object();
    private final SparseArray<SparseArray<DirectChannelConfiguration>> mConfiguredChannels = new SparseArray<>();

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mChannelsLock) {
            for (int i = 0; i < this.mChannels.size(); i++) {
                this.mChannels.valueAt(i).close();
            }
            this.mChannels.clear();
            this.mConfiguredChannels.clear();
        }
    }

    public void addChannel(int i, SharedMemory sharedMemory) throws ErrnoException {
        synchronized (this.mChannelsLock) {
            if (this.mChannels.contains(i)) {
                Log.w(TAG, "Channel with handle " + i + " already added.");
            } else {
                this.mChannels.put(i, new SharedMemoryWrapper((SharedMemory) Objects.requireNonNull(sharedMemory)));
            }
        }
    }

    public void removeChannel(int i) {
        synchronized (this.mChannelsLock) {
            SharedMemoryWrapper removeReturnOld = this.mChannels.removeReturnOld(i);
            if (removeReturnOld != null) {
                removeReturnOld.close();
            }
            for (int i2 = 0; i2 < this.mConfiguredChannels.size(); i2++) {
                this.mConfiguredChannels.valueAt(i2).remove(i);
            }
        }
    }

    public boolean configureChannel(int i, VirtualSensor virtualSensor, int i2, int i3) {
        synchronized (this.mChannelsLock) {
            SparseArray<DirectChannelConfiguration> sparseArray = this.mConfiguredChannels.get(((VirtualSensor) Objects.requireNonNull(virtualSensor)).getHandle());
            if (i2 == 0) {
                if (sparseArray != null && sparseArray.removeReturnOld(i) != null) {
                    return true;
                }
                Log.w(TAG, "Channel configuration failed - channel with handle " + i + " not found");
                return false;
            }
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                this.mConfiguredChannels.put(virtualSensor.getHandle(), sparseArray);
            }
            SharedMemoryWrapper sharedMemoryWrapper = this.mChannels.get(i);
            if (sharedMemoryWrapper == null) {
                Log.w(TAG, "Channel configuration failed - channel with handle " + i + " not found");
                return false;
            }
            sparseArray.put(i, new DirectChannelConfiguration(i3, virtualSensor.getType(), sharedMemoryWrapper));
            return true;
        }
    }

    public boolean writeSensorEvent(VirtualSensor virtualSensor, VirtualSensorEvent virtualSensorEvent) {
        Objects.requireNonNull(virtualSensorEvent);
        synchronized (this.mChannelsLock) {
            SparseArray<DirectChannelConfiguration> sparseArray = this.mConfiguredChannels.get(((VirtualSensor) Objects.requireNonNull(virtualSensor)).getHandle());
            if (sparseArray != null && sparseArray.size() != 0) {
                for (int i = 0; i < sparseArray.size(); i++) {
                    sparseArray.valueAt(i).write((VirtualSensorEvent) Objects.requireNonNull(virtualSensorEvent));
                }
                return true;
            }
            Log.w(TAG, "Sensor event write failed - no direct sensor channels configured for sensor " + virtualSensor.getName());
            return false;
        }
    }

    private static final class SharedMemoryWrapper {
        private static final int MAXIMUM_NUMBER_OF_SENSOR_VALUES = 16;
        private static final int SENSOR_EVENT_SIZE = 104;
        private final ByteBuffer mEventBuffer;
        private final ByteBuffer mMemoryMapping;
        private final SharedMemory mSharedMemory;
        private final Object mWriteLock;
        private int mWriteOffset = 0;

        SharedMemoryWrapper(SharedMemory sharedMemory) throws ErrnoException {
            ByteBuffer allocate = ByteBuffer.allocate(104);
            this.mEventBuffer = allocate;
            this.mWriteLock = new Object();
            this.mSharedMemory = sharedMemory;
            this.mMemoryMapping = sharedMemory.mapReadWrite();
            allocate.order(ByteOrder.nativeOrder());
        }

        void close() {
            synchronized (this.mWriteLock) {
                this.mSharedMemory.close();
            }
        }

        void write(int i, int i2, long j, VirtualSensorEvent virtualSensorEvent) {
            synchronized (this.mWriteLock) {
                this.mEventBuffer.position(0);
                this.mEventBuffer.putInt(104);
                this.mEventBuffer.putInt(i);
                this.mEventBuffer.putInt(i2);
                this.mEventBuffer.putInt((int) (j & 4294967295L));
                this.mEventBuffer.putLong(virtualSensorEvent.getTimestampNanos());
                for (int i3 = 0; i3 < 16; i3++) {
                    if (i3 < virtualSensorEvent.getValues().length) {
                        this.mEventBuffer.putFloat(virtualSensorEvent.getValues()[i3]);
                    } else {
                        this.mEventBuffer.putFloat(0.0f);
                    }
                }
                this.mEventBuffer.putInt(0);
                this.mMemoryMapping.position(this.mWriteOffset);
                this.mMemoryMapping.put(this.mEventBuffer.array(), 0, 104);
                int i4 = this.mWriteOffset;
                this.mWriteOffset = i4 + 104;
                if (i4 + 208 >= this.mSharedMemory.getSize()) {
                    this.mWriteOffset = 0;
                }
            }
        }
    }

    private static final class DirectChannelConfiguration {
        private final AtomicLong mEventCounter = new AtomicLong(1);
        private final int mReportToken;
        private final int mSensorType;
        private final SharedMemoryWrapper mSharedMemoryWrapper;

        DirectChannelConfiguration(int i, int i2, SharedMemoryWrapper sharedMemoryWrapper) {
            this.mReportToken = i;
            this.mSensorType = i2;
            this.mSharedMemoryWrapper = sharedMemoryWrapper;
        }

        void write(VirtualSensorEvent virtualSensorEvent) {
            long acquire = this.mEventCounter.getAcquire();
            long j = acquire + 1;
            this.mSharedMemoryWrapper.write(this.mReportToken, this.mSensorType, acquire, virtualSensorEvent);
            this.mEventCounter.setRelease(j != 4294967296L ? j : 1L);
        }
    }
}
