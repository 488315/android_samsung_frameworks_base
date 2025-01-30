package com.android.server.wm;

import android.R;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class DeviceStateController {
  public final int mConcurrentDisplayDeviceState;
  public int mCurrentState;
  public final int[] mFoldedDeviceStates;
  public final int[] mHalfFoldedDeviceStates;
  public final boolean mMatchBuiltInDisplayOrientationToDefaultDisplay;
  public final int[] mOpenDeviceStates;
  public final int[] mRearDisplayDeviceStates;
  public final int[] mReverseRotationAroundZAxisStates;
  public final WindowManagerGlobalLock mWmLock;
  final Map mDeviceStateCallbacks = new ArrayMap();
  public DeviceState mCurrentDeviceState = DeviceState.UNKNOWN;

  public enum DeviceState {
    UNKNOWN,
    OPEN,
    FOLDED,
    HALF_FOLDED,
    REAR,
    CONCURRENT
  }

  public DeviceStateController(Context context, WindowManagerGlobalLock windowManagerGlobalLock) {
    this.mWmLock = windowManagerGlobalLock;
    this.mOpenDeviceStates = context.getResources().getIntArray(17236271);
    this.mHalfFoldedDeviceStates =
        context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables);
    this.mFoldedDeviceStates = context.getResources().getIntArray(R.array.networks_not_clear_data);
    this.mRearDisplayDeviceStates = context.getResources().getIntArray(17236277);
    this.mConcurrentDisplayDeviceState =
        context.getResources().getInteger(R.integer.config_downloadDataDirLowSpaceThreshold);
    this.mReverseRotationAroundZAxisStates =
        context.getResources().getIntArray(R.array.config_supportedDreamComplications);
    this.mMatchBuiltInDisplayOrientationToDefaultDisplay =
        context.getResources().getBoolean(R.bool.config_keepRestrictedProfilesInBackground);
  }

  public void registerDeviceStateCallback(Consumer consumer, Executor executor) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mDeviceStateCallbacks.put(consumer, executor);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void unregisterDeviceStateCallback(Consumer consumer) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mDeviceStateCallbacks.remove(consumer);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean shouldReverseRotationDirectionAroundZAxis(DisplayContent displayContent) {
    if (displayContent.isDefaultDisplay) {
      return ArrayUtils.contains(this.mReverseRotationAroundZAxisStates, this.mCurrentState);
    }
    return false;
  }

  public boolean shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay() {
    return this.mMatchBuiltInDisplayOrientationToDefaultDisplay;
  }

  public void onDeviceStateReceivedByDisplayManager(int i) {
    final DeviceState deviceState;
    this.mCurrentState = i;
    if (ArrayUtils.contains(this.mHalfFoldedDeviceStates, i)) {
      deviceState = DeviceState.HALF_FOLDED;
    } else if (ArrayUtils.contains(this.mFoldedDeviceStates, i)) {
      deviceState = DeviceState.FOLDED;
    } else if (ArrayUtils.contains(this.mRearDisplayDeviceStates, i)) {
      deviceState = DeviceState.REAR;
    } else if (ArrayUtils.contains(this.mOpenDeviceStates, i)) {
      deviceState = DeviceState.OPEN;
    } else if (i == this.mConcurrentDisplayDeviceState) {
      deviceState = DeviceState.CONCURRENT;
    } else {
      deviceState = DeviceState.UNKNOWN;
    }
    DeviceState deviceState2 = this.mCurrentDeviceState;
    if (deviceState2 == null || !deviceState2.equals(deviceState)) {
      this.mCurrentDeviceState = deviceState;
      List copyDeviceStateCallbacks = copyDeviceStateCallbacks();
      for (int i2 = 0; i2 < copyDeviceStateCallbacks.size(); i2++) {
        final Pair pair = (Pair) copyDeviceStateCallbacks.get(i2);
        ((Executor) pair.second)
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    DeviceStateController.lambda$onDeviceStateReceivedByDisplayManager$0(
                        pair, deviceState);
                  }
                });
      }
    }
  }

  public static /* synthetic */ void lambda$onDeviceStateReceivedByDisplayManager$0(
      Pair pair, DeviceState deviceState) {
    ((Consumer) pair.first).accept(deviceState);
  }

  public List copyDeviceStateCallbacks() {
    final ArrayList arrayList = new ArrayList();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mDeviceStateCallbacks.forEach(
            new BiConsumer() { // from class:
                               // com.android.server.wm.DeviceStateController$$ExternalSyntheticLambda1
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                DeviceStateController.lambda$copyDeviceStateCallbacks$1(
                    arrayList, (Consumer) obj, (Executor) obj2);
              }
            });
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return arrayList;
  }

  public static /* synthetic */ void lambda$copyDeviceStateCallbacks$1(
      List list, Consumer consumer, Executor executor) {
    list.add(new Pair(consumer, executor));
  }
}
