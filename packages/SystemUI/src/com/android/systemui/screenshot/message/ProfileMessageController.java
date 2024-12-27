package com.android.systemui.screenshot.message;

import com.android.systemui.screenshot.data.model.ProfileType;
import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ProfileMessageController {
    public final ProfileFirstRunFileResources fileResources;
    public final ProfileFirstRunSettings firstRunSettings;
    public final PackageLabelIconProvider packageLabelIconProvider;
    public final ProfileTypeRepository profileTypes;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    static {
        new Companion(null);
    }

    public ProfileMessageController(PackageLabelIconProvider packageLabelIconProvider, ProfileFirstRunFileResources profileFirstRunFileResources, ProfileFirstRunSettings profileFirstRunSettings, ProfileTypeRepository profileTypeRepository) {
        this.packageLabelIconProvider = packageLabelIconProvider;
        this.fileResources = profileFirstRunFileResources;
        this.firstRunSettings = profileFirstRunSettings;
        this.profileTypes = profileTypeRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onScreenshotTaken(android.os.UserHandle r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.message.ProfileMessageController.onScreenshotTaken(android.os.UserHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
