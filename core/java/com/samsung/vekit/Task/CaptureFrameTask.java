package com.samsung.vekit.Task;

import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;

/* loaded from: classes6.dex */
public class CaptureFrameTask {
    private CaptureType captureType;
    private Item item;
    private CaptureFrameTaskListener listener;
    private int outputHeight;
    private int outputWidth;

    enum CaptureType {
        ORIGINAL_FRAME,
        RENDERED_FRAME
    }

    public CaptureFrameTask(Item item, int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.item = item;
        this.listener = listener;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.captureType = CaptureType.RENDERED_FRAME;
    }

    public CaptureFrameTask(int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        this.outputWidth = 0;
        this.outputHeight = 0;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
        this.listener = listener;
        this.captureType = CaptureType.ORIGINAL_FRAME;
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
}
