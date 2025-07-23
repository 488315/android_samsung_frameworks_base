package com.android.systemui.keyguard;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomizationProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public FingerprintPropertyInteractor fingerprintPropertyInteractor;
    public KeyguardQuickAffordanceInteractor interactor;
    public CoroutineDispatcher mainDispatcher;
    public KeyguardRemotePreviewManager previewManager;
    public ShadeModeInteractor shadeModeInteractor;
    public final UriMatcher uriMatcher;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomizationProvider() {
        UriMatcher uriMatcher = new UriMatcher(-1);
        CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
        uriMatcher.addURI("com.android.systemui.customization", "lockscreen_quickaffordance/".concat("slots"), 1);
        uriMatcher.addURI("com.android.systemui.customization", "lockscreen_quickaffordance/".concat("affordances"), 2);
        uriMatcher.addURI("com.android.systemui.customization", "lockscreen_quickaffordance/".concat("selections"), 3);
        uriMatcher.addURI("com.android.systemui.customization", "flags", 4);
        uriMatcher.addURI("com.android.systemui.customization", "runtime_values", 5);
        this.uriMatcher = uriMatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$deleteSelection(com.android.systemui.keyguard.CustomizationProvider r7, android.net.Uri r8, java.lang.String[] r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            Method dump skipped, instructions count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$deleteSelection(com.android.systemui.keyguard.CustomizationProvider, android.net.Uri, java.lang.String[], kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$insertSelection(com.android.systemui.keyguard.CustomizationProvider r7, android.content.ContentValues r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$insertSelection(com.android.systemui.keyguard.CustomizationProvider, android.content.ContentValues, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$queryAffordances(com.android.systemui.keyguard.CustomizationProvider r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r13.getClass()
            boolean r0 = r14 instanceof com.android.systemui.keyguard.CustomizationProvider$queryAffordances$1
            if (r0 == 0) goto L16
            r0 = r14
            com.android.systemui.keyguard.CustomizationProvider$queryAffordances$1 r0 = (com.android.systemui.keyguard.CustomizationProvider$queryAffordances$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.CustomizationProvider$queryAffordances$1 r0 = new com.android.systemui.keyguard.CustomizationProvider$queryAffordances$1
            r0.<init>(r13, r14)
        L1b:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3b
            if (r2 != r4) goto L33
            java.lang.Object r13 = r0.L$1
            android.database.MatrixCursor r13 = (android.database.MatrixCursor) r13
            java.lang.Object r0 = r0.L$0
            android.database.MatrixCursor r0 = (android.database.MatrixCursor) r0
            kotlin.ResultKt.throwOnFailure(r14)
            goto L75
        L33:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L3b:
            kotlin.ResultKt.throwOnFailure(r14)
            android.database.MatrixCursor r14 = new android.database.MatrixCursor
            java.lang.String r11 = "enablement_action_intent"
            java.lang.String r12 = "configure_intent"
            java.lang.String r5 = "id"
            java.lang.String r6 = "name"
            java.lang.String r7 = "icon"
            java.lang.String r8 = "is_enabled"
            java.lang.String r9 = "enablement_explanation"
            java.lang.String r10 = "enablement_action_text"
            java.lang.String[] r2 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11, r12}
            r14.<init>(r2)
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r13 = r13.interactor
            if (r13 == 0) goto L5c
            goto L5d
        L5c:
            r13 = r3
        L5d:
            r0.L$0 = r14
            r0.L$1 = r14
            r0.label = r4
            dagger.Lazy r13 = r13.repository
            java.lang.Object r13 = r13.get()
            com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository r13 = (com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository) r13
            java.lang.Object r13 = r13.getAffordancePickerRepresentations(r0)
            if (r13 != r1) goto L72
            return r1
        L72:
            r0 = r14
            r14 = r13
            r13 = r0
        L75:
            java.lang.Iterable r14 = (java.lang.Iterable) r14
            java.util.Iterator r14 = r14.iterator()
        L7b:
            boolean r1 = r14.hasNext()
            if (r1 == 0) goto Lbb
            java.lang.Object r1 = r14.next()
            com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation r1 = (com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation) r1
            java.lang.String r5 = r1.id
            java.lang.Integer r7 = new java.lang.Integer
            int r2 = r1.iconResourceId
            r7.<init>(r2)
            java.lang.Integer r8 = new java.lang.Integer
            boolean r2 = r1.isEnabled
            r8.<init>(r2)
            android.content.Intent r2 = r1.actionIntent
            if (r2 == 0) goto La1
            java.lang.String r2 = r2.toUri(r4)
            r11 = r2
            goto La2
        La1:
            r11 = r3
        La2:
            android.content.Intent r2 = r1.configureIntent
            if (r2 == 0) goto Lac
            java.lang.String r2 = r2.toUri(r4)
            r12 = r2
            goto Lad
        Lac:
            r12 = r3
        Lad:
            java.lang.String r10 = r1.actionText
            java.lang.String r6 = r1.name
            java.lang.String r9 = r1.explanation
            java.lang.Object[] r1 = new java.lang.Object[]{r5, r6, r7, r8, r9, r10, r11, r12}
            r13.addRow(r1)
            goto L7b
        Lbb:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$queryAffordances(com.android.systemui.keyguard.CustomizationProvider, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006d A[LOOP:0: B:11:0x0067->B:13:0x006d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$queryFlags(com.android.systemui.keyguard.CustomizationProvider r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5.getClass()
            boolean r0 = r6 instanceof com.android.systemui.keyguard.CustomizationProvider$queryFlags$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.systemui.keyguard.CustomizationProvider$queryFlags$1 r0 = (com.android.systemui.keyguard.CustomizationProvider$queryFlags$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.CustomizationProvider$queryFlags$1 r0 = new com.android.systemui.keyguard.CustomizationProvider$queryFlags$1
            r0.<init>(r5, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r5 = r0.L$1
            android.database.MatrixCursor r5 = (android.database.MatrixCursor) r5
            java.lang.Object r0 = r0.L$0
            android.database.MatrixCursor r0 = (android.database.MatrixCursor) r0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L61
        L32:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            android.database.MatrixCursor r6 = new android.database.MatrixCursor
            java.lang.String r2 = "name"
            java.lang.String r4 = "value"
            java.lang.String[] r2 = new java.lang.String[]{r2, r4}
            r6.<init>(r2)
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r5 = r5.interactor
            if (r5 == 0) goto L50
            goto L51
        L50:
            r5 = 0
        L51:
            r0.L$0 = r6
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r5 = r5.getPickerFlags(r0)
            if (r5 != r1) goto L5e
            return r1
        L5e:
            r0 = r6
            r6 = r5
            r5 = r0
        L61:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Iterator r6 = r6.iterator()
        L67:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L84
            java.lang.Object r1 = r6.next()
            com.android.systemui.keyguard.shared.model.KeyguardPickerFlag r1 = (com.android.systemui.keyguard.shared.model.KeyguardPickerFlag) r1
            java.lang.String r2 = r1.name
            java.lang.Integer r3 = new java.lang.Integer
            boolean r1 = r1.value
            r3.<init>(r1)
            java.lang.Object[] r1 = new java.lang.Object[]{r2, r3}
            r5.addRow(r1)
            goto L67
        L84:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$queryFlags(com.android.systemui.keyguard.CustomizationProvider, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$querySelections(com.android.systemui.keyguard.CustomizationProvider r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6.getClass()
            boolean r0 = r7 instanceof com.android.systemui.keyguard.CustomizationProvider$querySelections$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.keyguard.CustomizationProvider$querySelections$1 r0 = (com.android.systemui.keyguard.CustomizationProvider$querySelections$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.CustomizationProvider$querySelections$1 r0 = new com.android.systemui.keyguard.CustomizationProvider$querySelections$1
            r0.<init>(r6, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r6 = r0.L$1
            android.database.MatrixCursor r6 = (android.database.MatrixCursor) r6
            java.lang.Object r0 = r0.L$0
            android.database.MatrixCursor r0 = (android.database.MatrixCursor) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L63
        L32:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            android.database.MatrixCursor r7 = new android.database.MatrixCursor
            java.lang.String r2 = "slot_id"
            java.lang.String r4 = "affordance_id"
            java.lang.String r5 = "affordance_name"
            java.lang.String[] r2 = new java.lang.String[]{r2, r4, r5}
            r7.<init>(r2)
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r6 = r6.interactor
            if (r6 == 0) goto L52
            goto L53
        L52:
            r6 = 0
        L53:
            r0.L$0 = r7
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r6 = r6.getSelections(r0)
            if (r6 != r1) goto L60
            return r1
        L60:
            r0 = r7
            r7 = r6
            r6 = r0
        L63:
            java.util.Map r7 = (java.util.Map) r7
            java.util.Set r7 = r7.entrySet()
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.Iterator r7 = r7.iterator()
        L6f:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto La5
            java.lang.Object r1 = r7.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getKey()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r1 = r1.getValue()
            java.util.List r1 = (java.util.List) r1
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
        L8d:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L6f
            java.lang.Object r3 = r1.next()
            com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation r3 = (com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation) r3
            java.lang.String r4 = r3.id
            java.lang.String r3 = r3.name
            java.lang.String[] r3 = new java.lang.String[]{r2, r4, r3}
            r6.addRow(r3)
            goto L8d
        La5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$querySelections(com.android.systemui.keyguard.CustomizationProvider, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006c A[LOOP:0: B:11:0x0066->B:13:0x006c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$querySlots(com.android.systemui.keyguard.CustomizationProvider r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5.getClass()
            boolean r0 = r6 instanceof com.android.systemui.keyguard.CustomizationProvider$querySlots$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.systemui.keyguard.CustomizationProvider$querySlots$1 r0 = (com.android.systemui.keyguard.CustomizationProvider$querySlots$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.CustomizationProvider$querySlots$1 r0 = new com.android.systemui.keyguard.CustomizationProvider$querySlots$1
            r0.<init>(r5, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r5 = r0.L$1
            android.database.MatrixCursor r5 = (android.database.MatrixCursor) r5
            java.lang.Object r0 = r0.L$0
            android.database.MatrixCursor r0 = (android.database.MatrixCursor) r0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L60
        L32:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            android.database.MatrixCursor r6 = new android.database.MatrixCursor
            java.lang.String r2 = "id"
            java.lang.String r4 = "capacity"
            java.lang.String[] r2 = new java.lang.String[]{r2, r4}
            r6.<init>(r2)
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r5 = r5.interactor
            if (r5 == 0) goto L4f
            goto L50
        L4f:
            r5 = 0
        L50:
            r0.L$0 = r6
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r5 = r5.getSlotPickerRepresentations(r0)
            if (r5 != r1) goto L5d
            return r1
        L5d:
            r0 = r6
            r6 = r5
            r5 = r0
        L60:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Iterator r6 = r6.iterator()
        L66:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L83
            java.lang.Object r1 = r6.next()
            com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation r1 = (com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation) r1
            java.lang.String r2 = r1.id
            java.lang.Integer r3 = new java.lang.Integer
            int r1 = r1.maxSelectedAffordances
            r3.<init>(r1)
            java.lang.Object[] r1 = new java.lang.Object[]{r2, r3}
            r5.addRow(r1)
            goto L66
        L83:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.CustomizationProvider.access$querySlots(com.android.systemui.keyguard.CustomizationProvider, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.");
        }
        contextAvailableCallback.onContextAvailable(context);
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        if (requireContext().checkPermission("android.permission.BIND_WALLPAPER", Binder.getCallingPid(), Binder.getCallingUid()) != 0) {
            return null;
        }
        KeyguardRemotePreviewManager keyguardRemotePreviewManager = this.previewManager;
        return (keyguardRemotePreviewManager != null ? keyguardRemotePreviewManager : null).preview(bundle);
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        if (this.mainDispatcher == null) {
            return 0;
        }
        if (this.uriMatcher.match(uri) != 3) {
            throw new UnsupportedOperationException();
        }
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return ((Number) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#delete", coroutineDispatcher, new CustomizationProvider$delete$1(this, uri, strArr, null))).intValue();
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        String concat;
        int match = this.uriMatcher.match(uri);
        String str = (match == 1 || match == 2 || match == 3 || match == 4 || match == 5) ? "vnd.android.cursor.dir/vnd." : null;
        int match2 = this.uriMatcher.match(uri);
        if (match2 == 1) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            concat = "lockscreen_quickaffordance/".concat("slots");
        } else if (match2 == 2) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            concat = "lockscreen_quickaffordance/".concat("affordances");
        } else if (match2 != 3) {
            concat = match2 != 4 ? match2 != 5 ? null : "runtime_values" : "flags";
        } else {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            concat = "lockscreen_quickaffordance/".concat("selections");
        }
        if (str == null || concat == null) {
            return null;
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "com.android.systemui.customization.", concat);
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        if (this.mainDispatcher == null) {
            return null;
        }
        if (this.uriMatcher.match(uri) != 3) {
            throw new UnsupportedOperationException();
        }
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return (Uri) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#insert", coroutineDispatcher, new CustomizationProvider$insert$1(this, contentValues, null));
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            return null;
        }
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return (Cursor) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#query", coroutineDispatcher, new CustomizationProvider$query$1(this, uri, null));
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.contextAvailableCallback = contextAvailableCallback;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        android.util.Log.e("KeyguardQuickAffordanceProvider", "Update is not supported!");
        return 0;
    }
}
