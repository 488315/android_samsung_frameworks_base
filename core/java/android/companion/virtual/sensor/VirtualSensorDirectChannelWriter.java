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
  private final SparseArray<SparseArray<DirectChannelConfiguration>> mConfiguredChannels =
      new SparseArray<>();

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

  public void addChannel(int channelHandle, SharedMemory sharedMemory) throws ErrnoException {
    synchronized (this.mChannelsLock) {
      if (this.mChannels.contains(channelHandle)) {
        Log.m102w(TAG, "Channel with handle " + channelHandle + " already added.");
      } else {
        this.mChannels.put(
            channelHandle,
            new SharedMemoryWrapper((SharedMemory) Objects.requireNonNull(sharedMemory)));
      }
    }
  }

  public void removeChannel(int channelHandle) {
    synchronized (this.mChannelsLock) {
      SharedMemoryWrapper sharedMemoryWrapper = this.mChannels.removeReturnOld(channelHandle);
      if (sharedMemoryWrapper != null) {
        sharedMemoryWrapper.close();
      }
      for (int i = 0; i < this.mConfiguredChannels.size(); i++) {
        this.mConfiguredChannels.valueAt(i).remove(channelHandle);
      }
    }
  }

  public boolean configureChannel(
      int channelHandle, VirtualSensor sensor, int rateLevel, int reportToken) {
    synchronized (this.mChannelsLock) {
      SparseArray<DirectChannelConfiguration> configs =
          this.mConfiguredChannels.get(
              ((VirtualSensor) Objects.requireNonNull(sensor)).getHandle());
      if (rateLevel == 0) {
        if (configs != null && configs.removeReturnOld(channelHandle) != null) {
          return true;
        }
        Log.m102w(
            TAG,
            "Channel configuration failed - channel with handle " + channelHandle + " not found");
        return false;
      }
      if (configs == null) {
        configs = new SparseArray<>();
        this.mConfiguredChannels.put(sensor.getHandle(), configs);
      }
      SharedMemoryWrapper sharedMemoryWrapper = this.mChannels.get(channelHandle);
      if (sharedMemoryWrapper == null) {
        Log.m102w(
            TAG,
            "Channel configuration failed - channel with handle " + channelHandle + " not found");
        return false;
      }
      configs.put(
          channelHandle,
          new DirectChannelConfiguration(reportToken, sensor.getType(), sharedMemoryWrapper));
      return true;
    }
  }

  public boolean writeSensorEvent(VirtualSensor sensor, VirtualSensorEvent event) {
    Objects.requireNonNull(event);
    synchronized (this.mChannelsLock) {
      SparseArray<DirectChannelConfiguration> configs =
          this.mConfiguredChannels.get(
              ((VirtualSensor) Objects.requireNonNull(sensor)).getHandle());
      if (configs != null && configs.size() != 0) {
        for (int i = 0; i < configs.size(); i++) {
          configs.valueAt(i).write((VirtualSensorEvent) Objects.requireNonNull(event));
        }
        return true;
      }
      Log.m102w(
          TAG,
          "Sensor event write failed - no direct sensor channels configured for sensor "
              + sensor.getName());
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

    void write(int reportToken, int sensorType, long eventCounter, VirtualSensorEvent event) {
      synchronized (this.mWriteLock) {
        this.mEventBuffer.position(0);
        this.mEventBuffer.putInt(104);
        this.mEventBuffer.putInt(reportToken);
        this.mEventBuffer.putInt(sensorType);
        this.mEventBuffer.putInt((int) (4294967295L & eventCounter));
        this.mEventBuffer.putLong(event.getTimestampNanos());
        for (int i = 0; i < 16; i++) {
          if (i < event.getValues().length) {
            this.mEventBuffer.putFloat(event.getValues()[i]);
          } else {
            this.mEventBuffer.putFloat(0.0f);
          }
        }
        this.mEventBuffer.putInt(0);
        this.mMemoryMapping.position(this.mWriteOffset);
        this.mMemoryMapping.put(this.mEventBuffer.array(), 0, 104);
        int i2 = this.mWriteOffset + 104;
        this.mWriteOffset = i2;
        if (i2 + 104 >= this.mSharedMemory.getSize()) {
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

    DirectChannelConfiguration(
        int reportToken, int sensorType, SharedMemoryWrapper sharedMemoryWrapper) {
      this.mReportToken = reportToken;
      this.mSensorType = sensorType;
      this.mSharedMemoryWrapper = sharedMemoryWrapper;
    }

    void write(VirtualSensorEvent event) {
      long currentCounter = this.mEventCounter.getAcquire();
      long currentCounter2 = currentCounter + 1;
      this.mSharedMemoryWrapper.write(this.mReportToken, this.mSensorType, currentCounter, event);
      if (currentCounter2 == 4294967296L) {
        currentCounter2 = 1;
      }
      this.mEventCounter.setRelease(currentCounter2);
    }
  }
}
