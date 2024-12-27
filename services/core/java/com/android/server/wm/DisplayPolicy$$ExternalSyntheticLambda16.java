package com.android.server.wm;

import com.android.server.LocalServices;
import com.android.server.statusbar.StatusBarManagerInternal;

import com.samsung.android.cocktailbar.CocktailBarManagerInternal;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayPolicy$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ DisplayPolicy f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda16(
            DisplayPolicy displayPolicy,
            DisplayPolicy$$ExternalSyntheticLambda12 displayPolicy$$ExternalSyntheticLambda12) {
        this.f$0 = displayPolicy;
        this.f$1 = displayPolicy$$ExternalSyntheticLambda12;
    }

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda16(
            DisplayPolicy displayPolicy, Consumer consumer) {
        this.f$0 = displayPolicy;
        this.f$1 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CocktailBarManagerInternal cocktailBarManagerInternal;
        switch (this.$r8$classId) {
            case 0:
                DisplayPolicy displayPolicy = this.f$0;
                Consumer consumer = this.f$1;
                StatusBarManagerInternal statusBarManagerInternal =
                        displayPolicy.getStatusBarManagerInternal();
                if (statusBarManagerInternal != null) {
                    consumer.accept(statusBarManagerInternal);
                    return;
                }
                return;
            default:
                DisplayPolicy displayPolicy2 = this.f$0;
                Consumer consumer2 = this.f$1;
                synchronized (displayPolicy2.mServiceAcquireLock) {
                    try {
                        if (displayPolicy2.mCocktailBarInternal == null) {
                            displayPolicy2.mCocktailBarInternal =
                                    (CocktailBarManagerInternal)
                                            LocalServices.getService(
                                                    CocktailBarManagerInternal.class);
                        }
                        cocktailBarManagerInternal = displayPolicy2.mCocktailBarInternal;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (cocktailBarManagerInternal != null) {
                    consumer2.accept(cocktailBarManagerInternal);
                    return;
                }
                return;
        }
    }
}
