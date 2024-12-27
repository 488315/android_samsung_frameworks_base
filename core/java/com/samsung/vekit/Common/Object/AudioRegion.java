package com.samsung.vekit.Common.Object;

public class AudioRegion {
    private long endTime;
    private long fadeInDuration;
    private long fadeOutDuration;
    private long startTime;
    private float volume;

    public AudioRegion(long startTime, long endTime) {
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        this.volume = 100.0f;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AudioRegion(
            long startTime, long endTime, float volume, long fadeInDuration, long fadeOutDuration) {
        this.fadeInDuration = 0L;
        this.fadeOutDuration = 0L;
        this.volume = 100.0f;
        this.startTime = startTime;
        this.endTime = endTime;
        this.volume = volume;
        this.fadeInDuration = fadeInDuration;
        this.fadeOutDuration = fadeOutDuration;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AudioRegion m9383clone() {
        return new AudioRegion(
                this.startTime,
                this.endTime,
                this.volume,
                this.fadeInDuration,
                this.fadeOutDuration);
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getFadeInDuration() {
        return this.fadeInDuration;
    }

    public void setFadeInDuration(long fadeInDuration) {
        this.fadeInDuration = fadeInDuration;
    }

    public long getFadeOutDuration() {
        return this.fadeOutDuration;
    }

    public void setFadeOutDuration(long fadeOutDuration) {
        this.fadeOutDuration = fadeOutDuration;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
