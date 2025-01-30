package androidx.core.view;

import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowInsetsCompat {
    public static final WindowInsetsCompat CONSUMED = Impl30.CONSUMED;
    public final Impl mImpl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BuilderImpl {
        public final WindowInsetsCompat mInsets;

        public BuilderImpl() {
            this(new WindowInsetsCompat((WindowInsetsCompat) null));
        }

        public BuilderImpl(WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat;
        }

        public final void applyInsetTypes() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BuilderImpl30 extends BuilderImpl29 {
        public BuilderImpl30(WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl20 extends Impl {
        public Insets[] mOverriddenInsets;
        public final WindowInsets mPlatformInsets;
        public Insets mRootViewVisibleInsets;
        public WindowInsetsCompat mRootWindowInsets;
        public Insets mSystemWindowInsets;

        public Impl20(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat);
            this.mSystemWindowInsets = null;
            this.mPlatformInsets = windowInsets;
        }

        private Insets getRootStableInsets() {
            WindowInsetsCompat windowInsetsCompat = this.mRootWindowInsets;
            return windowInsetsCompat != null ? windowInsetsCompat.mImpl.getStableInsets() : Insets.NONE;
        }

        private Insets getVisibleInsets(View view) {
            throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void copyRootViewBounds(View view) {
            Insets visibleInsets = getVisibleInsets(view);
            if (visibleInsets == null) {
                visibleInsets = Insets.NONE;
            }
            setRootViewData(visibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void copyWindowDataInto(WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat.mImpl.setRootWindowInsets(this.mRootWindowInsets);
            windowInsetsCompat.mImpl.setRootViewData(this.mRootViewVisibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object obj) {
            if (super.equals(obj)) {
                return Objects.equals(this.mRootViewVisibleInsets, ((Impl20) obj).mRootViewVisibleInsets);
            }
            return false;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsets(int i) {
            return getInsets(i, false);
        }

        public Insets getInsetsForType(int i, boolean z) {
            Insets stableInsets;
            int i2;
            if (i == 1) {
                return z ? Insets.m28of(0, Math.max(getRootStableInsets().top, getSystemWindowInsets().top), 0, 0) : Insets.m28of(0, getSystemWindowInsets().top, 0, 0);
            }
            if (i == 2) {
                if (z) {
                    Insets rootStableInsets = getRootStableInsets();
                    Insets stableInsets2 = getStableInsets();
                    return Insets.m28of(Math.max(rootStableInsets.left, stableInsets2.left), 0, Math.max(rootStableInsets.right, stableInsets2.right), Math.max(rootStableInsets.bottom, stableInsets2.bottom));
                }
                Insets systemWindowInsets = getSystemWindowInsets();
                WindowInsetsCompat windowInsetsCompat = this.mRootWindowInsets;
                stableInsets = windowInsetsCompat != null ? windowInsetsCompat.mImpl.getStableInsets() : null;
                int i3 = systemWindowInsets.bottom;
                if (stableInsets != null) {
                    i3 = Math.min(i3, stableInsets.bottom);
                }
                return Insets.m28of(systemWindowInsets.left, 0, systemWindowInsets.right, i3);
            }
            if (i == 8) {
                Insets[] insetsArr = this.mOverriddenInsets;
                stableInsets = insetsArr != null ? insetsArr[3] : null;
                if (stableInsets != null) {
                    return stableInsets;
                }
                Insets systemWindowInsets2 = getSystemWindowInsets();
                Insets rootStableInsets2 = getRootStableInsets();
                int i4 = systemWindowInsets2.bottom;
                if (i4 > rootStableInsets2.bottom) {
                    return Insets.m28of(0, 0, 0, i4);
                }
                Insets insets = this.mRootViewVisibleInsets;
                return (insets == null || insets.equals(Insets.NONE) || (i2 = this.mRootViewVisibleInsets.bottom) <= rootStableInsets2.bottom) ? Insets.NONE : Insets.m28of(0, 0, 0, i2);
            }
            if (i == 16) {
                return getSystemGestureInsets();
            }
            if (i == 32) {
                return getMandatorySystemGestureInsets();
            }
            if (i == 64) {
                return getTappableElementInsets();
            }
            if (i != 128) {
                return Insets.NONE;
            }
            WindowInsetsCompat windowInsetsCompat2 = this.mRootWindowInsets;
            DisplayCutoutCompat displayCutout = windowInsetsCompat2 != null ? windowInsetsCompat2.mImpl.getDisplayCutout() : getDisplayCutout();
            if (displayCutout == null) {
                return Insets.NONE;
            }
            DisplayCutout displayCutout2 = displayCutout.mDisplayCutout;
            return Insets.m28of(displayCutout2.getSafeInsetLeft(), displayCutout2.getSafeInsetTop(), displayCutout2.getSafeInsetRight(), displayCutout2.getSafeInsetBottom());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public final Insets getSystemWindowInsets() {
            if (this.mSystemWindowInsets == null) {
                WindowInsets windowInsets = this.mPlatformInsets;
                this.mSystemWindowInsets = Insets.m28of(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
            }
            return this.mSystemWindowInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            Builder builder = new Builder(WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatformInsets));
            Insets insetInsets = WindowInsetsCompat.insetInsets(getSystemWindowInsets(), i, i2, i3, i4);
            BuilderImpl30 builderImpl30 = builder.mImpl;
            builderImpl30.setSystemWindowInsets(insetInsets);
            builderImpl30.setStableInsets(WindowInsetsCompat.insetInsets(getStableInsets(), i, i2, i3, i4));
            return builder.build();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean isRound() {
            return this.mPlatformInsets.isRound();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setOverriddenInsets(Insets[] insetsArr) {
            this.mOverriddenInsets = insetsArr;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setRootViewData(Insets insets) {
            this.mRootViewVisibleInsets = insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setRootWindowInsets(WindowInsetsCompat windowInsetsCompat) {
            this.mRootWindowInsets = windowInsetsCompat;
        }

        private Insets getInsets(int i, boolean z) {
            Insets insets = Insets.NONE;
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    Insets insetsForType = getInsetsForType(i2, z);
                    insets = Insets.m28of(Math.max(insets.left, insetsForType.left), Math.max(insets.top, insetsForType.top), Math.max(insets.right, insetsForType.right), Math.max(insets.bottom, insetsForType.bottom));
                }
            }
            return insets;
        }

        public Impl20(WindowInsetsCompat windowInsetsCompat, Impl20 impl20) {
            this(windowInsetsCompat, new WindowInsets(impl20.mPlatformInsets));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl28 extends Impl21 {
        public Impl28(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public WindowInsetsCompat consumeDisplayCutout() {
            return WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatformInsets.consumeDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl28)) {
                return false;
            }
            Impl28 impl28 = (Impl28) obj;
            return Objects.equals(this.mPlatformInsets, impl28.mPlatformInsets) && Objects.equals(this.mRootViewVisibleInsets, impl28.mRootViewVisibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public DisplayCutoutCompat getDisplayCutout() {
            return DisplayCutoutCompat.wrap(this.mPlatformInsets.getDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public int hashCode() {
            return this.mPlatformInsets.hashCode();
        }

        public Impl28(WindowInsetsCompat windowInsetsCompat, Impl28 impl28) {
            super(windowInsetsCompat, impl28);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Impl30 extends Impl29 {
        public static final WindowInsetsCompat CONSUMED = WindowInsetsCompat.toWindowInsetsCompat(null, WindowInsets.CONSUMED);

        public Impl30(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public Insets getInsets(int i) {
            return Insets.toCompatInsets(this.mPlatformInsets.getInsets(TypeImpl30.toPlatformType(i)));
        }

        public Impl30(WindowInsetsCompat windowInsetsCompat, Impl30 impl30) {
            super(windowInsetsCompat, impl30);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public final void copyRootViewBounds(View view) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TypeImpl30 {
        private TypeImpl30() {
        }

        public static int toPlatformType(int i) {
            int statusBars;
            int i2 = 0;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i & i3) != 0) {
                    if (i3 == 1) {
                        statusBars = WindowInsets.Type.statusBars();
                    } else if (i3 == 2) {
                        statusBars = WindowInsets.Type.navigationBars();
                    } else if (i3 == 4) {
                        statusBars = WindowInsets.Type.captionBar();
                    } else if (i3 == 8) {
                        statusBars = WindowInsets.Type.ime();
                    } else if (i3 == 16) {
                        statusBars = WindowInsets.Type.systemGestures();
                    } else if (i3 == 32) {
                        statusBars = WindowInsets.Type.mandatorySystemGestures();
                    } else if (i3 == 64) {
                        statusBars = WindowInsets.Type.tappableElement();
                    } else if (i3 == 128) {
                        statusBars = WindowInsets.Type.displayCutout();
                    }
                    i2 |= statusBars;
                }
            }
            return i2;
        }
    }

    private WindowInsetsCompat(WindowInsets windowInsets) {
        this.mImpl = new Impl30(this, windowInsets);
    }

    public static Insets insetInsets(Insets insets, int i, int i2, int i3, int i4) {
        int max = Math.max(0, insets.left - i);
        int max2 = Math.max(0, insets.top - i2);
        int max3 = Math.max(0, insets.right - i3);
        int max4 = Math.max(0, insets.bottom - i4);
        return (max == i && max2 == i2 && max3 == i3 && max4 == i4) ? insets : Insets.m28of(max, max2, max3, max4);
    }

    public static WindowInsetsCompat toWindowInsetsCompat(View view, WindowInsets windowInsets) {
        windowInsets.getClass();
        WindowInsetsCompat windowInsetsCompat = new WindowInsetsCompat(windowInsets);
        if (view != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isAttachedToWindow(view)) {
                WindowInsetsCompat rootWindowInsets = ViewCompat.Api23Impl.getRootWindowInsets(view);
                Impl impl = windowInsetsCompat.mImpl;
                impl.setRootWindowInsets(rootWindowInsets);
                impl.copyRootViewBounds(view.getRootView());
            }
        }
        return windowInsetsCompat;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WindowInsetsCompat)) {
            return false;
        }
        return Objects.equals(this.mImpl, ((WindowInsetsCompat) obj).mImpl);
    }

    public final Insets getInsets(int i) {
        return this.mImpl.getInsets(i);
    }

    public final int getSystemWindowInsetBottom() {
        return this.mImpl.getSystemWindowInsets().bottom;
    }

    public final int getSystemWindowInsetLeft() {
        return this.mImpl.getSystemWindowInsets().left;
    }

    public final int getSystemWindowInsetRight() {
        return this.mImpl.getSystemWindowInsets().right;
    }

    public final int getSystemWindowInsetTop() {
        return this.mImpl.getSystemWindowInsets().top;
    }

    public final int hashCode() {
        Impl impl = this.mImpl;
        if (impl == null) {
            return 0;
        }
        return impl.hashCode();
    }

    public final WindowInsets toWindowInsets() {
        Impl impl = this.mImpl;
        if (impl instanceof Impl20) {
            return ((Impl20) impl).mPlatformInsets;
        }
        return null;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final BuilderImpl30 mImpl;

        public Builder() {
            this.mImpl = new BuilderImpl30();
        }

        public final WindowInsetsCompat build() {
            return this.mImpl.build();
        }

        public Builder(WindowInsetsCompat windowInsetsCompat) {
            this.mImpl = new BuilderImpl30(windowInsetsCompat);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BuilderImpl29 extends BuilderImpl {
        public final WindowInsets.Builder mPlatBuilder;

        public BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        public WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatBuilder.build());
            windowInsetsCompat.mImpl.setOverriddenInsets(null);
            return windowInsetsCompat;
        }

        public void setStableInsets(Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        public void setSystemWindowInsets(Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        public BuilderImpl29(WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
            WindowInsets.Builder builder;
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            if (windowInsets != null) {
                builder = new WindowInsets.Builder(windowInsets);
            } else {
                builder = new WindowInsets.Builder();
            }
            this.mPlatBuilder = builder;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl21 extends Impl20 {
        public Insets mStableInsets;

        public Impl21(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
            this.mStableInsets = null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public WindowInsetsCompat consumeStableInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatformInsets.consumeStableInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public WindowInsetsCompat consumeSystemWindowInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatformInsets.consumeSystemWindowInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public final Insets getStableInsets() {
            if (this.mStableInsets == null) {
                WindowInsets windowInsets = this.mPlatformInsets;
                this.mStableInsets = Insets.m28of(windowInsets.getStableInsetLeft(), windowInsets.getStableInsetTop(), windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom());
            }
            return this.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean isConsumed() {
            return this.mPlatformInsets.isConsumed();
        }

        public Impl21(WindowInsetsCompat windowInsetsCompat, Impl21 impl21) {
            super(windowInsetsCompat, impl21);
            this.mStableInsets = null;
            this.mStableInsets = impl21.mStableInsets;
        }
    }

    public WindowInsetsCompat(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat != null) {
            Impl impl = windowInsetsCompat.mImpl;
            if (impl instanceof Impl30) {
                this.mImpl = new Impl30(this, (Impl30) impl);
            } else if (impl instanceof Impl29) {
                this.mImpl = new Impl29(this, (Impl29) impl);
            } else if (impl instanceof Impl28) {
                this.mImpl = new Impl28(this, (Impl28) impl);
            } else if (impl instanceof Impl21) {
                this.mImpl = new Impl21(this, (Impl21) impl);
            } else if (impl instanceof Impl20) {
                this.mImpl = new Impl20(this, (Impl20) impl);
            } else {
                this.mImpl = new Impl(this);
            }
            impl.copyWindowDataInto(this);
            return;
        }
        this.mImpl = new Impl(this);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl29 extends Impl28 {
        public Insets mMandatorySystemGestureInsets;
        public Insets mSystemGestureInsets;
        public Insets mTappableElementInsets;

        public Impl29(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getMandatorySystemGestureInsets() {
            if (this.mMandatorySystemGestureInsets == null) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
            }
            return this.mMandatorySystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getSystemGestureInsets() {
            if (this.mSystemGestureInsets == null) {
                this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
            }
            return this.mSystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public Insets getTappableElementInsets() {
            if (this.mTappableElementInsets == null) {
                this.mTappableElementInsets = Insets.toCompatInsets(this.mPlatformInsets.getTappableElementInsets());
            }
            return this.mTappableElementInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            return WindowInsetsCompat.toWindowInsetsCompat(null, this.mPlatformInsets.inset(i, i2, i3, i4));
        }

        public Impl29(WindowInsetsCompat windowInsetsCompat, Impl29 impl29) {
            super(windowInsetsCompat, impl29);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl {
        public static final WindowInsetsCompat CONSUMED = new Builder().build().mImpl.consumeDisplayCutout().mImpl.consumeStableInsets().mImpl.consumeSystemWindowInsets();
        public final WindowInsetsCompat mHost;

        public Impl(WindowInsetsCompat windowInsetsCompat) {
            this.mHost = windowInsetsCompat;
        }

        public WindowInsetsCompat consumeDisplayCutout() {
            return this.mHost;
        }

        public WindowInsetsCompat consumeStableInsets() {
            return this.mHost;
        }

        public WindowInsetsCompat consumeSystemWindowInsets() {
            return this.mHost;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl)) {
                return false;
            }
            Impl impl = (Impl) obj;
            return isRound() == impl.isRound() && isConsumed() == impl.isConsumed() && Objects.equals(getSystemWindowInsets(), impl.getSystemWindowInsets()) && Objects.equals(getStableInsets(), impl.getStableInsets()) && Objects.equals(getDisplayCutout(), impl.getDisplayCutout());
        }

        public DisplayCutoutCompat getDisplayCutout() {
            return null;
        }

        public Insets getInsets(int i) {
            return Insets.NONE;
        }

        public Insets getMandatorySystemGestureInsets() {
            return getSystemWindowInsets();
        }

        public Insets getStableInsets() {
            return Insets.NONE;
        }

        public Insets getSystemGestureInsets() {
            return getSystemWindowInsets();
        }

        public Insets getSystemWindowInsets() {
            return Insets.NONE;
        }

        public Insets getTappableElementInsets() {
            return getSystemWindowInsets();
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(isRound()), Boolean.valueOf(isConsumed()), getSystemWindowInsets(), getStableInsets(), getDisplayCutout());
        }

        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            return CONSUMED;
        }

        public boolean isConsumed() {
            return false;
        }

        public boolean isRound() {
            return false;
        }

        public void copyRootViewBounds(View view) {
        }

        public void copyWindowDataInto(WindowInsetsCompat windowInsetsCompat) {
        }

        public void setOverriddenInsets(Insets[] insetsArr) {
        }

        public void setRootViewData(Insets insets) {
        }

        public void setRootWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        }
    }
}
