package com.android.systemui.media.muteawait;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.sec.ims.presence.ServiceTuple;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaMuteAwaitConnectionManager {
    public final AudioManager audioManager;
    public AudioDeviceAttributes currentMutedDevice;
    public final DeviceIconUtil deviceIconUtil;
    public final LocalMediaManager localMediaManager;
    public final MediaMuteAwaitLogger logger;
    public final Executor mainExecutor;
    public final MediaMuteAwaitConnectionManager$muteAwaitConnectionChangeListener$1 muteAwaitConnectionChangeListener = new AudioManager.MuteAwaitConnectionCallback() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager$muteAwaitConnectionChangeListener$1
        public final void onMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
            MediaMuteAwaitLogger mediaMuteAwaitLogger = this.this$0.logger;
            String address = audioDeviceAttributes.getAddress();
            String name = audioDeviceAttributes.getName();
            this.this$0.getClass();
            boolean zContains = ArraysKt___ArraysKt.contains(1, iArr);
            mediaMuteAwaitLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaMuteAwaitLogger$$ExternalSyntheticLambda0 mediaMuteAwaitLogger$$ExternalSyntheticLambda0 = new MediaMuteAwaitLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = mediaMuteAwaitLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaMuteAwait", logLevel, mediaMuteAwaitLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = address;
            logMessageImpl.str2 = name;
            logMessageImpl.bool1 = zContains;
            logBuffer.commit(logMessageObtain);
            this.this$0.getClass();
            if (ArraysKt___ArraysKt.contains(1, iArr)) {
                MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0;
                mediaMuteAwaitConnectionManager.currentMutedDevice = audioDeviceAttributes;
                String address2 = audioDeviceAttributes.getAddress();
                String name2 = audioDeviceAttributes.getName();
                MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager2 = this.this$0;
                mediaMuteAwaitConnectionManager2.getClass();
                int type = audioDeviceAttributes.getType();
                DeviceIconUtil deviceIconUtil = mediaMuteAwaitConnectionManager2.deviceIconUtil;
                Drawable drawable = deviceIconUtil.mContext.getDrawable(deviceIconUtil.getIconResIdFromAudioDeviceType(type));
                Iterator it = ((CopyOnWriteArrayList) mediaMuteAwaitConnectionManager.localMediaManager.getCallbacks()).iterator();
                while (it.hasNext()) {
                    ((LocalMediaManager.DeviceCallback) it.next()).onAboutToConnectDeviceAdded(address2, drawable, name2);
                }
            }
        }

        public final void onUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
            boolean zAreEqual = Intrinsics.areEqual(this.this$0.currentMutedDevice, audioDeviceAttributes);
            MediaMuteAwaitLogger mediaMuteAwaitLogger = this.this$0.logger;
            String address = audioDeviceAttributes.getAddress();
            String name = audioDeviceAttributes.getName();
            this.this$0.getClass();
            boolean zContains = ArraysKt___ArraysKt.contains(1, iArr);
            mediaMuteAwaitLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaMuteAwaitLogger$$ExternalSyntheticLambda0 mediaMuteAwaitLogger$$ExternalSyntheticLambda0 = new MediaMuteAwaitLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = mediaMuteAwaitLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaMuteAwait", logLevel, mediaMuteAwaitLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = address;
            logMessageImpl.str2 = name;
            logMessageImpl.bool1 = zContains;
            logMessageImpl.bool2 = zAreEqual;
            logBuffer.commit(logMessageObtain);
            if (zAreEqual) {
                this.this$0.getClass();
                if (ArraysKt___ArraysKt.contains(1, iArr)) {
                    MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0;
                    mediaMuteAwaitConnectionManager.currentMutedDevice = null;
                    Iterator it = ((CopyOnWriteArrayList) mediaMuteAwaitConnectionManager.localMediaManager.getCallbacks()).iterator();
                    while (it.hasNext()) {
                        ((LocalMediaManager.DeviceCallback) it.next()).onAboutToConnectDeviceRemoved();
                    }
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager$muteAwaitConnectionChangeListener$1] */
    public MediaMuteAwaitConnectionManager(Executor executor, LocalMediaManager localMediaManager, Context context, DeviceIconUtil deviceIconUtil, MediaMuteAwaitLogger mediaMuteAwaitLogger) {
        this.mainExecutor = executor;
        this.localMediaManager = localMediaManager;
        this.deviceIconUtil = deviceIconUtil;
        this.logger = mediaMuteAwaitLogger;
        this.audioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }
}
