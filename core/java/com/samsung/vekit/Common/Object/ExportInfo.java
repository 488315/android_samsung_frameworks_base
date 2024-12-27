package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.ContentColorType;
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

    public ExportInfo(int width, int height, FileDescriptor fd) {
        this.width = width;
        this.height = height;
        this.fd = fd;
    }

    public boolean getRewriteMode() {
        return this.rewriteMode;
    }

    public void setRewriteMode(boolean flag) {
        this.rewriteMode = flag;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFd(FileDescriptor fd) {
        this.fd = fd;
    }

    public void setBitDepth(int bitDepth) {
        this.bitDepth = bitDepth;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setVideoCodecType(VideoCodecType videocodectype) {
        this.videocodectype = videocodectype;
    }

    public void setRecordingMode(int recordingMode) {
        this.recordingMode = recordingMode;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public void setPreserveAudio(boolean preserveAudio) {
        this.preserveAudio = preserveAudio;
    }

    public ContentColorType getContentColorType() {
        return this.contentColorType;
    }

    public void setContentColorType(ContentColorType contentColorType) {
        this.contentColorType = contentColorType;
    }
}
