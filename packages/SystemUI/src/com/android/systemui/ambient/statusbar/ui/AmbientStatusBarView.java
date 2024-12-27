package com.android.systemui.ambient.statusbar.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.shared.shadow.DoubleShadowIconDrawable;
import com.android.systemui.shared.shadow.DoubleShadowTextHelper;
import com.android.systemui.statusbar.AlphaOptimizedImageView;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class AmbientStatusBarView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DoubleShadowTextHelper.ShadowInfo mAmbientShadowInfo;
    public final Context mContext;
    public int mDrawableInsetSize;
    public int mDrawableSize;
    public ViewGroup mExtraSystemStatusViewGroup;
    public DoubleShadowTextHelper.ShadowInfo mKeyShadowInfo;
    public final Map mStatusIcons;
    public ViewGroup mSystemStatusViewGroup;

    public AmbientStatusBarView(Context context) {
        this(context, null);
    }

    public final void addDoubleShadow(View view) {
        if (view instanceof AlphaOptimizedImageView) {
            AlphaOptimizedImageView alphaOptimizedImageView = (AlphaOptimizedImageView) view;
            alphaOptimizedImageView.setImageDrawable(new DoubleShadowIconDrawable(this.mKeyShadowInfo, this.mAmbientShadowInfo, alphaOptimizedImageView.getDrawable(), this.mDrawableSize, this.mDrawableInsetSize));
        }
    }

    public final View fetchStatusIconForResId(int i) {
        View findViewById = findViewById(i);
        Objects.requireNonNull(findViewById);
        return findViewById;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mKeyShadowInfo = new DoubleShadowTextHelper.ShadowInfo(this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_key_text_shadow_radius), this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_key_text_shadow_dx), this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_key_text_shadow_dy), 0.35f);
        this.mAmbientShadowInfo = new DoubleShadowTextHelper.ShadowInfo(this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_ambient_text_shadow_radius), this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_ambient_text_shadow_dx), this.mContext.getResources().getDimension(R.dimen.dream_overlay_status_bar_ambient_text_shadow_dy), 0.4f);
        this.mDrawableSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.dream_overlay_status_bar_icon_size);
        this.mDrawableInsetSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.dream_overlay_icon_inset_dimen);
        Map map = this.mStatusIcons;
        View fetchStatusIconForResId = fetchStatusIconForResId(R.id.dream_overlay_wifi_status);
        addDoubleShadow(fetchStatusIconForResId);
        ((HashMap) map).put(1, fetchStatusIconForResId);
        Map map2 = this.mStatusIcons;
        View fetchStatusIconForResId2 = fetchStatusIconForResId(R.id.dream_overlay_alarm_set);
        addDoubleShadow(fetchStatusIconForResId2);
        ((HashMap) map2).put(2, fetchStatusIconForResId2);
        ((HashMap) this.mStatusIcons).put(3, fetchStatusIconForResId(R.id.dream_overlay_camera_off));
        ((HashMap) this.mStatusIcons).put(4, fetchStatusIconForResId(R.id.dream_overlay_mic_off));
        ((HashMap) this.mStatusIcons).put(5, fetchStatusIconForResId(R.id.dream_overlay_camera_mic_off));
        ((HashMap) this.mStatusIcons).put(0, fetchStatusIconForResId(R.id.dream_overlay_notification_indicator));
        Map map3 = this.mStatusIcons;
        View fetchStatusIconForResId3 = fetchStatusIconForResId(R.id.dream_overlay_priority_mode);
        addDoubleShadow(fetchStatusIconForResId3);
        ((HashMap) map3).put(6, fetchStatusIconForResId3);
        ((HashMap) this.mStatusIcons).put(7, fetchStatusIconForResId(R.id.dream_overlay_assistant_attention_indicator));
        this.mSystemStatusViewGroup = (ViewGroup) findViewById(R.id.dream_overlay_system_status);
        this.mExtraSystemStatusViewGroup = (ViewGroup) findViewById(R.id.dream_overlay_extra_items);
    }

    public AmbientStatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AmbientStatusBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        this.mContext = context;
    }

    public AmbientStatusBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mStatusIcons = new HashMap();
    }
}
