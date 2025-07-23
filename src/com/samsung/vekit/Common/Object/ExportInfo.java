package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ContentColorType;
import com.samsung.vekit.Common.Type.ContentOriginType;
import com.samsung.vekit.Common.Type.FrameEncodeType;
import com.samsung.vekit.Common.Type.VideoCodecType;
import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public class ExportInfo {
    private FileDescriptor fd;
    private int height;
    private int width;
    private final String TAG = "ExportInfo";
    private int bitDepth = 8;
    private int frameRate = 0;
    private int orientation = 0;
    private VideoCodecType videocodectype = VideoCodecType.AVC;
    private int recordingMode = 0;
    private int bitRate = 0;
    private boolean preserveAudio = true;
    private ContentColorType contentColorType = ContentColorType.SDR;
    private boolean rewriteMode = false;
    private FrameEncodeType bFrameMode = FrameEncodeType.AUTO;
    private ContentOriginType contentOrigin = ContentOriginType.UNKNOWN;
    private ContentReferenceInfo referenceInfo = new ContentReferenceInfo();

    public ExportInfo(int i, int i2, FileDescriptor fileDescriptor) {
        this.width = i;
        this.height = i2;
        this.fd = fileDescriptor;
    }

    public boolean getRewriteMode() {
        return this.rewriteMode;
    }

    public void setRewriteMode(boolean z) {
        this.rewriteMode = z;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getBitDepth() {
        return this.bitDepth;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public FileDescriptor getFd() {
        return this.fd;
    }

    public VideoCodecType getVideoCodecType() {
        return this.videocodectype;
    }

    public int getRecordingMode() {
        return this.recordingMode;
    }

    public int getBitRate() {
        return this.bitRate;
    }

    public boolean getPreserveAudio() {
        return this.preserveAudio;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setFd(FileDescriptor fileDescriptor) {
        this.fd = fileDescriptor;
    }

    public void setBitDepth(int i) {
        this.bitDepth = i;
    }

    public void setFrameRate(int i) {
        this.frameRate = i;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public void setVideoCodecType(VideoCodecType videoCodecType) {
        this.videocodectype = videoCodecType;
    }

    public void setRecordingMode(int i) {
        this.recordingMode = i;
    }

    public void setBitRate(int i) {
        this.bitRate = i;
    }

    public void setPreserveAudio(boolean z) {
        this.preserveAudio = z;
    }

    public ContentColorType getContentColorType() {
        return this.contentColorType;
    }

    public void setContentColorType(ContentColorType contentColorType) {
        this.contentColorType = contentColorType;
    }

    public ContentReferenceInfo getReferenceInfo() {
        return this.referenceInfo;
    }

    public FrameEncodeType getBFrameMode() {
        return this.bFrameMode;
    }

    public ContentOriginType getContentOrigin() {
        return this.contentOrigin;
    }

    public void setReferenceInfo(ContentReferenceInfo contentReferenceInfo) {
        this.referenceInfo = contentReferenceInfo;
    }

    public void setBFrameMode(FrameEncodeType frameEncodeType) {
        this.bFrameMode = frameEncodeType;
    }

    public void setContentOrigin(ContentOriginType contentOriginType) {
        this.contentOrigin = contentOriginType;
    }
}
