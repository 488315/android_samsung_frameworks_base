package com.android.systemui.keyguard;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shared.customization.data.SensorLocation;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class CustomizationProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public FingerprintPropertyInteractor fingerprintPropertyInteractor;
    public KeyguardQuickAffordanceInteractor interactor;
    public CoroutineDispatcher mainDispatcher;
    public KeyguardRemotePreviewManager previewManager;
    public ShadeModeInteractor shadeModeInteractor;
    public final UriMatcher uriMatcher;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.CustomizationProvider$delete$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String[] $selectionArgs;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Uri uri, String[] strArr, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$selectionArgs = strArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProvider.this.new AnonymousClass1(this.$uri, this.$selectionArgs, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            CustomizationProvider customizationProvider = CustomizationProvider.this;
            Uri uri = this.$uri;
            String[] strArr = this.$selectionArgs;
            this.label = 1;
            Object objAccess$deleteSelection = CustomizationProvider.access$deleteSelection(customizationProvider, uri, strArr, this);
            return objAccess$deleteSelection == coroutineSingletons ? coroutineSingletons : objAccess$deleteSelection;
        }
    }

    /* renamed from: com.android.systemui.keyguard.CustomizationProvider$insert$1, reason: invalid class name and case insensitive filesystem */
    final class C08921 extends SuspendLambda implements Function2 {
        final /* synthetic */ ContentValues $values;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08921(ContentValues contentValues, Continuation continuation) {
            super(2, continuation);
            this.$values = contentValues;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProvider.this.new C08921(this.$values, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08921) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            CustomizationProvider customizationProvider = CustomizationProvider.this;
            ContentValues contentValues = this.$values;
            this.label = 1;
            Object objAccess$insertSelection = CustomizationProvider.access$insertSelection(customizationProvider, contentValues, this);
            return objAccess$insertSelection == coroutineSingletons ? coroutineSingletons : objAccess$insertSelection;
        }
    }

    /* renamed from: com.android.systemui.keyguard.CustomizationProvider$query$1, reason: invalid class name and case insensitive filesystem */
    final class C08931 extends SuspendLambda implements Function2 {
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08931(Uri uri, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProvider.this.new C08931(this.$uri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08931) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x00f5, code lost:
        
            if (r10 == r5) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0103, code lost:
        
            if (r10 == r5) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x0111, code lost:
        
            if (r10 == r5) goto L66;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x011f, code lost:
        
            if (r10 == r5) goto L66;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            SensorLocation sensorLocation;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                    return (Cursor) obj;
                }
                if (i == 2) {
                    ResultKt.throwOnFailure(obj);
                    return (Cursor) obj;
                }
                if (i == 3) {
                    ResultKt.throwOnFailure(obj);
                    return (Cursor) obj;
                }
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return (Cursor) obj;
            }
            ResultKt.throwOnFailure(obj);
            int iMatch = CustomizationProvider.this.uriMatcher.match(this.$uri);
            if (iMatch == 1) {
                CustomizationProvider customizationProvider = CustomizationProvider.this;
                this.label = 2;
                obj = CustomizationProvider.access$querySlots(customizationProvider, this);
            } else if (iMatch == 2) {
                CustomizationProvider customizationProvider2 = CustomizationProvider.this;
                this.label = 1;
                obj = CustomizationProvider.access$queryAffordances(customizationProvider2, this);
            } else if (iMatch == 3) {
                CustomizationProvider customizationProvider3 = CustomizationProvider.this;
                this.label = 3;
                obj = CustomizationProvider.access$querySelections(customizationProvider3, this);
            } else {
                if (iMatch != 4) {
                    String string = null;
                    if (iMatch != 5) {
                        return null;
                    }
                    CustomizationProvider customizationProvider4 = CustomizationProvider.this;
                    FingerprintPropertyInteractor fingerprintPropertyInteractor = customizationProvider4.fingerprintPropertyInteractor;
                    if (fingerprintPropertyInteractor == null) {
                        fingerprintPropertyInteractor = null;
                    }
                    if (((Boolean) fingerprintPropertyInteractor.isUdfps.$$delegate_0.getValue()).booleanValue()) {
                        FingerprintPropertyInteractor fingerprintPropertyInteractor2 = customizationProvider4.fingerprintPropertyInteractor;
                        if (fingerprintPropertyInteractor2 == null) {
                            fingerprintPropertyInteractor2 = null;
                        }
                        sensorLocation = (SensorLocation) fingerprintPropertyInteractor2.sensorLocation.$$delegate_0.getValue();
                    } else {
                        sensorLocation = null;
                    }
                    MatrixCursor matrixCursor = new MatrixCursor(new String[]{"name", "value"});
                    ShadeModeInteractor shadeModeInteractor = customizationProvider4.shadeModeInteractor;
                    if (shadeModeInteractor == null) {
                        shadeModeInteractor = null;
                    }
                    matrixCursor.addRow(new Object[]{"is_shade_layout_wide", Integer.valueOf(((Boolean) ((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() ? 1 : 0)});
                    if (sensorLocation != null) {
                        float[] fArr = {sensorLocation.naturalCenterX, sensorLocation.naturalCenterY, sensorLocation.naturalRadius, sensorLocation.scale};
                        StringBuilder sb = new StringBuilder();
                        sb.append((CharSequence) "");
                        int i2 = 0;
                        for (int i3 = 0; i3 < 4; i3++) {
                            float f = fArr[i3];
                            i2++;
                            if (i2 > 1) {
                                sb.append((CharSequence) ",");
                            }
                            sb.append((CharSequence) String.valueOf(f));
                        }
                        sb.append((CharSequence) "");
                        string = sb.toString();
                    }
                    matrixCursor.addRow(new String[]{"udfps_location", string});
                    return matrixCursor;
                }
                CustomizationProvider customizationProvider5 = CustomizationProvider.this;
                this.label = 4;
                obj = CustomizationProvider.access$queryFlags(customizationProvider5, this);
            }
            return coroutineSingletons;
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$deleteSelection(CustomizationProvider customizationProvider, Uri uri, String[] strArr, ContinuationImpl continuationImpl) throws Throwable {
        CustomizationProvider$deleteSelection$1 customizationProvider$deleteSelection$1;
        Pair pair;
        CustomizationProvider customizationProvider2;
        String str;
        Uri uri2;
        String str2;
        ContentResolver contentResolver;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$deleteSelection$1) {
            customizationProvider$deleteSelection$1 = (CustomizationProvider$deleteSelection$1) continuationImpl;
            int i = customizationProvider$deleteSelection$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$deleteSelection$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$deleteSelection$1 = new CustomizationProvider$deleteSelection$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$deleteSelection$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$deleteSelection$1.label;
        int i3 = 0;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (strArr == null) {
                throw new IllegalArgumentException("Cannot delete selection, selection arguments not included!");
            }
            int length = strArr.length;
            if (length == 1) {
                pair = new Pair(strArr[0], null);
            } else {
                if (length != 2) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(strArr.length, "Cannot delete selection, selection arguments has wrong size, expected to have 1 or 2 arguments, had ", " instead!"));
                }
                pair = new Pair(strArr[0], strArr[1]);
            }
            String str3 = (String) pair.component1();
            String str4 = (String) pair.component2();
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$deleteSelection$1.L$0 = customizationProvider;
            customizationProvider$deleteSelection$1.L$1 = uri;
            customizationProvider$deleteSelection$1.L$2 = str3;
            customizationProvider$deleteSelection$1.L$3 = str4;
            customizationProvider$deleteSelection$1.label = 1;
            Object objUnselect = keyguardQuickAffordanceInteractor.unselect(str3, str4, customizationProvider$deleteSelection$1);
            if (objUnselect == coroutineSingletons) {
                return coroutineSingletons;
            }
            customizationProvider2 = customizationProvider;
            str = str4;
            obj = objUnselect;
            uri2 = uri;
            str2 = str3;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) customizationProvider$deleteSelection$1.L$3;
            str2 = (String) customizationProvider$deleteSelection$1.L$2;
            uri2 = (Uri) customizationProvider$deleteSelection$1.L$1;
            customizationProvider2 = (CustomizationProvider) customizationProvider$deleteSelection$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (((Boolean) obj).booleanValue()) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("Successfully unselected ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
            Context context = customizationProvider2.getContext();
            if (context != null && (contentResolver = context.getContentResolver()) != null) {
                contentResolver.notifyChange(uri2, null);
            }
            i3 = 1;
        } else {
            MediaSessions$H$$ExternalSyntheticOutline0.m("Failed to unselect ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
        }
        return new Integer(i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$insertSelection(CustomizationProvider customizationProvider, ContentValues contentValues, ContinuationImpl continuationImpl) throws Throwable {
        CustomizationProvider$insertSelection$1 customizationProvider$insertSelection$1;
        CustomizationProvider customizationProvider2;
        String str;
        String str2;
        ContentResolver contentResolver;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$insertSelection$1) {
            customizationProvider$insertSelection$1 = (CustomizationProvider$insertSelection$1) continuationImpl;
            int i = customizationProvider$insertSelection$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$insertSelection$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$insertSelection$1 = new CustomizationProvider$insertSelection$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$insertSelection$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$insertSelection$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (contentValues == null) {
                throw new IllegalArgumentException("Cannot insert selection, no values passed in!");
            }
            if (!contentValues.containsKey("slot_id")) {
                throw new IllegalArgumentException("Cannot insert selection, \"slot_id\" not specified!");
            }
            if (!contentValues.containsKey("affordance_id")) {
                throw new IllegalArgumentException("Cannot insert selection, \"affordance_id\" not specified!");
            }
            String asString = contentValues.getAsString("slot_id");
            String asString2 = contentValues.getAsString("affordance_id");
            if (asString == null || asString.length() == 0) {
                throw new IllegalArgumentException("Cannot insert selection, slot ID was empty!");
            }
            if (asString2 == null || asString2.length() == 0) {
                throw new IllegalArgumentException("Cannot insert selection, affordance ID was empty!");
            }
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$insertSelection$1.L$0 = customizationProvider;
            customizationProvider$insertSelection$1.L$1 = asString;
            customizationProvider$insertSelection$1.L$2 = asString2;
            customizationProvider$insertSelection$1.label = 1;
            Object objSelect = keyguardQuickAffordanceInteractor.select(asString, asString2, customizationProvider$insertSelection$1);
            if (objSelect == coroutineSingletons) {
                return coroutineSingletons;
            }
            customizationProvider2 = customizationProvider;
            str = asString2;
            str2 = asString;
            obj = objSelect;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) customizationProvider$insertSelection$1.L$2;
            str2 = (String) customizationProvider$insertSelection$1.L$1;
            customizationProvider2 = (CustomizationProvider) customizationProvider$insertSelection$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (!((Boolean) obj).booleanValue()) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("Failed to select ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
            return null;
        }
        MediaSessions$H$$ExternalSyntheticOutline0.m("Successfully selected ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
        Context context = customizationProvider2.getContext();
        if (context != null && (contentResolver = context.getContentResolver()) != null) {
            CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
            contentResolver.notifyChange(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null);
        }
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        return CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$queryAffordances(CustomizationProvider customizationProvider, ContinuationImpl continuationImpl) {
        CustomizationProvider$queryAffordances$1 customizationProvider$queryAffordances$1;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$queryAffordances$1) {
            customizationProvider$queryAffordances$1 = (CustomizationProvider$queryAffordances$1) continuationImpl;
            int i = customizationProvider$queryAffordances$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$queryAffordances$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$queryAffordances$1 = new CustomizationProvider$queryAffordances$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$queryAffordances$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$queryAffordances$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"id", "name", "icon", "is_enabled", "enablement_explanation", "enablement_action_text", "enablement_action_intent", "configure_intent"});
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$queryAffordances$1.L$0 = matrixCursor3;
            customizationProvider$queryAffordances$1.L$1 = matrixCursor3;
            customizationProvider$queryAffordances$1.label = 1;
            Object affordancePickerRepresentations = ((KeyguardQuickAffordanceRepository) keyguardQuickAffordanceInteractor.repository.get()).getAffordancePickerRepresentations(customizationProvider$queryAffordances$1);
            if (affordancePickerRepresentations == coroutineSingletons) {
                return coroutineSingletons;
            }
            matrixCursor = matrixCursor3;
            obj = affordancePickerRepresentations;
            matrixCursor2 = matrixCursor;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            matrixCursor2 = (MatrixCursor) customizationProvider$queryAffordances$1.L$1;
            matrixCursor = (MatrixCursor) customizationProvider$queryAffordances$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (Iterable) obj) {
            String str = keyguardQuickAffordancePickerRepresentation.id;
            Integer num = new Integer(keyguardQuickAffordancePickerRepresentation.iconResourceId);
            Integer num2 = new Integer(keyguardQuickAffordancePickerRepresentation.isEnabled ? 1 : 0);
            Intent intent = keyguardQuickAffordancePickerRepresentation.actionIntent;
            String uri = intent != null ? intent.toUri(1) : null;
            Intent intent2 = keyguardQuickAffordancePickerRepresentation.configureIntent;
            matrixCursor2.addRow(new Object[]{str, keyguardQuickAffordancePickerRepresentation.name, num, num2, keyguardQuickAffordancePickerRepresentation.explanation, keyguardQuickAffordancePickerRepresentation.actionText, uri, intent2 != null ? intent2.toUri(1) : null});
        }
        return matrixCursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$queryFlags(CustomizationProvider customizationProvider, ContinuationImpl continuationImpl) throws Throwable {
        CustomizationProvider$queryFlags$1 customizationProvider$queryFlags$1;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$queryFlags$1) {
            customizationProvider$queryFlags$1 = (CustomizationProvider$queryFlags$1) continuationImpl;
            int i = customizationProvider$queryFlags$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$queryFlags$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$queryFlags$1 = new CustomizationProvider$queryFlags$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$queryFlags$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$queryFlags$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"name", "value"});
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$queryFlags$1.L$0 = matrixCursor3;
            customizationProvider$queryFlags$1.L$1 = matrixCursor3;
            customizationProvider$queryFlags$1.label = 1;
            Object pickerFlags = keyguardQuickAffordanceInteractor.getPickerFlags(customizationProvider$queryFlags$1);
            if (pickerFlags == coroutineSingletons) {
                return coroutineSingletons;
            }
            matrixCursor = matrixCursor3;
            obj = pickerFlags;
            matrixCursor2 = matrixCursor;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            matrixCursor2 = (MatrixCursor) customizationProvider$queryFlags$1.L$1;
            matrixCursor = (MatrixCursor) customizationProvider$queryFlags$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        for (KeyguardPickerFlag keyguardPickerFlag : (Iterable) obj) {
            matrixCursor2.addRow(new Object[]{keyguardPickerFlag.name, new Integer(keyguardPickerFlag.value ? 1 : 0)});
        }
        return matrixCursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$querySelections(CustomizationProvider customizationProvider, ContinuationImpl continuationImpl) throws Throwable {
        CustomizationProvider$querySelections$1 customizationProvider$querySelections$1;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$querySelections$1) {
            customizationProvider$querySelections$1 = (CustomizationProvider$querySelections$1) continuationImpl;
            int i = customizationProvider$querySelections$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$querySelections$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$querySelections$1 = new CustomizationProvider$querySelections$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$querySelections$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$querySelections$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"slot_id", "affordance_id", "affordance_name"});
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$querySelections$1.L$0 = matrixCursor3;
            customizationProvider$querySelections$1.L$1 = matrixCursor3;
            customizationProvider$querySelections$1.label = 1;
            Object selections = keyguardQuickAffordanceInteractor.getSelections(customizationProvider$querySelections$1);
            if (selections == coroutineSingletons) {
                return coroutineSingletons;
            }
            matrixCursor = matrixCursor3;
            obj = selections;
            matrixCursor2 = matrixCursor;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            matrixCursor2 = (MatrixCursor) customizationProvider$querySelections$1.L$1;
            matrixCursor = (MatrixCursor) customizationProvider$querySelections$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        for (Map.Entry entry : ((Map) obj).entrySet()) {
            String str = (String) entry.getKey();
            for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (List) entry.getValue()) {
                matrixCursor2.addRow(new String[]{str, keyguardQuickAffordancePickerRepresentation.id, keyguardQuickAffordancePickerRepresentation.name});
            }
        }
        return matrixCursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$querySlots(CustomizationProvider customizationProvider, ContinuationImpl continuationImpl) throws Throwable {
        CustomizationProvider$querySlots$1 customizationProvider$querySlots$1;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        customizationProvider.getClass();
        if (continuationImpl instanceof CustomizationProvider$querySlots$1) {
            customizationProvider$querySlots$1 = (CustomizationProvider$querySlots$1) continuationImpl;
            int i = customizationProvider$querySlots$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                customizationProvider$querySlots$1.label = i - Integer.MIN_VALUE;
            } else {
                customizationProvider$querySlots$1 = new CustomizationProvider$querySlots$1(customizationProvider, continuationImpl);
            }
        }
        Object obj = customizationProvider$querySlots$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = customizationProvider$querySlots$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"id", "capacity"});
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = customizationProvider.interactor;
            if (keyguardQuickAffordanceInteractor == null) {
                keyguardQuickAffordanceInteractor = null;
            }
            customizationProvider$querySlots$1.L$0 = matrixCursor3;
            customizationProvider$querySlots$1.L$1 = matrixCursor3;
            customizationProvider$querySlots$1.label = 1;
            Object slotPickerRepresentations = keyguardQuickAffordanceInteractor.getSlotPickerRepresentations(customizationProvider$querySlots$1);
            if (slotPickerRepresentations == coroutineSingletons) {
                return coroutineSingletons;
            }
            matrixCursor = matrixCursor3;
            obj = slotPickerRepresentations;
            matrixCursor2 = matrixCursor;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            matrixCursor2 = (MatrixCursor) customizationProvider$querySlots$1.L$1;
            matrixCursor = (MatrixCursor) customizationProvider$querySlots$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        for (KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation : (Iterable) obj) {
            matrixCursor2.addRow(new Object[]{keyguardSlotPickerRepresentation.id, new Integer(keyguardSlotPickerRepresentation.maxSelectedAffordances)});
        }
        return matrixCursor;
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
        return ((Number) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#delete", coroutineDispatcher, new AnonymousClass1(uri, strArr, null))).intValue();
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        String strConcat;
        int iMatch = this.uriMatcher.match(uri);
        String str = (iMatch == 1 || iMatch == 2 || iMatch == 3 || iMatch == 4 || iMatch == 5) ? "vnd.android.cursor.dir/vnd." : null;
        int iMatch2 = this.uriMatcher.match(uri);
        if (iMatch2 == 1) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            strConcat = "lockscreen_quickaffordance/".concat("slots");
        } else if (iMatch2 == 2) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            strConcat = "lockscreen_quickaffordance/".concat("affordances");
        } else if (iMatch2 != 3) {
            strConcat = iMatch2 != 4 ? iMatch2 != 5 ? null : "runtime_values" : "flags";
        } else {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            strConcat = "lockscreen_quickaffordance/".concat("selections");
        }
        if (str == null || strConcat == null) {
            return null;
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "com.android.systemui.customization.", strConcat);
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
        return (Uri) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#insert", coroutineDispatcher, new C08921(contentValues, null));
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
        return (Cursor) CoroutineTracingKt.runBlockingTraced("KeyguardQuickAffordanceProvider#query", coroutineDispatcher, new C08931(uri, null));
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
