package android.view.accessibility;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import com.android.internal.R;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes4.dex */
public class CaptioningManager {
    private static final int DEFAULT_ENABLED = 0;
    private static final float DEFAULT_FONT_SCALE = 1.0f;
    private static final int DEFAULT_PRESET = 0;
    private static final boolean SYSTEM_AUDIO_CAPTIONING_DEFAULT_ENABLED = false;
    private final AccessibilityManager mAccessibilityManager;
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final Resources mResources;
    private final ArrayList<CaptioningChangeListener> mListeners = new ArrayList<>();
    private final Runnable mStyleChangedRunnable = new Runnable() { // from class: android.view.accessibility.CaptioningManager.1
        @Override // java.lang.Runnable
        public void run() {
            CaptioningManager.this.notifyUserStyleChanged();
        }
    };

    public static abstract class CaptioningChangeListener {
        public void onEnabledChanged(boolean z) {
        }

        public void onFontScaleChanged(float f) {
        }

        public void onLocaleChanged(Locale locale) {
        }

        public void onSystemAudioCaptioningChanged(boolean z) {
        }

        public void onSystemAudioCaptioningUiChanged(boolean z) {
        }

        public void onUserStyleChanged(CaptionStyle captionStyle) {
        }
    }

    public interface SystemAudioCaptioningAccessing {
        boolean isSystemAudioCaptioningUiEnabled(int i);

        void setSystemAudioCaptioningEnabled(boolean z, int i);

        void setSystemAudioCaptioningUiEnabled(boolean z, int i);
    }

    public CaptioningManager(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mContentObserver = new MyContentObserver(new Handler(context.getMainLooper()));
        this.mResources = context.getResources();
    }

    public final boolean isEnabled() {
        return Settings.Secure.getInt(this.mContentResolver, "accessibility_captioning_enabled", 0) == 1;
    }

    public final String getRawLocale() {
        return Settings.Secure.getString(this.mContentResolver, "accessibility_captioning_locale");
    }

    public final Locale getLocale() {
        String rawLocale = getRawLocale();
        if (TextUtils.isEmpty(rawLocale)) {
            return null;
        }
        String[] strArrSplit = rawLocale.split(Session.SESSION_SEPARATION_CHAR_CHILD);
        int length = strArrSplit.length;
        if (length == 1) {
            return new Locale(strArrSplit[0]);
        }
        if (length == 2) {
            return new Locale(strArrSplit[0], strArrSplit[1]);
        }
        if (length != 3) {
            return null;
        }
        return new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
    }

    public final float getFontScale() {
        return Settings.Secure.getFloat(this.mContentResolver, "accessibility_captioning_font_scale", 1.0f);
    }

    public int getRawUserStyle() {
        return Settings.Secure.getInt(this.mContentResolver, "accessibility_captioning_preset", 0);
    }

    public CaptionStyle getUserStyle() {
        int rawUserStyle = getRawUserStyle();
        if (rawUserStyle == -1) {
            return CaptionStyle.getCustomStyle(this.mContentResolver);
        }
        return CaptionStyle.PRESETS[rawUserStyle];
    }

    public final boolean isSystemAudioCaptioningEnabled() {
        return Settings.Secure.getIntForUser(this.mContentResolver, Settings.Secure.ODI_CAPTIONS_ENABLED, 0, this.mContext.getUserId()) == 1;
    }

