package com.android.systemui.shade.data.repository;

import com.android.systemui.privacy.PrivacyConfig;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PrivacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrivacyChipRepositoryImpl f$0;
    public final /* synthetic */ PrivacyConfig.Callback f$1;

    public /* synthetic */ PrivacyChipRepositoryImpl$privacyItems$1$$ExternalSyntheticLambda0(PrivacyChipRepositoryImpl privacyChipRepositoryImpl, PrivacyConfig.Callback callback, int i) {
        this.$r8$classId = i;
        this.f$0 = privacyChipRepositoryImpl;
        this.f$1 = callback;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.privacyItemController.removeCallback((PrivacyChipRepositoryImpl$privacyItems$1$callback$1) this.f$1);
                break;
            case 1:
                final PrivacyConfig privacyConfig = this.f$0.privacyConfig;
                PrivacyChipRepositoryImpl$isLocationIndicationEnabled$1$callback$1 privacyChipRepositoryImpl$isLocationIndicationEnabled$1$callback$1 = (PrivacyChipRepositoryImpl$isLocationIndicationEnabled$1$callback$1) this.f$1;
                privacyConfig.getClass();
                final WeakReference weakReference = new WeakReference(privacyChipRepositoryImpl$isLocationIndicationEnabled$1$callback$1);
                privacyConfig.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyConfig$removeCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        List list = PrivacyConfig.this.callbacks;
                        final WeakReference weakReference2 = weakReference;
                        final Function1 function1 = new Function1() { // from class: com.android.systemui.privacy.PrivacyConfig$removeCallback$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj) {
                                WeakReference weakReference3 = weakReference2;
                                PrivacyConfig.Callback callback = (PrivacyConfig.Callback) ((WeakReference) obj).get();
                                return Boolean.valueOf(callback != null ? callback.equals(weakReference3.get()) : true);
                            }
                        };
                        ((ArrayList) list).removeIf(new Predicate() { // from class: com.android.systemui.privacy.PrivacyConfig$sam$java_util_function_Predicate$0
                            @Override // java.util.function.Predicate
                            public final /* synthetic */ boolean test(Object obj) {
                                return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
                            }
                        });
                    }
                });
                break;
            default:
                final PrivacyConfig privacyConfig2 = this.f$0.privacyConfig;
                PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1$callback$1 privacyChipRepositoryImpl$isMicCameraIndicationEnabled$1$callback$1 = (PrivacyChipRepositoryImpl$isMicCameraIndicationEnabled$1$callback$1) this.f$1;
                privacyConfig2.getClass();
                final WeakReference weakReference2 = new WeakReference(privacyChipRepositoryImpl$isMicCameraIndicationEnabled$1$callback$1);
                privacyConfig2.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyConfig$removeCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        List list = PrivacyConfig.this.callbacks;
                        final WeakReference weakReference22 = weakReference2;
                        final Function1 function1 = new Function1() { // from class: com.android.systemui.privacy.PrivacyConfig$removeCallback$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj) {
                                WeakReference weakReference3 = weakReference22;
                                PrivacyConfig.Callback callback = (PrivacyConfig.Callback) ((WeakReference) obj).get();
                                return Boolean.valueOf(callback != null ? callback.equals(weakReference3.get()) : true);
                            }
                        };
                        ((ArrayList) list).removeIf(new Predicate() { // from class: com.android.systemui.privacy.PrivacyConfig$sam$java_util_function_Predicate$0
                            @Override // java.util.function.Predicate
                            public final /* synthetic */ boolean test(Object obj) {
                                return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
                            }
                        });
                    }
                });
                break;
        }
        return Unit.INSTANCE;
    }
}
