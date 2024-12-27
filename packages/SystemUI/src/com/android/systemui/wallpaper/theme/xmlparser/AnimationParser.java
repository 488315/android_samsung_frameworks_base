package com.android.systemui.wallpaper.theme.xmlparser;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        if (!parserData.mIsStartTag) {
            String str = this.mAttribute;
            if (str.equalsIgnoreCase("ImageResource") && (frameImageView = parserData.mFrameImageView) != null) {
                frameImageView.mApkResources = parserData.mApkResources;
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
                float f2 = (float) ((animationBuilder.to / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.to = f2;
                ofFloat = ValueAnimator.ofFloat(f, f2);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.3
                    public AnonymousClass3() {
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        ImageView imageView = animationBuilder2.imageView;
                        float f3 = animationBuilder2.a;
                        float f4 = animationBuilder2.r;
                        float f5 = animationBuilder2.from;
                        imageView.setX((f4 * ((float) Math.cos((valueAnimator.getAnimatedFraction() * (animationBuilder2.to - f5)) + f5))) + f3);
                        AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                        ImageView imageView2 = animationBuilder3.imageView;
                        float f6 = animationBuilder3.b;
                        float f7 = animationBuilder3.r;
                        float f8 = animationBuilder3.from;
                        imageView2.setY((f7 * ((float) Math.sin((valueAnimator.getAnimatedFraction() * (animationBuilder3.to - f8)) + f8))) + f6);
                    }
                });
            } else if (str.equals("ellipse")) {
                animationBuilder.imageView = frameImageView2;
                float f3 = (float) ((animationBuilder.from / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.from = f3;
                float f4 = (float) ((animationBuilder.to / 360.0f) * 2.0f * 3.141592653589793d);
                animationBuilder.to = f4;
                ofFloat = ValueAnimator.ofFloat(f3, f4);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.4
                    public AnonymousClass4() {
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                        ImageView imageView = animationBuilder2.imageView;
                        float f5 = animationBuilder2.a;
                        float f6 = animationBuilder2.ra;
                        float f7 = animationBuilder2.from;
                        imageView.setX((f6 * ((float) Math.cos((valueAnimator.getAnimatedFraction() * (animationBuilder2.to - f7)) + f7))) + f5);
                        AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                        ImageView imageView2 = animationBuilder3.imageView;
                        float f8 = animationBuilder3.b;
                        float f9 = animationBuilder3.rb;
                        float f10 = animationBuilder3.from;
                        imageView2.setY((f9 * ((float) Math.sin((valueAnimator.getAnimatedFraction() * (animationBuilder3.to - f10)) + f10))) + f8);
                    }
                });
            } else if (str.equals("parabola")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.ParabolaEvaluator(animationBuilder.key, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.5
                        public AnonymousClass5() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.dx + animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(((animationBuilder3.to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                            animationBuilder4.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue() + animationBuilder4.dy);
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.ParabolaEvaluatorReverse(animationBuilder.key, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.6
                        public AnonymousClass6() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.dx + animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(f5 - ((animationBuilder3.from - animationBuilder3.to) * animatedFraction));
                            AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                            animationBuilder4.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue() + animationBuilder4.dy);
                        }
                    });
                }
            } else if (str.equals("sinX")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinXEvaluator(animationBuilder.key, animationBuilder.adjust, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.7
                        public AnonymousClass7() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(((animationBuilder3.to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder.this.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinXEvaluatorReverse(animationBuilder.key, animationBuilder.adjust, animationBuilder.xOffSet, animationBuilder.yOffSet), Float.valueOf(animationBuilder.to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.8
                        public AnonymousClass8() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setX(f5 - ((animationBuilder3.from - animationBuilder3.to) * animatedFraction));
                            AnimationBuilder.this.imageView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                }
            } else if (str.equals("sinY")) {
                animationBuilder.imageView = frameImageView2;
                if (animationBuilder.to > animationBuilder.from) {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinYEvaluator(animationBuilder.key, animationBuilder.adjust, animationBuilder.yOffSet, animationBuilder.xOffSet), Float.valueOf(animationBuilder.from), Float.valueOf(animationBuilder.to));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.9
                        public AnonymousClass9() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setY(((animationBuilder3.to - animationBuilder3.from) * animatedFraction) + f5);
                            AnimationBuilder.this.imageView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                } else {
                    ofFloat = ValueAnimator.ofObject(new AnimationBuilder.SinYEvaluatorReverse(animationBuilder.key, animationBuilder.adjust, animationBuilder.yOffSet, animationBuilder.xOffSet), Float.valueOf(animationBuilder.to), Float.valueOf(animationBuilder.from));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.10
                        public AnonymousClass10() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AnimationBuilder animationBuilder2 = AnimationBuilder.this;
                            ImageView imageView = animationBuilder2.imageView;
                            float f5 = animationBuilder2.from;
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            AnimationBuilder animationBuilder3 = AnimationBuilder.this;
                            imageView.setY(f5 - ((animationBuilder3.from - animationBuilder3.to) * animatedFraction));
                            AnimationBuilder.this.imageView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                }
            } else if (str.equals("ImageResource")) {
                int i = animationBuilder.length;
                int[] iArr = new int[i];
                int[] iArr2 = new int[i];
                frameImageView2.mImageSetIds = new int[i];
                animationBuilder.elementDuration = animationBuilder.duration / (i - 1);
                for (int i2 = 0; i2 < animationBuilder.length; i2++) {
                    iArr2[i2] = i2;
                    int i3 = animationBuilder.imageViewId;
                    animationBuilder.imageViewId = i3 + 1;
                    iArr[i2] = i3;
                    frameImageView2.mImageSetIds[i2] = i3;
                }
                ofFloat = ValueAnimator.ofInt(iArr2);
                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.wallpaper.theme.builder.AnimationBuilder.1
                    public final /* synthetic */ FrameImageView val$view;

                    public AnonymousClass1(FrameImageView frameImageView22) {
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

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                            if (frameImageView != null) {
                                frameImageView.setImageBitmap(this.bitmap);
                                if (r2.mUsed != null) {
                                    Log.i("ImageWallpaper_AnimationBuilder", "recycle bitmap : " + r2.mUsed);
                                    r2.mUsed.recycle();
                                }
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                FrameImageView frameImageView2 = r2;
                                frameImageView2.mUsed = this.bitmap;
                                this.bitmap = null;
                                AnimationBuilder animationBuilder = AnimationBuilder.this;
                                int i = r2;
                                animationBuilder.preSequence = i;
                                frameImageView2.mQueue.remove(Integer.valueOf(i));
                            }
                        }
                    }

                    public AnonymousClass2(FrameImageView frameImageView22) {
                        r2 = frameImageView22;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int i4;
                        ((Integer) valueAnimator.getAnimatedValue()).getClass();
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
                                    if (frameImageView3 != null) {
                                        frameImageView3.setImageBitmap(this.bitmap);
                                        if (r2.mUsed != null) {
                                            Log.i("ImageWallpaper_AnimationBuilder", "recycle bitmap : " + r2.mUsed);
                                            r2.mUsed.recycle();
                                        }
                                        AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                        FrameImageView frameImageView22 = r2;
                                        frameImageView22.mUsed = this.bitmap;
                                        this.bitmap = null;
                                        AnimationBuilder animationBuilder4 = AnimationBuilder.this;
                                        int i6 = r2;
                                        animationBuilder4.preSequence = i6;
                                        frameImageView22.mQueue.remove(Integer.valueOf(i6));
                                    }
                                }
                            }.execute(new Void[0]);
                        }
                    }
                });
            } else {
                ofFloat = ObjectAnimator.ofFloat(frameImageView22, str, animationBuilder.from, animationBuilder.to);
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
        boolean z = parserData.mIsWallpaper;
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i4 = 0; i4 < attributeCount; i4++) {
            String attributeName = xmlPullParser.getAttributeName(i4);
            String attributeValue = xmlPullParser.getAttributeValue(i4);
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue)) {
                animationBuilder2.dx = parserData.mScaledDx;
                animationBuilder2.dy = parserData.mScaledDy;
                if (attributeName.equalsIgnoreCase("fromDegrees")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toDegrees")) {
                    animationBuilder2.to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("key")) {
                    animationBuilder2.key = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("xFrom")) {
                    animationBuilder2.from = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("xTo")) {
                    animationBuilder2.to = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
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
                    animationBuilder2.to = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("r")) {
                    animationBuilder2.r = (Float.parseFloat(attributeValue) * f5) + 0.5f;
                } else if (attributeName.equalsIgnoreCase("a")) {
                    animationBuilder2.a = parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("b")) {
                    animationBuilder2.b = parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("ra")) {
                    animationBuilder2.ra = parserData.getDevicePixelX(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("rb")) {
                    animationBuilder2.rb = parserData.getDevicePixelY(Float.parseFloat(attributeValue));
                } else if (attributeName.equalsIgnoreCase("fromAlpha")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toAlpha")) {
                    animationBuilder2.to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("fromXDelta")) {
                    animationBuilder2.from = z ? parserData.getDevicePixelX(Float.parseFloat(attributeValue)) : parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("toXDelta")) {
                    animationBuilder2.to = z ? parserData.getDevicePixelX(Float.parseFloat(attributeValue)) : parserData.getDevicePixelX(Float.parseFloat(attributeValue)) + parserData.mScaledDx;
                } else if (attributeName.equalsIgnoreCase("fromYDelta")) {
                    animationBuilder2.from = z ? parserData.getDevicePixelY(Float.parseFloat(attributeValue)) : parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("toYDelta")) {
                    animationBuilder2.to = z ? parserData.getDevicePixelY(Float.parseFloat(attributeValue)) : parserData.getDevicePixelY(Float.parseFloat(attributeValue)) + parserData.mScaledDy;
                } else if (attributeName.equalsIgnoreCase("fromXScale")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toXScale")) {
                    animationBuilder2.to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("fromYScale")) {
                    animationBuilder2.from = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("toYScale")) {
                    animationBuilder2.to = Float.parseFloat(attributeValue);
                } else if (attributeName.equalsIgnoreCase("length")) {
                    animationBuilder2.length = Integer.parseInt(attributeValue);
                } else if (attributeName.equalsIgnoreCase("image")) {
                    animationBuilder2.imageViewId = parserData.mApkResources.getIdentifier(attributeValue, BriefViewController.URI_PATH_DRAWABLE, parserData.mPkgName);
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
        }
        parserData.mAnimationBuilder = animationBuilder2;
    }
}
