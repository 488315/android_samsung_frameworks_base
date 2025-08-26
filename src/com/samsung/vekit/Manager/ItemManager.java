package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.AudioItem;
import com.samsung.vekit.Item.CaptionItem;
import com.samsung.vekit.Item.ColorItem;
import com.samsung.vekit.Item.DoodleItem;
import com.samsung.vekit.Item.EmptyItem;
import com.samsung.vekit.Item.FragmentAudioItem;
import com.samsung.vekit.Item.ImageItem;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Item.PortraitVideoItem;
import com.samsung.vekit.Item.VideoItem;

/* loaded from: classes6.dex */
public class ItemManager extends Manager<Item> {
    public ItemManager(VEContext vEContext) {
        super(vEContext, ManagerType.ITEM);
        this.TAG = getClass().getSimpleName();
    }

    public Item create(ItemType itemType, String str) {
        Item videoItem;
        try {
            int iGenerateUniqueId = generateUniqueId();
            switch (AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$ItemType[itemType.ordinal()]) {
                case 1:
                    videoItem = new VideoItem(this.context, iGenerateUniqueId, str);
                    break;
                case 2:
                    videoItem = new ImageItem(this.context, iGenerateUniqueId, str);
                    break;
                case 3:
                    videoItem = new DoodleItem(this.context, iGenerateUniqueId, str);
                    break;
                case 4:
                    videoItem = new CaptionItem(this.context, iGenerateUniqueId, str);
                    break;
                case 5:
                    videoItem = new AudioItem(this.context, iGenerateUniqueId, str);
                    break;
                case 6:
                    videoItem = new FragmentAudioItem(this.context, iGenerateUniqueId, str);
                    break;
                case 7:
                    videoItem = new ColorItem(this.context, iGenerateUniqueId, str);
                    break;
                case 8:
                    videoItem = new EmptyItem(this.context, iGenerateUniqueId, str);
                    break;
                case 9:
                    videoItem = new PortraitVideoItem(this.context, iGenerateUniqueId, str);
                    break;
                default:
                    return null;
            }
            add(videoItem);
            return videoItem;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.ItemManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ItemType;

        static {
            int[] iArr = new int[ItemType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ItemType = iArr;
            try {
                iArr[ItemType.VIDEO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.DOODLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.CAPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.AUDIO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.FRAGMENT_AUDIO.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.COLOR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.EMPTY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.PORTRAIT_VIDEO.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }
}
