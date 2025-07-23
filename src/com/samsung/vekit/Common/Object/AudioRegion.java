package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class AudioRegion {
    private long endTime;
    private long fadeInDuration;
    private long fadeOutDuration;
    private long startTime;
    private float volume;

    public AudioRegion(long j, long j2) {
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        this.volume = 100.0f;
        this.startTime = j;
        this.endTime = j2;
    }

    public AudioRegion(long j, long j2, float f, long j3, long j4) {
        this.startTime = j;
        this.endTime = j2;
        this.volume = f;
        this.fadeInDuration = j3;
        this.fadeOutDuration = j4;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AudioRegion m9803clone() {
        return new AudioRegion(this.startTime, this.endTime, this.volume, this.fadeInDuration, this.fadeOutDuration);
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long j) {
        this.endTime = j;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public void setFadeInDuration(long j) {
        this.fadeInDuration = j;
    }

    public long getFadeOutDuration() {
        return this.fadeOutDuration;
    }

    public void setFadeOutDuration(long j) {
        this.fadeOutDuration = j;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float f) {
        this.volume = f;
    }
}
