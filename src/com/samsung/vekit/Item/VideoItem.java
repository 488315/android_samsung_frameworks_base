package com.samsung.vekit.Item;

import android.util.Log;
import com.samsung.vekit.Common.Object.AudioSegment;
import com.samsung.vekit.Common.Object.Filter;
import com.samsung.vekit.Common.Object.FilterOption;
import com.samsung.vekit.Common.Object.LogProfile;
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
    protected LogProfile logProfile;
    protected float opacity;
    protected PcmInfoListener pcmInfoListener;
    protected HashMap<String, SpeakerIDInfo> speakerIDInfoMap;
    protected long startContentTime;
    protected ToneInfo toneInfo;
    protected int volume;

    public VideoItem(VEContext vEContext, int i, String str) {
        super(vEContext, ItemType.VIDEO, i, str);
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
    public VideoItem setParent(Layer layer) {
        return (VideoItem) super.setParent(layer);
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
    public VideoItem setPadding(long j) {
        return (VideoItem) super.setPadding(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setDuration(long j) {
        return (VideoItem) super.setDuration(j);
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setSpeed(float f) {
        return (VideoItem) super.setSpeed(f);
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

    public VideoItem setStartContentTime(long j) {
        this.startContentTime = j;
        return this;
    }

    public long getEndContentTime() {
        return this.endContentTime;
    }

    public VideoItem setEndContentTime(long j) {
        this.endContentTime = j;
        return this;
    }

    public int getVolume() {
        return this.volume;
    }

    public VideoItem setVolume(int i) {
        this.volume = i;
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
    public VideoItem setFilterIntensity(float f) {
        this.filterIntensity = f;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public float getOpacity() {
        return this.opacity;
    }

    @Override // com.samsung.vekit.Item.Item, com.samsung.vekit.Common.Object.Element
    public VideoItem setOpacity(float f) {
        this.opacity = f;
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public VideoItem setToneIntensity(ToneType toneType, int i) {
        this.toneInfo.setTone(toneType, i);
        return this;
    }

    @Override // com.samsung.vekit.Item.Item
    public int getToneIntensity(ToneType toneType) {
        return (int) this.toneInfo.getTone(toneType);
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public HashMap<String, SpeakerIDInfo> getSpeakerIDInfoMap() {
        return this.speakerIDInfoMap;
    }

    @Override // com.samsung.vekit.Interface.SpeakerIDInfoInterface
    public void setSpeakerIDInfoMap(HashMap<String, SpeakerIDInfo> map) {
        this.speakerIDInfoMap = map;
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

    public VideoItem setEnableDeflicker(boolean z) {
        this.enableDeflicker = z;
        return this;
    }

    public boolean isEnableFRC() {
        return this.enableFRC;
    }

    public VideoItem setEnableFRC(boolean z) {
        this.enableFRC = z;
        return this;
    }

    public VideoItem setFadeInDuration(long j) {
        this.fadeInDuration = j;
        return this;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public VideoItem setFadeOutDuration(long j) {
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
        ((Video) this.content).getAudioSegmentMap().forEach(new BiConsumer() { // from class: com.samsung.vekit.Item.VideoItem$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.m9821lambda$loadAudioSegment$0$comsamsungvekitItemVideoItem((String) obj, (AudioSegment) obj2);
            }
        });
        update();
        return true;
    }

    /* renamed from: lambda$loadAudioSegment$0$com-samsung-vekit-Item-VideoItem, reason: not valid java name */
    /* synthetic */ void m9821lambda$loadAudioSegment$0$comsamsungvekitItemVideoItem(String str, AudioSegment audioSegment) {
        this.audioSegmentMap.put(str, audioSegment.m9817clone());
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public HashMap<String, AudioSegment> getAudioSegmentMap() {
        return this.audioSegmentMap;
    }

    @Override // com.samsung.vekit.Interface.AudioSegmentInterface
    public void setAudioSegmentMap(HashMap<String, AudioSegment> map) {
        this.audioSegmentMap = map;
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

    public void setFilterOption(FilterOption filterOption) {
        this.filterOption = filterOption;
    }

    public FilterOption getFilterOption() {
        return this.filterOption;
    }

    public void setLogProfile(LogProfile logProfile) {
        this.logProfile = logProfile;
    }

    public LogProfile getLogProfile() {
        return this.logProfile;
    }
}
