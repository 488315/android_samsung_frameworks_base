package com.android.systemui.media;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.graphics.ColorUtils;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.function.Supplier;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaPlayerBarExpandHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArgbEvaluator argbEvaluator;
    public final Supplier barCallbackSupplier;
    public final Context context;
    public ValueAnimator expandAnimator;
    public final MediaPlayerBarExpandHelper$expandCallback$1 expandCallback;
    public final Supplier frameSupplier;
    public final int indicatorExpandSelectedColor;
    public final int indicatorExpandUnselectedColor;
    public int initialBarHeight;
    public boolean isGestureExpand;
    public final SecMediaPlayerData mediaPlayerData;
    public final SecPageIndicator pageIndicator;
    public int playerBarState;
    public int playerExpansionHeight;
    public final Rect rect;
    public final SecQSPanelResourcePicker resourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
    public boolean tracking;
    public boolean userTouch;
    public float velocity;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaPlayerBarExpandHelper(Context context, SecMediaPlayerData secMediaPlayerData, Supplier<BarController.C20874> supplier, Supplier<View> supplier2) {
        this.context = context;
        this.mediaPlayerData = secMediaPlayerData;
        this.barCallbackSupplier = supplier;
        this.frameSupplier = supplier2;
        SecPageIndicator secPageIndicator = (SecPageIndicator) supplier2.get().findViewById(R.id.sec_media_page_indicator);
        secPageIndicator.setSecIndicatorColorResId();
        this.pageIndicator = secPageIndicator;
        this.rect = new Rect();
        this.playerExpansionHeight = getCollapsedHeight();
        int color = context.getColor(R.color.media_expand_text);
        this.indicatorExpandSelectedColor = color;
        this.indicatorExpandUnselectedColor = ColorUtils.setAlphaComponent(color, 180);
        this.argbEvaluator = new ArgbEvaluator();
        this.expandCallback = new MediaPlayerBarExpandHelper$expandCallback$1(this);
        supplier2.get().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.MediaPlayerBarExpandHelper.1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                    return;
                }
                MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = MediaPlayerBarExpandHelper.this;
                mediaPlayerBarExpandHelper.rect.set(0, 0, ((View) mediaPlayerBarExpandHelper.frameSupplier.get()).getWidth(), MediaPlayerBarExpandHelper.this.playerExpansionHeight);
                ((QSMediaCornerRoundedView) ((View) MediaPlayerBarExpandHelper.this.frameSupplier.get()).findViewById(R.id.media_carousel_layout)).setClipBounds(MediaPlayerBarExpandHelper.this.rect);
                ArrayList<SecMediaControlPanel> sortedMediaPlayers = MediaPlayerBarExpandHelper.this.mediaPlayerData.getSortedMediaPlayers();
                MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper2 = MediaPlayerBarExpandHelper.this;
                for (SecMediaControlPanel secMediaControlPanel : sortedMediaPlayers) {
                    int measuredWidth = ((View) mediaPlayerBarExpandHelper2.frameSupplier.get()).getMeasuredWidth();
                    int i9 = mediaPlayerBarExpandHelper2.playerExpansionHeight;
                    View view2 = secMediaControlPanel.mViewHolder.playerView;
                    if (view2 == null) {
                        view2 = null;
                    }
                    Rect rect = secMediaControlPanel.mPlayerRect;
                    rect.set(0, 0, measuredWidth, i9);
                    view2.setClipBounds(rect);
                    secMediaControlPanel.mHeight = i9;
                    secMediaControlPanel.setFraction(secMediaControlPanel.getPlayerExpandedFraction());
                }
            }
        });
    }

    public final int getCollapsedHeight() {
        this.resourcePicker.getClass();
        return this.context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_height_collapsed);
    }

    public final int getExpandedHeight() {
        this.resourcePicker.getClass();
        return this.context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_height_expanded);
    }

    public final void setHeight(int i) {
        ValueAnimator valueAnimator;
        if (!this.tracking && (valueAnimator = this.expandAnimator) != null) {
            if (valueAnimator == null) {
                valueAnimator = null;
            }
            valueAnimator.cancel();
        }
        float abs = Math.abs(getExpandedHeight() - getCollapsedHeight());
        float abs2 = Math.abs(i - this.playerExpansionHeight);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.playerExpansionHeight, i);
        ofFloat.setDuration((int) ((abs2 / abs) * 300));
        ofFloat.setInterpolator(new PathInterpolator(0.43f, 0.43f, 0.17f, 1.0f));
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.MediaPlayerBarExpandHelper$setHeight$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = MediaPlayerBarExpandHelper.this;
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                int i2 = MediaPlayerBarExpandHelper.$r8$clinit;
                mediaPlayerBarExpandHelper.setPlayerBarExpansion(floatValue, true);
            }
        });
        ofFloat.start();
        this.expandAnimator = ofFloat;
    }

    public final void setPlayerBarExpansion() {
        boolean z = QpRune.QUICK_TABLET;
        Context context = this.context;
        boolean z2 = false;
        if (!z && context.getResources().getConfiguration().orientation == 2) {
            z2 = true;
        }
        Pair pair = z2 ? new Pair(Integer.valueOf(getCollapsedHeight()), Boolean.FALSE) : Prefs.getBoolean(context, "QsMediaPlayerLastExpanded", true) ? new Pair(Integer.valueOf(getExpandedHeight()), Boolean.TRUE) : new Pair(Integer.valueOf(getCollapsedHeight()), Boolean.TRUE);
        int intValue = ((Number) pair.component1()).intValue();
        boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Get Value for expanded: ", Prefs.getBoolean(context, "QsMediaPlayerLastExpanded", true), "SecMediaControlPanel");
        setPlayerBarExpansion(intValue, booleanValue);
    }

    public final boolean setTracking(float f, boolean z) {
        boolean z2 = false;
        if (this.tracking == z) {
            return false;
        }
        if (z) {
            this.initialBarHeight = this.playerExpansionHeight;
        } else {
            boolean shouldPlayerExpand = shouldPlayerExpand();
            int expandedHeight = shouldPlayerExpand ? getExpandedHeight() : getCollapsedHeight();
            boolean z3 = (this.playerBarState == 1) != shouldPlayerExpand;
            setHeight(expandedHeight);
            if (this.tracking && this.isGestureExpand) {
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0021", "type", "swipe");
            }
            this.isGestureExpand = false;
            z2 = z3;
        }
        this.velocity = f;
        this.tracking = z;
        return z2;
    }

    public final boolean shouldPlayerExpand() {
        if (!this.tracking) {
            if (!(this.playerBarState == 1)) {
                return true;
            }
        } else if (Math.abs(this.velocity) > 250.0f) {
            if (this.velocity > 0.0f) {
                return true;
            }
        } else if (this.playerExpansionHeight - getCollapsedHeight() > (getExpandedHeight() - getCollapsedHeight()) / 2.0f) {
            return true;
        }
        return false;
    }

    public final void setPlayerBarExpansion(float f, boolean z) {
        int i = (int) f;
        this.playerExpansionHeight = i;
        int i2 = f > ((float) getCollapsedHeight()) ? 1 : 0;
        if (this.playerBarState != i2) {
            this.playerBarState = i2;
            if (z) {
                boolean z2 = i2 == 1;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Set Value for expanded: ", z2, "SecMediaControlPanel");
                Prefs.putBoolean(this.context, "QsMediaPlayerLastExpanded", z2);
            }
        }
        Supplier supplier = this.frameSupplier;
        int width = ((View) supplier.get()).getWidth();
        Rect rect = this.rect;
        rect.set(0, 0, width, i);
        ((QSMediaCornerRoundedView) ((View) supplier.get()).findViewById(R.id.media_carousel_layout)).setClipBounds(rect);
        SecMediaPlayerData secMediaPlayerData = this.mediaPlayerData;
        if (secMediaPlayerData.getSortedMediaPlayersSize() <= 0) {
            Log.d("SecMediaControlPanel", "there is no media player, update player height returned.");
            return;
        }
        for (SecMediaControlPanel secMediaControlPanel : secMediaPlayerData.getSortedMediaPlayers()) {
            boolean z3 = this.playerBarState != 0;
            if (secMediaControlPanel.mExpanded != z3) {
                secMediaControlPanel.mExpanded = z3;
                if (secMediaControlPanel.mViewHolder.expandIcon != null) {
                    Context context = secMediaControlPanel.mContext;
                    secMediaControlPanel.mViewHolder.expandIcon.setContentDescription(z3 ? context.getString(R.string.sec_qs_media_player_collapse_content_description) : context.getString(R.string.sec_qs_media_player_expand_content_description));
                }
            }
            int width2 = ((View) supplier.get()).getWidth();
            int i3 = this.playerExpansionHeight;
            View view = secMediaControlPanel.mViewHolder.playerView;
            if (view == null) {
                view = null;
            }
            Rect rect2 = secMediaControlPanel.mPlayerRect;
            rect2.set(0, 0, width2, i3);
            view.setClipBounds(rect2);
            secMediaControlPanel.mHeight = i3;
            secMediaControlPanel.setFraction(secMediaControlPanel.getPlayerExpandedFraction());
        }
        SecPageIndicator secPageIndicator = this.pageIndicator;
        if (secPageIndicator != null) {
            int i4 = this.playerExpansionHeight;
            int collapsedHeight = getCollapsedHeight();
            if (i4 < collapsedHeight) {
                i4 = collapsedHeight;
            }
            secPageIndicator.setTranslationY(i4 - secPageIndicator.getHeight());
            float collapsedHeight2 = (this.playerExpansionHeight - getCollapsedHeight()) / (getExpandedHeight() - getCollapsedHeight());
            Integer valueOf = Integer.valueOf(secPageIndicator.getContext().getColor(R.color.qs_media_page_indicator_tint_color_selected));
            Integer valueOf2 = Integer.valueOf(this.indicatorExpandSelectedColor);
            ArgbEvaluator argbEvaluator = this.argbEvaluator;
            int intValue = ((Integer) argbEvaluator.evaluate(collapsedHeight2, valueOf, valueOf2)).intValue();
            int intValue2 = ((Integer) argbEvaluator.evaluate(collapsedHeight2, Integer.valueOf(secPageIndicator.getContext().getColor(R.color.qs_media_page_indicator_tint_color_unselected)), Integer.valueOf(this.indicatorExpandUnselectedColor))).intValue();
            secPageIndicator.mSelectedColor = intValue;
            secPageIndicator.mUnselectedColor = intValue2;
            for (int i5 = 0; i5 < secPageIndicator.getChildCount(); i5++) {
                ImageView imageView = (ImageView) ((LinearLayout) secPageIndicator.getChildAt(i5)).getChildAt(0);
                if (imageView != null) {
                    TransitionDrawable transitionDrawable = (TransitionDrawable) imageView.getBackground();
                    transitionDrawable.getDrawable(0).setColorFilter(secPageIndicator.mUnselectedColor, PorterDuff.Mode.SRC_IN);
                    transitionDrawable.getDrawable(1).setColorFilter(secPageIndicator.mSelectedColor, PorterDuff.Mode.SRC_IN);
                }
            }
        }
        ((BarController.C20874) this.barCallbackSupplier.get()).onBarHeightChanged();
    }
}
