package com.samsung.android.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemVirtualAudioDevice {
    private static final String TAG = "SemVirtualAudioDeviceManager";
    private AudioManager mAudioManager;
    private AudioPolicy mAudioPolicy;
    private Context mContext;

    public SemVirtualAudioDevice(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public synchronized AudioRecord connectVirtualAudioOutputDevice(AudioFormat audioFormat, int[] iArr, int[] iArr2, boolean z) {
        AudioMixingRule.Builder targetMixRole = new AudioMixingRule.Builder().setTargetMixRole(0);
        int i = z ? 3 : 2;
        if (iArr.length == 0) {
            throw new IllegalArgumentException("Invalid app uid array size");
        }
        for (int i2 : iArr) {
            targetMixRole.addMixRule(4, Integer.valueOf(i2));
        }
        if (iArr2 != null) {
            for (int i3 : iArr2) {
                targetMixRole.addMixRule(1, new AudioAttributes.Builder().setUsage(i3).build());
            }
        }
        targetMixRole.voiceCommunicationCaptureAllowed(true);
        AudioMix build = new AudioMix.Builder(targetMixRole.build()).setFormat(audioFormat).setRouteFlags(i).build();
        resetAudioPolicy();
        AudioPolicy build2 = new AudioPolicy.Builder(this.mContext).addMix(build).build();
        this.mAudioPolicy = build2;
        if (this.mAudioManager.registerAudioPolicy(build2) == -1) {
            return null;
        }
        return this.mAudioPolicy.createAudioRecordSink(build);
    }

    public synchronized AudioTrack connectVirtualAudioInputDevice(AudioFormat audioFormat, int[] iArr, int[] iArr2) {
        AudioMixingRule.Builder targetMixRole = new AudioMixingRule.Builder().setTargetMixRole(1);
        if (iArr.length == 0) {
            throw new IllegalArgumentException("Invalid app uid array size");
        }
        for (int i : iArr) {
            targetMixRole.addMixRule(4, Integer.valueOf(i));
        }
        if (iArr2 != null) {
            for (int i2 : iArr2) {
                targetMixRole.addMixRule(2, new AudioAttributes.Builder().setCapturePreset(i2).build());
            }
        }
        AudioMix build = new AudioMix.Builder(targetMixRole.build()).setFormat(audioFormat).setRouteFlags(2).build();
        resetAudioPolicy();
        AudioPolicy build2 = new AudioPolicy.Builder(this.mContext).addMix(build).build();
        this.mAudioPolicy = build2;
        if (this.mAudioManager.registerAudioPolicy(build2) == -1) {
            return null;
        }
        return this.mAudioPolicy.createAudioTrackSource(build);
    }

    public synchronized void disconnectVirtualAudioDevice() {
        resetAudioPolicy();
    }

    private void resetAudioPolicy() {
        AudioPolicy audioPolicy = this.mAudioPolicy;
        if (audioPolicy != null) {
            this.mAudioManager.unregisterAudioPolicy(audioPolicy);
            Log.i(TAG, "Unregister audio policy");
            this.mAudioPolicy = null;
        }
    }
}
