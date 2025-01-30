package com.android.systemui.media.controls.pipeline;

import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.util.Log;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaDeviceManager$Entry$start$1 implements Runnable {
    public final /* synthetic */ MediaDeviceManager.Entry this$0;
    public final /* synthetic */ MediaDeviceManager this$1;

    public MediaDeviceManager$Entry$start$1(MediaDeviceManager.Entry entry, MediaDeviceManager mediaDeviceManager) {
        this.this$0 = entry;
        this.this$1 = mediaDeviceManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MediaController.PlaybackInfo playbackInfo;
        MediaController.PlaybackInfo playbackInfo2;
        Log.d("MediaDeviceManager", "startScan()");
        MediaDeviceManager.Entry entry = this.this$0;
        if (entry.started) {
            return;
        }
        ((CopyOnWriteArrayList) entry.localMediaManager.mCallbacks).add(entry);
        this.this$0.localMediaManager.startScan();
        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0.muteAwaitConnectionManager;
        if (mediaMuteAwaitConnectionManager != null) {
            AudioManager audioManager = mediaMuteAwaitConnectionManager.audioManager;
            audioManager.registerMuteAwaitConnectionCallback(mediaMuteAwaitConnectionManager.mainExecutor, mediaMuteAwaitConnectionManager.muteAwaitConnectionChangeListener);
            AudioDeviceAttributes mutingExpectedDevice = audioManager.getMutingExpectedDevice();
            if (mutingExpectedDevice != null) {
                mediaMuteAwaitConnectionManager.currentMutedDevice = mutingExpectedDevice;
                String address = mutingExpectedDevice.getAddress();
                String name = mutingExpectedDevice.getName();
                Drawable icon = mediaMuteAwaitConnectionManager.getIcon(mutingExpectedDevice);
                Iterator it = ((CopyOnWriteArrayList) mediaMuteAwaitConnectionManager.localMediaManager.getCallbacks()).iterator();
                while (it.hasNext()) {
                    ((LocalMediaManager.DeviceCallback) it.next()).onAboutToConnectDeviceAdded(address, icon, name);
                }
            }
        }
        MediaDeviceManager.Entry entry2 = this.this$0;
        MediaController mediaController = entry2.controller;
        entry2.playbackType = (mediaController == null || (playbackInfo2 = mediaController.getPlaybackInfo()) == null) ? 0 : playbackInfo2.getPlaybackType();
        MediaDeviceManager.Entry entry3 = this.this$0;
        MediaController mediaController2 = entry3.controller;
        entry3.playbackVolumeControlId = (mediaController2 == null || (playbackInfo = mediaController2.getPlaybackInfo()) == null) ? null : playbackInfo.getVolumeControlId();
        MediaDeviceManager.Entry entry4 = this.this$0;
        MediaController mediaController3 = entry4.controller;
        if (mediaController3 != null) {
            mediaController3.registerCallback(entry4);
        }
        this.this$0.updateCurrent();
        MediaDeviceManager.Entry entry5 = this.this$0;
        entry5.started = true;
        ((ConfigurationControllerImpl) this.this$1.configurationController).addCallback(entry5.configListener);
    }
}
