package com.samsung.vekit.Item;

import android.util.Log;

import com.samsung.vekit.Common.Object.AudioSegment;
import com.samsung.vekit.Common.Object.Region;
import com.samsung.vekit.Common.Object.SpeakerIDInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Audio;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Interface.AudioSegmentInterface;
import com.samsung.vekit.Interface.SpeakerIDInfoInterface;
import com.samsung.vekit.Layer.Layer;
import com.samsung.vekit.Listener.PcmInfoListener;

import java.util.HashMap;
import java.util.function.BiConsumer;

/* loaded from: classes6.dex */
public class AudioItem extends Item
        implements AudioSegmentInterface<AudioItem>, SpeakerIDInfoInterface<AudioItem> {
    private HashMap<String, AudioSegment> audioSegmentMap;
    private long endContentTime;
    private long fadeInDuration;
    private long fadeOutDuration;
    private PcmInfoListener pcmInfoListener;
    private HashMap<String, SpeakerIDInfo> speakerIDInfoMap;
    private long startContentTime;
    private int volume;

    public AudioItem(VEContext context, int id, String name) {
        super(context, ItemType.AUDIO, id, name);
        this.volume = 100;
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        this.audioSegmentMap = new HashMap<>();
        this.speakerIDInfoMap = new HashMap<>();
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.AUDIO) {
            throw new Exception("isInvalidElement : please set audio(content).");
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setParent(Layer parent) {
        return (AudioItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (AudioItem) super.setContent(content);
        } catch (Exception e) {
            Log.e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setPadding(long padding) {
        return (AudioItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setDuration(long duration) {
        return (AudioItem) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setSpeed(float speed) {
        return (AudioItem) super.setSpeed(speed);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem addRegion(Region region) {
        return (AudioItem) super.addRegion(region);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem removeRegion(Region region) {
        return (AudioItem) super.removeRegion(region);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public HashMap<String, SpeakerIDInfo> getSpeakerIDInfoMap() {
        return this.speakerIDInfoMap;
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void setSpeakerIDInfoMap(HashMap<String, SpeakerIDInfo> speakerIDInfoMap) {
        this.speakerIDInfoMap = speakerIDInfoMap;
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public SpeakerIDInfo getSpeakerIDInfo(String key) {
        return this.speakerIDInfoMap.get(key);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void addSpeakerIDInfo(String key, SpeakerIDInfo speakerIDInfo) {
        this.speakerIDInfoMap.put(key, speakerIDInfo);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void removeSpeakerIDInfo(String key) {
        this.speakerIDInfoMap.remove(key);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void clearSpeakerIDInfo() {
        this.speakerIDInfoMap.clear();
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public int getSpeakerIDInfoMapSize() {
        return this.speakerIDInfoMap.size();
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem clearRegions() {
        return (AudioItem) super.clearRegions();
    }

    public long getStartContentTime() {
        return this.startContentTime;
    }

    public AudioItem setStartContentTime(long startContentTime) {
        this.startContentTime = startContentTime;
        return this;
    }

    public long getEndContentTime() {
        return this.endContentTime;
    }

    public AudioItem setEndContentTime(long endContentTime) {
        this.endContentTime = endContentTime;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public AudioItem setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public AudioItem setFadeInDuration(long duration) {
        this.fadeInDuration = duration;
        return this;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public AudioItem setFadeOutDuration(long duration) {
        this.fadeOutDuration = duration;
        return this;
    }

    public long getFadeOutDuration() {
        return this.fadeOutDuration;
    }

    public boolean loadAudioSegment() {
        Log.i(this.TAG, "loadAudioSegment()");
        if (this.content == null) {
            Log.e(this.TAG, "Failed loadAudioSegment(), content is null.");
            return false;
        }
        this.audioSegmentMap.clear();
        ((Audio) this.content)
                .getAudioSegmentMap()
                .forEach(
                        new BiConsumer() { // from class:
                                           // com.samsung.vekit.Item.AudioItem$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                AudioItem.this
                                        .m9387lambda$loadAudioSegment$0$comsamsungvekitItemAudioItem(
                                                (String) obj, (AudioSegment) obj2);
                            }
                        });
        return true;
    }

    /* renamed from: lambda$loadAudioSegment$0$com-samsung-vekit-Item-AudioItem, reason: not valid java name */
    /* synthetic */ void m9387lambda$loadAudioSegment$0$comsamsungvekitItemAudioItem(
            String key, AudioSegment audioSegment) {
        this.audioSegmentMap.put(key, audioSegment.m9384clone());
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public HashMap<String, AudioSegment> getAudioSegmentMap() {
        return this.audioSegmentMap;
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void setAudioSegmentMap(HashMap<String, AudioSegment> audioSegmentMap) {
        this.audioSegmentMap = audioSegmentMap;
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public AudioSegment getAudioSegment(String key) {
        return this.audioSegmentMap.get(key);
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void addAudioSegment(String key, AudioSegment audioSegment) {
        this.audioSegmentMap.put(key, audioSegment);
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void removeAudioSegment(String key) {
        this.audioSegmentMap.remove(key);
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void clearAudioSegment() {
        this.audioSegmentMap.clear();
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public int getAudioSegmentMapSize() {
        return this.audioSegmentMap.size();
    }

    @Override // com.samsung.vekit.Item.Item
    public PcmInfoListener getPcmInfoListener() {
        return this.pcmInfoListener;
    }

    public void setPcmInfoListener(PcmInfoListener pcmInfoListener) {
        this.pcmInfoListener = pcmInfoListener;
    }
}
