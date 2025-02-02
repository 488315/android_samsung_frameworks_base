package com.airbnb.lottie;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow$1$$ExternalSyntheticLambda0;
import java.io.ByteArrayInputStream;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.net.ssl.SSLException;

/* loaded from: classes.dex */
public class LottieAnimationView extends AppCompatImageView {
    private static final LottieListener<Throwable> DEFAULT_FAILURE_LISTENER = new C01801();
    private String animationName;
    private int animationResId;
    private boolean autoPlay;
    private int buildDrawingCacheDepth;
    private boolean cacheComposition;
    private LottieComposition composition;
    private LottieTask<LottieComposition> compositionTask;
    private LottieListener<Throwable> failureListener;
    private int fallbackResource;
    private boolean isInitialized;
    private final LottieListener<LottieComposition> loadedListener;
    private final LottieDrawable lottieDrawable;
    private final Set<LottieOnCompositionLoadedListener> lottieOnCompositionLoadedListeners;
    private boolean playAnimationWhenShown;
    private RenderMode renderMode;
    private boolean wasAnimatingWhenDetached;
    private boolean wasAnimatingWhenNotShown;
    private final LottieListener<Throwable> wrappedFailureListener;

    /* renamed from: com.airbnb.lottie.LottieAnimationView$1 */
    final class C01801 implements LottieListener<Throwable> {
        @Override // com.airbnb.lottie.LottieListener
        public final void onResult(Throwable th) {
            Throwable th2 = th;
            int i = Utils.$r8$clinit;
            if (!((th2 instanceof SocketException) || (th2 instanceof ClosedChannelException) || (th2 instanceof InterruptedIOException) || (th2 instanceof ProtocolException) || (th2 instanceof SSLException) || (th2 instanceof UnknownHostException) || (th2 instanceof UnknownServiceException))) {
                throw new IllegalStateException("Unable to parse composition", th2);
            }
            Logger.warning("Unable to load composition.", th2);
        }
    }

    private static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new C01861();
        String animationName;
        int animationResId;
        String imageAssetsFolder;
        boolean isAnimating;
        float progress;
        int repeatCount;
        int repeatMode;

