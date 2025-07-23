package androidx.compose.ui.platform;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidViewConfiguration implements ViewConfiguration {
    public final android.view.ViewConfiguration viewConfiguration;

    public AndroidViewConfiguration(android.view.ViewConfiguration viewConfiguration) {
        this.viewConfiguration = viewConfiguration;
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final long getDoubleTapTimeoutMillis() {
        return android.view.ViewConfiguration.getDoubleTapTimeout();
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final float getHandwritingGestureLineMargin() {
        AndroidViewConfigurationApi34 androidViewConfigurationApi34 = AndroidViewConfigurationApi34.INSTANCE;
        android.view.ViewConfiguration viewConfiguration = this.viewConfiguration;
        androidViewConfigurationApi34.getClass();
        return viewConfiguration.getScaledHandwritingGestureLineMargin();
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final float getHandwritingSlop() {
        AndroidViewConfigurationApi34 androidViewConfigurationApi34 = AndroidViewConfigurationApi34.INSTANCE;
        android.view.ViewConfiguration viewConfiguration = this.viewConfiguration;
        androidViewConfigurationApi34.getClass();
        return viewConfiguration.getScaledHandwritingSlop();
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final long getLongPressTimeoutMillis() {
        return android.view.ViewConfiguration.getLongPressTimeout();
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final float getMaximumFlingVelocity() {
        return this.viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override // androidx.compose.ui.platform.ViewConfiguration
    public final float getTouchSlop() {
        return this.viewConfiguration.getScaledTouchSlop();
    }
}
