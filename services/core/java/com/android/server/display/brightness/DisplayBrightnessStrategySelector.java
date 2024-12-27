package com.android.server.display.brightness;

import android.R;
import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy;
import com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy;
import com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy2;
import com.android.server.display.brightness.strategy.BoostBrightnessStrategy;
import com.android.server.display.brightness.strategy.DisplayBrightnessStrategy;
import com.android.server.display.brightness.strategy.DozeBrightnessStrategy;
import com.android.server.display.brightness.strategy.FallbackBrightnessStrategy;
import com.android.server.display.brightness.strategy.FollowerBrightnessStrategy;
import com.android.server.display.brightness.strategy.InvalidBrightnessStrategy;
import com.android.server.display.brightness.strategy.OffloadBrightnessStrategy;
import com.android.server.display.brightness.strategy.OverrideBrightnessStrategy;
import com.android.server.display.brightness.strategy.ScreenOffBrightnessStrategy;
import com.android.server.display.brightness.strategy.TemporaryBrightnessStrategy;
import com.android.server.display.feature.DisplayManagerFlags;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayBrightnessStrategySelector {
    public boolean mAllowAutoBrightnessWhileDozing;
    public final boolean mAllowAutoBrightnessWhileDozingConfig;
    public final AutoBrightnessFallbackStrategy mAutoBrightnessFallbackStrategy;
    public final AutomaticBrightnessStrategy2 mAutomaticBrightnessStrategy;
    public final AutomaticBrightnessStrategy mAutomaticBrightnessStrategy1;
    public final BoostBrightnessStrategy mBoostBrightnessStrategy;
    final DisplayBrightnessStrategy[] mDisplayBrightnessStrategies;
    public final int mDisplayId;
    public final DisplayManagerFlags mDisplayManagerFlags;
    public final DozeBrightnessStrategy mDozeBrightnessStrategy;
    public final FallbackBrightnessStrategy mFallbackBrightnessStrategy;
    public final FollowerBrightnessStrategy mFollowerBrightnessStrategy;
    public final InvalidBrightnessStrategy mInvalidBrightnessStrategy;
    public final OffloadBrightnessStrategy mOffloadBrightnessStrategy;
    public String mOldBrightnessStrategyName;
    public final OverrideBrightnessStrategy mOverrideBrightnessStrategy;
    public final ScreenOffBrightnessStrategy mScreenOffBrightnessStrategy;
    public final TemporaryBrightnessStrategy mTemporaryBrightnessStrategy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
    }

    public DisplayBrightnessStrategySelector(Context context, int i, DisplayManagerFlags displayManagerFlags) {
        new Injector();
        this.mDisplayManagerFlags = displayManagerFlags;
        this.mDisplayId = i;
        DozeBrightnessStrategy dozeBrightnessStrategy = new DozeBrightnessStrategy();
        this.mDozeBrightnessStrategy = dozeBrightnessStrategy;
        ScreenOffBrightnessStrategy screenOffBrightnessStrategy = new ScreenOffBrightnessStrategy();
        this.mScreenOffBrightnessStrategy = screenOffBrightnessStrategy;
        OverrideBrightnessStrategy overrideBrightnessStrategy = new OverrideBrightnessStrategy();
        this.mOverrideBrightnessStrategy = overrideBrightnessStrategy;
        TemporaryBrightnessStrategy temporaryBrightnessStrategy = new TemporaryBrightnessStrategy();
        temporaryBrightnessStrategy.mTemporaryScreenBrightness = Float.NaN;
        this.mTemporaryBrightnessStrategy = temporaryBrightnessStrategy;
        BoostBrightnessStrategy boostBrightnessStrategy = new BoostBrightnessStrategy();
        this.mBoostBrightnessStrategy = boostBrightnessStrategy;
        FollowerBrightnessStrategy followerBrightnessStrategy = new FollowerBrightnessStrategy(i);
        this.mFollowerBrightnessStrategy = followerBrightnessStrategy;
        InvalidBrightnessStrategy invalidBrightnessStrategy = new InvalidBrightnessStrategy();
        this.mInvalidBrightnessStrategy = invalidBrightnessStrategy;
        AutomaticBrightnessStrategy automaticBrightnessStrategy = !displayManagerFlags.mRefactorDisplayPowerController.isEnabled() ? null : new AutomaticBrightnessStrategy(context, i, null, displayManagerFlags);
        this.mAutomaticBrightnessStrategy1 = automaticBrightnessStrategy;
        this.mAutomaticBrightnessStrategy = displayManagerFlags.mRefactorDisplayPowerController.isEnabled() ? automaticBrightnessStrategy : displayManagerFlags.mRefactorDisplayPowerController.isEnabled() ? null : new AutomaticBrightnessStrategy2(context, i);
        AutoBrightnessFallbackStrategy autoBrightnessFallbackStrategy = displayManagerFlags.mRefactorDisplayPowerController.isEnabled() ? new AutoBrightnessFallbackStrategy() : null;
        this.mAutoBrightnessFallbackStrategy = autoBrightnessFallbackStrategy;
        if (displayManagerFlags.mDisplayOffloadFlagState.isEnabled()) {
            this.mOffloadBrightnessStrategy = new OffloadBrightnessStrategy(displayManagerFlags);
        } else {
            this.mOffloadBrightnessStrategy = null;
        }
        FallbackBrightnessStrategy fallbackBrightnessStrategy = displayManagerFlags.mRefactorDisplayPowerController.isEnabled() ? new FallbackBrightnessStrategy() : null;
        this.mFallbackBrightnessStrategy = fallbackBrightnessStrategy;
        this.mDisplayBrightnessStrategies = new DisplayBrightnessStrategy[]{invalidBrightnessStrategy, screenOffBrightnessStrategy, dozeBrightnessStrategy, followerBrightnessStrategy, boostBrightnessStrategy, overrideBrightnessStrategy, temporaryBrightnessStrategy, automaticBrightnessStrategy, this.mOffloadBrightnessStrategy, autoBrightnessFallbackStrategy, fallbackBrightnessStrategy};
        boolean z = context.getResources().getBoolean(R.bool.config_allowFloatingWindowsFillScreen);
        this.mAllowAutoBrightnessWhileDozingConfig = z;
        this.mAllowAutoBrightnessWhileDozing = z;
        this.mOldBrightnessStrategyName = "InvalidBrightnessStrategy";
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("DisplayBrightnessStrategySelector:");
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, this.mOldBrightnessStrategyName, "  mAllowAutoBrightnessWhileDozingConfig= ", BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mDisplayId= "), this.mDisplayId, printWriter, "  mOldBrightnessStrategyName= ")), this.mAllowAutoBrightnessWhileDozingConfig, printWriter, "  mAllowAutoBrightnessWhileDozing= ");
        m.append(this.mAllowAutoBrightnessWhileDozing);
        printWriter.println(m.toString());
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, " ");
        for (DisplayBrightnessStrategy displayBrightnessStrategy : this.mDisplayBrightnessStrategies) {
            if (displayBrightnessStrategy != null) {
                displayBrightnessStrategy.dump(indentingPrintWriter);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00aa, code lost:
    
        if (r3 == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00bd, code lost:
    
        if (r3 != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00de, code lost:
    
        if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(r3.getAutomaticScreenBrightness()) != false) goto L69;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.display.brightness.strategy.DisplayBrightnessStrategy selectStrategy(com.android.server.display.brightness.StrategySelectionRequest r19) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.brightness.DisplayBrightnessStrategySelector.selectStrategy(com.android.server.display.brightness.StrategySelectionRequest):com.android.server.display.brightness.strategy.DisplayBrightnessStrategy");
    }

    public void setAllowAutoBrightnessWhileDozing(DisplayManagerInternal.DisplayOffloadSession displayOffloadSession) {
        this.mAllowAutoBrightnessWhileDozing = this.mAllowAutoBrightnessWhileDozingConfig;
        DisplayManagerFlags displayManagerFlags = this.mDisplayManagerFlags;
        if (displayManagerFlags.mOffloadControlsDozeAutoBrightness.isEnabled() && displayManagerFlags.mDisplayOffloadFlagState.isEnabled() && displayOffloadSession != null) {
            this.mAllowAutoBrightnessWhileDozing = displayOffloadSession.allowAutoBrightnessInDoze() & this.mAllowAutoBrightnessWhileDozing;
        }
    }
}
