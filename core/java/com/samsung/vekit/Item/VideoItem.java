package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.AudioSegment;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.FilterOption;
import com.samsung.vekit.Common.Object.Region;
import com.samsung.vekit.Common.Object.SpeakerIDInfo;
import com.samsung.vekit.Common.Object.ToneInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.MeshType;
import com.samsung.vekit.Common.Type.ToneType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.Video;
import com.samsung.vekit.Interface.AudioSegmentInterface;
import com.samsung.vekit.Interface.SpeakerIDInfoInterface;
import com.samsung.vekit.Layer.Layer;
import com.samsung.vekit.Listener.PcmInfoListener;
import java.util.HashMap;
import java.util.function.BiConsumer;

/* loaded from: classes6.dex */
public class VideoItem extends Item implements AudioSegmentInterface<VideoItem>, SpeakerIDInfoInterface<VideoItem> {
    protected HashMap<String, AudioSegment> audioSegmentMap;
    protected boolean enableDeflicker;
    protected boolean enableFRC;
    protected long endContentTime;
    protected long fadeInDuration;
    protected long fadeOutDuration;
    protected Filter filter;
    protected float filterIntensity;
    protected FilterOption filterOption;
    protected float opacity;
    protected PcmInfoListener pcmInfoListener;
    protected HashMap<String, SpeakerIDInfo> speakerIDInfoMap;
    protected long startContentTime;
    protected ToneInfo toneInfo;
    protected int volume;

    public VideoItem(VEContext context, int id, String name) {
        super(context, ItemType.VIDEO, id, name);
        this.volume = 100;
        this.filterIntensity = 100.0f;
        this.opacity = 1.0f;
        this.enableDeflicker = false;
        this.enableFRC = false;
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        this.audioSegmentMap = new HashMap<>();
        this.toneInfo = new ToneInfo();
        this.speakerIDInfoMap = new HashMap<>();
        this.filterOption = new FilterOption();
    }

    @Override // com.samsung.vekit.Item.Item
    public void checkValidContent(Content content) throws Exception {
        if (content.getContentType() != ContentType.VIDEO) {
            throw new Exception("isInvalidElement : please set video(content).");
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setParent(Layer parent) {
        return (VideoItem) super.setParent(parent);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setContent(Content content) {
        try {
            checkValidContent(content);
            return (VideoItem) super.setContent(content);
        } catch (Exception e) {
            Log.e(this.TAG, "setContent: ", e);
            return this;
        }
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setPadding(long padding) {
        return (VideoItem) super.setPadding(padding);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setDuration(long duration) {
        return (VideoItem) super.setDuration(duration);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setSpeed(float speed) {
        return (VideoItem) super.setSpeed(speed);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem addRegion(Region region) {
        return (VideoItem) super.addRegion(region);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem removeRegion(Region region) {
        return (VideoItem) super.removeRegion(region);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem clearRegions() {
        return (VideoItem) super.clearRegions();
    }

    public long getStartContentTime() {
        return this.startContentTime;
    }

    public VideoItem setStartContentTime(long startContentTime) {
        this.startContentTime = startContentTime;
        return this;
    }

    public long getEndContentTime() {
        return this.endContentTime;
    }

    public VideoItem setEndContentTime(long endContentTime) {
        this.endContentTime = endContentTime;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public VideoItem setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public Filter getFilter() {
        return this.filter;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getFilterIntensity() {
        return this.filterIntensity;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setFilterIntensity(float filterIntensity) {
        this.filterIntensity = filterIntensity;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item, com.samsung.vekit.Common.Object.Element
    public VideoItem setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setToneIntensity(ToneType type, int intensity) {
        this.toneInfo.setTone(type, intensity);
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public int getToneIntensity(ToneType type) {
        return (int) this.toneInfo.getTone(type);
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

    public VideoItem setMeshType(MeshType meshType) {
        this.meshType = meshType;
        return this;
    }

    public MeshType getMeshType() {
        return this.meshType;
    }

    public boolean isEnableDeflicker() {
        return this.enableDeflicker;
    }

    public VideoItem setEnableDeflicker(boolean enableDeflicker) {
        this.enableDeflicker = enableDeflicker;
        return this;
    }

    public boolean isEnableFRC() {
        return this.enableFRC;
    }

    public VideoItem setEnableFRC(boolean enableFRC) {
        this.enableFRC = enableFRC;
        return this;
    }

    public VideoItem setFadeInDuration(long duration) {
        this.fadeInDuration = duration;
        return this;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public VideoItem setFadeOutDuration(long duration) {
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
        ((Video) this.content).getAudioSegmentMap().forEach(new BiConsumer() { // from class: com.samsung.vekit.Item.VideoItem$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                VideoItem.this.m9388lambda$loadAudioSegment$0$comsamsungvekitItemVideoItem((String) obj, (AudioSegment) obj2);
            }
        });
        update();
        return true;
    }

    /* renamed from: lambda$loadAudioSegment$0$com-samsung-vekit-Item-VideoItem, reason: not valid java name */
    /* synthetic */ void m9388lambda$loadAudioSegment$0$comsamsungvekitItemVideoItem(String key, AudioSegment audioSegment) {
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

    public void setFilterOption(FilterOption filterOption) {
        this.filterOption = filterOption;
    }

    public FilterOption getFilterOption() {
        return this.filterOption;
    }
}
