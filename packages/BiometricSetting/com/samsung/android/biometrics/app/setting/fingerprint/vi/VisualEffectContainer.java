package com.samsung.android.biometrics.app.setting.fingerprint.vi;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsKeyguardSensorWindow;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes.dex */
public class VisualEffectContainer extends FrameLayout {

    @VisibleForTesting
    static final String ASSET_NAME_OPTICAL_GREEN = "indisplay_fingerprint_touch_effect_green_circle.json";

    @VisibleForTesting
    static final String ASSET_NAME_OPTICAL_WHITE = "indisplay_fingerprint_touch_effect_white_circle.json";

    @VisibleForTesting
    static final String ASSET_NAME_ULTRASONIC = "indisplay_fingerprint_touch_effect_ripple.json";
    private long mAnimationDuration;
    private Callback mCallback;
    private final Context mContext;

    @VisibleForTesting
    LottieAnimationView mEffectAnimationView;
    List<Pair<View, Animation>> mEffectViews;
    private final Handler mHandler;
    private boolean mIsReadTouchMap;
    private Thread mReadTouchMapThread;
    private UdfpsInfo mSensorInfo;
    private final VisualEffectContainer$$ExternalSyntheticLambda0 mStartCallback;
    private final VisualEffectContainer$$ExternalSyntheticLambda0 mStopCallback;
    private final SemVisualEffectTouchMap mTouchMap;
    private long mTouchMapStartTime;

    public interface Callback {
    }

    class SemVisualEffectTouchMap {
        private int[][] mCheckArray;
        private Path mEffectPath;
        private final TouchMapReader mTouchMapReader;
        private int mWidth = 0;
        private int mHeight = 0;
        private final int[] EDGE_X = {0, 1, 1, 0};
        private final int[] EDGE_Y = {0, 0, 1, 1};
        private final int[] SEARCH_X = {0, 1, 0, -1};
        private final int[] SEARCH_Y = {-1, 0, 1, 0};

        SemVisualEffectTouchMap(Context context) {
            this.mTouchMapReader = new TouchMapReader(context);
        }

        private static Path calculateClipOutPath(ArrayList arrayList) {
            Path path = new Path();
            if (arrayList.size() >= 3) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    float[] fArr = (float[]) arrayList.get(i % size);
                    int i2 = i + 1;
                    float[] fArr2 = (float[]) arrayList.get(i2 % size);
                    float[] fArr3 = (float[]) arrayList.get((i + 2) % size);
                    if (i == 0) {
                        path.moveTo((fArr[0] + fArr2[0]) / 2.0f, (fArr[1] + fArr2[1]) / 2.0f);
                    }
                    float f = fArr[0];
                    float f2 = fArr2[0];
                    float f3 = (f + f2) / 2.0f;
                    float f4 = fArr[1];
                    float f5 = fArr2[1];
                    path.cubicTo(f3, (f4 + f5) / 2.0f, f2, f5, (fArr3[0] + f2) / 2.0f, (fArr3[1] + f5) / 2.0f);
                    i = i2;
                }
                path.close();
            }
            return path;
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x00ca, code lost:
        
            if (r5 < 0) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00cd, code lost:
        
            if (r4 >= r20.length) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00d2, code lost:
        
            if (r5 < r20[0].length) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00d5, code lost:
        
