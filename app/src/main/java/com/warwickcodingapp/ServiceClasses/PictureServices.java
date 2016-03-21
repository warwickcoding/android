package com.warwickcodingapp.ServiceClasses;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;

import com.warwickcodingapp.ModelClasses.User;
import com.warwickcodingapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PictureServices {

    public static void saveProfilePicFromBitmap(Bitmap newImage, int userID) {
        final String mPath = Environment.getExternalStorageDirectory().toString() + "/WarwickCoding/.ProfilePictures/" + "profile_"+userID +".jpg";

        OutputStream f_out;
        File imageFile = new File(mPath);

        try {
            f_out = new FileOutputStream(imageFile);
            newImage.compress(Bitmap.CompressFormat.JPEG, 90, f_out);
            f_out.flush();
            f_out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromStream(Uri selectedImage, int reqWidth, int reqHeight, Context context) {
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (imageStream != null) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(imageStream, null, options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            imageStream = null;
            try {
                imageStream = context.getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(imageStream, null, options);
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getCircleProfilePicture(User user, Context context) {
        if (user.getHasPicture() && user.getProfilePicture() != null) {
            //First square the bitmap
            Bitmap temp = createSquareBitmap(user.getProfilePicture());
            return getCircleBitmap(temp, temp.getWidth());
        }
        Bitmap temp = decodeSampledBitmapFromResource(context.getResources(),
                                                        R.drawable.profilepicture_placeholder, 80, 80);
        return getCircleBitmap(temp, temp.getWidth());
    }

    private static Bitmap getCircleBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap createSquareBitmap(Bitmap source) {
        Bitmap squareBitmap;
        if (source.getWidth() >= source.getHeight()){
            squareBitmap = Bitmap.createBitmap(
                    source,
                    source.getWidth()/2 - source.getHeight()/2,
                    0,
                    source.getHeight(),
                    source.getHeight()
            );
        }
        else{
            squareBitmap = Bitmap.createBitmap(
                    source,
                    0,
                    source.getHeight()/2 - source.getWidth()/2,
                    source.getWidth(),
                    source.getWidth()
            );
        }
        return squareBitmap;
    }
}


