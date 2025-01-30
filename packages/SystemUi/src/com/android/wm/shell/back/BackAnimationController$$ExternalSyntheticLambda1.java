package com.android.wm.shell.back;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.SparseArray;
import android.window.BackAnimationAdapter;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationController.C37804;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BackAnimationController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BackAnimationController f$0;

    public /* synthetic */ BackAnimationController$$ExternalSyntheticLambda1(BackAnimationController backAnimationController, int i) {
        this.$r8$classId = i;
        this.f$0 = backAnimationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BackAnimationController backAnimationController = this.f$0;
                backAnimationController.getClass();
                if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                    ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -362608487, 1, "Animation didn't finish in %d ms. Resetting...", 2000L);
                }
                backAnimationController.onBackAnimationFinished();
                break;
            case 1:
                final BackAnimationController backAnimationController2 = this.f$0;
                backAnimationController2.getClass();
                ContentObserver c37782 = new ContentObserver(backAnimationController2.mBgHandler) { // from class: com.android.wm.shell.back.BackAnimationController.2
                    public C37782(Handler handler) {
                        super(handler);
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        BackAnimationController backAnimationController3 = BackAnimationController.this;
                        boolean z2 = BackAnimationController.IS_ENABLED;
                        backAnimationController3.updateEnableAnimationFromSetting();
                    }
                };
                backAnimationController2.mContentResolver.registerContentObserver(Settings.Global.getUriFor("enable_back_animation"), false, c37782, 0);
                backAnimationController2.updateEnableAnimationFromSetting();
                backAnimationController2.mBackAnimationAdapter = new BackAnimationAdapter(backAnimationController2.new C37804());
                backAnimationController2.mShellController.addExternalInterface("extra_shell_back_animation", new Supplier() { // from class: com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        BackAnimationController backAnimationController3 = BackAnimationController.this;
                        backAnimationController3.getClass();
                        return new BackAnimationController.IBackAnimationImpl(backAnimationController3);
                    }
                }, backAnimationController2);
                if (BackAnimationController.IS_U_ANIMATION_ENABLED) {
                    Context context = backAnimationController2.mContext;
                    BackAnimationBackground backAnimationBackground = backAnimationController2.mAnimationBackground;
                    CrossTaskBackAnimation crossTaskBackAnimation = new CrossTaskBackAnimation(context, backAnimationBackground);
                    SparseArray sparseArray = backAnimationController2.mAnimationDefinition;
                    sparseArray.set(3, crossTaskBackAnimation.mBackAnimationRunner);
                    CrossActivityAnimation crossActivityAnimation = new CrossActivityAnimation(context, backAnimationBackground);
                    backAnimationController2.mDefaultActivityAnimation = crossActivityAnimation;
                    sparseArray.set(2, crossActivityAnimation.mBackAnimationRunner);
                    backAnimationController2.mCustomizeActivityAnimation = new CustomizeActivityAnimation(context, backAnimationBackground);
                    break;
                }
                break;
            default:
                this.f$0.onBackAnimationFinished();
                break;
        }
    }
}
