package com.samsung.vekit.Task;

import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;

/* loaded from: classes6.dex */
public class CaptureFrameTask {
    private CaptureType captureType;
    private Item item;
    private CaptureFrameTaskListener listener;
    private int outputCenterX;
    private int outputCenterY;
    private int outputHeight;
    private int outputWidth;

    public enum CaptureType {
        ORIGINAL_FRAME,
        RENDERED_FRAME,
        SUPERHDR_FRAME
    }

    public CaptureFrameTask(Item item, int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.item = item;
        this.listener = captureFrameTaskListener;
        this.outputWidth = i;
        this.outputHeight = i2;
        this.captureType = CaptureType.RENDERED_FRAME;
    }

    public CaptureFrameTask(int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.outputWidth = i;
        this.outputHeight = i2;
        this.listener = captureFrameTaskListener;
        this.captureType = CaptureType.ORIGINAL_FRAME;
    }

    public CaptureFrameTask(Item item, int i, int i2, CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.item = item;
        this.outputWidth = i;
        this.outputHeight = i2;
        this.listener = captureFrameTaskListener;
        this.captureType = captureType;
    }

    public CaptureFrameTask(int i, int i2, int i3, int i4, CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        this.item = null;
        this.outputWidth = i;
        this.outputHeight = i2;
        this.listener = captureFrameTaskListener;
        this.captureType = captureType;
        this.outputCenterX = i3;
        this.outputCenterY = i4;
    }

    public Item getItem() {
        return this.item;
    }

    public int getOutputWidth() {
        return this.outputWidth;
    }

    public int getOutputHeight() {
        return this.outputHeight;
    }

    public CaptureFrameTaskListener getListener() {
        return this.listener;
    }

    public CaptureType getCaptureType() {
        return this.captureType;
    }

    public int getOutputCenterX() {
        return this.outputCenterX;
    }

    public int getOutputCenterY() {
        return this.outputCenterY;
    }
}
