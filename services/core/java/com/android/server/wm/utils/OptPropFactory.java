package com.android.server.wm.utils;

import android.content.pm.PackageManager;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OptPropFactory {
    public final PackageManager mPackageManager;
    public final String mPackageName;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OptProp {
        public final BooleanSupplier mCondition;
        public final String mPropertyName;
        public int mValue = -1;
        public final OptPropFactory$$ExternalSyntheticLambda0 mValueSupplier;

        public OptProp(
                OptPropFactory$$ExternalSyntheticLambda0 optPropFactory$$ExternalSyntheticLambda0,
                String str,
                BooleanSupplier booleanSupplier) {
            this.mValueSupplier = optPropFactory$$ExternalSyntheticLambda0;
            this.mPropertyName = str;
            this.mCondition = booleanSupplier;
        }

        public final int getValue() {
            boolean z;
            if (this.mValue == -1) {
                try {
                    OptPropFactory$$ExternalSyntheticLambda0
                            optPropFactory$$ExternalSyntheticLambda0 = this.mValueSupplier;
                    switch (optPropFactory$$ExternalSyntheticLambda0.$r8$classId) {
                        case 0:
                            OptPropFactory optPropFactory =
                                    optPropFactory$$ExternalSyntheticLambda0.f$0;
                            z =
                                    optPropFactory
                                            .mPackageManager
                                            .getProperty(
                                                    optPropFactory$$ExternalSyntheticLambda0.f$1,
                                                    optPropFactory.mPackageName)
                                            .getBoolean();
                            break;
                        default:
                            OptPropFactory optPropFactory2 =
                                    optPropFactory$$ExternalSyntheticLambda0.f$0;
                            z =
                                    optPropFactory2
                                            .mPackageManager
                                            .getProperty(
                                                    optPropFactory$$ExternalSyntheticLambda0.f$1,
                                                    optPropFactory2.mPackageName)
                                            .getBoolean();
                            break;
                    }
                    Boolean valueOf = Boolean.valueOf(z);
                    if (Boolean.TRUE.equals(valueOf)) {
                        this.mValue = 1;
                    } else if (Boolean.FALSE.equals(valueOf)) {
                        this.mValue = 0;
                    } else {
                        this.mValue = -2;
                    }
                } catch (Exception unused) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Cannot read opt property "),
                            this.mPropertyName,
                            "OptProp");
                    this.mValue = -2;
                }
            }
            return this.mValue;
        }

        public final boolean isFalse() {
            return this.mCondition.getAsBoolean() && getValue() == 0;
        }

        public final boolean shouldEnableWithOptInOverrideAndOptOutProperty(boolean z) {
            return this.mCondition.getAsBoolean() && getValue() != 0 && z;
        }

        public final boolean shouldEnableWithOverrideAndProperty(boolean z) {
            if (this.mCondition.getAsBoolean() && getValue() != 0) {
                return getValue() == 1 || z;
            }
            return false;
        }
    }

    public OptPropFactory(PackageManager packageManager, String str) {
        this.mPackageManager = packageManager;
        this.mPackageName = str;
    }

    public final OptProp create(String str) {
        return new OptProp(
                new OptPropFactory$$ExternalSyntheticLambda0(this, str, 0),
                str,
                new OptPropFactory$OptProp$$ExternalSyntheticLambda0());
    }

    public final OptProp create(String str, BooleanSupplier booleanSupplier) {
        return new OptProp(
                new OptPropFactory$$ExternalSyntheticLambda0(this, str, 1), str, booleanSupplier);
    }
}
