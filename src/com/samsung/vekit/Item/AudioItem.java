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
public class AudioItem extends Item implements AudioSegmentInterface<AudioItem>, SpeakerIDInfoInterface<AudioItem> {
    private HashMap<String, AudioSegment> audioSegmentMap;
    private long endContentTime;
    private long fadeInDuration;
    private long fadeOutDuration;
    private PcmInfoListener pcmInfoListener;
    private HashMap<String, SpeakerIDInfo> speakerIDInfoMap;
    private long startContentTime;
    private int volume;

    public AudioItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.AUDIO, i, str);
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
    public AudioItem setParent(Layer layer) {
        return (AudioItem) super.setParent(layer);
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
    public AudioItem setPadding(long j) {
        return (AudioItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setDuration(long j) {
        return (AudioItem) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public AudioItem setSpeed(float f) {
        return (AudioItem) super.setSpeed(f);
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
    public void setSpeakerIDInfoMap(HashMap<String, SpeakerIDInfo> hashMap) {
        this.speakerIDInfoMap = hashMap;
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public SpeakerIDInfo getSpeakerIDInfo(String str) {
        return this.speakerIDInfoMap.get(str);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void addSpeakerIDInfo(String str, SpeakerIDInfo speakerIDInfo) {
        this.speakerIDInfoMap.put(str, speakerIDInfo);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void removeSpeakerIDInfo(String str) {
        this.speakerIDInfoMap.remove(str);
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

    public AudioItem setStartContentTime(long j) {
        this.startContentTime = j;
        return this;
    }

    public long getEndContentTime() {
        return this.endContentTime;
    }

    public AudioItem setEndContentTime(long j) {
        this.endContentTime = j;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public AudioItem setVolume(int i) {
        this.volume = i;
        return this;
    }

    public AudioItem setFadeInDuration(long j) {
        this.fadeInDuration = j;
        return this;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public AudioItem setFadeOutDuration(long j) {
        this.fadeOutDuration = j;
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
        ((Audio) this.content).getAudioSegmentMap().forEach(new BiConsumer() { // from class: com.samsung.vekit.Item.AudioItem$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioItem.this.m9807lambda$loadAudioSegment$0$comsamsungvekitItemAudioItem((String) obj, (AudioSegment) obj2);
            }
        });
        return true;
    }

    /* renamed from: lambda$loadAudioSegment$0$com-samsung-vekit-Item-AudioItem, reason: not valid java name */
    /* synthetic */ void m9807lambda$loadAudioSegment$0$comsamsungvekitItemAudioItem(String str, AudioSegment audioSegment) {
        this.audioSegmentMap.put(str, audioSegment.m9804clone());
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public HashMap<String, AudioSegment> getAudioSegmentMap() {
        return this.audioSegmentMap;
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void setAudioSegmentMap(HashMap<String, AudioSegment> hashMap) {
        this.audioSegmentMap = hashMap;
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public AudioSegment getAudioSegment(String str) {
        return this.audioSegmentMap.get(str);
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void addAudioSegment(String str, AudioSegment audioSegment) {
        this.audioSegmentMap.put(str, audioSegment);
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void removeAudioSegment(String str) {
        this.audioSegmentMap.remove(str);
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
