package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Object.AudioSegment;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Interface.AudioSegmentInterface;

import java.util.HashMap;

/* loaded from: classes6.dex */
public class Audio extends Content implements AudioSegmentInterface<Audio> {
    private HashMap<String, AudioSegment> audioSegmentMap;
    private String filePath;

    public Audio(VEContext context, int id, String name) {
        super(context, ContentType.AUDIO, id, name);
        this.audioSegmentMap = new HashMap<>();
    }

    public Audio setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.samsung.vekit.Content.Content
    public Audio setDuration(long duration) {
        return (Audio) super.setDuration(duration);
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
}
