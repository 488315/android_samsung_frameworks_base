package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Object.SlowVideoInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Video extends Content {
  protected String filePath;
  private int frameRate;
  protected boolean is360;
  private boolean isSlowMotion;
  private int orientation;
  private int recordingMode;
  private SlowVideoInfo slowVideoInfo;

  public Video(VEContext context, int id, String name) {
    super(context, ContentType.VIDEO, id, name);
    this.is360 = false;
    this.isSlowMotion = false;
    this.recordingMode = 0;
    this.frameRate = 30;
  }

  public Video setFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public Video set360(boolean is360) {
    this.is360 = is360;
    return this;
  }

  public boolean is360() {
    return this.is360;
  }

  @Override // com.samsung.vekit.Content.Content
  public Video setWidth(int width) {
    return (Video) super.setWidth(width);
  }

  @Override // com.samsung.vekit.Content.Content
  public Video setHeight(int height) {
    return (Video) super.setHeight(height);
  }

  @Override // com.samsung.vekit.Content.Content
  public Video setDuration(long duration) {
    return (Video) super.setDuration(duration);
  }

  public int getOrientation() {
    return this.orientation;
  }

  public void setOrientation(int orientation) {
    this.orientation = orientation;
  }

  public Video setSlowVideoInfo(SlowVideoInfo slowVideoInfo) {
    this.slowVideoInfo = slowVideoInfo;
    return this;
  }

  public SlowVideoInfo getSlowVideoInfo() {
    return this.slowVideoInfo;
  }

  public Video setisSlowMotion(boolean isSlowMotion) {
    this.isSlowMotion = isSlowMotion;
    return this;
  }

  public boolean isSlowMotion() {
    return this.isSlowMotion;
  }

  public int getRecordingMode() {
    return this.recordingMode;
  }

  public Video setRecordingMode(int recordingMode) {
    this.recordingMode = recordingMode;
    return this;
  }

  public int getFrameRate() {
    return this.frameRate;
  }

  public Video setFrameRate(int frameRate) {
    this.frameRate = frameRate;
    return this;
  }
}
