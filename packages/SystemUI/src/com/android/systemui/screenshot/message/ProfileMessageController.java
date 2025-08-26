package com.android.systemui.screenshot.message;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.screenshot.data.model.ProfileType;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepositoryImpl;
import com.android.systemui.screenshot.message.ProfileFirstRunSettingsImpl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ProfileMessageController {
    public final ProfileFirstRunFileResources fileResources;
    public final ProfileFirstRunSettings firstRunSettings;
    public final PackageLabelIconProvider packageLabelIconProvider;
    public final ProfileTypeRepository profileTypes;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class FirstRunProfile {
        public static final /* synthetic */ FirstRunProfile[] $VALUES;
        public static final FirstRunProfile PRIVATE;
        public static final FirstRunProfile WORK;

        static {
            FirstRunProfile firstRunProfile = new FirstRunProfile("WORK", 0);
            WORK = firstRunProfile;
            FirstRunProfile firstRunProfile2 = new FirstRunProfile("PRIVATE", 1);
            PRIVATE = firstRunProfile2;
            FirstRunProfile[] firstRunProfileArr = {firstRunProfile, firstRunProfile2};
            $VALUES = firstRunProfileArr;
            EnumEntriesKt.enumEntries(firstRunProfileArr);
        }

        private FirstRunProfile(String str, int i) {
        }

        public static FirstRunProfile valueOf(String str) {
            return (FirstRunProfile) Enum.valueOf(FirstRunProfile.class, str);
        }

        public static FirstRunProfile[] values() {
            return (FirstRunProfile[]) $VALUES.clone();
        }
    }

    public final class ProfileFirstRunData {
        public final LabeledIcon labeledIcon;
        public final FirstRunProfile profileType;

        public ProfileFirstRunData(LabeledIcon labeledIcon, FirstRunProfile firstRunProfile) {
            this.labeledIcon = labeledIcon;
            this.profileType = firstRunProfile;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ProfileFirstRunData)) {
                return false;
            }
            ProfileFirstRunData profileFirstRunData = (ProfileFirstRunData) obj;
            return Intrinsics.areEqual(this.labeledIcon, profileFirstRunData.labeledIcon) && this.profileType == profileFirstRunData.profileType;
        }

        public final int hashCode() {
            return this.profileType.hashCode() + (this.labeledIcon.hashCode() * 31);
        }

        public final String toString() {
            return "ProfileFirstRunData(labeledIcon=" + this.labeledIcon + ", profileType=" + this.profileType + ")";
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProfileType.values().length];
            try {
                iArr[ProfileType.WORK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProfileType.PRIVATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FirstRunProfile.values().length];
            try {
                iArr2[FirstRunProfile.WORK.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[FirstRunProfile.PRIVATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* renamed from: com.android.systemui.screenshot.message.ProfileMessageController$onScreenshotTaken$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ProfileMessageController.this.onScreenshotTaken(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public ProfileMessageController(PackageLabelIconProvider packageLabelIconProvider, ProfileFirstRunFileResources profileFirstRunFileResources, ProfileFirstRunSettings profileFirstRunSettings, ProfileTypeRepository profileTypeRepository) {
        this.packageLabelIconProvider = packageLabelIconProvider;
        this.fileResources = profileFirstRunFileResources;
        this.firstRunSettings = profileFirstRunSettings;
        this.profileTypes = profileTypeRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onScreenshotTaken(UserHandle userHandle, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        FirstRunProfile firstRunProfile;
        String str;
        ProfileMessageController profileMessageController;
        FirstRunProfile firstRunProfile2;
        Throwable th;
        ComponentName componentNameUnflattenFromString;
        Object failure;
        LabeledIcon labeledIcon;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object profileType = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(profileType);
            if (userHandle != null) {
                int identifier = userHandle.getIdentifier();
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = userHandle;
                anonymousClass1.label = 1;
                profileType = ((ProfileTypeRepositoryImpl) this.profileTypes).getProfileType(identifier, anonymousClass1);
                if (profileType != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            return null;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            firstRunProfile2 = (FirstRunProfile) anonymousClass1.L$1;
            profileMessageController = (ProfileMessageController) anonymousClass1.L$0;
            try {
                ResultKt.throwOnFailure(profileType);
                failure = (LabeledIcon) profileType;
                int i3 = Result.$r8$clinit;
            } catch (Throwable th2) {
                th = th2;
                int i4 = Result.$r8$clinit;
                failure = new Result.Failure(th);
                if (failure instanceof Result.Failure) {
                }
                labeledIcon = (LabeledIcon) failure;
                if (labeledIcon == null) {
                }
                return new ProfileFirstRunData(labeledIcon, firstRunProfile2);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            labeledIcon = (LabeledIcon) failure;
            if (labeledIcon == null) {
                labeledIcon = new LabeledIcon(((ProfileFirstRunFileResourcesImpl) profileMessageController.fileResources).context.getString(R.string.screenshot_default_files_app_name), null);
            }
            return new ProfileFirstRunData(labeledIcon, firstRunProfile2);
        }
        userHandle = (UserHandle) anonymousClass1.L$1;
        this = (ProfileMessageController) anonymousClass1.L$0;
        ResultKt.throwOnFailure(profileType);
        int i5 = WhenMappings.$EnumSwitchMapping$0[((ProfileType) profileType).ordinal()];
        if (i5 != 1) {
            if (i5 == 2) {
                firstRunProfile = FirstRunProfile.PRIVATE;
            }
            return null;
        }
        firstRunProfile = FirstRunProfile.WORK;
        ProfileFirstRunSettingsImpl profileFirstRunSettingsImpl = (ProfileFirstRunSettingsImpl) this.firstRunSettings;
        profileFirstRunSettingsImpl.getClass();
        int i6 = ProfileFirstRunSettingsImpl.WhenMappings.$EnumSwitchMapping$0[firstRunProfile.ordinal()];
        if (i6 == 1) {
            str = "work_profile_first_run";
        } else {
            if (i6 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            str = "private_profile_first_run";
        }
        if (!profileFirstRunSettingsImpl.context.getSharedPreferences("com.android.systemui.screenshot", 0).getBoolean(str, false)) {
            try {
                int i7 = Result.$r8$clinit;
                componentNameUnflattenFromString = ComponentName.unflattenFromString(((ProfileFirstRunFileResourcesImpl) this.fileResources).context.getString(R.string.config_screenshotFilesApp));
            } catch (Throwable th3) {
                profileMessageController = this;
                firstRunProfile2 = firstRunProfile;
                th = th3;
                int i42 = Result.$r8$clinit;
                failure = new Result.Failure(th);
                if (failure instanceof Result.Failure) {
                }
                labeledIcon = (LabeledIcon) failure;
                if (labeledIcon == null) {
                }
                return new ProfileFirstRunData(labeledIcon, firstRunProfile2);
            }
            if (componentNameUnflattenFromString == null) {
                profileMessageController = this;
                firstRunProfile2 = firstRunProfile;
                failure = null;
                int i32 = Result.$r8$clinit;
                if (failure instanceof Result.Failure) {
                }
                labeledIcon = (LabeledIcon) failure;
                if (labeledIcon == null) {
                }
                return new ProfileFirstRunData(labeledIcon, firstRunProfile2);
            }
            PackageLabelIconProvider packageLabelIconProvider = this.packageLabelIconProvider;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = firstRunProfile;
            anonymousClass1.label = 2;
            PackageLabelIconProviderImpl packageLabelIconProviderImpl = (PackageLabelIconProviderImpl) packageLabelIconProvider;
            LabeledIcon labeledIcon2 = new LabeledIcon(packageLabelIconProviderImpl.packageManager.getActivityInfo(componentNameUnflattenFromString, PackageManager.ComponentInfoFlags.of(0L)).loadLabel(packageLabelIconProviderImpl.packageManager), packageLabelIconProviderImpl.packageManager.getUserBadgedIcon(packageLabelIconProviderImpl.packageManager.getActivityIcon(componentNameUnflattenFromString), userHandle));
            if (labeledIcon2 != coroutineSingletons) {
                profileMessageController = this;
                firstRunProfile2 = firstRunProfile;
                profileType = labeledIcon2;
                failure = (LabeledIcon) profileType;
                int i322 = Result.$r8$clinit;
                if (failure instanceof Result.Failure) {
                }
                labeledIcon = (LabeledIcon) failure;
                if (labeledIcon == null) {
                }
                return new ProfileFirstRunData(labeledIcon, firstRunProfile2);
            }
            return coroutineSingletons;
        }
        return null;
    }
}
