package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.AnimatedImage;
import com.samsung.vekit.Content.Audio;
import com.samsung.vekit.Content.Caption;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.Doodle;
import com.samsung.vekit.Content.FragmentAudio;
import com.samsung.vekit.Content.Image;
import com.samsung.vekit.Content.PortraitVideo;
import com.samsung.vekit.Content.Video;

/* loaded from: classes6.dex */
public class ContentManager extends Manager<Content> {
    public ContentManager(VEContext vEContext) {
        super(vEContext, ManagerType.CONTENT);
        this.TAG = getClass().getSimpleName();
    }

    public Content create(ContentType contentType, String str) {
        Content audio;
        try {
            int iGenerateUniqueId = generateUniqueId();
            switch (AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$ContentType[contentType.ordinal()]) {
                case 1:
                    audio = new Audio(this.context, iGenerateUniqueId, str);
                    break;
                case 2:
                    audio = new Doodle(this.context, iGenerateUniqueId, str);
                    break;
                case 3:
                    audio = new Image(this.context, iGenerateUniqueId, str);
                    break;
                case 4:
                    audio = new AnimatedImage(this.context, iGenerateUniqueId, str);
                    break;
                case 5:
                    audio = new Video(this.context, iGenerateUniqueId, str);
                    break;
                case 6:
                    audio = new Caption(this.context, iGenerateUniqueId, str);
                    break;
                case 7:
                    audio = new FragmentAudio(this.context, iGenerateUniqueId, str);
                    break;
                case 8:
                    audio = new PortraitVideo(this.context, iGenerateUniqueId, str);
                    break;
                default:
                    return null;
            }
            add(audio);
            return audio;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.ContentManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ContentType;

        static {
            int[] iArr = new int[ContentType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ContentType = iArr;
            try {
                iArr[ContentType.AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.DOODLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.ANIMATED_IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.VIDEO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.CAPTION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.FRAGMENT_AUDIO.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ContentType[ContentType.PORTRAIT_VIDEO.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}
