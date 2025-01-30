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
import com.samsung.vekit.Item.VideoItem;

/* loaded from: classes6.dex */
public class ItemManager extends Manager<Item> {
    public ItemManager(VEContext context) {
        super(context, ManagerType.ITEM);
        this.TAG = getClass().getSimpleName();
    }

    public Item create(ItemType type, String name) {
        Item item;
        try {
            int uniqueId = generateUniqueId();
            switch (C53841.$SwitchMap$com$samsung$vekit$Common$Type$ItemType[type.ordinal()]) {
                case 1:
                    item = new VideoItem(this.context, uniqueId, name);
                    break;
                case 2:
                    item = new ImageItem(this.context, uniqueId, name);
                    break;
                case 3:
                    item = new DoodleItem(this.context, uniqueId, name);
                    break;
                case 4:
                    item = new CaptionItem(this.context, uniqueId, name);
                    break;
                case 5:
                    item = new AudioItem(this.context, uniqueId, name);
                    break;
                case 6:
                    item = new FragmentAudioItem(this.context, uniqueId, name);
                    break;
                case 7:
                    item = new ColorItem(this.context, uniqueId, name);
                    break;
                case 8:
                    item = new EmptyItem(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(item);
            return item;
        } catch (Exception e) {
            Log.m97e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.ItemManager$1 */
    static /* synthetic */ class C53841 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$ItemType;

        static {
            int[] iArr = new int[ItemType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$ItemType = iArr;
            try {
                iArr[ItemType.VIDEO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.DOODLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.CAPTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.AUDIO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.FRAGMENT_AUDIO.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.COLOR.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$ItemType[ItemType.EMPTY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }
}
