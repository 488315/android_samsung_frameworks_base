package com.samsung.android.nexus.particle.emitter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.Particle;
import com.samsung.android.nexus.particle.emitter.texture.ParticleTexture;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class World {
    public static final boolean DEBUG_ANY = false;
    public static final long DEBUG_UPDATE_DELAY;
    public static long sDebugUpdateTime;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WorldParticleLinkedList extends Particle.ParticleLinkedList {
        private WorldParticleLinkedList() {
        }
    }

    static {
        Paint paint = new Paint();
        DEBUG_UPDATE_DELAY = 500L;
        sDebugUpdateTime = 0L;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setTextSize(20.0f);
        paint.setColor(-65536);
    }

    public World(LayerContainer layerContainer) {
        Log.m262i("World", "World: created");
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
        int i;
        if (!this.mIsRunning || this.mIsPaused) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        WorldParticleLinkedList worldParticleLinkedList = this.mWorldParticleLinkedList;
        Particle particle = worldParticleLinkedList.head;
        int i2 = worldParticleLinkedList.size;
        int i3 = 0;
        while (i3 < i2) {
            ParticleTexture particleTexture = particle.mParticleTexture;
            if (particleTexture != null && particle.mEnable && particle.mIsInSight) {
                Status status = particle.status;
                if (status.alpha >= 0.0f) {
                    ParticleRule particleRule = particle.mParentEmitter.mParticleRule;
                    boolean z = status.mUpdateBounds;
                    RectF rectF = status.mBounds;
                    if (z) {
                        float f = status.drawingWidth / 2.0f;
                        float f2 = status.drawingHeight / 2.0f;
                        float f3 = status.posX;
                        float f4 = status.posY;
                        rectF.set(f3 - f, f4 - f2, f3 + f, f4 + f2);
                        status.mUpdateBounds = false;
                    }
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
                    float f5 = rectF.left;
                    float f6 = rectF.top;
                    float f7 = rectF.right;
                    float f8 = rectF.bottom;
                    RectF rectF2 = particleTexture.mBounds;
                    float f9 = rectF2.left;
                    float f10 = rectF2.top;
                    float f11 = rectF2.right;
                    i = i2;
                    float f12 = rectF2.bottom;
                    if (f9 != f5 || f10 != f6 || f11 != f7 || f12 != f8) {
                        rectF2.set(f5, f6, f7, f8);
                        particleTexture.onBoundChanged();
                    }
                    particleTexture.draw(canvas, paint);
                    canvas.restore();
                    particle = particle.next;
                    i3++;
                    i2 = i;
                }
            }
            i = i2;
            particle = particle.next;
            i3++;
            i2 = i;
        }
        if (!DEBUG_ANY || sDebugUpdateTime + DEBUG_UPDATE_DELAY >= currentTimeMillis) {
            return;
        }
        sDebugUpdateTime = currentTimeMillis;
    }

    public final void resume() {
        if (this.mIsPaused) {
            Log.m262i("World", "resume: ");
            long currentTimeMillis = System.currentTimeMillis();
            if (this.mIsRunning && 0 == this.mStartedTime) {
                this.mStartedTime = currentTimeMillis;
            }
            long j = this.mPausedTime;
            if (0 < j) {
                this.mTotalPausedTime = (currentTimeMillis - j) + this.mTotalPausedTime;
            }
            this.mLastStepTime = currentTimeMillis;
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
        Log.m262i("World", "start: ");
        if (this.mIsRunning) {
            return;
        }
        this.mIsRunning = true;
        if (!this.mIsPaused) {
            this.mStartedTime = System.currentTimeMillis();
        }
        this.mRootParticle.start(0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void step(long j) {
        long j2;
        int i;
        int i2;
        WorldParticleLinkedList worldParticleLinkedList;
        RectF rectF;
        float f;
        float[] fArr;
        boolean z;
        ParticleRule particleRule;
        boolean z2;
        int i3;
        long j3;
        int i4;
        int i5;
        World world = this;
        long nanoTime = System.nanoTime();
        if (!world.mIsRunning || world.mIsPaused) {
            return;
        }
        float f2 = world.mLastStepTime == 0 ? 0.0f : (j - r5) / 1000.0f;
        world.mLastStepTime = j;
        long j4 = (j - world.mStartedTime) - world.mTotalPausedTime;
        Particle particle = world.mRootParticle;
        if (!(!particle.mEnable || (j4 > particle.mEndTime && particle.mEmitterSchedules.isEmpty()))) {
            particle.checkEmitterSchedule(j4, null);
        }
        WorldParticleLinkedList worldParticleLinkedList2 = world.mWorldParticleLinkedList;
        Particle particle2 = worldParticleLinkedList2.head;
        int i6 = worldParticleLinkedList2.size;
        long j5 = j4;
        Particle particle3 = null;
        Particle particle4 = null;
        Particle particle5 = null;
        Particle particle6 = null;
        int i7 = 0;
        int i8 = 0;
        while (i7 < i6) {
            boolean z3 = !particle2.mEnable || (j5 > particle2.mEndTime && particle2.mEmitterSchedules.isEmpty());
            ArrayList arrayList = particle2.mEmitterSchedules;
            RectF rectF2 = particle2.mTempWorldBounds;
            Status status = particle2.status;
            if (z3) {
                particle2.mEnable = false;
                j2 = nanoTime;
                particle2.mStartTime = 0L;
                particle2.mEndTime = 0L;
                arrayList.clear();
                ParticleTexture particleTexture = particle2.mParticleTexture;
                if (particleTexture != null) {
                    particleTexture.onRelease();
                    particle2.mParticleTexture = null;
                }
                particle2.mEnable = false;
                particle2.mEnableEmission = false;
                particle2.mIsInSight = false;
                particle2.mStartTime = -1L;
                particle2.mEndTime = -1L;
                particle2.mLifeTime = -1L;
                particle2.mFraction = 0.0f;
                particle2.mParentEmitter = null;
                particle2.mSubEmitterKey = "";
                status.reset();
                ParticleTexture particleTexture2 = particle2.mParticleTexture;
                if (particleTexture2 != null) {
                    particleTexture2.onRelease();
                    particle2.mParticleTexture = null;
                }
                arrayList.clear();
                particle2.mTempEmitterSchedules.clear();
                particle2.mScheduleCheckLock = false;
                rectF2.setEmpty();
                if (i8 == 0) {
                    particle4 = particle2;
                    particle6 = particle3;
                }
                i8++;
                worldParticleLinkedList = worldParticleLinkedList2;
                particle5 = particle2;
                i4 = i7;
                i5 = i6;
                j3 = j4;
            } else {
                j2 = nanoTime;
                if (particle2.mEnable) {
                    ParticleRule particleRule2 = particle2.mParentEmitter.mParticleRule;
                    i = i7;
                    int i9 = i6;
                    float f3 = (j5 - particle2.mStartTime) / particle2.mLifeTime;
                    particle2.mFraction = f3;
                    boolean[] zArr = particleRule2.configValues;
                    boolean z4 = zArr[ParticleConfigType.AUTO_ROTATE_ALONG_MOVE_DIRECTION.idx];
                    i2 = i9;
                    boolean z5 = zArr[ParticleConfigType.DISABLE_WHEN_DISAPPEARED.idx];
                    worldParticleLinkedList = worldParticleLinkedList2;
                    boolean z6 = zArr[ParticleConfigType.DISABLE_WHEN_OUTSIDE.idx];
                    float f4 = status.posX;
                    long j6 = j4;
                    float f5 = status.posY;
                    int i10 = status.color;
                    Status status2 = world.mWorldStatus;
                    if (status2 == null) {
                        rectF = rectF2;
                        f = f4;
                        z = true;
                        fArr = null;
                    } else {
                        rectF = rectF2;
                        f = f4;
                        long j7 = particleRule2.lastWorldFactorUpdateTime;
                        fArr = particleRule2.tempWorldFactorValues;
                        if (j7 != j5) {
                            particleRule2.lastWorldFactorUpdateTime = j5;
                            System.arraycopy(status2.factor.values, 0, fArr, 0, fArr.length);
                            FactorType factorType = FactorType.WIDTH;
                            FactorType[] factorTypeArr = FactorType.Holder.sValuesCache;
                            int length = factorTypeArr.length;
                            int i11 = 0;
                            while (i11 < length) {
                                FactorType factorType2 = factorTypeArr[i11];
                                FactorType[] factorTypeArr2 = factorTypeArr;
                                int i12 = factorType2.opType;
                                int i13 = length;
                                boolean[] zArr2 = particleRule2.applyWorldFactorCheckList;
                                if (i12 != 0) {
                                    particleRule = particleRule2;
                                    if (i12 == 1) {
                                        int i14 = factorType2.valueIdx;
                                        fArr[i14] = zArr2[i14] ? fArr[i14] : 1.0f;
                                        int i15 = factorType2.speedIdx;
                                        fArr[i15] = zArr2[i15] ? fArr[i15] : 0.0f;
                                        int i16 = factorType2.accelerationIdx;
                                        fArr[i16] = zArr2[i16] ? fArr[i16] : 0.0f;
                                    }
                                } else {
                                    particleRule = particleRule2;
                                    int i17 = factorType2.valueIdx;
                                    fArr[i17] = fArr[i17] * (zArr2[i17] ? 1.0f : 0.0f);
                                    int i18 = factorType2.speedIdx;
                                    fArr[i18] = fArr[i18] * (zArr2[i18] ? 1.0f : 0.0f);
                                    int i19 = factorType2.accelerationIdx;
                                    fArr[i19] = fArr[i19] * (zArr2[i19] ? 1.0f : 0.0f);
                                }
                                i11++;
                                factorTypeArr = factorTypeArr2;
                                length = i13;
                                particleRule2 = particleRule;
                            }
                        }
                        z = true;
                    }
                    status.onStep(f3, f2, fArr);
                    if (z4) {
                        status.rotation = (((float) Math.toDegrees(Math.atan2(status.posY - f5, status.posX - f) + 1.5707963267948966d)) + 360.0f) % 360.0f;
                    }
                    int i20 = status.color;
                    if (i10 != i20) {
                        particle2.mColorFilter = new PorterDuffColorFilter(i20, PorterDuff.Mode.SRC_ATOP);
                    }
                    boolean z7 = status2.mUpdateBounds;
                    RectF rectF3 = status2.mBounds;
                    if (z7) {
                        float f6 = status2.drawingWidth / 2.0f;
                        float f7 = status2.drawingHeight / 2.0f;
                        float f8 = status2.posX;
                        float f9 = status2.posY;
                        rectF3.set(f8 - f6, f9 - f7, f8 + f6, f9 + f7);
                        status2.mUpdateBounds = false;
                    }
                    RectF rectF4 = rectF;
                    rectF4.set(rectF3);
                    boolean z8 = status.mUpdateBounds;
                    RectF rectF5 = status.mBounds;
                    if (z8) {
                        float f10 = status.drawingWidth / 2.0f;
                        float f11 = status.drawingHeight / 2.0f;
                        float f12 = status.posX;
                        float f13 = status.posY;
                        rectF5.set(f12 - f10, f13 - f11, f12 + f10, f13 + f11);
                        status.mUpdateBounds = false;
                    }
                    float width = rectF5.width();
                    float height = rectF5.height();
                    rectF4.left -= width;
                    rectF4.top -= height;
                    rectF4.right += width;
                    rectF4.bottom += height;
                    boolean contains = rectF4.contains(rectF5);
                    FactorType factorType3 = FactorType.ALPHA;
                    if (status.factorKeyFrameSetList.list[factorType3.idx] == null) {
                        float[] fArr2 = status.factor.values;
                        if (fArr2[factorType3.valueIdx] <= 0.0f && fArr2[factorType3.speedIdx] <= 0.0f && fArr2[factorType3.accelerationIdx] <= 0.0f) {
                            z2 = z;
                            particle2.mIsInSight = contains;
                            particle2.mEnableEmission = (contains && z6) ? false : z;
                            if (!arrayList.isEmpty()) {
                                i3 = 0;
                                particle2.mEnable = (!contains || (z5 && z2)) ? false : z;
                            } else if ((!z6 || contains) && !(z5 && z2)) {
                                i3 = 0;
                            } else {
                                i3 = 0;
                                particle2.mEnable = false;
                            }
                            j3 = j6;
                            particle2.checkEmitterSchedule(j3, status);
                            j5 = j3;
                        }
                    }
                    z2 = false;
                    particle2.mIsInSight = contains;
                    particle2.mEnableEmission = (contains && z6) ? false : z;
                    if (!arrayList.isEmpty()) {
                    }
                    j3 = j6;
                    particle2.checkEmitterSchedule(j3, status);
                    j5 = j3;
                } else {
                    worldParticleLinkedList = worldParticleLinkedList2;
                    i = i7;
                    i2 = i6;
                    j3 = j4;
                    i3 = 0;
                }
                if (i8 > 0) {
                    i4 = i;
                    i5 = i2;
                    Particle.sPool.transferFrom(worldParticleLinkedList, particle6, particle4, particle5, i8);
                    i8 = i3;
                    particle4 = null;
                    particle5 = null;
                } else {
                    i4 = i;
                    i5 = i2;
                }
            }
            i7 = i4 + 1;
            i6 = i5;
            particle3 = particle2;
            nanoTime = j2;
            world = this;
            particle2 = particle2.next;
            j4 = j3;
            worldParticleLinkedList2 = worldParticleLinkedList;
        }
        long j8 = nanoTime;
        WorldParticleLinkedList worldParticleLinkedList3 = worldParticleLinkedList2;
        if (i8 > 0) {
            Particle.sPool.transferFrom(worldParticleLinkedList3, particle6, particle4, particle5, i8);
        }
        if (worldParticleLinkedList3.size == 0) {
            World.this.mFrameController.startFrameRateDown();
        }
        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - j8);
    }
}