            r17 = 5;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private ArrayList<float[]> getOuterLine(int[][] iArr) {
            float f;
            float f2;
            float f3;
            float f4;
            VisualEffectContainer visualEffectContainer = VisualEffectContainer.this;
            int width = visualEffectContainer.getWidth();
            float f5 = width;
            float length = f5 / iArr[0].length;
            float height = visualEffectContainer.getHeight();
            float length2 = height / iArr.length;
            TouchMapReader touchMapReader = this.mTouchMapReader;
            if (touchMapReader.displayHCellMax == -1 || touchMapReader.displayVCellMax == -1) {
                f = 0.0f;
                f2 = length;
                f3 = length2;
                f4 = 0.0f;
            } else {
                Point displaySize = Utils.getDisplaySize(visualEffectContainer.mContext);
                float f6 = touchMapReader.displayHCellMax;
                float f7 = touchMapReader.displayVCellMax;
                float f8 = touchMapReader.displayHCellSize;
                float f9 = displaySize.x / f6;
                float f10 = displaySize.y / f7;
                float f11 = (f5 - (f8 * f9)) / 2.0f;
                f = height - (touchMapReader.displayVCellSize * f10);
                f2 = f9;
                f3 = f10;
                f4 = f11;
            }
            ArrayList<Point> arrayList = new ArrayList<>();
            this.mCheckArray = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, iArr.length, iArr[0].length);
            int[][] iArr2 = {new int[]{0, -1}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{1, 0}};
            int length3 = iArr[0].length / 2;
            int length4 = iArr.length / 2;
            int i = length3;
            int i2 = 0;
            int i3 = 1;
            while (i2 < 4) {
                if (i2 % 2 == 0) {
                    i3++;
                }
                int i4 = i3;
                for (int i5 = 0; i5 < i4; i5++) {
                    if (length4 < 0 || i < 0 || length4 >= iArr.length || i >= iArr[0].length || iArr[length4][i] == 1) {
                        int i6 = 4;
                        i2 = i6;
                        i2++;
                        i3 = i4;
                    } else {
                        int[] iArr3 = iArr2[i2];
                        i += iArr3[0];
                        length4 += iArr3[1];
                        if (i2 == 3 && i5 == i4 - 1 && i4 < iArr.length / 2) {
                            i2 = -1;
                        }
                    }
                }
                i2++;
                i3 = i4;
            }
            if (i2 != 6 || iArr[length4][i] != 1) {
                return new ArrayList<>();
            }
            searchNeighborBlock(arrayList, iArr, i, length4, 0);
            arrayList.add(new Point(i, length4));
            int i7 = 0;
            while (true) {
                int i8 = i7 + 2;
                if (arrayList.size() <= i8) {
                    break;
                }
                int i9 = i7 + 1;
                if (arrayList.get(i7).x != arrayList.get(i9).x) {
                    if (arrayList.get(i7).y == arrayList.get(i9).y && arrayList.get(i7).y == arrayList.get(i8).y) {
                        arrayList.remove(i9);
                    }
                    i7 = i9;
                } else if (arrayList.get(i7).x == arrayList.get(i8).x) {
                    arrayList.remove(i9);
                } else {
                    i7 = i9;
                }
            }
            ArrayList<float[]> arrayList2 = new ArrayList<>();
            Iterator<Point> it = arrayList.iterator();
            while (it.hasNext()) {
                Point next = it.next();
                arrayList2.add(new float[]{(next.x * f2) + f4, (next.y * f3) + f});
            }
            int i10 = 0;
            while (i10 < 3) {
                ArrayList<float[]> arrayList3 = new ArrayList<>();
                int i11 = 0;
                while (i11 < arrayList.size()) {
                    float[] fArr = arrayList2.get(i11);
                    i11++;
                    float[] fArr2 = arrayList2.get(i11 % arrayList2.size());
                    arrayList3.add(new float[]{(fArr[0] + fArr2[0]) / 2.0f, (fArr[1] + fArr2[1]) / 2.0f});
                }
                i10++;
                arrayList2 = arrayList3;
            }
            return arrayList2;
        }

        private void searchNeighborBlock(ArrayList<Point> arrayList, int[][] iArr, int i, int i2, int i3) {
            int[] iArr2 = this.mCheckArray[i2];
            if (iArr2[i] == 1) {
                return;
            }
            iArr2[i] = 1;
            for (int i4 = 0; i4 < 4; i4++) {
                int i5 = (i3 + i4) % 4;
                int i6 = i2 + this.EDGE_Y[i5];
                int i7 = i + this.EDGE_X[i5];
                int i8 = i + this.SEARCH_X[i5];
                int i9 = i2 + this.SEARCH_Y[i5];
                if (iArr[i2][i] == 1 && (i9 < 0 || i8 < 0 || i9 >= iArr.length || i8 >= iArr[0].length || iArr[i9][i8] == 0)) {
                    if (i9 >= 0 && i8 > 0 && i9 < iArr.length && i8 < iArr[0].length) {
                        this.mCheckArray[i9][i8] = 1;
                    }
                    arrayList.add(new Point(i7, i6));
                } else if (this.mCheckArray[i9][i8] != 1) {
                    searchNeighborBlock(arrayList, iArr, i8, i9, ((i5 - 1) + 4) % 4);
                }
            }
        }

        final void setClipOutPath(Canvas canvas) {
            Path path;
            if (canvas == null || (path = this.mEffectPath) == null || path.isEmpty()) {
                return;
            }
            canvas.clipOutPath(this.mEffectPath);
        }

        final void setScreenSize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        final synchronized void updateTouchMapFromFile() {
            if (this.mWidth != 0 && this.mHeight != 0) {
                int[][] readTouchMatrix = this.mTouchMapReader.readTouchMatrix();
                if (readTouchMatrix != null) {
                    this.mEffectPath = calculateClipOutPath(getOuterLine(readTouchMatrix));
                }
            }
        }
    }

    public static void $r8$lambda$WeunE3xHFN40gEF93fT2dO74a8M(VisualEffectContainer visualEffectContainer) {
        visualEffectContainer.getClass();
        Log.i("BSS_VisualEffectContainer", "start()");
        Iterator it = ((ArrayList) visualEffectContainer.mEffectViews).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            Object obj = pair.first;
            if (obj instanceof LottieAnimationView) {
                visualEffectContainer.mEffectAnimationView.setRenderMode(RenderMode.HARDWARE);
                visualEffectContainer.mEffectAnimationView.setFrame(0);
                if (!visualEffectContainer.mEffectAnimationView.isAnimating()) {
                    visualEffectContainer.mEffectAnimationView.playAnimation();
                }
            } else if (obj instanceof ImageView) {
                ((View) obj).startAnimation((Animation) pair.second);
            }
        }
        visualEffectContainer.mHandler.removeCallbacks(visualEffectContainer.mStartCallback);
        visualEffectContainer.mHandler.removeCallbacks(visualEffectContainer.mStopCallback);
        visualEffectContainer.mHandler.postDelayed(visualEffectContainer.mStopCallback, visualEffectContainer.mAnimationDuration);
        visualEffectContainer.mIsReadTouchMap = true;
        visualEffectContainer.mTouchMapStartTime = SystemClock.elapsedRealtime();
        if (visualEffectContainer.mReadTouchMapThread == null) {
            Thread thread = new Thread(new VisualEffectContainer$$ExternalSyntheticLambda0(visualEffectContainer, 2));
            visualEffectContainer.mReadTouchMapThread = thread;
            thread.start();
        }
        visualEffectContainer.updateLayout();
        visualEffectContainer.setVisibility(0);
    }

    public static /* synthetic */ void $r8$lambda$dhyVIR_QGXGm73WdU7zAnLLfbLQ(VisualEffectContainer visualEffectContainer) {
        while (visualEffectContainer.mTouchMap != null && visualEffectContainer.mIsReadTouchMap && SystemClock.elapsedRealtime() - visualEffectContainer.mTouchMapStartTime < 2000) {
            try {
                visualEffectContainer.mTouchMap.updateTouchMapFromFile();
                visualEffectContainer.mHandler.post(new VisualEffectContainer$$ExternalSyntheticLambda0(visualEffectContainer, 3));
                Thread.sleep(30L);
            } catch (InterruptedException unused) {
            }
        }
        visualEffectContainer.mReadTouchMapThread = null;
    }

    public VisualEffectContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchMapStartTime = 0L;
        this.mAnimationDuration = 1000L;
        this.mStartCallback = new VisualEffectContainer$$ExternalSyntheticLambda0(this, 0);
        this.mStopCallback = new VisualEffectContainer$$ExternalSyntheticLambda0(this, 1);
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        this.mTouchMap = new SemVisualEffectTouchMap(context);
        this.mEffectViews = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopVI() {
        Log.i("BSS_VisualEffectContainer", "stop()");
        this.mIsReadTouchMap = false;
        LottieAnimationView lottieAnimationView = this.mEffectAnimationView;
        if (lottieAnimationView != null) {
            lottieAnimationView.setRenderMode(RenderMode.SOFTWARE);
        }
        setVisibility(4);
        disableTouchMapUpdate();
        Callback callback = this.mCallback;
        if (callback != null) {
            ((UdfpsKeyguardSensorWindow) callback).onEffectFinished();
        }
    }

    private void updateLayout() {
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        if (udfpsInfo == null) {
            return;
        }
        int areaHeight = udfpsInfo.getAreaHeight();
        int marginBottom = this.mSensorInfo.getMarginBottom();
        int i = Utils.getDisplaySize(this.mContext).x;
        int i2 = (int) ((areaHeight / 2.0f) + (i / 2.0f) + marginBottom);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        layoutParams.gravity = 81;
        setLayoutParams(layoutParams);
        requestLayout();
        int applyDimension = (int) TypedValue.applyDimension(1, 400.0f, Utils.getDisplayMetrics(this.mContext));
        Iterator it = ((ArrayList) this.mEffectViews).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            ViewGroup.LayoutParams layoutParams2 = ((View) pair.first).getLayoutParams();
            layoutParams2.height = applyDimension;
            layoutParams2.width = applyDimension;
            ((View) pair.first).setLayoutParams(layoutParams2);
        }
    }

    public final void disableTouchMapUpdate() {
        this.mIsReadTouchMap = false;
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchConfigurationChanged(Configuration configuration) {
        super.dispatchConfigurationChanged(configuration);
        updateLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init(UdfpsInfo udfpsInfo, Callback callback) {
        String str;
        InputStream openRawResource;
        int available;
        byte[] bArr;
        this.mSensorInfo = udfpsInfo;
        this.mCallback = callback;
        setWillNotDraw(false);
        File file = new File(this.mContext.getFilesDir(), "user_fingerprint_touch_effect.png");
        Bitmap decodeFile = file.exists() ? BitmapFactory.decodeFile(file.getAbsolutePath()) : null;
        if (decodeFile != null) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageBitmap(decodeFile);
            ((ArrayList) this.mEffectViews).add(new Pair(imageView, AnimationUtils.loadAnimation(getContext(), R.anim.extender_fingerprint_image_effect_1)));
            ((ArrayList) this.mEffectViews).add(new Pair(imageView, AnimationUtils.loadAnimation(getContext(), R.anim.extender_fingerprint_image_effect_2)));
        }
        if (((ArrayList) this.mEffectViews).isEmpty()) {
            this.mEffectAnimationView = new LottieAnimationView(this.mContext);
            try {
                openRawResource = this.mContext.getResources().openRawResource(R.raw.fingerprint_touch_effect_json);
                try {
                    available = openRawResource.available();
                    bArr = new byte[available];
                } finally {
                }
            } catch (IOException e) {
                Log.e("BSS_InDisplayCustom", "IOException = e " + e);
                e.printStackTrace();
            }
            if (available != 0 && openRawResource.read(bArr) > 0) {
                str = new String(bArr, StandardCharsets.UTF_8);
                openRawResource.close();
                if (str == null) {
                    this.mEffectAnimationView.setAnimationFromJson(str);
                    this.mAnimationDuration = 7000L;
                    this.mEffectAnimationView.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer.1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                            VisualEffectContainer.this.mHandler.removeCallbacks(VisualEffectContainer.this.mStopCallback);
                            VisualEffectContainer.this.stopVI();
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            VisualEffectContainer.this.mHandler.removeCallbacks(VisualEffectContainer.this.mStopCallback);
                            VisualEffectContainer.this.stopVI();
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                        }
                    });
                } else if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                    String lightColor = udfpsInfo.getLightColor();
                    if (lightColor == null || lightColor.length() < 6 || lightColor.charAt(0) != lightColor.charAt(2) || lightColor.charAt(0) != lightColor.charAt(4)) {
                        this.mEffectAnimationView.setAnimation(ASSET_NAME_OPTICAL_GREEN);
                        this.mEffectAnimationView.setAlpha(0.7f);
                    } else {
                        this.mEffectAnimationView.setAnimation(ASSET_NAME_OPTICAL_WHITE);
                    }
                } else {
                    this.mEffectAnimationView.setAnimation(ASSET_NAME_ULTRASONIC);
                }
                ((ArrayList) this.mEffectViews).add(new Pair(this.mEffectAnimationView, null));
            }
            openRawResource.close();
            str = null;
            if (str == null) {
            }
            ((ArrayList) this.mEffectViews).add(new Pair(this.mEffectAnimationView, null));
        }
        setLayoutDirection(0);
        int applyDimension = (int) TypedValue.applyDimension(1, 400.0f, Utils.getDisplayMetrics(this.mContext));
        Iterator it = ((ArrayList) this.mEffectViews).iterator();
        while (it.hasNext()) {
            addView((View) ((Pair) it.next()).first, applyDimension, applyDimension);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.vi.VisualEffectContainer.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                VisualEffectContainer.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Point displaySize = Utils.getDisplaySize(VisualEffectContainer.this.mContext);
                VisualEffectContainer.this.mTouchMap.setScreenSize(displaySize.x, displaySize.y);
            }
        });
        updateLayout();
        setVisibility(4);
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        if (udfpsInfo != null) {
            int areaHeight = udfpsInfo.getAreaHeight();
            int marginBottom = this.mSensorInfo.getMarginBottom();
            float width = (getWidth() / 2.0f) + this.mSensorInfo.getMarginLeft();
            float height = getHeight() - ((areaHeight / 2.0f) + marginBottom);
            Iterator it = ((ArrayList) this.mEffectViews).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                ((View) pair.first).setTranslationX(((int) width) - (r4.getWidth() / 2.0f));
                ((View) pair.first).setTranslationY(((int) height) - (r1.getHeight() / 2.0f));
            }
        }
        if (this.mIsReadTouchMap) {
            this.mTouchMap.setClipOutPath(canvas);
        }
    }

    public final void start() {
        this.mHandler.post(this.mStartCallback);
    }

    public final void stop() {
        this.mCallback = null;
        this.mHandler.removeCallbacks(this.mStartCallback);
        this.mHandler.removeCallbacks(this.mStopCallback);
        stopVI();
        removeAllViews();
    }

    public final void updateDisplayChanged() {
        updateLayout();
    }
}
