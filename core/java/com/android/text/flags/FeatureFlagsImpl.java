package com.android.text.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.text.flags.FeatureFlags
    public boolean completeFontLoadInSystemServicesReady() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean deprecateUiFonts() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean disableHandwritingInitiatorForIme() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean escapeClearsFocus() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixDoubleUnderline() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixFontUpdateFailure() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixLineHeightForLocale() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixMisalignedContextMenu() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixNullTypefaceBolding() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingCursorPosition() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingEndOfLineTap() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean handwritingUnsupportedMessage() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean icuBidiMigration() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashWhenDelete() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeNotUpdateSelection() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean lazyVariationInstance() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean letterSpacingJustification() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean missingGetterApis() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean newFontsFallbackXml() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean noBreakNoHyphenationSpan() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean phraseStrictFallback() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean rustHyphenator() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useBoundsForWidth() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useOptimizedBoottimeFontLoading() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean vendorCustomLocaleFallback() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean wordStyleAuto() {
        return true;
    }
}
