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

    public synchronized AudioRecord connectVirtualAudioOutputDevice(AudioFormat audioFormat, int[] appUids, int[] targetUsages, boolean allowRender) {
        AudioMixingRule.Builder mixingRuleBuilder = new AudioMixingRule.Builder().setTargetMixRole(0);
        int routeFlags = allowRender ? 3 : 2;
        if (appUids.length == 0) {
            throw new IllegalArgumentException("Invalid app uid array size");
        }
        for (int uid : appUids) {
            mixingRuleBuilder.addMixRule(4, Integer.valueOf(uid));
        }
        if (targetUsages != null) {
            for (int usage : targetUsages) {
                AudioAttributes ruleAttribute = new AudioAttributes.Builder().setUsage(usage).build();
                mixingRuleBuilder.addMixRule(1, ruleAttribute);
            }
        }
        mixingRuleBuilder.voiceCommunicationCaptureAllowed(true);
        AudioMix audioRecordMix = new AudioMix.Builder(mixingRuleBuilder.build()).setFormat(audioFormat).setRouteFlags(routeFlags).build();
        resetAudioPolicy();
        this.mAudioPolicy = new AudioPolicy.Builder(this.mContext).addMix(audioRecordMix).build();
        if (this.mAudioManager.registerAudioPolicy(this.mAudioPolicy) == -1) {
            return null;
        }
        return this.mAudioPolicy.createAudioRecordSink(audioRecordMix);
    }

    public synchronized AudioTrack connectVirtualAudioInputDevice(AudioFormat audioFormat, int[] appUids, int[] targetSources) {
        AudioMixingRule.Builder mixingRuleBuilder = new AudioMixingRule.Builder().setTargetMixRole(1);
        if (appUids.length == 0) {
            throw new IllegalArgumentException("Invalid app uid array size");
        }
        for (int uid : appUids) {
            mixingRuleBuilder.addMixRule(4, Integer.valueOf(uid));
        }
        if (targetSources != null) {
            for (int source : targetSources) {
                AudioAttributes ruleAttribute = new AudioAttributes.Builder().setCapturePreset(source).build();
                mixingRuleBuilder.addMixRule(2, ruleAttribute);
            }
        }
        AudioMix audioTrackMix = new AudioMix.Builder(mixingRuleBuilder.build()).setFormat(audioFormat).setRouteFlags(2).build();
        resetAudioPolicy();
        this.mAudioPolicy = new AudioPolicy.Builder(this.mContext).addMix(audioTrackMix).build();
        if (this.mAudioManager.registerAudioPolicy(this.mAudioPolicy) == -1) {
            return null;
        }
        return this.mAudioPolicy.createAudioTrackSource(audioTrackMix);
    }

    public synchronized void disconnectVirtualAudioDevice() {
        resetAudioPolicy();
    }

    private void resetAudioPolicy() {
        if (this.mAudioPolicy != null) {
            this.mAudioManager.unregisterAudioPolicy(this.mAudioPolicy);
            Log.i(TAG, "Unregister audio policy");
            this.mAudioPolicy = null;
        }
    }
}
