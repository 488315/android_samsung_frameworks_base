package com.android.systemui.wallpaper.theme.xmlparser;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AnimationParser extends BaseParser {
    public final String mAttribute;

    public AnimationParser(String str) {
        this.mAttribute = str;
    }

    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        XmlPullParser xmlPullParser;
        ValueAnimator ofFloat;
        FrameImageView frameImageView;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        boolean z = parserData.mIsStartTag;
        int i = 0;
        Resources resources = parserData.mApkResources;
        if (!z) {
            String str = this.mAttribute;
            if (str.equalsIgnoreCase("ImageResource") && (frameImageView = parserData.mFrameImageView) != null) {
                frameImageView.mApkResources = resources;
            }
            ComplexAnimationBuilder complexAnimationBuilder = parserData.mComplexAnimationBuilder;
            if (complexAnimationBuilder == null) {
                return;
            }
            AnimationBuilder animationBuilder = parserData.mAnimationBuilder;
            FrameImageView frameImageView2 = parserData.mFrameImageView;
            animationBuilder.getClass();
            if (str.equals("round")) {
                animationBuilder.imageView = frameImageView2;
                float f = (float) ((animationBuilder.from / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.from = f;
                float f2 = (float) ((animationBuilder.f410to / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.f410to = f2;
                ofFloat = ValueAnimator.ofFloat(f, f2);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.3
                    public C36833() {
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        ImageView imageView = animationBuilder2.imageView;
                        float f3 = animationBuilder2.f403a;
                        float f4 = animationBuilder2.f407r;
                        float f5 = animationBuilder2.from;
                        imageView.setX((f4 * ((float) Math.cos((valueAnimator.getAnimatedFraction() * (animationBuilder2.f410to - f5)) + f5))) + f3);
                        AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                        ImageView imageView2 = animationBuilder3.imageView;
                        float f6 = animationBuilder3.f404b;
                        float f7 = animationBuilder3.f407r;
                        float f8 = animationBuilder3.from;
                        imageView2.setY((f7 * ((float) Math.sin((valueAnimator.getAnimatedFraction() * (animationBuilder3.f410to - f8)) + f8))) + f6);
                    }
                });
            } else if (str.equals("ellipse")) {
                animationBuilder.imageView = frameImageView2;
                float f3 = (float) ((animationBuilder.from / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.from = f3;
                float f4 = (float) ((animationBuilder.f410to / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.f410to = f4;
                ofFloat = ValueAnimator.ofFloat(f3, f4);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.4
                    public C36844() {
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        ImageView imageView = animationBuilder2.imageView;
                        float f5 = animationBuilder2.f403a;
                        float f6 = animationBuilder2.f408ra;
                        float f7 = animationBuilder2.from;
                        imageView.setX((f6 * ((float) Math.cos((valueAnimator.getAnimatedFraction() * (animationBuilder2.f410to - f7)) + f7))) + f5);
                        AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                        ImageView imageView2 = animationBuilder3.imageView;
                        float f8 = animationBuilder3.f404b;
                        float f9 = animationBuilder3.f409rb;
                        float f10 = animationBuilder3.from;
                        imageView2.setY((f9 * ((float) Math.sin((valueAnimator.getAnimatedFraction() * (animationBuilder3.f410to - f10)) + f10))) + f8);
                    }
                });
            } else if (str.equals("parabola")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.f410to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.ParabolaEvaluator(animationBuilder.key, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.f410to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.5
                        public C36855() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.f405dx + animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(((animationBuilder3.f410to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                            animationBuilder4.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue() + animationBuilder4.f406dy);
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.ParabolaEvaluatorReverse(animationBuilder.key, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.f410to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.6
                        public C36866() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.f405dx + animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(f5 - ((animationBuilder3.from - animationBuilder3.f410to) * animatedFraction));
                            AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                            animationBuilder4.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue() + animationBuilder4.f406dy);
                        }
                    });
                }
            } else if (str.equals("sinX")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.f410to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinXEvaluator(animationBuilder.key, animationBuilder.adjust, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.f410to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.7
                        public C36877() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(((animationBuilder3.f410to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder.this.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinXEvaluatorReverse(animationBuilder.key, animationBuilder.adjust, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.f410to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.8
                        public C36888() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(f5 - ((animationBuilder3.from - animationBuilder3.f410to) * animatedFraction));
                            AnimationBuilder.this.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                }
            } else if (str.equals("sinY")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.f410to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinYEvaluator(animationBuilder.key, animationBuilder.adjust, animationBuilder.yOffSet, animationBuilder.xOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.f410to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.9
                        public C36899() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setY(((animationBuilder3.f410to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder.this.imageView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinYEvaluatorReverse(animationBuilder.key, animationBuilder.adjust, animationBuilder.yOffSet, animationBuilder.xOffSet), Float.valueOf(animationBuilder.f410to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.10
                        public C368110() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setY(f5 - ((animationBuilder3.from - animationBuilder3.f410to) * animatedFraction));
                            AnimationBuilder.this.imageView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                }
            } else if (str.equals("ImageResource")) {
                int i2 = animationBuilder.length;
                int[] iArr = new int[i2];
                int[] iArr2 = new int[i2];
                frameImageView2.mImageSetIds = new int[i2];
                animationBuilder.elementDuration = animationBuilder.duration / (i2 - 1);
                while (i < animationBuilder.length) {
                    iArr2[i] = i;
                    int i3 = animationBuilder.imageViewId;
                    animationBuilder.imageViewId = i3 + 1;
                    iArr[i] = i3;
                    frameImageView2.mImageSetIds[i] = i3;
                    i++;
                }
                ofFloat = ValueAnimator.ofInt(iArr2);
                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.1
                    public final /* synthetic */ FrameImageView val$view;

                    public C36801(FrameImageView frameImageView22) {
                        r2 = frameImageView22;
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        animationBuilder2.isAnimationStarted = false;
                        animationBuilder2.startTime = 0L;
                        r2.mQueue.clear();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        AnimationBuilder.this.isAnimationStarted = true;
                        r2.mQueue.clear();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }
                });
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.2
                    public final /* synthetic */ FrameImageView val$view;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.wallpaper.theme.builder.AnimationBuilder$2$1 */
                    public final class AnonymousClass1 extends AsyncTask {
                        public Bitmap bitmap = null;
                        public final /* synthetic */ int val$sequence;

                        public AnonymousClass1(int i) {
                            r2 = i;
                        }

                        @Override // android.os.AsyncTask
                        public final Object doInBackground(Object[] objArr) {
                            FrameImageView frameImageView = r2;
                            if (frameImageView == null) {
                                return null;
                            }
                            this.bitmap = BitmapFactory.decodeResource(frameImageView.mApkResources, frameImageView.mImageSetIds[r2]);
                            return null;
                        }

                        @Override // android.os.AsyncTask
                        public final void onPostExecute(Object obj) {
                            FrameImageView frameImageView = r2;
                            if (frameImageView == null || !frameImageView.isAttachedToWindow()) {
                                return;
                            }
                            r2.setImageBitmap(this.bitmap);
                            Bitmap bitmap = r2.mUsed;
                            if (bitmap != null) {
                                bitmap.recycle();
                            }
                            C36822 c36822 = C36822.this;
                            FrameImageView frameImageView2 = r2;
                            frameImageView2.mUsed = this.bitmap;
                            this.bitmap = null;
                            AnimationBuilder animationBuilder = AnimationBuilder.this;
                            int i = r2;
                            animationBuilder.preSequence = i;
                            frameImageView2.mQueue.remove(Integer.valueOf(i));
                        }
                    }

                    public C36822(FrameImageView frameImageView22) {
                        r2 = frameImageView22;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i4;
                        ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        long currentTimeMillis = System.currentTimeMillis();
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        if (currentTimeMillis - animationBuilder2.startTime < animationBuilder2.elementDuration) {
                            return;
                        }
                        animationBuilder2.startTime = System.currentTimeMillis();
                        if (r2.mQueue.size() < 5) {
                            if (r2.mQueue.size() <= 0 || r2.mQueue.peekLast() == null) {
                                AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                                if (!animationBuilder3.isAnimationStarted) {
                                    animationBuilder3.preSequence = -1;
                                }
                                i4 = animationBuilder3.preSequence;
                            } else {
                                i4 = ((Integer) r2.mQueue.peekLast()).intValue();
                            }
                            int i5 = i4 < AnimationBuilder.this.length + (-2) ? i4 + 1 : 0;
                            r2.mQueue.add(Integer.valueOf(i5));
                            new AsyncTask() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.2.1
                                public Bitmap bitmap = null;
                                public final /* synthetic */ int val$sequence;

                                public AnonymousClass1(int i52) {
                                    r2 = i52;
                                }

                                @Override // android.os.AsyncTask
                                public final Object doInBackground(Object[] objArr) {
                                    FrameImageView frameImageView3 = r2;
                                    if (frameImageView3 == null) {
                                        return null;
                                    }
                                    this.bitmap = BitmapFactory.decodeResource(frameImageView3.mApkResources, frameImageView3.mImageSetIds[r2]);
                                    return null;
                                }

                                @Override // android.os.AsyncTask
                                public final void onPostExecute(Object obj) {
                                    FrameImageView frameImageView3 = r2;
                                    if (frameImageView3 == null || !frameImageView3.isAttachedToWindow()) {
                                        return;
                                    }
                                    r2.setImageBitmap(this.bitmap);
                                    Bitmap bitmap = r2.mUsed;
                                    if (bitmap != null) {
                                        bitmap.recycle();
                                    }
                                    C36822 c36822 = C36822.this;
                                    FrameImageView frameImageView22 = r2;
                                    frameImageView22.mUsed = this.bitmap;
                                    this.bitmap = null;
                                    AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                                    int i6 = r2;
                                    animationBuilder4.preSequence = i6;
                                    frameImageView22.mQueue.remove(Integer.valueOf(i6));
                                }
                            }.execute(new Void[0]);
                        }
                    }
                });
            } else {
                ofFloat = ObjectAnimator.ofFloat(frameImageView22, str, animationBuilder.from, animationBuilder.f410to);
            }
            ofFloat.setStartDelay(animationBuilder.delay);
            ofFloat.setDuration(animationBuilder.duration);
            ofFloat.setRepeatCount(animationBuilder.repeatCount);
            ofFloat.setRepeatMode(animationBuilder.repeatMode);
            ofFloat.setInterpolator(animationBuilder.interpolator);
            complexAnimationBuilder.mAnimatorSet.playTogether(ofFloat);
            return;
        }
        AnimationBuilder animationBuilder2 = new AnimationBuilder();
        float f5 = parserData.mDeviceDensity;
        boolean z2 = parserData.mIsWallpaper;
        int attributeCount = xmlPullParser.getAttributeCount();
        while (i < attributeCount) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue)) {
                animationBuilder2.f405dx = parserData.mScaledDx;
                animationBuilder2.f406dy = parserData.mScaledDy;
                if (attributeName.equalsIgnoreCase("fromDegrees")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toDegrees")) {
                    animationBuilder2.f410to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("key")) {
                    animationBuilder2.key = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("xFrom")) {
                    animationBuilder2.from = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("xTo")) {
                    animationBuilder2.f410to = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("xOffSet")) {
                    animationBuilder2.xOffSet = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("yOffSet")) {
                    animationBuilder2.yOffSet = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("key")) {
                    animationBuilder2.key = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("adjust")) {
                    animationBuilder2.adjust = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("yFrom")) {
                    animationBuilder2.from = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("yTo")) {
                    animationBuilder2.f410to = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("r")) {
                    animationBuilder2.f407r = (Float.parseFloat(attributeValue) * f5) + 0.5f;
                } else if (attributeName.equalsIgnoreCase("a")) {
                    animationBuilder2.f403a = parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("b")) {
                    animationBuilder2.f404b = parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("ra")) {
                    animationBuilder2.f408ra = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("rb")) {
                    animationBuilder2.f409rb = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("fromAlpha")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toAlpha")) {
                    animationBuilder2.f410to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("fromXDelta")) {
                    animationBuilder2.from = z2 ? parserData.getDevicePixelX(Float.parseFloat(attributeValue)) : parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("toXDelta")) {
                    animationBuilder2.f410to = z2 ? parserData.getDevicePixelX(Float.parseFloat(attributeValue)) : parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("fromYDelta")) {
                    animationBuilder2.from = z2 ? parserData.getDevicePixelY(Float.parseFloat(attributeValue)) : parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("toYDelta")) {
                    animationBuilder2.f410to = z2 ? parserData.getDevicePixelY(Float.parseFloat(attributeValue)) : parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("fromXScale")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toXScale")) {
                    animationBuilder2.f410to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("fromYScale")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toYScale")) {
                    animationBuilder2.f410to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("length")) {
                    animationBuilder2.length = Integer.parseInt(attributeValue);
                } else if (attributeName.equalsIgnoreCase("image")) {
                    animationBuilder2.imageViewId = resources.getIdentifier(attributeValue, "drawable", parserData.mPkgName);
                } else if (attributeName.equalsIgnoreCase("duration")) {
                    animationBuilder2.duration = Long.parseLong(attributeValue);
                } else if (attributeName.equalsIgnoreCase("repeatCount")) {
                    animationBuilder2.repeatCount = Integer.parseInt(attributeValue);
                } else if (attributeName.equalsIgnoreCase("repeatMode")) {
                    animationBuilder2.repeatMode = Integer.parseInt(attributeValue);
                } else if (attributeName.equalsIgnoreCase("delay")) {
                    animationBuilder2.delay = Long.parseLong(attributeValue);
                } else if (attributeName.equalsIgnoreCase("accelerateInterpolator")) {
                    if (attributeValue.equals("default")) {
                        animationBuilder2.interpolator = new AccelerateInterpolator();
                    } else {
                        animationBuilder2.interpolator = new AccelerateInterpolator(Float.parseFloat(attributeValue));
                    }
                } else if (attributeName.equalsIgnoreCase("decelerateInterpolator")) {
                    if (attributeValue.equals("default")) {
                        animationBuilder2.interpolator = new DecelerateInterpolator();
                    } else {
                        animationBuilder2.interpolator = new DecelerateInterpolator(Float.parseFloat(attributeValue));
                    }
                } else if (attributeName.equalsIgnoreCase("accelerateDecelerateInterpolator")) {
                    animationBuilder2.interpolator = new AccelerateDecelerateInterpolator();
                } else if (attributeName.equalsIgnoreCase("normalSpeed")) {
                    animationBuilder2.interpolator = null;
                }
            }
            i++;
        }
        parserData.mAnimationBuilder = animationBuilder2;
    }
}
