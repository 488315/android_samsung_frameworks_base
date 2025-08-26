package com.samsung.android.nexus.particle.emitter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemClock;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.Particle;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;
import java.util.concurrent.TimeUnit;

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
                    float fCenterX = rectF.centerX();
                    float fCenterY = rectF.centerY();
                    Paint paint = Particle.sPaint;
                    paint.setColorFilter(particle.mColorFilter);
                    paint.setAlpha(Math.min(255, Math.max(0, (int) (status.alpha * 255.0f))));
                    canvas.save();
                    canvas.rotate(status.rotation, fCenterX, fCenterY);
                    if (particleRule.configValues[ParticleConfigType.APPLY_DRAW_MORPHING_BY_SPEED.idx] && !rectF.isEmpty()) {
                        float fHeight = rectF.height();
                        float fMax = Math.max(0.5f * fHeight, 1.0f) / fHeight;
                        if (status.acc >= 0.0f) {
                            fMax = 1.0f;
                        }
                        float fMax2 = Math.max(fMax, Math.min(10.0f, status.speed / (Particle.sDensity * 1.5f)));
                        if (fMax2 != 1.0f) {
                            canvas.scale(1.0f, fMax2, fCenterX, fCenterY);
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
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (this.mIsRunning && 0 == this.mStartedTime) {
                this.mStartedTime = jElapsedRealtime;
            }
            long j = this.mPausedTime;
            if (0 < j) {
                this.mTotalPausedTime = (jElapsedRealtime - j) + this.mTotalPausedTime;
            }
            this.mLastStepTime = jElapsedRealtime;
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x0234  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void step(long j) {
        int i;
        int i2;
        long j2;
        long j3;
        Object obj;
        int i3;
        boolean z;
        float f;
        boolean z2;
        float[] fArr;
        World world = this;
        long jNanoTime = System.nanoTime();
        if (!world.mIsRunning || world.mIsPaused) {
            return;
        }
        float f2 = world.mLastStepTime == 0 ? 0.0f : (j - r6) / 1000.0f;
        world.mLastStepTime = j;
        long j4 = (j - world.mStartedTime) - world.mTotalPausedTime;
        Particle particle = world.mRootParticle;
        if (!(!particle.mEnable || (j4 > particle.mEndTime && particle.mEmitterSchedules.isEmpty()))) {
            particle.checkEmitterSchedule(j4, null);
        }
        WorldParticleLinkedList worldParticleLinkedList = world.mWorldParticleLinkedList;
        Particle particle2 = worldParticleLinkedList.head;
        int i4 = worldParticleLinkedList.size;
        Particle particle3 = null;
        Particle particle4 = null;
        Particle particle5 = null;
        Particle particle6 = null;
        int i5 = 0;
        int i6 = 0;
        while (i5 < i4) {
            if (particle2.mEnable) {
                i = i5;
                if (j4 <= particle2.mEndTime || !particle2.mEmitterSchedules.isEmpty()) {
                    if (particle2.mEnable) {
                        ParticleRule particleRule = particle2.mParentEmitter.mParticleRule;
                        float f3 = j4 - particle2.mStartTime;
                        j2 = jNanoTime;
                        float f4 = f3 / particle2.mLifeTime;
                        if (f4 < 0.0f) {
                            StringBuilder sb = new StringBuilder("onStep: fix wrong fraction: ");
                            sb.append(f4);
                            sb.append(" Play: ");
                            sb.append(j4);
                            sb.append(" Started: ");
                            i3 = i4;
                            sb.append(particle2.mStartTime);
                            sb.append(" life: ");
                            sb.append(particle2.mLifeTime);
                            sb.append(" elapsed: ");
                            sb.append(f3);
                            android.util.Log.w("Nexus[Particle]", sb.toString());
                            f4 = 0.0f;
                        } else {
                            i3 = i4;
                        }
                        particle2.mFraction = f4;
                        boolean[] zArr = particleRule.configValues;
                        boolean z3 = zArr[ParticleConfigType.AUTO_ROTATE_ALONG_MOVE_DIRECTION.idx];
                        boolean z4 = zArr[ParticleConfigType.DISABLE_WHEN_DISAPPEARED.idx];
                        boolean z5 = zArr[ParticleConfigType.DISABLE_WHEN_OUTSIDE.idx];
                        Status status = particle2.status;
                        float f5 = status.posX;
                        float f6 = status.posY;
                        int i7 = status.color;
                        i2 = i3;
                        Status status2 = world.mWorldStatus;
                        if (status2 == null) {
                            z = z3;
                            f = f5;
                            z2 = z4;
                            fArr = null;
                        } else {
                            z = z3;
                            f = f5;
                            long j5 = particleRule.lastWorldFactorUpdateTime;
                            float[] fArr2 = particleRule.tempWorldFactorValues;
                            if (j5 != j4) {
                                particleRule.lastWorldFactorUpdateTime = j4;
                                z2 = z4;
                                System.arraycopy(status2.factor.values, 0, fArr2, 0, fArr2.length);
                                FactorType factorType = FactorType.WIDTH;
                                FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
                                int length = factorTypeArr.length;
                                int i8 = 0;
                                while (i8 < length) {
                                    FactorType[] factorTypeArr2 = factorTypeArr;
                                    FactorType factorType2 = factorTypeArr2[i8];
                                    int i9 = length;
                                    int i10 = factorType2.opType;
                                    float[] fArr3 = fArr2;
                                    boolean[] zArr2 = particleRule.applyWorldFactorCheckList;
                                    if (i10 == 0) {
                                        int i11 = factorType2.valueIdx;
                                        fArr3[i11] = fArr3[i11] * (zArr2[i11] ? 1.0f : 0.0f);
                                        int i12 = factorType2.speedIdx;
                                        fArr3[i12] = fArr3[i12] * (zArr2[i12] ? 1.0f : 0.0f);
                                        int i13 = factorType2.accelerationIdx;
                                        fArr3[i13] = fArr3[i13] * (zArr2[i13] ? 1.0f : 0.0f);
                                    } else if (i10 == 1) {
                                        int i14 = factorType2.valueIdx;
                                        fArr3[i14] = zArr2[i14] ? fArr3[i14] : 1.0f;
                                        int i15 = factorType2.speedIdx;
                                        fArr3[i15] = zArr2[i15] ? fArr3[i15] : 0.0f;
                                        int i16 = factorType2.accelerationIdx;
                                        fArr3[i16] = zArr2[i16] ? fArr3[i16] : 0.0f;
                                    }
                                    i8++;
                                    factorTypeArr = factorTypeArr2;
                                    length = i9;
                                    fArr2 = fArr3;
                                }
                            } else {
                                z2 = z4;
                            }
                            fArr = fArr2;
                        }
                        status.onStep(f4, f2, fArr);
                        if (z) {
                            status.rotation = (((float) Math.toDegrees(Math.atan2(status.posY - f6, status.posX - f) + 1.5707963267948966d)) + 360.0f) % 360.0f;
                        }
                        int i17 = status.color;
                        if (i7 != i17) {
                            particle2.mColorFilter = new PorterDuffColorFilter(i17, PorterDuff.Mode.SRC_ATOP);
                        }
                        RectF rectF = particle2.mTempWorldBounds;
                        if (status2.mUpdateBounds) {
                            status2.updateBounds();
                        }
                        rectF.set(status2.mBounds);
                        RectF rectF2 = particle2.mTempWorldBounds;
                        if (status.mUpdateBounds) {
                            status.updateBounds();
                        }
                        RectF rectF3 = status.mBounds;
                        float fWidth = rectF3.width();
                        float fHeight = rectF3.height();
                        rectF2.left -= fWidth;
                        rectF2.top -= fHeight;
                        rectF2.right += fWidth;
                        rectF2.bottom += fHeight;
                        boolean zContains = rectF2.contains(rectF3);
                        FactorType factorType3 = FactorType.ALPHA;
                        if (status.factorKeyFrameSetList.list[factorType3.idx] == null) {
                            float[] fArr4 = status.factor.values;
                            boolean z6 = fArr4[factorType3.valueIdx] <= 0.0f && fArr4[factorType3.speedIdx] <= 0.0f && fArr4[factorType3.accelerationIdx] <= 0.0f;
                            particle2.mIsInSight = zContains;
                            particle2.mEnableEmission = zContains || !z5;
                            if (particle2.mEmitterSchedules.isEmpty()) {
                                particle2.mEnable = zContains && !(z2 && z6);
                            } else if ((z5 && !zContains) || (z2 && z6)) {
                                particle2.mEnable = false;
                            }
                            particle2.checkEmitterSchedule(j4, status);
                        }
                    } else {
                        j2 = jNanoTime;
                        i2 = i4;
                    }
                    if (i6 > 0) {
                        Particle.sPool.transferFrom(worldParticleLinkedList, particle3, particle4, particle5, i6);
                        obj = null;
                        j3 = 0;
                        particle4 = null;
                        particle5 = null;
                        i6 = 0;
                    } else {
                        obj = null;
                        j3 = 0;
                    }
                }
                i5 = i + 1;
                particle6 = particle2;
                i4 = i2;
                world = this;
                particle2 = particle2.next;
                jNanoTime = j2;
            } else {
                i = i5;
            }
            j2 = jNanoTime;
            i2 = i4;
            particle2.mEnable = false;
            j3 = 0;
            particle2.mStartTime = 0L;
            particle2.mEndTime = 0L;
            particle2.mEmitterSchedules.clear();
            BitmapParticleTexture bitmapParticleTexture = particle2.mParticleTexture;
            if (bitmapParticleTexture != null) {
                bitmapParticleTexture.onRelease();
                obj = null;
                particle2.mParticleTexture = null;
            } else {
                obj = null;
            }
            particle2.release();
            if (i6 == 0) {
                particle4 = particle2;
                particle3 = particle6;
            }
            i6++;
            particle5 = particle2;
            i5 = i + 1;
            particle6 = particle2;
            i4 = i2;
            world = this;
            particle2 = particle2.next;
            jNanoTime = j2;
        }
        long j6 = jNanoTime;
        if (i6 > 0) {
            Particle.sPool.transferFrom(worldParticleLinkedList, particle3, particle4, particle5, i6);
        }
        if (worldParticleLinkedList.size == 0) {
            World.this.mFrameController.startFrameRateDown();
        }
        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - j6);
    }
}
