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

public class ItemManager extends Manager<Item> {
    public ItemManager(VEContext context) {
        super(context, ManagerType.ITEM);
        this.TAG = getClass().getSimpleName();
    }

    public Item create(ItemType type, String name) {
        Item item;
        try {
            int uniqueId = generateUniqueId();
            switch (type) {
                case VIDEO:
                    item = new VideoItem(this.context, uniqueId, name);
                    break;
                case IMAGE:
                    item = new ImageItem(this.context, uniqueId, name);
                    break;
                case DOODLE:
                    item = new DoodleItem(this.context, uniqueId, name);
                    break;
                case CAPTION:
                    item = new CaptionItem(this.context, uniqueId, name);
                    break;
                case AUDIO:
                    item = new AudioItem(this.context, uniqueId, name);
                    break;
                case FRAGMENT_AUDIO:
                    item = new FragmentAudioItem(this.context, uniqueId, name);
                    break;
                case COLOR:
                    item = new ColorItem(this.context, uniqueId, name);
                    break;
                case EMPTY:
                    item = new EmptyItem(this.context, uniqueId, name);
                    break;
                case PORTRAIT_VIDEO:
                    item = new PortraitVideoItem(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(item);
            return item;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }
}
