package com.android.systemui.screenshot;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.android.systemui.R;
import com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda8;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.function.Consumer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ActionIntentCreator {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final PackageManager packageManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.screenshot.ActionIntentCreator$createEdit$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Intent> $consumer;
        final /* synthetic */ Uri $rawUri;
        Object L$0;
        int label;
        final /* synthetic */ ActionIntentCreator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Consumer<Intent> consumer, ActionIntentCreator actionIntentCreator, Uri uri, Continuation continuation) {
            super(2, continuation);
            this.$consumer = consumer;
            this.this$0 = actionIntentCreator;
            this.$rawUri = uri;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$consumer, this.this$0, this.$rawUri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Consumer consumer;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Consumer<Intent> consumer2 = this.$consumer;
                ActionIntentCreator actionIntentCreator = this.this$0;
                Uri uri = this.$rawUri;
                this.L$0 = consumer2;
                this.label = 1;
                Object objCreateEdit = actionIntentCreator.createEdit(uri, this);
                if (objCreateEdit == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objCreateEdit;
                consumer = consumer2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                consumer = (Consumer) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            consumer.accept(obj);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.screenshot.ActionIntentCreator$createEdit$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ActionIntentCreator.this.createEdit((Uri) null, this);
        }
    }

    static {
        new Companion(null);
    }

    public ActionIntentCreator(Context context, PackageManager packageManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.packageManager = packageManager;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public static Intent createShare(Uri uri, String str, String str2) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uriWithoutUserId, "image/png");
        intent.putExtra("android.intent.extra.STREAM", uriWithoutUserId);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uriWithoutUserId)));
        if (str != null) {
            intent.putExtra("android.intent.extra.SUBJECT", str);
        }
        if (str2 != null) {
            intent.putExtra("android.intent.extra.TEXT", str2);
        }
        intent.addFlags(1);
        intent.addFlags(2);
        return Intent.createChooser(intent, null).addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID).addFlags(268435456).addFlags(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object createEdit(Uri uri, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        Uri uriWithoutUserId;
        Intent intent;
        Object failure;
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i = anonymousClass2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = new AnonymousClass2(continuationImpl);
            }
        }
        Object obj = anonymousClass2.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass2.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
            intent = new Intent("android.intent.action.EDIT");
            String string = this.context.getString(R.string.config_screenshotEditor);
            if (string.length() > 0) {
                intent.setComponent(ComponentName.unflattenFromString(string));
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Intent intent2 = (Intent) anonymousClass2.L$3;
            intent = (Intent) anonymousClass2.L$2;
            uriWithoutUserId = (Uri) anonymousClass2.L$1;
            ActionIntentCreator actionIntentCreator = (ActionIntentCreator) anonymousClass2.L$0;
            ResultKt.throwOnFailure(obj);
            ComponentName componentName = (ComponentName) obj;
            if (componentName == null) {
                actionIntentCreator.getClass();
                try {
                    int i3 = Result.$r8$clinit;
                    failure = ComponentName.unflattenFromString(actionIntentCreator.context.getString(R.string.config_screenshotEditor));
                } catch (Throwable th) {
                    int i4 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                componentName = (ComponentName) failure;
            }
            intent2.setComponent(componentName);
        }
        return intent.setDataAndType(uriWithoutUserId, "image/png").putExtra("edit_source", "screenshot").addFlags(1).addFlags(2).addFlags(268435456).addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
    }

    public final void createEdit(Uri uri, LongScreenshotActivity$$ExternalSyntheticLambda8 longScreenshotActivity$$ExternalSyntheticLambda8) {
        BuildersKt.launch$default(this.applicationScope, null, null, new AnonymousClass1(longScreenshotActivity$$ExternalSyntheticLambda8, this, uri, null), 3);
    }
}
