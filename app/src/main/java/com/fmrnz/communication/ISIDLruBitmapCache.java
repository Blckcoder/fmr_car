package com.fmrnz.communication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;

import com.android.volley.toolbox.ImageLoader;


/**
 * Created by upen on 27/08/15.
 */
public class ISIDLruBitmapCache extends LruCache implements ImageLoader.ImageCache {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public ISIDLruBitmapCache(int maxSize) {
        super(maxSize);
    }

    public ISIDLruBitmapCache(Context context) {
        this(getCacheSize(context));
    }


    @Override
    public Bitmap getBitmap(String url) {
        return (Bitmap) get(url);
        //return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected int sizeOf(Object key, Object value) {
        Bitmap bitmap = (Bitmap) value;
        String strKey = (String) key;

        return bitmap.getRowBytes() * ((Bitmap) value).getHeight();
    }

    public static int getCacheSize(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 3;
    }
}
