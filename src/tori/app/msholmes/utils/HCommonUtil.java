package tori.app.msholmes.utils;

import android.content.Context;
import android.graphics.*;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class HCommonUtil {

    // ************************************************************************
    // 일반 유틸리티
    // ************************************************************************
    /**
     * 정상적인 이메일 형식인지 여부를 테스트한다.
     *
     * @param mailAddress 이메일 형식의 문자열
     * @return 메일 형식 여부
     */
    public static boolean isValidEmailAddress(String mailAddress) {
        String regExp = "^[A-Za-z0-9](([_\\.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";
        boolean isValid = mailAddress.matches(regExp);
        return isValid;
    }

    /**
     * 안전한 문자열을 만든다.
     * @param target 대상 문자열
     * @return 안전한 문자열
     */
    public static String getStringWithSafe(String target) {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(target);

        return sb.toString();
    }

    /**
     * 안전한 문자열을 만든다.
     * @param target 대상 문자열
     * @return 안전한 문자열
     */
    public static String getStringWithSafe(CharSequence target) {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(target);

        return sb.toString();
    }

    /**
     * 안전한 문자열을 만든다.
     * @param target int 데이터
     * @return int가 합쳐진 안전한 문자열
     */
    public static String getStringWithSafe(int target) {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(target);

        return sb.toString();
    }

    // ************************************************************************
    // 이미지 유틸리티
    // ************************************************************************
    /**
     * 모서리가 둥근 이미지를 만든다.
     * @param bitmap 원본 이미지
     * @param pixels 둥글게 할 모서리
     * @return 모서리가 둥근 이미지
     */
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        int color;
        Paint paint;
        Rect rect;
        RectF rectF;
        Bitmap result;
        Canvas canvas;
        float roundPx;

        result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(result);

        color = 0xff424242;
        paint = new Paint();
        rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectF = new RectF(rect);
        roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return result;
    }

    /**
     * 동그란 모양의 이미지 비트맵을 만든다.
     * @param scaleBitmapImage 원본 비트맵
     * @return 동그란 모양의 비트맵
     */
    public static Bitmap getRoundedCircleBitmap(Bitmap scaleBitmapImage) {
        int targetWidth = 150;
        int targetHeight = 150;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }


    // ************************************************************************
    // 통신 유틸리티
    // ************************************************************************


}
