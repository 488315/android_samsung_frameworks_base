package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.os.SystemClock;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.view.View;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.KeyguardConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final C14591 mStatusBarStateListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowNextIndication {
        public ExecutorImpl.ExecutionToken mCancelDelayedRunnable;
        public final RunnableC1458x49118fb4 mShowIndicationRunnable;

        public ShowNextIndication(long j) {
            RunnableC1458x49118fb4 runnableC1458x49118fb4 = new RunnableC1458x49118fb4(this);
            this.mShowIndicationRunnable = runnableC1458x49118fb4;
            this.mCancelDelayedRunnable = KeyguardIndicationRotateTextViewController.this.mExecutor.executeDelayed(j, runnableC1458x49118fb4);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$1] */
    public KeyguardIndicationRotateTextViewController(KeyguardIndicationTextView keyguardIndicationTextView, DelayableExecutor delayableExecutor, StatusBarStateController statusBarStateController, KeyguardLogger keyguardLogger, FeatureFlags featureFlags) {
        super(keyguardIndicationTextView);
        this.mIndicationMessages = new HashMap();
        this.mIndicationQueue = new ArrayList();
        this.mCurrIndicationType = -1;
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = KeyguardIndicationRotateTextViewController.this;
                ((KeyguardIndicationTextView) keyguardIndicationRotateTextViewController.mView).setAlpha((1.0f - f) * keyguardIndicationRotateTextViewController.mMaxAlpha);
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
                } else if (keyguardIndicationRotateTextViewController.mIndicationQueue.size() > 0) {
                    keyguardIndicationRotateTextViewController.showIndication(((Integer) keyguardIndicationRotateTextViewController.mIndicationQueue.get(0)).intValue());
                }
            }
        };
        this.mMaxAlpha = keyguardIndicationTextView.getAlpha();
        this.mExecutor = delayableExecutor;
        View view = this.mView;
        this.mInitialTextColorState = view != null ? ((KeyguardIndicationTextView) view).getTextColors() : ColorStateList.valueOf(-1);
        this.mStatusBarStateController = statusBarStateController;
        this.mLogger = keyguardLogger;
        this.mFeatureFlags = featureFlags;
        init();
    }

    public static String indicationTypeToString(int i) {
        switch (i) {
            case -1:
                return "none";
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
            default:
                return LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("unknown[", i, "]");
            case 10:
                return "reverse_charging";
            case 11:
                return "biometric_message";
            case 12:
                return "biometric_message_followup";
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "KeyguardIndicationRotatingTextViewController:", "    currentTextViewMessage=");
        m75m.append((Object) ((KeyguardIndicationTextView) this.mView).getText());
        printWriter.println(m75m.toString());
        printWriter.println("    currentStoredMessage=" + ((Object) ((KeyguardIndicationTextView) this.mView).mMessage));
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("    dozing:"), this.mIsDozing, printWriter, "    queue:");
        m64m.append(this.mIndicationQueue);
        printWriter.println(m64m.toString());
        printWriter.println("    showNextIndicationRunnable:" + this.mShowNextIndicationRunnable);
        Map map = this.mIndicationMessages;
        if (((HashMap) map).keySet().size() > 0) {
            printWriter.println("    All messages:");
            Iterator it = ((HashMap) map).keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("        type=", intValue, " ");
                m1m.append(((HashMap) map).get(Integer.valueOf(intValue)));
                printWriter.println(m1m.toString());
            }
        }
    }

    public final void hideIndication(int i) {
        Integer valueOf = Integer.valueOf(i);
        Map map = this.mIndicationMessages;
        if (!map.containsKey(valueOf) || TextUtils.isEmpty(((KeyguardIndication) map.get(Integer.valueOf(i))).mMessage)) {
            return;
        }
        updateIndication(i, null, true);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) this.mView;
        boolean isEnabled = ((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.KEYGUARD_TALKBACK_FIX);
        keyguardIndicationTextView.mAlwaysAnnounceText = isEnabled;
        if (isEnabled) {
            keyguardIndicationTextView.setAccessibilityLiveRegion(0);
        } else {
            keyguardIndicationTextView.setAccessibilityLiveRegion(1);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
        if (showNextIndication != null) {
            ExecutorImpl.ExecutionToken executionToken = showNextIndication.mCancelDelayedRunnable;
            if (executionToken != null) {
                executionToken.run();
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
            ExecutorImpl.ExecutionToken executionToken = showNextIndication.mCancelDelayedRunnable;
            if (executionToken != null) {
                executionToken.run();
                showNextIndication.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
        CharSequence charSequence2 = this.mCurrMessage;
        int i2 = this.mCurrIndicationType;
        this.mCurrIndicationType = i;
        Integer valueOf = Integer.valueOf(i);
        HashMap hashMap = (HashMap) this.mIndicationMessages;
        this.mCurrMessage = hashMap.get(valueOf) != null ? ((KeyguardIndication) hashMap.get(Integer.valueOf(i))).mMessage : null;
        C1457x419da85a c1457x419da85a = new C1457x419da85a(i, 2);
        ArrayList arrayList = (ArrayList) this.mIndicationQueue;
        arrayList.removeIf(c1457x419da85a);
        if (this.mCurrIndicationType != -1) {
            arrayList.add(Integer.valueOf(i));
        }
        this.mLastIndicationSwitch = SystemClock.uptimeMillis();
        if (!TextUtils.equals(charSequence2, this.mCurrMessage) || i2 != this.mCurrIndicationType) {
            CharSequence charSequence3 = this.mCurrMessage;
            this.mLogger.logKeyguardSwitchIndication(i, charSequence3 != null ? charSequence3.toString() : null);
            KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) this.mView;
            KeyguardIndication keyguardIndication = (KeyguardIndication) hashMap.get(Integer.valueOf(i));
            if (keyguardIndication == null) {
                charSequence = null;
            } else {
                keyguardIndicationTextView.getClass();
                charSequence = keyguardIndication.mMessage;
            }
            keyguardIndicationTextView.switchIndication(charSequence, keyguardIndication);
        }
        if (this.mCurrIndicationType == -1 || arrayList.size() <= 1) {
            return;
        }
        KeyguardIndication keyguardIndication2 = (KeyguardIndication) hashMap.get(Integer.valueOf(i));
        long j = 0;
        if (keyguardIndication2 != null && (l = keyguardIndication2.mMinVisibilityMillis) != null) {
            j = l.longValue();
        }
        long max = Math.max(j, 3500L);
        ShowNextIndication showNextIndication2 = this.mShowNextIndicationRunnable;
        if (showNextIndication2 != null) {
            ExecutorImpl.ExecutionToken executionToken2 = showNextIndication2.mCancelDelayedRunnable;
            if (executionToken2 != null) {
                executionToken2.run();
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
        Integer valueOf = Integer.valueOf(this.mCurrIndicationType);
        HashMap hashMap = (HashMap) this.mIndicationMessages;
        KeyguardIndication keyguardIndication2 = (KeyguardIndication) hashMap.get(valueOf);
        long j = 0;
        long longValue = (keyguardIndication2 == null || (l = keyguardIndication2.mMinVisibilityMillis) == null) ? 0L : l.longValue();
        boolean z2 = (keyguardIndication == null || TextUtils.isEmpty(keyguardIndication.mMessage)) ? false : true;
        List list = this.mIndicationQueue;
        if (z2) {
            ArrayList arrayList = (ArrayList) list;
            if (!arrayList.contains(Integer.valueOf(i))) {
                arrayList.add(Integer.valueOf(i));
            }
            hashMap.put(Integer.valueOf(i), keyguardIndication);
        } else {
            hashMap.remove(Integer.valueOf(i));
            ((ArrayList) list).removeIf(new C1457x419da85a(i, 0));
        }
        if (this.mIsDozing) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mLastIndicationSwitch;
        boolean z3 = uptimeMillis >= longValue;
        if (!z2) {
            if (this.mCurrIndicationType == i && !z2 && z) {
                if (z3) {
                    ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
                    if (showNextIndication == null) {
                        showIndication(-1);
                        return;
                    }
                    ExecutorImpl.ExecutionToken executionToken = showNextIndication.mCancelDelayedRunnable;
                    if (executionToken != null) {
                        executionToken.run();
                        showNextIndication.mCancelDelayedRunnable = null;
                    }
                    showNextIndication.mShowIndicationRunnable.run();
                    return;
                }
                long j2 = longValue - uptimeMillis;
                ShowNextIndication showNextIndication2 = this.mShowNextIndicationRunnable;
                if (showNextIndication2 != null) {
                    ExecutorImpl.ExecutionToken executionToken2 = showNextIndication2.mCancelDelayedRunnable;
                    if (executionToken2 != null) {
                        executionToken2.run();
                        showNextIndication2.mCancelDelayedRunnable = null;
                    }
                    this.mShowNextIndicationRunnable = null;
                }
                this.mShowNextIndicationRunnable = new ShowNextIndication(j2);
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
            ArrayList arrayList2 = (ArrayList) list;
            arrayList2.removeIf(new C1457x419da85a(i, 1));
            arrayList2.add(0, Integer.valueOf(i));
            long j3 = longValue - uptimeMillis;
            ShowNextIndication showNextIndication3 = this.mShowNextIndicationRunnable;
            if (showNextIndication3 != null) {
                ExecutorImpl.ExecutionToken executionToken3 = showNextIndication3.mCancelDelayedRunnable;
                if (executionToken3 != null) {
                    executionToken3.run();
                    showNextIndication3.mCancelDelayedRunnable = null;
                }
                this.mShowNextIndicationRunnable = null;
            }
            this.mShowNextIndicationRunnable = new ShowNextIndication(j3);
            return;
        }
        if (this.mShowNextIndicationRunnable != null) {
            return;
        }
        KeyguardIndication keyguardIndication3 = (KeyguardIndication) hashMap.get(Integer.valueOf(i));
        if (keyguardIndication3 != null && (l2 = keyguardIndication3.mMinVisibilityMillis) != null) {
            j = l2.longValue();
        }
        long max = Math.max(j, 3500L);
        if (uptimeMillis >= max) {
            showIndication(i);
            return;
        }
        long j4 = max - uptimeMillis;
        ShowNextIndication showNextIndication4 = this.mShowNextIndicationRunnable;
        if (showNextIndication4 != null) {
            ExecutorImpl.ExecutionToken executionToken4 = showNextIndication4.mCancelDelayedRunnable;
            if (executionToken4 != null) {
                executionToken4.run();
                showNextIndication4.mCancelDelayedRunnable = null;
            }
            this.mShowNextIndicationRunnable = null;
        }
        this.mShowNextIndicationRunnable = new ShowNextIndication(j4);
    }
}
