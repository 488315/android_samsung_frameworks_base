package com.android.wm.shell.onehanded;

import android.content.ContentResolver;
import android.provider.Settings;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.sysui.ShellController;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell$9$$ExternalSyntheticLambda0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandedController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OneHandedController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((OneHandedController) this.f$0).stopOneHanded();
                break;
            case 1:
                ((OneHandedController) this.f$0).onActivatedActionChanged();
                break;
            case 2:
                ((OneHandedController) this.f$0).onEnabledSettingChanged();
                break;
            case 3:
                ((OneHandedController) this.f$0).onSwipeToNotificationEnabledChanged();
                break;
            case 4:
                ((OneHandedController) this.f$0).onShortcutEnabledChanged();
                break;
            case 5:
                final OneHandedController oneHandedController = (OneHandedController) this.f$0;
                oneHandedController.getClass();
                oneHandedController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        OneHandedController oneHandedController2 = OneHandedController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        oneHandedController2.getClass();
                        printWriter.println();
                        printWriter.println("OneHandedController");
                        printWriter.print("  mOffSetFraction=");
                        printWriter.println(oneHandedController2.mOffSetFraction);
                        printWriter.print("  mLockedDisabled=");
                        printWriter.println(oneHandedController2.mLockedDisabled);
                        printWriter.print("  mUserId=");
                        printWriter.println(oneHandedController2.mUserId);
                        printWriter.print("  isShortcutEnabled=");
                        printWriter.println(oneHandedController2.isShortcutEnabled());
                        printWriter.print("  mIsSwipeToNotificationEnabled=");
                        printWriter.println(oneHandedController2.mIsSwipeToNotificationEnabled);
                        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = oneHandedController2.mDisplayAreaOrganizer;
                        if (oneHandedDisplayAreaOrganizer != null) {
                            printWriter.println("OneHandedDisplayAreaOrganizer");
                            printWriter.print("  mDisplayLayout.rotation()=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mDisplayLayout.mRotation);
                            printWriter.print("  mDisplayAreaTokenMap=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mDisplayAreaTokenMap);
                            printWriter.print("  mDefaultDisplayBounds=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mDefaultDisplayBounds);
                            printWriter.print("  mIsReady=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mIsReady);
                            printWriter.print("  mLastVisualDisplayBounds=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mLastVisualDisplayBounds);
                            printWriter.print("  mLastVisualOffset=");
                            printWriter.println(oneHandedDisplayAreaOrganizer.mLastVisualOffset);
                            OneHandedAnimationController oneHandedAnimationController = oneHandedDisplayAreaOrganizer.mAnimationController;
                            if (oneHandedAnimationController != null) {
                                printWriter.println("OneHandedAnimationControllerstates: ");
                                printWriter.print("  mAnimatorMap=");
                                printWriter.println(oneHandedAnimationController.mAnimatorMap);
                                OneHandedSurfaceTransactionHelper oneHandedSurfaceTransactionHelper = oneHandedAnimationController.mSurfaceTransactionHelper;
                                if (oneHandedSurfaceTransactionHelper != null) {
                                    printWriter.println("OneHandedSurfaceTransactionHelperstates: ");
                                    printWriter.print("  mEnableCornerRadius=");
                                    printWriter.println(oneHandedSurfaceTransactionHelper.mEnableCornerRadius);
                                    printWriter.print("  mCornerRadiusAdjustment=");
                                    printWriter.println(oneHandedSurfaceTransactionHelper.mCornerRadiusAdjustment);
                                    printWriter.print("  mCornerRadius=");
                                    printWriter.println(oneHandedSurfaceTransactionHelper.mCornerRadius);
                                }
                            }
                        }
                        OneHandedTouchHandler oneHandedTouchHandler = oneHandedController2.mTouchHandler;
                        if (oneHandedTouchHandler != null) {
                            printWriter.println("OneHandedTouchHandler");
                            printWriter.print("  mLastUpdatedBounds=");
                            printWriter.println(oneHandedTouchHandler.mLastUpdatedBounds);
                        }
                        OneHandedTimeoutHandler oneHandedTimeoutHandler = oneHandedController2.mTimeoutHandler;
                        if (oneHandedTimeoutHandler != null) {
                            printWriter.println("OneHandedTimeoutHandler");
                            printWriter.print("  sTimeout=");
                            printWriter.println(oneHandedTimeoutHandler.mTimeout);
                            printWriter.print("  sListeners=");
                            printWriter.println(oneHandedTimeoutHandler.mListeners);
                        }
                        if (oneHandedController2.mState != null) {
                            StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "OneHandedState", "  sCurrentState=");
                            m75m.append(OneHandedState.sCurrentState);
                            printWriter.println(m75m.toString());
                        }
                        OneHandedTutorialHandler oneHandedTutorialHandler = oneHandedController2.mTutorialHandler;
                        if (oneHandedTutorialHandler != null) {
                            printWriter.println("OneHandedTutorialHandler");
                            printWriter.print("  isAttached=");
                            printWriter.println(oneHandedTutorialHandler.isAttached());
                            printWriter.print("  mCurrentState=");
                            printWriter.println(oneHandedTutorialHandler.mCurrentState);
                            printWriter.print("  mDisplayBounds=");
                            printWriter.println(oneHandedTutorialHandler.mDisplayBounds);
                            printWriter.print("  mTutorialAreaHeight=");
                            printWriter.println(oneHandedTutorialHandler.mTutorialAreaHeight);
                            printWriter.print("  mAlphaTransitionStart=");
                            printWriter.println(oneHandedTutorialHandler.mAlphaTransitionStart);
                            printWriter.print("  mAlphaAnimationDurationMs=");
                            printWriter.println(oneHandedTutorialHandler.mAlphaAnimationDurationMs);
                            BackgroundWindowManager backgroundWindowManager = oneHandedTutorialHandler.mBackgroundWindowManager;
                            if (backgroundWindowManager != null) {
                                printWriter.println("BackgroundWindowManager");
                                printWriter.print("  mDisplayBounds=");
                                printWriter.println(backgroundWindowManager.mDisplayBounds);
                                printWriter.print("  mViewHost=");
                                printWriter.println(backgroundWindowManager.mViewHost);
                                printWriter.print("  mLeash=");
                                printWriter.println(backgroundWindowManager.mLeash);
                                printWriter.print("  mBackgroundView=");
                                printWriter.println(backgroundWindowManager.mBackgroundView);
                            }
                        }
                        OneHandedAccessibilityUtil oneHandedAccessibilityUtil = oneHandedController2.mOneHandedAccessibilityUtil;
                        if (oneHandedAccessibilityUtil != null) {
                            printWriter.println("OneHandedAccessibilityUtil");
                            printWriter.print("  mPackageName=");
                            printWriter.println(oneHandedAccessibilityUtil.mPackageName);
                            printWriter.print("  mDescription=");
                            printWriter.println(oneHandedAccessibilityUtil.mDescription);
                        }
                        ContentResolver contentResolver = oneHandedController2.mContext.getContentResolver();
                        int i = oneHandedController2.mUserId;
                        oneHandedController2.mOneHandedSettingsUtil.getClass();
                        printWriter.println("OneHandedSettingsUtil");
                        printWriter.print("  isOneHandedModeEnable=");
                        printWriter.println(OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i));
                        printWriter.print("  isSwipeToNotificationEnabled=");
                        printWriter.println(OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver, i));
                        printWriter.print("  oneHandedTimeOut=");
                        printWriter.println(Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_timeout", 8, i));
                        printWriter.print("  tapsAppToExit=");
                        printWriter.println(Settings.Secure.getIntForUser(contentResolver, "taps_app_to_exit", 1, i) == 1);
                        printWriter.print("  shortcutActivated=");
                        printWriter.println(Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_activated", 0, i) == 1);
                        printWriter.print("  tutorialShownCounts=");
                        printWriter.println(Settings.Secure.getIntForUser(contentResolver, "one_handed_tutorial_show_count", 0, i));
                    }
                }, oneHandedController);
                DisplayController displayController = oneHandedController.mDisplayController;
                displayController.addDisplayWindowListener(oneHandedController.mDisplaysChangedListener);
                displayController.mChangeController.mDisplayChangeListener.add(oneHandedController);
                OneHandedController$$ExternalSyntheticLambda4 oneHandedController$$ExternalSyntheticLambda4 = new OneHandedController$$ExternalSyntheticLambda4(oneHandedController);
                OneHandedTouchHandler oneHandedTouchHandler = oneHandedController.mTouchHandler;
                oneHandedTouchHandler.mTouchEventCallback = oneHandedController$$ExternalSyntheticLambda4;
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = oneHandedController.mDisplayAreaOrganizer;
                ((ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks).add(oneHandedTouchHandler);
                ArrayList arrayList = (ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks;
                OneHandedTutorialHandler oneHandedTutorialHandler = oneHandedController.mTutorialHandler;
                arrayList.add(oneHandedTutorialHandler);
                ((ArrayList) oneHandedDisplayAreaOrganizer.mTransitionCallbacks).add(oneHandedController.mTransitionCallBack);
                if (oneHandedController.mTaskChangeToExit) {
                    oneHandedController.mTaskStackListener.addListener(oneHandedController.mTaskStackListenerCallback);
                }
                oneHandedController.registerSettingObservers(oneHandedController.mUserId);
                ((ArrayList) oneHandedController.mTimeoutHandler.mListeners).add(new OneHandedController$$ExternalSyntheticLambda3(oneHandedController));
                oneHandedController.updateSettings();
                oneHandedController.updateDisplayLayout(oneHandedController.mContext.getDisplayId());
                oneHandedController.mAccessibilityManager.addAccessibilityStateChangeListener(oneHandedController.mAccessibilityStateChangeListener);
                ((ArrayList) oneHandedController.mState.mStateChangeListeners).add(oneHandedTutorialHandler);
                ShellController shellController = oneHandedController.mShellController;
                shellController.addConfigurationChangeListener(oneHandedController);
                CopyOnWriteArrayList copyOnWriteArrayList = shellController.mKeyguardChangeListeners;
                copyOnWriteArrayList.remove(oneHandedController);
                copyOnWriteArrayList.add(oneHandedController);
                CopyOnWriteArrayList copyOnWriteArrayList2 = shellController.mUserChangeListeners;
                copyOnWriteArrayList2.remove(oneHandedController);
                copyOnWriteArrayList2.add(oneHandedController);
                shellController.addExternalInterface(QuickStepContract.KEY_EXTRA_SHELL_ONE_HANDED, new Supplier() { // from class: com.android.wm.shell.onehanded.OneHandedController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        OneHandedController oneHandedController2 = OneHandedController.this;
                        oneHandedController2.getClass();
                        return new OneHandedController.IOneHandedImpl(oneHandedController2);
                    }
                }, oneHandedController);
                break;
            case 6:
                ((OneHandedController) this.f$0).startOneHanded();
                break;
            case 7:
                WMShell.C374410 c374410 = ((OneHandedController) this.f$0).mEventCallback;
                WMShell.this.mSysUiMainExecutor.execute(new WMShell$9$$ExternalSyntheticLambda0(c374410, 3));
                break;
            default:
                OneHandedController.this.stopOneHanded();
                break;
        }
    }
}
