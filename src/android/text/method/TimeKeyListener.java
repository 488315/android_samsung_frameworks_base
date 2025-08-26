package android.text.method;

import android.text.format.DateFormat;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.util.ArrayUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

/* loaded from: classes4.dex */
public class TimeKeyListener extends NumberKeyListener {
    private static final String SKELETON_12HOUR = "hms";
    private static final String SKELETON_24HOUR = "Hms";
    private static final String SYMBOLS_TO_IGNORE = "ahHKkms";
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.AM_PM, DateFormat.MINUTE, 'p', ShortcutConstants.SERVICES_SEPARATOR};
    private static final Object sLock = new Object();
    private static final HashMap<Locale, TimeKeyListener> sInstanceCache = new HashMap<>();

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return this.mNeedsAdvancedInput ? 1 : 36;
    }

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return this.mCharacters;
    }

    @Deprecated
    public TimeKeyListener() {
        this(null);
    }

    public TimeKeyListener(Locale locale) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (NumberKeyListener.addDigits(linkedHashSet, locale) && NumberKeyListener.addAmPmChars(linkedHashSet, locale) && NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_12HOUR, SYMBOLS_TO_IGNORE) && NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_24HOUR, SYMBOLS_TO_IGNORE)) {
            this.mCharacters = NumberKeyListener.collectionToArray(linkedHashSet);
            if (locale != null && "en".equals(locale.getLanguage())) {
                this.mNeedsAdvancedInput = false;
                return;
            } else {
                this.mNeedsAdvancedInput = !ArrayUtils.containsAll(CHARACTERS, r0);
                return;
            }
        }
        this.mCharacters = CHARACTERS;
        this.mNeedsAdvancedInput = false;
    }

    @Deprecated
    public static TimeKeyListener getInstance() {
        return getInstance(null);
    }

    public static TimeKeyListener getInstance(Locale locale) {
        TimeKeyListener timeKeyListener;
        synchronized (sLock) {
            HashMap<Locale, TimeKeyListener> map = sInstanceCache;
            timeKeyListener = map.get(locale);
            if (timeKeyListener == null) {
                timeKeyListener = new TimeKeyListener(locale);
                map.put(locale, timeKeyListener);
            }
        }
        return timeKeyListener;
    }
}
