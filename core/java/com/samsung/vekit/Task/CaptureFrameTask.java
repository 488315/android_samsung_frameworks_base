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

    public CaptureFrameTask(
            Item item, int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.item = item;
        this.listener = listener;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.captureType = CaptureType.RENDERED_FRAME;
    }

    public CaptureFrameTask(int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.listener = listener;
        this.captureType = CaptureType.ORIGINAL_FRAME;
    }

    public CaptureFrameTask(
            Item item,
            int outputWidth,
            int outputHeight,
            CaptureType captureType,
            CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.item = item;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.listener = listener;
        this.captureType = captureType;
    }

    public CaptureFrameTask(
            int outputWidth,
            int outputHeight,
            int outputCenterX,
            int outputCenterY,
            CaptureType captureType,
            CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.outputCenterX = 0;
        this.outputCenterY = 0;
        this.item = null;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.listener = listener;
        this.captureType = captureType;
        this.outputCenterX = outputCenterX;
        this.outputCenterY = outputCenterY;
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
