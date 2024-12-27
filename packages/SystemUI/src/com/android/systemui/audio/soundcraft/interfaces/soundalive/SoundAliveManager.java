package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundAliveManager {
    public static final String TAG;
    public final Context context;
    public final Uri uri = Uri.parse("content://com.sec.android.app.soundalive.compatibility.SAContentProvider");

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SoundAliveEffectEnum.values().length];
            try {
                iArr[SoundAliveEffectEnum.DOLBY_INDEX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SoundAliveEffectEnum.EQ_INDEX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SoundAliveEffectEnum.VOICE_BOOST_EFFECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SoundAliveEffectEnum.VOLUME_NORMALIZATION_EFFECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SoundAliveEffectEnum.UHQ_UPSCALER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[SoundAliveEffectEnum.SPATIAL_AUDIO.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        TAG = "SoundCraft.SoundAliveManager";
    }

    public SoundAliveManager(Context context) {
        this.context = context;
    }

    public final void setState(int i, String str) {
        Object failure;
        String str2 = TAG;
        Log.i(str2, "setState : key=" + str + ", state=" + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, Integer.valueOf(i));
        try {
            int i2 = Result.$r8$clinit;
            failure = this.context.getContentResolver().insert(this.uri, contentValues);
        } catch (Throwable th) {
            int i3 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (Result.m2527exceptionOrNullimpl(failure) != null) {
            Log.e(str2, "Exception occurs, can't execute requested insert");
        }
    }
}
