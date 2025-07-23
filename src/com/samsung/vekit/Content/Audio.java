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

    public Audio(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.AUDIO, i, str);
        this.audioSegmentMap = new HashMap<>();
    }

    public Audio setFilePath(String str) {
        this.filePath = str;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.samsung.vekit.Content.Content
    public Audio setDuration(long j) {
        return (Audio) super.setDuration(j);
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
}
