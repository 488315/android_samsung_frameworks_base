package com.android.wm.shell.splitscreen;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.util.SettingsHelper;
import java.util.Set;

/* loaded from: classes3.dex */
public final /* synthetic */ class EnterSplitGestureHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EnterSplitGestureHandler f$0;

    public /* synthetic */ EnterSplitGestureHandler$$ExternalSyntheticLambda1(EnterSplitGestureHandler enterSplitGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = enterSplitGestureHandler;
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wm.shell.splitscreen.EnterSplitGestureHandler$5] */
    @Override // java.lang.Runnable
    public final void run() throws NumberFormatException {
        int i;
        switch (this.$r8$classId) {
            case 0:
                final EnterSplitGestureHandler enterSplitGestureHandler = this.f$0;
                String str = EnterSplitGestureHandler.TAG;
                boolean z = EnterSplitGestureHandler.DEBUG;
                if (z) {
                    Slog.d(str, "init");
                }
                enterSplitGestureHandler.mIsSupportSplitScreen = ActivityTaskManager.deviceSupportsMultiWindow(enterSplitGestureHandler.mContext);
                if (z) {
                    Slog.d(str, "get settings");
                }
                ContentResolver contentResolver = enterSplitGestureHandler.mContext.getContentResolver();
                boolean z2 = false;
                enterSplitGestureHandler.mIsSettingEnabled = Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE, 0) == 1;
                String string = Settings.Secure.getString(contentResolver, SettingsHelper.INDEX_NAVIGATION_MODE);
                try {
                    i = Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    Slog.d(str, "failed to load nav mode=" + string);
                    e.printStackTrace();
                    i = 0;
                }
                enterSplitGestureHandler.mNavMode = i;
                enterSplitGestureHandler.mIsDeviceProvisioned = Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0;
                enterSplitGestureHandler.mIsUserSetupComplete = Settings.Secure.getIntForUser(contentResolver, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) != 0;
                try {
                    enterSplitGestureHandler.mIsLockTaskMode = enterSplitGestureHandler.mAtm.isInLockTaskMode();
                } catch (RemoteException e2) {
                    if (z) {
                        Slog.e(str, "Failed to get lock task mode.");
                    }
                    e2.printStackTrace();
                }
                Set enabledServicesFromSettings = AccessibilityUtils.getEnabledServicesFromSettings(enterSplitGestureHandler.mContext, 0);
                ComponentName talkbackComponent = enterSplitGestureHandler.getTalkbackComponent();
                if (talkbackComponent == null) {
                    talkbackComponent = new ComponentName("com.samsung.android.accessibility.talkback", "com.samsung.android.marvin.talkback.TalkBackService");
                }
                enterSplitGestureHandler.mIsTalkbackEnabled = enabledServicesFromSettings.contains(talkbackComponent);
                if (z) {
                    Slog.d(str, "register observer");
                }
                final Uri uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_MW_ENTER_SPLIT_USING_GESTURE);
                final Uri uriFor2 = Settings.Secure.getUriFor("MultiWindow_twoFingerSplitGesture_TestTouchSlop");
                final Uri uriFor3 = Settings.Secure.getUriFor("MultiWindow_twoFingerSplitGesture_TestFlag");
                final Uri uriFor4 = Settings.Secure.getUriFor(SettingsHelper.INDEX_NAVIGATION_MODE);
                final Uri uriFor5 = Settings.Global.getUriFor("device_provisioned");
                final Uri uriFor6 = Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE);
                final Uri uriFor7 = Settings.Secure.getUriFor(SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
                final ContentResolver contentResolver2 = enterSplitGestureHandler.mContext.getContentResolver();
                final Handler handler = enterSplitGestureHandler.mHandler;
                enterSplitGestureHandler.mObserver = 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00ed: IPUT 
                      (wrap:??:0x00ea: CONSTRUCTOR 
                      (r3v0 'enterSplitGestureHandler' com.android.wm.shell.splitscreen.EnterSplitGestureHandler A[DONT_INLINE])
                      (r4v3 'handler' android.os.Handler A[DONT_INLINE])
                      (r0v21 'uriFor' android.net.Uri A[DONT_INLINE])
                      (r6v3 'contentResolver2' android.content.ContentResolver A[DONT_INLINE])
                      (r2v6 'uriFor2' android.net.Uri A[DONT_INLINE])
                      (r4v2 'uriFor3' android.net.Uri A[DONT_INLINE])
                      (r9v2 'uriFor4' android.net.Uri A[DONT_INLINE])
                      (r10v1 'uriFor5' android.net.Uri A[DONT_INLINE])
                      (r11v0 'uriFor6' android.net.Uri A[DONT_INLINE])
                      (r12v0 'uriFor7' android.net.Uri A[DONT_INLINE])
                     A[MD:(com.android.wm.shell.splitscreen.EnterSplitGestureHandler, android.os.Handler, android.net.Uri, android.content.ContentResolver, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri):void (m), WRAPPED] (LINE:235) call: com.android.wm.shell.splitscreen.EnterSplitGestureHandler.5.<init>(com.android.wm.shell.splitscreen.EnterSplitGestureHandler, android.os.Handler, android.net.Uri, android.content.ContentResolver, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri, android.net.Uri):void type: CONSTRUCTOR)
                      (r3v0 'enterSplitGestureHandler' com.android.wm.shell.splitscreen.EnterSplitGestureHandler)
                     (LINE:238) com.android.wm.shell.splitscreen.EnterSplitGestureHandler.mObserver com.android.wm.shell.splitscreen.EnterSplitGestureHandler$5 in method: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda1.run():void, file: classes3.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.wm.shell.splitscreen.EnterSplitGestureHandler, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:487)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 21 more
                    */
                /*
                    Method dump skipped, instructions count: 390
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda1.run():void");
            }
        }
