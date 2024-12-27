package com.android.server.pm;

import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.function.Consumer;

public final /* synthetic */
class PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(
            int i, int i2, String str) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = i;
    }

    public /* synthetic */ PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(
            int i, CharSequence charSequence) {
        this.$r8$classId = 3;
        this.f$1 = i;
        this.f$0 = charSequence;
    }

    public /* synthetic */ PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2(
            int i, String str) {
        this.$r8$classId = 2;
        this.f$1 = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((PackageStateMutator.StateWriteWrapper) obj)
                        .setInstaller(this.f$1, (String) this.f$0);
                break;
            case 1:
                ((PackageStateMutator.StateWriteWrapper) obj)
                        .setInstaller(this.f$1, (String) this.f$0);
                break;
            case 2:
                int i = this.f$1;
                String str = (String) this.f$0;
                PackageUserStateImpl packageUserStateImpl =
                        ((PackageStateMutator.StateWriteWrapper) obj).userState(i).mUserState;
                if (packageUserStateImpl != null) {
                    packageUserStateImpl.mSplashScreenTheme = str;
                    packageUserStateImpl.onChanged$4();
                    break;
                }
                break;
            default:
                int i2 = this.f$1;
                CharSequence charSequence = (CharSequence) this.f$0;
                PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper userState =
                        ((PackageStateMutator.StateWriteWrapper) obj).userState(i2);
                String charSequence2 = charSequence == null ? null : charSequence.toString();
                PackageUserStateImpl packageUserStateImpl2 = userState.mUserState;
                if (packageUserStateImpl2 != null) {
                    packageUserStateImpl2.mHarmfulAppWarning = charSequence2;
                    packageUserStateImpl2.onChanged$4();
                    break;
                }
                break;
        }
    }
}
