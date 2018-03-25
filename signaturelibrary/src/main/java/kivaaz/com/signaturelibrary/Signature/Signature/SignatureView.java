package kivaaz.com.signaturelibrary.Signature.Signature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muguntan on 3/25/2018.
 */

public class SignatureView extends View {

    private static final float TOUCH_TOLERANCE = 4;
    private List<FingerPath> drawPath;
    private Path mPath;
    private float mX, mY;
    private static int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static int DEFAULT_DRAW_COLOR = Color.BLUE;
    private int strokeWidth = 12;
    private Paint drawPaint, canvasPaint;
    private int drawColor = DEFAULT_DRAW_COLOR;
    private int bckColor;
    private Canvas drawCanvas;
    private static Canvas currentCanvas;
    private Bitmap canvasBitmap;


    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bckColor = DEFAULT_BACKGROUND_COLOR;
        drawPath= new ArrayList<>();
        drawPaint = new Paint();


        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setupDrawing(DisplayMetrics metrics){
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        drawPaint.setColor(drawColor);
        drawPaint.setStrokeWidth(strokeWidth);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
        canvasBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void clearCanvas(){
        bckColor = DEFAULT_BACKGROUND_COLOR;
        drawPath.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        drawCanvas.drawColor(bckColor);

        for(FingerPath path : drawPath){
            drawCanvas.drawPath(path.path, drawPaint);
        }
        canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y){
        mPath = new Path();
        FingerPath fingerPath = new FingerPath(drawColor,mPath);
        drawPath.add(fingerPath);

        mPath.reset();
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    public Bitmap getCanvasBitmap() {
        return canvasBitmap;
    }

    private void touchMove(float x, float y){
        float dX = Math.abs(x - mX);
        float dY = Math.abs(y - mY);

        if (dX >= TOUCH_TOLERANCE || dY >= TOUCH_TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(touchX,touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(touchX,touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
                return false;
        }

        return true;
    }


    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
    public void setPenColor(int color) {
        drawColor = color;
    }

    public void setCanvasColor(int bckColor) {
        this.bckColor = bckColor;
    }


    public SignatureView(Context context) {
        this(context,null);
    }
}
