package com.android.systemui.power;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.doze.PluginAODManager;
import com.samsung.android.hardware.display.IRefreshRateToken;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ChargerAnimationView extends RelativeLayout {
    public AnimatorSet mAlphaAnimatorSet;
    public ChargerAnimationListener mAnimationListener;
    public boolean mAnimationPlaying;
    public LinearLayout mBackGroundView;
    public TextView mBatteryLevelTextView;
    public LinearLayout mBatteryTextContainer;
    public LottieAnimationView mChargerAnimationView;
    public RelativeLayout mChargerContainer;
    public ImageView mChargingIconView;
    public ImageView mCircleBackgroundView;
    public final Context mContext;
    public int mCurrentBatteryLevel;
    public IDisplayManager mDisplayManager;
    public final IBinder mDisplayStateLock;
    public PowerManager.WakeLock mDozeChargingPartialWakelock;
    public ObjectAnimator mFadeInAnimation;
    public boolean mIsSubscreenOff;
    public boolean mNeedFullScreenBlur;
    public TextView mPercentTextView;
    public TextView mPercentTextViewRtl;
    public PluginAODManager mPluginAODManager;
    public final PowerManager mPowerManager;
    public IRefreshRateToken mRefreshRateToken;
    public boolean mStartedInDoze;
    public int mSuperFastChargingType;
    public final IBinder mToken;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ChargerAnimationListener {
    }

    /* renamed from: -$$Nest$mreleaseDisplayStateLimit, reason: not valid java name */
    public static void m1596$$Nest$mreleaseDisplayStateLimit(ChargerAnimationView chargerAnimationView) {
        if (chargerAnimationView.mDisplayManager == null) {
            chargerAnimationView.mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
        }
        try {
            Log.i("PowerUI.ChargerAnimationView", "setDisplayStateLimit(Display.STATE_UNKNOWN)");
            IDisplayManager iDisplayManager = chargerAnimationView.mDisplayManager;
            if (iDisplayManager == null) {
                Log.e("PowerUI.ChargerAnimationView", "mDisplayManager is null. Error case");
                return;
            }
            iDisplayManager.setDisplayStateLimit(chargerAnimationView.mDisplayStateLock, 0);
            IRefreshRateToken iRefreshRateToken = chargerAnimationView.mRefreshRateToken;
            if (iRefreshRateToken != null) {
                iRefreshRateToken.release();
                chargerAnimationView.mRefreshRateToken = null;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ChargerAnimationView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBackGroundView = (LinearLayout) findViewById(R.id.backgroundArea);
        this.mBatteryTextContainer = (LinearLayout) findViewById(R.id.charging_content_container);
        this.mChargerAnimationView = (LottieAnimationView) findViewById(R.id.charger_animation_view);
        this.mChargerContainer = (RelativeLayout) findViewById(R.id.charger_animation_container);
        this.mBatteryLevelTextView = (TextView) findViewById(R.id.battery_level);
        this.mPercentTextView = (TextView) findViewById(R.id.battery_level_unit);
        this.mPercentTextViewRtl = (TextView) findViewById(R.id.battery_level_unit_rtl);
        this.mChargingIconView = (ImageView) findViewById(R.id.charging_icon);
        this.mCircleBackgroundView = (ImageView) findViewById(R.id.charger_animation_background_blur_view);
        this.mAlphaAnimatorSet = new AnimatorSet();
        setBatteryLevelText();
    }

    public final void setBatteryLevelText() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.charger_anim_text_margin_start);
        FontSizeUtils.updateFontSize(R.dimen.charger_anim_battery_text_size, this.mBatteryLevelTextView);
        this.mBatteryLevelTextView.setText(this.mContext.getString(R.string.status_bar_settings_battery_meter_format, Integer.valueOf(this.mCurrentBatteryLevel)).replace("%", "").replace(" ", ""));
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBatteryTextContainer.getLayoutParams();
        String language = Locale.getDefault().getLanguage();
        if ("iw".equals(language) || "ur".equals(language) || "tr".equals(language) || "eu".equals(language)) {
            marginLayoutParams.setMarginEnd(dimensionPixelSize);
            this.mPercentTextViewRtl.setText("%");
            this.mPercentTextViewRtl.setVisibility(0);
            this.mPercentTextView.setVisibility(8);
        } else {
            marginLayoutParams.setMarginStart(dimensionPixelSize);
            this.mPercentTextView.setText("%");
            this.mPercentTextView.setVisibility(0);
            this.mPercentTextViewRtl.setVisibility(8);
        }
        this.mBatteryTextContainer.setLayoutParams(marginLayoutParams);
    }

    public ChargerAnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChargerAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNeedFullScreenBlur = false;
        this.mStartedInDoze = false;
        this.mAnimationPlaying = false;
        this.mCurrentBatteryLevel = 0;
        this.mDisplayStateLock = new Binder();
        this.mIsSubscreenOff = false;
        this.mToken = new Binder();
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
    }
}