    @SystemApi
    public final void setSystemAudioCaptioningEnabled(boolean z) {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.setSystemAudioCaptioningEnabled(z, this.mContext.getUserId());
        }
    }

    public final boolean isSystemAudioCaptioningUiEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isSystemAudioCaptioningUiEnabled(this.mContext.getUserId());
    }

    @SystemApi
    public final void setSystemAudioCaptioningUiEnabled(boolean z) {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.setSystemAudioCaptioningUiEnabled(z, this.mContext.getUserId());
        }
    }

    public void addCaptioningChangeListener(CaptioningChangeListener captioningChangeListener) {
        synchronized (this.mListeners) {
            if (this.mListeners.isEmpty()) {
                registerObserver("accessibility_captioning_enabled");
                registerObserver("accessibility_captioning_foreground_color");
                registerObserver("accessibility_captioning_background_color");
                registerObserver("accessibility_captioning_window_color");
                registerObserver("accessibility_captioning_edge_type");
                registerObserver("accessibility_captioning_edge_color");
                registerObserver("accessibility_captioning_typeface");
                registerObserver("accessibility_captioning_font_scale");
                registerObserver("accessibility_captioning_locale");
                registerObserver("accessibility_captioning_preset");
                registerObserver(Settings.Secure.ODI_CAPTIONS_ENABLED);
                registerObserver(Settings.Secure.ODI_CAPTIONS_VOLUME_UI_ENABLED);
            }
            this.mListeners.add(captioningChangeListener);
        }
    }

    private void registerObserver(String str) {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(str), false, this.mContentObserver);
    }

    public void removeCaptioningChangeListener(CaptioningChangeListener captioningChangeListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(captioningChangeListener);
            if (this.mListeners.isEmpty()) {
                this.mContentResolver.unregisterContentObserver(this.mContentObserver);
            }
        }
    }

    public boolean isCallCaptioningEnabled() {
        if (SemSystemProperties.getInt("ro.product.first_api_level", 0) >= 34 && this.mContext.getPackageManager().hasSystemFeature("com.google.android.feature.ASI") && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOICE_TX_FOR_INCALL_MUSIC")) {
            return false;
        }
        try {
            return this.mResources.getBoolean(R.bool.config_systemCaptionsServiceCallsEnabled);
        } catch (Resources.NotFoundException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyEnabledChanged() {
        boolean zIsEnabled = isEnabled();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onEnabledChanged(zIsEnabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUserStyleChanged() {
        CaptionStyle userStyle = getUserStyle();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onUserStyleChanged(userStyle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLocaleChanged() {
        Locale locale = getLocale();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onLocaleChanged(locale);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFontScaleChanged() {
        float fontScale = getFontScale();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onFontScaleChanged(fontScale);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemAudioCaptionChanged() {
        boolean zIsSystemAudioCaptioningEnabled = isSystemAudioCaptioningEnabled();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSystemAudioCaptioningChanged(zIsSystemAudioCaptioningEnabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemAudioCaptionUiChanged() {
        boolean zIsSystemAudioCaptioningUiEnabled = isSystemAudioCaptioningUiEnabled();
        synchronized (this.mListeners) {
            Iterator<CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSystemAudioCaptioningUiChanged(zIsSystemAudioCaptioningUiEnabled);
            }
        }
    }

    private class MyContentObserver extends ContentObserver {
        private final Handler mHandler;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            String path = uri.getPath();
            String strSubstring = path.substring(path.lastIndexOf(47) + 1);
            if ("accessibility_captioning_enabled".equals(strSubstring)) {
                CaptioningManager.this.notifyEnabledChanged();
                return;
            }
            if ("accessibility_captioning_locale".equals(strSubstring)) {
                CaptioningManager.this.notifyLocaleChanged();
                return;
            }
            if ("accessibility_captioning_font_scale".equals(strSubstring)) {
                CaptioningManager.this.notifyFontScaleChanged();
                return;
            }
            if (Settings.Secure.ODI_CAPTIONS_ENABLED.equals(strSubstring)) {
                CaptioningManager.this.notifySystemAudioCaptionChanged();
            } else if (Settings.Secure.ODI_CAPTIONS_VOLUME_UI_ENABLED.equals(strSubstring)) {
                CaptioningManager.this.notifySystemAudioCaptionUiChanged();
            } else {
                this.mHandler.removeCallbacks(CaptioningManager.this.mStyleChangedRunnable);
                this.mHandler.post(CaptioningManager.this.mStyleChangedRunnable);
            }
        }
    }

    public static final class CaptionStyle {
        private static final CaptionStyle BLACK_ON_WHITE;
        private static final int COLOR_NONE_OPAQUE = 255;
        public static final int COLOR_UNSPECIFIED = 16777215;
        public static final CaptionStyle DEFAULT;
        private static final CaptionStyle DEFAULT_CUSTOM;
        public static final int EDGE_TYPE_DEPRESSED = 4;
        public static final int EDGE_TYPE_DROP_SHADOW = 2;
        public static final int EDGE_TYPE_NONE = 0;
        public static final int EDGE_TYPE_OUTLINE = 1;
        public static final int EDGE_TYPE_RAISED = 3;
        public static final int EDGE_TYPE_UNSPECIFIED = -1;
        public static final CaptionStyle[] PRESETS;
        public static final int PRESET_CUSTOM = -1;
        private static final CaptionStyle UNSPECIFIED;
        private static final CaptionStyle WHITE_ON_BLACK;
        private static final CaptionStyle YELLOW_ON_BLACK;
        private static final CaptionStyle YELLOW_ON_BLUE;
        public final int backgroundColor;
        public final int edgeColor;
        public final int edgeType;
        public final int foregroundColor;
        private final boolean mHasBackgroundColor;
        private final boolean mHasEdgeColor;
        private final boolean mHasEdgeType;
        private final boolean mHasForegroundColor;
        private final boolean mHasWindowColor;
        private Typeface mParsedTypeface;
        public final String mRawTypeface;
        public final int windowColor;

        public static boolean hasColor(int i) {
            return (i >>> 24) != 0 || (i & 16776960) == 0;
        }

        private CaptionStyle(int i, int i2, int i3, int i4, int i5, String str) {
            boolean zHasColor = hasColor(i);
            this.mHasForegroundColor = zHasColor;
            boolean zHasColor2 = hasColor(i2);
            this.mHasBackgroundColor = zHasColor2;
            boolean z = i3 != -1;
            this.mHasEdgeType = z;
            boolean zHasColor3 = hasColor(i4);
            this.mHasEdgeColor = zHasColor3;
            boolean zHasColor4 = hasColor(i5);
            this.mHasWindowColor = zHasColor4;
            this.foregroundColor = zHasColor ? i : -1;
            this.backgroundColor = zHasColor2 ? i2 : -16777216;
            this.edgeType = z ? i3 : 0;
            this.edgeColor = zHasColor3 ? i4 : -16777216;
            this.windowColor = zHasColor4 ? i5 : 255;
            this.mRawTypeface = str;
        }

        public CaptionStyle applyStyle(CaptionStyle captionStyle) {
            int i = captionStyle.hasForegroundColor() ? captionStyle.foregroundColor : this.foregroundColor;
            int i2 = captionStyle.hasBackgroundColor() ? captionStyle.backgroundColor : this.backgroundColor;
            int i3 = captionStyle.hasEdgeType() ? captionStyle.edgeType : this.edgeType;
            int i4 = captionStyle.hasEdgeColor() ? captionStyle.edgeColor : this.edgeColor;
            int i5 = captionStyle.hasWindowColor() ? captionStyle.windowColor : this.windowColor;
            String str = captionStyle.mRawTypeface;
            if (str == null) {
                str = this.mRawTypeface;
            }
            return new CaptionStyle(i, i2, i3, i4, i5, str);
        }

        public boolean hasBackgroundColor() {
            return this.mHasBackgroundColor;
        }

        public boolean hasForegroundColor() {
            return this.mHasForegroundColor;
        }

        public boolean hasEdgeType() {
            return this.mHasEdgeType;
        }

        public boolean hasEdgeColor() {
            return this.mHasEdgeColor;
        }

        public boolean hasWindowColor() {
            return this.mHasWindowColor;
        }

        public Typeface getTypeface() {
            if (this.mParsedTypeface == null && !TextUtils.isEmpty(this.mRawTypeface)) {
                this.mParsedTypeface = Typeface.create(this.mRawTypeface, 0);
            }
            return this.mParsedTypeface;
        }

        public static CaptionStyle getCustomStyle(ContentResolver contentResolver) {
            CaptionStyle captionStyle = DEFAULT_CUSTOM;
            int i = Settings.Secure.getInt(contentResolver, "accessibility_captioning_foreground_color", captionStyle.foregroundColor);
            int i2 = Settings.Secure.getInt(contentResolver, "accessibility_captioning_background_color", captionStyle.backgroundColor);
            int i3 = Settings.Secure.getInt(contentResolver, "accessibility_captioning_edge_type", captionStyle.edgeType);
            int i4 = Settings.Secure.getInt(contentResolver, "accessibility_captioning_edge_color", captionStyle.edgeColor);
            int i5 = Settings.Secure.getInt(contentResolver, "accessibility_captioning_window_color", captionStyle.windowColor);
            String string = Settings.Secure.getString(contentResolver, "accessibility_captioning_typeface");
            if (string == null) {
                string = captionStyle.mRawTypeface;
            }
            return new CaptionStyle(i, i2, i3, i4, i5, string);
        }

        static {
            CaptionStyle captionStyle = new CaptionStyle(-1, -16777216, 0, -16777216, 255, null);
            WHITE_ON_BLACK = captionStyle;
            CaptionStyle captionStyle2 = new CaptionStyle(-16777216, -1, 0, -16777216, 255, null);
            BLACK_ON_WHITE = captionStyle2;
            CaptionStyle captionStyle3 = new CaptionStyle(-256, -16777216, 0, -16777216, 255, null);
            YELLOW_ON_BLACK = captionStyle3;
            CaptionStyle captionStyle4 = new CaptionStyle(-256, -16776961, 0, -16777216, 255, null);
            YELLOW_ON_BLUE = captionStyle4;
            CaptionStyle captionStyle5 = new CaptionStyle(16777215, 16777215, -1, 16777215, 16777215, null);
            UNSPECIFIED = captionStyle5;
            PRESETS = new CaptionStyle[]{captionStyle, captionStyle2, captionStyle3, captionStyle4, captionStyle5};
            DEFAULT_CUSTOM = captionStyle;
            DEFAULT = captionStyle;
        }
    }
}
