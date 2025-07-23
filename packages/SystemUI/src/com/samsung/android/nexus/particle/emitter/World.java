package com.samsung.android.nexus.particle.emitter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.Particle;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class World {
    public final LayerContainer mContainer;
    public final FrameController mFrameController;
    public final Emitter mRootEmitter;
    public final Particle mRootParticle;
    public boolean mIsPaused = true;
    public boolean mIsRunning = false;
    public long mStartedTime = 0;
    public long mLastStepTime = 0;
    public long mPausedTime = 0;
    public long mTotalPausedTime = 0;
    public final Status mWorldStatus = new Status();
    public final WorldParticleLinkedList mWorldParticleLinkedList = new WorldParticleLinkedList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class WorldParticleLinkedList extends Particle.ParticleLinkedList {
        private WorldParticleLinkedList() {
        }
    }

    static {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setTextSize(20.0f);
        paint.setColor(-65536);
    }

    public World(LayerContainer layerContainer) {
        Log.i("World", "World: created");
        this.mContainer = layerContainer;
        FrameController frameController = new FrameController(layerContainer);
        this.mFrameController = frameController;
        ParticleRule particleRule = new ParticleRule();
        LongRangeable longRangeable = particleRule.lifeTime;
        longRangeable.mMin = -1L;
        longRangeable.mMax = -1L;
        longRangeable.onRangeUpdated();
        Emitter emitter = new Emitter(layerContainer.getAppContext(), this, new EmissionRule(), particleRule);
        this.mRootEmitter = emitter;
        this.mRootParticle = new Particle(emitter);
        frameController.startFrameRateDown();
    }

    public final void draw(Canvas canvas) {
        if (!this.mIsRunning || this.mIsPaused) {
            return;
        }
        SystemClock.elapsedRealtime();
        WorldParticleLinkedList worldParticleLinkedList = this.mWorldParticleLinkedList;
        Particle particle = worldParticleLinkedList.head;
        int i = worldParticleLinkedList.size;
        for (int i2 = 0; i2 < i; i2++) {
            BitmapParticleTexture bitmapParticleTexture = particle.mParticleTexture;
            if (bitmapParticleTexture != null && particle.mEnable && particle.mIsInSight) {
                Status status = particle.status;
                if (status.alpha >= 0.0f) {
                    ParticleRule particleRule = particle.mParentEmitter.mParticleRule;
                    if (status.mUpdateBounds) {
                        status.updateBounds();
                    }
                    RectF rectF = status.mBounds;
                    float centerX = rectF.centerX();
                    float centerY = rectF.centerY();
                    Paint paint = Particle.sPaint;
                    paint.setColorFilter(particle.mColorFilter);
                    paint.setAlpha(Math.min(255, Math.max(0, (int) (status.alpha * 255.0f))));
                    canvas.save();
                    canvas.rotate(status.rotation, centerX, centerY);
                    if (particleRule.configValues[ParticleConfigType.APPLY_DRAW_MORPHING_BY_SPEED.idx] && !rectF.isEmpty()) {
                        float height = rectF.height();
                        float max = Math.max(0.5f * height, 1.0f) / height;
                        if (status.acc >= 0.0f) {
                            max = 1.0f;
                        }
                        float max2 = Math.max(max, Math.min(10.0f, status.speed / (Particle.sDensity * 1.5f)));
                        if (max2 != 1.0f) {
                            canvas.scale(1.0f, max2, centerX, centerY);
                        }
                    }
                    float f = rectF.left;
                    float f2 = rectF.top;
                    float f3 = rectF.right;
                    float f4 = rectF.bottom;
                    RectF rectF2 = bitmapParticleTexture.mBounds;
                    float f5 = rectF2.left;
                    float f6 = rectF2.top;
                    float f7 = rectF2.right;
                    float f8 = rectF2.bottom;
                    if (f5 != f || f6 != f2 || f7 != f3 || f8 != f4) {
                        rectF2.set(f, f2, f3, f4);
                    }
                    Bitmap bitmap = bitmapParticleTexture.mBitmap;
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap, (Rect) null, bitmapParticleTexture.mBounds, paint);
                    }
                    canvas.restore();
                }
            }
            particle = particle.next;
        }
    }

    public final void resume() {
        if (this.mIsPaused) {
            Log.i("World", "resume: ");
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.mIsRunning && 0 == this.mStartedTime) {
                this.mStartedTime = elapsedRealtime;
            }
            long j = this.mPausedTime;
            if (0 < j) {
                this.mTotalPausedTime = (elapsedRealtime - j) + this.mTotalPausedTime;
            }
            this.mLastStepTime = elapsedRealtime;
            this.mIsPaused = false;
        }
    }

    public final void setSize(float f, float f2) {
        Status status = this.mWorldStatus;
        status.width = f;
        status.drawingWidth = status.scaleX * f;
        status.height = f2;
        status.drawingHeight = status.scaleY * f2;
        status.posX = f / 2.0f;
        status.posY = f2 / 2.0f;
        status.mUpdateBounds = true;
        Particle.sDensity = this.mContainer.getAppContext().getResources().getDisplayMetrics().density;
    }

    public final void start() {
        Log.i("World", "start: ");
        if (this.mIsRunning) {
            return;
        }
        this.mIsRunning = true;
        if (!this.mIsPaused) {
            this.mStartedTime = SystemClock.elapsedRealtime();
        }
        this.mRootParticle.start(0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0258  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void step(long r36) {
        /*
            Method dump skipped, instructions count: 730
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.nexus.particle.emitter.World.step(long):void");
    }
}