        /* renamed from: com.airbnb.lottie.LottieAnimationView$SavedState$1 */
        final class C01861 implements Parcelable.Creator<SavedState> {
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.animationName = parcel.readString();
            this.progress = parcel.readFloat();
            this.isAnimating = parcel.readInt() == 1;
            this.imageAssetsFolder = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.animationName);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.isAnimating ? 1 : 0);
            parcel.writeString(this.imageAssetsFolder);
            parcel.writeInt(this.repeatMode);
            parcel.writeInt(this.repeatCount);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Throwable th) {
                Throwable th2 = th;
                LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                if (lottieAnimationView.fallbackResource != 0) {
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (lottieAnimationView.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : lottieAnimationView.failureListener).onResult(th2);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(null, R.attr.lottieAnimationViewStyle);
    }

    private void cancelLoaderTask() {
        LottieTask<LottieComposition> lottieTask = this.compositionTask;
        if (lottieTask != null) {
            lottieTask.removeListener(this.loadedListener);
            this.compositionTask.removeFailureListener(this.wrappedFailureListener);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0024, code lost:
    
        if ((r0 == null || r0.getMaskAndMatteCount() <= 4) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3:0x000a, code lost:
    
        if (r0 != 1) goto L5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000c, code lost:
    
        r1 = 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void enableOrDisableHardwareLayer() {
        int ordinal = this.renderMode.ordinal();
        int i = 2;
        if (ordinal == 0) {
            LottieComposition lottieComposition = this.composition;
            if (lottieComposition != null) {
                lottieComposition.hasDashPattern();
            }
            LottieComposition lottieComposition2 = this.composition;
        }
        if (i != getLayerType()) {
            setLayerType(i, null);
        }
    }

    private void init(AttributeSet attributeSet, int i) {
        String string;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, i, 0);
        this.cacheComposition = obtainStyledAttributes.getBoolean(1, true);
        boolean hasValue = obtainStyledAttributes.hasValue(9);
        boolean hasValue2 = obtainStyledAttributes.hasValue(5);
        boolean hasValue3 = obtainStyledAttributes.hasValue(15);
        if (hasValue && hasValue2) {
            throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
        }
        if (hasValue) {
            int resourceId = obtainStyledAttributes.getResourceId(9, 0);
            if (resourceId != 0) {
                setAnimation(resourceId);
            }
        } else if (hasValue2) {
            String string2 = obtainStyledAttributes.getString(5);
            if (string2 != null) {
                setAnimation(string2);
            }
        } else if (hasValue3 && (string = obtainStyledAttributes.getString(15)) != null) {
            setAnimationFromUrl(string);
        }
        setFallbackResource(obtainStyledAttributes.getResourceId(4, 0));
        if (obtainStyledAttributes.getBoolean(0, false)) {
            this.wasAnimatingWhenDetached = true;
            this.autoPlay = true;
        }
        if (obtainStyledAttributes.getBoolean(7, false)) {
            this.lottieDrawable.setRepeatCount(-1);
        }
        if (obtainStyledAttributes.hasValue(12)) {
            setRepeatMode(obtainStyledAttributes.getInt(12, 1));
        }
        if (obtainStyledAttributes.hasValue(11)) {
            setRepeatCount(obtainStyledAttributes.getInt(11, -1));
        }
        if (obtainStyledAttributes.hasValue(14)) {
            setSpeed(obtainStyledAttributes.getFloat(14, 1.0f));
        }
        setImageAssetsFolder(obtainStyledAttributes.getString(6));
        setProgress(obtainStyledAttributes.getFloat(8, 0.0f));
        this.lottieDrawable.enableMergePathsForKitKatAndAbove(obtainStyledAttributes.getBoolean(3, false));
        if (obtainStyledAttributes.hasValue(2)) {
            this.lottieDrawable.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(obtainStyledAttributes.getColor(2, 0))));
        }
        if (obtainStyledAttributes.hasValue(13)) {
            this.lottieDrawable.setScale(obtainStyledAttributes.getFloat(13, 1.0f));
        }
        if (obtainStyledAttributes.hasValue(10)) {
            int i2 = obtainStyledAttributes.getInt(10, 0);
            if (i2 >= RenderMode.values().length) {
                i2 = 0;
            }
            setRenderMode(RenderMode.values()[i2]);
        }
        if (getScaleType() != null) {
            this.lottieDrawable.setScaleType(getScaleType());
        }
        obtainStyledAttributes.recycle();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        Context context = getContext();
        int i3 = Utils.$r8$clinit;
        lottieDrawable.setSystemAnimationsAreEnabled(Boolean.valueOf(Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f) != 0.0f));
        enableOrDisableHardwareLayer();
        this.isInitialized = true;
    }

    private void setCompositionTask(LottieTask<LottieComposition> lottieTask) {
        this.composition = null;
        this.lottieDrawable.clearComposition();
        cancelLoaderTask();
        lottieTask.addListener(this.loadedListener);
        lottieTask.addFailureListener(this.wrappedFailureListener);
        this.compositionTask = lottieTask;
    }

    public final void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.lottieDrawable.addAnimatorListener(animatorListener);
    }

    public final void addLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        if (this.composition != null) {
            lottieOnCompositionLoadedListener.onCompositionLoaded();
        }
        ((HashSet) this.lottieOnCompositionLoadedListeners).add(lottieOnCompositionLoadedListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void addValueCallback(KeyPath keyPath, T t, final UdfpsSensorWindow$1$$ExternalSyntheticLambda0 udfpsSensorWindow$1$$ExternalSyntheticLambda0) {
        this.lottieDrawable.addValueCallback(keyPath, t, new LottieValueCallback<Object>() { // from class: com.airbnb.lottie.LottieAnimationView.6
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public final Object getValue(LottieFrameInfo<Object> lottieFrameInfo) {
                return UdfpsSensorWindow$1$$ExternalSyntheticLambda0.this.getValue();
            }
        });
    }

    @Override // android.view.View
    public final void buildDrawingCache(boolean z) {
        this.buildDrawingCacheDepth++;
        super.buildDrawingCache(z);
        if (this.buildDrawingCacheDepth == 1 && getWidth() > 0 && getHeight() > 0 && getLayerType() == 1 && getDrawingCache(z) == null) {
            setRenderMode(RenderMode.HARDWARE);
        }
        this.buildDrawingCacheDepth--;
        C0179L.endSection();
    }

    public final void cancelAnimation() {
        this.wasAnimatingWhenDetached = false;
        this.wasAnimatingWhenNotShown = false;
        this.playAnimationWhenShown = false;
        this.lottieDrawable.cancelAnimation();
        enableOrDisableHardwareLayer();
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return (long) lottieComposition.getDuration();
        }
        return 0L;
    }

    public int getFrame() {
        return this.lottieDrawable.getFrame();
    }

    public String getImageAssetsFolder() {
        return this.lottieDrawable.getImageAssetsFolder();
    }

    public float getMaxFrame() {
        return this.lottieDrawable.getMaxFrame();
    }

    public float getMinFrame() {
        return this.lottieDrawable.getMinFrame();
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.lottieDrawable.getPerformanceTracker();
    }

    public float getProgress() {
        return this.lottieDrawable.getProgress();
    }

    public int getRepeatCount() {
        return this.lottieDrawable.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.lottieDrawable.getRepeatMode();
    }

    public float getScale() {
        return this.lottieDrawable.getScale();
    }

    public float getSpeed() {
        return this.lottieDrawable.getSpeed();
    }

    @Override // android.widget.ImageView, android.view.View, android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable drawable2 = getDrawable();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (drawable2 == lottieDrawable) {
            super.invalidateDrawable(lottieDrawable);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public final boolean isAnimating() {
        return this.lottieDrawable.isAnimating();
    }

    @Deprecated
    public final void loop() {
        this.lottieDrawable.setRepeatCount(-1);
    }

    @Override // android.widget.ImageView, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            return;
        }
        if (this.autoPlay || this.wasAnimatingWhenDetached) {
            playAnimation();
            this.autoPlay = false;
            this.wasAnimatingWhenDetached = false;
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected final void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        String str = savedState.animationName;
        this.animationName = str;
        if (!TextUtils.isEmpty(str)) {
            setAnimation(this.animationName);
        }
        int i = savedState.animationResId;
        this.animationResId = i;
        if (i != 0) {
            setAnimation(i);
        }
        setProgress(savedState.progress);
        if (savedState.isAnimating) {
            playAnimation();
        }
        this.lottieDrawable.setImagesAssetsFolder(savedState.imageAssetsFolder);
        setRepeatMode(savedState.repeatMode);
        setRepeatCount(savedState.repeatCount);
    }

    @Override // android.view.View
    protected final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.animationName = this.animationName;
        savedState.animationResId = this.animationResId;
        savedState.progress = this.lottieDrawable.getProgress();
        savedState.isAnimating = this.lottieDrawable.isAnimating() || (!ViewCompat.isAttachedToWindow(this) && this.wasAnimatingWhenDetached);
        savedState.imageAssetsFolder = this.lottieDrawable.getImageAssetsFolder();
        savedState.repeatMode = this.lottieDrawable.getRepeatMode();
        savedState.repeatCount = this.lottieDrawable.getRepeatCount();
        return savedState;
    }

    @Override // android.view.View
    protected final void onVisibilityChanged(View view, int i) {
        if (this.isInitialized) {
            if (!isShown()) {
                if (isAnimating()) {
                    pauseAnimation();
                    this.wasAnimatingWhenNotShown = true;
                    return;
                }
                return;
            }
            if (this.wasAnimatingWhenNotShown) {
                if (isShown()) {
                    this.lottieDrawable.resumeAnimation();
                    enableOrDisableHardwareLayer();
                } else {
                    this.playAnimationWhenShown = false;
                    this.wasAnimatingWhenNotShown = true;
                }
            } else if (this.playAnimationWhenShown) {
                playAnimation();
            }
            this.wasAnimatingWhenNotShown = false;
            this.playAnimationWhenShown = false;
        }
    }

    public final void pauseAnimation() {
        this.autoPlay = false;
        this.wasAnimatingWhenDetached = false;
        this.wasAnimatingWhenNotShown = false;
        this.playAnimationWhenShown = false;
        this.lottieDrawable.pauseAnimation();
        enableOrDisableHardwareLayer();
    }

    public final void playAnimation() {
        if (!isShown()) {
            this.playAnimationWhenShown = true;
        } else {
            this.lottieDrawable.playAnimation();
            enableOrDisableHardwareLayer();
        }
    }

    public final void removeAllLottieOnCompositionLoadedListener() {
        this.lottieOnCompositionLoadedListeners.clear();
    }

    public void setAnimation(final int i) {
        LottieTask<LottieComposition> fromRawRes;
        this.animationResId = i;
        this.animationName = null;
        if (isInEditMode()) {
            fromRawRes = new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieAnimationView.4
                @Override // java.util.concurrent.Callable
                public final LottieResult<LottieComposition> call() throws Exception {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    boolean z = lottieAnimationView.cacheComposition;
                    int i2 = i;
                    return z ? LottieCompositionFactory.fromRawResSync(lottieAnimationView.getContext(), i2) : LottieCompositionFactory.fromRawResSync(lottieAnimationView.getContext(), i2, null);
                }
            }, true);
        } else {
            fromRawRes = this.cacheComposition ? LottieCompositionFactory.fromRawRes(getContext(), i) : LottieCompositionFactory.fromRawRes(getContext(), i, null);
        }
        setCompositionTask(fromRawRes);
    }

    @Deprecated
    public void setAnimationFromJson(String str) {
        setCompositionTask(LottieCompositionFactory.fromJsonInputStream(new ByteArrayInputStream(str.getBytes())));
    }

    public void setAnimationFromUrl(String str) {
        LottieTask<LottieComposition> fromUrl;
        if (this.cacheComposition) {
            Context context = getContext();
            int i = LottieCompositionFactory.$r8$clinit;
            fromUrl = LottieCompositionFactory.fromUrl(context, str, "url_" + str);
        } else {
            fromUrl = LottieCompositionFactory.fromUrl(getContext(), str, null);
        }
        setCompositionTask(fromUrl);
    }

    public void setApplyingOpacityToLayersEnabled(boolean z) {
        this.lottieDrawable.setApplyingOpacityToLayersEnabled(z);
    }

    public void setCacheComposition(boolean z) {
        this.cacheComposition = z;
    }

    public void setComposition(LottieComposition lottieComposition) {
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        boolean composition = this.lottieDrawable.setComposition(lottieComposition);
        enableOrDisableHardwareLayer();
        if (getDrawable() != this.lottieDrawable || composition) {
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            Iterator it = ((HashSet) this.lottieOnCompositionLoadedListeners).iterator();
            while (it.hasNext()) {
                ((LottieOnCompositionLoadedListener) it.next()).onCompositionLoaded();
            }
        }
    }

    public void setFailureListener(LottieListener<Throwable> lottieListener) {
        this.failureListener = lottieListener;
    }

    public void setFallbackResource(int i) {
        this.fallbackResource = i;
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.lottieDrawable.getClass();
    }

    public void setFrame(int i) {
        this.lottieDrawable.setFrame(i);
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.lottieDrawable.getClass();
    }

    public void setImageAssetsFolder(String str) {
        this.lottieDrawable.setImagesAssetsFolder(str);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i) {
        cancelLoaderTask();
        super.setImageResource(i);
    }

    public void setMaxFrame(int i) {
        this.lottieDrawable.setMaxFrame(i);
    }

    public void setMaxProgress(float f) {
        this.lottieDrawable.setMaxProgress(f);
    }

    public void setMinAndMaxFrame(String str) {
        this.lottieDrawable.setMinAndMaxFrame(str);
    }

    public void setMinFrame(int i) {
        this.lottieDrawable.setMinFrame(i);
    }

    public void setMinProgress(float f) {
        this.lottieDrawable.setMinProgress(f);
    }

    public void setOutlineMasksAndMattes(boolean z) {
        this.lottieDrawable.setOutlineMasksAndMattes(z);
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.lottieDrawable.setPerformanceTrackingEnabled(z);
    }

    public void setProgress(float f) {
        this.lottieDrawable.setProgress(f);
    }

    public void setRenderMode(RenderMode renderMode) {
        this.renderMode = renderMode;
        enableOrDisableHardwareLayer();
    }

    public void setRepeatCount(int i) {
        this.lottieDrawable.setRepeatCount(i);
    }

    public void setRepeatMode(int i) {
        this.lottieDrawable.setRepeatMode(i);
    }

    public void setSafeMode(boolean z) {
        this.lottieDrawable.setSafeMode(z);
    }

    public void setScale(float f) {
        this.lottieDrawable.setScale(f);
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
        }
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable != null) {
            lottieDrawable.setScaleType(scaleType);
        }
    }

    public void setSpeed(float f) {
        this.lottieDrawable.setSpeed(f);
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.lottieDrawable.getClass();
    }

    public void setMaxFrame(String str) {
        this.lottieDrawable.setMaxFrame(str);
    }

    public final void setMinAndMaxFrame(int i, int i2) {
        this.lottieDrawable.setMinAndMaxFrame(i, i2);
    }

    public void setMinFrame(String str) {
        this.lottieDrawable.setMinFrame(str);
    }

    public void setAnimation(final String str) {
        LottieTask<LottieComposition> fromAsset;
        LottieTask<LottieComposition> lottieTask;
        this.animationName = str;
        this.animationResId = 0;
        if (isInEditMode()) {
            lottieTask = new LottieTask<>(new Callable<LottieResult<LottieComposition>>() { // from class: com.airbnb.lottie.LottieAnimationView.5
                @Override // java.util.concurrent.Callable
                public final LottieResult<LottieComposition> call() throws Exception {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    boolean z = lottieAnimationView.cacheComposition;
                    String str2 = str;
                    if (!z) {
                        return LottieCompositionFactory.fromAssetSync(lottieAnimationView.getContext(), str2, null);
                    }
                    Context context = lottieAnimationView.getContext();
                    int i = LottieCompositionFactory.$r8$clinit;
                    return LottieCompositionFactory.fromAssetSync(context, str2, "asset_" + str2);
                }
            }, true);
        } else {
            if (this.cacheComposition) {
                Context context = getContext();
                int i = LottieCompositionFactory.$r8$clinit;
                fromAsset = LottieCompositionFactory.fromAsset(context, str, "asset_" + str);
            } else {
                fromAsset = LottieCompositionFactory.fromAsset(getContext(), str, null);
            }
            lottieTask = fromAsset;
        }
        setCompositionTask(lottieTask);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Throwable th) {
                Throwable th2 = th;
                LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                if (lottieAnimationView.fallbackResource != 0) {
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (lottieAnimationView.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : lottieAnimationView.failureListener).onResult(th2);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(attributeSet, R.attr.lottieAnimationViewStyle);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.loadedListener = new LottieListener<LottieComposition>() { // from class: com.airbnb.lottie.LottieAnimationView.2
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(LottieComposition lottieComposition) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
        };
        this.wrappedFailureListener = new LottieListener<Throwable>() { // from class: com.airbnb.lottie.LottieAnimationView.3
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Throwable th) {
                Throwable th2 = th;
                LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                if (lottieAnimationView.fallbackResource != 0) {
                    lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
                }
                (lottieAnimationView.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : lottieAnimationView.failureListener).onResult(th2);
            }
        };
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.playAnimationWhenShown = false;
        this.wasAnimatingWhenNotShown = false;
        this.wasAnimatingWhenDetached = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.renderMode = RenderMode.AUTOMATIC;
        this.lottieOnCompositionLoadedListeners = new HashSet();
        this.buildDrawingCacheDepth = 0;
        init(attributeSet, i);
    }
}
