package com.samsung.android.allshare.media;

import com.samsung.android.allshare.Item;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class Playlist {
  private Item.MediaType mPlayListType;
  private final ArrayList<Item> mPlaylist;

  private Playlist(ImageListBuilder builder) {
    this.mPlayListType = Item.MediaType.ITEM_UNKNOWN;
    this.mPlaylist = builder.mPlaylist;
    this.mPlayListType = Item.MediaType.ITEM_IMAGE;
  }

  private Playlist(AudioListBuilder builder) {
    this.mPlayListType = Item.MediaType.ITEM_UNKNOWN;
    this.mPlaylist = builder.mPlaylist;
    this.mPlayListType = Item.MediaType.ITEM_AUDIO;
  }

  private Playlist(VideoListBuilder builder) {
    this.mPlayListType = Item.MediaType.ITEM_UNKNOWN;
    this.mPlaylist = builder.mPlaylist;
    this.mPlayListType = Item.MediaType.ITEM_VIDEO;
  }

  public static class ImageListBuilder {
    private ArrayList<Item> mPlaylist;

    public ImageListBuilder() {
      this.mPlaylist = null;
      this.mPlaylist = new ArrayList<>();
    }

    public ImageListBuilder addItem(Item item) {
      if (item != null
          && item.getType() != null
          && item.getType().equals(Item.MediaType.ITEM_IMAGE)) {
        this.mPlaylist.add(item);
      }
      return this;
    }

    public Playlist build() {
      if (this.mPlaylist.isEmpty()) {
        return null;
      }
      return new Playlist(this);
    }
  }

  public static class AudioListBuilder {
    private ArrayList<Item> mPlaylist;

    public AudioListBuilder() {
      this.mPlaylist = null;
      this.mPlaylist = new ArrayList<>();
    }

    public AudioListBuilder addItem(Item item) {
      if (item != null
          && item.getType() != null
          && item.getType().equals(Item.MediaType.ITEM_AUDIO)) {
        this.mPlaylist.add(item);
      }
      return this;
    }

    public Playlist build() {
      if (this.mPlaylist.isEmpty()) {
        return null;
      }
      return new Playlist(this);
    }
  }

  public static class VideoListBuilder {
    private ArrayList<Item> mPlaylist;

    public VideoListBuilder() {
      this.mPlaylist = null;
      this.mPlaylist = new ArrayList<>();
    }

    public VideoListBuilder addItem(Item item) {
      if (item != null
          && item.getType() != null
          && item.getType().equals(Item.MediaType.ITEM_VIDEO)) {
        this.mPlaylist.add(item);
      }
      return this;
    }

    public Playlist build() {
      if (this.mPlaylist.isEmpty()) {
        return null;
      }
      return new Playlist(this);
    }
  }

  public final ArrayList<Item> getPlaylist() {
    ArrayList<Item> arrayList = this.mPlaylist;
    if (arrayList == null) {
      return new ArrayList<>();
    }
    return arrayList;
  }

  public final Item.MediaType getMediaType() {
    return this.mPlayListType;
  }
}
