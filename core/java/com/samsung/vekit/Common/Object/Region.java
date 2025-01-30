package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class Region {
  private long endTime;
  private boolean isFrcOn;
  private float speed;
  private long startTime;

  public Region(long startTime, long endTime, float speed, boolean isFrcOn) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.speed = speed;
    this.isFrcOn = isFrcOn;
  }

  public Region getRegion() {
    return this;
  }

  public long getStartTime() {
    return this.startTime;
  }

  public long getEndTime() {
    return this.endTime;
  }

  public float getSpeed() {
    return this.speed;
  }

  public boolean isFrcOn() {
    return this.isFrcOn;
  }

  public void setStartTime(int starTime) {
    this.startTime = starTime;
  }

  public void setEndTime(int endTime) {
    this.endTime = endTime;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public void setFrcOn(boolean isFrcOn) {
    this.isFrcOn = isFrcOn;
  }
}
