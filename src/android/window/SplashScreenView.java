package android.window;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DecorView;
import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* loaded from: classes5.dex */
public final class SplashScreenView extends FrameLayout {
    private static final boolean DEBUG = Build.IS_DEBUGGABLE;
    private static final String TAG = "SplashScreenView";
    private View mBrandingImageView;
    private RemoteCallback mClientCallback;
    private boolean mHasRemoved;
    private Duration mIconAnimationDuration;
    private Instant mIconAnimationStart;
    private View mIconView;
    private int mInitBackgroundColor;
    private boolean mIsCopied;
    private boolean mNotCopyable;
    private Bitmap mParceledBrandingBitmap;
    private Bitmap mParceledIconBackgroundBitmap;
    private Bitmap mParceledIconBitmap;
    private SurfaceControlViewHost mSurfaceHost;
    private SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private SurfaceControlViewHost.SurfacePackage mSurfacePackageCopy;
    private SurfaceView mSurfaceView;
    private final int[] mTmpPos;
    private final Rect mTmpRect;
    private Window mWindow;

    public interface IconAnimateListener {
        void prepareAnimate(LongConsumer longConsumer);

        default void setAnimationJankMonitoring(AnimatorListenerAdapter animatorListenerAdapter) {
        }

        void stopAnimation();
    }

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean mAllowHandleSolidColor = true;
        private int mBackgroundColor;
        private Drawable mBrandingDrawable;
        private int mBrandingImageHeight;
        private int mBrandingImageWidth;
        private RemoteCallback mClientCallback;
        private final Context mContext;
        private Duration mIconAnimationDuration;
        private Instant mIconAnimationStart;
        private Drawable mIconBackground;
        private Drawable mIconDrawable;
        private int mIconSize;
        private Drawable mOverlayDrawable;
        private Bitmap mParceledBrandingBitmap;
        private Bitmap mParceledIconBackgroundBitmap;
        private Bitmap mParceledIconBitmap;
        private SurfaceControlViewHost.SurfacePackage mSurfacePackage;
        private Consumer<Runnable> mUiThreadInitTask;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder createFromParcel(SplashScreenViewParcelable splashScreenViewParcelable) {
            this.mIconSize = splashScreenViewParcelable.getIconSize();
            this.mBackgroundColor = splashScreenViewParcelable.getBackgroundColor();
            SurfaceControlViewHost.SurfacePackage surfacePackage = splashScreenViewParcelable.mSurfacePackage;
            this.mSurfacePackage = surfacePackage;
            if (surfacePackage == null && splashScreenViewParcelable.mIconBitmap != null) {
                this.mIconDrawable = new BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mIconBitmap);
                this.mParceledIconBitmap = splashScreenViewParcelable.mIconBitmap;
            }
            if (splashScreenViewParcelable.mIconBackground != null) {
                this.mIconBackground = new BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mIconBackground);
                this.mParceledIconBackgroundBitmap = splashScreenViewParcelable.mIconBackground;
            }
            if (splashScreenViewParcelable.mBrandingBitmap != null) {
                setBrandingDrawable(new BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mBrandingBitmap), splashScreenViewParcelable.mBrandingWidth, splashScreenViewParcelable.mBrandingHeight);
                this.mParceledBrandingBitmap = splashScreenViewParcelable.mBrandingBitmap;
            }
            this.mIconAnimationStart = Instant.ofEpochMilli(splashScreenViewParcelable.mIconAnimationStartMillis);
            this.mIconAnimationDuration = Duration.ofMillis(splashScreenViewParcelable.mIconAnimationDurationMillis);
            this.mClientCallback = splashScreenViewParcelable.mClientCallback;
            if (SplashScreenView.DEBUG) {
                Log.d(SplashScreenView.TAG, String.format("Building from parcel drawable: %s", this.mIconDrawable));
            }
            return this;
        }

        public Builder setIconSize(int i) {
            this.mIconSize = i;
            return this;
        }

        public Builder setBackgroundColor(int i) {
            this.mBackgroundColor = i;
            return this;
        }

        public Builder setOverlayDrawable(Drawable drawable) {
            this.mOverlayDrawable = drawable;
            return this;
        }

        public Builder setCenterViewDrawable(Drawable drawable) {
            this.mIconDrawable = drawable;
            return this;
        }

        public Builder setIconBackground(Drawable drawable) {
            this.mIconBackground = drawable;
            return this;
        }

        public Builder setUiThreadInitConsumer(Consumer<Runnable> consumer) {
            this.mUiThreadInitTask = consumer;
            return this;
        }

        public Builder setBrandingDrawable(Drawable drawable, int i, int i2) {
            this.mBrandingDrawable = drawable;
            this.mBrandingImageWidth = i;
            this.mBrandingImageHeight = i2;
            return this;
        }

        public Builder setAllowHandleSolidColor(boolean z) {
            this.mAllowHandleSolidColor = z;
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00f1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.window.SplashScreenView build() {
            /*
                Method dump skipped, instructions count: 338
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.window.SplashScreenView.Builder.build():android.window.SplashScreenView");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$0(SplashScreenView splashScreenView) {
            splashScreenView.mIconView = createSurfaceView(splashScreenView);
        }

        private SurfaceView createSurfaceView(SplashScreenView splashScreenView) {
            Trace.traceBegin(32L, "SplashScreenView#createSurfaceView");
            Context context = splashScreenView.getContext();
            SurfaceView surfaceView = new SurfaceView(context);
            surfaceView.setPadding(0, 0, 0, 0);
            surfaceView.setBackground(this.mIconBackground);
            if (this.mSurfacePackage == null) {
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "SurfaceControlViewHost created on thread " + Thread.currentThread().getId());
                }
                AttachedSurfaceControl rootSurfaceControl = surfaceView.getRootSurfaceControl();
                SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, context.getDisplay(), rootSurfaceControl == null ? null : rootSurfaceControl.getInputTransferToken(), SplashScreenView.TAG);
                ImageView imageView = new ImageView(context);
                imageView.setBackground(this.mIconDrawable);
                int i = this.mIconSize;
                surfaceControlViewHost.setView(imageView, new WindowManager.LayoutParams(i, i, 2, 131096, -2));
                SurfaceControlViewHost.SurfacePackage surfacePackage = surfaceControlViewHost.getSurfacePackage();
                surfaceView.setChildSurfacePackage(surfacePackage);
                splashScreenView.mSurfacePackage = surfacePackage;
                splashScreenView.mSurfaceHost = surfaceControlViewHost;
                splashScreenView.mSurfacePackageCopy = new SurfaceControlViewHost.SurfacePackage(surfacePackage);
            } else {
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "Using copy of SurfacePackage in the client");
                }
                splashScreenView.mSurfacePackage = this.mSurfacePackage;
            }
            if (this.mIconSize != 0) {
                int i2 = this.mIconSize;
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i2);
                layoutParams.gravity = 17;
                surfaceView.setLayoutParams(layoutParams);
                if (SplashScreenView.DEBUG) {
                    Log.d(SplashScreenView.TAG, "Icon size " + this.mIconSize);
                }
            }
            surfaceView.setUseAlpha();
            surfaceView.setZOrderOnTop(true);
            surfaceView.getHolder().setFormat(-3);
            splashScreenView.addView(surfaceView);
            splashScreenView.mSurfaceView = surfaceView;
            Trace.traceEnd(32L);
            return surfaceView;
        }
    }

    public SplashScreenView(Context context) {
        super(context);
        this.mTmpRect = new Rect();
        this.mTmpPos = new int[2];
    }

    public SplashScreenView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpRect = new Rect();
        this.mTmpPos = new int[2];
    }

    public void setNotCopyable() {
        this.mNotCopyable = true;
    }

    public boolean isCopyable() {
        return !this.mNotCopyable;
    }

    public void onCopied() {
        this.mIsCopied = true;
        if (this.mSurfaceView == null || this.mSurfacePackage == null) {
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "Setting SurfaceView's SurfacePackage to null.");
        }
        this.mSurfacePackage.release();
        this.mSurfacePackage = null;
    }

    public SurfaceControlViewHost getSurfaceHost() {
        return this.mSurfaceHost;
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setAlpha(surfaceView.getAlpha() * f);
        }
    }

    public Duration getIconAnimationDuration() {
        return this.mIconAnimationDuration;
    }

    public Instant getIconAnimationStart() {
        return this.mIconAnimationStart;
    }

    public void syncTransferSurfaceOnDraw() {
        SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
        if (surfacePackage == null) {
            return;
        }
        if (DEBUG) {
            surfacePackage.getSurfaceControl().addOnReparentListener(new SurfaceControl.OnReparentListener() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda0
                @Override // android.view.SurfaceControl.OnReparentListener
                public final void onReparent(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
                    Log.e(SplashScreenView.TAG, String.format("SurfacePackage'surface reparented to %s", surfaceControl));
                }
            });
            Log.d(TAG, "Transferring surface " + this.mSurfaceView.toString());
        }
        this.mSurfaceView.setChildSurfacePackage(this.mSurfacePackage);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void initIconAnimation(Drawable drawable) {
        if (drawable instanceof IconAnimateListener) {
            IconAnimateListener iconAnimateListener = (IconAnimateListener) drawable;
            iconAnimateListener.prepareAnimate(new LongConsumer() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    SplashScreenView.this.animationStartCallback(j);
                }
            });
            iconAnimateListener.setAnimationJankMonitoring(new AnimatorListenerAdapter() { // from class: android.window.SplashScreenView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    InteractionJankMonitor.getInstance().cancel(38);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    InteractionJankMonitor.getInstance().end(38);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    InteractionJankMonitor.getInstance().begin(SplashScreenView.this, 38);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animationStartCallback(long j) {
        this.mIconAnimationStart = Instant.now();
        if (j >= 0) {
            this.mIconAnimationDuration = Duration.ofMillis(j);
        }
    }

    public void remove() {
        if (this.mHasRemoved) {
            return;
        }
        setVisibility(8);
        if (this.mParceledIconBitmap != null) {
            View view = this.mIconView;
            if (view instanceof ImageView) {
                ((ImageView) view).lambda$setImageURIAsync$2(null);
            } else if (view != null) {
                view.setBackground(null);
            }
            this.mParceledIconBitmap.recycle();
            this.mParceledIconBitmap = null;
        }
        if (this.mParceledBrandingBitmap != null) {
            this.mBrandingImageView.setBackground(null);
            this.mParceledBrandingBitmap.recycle();
            this.mParceledBrandingBitmap = null;
        }
        if (this.mParceledIconBackgroundBitmap != null) {
            View view2 = this.mIconView;
            if (view2 != null) {
                view2.setBackground(null);
            }
            this.mParceledIconBackgroundBitmap.recycle();
            this.mParceledIconBackgroundBitmap = null;
        }
        Window window = this.mWindow;
        if (window != null) {
            DecorView decorView = (DecorView) window.peekDecorView();
            if (DEBUG) {
                Log.d(TAG, "remove starting view");
            }
            if (decorView != null) {
                decorView.removeView(this);
            }
            this.mWindow = null;
        }
        this.mHasRemoved = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseAnimationSurfaceHost();
        View view = this.mIconView;
        if (view instanceof ImageView) {
            Object drawable = ((ImageView) view).getDrawable();
            if (drawable instanceof Closeable) {
                try {
                    ((Closeable) drawable).close();
                } catch (IOException unused) {
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mBrandingImageView.getDrawingRect(this.mTmpRect);
        int height = this.mTmpRect.height();
        if (height == 0 || this.mIconView == null || this.mBrandingImageView.getVisibility() != 0) {
            return;
        }
        int i5 = i4 - i2;
        this.mIconView.getLocationInWindow(this.mTmpPos);
        this.mIconView.getDrawingRect(this.mTmpRect);
        int height2 = this.mTmpRect.height();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBrandingImageView.getLayoutParams();
        if (marginLayoutParams == null) {
            Log.e(TAG, "Unable to adjust branding image layout, layout changed?");
            return;
        }
        int i6 = marginLayoutParams.bottomMargin;
        int i7 = (i5 - this.mTmpPos[1]) - height2;
        int i8 = i7 - height;
        if (i7 < height) {
            this.mBrandingImageView.setVisibility(8);
        } else if (i8 < i6) {
            marginLayoutParams.bottomMargin = (int) Math.round(i8 / 2.0d);
            this.mBrandingImageView.setLayoutParams(marginLayoutParams);
        }
    }

    private void releaseAnimationSurfaceHost() {
        SurfaceControlViewHost surfaceControlViewHost = this.mSurfaceHost;
        if (surfaceControlViewHost != null && !this.mIsCopied) {
            if (DEBUG) {
                Log.d(TAG, "Shell removed splash screen. Releasing SurfaceControlViewHost on thread #" + Thread.currentThread().getId());
            }
            releaseIconHost(this.mSurfaceHost);
            this.mSurfaceHost = null;
            return;
        }
        if (this.mSurfacePackage == null || surfaceControlViewHost != null) {
            return;
        }
        this.mSurfacePackage = null;
        this.mClientCallback.sendResult(null);
    }

    public static void releaseIconHost(SurfaceControlViewHost surfaceControlViewHost) {
        Object background = surfaceControlViewHost.getView().getBackground();
        if (background instanceof IconAnimateListener) {
            ((IconAnimateListener) background).stopAnimation();
        }
        surfaceControlViewHost.release();
    }

    public void attachHostWindow(Window window) {
        this.mWindow = window;
    }

    public View getIconView() {
        return this.mIconView;
    }

    public View getBrandingView() {
        return this.mBrandingImageView;
    }

    public int getInitBackgroundColor() {
        return this.mInitBackgroundColor;
    }

    public static class SplashScreenViewParcelable implements Parcelable {
        public static final Parcelable.Creator<SplashScreenViewParcelable> CREATOR = new Parcelable.Creator<SplashScreenViewParcelable>() { // from class: android.window.SplashScreenView.SplashScreenViewParcelable.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SplashScreenViewParcelable createFromParcel(Parcel parcel) {
                return new SplashScreenViewParcelable(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SplashScreenViewParcelable[] newArray(int i) {
                return new SplashScreenViewParcelable[i];
            }
        };
        private int mBackgroundColor;
        private Bitmap mBrandingBitmap;
        private int mBrandingHeight;
        private int mBrandingWidth;
        private RemoteCallback mClientCallback;
        private long mIconAnimationDurationMillis;
        private long mIconAnimationStartMillis;
        private Bitmap mIconBackground;
        private Bitmap mIconBitmap;
        private int mIconSize;
        private SurfaceControlViewHost.SurfacePackage mSurfacePackage;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SplashScreenViewParcelable(SplashScreenView splashScreenView) {
            this.mIconBitmap = null;
            View iconView = splashScreenView.getIconView();
            this.mIconSize = iconView != null ? iconView.getWidth() : 0;
            this.mBackgroundColor = splashScreenView.getInitBackgroundColor();
            this.mIconBackground = iconView != null ? copyDrawable(iconView.getBackground()) : null;
            SurfaceControlViewHost.SurfacePackage surfacePackage = splashScreenView.mSurfacePackageCopy;
            this.mSurfacePackage = surfacePackage;
            if (surfacePackage == null) {
                this.mIconBitmap = iconView != null ? copyDrawable(((ImageView) splashScreenView.getIconView()).getDrawable()) : null;
            }
            ViewGroup.LayoutParams layoutParams = splashScreenView.getBrandingView().getLayoutParams();
            this.mBrandingWidth = layoutParams.width;
            this.mBrandingHeight = layoutParams.height;
            this.mBrandingBitmap = copyDrawableWithSize(splashScreenView.getBrandingView().getBackground(), this.mBrandingWidth, this.mBrandingHeight);
            if (splashScreenView.getIconAnimationStart() != null) {
                this.mIconAnimationStartMillis = splashScreenView.getIconAnimationStart().toEpochMilli();
            }
            if (splashScreenView.getIconAnimationDuration() != null) {
                this.mIconAnimationDurationMillis = splashScreenView.getIconAnimationDuration().toMillis();
            }
        }

        private Bitmap copyDrawable(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            Rect copyBounds = drawable.copyBounds();
            return copyDrawableWithSize(drawable, copyBounds.width(), copyBounds.height());
        }

        private Bitmap copyDrawableWithSize(Drawable drawable, int i, int i2) {
            if (drawable == null || i <= 0 || i2 <= 0) {
                return null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, i, i2);
            drawable.draw(canvas);
            Bitmap createAshmemBitmap = createBitmap.createAshmemBitmap();
            createBitmap.recycle();
            return createAshmemBitmap;
        }

        private SplashScreenViewParcelable(Parcel parcel) {
            this.mIconBitmap = null;
            readParcel(parcel);
        }

        private void readParcel(Parcel parcel) {
            this.mIconSize = parcel.readInt();
            this.mBackgroundColor = parcel.readInt();
            this.mIconBitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
            this.mBrandingWidth = parcel.readInt();
            this.mBrandingHeight = parcel.readInt();
            this.mBrandingBitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
            this.mIconAnimationStartMillis = parcel.readLong();
            this.mIconAnimationDurationMillis = parcel.readLong();
            this.mIconBackground = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
            this.mSurfacePackage = (SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
            this.mClientCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mIconSize);
            parcel.writeInt(this.mBackgroundColor);
            parcel.writeTypedObject(this.mIconBitmap, i);
            parcel.writeInt(this.mBrandingWidth);
            parcel.writeInt(this.mBrandingHeight);
            parcel.writeTypedObject(this.mBrandingBitmap, i);
            parcel.writeLong(this.mIconAnimationStartMillis);
            parcel.writeLong(this.mIconAnimationDurationMillis);
            parcel.writeTypedObject(this.mIconBackground, i);
            parcel.writeTypedObject(this.mSurfacePackage, i);
            parcel.writeTypedObject(this.mClientCallback, i);
        }

        public void clearIfNeeded() {
            Bitmap bitmap = this.mIconBitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.mIconBitmap = null;
            }
            Bitmap bitmap2 = this.mBrandingBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
                this.mBrandingBitmap = null;
            }
        }

        int getIconSize() {
            return this.mIconSize;
        }

        int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public void setClientCallback(RemoteCallback remoteCallback) {
            this.mClientCallback = remoteCallback;
        }
    }
}
