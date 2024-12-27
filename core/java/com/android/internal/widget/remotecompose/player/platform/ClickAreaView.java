package com.android.internal.widget.remotecompose.player.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/* loaded from: classes5.dex */
class ClickAreaView extends View {
    private boolean mDebug;
    private int mId;
    private String mMetadata;
    Paint mPaint;

    ClickAreaView(
            Context context, boolean debug, int id, String contentDescription, String metadata) {
        super(context);
        this.mPaint = new Paint();
        this.mId = id;
        this.mMetadata = metadata;
        this.mDebug = debug;
        setContentDescription(contentDescription);
    }

    public void setDebug(boolean value) {
        if (this.mDebug != value) {
            this.mDebug = value;
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDebug) {
            this.mPaint.setARGB(200, 200, 0, 0);
            this.mPaint.setStrokeWidth(3.0f);
            canvas.drawLine(0.0f, 0.0f, getWidth(), 0.0f, this.mPaint);
            canvas.drawLine(getWidth(), 0.0f, getWidth(), getHeight(), this.mPaint);
            canvas.drawLine(getWidth(), getHeight(), 0.0f, getHeight(), this.mPaint);
            canvas.drawLine(0.0f, getHeight(), 0.0f, 0.0f, this.mPaint);
            this.mPaint.setTextSize(20.0f);
            canvas.drawText("id: " + this.mId + " : " + this.mMetadata, 4.0f, 22.0f, this.mPaint);
        }
    }
}
