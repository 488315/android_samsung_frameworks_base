package com.android.systemui.media.mediaoutput.common;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import androidx.core.os.BundleKt;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import com.android.systemui.media.mediaoutput.entity.Configuration;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class ScpmHelper {
    public final Context context;
    public final DataStore dataStore;
    public static final Companion Companion = new Companion(null);
    public static final Uri BASE_URI = Uri.parse("content://com.samsung.android.scpm.policy/");

    /* renamed from: com.android.systemui.media.mediaoutput.common.ScpmHelper$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.common.ScpmHelper$1$1, reason: invalid class name and collision with other inner class name */
        final class C03371 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ ScpmHelper this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03371(ScpmHelper scpmHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = scpmHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03371 c03371 = new C03371(this.this$0, continuation);
                c03371.L$0 = obj;
                return c03371;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03371) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceKeys.INSTANCE.getClass();
                Preferences.Key key = PreferenceKeys.SETTING_VERSION;
                if (mutablePreferences.preferencesMap.containsKey(key)) {
                    return Unit.INSTANCE;
                }
                Integer num = new Integer(Settings.System.getInt(this.this$0.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", -1));
                if (num.intValue() < 0) {
                    num = null;
                }
                if (num == null) {
                    return Unit.INSTANCE;
                }
                int iIntValue = num.intValue();
                Preferences.Key key2 = PreferenceKeys.MIRRORING_PRIORITY;
                if (mutablePreferences.preferencesMap.containsKey(key2)) {
                    Settings.System.putInt(this.this$0.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", iIntValue + 112);
                } else {
                    Preferences.Key key3 = PreferenceKeys.CASTING_PRIORITY;
                    mutablePreferences.checkNotFrozen$datastore_preferences_core();
                    mutablePreferences.preferencesMap.remove(key3);
                    boolean z = (iIntValue & 1) != 1;
                    mutablePreferences.setUnchecked$datastore_preferences_core(key2, Boolean.valueOf(z));
                    Settings.System.putInt(this.this$0.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", z ? 16 : 17);
                }
                mutablePreferences.setUnchecked$datastore_preferences_core(key, new Integer(3));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ScpmHelper.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ScpmHelper scpmHelper = ScpmHelper.this;
                DataStore dataStore = scpmHelper.dataStore;
                C03371 c03371 = new C03371(scpmHelper, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03371, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.common.ScpmHelper$checkConfiguration$1, reason: invalid class name and case insensitive filesystem */
    final class C09301 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C09301(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ScpmHelper.this.checkConfiguration(this);
        }
    }

    public ScpmHelper(Context context, DataStore dataStore) {
        this.context = context;
        this.dataStore = dataStore;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.Default), null, null, new AnonymousClass1(null), 3);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:0|2|(2:4|(1:6)(1:7))(0)|8|(1:(1:(1:(3:13|110|111)(2:14|15))(3:16|48|(2:50|51)(11:52|114|53|57|(1:59)|60|(1:62)|63|(13:118|65|127|66|121|67|(1:69)|128|70|71|72|73|(8:116|75|79|(1:81)|82|(1:84)|85|(3:87|(1:90)|(1:92))))|110|111)))(1:17))(3:18|(0)|94)|21|(3:23|(1:26)|(2:28|29))|30|125|31|35|(1:37)|38|(1:40)|41|(1:43)(1:44)|(3:47|48|(0)(0))|94|(4:(0)|(1:113)|(1:120)|(1:124))) */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ee, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ef, code lost:
    
        r5 = kotlin.Result.$r8$clinit;
        r2 = new kotlin.Result.Failure(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01f4, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit(r2.dataStore, r14, r0) == r1) goto L94;
     */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkConfiguration(ContinuationImpl continuationImpl) throws IOException {
        C09301 c09301;
        ScpmHelper scpmHelper;
        Configuration configuration;
        String str;
        Object failure;
        Object failure2;
        if (continuationImpl instanceof C09301) {
            c09301 = (C09301) continuationImpl;
            int i = c09301.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09301.label = i - Integer.MIN_VALUE;
            } else {
                c09301 = new C09301(continuationImpl);
            }
        }
        Object objFirstOrNull = c09301.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09301.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirstOrNull);
            DataStoreExt.INSTANCE.getClass();
            DataStoreExt$special$$inlined$map$5 dataStoreExt$special$$inlined$map$5 = new DataStoreExt$special$$inlined$map$5(this.dataStore.getData());
            c09301.L$0 = this;
            c09301.label = 1;
            objFirstOrNull = FlowKt.firstOrNull(dataStoreExt$special$$inlined$map$5, c09301);
            if (objFirstOrNull != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirstOrNull);
                return Unit.INSTANCE;
            }
            configuration = (Configuration) c09301.L$1;
            scpmHelper = (ScpmHelper) c09301.L$0;
            ResultKt.throwOnFailure(objFirstOrNull);
            str = (String) objFirstOrNull;
            if (str != null) {
                return Unit.INSTANCE;
            }
            Gson gson = new Gson();
            ContentResolver contentResolver = scpmHelper.context.getContentResolver();
            try {
                int i3 = Result.$r8$clinit;
                Companion.getClass();
                failure = contentResolver.openFileDescriptor(Uri.parse("content://com.samsung.android.scpm.policy/" + str + "/mediaoutput-b72f"), "r");
            } catch (Throwable th) {
                int i4 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
            if (thM3442exceptionOrNullimpl != null) {
                thM3442exceptionOrNullimpl.printStackTrace();
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) failure;
            if (parcelFileDescriptor != null) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, Charsets.UTF_8), 8192);
                        try {
                            StringWriter stringWriter = new StringWriter();
                            char[] cArr = new char[8192];
                            for (int i5 = bufferedReader.read(cArr); i5 >= 0; i5 = bufferedReader.read(cArr)) {
                                stringWriter.write(cArr, 0, i5);
                            }
                            String string = stringWriter.toString();
                            bufferedReader.close();
                            fileInputStream.close();
                            parcelFileDescriptor.close();
                            if (string != null) {
                                try {
                                    int i6 = Result.$r8$clinit;
                                    failure2 = (Configuration) gson.fromJson(string, Configuration.class);
                                } catch (Throwable th2) {
                                    int i7 = Result.$r8$clinit;
                                    failure2 = new Result.Failure(th2);
                                }
                                Throwable thM3442exceptionOrNullimpl2 = Result.m3442exceptionOrNullimpl(failure2);
                                if (thM3442exceptionOrNullimpl2 != null) {
                                    thM3442exceptionOrNullimpl2.printStackTrace();
                                }
                                if (failure2 instanceof Result.Failure) {
                                    failure2 = null;
                                }
                                Configuration configuration2 = (Configuration) failure2;
                                if (configuration2 != null) {
                                    configuration2.updateTime = System.currentTimeMillis();
                                    if (Intrinsics.areEqual(configuration, configuration2)) {
                                        configuration2 = null;
                                    }
                                    if (configuration2 != null) {
                                        Log.d("ScpmHelper", "checkConfiguration() - new Configuration updated.");
                                        ScpmHelper$checkConfiguration$12$1 scpmHelper$checkConfiguration$12$1 = new ScpmHelper$checkConfiguration$12$1(gson, configuration2, null);
                                        c09301.L$0 = configuration2;
                                        c09301.L$1 = null;
                                        c09301.label = 3;
                                    }
                                }
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            }
            return Unit.INSTANCE;
        }
        this = (ScpmHelper) c09301.L$0;
        ResultKt.throwOnFailure(objFirstOrNull);
        Configuration configuration3 = (Configuration) objFirstOrNull;
        if (configuration3 != null) {
            Long l = new Long(System.currentTimeMillis() - configuration3.updateTime);
            if (l.longValue() > 86400000) {
                l = null;
            }
            if (l != null) {
                Log.d("ScpmHelper", "checkConfiguration() - Configuration was recently updated.");
                return Unit.INSTANCE;
            }
        }
        c09301.L$0 = this;
        c09301.L$1 = configuration3;
        c09301.label = 2;
        Bundle bundleBundleOf = BundleKt.bundleOf(new Pair("packageName", this.context.getPackageName()), new Pair("appId", "ifdzefg1lz"), new Pair("version", String.valueOf(Build.VERSION.SEM_PLATFORM_INT)), new Pair("receiverPackageName", this.context.getPackageName()));
        ContentResolver contentResolver2 = this.context.getContentResolver();
        int i8 = Result.$r8$clinit;
        Object failure3 = contentResolver2.call(BASE_URI, "register", this.context.getPackageName(), bundleBundleOf);
        Throwable thM3442exceptionOrNullimpl3 = Result.m3442exceptionOrNullimpl(failure3);
        if (thM3442exceptionOrNullimpl3 != null) {
            thM3442exceptionOrNullimpl3.printStackTrace();
        }
        if (failure3 instanceof Result.Failure) {
            failure3 = null;
        }
        Bundle bundle = (Bundle) failure3;
        String string2 = bundle != null ? bundle.getString("token") : null;
        if (string2 != coroutineSingletons) {
            String str2 = string2;
            scpmHelper = this;
            configuration = configuration3;
            objFirstOrNull = str2;
            str = (String) objFirstOrNull;
            if (str != null) {
            }
        }
        return coroutineSingletons;
    }
}
