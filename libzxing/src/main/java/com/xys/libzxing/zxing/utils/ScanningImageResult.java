package com.xys.libzxing.zxing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZH4 on 2016/11/11.
 * 识别图中二维码
 */

public class ScanningImageResult {

    private static Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Bitmap scanBitmap = BitmapFactory.decodeFile(path);
        return scanningImage(scanBitmap);
    }

    /**
     * 二维码的解析
     *
     * @param bitmap       二维码图片对象
     * @param //characterSet 编码。默认以 utf-8 此文字编码解析二维码文字内容
     * @return Result 对象。谷歌封装了解析的相关结果
     */
    public static Result scanningImage(Bitmap bitmap) {
        MultiFormatReader formatReader = new MultiFormatReader();

        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
        /*
        * 似乎之前的版本中，是封装bitmap对象到RgbLuminanceSource构造方法里的，现在变麻烦了
        * 我只能说，很糟糕的改变
        */
        int[] pixels = new int[bw * bh];
        bitmap.getPixels(pixels, 0, bw, 0, 0, bw, bh);
        RGBLuminanceSource source = new RGBLuminanceSource(bw, bh, pixels);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

        Map<DecodeHintType, String> hints = new HashMap();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        // hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//其他版本的写法
        Result result = null;
        try {
            result = formatReader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}


