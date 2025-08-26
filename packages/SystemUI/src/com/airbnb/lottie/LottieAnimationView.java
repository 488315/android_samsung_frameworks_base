package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class LottieAnimationView extends AppCompatImageView {
    public static final /* synthetic */ int $r8$clinit = 0;
    private String animationName;
    private int animationResId;
    private boolean autoPlay;
    private boolean cacheComposition;
    private LottieComposition composition;
    private LottieTask compositionTask;
    private LottieListener failureListener;
    private int fallbackResource;
    private boolean ignoreUnschedule;
    private final LottieListener loadedListener;
    private final LottieDrawable lottieDrawable;
    private final Set<LottieOnCompositionLoadedListener> lottieOnCompositionLoadedListeners;
    private final Set<UserActionTaken> userActionsTaken;
    private final LottieListener wrappedFailureListener;
    private static final String TAG = "LottieAnimationView";
    private static final LottieListener DEFAULT_FAILURE_LISTENER = new LottieAnimationView$$ExternalSyntheticLambda1();

    public class SavedState extends View.BaseSavedState {
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

    enum UserActionTaken {
        SET_ANIMATION,
        SET_PROGRESS,
        SET_REPEAT_MODE,
        SET_REPEAT_COUNT,
        SET_IMAGE_ASSETS,
        PLAY_OPTION
    }

    public class WeakFailureListener implements LottieListener {
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
            if (lottieAnimationView.fallbackResource != 0) {
                lottieAnimationView.setImageResource(lottieAnimationView.fallbackResource);
            }
            (lottieAnimationView.failureListener == null ? LottieAnimationView.DEFAULT_FAILURE_LISTENER : lottieAnimationView.failureListener).onResult(th);
        }
    }

    public class WeakSuccessListener implements LottieListener {
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

    public static LottieResult $r8$lambda$O1_TWNBXrGORBOUM9inamZkQ5H0(LottieAnimationView lottieAnimationView, String str) {
        if (!lottieAnimationView.cacheComposition) {
            return LottieCompositionFactory.fromAssetSync(lottieAnimationView.getContext(), str, null);
        }
        Context context = lottieAnimationView.getContext();
        Map map = LottieCompositionFactory.taskCache;
        return LottieCompositionFactory.fromAssetSync(context, str, "asset_" + str);
    }

    public static LottieResult $r8$lambda$lm7IBxttXmMSWkbtYZepWooj1JA(LottieAnimationView lottieAnimationView, int i) {
        if (!lottieAnimationView.cacheComposition) {
            return LottieCompositionFactory.fromRawResSync(lottieAnimationView.getContext(), null, i);
        }
        Context context = lottieAnimationView.getContext();
        return LottieCompositionFactory.fromRawResSync(context, LottieCompositionFactory.rawResCacheKey(i, context), i);
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
        init$1(null, R.attr.lottieAnimationViewStyle);
    }

    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.lottieDrawable.animator.addListener(animatorListener);
    }

    public void addAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.lottieDrawable.animator.addPauseListener(animatorPauseListener);
    }

    public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.animator.addUpdateListener(animatorUpdateListener);
    }

    public boolean addLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        if (this.composition != null) {
            lottieOnCompositionLoadedListener.onCompositionLoaded();
        }
        return this.lottieOnCompositionLoadedListeners.add(lottieOnCompositionLoadedListener);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, LottieValueCallback lottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, lottieValueCallback);
    }

    public void cancelAnimation() {
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
            LottieListener lottieListener = this.loadedListener;
            synchronized (lottieTask) {
                lottieTask.successListeners.remove(lottieListener);
            }
            LottieTask lottieTask2 = this.compositionTask;
            LottieListener lottieListener2 = this.wrappedFailureListener;
            synchronized (lottieTask2) {
                lottieTask2.failureListeners.remove(lottieListener2);
            }
        }
    }

    @Deprecated
    public void disableExtraScaleModeInFitXY() {
        this.lottieDrawable.getClass();
    }

    public void enableMergePathsForKitKatAndAbove(boolean z) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable.enableMergePaths == z) {
            return;
        }
        lottieDrawable.enableMergePaths = z;
        if (lottieDrawable.composition != null) {
            lottieDrawable.buildCompositionLayer();
        }
    }

    public AsyncUpdates getAsyncUpdates() {
        return this.lottieDrawable.asyncUpdates;
    }

    public boolean getAsyncUpdatesEnabled() {
        return this.lottieDrawable.asyncUpdates == AsyncUpdates.ENABLED;
    }

    public boolean getClipToCompositionBounds() {
        return this.lottieDrawable.clipToCompositionBounds;
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
        return (int) this.lottieDrawable.animator.frame;
    }

    public String getImageAssetsFolder() {
        return this.lottieDrawable.imageAssetsFolder;
    }

    public boolean getMaintainOriginalImageBounds() {
        return this.lottieDrawable.maintainOriginalImageBounds;
    }

    public float getMaxFrame() {
        return this.lottieDrawable.animator.getMaxFrame();
    }

    public float getMinFrame() {
        return this.lottieDrawable.animator.getMinFrame();
    }

    public PerformanceTracker getPerformanceTracker() {
        LottieComposition lottieComposition = this.lottieDrawable.composition;
        if (lottieComposition != null) {
            return lottieComposition.performanceTracker;
        }
        return null;
    }

    public float getProgress() {
        return this.lottieDrawable.animator.getAnimatedValueAbsolute();
    }

    public RenderMode getRenderMode() {
        return this.lottieDrawable.useSoftwareRendering ? RenderMode.SOFTWARE : RenderMode.HARDWARE;
    }

    public int getRepeatCount() {
        return this.lottieDrawable.animator.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.lottieDrawable.animator.getRepeatMode();
    }

    public float getSpeed() {
        return this.lottieDrawable.animator.speed;
    }

    public boolean hasMasks() {
        CompositionLayer compositionLayer = this.lottieDrawable.compositionLayer;
        return compositionLayer != null && compositionLayer.hasMasks();
    }

    public boolean hasMatte() {
        boolean zBooleanValue;
        CompositionLayer compositionLayer = this.lottieDrawable.compositionLayer;
        if (compositionLayer == null) {
            return false;
        }
        if (compositionLayer.hasMatte == null) {
            if (compositionLayer.matteLayer != null) {
                compositionLayer.hasMatte = Boolean.TRUE;
            } else {
                for (int size = ((ArrayList) compositionLayer.layers).size() - 1; size >= 0; size--) {
                    if (((BaseLayer) ((ArrayList) compositionLayer.layers).get(size)).matteLayer != null) {
                        compositionLayer.hasMatte = Boolean.TRUE;
                    }
                }
                compositionLayer.hasMatte = Boolean.FALSE;
                zBooleanValue = compositionLayer.hasMatte.booleanValue();
            }
            zBooleanValue = true;
            break;
        } else {
            zBooleanValue = compositionLayer.hasMatte.booleanValue();
        }
        return zBooleanValue;
    }

    public final void init$1(AttributeSet attributeSet, int i) {
        String string;
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, i, 0);
        this.cacheComposition = typedArrayObtainStyledAttributes.getBoolean(2, true);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(13);
        boolean zHasValue2 = typedArrayObtainStyledAttributes.hasValue(8);
        boolean zHasValue3 = typedArrayObtainStyledAttributes.hasValue(18);
        if (zHasValue && zHasValue2) {
            throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
        }
        if (zHasValue) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(13, 0);
            if (resourceId != 0) {
                setAnimation(resourceId);
            }
        } else if (zHasValue2) {
            String string2 = typedArrayObtainStyledAttributes.getString(8);
            if (string2 != null) {
                setAnimation(string2);
            }
        } else if (zHasValue3 && (string = typedArrayObtainStyledAttributes.getString(18)) != null) {
            setAnimationFromUrl(string);
        }
        setFallbackResource(typedArrayObtainStyledAttributes.getResourceId(7, 0));
        if (typedArrayObtainStyledAttributes.getBoolean(1, false)) {
            this.autoPlay = true;
        }
        if (typedArrayObtainStyledAttributes.getBoolean(11, false)) {
            this.lottieDrawable.animator.setRepeatCount(-1);
        }
        if (typedArrayObtainStyledAttributes.hasValue(16)) {
            setRepeatMode(typedArrayObtainStyledAttributes.getInt(16, 1));
        }
        if (typedArrayObtainStyledAttributes.hasValue(15)) {
            setRepeatCount(typedArrayObtainStyledAttributes.getInt(15, -1));
        }
        if (typedArrayObtainStyledAttributes.hasValue(17)) {
            setSpeed(typedArrayObtainStyledAttributes.getFloat(17, 1.0f));
        }
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            setClipToCompositionBounds(typedArrayObtainStyledAttributes.getBoolean(3, true));
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            setDefaultFontFileExtension(typedArrayObtainStyledAttributes.getString(5));
        }
        setImageAssetsFolder(typedArrayObtainStyledAttributes.getString(10));
        boolean zHasValue4 = typedArrayObtainStyledAttributes.hasValue(12);
        float f = typedArrayObtainStyledAttributes.getFloat(12, 0.0f);
        if (zHasValue4) {
            this.userActionsTaken.add(UserActionTaken.SET_PROGRESS);
        }
        this.lottieDrawable.setProgress(f);
        enableMergePathsForKitKatAndAbove(typedArrayObtainStyledAttributes.getBoolean(6, false));
        if (typedArrayObtainStyledAttributes.hasValue(4)) {
            addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(ContextCompat.getColorStateList(typedArrayObtainStyledAttributes.getResourceId(4, -1), getContext()).getDefaultColor())));
        }
        if (typedArrayObtainStyledAttributes.hasValue(14)) {
            RenderMode renderMode = RenderMode.AUTOMATIC;
            int iOrdinal = typedArrayObtainStyledAttributes.getInt(14, renderMode.ordinal());
            if (iOrdinal >= RenderMode.values().length) {
                iOrdinal = renderMode.ordinal();
            }
            setRenderMode(RenderMode.values()[iOrdinal]);
        }
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            AsyncUpdates asyncUpdates = AsyncUpdates.AUTOMATIC;
            int iOrdinal2 = typedArrayObtainStyledAttributes.getInt(0, asyncUpdates.ordinal());
            if (iOrdinal2 >= RenderMode.values().length) {
                iOrdinal2 = asyncUpdates.ordinal();
            }
            setAsyncUpdates(AsyncUpdates.values()[iOrdinal2]);
        }
        setIgnoreDisabledSystemAnimations(typedArrayObtainStyledAttributes.getBoolean(9, false));
        if (typedArrayObtainStyledAttributes.hasValue(19)) {
            setUseCompositionFrameRate(typedArrayObtainStyledAttributes.getBoolean(19, false));
        }
        typedArrayObtainStyledAttributes.recycle();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        Context context = getContext();
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        boolean z = Settings.Global.getFloat(context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) != 0.0f;
        lottieDrawable.getClass();
        lottieDrawable.systemAnimationsEnabled = z;
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
    public void invalidateDrawable(Drawable drawable) {
        Drawable drawable2 = getDrawable();
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (drawable2 == lottieDrawable) {
            super.invalidateDrawable(lottieDrawable);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public boolean isAnimating() {
        LottieValueAnimator lottieValueAnimator = this.lottieDrawable.animator;
        if (lottieValueAnimator == null) {
            return false;
        }
        return lottieValueAnimator.running;
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.lottieDrawable.enableMergePaths;
    }

    @Deprecated
    public void loop(boolean z) {
        this.lottieDrawable.animator.setRepeatCount(z ? -1 : 0);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode() || !this.autoPlay) {
            return;
        }
        this.lottieDrawable.playAnimation();
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        int i;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.animationName = savedState.animationName;
        Set<UserActionTaken> set = this.userActionsTaken;
        UserActionTaken userActionTaken = UserActionTaken.SET_ANIMATION;
        if (!set.contains(userActionTaken) && !TextUtils.isEmpty(this.animationName)) {
            setAnimation(this.animationName);
        }
        this.animationResId = savedState.animationResId;
        if (!this.userActionsTaken.contains(userActionTaken) && (i = this.animationResId) != 0) {
            setAnimation(i);
        }
        if (!this.userActionsTaken.contains(UserActionTaken.SET_PROGRESS)) {
            this.lottieDrawable.setProgress(savedState.progress);
        }
        if (!this.userActionsTaken.contains(UserActionTaken.PLAY_OPTION) && savedState.isAnimating) {
            playAnimation();
        }
        if (!this.userActionsTaken.contains(UserActionTaken.SET_IMAGE_ASSETS)) {
            setImageAssetsFolder(savedState.imageAssetsFolder);
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
    public Parcelable onSaveInstanceState() {
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

    public void pauseAnimation() {
        this.autoPlay = false;
        this.lottieDrawable.pauseAnimation();
    }

    public void playAnimation() {
        this.userActionsTaken.add(UserActionTaken.PLAY_OPTION);
        this.lottieDrawable.playAnimation();
    }

    public void removeAllAnimatorListeners() {
        this.lottieDrawable.animator.removeAllListeners();
    }

    public void removeAllLottieOnCompositionLoadedListener() {
        this.lottieOnCompositionLoadedListeners.clear();
    }

    public void removeAllUpdateListeners() {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        lottieDrawable.animator.removeAllUpdateListeners();
        lottieDrawable.animator.addUpdateListener(lottieDrawable.progressUpdateListener);
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.lottieDrawable.animator.removeListener(animatorListener);
    }

    public void removeAnimatorPauseListener(Animator.AnimatorPauseListener animatorPauseListener) {
        this.lottieDrawable.animator.removePauseListener(animatorPauseListener);
    }

    public boolean removeLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener lottieOnCompositionLoadedListener) {
        return this.lottieOnCompositionLoadedListeners.remove(lottieOnCompositionLoadedListener);
    }

    public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.animator.removeUpdateListener(animatorUpdateListener);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        return this.lottieDrawable.resolveKeyPath(keyPath);
    }

    public void resumeAnimation() {
        this.userActionsTaken.add(UserActionTaken.PLAY_OPTION);
        this.lottieDrawable.resumeAnimation();
    }

    public void reverseAnimationSpeed() {
        LottieValueAnimator lottieValueAnimator = this.lottieDrawable.animator;
        lottieValueAnimator.speed = -lottieValueAnimator.speed;
    }

    public void setAnimation(InputStream inputStream, String str) {
        setCompositionTask(LottieCompositionFactory.cache(str, new LottieCompositionFactory$$ExternalSyntheticLambda1(inputStream, str), new LottieCompositionFactory$$ExternalSyntheticLambda2(inputStream)));
    }

    @Deprecated
    public void setAnimationFromJson(String str) {
        setAnimationFromJson(str, null);
    }

    public void setAnimationFromUrl(String str) {
        LottieTask lottieTaskCache;
        int i = 0;
        String str2 = null;
        if (this.cacheComposition) {
            Context context = getContext();
            Map map = LottieCompositionFactory.taskCache;
            String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("url_", str);
            lottieTaskCache = LottieCompositionFactory.cache(strM, new LottieCompositionFactory$$ExternalSyntheticLambda0(context, str, strM, i), null);
        } else {
            lottieTaskCache = LottieCompositionFactory.cache(null, new LottieCompositionFactory$$ExternalSyntheticLambda0(getContext(), str, str2, i), null);
        }
        setCompositionTask(lottieTaskCache);
    }

    public void setApplyingOpacityToLayersEnabled(boolean z) {
        this.lottieDrawable.isApplyingOpacityToLayersEnabled = z;
    }

    public void setAsyncUpdates(AsyncUpdates asyncUpdates) {
        this.lottieDrawable.asyncUpdates = asyncUpdates;
    }

    public void setCacheComposition(boolean z) {
        this.cacheComposition = z;
    }

    public void setClipToCompositionBounds(boolean z) {
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

    public void setComposition(LottieComposition lottieComposition) {
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        this.ignoreUnschedule = true;
        boolean composition = this.lottieDrawable.setComposition(lottieComposition);
        this.ignoreUnschedule = false;
        if (getDrawable() != this.lottieDrawable || composition) {
            if (!composition) {
                boolean zIsAnimating = isAnimating();
                setImageDrawable(null);
                setImageDrawable(this.lottieDrawable);
                if (zIsAnimating) {
                    this.lottieDrawable.resumeAnimation();
                }
            }
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            Iterator<LottieOnCompositionLoadedListener> it = this.lottieOnCompositionLoadedListeners.iterator();
            if (it.hasNext()) {
                throw FragmentManager$$ExternalSyntheticOutline0.m(it);
            }
        }
    }

    public final void setCompositionTask(LottieTask lottieTask) {
        this.userActionsTaken.add(UserActionTaken.SET_ANIMATION);
        this.composition = null;
        this.lottieDrawable.clearComposition();
        cancelLoaderTask();
        lottieTask.addListener(this.loadedListener);
        lottieTask.addFailureListener(this.wrappedFailureListener);
        this.compositionTask = lottieTask;
    }

    public void setDefaultFontFileExtension(String str) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        lottieDrawable.defaultFontFileExtension = str;
        FontAssetManager fontAssetManager = lottieDrawable.getFontAssetManager();
        if (fontAssetManager != null) {
            fontAssetManager.defaultFontFileExtension = str;
        }
    }

    public void setFailureListener(LottieListener lottieListener) {
        this.failureListener = lottieListener;
    }

    public void setFallbackResource(int i) {
        this.fallbackResource = i;
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.lottieDrawable.fontAssetDelegate = fontAssetDelegate;
    }

    public void setFontMap(Map<String, Typeface> map) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (map == lottieDrawable.fontMap) {
            return;
        }
        lottieDrawable.fontMap = map;
        lottieDrawable.invalidateSelf();
    }

    public void setFrame(int i) {
        this.lottieDrawable.setFrame(i);
    }

    public void setIgnoreDisabledSystemAnimations(boolean z) {
        this.lottieDrawable.ignoreSystemAnimationsDisabled = z;
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        ImageAssetManager imageAssetManager = this.lottieDrawable.imageAssetManager;
    }

    public void setImageAssetsFolder(String str) {
        this.lottieDrawable.imageAssetsFolder = str;
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

    public void setMaintainOriginalImageBounds(boolean z) {
        this.lottieDrawable.maintainOriginalImageBounds = z;
    }

    public void setMaxFrame(int i) {
        this.lottieDrawable.setMaxFrame(i);
    }

    public void setMaxProgress(float f) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        LottieComposition lottieComposition = lottieDrawable.composition;
        if (lottieComposition == null) {
            lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda8(lottieDrawable, f, 1));
            return;
        }
        LottieValueAnimator lottieValueAnimator = lottieDrawable.animator;
        lottieValueAnimator.setMinAndMaxFrames(lottieValueAnimator.minFrame, MiscUtils.lerp(lottieComposition.startFrame, lottieComposition.endFrame, f));
    }

    public void setMinAndMaxFrame(String str) {
        this.lottieDrawable.setMinAndMaxFrame(str);
    }

    public void setMinAndMaxProgress(float f, float f2) {
        this.lottieDrawable.setMinAndMaxProgress(f, f2);
    }

    public void setMinFrame(int i) {
        this.lottieDrawable.setMinFrame(i);
    }

    public void setMinProgress(float f) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        LottieComposition lottieComposition = lottieDrawable.composition;
        if (lottieComposition == null) {
            lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda8(lottieDrawable, f, 0));
        } else {
            lottieDrawable.setMinFrame((int) MiscUtils.lerp(lottieComposition.startFrame, lottieComposition.endFrame, f));
        }
    }

    public void setOutlineMasksAndMattes(boolean z) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (lottieDrawable.outlineMasksAndMattes == z) {
            return;
        }
        lottieDrawable.outlineMasksAndMattes = z;
        CompositionLayer compositionLayer = lottieDrawable.compositionLayer;
        if (compositionLayer != null) {
            compositionLayer.setOutlineMasksAndMattes(z);
        }
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        lottieDrawable.performanceTrackingEnabled = z;
        LottieComposition lottieComposition = lottieDrawable.composition;
        if (lottieComposition != null) {
            lottieComposition.performanceTracker.enabled = z;
        }
    }

    public void setProgress(float f) {
        this.userActionsTaken.add(UserActionTaken.SET_PROGRESS);
        this.lottieDrawable.setProgress(f);
    }

    public void setRenderMode(RenderMode renderMode) {
        LottieDrawable lottieDrawable = this.lottieDrawable;
        lottieDrawable.renderMode = renderMode;
        lottieDrawable.computeRenderMode();
    }

    public void setRepeatCount(int i) {
        this.userActionsTaken.add(UserActionTaken.SET_REPEAT_COUNT);
        this.lottieDrawable.animator.setRepeatCount(i);
    }

    public void setRepeatMode(int i) {
        this.userActionsTaken.add(UserActionTaken.SET_REPEAT_MODE);
        this.lottieDrawable.animator.setRepeatMode(i);
    }

    public void setSafeMode(boolean z) {
        this.lottieDrawable.safeMode = z;
    }

    public void setSpeed(float f) {
        this.lottieDrawable.animator.speed = f;
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.lottieDrawable.textDelegate = textDelegate;
    }

    public void setUseCompositionFrameRate(boolean z) {
        this.lottieDrawable.animator.useCompositionFrameRate = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0017  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void unscheduleDrawable(Drawable drawable) {
        LottieDrawable lottieDrawable;
        boolean z = this.ignoreUnschedule;
        if (!z && drawable == (lottieDrawable = this.lottieDrawable)) {
            LottieValueAnimator lottieValueAnimator = lottieDrawable.animator;
            if (lottieValueAnimator == null ? false : lottieValueAnimator.running) {
                pauseAnimation();
            }
        } else if (!z && (drawable instanceof LottieDrawable)) {
            LottieDrawable lottieDrawable2 = (LottieDrawable) drawable;
            LottieValueAnimator lottieValueAnimator2 = lottieDrawable2.animator;
            if (lottieValueAnimator2 != null ? lottieValueAnimator2.running : false) {
                lottieDrawable2.pauseAnimation();
            }
        }
        super.unscheduleDrawable(drawable);
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        Bitmap bitmap2;
        LottieDrawable lottieDrawable = this.lottieDrawable;
        ImageAssetManager imageAssetManager = lottieDrawable.getImageAssetManager();
        if (imageAssetManager == null) {
            Logger.warning("Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        if (bitmap == null) {
            LottieImageAsset lottieImageAsset = (LottieImageAsset) imageAssetManager.imageAssets.get(str);
            bitmap2 = lottieImageAsset.bitmap;
            lottieImageAsset.bitmap = null;
        } else {
            Bitmap bitmap3 = ((LottieImageAsset) imageAssetManager.imageAssets.get(str)).bitmap;
            imageAssetManager.putBitmap(str, bitmap);
            bitmap2 = bitmap3;
        }
        lottieDrawable.invalidateSelf();
        return bitmap2;
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, final SimpleLottieValueCallback simpleLottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, new LottieValueCallback(this) { // from class: com.airbnb.lottie.LottieAnimationView.1
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                return simpleLottieValueCallback.getValue();
            }
        });
    }

    public void setAnimationFromJson(String str, String str2) {
        setAnimation(new ByteArrayInputStream(str.getBytes()), str2);
    }

    public void setMaxFrame(String str) {
        this.lottieDrawable.setMaxFrame(str);
    }

    public void setMinAndMaxFrame(String str, String str2, boolean z) {
        this.lottieDrawable.setMinAndMaxFrame(str, str2, z);
    }

    public void setMinFrame(String str) {
        this.lottieDrawable.setMinFrame(str);
    }

    public void setAnimation(final int i) {
        LottieTask lottieTaskFromRawRes;
        this.animationResId = i;
        this.animationName = null;
        if (isInEditMode()) {
            lottieTaskFromRawRes = new LottieTask(new Callable() { // from class: com.airbnb.lottie.LottieAnimationView$$ExternalSyntheticLambda2
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return LottieAnimationView.$r8$lambda$lm7IBxttXmMSWkbtYZepWooj1JA(this.f$0, i);
                }
            }, true);
        } else {
            lottieTaskFromRawRes = this.cacheComposition ? LottieCompositionFactory.fromRawRes(i, getContext()) : LottieCompositionFactory.fromRawRes(getContext(), null, i);
        }
        setCompositionTask(lottieTaskFromRawRes);
    }

    public void setMinAndMaxFrame(int i, int i2) {
        this.lottieDrawable.setMinAndMaxFrame(i, i2);
    }

    public void setAnimation(final String str) {
        LottieTask lottieTaskCache;
        int i = 1;
        this.animationName = str;
        this.animationResId = 0;
        if (isInEditMode()) {
            lottieTaskCache = new LottieTask(new Callable() { // from class: com.airbnb.lottie.LottieAnimationView$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return LottieAnimationView.$r8$lambda$O1_TWNBXrGORBOUM9inamZkQ5H0(this.f$0, str);
                }
            }, true);
        } else if (this.cacheComposition) {
            lottieTaskCache = LottieCompositionFactory.fromAsset(getContext(), str);
        } else {
            Context context = getContext();
            Map map = LottieCompositionFactory.taskCache;
            lottieTaskCache = LottieCompositionFactory.cache(null, new LottieCompositionFactory$$ExternalSyntheticLambda0(context.getApplicationContext(), str, null, i), null);
        }
        setCompositionTask(lottieTaskCache);
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
        init$1(attributeSet, R.attr.lottieAnimationViewStyle);
    }

    public void setAnimationFromUrl(String str, String str2) {
        setCompositionTask(LottieCompositionFactory.cache(str2, new LottieCompositionFactory$$ExternalSyntheticLambda0(getContext(), str, str2, 0), null));
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
        init$1(attributeSet, i);
    }
}
