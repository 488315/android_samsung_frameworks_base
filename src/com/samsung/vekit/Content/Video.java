package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Object.AudioSegment;
import com.samsung.vekit.Common.Object.SlowVideoInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Interface.AudioSegmentInterface;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class Video extends Content implements AudioSegmentInterface<Video> {
    private HashMap<String, AudioSegment> audioSegmentMap;
    private long fileLength;
    private long fileOffset;
    protected String filePath;
    private int frameRate;
    protected boolean is360;
    private boolean isSlowMotion;
    private int orientation;
    private int recordingMode;
    private SlowVideoInfo slowVideoInfo;

    public Video(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.VIDEO, i, str);
        this.is360 = false;
        this.isSlowMotion = false;
        this.recordingMode = 0;
        this.frameRate = 30;
        this.audioSegmentMap = new HashMap<>();
        this.fileOffset = 0L;
        this.fileLength = 0L;
    }

    public Video setFilePath(String str) {
        this.filePath = str;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Video set360(boolean z) {
        this.is360 = z;
        return this;
    }

    public boolean is360() {
        return this.is360;
    }

    @Override // com.samsung.vekit.Content.Content
    public Video setWidth(int i) {
        return (Video) super.setWidth(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Video setHeight(int i) {
        return (Video) super.setHeight(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Video setDuration(long j) {
        return (Video) super.setDuration(j);
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public Video setSlowVideoInfo(SlowVideoInfo slowVideoInfo) {
        this.slowVideoInfo = slowVideoInfo;
        return this;
    }

    public SlowVideoInfo getSlowVideoInfo() {
        return this.slowVideoInfo;
    }

    public Video setisSlowMotion(boolean z) {
        this.isSlowMotion = z;
        return this;
    }

    public boolean isSlowMotion() {
        return this.isSlowMotion;
    }

    public int getRecordingMode() {
        return this.recordingMode;
    }

    public Video setRecordingMode(int i) {
        this.recordingMode = i;
        return this;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public Video setFrameRate(int i) {
        this.frameRate = i;
        return this;
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

    public Video setFileLength(long j) {
        this.fileLength = j;
        return this;
    }

    public Video setFileOffset(long j) {
        this.fileOffset = j;
        return this;
    }

    public long getFileLength() {
        return this.fileLength;
    }

    public long getFileOffset() {
        return this.fileOffset;
    }
}
