package com.android.systemui.screenshot;

import com.android.systemui.screenshot.data.model.ProfileType;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepositoryImpl;
import com.android.systemui.screenshot.resources.Messages;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class AnnouncementResolver {
    public final CoroutineScope mainScope;
    public final Messages messages;
    public final ProfileTypeRepository profileTypes;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProfileType.values().length];
            try {
                iArr[ProfileType.PRIVATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProfileType.WORK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.screenshot.AnnouncementResolver$getScreenshotAnnouncement$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AnnouncementResolver.this.getScreenshotAnnouncement(0, this);
        }
    }

    /* renamed from: com.android.systemui.screenshot.AnnouncementResolver$getScreenshotAnnouncement$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<String> $announceCallback;
        final /* synthetic */ int $userId;
        Object L$0;
        int label;
        final /* synthetic */ AnnouncementResolver this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Consumer<String> consumer, AnnouncementResolver announcementResolver, int i, Continuation continuation) {
            super(2, continuation);
            this.$announceCallback = consumer;
            this.this$0 = announcementResolver;
            this.$userId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$announceCallback, this.this$0, this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Consumer consumer;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Consumer<String> consumer2 = this.$announceCallback;
                AnnouncementResolver announcementResolver = this.this$0;
                int i2 = this.$userId;
                this.L$0 = consumer2;
                this.label = 1;
                Object screenshotAnnouncement = announcementResolver.getScreenshotAnnouncement(i2, this);
                if (screenshotAnnouncement == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = screenshotAnnouncement;
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

    public AnnouncementResolver(Messages messages, ProfileTypeRepository profileTypeRepository, CoroutineScope coroutineScope) {
        this.messages = messages;
        this.profileTypes = profileTypeRepository;
        this.mainScope = coroutineScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getScreenshotAnnouncement(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object profileType = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(profileType);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            profileType = ((ProfileTypeRepositoryImpl) this.profileTypes).getProfileType(i, anonymousClass1);
            if (profileType == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (AnnouncementResolver) anonymousClass1.L$0;
            ResultKt.throwOnFailure(profileType);
        }
        int i4 = WhenMappings.$EnumSwitchMapping$0[((ProfileType) profileType).ordinal()];
        return i4 != 1 ? i4 != 2 ? (String) this.messages.savingScreenshotAnnouncement$delegate.getValue() : (String) this.messages.savingToWorkProfileAnnouncement$delegate.getValue() : (String) this.messages.savingToPrivateProfileAnnouncement$delegate.getValue();
    }
}
