package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class KeyguardIndicationRotateTextViewController extends ViewController implements Dumpable {
    public int mCurrIndicationType;
    public CharSequence mCurrMessage;
    public final DelayableExecutor mExecutor;
    public final FeatureFlags mFeatureFlags;
    public final Map mIndicationMessages;
    public final List mIndicationQueue;
    public final ColorStateList mInitialTextColorState;
    public boolean mIsDozing;
    public long mLastIndicationSwitch;
    public final KeyguardLogger mLogger;
    public final float mMaxAlpha;
    ShowNextIndication mShowNextIndicationRunnable;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass1 mStatusBarStateListener;

    public final class ShowNextIndication {
        public Runnable mCancelDelayedRunnable;
        public final KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 mShowIndicationRunnable;

        public ShowNextIndication(long j) {
            KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 keyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 = new KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0(this);
            this.mShowIndicationRunnable = keyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0;
            this.mCancelDelayedRunnable = KeyguardIndicationRotateTextViewController.this.mExecutor.executeDelayed(keyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0, j);
        }
    }

    public KeyguardIndicationRotateTextViewController(KeyguardIndicationTextView keyguardIndicationTextView, DelayableExecutor delayableExecutor, StatusBarStateController statusBarStateController, KeyguardLogger keyguardLogger, FeatureFlags featureFlags) {
        super(keyguardIndicationTextView);
        this.mIndicationMessages = new HashMap();
        this.mIndicationQueue = new ArrayList();
        this.mCurrIndicationType = -1;
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = KeyguardIndicationRotateTextViewController.this;
                ((KeyguardIndicationTextView) ((ViewController) keyguardIndicationRotateTextViewController).mView).setAlpha((1.0f - f) * keyguardIndicationRotateTextViewController.mMaxAlpha);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = KeyguardIndicationRotateTextViewController.this;
                if (z == keyguardIndicationRotateTextViewController.mIsDozing) {
                    return;
                }
                keyguardIndicationRotateTextViewController.mIsDozing = z;
                if (z) {
                    keyguardIndicationRotateTextViewController.showIndication(-1);
                } else if (((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).size() > 0) {
                    keyguardIndicationRotateTextViewController.showIndication(((Integer) ((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).get(0)).intValue());
                }
            }
        };
        this.mMaxAlpha = keyguardIndicationTextView.getAlpha();
        this.mExecutor = delayableExecutor;
        T t = this.mView;
        this.mInitialTextColorState = t != 0 ? ((KeyguardIndicationTextView) t).getTextColors() : ColorStateList.valueOf(-1);
        this.mStatusBarStateController = statusBarStateController;
        this.mLogger = keyguardLogger;
        this.mFeatureFlags = featureFlags;
        init();
    }

    public static String indicationTypeToString(int i) {
        switch (i) {
            case -1:
                return SignalSeverity.NONE;
            case 0:
                return "owner_info";
            case 1:
                return "disclosure";
            case 2:
                return "logout";
            case 3:
                return "battery";
            case 4:
                return "alignment";
            case 5:
                return "transient";
            case 6:
                return KeyguardConstants.UpdateType.TrustStateKey.TRUST;
            case 7:
                return "persistent_unlock_message";
            case 8:
                return "user_locked";
            case 9:
            case 13:
            default:
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "unknown[", "]");
            case 10:
                return "reverse_charging";
            case 11:
                return "biometric_message";
            case 12:
                return "biometric_message_followup";
            case 14:
                return "adaptive_auth";
        }
    }

    public final void clearMessages() {
        this.mCurrIndicationType = -1;
        ((ArrayList) this.mIndicationQueue).clear();
        ((HashMap) this.mIndicationMessages).clear();
        ((KeyguardIndicationTextView) this.mView).clearMessages();
    }

    @Override // com.android.systemui.util.ViewController
    public final void destroy() {
        super.destroy();
        onViewDetached();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardIndicationRotatingTextViewController:", "    currentTextViewMessage=");
        m.append((Object) ((KeyguardIndicationTextView) this.mView).getText());
        printWriter.println(m.toString());
        printWriter.println("    currentStoredMessage=" + ((Object) ((KeyguardIndicationTextView) this.mView).mMessage));
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("    dozing:"), this.mIsDozing, printWriter, "    queue:");
        m2.append(this.mIndicationQueue);
        printWriter.println(m2.toString());
        printWriter.println("    showNextIndicationRunnable:" + this.mShowNextIndicationRunnable);
        if (((HashMap) this.mIndicationMessages).keySet().size() > 0) {
            printWriter.println("    All messages:");
            for (Integer num : ((HashMap) this.mIndicationMessages).keySet()) {
                StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(num.intValue(), "        type=", " ");
                m3.append(((HashMap) this.mIndicationMessages).get(num));
                printWriter.println(m3.toString());
            }
        }
    }

    public final void hideIndication(int i) {
        if (((HashMap) this.mIndicationMessages).containsKey(Integer.valueOf(i))) {
            if (TextUtils.isEmpty(((KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i))).mMessage)) {
                return;
            }
            updateIndication(i, null, true);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) this.mView;
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        keyguardIndicationTextView.mAlwaysAnnounceText = false;
        keyguardIndicationTextView.setAccessibilityLiveRegion(1);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
        if (showNextIndication != null) {
            Runnable runnable = showNextIndication.mCancelDelayedRunnable;
            if (runnable != null) {
                runnable.run();
                showNextIndication.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
    }

    public final void showIndication(int i) {
        CharSequence charSequence;
        Long l;
        ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
        if (showNextIndication != null) {
            Runnable runnable = showNextIndication.mCancelDelayedRunnable;
            if (runnable != null) {
                runnable.run();
                showNextIndication.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
        CharSequence charSequence2 = this.mCurrMessage;
        int i2 = this.mCurrIndicationType;
        this.mCurrIndicationType = i;
        this.mCurrMessage = ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i)) != null ? ((KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i))).mMessage : null;
        ((ArrayList) this.mIndicationQueue).removeIf(new KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0(i, 2));
        if (this.mCurrIndicationType != -1) {
            ((ArrayList) this.mIndicationQueue).add(Integer.valueOf(i));
        }
        this.mLastIndicationSwitch = SystemClock.uptimeMillis();
        if (!TextUtils.equals(charSequence2, this.mCurrMessage) || i2 != this.mCurrIndicationType) {
            CharSequence charSequence3 = this.mCurrMessage;
            this.mLogger.logKeyguardSwitchIndication(i, charSequence3 != null ? charSequence3.toString() : null);
            KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) this.mView;
            KeyguardIndication keyguardIndication = (KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i));
            if (keyguardIndication == null) {
                charSequence = null;
            } else {
                keyguardIndicationTextView.getClass();
                charSequence = keyguardIndication.mMessage;
            }
            keyguardIndicationTextView.switchIndication(charSequence, keyguardIndication);
        }
        if (this.mCurrIndicationType == -1 || ((ArrayList) this.mIndicationQueue).size() <= 1) {
            return;
        }
        KeyguardIndication keyguardIndication2 = (KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i));
        long j = 0;
        if (keyguardIndication2 != null && (l = keyguardIndication2.mMinVisibilityMillis) != null) {
            j = l.longValue();
        }
        long max = Math.max(j, 3500L);
        ShowNextIndication showNextIndication2 = this.mShowNextIndicationRunnable;
        if (showNextIndication2 != null) {
            Runnable runnable2 = showNextIndication2.mCancelDelayedRunnable;
            if (runnable2 != null) {
                runnable2.run();
                showNextIndication2.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
        this.mShowNextIndicationRunnable = new ShowNextIndication(max);
    }

    public final void updateIndication(int i, KeyguardIndication keyguardIndication, boolean z) {
        Long l;
        Long l2;
        if (i == 10) {
            return;
        }
        if (i != 1) {
            return;
        }
        KeyguardIndication keyguardIndication2 = (KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(this.mCurrIndicationType));
        long j = 0;
        long longValue = (keyguardIndication2 == null || (l = keyguardIndication2.mMinVisibilityMillis) == null) ? 0L : l.longValue();
        boolean z2 = (keyguardIndication == null || TextUtils.isEmpty(keyguardIndication.mMessage)) ? false : true;
        if (z2) {
            if (!((ArrayList) this.mIndicationQueue).contains(Integer.valueOf(i))) {
                ((ArrayList) this.mIndicationQueue).add(Integer.valueOf(i));
            }
            ((HashMap) this.mIndicationMessages).put(Integer.valueOf(i), keyguardIndication);
        } else {
            ((HashMap) this.mIndicationMessages).remove(Integer.valueOf(i));
            ((ArrayList) this.mIndicationQueue).removeIf(new KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0(i, 0));
        }
        if (this.mIsDozing) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mLastIndicationSwitch;
        boolean z3 = uptimeMillis >= longValue;
        if (!z2) {
            if (this.mCurrIndicationType == i && !z2 && z) {
                ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
                if (showNextIndication == null) {
                    showIndication(-1);
                    return;
                }
                Runnable runnable = showNextIndication.mCancelDelayedRunnable;
                if (runnable != null) {
                    runnable.run();
                    showNextIndication.mCancelDelayedRunnable = null;
                }
                showNextIndication.mShowIndicationRunnable.run();
                return;
            }
            return;
        }
        int i2 = this.mCurrIndicationType;
        if (i2 == -1 || i2 == i) {
            showIndication(i);
            return;
        }
        if (z) {
            if (z3) {
                showIndication(i);
                return;
            }
            ((ArrayList) this.mIndicationQueue).removeIf(new KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0(i, 1));
            ((ArrayList) this.mIndicationQueue).add(0, Integer.valueOf(i));
            long j2 = longValue - uptimeMillis;
            ShowNextIndication showNextIndication2 = this.mShowNextIndicationRunnable;
            if (showNextIndication2 != null) {
                Runnable runnable2 = showNextIndication2.mCancelDelayedRunnable;
                if (runnable2 != null) {
                    runnable2.run();
                    showNextIndication2.mCancelDelayedRunnable = null;
                }
                this.mShowNextIndicationRunnable = null;
            }
            this.mShowNextIndicationRunnable = new ShowNextIndication(j2);
            return;
        }
        if (this.mShowNextIndicationRunnable != null) {
            return;
        }
        KeyguardIndication keyguardIndication3 = (KeyguardIndication) ((HashMap) this.mIndicationMessages).get(Integer.valueOf(i));
        if (keyguardIndication3 != null && (l2 = keyguardIndication3.mMinVisibilityMillis) != null) {
            j = l2.longValue();
        }
        long max = Math.max(j, 3500L);
        if (uptimeMillis >= max) {
            showIndication(i);
            return;
        }
        long j3 = max - uptimeMillis;
        ShowNextIndication showNextIndication3 = this.mShowNextIndicationRunnable;
        if (showNextIndication3 != null) {
            Runnable runnable3 = showNextIndication3.mCancelDelayedRunnable;
            if (runnable3 != null) {
                runnable3.run();
                showNextIndication3.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
        this.mShowNextIndicationRunnable = new ShowNextIndication(j3);
    }
}
