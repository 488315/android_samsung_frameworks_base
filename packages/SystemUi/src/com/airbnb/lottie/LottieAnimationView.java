package com.airbnb.lottie;

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
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LottieAnimationView extends AppCompatImageView {
    public static final LottieAnimationView$$ExternalSyntheticLambda1 DEFAULT_FAILURE_LISTENER = new LottieAnimationView$$ExternalSyntheticLambda1();
    public String animationName;
    public int animationResId;
    public boolean autoPlay;
    public boolean cacheComposition;
    public LottieComposition composition;
    public LottieTask compositionTask;
    public LottieListener failureListener;
    public int fallbackResource;
    public boolean ignoreUnschedule;
    public final WeakSuccessListener loadedListener;
    public final LottieDrawable lottieDrawable;
    public final Set lottieOnCompositionLoadedListeners;
    public final Set userActionsTaken;
    public final WeakFailureListener wrappedFailureListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.airbnb.lottie.LottieAnimationView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public String animationName;
        public int animationResId;
        public String imageAssetsFolder;
        public boolean isAnimating;
        public float progress;
        public int repeatCount;
        public int repeatMode;

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

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.animationName = parcel.readString();
            this.progress = parcel.readFloat();
            this.isAnimating = parcel.readInt() == 1;
            this.imageAssetsFolder = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum UserActionTaken {
        SET_ANIMATION,
        SET_PROGRESS,
        SET_REPEAT_MODE,
        SET_REPEAT_COUNT,
        SET_IMAGE_ASSETS,
        PLAY_OPTION
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WeakFailureListener implements LottieListener {
        public final WeakReference targetReference;

        public WeakFailureListener(LottieAnimationView lottieAnimationView) {
            this.targetReference = new WeakReference(lottieAnimationView);
        }

        @Override // com.airbnb.lottie.LottieListener
        public final void onResult(Object obj) {
            Throwable th = (Throwable) obj;
            LottieAnimationView lottieAnimationView = (LottieAnimationView) this.targetReference.get();
            if (lottieAnimationView == null) {
                return;
            }
            int i = lottieAnimationView.fallbackResource;
            if (i != 0) {
                lottieAnimationView.setImageResource(i);
            }
            LottieListener lottieListener = lottieAnimationView.failureListener;
            if (lottieListener == null) {
                lottieListener = LottieAnimationView.DEFAULT_FAILURE_LISTENER;
            }
            lottieListener.onResult(th);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WeakSuccessListener implements LottieListener {
        public final WeakReference targetReference;

        public WeakSuccessListener(LottieAnimationView lottieAnimationView) {
            this.targetReference = new WeakReference(lottieAnimationView);
        }

        @Override // com.airbnb.lottie.LottieListener
        public final void onResult(Object obj) {
            LottieComposition lottieComposition = (LottieComposition) obj;
            LottieAnimationView lottieAnimationView = (LottieAnimationView) this.targetReference.get();
            if (lottieAnimationView == null) {
                return;
            }
            lottieAnimationView.setComposition(lottieComposition);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        this.loadedListener = new WeakSuccessListener(this);
        this.wrappedFailureListener = new WeakFailureListener(this);
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.ignoreUnschedule = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.userActionsTaken = new HashSet();
        this.lottieOnCompositionLoadedListeners = new HashSet();
        init(null, R.attr.lottieAnimationViewStyle);
    }

    public final void addValueCallback(KeyPath keyPath, Object obj, LottieValueCallback lottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, obj, lottieValueCallback);
    }

    public final void cancelAnimation() {
        this.userActionsTaken.add(UserActionTaken.PLAY_OPTION);
        LottieDrawable lottieDrawable = this.lottieDrawable;
        lottieDrawable.lazyCompositionTasks.clear();
        lottieDrawable.animator.cancel();
        if (lottieDrawable.isVisible()) {
            return;
        }
        lottieDrawable.onVisibleAction = LottieDrawable.OnVisibleAction.NONE;
    }

    public final void cancelLoaderTask() {
        LottieTask lottieTask = this.compositionTask;
        if (lottieTask != null) {
            WeakSuccessListener weakSuccessListener = this.loadedListener;
            synchronized (lottieTask) {
                lottieTask.successListeners.remove(weakSuccessListener);
            }
            LottieTask lottieTask2 = this.compositionTask;
            WeakFailureListener weakFailureListener = this.wrappedFailureListener;
            synchronized (lottieTask2) {
                lottieTask2.failureListeners.remove(weakFailureListener);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void init(AttributeSet attributeSet, int i) {
        String string;
        LottieTask cache;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, i, 0);
        this.cacheComposition = obtainStyledAttributes.getBoolean(2, true);
        boolean hasValue = obtainStyledAttributes.hasValue(13);
        boolean hasValue2 = obtainStyledAttributes.hasValue(8);
        boolean hasValue3 = obtainStyledAttributes.hasValue(18);
        if (hasValue && hasValue2) {
            throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
        }
        if (hasValue) {
            int resourceId = obtainStyledAttributes.getResourceId(13, 0);
            if (resourceId != 0) {
                setAnimation(resourceId);
            }
        } else if (hasValue2) {
            String string2 = obtainStyledAttributes.getString(8);
            if (string2 != null) {
                setAnimation(string2);
            }
        } else if (hasValue3 && (string = obtainStyledAttributes.getString(18)) != null) {
            String str = null;
            if (this.cacheComposition) {
                Context context = getContext();
                Map map = LottieCompositionFactory.taskCache;
                String concat = "url_".concat(string);
                cache = LottieCompositionFactory.cache(concat, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, string, concat, objArr2 == true ? 1 : 0), null);
            } else {
                cache = LottieCompositionFactory.cache(null, new LottieCompositionFactory$$ExternalSyntheticLambda0(getContext(), string, str, objArr == true ? 1 : 0), null);
            }
            setCompositionTask(cache);
        }
        this.fallbackResource = obtainStyledAttributes.getResourceId(7, 0);
        if (obtainStyledAttributes.getBoolean(1, false)) {
            this.autoPlay = true;
        }
        if (obtainStyledAttributes.getBoolean(11, false)) {
            this.lottieDrawable.animator.setRepeatCount(-1);
        }
        if (obtainStyledAttributes.hasValue(16)) {
            setRepeatMode(obtainStyledAttributes.getInt(16, 1));
        }
        if (obtainStyledAttributes.hasValue(15)) {
            setRepeatCount(obtainStyledAttributes.getInt(15, -1));
        }
        if (obtainStyledAttributes.hasValue(17)) {
            this.lottieDrawable.animator.speed = obtainStyledAttributes.getFloat(17, 1.0f);
        }
        if (obtainStyledAttributes.hasValue(3)) {
            boolean z = obtainStyledAttributes.getBoolean(3, true);
            LottieDrawable lottieDrawable = this.lottieDrawable;
            if (z != lottieDrawable.clipToCompositionBounds) {
                lottieDrawable.clipToCompositionBounds = z;
                CompositionLayer compositionLayer = lottieDrawable.compositionLayer;
                if (compositionLayer != null) {
                    compositionLayer.clipToCompositionBounds = z;
                }
                lottieDrawable.invalidateSelf();
            }
        }
        if (obtainStyledAttributes.hasValue(5)) {
            String string3 = obtainStyledAttributes.getString(5);
            LottieDrawable lottieDrawable2 = this.lottieDrawable;
            lottieDrawable2.defaultFontFileExtension = string3;
            FontAssetManager fontAssetManager = lottieDrawable2.getFontAssetManager();
            if (fontAssetManager != null) {
                fontAssetManager.defaultFontFileExtension = string3;
            }
        }
        this.lottieDrawable.imageAssetsFolder = obtainStyledAttributes.getString(10);
        setProgressInternal(obtainStyledAttributes.getFloat(12, 0.0f), obtainStyledAttributes.hasValue(12));
        boolean z2 = obtainStyledAttributes.getBoolean(6, false);
        LottieDrawable lottieDrawable3 = this.lottieDrawable;
        if (lottieDrawable3.enableMergePaths != z2) {
            lottieDrawable3.enableMergePaths = z2;
            if (lottieDrawable3.composition != null) {
                lottieDrawable3.buildCompositionLayer();
            }
        }
        if (obtainStyledAttributes.hasValue(4)) {
            addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(ContextCompat.getColorStateList(obtainStyledAttributes.getResourceId(4, -1), getContext()).getDefaultColor())));
        }
        if (obtainStyledAttributes.hasValue(14)) {
            RenderMode renderMode = RenderMode.AUTOMATIC;
            int i2 = obtainStyledAttributes.getInt(14, renderMode.ordinal());
            if (i2 >= RenderMode.values().length) {
                i2 = renderMode.ordinal();
            }
            RenderMode renderMode2 = RenderMode.values()[i2];
            LottieDrawable lottieDrawable4 = this.lottieDrawable;
            lottieDrawable4.renderMode = renderMode2;
            lottieDrawable4.computeRenderMode();
        }
        if (obtainStyledAttributes.hasValue(0)) {
            AsyncUpdates asyncUpdates = AsyncUpdates.AUTOMATIC;
            int i3 = obtainStyledAttributes.getInt(0, asyncUpdates.ordinal());
            if (i3 >= RenderMode.values().length) {
                i3 = asyncUpdates.ordinal();
            }
            this.lottieDrawable.asyncUpdates = AsyncUpdates.values()[i3];
        }
        this.lottieDrawable.ignoreSystemAnimationsDisabled = obtainStyledAttributes.getBoolean(9, false);
        if (obtainStyledAttributes.hasValue(19)) {
            this.lottieDrawable.animator.useCompositionFrameRate = obtainStyledAttributes.getBoolean(19, false);
        }
        obtainStyledAttributes.recycle();
        LottieDrawable lottieDrawable5 = this.lottieDrawable;
        Context context2 = getContext();
        Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
        Boolean valueOf = Boolean.valueOf(Settings.Global.getFloat(context2.getContentResolver(), "animator_duration_scale", 1.0f) != 0.0f);
        lottieDrawable5.getClass();
        lottieDrawable5.systemAnimationsEnabled = valueOf.booleanValue();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        Drawable drawable = getDrawable();
        if (drawable instanceof LottieDrawable) {
            if ((((LottieDrawable) drawable).useSoftwareRendering ? RenderMode.SOFTWARE : RenderMode.HARDWARE) == RenderMode.SOFTWARE) {
                this.lottieDrawable.invalidateSelf();
            }
        }
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

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode() || !this.autoPlay) {
            return;
        }
        this.lottieDrawable.playAnimation();
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        int i;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.animationName = savedState.animationName;
        Set set = this.userActionsTaken;
        UserActionTaken userActionTaken = UserActionTaken.SET_ANIMATION;
        if (!set.contains(userActionTaken) && !TextUtils.isEmpty(this.animationName)) {
            setAnimation(this.animationName);
        }
        this.animationResId = savedState.animationResId;
        if (!this.userActionsTaken.contains(userActionTaken) && (i = this.animationResId) != 0) {
            setAnimation(i);
        }
        if (!this.userActionsTaken.contains(UserActionTaken.SET_PROGRESS)) {
            setProgressInternal(savedState.progress, false);
        }
        if (!this.userActionsTaken.contains(UserActionTaken.PLAY_OPTION) && savedState.isAnimating) {
            playAnimation();
        }
        if (!this.userActionsTaken.contains(UserActionTaken.SET_IMAGE_ASSETS)) {
            this.lottieDrawable.imageAssetsFolder = savedState.imageAssetsFolder;
        }
        if (!this.userActionsTaken.contains(UserActionTaken.SET_REPEAT_MODE)) {
            setRepeatMode(savedState.repeatMode);
        }
        if (this.userActionsTaken.contains(UserActionTaken.SET_REPEAT_COUNT)) {
            return;
        }
        setRepeatCount(savedState.repeatCount);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        boolean z;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.animationName = this.animationName;
        savedState.animationResId = this.animationResId;
        savedState.progress = this.lottieDrawable.animator.getAnimatedValueAbsolute();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable.isVisible()) {
            z = lottieDrawable.animator.running;
        } else {
            LottieDrawable.OnVisibleAction onVisibleAction = lottieDrawable.onVisibleAction;
            z = onVisibleAction == LottieDrawable.OnVisibleAction.PLAY || onVisibleAction == LottieDrawable.OnVisibleAction.RESUME;
        }
        savedState.isAnimating = z;
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        savedState.imageAssetsFolder = lottieDrawable2.imageAssetsFolder;
        savedState.repeatMode = lottieDrawable2.animator.getRepeatMode();
        savedState.repeatCount = this.lottieDrawable.animator.getRepeatCount();
        return savedState;
    }

    public final void pauseAnimation() {
        this.autoPlay = false;
        this.lottieDrawable.pauseAnimation();
    }

    public final void playAnimation() {
        this.userActionsTaken.add(UserActionTaken.PLAY_OPTION);
        this.lottieDrawable.playAnimation();
    }

    public final void setAnimation(final int i) {
        LottieTask fromRawRes;
        LottieTask lottieTask;
        this.animationResId = i;
        this.animationName = null;
        if (isInEditMode()) {
            lottieTask = new LottieTask(new Callable() { // from class: com.airbnb.lottie.LottieAnimationView$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    boolean z = lottieAnimationView.cacheComposition;
                    int i2 = i;
                    if (!z) {
                        return LottieCompositionFactory.fromRawResSync(lottieAnimationView.getContext(), null, i2);
                    }
                    Context context = lottieAnimationView.getContext();
                    return LottieCompositionFactory.fromRawResSync(context, LottieCompositionFactory.rawResCacheKey(i2, context), i2);
                }
            }, true);
        } else {
            if (this.cacheComposition) {
                Context context = getContext();
                fromRawRes = LottieCompositionFactory.fromRawRes(context, LottieCompositionFactory.rawResCacheKey(i, context), i);
            } else {
                fromRawRes = LottieCompositionFactory.fromRawRes(getContext(), null, i);
            }
            lottieTask = fromRawRes;
        }
        setCompositionTask(lottieTask);
    }

    public final void setComposition(LottieComposition lottieComposition) {
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        boolean z = true;
        this.ignoreUnschedule = true;
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable.composition == lottieComposition) {
            z = false;
        } else {
            lottieDrawable.isDirty = true;
            lottieDrawable.clearComposition();
            lottieDrawable.composition = lottieComposition;
            lottieDrawable.buildCompositionLayer();
            LottieValueAnimator lottieValueAnimator = lottieDrawable.animator;
            boolean z2 = lottieValueAnimator.composition == null;
            lottieValueAnimator.composition = lottieComposition;
            if (z2) {
                lottieValueAnimator.setMinAndMaxFrames(Math.max(lottieValueAnimator.minFrame, lottieComposition.startFrame), Math.min(lottieValueAnimator.maxFrame, lottieComposition.endFrame));
            } else {
                lottieValueAnimator.setMinAndMaxFrames((int) lottieComposition.startFrame, (int) lottieComposition.endFrame);
            }
            float f = lottieValueAnimator.frame;
            lottieValueAnimator.frame = 0.0f;
            lottieValueAnimator.frameRaw = 0.0f;
            lottieValueAnimator.setFrame((int) f);
            lottieValueAnimator.notifyUpdate();
            lottieDrawable.setProgress(lottieDrawable.animator.getAnimatedFraction());
            Iterator it = new ArrayList(lottieDrawable.lazyCompositionTasks).iterator();
            while (it.hasNext()) {
                LottieDrawable.LazyCompositionTask lazyCompositionTask = (LottieDrawable.LazyCompositionTask) it.next();
                if (lazyCompositionTask != null) {
                    lazyCompositionTask.run();
                }
                it.remove();
            }
            lottieDrawable.lazyCompositionTasks.clear();
            lottieComposition.performanceTracker.enabled = false;
            lottieDrawable.computeRenderMode();
            Drawable.Callback callback = lottieDrawable.getCallback();
            if (callback instanceof ImageView) {
                ImageView imageView = (ImageView) callback;
                imageView.setImageDrawable(null);
                imageView.setImageDrawable(lottieDrawable);
            }
        }
        this.ignoreUnschedule = false;
        Drawable drawable = getDrawable();
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (drawable != lottieDrawable2 || z) {
            if (!z) {
                LottieValueAnimator lottieValueAnimator2 = lottieDrawable2.animator;
                boolean z3 = lottieValueAnimator2 != null ? lottieValueAnimator2.running : false;
                setImageDrawable(null);
                setImageDrawable(this.lottieDrawable);
                if (z3) {
                    this.lottieDrawable.resumeAnimation();
                }
            }
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            Iterator it2 = ((HashSet) this.lottieOnCompositionLoadedListeners).iterator();
            while (it2.hasNext()) {
                ((LottieOnCompositionLoadedListener) it2.next()).onCompositionLoaded(lottieComposition);
            }
        }
    }

    public final void setCompositionTask(LottieTask lottieTask) {
        ((HashSet) this.userActionsTaken).add(UserActionTaken.SET_ANIMATION);
        this.composition = null;
        this.lottieDrawable.clearComposition();
        cancelLoaderTask();
        lottieTask.addListener(this.loadedListener);
        lottieTask.addFailureListener(this.wrappedFailureListener);
        this.compositionTask = lottieTask;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public final void setImageBitmap(Bitmap bitmap) {
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public final void setImageResource(int i) {
        cancelLoaderTask();
        super.setImageResource(i);
    }

    public final void setProgressInternal(float f, boolean z) {
        if (z) {
            this.userActionsTaken.add(UserActionTaken.SET_PROGRESS);
        }
        this.lottieDrawable.setProgress(f);
    }

    public final void setRepeatCount(int i) {
        ((HashSet) this.userActionsTaken).add(UserActionTaken.SET_REPEAT_COUNT);
        this.lottieDrawable.animator.setRepeatCount(i);
    }

    public final void setRepeatMode(int i) {
        this.userActionsTaken.add(UserActionTaken.SET_REPEAT_MODE);
        this.lottieDrawable.animator.setRepeatMode(i);
    }

    @Override // android.view.View
    public final void unscheduleDrawable(Drawable drawable) {
        LottieDrawable lottieDrawable;
        boolean z = this.ignoreUnschedule;
        if (!z && drawable == (lottieDrawable = this.lottieDrawable)) {
            LottieValueAnimator lottieValueAnimator = lottieDrawable.animator;
            if (lottieValueAnimator == null ? false : lottieValueAnimator.running) {
                pauseAnimation();
                super.unscheduleDrawable(drawable);
            }
        }
        if (!z && (drawable instanceof LottieDrawable)) {
            LottieDrawable lottieDrawable2 = (LottieDrawable) drawable;
            LottieValueAnimator lottieValueAnimator2 = lottieDrawable2.animator;
            if (lottieValueAnimator2 != null ? lottieValueAnimator2.running : false) {
                lottieDrawable2.pauseAnimation();
            }
        }
        super.unscheduleDrawable(drawable);
    }

    public final void addValueCallback(KeyPath keyPath, Object obj, final SimpleLottieValueCallback simpleLottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, obj, new LottieValueCallback(this) { // from class: com.airbnb.lottie.LottieAnimationView.1
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                return simpleLottieValueCallback.getValue();
            }
        });
    }

    public final void setAnimation(final String str) {
        LottieTask cache;
        LottieTask lottieTask;
        this.animationName = str;
        this.animationResId = 0;
        int i = 1;
        if (isInEditMode()) {
            lottieTask = new LottieTask(new Callable() { // from class: com.airbnb.lottie.LottieAnimationView$$ExternalSyntheticLambda2
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    LottieAnimationView lottieAnimationView = LottieAnimationView.this;
                    boolean z = lottieAnimationView.cacheComposition;
                    String str2 = str;
                    if (!z) {
                        return LottieCompositionFactory.fromAssetSync(lottieAnimationView.getContext(), str2, null);
                    }
                    Context context = lottieAnimationView.getContext();
                    Map map = LottieCompositionFactory.taskCache;
                    return LottieCompositionFactory.fromAssetSync(context, str2, "asset_" + str2);
                }
            }, true);
        } else {
            if (this.cacheComposition) {
                cache = LottieCompositionFactory.fromAsset(getContext(), str);
            } else {
                Context context = getContext();
                Map map = LottieCompositionFactory.taskCache;
                cache = LottieCompositionFactory.cache(null, new LottieCompositionFactory$$ExternalSyntheticLambda0(context.getApplicationContext(), str, null, i), null);
            }
            lottieTask = cache;
        }
        setCompositionTask(lottieTask);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loadedListener = new WeakSuccessListener(this);
        this.wrappedFailureListener = new WeakFailureListener(this);
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.ignoreUnschedule = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.userActionsTaken = new HashSet();
        this.lottieOnCompositionLoadedListeners = new HashSet();
        init(attributeSet, R.attr.lottieAnimationViewStyle);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.loadedListener = new WeakSuccessListener(this);
        this.wrappedFailureListener = new WeakFailureListener(this);
        this.fallbackResource = 0;
        this.lottieDrawable = new LottieDrawable();
        this.ignoreUnschedule = false;
        this.autoPlay = false;
        this.cacheComposition = true;
        this.userActionsTaken = new HashSet();
        this.lottieOnCompositionLoadedListeners = new HashSet();
        init(attributeSet, i);
    }
}
